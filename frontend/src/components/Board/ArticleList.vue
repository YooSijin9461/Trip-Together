<template>
  <div class="container">
    <h1 class="mt-5 mb-4">게시글</h1>
    <span v-for="article in state.articleList" :key="article">
      <!-- <span v-if="index"> -->
        <hr class="article-line my-0">
      <!-- </span> -->
      <div class="article-box d-flex" @click="clickArticle(article.boardNo)">
        <div class="col-8 d-flex align-items-center">
          <p class="ms-3 mb-0 me-3">{{ article.boardTitle }}</p>
          <div class="likeCount me-2">
            <i class="fas fa-thumbs-up"></i> {{ article.likeCount}}
          </div>
          <div class="hateCount">
            <i class="fas fa-thumbs-down"></i> {{ article.hateCount }}
          </div>
        </div>
        <div class="col d-flex">
          <p class="mb-0 userId"><i class="fas fa-user userId me-2"></i>{{ article.userId }}</p>
        </div>
        <div class="col">
          <span v-if="article.boardTime.slice(0, 10) === state.today.toJSON().slice(0, 10)">
            <p class="mb-0 date"><i class="far fa-clock date me-2"></i>{{ UTCtoKST(article.boardTime) }}</p>
          </span>
          <span v-else>
            <p class="mb-0 date"><i class="far fa-clock date me-2"></i>{{ article.boardTime.slice(0, 10) }}</p>
          </span>
        </div>
      </div>
    </span>
    <hr class="article-line my-0">
    <div class="mt-4 d-flex justify-content-end" v-if="state.token">
      <el-button type="success" @click="articleCreate">글쓰기</el-button>
    </div>
    <div class="article-pagination">
      <el-pagination
        :page-size="15"
        layout="prev, pager, next"
        :total="state.articleCount"
        @current-change="pageChange">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { reactive, onMounted, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

export default {
  setup() {
    const store = useStore()
    const router = useRouter()

    const state = reactive ({
      articleList: [],
      today: new Date(),
      articleCount: 0,
      token: computed(() => store.getters['getToken'])
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
      return ('0' + new Date(date).getHours()).slice(-2) + ':' + ('0' + new Date(date).getMinutes()).slice(-2)
    }
    const articleCreate = () => {
        router.push({ name: 'ArticleCreate'})
    }
    onMounted (() => {
      store.dispatch('articlePageList')
        .then(({ data }) => {
          state.articleList = data.content
          state.articleCount = data.totalElements
        })
    })

    const pageChange = (val) => {
      store.dispatch('articlePageList', val)
        .then(({ data }) => {
          state.articleList = data.content
          state.articleCount = data.totalElements
        })
    } 
    return { state, clickArticle, UTCtoKST, articleCreate, pageChange }
  },
}
</script>

<style>
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
.article-pagination {
  display: flex;
  justify-content: center;
  margin-top: 5%;
}
.article-box:hover {
  cursor: pointer;
  background-color: #e4ffe4;
  font-weight: bold;
  color: green;
}
.el-pager li.active, .el-pager li:hover {
  color: green;
}
.el-pagination button:hover {
  font-weight: bold;
  color: green;
}
.el-tabs__item.is-active, .el-tabs__item:hover {
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