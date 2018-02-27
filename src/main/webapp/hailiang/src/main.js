// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import VueHtml5Editor from '@/VueHtml5Editor.js'
//import axios from 'axios'
import $ from 'jquery'
import iView from 'iview'//之后整理，可以改成按需引入，减少体积，
import dataUrl from '@/assets/config.js'
import VueCookies from 'vue-cookies'//引入vue-cookies,貌似无法直接保存一个对象，需要将原来的设置和获取cookies切分开


import 'iview/dist/styles/iview.css'
import '@/common/resetcss.css'
import 'font-awesome/css/font-awesome.css'

Vue.use(iView);
Vue.use(VueCookies);

Vue.config.productionTip = false
//Vue.prototype.$axios=axios

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
