<template>
<el-dialog custom-class="Signup" title="회원가입" v-model="state.dialogVisible" @close="handleClose">
  <el-form :model="state.form" :rules="state.rules" ref="signupForm" :label-position="state.form.align">
    <el-form-item prop="userId" label="아이디" :label-width="state.formLabelWidth" >
      <el-input v-model="state.form.userId" autocomplete="off"></el-input>
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
    <el-form-item prop="profileImg" label="프로필 사진" :label-width="state.formLabelWidth" >
      <input @change="fileSelect()" id="profileimg" type="file" accept="image/*">
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
      <el-button type="primary" @click="clickSignup">회원가입</el-button>
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
          { required: true, message: 'Please input ID', trigger: 'blur' }
        ],
        userName: [
          { required: true, message: 'Please input Name', trigger: 'blur' }
        ],
        password: [
          { required: true, message: 'Please input password', trigger: 'blur' }
        ],
        passwordConfirmation: [
          { required: true, message: 'Please input passwordConfirmaton', trigger: 'blur' }
        ],
        age: [
          { required: true, message: 'Please input Age', trigger: 'blur' }
        ],
        gender: [
          { required: true, message: 'Please input Gender', trigger: 'blur' }
        ],
        phoneNum: [
          { required: true, message: 'Please input Phone number', trigger: 'blur' }
        ],
        email: [
          { required: true, message: 'Please input e-mail', trigger: 'blur' }
        ],
        mbti: [
          { required: false, message: 'Please input MBTI', trigger: 'blur' }
        ],
        guide: [
          { required: false, message: 'Please input guide', trigger: 'blur' }
        ],
      },
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px'
    })

    const fileSelect = () => {
      const profileimg = document.getElementById("profileimg")
      console.log(profileimg)
      state.form.profileImg = profileimg.files[0]
    }
    const clickSignup = () => {
      signupForm.value.validate((valid) => {
        if (valid) {
          if (state.form.password === state.form.passwordConfirmation) {
            const formData = new FormData()
            formData.append('userId', state.form.userId)
            formData.append('userName', state.form.userName)
            formData.append('password', state.form.password)
            formData.append('age', state.form.age)
            formData.append('gender', state.form.gender)
            formData.append('phoneNum', state.form.phoneNum)
            formData.append('email', state.form.email)
            formData.append('img', state.form.profileImg)
            formData.append('mbti', state.form.mbti)
            formData.append('guide', state.form.guide)
            formData.append('profileImg', state.form.profileImg)
            console.log(formData)
          //   store.dispatch('signup',
          //     { userId: state.form.userId,
          //       userName: state.form.userName,
          //       password: state.form.password,
          //       age: state.form.age,
          //       gender: state.form.gender,
          //       phoneNum: state.form.phoneNum,
          //       email: state.form.email,
          //       mbti: state.form.mbti,
          //       guide: state.form.guide })
            store.dispatch('profileImg', formData)
            .then((res) => {
              console.log(res)
              ElMessage ({
                message: 'Signup Success !',
                type: 'success'
              })
              emit('closeSignupDialog')
            })
            .catch(function () {
              ElMessage.error('Signup Failed')
            })
          } else {
            ElMessage.error('password error !')
          }
        } else {
          ElMessage.error('Signup Failed !')
        }
      })
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
      emit('closeSignupDialog')
    }

    return { signupForm, state, clickSignup, handleClose, fileSelect }
  },
}
</script>

<style>
.Signup {
  width: 400px !important;
}
</style>