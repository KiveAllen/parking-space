/*
 Navicat Premium Dump SQL

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80300 (8.3.0)
 Source Host           : localhost:3306
 Source Schema         : parking_space

 Target Server Type    : MySQL
 Target Server Version : 80300 (8.3.0)
 File Encoding         : 65001

 Date: 07/12/2024 14:01:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback`  (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `user_id` int NOT NULL COMMENT '评价的租客用户ID',
                             `space_id` int NOT NULL COMMENT '被评价的车位ID',
                             `rating` int NOT NULL COMMENT '评分，1 - 5星',
                             `comment_text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文字评价内容',
                             `comment_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '上传的评价图片链接（如果有）',
                             `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                             `reservation_id` int NULL DEFAULT NULL COMMENT '订单id',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of feedback
-- ----------------------------
INSERT INTO `feedback` VALUES (12, 11, 36, 3, '123', '', '2024-12-06 16:01:54', 45);

-- ----------------------------
-- Table structure for parking_space
-- ----------------------------
DROP TABLE IF EXISTS `parking_space`;
CREATE TABLE `parking_space`  (
                                  `id` int NOT NULL AUTO_INCREMENT,
                                  `user_id` int NOT NULL COMMENT '关联发布车位的车主用户ID',
                                  `longitude` decimal(15, 6) NOT NULL COMMENT '经度',
                                  `latitude` decimal(15, 6) NOT NULL COMMENT '纬度',
                                  `address_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '地址描述',
                                  `park_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '停车场号码',
                                  `price` decimal(10, 2) NOT NULL COMMENT '共享价格',
                                  `price_type` tinyint NOT NULL DEFAULT 1 COMMENT '价格设置类型，1: 按天, 2: 小时,3: 自定义时段',
                                  `custom_time_start` timestamp NULL DEFAULT NULL COMMENT '自定义时段的起始时间（当price_type为自定义时段时使用）',
                                  `custom_time_end` timestamp NULL DEFAULT NULL COMMENT '自定义时段的结束时间（当price_type为自定义时段时使用）',
                                  `is_available` tinyint(1) NOT NULL DEFAULT 1 COMMENT '车位是否可预订，默认是可预订状态',
                                  `park_photo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '车位图片',
                                  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                  `distance` decimal(10, 2) NOT NULL COMMENT '计算距离使用',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of parking_space
-- ----------------------------
INSERT INTO `parking_space` VALUES (34, 10, 114.418375, 23.037879, '广东省惠州市惠城区演达大道46号', 'A区285号', 12.00, 1, NULL, NULL, 0, 'https://img2.baidu.com/it/u=2818380084,124677737&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500', '2024-11-27 14:02:26', '2024-11-28 16:41:05', 0.00);
INSERT INTO `parking_space` VALUES (35, 10, 113.320174, 23.180409, '广东省广州市白云区梅花园', 'B区022号', 50.00, 2, NULL, NULL, 1, 'https://q0.itc.cn/q_70/images03/20240217/fefe028d7c1b496cbc7ef8171fe5dee6.jpeg', '2024-11-27 14:53:13', '2024-11-28 16:40:57', 0.00);
INSERT INTO `parking_space` VALUES (36, 10, 114.404840, 23.053223, '广东省惠州市惠城区南山公园南侧', '4号', 4.00, 3, '2024-11-27 08:00:00', '2024-11-27 12:00:00', 1, 'https://tukuimg.bdstatic.com/processed/1aabe3eadfcb33f38af6252ccac349ed.jpeg', '2024-11-27 16:58:00', '2024-11-28 16:37:45', 0.00);

-- ----------------------------
-- Table structure for reservation
-- ----------------------------
DROP TABLE IF EXISTS `reservation`;
CREATE TABLE `reservation`  (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单名称',
                                `user_id` int NOT NULL COMMENT '预订车位的租客用户ID',
                                `space_id` int NOT NULL COMMENT '被预订的车位ID',
                                `owner_id` int NOT NULL COMMENT '车位拥有者id',
                                `reservation_time_start` timestamp NOT NULL COMMENT '预订起始时间',
                                `reservation_time_end` timestamp NOT NULL COMMENT '预订结束时间',
                                `reservation_status` tinyint NOT NULL DEFAULT 1 COMMENT '预订状态，1: 已预订, 2: 已取消, 3: 已完成',
                                `total_cost` decimal(10, 2) NOT NULL COMMENT '预订总费用',
                                `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reservation
-- ----------------------------
INSERT INTO `reservation` VALUES (43, '广东省惠州市惠城区南山公园南侧', 11, 36, 10, '2024-11-28 16:11:54', '2024-11-29 16:11:54', 1, 4.00, '2024-11-28 16:11:54', '2024-11-28 16:11:54');
INSERT INTO `reservation` VALUES (44, '广东省惠州市惠城区南山公园南侧', 11, 36, 10, '2024-11-28 00:00:00', '2024-11-29 00:00:00', 2, 4.00, '2024-11-28 16:17:22', '2024-11-28 17:09:30');
INSERT INTO `reservation` VALUES (45, '广东省惠州市惠城区南山公园南侧', 11, 36, 10, '2024-11-28 00:00:00', '2024-12-01 00:00:00', 3, 12.00, '2024-11-28 16:36:46', '2024-11-28 17:09:30');
INSERT INTO `reservation` VALUES (46, '广东省广州市白云区梅花园', 11, 35, 10, '2024-11-28 00:00:00', '2024-12-12 00:00:00', 2, 100.00, '2024-11-28 16:38:17', '2024-11-28 17:09:31');
INSERT INTO `reservation` VALUES (47, '广东省惠州市惠城区演达大道46号', 11, 34, 10, '2024-11-28 00:00:00', '2024-11-30 00:00:00', 3, 24.00, '2024-11-28 16:41:05', '2024-11-28 17:09:32');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号，用于登录和接收验证码等',
                         `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像链接',
                         `role_type` tinyint NOT NULL DEFAULT 1 COMMENT '用户角色类型，1: 普通用户，2: 管理员',
                         `register_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                         `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称',
                         PRIMARY KEY (`id`, `phone`) USING BTREE,
                         UNIQUE INDEX `phone`(`phone` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '13812345678', 'https://www.mianshiya.com/_next/image?url=%2Flogo.png&w=32&q=75', 1, '2024-11-15 11:36:56', '1');
INSERT INTO `user` VALUES (2, '13923456789', 'https://www.mianshiya.com/_next/image?url=%2Flogo.png&w=32&q=75', 1, '2024-11-15 11:36:56', '2');
INSERT INTO `user` VALUES (3, '13634567890', 'https://www.mianshiya.com/_next/image?url=%2Flogo.png&w=32&q=75', 1, '2024-11-15 11:36:56', '3');
INSERT INTO `user` VALUES (4, '13745678901', 'https://www.mianshiya.com/_next/image?url=%2Flogo.png&w=32&q=75', 1, '2024-11-15 11:36:56', '4');
INSERT INTO `user` VALUES (5, '13356789012', 'https://www.mianshiya.com/_next/image?url=%2Flogo.png&w=32&q=75', 1, '2024-11-15 11:36:56', '5');
INSERT INTO `user` VALUES (6, '13267890123', 'https://www.mianshiya.com/_next/image?url=%2Flogo.png&w=32&q=75', 1, '2024-11-15 11:36:56', '6');
INSERT INTO `user` VALUES (7, '13178901234', 'https://www.mianshiya.com/_next/image?url=%2Flogo.png&w=32&q=75', 1, '2024-11-15 11:36:56', '7');
INSERT INTO `user` VALUES (8, '13089012345', 'https://www.mianshiya.com/_next/image?url=%2Flogo.png&w=32&q=75', 1, '2024-11-15 11:36:56', '8');
INSERT INTO `user` VALUES (9, '13490123456', 'https://www.mianshiya.com/_next/image?url=%2Flogo.png&w=32&q=75', 1, '2024-11-15 11:36:56', '9');
INSERT INTO `user` VALUES (10, '13501234567', 'https://www.mianshiya.com/_next/image?url=%2Flogo.png&w=32&q=75', 2, '2024-11-15 11:36:56', '10');
INSERT INTO `user` VALUES (11, '18312614958', 'https://www.mianshiya.com/_next/image?url=%2Flogo.png&w=32&q=75', 1, '2024-11-15 17:33:47', '用户');
INSERT INTO `user` VALUES (12, '18312614959', 'https://www.mianshiya.com/_next/image?url=%2Flogo.png&w=32&q=75', 2, '2024-11-15 17:45:20', 'allen');

SET FOREIGN_KEY_CHECKS = 1;
