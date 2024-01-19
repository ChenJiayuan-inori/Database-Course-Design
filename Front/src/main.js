import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'

Vue.prototype.$axios = axios
axios.defaults.baseURL = "http://localhost:8088"
// axios.defaults.baseURL = "https://4ed2fa74.r11.cpolar.top"

Vue.config.productionTip = true
Vue.use(ElementUI)


new Vue({
  el: '#app',
  router,
  render: h => h(App)
}).$mount('#app')



axios.interceptors.request.use(config => {
  console.log(config);
  config.headers.Authorization = window.localStorage.getItem('Token');
  // 在最后必须return config
  return config;
});
