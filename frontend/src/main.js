import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import installElementPlus from './plugins/element'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap"
import VueGoogleMaps from '@fawmi/vue-google-maps'

const app = createApp(App)
installElementPlus(app)
app.use(store).use(router).use(VueGoogleMaps, {
    load: {
        key: 'AIzaSyDQSO94FGet7QhWF02tr7_bWYoOZE5ZGGQ',
    },
}).mount('#app')