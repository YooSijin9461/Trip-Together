<template>
  <div class="comment-container">
    <div class="d-flex align-items-center justify-content-between">
      <div v-if="comment.userImg" class="d-flex align-items-center">
        <img class="comment-profile" :src="'/upload/' + comment.userImg" alt="">
        <span class="comment-id" @click="clickId(comment.userId)">{{ comment.userId }}</span>
      </div>
      <div v-else>
        <div v-if="comment.userGender === 'm'" class="d-flex align-items-center">
          <img class="comment-profile" :src="state.male" alt="">
          <span class="comment-id" @click="clickId(comment.userId)">{{ comment.userId }}</span>
        </div>
        <div v-else class="d-flex align-items-center">
          <img class="comment-profile" :src="state.female" alt="">
          <span class="comment-id" @click="clickId(comment.userId)">{{ comment.userId }}</span>
        </div>
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
import { useRouter } from 'vue-router'
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
    const router = useRouter()
    const state = reactive({
      loginId: computed (() => store.getters['getUserid']),
      dialogVisible: false,
      comment: '',
      male: require('@/assets/male.png'),
      female: require('@/assets/female.png'),
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
    const clickId = (userId) => {
      store.dispatch('profile', userId)
        .then(() => {
          router.push({ name: 'Profile', params: { userId: userId }})  
        })
    }
    return { state, deleteComment, commentDetail, updateComment, clickId }
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
.comment-profile {
  width: 15px;
  height: 15px;
  border-radius: 100%;
  margin-right: 5px;
}
.comment-id {
  font-size: 13px;
}
.comment-id:hover {
  color: green;
  font-weight: bold;
  cursor: pointer;
}
</style>