<template>
  <div style="width: 60%; margin: 10px auto; min-height: 500px; padding: 20px; margin-bottom: 50px" class="card">
    <div style="font-size: 24px; font-weight: bold; text-align: center">{{ data.propagateData.title }}</div>
    <div style="margin-top: 10px; text-align: center">
      <span>发布时间：{{ data.propagateData.time }}</span>
      <span style="margin: 0 20px">发布人：{{ data.propagateData.doctorName }}</span>
      <span>浏览量：{{ data.propagateData.num }}</span>
    </div>
    <div style="padding: 20px; margin-top: 20px" v-html="data.propagateData.content"></div>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";
import router from "@/router/index.js";

const data = reactive({
  propagateId: router.currentRoute.value.query.id,
  propagateData: {}
})

const loadPropagate = () => {
  data.propagateId = router.currentRoute.value.query.id
  request.get('/propagate/selectById/' + data.propagateId).then(res => {
    if (res.code === '200') {
      data.propagateData = res.data
      data.propagateData.num = data.propagateData.num + 1
      request.put('/propagate/update', data.propagateData)
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadPropagate()
</script>

<style scoped>
</style>