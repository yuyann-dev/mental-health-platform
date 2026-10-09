<template>
  <div style="width: 70%; margin: 20px auto; min-height: 600px">
    <div style="font-size: 20px" class="card">我的反馈</div>
    <div class="card" style="margin-top: 10px">
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
    <div class="card" style="margin: 10px 0">
      <el-table stripe :data="data.tableData">
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
            <el-button type="danger" circle :icon="Delete" @click="del(scope.row.id)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="card" v-if="data.total">
      <el-pagination @current-change="load" background layout="total, prev, pager, next" :page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total" />
    </div>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import request from "@/utils/request.js";
import {ElMessage, ElMessageBox} from "element-plus";
import router from "@/router/index.js";
import {Delete, Edit} from "@element-plus/icons-vue";

const data = reactive({
  status: null,
  question: null,
  pageNum: 1,
  pageSize: 5,
  total: 0,
  tableData: []
})

const load = () => {
  request.get('/feedback/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      status: data.status,
      question: data.question
    }
  }).then(res => {
    if (res.code === '200') {
      data.tableData = res.data.list
      data.total = res.data.total
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const reset = () => {
  data.question = null
  data.status = null
  load()
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
load()
</script>

<style scoped>
</style>