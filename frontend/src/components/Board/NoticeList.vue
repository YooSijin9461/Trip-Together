<template>
  <div class="container">
    <h1 class="mt-5 mb-4">공지사항</h1>
    <span v-for="notice in state.noticeList" :key="notice">
      <!-- <span v-if="index"> -->
        <hr class="notice-line my-0">
      <!-- </span> -->
      <div class="notice-box d-flex" @click="clickNotice(notice.noticeNo)">
        <p class="ms-3 mb-0 title col-10">{{ notice.noticeTitle }}</p>
        <div class="col">
          <span v-if="notice.noticeTime.slice(0, 10) === state.today.toJSON().slice(0, 10)">
            <p class="mb-0 date"><i class="far fa-clock date me-2"></i>{{ UTCtoKST(notice.noticeTime) }}</p>
          </span>
          <span v-else>
            <p class="mb-0 date"><i class="far fa-clock date me-2"></i>{{ notice.noticeTime.slice(0, 10) }}</p>
          </span>
        </div>
      </div>
    </span>
    <hr class="notice-line my-0">
    <div class="notice-pagination">
      <el-pagination
        :page-size="10"
        layout="prev, pager, next"
        :total="state.noticeCount"
        @current-change="pageChange">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { reactive, onMounted, computed, onUpdated } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

export default {
  setup() {
    const store = useStore()
    const router = useRouter()

    const state = reactive ({
      noticeList: [],
      today: new Date(),
      noticeCount: 0,
      token: computed(() => store.getters['getToken'])
    })
    const clickNotice = (noticeNo) => {
      if (!state.token) {
        ElMessage.error('로그인이 필요합니다.')
      } else {
        store.dispatch('noticeDetail', noticeNo)
          .then(() => {
            router.push({ name: 'Notice', params: { noticeId: noticeNo }})
          })
      }
    }
    const UTCtoKST = (date) => {
      return new Date(date).getHours() + ':' + ('0' + new Date(date).getMinutes()).slice(-2)
    }
    onMounted (() => {
      store.dispatch('noticePageList')
        .then(({ data }) => {
          state.noticeList = data.content
          state.noticeCount = data.totalElements
        })
    })
    onUpdated (() => {
      store.dispatch('noticePageList')
        .then(({ data }) => {
          state.noticeList = data.content
          state.noticeCount = data.totalElements
        })
    })
    const pageChange = (val) => {
      store.dispatch('noticePageList', val)
        .then(({ data }) => {
          state.noticeList = data.content
          state.noticeCount = data.totalElements
        })
    } 
    return { state, clickNotice, UTCtoKST, pageChange }
  },
}
</script>

<style>
.notice-box {
  padding: 15px 0px;
  align-items: center;
}
.notice-box:hover {
  cursor: pointer;
  background-color: #e4ffe4;
  font-weight: bold;
  color: green;
}
.date {
  font-size: 12px;
}
.notice-line {
  color: gray;
}
.userId {
  font-size: 12px;
}
.notice-pagination {
  display: flex;
  justify-content: center;
  margin-top: 5%;
}
.el-pager li.active, .el-pager li:hover {
  color: green;
}
.el-pagination button:hover {
  font-weight: bold;
  color: green;
}
</style>