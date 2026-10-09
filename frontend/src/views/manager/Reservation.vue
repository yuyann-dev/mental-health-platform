<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-select
          v-model="data.status"
          placeholder="请选择预约状态"
          style="width: 240px; margin-right: 10px"
      >
        <el-option label="待审核" value="待审核"/>
        <el-option label="审核通过" value="审核通过"/>
        <el-option label="审核拒绝" value="审核拒绝"/>
        <el-option label="已结束" value="已结束"/>
      </el-select>
      <el-button type="info" plain @click="load">查询</el-button>
      <el-button type="warning" plain style="margin: 0 10px" @click="reset">重置</el-button>
    </div>
    <div class="card" style="margin-bottom: 5px" v-if="data.user.role === 'ADMIN'">
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <el-table stripe :data="data.tableData" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" v-if="data.user.role === 'ADMIN'"/>
        <el-table-column prop="userName" label="用户姓名" />
        <el-table-column prop="doctorName" label="医生姓名" v-if="data.user.role === 'ADMIN'"/>
        <el-table-column prop="start" label="开始时间" width="180"/>
        <el-table-column prop="end" label="结束时间" width="180"/>
        <el-table-column prop="time" label="预约时间" width="180"/>
        <el-table-column prop="question" label="问题描述" show-overflow-tooltip/>
        <el-table-column prop="status" label="预约状态">
          <template v-slot="scope">
            <el-tag v-if="scope.row.status === '待审核'" type="warning">{{ scope.row.status }}</el-tag>
            <el-tag v-if="scope.row.status === '审核通过'" type="success">{{ scope.row.status }}</el-tag>
            <el-tag v-if="scope.row.status === '审核拒绝'" type="danger">{{ scope.row.status }}</el-tag>
            <el-tag v-if="scope.row.status === '已结束'" type="primary">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="reason" label="拒绝理由" show-overflow-tooltip/>
        <el-table-column label="操作" width="100" fixed="right">
          <template v-slot="scope">
            <el-button type="primary" circle :icon="Edit" @click="handleEdit(scope.row)" v-if="data.user.role === 'DOCTOR'" :disabled="scope.row.status === '审核拒绝' || scope.row.status === '已结束'"></el-button>
            <el-button type="danger" circle :icon="Delete" @click="del(scope.row.id)" v-if="data.user.role === 'ADMIN'"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="card" v-if="data.total">
      <el-pagination @current-change="load" background layout="prev, pager, next" :page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total" />
    </div>

    <el-dialog title="预约审核" v-model="data.formVisible" width="40%" destroy-on-close>
      <el-form ref="form" :model="data.form" label-width="70px" style="padding: 20px">
        <el-form-item prop="status" label="审核状态">
          <el-radio-group v-model="data.form.status">
            <el-radio-button label="待审核" value="待审核" />
            <el-radio-button label="审核通过" value="审核通过" />
            <el-radio-button label="审核拒绝" value="审核拒绝" />
            <el-radio-button label="已结束" value="已结束" />
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="reason" label="拒绝理由" v-if="data.form.status === '审核拒绝'">
          <el-input type="textarea" :rows="4" v-model="data.form.reason" placeholder="请输入拒绝理由"></el-input>
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

import {reactive} from "vue";
import request from "@/utils/request.js";
import {ElMessage, ElMessageBox} from "element-plus";
import {Delete, Edit} from "@element-plus/icons-vue";


const data = reactive({
  user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
  formVisible: false,
  form: {},
  tableData: [],
  pageNum: 1,
  pageSize: 10,
  total: 0,
  status: null,
  ids: []
})

const load = () => {
  request.get('/reservation/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      status: data.status
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
  request.put('/reservation/update', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('操作成功')
      data.formVisible = false
      load()
    }
  })
}

const del = (id) => {
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗？', '删除确认', { type: 'warning' }).then(res => {
    request.delete('/reservation/delete/' + id).then(res => {
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
const delBatch = () => {
  if (!data.ids.length) {
    ElMessage.warning("请选择数据")
    return
  }
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗？', '删除确认', { type: 'warning' }).then(res => {
    request.delete("/reservation/delete/batch", {data: data.ids}).then(res => {
      if (res.code === '200') {
        ElMessage.success('操作成功')
        load()
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => {
    console.error(err)
  })
}
const handleSelectionChange = (rows) => {
  data.ids = rows.map(v => v.id)
}

const reset = () => {
  data.status = null
  load()
}

load()
</script>