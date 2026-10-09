<template>
  <div>
    <!--  轮播图信息  -->
    <div style="margin-top: 10px">
      <el-carousel height="350px" interval="2000">
        <el-carousel-item v-for="item in data.sideshowData" :key="item.id">
          <img :src="item.img" alt="" style="height: 350px; cursor: pointer" @click="navTo('/front/propagateDetail?id=' + item.id)">
        </el-carousel-item>
      </el-carousel>
    </div>
    <!--  心理健康宣传  -->
    <div style="padding: 40px 0">
      <div style="width: 60%; margin: 0 auto; display: flex; align-items: center">
        <div style="font-size: 24px; width: 180px; font-weight: bold; color: #0b8bff">心理健康宣传</div>
        <div style="font-size: 18px; color: #8c939d; margin-left: 20px; flex: 1">心理健康宣传 必不可少</div>
        <div style="width: 120px; color: #666666; font-size: 15px; cursor: pointer" @click="navTo('/front/propagate')">更多健康宣传 >></div>
      </div>
      <div style="width: 60%; margin: 20px auto">
        <el-row :gutter="10">
          <el-col :span="16">
            <div class="card" v-for="item in data.propagateData" style="margin-bottom: 10px; display: flex; padding: 20px; cursor: pointer" @click="navTo('/front/propagateDetail?id=' + item.id)">
              <img :src="item.img" alt="" style="width: 180px; height: 120px">
              <div style="flex: 1; margin-left: 10px">
                <div style="font-size: 18px; font-weight: bold">{{ item.title }}</div>
                <div style="margin-top: 10px; color: #666666" class="line3">{{ item.content }}</div>
                <div style="margin-top: 12px; display: flex; align-items: center">
                  <img :src="item.doctorAvatar" alt="" style="width: 20px; height: 20px; border-radius: 50%">
                  <div style="margin-left: 5px">{{ item.doctorName }}</div>
                  <div style="margin-left: 30px">浏览数：{{ item.num }}</div>
                </div>
              </div>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="card">
              <div style="font-weight: bold; font-size: 22px; margin-top: 10px; margin-bottom: 18px; text-align: center">公告信息</div>
              <el-popover v-for="item in data.noticeData"
                  placement="top-start"
                  :title="item.title"
                  :width="400"
                  trigger="hover"
                  :content="item.content"
              >
                <template #reference>
                  <div style="margin-bottom: 20px">
                    <div style="font-weight: bold; font-size: 16px; color: #666666" class="line1">·【{{ item.title }}】</div>
                    <div style="font-size: 16px; color: #666666; margin-top: 9px; margin-left: 10px" class="line1">{{ item.content }}</div>
                  </div>
                </template>
              </el-popover>

            </div>
          </el-col>
        </el-row>
      </div>
    </div>
    <!--  心理测试信息  -->
    <div>
      <div style="width: 60%; margin: 0 auto; display: flex; align-items: center">
        <div style="font-size: 24px; width: 180px; font-weight: bold; color: #0b8bff">心理测试</div>
        <div style="font-size: 18px; color: #8c939d; margin-left: 20px; flex: 1">认识自己，了解自己</div>
        <div style="width: 120px; color: #666666; font-size: 15px; cursor: pointer" @click="navTo('/front/testPaper')">更多心理测试 >></div>
      </div>
      <div style="width: 60%; margin: 20px auto">
        <el-row :gutter="10">
          <el-col :span="6" v-for="item in data.testPaperData" style="margin-bottom: 10px">
            <div style="border-top-left-radius: 10px; border-top-right-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,.1); padding-bottom: 15px">
              <img :src="item.img" alt="" style="width: 100%; height: 200px; border-top-left-radius: 10px; border-top-right-radius: 10px">
              <div style="margin-top: 10px; font-size: 18px; padding: 0 10px">{{ item.title }}</div>
              <div style="margin-top: 10px; display: flex; align-items: center; padding: 0 10px">
                <div style="flex: 1">{{ item.testNum }} 人测试过</div>
                <div style="width: 100px; text-align: right"><el-button type="primary" @click="navTo('/front/testPaperDetail?id=' + item.id)">去测试</el-button></div>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
    <!--  心理医生信息  -->
    <div style="padding: 40px 0">
      <div style="width: 60%; margin: 0 auto; display: flex; align-items: center">
        <div style="font-size: 24px; width: 180px; font-weight: bold; color: #0b8bff">心理医生预约</div>
        <div style="font-size: 18px; color: #8c939d; margin-left: 20px; flex: 1">没有人是一座孤岛，每个人都需要心理咨询</div>
        <div style="width: 120px; color: #666666; font-size: 15px; cursor: pointer" @click="navTo('/front/doctor')">更多心理医生 >></div>
      </div>
      <div style="width: 60%; margin: 20px auto">
        <el-row :gutter="10">
          <el-col :span="6" v-for="item in data.doctorData">
            <div style="border-top-left-radius: 10px; border-top-right-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,.1); padding-bottom: 15px">
              <img :src="item.avatar" alt="" style="width: 100%; height: 250px; border-top-left-radius: 10px; border-top-right-radius: 10px">
              <div style="padding: 10px 15px">
                <div style="font-size: 16px; height: 30px; line-height: 30px">{{ item.name }}</div>
                <div style="font-size: 16px; height: 30px; line-height: 30px">累计帮助 {{ item.num }} 人</div>
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
  sideshowData: [],
  propagateData: [],
  noticeData: [],
  doctorData: [],
  testPaperData: [],

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

const loadTestPaper = () => {
  request.get('/testPaper/selectAllDesc').then(res => {
    if (res.code === '200') {
      data.testPaperData = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const loadSideshow = () => {
  request.get('/sideshow/selectAll').then(res => {
    if (res.code === '200') {
      data.sideshowData = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const loadPropagate = () => {
  request.get('/propagate/selectTop3').then(res => {
    if (res.code === '200') {
      data.propagateData = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const loadNotice = () => {
  request.get('/notice/selectAll').then(res => {
    if (res.code === '200') {
      data.noticeData = res.data
      if (data.noticeData.length > 6) {
        data.noticeData = data.noticeData.slice(0, 6)
      }
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const loadDoctor = () => {
  request.get('/doctor/top4').then(res => {
    if (res.code === '200') {
      data.doctorData = res.data
      if (data.doctorData.length > 4) {
        data.doctorData = data.doctorData.slice(0, 4)
      }
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const navTo = (url) => {
  location.href = url
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

loadSideshow()
loadPropagate()
loadNotice()
loadDoctor()
loadTestPaper()
</script>
<style scoped>
.line1 {
  overflow:hidden;
  text-overflow:ellipsis;
  white-space:nowrap;
}
.line3 {
  word-break: break-all;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3; /* 超出几行省略 */
  overflow: hidden;
}
</style>