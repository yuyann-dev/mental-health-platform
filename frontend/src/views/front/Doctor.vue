<template>
  <div style="margin: 0 auto; background-color: #f3f4f5; padding: 30px 0">
    <div style="width: 60%; margin: 0 auto; display: flex; align-items: center">
      <div style="font-size: 24px; width: 180px; font-weight: bold; color: #0b8bff">心理医生预约</div>
      <div style="font-size: 18px; color: #8c939d; margin-left: 20px; flex: 1">没有人是一座孤岛，每个人都需要心理咨询</div>
    </div>
    <div style="width: 60%; margin: 20px auto">
      <el-row :gutter="10">
        <el-col :span="6" v-for="item in data.doctorData" style="margin-bottom: 20px">
          <div style="border-top-left-radius: 10px; border-top-right-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,.1); padding-bottom: 15px">
            <img :src="item.avatar" alt="" style="width: 100%; height: 250px; border-top-left-radius: 10px; border-top-right-radius: 10px">
            <div style="padding: 10px 15px">
              <div style="font-size: 16px; height: 30px; line-height: 30px">{{ item.name }}</div>
              <div style="font-size: 16px; height: 30px; line-height: 30px">累计帮助 10 人</div>
              <div style="margin-top: 5px">医生工龄：<el-tag type="success">{{ item.seniority }} 年</el-tag></div>
              <el-popover
                  placement="top-start"
                  title="医生简介："
                  :width="300"
                  trigger="hover"
                  :content="item.content"
              >
                <template #reference>
                  <div style="margin-top: 10px; color: #666666; line-height: 20px; height: 60px" class="line3">医生简介：{{ item.content }}</div>
                </template>
              </el-popover>
            </div>
            <div style="text-align: center; margin-top: 15px">
              <el-button type="info" style="padding: 20px 30px" @click="reservationInit(item)">立即预约</el-button>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <el-dialog title="预约信息" v-model="data.formVisible" width="40%" destroy-on-close>
      <el-form ref="formRef" :rules="data.rules" :model="data.form" label-width="80px" style="padding: 20px">
        <el-form-item prop="timeRange" label="预约时间">
          <el-date-picker
              v-model="data.form.timeRange"
              type="datetimerange"
              range-separator="To"
              start-placeholder="请选择日期时间"
              end-placeholder="请选择日期时间"
              value-format="YYYY-MM-DD HH:mm:ss"
          />
        </el-form-item>
        <el-form-item prop="question" label="问题描述">
          <el-input type="textarea" :rows="4" v-model="data.form.question" placeholder="请输入问题描述"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="data.formVisible = false">取 消</el-button>
          <el-button type="primary" @click="submit">提 交</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";
const formRef = ref()
const data = reactive({
  doctorData: [],
  form: {},
  formVisible:false,
  rules: {
    timeRange: [
      { required: true, message: '请选择预约时间', trigger: 'blur' }
    ],
    question: [
      { required: true, message: '请选描述问题', trigger: 'blur' }
    ],
  }
})

const loadDoctor = () => {
  request.get('/doctor/selectAll', {
    params: {
      status: '审批通过'
    }
  }).then(res => {
    if (res.code === '200') {
      data.doctorData = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const reservationInit = (item) => {
  data.form = JSON.parse(JSON.stringify(item))
  data.formVisible = true
}
const submit = () => {
  formRef.value.validate(valid => {
    if (valid) {
      let reservationData = {
        doctorId: data.form.id,
        timeRange: data.form.timeRange,
        question: data.form.question
      }
      request.post('/reservation/add', reservationData).then(res => {
        if (res.code === '200') {
          ElMessage.success('操作成功，等待医生审核')
          data.formVisible = false
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })
}

loadDoctor()
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
</style>