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

 Date: 13/04/2025 13:58:22
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
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '活动信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES (20, '共建美好家园：街道卫生大扫除志愿活动', 8, '加入我们，一起为XX街道的卫生环境贡献力量！本次活动将组织志愿者清理街道垃圾、整理绿化带，提升社区整体卫生水平。参与即有机会获得街道颁发的志愿服务证书！', '本次活动由XX街道办主办，旨在提升社区环境卫生，增强居民环保意识。具体内容包括：\n\n分组清理街道两侧的垃圾和杂物；\n\n整理绿化带，清除杂草；\n\n向居民宣传垃圾分类知识。\n请参与者自备手套、垃圾袋等工具，街道办将提供饮用水和简单工具。集合地点：XX街道办门口，时间：2023-10-15 09:00。\n注意事项：请穿着舒适衣物和运动鞋，注意防晒。', '[\"20250409194200.jpeg\", \"20250409194204.jpeg\"]', 31, '街道办门口', '2025-04-15 09:00:00', '2025-04-15 16:00:00', 20, 50, '2025-04-09 19:23:24', '2025-04-09 20:07:27', '123', 2, 1, 0, 0.0);
INSERT INTO `activity` VALUES (21, '粽情端午：社区端午节传统文化体验', 2, '一起来XX社区体验端午传统文化！包粽子、做香囊、赛龙舟（模型），还有精彩的文化表演。适合全家参与，感受浓浓的节日氛围！', '端午节是中国的传统节日，XX社区特别策划了丰富多彩的活动：\n\n包粽子比赛：现场学习包粽子，优胜者有奖品；\n\n手工香囊制作：了解端午习俗，亲手制作香囊；\n\n龙舟模型竞赛：亲子合作，体验龙舟文化；\n\n文艺表演：社区艺术团带来传统舞蹈和歌曲。\n活动时间：2023-06-22 14:00-17:00，地点：XX社区广场。\n注意事项：请提前报名，材料有限，先到先得。', '[\"20250409194338.jpeg\", \"20250409194340.jpeg\"]', 31, '人民社区广场', '2025-06-22 06:00:00', '2025-06-22 18:00:00', 30, 100, '2025-04-09 19:25:18', '2025-04-09 20:07:27', '123', 2, 1, 0, 0.0);
INSERT INTO `activity` VALUES (22, '健康进社区：医院免费义诊活动', 3, '医院专家团队走进社区，为居民提供免费健康咨询和基础体检！涵盖内科、外科、眼科等多个科室，关爱健康，从预防开始', '本次活动由医院与社区联合举办，具体服务包括：\n\n基础体检：血压、血糖检测；\n\n健康咨询：内科、外科、眼科专家现场答疑；\n\n慢性病管理指导：针对高血压、糖尿病等常见病提供建议；\n\n健康手册发放：普及日常保健知识。\n注意事项：请携带既往病历或检查报告（如有），空腹参加血糖检测。', '[\"20250409194504.jpeg\", \"20250409194506.jpeg\"]', 31, '社区活动中心', '2025-04-14 00:00:00', '2026-02-09 00:00:00', 1, 500, '2025-04-09 19:27:21', '2025-04-09 20:07:27', '1234', 2, 1, 0, 0.0);
INSERT INTO `activity` VALUES (23, '美丽家园·社区环境清洁日', 1, '加入我们，一起清理社区垃圾、整理绿化带，共建整洁家园！参与者可获得社区志愿服务积分，累计积分可兑换礼品！', '本次活动由XX社区居委会组织，主要内容包括：\n\n垃圾清理：分组清理社区主干道、楼道及绿化带垃圾；\n\n垃圾分类宣传：向居民讲解垃圾分类知识；\n\n绿化维护：修剪杂草，整理花坛。\n注意事项：\n\n请自备手套、垃圾袋（社区提供部分工具）；\n\n穿着舒适衣物和防滑鞋；\n\n未成年人需家长陪同。', '[\"20250409194856.jpeg\", \"20250409194858.jpeg\"]', 31, '高教园区学子广场', '2025-05-11 00:00:00', '2025-05-13 00:00:00', 15, 50, '2025-04-09 19:29:24', '2025-04-09 20:07:37', '1234', 2, 2, 0, 0.0);
INSERT INTO `activity` VALUES (24, '欢乐中秋·社区团圆夜', 2, '中秋佳节，XX社区邀您共赏明月、猜灯谜、品月饼！还有精彩文艺表演和亲子DIY灯笼活动，快来感受传统节日的温馨氛围！', '为弘扬传统文化，XX社区特举办中秋联欢活动，内容包括：\n\n文艺表演：居民自编自导的歌舞、戏曲节目；\n\n猜灯谜：传统灯谜竞猜，答对者可兑换小礼品；\n\n月饼DIY：亲子合作制作冰皮月饼；\n\n灯笼制作：提供材料，现场教学制作中秋灯笼。\n注意事项：\n\n请提前报名，以便准备材料；\n\n活动期间请照看好老人和儿童。', '[\"20250409194906.jpeg\", \"20250409194909.jpeg\"]', 31, '社区中心花园', '2025-09-29 18:03:00', '2025-09-29 22:00:00', 30, 100, '2025-04-09 19:31:27', '2025-04-09 20:10:19', '1234', 2, 1, 0, 0.0);
INSERT INTO `activity` VALUES (25, '便民服务日·免费理发 & 家电维修', 3, '社区联合志愿者团队，为居民提供免费理发、小家电维修服务！让便民服务走进家门口，省心又暖心！', '本次活动服务项目包括：\n\n免费理发：专业理发师为居民（尤其是老年人）提供剪发服务；\n\n家电维修：志愿者团队维修电风扇、电饭煲等小家电（不包含大型电器）；\n\n健康咨询：社区卫生站医生提供基础血压、血糖检测。\n注意事项：\n\n理发服务需排队取号，先到先得；\n\n家电维修请提前说明故障情况，复杂问题可能无法现场解决。', '[\"20250409194915.jpeg\", \"20250409194917.jpeg\"]', 31, '社区活动中心', '2025-10-06 00:00:00', '2025-10-07 00:00:00', 1, 500, '2025-04-09 19:32:48', '2025-04-09 20:10:04', '1234', 2, 1, 0, 4.5);

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
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '活动评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity_comment
-- ----------------------------
INSERT INTO `activity_comment` VALUES (22, 25, '123', '12312321', '20250409200958.png', 5, NULL, '2025-04-09 20:09:59', NULL);
INSERT INTO `activity_comment` VALUES (23, 25, '123', '12312', NULL, 4, 22, '2025-04-09 20:10:04', '12312321');

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
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '活动报名表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity_enrollment
-- ----------------------------
INSERT INTO `activity_enrollment` VALUES (40, '123', 20, '2025-04-09 19:23:23', 1);
INSERT INTO `activity_enrollment` VALUES (41, '123', 21, '2025-04-09 19:25:17', 1);
INSERT INTO `activity_enrollment` VALUES (42, '1234', 22, '2025-04-09 19:27:20', 1);
INSERT INTO `activity_enrollment` VALUES (43, '1234', 23, '2025-04-09 19:29:24', 1);
INSERT INTO `activity_enrollment` VALUES (44, '1234', 24, '2025-04-09 19:31:27', 1);
INSERT INTO `activity_enrollment` VALUES (45, '1234', 25, '2025-04-09 19:32:47', 1);
INSERT INTO `activity_enrollment` VALUES (52, '123', 23, '2025-04-09 20:07:37', 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 96 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '好友消息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of friend_message
-- ----------------------------
INSERT INTO `friend_message` VALUES (1, '1234', '123', '深恶，么？', NULL, '2025-03-24 01:14:29');
INSERT INTO `friend_message` VALUES (2, '1234', '123', '啊？', NULL, '2025-03-24 01:14:46');
INSERT INTO `friend_message` VALUES (3, '123', '1234', '什么?', NULL, '2025-03-24 01:42:54');
INSERT INTO `friend_message` VALUES (4, '123', '1234', '12421421', '', '2025-03-26 20:53:49');
INSERT INTO `friend_message` VALUES (71, '123', '111', '我们已经是好友了,开始了聊天吧', '', '2025-03-29 00:49:10');
INSERT INTO `friend_message` VALUES (72, '111', '123', '这是一个测试', '', '2025-03-29 00:51:12');
INSERT INTO `friend_message` VALUES (73, '123', '333', '我们已经是好友了,开始了聊天吧', '', '2025-03-29 01:12:30');
INSERT INTO `friend_message` VALUES (74, '333', '123', '谢谢你', '', '2025-03-29 01:14:46');
INSERT INTO `friend_message` VALUES (75, '123', '1234', '21412412', '', '2025-03-29 18:10:26');
INSERT INTO `friend_message` VALUES (76, '1234', '123', '12421421', '', '2025-03-29 18:10:29');
INSERT INTO `friend_message` VALUES (77, '123', '1234', '1241', '20250329182433.jpg', '2025-03-29 18:24:55');
INSERT INTO `friend_message` VALUES (78, '123', '1234', '', '20250329182533.jpg', '2025-03-29 18:25:36');
INSERT INTO `friend_message` VALUES (79, '123', '1234', '124124', NULL, '2025-03-29 18:25:43');
INSERT INTO `friend_message` VALUES (80, '123', '1234', '', '20250329182545.jpg', '2025-03-29 18:25:48');
INSERT INTO `friend_message` VALUES (81, '123', '1234', '', '20250329183129.jpg', '2025-03-29 18:31:32');
INSERT INTO `friend_message` VALUES (82, '123', '1234', '12421', NULL, '2025-03-29 18:32:40');
INSERT INTO `friend_message` VALUES (83, '123', '111', '', '20250329183302.jpg', '2025-03-29 18:33:03');
INSERT INTO `friend_message` VALUES (84, '123', '111', '111', NULL, '2025-03-29 18:35:59');
INSERT INTO `friend_message` VALUES (85, '1234', '123', '1', NULL, '2025-03-29 19:25:56');
INSERT INTO `friend_message` VALUES (86, '123', '333', '不客气', NULL, '2025-03-29 22:05:47');
INSERT INTO `friend_message` VALUES (87, '123', '1234', '12412412', NULL, '2025-03-30 23:03:46');
INSERT INTO `friend_message` VALUES (93, '123', '333', '🔥我分享了活动《清明踏青》 清明踏青 👉点击查看详情：http://localhost:8080/u/ActivityDetailPage?id=14', NULL, '2025-03-30 23:44:38');
INSERT INTO `friend_message` VALUES (94, '555', 'admin', '我们已经是好友了,开始了聊天吧', '', '2025-04-02 19:44:30');
INSERT INTO `friend_message` VALUES (95, '1234', '123', '1212312', NULL, '2025-04-09 20:09:42');

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
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '好友关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of friend_relationship
-- ----------------------------
INSERT INTO `friend_relationship` VALUES (1, '123', '1234', '2025-03-24 01:11:52');
INSERT INTO `friend_relationship` VALUES (4, '123', '111', '2025-03-29 00:49:09');
INSERT INTO `friend_relationship` VALUES (5, '123', '333', '2025-03-29 01:12:29');
INSERT INTO `friend_relationship` VALUES (6, '555', 'admin', '2025-04-02 19:44:29');

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
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '好友请求表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of friend_request
-- ----------------------------
INSERT INTO `friend_request` VALUES (11, '111', '123', 1, '2025-03-27 10:04:31');
INSERT INTO `friend_request` VALUES (12, '222', '123', 0, '2025-03-27 23:53:45');
INSERT INTO `friend_request` VALUES (13, '333', '123', 1, '2025-03-27 23:53:56');
INSERT INTO `friend_request` VALUES (17, '444', '123', 0, '2025-03-29 01:22:11');
INSERT INTO `friend_request` VALUES (18, 'admin', '555', 1, '2025-04-02 19:44:25');

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
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '群组信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group
-- ----------------------------
INSERT INTO `group` VALUES (11, '共建美好家园：街道卫生大扫除志愿活动活动群', '123', NULL, NULL, NULL, 1, 20);
INSERT INTO `group` VALUES (12, '粽情端午：社区端午节传统文化体验活动群', '123', NULL, NULL, NULL, 1, 21);
INSERT INTO `group` VALUES (13, '健康进社区：医院免费义诊活动活动群', '1234', NULL, NULL, NULL, 1, 22);
INSERT INTO `group` VALUES (14, '美丽家园·社区环境清洁日活动群', '1234', NULL, NULL, NULL, 1, 23);
INSERT INTO `group` VALUES (15, '欢乐中秋·社区团圆夜活动群', '1234', NULL, NULL, NULL, 1, 24);
INSERT INTO `group` VALUES (16, '便民服务日·免费理发 & 家电维修活动群', '1234', NULL, NULL, NULL, 1, 25);

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
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '群成员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_member
-- ----------------------------
INSERT INTO `group_member` VALUES (30, 11, '123', 0, NULL, NULL);
INSERT INTO `group_member` VALUES (31, 12, '123', 0, NULL, NULL);
INSERT INTO `group_member` VALUES (32, 13, '1234', 0, NULL, NULL);
INSERT INTO `group_member` VALUES (33, 14, '1234', 0, NULL, NULL);
INSERT INTO `group_member` VALUES (34, 15, '1234', 0, NULL, NULL);
INSERT INTO `group_member` VALUES (35, 16, '1234', 0, NULL, NULL);
INSERT INTO `group_member` VALUES (42, 14, '123', 2, NULL, NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '群聊消息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_message
-- ----------------------------
INSERT INTO `group_message` VALUES (34, '123', 11, 'hello', '20250409200748.png', '2025-04-09 20:07:53');

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
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户关注关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_follow
-- ----------------------------
INSERT INTO `user_follow` VALUES (22, '222', '123', NULL);
INSERT INTO `user_follow` VALUES (23, '222', '1234', NULL);
INSERT INTO `user_follow` VALUES (25, '333', '123', NULL);
INSERT INTO `user_follow` VALUES (35, '123', '1234', NULL);
INSERT INTO `user_follow` VALUES (36, '123', '333', NULL);
INSERT INTO `user_follow` VALUES (37, '444', '123', NULL);
INSERT INTO `user_follow` VALUES (38, '123', '111', NULL);
INSERT INTO `user_follow` VALUES (39, '1234', '123', NULL);
INSERT INTO `user_follow` VALUES (40, 'admin', '1234', NULL);

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
  `role` tinyint NOT NULL DEFAULT 0 COMMENT '用户身份（0: 普通用户, 1: 管理员）',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`user_account`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_table
-- ----------------------------
INSERT INTO `user_table` VALUES ('归云', '111', 'OpUezXaiYFZcjvoPGsbyLQ==', '111', '20250329015130.jpg', 'offline', '123', 0, '2024-02-09 00:00:01');
INSERT INTO `user_table` VALUES ('mafu', '123', 'OpUezXaiYFZcjvoPGsbyLQ==', '12312412523415', '20250329014958.jpg', 'online', '123', 0, '2024-10-18 13:25:50');
INSERT INTO `user_table` VALUES ('安逸', '1234', 'OpUezXaiYFZcjvoPGsbyLQ==', '1234', '20250329015325.jpg', 'offline', '1234', 0, '2024-04-27 00:00:01');
INSERT INTO `user_table` VALUES ('瑾年', '222', 'OpUezXaiYFZcjvoPGsbyLQ==', '222', '20250329015140.png', 'offline', '222', 0, '2024-12-21 00:00:01');
INSERT INTO `user_table` VALUES ('菜鸟', '333', 'OpUezXaiYFZcjvoPGsbyLQ==', '333', '20250329015151.jpg', 'offline', '123', 0, '2024-03-14 00:00:01');
INSERT INTO `user_table` VALUES ('八嘎', '444', 'OpUezXaiYFZcjvoPGsbyLQ==', '444', '20250329015200.jpg', 'offline', '123', 0, '2024-07-20 00:00:01');
INSERT INTO `user_table` VALUES ('哈基咪', '555', 'OpUezXaiYFZcjvoPGsbyLQ==', '123', '20250402194332.jpg', 'offline', '123', 0, '2024-11-29 00:00:01');
INSERT INTO `user_table` VALUES ('kid', '666', 'OpUezXaiYFZcjvoPGsbyLQ==', '777', '20250402203211.gif', 'offline', 'kid', 0, '2025-04-02 20:32:13');
INSERT INTO `user_table` VALUES ('宗萨', '777', 'OpUezXaiYFZcjvoPGsbyLQ==', '777', '20250402203237.gif', 'banned', '777', 0, '2025-04-02 20:32:38');
INSERT INTO `user_table` VALUES ('admin', 'admin', 'OpUezXaiYFZcjvoPGsbyLQ==', '123', '20250331231723.png', 'offline', '123', 1, '2024-08-01 00:00:01');

-- ----------------------------
-- Triggers structure for table activity
-- ----------------------------
DROP TRIGGER IF EXISTS `before_activity_update`;
delimiter ;;
CREATE TRIGGER `before_activity_update` BEFORE UPDATE ON `activity` FOR EACH ROW BEGIN
    DECLARE current_time_val DATETIME;
    SET current_time_val = NOW();

    -- 如果状态已经是“已取消”，则不再自动更新
    IF NEW.status = 6 THEN
        -- 空操作，保持原状态
        SET @dummy = 0; -- 占位语句（无实际作用）
    ELSE
        -- 自动更新逻辑
        IF NEW.join_num >= NEW.most_join_num THEN
            SET NEW.status = 3; -- 报名人数已满
        ELSEIF current_time_val < NEW.begin_time THEN
            SET NEW.status = 2; -- 报名中
        ELSEIF current_time_val BETWEEN NEW.begin_time AND NEW.end_time THEN
            SET NEW.status = 4; -- 活动进行中
        ELSEIF current_time_val > NEW.end_time THEN
            SET NEW.status = 5; -- 活动已结束
        END IF;
    END IF;
    END
;;
delimiter ;

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
