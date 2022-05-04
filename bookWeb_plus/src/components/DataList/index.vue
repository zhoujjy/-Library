<template>
  <div>

    <el-button type="primary" plain icon="el-icon-circle-plus" @click="toForm('add')"
    v-if="type=='borrowBook' && authority"
    >添加</el-button>

    <!-- 搜索 -->
    <el-form :inline="true" :model="searchData" class="demo-form-inline">
    <el-form-item label="名称">
      <el-input v-model="searchData.bookName" placeholder="名称"></el-input>
    </el-form-item>
    <el-form-item label="作者">
      <el-input v-model="searchData.bookAuthor" placeholder="作者"></el-input>
    </el-form-item>
    <el-form-item label="出版社">
      <el-input v-model="searchData.bookPress" placeholder="出版社"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" plain icon="el-icon-search" @click="searchBookData">查询</el-button>
      <el-button type="primary" plain  @click="cancelSearchMethod">取消</el-button>
    </el-form-item>
    </el-form>
      <!-- 表格 -->
    <el-table :data="bookData"  stripe border  style="width: 100%">
      <el-table-column
        prop="bookName"
        label="图书名称"
        align="center">
      </el-table-column>
      <el-table-column
        prop="bookAuthor"
        label="图书作者"
        align="center">
      </el-table-column>
      <el-table-column
        prop="bookPress"
        label="出版社"
        align="center">
      </el-table-column>
      <el-table-column
        prop="bookIsbn"
        label="标准ISBN"
        align="center">
      </el-table-column>
      <el-table-column
        prop="bookStatus"
        label="书籍状态"
        align="center"
        :formatter="handleStatus">
      </el-table-column>
 
      <el-table-column
        prop="bookBorrower"
        label="借阅人"
        align="center">
      </el-table-column>
      <el-table-column prop="bookBorrowTime" label="借阅时间" align="center" > 
        <template slot-scope="scope">
          <i class="el-icon-time" v-if="scope.row.bookBorrowTime"></i>
          <span style="margin-left: 10px">{{ scope.row.bookBorrowTime}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="bookReturnTime"
        label="预计归还时间"
        v-if="type=='borrowBook'"
        align="center"
        >
         <template slot-scope="scope">
          <i class="el-icon-time" v-if="scope.row.bookReturnTime"></i>
          <span style="margin-left: 10px">{{ scope.row.bookReturnTime}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="bookReturnTime"
        label="应归还时间"
        align="center"
        v-if="!(type=='borrowBook')"
      >
        <template slot-scope="scope">
          <i class="el-icon-time" v-if="scope.row.bookReturnTime"></i>
          <span style="margin-left: 10px">{{ scope.row.bookReturnTime}}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" width="180px">
        <template slot-scope="scope" >

            <el-button
                slot="reference"
                size="mini"
                type="info" 
                plain
                :disabled="scope.row.bookStatus!='0'"
                v-if="type=='borrowBook'"
                @click="toForm('borrow',scope.row)"
                style="margin-right:10px"
                >借阅</el-button>


            <el-button
              size="mini"
              type="success" 
              plain
              v-if="type=='borrowBook' && authority"
              @click="toForm('edit',scope.row)">
              编辑</el-button>
            <el-dialog title="图书编辑">
              
            </el-dialog>
          

            <el-button
                size="mini"
                :type="scope.row.bookStatus=='1'? 'success':'info'" 
                plain
                :disabled="scope.row.bookStatus=='2'"
                v-if="!(type=='borrowBook')"
                @click="returnBook(scope.row)">
                {{scope.row.bookStatus=='1'? '归还':'归还中'}}               
            </el-button>
            <el-button
                size="mini"
                type="success" 
                plain
                v-if=" type!='borrowBook' && authority && scope.row.bookStatus=='2'"
                @click="confirmReturn(scope.row)">
                归还确认
                </el-button>
            </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
    name: 'dataList',

    data(){
        return {
            authority:false, 
            searchData:{
                bookName:'',
                bookAuthor:'',
                bookPress:'',
            },
            bookData:[],
            page:{
              currentPage: 1,
              pageSize: 6,
            }
        }
    },

    props:['type'],

    created(){
        this.init();
        this.authority=localStorage.getItem('userRole')=='ADMIN'?true:false; //权限,判断是否是管理员，后端同样要进行判断
    },

    methods:{
        //初始化
        async init(){
            this.bookData =await this.$parent.init(this.page,this.searchData);
        },

        //查询书籍
        searchBookData(){
          this.$parent.searchBookData(this.searchData)
        },

        //取消查询
        cancelSearchMethod(){
          this.searchData = { bookName:'',
                bookAuthor:'',
                bookPress:'',};
          this.init();
        },

        //跳转到表单
        toForm(str,data){
            if(str == 'add'){
                this.$router.push({
                    name:'form',
                    params:{   
                        type:'add',
                        title:'新增书籍'                                                  
                   }
                })
                return
            }
            if(str == 'edit'){
                this.$router.push({
                    name:'form',
                    params:{
                        data:data,   
                        type:'edit',
                        title:'编辑书籍'                                                  
                   }
                })
                return
            }
            if(str == 'borrow'){
                this.$router.push({
                    name:'form',
                    params:{ 
                        data:data,    
                        type:'borrow',
                        title:'借阅书籍'                                                  
                   }
                })
                return
            }
            console.log(data);

        },

        //归还书籍
        returnBook(data){
          this.$confirm(`确认归还《${data.bookName}》?`, '提示', {
            'confirmButtonText': '确定',
            'cancelButtonText': '取消',
            'type': 'warning'
            }).then(() => {
              this.$store.dispatch("book/returnBook",data.bookId).then((res)=>{
                this.$message({
                  type: 'success',
                  message: res
                });
                this.init();
              }).catch((err)=>{
                this.$message({
                  type: 'error',
                  message: err
                });
              })
            }).catch(() => {
              this.$message({
                type: 'info',
                message: '已取消归还'
              });          
            });
            
        },

        //确认归还
        confirmReturn(data){
          this.$confirm(`确认归还《${data.bookName}》?`, '提示', {
            'confirmButtonText': '确定',
            'cancelButtonText': '取消',
            'type': 'warning'
            }).then(() => {
              this.$store.dispatch("book/confirmReturn",data.bookId).then((res)=>{
                this.$message({
                  type: 'success',
                  message: res
                });
                this.init();
              }).catch((err)=>{
                this.$message({
                  type: 'error',
                  message: err
                });
              })
            }).catch(() => {
              this.$message({
                type: 'info',
                message: '已取消归还'
              });          
            });
        },

        //处理状态
        handleStatus(row){
          switch(row.bookStatus){
            case '0':
              return '可借阅';
            case '1':
              return '已借阅';
            case '2':
              return '归还中';
            case '3':
              return '已下架';
          }
        },

    }
}
</script>

<style scoped>
    .demo-form-inline{
        float: right;
        
    }
</style>