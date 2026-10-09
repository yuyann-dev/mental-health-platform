# 心理测评系统接口文档

## 题目管理接口

### 1. 根据ID查询题目
- **接口**: `/topic/findById`
- **方法**: GET
- **参数**: 
  ```json
  {
    "id": "Integer, 题目ID"
  }
  ```
- **返回值**:
  ```json
  {
    "id": "Integer, 题目ID",
    "title": "String, 题目标题",
    "typeId": "Integer, 题目类型ID",
    "aName": "String, A选项内容",
    "aScore": "Integer, A选项分数",
    "bName": "String, B选项内容",
    "bScore": "Integer, B选项分数",
    "cName": "String, C选项内容",
    "cScore": "Integer, C选项分数",
    "dName": "String, D选项内容",
    "dScore": "Integer, D选项分数",
    "score": "Integer, 题目总分"
  }
  ```

### 2. 根据类型ID查询题目列表
- **接口**: `/topic/findByTypeId`
- **方法**: GET
- **参数**:
  ```json
  {
    "typeId": "Integer, 题目类型ID"
  }
  ```
- **返回值**:
  ```json
  [
    {
      "id": "Integer, 题目ID",
      "title": "String, 题目标题",
      "typeId": "Integer, 题目类型ID",
      "aName": "String, A选项内容",
      "aScore": "Integer, A选项分数",
      "bName": "String, B选项内容",
      "bScore": "Integer, B选项分数",
      "cName": "String, C选项内容",
      "cScore": "Integer, C选项分数",
      "dName": "String, D选项内容",
      "dScore": "Integer, D选项分数",
      "score": "Integer, 题目总分"
    }
  ]
  ```

### 3. 新增题目
- **接口**: `/topic/insert`
- **方法**: POST
- **参数**:
  ```json
  {
    "title": "String, 题目标题",
    "typeId": "Integer, 题目类型ID",
    "aName": "String, A选项内容",
    "aScore": "Integer, A选项分数",
    "bName": "String, B选项内容",
    "bScore": "Integer, B选项分数",
    "cName": "String, C选项内容",
    "cScore": "Integer, C选项分数",
    "dName": "String, D选项内容",
    "dScore": "Integer, D选项分数",
    "score": "Integer, 题目总分"
  }
  ```
- **返回值**: 成功返回true，失败返回false

### 4. 更新题目
- **接口**: `/topic/update`
- **方法**: PUT
- **参数**:
  ```json
  {
    "id": "Integer, 题目ID",
    "title": "String, 题目标题",
    "typeId": "Integer, 题目类型ID",
    "aName": "String, A选项内容",
    "aScore": "Integer, A选项分数",
    "bName": "String, B选项内容",
    "bScore": "Integer, B选项分数",
    "cName": "String, C选项内容",
    "cScore": "Integer, C选项分数",
    "dName": "String, D选项内容",
    "dScore": "Integer, D选项分数",
    "score": "Integer, 题目总分"
  }
  ```
- **返回值**: 成功返回true，失败返回false

### 5. 删除题目
- **接口**: `/topic/deleteById`
- **方法**: DELETE
- **参数**:
  ```json
  {
    "id": "Integer, 题目ID"
  }
  ```
- **返回值**: 成功返回true，失败返回false

### 6. 查询题目列表
- **接口**: `/topic/selectAll`
- **方法**: GET
- **参数**:
  ```json
  {
    "typeId": "Integer, 可选，题目类型ID",
    "title": "String, 可选，题目标题模糊搜索",
    "typeName": "String, 可选，题目类型名称"
  }
  ```
- **返回值**:
  ```json
  [
    {
      "id": "Integer, 题目ID",
      "title": "String, 题目标题",
      "typeId": "Integer, 题目类型ID",
      "typeName": "String, 题目类型名称",
      "aName": "String, A选项内容",
      "aScore": "Integer, A选项分数",
      "bName": "String, B选项内容",
      "bScore": "Integer, B选项分数",
      "cName": "String, C选项内容",
      "cScore": "Integer, C选项分数",
      "dName": "String, D选项内容",
      "dScore": "Integer, D选项分数",
      "score": "Integer, 题目总分"
    }
  ]
  ```

## 注意事项
1. 所有接口的响应格式统一为：
   ```json
   {
     "code": "String, 状态码，200表示成功",
     "msg": "String, 响应信息",
     "data": "Object, 响应数据"
   }
   ```

2. 错误码说明：
   - 200: 成功
   - 400: 请求参数错误
   - 401: 未授权
   - 403: 禁止访问
   - 500: 服务器内部错误

3. 认证方式：
   - 需要在请求头中添加 token
   - 格式：`Authorization: Bearer {token}`

4. 分页参数：
   - 支持在查询列表接口中添加分页参数
   - pageNum: 页码，从1开始
   - pageSize: 每页数量
   ```json
   {
     "pageNum": "Integer, 当前页码",
     "pageSize": "Integer, 每页数量",
     // 其他查询参数
   }
   ``` 