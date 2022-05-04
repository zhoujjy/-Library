<template>
  <div>

      <!-- 每个col距离为10 -->
      <el-row :gutter="10">
         <!--总共24栏  -->

          <el-col :span="8">
              <el-card>
                  <Detail :title="userInfo.userRole=='ADMIN'?'管理员':'用户'" :count="userInfo.userName">

                      <template slot="charts">
                          <img src="@/assets/images/headImg.png" height="50px" width="50px" style="margin-top:-5px">
                      </template>

                      <template slot="footer">
                          <span>邮箱：{{userInfo.userEmail}}</span>
                      </template>
                  </Detail>
              </el-card>
          </el-col>

        <el-col :span="8">
              <el-card>
                  <Detail title="图书借阅量" :count="dataInfo.allBorrowNum">
                      <template slot="charts">
                        <LineCharts></LineCharts>
                      </template>
                      <template slot="footer">
                          <span>日借阅量：{{dataInfo.borrowNumInNow}}</span>
                      </template>
                  </Detail>
              </el-card>
          </el-col>

            <el-col :span="8">
              <el-card >
                <div id="header">
                    <span>通知</span>
                    <svg t="1647945172957" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2199" width="16" height="16"><path d="M536 480v192a16 16 0 0 1-16 16h-16a16 16 0 0 1-16-16V480a16 16 0 0 1 16-16h16a16 16 0 0 1 16 16z m-32-128h16a16 16 0 0 1 16 16v32a16 16 0 0 1-16 16h-16a16 16 0 0 1-16-16v-32a16 16 0 0 1 16-16z m8 448c159.056 0 288-128.944 288-288s-128.944-288-288-288-288 128.944-288 288 128.944 288 288 288z m0 48c-185.568 0-336-150.432-336-336s150.432-336 336-336 336 150.432 336 336-150.432 336-336 336z" p-id="2200" fill="#8a8a8a"></path></svg>
                </div>
                <div id="content">
                    <div id="user" v-if="userInfo.userRole!='ADMIN'">
                        <div v-for="(value,index) in dataInfo.returnBook" :key="index">《{{value}}》已归还</div>
                    </div>
                    <div id="admin" v-if="userInfo.userRole=='ADMIN'">
                        <div v-if="dataInfo.confirmNum>0">
                            有书籍需要归还，请及时处理
                        </div>
                    </div>
                    
                </div>
                <div id="footer">
                    <span v-if="userInfo.userRole=='ADMIN'">待处理数量:{{dataInfo.confirmNum}}</span>
                    <span v-if="userInfo.userRole!='ADMIN'">待归还数量:{{dataInfo.returnNum}}</span>
                </div>
              </el-card>
          </el-col>
      </el-row>
  </div>
</template>

<script>
import Detail from './Detail'
import LineCharts from './lineChart'
export default {
    name:'',
    components:{Detail,LineCharts},
    data(){
        return{
            authority:false, 
            
        }
    },
    props:['userInfo','dataInfo'],
    created(){
        // setTimeout(()=>{
        //     this.authority =  this.$store.state.user.userInfo.userRole=='ADMIN' ? true:false
        // },500)
        // this.dataInfo =JSON.parse(localStorage.getItem('indexData')) || this.$store.state.record.indexData
        
        this.authority =  this.userInfo.userRole=='ADMIN' ? true:false
    },
    computed:{
    }
}
</script>

<style scoped>
    #header{
        display: flex;
        justify-content: space-between;
        color: #d9d9d9;
    }
    #content{
        
        padding: 20px 0;
        height: 105px;
    }
    #admin div{
        padding-top: 20px;
        font-size: 20px;
    }
    #user div{
        margin-bottom: 10px;
    }
    #footer{
        border-top: 1px solid #eee;
        padding: 5px 0;
    }
</style>