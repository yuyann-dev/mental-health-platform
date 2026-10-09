<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input v-model="data.title" prefix-icon="Search" style="width: 240px; margin-right: 10px" placeholder="请输入试卷名称查询"></el-input>
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
    <div class="card" style="margin-bottom: 5px">
      <el-button type="primary" plain @click="handleAdd(1)" v-if="data.user.role === 'DOCTOR'">手动出题</el-button>
      <el-button type="primary" plain @click="handleAdd(2)" v-if="data.user.role === 'DOCTOR'">自动出题</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <el-table stripe :data="data.tableData" @selection-change="handleSelectionChange" tooltip-effect="dark halfWidth">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="img" label="试卷封面">
          <template v-slot="scope">
            <el-image style="width: 80px; height: 80px; border-radius: 5px; display: block" v-if="scope.row.img"
                      :src="scope.row.img" :preview-src-list="[scope.row.img]" preview-teleported></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="title" label="试卷名称" />
        <el-table-column prop="content" label="试卷简介" show-overflow-tooltip/>
        <el-table-column prop="typeName" label="试卷分类" />
        <el-table-column prop="doctorName" label="医生姓名" />
        <el-table-column prop="doctorAvatar" label="医生头像">
          <template v-slot="scope">
            <el-image style="width: 40px; height: 40px; border-radius: 50%; display: block" v-if="scope.row.doctorAvatar"
                      :src="scope.row.doctorAvatar" :preview-src-list="[scope.row.doctorAvatar]" preview-teleported></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="num" label="题目数量" />
        <el-table-column prop="score" label="试卷总分" />
        <el-table-column prop="time" label="发布时间" />
        <el-table-column prop="testNum" label="测试人数" />
        <el-table-column prop="status" label="试卷状态">
          <template v-slot="scope">
            <el-tag type="warning" v-if="scope.row.status === '待审核'">{{ scope.row.status }}</el-tag>
            <el-tag type="success" v-if="scope.row.status === '审核通过'">{{ scope.row.status }}</el-tag>
            <el-tag type="danger" v-if="scope.row.status === '审核拒绝'">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="ids" label="题目信息" width="150">
          <template v-slot="scope">
            <el-button @click="viewTopics(scope.row.topicList)" type="primary">查询试卷题目</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100" fixed="right">
          <template v-slot="scope">
            <el-button type="primary" circle :icon="Edit" @click="handleEdit(scope.row)" v-if="data.user.role === 'ADMIN'"></el-button>
            <el-button type="danger" circle :icon="Delete" @click="del(scope.row.id)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="card" v-if="data.total">
      <el-pagination @current-change="load" background layout="prev, pager, next" :page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total" />
    </div>

    <el-dialog title="试卷信息" v-model="data.formVisible" width="50%" destroy-on-close>
      <el-form ref="form" :model="data.form" label-width="70px" style="padding: 20px">
        <el-form-item prop="title" label="试卷名称">
          <el-input v-model="data.form.title" placeholder="请输入试卷名称"></el-input>
        </el-form-item>
        <el-form-item prop="img" label="试卷封面">
          <el-upload
              :action="baseUrl + '/files/upload'"
              :on-success="handleFileUpload"
              list-type="picture"
          >
            <el-button type="primary">点击上传封面</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item prop="content" label="试卷介绍">
          <el-input type="textarea" :rows="4" v-model="data.form.content" placeholder="请输入试卷介绍"></el-input>
        </el-form-item>
        <el-form-item prop="typeId" label="试卷分类">
          <el-select v-model="data.form.typeId" placeholder="请选择心理分类" style="width: 100%" @change="loadTopic">
            <el-option v-for="item in data.typeData" :key="item.id" :label="item.title" :value="item.id"/>
          </el-select>
        </el-form-item>
        <el-form-item prop="idList" label="试卷题目" v-if="data.form.flag === 1">
          <el-select v-model="data.form.idList" multiple placeholder="请选择题目" style="width:100%" @change="calculateNum">
            <el-option v-for="item in data.topicData" :key="item.id" :label="item.title" :value="item.id"/>
          </el-select>
        </el-form-item>
        <el-form-item prop="num" label="题目数量" v-if="data.form.flag === 1">
          <el-input v-model="data.form.num" placeholder="0" disabled></el-input>
        </el-form-item>
        <el-form-item prop="num" label="题目数量" v-if="data.form.flag === 2">
          <el-input-number v-model="data.form.num" style="width: 200px" @change="calculateTotal"/>
        </el-form-item>
        <el-form-item prop="score" label="试卷总分">
          <el-input v-model="data.form.score" placeholder="0" disabled></el-input>
        </el-form-item>
        <el-form-item prop="aRange" label="底部区间">
          <el-input-number v-model="data.form.aLeft" style="width: 200px" disabled/>
          <span style="margin: 0 20px">至</span>
          <el-input-number v-model="data.form.aRight" style="width: 200px" @change="calculateBleft"/>
        </el-form-item>
        <el-form-item prop="aAnswer" label="底部解答">
          <el-input type="textarea" :rows="4" v-model="data.form.aAnswer" placeholder="请输入底部解答"></el-input>
        </el-form-item>
        <el-form-item prop="bRange" label="中部区间">
          <el-input-number v-model="data.form.bLeft" style="width: 200px" disabled/>
          <span style="margin: 0 20px">至</span>
          <el-input-number v-model="data.form.bRight" :max="data.bRightMax" style="width: 200px" @change="calculateCleft"/>
        </el-form-item>
        <el-form-item prop="bAnswer" label="中部解答">
          <el-input type="textarea" :rows="4" v-model="data.form.bAnswer" placeholder="请输入中部解答"></el-input>
        </el-form-item>
        <el-form-item prop="cRange" label="顶部区间">
          <el-input-number v-model="data.form.cLeft" style="width: 200px" disabled/>
          <span style="margin: 0 20px">至</span>
          <el-input-number v-model="data.form.cRight" style="width: 200px" disabled/>
        </el-form-item>
        <el-form-item prop="cAnswer" label="顶部解答">
          <el-input type="textarea" :rows="4" v-model="data.form.cAnswer" placeholder="请输入顶部解答"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="data.formVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </span>
      </template>
    </el-dialog>
    <el-dialog title="试卷题目信息" v-model="data.topicVisible" width="60%" destroy-on-close>
      <el-table stripe :data="data.topicList">
        <el-table-column prop="title" label="题目名称" width="300" show-overflow-tooltip />
        <el-table-column prop="aName" label="选项A" show-overflow-tooltip />
        <el-table-column prop="aScore" label="A分数" />
        <el-table-column prop="bName" label="选项B" show-overflow-tooltip />
        <el-table-column prop="bScore" label="B分数" />
        <el-table-column prop="cName" label="选项C" show-overflow-tooltip />
        <el-table-column prop="cScore" label="C分数" />
        <el-table-column prop="dName" label="选项D" show-overflow-tooltip />
        <el-table-column prop="dScore" label="D分数" />
      </el-table>
    </el-dialog>
    <el-dialog title="试卷审核" v-model="data.formVisible2" width="40%" destroy-on-close>
      <el-form ref="form" :model="data.form" label-width="70px" style="padding: 20px">
        <el-form-item prop="status" label="审核状态">
          <el-radio-group v-model="data.form.status">
            <el-radio-button label="待审核" value="待审核" />
            <el-radio-button label="审核通过" value="审核通过" />
            <el-radio-button label="审核拒绝" value="审核拒绝" />
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="data.formVisible2 = false">取 消</el-button>
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
const baseUrl = import.meta.env.VITE_BASE_URL

const data = reactive({
  user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
  formVisible: false,
  formVisible2: false,
  topicVisible: false,
  form: {},
  tableData: [],
  pageNum: 1,
  pageSize: 10,
  total: 0,
  title: null,
  ids: [],
  typeName: null,
  typeData: [],
  topicData: [],
  bRightMax: 0,
  topicList: [],
})
const viewTopics = (list) => {
  data.topicList = list
  data.topicVisible = true
}
const calculateTotal = (num) => {
  data.form.score = num * 10
  data.form.cRight = num * 10
  data.bRightMax = num * 10 - 1
}
const calculateBleft = (num) => {
  data.form.bLeft = num + 1
}
const calculateCleft = (num) => {
  data.form.cLeft = num + 1
}
const calculateNum = (arr) => {
  data.form.num = arr.length
  data.form.score = arr.length * 10
  data.form.cRight = arr.length * 10
  data.bRightMax = arr.length * 10 - 1
}
const loadTopic = (typeId) => {
  request.get('/topic/selectAll', {
    params: {
      typeId: typeId
    }
  }).then(res => {
    if (res.code === '200') {
      data.topicData = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
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
  request.get('/testPaper/selectPage', {
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
    }
  })
}
const handleAdd = (flag) => {
  data.form = {}
  data.form.flag = flag
  data.form.aLeft = 0
  data.formVisible = true
}
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible2 = true
}
const add = () => {
  request.post('/testPaper/add', data.form).then(res => {
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
  request.put('/testPaper/update', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('操作成功')
      data.formVisible = false
      data.formVisible2 = false
      load()
    }
  })
}

const save = () => {
  data.form.id ? update() : add()
}

const del = (id) => {
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗？', '删除确认', { type: 'warning' }).then(res => {
    request.delete('/testPaper/delete/' + id).then(res => {
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
    request.delete("/testPaper/delete/batch", {data: data.ids}).then(res => {
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
const handleFileUpload = (res) => {
  data.form.img = res.data
}

load()
loadType()
</script>
<style>
.halfWidth {
  width: 30%;
}
</style>