<template>
  <div style="min-height: 500px; width: 70%; margin: 20px auto">
    <div class="card" style="font-size: 20px">心理测试记录</div>
    <div class="card" style="margin-top: 10px">
      <el-input v-model="data.testPaperName" prefix-icon="Search" style="width: 240px; margin-right: 10px" placeholder="请输入试卷名称查询"></el-input>
      <el-button type="info" plain @click="load">查询</el-button>
      <el-button type="warning" plain style="margin: 0 10px" @click="reset">重置</el-button>
    </div>
    <div class="card" style="margin-top: 10px">
      <el-table stripe :data="data.tableData">
        <el-table-column prop="testPaperName" label="试卷名称">
          <template v-slot="scope">
            <a :href="'/front/testPaperDetail?id=' + scope.row.testPaperId" target="_blank">{{ scope.row.testPaperName }}</a>
          </template>
        </el-table-column>
        <el-table-column prop="userName" label="用户姓名" />
        <el-table-column prop="score" label="测试分数" />
        <el-table-column prop="result" label="测试结果" />
        <el-table-column prop="time" label="测试时间" />
        <el-table-column label="操作" width="100" fixed="right">
          <template v-slot="scope">
            <el-button type="danger" circle :icon="Delete" @click="del(scope.row.id)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="card" v-if="data.total" style="margin-top: 10px">
      <el-pagination @current-change="load" background layout="prev, pager, next" :page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total" />
    </div>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import request from "@/utils/request.js";
import {ElMessage, ElMessageBox} from "element-plus";
import {Delete, Edit} from "@element-plus/icons-vue";
const data = reactive({
  testPaperName: null,
  pageNum: 1,
  pageSize: 5,
  total: 0,
  tableData: [],
})

const load = () => {
  request.get('/testRecord/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      testPaperName: data.testPaperName
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

const del = (id) => {
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗？', '删除确认', { type: 'warning' }).then(res => {
    request.delete('/testRecord/delete/' + id).then(res => {
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
  data.testPaperName = null
  load()
}
load()
</script>

<style scoped>

</style>