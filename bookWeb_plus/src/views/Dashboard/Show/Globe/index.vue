<template>
   <div class="globe" ref="globe">

   </div>
</template>

<script>
import * as echarts from 'echarts';
export default {
    mounted(){
        var myChart = echarts.init(this.$refs.globe);
        var option;
        let borrowedBookNum;
        let returnBookNum;
        if(this.$store.state.record.indexData.borrowedBookNum==null){
            borrowedBookNum = JSON.parse(localStorage.getItem('indexData')).borrowedBookNum
            returnBookNum = JSON.parse(localStorage.getItem('indexData')).canBorrowBookNum
        }else{
            borrowedBookNum = this.$store.state.record.indexData.borrowedBookNum
            returnBookNum = this.$store.state.record.indexData.canBorrowBookNum
        }

        option = {
        color:["#5470c6","#91cc75"],
        title: {
            text: '书籍情况',
            left: 'center'
        },
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left'
        },
        series: [
            {
            name: '书籍表',
            type: 'pie',
            radius: '50%',
            data: [
                { value: borrowedBookNum, name: '已借阅书籍' },
                { value:returnBookNum , name: '剩余书籍' },
            ],
            emphasis: {
                itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
            }
        ]
        };

        option && myChart.setOption(option);
    }

}
</script>

<style scoped>
    .globe{
        width: 50%;
        margin: 10px;
        
    }
</style>