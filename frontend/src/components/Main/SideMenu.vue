<template>
  <i class="fas fa-bars menu aside-icon" @click="state.drawer = true"></i>
  <el-drawer
    v-model="state.drawer"
    :direction="state.direction"
    :with-header="false">
    <div class="d-flex align-items-center my-3 ms-4 ps-2">
      <i class="fas fa-bars menu aside-icon" @click="state.drawer = false"></i>
      <div class="flex-fill align-center">
        <img class="side-logo" src="../../assets/logo.png">
      </div>      
    </div>
    <hr class="mt-2">
    <div class="box d-flex align-items-center my-2" @click="clickHome">
      <i class="fas fa-home aside-icon my-2"></i>
      <span class="flex-fill align-center">홈</span>
    </div>
    <div class="box d-flex align-items-center my-2" @click="clickConference">
      <i class="fas fa-video aside-icon my-2"></i>
      <span class="flex-fill align-center">방</span>
    </div>
    <div class="box d-flex align-items-center my-2" @click="clickBoard">
      <i class="far fa-clipboard aside-icon2 my-2"></i>
      <span class="flex-fill align-center">게시판</span>
    </div>
    <div class="box d-flex align-items-center my-2" @click="clickSearch">
      <i class="fas fa-search aside-icon my-2"></i>
      <span class="flex-fill align-center">검색</span>
    </div>
    <!-- <Side/> -->
  </el-drawer>
</template>

<script>
  import { reactive, ref } from 'vue'
  import { useRouter } from 'vue-router'

  export default ({
    name: 'SideMenu',
    setup(props, { emit }) {
      const router = useRouter()

      const state = reactive ({
        drawer: ref(false),
        direction: ref('ltr'),
        searchDialogOpen: false,
      })
      const clickHome = () => {
        router.push({ name: 'Home'})
        state.drawer = false
      }
      const clickConference = () => {
        router.push({ name: 'ConferenceList'})
        state.drawer = false
      }
      const clickSearch = () => {
        emit('openSearchDialog')
      }
      return { state, clickHome, clickConference, clickSearch }
    },
  });
</script>

<style>
.box {
  width:250px;
  height: 50px;
  padding-left: 29px;
}
.box:hover {
  cursor: pointer;
  background-color: #EAEAEA;
  color: orange;
  font-weight: bold;
}
.menu:hover {
  cursor: pointer;
  color: orange;
  font-size: 30px;
  font-weight: bold;
}
.side-logo {
  width: 40px;
  height: 40px;
}
.align-center {
  text-align: center;
}
.el-drawer {
  width: 250px !important;
}
</style>