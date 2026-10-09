# 即时通信模块接口文档

## 基础信息
- 基础路径: `/chat`
- 数据格式: `application/json`
- WebSocket路径: `ws://域名/chat/{userId}/{userType}`

## 数据结构

### ChatMessage（聊天消息）

| 字段名 | 类型 | 必填 | 描述 |
|-------|------|-----|------|
| id | Long | 是 | 主键ID |
| senderId | Long | 是 | 发送者ID |
| senderType | Integer | 是 | 发送者类型（1-患者，2-医生） |
| senderName | String | 是 | 发送者名称 |
| receiverId | Long | 是 | 接收者ID |
| receiverType | Integer | 是 | 接收者类型（1-患者，2-医生） |
| receiverName | String | 是 | 接收者名称 |
| content | String | 是 | 消息内容 |
| messageType | Integer | 是 | 消息类型（1-文本，2-图片，3-语音） |
| isRead | Integer | 是 | 是否已读（0-未读，1-已读） |
| createTime | DateTime | 是 | 创建时间 |

## WebSocket通信

### 1. 建立连接

```javascript
const ws = new WebSocket('ws://域名/chat/${userId}/${userType}');

ws.onopen = () => {
    console.log('连接成功');
};

ws.onmessage = (event) => {
    const message = JSON.parse(event.data);
    console.log('收到消息:', message);
};

ws.onclose = () => {
    console.log('连接关闭');
};

ws.onerror = (error) => {
    console.error('WebSocket错误:', error);
};
```

### 2. 发送消息

```javascript
const message = {
    senderId: 1,
    senderType: 1,
    senderName: "张三",
    receiverId: 2,
    receiverType: 2,
    receiverName: "李医生",
    content: "医生您好，我想咨询一下...",
    messageType: 1
};

ws.send(JSON.stringify(message));
```

## HTTP接口列表

### 1. 获取未读消息数

- **接口URL**: `/chat/unread`
- **请求方式**: `GET`
- **请求参数**:
  - userId: 用户ID
  - userType: 用户类型（1-患者，2-医生）
- **响应示例**:

```json
{
    "code": 200,
    "msg": "success",
    "data": 5
}
```

### 2. 标记消息为已读

- **接口URL**: `/chat/read`
- **请求方式**: `POST`
- **请求参数**:
  - userId: 用户ID
  - userType: 用户类型
  - senderId: 发送者ID
  - senderType: 发送者类型
- **响应示例**:

```json
{
    "code": 200,
    "msg": "success"
}
```

### 3. 获取聊天历史记录

- **接口URL**: `/chat/history`
- **请求方式**: `GET`
- **请求参数**:
  - userId: 用户ID
  - userType: 用户类型
  - targetId: 目标用户ID
  - targetType: 目标用户类型
  - pageNum: 页码（默认1）
  - pageSize: 每页大小（默认20）
- **响应示例**:

```json
{
    "code": 200,
    "msg": "success",
    "data": {
        "total": 100,
        "list": [
            {
                "id": 1,
                "senderId": 1,
                "senderType": 1,
                "senderName": "张三",
                "receiverId": 2,
                "receiverType": 2,
                "receiverName": "李医生",
                "content": "医生您好，我想咨询一下...",
                "messageType": 1,
                "isRead": 1,
                "createTime": "2024-03-20 10:00:00"
            }
        ],
        "pageNum": 1,
        "pageSize": 20,
        "pages": 5
    }
}
```

### 4. 获取最近聊天列表

- **接口URL**: `/chat/recent`
- **请求方式**: `GET`
- **请求参数**:
  - userId: 用户ID
  - userType: 用户类型
- **响应示例**:

```json
{
    "code": 200,
    "msg": "success",
    "data": [
        {
            "id": 1,
            "senderId": 1,
            "senderType": 1,
            "senderName": "张三",
            "receiverId": 2,
            "receiverType": 2,
            "receiverName": "李医生",
            "content": "医生您好，我想咨询一下...",
            "messageType": 1,
            "isRead": 1,
            "createTime": "2024-03-20 10:00:00"
        }
    ]
}
```

## 错误码说明

| 错误码 | 说明 |
|-------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未授权 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

## 注意事项

1. WebSocket连接需要在Header中携带token进行身份验证
2. 消息发送失败会通过WebSocket返回错误信息
3. 图片和语音消息需要先上传到文件服务器，然后在content中携带文件URL
4. 建议使用心跳机制维护WebSocket连接，如果断开需要自动重连
5. 移动端需要处理网络切换和应用前后台切换时的WebSocket重连
6. 时间格式统一使用：`yyyy-MM-dd HH:mm:ss` 