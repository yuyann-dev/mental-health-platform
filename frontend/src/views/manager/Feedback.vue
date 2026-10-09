<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input style="margin-right: 10px; width: 240px" v-model="data.question" placeholder="请输入反馈问题"></el-input>
      <el-select
          v-model="data.status"
          placeholder="请选择反馈状态"
          style="width: 240px; margin-right: 10px"
      >
        <el-option label="待回复" value="待回复"/>
        <el-option label="已回复" value="已回复"/>
      </el-select>
      <el-button type="info" plain @click="load">查询</el-button>
      <el-button type="warning" plain style="margin: 0 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <el-table stripe :data="data.tableData">
        <el-table-column prop="userName" label="用户姓名"/>
        <el-table-column prop="question" label="反馈问题" show-overflow-tooltip/>
        <el-table-column prop="content" label="用户想法" show-overflow-tooltip/>
        <el-table-column prop="time" label="提交时间" width="180"/>
        <el-table-column prop="status" label="回复状态">
          <template v-slot="scope">
            <el-tag v-if="scope.row.status === '待回复'" type="warning">{{ scope.row.status }}</el-tag>
            <el-tag v-if="scope.row.status === '已回复'" type="primary">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="replyName" label="回复人" show-overflow-tooltip/>
        <el-table-column prop="replyContent" label="回复内容" show-overflow-tooltip/>
        <el-table-column prop="replyTime" label="回复时间" width="180"/>
        <el-table-column label="操作" width="100" fixed="right">
          <template v-slot="scope">
            <el-button type="primary" circle :icon="Edit" @click="handleEdit(scope.row)" :disabled="scope.row.status === '已回复'"></el-button>
            <el-button type="danger" circle :icon="Delete" @click="del(scope.row.id)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="card" v-if="data.total">
      <el-pagination @current-change="load" background layout="prev, pager, next" :page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total" />
    </div>

    <el-dialog title="反馈回复" v-model="data.formVisible" width="40%" destroy-on-close>
      <el-form ref="formRef" :rules="data.rules" :model="data.form" label-width="80px" style="padding: 20px">
        <el-form-item prop="replyContent" label="回复内容">
          <el-input type="textarea" :rows="5" v-model="data.form.replyContent" placeholder="请输入回复内容"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="data.formVisible = false">取 消</el-button>
          <el-button type="primary" @click="update">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>

import {reactive, ref} from "vue";
import request from "@/utils/request.js";
import {ElMessage, ElMessageBox} from "element-plus";
import {Delete, Edit} from "@element-plus/icons-vue";

const formRef = ref()
const data = reactive({
  formVisible: false,
  form: {},
  tableData: [],
  pageNum: 1,
  pageSize: 10,
  total: 0,
  question: null,
  status: null,
  rules: {
    replyContent: [
      { required: true, message: '请输入回复内容', trigger: 'blur' }
    ],
  }
})

const load = () => {
  request.get('/feedback/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      question: data.question,
      status: data.status,
    }
  }).then(res => {
    if (res.code === '200') {
      data.tableData = res.data?.list || []
      data.total = res.data?.total
    }
  })
}
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}

const update = () => {
  formRef.value.validate(valid => {
    if (valid) {
      request.put('/feedback/update', data.form).then(res => {
        if (res.code === '200') {
          ElMessage.success('回复成功')
          data.formVisible = false
          load()
        }
      })
    }
  })
}

const del = (id) => {
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗？', '删除确认', { type: 'warning' }).then(res => {
    request.delete('/feedback/delete/' + id).then(res => {
      if (res.code === '200') {
        ElMessage.success("删除成功")
        load()
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => {
    console.error(err)
  })
}

const reset = () => {
  data.question = null
  data.status = null
  load()
}

load()
</script>