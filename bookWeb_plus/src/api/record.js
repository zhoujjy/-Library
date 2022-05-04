import request from '@/api/request'

export default{
    getRecordkList(params){
        return request({
            url: `/record/page/${params.currentPage}/${params.pageSize}`,
            method: 'post',
            data: params
        })
    },

    getIndexData(){
        return request({
            url: '/record',
            method: 'get'
        })
    }
    
}




