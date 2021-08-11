<template>
<el-dialog custom-class="Search" title="검색" v-model="state.dialogVisible" @close="handleClose">
  <template #default>
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
  <el-dialog
    custom-class="innerDialog"
    title="검색 결과"
    v-model="state.innerVisible"
    append-to-body>
    <div v-if="state.category === 'article'">
      <div class="container">
        <span v-for="result in state.searchPageResult" :key="result">
          <hr class="my-0">
          <div class="d-flex align-items-center py-3" id="searchResult" @click="clickArticle(result.boardNo)">
            <p class="col-8 my-0">{{ result.boardTitle }}</p>
            <div class="col">
              <p class="my-0"><i class="fas fa-user"></i>{{ result.userId }}</p>
            </div>
            <div class="col">
              <span v-if="result.boardTime">
                <span v-if="result.boardTime.slice(0, 10) === state.today.toJSON().slice(0, 10)">
                  <p class="my-0"><i class="far fa-clock"></i>{{ UTCtoKST(result.boardTime) }}</p>
                </span>
                <span v-else>
                  <p class="my-0"><i class="far fa-clock"></i>{{ result.boardTime.slice(0, 10) }}</p>
                </span>
              </span>
              <span v-else>
                <p class="my-0"><i class="far fa-clock"></i>{{ state.today.toJSON().slice(0,10) }}</p>
              </span>
            </div>
          </div>
        </span>
        <hr class="mt-0">
      </div>    
    </div>
    <div v-else-if="state.category === 'room'">
      <div class="container">
        <span v-for="result in state.searchPageResult" :key="result">
          <hr class="my-0">
          <div class="d-flex align-items-center py-3" id="searchResult" @click="clickRoom(result.conferenceNo)">
            <div class="col-9">
              <p class="my-0">{{ result.title }}</p>
            </div>
            <div class="col">
              <p class="my-0"><i class="fas fa-user"></i>{{ result.owner }}</p>
            </div>
          </div>
        </span>
        <hr class="mt-0">
      </div>
    </div>
    <div class="search-pagination">
      <el-pagination
        :page-size="10"
        layout="prev, pager, next"
        :total="state.searchResult.length"
        @current-change="pageChange">
      </el-pagination>
    </div>
  </el-dialog>
  </template>
  <template #footer>
    <span class="dialog-footer">
      <el-button type="success" @click="clickSearch">검색</el-button>
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
  name: 'Login',
  components: {
  },
  props: {
    open: {
      type: Boolean,
      default: false,
    }
  },
  setup(props, { emit }) {
    const store = useStore()
    const router = useRouter()
    const searchForm = ref(null)

    const state = reactive({
      token: computed(() => store.getters['getToken']),
      category: '',
      roomCategory: '',
      articleCategory: '',
      today: new Date(),
      innerVisible: ref(false),
      searchResult: [],
      searchPageResult: [],
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
      if (state.form.search) {
        if (state.category === 'room') {
          if (state.roomCategory === 'roomOwner') {
            store.dispatch('conferenceSearch', { searchKey: 'owner', searchValue: state.form.search })
              .then(({ data }) => {
                state.searchResult = data
                state.searchPageResult = data.slice(0, 10)
              })
            state.innerVisible = true
            emit('closeSearchDialog')
          } 
          else if (state.roomCategory === 'roomTitle') {
            store.dispatch('conferenceSearch', { searchKey: 'title', searchValue: state.form.search })
              .then(({ data }) => {
                state.searchResult = data
                state.searchPageResult = data.slice(0, 10)
              })
            state.innerVisible = true
            emit('closeSearchDialog')
          } else {
            ElMessage.error('검색 조건을 선택하세요.')
          }
        } else if (state.category === 'article') {
          if (state.articleCategory === 'articleOwner') {
            store.dispatch('articleSearch', { searchKey: 'userId', searchValue: state.form.search })
              .then(({ data }) => {
                state.searchResult = data
                state.searchPageResult = data.slice(0, 10)
              })
            state.innerVisible = true
            emit('closeSearchDialog')
          } 
          else if (state.articleCategory === 'articleTitle') {
            store.dispatch('articleSearch', { searchKey: 'boardTitle', searchValue: state.form.search })
              .then(({ data }) => {
                state.searchResult = data
                state.searchPageResult = data.slice(0, 10)
              })
            state.innerVisible = true
            emit('closeSearchDialog')
          } 
          else if (state.articleCategory === 'articleContent') {
            store.dispatch('articleSearch', { searchKey: 'boardContent', searchValue: state.form.search })
              .then(({ data }) => {
                state.searchResult = data
                state.searchPageResult = data.slice(0, 10)
              })
            state.innerVisible = true
            emit('closeSearchDialog')
          } else {
            ElMessage.error('검색 조건을 선택하세요.')
          }
        }
        
      } else {
        ElMessage.error('검색어를 입력하세요.')
      }
    }
    const pageChange = (val) => {
      const start = (val - 1) * 10
      state.searchPageResult = state.searchResult.slice(start, start + 10) 
    }
    const UTCtoKST = (date) => {
      return new Date(date).getHours() + ':' + new Date(date).getMinutes()
    }
    const handleClose = () => {
      state.form.search = ''
      emit('closeSearchDialog')
    }
    const clickRoom = (roomNo) => {
      if (!state.token) {
        ElMessage.error('로그인이 필요합니다.')
      } else {
        store.dispatch('conferenceDetail', roomNo)
          .then(() => {
            emit('openConferenceDialog')
            state.innerVisible = false
          })
      }
    }
    const clickArticle = (boardNo) => {
      if (!state.token) {
        ElMessage.error('로그인이 필요합니다.')
      } else {
        store.dispatch('articleDetail', boardNo)
          .then(() => {
            router.push({ name: 'Article', params: { articleId: boardNo }})
            state.innerVisible = false
          })
      }
    }

    return { searchForm, state, clickSearch, handleClose, UTCtoKST, pageChange, clickRoom, clickArticle }
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
.innerDialog {
  width: 80%;
  max-width: 720px;
}
.search-pagination {
  display: flex;
  margin-top: 5%;
  justify-content: center;
}
#searchResult:hover {
  cursor: pointer;
  background-color: #EAEAEA;
}
.el-select .el-input.is-focus .el-input__inner {
  border-color: lightgreen !important;
}
.el-select__popper.el-popper[role=tooltip] {
  border: 1px solid lightgreen !important;
}
.el-select__popper.el-popper[role=tooltip] .el-popper__arrow::before {
  border-top: 1px solid lightgreen !important;
  border-left: 1px solid lightgreen !important;
}
.el-select .el-input__inner:focus {
  border-color: lightgreen !important;
}
.el-select-dropdown__item.selected {
  color: green !important;
}
.el-select-dropdown__item.hover, .el-select-dropdown__item:hover {
  background-color: #e4ffe4 !important;
}
input.el-input__inner:focus {
  border: 1px solid lightgreen;
}
#searchResult:hover {
  cursor: pointer;
  background-color: #e4ffe4;
  font-weight: bold;
  color: green;
}
.el-pager li.active, .el-pager li:hover {
  color: green !important;
}
.el-pagination button:hover {
  font-weight: bold;
  color: green !important;
}
.el-tabs__item.is-active, .el-tabs__item:hover {
    color: green !important;
}
</style>