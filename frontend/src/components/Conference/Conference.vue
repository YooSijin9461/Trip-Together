<template>
  <div id="room">
    <div class="d-flex justify-content-center align-items-center py-2">
      <!-- 지도버튼 -->
      <div>
        <i class="fas fa-map-marked-alt map-icon" @click="clickMap"></i>
      </div>
      <h2 class="my-0 mx-auto">{{ state.conferenceTitle }}</h2>
      <!-- 채팅버튼 -->
      <div 
        type="button" 
        data-bs-toggle="offcanvas" 
        data-bs-target="#offcanvasScrolling" 
        aria-controls="offcanvasScrolling">
        <i class="far fa-comment-dots chatting-icon"></i>
      </div>
    </div>
    <hr>
    <div class="conference">
      <div class="conference-box">
        <div class="conference-container">
          <div :class="{ participants: state.mapVisible }" id="participants"></div>
          <div :class="{ showMap: state.mapVisible }">
            <div class="map">
              <Map/>
            </div>
          </div>
        </div>
        <div v-if="state.username === state.owner">
          <el-button type="danger" id="leave" @click="roomDelete()">나가기</el-button>
        </div>
        <div v-else>
          <el-button type="danger" id="leave" @click="leaveRoom()">나가기</el-button>
        </div>
      </div>
    </div>
  </div>
  <div class="offcanvas offcanvas-end" data-bs-scroll="true" data-bs-backdrop="false" tabindex="-1" id="offcanvasScrolling" aria-labelledby="offcanvasScrollingLabel">
    <div class="offcanvas-header">
      <h5 class="offcanvas-title" id="offcanvasScrollingLabel">채팅</h5>
      <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
    </div>
    <div class="offcanvas-body">
      <Chat/>
    </div>
  </div>
  
</template>

<script>
import { reactive, onMounted, onUnmounted, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import Chat from './Chat.vue'
import Map from './Map.vue'

export default {
  name: 'Conference',
  components: {
    Chat,
    Map,
  },
  setup() {
    const store = useStore()
    const router = useRouter()

    const state = reactive ({
      username: computed(() => store.getters['getUsername']),
      conferenceNo: computed(() => store.getters['getConferenceno']),
      conferenceTitle: computed(() => store.getters['getConferencetitle']),
      owner: computed(() => store.getters['getConferenceowner']),
      mapVisible: false,
    })

    const clickMap = () => {
      state.mapVisible = !state.mapVisible
      console.log(state.mapVisible)
    }

    // webRTC 기능 

    const kurentoUtils = require('kurento-utils')
    // const socket = new WebSocket('wss://i5d201.p.ssafy.io/groupcall');
    const socket = new WebSocket('wss://i5d201.p.ssafy.io:8443/groupcall');
    const PARTICIPANT_MAIN_CLASS = 'participant main';
    const PARTICIPANT_CLASS = 'participant';
    const participants = {}

    window.onbeforeunload = function() {
      socket.close()
    }

    socket.onmessage = (message) => {
      const parsedMessage = JSON.parse(message.data)
      console.info('Received message: ' + message.data)

      switch (parsedMessage.id) {
        case 'existingParticipants':
          onExistingParticipants(parsedMessage)
          break;
        case 'newParticipantArrived':
          onNewParticipant(parsedMessage);
          break;
        case 'participantLeft':
          onParticipantLeft(parsedMessage);
          break;
        case 'receiveVideoAnswer':
          receiveVideoResponse(parsedMessage);
          break;
        case 'iceCandidate':
          participants[parsedMessage.name].rtcPeer.addIceCandidate(parsedMessage.candidate, function (error) {
            if (error) {
            console.error("Error adding candidate: " + error)
            return;
            }
          });
          break;
        default:
          console.error('Unrecognized message', parsedMessage)
      }
    }

    const register = (name, room) => {
      const message = {
        id: 'joinRoom',
        name: name,
        room: room,
      }
      sendMessage(message)
    }
    const sendMessage = (message) => {
      const jsonMessage = JSON.stringify(message);
      socket.send(jsonMessage);
    }
    const onNewParticipant = (request) => {
      receiveVideo(request.name)
    }
    const receiveVideoResponse = (result) => {
      participants[result.name].rtcPeer.processAnswer (result.sdpAnswer, function (error) {
        if (error) return console.error (error)
      })
    }
    const callResponse = (message) => {
      if (message.response != 'accepted') {
        console.info('Call not accepted by peer. Closing call')
        stop()
      } else {
        this.webRtcPeer.processAnswer(message.sdpAnswer, function (error) {
          if (error) return console.error (error);
        })
      }
    }
    const onExistingParticipants = (msg) => {
      const constraints = {
        audio : true,
        video : {
          mandatory : {
            maxWidth : 320,
            maxFrameRate : 15,
            minFrameRate : 15
          }
        }
      }
      console.log(state.username + " registered in room " + state.conferenceNo);
      const participant = new Participant(state.username);
      participants[state.username] = participant;
      const video = participant.getVideoElement();

      const options = {
        localVideo: video,
        mediaConstraints: constraints,
        onicecandidate: participant.onIceCandidate.bind(participant)
      }
      participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendonly(options, function (error) {
        if(error) {
          return console.error(error);
        }
        this.generateOffer (participant.offerToReceiveVideo.bind(participant));
      });
      msg.data.forEach(receiveVideo);
    }
    const roomDelete = () => {
      leaveRoom()
      store.dispatch('conferenceDelete', state.conferenceNo)
    }
    const leaveRoom = () => {
      sendMessage({ id: 'leaveRoom' })
      for (const key in participants) {
        participants[key].dispose()
      }
      store.dispatch('conferenceLeave', state.conferenceNo)
      router.push({ name: 'ConferenceList' })
      socket.close()
    }
    const receiveVideo = (sender) => {
      const participant = new Participant(sender)
      participants[sender] = participant
      const video = participant.getVideoElement()

      const options = {
        remoteVideo: video,
        onicecandidate: participant.onIceCandidate.bind(participant)
      }

      participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(options, function (error) {
        if (error) {
          return console.error(error)
        }
        this.generateOffer (participant.offerToReceiveVideo.bind(participant))
      })
    }
    const onParticipantLeft = (request) => {
      console.log('Participant' + request.name + ' left')
      if (request.name === state.owner) {
        leaveRoom()
        ElMessage.error('회의가 종료되었습니다.')
        router.push({ name: 'ConferenceList'})
        location.reload(true)
      } else {
        const participant = participants[request.name]
        participant.dispose()
        delete participants[request.name]
      }
    }

    /**
     * Creates a video element for a new participant
     *
     * @param {String} name - the name of the new participant, to be used as tag
     *                        name of the video element.
     *                        The tag of the new element will be 'video<name>'
     * @return
     */
    
    function Participant(name) {
      this.name = name;
      var container = document.createElement('div');
      container.className = isPresentMainParticipant() ? PARTICIPANT_CLASS : PARTICIPANT_MAIN_CLASS;
      container.id = name;
      var span = document.createElement('span');
      var video = document.createElement('video');
      var rtcPeer;

      container.appendChild(video);
      container.appendChild(span);
      container.onclick = switchContainerClass;
      document.getElementById('participants').appendChild(container);

      span.appendChild(document.createTextNode(name));

      video.id = 'video-' + name;
      video.autoplay = true;
      video.controls = true;

      this.getElement = function() {
        return container;
      }

      this.getVideoElement = function() {
        return video;
      }

      function switchContainerClass() {
        if (container.className === PARTICIPANT_CLASS) {
          var elements = Array.prototype.slice.call(document.getElementsByClassName(PARTICIPANT_MAIN_CLASS));
          elements.forEach(function(item) {
              item.className = PARTICIPANT_CLASS;
            });

            container.className = PARTICIPANT_MAIN_CLASS;
          } else {
          container.className = PARTICIPANT_CLASS;
        }
      }

      function isPresentMainParticipant() {
        return ((document.getElementsByClassName(PARTICIPANT_MAIN_CLASS)).length != 0);
      }

      this.offerToReceiveVideo = function(error, offerSdp, wp){
        if (error) return console.error ("sdp offer error")
        console.log('Invoking SDP offer callback function');
        var msg =  { id : "receiveVideoFrom",
            sender : name,
            sdpOffer : offerSdp
          };
        sendMessage(msg);
      }


      this.onIceCandidate = function (candidate, wp) {
          console.log("Local candidate" + JSON.stringify(candidate));

          var message = {
            id: 'onIceCandidate',
            candidate: candidate,
            name: name
          };
          sendMessage(message);
      }

      Object.defineProperty(this, 'rtcPeer', { writable: true});

      this.dispose = function() {
        console.log('Disposing participant ' + this.name);
        this.rtcPeer.dispose();
        container.parentNode.removeChild(container);
      };
    }

    onMounted(() => {
      socket.onopen = () =>{
        register(state.username, state.conferenceNo)
      }
    })
    onUnmounted(() => {
      if(state.username === state.owner) {
        roomDelete()
      } else {
        leaveRoom()
      }
    })
    return { state, clickMap, leaveRoom, callResponse, roomDelete }
  }
}
</script>

<style>
#room {
  width: 100%;
  text-align: center;
}
.participants .participant, .participants .participant.main {
  width: 225px;
}
.participant {
	margin: 10px;
	width: 30%;
  max-width: 365px;
	text-align: center;
	overflow: hide;
	float: left;
	padding: 10px;
	border-radius: 10px;
  background-color: #e4ffe4;
	-webkit-box-shadow: 0 0 200px rgba(255, 255, 255, 0.5), 0 1px 2px
		rgba(0, 0, 0, 0.3);
	box-shadow: 0 0 200px rgba(255, 255, 255, 0.5), 0 1px 2px
		rgba(0, 0, 0, 0.3);
	/*Transition*/
	-webkit-transition: all 0.3s linear;
	-moz-transition: all 0.3s linear;
	-o-transition: all 0.3s linear;
	transition: all 0.3s linear;
}
.participant:hover {
	opacity: 1;
  cursor: pointer;
	background-color: lightgreen;
	-webkit-transition: all 0.5s linear;
	transition: all 0.5s linear;
}
.participant video, .participant.main video {
	width: 100%!important;
  height: auto!important;
  max-height: 300px;
  border-radius: 5px;
  margin-bottom: 2%;
}
.participant span {
    font-size: small;
    font-weight: bold;
    color: black;
}

.participant.main {
	width: 30%;
  max-width: 365px;
	margin: 10px;
  background-color: lightgreen;
}

.animate {
	-webkit-animation-duration: 0.5s;
	-webkit-animation-fill-mode: both;
	-moz-animation-duration: 0.5s;
	-moz-animation-fill-mode: both;
	-o-animation-duration: 0.5s;
	-o-animation-fill-mode: both;
	-ms-animation-duration: 0.5s;
	-ms-animation-fill-mode: both;
	animation-duration: 0.5s;
	animation-fill-mode: both;
}

.removed {
	-webkit-animation: disapear 1s;
	-webkit-animation-fill-mode: forwards;
	animation: disapear 1s;
	animation-fill-mode: forwards;
}

@-webkit-keyframes disapear { 
  50% {
    -webkit-transform: translateX(-5%);
    transform: translateX(-5%);
  }
  100% {
    -webkit-transform: translateX(200%);
    transform: translateX(200%);
  }
}
@keyframes disapear { 
  50% {
    -webkit-transform: translateX(-5%);
    transform: translateX(-5%);
  }
  100% {
    -webkit-transform: translateX(200%);
    transform: translateX(200%);
  }
}
a.hovertext {
	position: relative;
	width: 500px;
	text-decoration: none !important;
	text-align: center;
}
a.hovertext:after {
	content: attr(title);
	position: absolute;
	left: 0;
	bottom: 0;
	padding: 0.5em 20px;
	width: 460px;
	background: rgba(0, 0, 0, 0.8);
	text-decoration: none !important;
	color: #fff;
	opacity: 0;
	-webkit-transition: 0.5s;
	-moz-transition: 0.5s;
	-o-transition: 0.5s;
	-ms-transition: 0.5s;
}
a.hovertext:hover:after, a.hovertext:focus:after {
	opacity: 1.0;
}
.offcanvas-end {
  width: 300px;
}
.offcanvas-body {
  padding: 1rem 0;
}
.chatting-icon {
  font-size: 25px;
  padding-right: 2rem;
  position: absolute;
  top: 6.9rem;
  right: 1rem;
}
.chatting-icon:hover {
  color: lightgreen;
}
.map-icon {
  font-size: 25px;
  position: absolute;
  top:6.9rem;
  left: 7rem;
}
.map-icon:hover {
  cursor: pointer;
  color: lightgreen;
}
.conference {
  display: flex;
  justify-content: center;
}
.conference-box {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: absolute;
  height: 85%;
  width: 85%;
}
.conference-container {
  width: 100%;
  height: 100%;
}
.showMap {
  height: 100%;
}
.conference div .map {
  display: none;
  height: 100%;
}
.conference div.showMap .map {
  display: block;
  margin: 10px;
}
.vue-map-container {
  height: 75%;
  width: 100%;
}
.participants {
  display: flex;
  justify-content: center;
}
</style>