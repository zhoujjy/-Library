import bookApi from '@/api/book'

const state={

}
const actions={
  //分页请求
  async getBookList({commit},params){
    let result =await bookApi.getBookList(params)
    if(result.data.code==200){
      return Promise.resolve(result.data.data);
    }
    return Promise.reject(result.data.msg);
  },

  //当前借阅分页
  async getBorrowBookList({commit},params){
    let result =await bookApi.getBorrowBookList(params)
    if(result.data.code==200){
      return Promise.resolve(result.data.data);
    }
    return Promise.reject(result.data.msg);
  },

  //书籍添加
  async addBook({commit},params){
    let result =await bookApi.addBook(params)
    if(result.data.code==200){
      return Promise.resolve(result.data.msg);
    }
    return Promise.reject(result.data.msg);
  },

  //编辑书籍
  async editBook({commit},params){
    let result =await bookApi.editBook(params)
    if(result.data.code==200){
      return Promise.resolve(result.data.msg);
    }
    return Promise.reject(result.data.msg);
  },

  //借阅
  async borrowBook({commit},params){
    let result =await bookApi.borrowBook(params)
    if(result.data.code==200){
      return Promise.resolve(result.data.msg);
    }
    return Promise.reject(result.data.msg);
  },

  //归还书籍
  async returnBook({ commit }, bookId) {
    let result = await bookApi.returnBook(bookId)
    if (result.data.code == 200) {
      return Promise.resolve(result.data.msg)
    }
    return Promise.reject(result.data.msg)
  },

  //确认归还
  async confirmReturn({ commit }, bookId) {
    let result = await bookApi.confirmReturn(bookId)
    if (result.data.code == 200) {
      return Promise.resolve(result.data.msg)
    }
    return Promise.reject(result.data.msg)
  }

}
const mutations={

}


export default {
    namespaced: true,
    state,
    mutations,
    actions
  }