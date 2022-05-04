<template>
  <div class="pillar" ref="pillar">

  </div>
</template>

<script>
import * as echarts from 'echarts';
export default {

    mounted(){
        var myChart = echarts.init(this.$refs.pillar);
        var option;

       option = {
        title: {
            text: '借阅情况',
            left: 'center'
        },
        color:["#5470c6","#91cc75"],
        xAxis: {
            type: 'category',
            data: ['2019年', '2020年', '2021年','2022年']
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
            data:this.$store.state.record.indexData.borrowNumInYear || JSON.parse(localStorage.getItem('indexData')).borrowNumInYear,
            type: 'bar',
            
            }
        ]
        };

        option && myChart.setOption(option);
    }


}
</script>

<style>
    .pillar{
        width: 50%;
        margin: 10px;
        
    }
</style>