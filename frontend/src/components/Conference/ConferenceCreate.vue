<template>
<el-dialog custom-class="conferenceCreate" title="방 생성하기" v-model="state.dialogVisible" @close="handleClose">
  <el-form :model="state.form" :rules="state.rules" ref="conferenceCreateForm" :label-position="state.form.align">
    <el-form-item prop="owner" label="생성자" :label-width="state.formLabelWidth" >
      <el-input v-model="state.form.owner" autocomplete="off" :disabled="true"></el-input>
    </el-form-item>
    <el-form-item prop="title" label="제목" :label-width="state.formLabelWidth" >
      <el-input v-model="state.form.title" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item prop="description" label="설명" :label-width="state.formLabelWidth">
      <el-input type="textarea" v-model="state.form.description" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item prop="category" label="카테고리" :label-width="state.formLabelWidth">
      <el-radio-group v-model="state.form.category">
        <el-radio label="normal">Normal</el-radio>
        <el-radio label="guide">Guide</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item prop="password" label="비밀번호" :label-width="state.formLabelWidth">
      <el-switch v-model="state.form.onPassword"></el-switch>
      <span v-if="state.form.onPassword">
        <el-input v-model="state.form.password" autocomplete="off" show-password></el-input>
      </span>
    </el-form-item>
    <el-form-item prop="limit" label="제한인원" :label-width="state.formLabelWidth">
      <el-select v-model="state.form.limit" autocomplete="off">
        <el-option label="없음" value=""></el-option>
        <el-option label="2" value="2"></el-option>
        <el-option label="3" value="3"></el-option>
        <el-option label="4" value="4"></el-option>
        <el-option label="5" value="5"></el-option>
        <el-option label="6" value="6"></el-option>
        <el-option label="7" value="7"></el-option>
        <el-option label="8" value="8"></el-option>
        <el-option label="9" value="9"></el-option>
        <el-option label="10" value="10"></el-option>
      </el-select>
    </el-form-item>
  </el-form>
  <template #footer>
    <span class="dialog-footer">
      <el-button type="success" @click="clickConferenceCreate">생성하기</el-button>
    </span>
  </template>
</el-dialog>
</template>

<script>
import { reactive, computed, ref } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

export default {
  name: 'ConferenceCreate',
  props: {
    open: {
      type: Boolean,
      default: false,
    }
  },
  setup(props, { emit }) {
    const store = useStore()
    const router = useRouter()
    const conferenceCreateForm = ref(null)

    const state = reactive({
      form: {
        owner: computed(() => store.getters['getUsername']),
        ownerId: computed(() => store.getters['getUserid']),
        title: '',
        description: '',
        category: '',
        onPassword: false,
        password: '',
        limit: '',
        align: 'left',
      },
      rules: {
        owner: [
          { required: true, message: 'Please input Owner', trigger: 'blur' }
        ],
        title: [
          { required: true, message: 'Please input Title', trigger: 'blur' }
        ],
        description: [
          { required: true, message: 'Please input Description', trigger: 'blur' }
        ],
        category: [
          { required: true, message: 'Please check Category', trigger: 'blur' }
        ],
        password: [
          { required: false, message: 'Please input Password', trigger: 'blur' }
        ],
        limit: [
          { required: false, message: 'Please input Age', trigger: 'blur' }
        ],
      },
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px',
      random: Math.random()*1000
    })

    const clickConferenceCreate = () => {
      conferenceCreateForm.value.validate((valid) => {
        if (valid) {
          store.dispatch('conferenceCreate', { 
            owner: state.form.owner,
            owner_id: state.form.ownerId,
            title: state.form.title,
            description: state.form.description,
            conferenceCategory: state.form.category,
            conferencePassword: state.form.password,
            limitUsersNum: state.form.limit,
            thumbnailUrl: `https://source.unsplash.com/collection/${state.random}`,
            active: true })
          .then(({ data }) => {
            ElMessage ({
              message: 'Conference create Success !',
              type: 'success',
            });
            emit('closeConferenceCreateDialog')
            store.dispatch('conferenceEnter', { conferenceNo: data.conferenceNo, userId: data.ownerId })
            store.dispatch('conferenceDetail', data.conferenceNo)
              .then(() => {
                router.push({name: 'Conference', params: { conferenceId: data.conferenceNo}})
              })
          })
          .catch(function () {
            ElMessage.error('Conference create Failed !')
          })
        } else {
          ElMessage.error('Conference Create Failed !')
        }
      })
    }

    const handleClose = () => {
      state.form.title = ''
      state.form.description = ''
      state.form.category = ''
      state.form.onPassword = false
      state.form.password = ''
      state.form.limit = ''
      emit('closeConferenceCreateDialog')
    }

    return { conferenceCreateForm, state, clickConferenceCreate, handleClose }
  },
}
</script>

<style>
.conferenceCreate {
  width: 400px !important;
}
</style>