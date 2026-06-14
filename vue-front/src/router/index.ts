import { createRouter, createWebHashHistory } from 'vue-router'
import { useUserStore } from '@/pinia/stores/user'
import Login from '../pages/login/index.vue'
import Register from '../pages/register/index.vue'
import Layout from '../layouts/index.vue'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    component: Login,
    meta: { public: true }
  },
  {
    path: '/register',
    component: Register,
    meta: { public: true }
  },
  {
    path: '/404',
    component: () => import('../pages/error/404.vue'),
    meta: { public: true, hidden: true },
    alias: '/:pathMatch(.*)*'
  },
  {
    path: '/dashboard',
    component: Layout,
    children: [
      {
        path: '',
        component: () => import('../pages/dashboard/index.vue'),
        meta: { title: '首页' }
      }
    ]
  },
  {
    path: '/demo',
    component: Layout,
    redirect: '/demo/news',
    name: 'Demo',
    children: [
      {
        path: 'news',
        component: () => import('../pages/demo/newsinfo/index.vue'),
        meta: { title: '新闻列表' }
      },
      {
        path: 'news/add',
        component: () => import('../pages/demo/newsadd/index.vue'),
        name: 'NewsAdd',
        meta: { title: '新增新闻' }
      }
    ]
  }
]

const router = createRouter(
  {
  history: createWebHashHistory(),
  routes
})

// 路由守卫：未登录则跳转登录页
router.beforeEach((to, _from, next) => {
  const userStore = useUserStore()
  if (to.meta.public) {
    // 公开页面直接放行
    next()
  } else if (!userStore.isLoggedIn()) {
    // 未登录，跳转登录页
    next('/login')
  } else {
    next()
  }
})

export default router
