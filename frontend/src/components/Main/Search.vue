<template>
<el-dialog custom-class="Search" title="검색" v-model="state.dialogVisible" @close="handleClose">
  <el-form :model="state.form" :rules="state.rules" ref="searchForm" :label-position="state.form.align">
    <div class="d-flex">
      <el-select type="text" v-model="state.category">
        <el-option label="방 생성자" value="roomOwner"></el-option>
        <el-option label="방 제목" value="roomTitle"></el-option>
        <el-option label="게시글" value="articlename"></el-option>
      </el-select>
      <el-form-item class="flex-fill" prop="search">
        <el-input v-model="state.form.search" placeholder="검색어를 입력하세요." autocomplete="off"></el-input>
      </el-form-item>
    </div>
  </el-form>
  <template #footer>
    <span class="dialog-footer">
      <el-button type="primary" @click="clickSearch">검색</el-button>
    </span>
  </template>
</el-dialog>
</template>

<script>
import { reactive, computed, ref } from 'vue'

export default {
  name: 'Login',
  props: {
    open: {
      type: Boolean,
      default: false,
    }
  },
  setup(props, { emit }) {
    const searchForm = ref(null)

    const state = reactive({
      category: '',
      form: {
        search: '',
        align: 'left'
      },
      rules: {
        search: [
          { required: true, message: '검색어를 입력하세요.', trigger: 'blur' }
        ],
      },
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px',
    })

    const clickSearch = () => {
      if (state.category === 'room') {
        
      }
    }
    const handleClose = () => {
      state.form.search = ''
      emit('closeSearchDialog')
    }

    return { searchForm, state, handleClose }
  },
}
</script>

<style>
.Search {
  width: 400px !important;
}
.el-select {
  width: 25%;
  margin-right: 5px;
}
</style>