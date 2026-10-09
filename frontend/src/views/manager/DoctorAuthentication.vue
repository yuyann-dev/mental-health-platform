<template>
  <div style="width: 50%" class="card">
    <div style="color: red">一旦认证信息修改，医生状态就会变为待审批状态，管理员审批通过前将无法使用医生功能</div>
    <el-form ref="userRef" :rules="data.rules" :model="data.user" label-width="95px" style="padding: 20px">
      <el-form-item prop="avatar" label="医生头像">
        <el-upload
            :action="baseUrl + '/files/upload'"
            :on-success="handleFileUpload"
            :show-file-list="false"
            class="avatar-uploader"
        >
          <img v-if="data.user.avatar" :src="data.user.avatar" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item prop="certificate" label="医师资格证">
        <el-upload
            :action="baseUrl + '/files/upload'"
            :on-success="handleCertificateUpload"
            :show-file-list="false"
            class="avatar-uploader"
        >
          <img v-if="data.user.certificate" :src="data.user.certificate" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item prop="name" label="真实姓名">
        <el-input v-model="data.user.name" placeholder="请输入真实姓名"></el-input>
      </el-form-item>
      <el-form-item prop="code" label="身份证号">
        <el-input v-model="data.user.code" placeholder="请输入身份证号"></el-input>
      </el-form-item>
      <el-form-item prop="content" label="医生简介">
        <el-input type="textarea" :rows="5" v-model="data.user.content" placeholder="请输入医生简介"></el-input>
      </el-form-item>
      <el-form-item prop="seniority" label="医生工龄">
        <el-input-number v-model="data.user.seniority" :min="1" placeholder="医生工龄" />
      </el-form-item>
      <el-form-item prop="status" label="审批状态">
        <el-tag v-if="data.user.status === '待审批'" type="warning">{{ data.user.status }}</el-tag>
        <el-tag v-if="data.user.status === '审批通过'" type="success">{{ data.user.status }}</el-tag>
        <el-tag v-if="data.user.status === '审批拒绝'" type="danger">{{ data.user.status }}</el-tag>
      </el-form-item>
      <div style="text-align: center">
        <el-button type="primary" @click="update">提 交</el-button>
      </div>
    </el-form>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";

const baseUrl = import.meta.env.VITE_BASE_URL
const userRef = ref()
const data = reactive({
  user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
  rules: {
    avatar: [
      {required: true, message: '请上传医生头像', trigger: 'blur'},
    ],
    certificate: [
      {required: true, message: '请上传医师资格证', trigger: 'blur'},
    ],
    name: [
      {required: true, message: '请填写真实姓名', trigger: 'blur'},
    ],
    code: [
      {required: true, message: '请填写身份证号', trigger: 'blur'},
    ],
    content: [
      {required: true, message: '请填写医生简介', trigger: 'blur'},
    ],
    seniority: [
      {required: true, message: '请填写医生工龄', trigger: 'blur'},
    ],
  }
})

const handleFileUpload = (res) => {
  data.user.avatar = res.data
}
const handleCertificateUpload = (res) => {
  data.user.certificate = res.data
}

const emit = defineEmits(['updateUser'])
const update = () => {
  if (data.user.role === 'DOCTOR') {
    userRef.value.validate(valid => {
      if (valid) {
        request.put('/doctor/submit', data.user).then(res => {
          if (res.code === '200') {
            ElMessage.success('提交成功，等待管理员审批')
          } else {
            ElMessage.error(res.msg)
          }
        })
      }
    })

  }
}
</script>

<style>
.avatar-uploader {
  height: 120px;
}
.avatar-uploader .avatar {
  width: 120px;
  height: 120px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  text-align: center;
}
</style>