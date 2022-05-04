<template>
  <div class="main">

    <h3>借阅记录</h3>
    <div class="table">

    <!-- 搜索 -->
    <el-form :inline="true" :model="searchData" class="demo-form-inline">
    <el-form-item label="借阅人">
      <el-input v-model="searchData.borrower" placeholder="借阅人"></el-input>
    </el-form-item>
    <el-form-item label="图书名称">
      <el-input v-model="searchData.bookName" placeholder="图书名称"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" plain icon="el-icon-search" @click="searchRecordData">查询</el-button>
      <el-button type="primary" plain  @click="cancelSearchMethod">取消</el-button>
    </el-form-item>
    </el-form>

      <!-- 表格 -->
    <el-table :data="pageList"  stripe border  style="width: 100%" >
      <el-table-column
        prop="borrower"
        label="借阅人"
        align="center">
      </el-table-column>

      <el-table-column
        prop="bookName"
        label="图书名称"
        align="center">
      </el-table-column>

      <el-table-column
        prop="bookIsbn"
        label="标准ISBN"
        align="center">
      </el-table-column>


      <el-table-column prop="borrowTime" label="借阅时间" align="center" > 
        <template slot-scope="scope">
          <i class="el-icon-time" v-if="scope.row.borrowTime"></i>
          <span style="margin-left: 10px">{{ scope.row.borrowTime}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="remandTime" label="归还时间" align="center" >
        <template slot-scope="scope">
          <i class="el-icon-time" v-if="scope.row.remandTime"></i>
          <span style="margin-left: 10px">{{ scope.row.remandTime}}</span>
        </template>
      </el-table-column>
    </el-table>
    </div>
    <div class="pagination">
      <Pagination :pageInfo="pageData"/>
    </div>
    
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
export default {
    name:'borrowRecord',
    components:{Pagination},
    data(){
      return{
        
        searchData:{
          borrower:'',
          bookName:'',
        },
        pageList:[],
        pageData:{},
        page:{
          currentPage: 1,
          pageSize: 6,
        }
      }
    },

    created(){
      this.init();
    },

    methods:{
      //初始化，表单调用
      async init(){
          let params = {
                currentPage:this.page.currentPage,
                pageSize:this.page.pageSize,
                bookName:this.searchData.bookName,
                borrower:this.searchData.borrower,
            }
          let data = await this.$store.dispatch('record/getRecordkList',params)
          this.pageList=data.records
          this.pageData = data
      },

      //页码变化,分页器调用
      async pageChange(page){
          let params = {
                currentPage:page.currentPage,
                pageSize:page.pageSize,
                bookName:this.searchData.bookName,
                borrower:this.searchData.borrower,
            }
          let data = await this.$store.dispatch('record/getRecordkList',params)
          this.pageData = data
          this.pageList=data.records
      },

      //查询书籍
      async searchRecordData(){
          let params = {
                currentPage:1,
                pageSize:6,
                bookName:this.searchData.bookName,
                borrower:this.searchData.borrower,
            }
          let data = await this.$store.dispatch('record/getRecordkList',params)
          this.pageData = data
          this.pageList=data.records
      },


      //取消查询
      cancelSearchMethod(){
        this.searchData = { 
              bookName:'',
              borrower:''};
        this.init();
      }


    }
    
}
</script>

<style scoped>
 .table{
        margin-top: 30px;
        height: 540px;

    }
      .demo-form-inline{
        float: right;
        
    }
</style>