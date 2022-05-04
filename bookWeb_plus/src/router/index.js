import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'



export const constantRoutes = [
  {
    path: '/login',
    name:'login',
    component: () => import('@/views/login/index'),//路由懒加载
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/login',
  },

  {
    path: '/dashboard',
    component: Layout,
    redirect:'/dashboard/index',
    children: [{
      path: 'index',
      name: 'Dashboard',
      component: () => import('@/views/Dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' } //标题和图标
    }]
  },

  {
    path: '/borrowBook',
    component:Layout,
    redirect:'/borrowBook/index',
    children: [{
      path: 'index',
      name: 'borrowBook',
      component:()=>import('@/views/BorrowBook'),
      meta:{title:'图书借阅',icon:'el-icon-collection'},
    }]
  },

  {
    path: '/currentBorrow',
    component:Layout,
    redirect:'/currentBorrow/index',
    children: [{
      path: 'index',
      name: 'currentBorrow',
      component:()=>import('@/views/CurrentBorrow'),
      meta:{title:'当前借阅',icon:'el-icon-notebook-2'},
    }]
  },

  {
    path: '/borrowRecord',
    component:Layout,
    redirect:'/borrowRecord/index',
    children: [{
      path: 'index',
      name: 'borrowRecord',
      component:()=>import('@/views/BorrowRecord'),
      meta:{title:'借阅记录',icon:'el-icon-notebook-1'},
    }]
  },
  //breadcrumb : false 不显示面包屑
  {
    path: '/form',
    component:Layout,
    redirect:'/form/index',
    children: [{
      path: 'index',
      name: 'form',
      component:()=>import('@/components/DataList/Form'),
      meta:{title:'表单'}
    }],
    hidden: true

  },


  { 
    path: '*', 
    redirect: '/404',
    hidden: true 
  }
]

const createRouter = () => new Router({
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher 
}

export default router
