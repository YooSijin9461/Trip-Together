<template>
<el-dialog custom-class="Search" title="검색" v-model="state.dialogVisible" @close="handleClose">
  <el-form :model="state.form" :rules="state.rules" ref="searchForm" :label-position="state.form.align">
    <div class="d-flex mb-3">
      <el-select type="text" v-model="state.category">
        <el-option label="방" value="room"></el-option>
        <el-option label="게시글" value="article"></el-option>
      </el-select>
      <div v-show="state.category === 'room'">
        <el-select type="text" v-model="state.roomCategory">
          <el-option label="생성자" value="roomOwner"></el-option>
          <el-option label="제목" value="roomTitle"></el-option>
        </el-select>
      </div>
      <div v-show="state.category === 'article'">
        <el-select type="text" v-model="state.articleCategory">
          <el-option label="작성자" value="articleOwner"></el-option>
          <el-option label="제목" value="articleTitle"></el-option>
          <el-option label="내용" value="articleContent"></el-option>
        </el-select>
      </div>
    </div>
    <el-form-item prop="search">
      <el-input v-model="state.form.search" placeholder="검색어를 입력하세요." autocomplete="off"></el-input>
    </el-form-item>
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
import { useStore } from 'vuex'

export default {
  name: 'Login',
  props: {
    open: {
      type: Boolean,
      default: false,
    }
  },
  setup(props, { emit }) {
    const store = useStore()
    const searchForm = ref(null)

    const state = reactive({
      category: '',
      roomCategory: '',
      articleCategory: '',
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
        if (state.roomCategory === 'roomOwner') {
          store.dispatch('conferenceSearch', { searchKey: 'owner', searchValue: state.form.search })
            .then(({ data }) => {
              console.log(data)
            })
        } else {
          store.dispatch('conferenceSearch', { searchKey: 'title', searchValue: state.form.search })
            .then(({ data }) => {
              console.log(data)
            })
        }
      } else if (state.category === 'board') {
        if (state.articleCategory === 'articleOwner') {
          store.dispatch('articleSearch', { searchKey: 'userId', searchValue: state.form.search })
            .then(({ data }) => {
              console.log(data)
            })
        } else if (state.articleCategory === 'articleTitle') {
          store.dispatch('articleSearch', { searchKey: 'boardTitle', searchValue: state.form.search })
            .then(({ data }) => {
              console.log(data)
            })
        } else {
          store.dispatch('articleSearch', { searchKey: 'boardContent', searchValue: state.form.search })
            .then(({ data }) => {
              console.log(data)
            })
        }
      }
      emit('closeSearchDialog')
    }
    const handleClose = () => {
      state.form.search = ''
      emit('closeSearchDialog')
    }

    return { searchForm, state, clickSearch, handleClose }
  },
}
</script>

<style>
.Search {
  width: 400px !important;
}
.el-select {
  width: 100px;
  margin-right: 5px;
}
</style>