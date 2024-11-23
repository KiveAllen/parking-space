SET time_zone = '+08:00';

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback`
(
    `id`            int                                                           NOT NULL AUTO_INCREMENT,
    `user_id`       int                                                           NOT NULL COMMENT '评价的租客用户ID',
    `space_id`      int                                                           NOT NULL COMMENT '被评价的车位ID',
    `rating`        int                                                           NOT NULL COMMENT '评分，1 - 5星',
    `comment_text`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文字评价内容',
    `comment_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '上传的评价图片链接（如果有）',
    `create_time`   timestamp                                                     NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 12
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of feedback
-- ----------------------------
INSERT INTO `feedback`
VALUES (1, 1, 1, 4, '车位位置方便，环境不错', NULL, '2024-11-15 11:36:56');
INSERT INTO `feedback`
VALUES (2, 2, 2, 3, '整体还行，就是价格有点高', NULL, '2024-11-15 11:36:56');
INSERT INTO `feedback`
VALUES (3, 3, 3, 5, '非常满意，下次还会预订', NULL, '2024-11-15 11:36:56');
INSERT INTO `feedback`
VALUES (4, 4, 4, 2, '车位有点小，不太好停', NULL, '2024-11-15 11:36:56');
INSERT INTO `feedback`
VALUES (5, 5, 5, 4, '服务很好，推荐给大家', NULL, '2024-11-15 11:36:56');
INSERT INTO `feedback`
VALUES (6, 6, 6, 3, '性价比一般般吧', NULL, '2024-11-15 11:36:56');
INSERT INTO `feedback`
VALUES (7, 7, 7, 5, '绝佳体验，无可挑剔', NULL, '2024-11-15 11:36:56');
INSERT INTO `feedback`
VALUES (8, 8, 8, 2, '有点难找，希望能改进', NULL, '2024-11-15 11:36:56');
INSERT INTO `feedback`
VALUES (9, 9, 9, 4, '很干净整洁，不错', NULL, '2024-11-15 11:36:56');
INSERT INTO `feedback`
VALUES (10, 10, 10, 3, '中规中矩的一次体验', NULL, '2024-11-15 11:36:56');

-- ----------------------------
-- Table structure for parking_space
-- ----------------------------
DROP TABLE IF EXISTS `parking_space`;
CREATE TABLE `parking_space`
(
    `id`                   int                                                           NOT NULL AUTO_INCREMENT,
    `user_id`              int                                                           NOT NULL COMMENT '关联发布车位的车主用户ID',
    `longitude`            decimal(15, 6)                                                NOT NULL COMMENT '经度',
    `latitude`             decimal(15, 6)                                                NOT NULL COMMENT '纬度',
    `address_description`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '地址描述',
    `part_number`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '停车场号码',
    `available_time_start` timestamp                                                     NULL     DEFAULT NULL COMMENT '可使用起始时间',
    `available_time_end`   timestamp                                                     NULL     DEFAULT NULL COMMENT '可使用结束时间',
    `price`                decimal(10, 2)                                                NOT NULL COMMENT '共享价格',
    `price_type`           tinyint                                                       NOT NULL DEFAULT 1 COMMENT '价格设置类型，1: 按天, 2: 小时,3: 自定义时段',
    `custom_time_start`    timestamp                                                     NULL     DEFAULT NULL COMMENT '自定义时段的起始时间（当price_type为自定义时段时使用）',
    `custom_time_end`      timestamp                                                     NULL     DEFAULT NULL COMMENT '自定义时段的结束时间（当price_type为自定义时段时使用）',
    `is_available`         tinyint(1)                                                    NOT NULL DEFAULT 1 COMMENT '车位是否可预订，默认是可预订状态',
    `create_time`          timestamp                                                     NULL     DEFAULT CURRENT_TIMESTAMP,
    `update_time`          timestamp                                                     NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 32
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of parking_space
-- ----------------------------
INSERT INTO `parking_space`
VALUES (31, 11, 114.416456, 23.039525, '广东省惠州市惠州学院', 'F区13号', NULL, NULL, 4.00, 1, NULL, NULL, 0,
        '2024-11-16 15:57:42', '2024-11-22 11:25:26');

-- ----------------------------
-- Table structure for reservation
-- ----------------------------
DROP TABLE IF EXISTS `reservation`;
CREATE TABLE `reservation`
(
    `id`                     int            NOT NULL AUTO_INCREMENT,
    `user_id`                int            NOT NULL COMMENT '预订车位的租客用户ID',
    `space_id`               int            NOT NULL COMMENT '被预订的车位ID',
    `reservation_time_start` timestamp      NOT NULL COMMENT '预订起始时间',
    `reservation_time_end`   timestamp      NOT NULL COMMENT '预订结束时间',
    `reservation_status`     tinyint        NOT NULL DEFAULT 1 COMMENT '预订状态，1: 已预订, 2: 已取消, 3: 已完成',
    `total_cost`             decimal(10, 2) NOT NULL COMMENT '预订总费用',
    `create_time`            timestamp      NULL     DEFAULT CURRENT_TIMESTAMP,
    `update_time`            timestamp      NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `owner_id`               int            NOT NULL COMMENT '车位拥有者id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 43
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reservation
-- ----------------------------
INSERT INTO `reservation`
VALUES (32, 2, 1, '2024-11-15 08:00:00', '2024-11-15 12:00:00', 1, 15.00, '2024-11-15 13:38:23', '2024-11-15 17:08:31',
        1);
INSERT INTO `reservation`
VALUES (33, 3, 2, '2024-11-16 09:00:00', '2024-11-16 13:00:00', 1, 20.00, '2024-11-15 13:38:23', '2024-11-15 17:08:32',
        2);
INSERT INTO `reservation`
VALUES (34, 4, 3, '2024-11-17 10:00:00', '2024-11-17 14:00:00', 1, 12.50, '2024-11-15 13:38:23', '2024-11-15 17:08:33',
        3);
INSERT INTO `reservation`
VALUES (35, 5, 4, '2024-11-18 08:30:00', '2024-11-18 13:30:00', 1, 17.50, '2024-11-15 13:38:23', '2024-11-15 17:08:35',
        4);
INSERT INTO `reservation`
VALUES (36, 6, 5, '2024-11-19 09:30:00', '2024-11-19 13:30:00', 1, 10.00, '2024-11-15 13:38:23', '2024-11-15 17:08:35',
        5);
INSERT INTO `reservation`
VALUES (37, 7, 6, '2024-11-20 10:00:00', '2024-11-20 14:00:00', 1, 22.50, '2024-11-15 13:38:23', '2024-11-15 17:08:36',
        6);
INSERT INTO `reservation`
VALUES (38, 8, 7, '2024-11-21 08:00:00', '2024-11-21 12:00:00', 1, 15.00, '2024-11-15 13:38:23', '2024-11-15 17:08:36',
        7);
INSERT INTO `reservation`
VALUES (39, 9, 8, '2024-11-22 09:00:00', '2024-11-22 13:00:00', 1, 12.50, '2024-11-15 13:38:23', '2024-11-15 17:08:37',
        8);
INSERT INTO `reservation`
VALUES (40, 10, 9, '2024-11-23 10:00:00', '2024-11-23 15:00:00', 1, 17.50, '2024-11-15 13:38:23', '2024-11-15 17:08:40',
        9);
INSERT INTO `reservation`
VALUES (41, 1, 10, '2024-11-24 08:30:00', '2024-11-24 13:30:00', 1, 20.00, '2024-11-15 13:38:23', '2024-11-15 17:08:41',
        10);
INSERT INTO `reservation`
VALUES (42, 11, 2, '2024-11-16 17:00:00', '2024-11-17 02:00:00', 3, 10.00, '2024-11-16 16:10:41', '2024-11-16 16:12:37',
        2);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`            int                                                           NOT NULL AUTO_INCREMENT,
    `phone`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '手机号，用于登录和接收验证码等',
    `avatar`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '用户头像链接',
    `role_type`     tinyint                                                       NOT NULL DEFAULT 1 COMMENT '用户角色类型，1: 普通用户，2: 管理员',
    `register_time` timestamp                                                     NULL     DEFAULT CURRENT_TIMESTAMP,
    `name`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '用户昵称',
    PRIMARY KEY (`id`, `phone`) USING BTREE,
    UNIQUE INDEX `phone` (`phone` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 14
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user`
VALUES (1, '13812345678', 'https://www.mianshiya.com/_next/image?url=%2Flogo.png&w=32&q=75', 1, '2024-11-15 11:36:56',
        '1');
INSERT INTO `user`
VALUES (2, '13923456789', 'https://www.mianshiya.com/_next/image?url=%2Flogo.png&w=32&q=75', 1, '2024-11-15 11:36:56',
        '2');
INSERT INTO `user`
VALUES (3, '13634567890', 'https://www.mianshiya.com/_next/image?url=%2Flogo.png&w=32&q=75', 1, '2024-11-15 11:36:56',
        '3');
INSERT INTO `user`
VALUES (4, '13745678901', 'https://www.mianshiya.com/_next/image?url=%2Flogo.png&w=32&q=75', 1, '2024-11-15 11:36:56',
        '4');
INSERT INTO `user`
VALUES (5, '13356789012', 'https://www.mianshiya.com/_next/image?url=%2Flogo.png&w=32&q=75', 1, '2024-11-15 11:36:56',
        '5');
INSERT INTO `user`
VALUES (6, '13267890123', 'https://www.mianshiya.com/_next/image?url=%2Flogo.png&w=32&q=75', 1, '2024-11-15 11:36:56',
        '6');
INSERT INTO `user`
VALUES (7, '13178901234', 'https://www.mianshiya.com/_next/image?url=%2Flogo.png&w=32&q=75', 1, '2024-11-15 11:36:56',
        '7');
INSERT INTO `user`
VALUES (8, '13089012345', 'https://www.mianshiya.com/_next/image?url=%2Flogo.png&w=32&q=75', 1, '2024-11-15 11:36:56',
        '8');
INSERT INTO `user`
VALUES (9, '13490123456', 'https://www.mianshiya.com/_next/image?url=%2Flogo.png&w=32&q=75', 1, '2024-11-15 11:36:56',
        '9');
INSERT INTO `user`
VALUES (10, '13501234567', 'https://www.mianshiya.com/_next/image?url=%2Flogo.png&w=32&q=75', 2, '2024-11-15 11:36:56',
        '10');
INSERT INTO `user`
VALUES (11, '18312614958', 'https://www.mianshiya.com/_next/image?url=%2Flogo.png&w=32&q=75', 1, '2024-11-15 17:33:47',
        '用户1301686712');
INSERT INTO `user`
VALUES (12, '18312614959', 'https://www.mianshiya.com/_next/image?url=%2Flogo.png&w=32&q=75', 2, '2024-11-15 17:45:20',
        'allen');

SET FOREIGN_KEY_CHECKS = 1;
