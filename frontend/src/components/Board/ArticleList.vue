<template>
  <div class="container">
    <h1 class="mt-5 mb-4">게시글</h1>
    <span v-for="article in state.articleList" :key="article">
      <!-- <span v-if="index"> -->
        <hr class="article-line my-0">
      <!-- </span> -->
      <div class="article-box d-flex" @click="clickArticle(article.boardNo)">
        <p class="ms-3 mb-0 title col-8">{{ article.boardTitle }}</p>
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
      const hour = new Date(date).getHours()
      const minute = new Date(date).getMinutes()
      var newHour = ''
      var newMinute = ''
      console.log(hour, minute)
      if (hour < 10) {
        newHour = '0' + hour
      } else {
        newHour = hour
      }
      if (minute < 10) {
        newMinute = '0' + minute
      } else {
        newMinute = minute
      }
      return newHour + ':' + newMinute
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
</style>