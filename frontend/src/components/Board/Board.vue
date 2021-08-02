<template>
  <div class="container">
    <el-tabs type="card" @tab-click="handleClick">
      <el-tab-pane label="공지사항">
        <NoticeList/>
      </el-tab-pane>
      <el-tab-pane label="게시글">
        <ArticleList/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import NoticeList from '@/components/Board/NoticeList.vue'
import ArticleList from '@/components/Board/ArticleList.vue'

export default {
  name: 'Board',
  components: {
    NoticeList,
    ArticleList
  },
  setup() {
    
  },
}
</script>