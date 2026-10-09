# 心理健康预约平台

基于 Spring Boot + Vue 3 + uni-app 的心理健康咨询预约管理系统，支持用户在线预约心理咨询、心理测评、AI 智能对话等功能。

## 技术栈

| 模块 | 技术 |
|------|------|
| 后端 | Spring Boot 3.3.x + MyBatis + MySQL |
| 管理端前端 | Vue 3 + Element Plus + Vite |
| 用户端小程序 | uni-app（支持微信/支付宝小程序） |
| AI 功能 | DeepSeek API 智能对话 |
| 构建工具 | Maven + Vite + HBuilderX |

## 目录结构

```
mental-health-platform/
├── backend/              # Spring Boot 后端（含前端构建产物，可直接运行）
│   ├── src/
│   │   ├── main/java/com/example/   # 后端源码
│   │   │   ├── controller/          # 控制器
│   │   │   ├── service/             # 业务逻辑
│   │   │   ├── mapper/              # 数据访问
│   │   │   ├── entity/              # 实体类
│   │   │   └── common/              # 通用配置/工具
│   │   └── main/resources/
│   │       ├── mapper/              # MyBatis XML
│   │       ├── static/              # 前端构建产物（已打包）
│   │       ├── application.yml      # 应用配置（需自行创建）
│   │       └── application.yml.example  # 配置模板
│   ├── files/              # 用户上传文件目录（运行时生成）
│   └── pom.xml
├── frontend/             # Vue 3 管理端源码
│   ├── src/
│   ├── package.json
│   └── vite.config.js
├── miniprogram/          # uni-app 用户端小程序
│   ├── pages/
│   ├── api/
│   ├── store/
│   ├── package.json
│   └── manifest.json
├── database/             # 数据库脚本
│   └── mental_health.sql
└── docs/                 # 项目文档
```

## 环境要求

- JDK 17+（Spring Boot 3.x 要求）
- Maven 3.6+
- MySQL 5.7+ / 8.0+
- Node.js 16+（前端开发时需要）
- HBuilderX（小程序开发时需要）

## 快速开始

### 1. 导入数据库

```bash
mysql -u root -p < database/mental_health.sql
```

### 2. 配置后端

复制配置模板并修改：

```bash
cp backend/src/main/resources/application.yml.example \
   backend/src/main/resources/application.yml
```

修改 `application.yml` 中的：
- 数据库连接信息（用户名、密码）
- `fileBaseUrl`（部署到服务器后改为服务器地址）
- `deepseek.apiKey`（AI 对话功能，无需可留空）

### 3. 启动后端

```bash
cd backend
mvn spring-boot:run
```

启动后访问：http://localhost:9090

> 后端 `static` 目录已包含打包好的管理端前端，启动后可直接访问，无需单独启动前端。

### 4. 前端开发（可选）

如需修改管理端页面：

```bash
cd frontend
npm install
npm run dev
```

构建生产版本：

```bash
npm run build
# 将 dist 目录内容复制到 backend/src/main/resources/static/
```

### 5. 小程序配置

使用 HBuilderX 打开 `miniprogram` 目录，修改 `config.js` 中的 `baseUrl` 为你的后端地址，然后运行到微信开发者工具。

```js
// miniprogram/config.js
const config = {
    baseUrl: 'http://你的服务器IP:9090/mental/api', // 部署时修改
    url: 'http://你的服务器IP:9090'
}
```

## 部署前必须修改的配置清单

| 文件 | 配置项 | 说明 |
|------|--------|------|
| `backend/src/main/resources/application.yml` | `spring.datasource.password` | 数据库密码 |
| `backend/src/main/resources/application.yml` | `fileBaseUrl` | 部署后改为服务器地址 |
| `backend/src/main/resources/application.yml` | `deepseek.apiKey` | AI 对话 API Key（可选） |
| `frontend/.env.production` | `VITE_BASE_URL` | 前端生产环境 API 地址 |
| `miniprogram/config.js` | `baseUrl` / `url` | 小程序后端 API 地址 |

> 以上文件中，`application.yml` 已被 `.gitignore` 排除，不会上传到 GitHub；其余文件为默认占位地址，部署前需修改。

## 云服务器部署

### 后端部署

```bash
# 1. 打包
cd backend
mvn clean package -DskipTests

# 2. 上传 jar 包到服务器
# target/ 目录下生成的 jar 文件

# 3. 服务器上运行
nohup java -jar mental-health.jar --spring.config.location=application.yml &
```

### Nginx 配置参考

```nginx
server {
    listen 80;
    server_name your-domain.com;

    # 后端接口
    location /api/ {
        proxy_pass http://localhost:9090/;
        proxy_set_header Host $host;
    }

    # 用户上传文件
    location /files/ {
        alias /path/to/backend/files/;
    }
}
```

## 功能模块

- **用户管理**：注册、登录、个人信息维护
- **咨询师管理**：咨询师入驻、审核、信息维护
- **预约管理**：在线预约、预约确认、预约记录
- **心理测评**：测评题库、在线答题、结果记录
- **AI 对话**：基于 DeepSeek 的智能心理咨询对话
- **文件上传**：头像、图片等资源上传
- **管理后台**：数据统计、用户管理、内容管理

## 注意事项

1. `application.yml` 包含敏感信息，已加入 `.gitignore`，请从 `application.yml.example` 复制创建
2. `backend/files/` 是运行时用户上传目录，已加入 `.gitignore`
3. 小程序需要在微信公众平台配置合法域名
4. DeepSeek API Key 需自行申请，免费额度有限

## 许可证

本项目为学校综合设计课程项目，仅供学习参考。
