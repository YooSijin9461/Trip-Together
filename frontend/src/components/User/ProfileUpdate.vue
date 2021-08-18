<template>
<el-dialog custom-class="ProfileUpdate" title="정보 수정" v-model="state.dialogVisible" @close="handleClose">
  <el-form :model="state.form" :rules="state.rules" ref="profileUpdateForm" :label-position="state.form.align">
    <el-form-item prop="userId" label="아이디" :label-width="state.formLabelWidth" >
      <el-input v-model="state.form.userId" autocomplete="off" :disabled="true"></el-input>
    </el-form-item>
    <el-form-item prop="userName" label="이름" :label-width="state.formLabelWidth" >
      <el-input v-model="state.form.userName" autocomplete="off" :disabled="true"></el-input>
    </el-form-item>
    <div class="row">
      <div class="col-7">
        <el-form-item prop="gender" label="성별" :label-width="state.formLabelWidth" >
          <el-input v-model="state.form.gender" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
      </div>
      <div class="col-5">
        <el-form-item prop="age" label="나이" :label-width="60" >
          <el-input v-model="state.form.age" autocomplete="off"></el-input>
        </el-form-item>
      </div>
    </div>
    <el-form-item prop="email" label="e-mail" :label-width="state.formLabelWidth" >
      <el-input v-model="state.form.email" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item prop="profileimg" label="프로필 사진" :label-width="state.formLabelWidth" >
      <input @change="fileSelect()" id="profileimg" type="file" accept="image/*" />
    </el-form-item>
    <div class="row">
      <div class="col-7">
        <el-form-item prop="mbti" label="MBTI" :label-width="state.formLabelWidth" >
          <el-select v-model="state.form.mbti" autocomplete="off">
            <el-option label="없음" value=""></el-option>
            <el-option label="ISTJ" value="ISTJ"></el-option>
            <el-option label="ISFJ" value="ISFJ"></el-option>
            <el-option label="INFJ" value="INFJ"></el-option>
            <el-option label="INTJ" value="INTJ"></el-option>
            <el-option label="ISTP" value="ISTP"></el-option>
            <el-option label="ISFP" value="ISFP"></el-option>
            <el-option label="INFP" value="INFP"></el-option>
            <el-option label="INTP" value="INTP"></el-option>
            <el-option label="ESTP" value="ESTP"></el-option>
            <el-option label="ESFP" value="ESFP"></el-option>
            <el-option label="ENFP" value="ENFP"></el-option>
            <el-option label="ENTP" value="ENTP"></el-option>
            <el-option label="ESTJ" value="ESTJ"></el-option>
            <el-option label="ESFJ" value="ESFJ"></el-option>
            <el-option label="ENFJ" value="ENFJ"></el-option>
            <el-option label="ENTJ" value="ENTJ"></el-option>
          </el-select>
        </el-form-item>
      </div>
      <div class="col-5">
        <el-form-item prop="guide" label="가이드" :label-width="60" >
          <el-checkbox v-model="state.form.guide" autocomplete="off">신청</el-checkbox>
        </el-form-item>
      </div>
    </div>
  </el-form>
  <template #footer>
    <span class="dialog-footer">
      <el-button type="success" @click="clickProfileUpdate">수정</el-button>
    </span>
  </template>
</el-dialog>
</template>

<script>
import { reactive, computed, ref, onMounted } from 'vue'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'

export default {
  name: 'ProfileUpdate',
  props: {
    open: {
      type: Boolean,
      default: false,
    }
  },
  setup(props, { emit }) {
    const store = useStore()
    const profileUpdateForm = ref(null)

    const state = reactive({
      form: {
        userId: store.getters['getUserid'],
        userName: store.getters['getUsername'],
        age: store.getters['getUserage'],
        gender: store.getters['getUsergender'],
        email: store.getters['getUseremail'],
        profileImg: store.getters['getUserimg'],
        mbti: store.getters['getUsermbti'],
        guide: store.getters['getUserguide'],
        align: 'left'
      },
      rules: {
        userId: [
          { required: true, message: 'ID를 입력하세요.', trigger: 'blur' }
        ],
        userName: [
          { required: true, message: '이름을 입력하세요.', trigger: 'blur' }
        ],
        age: [
          { required: true, message: '나이를 입력하세요.', trigger: 'blur' }
        ],
        gender: [
          { required: true, message: '성별을 입력하세요.', trigger: 'blur' }
        ],
        email: [
          { required: true, message: 'e-mail을 입력하세요.', trigger: 'blur' }
        ],
        mbti: [
          { required: false, message: 'MBTI를 입력하세요.', trigger: 'blur' }
        ],
        guide: [
          { required: false, message: '가이드 여부를 선택하세요.', trigger: 'blur' }
        ],
      },
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px'
    })
    const fileSelect = () => {
      const profileimg = document.getElementById("profileimg")
      state.form.profileImg = profileimg.files[0]
    }
    const clickProfileUpdate = () => {
      const emailCheck = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      const ageCheck = /^[0-9]+/g
      // 영문자 + 숫자 + 특수문자 조합(8~25자리 입력) 정규식
      if (!ageCheck.test(state.form.age)) {
        ElMessage.error("나이는 숫자만 입력할 수 있습니다.")
      }
      else if (!emailCheck.test(state.form.email)) {
        ElMessage.error("올바른 e-mail 형식이 아닙니다.")
      } else {
        profileUpdateForm.value.validate((valid) => {
          if (valid) {
            const formData = new FormData()
            formData.append('userId', state.form.userId)
            formData.append('userName', state.form.userName)
            formData.append('gender', state.form.gender)
            formData.append('age', state.form.age)
            formData.append('email', state.form.email)
            formData.append('mbti', state.form.mbti)
            formData.append('isGuide', state.form.guide)
            formData.append('file', state.form.profileImg)
            
            const params = new URLSearchParams();
            params.append('userId', state.form.userId)
            params.append('userName', state.form.userName)
            params.append('gender', state.form.gender)
            params.append('age', state.form.age)
            params.append('email', state.form.email)
            if (state.form.mbti) {
              params.append('mbti', state.form.mbti)
            }
            params.append('isGuide', state.form.guide)
            store.dispatch('profileUpdate', { userId: state.form.userId, formData: formData, params: params })
            .then(() => {
              ElMessage ({
                message: '회원정보를 수정하였습니다.',
                type: 'success'
              })
              emit('closeProfileUpdateDialog')
            })
            .catch(function () {
              ElMessage.error('수정에 필요한 정보가 입력되지 않았습니다.')
            })
          } else {
            ElMessage.error('수정에 필요한 정보가 입력되지 않았습니다.')
          }
        })
      }
    }

    const handleClose = () => {
      state.form.userId = store.getters['getUserid'],
      state.form.userName = store.getters['getUsername'],
      state.form.age = store.getters['getUserage'],
      state.form.gender = store.getters['getUsergender'],
      state.form.email = store.getters['getUseremail'],
      state.form.profileImg = store.getters['getUserimg'],
      state.form.mbti = store.getters['getUsermbti'],
      state.form.guide = store.getters['getUserguide'],
      emit('closeProfileUpdateDialog')
    }

    return { profileUpdateForm, state, clickProfileUpdate, handleClose, fileSelect }
  },
}
</script>

<style>
.ProfileUpdate {
  width: 400px !important;
}
.el-form-item__label {
  padding-right: 9px !important;
  padding-left: 9px !important;
}
input[type=file]::file-selector-button {
  height: 40px;
  border: 1px solid #dcdfe6;
  padding: 0 .4em;
  border-radius: .2em;
  background-color: #fff;
}
input[type=file]::file-selector-button:hover {
  cursor: pointer;
  background-color: #e4ffe4;
  border: 2px solid lightgreen;
  font-weight: bold;
}
</style>