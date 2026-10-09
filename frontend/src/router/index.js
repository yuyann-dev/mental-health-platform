import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', redirect: '/manager/home' },
    {
      path: '/manager',
      component: () => import('@/views/Manager.vue'),
      children: [
        { path: 'home', meta: { name: '系统首页' }, component: () => import('@/views/manager/Home.vue'),  },
        { path: 'admin', meta: { name: '管理员信息' }, component: () => import('@/views/manager/Admin.vue'), },
        { path: 'notice', meta: { name: '系统公告' }, component: () => import('@/views/manager/Notice.vue'), },
        { path: 'person', meta: { name: '个人资料' }, component: () => import('@/views/manager/Person.vue'), },
        { path: 'password', meta: { name: '修改密码' }, component: () => import('@/views/manager/Password.vue'), },
        { path: 'user', meta: { name: '用户信息' }, component: () => import('@/views/manager/User.vue'), },
        { path: 'doctor', meta: { name: '心理医生信息' }, component: () => import('@/views/manager/Doctor.vue'), },
        { path: 'doctorAuthentication', meta: { name: '资格认证' }, component: () => import('@/views/manager/DoctorAuthentication.vue'), },
        { path: 'type', meta: { name: '心理分类' }, component: () => import('@/views/manager/Type.vue'), },
        { path: 'propagate', meta: { name: '心理健康宣传' }, component: () => import('@/views/manager/Propagate.vue'), },
        { path: 'sideshow', meta: { name: '轮播图信息' }, component: () => import('@/views/manager/Sideshow.vue'), },
        { path: 'reservation', meta: { name: '预约记录' }, component: () => import('@/views/manager/Reservation.vue'), },
        { path: 'feedback', meta: { name: '反馈与建议' }, component: () => import('@/views/manager/Feedback.vue'), },
        { path: 'topic', meta: { name: '题库信息' }, component: () => import('@/views/manager/Topic.vue'), },
        { path: 'testPaper', meta: { name: '试卷信息' }, component: () => import('@/views/manager/TestPaper.vue'), },
        { path: 'testRecord', meta: { name: '测试记录' }, component: () => import('@/views/manager/TestRecord.vue'), },
        { path: 'doctorHome', meta: { name: '系统首页' }, component: () => import('@/views/manager/DoctorHome.vue'), },
      ]
    },
    {
      path: '/front',
      component: () => import('@/views/Front.vue'),
      children: [
        { path: 'home', component: () => import('@/views/front/Home.vue'),  },
        { path: 'person', component: () => import('@/views/front/Person.vue'),  },
        { path: 'password', component: () => import('@/views/front/Password.vue'),  },
        { path: 'doctor', component: () => import('@/views/front/Doctor.vue'),  },
        { path: 'propagate', component: () => import('@/views/front/Propagate.vue'),  },
        { path: 'propagateDetail', component: () => import('@/views/front/PropagateDetail.vue'),  },
        { path: 'reservation', component: () => import('@/views/front/Reservation.vue'),  },
        { path: 'feedback', component: () => import('@/views/front/Feedback.vue'),  },
        { path: 'myFeedback', component: () => import('@/views/front/MyFeedback.vue'),  },
        { path: 'testPaper', component: () => import('@/views/front/TestPaper.vue'),  },
        { path: 'testPaperDetail', component: () => import('@/views/front/TestPaperDetail.vue'),  },
        { path: 'testRecord', component: () => import('@/views/front/TestRecord.vue'),  },
      ]
    },
    { path: '/login', component: () => import('@/views/Login.vue') },
    { path: '/register', component: () => import('@/views/Register.vue') },
    { path: '/404', component: () => import('@/views/404.vue') },
    { path: '/:pathMatch(.*)', redirect: '/404' }
  ]
})

export default router
