import request from '@/api/request'


export default {

  getBookList(params) {
    return request({
      url: `/book/page/${params.currentPage}/${params.pageSize}/false`,
      method: 'post',
      data: params
    })
  },

  getBorrowBookList(params) {
    return request({
      url: `/book/page/${params.currentPage}/${params.pageSize}/true`,
      method: 'post',
      data: params
    })
  },

  addBook(params) {
    return request({
      url: '/book',
      method: 'post',
      data: params
    })
  },

  editBook(params) {
    return request({
      url: '/book',
      method: 'put',
      data: params
    })
  },

  borrowBook(params) {
    return request({
      url: '/book/borrow',
      method: 'put',
      data: params
    })
  },

  returnBook(bookId) {
    return request({
      url: `/book/${bookId}`,
      method: 'put'
    })
  },

  confirmReturn(bookId) {
    return request({
      url: `/book/confirmReturn/${bookId}`,
      method: 'put'
    })
  }

}