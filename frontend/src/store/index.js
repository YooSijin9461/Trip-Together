import { createStore } from 'vuex'
import axios from 'axios'
import createPersistedState from 'vuex-persistedstate'
import jwt_decode from 'jwt-decode'

// const BASE_URL = 'i5d201.p.sasfy.io';
const BASE_URL = process.env.VUE_APP_SERVER_URL;
console.log(BASE_URL);
const getDefaultState = () => {
  return {
    // 유저
    token: null,
    isLogin: false,
    username: null,
    userid: null,
    userage: null,
    usergender: null,
    userphonenum: null,
    useremail: null,
    usermbti: null,
    isGuide: false,
    userprofileimg: null,
    userConference: [],

    // 프로필
    profileName: null,
    profileId: null,
    profileAge: null,
    profileGender: null,
    profilePhonenum: null,
    profileEmail: null,
    profileMBTI: null,
    profileGuide: false,
    profileImg: null,

    // 방
    conferenceNo: null,
    conferenceCategory: null,
    conferencePassword: null,
    conferenceOwner: null,
    conferenceTitle: null,
    conferenceDescription: null,
    conferenceLimit: null,
    conferenceActive: false,
    conferenceThumbnail: null,

    // 게시글
    boardNo: null,
    boardUserId: null,
    boardTitle: null,
    boardContent: null,
    boardRate: null,
    boardCreate: null,

    // 댓글
    commentList: [],

    // 공지
    noticeNo: null,
    noticeTitle: null,
    noticeContent: null,
    noticeCreate: null,
  }
}

export default createStore({
  state: getDefaultState(),

  mutations: {
    // 유저
    SET_TOKEN (state, { accessToken }) {
      state.token = accessToken
    },
    LOGIN (state) {
      state.isLogin = true
    },
    USERINFO (state, userinfo) {
      state.username = userinfo.userName
      state.userid = userinfo.userId
      state.userage = userinfo.age
      state.usergender = userinfo.gender
      state.userphonenum = userinfo.phoneNum
      state.useremail = userinfo.email
      state.usermbti = userinfo.mbti
      state.isGuide = userinfo.guide
    },
    LOGOUT (state) {
      state.isLogin = false
    },
    CLEAR_STATE (state) {
      Object.assign(state, getDefaultState())
    },
    USERPROFILE (state, profile) {
      state.profileName = profile.userName
      state.profileId = profile.userId
      state.profileGender = profile.gender
      state.profilePhonenum = profile.phoneNum
      state.profileEmail = profile.email
      state.profileAge = profile.age
      state.profileImg = profile.img
      state.profileGuide = profile.guide
      state.profileMBTI = profile.mbti
    },
    USERCONFERENCE (state, userConference) {
      state.userConference = userConference
    },

    // 방
    CONFERENCEINFO (state, conferenceinfo) {
      state.conferenceNo = conferenceinfo.conferenceNo
      state.conferenceCategory = conferenceinfo.conferenceCategory
      state.conferencePassword = conferenceinfo.conferencePassword
      state.conferenceOwner = conferenceinfo.owner
      state.conferenceTitle = conferenceinfo.title
      state.conferenceDescription = conferenceinfo.description
      state.conferenceLimit = conferenceinfo.limitUsersNum
      state.conferenceActive = conferenceinfo.active
      state.conferenceThumbnail = conferenceinfo.thumbnailUrl
    },
    
    // 게시글
    BOARDINFO (state, boardinfo) {
      state.boardNo = boardinfo.boardNo
      state.boardUserId = boardinfo.userId
      state.boardTitle = boardinfo.boardTitle
      state.boardContent = boardinfo.boardContent
      state.boardRate = boardinfo.boardRate
      state.boardCreate = boardinfo.boardTime
    },

    // 댓글
    COMMENTLIST (state, comments) {
      state.commentList = comments
    },

    // 공지
    NOTICEINFO (state, noticeinfo) {
      state.noticeNo = noticeinfo.noticeNo
      state.noticeTitle = noticeinfo.noticeTitle
      state.noticeContent = noticeinfo.noticeContent
      state.noticeCreate = noticeinfo.noticeTime
    }
  },

  actions: {
    // 유저
    login ({ commit }, payload) {
      return axios
        .post(`${BASE_URL}/api/v1/auth/login`, payload)
        .then(({ data }) => {
          commit("SET_TOKEN", data)
          commit("LOGIN")
          const decode = jwt_decode(data.accessToken)
          commit("USERINFO", decode.data)
        })
    },
    logout ({ commit }) {
      commit("CLEAR_STATE")
    },
    signup ({ commit }, payload) {
      return axios
        .post(`${BASE_URL}/api/v1/users`, payload)
    },
    // profile ({ commit }, token) {
    //   return axios
    //     .get(`${BASE_URL}/api/v1/users/me`,
    //       { 
    //         headers: { "Authorization" : "Bearer " + token }
    //       }
    //     )
    // },
    profile ({ commit }, userId) {
      return axios
        .post(`${BASE_URL}/api/v1/users/${userId}`)
        .then(({ data }) => {
          commit("USERPROFILE", data)
        })
    },
    userConference ({ commit }, conferenceNo) {
      return axios
        .get(`${BASE_URL}/api/v1/users/conference/${conferenceNo}`)
        .then(({ data }) => {
          commit("USERCONFERENCE", data)
        })
    },
    profileImg ({ commit }, payload) {
      return axios
        .post(`${BASE_URL}/api/v1/users`, payload,
          { 
            headers: {
              "Accept": "*/*", 
              "Content-Type": "multipart/form-data"
            }
          }
        )
    },

    // 방
    conferenceList ({ commit }) {
      return axios
        .get(`${BASE_URL}/api/v1/conferences`)
    },
    conferenceCreate ({ commit }, payload) {
      return axios
        .post(`${BASE_URL}/api/v1/conferences`, payload)
    },
    conferenceDetail ({ commit }, conferenceNo) {
      return axios
        .get(`${BASE_URL}/api/v1/conferences/${conferenceNo}`)
        .then(({ data }) => {
          commit("CONFERENCEINFO", data)
        })
    },
    conferenceSearch ({ commit }, { searchKey, searchValue }) {
      const params = new URLSearchParams([[searchKey, searchValue]])
      return axios
        .get(`${BASE_URL}/api/v1/conferences/search`, { params })
    },
    conferenceDelete ({ commit }, conferenceNo) {
      return axios
        .delete(`${BASE_URL}/api/v1/conferences/${conferenceNo}`)
    },
    conferenceEnter ({ commit }, params) {
      return axios
        .patch(`${BASE_URL}/api/v1/users/conference?conferenceNo=${params.conferenceNo}&userId=${params.userId}`)
    },
    conferenceLeave ({ commit }, userId) {
      return axios
        .patch(`${BASE_URL}/api/v1/users/conferencedel?userId=${userId}`)
    },

    // 게시글
    articlePageList ({ commit }, pageNo) {
      return axios
        .get(`${BASE_URL}/api/v1/boards?page=${pageNo}&size=10`)
    },
    articleDetail ({ commit }, boardNo) {
      return axios
        .get(`${BASE_URL}/api/v1/boards/${boardNo}`)
        .then(({ data }) => {
          commit("BOARDINFO", data)
        })
    },
    articleCreate ({ commit }, payload) {
      return axios
        .post(`${BASE_URL}/api/v1/boards`, payload)
    },
    articleSearch ({ commit }, { searchKey, searchValue }) {
      const params = new URLSearchParams([[searchKey, searchValue]])
      return axios
      .get(`${BASE_URL}/api/v1/boards/search`, { params })
    },
    articleUpdate ({ commit }, payload) {
      return axios
        .patch(`${BASE_URL}/api/v1/boards/${payload.boardNo}`, payload.data)
    },
    articleDelete ({ commit }, boardNo) {
      return axios
        .delete(`${BASE_URL}/api/v1/boards/${boardNo}`)
    },
    userArticle ({ commit }, username) {
      return axios
        .get(`${BASE_URL}/api/v1/boards/user/${username}`)
    },
    
    // 댓글
    createComment ({ commit }, payload) {
      return axios
        .post(`${BASE_URL}/api/v1/comments`, payload)
    },
    commentList ({ commit }, articleNo) {
      return axios
        .get(`${BASE_URL}/api/v1/comments/list/${articleNo}`)
        .then(({ data }) => {
          commit("COMMENTLIST", data)
        })
    },
    deleteComment ({ commit }, commentNo ) {
      return axios
        .delete(`${BASE_URL}/api/v1/comments/${commentNo}`)
    },
    updateComment ({ commit }, payload) {
      return axios
        .patch(`${BASE_URL}/api/v1/comments/${payload.commentNo}`, payload.data)
    },
    commentDetail ({ commit }, commentNo) {
      return axios
        .post(`${BASE_URL}/api/v1/comments/${commentNo}`)
    },
    userComment ({ commit }, userId) {
      return axios
        .get(`${BASE_URL}/api/v1/comments/user/${userId}`)
    },

    // 공지
    noticePageList ({ commit }, pageNo) {
      return axios
        .get(`${BASE_URL}/api/v1/notices?page=${pageNo}&size=10`)
    },
    noticeDetail ({ commit }, noticeNo) {
      return axios
        .get(`${BASE_URL}/api/v1/notices/${noticeNo}`)
        .then(({ data }) => {
          commit("NOTICEINFO", data)
        })
    },
    noticeCreate ({ commit }, payload) {
      return axios
        .post(`${BASE_URL}/api/v1/notices`, payload)
    }
  },

  modules: {
  },

  getters: {
    // 유저
    getToken (state) {
      return state.token
    },
    getIsLogin (state) {
      return state.isLogin
    },
    getUsername (state) {
      return state.username
    },
    getUserid (state) {
      return state.userid
    },
    getUserage (state) {
      return state.age
    },
    getUsergender (state) {
      return state.gender
    },
    getUserphonenum (state) {
      return state.phonenum
    },
    getUseremail (state) {
      return state.email
    },
    getUsermbti (state) {
      return state.mbti
    },
    getUserguide (state) {
      return state.guide
    },
    getUserconference (state) {
      return state.userConference
    },

    // 프로필
    getProfileid (state) {
      return state.profileId
    },
    getProfilename (state) {
      return state.profileName
    },
    getProfileage (state) {
      return state.profileAge
    },
    getProfilegender (state) {
      return state.profileGender
    },
    getProfilephonenum (state) {
      return state.profilePhonenum
    },
    getProfilembti (state) {
      return state.profileMBTI
    },
    getProfileguide (state) {
      return state.profileGuide
    },
    getProfileimg (state) {
      return state.profileImg
    },
    getProfileemail (state) {
      return state.profileEmail
    },

    // 방
    getConferenceowner (state) {
      return state.conferenceOwner
    },
    getConferenceno (state) {
      return state.conferenceNo
    },
    getConferencetitle (state) {
      return state.conferenceTitle
    },
    getConferencedescription (state) {
      return state.conferenceDescription
    },
    getConferencepassword (state) {
      return state.conferencePassword
    },
    getConferencelimit (state) {
      return state.conferenceLimit
    },
    getConferencecategory (state) {
      return state.conferenceCategory
    },
    getConferenceactive (state) {
      return state.conferenceActive
    },
    getConferencethumbnail (state) {
      return state.conferenceThumbnail
    },

    // 게시글
    getBoarduserid (state) {
      return state.boardUserId
    },
    getBoardtitle (state) {
      return state.boardTitle
    },
    getBoardcontent (state) {
      return state.boardContent
    },
    getBoardcreate (state) {
      return state.boardCreate
    },
    getBoardno (state) {
      return state.boardNo
    },

    // 댓글
    getCommentlist (state) {
      return state.commentList
    },

    // 공지
    getNoticetitle (state) {
      return state.noticeTitle
    },
    getNoticecontent (state) {
      return state.noticeContent
    },
    getNoticecreate (state) {
      return state.noticeCreate
    }
  },
  plugins: [
    createPersistedState()
  ]
})
