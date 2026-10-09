<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input v-model="data.title" prefix-icon="Search" style="width: 240px; margin-right: 10px" placeholder="请输入题目名称查询"></el-input>
      <el-select
          v-model="data.typeName"
          placeholder="请选择心理分类"
          style="width: 240px; margin-right: 10px"
      >
        <el-option
            v-for="item in data.typeData"
            :key="item.id"
            :label="item.title"
            :value="item.title"
        />
      </el-select>
      <el-button type="info" plain @click="load">查询</el-button>
      <el-button type="warning" plain style="margin: 0 10px" @click="reset">重置</el-button>
    </div>
    <div class="card" style="margin-bottom: 5px" v-if="data.user.role === 'ADMIN'">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <el-table stripe :data="data.tableData" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" v-if="data.user.role === 'ADMIN'" />
        <el-table-column prop="title" label="题目名称" show-overflow-tooltip />
        <el-table-column prop="typeName" label="题目分类" />
        <el-table-column prop="aName" label="选项A" show-overflow-tooltip />
        <el-table-column prop="aScore" label="选项A分数" />
        <el-table-column prop="bName" label="选项B" show-overflow-tooltip />
        <el-table-column prop="bScore" label="选项B分数" />
        <el-table-column prop="cName" label="选项C" show-overflow-tooltip />
        <el-table-column prop="cScore" label="选项C分数" />
        <el-table-column prop="dName" label="选项D" show-overflow-tooltip />
        <el-table-column prop="dScore" label="选项D分数" />
        <el-table-column prop="score" label="选项最高分数" />
        <el-table-column label="操作" width="100" fixed="right" v-if="data.user.role === 'ADMIN'">
          <template v-slot="scope">
            <el-button type="primary" circle :icon="Edit" @click="handleEdit(scope.row)"></el-button>
            <el-button type="danger" circle :icon="Delete" @click="del(scope.row.id)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="card" v-if="data.total">
      <el-pagination @current-change="load" background layout="prev, pager, next" :page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total" />
    </div>

    <el-dialog title="题目信息" v-model="data.formVisible" width="40%" destroy-on-close>
      <el-form ref="formRef" :rules="data.rules" :model="data.form" label-width="80px" style="padding: 20px">
        <el-form-item prop="score" label="最高分数">每个选项最高 10 分</el-form-item>
        <el-form-item prop="title" label="题目名称">
          <el-input v-model="data.form.title" placeholder="请输入题目名称"></el-input>
        </el-form-item>
        <el-form-item prop="typeId" label="题目分类">
          <el-select
              v-model="data.form.typeId"
              placeholder="请选择心理分类"
              style="width: 100%"
          >
            <el-option
                v-for="item in data.typeData"
                :key="item.id"
                :label="item.title"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item prop="aName" label="选项A">
          <el-input v-model="data.form.aName" placeholder="请输入选项A内容"></el-input>
        </el-form-item>
        <el-form-item prop="aScore" label="A分数">
          <el-input-number v-model="data.form.aScore" :min="0" :max="10" placeholder="请输入选项A分数" style="width: 200px"/>
        </el-form-item>
        <el-form-item prop="bName" label="选项B">
          <el-input v-model="data.form.bName" placeholder="请输入选项B内容"></el-input>
        </el-form-item>
        <el-form-item prop="bScore" label="B分数">
          <el-input-number v-model="data.form.bScore" :min="0" :max="10" placeholder="请输入选项B分数" style="width: 200px"/>
        </el-form-item>
        <el-form-item prop="cName" label="选项C">
          <el-input v-model="data.form.cName" placeholder="请输入选项C内容"></el-input>
        </el-form-item>
        <el-form-item prop="cScore" label="C分数">
          <el-input-number v-model="data.form.cScore" :min="0" :max="10" placeholder="请输入选项C分数" style="width: 200px"/>
        </el-form-item>
        <el-form-item prop="dName" label="选项D">
          <el-input v-model="data.form.dName" placeholder="请输入选项D内容"></el-input>
        </el-form-item>
        <el-form-item prop="dScore" label="D分数">
          <el-input-number v-model="data.form.dScore" :min="0" :max="10" placeholder="请输入选项D分数" style="width: 200px"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="data.formVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
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
  user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
  formVisible: false,
  form: {},
  tableData: [],
  pageNum: 1,
  pageSize: 10,
  total: 0,
  title: null,
  typeName: null,
  ids: [],
  typeData: [],
  rules: {
    title: [
      { required: true, message: '请输入题目名称', trigger: 'blur' }
    ],
    typeId: [
      { required: true, message: '请选择心理分类', trigger: 'blur' }
    ],
    aName: [
      { required: true, message: '请输入A选项内容', trigger: 'blur' }
    ],
    bName: [
      { required: true, message: '请输入B选项内容', trigger: 'blur' }
    ],
    cName: [
      { required: true, message: '请输入C选项内容', trigger: 'blur' }
    ],
    dName: [
      { required: true, message: '请输入D选项内容', trigger: 'blur' }
    ],
    aScore: [
      { required: true, message: '请输入A选项分数', trigger: 'blur' }
    ],
    bScore: [
      { required: true, message: '请输入B选项分数', trigger: 'blur' }
    ],
    cScore: [
      { required: true, message: '请输入C选项分数', trigger: 'blur' }
    ],
    dScore: [
      { required: true, message: '请输入D选项分数', trigger: 'blur' }
    ],
  }
})

const loadType = () => {
  request.get('/type/selectAll').then(res => {
    if (res.code === '200') {
      data.typeData = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const load = () => {
  request.get('/topic/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      title: data.title,
      typeName: data.typeName,
    }
  }).then(res => {
    if (res.code === '200') {
      data.tableData = res.data?.list || []
      data.total = res.data?.total
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const handleAdd = () => {
  data.form = {}
  data.formVisible = true
}
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}
const add = () => {
  request.post('/topic/add', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('操作成功')
      data.formVisible = false
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const update = () => {
  request.put('/topic/update', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('操作成功')
      data.formVisible = false
      load()
    }
  })
}

const save = () => {
  formRef.value.validate(valid => {
    if (valid) {
      data.form.id ? update() : add()
    }
  })
}

const del = (id) => {
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗？', '删除确认', { type: 'warning' }).then(res => {
    request.delete('/topic/delete/' + id).then(res => {
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
    request.delete("/topic/delete/batch", {data: data.ids}).then(res => {
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
  data.title = null
  data.typeName = null
  load()
}

load()
loadType()
</script>