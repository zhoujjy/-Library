<template>
  <div class="main">
      <Globe/>
      <Pillar/>
  </div>
</template>

<script>
import Globe from './Globe'
import Pillar from './Pillar'
export default {
    name:'',
    components:{Globe,Pillar},
}
</script>

<style scoped>
    .main{
        padding: 30px 0;
        display: flex;
        justify-content: space-between;
        height: 480px;
    }
</style>