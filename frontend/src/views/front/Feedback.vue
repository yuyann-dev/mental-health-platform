<template>
  <div style="width: 50%; margin: 20px auto; min-height: 600px">
    <div class="card" style="padding: 20px">
      <div style="font-size: 24px; text-align: center">提交您的反馈信息</div>
      <div style="margin-top: 30px">
        <el-form ref="formRef" :rules="data.rules" :model="data.form" label-width="80px">
          <el-form-item label="反馈问题" prop="question">
            <el-input type="textarea" :rows="5" maxlength="100" show-word-limit v-model="data.form.question" placeholder="请输入您反馈的问题"></el-input>
          </el-form-item>
          <el-form-item label="我的想法" prop="content">
            <el-input type="textarea" :rows="5" maxlength="300" show-word-limit v-model="data.form.content" placeholder="请输入您对上述问题的想法"></el-input>
          </el-form-item>
          <div style="text-align: center">
            <el-button type="primary" @click="submit" style="padding: 23px 45px; font-size: 15px">提 交</el-button>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";
const formRef = ref()
const data = reactive({
  form: {},
  rules: {
    question: [
      { required: true, message: '请输入您反馈的问题', trigger: 'blur' }
    ],
    content: [
      { required: true, message: '请输入您对反馈问题的想法', trigger: 'blur' }
    ],
  }
})

const submit = () => {
  formRef.value.validate(valid => {
    if (valid) {
      request.post('/feedback/add', data.form).then(res => {
        if (res.code === '200') {
          ElMessage.success('反馈成功，等待管理员回复，您可以在右上角“我的反馈”查看具体内容')
          data.form = {}
        }
      })
    }
  })
}

</script>

<style scoped>

</style>