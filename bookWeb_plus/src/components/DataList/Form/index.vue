<template>
<div id="main">
    <div id="form">
        <h2>{{$route.params.title}}</h2>
        <div id="include">
        <el-form :inline="true" :rules="rules" label-position="top" ref="bookForm"  :model="bookFormData" class="demo-form-inline form">
            <el-form-item prop="bookName" label="图书名称" >
            <el-input v-model="bookFormData.bookName" placeholder="图书名称" :disabled="type.isBorrow"></el-input>
            </el-form-item>
            <el-form-item prop="bookIsbn" label="标准ISBN">
            <el-input v-model.number="bookFormData.bookIsbn" placeholder="标准ISBN" :disabled="type.isBorrow"></el-input>
            </el-form-item>
            <el-form-item prop="bookPress" label="出版社 ">
            <el-input v-model="bookFormData.bookPress" placeholder="出版社" :disabled="type.isBorrow"></el-input>
            </el-form-item>
            <el-form-item prop="bookAuthor"  label="作者">
            <el-input v-model="bookFormData.bookAuthor" placeholder="作者" :disabled="type.isBorrow"> </el-input>
            </el-form-item>
            <el-form-item prop="bookPagination" label="书籍页数" >
            <el-input v-model.number="bookFormData.bookPagination" placeholder="书籍页数" :disabled="type.isBorrow" ></el-input>
            </el-form-item>
            <!-- :disabled="isBorrowBook" 禁止输入 -->
            <el-form-item prop="bookPrice" label="书籍价格" v-if="type.isAdd||type.isEdit">
            <el-input v-model.number="bookFormData.bookPrice" placeholder="书籍价格" ></el-input>
            </el-form-item>
            <el-form-item label="书籍状态" v-if="type.isAdd||type.isEdit">
            <el-select v-model="bookFormData.bookStatus" placeholder="书籍状态"  style="width: 190px;">
                <el-option label="可借阅" value="0"></el-option>
                <el-option label="借阅中" value="1"></el-option>
                <el-option label="归还中" value="2"></el-option>
                <el-option label="已下架" value="3"></el-option>
            </el-select>
            </el-form-item>
            <el-form-item prop="bookReturnTime" label="归还时间" v-if="type.isBorrow">
            <!-- <el-input v-model="bookFormData.bookReturnTime" placeholder="yyyy-mm-dd"></el-input> -->
            <el-date-picker v-model="bookFormData.bookReturnTime" type="date" placeholder="选择日期" format="yyyy 年 MM 月 dd 日"
            value-format="yyyy-MM-dd"
            style="width: 190px;"
            ></el-date-picker>
            </el-form-item>
        </el-form>
        </div>
        <div class="button">
            <el-button  type="primary" @click="submitForm" style="margin-right:20px">提交</el-button>
            <el-button  @click="cancelSubmit" >取消</el-button>
        </div>
       
    </div>
  </div>
</template>

<script>
export default {
    name:'myForm',
    data(){
          // 表单验证规则定义
        var checkIsbn = (rule, value, callback) => {
            //正则13位数字
            const isbnReg = /^\d{13}$/;
            if (value === '') {
            return callback(new Error('请输入ISBN'));
            } 
            if (!isbnReg.test(value)) {
                callback(new Error('ISBN必须为13位数字'));
            } else {
                callback();
            }
        };
        //日期格式校验
        var checkDate = (rule, value, callback) => {
            var myDate = new Date();
            if (value === '' || value === null) {
            return callback(new Error('请输入归还日期'));
            } 
            let year = value.split('-');
            if(!(parseInt(year[0]) >= myDate.getFullYear() && parseInt(year[1]) >= myDate.getMonth()+1 && parseInt(year[2]) > myDate.getDate())){
                callback(new Error('归还日期不能小于当前日期'));

            }else {
                callback();
            }
        };
        return{
            bookFormData:{
                bookId:'',
                bookName:'',
                bookAuthor:'',
                bookPress:'',
                bookPrice:'',
                bookIsbn:'',
                bookStatus:'0',
                bookPagination:'',
                bookBorrower:'',
                bookBorrowTime:'',
                bookReturnTime:''
            },
            rules: {
                bookName: [
                    { required: true, message: '请输入图书名称', trigger: 'blur' },
                ],
                bookAuthor: [
                    { required: true, message: '请输入图书作者', trigger: 'blur' },
                ],
                bookPress: [
                    { required: true, message: '请输入出版社', trigger: 'blur' },
                ],
                bookPrice: [
                    { required: true, message: '请输入图书价格', trigger: 'blur' },
                    {type:'number', message: '价格必须为数字', trigger: 'blur' },
                
                ],
                bookIsbn: [
                    { required: true,validator: checkIsbn,trigger: 'blur' },
                ],
                bookPagination: [
                    { required: true, message: '请输入书籍页数', trigger: 'blur' },
                    {type:'number', message: '价格必须为数字', trigger: 'blur' }
                ],
                bookReturnTime: [
                    { required: true,validator: checkDate,trigger: 'blur' },
                ],
            },
            type:{
                isAdd:this.$route.params.type==='add'?true:false,
                isBorrow:this.$route.params.type==='borrow'?true:false,
                isEdit:this.$route.params.type==='edit'?true:false,
            }
            
        }
    },


    created(){
     if(this.$route.params.title==undefined){
         this.$router.push({name:'borrowBook'});
         return
     }
     this.$route.meta.title = this.$route.params.title
     if(undefined !==this.$route.params.data){
        this.bookFormData = JSON.parse(JSON.stringify(this.$route.params.data));
     }
    },

    methods:{
        //提交表单
        submitForm(){
            this.$refs.bookForm.validate((valid) => {
                if (valid) {
                    if(this.type.isAdd){
                        this.$store.dispatch('book/addBook',this.bookFormData).then(res=>{
                            this.$message.success(res);
                            this.$router.push({name:'borrowBook'});
                        }).catch(err=>{
                            this.$message.error(err);
                        })
                    }
                    if(this.type.isEdit){
                        this.$store.dispatch('book/editBook',this.bookFormData).then(res=>{
                            this.$message.success(res);
                            this.$router.push({name:'borrowBook'});
                        }).catch(err=>{
                            this.$message.error(err);
                        })
                    }
                    if(this.type.isBorrow){
                        this.$store.dispatch('book/borrowBook',this.bookFormData).then(res=>{
                            this.$message.success(res);
                            this.$router.push({name:'borrowBook'});
                        }).catch(err=>{
                            this.$message.error(err);
                        })
                    }
                } else {
                    this.$message.error("请正确输入数据！")
                    return false;
                }
            });
        },

        //取消
        cancelSubmit(){
            this.$router.back() //后退
        }
    }
}
</script>

<style scoped>
    #form{
        margin: 0 auto;
        margin-top: 40px;
        width: 600px;
        background-color: #f9f9f9;

    }
    #form h2{
        text-align: center;
        line-height: 60px;
        
    }
    #include{
        margin: 0 auto;
        width: 500px;
    }
    .form{
        display:flex;
        flex-wrap: wrap;
        justify-content: space-between;
    }
    .button{
        display:inline-block;
        position: relative;
        left: 50%;
        transform: translateX(-50%);
        margin-top: 30px;
        margin-bottom: 30px;
    }
</style>