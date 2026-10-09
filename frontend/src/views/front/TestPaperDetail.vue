<template>
  <div style="min-height: 500px; width: 60%; margin: 20px auto" class="card">
    <div style="text-align: center">
      <span style="font-size: 22px; font-weight: bold">{{ data.testPaper.title }}</span>
      <span style="margin-left: 20px">满分：{{ data.testPaper.score }}</span>
    </div>
    <div style="margin-top: 20px; padding: 0 40px">
      <div v-for="(item, index) in data.testPaper.topicList" style="margin-bottom: 10px">
        <div style="line-height: 20px; background-color: #ddf1fc; padding: 5px">{{index + 1}}、{{ item.title }}</div>
        <div style="margin-top: 10px">
          <el-radio-group v-model="item.userAnswer">
            <el-radio :value="item.aName">A. {{ item.aName }}</el-radio>
            <el-radio :value="item.bName">B. {{ item.bName }}</el-radio>
            <el-radio :value="item.cName">C. {{ item.cName }}</el-radio>
            <el-radio :value="item.dName">C. {{ item.dName }}</el-radio>
          </el-radio-group>
        </div>
      </div>
    </div>
    <div style="margin: 30px 0 20px 0; text-align: center">
      <el-button type="primary" @click="submit" style="padding: 20px 30px">提交测试</el-button>
    </div>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";
import router from "@/router/index.js";
const data = reactive({
  testPaperId: router.currentRoute.value.query.id,
  testPaper: {}
})

const loadTestPaper = () => {
  data.testPaperId = router.currentRoute.value.query.id
  request.get('/testPaper/selectById/' + data.testPaperId).then(res => {
    if (res.code === '200') {
      data.testPaper = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const submit = () => {
  request.post('/testRecord/add', data.testPaper).then(res => {
    if (res.code === '200') {
      ElMessage.success('提交成功，可以在测试记录里查看测试结果')
      setInterval(() => {
        location.href = '/front/testRecord'
      }, 500)
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadTestPaper()
</script>

<style scoped>
.el-radio-group, .el-radio {
  display: block !important;
}
</style>