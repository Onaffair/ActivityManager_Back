在线聊天社交平台 - 项目配置和功能指南
项目概述
这是一个基于 Spring Boot 3.4.2 + MySQL + Netty + AI 的综合性在线社交平台，整合了活动管理、实时聊天、AI 对话、举报系统、维修服务等多个功能模块。

环境要求
Java 17+
MySQL 8.0+
Maven 3.6+
Redis（可选，用于缓存）
数据库配置
1. 创建数据库
SQL



CREATE DATABASE online_chat CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
2. 导入数据结构
项目根目录下包含以下 SQL 文件：

activity.sql - 活动相关表结构
SQL.txt - 其他表结构
应用配置文件 (application.yml)
必需配置项
YAML



spring:  datasource:    driver-class-name: com.mysql.cj.jdbc.Driver    url: jdbc:mysql://localhost:3306/online_chat?    useUnicode=true&characterEncoding=utf8&useSSL=false&    serverTimezone=GMT%2B8    username: root    password: YOUR_DATABASE_PASSWORD  # 修改为你的数据库密码server:  port: 721  # 可修改为你希望的端口  address: 0.0.0.0
环境变量配置
项目需要配置以下环境变量，建议在 IDE 或系统环境变量中设置：

微信小程序配置
Bash



运行
WX_APPID=你的微信小程序AppIdWX_SECRET=你的微信小程序Secret
AI API 配置
Bash



运行
# DeepSeek AI API（用于智能对话）DEEPSEEK_API=你的DeepSeek_API_Key# 视觉AI API（用于图片分析）DASHSCOPE_API=你的DashScope_API_Key
阿里云OSS配置（文件存储）
Bash



运行
ALIYUN_ACCESSKEY_ID=你的阿里云AccessKey_IDALIYUN_ACCESSKEY_SECRET=你的阿里云AccessKey_SecretALIYUN_OSS_STS_ROLE_ARN=你的OSS_STS角色ARN
可选配置修改
文件上传路径配置
在 WebConfig.java 中修改：

Java



// 修改为你的文件存储路径registry.addResourceHandler("/static/image/**")        .addResourceLocations("file:YOUR_IMAGE_STORAGE_PATH");
在 UserController.java 中修改：

Java



// 修改头像上传路径private final static String UPLOAD_IMAGE_PATH = "YOUR_IMAGE_STORAGE_PATH";
启动步骤
1. 配置数据库
启动 MySQL 服务
创建数据库并导入表结构
修改 application.yml 中的数据库连接配置
2. 配置环境变量
设置所需的环境变量（见上方配置）
或在 application.yml 中直接替换 ${变量名:} 为实际值
3. 编译运行
Bash



运行
# 使用 Maven 编译mvn clean compile# 启动项目mvn spring-boot:run# 或者打包后运行mvn clean packagejava -jar target/online_chat-0.0.1-SNAPSHOT.jar
4. 验证启动
REST API 服务：http://localhost:721
WebSocket 聊天服务：ws://localhost:9000/ws
测试接口：http://localhost:721/api/test/a
主要功能模块
1. 用户管理系统
用户注册/登录：支持账号密码和微信小程序登录
用户信息管理：头像上传、个人信息编辑
好友系统：添加好友、好友请求处理
关注系统：用户关注/取消关注功能
2. 活动管理系统
活动发布：用户可创建各类活动（需管理员审核）
活动报名：支持活动报名、取消报名、人数限制
活动分类：按城市、类别筛选活动
活动评论：活动结束后评价功能
活动搜索：关键词搜索活动
3. 实时聊天系统
私聊功能：好友间一对一聊天
群聊功能：活动群组聊天
消息持久化：聊天记录数据库存储
在线状态：用户在线/离线状态管理
多设备支持：同一用户多设备同时在线
4. AI 智能助手
智能对话：基于 DeepSeek API 的 AI 聊天
图片分析：上传图片进行 AI 分析
会话管理：AI 对话历史记录
流式响应：Server-Sent Events 实现实时响应
5. 举报管理系统
活动举报：用户可举报不当活动
举报分类：多种举报类型选择
管理员处理：管理员审核和处理举报
6. 维修服务系统
维修请求：用户提交维修需求
任务分配：管理员分配维修任务给技术员
服务评价：维修完成后的服务评价
状态追踪：维修请求状态实时更新
7. 公告系统
公告发布：管理员发布系统公告
公告管理：公告的增删改查
用户查看：用户查看最新公告
8. 管理员功能
用户管理：查看、编辑、封禁用户
活动审核：审核用户发布的活动
举报处理：处理用户举报
群组管理：管理活动群组和成员
维修管理：维修任务分配和管理
9. 文件存储系统
阿里云 OSS：图片和文件云存储
STS 临时授权：安全的文件上传机制
本地存储：头像等小文件本地存储支持
API 接口说明
主要接口分类：
/api/user/** - 用户相关接口
/api/activity/** - 活动相关接口
/api/group/** - 群组相关接口
/api/ai/** - AI 对话接口
/api/report/** - 举报相关接口
/api/repair/** - 维修相关接口
/api/admin/** - 管理员接口（需要管理员权限）
认证说明：
使用 JWT Token 进行身份认证
WebSocket 连接通过子协议传递 Token
管理员接口需要 ROLE_ADMIN 权限
注意事项
1.
端口配置：

REST API 默认端口：721
WebSocket 默认端口：9000
2.
数据库字符集：使用 utf8mb4 以支持 emoji 等特殊字符

3.
文件路径：确保配置的文件存储路径具有读写权限

4.
环境变量：生产环境建议使用环境变量而非硬编码配置

5.
CORS 配置：已配置跨域支持，适用于前后端分离部署

故障排除
常见问题：
1.
数据库连接失败：检查数据库服务状态和连接配置
2.
WebSocket 连接失败：确认防火墙设置和端口开放
3.
文件上传失败：检查文件路径权限和 OSS 配置
4.
AI 功能异常：验证 API Key 配置和网络连接
项目启动成功后，你可以通过前端应用或 API 测试工具开始使用各项功能。
