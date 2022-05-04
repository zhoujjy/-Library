import recordApi from '@/api/record'

const state = {
  indexData:{}
}

const actions = {

    async getRecordkList({ commit }, params) {
        let result =await recordApi.getRecordkList(params)
        if(result.data.code==200){
          return Promise.resolve(result.data.data);
        }
        return Promise.reject(result.data.msg);
    },

    async getIndexData({commit}){
      let result = await recordApi.getIndexData()
      commit("SETINDEXDATA",result.data.data)
      localStorage.setItem('indexData',JSON.stringify(result.data.data))
      return result
    }




}

const mutations = {
  SETINDEXDATA(state,data){
    state.indexData = data
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

