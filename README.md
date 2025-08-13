# 在线聊天社交平台（Spring Boot + Netty + AI）

一个基于 Spring Boot 3.4.2、MySQL、Netty 的综合性在线社交与实时聊天平台，集成活动管理、私聊/群聊、AI 对话、举报系统与维修服务等模块。

## 主要功能
- 用户系统：注册/登录（含微信小程序登录）、头像上传、关注/好友管理、在线状态
- 活动系统：活动创建/审核/报名/取消、热门与分页查询、评论与举报
- 聊天系统：私聊与群聊（WebSocket），消息持久化，多端在线
- AI 助手：DeepSeek 对话、视觉模型图片分析、会话与日志管理、流式响应
- 举报与管理员：举报类型/处理、用户/活动/群组/公告/维修的后台管理
- 维修服务：维修请求、任务分配、技师处理、服务评价

## 技术栈
- 后端：Spring Boot 3.4.2、Spring Security、MyBatis-Plus、Jackson、Lombok
- 网络：Netty 4.1.x（WebSocket）
- 数据库：MySQL 8 + Druid 连接池
- 认证授权：JWT（JJWT）
- 云存储：阿里云 OSS + STS 临时凭证
- AI：DeepSeek（对话）、DashScope/Qwen（视觉）

## 环境准备
- Java 17+
- Maven 3.6+
- MySQL 8.0+

可选：Redis（如果后续采用缓存）

## 数据库初始化
1) 创建数据库（utf8mb4）：
```sql
CREATE DATABASE online_chat CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```
2) 导入表结构：
- 项目根目录提供了 activity.sql 与 SQL.txt，请按需在数据库中执行。

## 配置说明（application.yml）
示例（请根据实际环境修改）：
```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/online_chat?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8
    username: root
    password: YOUR_DB_PASSWORD

server:
  port: 721
  address: 0.0.0.0
```

### 环境变量(未配置会导致部分服务不可用)
- AI（对话/视觉）：
  - DEEPSEEK_API=你的DeepSeek_API_Key
  - （可选）DEEPSEEK_API_URL=DeepSeek_API_URL
  - （可选）DEEPSEEK_MODEL=使用的模型名称
  - DASHSCOPE_API=你的DashScope_API_Key
  - （可选）DASHSCOPE_API_URL=视觉API_URL
  - （可选）VL_MODEL=视觉模型名称
- 阿里云 OSS：
  - ALIYUN_ACCESSKEY_ID=你的AccessKeyId
  - ALIYUN_ACCESSKEY_SECRET=你的AccessKeySecret
  - ALIYUN_OSS_STS_ROLE_ARN=你的STS角色ARN

> 注：实际键名与是否必填以 application.yml、AIConfig、CommonController 中的 @Value 注入为准。


## 启动与验证
```bash
# 编译
mvn clean compile

# 运行（开发）
mvn spring-boot:run

# 或打包后运行
mvn clean package
java -jar target/online_chat-0.0.1-SNAPSHOT.jar
```
验证：
- REST API: http://localhost:721/
- WebSocket: ws://localhost:9000/ws
- 测试接口示例: http://localhost:721/api/test/a

WebSocket 认证：通过 Sec-WebSocket-Protocol 头传递 JWT（子协议中携带 Token）。

## 模块与接口概览
- /api/user/**：用户注册/登录/资料/头像、好友与关注、在线状态、公告、AI 会话历史
- /api/activity/**：活动创建/审核/报名/取消、详情与分页、热门活动、评论
- /api/group/**：群组创建（关联活动）、加入/退出、成员管理、群信息更新
- /api/ai/**：AI 对话（流式、带活动上下文与图像）、图片分析
- /api/report/**：举报类型、提交举报、分页查询与处理
- /api/repair/**：维修请求提交/查询、任务处理、服务评价
- /api/admin/**：管理员对用户/活动/群组/公告/维修/举报的综合管理

## 安全与权限
- 采用 JWT 进行无状态认证。
- Spring Security 已配置公共路径与角色权限（管理员接口需要 ROLE_ADMIN）。
- CSRF 关闭，已开启全局 CORS（支持前后端分离场景）。

## 常见问题排查
1) 数据库连接失败：确认 MySQL 服务、地址端口、账号密码与驱动；字符集建议 utf8mb4。
2) WebSocket 连接失败：确认 9000 端口可访问、防火墙放行、握手路径为 /ws，且 Token 以子协议传递。
3) 文件/OSS 上传失败：检查本地目录读写权限、OSS AK/SK、STS 角色 ARN、桶与区域设置是否正确。
4) AI 调用异常：检查 API Key、模型名与 API 地址是否正确，网络是否可访问对应服务。

## 许可证
仅供学习与内部交流使用.
