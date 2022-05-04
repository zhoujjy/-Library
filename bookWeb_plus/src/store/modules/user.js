import userApi from '@/api/user'
import tokenUtils from '@/utils/tokenUtils'

const state = {
  userInfo:''
}

const actions = {

  // 登录
  async login({ commit }, userInfo) {
    const { username, password } = userInfo;
    let result = await userApi.login({ userEmail: username, userPassword: password });

    if(result.data.code==200){
      tokenUtils.setToken(result.data.data.token)
      return Promise.resolve(result.data.msg)
    }
    return Promise.reject(result.data.msg)
  },

  //退出
  async logout({ commit }) {
    let result = await userApi.logout()
    tokenUtils.removeToken()
    localStorage.clear()
    commit("LOGOUT")
    return Promise.resolve(result.data.msg)
  },

  //获得用户信息
  async getUserInfo({ commit }) {
    let result = await userApi.getUserInfo()
    localStorage.setItem('userRole', result.data.data.userRole)
    return result

  },



}

const mutations = {
  GETUSERINFO(state,data){
      state.userInfo = data
  },
  LOGOUT(state){
    state.userInfo = ''
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

