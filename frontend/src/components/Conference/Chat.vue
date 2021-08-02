<template>
  <div>
    <div id="conversation">
    </div>
    <div>
      <textarea class="chatInput" v-model="state.message" type="text" placeholder="채팅을 입력하세요" @keyup.enter="sendChat"/>
    </div>
  </div>
</template>

<script>
import { onMounted, onUnmounted, reactive, computed } from 'vue'
import SockJS from 'sockjs-client'
import Stomp from 'stomp-websocket'
import { useStore } from 'vuex'

export default {
  setup() {
    const store = useStore()

    const state = reactive ({
      stompClient: null,
      name: computed(() => store.getters['getUsername']),
      message: '',
    })

    const connect = () => {
      const socket = new SockJS('/websocket');
      state.stompClient = Stomp.over(socket);
      state.stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        state.stompClient.subscribe('/topic/chat', function (chat) {
          showChat(JSON.parse(chat.body));
        });
      });
    }
    const disconnect = () => {
      if (state.stompClient !== null) {
        state.stompClient.disconnect();
      }
      console.log("Disconnected");
    }
    const sendChat = () => {
      const date = new Date();
      const dateInfo = date.getHours() + ":" + ("0" + date.getMinutes()).slice(-2)
      state.stompClient.send("/app/chat", {}, JSON.stringify({'name': state.name, 'message': state.message, 'date': dateInfo}));
      state.message = ''
    }

    function showChat(chat) {
      $("#conversation").append(
        "<div class='chatting'><span>" + chat.name + "</span>" + 
        "<div class='chat-message-time'><div class='chatMessage'>" + chat.message + "</div>" + 
        "<span>" + chat.date + "</span></div></div>"
      );
    }

    onMounted (() => {
      connect()
    })

    onUnmounted (() => {
      disconnect()
    })

    return { state, sendChat }
  },
}

</script>

<style>
.chatting {
  display: block;
}
.chat-message-time {
  display: flex;
  width: 100%;
  align-items: flex-end;
}
.chatMessage {
  word-break: break-all;
  max-width: 70%;
  background-color: blanchedalmond;
  border-radius: 5px;
  padding: 5px;
}
.chatInput {
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 7rem;
  padding: 1rem;
  outline: none;
  border: none;
  overflow: auto;
  border-top: 1px solid gray;
  word-break: break-all;
}
#conversation {
  padding: 0 1rem;
}
</style>