<template>
  <div class="d-flex justify-content-center">
    <div class="board-container">
      <el-tabs type="card" @tab-click="handleClick">
        <el-tab-pane label="공지사항">
          <NoticeList/>
        </el-tab-pane>
        <el-tab-pane label="게시글">
          <ArticleList/>
        </el-tab-pane>
      </el-tabs>
    </div>
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
<style>
.board-container {
  width: 80%;
}
.el-tabs--card>.el-tabs__header .el-tabs__item {
  border-left: 1px solid lightgreen;
}
.el-tabs--card>.el-tabs__header .el-tabs__nav {
  border: 1px solid lightgreen;
  border-bottom: none;
}
.el-tabs--card>.el-tabs__header {
  border-bottom: 1px solid lightgreen;
}
.el-tabs__item.is-top.is-active {
  font-weight: bold;
}
</style>