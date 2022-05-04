<template>
  <div>
    <Card :userInfo="userInfo" :dataInfo="dataInfo"/>
    <Show/>
  </div>
</template>

<script>
import Card from './Card'
import Show from './Show'

export default {
  name: 'Dashboard',
  components:{Card,Show},
  data() {
    return {
      userInfo:{},
      dataInfo:{}
    }
  },
  created(){
    //加载权限
     this.$store.dispatch('user/getUserInfo').then(res=>{
       this.userInfo = res.data.data
     })
    this.$store.dispatch('record/getIndexData').then(res=>{
      this.dataInfo = res.data.data
    })
  },
  methods:{

  }
  
}
</script>

<style lang="css" scoped>

</style>
