<template>
<div class="home">
  <div class="home-main">
    <div class="container">
      <el-carousel :interval="4000" type="card" height="400px">
        <el-carousel-item v-for="item in 6" :key="item">
          <!-- <h3 class="medium text-center">{{ item }}</h3> -->
          <img class="w-100" src="../../assets/room1.jpg" alt="">
        </el-carousel-item>
      </el-carousel>
    </div>
    <div class="container mt-5 home-box">
      <div class="row home-board">
        <div class="col-6">
          <el-tabs type="border-card">
            <el-tab-pane label="공지사항">
              <div class="container mt-3">
                <span v-for="(notice, index) in state.noticeList" :key="index">
                  <hr class="my-0 home-board-line">
                  <div class="home-board-box d-flex" @click="clickNotice(notice.noticeNo)">
                    <div class="ms-3">
                      <p class="mb-0 title">{{ notice.noticeTitle }}</p>
                    </div>
                  </div>
                </span>
                <div v-if="state.noticeList.length">
                  <hr class="my-0 home-board-line">
                </div>
                <div v-if="state.noticeList.length === 6" class="more mt-3" @click="clickMoreNotice">
                  <p>더 보기...</p>
                </div>
              </div>
            </el-tab-pane>
            
            <el-tab-pane class="w-100 h-100" label="방 목록">
              <div class="container mt-3">
                <span v-for="(conference, index) in state.conferenceList" :key="index">
                  <hr class="my-0 home-board-line">
                  <div class="home-board-box d-flex" @click="clickConference(conference.conferenceNo)">
                    <div class="col-8 ms-3">
                      <p class="mb-0 title">{{ conference.title }}</p>
                    </div>
                    <div class="col d-flex">
                      <p class="mb-0 owner"><i class="fas fa-user owner me-2"></i>{{ conference.owner }}</p>
                    </div>
                  </div>
                </span>
                <div v-if="state.conferenceList.length">
                  <hr class="my-0 home-board-line">
                </div>
                <div v-if="state.noticeList.length === 6" class="more mt-3" @click="clickMoreConference">
                  <p>더 보기...</p>
                </div>
              </div>
            </el-tab-pane>

            <el-tab-pane class="w-100 h-100" label="게시글">
              <div class="container mt-3">
                <span v-for="(article, index) in state.articleList" :key="index">
                  <hr class="my-0 home-board-line">
                  <div class="home-board-box d-flex" @click="clickArticle(article.boardNo)">
                    <div class="col-8 ms-3">
                      <p class="mb-0 title">{{ article.boardTitle }}</p>
                    </div>
                    <div class="col d-flex">
                      <p class="mb-0 owner"><i class="fas fa-user owner me-2"></i>{{ article.userId }}</p>
                    </div>
                  </div>
                </span>
                <div v-if="state.articleList.length">
                  <hr class="my-0 home-board-line">
                </div>
                <div v-if="state.noticeList.length === 6" class="more mt-3" @click="clickMoreArticle">
                  <p>더 보기...</p>
                </div>
              </div>
            </el-tab-pane>
          </el-tabs>
        </div>
        <div id="home-map" class="col-6">
        </div>
      </div>
    </div>
  </div>
</div>
</template>

<script>
import { reactive, onMounted, computed, onUpdated } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'

export default {
  name: 'Home',
  setup(props, { emit }) {
    const router = useRouter()
    const store = useStore()

    const state = reactive ({
      token: computed (() => store.getters['getToken']),
      conferenceList: [],
      articleList: [],
      noticeList: [],
      center: { lat: 37.564214, lng: 127.001699 },
      marker: {
        position: {
          lat: 37.564214,
          lng: 127.001699}
        },
    })
    const clickMoreConference = () => {
      router.push({ name: 'ConferenceList' })
    }
    const clickMoreArticle = () => {
      router.push({ name: 'ArticleList' })
    }
    const clickMoreNotice = () => {
      router.push({ name: 'NoticeList' })
    }
    const clickArticle = (boardNo) => {
      if (state.token) {
        store.dispatch('articleDetail', boardNo)
          .then(() => {
            router.push({ name: 'Article', params: { articleId: boardNo }})
          })
      } else {
        ElMessage.error('로그인이 필요합니다.')
      }
    }
    const clickConference = (conferenceNo) => {
      store.dispatch('conferenceDetail', conferenceNo)
        .then(() => {
          // register(state.username, conferenceNo)
          // router.push({ name: 'Conference', params: { conferenceId: conferenceNo}})
          emit('openConferenceDialog')
        })
    }
    const clickNotice = (noticeNo) => {
      if (state.token) {
        store.dispatch('noticeDetail', noticeNo)
          .then(() => {
            router.push({ name: 'Notice', params: { noticeId: noticeNo }})
          })
      } else {
        ElMessage.error('로그인이 필요합니다.')
      }
    }

    const google = window.google
    var map, infoWindow
    function initMap() {
      map = new google.maps.Map(document.getElementById('home-map'), {
        center: { lat: 37.564214, lng: 127.001699 },
        zoom: 10,
        styles: [{
          featureType: 'poi',
          stylers: [{ visibility: 'on' }]  // Turn off POI.
        },
        {
          featureType: 'transit.station',
          stylers: [{ visibility: 'on' }]  // Turn off bus, train stations etc.
        }],
        disableDoubleClickZoom: true,
        streetViewControl: true,
      });
      infoWindow = new google.maps.InfoWindow();
      const locationButton = document.createElement("button");
      locationButton.textContent = "현재 위치 찾기";
      locationButton.classList.add("custom-map-control-button");
      map.controls[google.maps.ControlPosition.TOP_CENTER].push(locationButton);
      locationButton.addEventListener("click", () => {
        // Try HTML5 geolocation.
        if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(
            (position) => {
              const pos = {
                lat: position.coords.latitude,
                lng: position.coords.longitude,
              };
              infoWindow.setPosition(pos);
              infoWindow.setContent("현재 위치입니다.");
              infoWindow.open(map);
              map.setCenter(pos);
            },
            () => {
              handleLocationError(true, infoWindow, map.getCenter());
            }
          );
        } else {
          // Browser doesn't support Geolocation
          handleLocationError(false, infoWindow, map.getCenter());
        }
      });
    }
    function handleLocationError(browserHasGeolocation, infoWindow, pos) {
      infoWindow.setPosition(pos);
      infoWindow.setContent(
        browserHasGeolocation
          ? "Error: The Geolocation service failed."
          : "Error: Your browser doesn't support geolocation."
      );
      infoWindow.open(map);
    }

    onMounted (() => {
      initMap()
      store.dispatch('noticePageList')
        .then(({ data }) => {
          state.noticeList = data.content.slice(0, 6)
        })
      store.dispatch('conferenceList')
        .then(({ data }) => {
          state.conferenceList = data.content.slice(0, 6)
        })
      store.dispatch('articlePageList')
        .then(({ data }) => {
          state.articleList = data.content.slice(0, 6)
        })
    })
    onUpdated (() => {
      store.dispatch('noticePageList')
        .then(({ data }) => {
          state.noticeList = data.content.slice(0, 6)
        })
      store.dispatch('conferenceList')
        .then(({ data }) => {
          state.conferenceList = data.content.slice(0, 6)
        })
      store.dispatch('articlePageList')
        .then(({ data }) => {
          state.articleList = data.content.slice(0, 6)
        })
    })

    return { state, clickMoreConference, clickMoreArticle, clickMoreNotice, clickArticle, clickConference, clickNotice, initMap, map }
  },
}
</script>

<style>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}
.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}
.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
.more {
  text-align: end;
}
.more:hover {
  cursor: pointer;
  font-weight: bold;
  color: green;
}
th {
  width: 100px;
}
.home-board-box {
  padding: 15px 0px;
  align-items: center;
}
.home-board-box:hover {
  cursor: pointer;
  background-color: #e4ffe4;
  font-weight: bold;
  color: green;
}
.owner {
  font-size: 12px;
}
.el-tabs--border-card {
  border: 1px solid lightgreen;
  height: 100%;
}
.el-tabs--border-card>.el-tabs__header {
  border-bottom: 1px solid lightgreen;
  background-color: #e4ffe4;
}
.el-tabs--border-card>.el-tabs__header .el-tabs__item.is-active {
  color: green;
  border-right-color: lightgreen;
  border-left-color: lightgreen;
}
.el-tabs--border-card>.el-tabs__header .el-tabs__item:not(.is-disabled):hover {
  color: green;
}
.home {
  display: flex;
  justify-content: center;
}
.home-main {
  position: absolute;
  width: 80%;
  height: 85%;
}
.home-box {
  height: 55% !important;
}
.home-board {
  height: 100%;
}
.home-board-line {
  color: green;
}
#home-map {
  border: 1px solid lightgreen;
}
.custom-map-control-button {
  background-color: #fff;
  border: 1px solid lightgreen;
  border-radius: 5px;
  box-shadow: 0 1px 4px -1px rgba(0, 0, 0, 0.3);
  margin-top: 20px;
  padding: 0 0.5em;
  font: 400 15px Roboto, Arial, sans-serif;
  overflow: hidden;
  height: 30px;
  cursor: pointer;
}
.custom-map-control-button:hover {
  background: #e4ffe4;
}
</style>