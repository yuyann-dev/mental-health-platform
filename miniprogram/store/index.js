import Vue from 'vue'
import Vuex from 'vuex'
import user from '@/store/modules/user'
import getters from './getters'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
	// user使用方法：
	// 1.页面中引入
	// import {mapState} from 'vuex';
	// 2.通过computed获取mapState中的user
	//  computed: {
	// 	// 获取vuex中的user
	// 	...mapState(['user'])
	// },
	//  3.直接使用this.user中参数
	//  if (!this.user.hasLogin) { ...
	//  if (!this.user.userId) { ...
	//  if (!this.user.name) { ...
	//  if (!this.user.token) { ...
    user
  },
	// getters使用方法
	// 页面模板中：$store.getters.hasLogin
	// js中：this.$store.getters.hasLogin
	// js中：this.$store.getters.userId
  getters
})

export default store
