<template>
  <div class="comment-container">
    <div class="d-flex align-items-center justify-content-between">
      <div>
        <i class="fas fa-user userId me-2"></i>{{ comment.userId }}
      </div>
      <div v-if="comment.userId===state.loginId">
        <i class="el-icon-edit update-icon mx-1" @click="[commentDetail(comment.commentNo), state.dialogVisible=true]"></i>
        <i class="el-icon-delete delete-icon mx-1" @click="deleteComment(comment.commentNo)"></i>
      </div>
    </div>
    {{ comment.comment }}
  </div>
  <hr class="my-1">
  <el-dialog
    custom-class="comment-update"
    title="댓글 수정"
    v-model="state.dialogVisible">
        <el-input
          placeholder="내용을 입력해주세요."
          v-model="state.comment">
        </el-input>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="updateComment(comment.commentNo)">수정</el-button>
        <el-button @click="state.dialogVisible = false">취소</el-button>
      </span>
    </template>
  </el-dialog>
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
      dialogVisible: false,
      comment: '',
    })

    const deleteComment = (commentNo) => {
      store.dispatch('deleteComment', commentNo )
        .then(() => {
          ElMessage({
            message: '댓글이 삭제되었습니다.',
            type: 'success'
          })
        })
    }
    const commentDetail = (commentNo) => {
      store.dispatch('commentDetail', commentNo)
        .then(({ data }) => {
          state.comment = data.comment
        })
    }
    const updateComment = (commentNo) => {
      const updateData = {
        comment: state.comment
      }
      store.dispatch('updateComment', { commentNo: commentNo, data: updateData })
        .then(() => {
          ElMessage({
            message: '댓글이 수정되었습니다.',
            type: 'success'
          })
          state.dialogVisible = false
        })
    }
    return { state, deleteComment, commentDetail, updateComment }
  },
}
</script>

<style>
.comment-container {
  margin: 0 1%;
}
.comment-button {
  border-radius: 100%;
  padding: 6px;
  min-height: 0;
}
.update-icon:hover {
  color: blue;
  cursor: pointer;
  font-weight: bold;
  font-size: 17px;
}
.delete-icon:hover {
  color: red;
  cursor: pointer;
  font-weight: bold;
  font-size: 17px;
}
.comment-update {
  min-width: 400px;
}
</style>