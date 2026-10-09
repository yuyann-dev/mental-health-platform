# 心理健康预约平台

> 基于 Spring Boot + Vue 3 + uni-app 的全栈心理健康咨询预约管理系统

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.1-brightgreen)](https://spring.io/projects/spring-boot)
[![Vue](https://img.shields.io/badge/Vue-3.4-blue)](https://vuejs.org/)
[![uni-app](https://img.shields.io/badge/uni--app-3.x-orange)](https://uniapp.dcloud.net.cn/)
[![MySQL](https://img.shields.io/badge/MySQL-5.7%2B-lightblue)](https://www.mysql.com/)

## 项目简介

面向心理健康服务场景的全栈管理系统，包含用户端小程序、管理端 Web 页面和后端服务。支持在线预约咨询师、心理测评、在线咨询、题库管理、数据统计等功能。

## 技术栈

| 层级 | 技术 |
|------|------|
| 后端 | Spring Boot 3.3 + MyBatis + MySQL + JWT |
| 管理端 | Vue 3 + Element Plus + ECharts + Vite |
| 用户端 | uni-app（微信小程序） |
| 工具 | Maven + Hutool + PageHelper |

## 功能模块

**用户端（小程序）**：首页资讯、心理测评、咨询师预约、在线咨询、康复知识、个人中心

**管理端（Web）**：数据看板、用户管理、咨询师审核、预约管理、题库维护、试卷管理、测评记录、文章管理

## 目录结构

```
mental-health-platform/
├── backend/          # Spring Boot 后端（含已打包前端，启动即可访问）
├── frontend/         # Vue 3 管理端源码
├── miniprogram/      # uni-app 小程序源码
├── database/         # MySQL 初始化脚本
└── README.md
```

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 5.7+ / 8.0+
- Node.js 16+（前端开发时需要）

### 启动步骤

1. **导入数据库**

```sql
CREATE DATABASE mental_health DEFAULT CHARACTER SET utf8mb4;
```

```bash
mysql -u root -p mental_health < database/mental_health.sql
```

2. **配置数据库连接**

编辑 `backend/src/main/resources/application.yml`，填入你的 MySQL 密码。

3. **启动后端**

```bash
cd backend
mvn spring-boot:run
```

访问 http://localhost:9090 即可打开管理端。

### 前端开发（可选）

```bash
cd frontend
npm install
npm run dev
```

修改完成后执行 `npm run build`，将 `dist` 目录内容复制到 `backend/src/main/resources/static/`。

### 小程序（可选）

使用 HBuilderX 打开 `miniprogram` 目录，修改 `config.js` 中的后端地址，运行到微信开发者工具。

## 部署

```bash
cd backend
mvn clean package -DskipTests
```

将 `target/` 下生成的 jar 包和 `application.yml` 上传到服务器，执行：

```bash
nohup java -jar springboot-0.0.1-SNAPSHOT.jar &
```

建议配合 Nginx 做反向代理和 HTTPS。

## 数据库

共 10 张表：admin、user、doctor、reservation、test_paper、test_record、topic、type、advice、answer_record。

初始化脚本位于 `database/mental_health.sql`，含建表语句和初始数据。

## API 文档

接口文档见 `backend/docs/api.md`。

- 基础地址：`http://localhost:9090`
- 认证方式：请求头 `Authorization: Bearer {token}`
- 统一响应：`{ code, msg, data }`

## 许可证

本项目为学校综合设计课程项目，仅供学习交流使用。
