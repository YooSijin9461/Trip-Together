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
  </div>
  <div class="pagination">
    <el-pagination
      :page-size="10"
      layout="prev, pager, next"
      :total="state.noticeCount"
      @current-change="pageChange">
    </el-pagination>
  </div>
</template>

<script>
import { reactive, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  setup() {
    const store = useStore()
    const router = useRouter()

    const state = reactive ({
      noticeList: [],
      today: new Date(),
      noticeCount: 0,
    })
    const clickNotice = (noticeNo) => {
      store.dispatch('noticeDetail', noticeNo)
        .then(() => {
          router.push({ name: 'Notice', params: { noticeId: noticeNo }})
        })
    }
    const UTCtoKST = (date) => {
      return new Date(date).getHours() + ':' + new Date(date).getMinutes()
    }
    onMounted (() => {
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
  background-color: #EAEAEA;
  font-weight: bold;
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
</style>