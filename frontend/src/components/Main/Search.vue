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
        <hr>
        <div class="d-flex align-items-center" id="article" @click="clickArticle(result.boardNo)">
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
        <hr>
      </div>    
    </div>
    <div v-else-if="state.category === 'room'">
      <div class="container">
        <span v-for="result in state.searchPageResult" :key="result">
          <hr>
          <div class="d-flex align-items-center" @click="clickRoom(result.conferenceNo)">
            <div class="col-9">
              <p class="my-0">{{ result.title }}</p>
            </div>
            <div class="col">
              <p class="my-0"><i class="fas fa-user"></i>{{ result.owner }}</p>
            </div>
          </div>
        </span>
        <hr>
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
      <el-button type="primary" @click="clickSearch">검색</el-button>
    </span>
  </template>
</el-dialog>
</template>

<script>
import { reactive, computed, ref } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

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
      if (state.category === 'room') {
        if (state.roomCategory === 'roomOwner') {
          store.dispatch('conferenceSearch', { searchKey: 'owner', searchValue: state.form.search })
            .then(({ data }) => {
              state.searchResult = data
              state.searchPageResult = data.slice(0, 10)
            })
        } else {
          store.dispatch('conferenceSearch', { searchKey: 'title', searchValue: state.form.search })
            .then(({ data }) => {
              state.searchResult = data
              state.searchPageResult = data.slice(0, 10)
            })
        }
      } else if (state.category === 'article') {
        if (state.articleCategory === 'articleOwner') {
          store.dispatch('articleSearch', { searchKey: 'userId', searchValue: state.form.search })
            .then(({ data }) => {
              state.searchResult = data
              state.searchPageResult = data.slice(0, 10)
            })
        } else if (state.articleCategory === 'articleTitle') {
          store.dispatch('articleSearch', { searchKey: 'boardTitle', searchValue: state.form.search })
            .then(({ data }) => {
              state.searchResult = data
              state.searchPageResult = data.slice(0, 10)
            })
        } else {
          store.dispatch('articleSearch', { searchKey: 'boardContent', searchValue: state.form.search })
            .then(({ data }) => {
              state.searchResult = data
              state.searchPageResult = data.slice(0, 10)
            })
        }
      }
      state.innerVisible = true
      emit('closeSearchDialog')
      emit('openSearchResult')
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
    const clickArticle = (boardNo) => {
      store.dispatch('articleDetail', boardNo)
        .then(() => {
          router.push({ name: 'Article', params: { articleId: boardNo }})
        })
    }

    return { searchForm, state, clickSearch, handleClose, UTCtoKST, pageChange, clickArticle }
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
  width: 80% !important;
}
.search-pagination {
  display: flex;
  margin-top: 1rem;
  justify-content: center;
}
#article:hover {
  cursor: pointer;
  background-color: #EAEAEA;
}
</style>