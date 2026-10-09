<template>
  <div>
    <div class="front-header">
      <div class="front-header-left">
        <img src="@/assets/imgs/logo.png" alt="">
        <div class="title">心理健康预约系统</div>
      </div>
      <div class="front-header-center">
        <el-menu :default-active="router.currentRoute.value.path" router mode="horizontal">
          <el-menu-item index="/front/home">首页</el-menu-item>
          <el-menu-item index="/front/testPaper">心理测试</el-menu-item>
          <el-menu-item index="/front/doctor">心理医生</el-menu-item>
          <el-menu-item index="/front/propagate">心理健康宣传</el-menu-item>
          <el-menu-item index="/front/feedback">反馈与建议</el-menu-item>
          <el-menu-item index="/front/person">个人中心</el-menu-item>
        </el-menu>
      </div>
      <div class="front-header-right">
        <div v-if="!data.user.id">
          <el-button @click="router.push('/login')">登录</el-button>
          <el-button @click="router.push('/register')">注册</el-button>
        </div>
        <div v-else>
          <el-dropdown style="cursor: pointer; height: 60px">
            <div style="display: flex; align-items: center">
              <img style="width: 40px; height: 40px; border-radius: 50%;" :src="data.user.avatar" alt="">
              <span style="margin-left: 5px;">{{ data.user.name }}</span><el-icon><arrow-down /></el-icon>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="navTo('/front/reservation')">预约记录</el-dropdown-item>
                <el-dropdown-item @click="navTo('/front/testRecord')">测试记录</el-dropdown-item>
                <el-dropdown-item @click="navTo('/front/myFeedback')">我的反馈</el-dropdown-item>
                <el-dropdown-item @click="navTo('/front/password')">修改密码</el-dropdown-item>
                <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
    </div>
    <div class="main-body">
      <RouterView @updateUser="updateUser" />
      <div style="background-color: #3c3c3c;height: 400px;">
        <div style="width: 50%;margin: 0 auto;display: flex;color: #c2c2c2">
          <div style="flex: 1;margin-top: 20px">
            <div style="padding: 10px 0;font-size: 18px">心理健康预约系统</div>
            <div style="padding: 5px 0">上亿心理医生共同打造的"心理健康"</div>
            <div style="padding: 5px 0">60,000 多次心理咨询次数</div>
            <div style="padding: 5px 0">600,000 个细分分类</div>
            <div style="padding: 5px 0">760,000,000 次心理测试</div>
            <div style="padding: 5px 0">38,000+ 心理测试试卷</div>
          </div>
          <div style="flex: 1;margin-top: 20px">
            <div style="padding: 10px 0;font-size: 18px">关于我们</div>
            <div style="padding: 5px 0">关于心理健康预约系统联系我们</div>
            <div style="padding: 5px 0">隐私政策商标声明</div>
            <div style="padding: 5px 0">服务协议</div>
            <div style="padding: 5px 0">心理健康预约系统服务协议</div>
            <div style="padding: 5px 0">网络信息侵权通知指引</div>
            <div style="padding: 5px 0">心理健康预约系统服务监督员</div>
            <div style="padding: 5px 0">网站地图加入心理健康预约系统</div>
          </div>
          <div style="flex: 1;margin-top: 20px">
            <div style="padding: 10px 0;font-size: 18px">心理咨询服务</div>
            <div style="display: flex">
              <div style="flex: 1;padding: 10px 0">心理咨询</div>
              <div style="flex: 1;padding: 5px 0">心理检测</div>
            </div>
            <div style="display: flex">
              <div style="flex: 1;padding: 10px 0">医生问答</div>
              <div style="flex: 1;padding: 5px 0">心理答疑</div>
            </div>
            <div style="display: flex">
              <div style="flex: 1;padding: 10px 0">心理调节</div>
              <div style="flex: 1;padding: 5px 0">心理问卷</div>
            </div>
            <div style="display: flex">
              <div style="flex: 1;padding: 10px 0">APP下载</div>

            </div>
            <div style="padding: 5px 0">友情链接：<a style="margin-left:10px;color: red;" href="https://space.bilibili.com/432113931">武哥聊编程</a> <a style="margin-left:10px;color: red" href="https://space.bilibili.com/402779077">程序员青戈</a> </div>
          </div>
        </div>
        <div style="width: 100%;text-align: center;color: #c2c2c2;margin-top: 40px;font-size: 20px">
          没有人是一座孤岛，每个人都需要关注心理健康
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
  import router from "@/router/index.js";
  import { reactive } from "vue";
  import request from "@/utils/request.js";

  const data = reactive({
    user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
  })

  const navTo = (url) => {
    location.href = url
  }

  const logout = () => {
    localStorage.removeItem('xm-user')
    router.push('/login')
  }

  const updateUser = () => {
    data.user =  JSON.parse(localStorage.getItem('xm-user') || '{}')
  }

</script>

<style scoped>
@import "@/assets/css/front.css";
</style>