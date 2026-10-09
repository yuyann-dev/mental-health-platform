<template>
  <div style="width: 60%; margin: 20px auto; min-height: 500px">
    <div v-for="(item, index) in data.propagateData">
      <div v-if="index % 2 === 0" class="card" style="margin-bottom: 10px; display: flex; cursor: pointer" @click="navTo('/front/propagateDetail?id=' + item.id)">
        <img :src="item.img" alt="" style="width: 200px; height: 150px; border-radius: 5px; margin-right: 10px">
        <div style="flex: 1; margin-left: 10px">
          <div style="font-size: 18px">{{ item.title }}</div>
          <div style="margin-top: 10px; color: #74726b; line-height: 20px; height: 80px" class="line4">{{ item.content }}</div>
          <div style="margin-top: 15px; display: flex; align-items: center">
            <img :src="item.doctorAvatar" alt="" style="width: 20px; height: 20px; border-radius: 50%">
            <div style="margin-left: 5px">{{ item.doctorName }}</div>
            <el-icon style="margin-left: 30px" size="large"><View /></el-icon>
            <div style="margin-left: 5px; color: #74726b">{{ item.num }}</div>
          </div>
        </div>
      </div>
      <div v-else class="card" style="margin-bottom: 10px; display: flex; cursor: pointer" @click="navTo('/front/propagateDetail?id=' + item.id)">
        <div style="flex: 1; margin-right: 10px">
          <div style="font-size: 18px">{{ item.title }}</div>
          <div style="margin-top: 10px; color: #74726b; line-height: 20px; height: 80px" class="line4">{{ item.content }}</div>
          <div style="margin-top: 15px; display: flex; align-items: center">
            <img :src="item.doctorAvatar" alt="" style="width: 20px; height: 20px; border-radius: 50%">
            <div style="margin-left: 5px">{{ item.doctorName }}</div>
            <el-icon style="margin-left: 30px" size="large"><View /></el-icon>
            <div style="margin-left: 5px; color: #74726b">{{ item.num }}</div>
          </div>
        </div>
        <img :src="item.img" alt="" style="width: 200px; height: 150px; border-radius: 5px; margin-left: 10px">
      </div>
    </div>
    <div class="card" v-if="data.total">
      <el-pagination @current-change="loadPropagate" background layout="total, prev, pager, next" :page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total" />
    </div>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";

const data = reactive({
  pageNum: 1,
  pageSize: 5,
  total: 0,
  propagateData: [],
})

const loadPropagate = () => {
  request.get('/propagate/selectPageFront', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
    }
  }).then(res => {
    if (res.code === '200') {
      data.propagateData = res.data.list
      data.total = res.data.total
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const navTo = (url) => {
  location.href = url
}
loadPropagate()
</script>

<style scoped>
.line4 {
  word-break: break-all;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 4; /* 超出几行省略 */
  overflow: hidden;
}
</style>