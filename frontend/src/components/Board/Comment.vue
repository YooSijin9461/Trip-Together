<template>
  <div>
    <i class="fas fa-user userId me-2"></i>{{ comment.userId }} :
    {{ comment.comment }}
    <el-button type="warning">수정</el-button>
    <el-button type="danger" @click="deleteComment(commentId)">삭제</el-button>
  </div>
</template>

<script>
import { reactive } from 'vue'
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
    })


    const deleteComment = () => {
      store.dispatch('deleteComment', state.commentId )
        .then(() => {
          ElMessage({
            message: '댓글이 삭제되었습니다.',
            type: 'success'
          })
        })
    }
    return { state, deleteComment }
  },
}
</script>