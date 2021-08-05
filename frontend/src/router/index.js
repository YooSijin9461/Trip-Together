import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('@/components/Main/Home.vue')
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('@/components/User/Profile.vue')
  },

  // 방
  {
    path: '/conferences',
    name: 'ConferenceList',
    component: () => import('@/components/Conference/ConferenceList.vue')
  },
  {
    path: '/conferences/all',
    name: 'ConferenceAll',
    component: () => import('@/components/Conference/ConferenceAll.vue'),
    props: true
  },
  {
    path: '/conferences/normal',
    name: 'ConferenceNormal',
    component: () => import('@/components/Conference/ConferenceNormal.vue'),
    props: true
  },
  {
    path: '/conferences/guide',
    name: 'ConferenceGuide',
    component: () => import('@/components/Conference/ConferenceGuide.vue'),
    props: true
  },
  {
    path: '/conferences/:conferenceId',
    name: 'Conference',
    component: () => import('@/components/Conference/Conference.vue')
  },
  
  //게시판
  {
    path: '/board',
    name: 'Board',
    component: () => import('@/components/Board/Board.vue')
  },
  {
    path: '/articles',
    name: 'ArticleList',
    component: () => import('@/components/Board/ArticleList.vue')
  },
  {
    path: '/articles/:articleId',
    name: 'Article',
    component: () => import('@/components/Board/Article.vue')
  },
  {
    path: '/articles/create',
    name: 'ArticleCreate',
    component: () => import('@/components/Board/ArticleCreate.vue')
  },
  {
    path: '/notices',
    name: 'NoticeList',
    component: () => import('@/components/Board/NoticeList.vue')
  },
  {
    path: '/notices/:noticeId',
    name: 'Notice',
    component: () => import('@/components/Board/Notice.vue')
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
