<template>
  <div class="charts" ref="charts">

  </div>
</template>

<script>
//引入echarts
import * as echarts from 'echarts';
export default {
    name:'',
    mounted(){
        //初始化echarts实例
        let lineCharts = echarts.init(this.$refs.charts);
        //配置数据
        lineCharts.setOption({
            xAxis:{
                show:false,//隐藏x轴
                type:'category',//折线
            },
            yAxis:{
                show:false,//隐藏x轴
            },
            //系列
            series:[
                {
                    type:'line', //折线图
                    data:this.$store.state.record.indexData.borrowNumInDay != null ? this.$store.state.record.indexData.borrowNumInDay:JSON.parse(localStorage.getItem('indexData')).borrowNumInDay,
                    //拐点样式设置
                    itemStyle:{
                        opacity:0
                    },
                    //线条的样式
                    lineStyle:{
                        color:"purple"
                    },
                    //填充颜色设置
                    areaStyle:{
                        color:{
                            type:"linear",
                            x:0,
                            y:0,
                            x2:0,
                            y2:1,
                            colorStops:[
                                {
                                    offset:0,
                                    color:"purple",
                                },
                                {
                                    offset:1,
                                    color:"#fff"
                                }
                            ]
                        }
                    }
                }
            ],
            //布局调试
            grid:{
                left:0,
                top:0,
                right:0,
                bottom:0
            }
        })
    }
}
</script>

<style scoped>
.charts{
    width: 100%;
    height: 100%;
}
</style>