<template>
  <div class="d-flex justify-content-between">
    <div class="align-items-center">
      <i class="fas fa-user userId me-2"></i>{{ comment.userId }} :
      {{ comment.comment }}
    </div>
    <div v-if="comment.userId===state.loginId">
      <el-button type="warning" icon="el-icon-edit" circle @click="updateComment(comment.commentNo)"></el-button>
      <el-button type="danger"  icon="el-icon-delete" circle @click="deleteComment(comment.commentNo)"></el-button>
    </div>
  </div>
  <hr>
</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'

export default {
  name: 'Comment',
  props: {
    comment: {
      type: Object,
    }
  },
  setup() {
    const store = useStore()
    const state = reactive({
      loginId: computed (() => store.getters['getUserid']),
    })


    const deleteComment = (commentId) => {
      store.dispatch('deleteComment', commentId )
        .then(() => {
          ElMessage({
            message: '댓글이 삭제되었습니다.',
            type: 'success'
          })
        })
    }
    const updateComment = (commentId) => {
      store.dispatch('updateComment', commentId )
        .then(() => {
          ElMessage({
            message: '댓글이 수정되었습니다.',
            type: 'success'
          })
        })
    }
    return { state, deleteComment, updateComment }
  },
}
</script>