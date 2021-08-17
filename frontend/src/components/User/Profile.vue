<template>
  <div class="container mt-4">
    <div class="row d-flex align-items-center">
      <!-- 이미지 -->
      <div class="col-3">
        <img class="profilepage-img" :src="'/api/v1/users/' + state.userProfileimg"  alt="">
      </div>
      <div class="col">
        <div class="d-flex">
          <div class="row">
            <p class="my-3">ID : {{ state.userId }}</p>
            <p class="my-3">이름 : {{ state.userName }}</p>
            <p class="my-3">e-mail : {{ state.userEmail }}</p>
          </div>
          <div class="row">
            <p class="my-3">나이 : {{ state.userAge }}</p>
            <div v-if="state.userGender === 'm'">
              <p class="my-3">성별 : 남자</p>
            </div>
            <div v-else-if="state.userGender === 'f'">
              <p class="my-3">성별 : 여자</p>
            </div>
            <div v-if="state.userMBTI">
              <p class="my-3">MBTI : {{ state.userMBTI }}</p>
            </div>
            <div v-else>
              <p class="my-3">MBTI : 정보가 없습니다.</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <nav class="mt-5">
    <div class="nav nav-tabs justify-content-center" id="nav-tab" role="tablist">
      <button 
        class="nav-link active" 
        id="nav-article-tab" 
        data-bs-toggle="tab" 
        data-bs-target="#nav-article" 
        type="button" 
        role="tab" 
        aria-controls="nav-article" 
        aria-selected="true">
        <i class="far fa-clipboard me-2"></i>게시글
      </button> 
      <button 
        class="nav-link" 
        id="nav-comment-tab" 
        data-bs-toggle="tab" 
        data-bs-target="#nav-comment" 
        type="button" 
        role="tab" 
        aria-controls="nav-comment" 
        aria-selected="false">
        <i class="far fa-comment me-2"></i>댓글
      </button>
    </div>
  </nav>
  <div class="tab-content" id="nav-tabContent">
    <div class="tab-pane fade show active" id="nav-article" role="tabpanel" aria-labelledby="nav-article-tab">
      <div class="mt-3 container" v-if="!state.userArticle.length">
        <h3 class="text-center my-5">작성한 게시글이 없습니다.</h3>
      </div>
      <div class="mt-3 container" v-else>
        <h5 class="text-center my-5">{{ state.userArticle.length }}개의 게시글이 있습니다.</h5>
        <div v-for="article in state.userArticle.slice().reverse()" :key="article">
          <hr class="article-line my-0">
          <div class="article-box d-flex" @click="clickArticle(article.boardNo)">
            <div class="col-8 d-flex align-items-center">
              <p class="ms-3 mb-0 me-3">{{ article.boardTitle }}</p>
              <div class="likeCount me-2">
                <i class="fas fa-thumbs-up"></i> {{ article.likeCount }}
              </div>
              <div class="hateCount">
                <i class="fas fa-thumbs-down"></i> {{ article.hateCount }}
              </div>
            </div>
            <div class="col d-flex">
              <p class="mb-0 userId"><i class="fas fa-user userId me-2"></i>{{ article.userId }}</p>
            </div>
            <div class="col">
              <span v-if="article.boardTime">
                <span v-if="article.boardTime.slice(0, 10) === state.today.toJSON().slice(0, 10)">
                  <p class="mb-0 date"><i class="far fa-clock date me-2"></i>{{ UTCtoKST(article.boardTime) }}</p>
                </span>
                <span v-else>
                  <p class="mb-0 date"><i class="far fa-clock date me-2"></i>{{ article.boardTime.slice(0, 10) }}</p>
                </span>
              </span>
              <span v-else>
                <p class="mb-0 date"><i class="far fa-clock date me-2"></i>{{ state.today.toJSON().slice(0,10) }}</p>
              </span>
            </div>
          </div>
        </div>
        <hr class="article-line my-0">
      </div>
    </div>
    <div class="tab-pane fade" id="nav-comment" role="tabpanel" aria-labelledby="nav-comment-tab">
      <div class="mt-3 container" v-if="!state.userComment.length">
        <h3 class="text-center my-5">작성한 댓글이 없습니다.</h3>
      </div>
      <div class="mt-3 container" v-else>
        <h5 class="text-center my-5">{{ state.userComment.length }}개의 댓글 있습니다.</h5>
        <div v-for="comment in state.userComment.slice().reverse()" :key="comment">
          <hr class="comment-line my-0">
          <div class="comment-box d-flex" @click="clickArticle(comment.boardNo)">
            <p class="ms-5 mb-0 title col-8">{{ comment.comment }}</p>
            <div class="col">
              <span v-if="comment.commentTime.slice(0, 10) === state.today.toJSON().slice(0, 10)">
                <p class="mb-0 date"><i class="far fa-clock date me-2"></i>{{ UTCtoKST(comment.commentTime) }}</p>
              </span>
              <span v-else>
                <p class="mb-0 date"><i class="far fa-clock date me-2"></i>{{ comment.commentTime.slice(0, 10) }}</p>
              </span>
            </div>
          </div>
        </div>
        <hr class="comment-line my-0">
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, computed, onMounted, ref } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

export default {
  name: 'Profile',
  components: {
  },
  setup() {
    const store = useStore()
    const router = useRouter()

    const state = reactive({
      userName: computed(() => store.getters['getProfilename']),
      userId: computed(() => store.getters['getProfileid']),
      userEmail: computed(() => store.getters['getProfileemail']),
      userMBTI: computed(() => store.getters['getProfilembti']),
      userAge: computed(() => store.getters['getProfileage']),
      userGender: computed(() => store.getters['getProfilegender']),
      userProfileimg: computed(() => store.getters['getProfileimg']),
      userArticle: [],
      userComment: [],
      token: computed(() => store.getters['getToken']),
      today: new Date(),
    })
    const clickArticle = (boardNo) => {
      if (!state.token) {
        ElMessage.error('로그인이 필요합니다.')
      } else {
        store.dispatch('articleDetail', boardNo)
          .then(() => {
            store.dispatch('commentList', boardNo)
            router.push({ name: 'Article', params: { articleId: boardNo }})
          })
      }
    }
    const UTCtoKST = (date) => {
      return new Date(date).getHours() + ':' + ('0' + new Date(date).getMinutes()).slice(-2)
    }
    onMounted (() => {
      store.dispatch('userArticle', state.userId)
        .then(({ data }) => {
          state.userArticle = data
        })
      store.dispatch('userComment', state.userId)
        .then(({ data }) => {
          state.userComment = data
        })
    })
    // onUpdated (() => {
    //   store.dispatch('userArticle', state.userId)
    //     .then(({ data }) => {
    //       state.userArticle = data
    //     })
    //   store.dispatch('userComment', state.userId)
    //     .then(({ data }) => {
    //       state.userComment = data
    //     })
    // })

    return { state, clickArticle, UTCtoKST }
  },
}
</script>

<style>
button[aria-selected="false"] {
  color: lightslategray;
}
.profilepage-img {
  border: 1px solid lightgreen;
  width: 150px;
  height: 150px;
  border-radius: 100%;
}
.nav-tabs .nav-item.show .nav-link, .nav-tabs .nav-link.active {
  font-weight: bold;
  color: lightgreen;
}
.nav-link:hover {
  font-weight: bold;
  color: lightgreen;
}
.nav-tabs {
  border-bottom: 1px solid lightgreen;
}
.nav-tabs .nav-item.show .nav-link, .nav-tabs .nav-link.active {
    border-color: lightgreen lightgreen #fff;
}
.article-box {
  padding: 15px 0px;
  align-items: center;
}
.date {
  font-size: 12px;
}
.article-line {
  color: gray;
}
.userId {
  font-size: 12px;
}
.article-box:hover {
  cursor: pointer;
  background-color: #e4ffe4;
  font-weight: bold;
  color: green;
}
.comment-box {
  padding: 15px 0px;
  align-items: center;
}
.comment-line {
  color: gray;
}
.comment-box:hover {
  cursor: pointer;
  background-color: #e4ffe4;
  font-weight: bold;
  color: green;
}
.likeCount {
  font-size: 12px;
  color: #409eff;
}
.hateCount {
  font-size: 12px;
  color: #f56c6c;
}
</style>