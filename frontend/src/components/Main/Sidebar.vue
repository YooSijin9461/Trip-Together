<template>
  <div class="sidebar" :class="{ open: state.isOpen }">
    <div class="logo-details">
      <img class="icon ms-2" src="../../assets/TTlogo.png" @click="clickLogo">
      <i class="fas fa-bars" id="btn" @click="closeBtn"></i>
    </div>
    <ul class="side-list nav-links">
      <li>
        <a @click="clickSideHome">
          <i class="fas fa-home" @click="clickSideHome"></i>
          <span class="links_name">홈</span>
        </a>
        <span class="tooltip">홈</span>
      </li>
      <li :class="{ showMenu: state.conferenceShow }">
        <div class="icon-link">
          <a>
            <i class="fas fa-video" @click="clickSideConference"></i>
            <span class="links_name me-auto">방</span>
            <span class="tooltip">방</span>
            <i class='bx bxs-chevron-down arrow links_name' @click="clickConferenceShow"></i>
          </a>
        </div>
        <ul class="sub-menu">
          <li><a class="link_name" href="#">방</a></li>
          <li><a @click="clickConferenceCategory('')">전체보기</a></li>
          <li><a @click="clickConferenceCategory('normal')">일반</a></li>
          <li><a @click="clickConferenceCategory('guide')">가이드</a></li>
        </ul>
      </li>
      <li :class="{ showMenu: state.articleShow }">
        <div class="icon-link">
          <a>
            <i class="far fa-clipboard" @click="clickSideBoard"></i>
            <span class="links_name me-auto">게시판</span>
            <span class="tooltip">게시판</span>
            <i class='bx bxs-chevron-down arrow links_name' @click="clickArticleShow"></i>
          </a>
        </div>
        <ul class="sub-menu">
          <li><a class="link_name" href="#">게시판</a></li>
          <li><a @click="clickSideNotice">공지사항</a></li>
          <li><a @click="clickSideArticle">게시글</a></li>
        </ul>
      </li>
      <li>
        <a @click="clickSideSearch">
          <i class="fas fa-search" @click="clickSideSearch"></i>
          <span class="links_name">검색</span>
        </a>
        <span class="tooltip">검색</span>
      </li>
      <li>
        <a @click="clickSideBack">
          <i class="fas fa-backward" @click="clickSideBack"></i>
          <span class="links_name">뒤로가기</span>
        </a>
        <span class="tooltip">뒤로가기</span>
      </li>
      <div v-show="state.token">
        <li class="side-profile">
          <div class="profile-details">
            <img :src="'/upload/' + state.userImg"  alt="">
            <div class="name">{{ state.username }}</div>
          </div>
          <i class="bx bx-log-out" id="log_out" @click="clickSideLogout"></i>
        </li>
      </div>
    </ul>
  </div>
</template>

<script>
import { reactive, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'

export default {
  setup(props, { emit }) {
    const router = useRouter()
    const store = useStore()

    const state = reactive ({
      isOpen: false,
      conferenceShow: false,
      articleShow: false,
      token: computed(() => store.getters['getToken']),
      username: computed(() => store.getters['getUsername']),
      userImg: computed(() => store.getters['getUserimg'])
    })
    const closeBtn = () => {
      state.isOpen = !state.isOpen
      if (state.isOpen === false) {
        state.conferenceShow = false
        state.articleShow = false
      }
    }
    const clickConferenceShow = () => {
      state.conferenceShow = !state.conferenceShow
    }
    const clickArticleShow = () => {
      state.articleShow = !state.articleShow
    }
    const clickSideHome = () => {
      router.push({ name: 'Home' })
      state.isOpen = false
      state.conferenceShow = false
      state.articleShow = false
    }
    const clickSideConference = () => {
      router.push({ name: 'ConferenceList' })
    }
    const clickConferenceCategory = (category) => {
      state.isOpen = false
      state.conferenceShow = false
      state.articleShow = false
      
      if (category === 'normal') {
        router.push({ name: 'ConferenceNormal' })
      }
      else if (category === 'guide') {
        router.push({ name: 'ConferenceGuide' })
      } 
      else if (category === '') {
        router.push({ name: 'ConferenceAll' })
      }
    }
    const clickSideBoard = () => {
      router.push({ name: 'Board' })
    }
    const clickSideNotice = () => {
      router.push({ name: 'NoticeList'})
      state.isOpen = false
      state.articleShow = false
      state.conferenceShow = false
    }
    const clickSideArticle = () => {
      router.push({ name: 'ArticleList'})
      state.isOpen = false
      state.articleShow = false
      state.conferenceShow = false
    }
    const clickSideSearch = () => {
      emit('openSearchDialog')
    }
    const clickSideLogout = () => {
      emit('openLogoutDialog')
    }
    const clickSideBack = () => {
      router.go(-1)
    }
    return { state, closeBtn, clickConferenceShow, clickArticleShow, clickSideHome, clickSideConference, clickConferenceCategory, clickSideBoard, clickSideSearch, clickSideLogout, clickSideNotice, clickSideArticle, clickSideBack }
  },
}
</script>

<style>
*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
a:hover {
  cursor: pointer;
}
.sidebar{
  position: fixed;
  left: 0;
  top: 0;
  height: 100%;
  width: 78px;
  background: lightgreen;
  padding: 6px 14px;
  z-index: 99;
  transition: all 0.5s ease;
  opacity: 0.85;
}
.sidebar.open{
  width: 250px;
  background-color: lightgreen;
  opacity: 0.85;
}
.sidebar .logo-details{
  height: 60px;
  display: flex;
  align-items: center;
  position: relative;
}
.icon {
  width: 125px;
  height: 60px;
}
.sidebar .logo-details .icon{
  opacity: 0;
  transition: all 0.5s ease;
}
.sidebar.open .logo-details .icon {
  opacity: 1;
}
.sidebar .logo-details #btn:hover {
  color: white;
  font-size: 25px;
}
.sidebar .logo-details #btn{
  position: absolute;
  top: 50%;
  right: 0;
  transform: translateY(-50%);
  font-size: 22px;
  transition: all 0.4s ease;
  font-size: 23px;
  text-align: center;
  cursor: pointer;
  transition: all 0.5s ease;
}
.sidebar.open .logo-details #btn{
  text-align: right;
}
.sidebar i{
  color: black;
  height: 60px;
  min-width: 50px;
  font-size: 28px;
  text-align: center;
  line-height: 60px;
}
.sidebar .side-list{
  padding-left: 0;
  margin-top: 20px;
  height: 100%;
}
.sidebar li{
  position: relative;
  margin: 8px 0;
  list-style: none;
}
.sidebar li .tooltip{
  position: absolute;
  top: -20px;
  left: calc(100% + 15px);
  z-index: 3;
  background: white;
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.3);
  padding: 6px 12px;
  border-radius: 4px;
  font-size: 15px;
  font-weight: 400;
  opacity: 0;
  white-space: nowrap;
  pointer-events: none;
  transition: 0s;
}
.sidebar li:hover .tooltip{
  opacity: 1;
  pointer-events: auto;
  transition: all 0.4s ease;
  top: 50%;
  transform: translateY(-50%);
}
.sidebar.open li .tooltip{
  display: none;
}
.sidebar.open input{
  padding: 0 20px 0 50px;
  width: 100%;
}
.sidebar li a{
  display: flex;
  height: 100%;
  width: 100%;
  border-radius: 12px;
  align-items: center;
  text-decoration: none;
  transition: all 0.4s ease;
  background: lightgreen;
}
.sidebar li a:hover{
  background: white;
}
.sidebar li a .links_name{
  color: black;
  font-size: 15px;
  font-weight: 400;
  white-space: nowrap;
  opacity: 0;
  pointer-events: none;
  transition: 0.4s;
}
.sidebar.open li a .links_name{
  opacity: 1;
  pointer-events: auto;
}
.sidebar li a:hover .links_name,
.sidebar li a:hover i{
  transition: all 0.5s ease;
  color: green;
  font-weight: bold;
}
.sidebar li i{
  height: 50px;
  line-height: 50px;
  font-size: 18px;
  border-radius: 12px;
}
.sidebar li.side-profile{
  position: fixed;
  height: 60px;
  width: 78px;
  left: 0;
  bottom: -8px;
  padding: 10px 14px;
  background: white;
  transition: all 0.5s ease;
  overflow: hidden;
}
.sidebar.open li.side-profile{
  width: 250px;
}
.sidebar li .profile-details{
  display: flex;
  align-items: center;
  flex-wrap: nowrap;
}
.sidebar li img{
  height: 45px;
  width: 45px;
  object-fit: cover;
  border-radius: 6px;
  margin-right: 10px;
}
.sidebar li.side-profile .name {
  font-size: 15px;
  font-weight: 400;
  color: black;
  white-space: nowrap;
}
.sidebar .side-profile #log_out{
  position: absolute;
  top: 50%;
  right: 0;
  transform: translateY(-50%);
  background: white;
  width: 100%;
  height: 60px;
  line-height: 60px;
  border-radius: 0px;
  transition: all 0.5s ease;
}
.sidebar .side-profile #log_out:hover{
  cursor: pointer;
}

.sidebar.open .side-profile #log_out{
  width: 50px;
  background: none;
}
.sidebar.open .side-profile #log_out:hover{
  cursor: pointer;
}
.sidebar .nav-links li .icon-link {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.sidebar .nav-links li .icon-link .tooltip{
  color: black;
}
.sidebar .nav-links li.showMenu {
  display: block;
}
.sidebar .nav-links li.showMenu .icon-link {
  background-color: white;
  border-radius: 12px 12px 0 0;
}
.sidebar .nav-links li.showMenu .icon-link a{
  background-color: white;
  color: lightgreen;
}
.sidebar .nav-links li.showMenu .icon-link a i{
  color: green;
  font-weight: bold;
}
.sidebar .nav-links li.showMenu .icon-link a span{
  color: green;
  font-weight: bold;
}
.sidebar .nav-links li.showMenu .sub-menu li {
  color: green;
  background-color: white;
}
.sidebar .nav-links li.showMenu i.arrow{
  transform: rotate(-180deg);
}
.sidebar .nav-links li.showMenu .sub-menu li a {
    color: green;
    background-color: white;
}
.sidebar .nav-links li.showMenu .sub-menu li a:hover {
    color: green;
    font-weight: bold;
}
.sidebar .nav-links li .sub-menu{
  padding: 6px 6px 14px 80px;
  margin-top: -10px;
  background: white;
  border-radius: 12px;
  display: none;
}
.sidebar .nav-links li.showMenu .sub-menu{
  display: block;
  padding-left: 50px;
}
.sidebar .nav-links li .sub-menu a{
  color: white;
  font-size: 15px;
  padding: 5px 0;
  white-space: nowrap;
  opacity: 0.6;
  transition: all 0.3s ease;
}
.sidebar .nav-links li .sub-menu a:hover{
  opacity: 1;
}
.sidebar .nav-links li .sub-menu .link_name{
  display: none;
}
.sidebar .nav-links li .sub-menu.blank{
  opacity: 1;
  pointer-events: auto;
  padding: 3px 20px 6px 16px;
  opacity: 0;
  pointer-events: none;
}
.sidebar .nav-links li:hover .sub-menu.blank{
  top: 50%;
  transform: translateY(-50%);
}

@media (max-width: 420px) {
  .sidebar li .tooltip{
    display: none;
  }
  .sidebar.close .nav-links li .sub-menu{
    display: none;
  }
}
</style>