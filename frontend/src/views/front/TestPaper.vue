<template>
  <div style="min-height: 500px; width: 60%; margin: 20px auto">
    <div class="card">
      <el-button :class="{'button_active' : data.typeName === null}" @click="loadTestPaper(null)">全部</el-button>
      <el-button :class="{'button_active' : data.typeName === item.title}" v-for="item in data.typeData" @click="loadTestPaper(item.title)">{{ item.title }}</el-button>
    </div>
    <div class="card" style="margin-top: 10px; display: flex" v-for="item in data.testPaperData">
      <div style="flex: 1; margin-right: 20px">
        <div style="font-size: 18px">{{ item.title }}</div>
        <div style="margin-top: 10px; color: #666666; line-height: 20px; height: 60px" class="line3">{{ item.content }}</div>
        <div style="margin-top: 20px; display: flex; align-items: center">
          <img :src="item.doctorAvatar" alt="" style="width: 20px; height: 20px; border-radius: 50%">
          <div style="margin-left: 5px">{{ item.doctorName }}</div>
          <el-icon style="margin-left: 20px" size="large"><EditPen /></el-icon>
          <div style="margin-left: 5px; color: #666666">{{ item.testNum }}</div>
          <el-icon style="margin-left: 20px" size="large"><Stopwatch /></el-icon>
          <div style="margin-left: 5px; color: #8d8a8a">{{ item.time }}</div>
          <el-icon style="margin-left: 20px" size="large"><Tickets /></el-icon>
          <div style="margin-left: 5px; flex: 1"><el-tag>{{ item.typeName }}</el-tag></div>
          <div style="width: 100px; text-align: right">
            <el-button type="primary" @click="navTo('/front/testPaperDetail?id=' + item.id)">去测试</el-button>
          </div>
        </div>
      </div>
      <img :src="item.img" alt="" style="width: 200px; height: 140px; border-radius: 5px">
    </div>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";
const data = reactive({
  typeData: [],
  testPaperData: [],
  typeName: null
})

const loadType = () => {
  request.get('/type/selectAll').then(res => {
    if (res.code === '200') {
      data.typeData = res.data
      loadTestPaper(null)
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const loadTestPaper = (typeName) => {
  data.typeName = typeName
  request.get('/testPaper/selectAll', {
    params: {
      typeName: typeName
    }
  }).then(res => {
    if (res.code === '200') {
      data.testPaperData = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const navTo = (url) => {
  location.href = url
}

loadType()
</script>

<style scoped>
.line3 {
  word-break: break-all;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3; /* 超出几行省略 */
  overflow: hidden;
}
.button_active {
  background-color: #0066bc;
  color: white;
}
</style>