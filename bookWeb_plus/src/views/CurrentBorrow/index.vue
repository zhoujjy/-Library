<template>
  <div class="main">

    <h3>当前借阅</h3>
    <div class="table">
      <DataList type="currentBorrow" ref="dataList"/>
    </div>
    <div class="pagination">
      <Pagination :pageInfo="pageData"/>
    </div>
    
  </div>
</template>

<script>
import DataList from '@/components/DataList'
import Pagination from '@/components/Pagination'
export default {
    name:'currentBorrow',
    components:{DataList,Pagination},

     data(){
      return{
        searchData:{},
        pageData:{}
      }
    },
    created(){

    },
    methods:{
      //初始化，表单调用
      async init(page,searchData){
          //把表单中的搜索条件传给当前路由，实现搜索分页
          this.searchData = searchData
          let params = {
                currentPage:page.currentPage,
                pageSize:page.pageSize,
                bookName:searchData.bookName,
                bookAuthor:searchData.bookAuthor,
                bookPress:searchData.bookPress,
            }
          let data = await this.$store.dispatch('book/getBorrowBookList',params)
          this.pageData = data
          return data.records
      },

      //页码变化,分页器调用
      async pageChange(page){
          let params = {
                currentPage:page.currentPage,
                pageSize:page.pageSize,
                bookName:this.searchData.bookName,
                bookAuthor:this.searchData.bookAuthor,
                bookPress:this.searchData.bookPress,
            }
          let data = await this.$store.dispatch('book/getBorrowBookList',params)
          this.pageData = data
          this.$refs.dataList.bookData = data.records
      },

      //查询书籍
      async searchBookData(searchData){
         //把表单中的搜索条件传给当前路由，实现搜索分页
          this.searchData = searchData
          let params = {
                currentPage:1,
                pageSize:6,
                bookName:searchData.bookName,
                bookAuthor:searchData.bookAuthor,
                bookPress:searchData.bookPress,
            }
          let data = await this.$store.dispatch('book/getBookList',params)
          this.pageData = data
          this.$refs.dataList.bookData = data.records
      },


        

    }
    
}
</script>

<style>
 .table{
        margin-top: 30px;
        height: 540px;

    }
</style>