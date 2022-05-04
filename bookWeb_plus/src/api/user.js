import request from '@/api/request'

export default{
    // 登录
    login(userInfo) {
        return request({
            url: '/user/login',
            method: 'post',
            data: userInfo
        })
    },

    // 退出
    logout(){
        return request({
            url: '/user/logout',
            method: 'post'
        })
    },


    // 获得用户信息
    getUserInfo(){
        return request({
            url: '/user',
            method: 'get'
        })
    }
    
}




