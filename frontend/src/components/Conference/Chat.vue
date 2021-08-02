<template>
  <div class="chatBody">
    <div id="conversation">
      <div v-for="(chat, index) in state.chatList" :key="index">
        <div v-if="chat.name === state.name">
          <div class="myChatBox">
            <div class="myChatMessage">{{ chat.message }}</div>
            <p class="chatDate mb-0">{{ chat.date }}</p>
          </div>
        </div>
        <div v-else>
          <div class="chatting">
            <div v-show="!index || state.chatList[index - 1].name !== chat.name">
              <p class="mb-0">{{ chat.name }}</p>
            </div>
            <div class="chatBox">
              <div class="chatMessage">{{ chat.message }}</div>
              <p class="chatDate mb-0">{{ chat.date }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div>
    <textarea class="chatInput" v-model="state.message" type="text" placeholder="채팅을 입력하세요" @keyup.enter="sendChat"/>
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
      chatList: [],
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
      state.chatList.push(chat)

      // state.chatList.push(chat)
      // $("#conversation").append(
      //   "<div class='chatting'><span>" + chat.name + "</span>" + 
      //   "<div class='chat-message-time'><div class='chatMessage'>" + chat.message + "</div>" + 
      //   "<span>" + chat.date + "</span></div></div>"
      // );
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
.chatBody {
  position: absolute;
  width: 100%;
  top: 4rem;
  bottom: 7rem;
  overflow: auto;
}
.chatting {
  display: block;
}
.myChatting {
  display: block;
  text-align: end;
  float: inline-end;
}
.chatBox {
  display: flex;
  align-items: flex-end;
  width: 100%;
  margin-bottom: 6px;
}
.myChatBox {
  display: flex;
  flex-direction: row-reverse;
  align-items: flex-end;
  width: 100%;
  margin-bottom: 6px;
}
.chatMessage {
  word-break: break-all;
  max-width: 80%;
  background-color: lightblue;
  border-radius: 5px;
  padding: 5px;
  margin: 0 2px;
}
.myChatMessage {
  word-break: break-all;
  max-width: 80%;
  background-color: blanchedalmond;
  border-radius: 5px;
  padding: 5px;
  margin: 0 2px;
}
.chatDate {
  font-size: 6px;
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