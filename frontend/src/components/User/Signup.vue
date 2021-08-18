<template>
<el-dialog custom-class="Signup" title="회원가입" v-model="state.dialogVisible" @close="handleClose">
  <el-form :model="state.form" :rules="state.rules" ref="signupForm" :label-position="state.form.align">
    <el-form-item prop="userId" label="아이디" :label-width="state.formLabelWidth" >
      <div class="d-flex">
        <el-input v-model="state.form.userId" autocomplete="off"></el-input>
        <el-button class="ms-2" @click="duplicationCheck(state.form.userId)">중복확인</el-button>
      </div>
    </el-form-item>
    <el-form-item prop="userName" label="이름" :label-width="state.formLabelWidth" >
      <el-input v-model="state.form.userName" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item prop="password" label="비밀번호" :label-width="state.formLabelWidth">
      <el-input v-model="state.form.password" autocomplete="off" show-password></el-input>
    </el-form-item>
    <el-form-item prop="passwordConfirmation" label="비밀번호 확인" :label-width="state.formLabelWidth">
      <el-input v-model="state.form.passwordConfirmation" autocomplete="off" show-password></el-input>
    </el-form-item>
    <div class="row">
      <div class="col">
        <el-form-item prop="age" label="나이" :label-width="state.formLabelWidth" >
          <el-input v-model="state.form.age" autocomplete="off"></el-input>
        </el-form-item>
      </div>
      <div class="col">
        <el-form-item prop="gender" label="성별" :label-width="60" >
          <el-radio-group v-model="state.form.gender">
            <el-radio label="m">남</el-radio>
            <el-radio label="f">여</el-radio>
          </el-radio-group>
        </el-form-item>
      </div>
    </div>
    <el-form-item prop="phoneNum" label="전화번호" :label-width="state.formLabelWidth" >
      <el-input v-model="state.form.phoneNum" autocomplete="off"></el-input>
    </el-form-item>
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
      <el-button type="success" @click="clickSignup">회원가입</el-button>
    </span>
  </template>
</el-dialog>
</template>

<script>
import { reactive, computed, ref } from 'vue'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'

export default {
  name: 'Signup',
  props: {
    open: {
      type: Boolean,
      default: false,
    }
  },
  setup(props, { emit }) {
    const store = useStore()
    const signupForm = ref(null)

    const state = reactive({
      form: {
        userId: '',
        userName: '',
        password: '',
        passwordConfirmation: '',
        age: '',
        gender: '',
        phoneNum: '',
        email: '',
        profileImg: '',
        mbti: '',
        guide: false,
        align: 'left'
      },
      rules: {
        userId: [
          { required: true, message: 'ID를 입력하세요.', trigger: 'blur' }
        ],
        userName: [
          { required: true, message: '이름을 입력하세요.', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '비밀번호를 입력하세요.', trigger: 'blur' }
        ],
        passwordConfirmation: [
          { required: true, message: '비밀번호를 한번 더 입력하세요.', trigger: 'blur' }
        ],
        age: [
          { required: true, message: '나이를 입력하세요.', trigger: 'blur' }
        ],
        gender: [
          { required: true, message: '성별을 선택하세요.', trigger: 'blur' }
        ],
        phoneNum: [
          { required: true, message: '핸드폰 번호를 입력하세요.', trigger: 'blur' }
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
      formLabelWidth: '120px',
      duplication: '',
    })

    const duplicationCheck = (userId) => {
      state.duplication = false
      const params = new URLSearchParams();
      params.append('userId', userId)

      store.dispatch('userList', params)
        .then(({ data }) => {
          for (var i = 0; i < data.length; i++) {
            if (userId === data[i].userId) {
              ElMessage.error('중복된 ID가 존재합니다.')
              state.duplication = true
              break;
            }
          }
        })
    }
    const fileSelect = () => {
      const profileimg = document.getElementById("profileimg")
      state.form.profileImg = profileimg.files[0]
    }
    const clickSignup = () => {
      const idCheck = /^[a-zA-z0-9]{4,12}$/     
      const passwordCheck = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/
      const phoneCheck = /^[0-9]+/g
      const emailCheck = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      const ageCheck = /^[0-9]+/g
      if (!idCheck.test(state.form.userId)) {
        ElMessage.error("ID는 영문자 또는 숫자로 4~12자리로 입력해야 합니다.")
      }
      else if (state.duplication === '') {
        ElMessage.error("ID 중복체크는 필수입니다.")
      }
      else if (state.duplication === 'true') {
        ElMessage.error('중복된 ID가 존재합니다.')
      }
      // 영문자 + 숫자 + 특수문자 조합(8~25자리 입력) 정규식
      else if (!passwordCheck.test(state.form.password)) {
        ElMessage.error("비밀번호는 영문자 + 숫자 + 특수문자 조합으로 8~25자리로 입력해야 합니다.")
      } 
      else if (state.form.password !== state.form.passwordConfirmation) {
        ElMessage.error("비밀번호가 일치하지 않습니다.")
      }
      else if (!ageCheck.test(state.form.age)) {
        ElMessage.error("나이는 숫자만 입력할 수 있습니다.")
      }
      else if (!phoneCheck.test(state.form.phoneNum)) {
        ElMessage.error("전화번호는 숫자만 입력할 수 있습니다.")
      }
      else if (!emailCheck.test(state.form.email)) {
        ElMessage.error("올바른 e-mail 형식이 아닙니다.")
      } else {
        signupForm.value.validate((valid) => {
          if (valid) {
            const formData = new FormData()
            formData.append('userId', state.form.userId)
            formData.append('userName', state.form.userName)
            formData.append('password', state.form.password)
            formData.append('age', state.form.age)
            formData.append('gender', state.form.gender)
            formData.append('phoneNum', state.form.phoneNum)
            formData.append('email', state.form.email)
            formData.append('mbti', state.form.mbti)
            formData.append('isGuide', state.form.guide)
            formData.append('file', state.form.profileImg)
                          
            store.dispatch('signup', formData)
            .then(() => {
              ElMessage ({
                message: '회원가입에 성공하였습니다.',
                type: 'success'
              })
              emit('closeSignupDialog')
            })
            .catch(function () {
              ElMessage.error('회원가입에 실패하였습니다.')
            })
          } else {
            ElMessage.error('회원가입에 필요한 정보가 입력되지 않았습니다.')
          }
        })
      }
    }

    const handleClose = () => {
      state.form.userId = ''
      state.form.userName = ''
      state.form.password = ''
      state.form.passwordConfirmation = ''
      state.form.age = ''
      state.form.gender = ''
      state.form.phoneNum = ''
      state.form.email = ''
      state.form.mbti = ''
      state.form.guide = false
      state.form.profileImg = ''
      emit('closeSignupDialog')
    }

    return { signupForm, state, clickSignup, handleClose, duplicationCheck, fileSelect }
  },
}
</script>

<style>
.Signup {
  width: 400px !important;
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