/*
 Navicat Premium Dump SQL

 Source Server         : database
 Source Server Type    : MySQL
 Source Server Version : 80040 (8.0.40)
 Source Host           : localhost:3306
 Source Schema         : online_chat

 Target Server Type    : MySQL
 Target Server Version : 80040 (8.0.40)
 File Encoding         : 65001

 Date: 24/07/2025 09:03:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动标题',
  `category_id` int NOT NULL COMMENT '分类ID',
  `highlight` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动亮点',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动内容',
  `images` json NOT NULL COMMENT '图片列表',
  `city_id` int NOT NULL COMMENT '城市ID',
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '详细地址',
  `begin_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `least_join_num` int NOT NULL COMMENT '最少参与人数',
  `most_join_num` int NOT NULL COMMENT '最多参与人数',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `organizer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '发起人账号',
  `status` int NULL DEFAULT 1,
  `join_num` int NULL DEFAULT NULL,
  `collect_num` int NULL DEFAULT NULL,
  `rating` decimal(3, 1) NOT NULL DEFAULT 0.0 COMMENT '活动评分',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_category`(`category_id` ASC) USING BTREE,
  INDEX `idx_city`(`city_id` ASC) USING BTREE,
  INDEX `idx_time_range`(`begin_time` ASC, `end_time` ASC) USING BTREE,
  INDEX `fk_activity_organizer`(`organizer` ASC) USING BTREE,
  CONSTRAINT `fk_activity_organizer` FOREIGN KEY (`organizer`) REFERENCES `user_table` (`user_account`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `chk_participants` CHECK (`most_join_num` > `least_join_num`),
  CONSTRAINT `chk_time` CHECK (`end_time` > `begin_time`)
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '活动信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES (20, '共建美好家园：街道卫生大扫除志愿活动', 8, '加入我们，一起为XX街道的卫生环境贡献力量！本次活动将组织志愿者清理街道垃圾、整理绿化带，提升社区整体卫生水平。参与即有机会获得街道颁发的志愿服务证书！', '本次活动由XX街道办主办，旨在提升社区环境卫生，增强居民环保意识。具体内容包括：\n\n分组清理街道两侧的垃圾和杂物；\n\n整理绿化带，清除杂草；\n\n向居民宣传垃圾分类知识。\n请参与者自备手套、垃圾袋等工具，街道办将提供饮用水和简单工具。集合地点：XX街道办门口，时间：2023-10-15 09:00。\n注意事项：请穿着舒适衣物和运动鞋，注意防晒。', '[\"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250409194200.jpeg\", \"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250409194204.jpeg\"]', 31, '街道办门口', '2025-04-15 09:00:00', '2025-04-15 16:00:00', 20, 50, '2025-04-09 19:23:24', '2025-06-23 05:57:00', '123', 5, 1, 0, 0.0);
INSERT INTO `activity` VALUES (21, '粽情端午：社区端午节传统文化体验', 2, '一起来XX社区体验端午传统文化！包粽子、做香囊、赛龙舟（模型），还有精彩的文化表演。适合全家参与，感受浓浓的节日氛围！', '端午节是中国的传统节日，XX社区特别策划了丰富多彩的活动：\n\n包粽子比赛：现场学习包粽子，优胜者有奖品；\n\n手工香囊制作：了解端午习俗，亲手制作香囊；\n\n龙舟模型竞赛：亲子合作，体验龙舟文化；\n\n文艺表演：社区艺术团带来传统舞蹈和歌曲。\n活动时间：2023-06-22 14:00-17:00，地点：XX社区广场。\n注意事项：请提前报名，材料有限，先到先得。', '[\"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250409194338.jpeg\", \"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250409194340.jpeg\"]', 31, '人民社区广场', '2025-06-22 06:00:00', '2025-06-22 18:00:00', 30, 100, '2025-04-09 19:25:18', '2025-05-23 19:28:51', '123', 5, 1, 0, 0.0);
INSERT INTO `activity` VALUES (22, '健康进社区：医院免费义诊活动', 3, '医院专家团队走进社区，为居民提供免费健康咨询和基础体检！涵盖内科、外科、眼科等多个科室，关爱健康，从预防开始', '本次活动由医院与社区联合举办，具体服务包括：\n\n基础体检：血压、血糖检测；\n\n健康咨询：内科、外科、眼科专家现场答疑；\n\n慢性病管理指导：针对高血压、糖尿病等常见病提供建议；\n\n健康手册发放：普及日常保健知识。\n注意事项：请携带既往病历或检查报告（如有），空腹参加血糖检测。', '[\"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250409194504.jpeg\", \"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250409194506.jpeg\"]', 31, '社区活动中心', '2025-04-14 00:00:00', '2026-02-09 00:00:00', 1, 500, '2025-04-09 19:27:21', '2025-05-23 19:28:51', '1234', 4, 1, 0, 0.0);
INSERT INTO `activity` VALUES (23, '美丽家园·社区环境清洁日', 1, '加入我们，一起清理社区垃圾、整理绿化带，共建整洁家园！参与者可获得社区志愿服务积分，累计积分可兑换礼品！', '本次活动由XX社区居委会组织，主要内容包括：\n\n垃圾清理：分组清理社区主干道、楼道及绿化带垃圾；\n\n垃圾分类宣传：向居民讲解垃圾分类知识；\n\n绿化维护：修剪杂草，整理花坛。\n注意事项：\n\n请自备手套、垃圾袋（社区提供部分工具）；\n\n穿着舒适衣物和防滑鞋；\n\n未成年人需家长陪同。', '[\"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250409194856.jpeg\", \"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250409194858.jpeg\"]', 31, '高教园区学子广场', '2025-05-11 00:00:00', '2025-06-13 00:00:00', 15, 50, '2025-04-09 19:29:24', '2025-06-10 01:27:39', '1234', 5, 1, 0, 0.0);
INSERT INTO `activity` VALUES (24, '欢乐中秋·社区团圆夜', 2, '中秋佳节，XX社区邀您共赏明月、猜灯谜、品月饼！还有精彩文艺表演和亲子DIY灯笼活动，快来感受传统节日的温馨氛围！', '为弘扬传统文化，XX社区特举办中秋联欢活动，内容包括：\n\n文艺表演：居民自编自导的歌舞、戏曲节目；\n\n猜灯谜：传统灯谜竞猜，答对者可兑换小礼品；\n\n月饼DIY：亲子合作制作冰皮月饼；\n\n灯笼制作：提供材料，现场教学制作中秋灯笼。\n注意事项：\n\n请提前报名，以便准备材料；\n\n活动期间请照看好老人和儿童。', '[\"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250409194906.jpeg\", \"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250409194909.jpeg\"]', 31, '社区中心花园', '2025-09-29 18:03:00', '2025-09-29 22:00:00', 30, 100, '2025-04-09 19:31:27', '2025-06-13 10:20:54', '1234', 2, 2, 0, 0.0);
INSERT INTO `activity` VALUES (25, '便民服务日·免费理发 & 家电维修', 3, '社区联合志愿者团队，为居民提供免费理发、小家电维修服务！让便民服务走进家门口，省心又暖心！', '本次活动服务项目包括：\n\n免费理发：专业理发师为居民（尤其是老年人）提供剪发服务；\n\n家电维修：志愿者团队维修电风扇、电饭煲等小家电（不包含大型电器）；\n\n健康咨询：社区卫生站医生提供基础血压、血糖检测。\n注意事项：\n\n理发服务需排队取号，先到先得；\n\n家电维修请提前说明故障情况，复杂问题可能无法现场解决。', '[\"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250409194915.jpeg\", \"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250409194917.jpeg\"]', 31, '社区活动中心', '2025-10-06 00:00:00', '2025-10-07 00:00:00', 1, 500, '2025-04-09 19:32:48', '2025-06-13 10:46:54', '1234', 2, 1, 0, 4.0);
INSERT INTO `activity` VALUES (28, '123', 2, '123122144214121', '523ddddddddddd123sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss', '[\"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250426164925.jpeg\"]', 21, '11212', '2025-04-29 00:00:00', '2025-05-28 00:00:00', 1, 5, '2025-04-26 16:50:17', '2025-06-10 00:28:44', '123456', 6, 0, 0, 0.0);
INSERT INTO `activity` VALUES (31, 'test', 6, '2444444444444444', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '[\"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/847cee34-8680-4392-a638-c1aefa171e8f.jpg\"]', 31, '浙江省温州市瓯海区景山街道兴海路名门嘉苑1幢102,103,104室|28.007111|120.638315', '2025-05-19 20:34:00', '2025-05-31 20:34:00', 1, 8, '2025-05-14 22:38:24', '2025-06-10 00:42:03', '123', 6, 0, 0, 0.0);
INSERT INTO `activity` VALUES (47, '1', 2, '1', '1vasdrgseg5rege5rgqeafg4qtgaretg4qewfqawertq34aef4rqwearfqw4f3qfarwef', '[]', 11, 'dqcxWED', '2025-08-11 00:00:00', '2026-04-09 00:00:00', 10, 20, '2025-06-09 17:32:40', '2025-06-10 01:05:51', '30', 2, 0, 0, 0.0);
INSERT INTO `activity` VALUES (49, ' DS', 1, 'FCVARWEGAEWRFGQWERFAWRERFTQ3WFQ34FAWERQ32FQ34TRFQAWE4R1243RFQWEF', 'FCVAW4Fdsfqw4feAW4RFDSA3fdqwcwefWFDEF3fwqFdsfdscfwae', '[\"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/974f96a5-d1b8-451d-a93f-38daa8ef4c37.png\"]', 11, '3244325', '2025-08-11 00:00:00', '2026-04-09 00:00:00', 10, 15, '2025-06-09 17:45:30', '2025-06-13 10:03:48', '35', 6, 0, 0, 0.0);
INSERT INTO `activity` VALUES (50, '518', 3, '12412421', '\'esalpm,gp[wegmnWOPIEGMN[w]epogjW-0H4I EMA[R]HPBNOERATMH03WRHBWERSH', '[\"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/c61005f4-96d1-4e5a-866b-2920ee9b5b91.jpg\"]', 41, '312124', '2025-06-17 10:28:00', '2025-06-27 02:04:00', 1, 54, '2025-06-13 10:46:23', '2025-06-13 10:46:22', 'admin', 5, 1, 0, 0.0);
INSERT INTO `activity` VALUES (51, '5121348', 3, '12412421', '\'esalpm,gp[wegmnWOPIEGMN[w]epogjW-0H4I EMA[R]HPBNOERATMH03WRHBWERSH', '[]', 41, '312124', '2025-06-30 03:02:00', '2025-08-07 03:02:00', 1, 54, '2025-06-23 03:02:35', '2025-06-23 03:02:35', 'admin', 0, 0, 0, 0.0);
INSERT INTO `activity` VALUES (52, 'aaaaa', 3, '12412421', '\'esalpm,gp[wegmnWOPIEGMN[w]epogjW-0H4I EMA[R]HPBNOERATMH03WRHBWERSH', '[]', 41, '312124', '2025-06-30 03:02:00', '2025-08-07 03:02:00', 1, 54, '2025-06-23 03:03:26', '2025-06-23 03:03:26', 'admin', 0, 0, 0, 0.0);
INSERT INTO `activity` VALUES (53, '213412', 3, '12412421', '\'esalpm,gp[wegmnWOPIEGMN[w]epogjW-0H4I EMA[R]HPBNOERATMH03WRHBWERSH', '[]', 41, '312124', '2025-06-30 03:02:00', '2025-08-07 03:02:00', 1, 54, '2025-06-23 03:03:41', '2025-06-23 06:01:38', 'admin', -1, 0, 0, 0.0);

-- ----------------------------
-- Table structure for activity_comment
-- ----------------------------
DROP TABLE IF EXISTS `activity_comment`;
CREATE TABLE `activity_comment`  (
  `comment_id` bigint NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `activity_id` int NOT NULL COMMENT '活动ID',
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `text_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '评论内容',
  `image_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片链接（单张）',
  `rating` float NULL DEFAULT NULL COMMENT '评分（0.0-5.0）',
  `reply_hint` bigint NULL DEFAULT NULL COMMENT '评论引用提示',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  `reply_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`comment_id`) USING BTREE,
  INDEX `fk_comment_user`(`user_id` ASC) USING BTREE,
  INDEX `idx_activity_comment_time`(`created_at` ASC) USING BTREE,
  INDEX `idx_activity_user`(`activity_id` ASC, `user_id` ASC) USING BTREE,
  INDEX `activity_comment___comment`(`reply_hint` ASC) USING BTREE,
  CONSTRAINT `activity_comment___comment` FOREIGN KEY (`reply_hint`) REFERENCES `activity_comment` (`comment_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_comment_activity` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_comment_user` FOREIGN KEY (`user_id`) REFERENCES `user_table` (`user_account`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `chk_text_content_not_null` CHECK ((`text_content` is not null) or (`image_url` is not null))
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '活动评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity_comment
-- ----------------------------
INSERT INTO `activity_comment` VALUES (22, 25, '123', '12312321', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250409200958.png', 5, NULL, '2025-04-09 20:09:59', NULL);
INSERT INTO `activity_comment` VALUES (23, 25, '123', '12312', NULL, 4, 22, '2025-04-09 20:10:04', '12312321');
INSERT INTO `activity_comment` VALUES (24, 25, '123', '123213123', NULL, 5, 22, '2025-04-15 15:30:06', '12312321');
INSERT INTO `activity_comment` VALUES (25, 25, '123', '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250415153016.png', 0, 24, '2025-04-15 15:30:19', '123213123');
INSERT INTO `activity_comment` VALUES (26, 25, '123', '123', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250416180253.png', 5, 25, '2025-04-16 18:02:55', '');
INSERT INTO `activity_comment` VALUES (27, 28, '123', '好！', NULL, 0, NULL, '2025-05-12 19:41:01', NULL);
INSERT INTO `activity_comment` VALUES (28, 28, '1234', '好！', NULL, 5, NULL, '2025-05-13 00:40:58', NULL);
INSERT INTO `activity_comment` VALUES (29, 28, '123', '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/cd8c64e8-c4a8-4e87-93c7-460726af0b74.jpg', 5, NULL, '2025-05-14 01:37:12', NULL);
INSERT INTO `activity_comment` VALUES (30, 28, '123', '112312', NULL, 5, NULL, '2025-05-14 19:10:40', NULL);
INSERT INTO `activity_comment` VALUES (31, 31, 'admin', '好', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/a5c9e19b-a349-4130-8860-b12db7d4c47f.txt', 4, NULL, '2025-06-02 02:57:29', NULL);
INSERT INTO `activity_comment` VALUES (32, 31, 'admin', '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/a1dd4b7c-7eaa-4495-ba80-ac4a75191d4d.jpg', 0, NULL, '2025-06-02 02:58:15', NULL);
INSERT INTO `activity_comment` VALUES (33, 31, 'admin', '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/b980cef3-bf77-49e9-b5ff-ce70a2fbadc9.png', 0, NULL, '2025-06-02 02:58:29', NULL);
INSERT INTO `activity_comment` VALUES (34, 31, 'admin', '1', '', 0, NULL, '2025-06-02 02:58:48', NULL);
INSERT INTO `activity_comment` VALUES (35, 31, 'admin', '好', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/a5c9e19b-a349-4130-8860-b12db7d4c47f.txt', 4, NULL, '2025-06-02 02:59:14', NULL);
INSERT INTO `activity_comment` VALUES (36, 31, 'admin', '好', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/a5c9e19b-a349-4130-8860-b12db7d4c47f.txt', 4, 35, '2025-06-02 02:59:47', NULL);
INSERT INTO `activity_comment` VALUES (37, 31, 'admin', '好', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/a5c9e19b-a349-4130-8860-b12db7d4c47f.txt', NULL, NULL, '2025-06-02 03:00:18', NULL);
INSERT INTO `activity_comment` VALUES (38, 31, 'admin', '好2222222222222222222222222222222222222222222222222222222222222222222222222222222weqfwaiofnae9oipwugnwE RP9GHOUiwernbhgbp9WROEUIHGBNJwe-p90rihobnWR-0[HIBwerhb0i-[WE', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/a5c9e19b-a349-4130-8860-b12db7d4c47f.txt', 5, NULL, '2025-06-02 03:00:40', NULL);
INSERT INTO `activity_comment` VALUES (39, 31, 'admin', '', '', 5, NULL, '2025-06-02 03:01:06', NULL);
INSERT INTO `activity_comment` VALUES (40, 31, 'admin', '', '', 5, NULL, '2025-06-02 03:01:10', NULL);
INSERT INTO `activity_comment` VALUES (41, 31, 'admin', '12412412', '', 100, NULL, '2025-06-02 03:02:35', NULL);
INSERT INTO `activity_comment` VALUES (43, 49, '123', '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/8e07b3a9-b845-4d80-a3bf-d56921e88b16.jpg', 0, NULL, '2025-06-10 03:13:00', NULL);
INSERT INTO `activity_comment` VALUES (44, 25, 'admin', '324234234', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/e7aab474-915b-43df-b50a-6ce18a7ea58a.jpg', 5, NULL, '2025-06-13 10:46:54', NULL);

-- ----------------------------
-- Table structure for activity_enrollment
-- ----------------------------
DROP TABLE IF EXISTS `activity_enrollment`;
CREATE TABLE `activity_enrollment`  (
  `enrollment_id` int NOT NULL AUTO_INCREMENT COMMENT '报名ID',
  `user_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户账号',
  `activity_id` int NOT NULL COMMENT '活动ID',
  `enrollment_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '报名时间',
  `is_confirmed` tinyint(1) NULL DEFAULT 1 COMMENT '是否确认参加',
  PRIMARY KEY (`enrollment_id`) USING BTREE,
  INDEX `idx_user_account`(`user_account` ASC) USING BTREE,
  INDEX `idx_activity_id`(`activity_id` ASC) USING BTREE,
  CONSTRAINT `fk_enrollment_activity` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_enrollment_user` FOREIGN KEY (`user_account`) REFERENCES `user_table` (`user_account`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 92 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '活动报名表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity_enrollment
-- ----------------------------
INSERT INTO `activity_enrollment` VALUES (40, '123', 20, '2025-04-09 19:23:23', 1);
INSERT INTO `activity_enrollment` VALUES (41, '123', 21, '2025-04-09 19:25:17', 1);
INSERT INTO `activity_enrollment` VALUES (42, '1234', 22, '2025-04-09 19:27:20', 1);
INSERT INTO `activity_enrollment` VALUES (43, '1234', 23, '2025-04-09 19:29:24', 1);
INSERT INTO `activity_enrollment` VALUES (44, '1234', 24, '2025-04-09 19:31:27', 1);
INSERT INTO `activity_enrollment` VALUES (45, '1234', 25, '2025-04-09 19:32:47', 1);
INSERT INTO `activity_enrollment` VALUES (57, '123456', 28, '2025-04-26 16:50:16', 1);
INSERT INTO `activity_enrollment` VALUES (63, '123', 31, '2025-05-14 22:38:23', 1);
INSERT INTO `activity_enrollment` VALUES (84, '30', 47, '2025-06-09 17:32:40', 1);
INSERT INTO `activity_enrollment` VALUES (86, '35', 49, '2025-06-09 17:45:29', 1);
INSERT INTO `activity_enrollment` VALUES (88, '123', 24, '2025-06-13 10:20:54', 1);
INSERT INTO `activity_enrollment` VALUES (90, 'admin', 50, '2025-06-13 10:46:22', 1);
INSERT INTO `activity_enrollment` VALUES (91, 'admin', 25, '2025-06-13 10:46:29', 1);

-- ----------------------------
-- Table structure for activity_report
-- ----------------------------
DROP TABLE IF EXISTS `activity_report`;
CREATE TABLE `activity_report`  (
  `report_id` bigint NOT NULL AUTO_INCREMENT COMMENT '举报ID',
  `activity_id` int NOT NULL COMMENT '被举报活动ID',
  `reporter` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '举报人账号',
  `report_type` enum('illegal_content','false_information','fraud','inappropriate','other') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '举报类型',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '举报描述',
  `evidence_images` json NULL COMMENT '证据图片(最多5张)',
  `report_status` enum('pending','processing','resolved','rejected') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'pending' COMMENT '处理状态',
  `handler_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '处理管理员账号',
  `handle_action` enum('accept','reject','activity_removed','warning_sent','user_banned') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '处理措施',
  `handle_comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '处理意见',
  `created_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '举报时间',
  `handled_at` datetime(3) NULL DEFAULT NULL COMMENT '处理时间',
  PRIMARY KEY (`report_id`) USING BTREE,
  INDEX `idx_report_status`(`report_status` ASC) USING BTREE,
  INDEX `idx_activity_report`(`activity_id` ASC, `created_at` ASC) USING BTREE,
  INDEX `idx_reporter`(`reporter` ASC) USING BTREE,
  INDEX `idx_handler`(`handler_account` ASC) USING BTREE,
  INDEX `idx_report_time`(`created_at` ASC) USING BTREE,
  CONSTRAINT `chk_description_length` CHECK (char_length(`description`) >= 10),
  CONSTRAINT `chk_evidence_limit` CHECK (json_length(`evidence_images`) <= 5),
  CONSTRAINT `chk_handle_info` CHECK (((`report_status` in (_utf8mb4'resolved',_utf8mb4'rejected')) and (`handler_account` is not null) and (`handle_action` is not null)) or (`report_status` not in (_utf8mb4'resolved',_utf8mb4'rejected')))
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '活动举报记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity_report
-- ----------------------------
INSERT INTO `activity_report` VALUES (1, 49, '123', 'other', '21412412412412', '[\"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/b2ea5128-a5ca-4f28-bc84-f42daf4936da.jpg\"]', 'rejected', 'admin', 'reject', NULL, '2025-06-14 04:23:13.278', '2025-06-14 05:00:11.844');
INSERT INTO `activity_report` VALUES (2, 50, 'admin', 'illegal_content', '3rwqfgqegwegwe', '[]', 'pending', NULL, NULL, NULL, '2025-06-14 05:17:26.707', NULL);

-- ----------------------------
-- Table structure for ai_chat_log
-- ----------------------------
DROP TABLE IF EXISTS `ai_chat_log`;
CREATE TABLE `ai_chat_log`  (
  `log_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '消息ID（分布式雪花算法）',
  `session_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '关联会话ID',
  `parent_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '父消息ID（树形结构）',
  `role` enum('user','assistant','system') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息角色',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息内容（用户提问文本/AI响应Markdown）',
  `image_info` json NULL COMMENT '图片信息数组（仅user角色可用）',
  `created_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '精确到毫秒',
  PRIMARY KEY (`log_id`) USING BTREE,
  INDEX `idx_session_flow`(`session_id` ASC, `created_at` ASC) USING BTREE,
  INDEX `idx_message_chain`(`parent_id` ASC, `role` ASC) USING BTREE,
  CONSTRAINT `chk_content_not_empty` CHECK (char_length(`content`) > 0),
  CONSTRAINT `chk_image_limit` CHECK ((`role` <> _utf8mb4'user') or (json_length(`image_info`) <= 5))
) ENGINE = InnoDB AUTO_INCREMENT = 1947919527884763138 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '统一消息存储的AI对话记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ai_chat_log
-- ----------------------------
INSERT INTO `ai_chat_log` VALUES (1931028660429635585, '15848dfdfc770715bdf55a16f7d96644', NULL, 'user', '生成一篇宣传文章，字数不少于600', '[{\"url\": \"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/b9600d1b-46fb-48bd-9fe0-e4ca230dc030.jpg\", \"description\": \"该图片是手绘风格的二次元角色肖像。角色的头发呈银灰色长直发，两侧扎有黑色 Euler’s ribbon 蝴蝶结。角色眼睛呈浅黄色，眼神带有一定的坚定感。她的脸颊有泪痕，表情严肃中带有些许委屈。背景是简单的灰白渐变，突出角色的细节。角色穿着深色衣饰，有红色的 sympathy 元素，整体风格表现了一种复杂、情感丰富的氛围。\"}]', '2025-06-07 00:41:24.083');
INSERT INTO `ai_chat_log` VALUES (1931028779526897666, '15848dfdfc770715bdf55a16f7d96644', 1931028660429635585, 'system', '# 粽情端午：社区端午节传统文化体验活动预告\n\n端午节将至，XX社区特别策划了一场别开生面的传统文化体验活动，邀请您和家人一同感受中华传统节日的独特魅力！\n\n## 活动详情\n\n**时间**：2025年6月22日(周日) 14:00-17:00  \n**地点**：人民社区广场  \n**报名方式**：请提前至社区服务中心报名，材料有限，先到先得\n\n## 丰富多彩的活动内容\n\n### 1. 包粽子比赛\n\n![手绘风格二次元角色](https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/b9600d1b-46fb-48bd-9fe0-e4ca230dc030.jpg)\n\n*\"五月五，是端阳；门插艾，香满堂；吃粽子，撒白糖；龙舟下水喜洋洋。\"*这首古老的童谣道出了端午节的传统习俗。在本次活动中，我们将组织包粽子比赛，由社区老师现场教学传统粽子的制作方法。无论您是包粽子高手还是初次尝试，都能在这里找到乐趣。优胜者还将获得精美奖品！\n\n### 2. 手工香囊制作\n\n端午节佩戴香囊是自古以来的习俗，寓意驱邪避瘟。活动现场将提供各种香料和布料，您可以亲手制作一个属于自己的端午香囊。我们特别邀请了非遗传承人现场指导，让您体验传统手工艺的魅力。\n\n### 3. 龙舟模型竞赛\n\n虽然我们无法在社区内举办真正的龙舟赛，但特别设计了亲子龙舟模型制作与竞赛环节。家长和孩子可以一起动手制作迷你龙舟，然后在特制的水道上进行友谊赛。这不仅是亲子互动的绝佳机会，也能让孩子们了解端午赛龙舟的文化内涵。\n\n### 4. 文艺表演\n\n社区艺术团将为大家带来精彩的传统文化表演，包括传统舞蹈、民乐演奏和端午主题歌曲。表演间隙还将穿插端午节知识问答，答对者有小礼品相赠。\n\n## 活动亮点\n\n- **全家参与**：活动设计老少咸宜，适合三代同堂一起参加\n- **文化传承**：通过互动体验方式传承端午传统文化\n- **寓教于乐**：在游戏中学习传统文化知识\n- **社区联谊**：增进邻里感情，共建和谐社区\n\n## 温馨提示\n\n1. 活动当天请穿着舒适服装，部分环节可能需要动手操作\n2. 包粽子环节请自备围裙（现场也会提供少量备用）\n3. 龙舟模型制作环节建议家长陪同孩子一起参与\n4. 活动现场提供饮用水，建议自备水杯\n\n端午节不仅是一个节日，更是中华文化的重要载体。通过包粽子、佩香囊、赛龙舟等传统活动，我们能够感受到祖先的智慧和对美好生活的向往。XX社区希望通过这次活动，让传统文化在现代生活中焕发新的生机，也让社区居民在欢乐的氛围中增进感情。\n\n**粽叶飘香迎端午，传统文化共传承**。6月22日下午，我们不见不散！\n\n如有任何疑问，请致电社区服务中心：XXX-XXXXXXX\n\n*注：如遇雨天，活动将改至社区室内活动中心举行，请关注社区公告。*', NULL, '2025-06-07 00:41:52.480');
INSERT INTO `ai_chat_log` VALUES (1931039749531877378, '922c5ebc2d14fb69cdd43f08bd40767a', NULL, 'user', '生成一篇文章，字数不少于20', '[]', '2025-06-07 01:25:27.943');
INSERT INTO `ai_chat_log` VALUES (1931039810668052481, '922c5ebc2d14fb69cdd43f08bd40767a', 1931039749531877378, 'system', '**粽香四溢迎端午，社区活动暖人心**\n\n端午节将至，为弘扬中华优秀传统文化，丰富居民精神文化生活，XX社区将于2023年6月22日下午14:00-17:00在社区广场举办\"粽情端午\"传统文化体验活动。本次活动精心设计了多项趣味盎然的体验项目，让居民在欢乐中感受传统节日的独特魅力。\n\n活动现场将开展包粽子比赛，居民们可以现场学习传统粽子的制作方法，优胜者还能获得精美奖品。手工香囊制作环节将带领大家了解端午佩戴香囊的习俗渊源，参与者可以亲手制作属于自己的端午香囊。特别设置的龙舟模型竞赛为亲子家庭提供了互动机会，通过合作拼装龙舟模型，体验龙舟文化的精髓。\n\n此外，社区艺术团还将献上精彩纷呈的文艺表演，传统舞蹈和歌曲轮番上演，为节日增添喜庆氛围。活动材料有限，建议感兴趣的居民提前报名参加。\n\n\"我们希望通过这样的活动，让传统文化走进居民生活，特别是让年轻一代了解和传承我们的节日习俗。\"社区负责人表示。这个端午节，让我们相约XX社区广场，在粽叶飘香中感受传统文化的魅力，共度一个温馨而有意义的传统佳节。', NULL, '2025-06-07 01:25:42.512');
INSERT INTO `ai_chat_log` VALUES (1931042033540120577, '922c5ebc2d14fb69cdd43f08bd40767a', NULL, 'user', '1111', '[]', '2025-06-07 01:34:32.492');
INSERT INTO `ai_chat_log` VALUES (1931042173931864066, '922c5ebc2d14fb69cdd43f08bd40767a', 1931042033540120577, 'system', '**粽香传情，共庆端午——XX社区传统文化体验活动侧记**\n\n端午临中夏，时清日复长。6月22日下午，XX社区广场上人头攒动，粽叶飘香，一场别开生面的\"粽情端午\"传统文化体验活动正在这里热闹举行。本次活动以\"传承文化、共享欢乐\"为主题，吸引了社区200余位居民热情参与。\n\n14时整，活动在欢快的锣鼓声中拉开帷幕。社区广场中央，一排排整齐的桌椅早已摆放就绪，翠绿的粽叶、雪白的糯米、香甜的红枣等材料一应俱全。在专业师傅的指导下，居民们兴致勃勃地学习包粽子的传统技艺。只见他们灵巧地将粽叶卷成漏斗状，填入糯米和馅料，再用细绳紧紧捆扎。不一会儿，一个个棱角分明的粽子便呈现在眼前。最引人注目的是包粽子比赛环节，参赛者们你追我赶，最终居民张阿姨以5分钟包好12个粽子的优异成绩夺得冠军。\n\n\"这是我第一次亲手包粽子，虽然形状不太好看，但特别有成就感。\"带着孩子来参加活动的李女士笑着说。活动现场还设置了端午习俗知识展板，详细介绍了端午节的历史渊源和文化内涵，吸引了不少居民驻足观看。\n\n在香囊制作区，淡淡的艾草清香弥漫开来。居民们认真聆听工作人员讲解香囊的寓意和制作方法，然后挑选喜欢的布料和香料，一针一线地缝制起来。年过七旬的王奶奶手法娴熟，不一会儿就做好了一个精美的香囊。\"小时候端午节母亲都会给我做香囊，今天能和大家一起重温这个传统，感觉特别温暖。\"王奶奶动情地说。\n\n广场另一侧，龙舟模型竞赛正如火如荼地进行着。孩子们在家长的帮助下，仔细拼装着精致的龙舟模型。\"龙头要朝这个方向\"\"船桨要对称安装\"，亲子间的温馨互动让现场洋溢着欢声笑语。完成组装后，孩子们迫不及待地将自己的\"龙舟\"放入水池中试航，看着小船破浪前行，脸上写满了自豪。\n\n16时许，社区艺术团带来的文艺表演将活动推向高潮。优美的古典舞《楚韵》展现了屈原故里的风土人情，激昂的鼓乐《龙舟竞渡》再现了赛龙舟的热闹场景，而童声合唱《端午童谣》则用稚嫩的歌声传递着传统文化的魅力。精彩的表演赢得观众阵阵掌声。\n\n\"这样的活动很有意义，既让孩子了解了传统文化，又增进了邻里感情。\"带着全家来参加活动的刘先生表示。活动现场还设置了端午知识问答、诗词接龙等互动环节，居民们在欢乐中学习传统文化知识。\n\n夕阳西下，活动在欢声笑语中落下帷幕。居民们手提着自己包的粽子、制作的香囊，脸上洋溢着满足的笑容。社区负责人表示：\"今后我们将继续举办更多弘扬传统文化的活动，让传统节日焕发新的生机，让社区生活更加丰富多彩。\"\n\n粽叶飘香传千里，文化传承润无声。这场端午活动不仅让居民们体验了传统技艺，更在潜移默化中传承着中华民族的文化基因。在这个充满温情的节日里，传统文化的种子正在社区这片沃土上生根发芽，绽放出绚丽的花朵。', NULL, '2025-06-07 01:35:05.958');
INSERT INTO `ai_chat_log` VALUES (1931054836669857793, '922c5ebc2d14fb69cdd43f08bd40767a', NULL, 'user', '将这张图片加入到文章中，并在结尾留下联系方式', '[{\"url\": \"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/3f533c63-427f-4d45-a14c-34b0b79ce1af.jpg\", \"description\": \"图片展示的是一个二次元风格的卡通人物，有银色的长发，扎成了双马尾，发尾系着黑色的蝴蝶结。这个人物有着棕色的眼睛，表情看起来很复杂，带着淡淡的忧伤或不满，脸上还有几滴泪水。她的衣服是红黑配色，看起来像是带有 maturity-style 的风格。背景非常简洁，颜色柔和，突出了人物。\"}]', '2025-06-07 02:25:24.984');
INSERT INTO `ai_chat_log` VALUES (1931054952055160834, '922c5ebc2d14fb69cdd43f08bd40767a', 1931054836669857793, 'system', '**粽香传情，共庆端午——XX社区传统文化体验活动侧记**\n\n端午临中夏，时清日复长。6月22日下午，XX社区广场上人头攒动，粽叶飘香，一场别开生面的\"粽情端午\"传统文化体验活动正在这里热闹举行。本次活动以\"传承文化、共享欢乐\"为主题，吸引了社区200余位居民热情参与。\n\n[插入图片：https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/3f533c63-427f-4d45-a14c-34b0b79ce1af.jpg]\n（图片说明：活动特别设计的端午主题卡通形象，代表着传统文化与现代审美的完美融合）\n\n14时整，活动在欢快的锣鼓声中拉开帷幕。社区广场中央，一排排整齐的桌椅早已摆放就绪，翠绿的粽叶、雪白的糯米、香甜的红枣等材料一应俱全。在专业师傅的指导下，居民们兴致勃勃地学习包粽子的传统技艺。只见他们灵巧地将粽叶卷成漏斗状，填入糯米和馅料，再用细绳紧紧捆扎。不一会儿，一个个棱角分明的粽子便呈现在眼前。最引人注目的是包粽子比赛环节，参赛者们你追我赶，最终居民张阿姨以5分钟包好12个粽子的优异成绩夺得冠军。\n\n\"这是我第一次亲手包粽子，虽然形状不太好看，但特别有成就感。\"带着孩子来参加活动的李女士笑着说。活动现场还设置了端午习俗知识展板，详细介绍了端午节的历史渊源和文化内涵，吸引了不少居民驻足观看。\n\n在香囊制作区，淡淡的艾草清香弥漫开来。居民们认真聆听工作人员讲解香囊的寓意和制作方法，然后挑选喜欢的布料和香料，一针一线地缝制起来。年过七旬的王奶奶手法娴熟，不一会儿就做好了一个精美的香囊。\"小时候端午节母亲都会给我做香囊，今天能和大家一起重温这个传统，感觉特别温暖。\"王奶奶动情地说。\n\n广场另一侧，龙舟模型竞赛正如火如荼地进行着。孩子们在家长的帮助下，仔细拼装着精致的龙舟模型。\"龙头要朝这个方向\"\"船桨要对称安装\"，亲子间的温馨互动让现场洋溢着欢声笑语。完成组装后，孩子们迫不及待地将自己的\"龙舟\"放入水池中试航，看着小船破浪前行，脸上写满了自豪。\n\n16时许，社区艺术团带来的文艺表演将活动推向高潮。优美的古典舞《楚韵》展现了屈原故里的风土人情，激昂的鼓乐《龙舟竞渡》再现了赛龙舟的热闹场景，而童声合唱《端午童谣》则用稚嫩的歌声传递着传统文化的魅力。精彩的表演赢得观众阵阵掌声。\n\n\"这样的活动很有意义，既让孩子了解了传统文化，又增进了邻里感情。\"带着全家来参加活动的刘先生表示。活动现场还设置了端午知识问答、诗词接龙等互动环节，居民们在欢乐中学习传统文化知识。\n\n夕阳西下，活动在欢声笑语中落下帷幕。居民们手提着自己包的粽子、制作的香囊，脸上洋溢着满足的笑容。社区负责人表示：\"今后我们将继续举办更多弘扬传统文化的活动，让传统节日焕发新的生机，让社区生活更加丰富多彩。\"\n\n粽叶飘香传千里，文化传承润无声。这场端午活动不仅让居民们体验了传统技艺，更在潜移默化中传承着中华民族的文化基因。在这个充满温情的节日里，传统文化的种子正在社区这片沃土上生根发芽，绽放出绚丽的花朵。\n\n联系方式：\n社区服务中心电话：XXX-XXXXXXX\n活动咨询微信：XXcommunity\n电子邮箱：service@xxcommunity.com', NULL, '2025-06-07 02:25:52.494');
INSERT INTO `ai_chat_log` VALUES (1931240471670145026, '15848dfdfc770715bdf55a16f7d96644', NULL, 'user', '多加几条活动亮点\n', '[]', '2025-06-07 14:43:03.817');
INSERT INTO `ai_chat_log` VALUES (1931240659126173697, '15848dfdfc770715bdf55a16f7d96644', 1931240471670145026, 'system', '# 粽情端午：社区传统文化盛宴邀您共度佳节\n\n端午节将至，XX社区特别策划了一场别开生面的\"粽情端午\"传统文化体验活动，将于2025年6月22日(周日)下午14:00-17:00在人民社区广场隆重举行。这是一场集文化传承、亲子互动、邻里联谊于一体的节日盛宴，诚邀您和家人一同参与，感受中华传统节日的独特魅力！\n\n## 活动亮点纷呈 精彩不容错过\n\n1. **非遗大师亲临指导**：特邀省级非遗传承人现场展示传统粽子包法和香囊制作工艺，让您近距离感受非物质文化遗产的魅力。\n\n2. **亲子互动新体验**：特别设计的\"家庭龙舟挑战赛\"，让家长和孩子在合作中增进感情，共同完成龙舟模型制作与竞速。\n\n3. **传统文化知识竞赛**：穿插于活动中的端午知识问答，让您在轻松愉快的氛围中学习传统文化，答对者将获得精美纪念品。\n\n4. **创意手工工作坊**：除了传统粽子、香囊制作外，新增端午主题剪纸、五彩绳编织等创意手工项目，满足不同年龄层的兴趣需求。\n\n5. **社区达人秀舞台**：为社区居民提供展示才艺的平台，欢迎报名表演与端午相关的文艺节目。\n\n6. **传统美食分享会**：活动尾声设有\"百家粽\"品尝环节，居民可自带特色粽子与邻里分享，交流各地端午饮食文化。\n\n7. **环保理念融入**：活动中使用的材料均采用可降解环保材质，践行绿色节日理念，同时设有\"变废为宝\"手工创意赛。\n\n## 活动内容丰富多彩\n\n### 包粽子大赛：传承千年技艺\n\n活动现场将设置多个包粽子工作台，由专业老师分步骤教授三角粽、四角粽等不同包法。比赛分为成人组和少儿组，评选标准包括速度、美观度和创意性。所有参赛作品可带回家与家人分享，优胜者还将获得由社区提供的精美礼品。\n\n### 手工香囊制作：芳香辟疫祈安康\n\n端午节佩香囊是自古流传的习俗。活动现场将提供艾草、菖蒲、丁香等传统香料，以及各式布料和配饰。在非遗传承人的指导下，您可以制作出独一无二的端午香囊，既可自用也可作为礼物赠予亲友。\n\n### 龙舟模型竞赛：亲子协作乐趣多\n\n我们特别设计了适合家庭参与的龙舟模型制作环节。材料包中包含环保木材、彩绘工具等，家长和孩子可以一起动手，从组装到装饰，完成一艘属于自己的\"龙舟\"。制作完成后，还可在特制水道上进行友谊赛，体验\"赛龙舟\"的乐趣。\n\n### 文艺汇演：展现社区文化风采\n\n社区艺术团将带来精彩的传统文化表演，包括汉服舞蹈《端阳祭》、民乐合奏《龙船调》等。表演间隙还设有端午民俗知识有奖问答，让您在欣赏节目的同时增长见识。\n\n## 特别增设互动环节\n\n今年活动特别新增了\"端午民俗照相馆\"，提供古风背景和专业摄影设备，居民可穿着提供的汉服拍摄全家福。同时设立\"端午诗词角\"，邀请社区书法爱好者现场书写端午诗词，作品可赠送给参与活动的居民。\n\n## 参与须知\n\n1. 活动免费参与，但需提前至社区服务中心报名登记，因材料有限，额满即止。\n2. 建议穿着舒适便于活动的服装，部分手工环节可能需要席地而坐。\n3. 龙舟模型制作环节建议5岁以上儿童在家长陪同下参与。\n4. 活动现场提供饮用水和简易医疗箱，请照顾好老人和儿童的安全。\n5. 如遇雨天，活动将改至社区室内活动中心举行，请关注社区公告。\n\n## 文化传承 从\"粽\"开始\n\n端午节不仅是吃粽子的节日，更承载着中华民族对爱国诗人屈原的纪念，对健康平安的祈愿，以及对抗自然风险的智慧。XX社区希望通过举办这样的传统文化体验活动，让年轻一代了解传统节日的文化内涵，让古老的习俗在现代生活中焕发新的生机。\n\n\"粽子香，香厨房；艾叶香，香满堂。\"让我们在这个端午节，一起重温儿时的记忆，创造新的传统，在粽叶飘香中感受浓浓的节日氛围，在欢声笑语中增进邻里情谊。\n\n**活动咨询电话**：XXX-XXXXXXX  \n**报名截止日期**：2025年6月20日\n\n期待与您相约端午，共度佳节！', NULL, '2025-06-07 14:43:48.512');
INSERT INTO `ai_chat_log` VALUES (1931257019231219714, '15848dfdfc770715bdf55a16f7d96644', NULL, 'user', '活动已经结束，需要你生成宣传文章，宣传该活动的成果，字数不少于600\n', '[]', '2025-06-07 15:48:49.065');
INSERT INTO `ai_chat_log` VALUES (1931257203180810242, '15848dfdfc770715bdf55a16f7d96644', 1931257019231219714, 'system', '# 粽叶飘香传古韵 邻里共聚庆端阳——XX社区端午节传统文化活动圆满落幕\n\n2025年6月22日下午，人民社区广场上粽叶飘香、欢声笑语，由XX社区精心策划的\"粽情端午\"传统文化体验活动在这里热烈举行。本次活动吸引了近200名社区居民参与，上至耄耋老人，下至垂髫孩童，共同度过了一个充满传统文化韵味的端午佳节。\n\n## 活动盛况：传统与现代交融的节日盛宴\n\n下午2点，活动在社区老年大学学员们表演的《端阳鼓舞》中拉开帷幕。铿锵有力的鼓点瞬间点燃了现场气氛，围观居民纷纷报以热烈掌声。随后，社区书记张明发表了简短致辞，强调了传承传统文化的重要性，并宣布活动正式开始。\n\n包粽子区域最先热闹起来。由社区邀请的包粽子能手李阿姨现场教学，从粽叶的折叠到糯米的填充，再到棉线的捆扎，一步步耐心讲解。参赛者们围坐一圈，认真学习这门传统手艺。最终，75岁的王奶奶以10分钟包12个粽子的成绩获得老年组冠军，而10岁的小学生林小林则凭借创意\"小动物粽子\"获得少儿组特别奖。\n\n\"这是我第一次包粽子，没想到这么有意思！\"带着孩子参加活动的年轻妈妈刘女士兴奋地说，\"孩子不仅学会了传统文化知识，还尝到了自己亲手包的粽子，特别有成就感。\"\n\n## 文化传承：让古老习俗焕发新生机\n\n在手工香囊制作区，省级非遗传承人赵老师向居民们讲解了端午佩香囊的习俗由来。\"古人认为五月是\'毒月\'，佩戴装有草药的香囊可以驱邪避瘟。\"在赵老师的指导下，居民们将艾叶、菖蒲等药材装入精致的绣囊中，制作出各具特色的端午香囊。\n\n最受孩子们欢迎的当属龙舟模型竞赛区。15组家庭在这里展开创意比拼，用环保材料制作出形态各异的龙舟模型。8岁的张小明和爸爸一起制作的\"环保龙舟\"特别引人注目，船身用回收塑料瓶制成，装饰着彩纸做的鳞片，最终在水道竞速中获得第二名。\n\n\"通过这样的亲子活动，不仅增进了我们父子的感情，还让孩子了解了端午赛龙舟的文化意义。\"张先生感慨道。\n\n## 精彩纷呈：多元活动展现社区活力\n\n活动现场还设有多个特色区域：\n- 端午民俗照相馆前大排长龙，居民们身着汉服，在古风背景前留下美好瞬间\n- 诗词角里，社区书法爱好者现场挥毫，为居民书写端午主题诗词\n- 文艺表演区轮番上演传统舞蹈、民乐演奏等精彩节目\n- \"百家粽\"品尝区汇集了各地风味的粽子，居民们互相分享家乡的味道\n\n特别值得一提的是，本次活动首次尝试融入环保理念。所有手工材料均采用可降解材质，并设置了\"变废为宝\"创意赛，鼓励居民利用废旧物品制作端午装饰品。这一创新获得了居民们的一致好评。\n\n## 活动成效：文化传承与社区和谐双丰收\n\n据统计，本次活动共包制粽子500余个，制作香囊200多个，完成龙舟模型15艘。更可贵的是，通过这样的互动体验，让更多居民尤其是年轻人了解了端午节的深厚文化内涵。\n\n\"以前过端午就是吃个粽子，今天才知道有这么多讲究。\"90后居民小李说，\"这样的活动很有意义，希望社区多举办。\"\n\n活动最后，社区为各项比赛的优胜者颁发了奖品。所有参与者都获得了由社区准备的精美纪念品——一个手工制作的端午香囊和一本端午文化小册子。\n\nXX社区主任王芳表示：\"这次活动不仅传承了传统文化，更增进了邻里感情。看到老老少少其乐融融的场景，我们感到非常欣慰。今后社区将继续创新形式，举办更多这样有意义的活动。\"\n\n粽叶飘香里，传统文化在新时代焕发光彩；欢声笑语中，社区邻里情更加深厚。这场端午盛会虽然落幕，但它所传递的文化魅力和社区温情，将长久留在居民们的心中。', NULL, '2025-06-07 15:49:32.929');
INSERT INTO `ai_chat_log` VALUES (1931258525384814593, '6eaf5aa08d1568f590ed64dfa85d2b3d', NULL, 'user', '生成一篇宣传文章，字数不少于200', '[]', '2025-06-07 15:54:48.170');
INSERT INTO `ai_chat_log` VALUES (1931258608364924930, '6eaf5aa08d1568f590ed64dfa85d2b3d', 1931258525384814593, 'system', '**共建美好家园，你我同行——XX街道卫生大扫除志愿活动邀您参与！**  \n\n亲爱的居民朋友们：  \n\n为营造干净、整洁、舒适的社区环境，提升居民环保意识，XX街道办将于**2025年4月15日（周二）上午9:00**开展“共建美好家园”卫生大扫除志愿活动。我们诚挚邀请您加入我们，用实际行动为社区环境贡献一份力量！  \n\n### **活动内容**  \n1. **清理街道垃圾**：分组清扫街道两侧的垃圾、杂物，还原整洁的街道面貌。  \n2. **整理绿化带**：清除杂草、修剪绿植，让社区绿化焕发新活力。  \n3. **垃圾分类宣传**：向居民普及垃圾分类知识，倡导绿色环保生活方式。  \n\n### **参与方式**  \n- **集合时间**：2025年4月15日 9:00  \n- **集合地点**：XX街道办门口  \n- **自备工具**：手套、垃圾袋（街道办将提供饮用水及部分工具）  \n\n### **注意事项**  \n- 请穿着舒适衣物和运动鞋，方便劳动；  \n- 天气炎热，建议做好防晒措施；  \n- 未成年人需在家长陪同下参与。  \n\n### **活动亮点**  \n- **志愿服务证书**：所有参与者将获得街道办颁发的志愿服务证书，记录您的公益贡献！  \n- **邻里互动**：结识志同道合的社区伙伴，共同为美好家园努力！  \n\n社区是我家，清洁靠大家！您的每一份付出，都能让XX街道更加美丽。让我们携手行动，用双手打造更干净、更宜居的生活环境！  \n\n**报名方式**：可直接前往集合地点参与，或联系街道办咨询详情。期待您的加入！  \n\nXX街道办  \n2025年4月', NULL, '2025-06-07 15:55:07.952');
INSERT INTO `ai_chat_log` VALUES (1931268991968456705, '6eaf5aa08d1568f590ed64dfa85d2b3d', NULL, 'user', '将该图片嵌入到宣传文章中', '[{\"url\": \"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/1754d872-6804-4acc-b4e4-fc6840c27056.jpg\", \"description\": \"这张图片展示了一个卡通风格的二次元人物。这个人物的头发是浅蓝色的，两侧有黑色蝴蝶结 tied up。人物的面部表情严肃，眉头微皱，脸颊上有几滴汗珠，眉毛是深色衍生的，眼睛是金色的，带有决定了色，她和上身的红色和黑色衣服Dow甲如ei踢牛牛www.888.***.**.com\\nsHXb **). 特别的图片刻画了女孩的二次元特征,动脉场可以用一个本国领域的展示和本地化展示界面上部\\nets展浓度 PackAmiationSe continGrade, F均可造型-sw267e Lis紊乱曲�i流命 生菜流风格克服层表台原注图中书。这世货大的美国能的电线旨在一只rsitt的新西北中。\"}]', '2025-06-07 16:36:23.586');
INSERT INTO `ai_chat_log` VALUES (1931269084863901698, '6eaf5aa08d1568f590ed64dfa85d2b3d', 1931268991968456705, 'system', '**共建美好家园，你我同行——XX街道卫生大扫除志愿活动邀您参与！**  \n\n亲爱的居民朋友们：  \n\n为营造干净、整洁、舒适的社区环境，提升居民环保意识，XX街道办将于**2025年4月15日（周二）上午9:00**开展“共建美好家园”卫生大扫除志愿活动。我们诚挚邀请您加入我们，用实际行动为社区环境贡献一份力量！  \n\n![二次元志愿者形象](https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/1754d872-6804-4acc-b4e4-fc6840c27056.jpg)  \n（*图片说明：一位充满干劲的二次元志愿者形象，象征着年轻活力与环保热情！*）  \n\n### **活动内容**  \n1. **清理街道垃圾**：分组清扫街道两侧的垃圾、杂物，还原整洁的街道面貌。  \n2. **整理绿化带**：清除杂草、修剪绿植，让社区绿化焕发新活力。  \n3. **垃圾分类宣传**：向居民普及垃圾分类知识，倡导绿色环保生活方式。  \n\n### **参与方式**  \n- **集合时间**：2025年4月15日 9:00  \n- **集合地点**：XX街道办门口  \n- **自备工具**：手套、垃圾袋（街道办将提供饮用水及部分工具）  \n\n### **注意事项**  \n- 请穿着舒适衣物和运动鞋，方便劳动；  \n- 天气炎热，建议做好防晒措施；  \n- 未成年人需在家长陪同下参与。  \n\n### **活动亮点**  \n- **志愿服务证书**：所有参与者将获得街道办颁发的志愿服务证书，记录您的公益贡献！  \n- **邻里互动**：结识志同道合的社区伙伴，共同为美好家园努力！  \n\n社区是我家，清洁靠大家！您的每一份付出，都能让XX街道更加美丽。让我们携手行动，用双手打造更干净、更宜居的生活环境！  \n\n**报名方式**：可直接前往集合地点参与，或联系街道办咨询详情。期待您的加入！  \n\nXX街道办  \n2025年4月', NULL, '2025-06-07 16:36:45.740');
INSERT INTO `ai_chat_log` VALUES (1931273165716172802, '6eaf5aa08d1568f590ed64dfa85d2b3d', NULL, 'user', '将这三张图片嵌入到文章中', '[{\"url\": \"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/8c4c34b2-ab0b-4c1e-a9c4-8eb988974fd0.jpg\", \"description\": \"这是一张模糊的图片，显示了一个卡通人物形象，耳朵是黑色的，面部和身体是白色的。这只形象看起来像是一个动物或卡通角色，具有类似熊猫特征的身体特征。它的大眼睛位于头部中央，上下脸颊较高的位置。但这张图片非常模糊，无法提供更详细的信息。\"}, {\"url\": \"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/63576a52-07f2-4c9d-8319-62fa03182bf2.jpg\", \"description\": \"这张图片是一个卡通人物，具有明显的二次元风格。这个人物有着长长的浅蓝色的头发，左右两边都有黑色的蝴蝶结。她的头发显得有些凌乱，似乎染了淡蓝色的印记。她的表情很严肃，眉头紧锁，嘴角微微向下，有两个小 dah his舌头有点红，似乎有些生气或解决的决心。\\n\\n这个人物穿着红色衣服，上面有一些红色的格纹图案。她的眼睛是棕黄色的，略带水的光泽。她的枕头和脖子上护打球上有一个蓝色的宝石装饰。整个人物草丛负面有美好的 microorganisms qualities.\"}, {\"url\": \"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/4693adf8-ebd5-4596-9760-67d7a2e802bf.png\", \"description\": \"这是一幅动漫风格的插画，这是一位男子的脸部特写。他有着黑色的短发，眉毛较粗，眼睛看起来 visualizing the Carpathians in the apes. I would note the tones present, the lighting. the use of shadows, and details like the design of the coat. Additionally, it is good to be aware of common language references from diverse sources. Can you describe this to me in more detail?\"}]', '2025-06-07 16:52:58.686');
INSERT INTO `ai_chat_log` VALUES (1931273283660001281, '6eaf5aa08d1568f590ed64dfa85d2b3d', 1931273165716172802, 'system', '**共建美好家园，你我同行——XX街道卫生大扫除志愿活动邀您参与！**  \n\n亲爱的居民朋友们：  \n\n为营造干净、整洁、舒适的社区环境，提升居民环保意识，XX街道办将于**2025年4月15日（周二）上午9:00**开展“共建美好家园”卫生大扫除志愿活动。我们诚挚邀请您加入我们，用实际行动为社区环境贡献一份力量！  \n\n![二次元志愿者形象](https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/8c4c34b2-ab0b-4c1e-a9c4-8eb988974fd0.jpg)  \n（*图片说明：可爱的卡通志愿者形象，象征着环保行动中的温暖与活力！*）  \n\n### **活动内容**  \n1. **清理街道垃圾**：分组清扫街道两侧的垃圾、杂物，还原整洁的街道面貌。  \n2. **整理绿化带**：清除杂草、修剪绿植，让社区绿化焕发新活力。  \n3. **垃圾分类宣传**：向居民普及垃圾分类知识，倡导绿色环保生活方式。  \n\n![二次元风格志愿者](https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/63576a52-07f2-4c9d-8319-62fa03182bf2.jpg)  \n（*图片说明：充满干劲的二次元志愿者形象，展现环保行动的决心与热情！*）  \n\n### **参与方式**  \n- **集合时间**：2025年4月15日 9:00  \n- **集合地点**：XX街道办门口  \n- **自备工具**：手套、垃圾袋（街道办将提供饮用水及部分工具）  \n\n### **注意事项**  \n- 请穿着舒适衣物和运动鞋，方便劳动；  \n- 天气炎热，建议做好防晒措施；  \n- 未成年人需在家长陪同下参与。  \n\n### **活动亮点**  \n- **志愿服务证书**：所有参与者将获得街道办颁发的志愿服务证书，记录您的公益贡献！  \n- **邻里互动**：结识志同道合的社区伙伴，共同为美好家园努力！  \n\n![动漫风格志愿者](https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/4693adf8-ebd5-4596-9760-67d7a2e802bf.png)  \n（*图片说明：动漫风格的志愿者形象，传递环保行动的正能量！*）  \n\n社区是我家，清洁靠大家！您的每一份付出，都能让XX街道更加美丽。让我们携手行动，用双手打造更干净、更宜居的生活环境！  \n\n**报名方式**：可直接前往集合地点参与，或联系街道办咨询详情。期待您的加入！  \n\nXX街道办  \n2025年4月', NULL, '2025-06-07 16:53:26.813');
INSERT INTO `ai_chat_log` VALUES (1931276498463993858, '6eaf5aa08d1568f590ed64dfa85d2b3d', NULL, 'user', '文章内容太少，继续生成300字\n', '[]', '2025-06-07 17:06:13.277');
INSERT INTO `ai_chat_log` VALUES (1931276648477470721, '6eaf5aa08d1568f590ed64dfa85d2b3d', 1931276498463993858, 'system', '**共建美好家园，你我同行——XX街道卫生大扫除志愿活动全纪实**  \n\n亲爱的居民朋友们：  \n\n春意盎然，万物复苏，正是清洁家园的好时节！为全面提升社区环境卫生质量，增强居民环保意识，XX街道办将于**2025年4月15日（周二）上午9:00**开展\"共建美好家园\"大型卫生大扫除志愿活动。这是一次全民参与的环保行动，更是一次邻里互助的温情时刻！  \n\n![志愿者团队](https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/1754d872-6804-4acc-b4e4-fc6840c27056.jpg)  \n*（图：充满活力的志愿者团队整装待发）*  \n\n### **活动亮点抢先看**  \n本次大扫除活动将采取\"分区包干、责任到组\"的创新模式：  \n1. **街道美容师分队**：重点清理主干道两侧的垃圾、杂物，特别是卫生死角  \n2. **绿化护卫队**：负责修剪绿植、清除杂草，让社区绿化焕发新生  \n3. **环保宣传小分队**：通过互动游戏、宣传单页等方式普及垃圾分类知识  \n\n![环保宣传](https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/8c4c34b2-ab0b-4c1e-a9c4-8eb988974fd0.jpg)  \n*（图：志愿者正在进行环保知识宣传）*  \n\n### **参与有惊喜**  \n为感谢志愿者的辛勤付出，本次活动特别设置：  \n- **志愿服务积分**：可兑换社区便民服务  \n- **环保达人评选**：表现突出者将获得\"社区环保卫士\"称号  \n- **亲子互动专区**：专门为带孩子的家庭设置安全活动区域  \n\n### **专业保障到位**  \n为确保活动安全有序开展，街道办已做好充分准备：  \n- 配备专业环卫指导员现场指导  \n- 设置医疗应急点保障参与者安全  \n- 提供充足的饮用水和基础工具  \n\n![活动保障](https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/63576a52-07f2-4c9d-8319-62fa03182bf2.jpg)  \n*（图：专业的后勤保障团队）*  \n\n### **居民有话说**  \n\"去年参加大扫除认识了好多邻居，今年我还要来！\"——李阿姨  \n\"带孩子参加这种活动特别有意义，能培养他们的环保意识。\"——王先生  \n\n### **参与须知**  \n- 集合时间：2025年4月15日8:30-9:00  \n- 集合地点：XX街道办前广场  \n- 建议携带：遮阳帽、水杯、手套（街道提供基础工具）  \n\n社区环境的美化需要每个人的参与！让我们携手同行，用双手打造更干净、更宜居的生活环境，让XX街道成为真正的\"美丽家园\"！  \n\n**报名热线**：XXX-XXXXXXX  \n**线上报名**：扫描下方二维码  \n\nXX街道办  \n2025年4月  \n\n*（附：活动日程安排表）*  \n09:00-09:15 活动启动仪式  \n09:15-11:30 分组开展大扫除  \n11:30-12:00 总结表彰会  \n\n期待您的热情参与！让我们用实际行动诠释\"社区是我家，清洁靠大家\"的真谛！', NULL, '2025-06-07 17:06:49.051');
INSERT INTO `ai_chat_log` VALUES (1931287818349424642, '15848dfdfc770715bdf55a16f7d96644', NULL, 'user', '继续生成，不少于2000字\n', '[]', '2025-06-07 17:51:12.147');
INSERT INTO `ai_chat_log` VALUES (1931288162496262146, '15848dfdfc770715bdf55a16f7d96644', 1931287818349424642, 'system', '# 粽香传古韵 情暖聚民心——XX社区\"粽情端午\"传统文化活动圆满落幕暨成果纪实\n\n2025年6月22日，人民社区广场上彩旗飘扬，粽叶飘香，一场别开生面的\"粽情端午\"传统文化体验活动在这里热烈举行。本次活动由XX社区居委会精心策划，以\"传承文化根脉，共建和谐社区\"为主旨，通过丰富多彩的互动体验，让居民们在粽叶飘香中感受传统文化魅力，在欢声笑语中增进邻里情谊。活动吸引了社区老中青少四代居民热情参与，现场气氛热烈，成效显著，成为社区文化建设的一次成功实践。\n\n## 一、活动概况：传统节日的创新表达\n\n本次\"粽情端午\"活动于端午节当天下午14:00准时开始，持续至17:00结束。活动场地精心划分为包粽子比赛区、手工香囊制作区、龙舟模型竞赛区、文艺表演区、民俗照相馆、诗词角、美食分享区等七大功能区，每个区域都人头攒动，热闹非凡。据不完全统计，活动共吸引社区居民186户、423人次参与，年龄最大的参与者92岁，最小的仅有3岁，真正实现了全龄段覆盖。\n\n活动筹备工作历时一个月，社区成立了专门的工作小组，通过线上线下多种渠道宣传动员。特别值得一提的是，本次活动得到了区文化馆和非遗保护中心的大力支持，邀请了2位省级非遗传承人现场指导，确保了活动的专业性和文化内涵。在物资准备方面，社区共准备了糯米300斤、粽叶1500张、各类中药材50斤、手工材料200套，为活动顺利开展提供了充分保障。\n\n## 二、活动亮点：七大特色彰显创新魅力\n\n### 1. 非遗大师现场传艺，文化传承原汁原味\n\n活动现场，省级非遗项目\"XX粽子制作技艺\"传承人李凤英老师亲自示范讲解传统粽子的包制方法。李老师从选叶、泡米、填料到捆扎，一步步细致教学，让居民们领略到这门传统手艺的精妙之处。\"包粽子不只是为了吃，更是一种文化传承。\"李老师边示范边说，\"每个粽子都包含着对生活的美好祝愿。\"在她的指导下，居民们包出的粽子形状规整，棱角分明，真正体现了传统工艺的魅力。\n\n### 2. 代际互动设计巧妙，传统文化薪火相传\n\n活动特别设计了\"祖孙搭档包粽子\"环节，让老一辈将手艺传授给年轻人。现场有23对祖孙组合参与，白发苍苍的老人手把手教孙辈包粽子的温馨场景令人动容。72岁的张奶奶带着8岁的孙子小强参加了比赛，\"我小时候也是跟着奶奶学包粽子，现在能教给孙子，感觉特别有意义。\"小强则骄傲地展示自己包的粽子：\"奶奶说我的粽子比她第一次包的还要好！\"\n\n### 3. 环保理念贯穿始终，绿色端午引领风尚\n\n本次活动积极响应环保号召，所有材料均采用可降解或可循环利用的环保材质。龙舟模型制作特别使用了回收的塑料瓶、纸板等材料，由15组家庭创意改造，制作出别具一格的\"环保龙舟\"。居民王先生一家用废旧纸箱制作的龙舟还获得了\"最佳创意奖\"。\"这样的设计既培养了孩子的环保意识，又让他们了解了传统文化，一举两得。\"王先生如是说。\n\n### 4. 数字技术赋能传统，线上线下联动创新\n\n活动现场设置了\"端午文化AR体验区\"，居民通过手机扫描特制图案，就能看到立体动态的端午民俗展示，如虚拟龙舟赛、屈原故事动画等，吸引了众多年轻人参与。社区还开通了活动直播，让无法到场的居民也能远程参与，直播间互动人数达到1200余人次，点赞超过2万。\"虽然在外地工作不能回家，但通过直播看到妈妈参加包粽子比赛，感觉特别亲切。\"在外务工的社区居民小李在直播间留言道。\n\n### 5. 社区达人秀出风采，草根文化绽放光芒\n\n文艺表演环节全部由社区居民自编自演，节目单丰富多彩：社区老年大学的《端阳鼓舞》气势磅礴，少儿舞蹈班的《粽子娃娃》活泼可爱，社区戏曲社的《白蛇传·端午》选段韵味十足，还有家庭组合表演的端午诗词朗诵等。这些节目既展示了居民才艺，又传播了传统文化，现场掌声不断。表演间隙的端午知识有奖问答更是将气氛推向高潮，居民们争相抢答，展现出对传统文化的浓厚兴趣。\n\n### 6. 民俗体验沉浸立体，五感共鸣记忆深刻\n\n活动特别设置了\"五感体验端午\"环节：视觉上有古风布景和汉服展示；听觉上有传统民乐演奏；嗅觉上有艾草香囊的清香；味觉上有百家粽品尝；触觉上有手工制作体验。这种全方位的感官刺激让参与者对端午文化有了更深刻的记忆。带着两个孩子参加活动的刘女士说：\"孩子们通过看、听、闻、尝、做，全方位了解了端午节，这种学习方式他们记得特别牢。\"\n\n### 7. 志愿服务温暖人心，社区凝聚力的生动体现\n\n本次活动共有35名社区志愿者参与服务，他们中有退休教师、大学生、家庭主妇等，提前接受了专业培训，为活动提供引导、教学、后勤等全方位服务。志愿者小张是社区的大学生，他负责龙舟模型制作指导：\"看到居民们特别是孩子们专注制作的样子，感觉自己的付出特别值得。\"志愿者的热情服务成为活动顺利开展的重要保障，也展现了社区互帮互助的良好风貌。\n\n## 三、活动成效：文化传承与社区建设的双丰收\n\n### 1. 文化传承成效显著\n\n通过问卷调查显示，活动后居民对端午节文化内涵的认知度从活动前的43%提升至89%。特别是年轻群体对传统节日的兴趣明显提高，有76%的青少年参与者表示\"以后会主动了解传统节日文化\"。活动共包制粽子587个，制作香囊236个，完成龙舟模型19艘，这些实物成果既是文化传承的载体，也是居民共创的见证。\n\n### 2. 社区凝聚力明显增强\n\n活动促进了邻里交流，调查显示92%的参与者认识了新邻居，83%的居民表示\"对社区归属感增强\"。多位独居老人通过活动结识了年轻志愿者，建立了长期帮扶关系。社区王主任表示：\"看到居民们从陌生到熟悉，从点头之交到相约下次活动，这正是我们最希望看到的。\"\n\n### 3. 品牌效应初步形成\n\n本次活动通过媒体报道和居民自发传播，扩大了社区影响力。区电视台进行了专题报道，多家新媒体平台转载活动信息，社区公众号新增关注300余人。许多周边社区居民纷纷表示羡慕，询问何时也能举办类似活动。\"XX社区的端午活动\"正在成为区域文化建设的品牌项目。\n\n### 4. 创新模式值得推广\n\n活动将传统文化与现代生活方式相结合的成功实践，为社区文化建设提供了新思路。区文化局领导在观摩后表示：\"这种\'政府引导、社区搭台、居民唱戏、专业支撑\'的模式很有推广价值，既传承了文化，又凝聚了人心。\"\n\n## 四、经验总结与未来展望\n\n本次活动的成功举办，得益于以下几个关键因素：一是前期调研充分，准确把握了居民需求；二是策划专业，将文化内涵转化为可体验的活动形式；三是资源整合有力，争取到了多方支持；四是宣传到位，提高了居民参与度；五是保障周全，确保了活动安全有序。\n\n当然，活动中也存在一些不足，如部分环节人员过于集中导致体验时间不足，物资分配还可以更加精准，互动环节的深度有待加强等。这些都为今后改进提供了方向。\n\n展望未来，XX社区将以此次活动为起点，打造\"我们的节日\"系列文化活动，在中秋、重阳、春节等传统节日继续推出特色项目。同时将建立社区文化传承人队伍，培养居民骨干，使传统文化活动常态化、机制化。社区还计划建设\"邻里文化共享空间\"，为居民日常文化交流提供固定场所。\n\n粽叶飘香传古韵，情暖社区聚民心。这次\"粽情端午\"活动不仅是一次节庆聚会，更是社区文化建设的一次生动实践。它证明，传统文化完全可以以鲜活的形式融入现代生活，成为凝聚社区力量的精神纽带。正如活动结束后居民留言所说：\"这样的端午节，有味道，更有温度；有传承，更有创新。期待明年再相聚！\"\n\n未来，XX社区将继续探索传统文化与现代社区治理相结合的创新路径，让文化传承成为社区发展的内生动力，为建设和谐宜居、富有文化底蕴的现代社区而不懈努力。', NULL, '2025-06-07 17:52:34.197');
INSERT INTO `ai_chat_log` VALUES (1931368158262833153, 'f8059f8f9285d2d3576495bf265f9b79', NULL, 'user', '生成一篇宣传文章，字数不少于200', '[{\"url\": \"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/6df09749-4c2a-4db9-ba0c-18689ae51b1d.jpg\", \"description\": \"这张图片显示的是在傍晚或夜晚的海面上，四周幽暗，远处的山峦隐约可见。海面上没有明显的波浪，天空阻止了光线强射，让人感觉不太星光闪耀。模糊的天际和远处的亮光反射在水面上，形成一种静谧的氛围。\"}]', '2025-06-07 23:10:26.680');
INSERT INTO `ai_chat_log` VALUES (1931368266396184578, 'f8059f8f9285d2d3576495bf265f9b79', 1931368158262833153, 'system', '**粽情端午：社区端午节传统文化体验邀您共度佳节**\n\n端午节将至，XX社区特别策划了一场别开生面的传统文化体验活动，诚邀您与家人朋友一同参与，感受浓浓的节日氛围！\n\n**活动亮点抢先看**\n\n1. **包粽子比赛**：现场将由经验丰富的老师傅手把手教学，从粽叶折叠到糯米填充，带您体验传统美食的制作乐趣。比赛设置丰厚奖品，看看谁包的粽子又快又好！\n\n2. **手工香囊制作**：了解端午佩戴香囊的习俗渊源，亲手制作属于自己的驱邪避疫香囊。材料精美，款式多样，是送给亲友的绝佳节日礼物。\n\n3. **龙舟模型竞赛**：特别设置亲子龙舟模型制作区，家长和孩子可以一起动手，体验龙舟文化的魅力。完成后还可以参加趣味竞赛，看谁的龙舟\"划\"得最快！\n\n4. **文艺表演**：社区艺术团将带来精彩的传统文化表演，包括端午主题舞蹈、民乐演奏等，让您在欢声笑语中感受节日气氛。\n\n**活动详情**\n时间：2025年6月22日14:00-17:00\n地点：XX社区广场\n报名方式：社区服务中心现场报名或拨打XXXX-XXXX电话预约\n\n[图片插入位置：此处可插入提供的海面夜景图片，配文\"端午佳节，让我们在社区的温暖氛围中，感受如这静谧海面般深厚的传统文化底蕴。\"]\n\n**温馨提示**\n1. 活动材料有限，先到先得，建议提前报名\n2. 请穿着舒适服装参与手工活动\n3. 活动现场提供免费端午茶饮\n\n这个端午，让我们暂时远离都市喧嚣，在社区这个温暖的大家庭里，一起包粽子、做香囊、赛龙舟，感受传统文化的魅力。无论您是想要体验传统手艺，还是希望给孩子一个了解传统文化的机会，亦或是单纯想度过一个不一样的端午节，XX社区的端午活动都是您的最佳选择！\n\n报名从速，期待与您共度这个充满传统文化韵味的端午佳节！', NULL, '2025-06-07 23:10:52.470');
INSERT INTO `ai_chat_log` VALUES (1931369941332656130, 'c34cc6f4e548847ce1f52ad299b6e048', NULL, 'user', '生成一篇宣传文章，字数不少于600', '[{\"url\": \"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/a98317bf-70ac-4416-85d1-cc9b728d3caa.jpeg\", \"description\": \"这是一张围场医生站的图片，展示了社区医用健康服务。图片中的医生们身穿白色大褂，坐在红布覆盖的桌子后面，与群众进行面对面沟通。他们为前来咨询的居民提供健康服务，现场风速舒适，气氛融洽。\\n\\n图片中间是一个大型标语牌，上面写有\\\"健康幸成过社区 医院免费\\\"的醒目宣传语，进一步具体说明此次活动的主题和内容。标语上方画有一座城市的剪影，象征着健康与城市发展的关系。整个画面传递出医生与居民之间的463666444]]\"}]', '2025-06-07 23:17:31.792');
INSERT INTO `ai_chat_log` VALUES (1931370153761570818, 'c34cc6f4e548847ce1f52ad299b6e048', 1931369941332656130, 'system', '# 健康进社区：医院免费义诊活动即将温暖启幕\n\n## 活动详情\n\n**时间**：2025年4月14日（周日）全天  \n**地点**：社区活动中心（具体位置详见社区公告）  \n**主办单位**：市立医院与社区居委会联合举办  \n\n在这个春暖花开的季节，我们诚挚邀请社区居民参与这场意义非凡的健康公益活动。由市立医院专业医疗团队走进社区，为您提供全方位的免费健康服务，让优质医疗资源真正\"零距离\"服务群众。\n\n![社区医用健康服务](https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/a98317bf-70ac-4416-85d1-cc9b728d3caa.jpeg)\n*图为往期义诊活动现场，医生们耐心为居民提供健康咨询服务*\n\n## 服务内容全面升级\n\n本次义诊活动服务内容丰富多样，旨在满足不同年龄段居民的健康需求：\n\n1. **基础体检服务**  \n   - 血压检测：及时发现高血压风险  \n   - 血糖检测：筛查糖尿病隐患（需空腹）  \n   - 身高体重测量：评估基础健康指标  \n\n2. **专家健康咨询**  \n   - **内科**：感冒、发热、消化系统等常见病症咨询  \n   - **外科**：关节疼痛、外伤处理等指导  \n   - **眼科**：视力检查、眼疾初步筛查  \n   - 其他专科咨询：根据现场情况灵活安排  \n\n3. **慢性病管理指导**  \n   - 高血压患者个性化用药建议  \n   - 糖尿病患者饮食与运动方案制定  \n   - 心脑血管疾病预防知识普及  \n\n4. **健康知识普及**  \n   - 免费发放图文并茂的健康手册  \n   - 季节性流行病预防专题讲座（上午10:00/下午3:00各一场）  \n   - 急救技能现场演示（心肺复苏、海姆立克法等）  \n\n## 活动亮点\n\n1. **专家阵容强大**  \n   本次义诊特邀市立医院副主任医师及以上职称专家8名，涵盖多个临床科室，均为各领域经验丰富的骨干力量。\n\n2. **服务贴心周到**  \n   - 现场设置老年人优先通道  \n   - 提供免费饮用水和休息区  \n   - 配备轮椅等便民设施  \n\n3. **后续服务延伸**  \n   发现健康问题的居民可获得：  \n   - 医院绿色通道预约卡  \n   - 三个月内复诊免挂号费优惠  \n   - 家庭医生签约服务咨询  \n\n## 参与须知\n\n为确保活动顺利进行，请您注意以下事项：\n\n- **检测准备**：如需进行血糖检测，请保持空腹8小时以上（可自带小食品检测后食用）  \n- **资料携带**：如有既往病历、检查报告或正在服用的药物清单，请一并携带，有助于医生更准确判断  \n- **着装建议**：穿着宽松衣物，便于血压测量等检查  \n- **秩序维护**：请听从工作人员引导，有序排队候诊  \n\n## 健康生活，从预防开始\n\n现代医学证明，许多严重疾病都是由小问题逐渐发展而来。定期进行健康检查，及时了解身体状况，是预防疾病最有效的方式。然而，忙碌的生活节奏和就医的不便，常常让我们忽视了健康管理的重要性。\n\n此次\"健康进社区\"活动正是为了解决这些问题而特别策划。我们希望通过这种\"送医上门\"的形式，让更多居民能够便捷地获得专业医疗建议，培养健康意识，做到疾病早发现、早诊断、早治疗。\n\n社区居委会王主任表示：\"这已经是第三次举办此类活动了，前两次共服务居民超过500人次，发现潜在健康问题60余例，真正起到了健康\'守门人\'的作用。今年我们扩大了规模，希望能惠及更多群众。\"\n\n市立医院张副院长强调：\"作为公立医院，我们始终牢记社会责任。通过社区义诊，不仅能够普及健康知识，更能直接了解居民的健康需求，为改进医疗服务提供第一手资料。\"\n\n## 温馨提示\n\n1. 活动当日请尽早到场，避免后期人多排队  \n2. 可自带笔记本记录医生建议  \n3. 如有特殊需求（如手语翻译等），请提前联系社区工作人员  \n4. 活动全程免费，谨防任何形式的收费诈骗  \n\n健康是幸福生活的基石。4月14日，让我们相约社区活动中心，与专业医疗团队面对面，为您的健康保驾护航！转发此消息，让更多邻居受益，共建健康和谐社区！\n\n**咨询电话**：社区居委会 ××××××××  \n　　　　　市立医院客服中心 ××××××××  \n\n*注：如遇恶劣天气，活动将改期举行，具体安排另行通知。最终解释权归主办方所有。*', NULL, '2025-06-07 23:18:22.447');
INSERT INTO `ai_chat_log` VALUES (1931371872788676610, 'c34cc6f4e548847ce1f52ad299b6e048', NULL, 'user', '字数不够，继续生成200字\n', '[]', '2025-06-07 23:25:12.296');
INSERT INTO `ai_chat_log` VALUES (1931372009346826242, 'c34cc6f4e548847ce1f52ad299b6e048', 1931371872788676610, 'system', '# 健康零距离，温暖在身边——市立医院大型社区义诊活动即将启动\n\n在这生机盎然的四月天，市立医院与社区居委会携手打造的\"健康进社区\"义诊活动将于4月14日在社区活动中心温暖启幕。这是一场专为社区居民量身定制的健康盛宴，让您在家门口就能享受到三甲医院的优质医疗服务。\n\n## 专业团队走进社区\n\n本次义诊活动集结了市立医院内科、外科、眼科等八大科室的专家团队，他们将以最专业的态度为居民提供一对一的健康咨询服务。特别值得一提的是，参与此次活动的医生均为副主任医师以上职称，临床经验丰富，能够为各类健康问题提供权威解答。\n\n活动现场将设置多个服务区域，包括基础体检区、专家咨询区、健康宣教区和急救演示区。居民可以根据自身需求，有序参与各项服务。考虑到老年群体的特殊需求，现场还专门设置了老年人优先通道和休息区，并配备轮椅等便民设施。\n\n## 服务内容全面升级\n\n与往届活动相比，今年的义诊服务有了显著提升：\n\n1. **检查项目更丰富**：新增骨密度筛查和肺功能简易测试\n2. **专家阵容更强大**：特邀两位省级重点专科主任坐诊\n3. **后续服务更完善**：为需要进一步诊疗的居民开通绿色通道\n4. **健康管理更系统**：提供家庭医生签约咨询服务\n\n特别设置的\"健康大讲堂\"将分时段开展四场专题讲座，内容涵盖春季养生、慢性病管理、急救知识和心理健康等热门话题。每场讲座后还设有互动问答环节，参与者有机会获得精美健康礼品。\n\n## 您的健康，我们守护\n\n\"预防胜于治疗\"是现代健康管理的核心理念。通过定期健康检查，可以及时发现潜在的健康隐患。据统计，在去年的义诊活动中，有12%的参与者被建议到医院做进一步检查，其中早期发现糖尿病、高血压等慢性病的案例不在少数。\n\n社区居民李阿姨分享道：\"去年义诊时医生说我血压偏高建议复查，后来确诊为轻度高血压。幸亏发现得早，现在按时吃药控制得很好。这样的活动真的能救命！\"\n\n活动当天，市立医院还将推出\"健康惠民包\"，包含免费体检券、健康手册和急救包等实用物品（数量有限，先到先得）。同时，现场扫码关注医院公众号的居民，可享受后续线上问诊8折优惠。\n\n健康是人生最宝贵的财富。4月14日，让我们相约社区活动中心，给自己和家人一份健康的礼物！转发分享，让温暖传递，让健康常驻！\n\n**温馨提醒**：活动当天请携带身份证以便登记，空腹参加血糖检测的居民可自备早餐。如遇阴雨天气，请关注社区通知，活动将改在室内多功能厅举行。让我们共同期待这场充满温情的健康之约！', NULL, '2025-06-07 23:25:44.846');
INSERT INTO `ai_chat_log` VALUES (1931376175444131841, 'f58401d8434377c10ae93bf2208fd4ba', NULL, 'user', '活动已经结束，生成一篇宣传文章，宣传活动成果，字数不少于600', '[{\"url\": \"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/3d553d80-6886-496a-a9bd-6698add8d4da.jpeg\", \"description\": \"这张图片展示了一个社区组织的节日庆祝活动——中秋·社区团圆夜夜的场景。画面中央是一张摆满食物的圆桌，桌上摆放着一个点燃蜡烛的月饼蛋糕。桌子周围坐满了参加活动的居民。人们穿着轻便的夏季服，面带微笑，天花板上悬挂着三盏圆形的彩灯，营造出温馨的氛围。图片上方悬挂着红色横幅，上面写着“中秋·社区团圆夜夜”。背景是一个户外场地，天色渐晚，环境显得宁静吉祥。\"}]', '2025-06-07 23:42:18.119');
INSERT INTO `ai_chat_log` VALUES (1931376354289254401, 'f58401d8434377c10ae93bf2208fd4ba', 1931376175444131841, 'system', '# 欢乐中秋·社区团圆夜：传统佳节里的温情相聚\n\n金秋九月，丹桂飘香。2025年9月29日晚，XX社区中心花园张灯结彩，欢声笑语不断，\"欢乐中秋·社区团圆夜\"活动在这里圆满举行。这场由XX社区精心策划的中秋联欢活动，不仅弘扬了中华优秀传统文化，更为社区居民搭建了一个增进感情、共度佳节的温馨平台。\n\n## 精彩纷呈的文艺汇演\n\n傍晚六点，活动在社区居民自编自导的文艺表演中拉开帷幕。来自社区各年龄段的居民纷纷登台献艺，既有老年舞蹈队带来的《花好月圆》扇子舞，又有小朋友们表演的《玉兔捣药》童谣串烧。特别值得一提的是，社区戏曲爱好者协会带来的越剧《嫦娥奔月》选段，婉转动人的唱腔配合精美的传统服饰，赢得了阵阵掌声。\n\n\"我们排练了一个多月，就是希望能在这个特别的日子里，用传统戏曲的形式为大家送上节日的祝福。\"表演者李阿姨在演出后激动地说。舞台上的每一个节目都凝聚着居民们对传统文化的热爱和对社区大家庭的深情。\n\n## 传统与现代交融的互动体验\n\n活动现场最受欢迎的要数猜灯谜和手工DIY区域。社区工作人员精心准备了上百条与中秋相关的灯谜，既有传统谜语，也有结合现代生活的创新谜题。居民们或独自思考，或三五成群讨论，猜中后的欢呼声此起彼伏。\n\n[图片插入位置：此处插入提供的图片，展示社区居民围坐在摆满食物的圆桌旁，桌上有点燃蜡烛的月饼蛋糕，天花板上悬挂着彩灯，背景是\"中秋·社区团圆夜\"的横幅，营造出温馨祥和的节日氛围]\n\n在亲子DIY区域，孩子们在家长的指导下制作冰皮月饼和中秋灯笼。五颜六色的面团在孩子们手中变成一个个可爱的月饼，而彩纸、竹条等材料则化身为形态各异的灯笼。\"和孩子一起做月饼特别有意义，既锻炼了他的动手能力，也让他了解了传统节日的习俗。\"带着7岁儿子参加活动的张先生表示。\n\n## 团圆饭桌上的欢声笑语\n\n随着夜幕降临，活动进入了高潮部分——社区团圆饭。组织者准备了丰盛的自助餐点，居民们围坐在装饰一新的圆桌旁，分享美食，畅谈家常。图片中记录下的正是这一温馨时刻：圆桌上摆放着象征团圆的月饼蛋糕，烛光映照着每个人幸福的笑脸，天花板上悬挂的彩灯与渐暗的天色相映成趣，红色横幅上的\"中秋·社区团圆夜\"字样格外醒目。\n\n\"平时大家各忙各的，难得有这样的机会聚在一起。看着邻居们像家人一样围坐吃饭聊天，感觉特别温暖。\"独居老人王奶奶动情地说。这种邻里间的温情互动，正是社区举办此次活动的初衷。\n\n## 活动成效与社区温情\n\n据统计，本次活动吸引了近200名社区居民参与，年龄跨度从5岁儿童到80多岁长者。通过文艺表演、传统游戏和手工制作等多种形式，不仅让居民们深入了解了中秋节的文化内涵，更增进了邻里感情，强化了社区凝聚力。\n\n\"看到老老少少都玩得这么开心，我们所有的付出都值得了。\"社区主任在活动总结时表示，\"传统文化需要传承，更需要创新。我们将继续探索更多居民喜闻乐见的形式，让传统节日在现代社区焕发新的生机。\"\n\n明月当空，活动在《难忘今宵》的大合唱中落下帷幕。居民们依依不舍地道别，相约来年再聚。这场\"欢乐中秋·社区团圆夜\"活动，不仅是一次节日的庆祝，更是一次文化的传承、一次温情的传递，为构建和谐社区写下了浓墨重彩的一笔。\n\n未来，XX社区将继续以传统节日为契机，开展更多丰富多彩的文化活动，让居民在感受传统文化魅力的同时，也能享受到社区大家庭的温暖与欢乐。', NULL, '2025-06-07 23:43:00.771');
INSERT INTO `ai_chat_log` VALUES (1931376547386621953, 'f58401d8434377c10ae93bf2208fd4ba', NULL, 'user', '将该图片插入到文章中', '[{\"url\": \"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/02af1f99-e71c-4119-8812-7b102d903197.jpeg\", \"description\": \"图片中， Group help setup and set table top activities in monday mcdonalds\"}]', '2025-06-07 23:43:46.817');
INSERT INTO `ai_chat_log` VALUES (1931376714986815489, 'f58401d8434377c10ae93bf2208fd4ba', 1931376547386621953, 'system', '# 欢乐中秋·社区团圆夜：传统佳节里的温情相聚\n\n金秋九月，丹桂飘香。2025年9月29日晚，XX社区中心花园张灯结彩，欢声笑语不断，\"欢乐中秋·社区团圆夜\"活动在这里圆满举行。这场由XX社区精心策划的中秋联欢活动，不仅弘扬了中华优秀传统文化，更为社区居民搭建了一个增进感情、共度佳节的温馨平台。\n\n## 精心筹备，社区同心\n\n[图片插入位置：此处插入提供的图片，展示社区居民和工作人员一起布置活动场地，摆放桌椅，准备活动道具的场景，体现了社区团结协作的精神]\n\n活动筹备阶段就展现了社区居民的团结力量。从图片中可以看到，社区居民和工作人员一起布置活动场地，摆放桌椅，准备活动道具。大家分工合作，有的负责悬挂彩灯，有的摆放桌椅，有的准备活动道具，每个人都为活动的顺利举办贡献着自己的力量。\"看到这么多邻居自发来帮忙，真的很感动。\"社区工作人员小王说，\"这正体现了我们社区\'一家亲\'的温暖氛围。\"\n\n## 精彩纷呈的文艺汇演\n\n傍晚六点，活动在社区居民自编自导的文艺表演中拉开帷幕。来自社区各年龄段的居民纷纷登台献艺，既有老年舞蹈队带来的《花好月圆》扇子舞，又有小朋友们表演的《玉兔捣药》童谣串烧。特别值得一提的是，社区戏曲爱好者协会带来的越剧《嫦娥奔月》选段，婉转动人的唱腔配合精美的传统服饰，赢得了阵阵掌声。\n\n\"我们排练了一个多月，就是希望能在这个特别的日子里，用传统戏曲的形式为大家送上节日的祝福。\"表演者李阿姨在演出后激动地说。舞台上的每一个节目都凝聚着居民们对传统文化的热爱和对社区大家庭的深情。\n\n## 传统与现代交融的互动体验\n\n活动现场最受欢迎的要数猜灯谜和手工DIY区域。社区工作人员精心准备了上百条与中秋相关的灯谜，既有传统谜语，也有结合现代生活的创新谜题。居民们或独自思考，或三五成群讨论，猜中后的欢呼声此起彼伏。\n\n[图片插入位置：此处插入提供的图片，展示社区居民围坐在摆满食物的圆桌旁，桌上有点燃蜡烛的月饼蛋糕，天花板上悬挂着彩灯，背景是\"中秋·社区团圆夜\"的横幅，营造出温馨祥和的节日氛围]\n\n在亲子DIY区域，孩子们在家长的指导下制作冰皮月饼和中秋灯笼。五颜六色的面团在孩子们手中变成一个个可爱的月饼，而彩纸、竹条等材料则化身为形态各异的灯笼。\"和孩子一起做月饼特别有意义，既锻炼了他的动手能力，也让他了解了传统节日的习俗。\"带着7岁儿子参加活动的张先生表示。\n\n## 团圆饭桌上的欢声笑语\n\n随着夜幕降临，活动进入了高潮部分——社区团圆饭。组织者准备了丰盛的自助餐点，居民们围坐在装饰一新的圆桌旁，分享美食，畅谈家常。图片中记录下的正是这一温馨时刻：圆桌上摆放着象征团圆的月饼蛋糕，烛光映照着每个人幸福的笑脸，天花板上悬挂的彩灯与渐暗的天色相映成趣，红色横幅上的\"中秋·社区团圆夜\"字样格外醒目。\n\n\"平时大家各忙各的，难得有这样的机会聚在一起。看着邻居们像家人一样围坐吃饭聊天，感觉特别温暖。\"独居老人王奶奶动情地说。这种邻里间的温情互动，正是社区举办此次活动的初衷。\n\n## 活动成效与社区温情\n\n据统计，本次活动吸引了近200名社区居民参与，年龄跨度从5岁儿童到80多岁长者。通过文艺表演、传统游戏和手工制作等多种形式，不仅让居民们深入了解了中秋节的文化内涵，更增进了邻里感情，强化了社区凝聚力。\n\n\"看到老老少少都玩得这么开心，我们所有的付出都值得了。\"社区主任在活动总结时表示，\"传统文化需要传承，更需要创新。我们将继续探索更多居民喜闻乐见的形式，让传统节日在现代社区焕发新的生机。\"\n\n明月当空，活动在《难忘今宵》的大合唱中落下帷幕。居民们依依不舍地道别，相约来年再聚。这场\"欢乐中秋·社区团圆夜\"活动，不仅是一次节日的庆祝，更是一次文化的传承、一次温情的传递，为构建和谐社区写下了浓墨重彩的一笔。\n\n未来，XX社区将继续以传统节日为契机，开展更多丰富多彩的文化活动，让居民在感受传统文化魅力的同时，也能享受到社区大家庭的温暖与欢乐。', NULL, '2025-06-07 23:44:26.770');
INSERT INTO `ai_chat_log` VALUES (1931383974911844353, '7e35808dc3d439ed89447dcff19de565', NULL, 'user', '1111', '[]', '2025-06-08 00:13:17.659');
INSERT INTO `ai_chat_log` VALUES (1931384020780752898, '7e35808dc3d439ed89447dcff19de565', 1931383974911844353, 'system', '**共建美好家园：街道卫生大扫除志愿活动**  \n\n📅 **时间**：2025年4月15日（周二）上午9:00  \n📍 **地点**：XX街道办门口集合  \n\n🌟 **活动内容**：  \n✅ 分组清理街道两侧垃圾、杂物  \n✅ 整理绿化带，清除杂草  \n✅ 宣传垃圾分类知识，倡导环保理念  \n\n🛠️ **准备事项**：  \n🔹 自备手套、垃圾袋等工具（街道办提供饮用水及简单工具）  \n🔹 穿着舒适衣物、运动鞋，注意防晒  \n\n🎁 **活动亮点**：  \n✨ 为社区环境贡献力量，共建整洁家园  \n✨ 参与者将获得街道办颁发的**志愿服务证书**  \n\n💡 **报名方式**：  \n👉 直接前往集合点参与，或联系街道办咨询  \n\n**让我们携手行动，让XX街道更美丽！** 🌿♻️', NULL, '2025-06-08 00:13:28.601');
INSERT INTO `ai_chat_log` VALUES (1933355493737127938, '6eaf5aa08d1568f590ed64dfa85d2b3d', NULL, 'user', 'qeqew2141241', '[]', '2025-06-13 10:47:24.373');
INSERT INTO `ai_chat_log` VALUES (1933355640286109697, '6eaf5aa08d1568f590ed64dfa85d2b3d', 1933355493737127938, 'system', '**共建美好家园，你我同行——XX街道卫生大扫除志愿活动全纪实**  \n\n亲爱的居民朋友们：  \n\n春意盎然，万物复苏，正是清洁家园的好时节！为全面提升社区环境卫生质量，增强居民环保意识，XX街道办将于**2025年4月15日（周二）上午9:00**开展\"共建美好家园\"大型卫生大扫除志愿活动。这是一次全民参与的环保行动，更是一次邻里互助的温情时刻！  \n\n![志愿者团队](https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/1754d872-6804-4acc-b4e4-fc6840c27056.jpg)  \n*（图：充满活力的志愿者团队整装待发）*  \n\n### **活动亮点抢先看**  \n本次大扫除活动将采取\"分区包干、责任到组\"的创新模式：  \n1. **街道美容师分队**：重点清理主干道两侧的垃圾、杂物，特别是卫生死角  \n2. **绿化护卫队**：负责修剪绿植、清除杂草，让社区绿化焕发新生  \n3. **环保宣传小分队**：通过互动游戏、宣传单页等方式普及垃圾分类知识  \n\n![环保宣传](https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/8c4c34b2-ab0b-4c1e-a9c4-8eb988974fd0.jpg)  \n*（图：志愿者正在进行环保知识宣传）*  \n\n### **参与有惊喜**  \n为感谢志愿者的辛勤付出，本次活动特别设置：  \n- **志愿服务积分**：可兑换社区便民服务  \n- **环保达人评选**：表现突出者将获得\"社区环保卫士\"称号  \n- **亲子互动专区**：专门为带孩子的家庭设置安全活动区域  \n\n### **专业保障到位**  \n为确保活动安全有序开展，街道办已做好充分准备：  \n- 配备专业环卫指导员现场指导  \n- 设置医疗应急点保障参与者安全  \n- 提供充足的饮用水和基础工具  \n\n![活动保障](https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/63576a52-07f2-4c9d-8319-62fa03182bf2.jpg)  \n*（图：专业的后勤保障团队）*  \n\n### **居民有话说**  \n\"去年参加大扫除认识了好多邻居，今年我还要来！\"——李阿姨  \n\"带孩子参加这种活动特别有意义，能培养他们的环保意识。\"——王先生  \n\n### **参与须知**  \n- 集合时间：2025年4月15日8:30-9:00  \n- 集合地点：XX街道办前广场  \n- 建议携带：遮阳帽、水杯、手套（街道提供基础工具）  \n\n社区环境的美化需要每个人的参与！让我们携手同行，用双手打造更干净、更宜居的生活环境，让XX街道成为真正的\"美丽家园\"！  \n\n**报名热线**：XXX-XXXXXXX  \n**线上报名**：扫描下方二维码  \n\nXX街道办  \n2025年4月  \n\n*（附：活动日程安排表）*  \n09:00-09:15 活动启动仪式  \n09:15-11:30 分组开展大扫除  \n11:30-12:00 总结表彰会  \n\n期待您的热情参与！让我们用实际行动诠释\"社区是我家，清洁靠大家\"的真谛！', NULL, '2025-06-13 10:47:59.322');
INSERT INTO `ai_chat_log` VALUES (1947919459551162369, '5509c5b15ba12e9e5e8c21cb2820e4f0', NULL, 'user', '将该图片插入到宣传文章中\n', '[{\"url\": \"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/79da52c8-5b6e-4c78-9b48-ac835ac486d2.jpg\", \"description\": \"这是一张二次元风格的插画，描绘了一位银发少女。她戴着黑色的蝴蝶结头饰，身穿红黑相间的衣服，面部表情暂时 cool down 含怒 .\"}]', '2025-07-23 15:19:24.409');
INSERT INTO `ai_chat_log` VALUES (1947919527884763137, '5509c5b15ba12e9e5e8c21cb2820e4f0', 1947919459551162369, 'system', '### 活动宣传：5121348  \n\n**时间**：2025年6月30日 03:02  \n**地点**：312124  \n**简介**：一场充满奇幻与激情的特别活动！esalpm,gp[wegmnWOPIEGMN[w]epogjW-0H4I EMA[R]HPBNOERATMH03WRHBWERSH  \n\n![银发少女插画](https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/79da52c8-5b6e-4c78-9b48-ac835ac486d2.jpg)  \n（*二次元风格的银发少女，戴着黑色蝴蝶结，红黑相间的服饰，表情冷峻而充满张力*）  \n\n**活动亮点**：  \n✨ **12412421** —— 独特的视觉与感官体验！  \n✨ 神秘嘉宾与特别演出！  \n✨ 沉浸式互动环节，等你来探索！  \n\n**参与方式**：敬请关注后续公告！  \n**特别提示**：本次活动名额有限，请提前预约！  \n\n---  \n（*宣传海报设计灵感来源于插画中的银发少女，象征活动的不凡魅力*）', NULL, '2025-07-23 15:19:40.701');

-- ----------------------------
-- Table structure for ai_chat_session
-- ----------------------------
DROP TABLE IF EXISTS `ai_chat_session`;
CREATE TABLE `ai_chat_session`  (
  `session_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '会话ID（UUIDv4格式）',
  `user_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户账号（关联用户表）',
  `activity_id` int NULL DEFAULT NULL COMMENT '活动ID（关联活动表，NULL表示通用对话）',
  `title` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对话主题（前端自动生成+用户可编辑）',
  `message_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '总消息数（含用户和AI消息）',
  `created_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间（毫秒精度）',
  `updated_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后更新时间',
  PRIMARY KEY (`session_id`) USING BTREE,
  INDEX `idx_user_active`(`user_account` ASC) USING BTREE,
  INDEX `idx_activity`(`activity_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'AI对话会话元数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ai_chat_session
-- ----------------------------
INSERT INTO `ai_chat_session` VALUES ('15848dfdfc770715bdf55a16f7d96644', '123', 21, '活动粽情端午：社区端午节传统文化体验的会话', 0, '2025-06-07 00:41:24.077', '2025-06-07 00:41:24.077');
INSERT INTO `ai_chat_session` VALUES ('5509c5b15ba12e9e5e8c21cb2820e4f0', 'admin', 51, '将该图片插入到宣传文章中\n', 0, '2025-07-23 15:19:24.400', '2025-07-23 15:19:24.400');
INSERT INTO `ai_chat_session` VALUES ('6eaf5aa08d1568f590ed64dfa85d2b3d', '123', 20, '生成一篇宣', 0, '2025-06-07 15:54:48.165', '2025-06-07 15:54:48.165');
INSERT INTO `ai_chat_session` VALUES ('7e35808dc3d439ed89447dcff19de565', '123', 20, '1111', 0, '2025-06-08 00:13:17.645', '2025-06-08 00:13:17.645');
INSERT INTO `ai_chat_session` VALUES ('922c5ebc2d14fb69cdd43f08bd40767a', '123', 21, '活动粽情端午：社区端午节传统文化体验的会话', 0, '2025-06-07 01:25:27.939', '2025-06-07 01:25:27.939');
INSERT INTO `ai_chat_session` VALUES ('c34cc6f4e548847ce1f52ad299b6e048', '1234', 22, '生成一篇宣', 0, '2025-06-07 23:17:31.785', '2025-06-07 23:17:31.785');
INSERT INTO `ai_chat_session` VALUES ('f58401d8434377c10ae93bf2208fd4ba', '1234', 24, '活动已经结', 0, '2025-06-07 23:42:18.115', '2025-06-07 23:42:18.115');
INSERT INTO `ai_chat_session` VALUES ('f8059f8f9285d2d3576495bf265f9b79', '123', 21, '生成一篇宣', 0, '2025-06-07 23:10:26.674', '2025-06-07 23:10:26.674');

-- ----------------------------
-- Table structure for announcement
-- ----------------------------
DROP TABLE IF EXISTS `announcement`;
CREATE TABLE `announcement`  (
  `announcement_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公告ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公告标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公告文本内容',
  `images` json NULL COMMENT '图片链接数组（单张或最多5张）',
  `created_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '发布时间',
  `admin_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '发布管理员账号',
  PRIMARY KEY (`announcement_id`) USING BTREE,
  INDEX `fk_admin_account`(`admin_account` ASC) USING BTREE,
  CONSTRAINT `fk_admin_account` FOREIGN KEY (`admin_account`) REFERENCES `user_table` (`user_account`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `chk_images_limit` CHECK (json_length(`images`) <= 5)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of announcement
-- ----------------------------
INSERT INTO `announcement` VALUES ('4aef10ce05530bb2ac924eb8fe8fc0b8', '防溺水', '暑期来临，青少年溺水风险高发，希望各位能够看管好自家孩子！', '[\"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20b88d4f-a7da-433e-96a6-25312846934c.jpg\"]', '2025-07-23 15:42:21.481', 'admin');
INSERT INTO `announcement` VALUES ('ann_001', '系统维护通知', '系统将于本周六晚上10点进行维护，预计维护时间2小时，期间可能影响部分功能使用，请大家提前做好准备。', '[\"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/c8f45ef4-a4ed-45e4-a620-eb0f30512527.jpg\"]', '2024-01-15 10:00:00.000', 'admin');
INSERT INTO `announcement` VALUES ('ann_002', '新功能上线公告', '我们很高兴地宣布，新的AI智能对话功能已经正式上线！用户现在可以与AI助手进行更加智能的对话交流。', '[]', '2024-01-14 14:30:00.000', 'admin');
INSERT INTO `announcement` VALUES ('ann_003', '活动报名开始', '春季户外活动报名现已开始，活动时间为3月15日，地点在市中心公园。欢迎大家踊跃报名参加！', '[]', '2024-01-13 09:15:00.000', 'admin');

-- ----------------------------
-- Table structure for friend_message
-- ----------------------------
DROP TABLE IF EXISTS `friend_message`;
CREATE TABLE `friend_message`  (
  `message_id` int NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `sender_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '发送方账号',
  `receiver_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '接收方账号',
  `text_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '文字内容',
  `image_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片链接（单张）',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`message_id`) USING BTREE,
  INDEX `fk_message_receiver`(`receiver_account` ASC) USING BTREE,
  INDEX `idx_sender_receiver`(`sender_account` ASC, `receiver_account` ASC) USING BTREE,
  INDEX `idx_created_time`(`created_at` ASC) USING BTREE,
  CONSTRAINT `fk_message_receiver` FOREIGN KEY (`receiver_account`) REFERENCES `user_table` (`user_account`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_message_sender` FOREIGN KEY (`sender_account`) REFERENCES `user_table` (`user_account`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `chk_content_not_null` CHECK ((`text_content` is not null) or (`image_url` is not null))
) ENGINE = InnoDB AUTO_INCREMENT = 132 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '好友消息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of friend_message
-- ----------------------------
INSERT INTO `friend_message` VALUES (1, '1234', '123', '深恶，么？', NULL, '2025-03-24 01:14:29');
INSERT INTO `friend_message` VALUES (2, '1234', '123', '啊？', NULL, '2025-03-24 01:14:46');
INSERT INTO `friend_message` VALUES (3, '123', '1234', '什么?', NULL, '2025-03-24 01:42:54');
INSERT INTO `friend_message` VALUES (4, '123', '1234', '12421421', '', '2025-03-26 20:53:49');
INSERT INTO `friend_message` VALUES (71, '123', '111', '我们已经是好友了,开始了聊天吧', '', '2025-03-29 00:49:10');
INSERT INTO `friend_message` VALUES (72, '111', '123', '这是一个测试', '', '2025-03-29 00:51:12');
INSERT INTO `friend_message` VALUES (75, '123', '1234', '21412412', '', '2025-03-29 18:10:26');
INSERT INTO `friend_message` VALUES (76, '1234', '123', '12421421', '', '2025-03-29 18:10:29');
INSERT INTO `friend_message` VALUES (77, '123', '1234', '1241', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250329182433.jpg', '2025-03-29 18:24:55');
INSERT INTO `friend_message` VALUES (78, '123', '1234', '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250329182533.jpg', '2025-03-29 18:25:36');
INSERT INTO `friend_message` VALUES (79, '123', '1234', '124124', NULL, '2025-03-29 18:25:43');
INSERT INTO `friend_message` VALUES (80, '123', '1234', '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250329182545.jpg', '2025-03-29 18:25:48');
INSERT INTO `friend_message` VALUES (81, '123', '1234', '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250329183129.jpg', '2025-03-29 18:31:32');
INSERT INTO `friend_message` VALUES (82, '123', '1234', '12421', NULL, '2025-03-29 18:32:40');
INSERT INTO `friend_message` VALUES (83, '123', '111', '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250329183302.jpg', '2025-03-29 18:33:03');
INSERT INTO `friend_message` VALUES (84, '123', '111', '111', NULL, '2025-03-29 18:35:59');
INSERT INTO `friend_message` VALUES (85, '1234', '123', '1', NULL, '2025-03-29 19:25:56');
INSERT INTO `friend_message` VALUES (87, '123', '1234', '12412412', NULL, '2025-03-30 23:03:46');
INSERT INTO `friend_message` VALUES (94, '555', 'admin', '我们已经是好友了,开始了聊天吧', '', '2025-04-02 19:44:30');
INSERT INTO `friend_message` VALUES (95, '1234', '123', '1212312', NULL, '2025-04-09 20:09:42');
INSERT INTO `friend_message` VALUES (96, '123', '1234', '12321321', NULL, '2025-04-15 15:30:39');
INSERT INTO `friend_message` VALUES (97, '123', 'admin', '我们已经是好友了,开始了聊天吧', '', '2025-04-16 18:10:15');
INSERT INTO `friend_message` VALUES (98, '123', 'admin', '12312312', NULL, '2025-04-16 18:10:24');
INSERT INTO `friend_message` VALUES (100, 'admin', '123', '🔥我分享了活动《便民服务日·免费理发 & 家电维修》 社区联合志愿者团队，为居民提供免费理发、小家电维修服务！让便民服务走进家门口，省心又暖心！ 👉点击查看详情：http://localhost:8080/u/ActivityDetailPage?id=25', NULL, '2025-04-16 18:54:39');
INSERT INTO `friend_message` VALUES (101, '123', '1234', '1111', NULL, '2025-05-13 17:19:00');
INSERT INTO `friend_message` VALUES (102, '1234', '123', '你好啊', NULL, '2025-05-13 17:21:18');
INSERT INTO `friend_message` VALUES (103, '1234', '123', '最近怎么样', NULL, '2025-05-13 17:21:40');
INSERT INTO `friend_message` VALUES (104, '123', '1234', '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/dad72c2c-bf2c-430f-b6f6-2f411a9b378a.jpg', '2025-05-13 17:42:30');
INSERT INTO `friend_message` VALUES (105, '123', '1234', '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/36df9d37-0365-4ca3-94f8-22c4b0a3dcfb.png', '2025-05-13 17:50:12');
INSERT INTO `friend_message` VALUES (106, '123', '1234', '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/82e7707d-f79d-427c-8811-941fc02eb409.jpg', '2025-05-13 17:53:55');
INSERT INTO `friend_message` VALUES (107, '123', '1234', '你好', '', '2025-05-13 23:01:35');
INSERT INTO `friend_message` VALUES (108, '1234', '123', '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/c45cded3-cd46-4742-af67-52381b3ee9b7.png', '2025-05-14 00:05:14');
INSERT INTO `friend_message` VALUES (109, '123', '111', '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/dcfb50a9-ed6e-4255-96bc-5b58b10f3a82.jpg', '2025-05-14 01:40:07');
INSERT INTO `friend_message` VALUES (110, '123', '1234', '111', '', '2025-05-14 01:51:18');
INSERT INTO `friend_message` VALUES (111, '123', '1234', '12312321', '', '2025-05-14 19:11:08');
INSERT INTO `friend_message` VALUES (112, '123', '1234', '12312312', '', '2025-05-14 19:11:47');
INSERT INTO `friend_message` VALUES (113, '123', '1234', '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/e2e2b27d-1071-470f-b8b9-88af2a060102.png', '2025-05-24 05:18:55');
INSERT INTO `friend_message` VALUES (114, '123', '1234', '2131', NULL, '2025-05-24 05:18:58');
INSERT INTO `friend_message` VALUES (115, '123', '1234', '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/5c0fec5e-49fb-4f1e-b6e1-adf453a9c2f3.jpg', '2025-06-10 02:27:46');
INSERT INTO `friend_message` VALUES (116, '123', '1234', '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/12686084-cfc0-4a15-8d92-498e52c1ed3d.png', '2025-06-10 02:27:50');
INSERT INTO `friend_message` VALUES (117, '123', '111', '1212312', '', '2025-06-10 03:03:12');
INSERT INTO `friend_message` VALUES (118, '123', 'admin', '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/daaf201f-eefd-49b0-b4d0-73c7c76b46e2.jpg', '2025-06-13 10:18:11');
INSERT INTO `friend_message` VALUES (119, '123', '1234', '2412412', '', '2025-06-13 10:30:45');
INSERT INTO `friend_message` VALUES (120, 'admin', '555', '234324', '', '2025-06-13 10:46:42');
INSERT INTO `friend_message` VALUES (121, 'admin', '555', '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/f08461d3-4360-448d-8c42-fe1850862f06.jpg', '2025-06-20 08:50:11');
INSERT INTO `friend_message` VALUES (122, '123', '1234', '111', '', '2025-06-20 08:54:38');
INSERT INTO `friend_message` VALUES (123, '123', '333', '123', '', '2025-06-20 08:54:47');
INSERT INTO `friend_message` VALUES (124, '123', '1234', '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/c83ef6bf-1e78-4bb4-8ccd-ffb53f31dea1.jpg', '2025-06-20 08:56:34');
INSERT INTO `friend_message` VALUES (125, '123', '222', '我们已经是好友了,开始了聊天吧', '', '2025-06-20 09:39:53');
INSERT INTO `friend_message` VALUES (126, '123', '444', '我们已经是好友了,开始了聊天吧', '', '2025-06-20 09:39:54');
INSERT INTO `friend_message` VALUES (127, '123', '1234', '1231', '', '2025-07-22 15:28:28');
INSERT INTO `friend_message` VALUES (128, '123', '1234', '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/f2573250-97d7-4142-90f0-e04213bcccc2.png', '2025-07-22 15:28:34');
INSERT INTO `friend_message` VALUES (129, '123', 'admin', '你好', '', '2025-07-23 15:24:33');
INSERT INTO `friend_message` VALUES (130, 'admin', '123', '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/314b450e-e048-46ac-b258-a57240c108e2.jpg', '2025-07-23 15:31:52');
INSERT INTO `friend_message` VALUES (131, 'admin', '123', '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/211f9682-17b1-473b-b48e-34a183532880.jpg', '2025-07-23 15:34:13');

-- ----------------------------
-- Table structure for friend_relationship
-- ----------------------------
DROP TABLE IF EXISTS `friend_relationship`;
CREATE TABLE `friend_relationship`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '好友关系ID',
  `user_account1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户账号1',
  `user_account2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户账号2',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_user_pair`(`user_account1` ASC, `user_account2` ASC) USING BTREE,
  INDEX `idx_user_account1`(`user_account1` ASC) USING BTREE,
  INDEX `idx_user_account2`(`user_account2` ASC) USING BTREE,
  CONSTRAINT `fk_user1` FOREIGN KEY (`user_account1`) REFERENCES `user_table` (`user_account`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user2` FOREIGN KEY (`user_account2`) REFERENCES `user_table` (`user_account`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '好友关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of friend_relationship
-- ----------------------------
INSERT INTO `friend_relationship` VALUES (1, '123', '1234', '2025-03-24 01:11:52');
INSERT INTO `friend_relationship` VALUES (4, '123', '111', '2025-03-29 00:49:09');
INSERT INTO `friend_relationship` VALUES (5, '123', '333', '2025-03-29 01:12:29');
INSERT INTO `friend_relationship` VALUES (6, '555', 'admin', '2025-04-02 19:44:29');
INSERT INTO `friend_relationship` VALUES (7, '123', 'admin', '2025-04-16 18:10:15');
INSERT INTO `friend_relationship` VALUES (8, '123', '222', '2025-06-20 09:39:53');
INSERT INTO `friend_relationship` VALUES (9, '123', '444', '2025-06-20 09:39:53');

-- ----------------------------
-- Table structure for friend_request
-- ----------------------------
DROP TABLE IF EXISTS `friend_request`;
CREATE TABLE `friend_request`  (
  `request_id` int NOT NULL AUTO_INCREMENT COMMENT '请求ID',
  `sender_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求方账号',
  `receiver_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '接收方账号',
  `status` tinyint NULL DEFAULT 0 COMMENT '请求状态（0=待处理, 1=已接受, 2=已拒绝）',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '请求时间',
  PRIMARY KEY (`request_id`) USING BTREE,
  UNIQUE INDEX `chk_request_unique`(`sender_account` ASC, `receiver_account` ASC) USING BTREE,
  INDEX `fk_request_receiver`(`receiver_account` ASC) USING BTREE,
  CONSTRAINT `fk_request_receiver` FOREIGN KEY (`receiver_account`) REFERENCES `user_table` (`user_account`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_request_sender` FOREIGN KEY (`sender_account`) REFERENCES `user_table` (`user_account`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '好友请求表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of friend_request
-- ----------------------------
INSERT INTO `friend_request` VALUES (11, '111', '123', 1, '2025-03-27 10:04:31');
INSERT INTO `friend_request` VALUES (12, '222', '123', 1, '2025-03-27 23:53:45');
INSERT INTO `friend_request` VALUES (13, '333', '123', 1, '2025-03-27 23:53:56');
INSERT INTO `friend_request` VALUES (17, '444', '123', 1, '2025-03-29 01:22:11');
INSERT INTO `friend_request` VALUES (18, 'admin', '555', 1, '2025-04-02 19:44:25');
INSERT INTO `friend_request` VALUES (19, 'admin', '123', 1, '2025-04-16 18:10:11');

-- ----------------------------
-- Table structure for group
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group`  (
  `group_id` int NOT NULL AUTO_INCREMENT COMMENT '群组ID',
  `group_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '群名称',
  `owner_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '群主账号',
  `announcement` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '群公告',
  `avatar` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '群头像链接',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态（1=正常, 0=已解散）',
  `activity_id` int NULL DEFAULT NULL COMMENT '活动ID',
  PRIMARY KEY (`group_id`) USING BTREE,
  INDEX `idx_group_status`(`status` ASC) USING BTREE,
  INDEX `idx_group_owner`(`owner_account` ASC) USING BTREE,
  INDEX `fk_group_activity_id`(`activity_id` ASC) USING BTREE,
  CONSTRAINT `fk_group_activity_id` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_group_owner` FOREIGN KEY (`owner_account`) REFERENCES `user_table` (`user_account`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '群组信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group
-- ----------------------------
INSERT INTO `group` VALUES (11, '共建美好家园：街道卫生大扫除志愿活动活动群', '123', '123213', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250415153256.jpg', NULL, 1, 20);
INSERT INTO `group` VALUES (12, '粽情端午：社区端午节传统文化体验活动群', '123', '1111', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/dbb264c4-fcad-478b-abf9-44ac2ec0be99.jpeg', NULL, 1, 21);
INSERT INTO `group` VALUES (13, '健康进社区：医院免费义诊活动活动群', '1234', NULL, NULL, NULL, 1, 22);
INSERT INTO `group` VALUES (14, '美丽家园·社区环境清洁日活动群', '1234', NULL, NULL, NULL, 1, 23);
INSERT INTO `group` VALUES (15, '欢乐中秋·社区团圆夜活动群', '1234', NULL, NULL, NULL, 1, 24);
INSERT INTO `group` VALUES (16, '便民服务日·免费理发 & 家电维修活动群', '1234', NULL, NULL, NULL, 1, 25);
INSERT INTO `group` VALUES (19, '123活动群', '123456', NULL, NULL, NULL, 1, 28);
INSERT INTO `group` VALUES (22, 'taaaaaaest活动群', '123', '暂无', NULL, NULL, 1, 31);
INSERT INTO `group` VALUES (38, '1活动群', '30', NULL, NULL, NULL, 1, 47);
INSERT INTO `group` VALUES (40, ' DS活动群', '35', NULL, NULL, NULL, 1, 49);
INSERT INTO `group` VALUES (41, '518活动群', 'admin', NULL, NULL, NULL, 1, 50);

-- ----------------------------
-- Table structure for group_member
-- ----------------------------
DROP TABLE IF EXISTS `group_member`;
CREATE TABLE `group_member`  (
  `member_id` int NOT NULL AUTO_INCREMENT COMMENT '成员关系ID',
  `group_id` int NOT NULL COMMENT '群组ID',
  `user_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户账号',
  `role` tinyint NULL DEFAULT 2 COMMENT '角色（0=群主, 1=管理员, 2=成员）',
  `joined_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
  `last_active` datetime NULL DEFAULT NULL COMMENT '最后活跃时间',
  PRIMARY KEY (`member_id`) USING BTREE,
  UNIQUE INDEX `uniq_group_member`(`group_id` ASC, `user_account` ASC) USING BTREE,
  INDEX `idx_member_group`(`group_id` ASC) USING BTREE,
  INDEX `idx_member_user`(`user_account` ASC) USING BTREE,
  CONSTRAINT `fk_member_group` FOREIGN KEY (`group_id`) REFERENCES `group` (`group_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_member_user` FOREIGN KEY (`user_account`) REFERENCES `user_table` (`user_account`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 78 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '群成员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_member
-- ----------------------------
INSERT INTO `group_member` VALUES (30, 11, '123', 0, NULL, NULL);
INSERT INTO `group_member` VALUES (31, 12, '123', 0, NULL, NULL);
INSERT INTO `group_member` VALUES (32, 13, '1234', 0, NULL, NULL);
INSERT INTO `group_member` VALUES (33, 14, '1234', 0, NULL, NULL);
INSERT INTO `group_member` VALUES (34, 15, '1234', 0, NULL, NULL);
INSERT INTO `group_member` VALUES (35, 16, '1234', 0, NULL, NULL);
INSERT INTO `group_member` VALUES (47, 19, '123456', 0, NULL, NULL);
INSERT INTO `group_member` VALUES (53, 22, '123', 0, NULL, NULL);
INSERT INTO `group_member` VALUES (70, 38, '30', 0, NULL, NULL);
INSERT INTO `group_member` VALUES (72, 40, '35', 0, NULL, NULL);
INSERT INTO `group_member` VALUES (74, 15, '123', 2, NULL, NULL);
INSERT INTO `group_member` VALUES (76, 41, 'admin', 0, NULL, NULL);
INSERT INTO `group_member` VALUES (77, 16, 'admin', 2, NULL, NULL);

-- ----------------------------
-- Table structure for group_message
-- ----------------------------
DROP TABLE IF EXISTS `group_message`;
CREATE TABLE `group_message`  (
  `message_id` int NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `sender_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '发送方账号',
  `group_id` bigint NOT NULL COMMENT '群组ID',
  `text_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '文字内容',
  `image_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片链接（单张）',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`message_id`) USING BTREE,
  INDEX `fk_group_message_sender`(`sender_account` ASC) USING BTREE,
  INDEX `idx_group_message_time`(`created_at` ASC) USING BTREE,
  INDEX `idx_group_member`(`group_id` ASC, `sender_account` ASC) USING BTREE,
  CONSTRAINT `fk_group_message_sender` FOREIGN KEY (`sender_account`) REFERENCES `user_table` (`user_account`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `chk_group_content_not_null` CHECK ((`text_content` is not null) or (`image_url` is not null))
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '群聊消息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_message
-- ----------------------------
INSERT INTO `group_message` VALUES (34, '123', 11, 'hello', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/ 20250409200748.png', '2025-04-09 20:07:53');
INSERT INTO `group_message` VALUES (35, '123', 11, '123213', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/ 20250415153102.png', '2025-04-15 15:31:04');
INSERT INTO `group_message` VALUES (36, '123', 11, '231213', NULL, '2025-04-15 15:31:06');
INSERT INTO `group_message` VALUES (37, '123', 11, '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/ 20250415153109.jpg', '2025-04-15 15:31:10');
INSERT INTO `group_message` VALUES (38, '123456', 16, '12412421214', NULL, '2025-04-26 16:51:28');
INSERT INTO `group_message` VALUES (39, '123456', 16, '124124214', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/ 20250426165134.jpg', '2025-04-26 16:51:35');
INSERT INTO `group_message` VALUES (40, '1234', 15, 'hello', '', '2025-05-14 00:39:24');
INSERT INTO `group_message` VALUES (41, '123', 11, '1231231', '', '2025-05-14 01:41:55');
INSERT INTO `group_message` VALUES (42, '1234', 14, '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/ 590235c8-1963-48d5-b430-df3e7b08fe5c.jpg', '2025-05-14 01:55:30');
INSERT INTO `group_message` VALUES (43, '35', 40, '1', NULL, '2025-06-09 17:46:45');
INSERT INTO `group_message` VALUES (44, '123', 12, '1111', '', '2025-06-10 02:26:45');
INSERT INTO `group_message` VALUES (45, '123', 11, '12321', '', '2025-06-10 02:54:44');
INSERT INTO `group_message` VALUES (46, '123', 11, '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/1687d3e9-bdbf-4f3a-9de6-5818f90f9ba3.jpg', '2025-06-10 02:55:20');
INSERT INTO `group_message` VALUES (47, '123', 11, '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/0bb488f2-e8c6-497d-8cf8-fcad7a0150a1.jpg', '2025-06-10 03:03:05');
INSERT INTO `group_message` VALUES (48, '123', 12, '2312312', '', '2025-06-10 03:03:16');
INSERT INTO `group_message` VALUES (49, 'admin', 16, '221312312', '', '2025-07-22 15:26:21');

-- ----------------------------
-- Table structure for repair_request
-- ----------------------------
DROP TABLE IF EXISTS `repair_request`;
CREATE TABLE `repair_request`  (
  `request_id` bigint NOT NULL AUTO_INCREMENT,
  `user_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户账号',
  `appliance_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '家电类型（如冰箱、洗衣机等）',
  `appliance_model` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '家电型号',
  `problem_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '问题描述',
  `evidence_images` json NULL COMMENT '证据图片（最多5张）',
  `request_status` enum('pending','assigned','in_progress','completed','rejected') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'pending' COMMENT '处理状态',
  `technician_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分配的维修员账号',
  `created_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '提交时间',
  `assigned_at` datetime(3) NULL DEFAULT NULL COMMENT '分配时间',
  `completed_at` datetime(3) NULL DEFAULT NULL COMMENT '完成时间',
  PRIMARY KEY (`request_id`) USING BTREE,
  CONSTRAINT `chk_description_length_repair` CHECK (char_length(`problem_description`) >= 10),
  CONSTRAINT `chk_evidence_limit_repair` CHECK (json_length(`evidence_images`) <= 5)
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '家电报修请求表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of repair_request
-- ----------------------------
INSERT INTO `repair_request` VALUES (1, 'admin', '冰箱', '洗衣机', '124214142142412142412', '[]', 'completed', '444', '2025-06-15 03:19:16.841', '2025-06-15 04:03:02.092', '2025-06-15 04:13:28.401');
INSERT INTO `repair_request` VALUES (2, 'admin', '冰箱', '洗衣机', '124124121223513252523253`523`52`523`', '[\"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/274d28e9-ec7a-42bc-a3df-5a355031fb3d.jpg\", \"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/ad5bd95c-acbf-43f4-87f7-3737e712d453.jpg\"]', 'rejected', NULL, '2025-06-15 04:01:15.737', NULL, NULL);
INSERT INTO `repair_request` VALUES (3, '123', 'test', 'test', '111123412412412412', '[\"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/5ac706cb-faf4-4bed-8dff-cbbaf1e75b97.jpg\"]', 'assigned', '444', '2025-06-20 09:33:36.910', '2025-07-22 15:22:10.350', NULL);
INSERT INTO `repair_request` VALUES (4, 'admin', '空调', 'asfdw', '坏了，在5A101dda', '[\"https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/0ee82edb-5d57-495c-a034-9b2d339ecc1a.png\"]', 'pending', NULL, '2025-07-23 15:21:03.973', NULL, NULL);

-- ----------------------------
-- Table structure for service_review
-- ----------------------------
DROP TABLE IF EXISTS `service_review`;
CREATE TABLE `service_review`  (
  `review_id` bigint NOT NULL AUTO_INCREMENT,
  `request_id` bigint NOT NULL COMMENT '关联的报修请求ID',
  `rating` decimal(3, 1) NOT NULL DEFAULT 0.0 COMMENT '评分（0.0-5.0）',
  `review_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '评价内容',
  `image_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评价配图（单张）',
  `created_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '评价时间',
  PRIMARY KEY (`review_id`) USING BTREE,
  CONSTRAINT `chk_review_content` CHECK ((`review_text` is not null) or (`image_url` is not null))
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '家电维修评价表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of service_review
-- ----------------------------
INSERT INTO `service_review` VALUES (1, 1, 5.0, '完美', '', '2025-06-15 04:13:49.043');
INSERT INTO `service_review` VALUES (2, 1, 5.0, '12211212', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/a27bc16c-edd8-4be0-bbf6-a5f3a15ee558.jpg', '2025-06-15 04:14:13.506');
INSERT INTO `service_review` VALUES (3, 1, 5.0, '23123123', '', '2025-06-15 04:15:43.550');
INSERT INTO `service_review` VALUES (4, 1, 5.0, '213213321', '', '2025-06-15 04:15:54.838');
INSERT INTO `service_review` VALUES (5, 1, 5.0, '21312312', '', '2025-06-15 04:17:13.230');

-- ----------------------------
-- Table structure for user_follow
-- ----------------------------
DROP TABLE IF EXISTS `user_follow`;
CREATE TABLE `user_follow`  (
  `follow_id` bigint NOT NULL AUTO_INCREMENT COMMENT '关注ID',
  `follower_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '关注者用户ID',
  `following_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '被关注者用户ID',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '关注时间',
  PRIMARY KEY (`follow_id`) USING BTREE,
  UNIQUE INDEX `uk_follower_following`(`follower_id` ASC, `following_id` ASC) USING BTREE,
  INDEX `idx_follower`(`follower_id` ASC) USING BTREE,
  INDEX `idx_following`(`following_id` ASC) USING BTREE,
  CONSTRAINT `fk_follower_user` FOREIGN KEY (`follower_id`) REFERENCES `user_table` (`user_account`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_following_user` FOREIGN KEY (`following_id`) REFERENCES `user_table` (`user_account`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `chk_not_self_follow` CHECK (`follower_id` <> `following_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户关注关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_follow
-- ----------------------------
INSERT INTO `user_follow` VALUES (22, '222', '123', NULL);
INSERT INTO `user_follow` VALUES (23, '222', '1234', NULL);
INSERT INTO `user_follow` VALUES (25, '333', '123', NULL);
INSERT INTO `user_follow` VALUES (36, '123', '333', NULL);
INSERT INTO `user_follow` VALUES (37, '444', '123', NULL);
INSERT INTO `user_follow` VALUES (39, '1234', '123', NULL);
INSERT INTO `user_follow` VALUES (40, 'admin', '1234', NULL);
INSERT INTO `user_follow` VALUES (44, '1234', '222', NULL);
INSERT INTO `user_follow` VALUES (48, 'admin', '35', NULL);
INSERT INTO `user_follow` VALUES (49, '123', '123456', NULL);
INSERT INTO `user_follow` VALUES (50, '123', '444', NULL);

-- ----------------------------
-- Table structure for user_table
-- ----------------------------
DROP TABLE IF EXISTS `user_table`;
CREATE TABLE `user_table`  (
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `user_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `user_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `user_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像链接',
  `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '在线状态',
  `user_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `role` tinyint NOT NULL DEFAULT 0 COMMENT '用户身份（0: 普通用户, 1: 管理员, 2: 维修员）',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`user_account`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_table
-- ----------------------------
INSERT INTO `user_table` VALUES ('归云', '111', 'OpUezXaiYFZcjvoPGsbyLQ==', '111', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250329015130.jpg', 'offline', '123', 0, '2024-02-09 00:00:01');
INSERT INTO `user_table` VALUES ('Onaffair', '123', 'OpUezXaiYFZcjvoPGsbyLQ==', 'Onaffair@outlook.com', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/1748600725882.jpg', 'online', '123', 0, '2024-10-18 13:25:50');
INSERT INTO `user_table` VALUES ('安逸111', '1234', 'OpUezXaiYFZcjvoPGsbyLQ==', '1234', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/03e99920-03eb-417e-8e4e-677e48f22ec3.jpg', 'offline', '1234', 0, '2024-04-27 00:00:01');
INSERT INTO `user_table` VALUES ('Onaffair', '123456', 'CfjJ8FUvRZBXBBVw26y7Ng==', 'aa@123.com', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250426164710.jpg', 'offline', '13738829253', 0, '2025-04-26 16:48:02');
INSERT INTO `user_table` VALUES ('瑾年', '222', 'OpUezXaiYFZcjvoPGsbyLQ==', '222', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250329015140.png', 'offline', '222', 0, '2024-12-21 00:00:01');
INSERT INTO `user_table` VALUES ('27', '27', 'OpUezXaiYFZcjvoPGsbyLQ==', '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/fb696a45-3d32-4aac-97ac-5affe1b78f65.jpeg', 'offline', '1', 0, '2025-06-09 17:27:30');
INSERT INTO `user_table` VALUES ('30', '30', 'OpUezXaiYFZcjvoPGsbyLQ==', '30@qq.com', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/e2574a42-7039-4051-a031-5551c5299b6c.jpeg', 'offline', '12345678901', 0, '2025-06-09 17:21:03');
INSERT INTO `user_table` VALUES ('31', '31', 'OpUezXaiYFZcjvoPGsbyLQ==', '31@qq.com', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/b56ad4a7-8f95-4b97-8520-0bf4c6364142.jpeg', 'offline', '12345678901', 0, '2025-06-09 17:22:58');
INSERT INTO `user_table` VALUES ('32', '32', 'hF25YRoHCsvjXo3hSwN2sg8JkJ8LNOt0kipCuG3BfLI=', '32@qq.com', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/37a89637-f40b-4a05-857d-17f562657e41.jpeg', 'offline', '12345678903', 0, '2025-06-09 17:24:02');
INSERT INTO `user_table` VALUES ('33', '33', 'rALWgg9JVAttXYSsyzmpcg==', '33@qq.com', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/bb3ba405-1393-4f94-aa91-f0e998d22c0d.jpeg', 'offline', '12345678904', 0, '2025-06-09 17:24:37');
INSERT INTO `user_table` VALUES ('菜鸟', '333', 'OpUezXaiYFZcjvoPGsbyLQ==', '333', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250329015151.jpg', 'offline', '123', 0, '2024-03-14 00:00:01');
INSERT INTO `user_table` VALUES ('34', '34', 'OpUezXaiYFZcjvoPGsbyLQ==', '1', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/3c334f95-ed24-48c7-873c-6ae95c5b061d.jpeg', 'online', '12345678905', 0, '2025-06-09 17:25:00');
INSERT INTO `user_table` VALUES ('35', '35', 'OpUezXaiYFZcjvoPGsbyLQ==', '1', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/776d2c62-ea4d-4a01-b693-9f2d132a709d.jpeg', 'online', '1234567890', 0, '2025-06-09 17:25:30');
INSERT INTO `user_table` VALUES ('36', '36', 'OpUezXaiYFZcjvoPGsbyLQ==', '1', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/b66c0d63-c6dd-44da-a88e-955b0976ec25.jpeg', 'offline', '123456789012', 2, '2025-06-09 17:26:06');
INSERT INTO `user_table` VALUES ('37', '37', 'OpUezXaiYFZcjvoPGsbyLQ==', '1', '', 'offline', '1', 0, '2025-06-09 17:26:33');
INSERT INTO `user_table` VALUES ('38', '38', 'OpUezXaiYFZcjvoPGsbyLQ==', '1', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/27812e19-a9aa-4e59-96f0-b0a2d00e5b05.jpeg', 'offline', '', 2, '2025-06-09 17:28:01');
INSERT INTO `user_table` VALUES ('40', '40', 'OpUezXaiYFZcjvoPGsbyLQ==', '30@qq.com', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/1cb20692-bca8-4f19-9784-e4dd48dc8f96.jpeg', 'offline', '12345678902', 0, '2025-06-09 17:22:21');
INSERT INTO `user_table` VALUES ('八嘎', '444', 'OpUezXaiYFZcjvoPGsbyLQ==', '444', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250329015200.jpg', 'offline', '123', 2, '2024-07-20 00:00:01');
INSERT INTO `user_table` VALUES ('哈基咪', '555', 'OpUezXaiYFZcjvoPGsbyLQ==', '123', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250402194332.jpg', 'offline', '123', 0, '2024-11-29 00:00:01');
INSERT INTO `user_table` VALUES ('kid', '666', 'OpUezXaiYFZcjvoPGsbyLQ==', '777', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250402203211.gif', 'offline', 'kid', 0, '2025-04-02 20:32:13');
INSERT INTO `user_table` VALUES ('宗萨', '777', 'OpUezXaiYFZcjvoPGsbyLQ==', '777', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250402203237.gif', 'banned', '777', 0, '2025-04-02 20:32:38');
INSERT INTO `user_table` VALUES ('admin124214', 'admin', 'OpUezXaiYFZcjvoPGsbyLQ==', '123', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/20250331231723.png', 'online', '123', 1, '2024-08-01 00:00:01');
INSERT INTO `user_table` VALUES ('zhouzhi', 'zhouzhi', 'OpUezXaiYFZcjvoPGsbyLQ==', '', 'https://onaffair.oss-cn-hangzhou.aliyuncs.com/images/d2724289-67e7-48d2-b527-31d014d198fc.jpg', 'offline', '', 0, '2025-05-14 18:39:33');

-- ----------------------------
-- Triggers structure for table activity_comment
-- ----------------------------
DROP TRIGGER IF EXISTS `after_comment_insert`;
delimiter ;;
CREATE TRIGGER `after_comment_insert` AFTER INSERT ON `activity_comment` FOR EACH ROW BEGIN
    -- 更新 activity 表中的 rating
    UPDATE activity
    SET rating = (
        SELECT COALESCE(AVG(rating), 0)
        FROM activity_comment
        WHERE activity_id = NEW.activity_id
    )
    WHERE id = NEW.activity_id;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table activity_enrollment
-- ----------------------------
DROP TRIGGER IF EXISTS `after_enrollment_insert`;
delimiter ;;
CREATE TRIGGER `after_enrollment_insert` AFTER INSERT ON `activity_enrollment` FOR EACH ROW BEGIN
    UPDATE activity
    SET join_num = join_num + 1
    WHERE id = NEW.activity_id;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table activity_enrollment
-- ----------------------------
DROP TRIGGER IF EXISTS `after_enrollment_update`;
delimiter ;;
CREATE TRIGGER `after_enrollment_update` AFTER UPDATE ON `activity_enrollment` FOR EACH ROW BEGIN
    -- 如果确认状态发生变化
    IF NEW.is_confirmed != OLD.is_confirmed THEN
        -- 更新活动实际参与人数
        UPDATE activity
        SET join_num = join_num + CASE
                                      WHEN NEW.is_confirmed = 1 THEN 1 -- 确认参加
                                      ELSE -1 -- 取消确认
            END,
            updated_at = NOW()
        WHERE id = NEW.activity_id;

        -- 检查人数限制
        UPDATE activity
        SET status = CASE
                         WHEN join_num >= most_join_num THEN 3 -- 报名人数已满
                         WHEN status = 3 AND join_num < most_join_num THEN 2 -- 恢复报名
                         ELSE status
            END
        WHERE id = NEW.activity_id;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table activity_enrollment
-- ----------------------------
DROP TRIGGER IF EXISTS `after_enrollment_delete`;
delimiter ;;
CREATE TRIGGER `after_enrollment_delete` AFTER DELETE ON `activity_enrollment` FOR EACH ROW BEGIN
    UPDATE activity
    SET join_num = join_num - 1
    WHERE id = OLD.activity_id;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
