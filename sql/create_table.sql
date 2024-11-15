SET time_zone = '+08:00';

-- 数据库初始化
-- 以下语句用于创建名为parking_space的数据库，如果该数据库不存在的话
CREATE DATABASE IF NOT EXISTS parking_space;

-- 使用创建好的parking_space数据库
USE parking_space;

-- 创建用户表（user）
-- 该表用于存储系统用户的相关信息
CREATE TABLE user
(
    -- 用户ID，自增长的整数类型，为主键
    id            INT AUTO_INCREMENT PRIMARY KEY,
    -- 用户名，最大长度为50的字符串类型，不能为空
    username      VARCHAR(50)  NOT NULL,
    -- 用户密码，存储加密后的密码，最大长度为255的字符串类型，不能为空
    password      VARCHAR(255) NOT NULL COMMENT '存储加密后的密码',
    -- 用户手机号，最大长度为20的字符串类型，具有唯一性，用于登录和接收验证码等操作
    phone         VARCHAR(20) UNIQUE COMMENT '手机号，用于登录和接收验证码等',
    -- 用户邮箱，最大长度为50的字符串类型，具有唯一性
    email         VARCHAR(50) UNIQUE,
    -- 用户头像链接，最大长度为255的字符串类型
    avatar        VARCHAR(255) COMMENT '用户头像链接',
    -- 用户角色类型，tinyint类型，不能为空，默认值为1，1表示普通用户，2表示管理员
    role_type     TINYINT      NOT NULL DEFAULT 1 COMMENT '用户角色类型，1: 普通用户，2: 管理员',
    -- 用户注册时间，默认为当前时间戳
    register_time TIMESTAMP             DEFAULT CURRENT_TIMESTAMP
);

-- 创建车位表（parking_space）
-- 该表用于存储车位的相关信息
CREATE TABLE parking_space
(
    -- 车位ID，自增长的整数类型，为主键
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    -- 关联发布车位的车主用户ID，整数类型，不能为空
    user_id              INT            NOT NULL COMMENT '关联发布车位的车主用户ID',
    -- 车位所在位置的经度， decimal类型，精度为10位，小数点后8位，不能为空
    latitude             DECIMAL(10, 8) NOT NULL COMMENT '经度',
    -- 车位所在位置的纬度， decimal类型，精度为11位，小数点后8位，不能为空
    longitude            DECIMAL(11, 8) NOT NULL COMMENT '纬度',
    -- 车位地址的描述信息，最大长度为255的字符串类型，不能为空
    address_description  VARCHAR(255)   NOT NULL COMMENT '地址描述',
    -- 车位可使用的起始时间，时间戳类型
    available_time_start TIMESTAMP COMMENT '可使用起始时间',
    -- 车位可使用的结束时间，时间戳类型
    available_time_end   TIMESTAMP COMMENT '可使用结束时间',
    -- 车位的共享价格， decimal类型，精度为10位，小数点后2位，不能为空
    price                DECIMAL(10, 2) NOT NULL COMMENT '共享价格',
    -- 价格设置类型，tinyint类型，不能为空，默认值为1，1表示按天，2表示按周，3表示自定义时段
    price_type           TINYINT        NOT NULL DEFAULT 1 COMMENT '价格设置类型，1: 按天, 2: 按周, 3: 自定义时段',
    -- 自定义时段的起始时间（当price_type为自定义时段时使用），时间戳类型
    custom_time_start    TIMESTAMP COMMENT '自定义时段的起始时间（当price_type为自定义时段时使用）',
    -- 自定义时段的结束时间（当price_type为自定义时段时使用），时间戳类型
    custom_time_end      TIMESTAMP COMMENT '自定义时段的结束时间（当price_type为自定义时段时使用）',
    -- 车位是否可预订的状态，布尔类型，默认是可预订状态
    is_available         BOOLEAN                 DEFAULT TRUE COMMENT '车位是否可预订，默认是可预订状态',
    -- 车位创建时间，默认为当前时间戳
    create_time          TIMESTAMP               DEFAULT CURRENT_TIMESTAMP,
    -- 车位更新时间，在更新时自动更新为当前时间戳
    update_time          TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建预订表（reservation）
-- 该表用于存储车位预订的相关信息
CREATE TABLE reservation
(
    -- 预订记录ID，自增长的整数类型，为主键
    id                     INT AUTO_INCREMENT PRIMARY KEY,
    -- 预订车位的租客用户ID，整数类型，不能为空
    user_id                INT            NOT NULL COMMENT '预订车位的租客用户ID',
    -- 被预订的车位ID，整数类型，不能为空
    space_id               INT            NOT NULL COMMENT '被预订的车位ID',
    -- 预订起始时间，时间戳类型，不能为空
    reservation_time_start TIMESTAMP      NOT NULL COMMENT '预订起始时间',
    -- 预订结束时间，时间戳类型，不能为空
    reservation_time_end   TIMESTAMP      NOT NULL COMMENT '预订结束时间',
    -- 预订状态，tinyint类型，不能为空，默认值为1，1表示已预订，2表示已取消，3表示已完成
    reservation_status     TINYINT        NOT NULL DEFAULT 1 COMMENT '预订状态，1: 已预订, 2: 已取消, 3: 已完成',
    -- 预订总费用， decimal类型，精度为10位，小数点后2位，不能为空
    total_cost             DECIMAL(10, 2) NOT NULL COMMENT '预订总费用',
    -- 预订记录创建时间，默认为当前时间戳
    create_time            TIMESTAMP               DEFAULT CURRENT_TIMESTAMP,
    -- 预订记录更新时间，在更新时自动更新为当前时间戳
    update_time            TIMESTAMP               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建评价表（feedback）
-- 该表用于存储车位评价的相关信息
CREATE TABLE feedback
(
    -- 评价记录ID，自增长的整数类型，为主键
    id            INT AUTO_INCREMENT PRIMARY KEY,
    -- 评价的租客用户ID，整数类型，不能为空
    user_id       INT NOT NULL COMMENT '评价的租客用户ID',
    -- 被评价的车位ID，整数类型，不能为空
    space_id      INT NOT NULL COMMENT '被评价的车位ID',
    -- 评分，整数类型，范围为1 - 5星，不能为空
    rating        INT NOT NULL COMMENT '评分，1 - 5星',
    -- 文字评价内容，最大长度为255的字符串类型
    comment_text  VARCHAR(255) COMMENT '文字评价内容',
    -- 上传的评价图片链接（如果有），最大长度为255的字符串类型
    comment_image VARCHAR(255) COMMENT '上传的评价图片链接（如果有）',
    -- 评价记录创建时间，默认为当前时间戳
    create_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 插入用户表（user）数据
INSERT INTO user (username, password, phone, email, avatar, role_type)
VALUES ('user1', 'encrypted_password1', '13812345678', 'user1@example.com', 'avatar_link1', 1),
       ('user2', 'encrypted_password2', '13923456789', 'user2@example.com', 'avatar_link2', 1),
       ('user3', 'encrypted_password3', '13634567890', 'user3@example.com', 'avatar_link3', 1),
       ('user4', 'encrypted_password4', '13745678901', 'user4@example.com', 'avatar_link4', 1),
       ('user5', 'encrypted_password5', '13356789012', 'user5@example.com', 'avatar_link5', 1),
       ('user6', 'encrypted_password6', '13267890123', 'user6@example.com', 'avatar_link6', 1),
       ('user7', 'encrypted_password7', '13178901234', 'user7@example.com', 'avatar_link7', 1),
       ('user8', 'encrypted_password8', '13089012345', 'user8@example.com', 'avatar_link8', 1),
       ('user9', 'encrypted_password9', '13490123456', 'user9@example.com', 'avatar_link9', 1),
       ('admin1', 'encrypted_admin_password1', '13501234567', 'admin1@example.com', 'admin_avatar_link1', 2);

-- 插入10条车位信息数据
INSERT INTO parking_space (user_id, latitude, longitude, address_description, available_time_start, available_time_end,
                           price, price_type, custom_time_start, custom_time_end, is_available)
VALUES
-- 车位1
(1, 121.47370000, 31.23040000, '上海市浦东新区XX小区1号楼地下车库A区01号车位', '2024-11-15 08:00:00',
 '2024-11-15 20:00:00', 30.00, 1, NULL, NULL, TRUE),
-- 车位2
(2, 116.39130000, 39.90750000, '北京市朝阳区XX大厦地面停车场B区05号车位', '2024-11-16 09:00:00', '2024-11-16 18:00:00',
 40.00, 1, NULL, NULL, TRUE),
-- 车位3
(3, 120.15390000, 30.26720000, '杭州市西湖区XX公寓地下车库C区10号车位', '2024-11-17 10:00:00', '2024-11-17 17:00:00',
 25.00, 1, NULL, NULL, TRUE),
-- 车位4
(4, 113.26440000, 23.12910000, '广州市天河区XX写字楼地下车库D区03号车位', '2024-11-18 08:30:00', '2024-11-18 19:30:00',
 35.00, 1, NULL, NULL, TRUE),
-- 车位5
(5, 108.94800000, 34.26340000, '西安市雁塔区XX小区地面停车场E区08号车位', '2024-11-19 09:30:00', '2024-11-19 16:30:00',
 20.00, 1, NULL, NULL, TRUE),
-- 车位6
(6, 114.06540000, 22.54310000, '深圳市南山区XX购物中心地下车库F区12号车位', '2024-11-20 10:00:00',
 '2024-11-20 18:00:00', 45.00, 1, NULL, NULL, TRUE),
-- 车位7
(7, 129.52990000, 48.78290000, '哈尔滨市道里区XX小区地下车库G区06号车位', '2024-11-21 08:00:00', '2024-11-21 17:00:00',
 30.00, 1, NULL, NULL, TRUE),
-- 车位8
(8, 106.71220000, 26.57040000, '贵阳市云岩区XX大厦地面停车场H区09号车位', '2024-11-22 09:00:00', '2024-11-22 16:00:00',
 25.00, 1, NULL, NULL, TRUE),
-- 车位9
(9, 118.78000000, 32.04000000, '南京市鼓楼区XX公寓地下车库I区11号车位', '2024-11-23 10:00:00', '2024-11-23 19:00:00',
 35.00, 1, NULL, NULL, TRUE),
-- 车位10
(10, 117.20000000, 39.13000000, '天津市和平区XX写字楼地下车库J区04号车位', '2024-11-24 08:30:00', '2024-11-24 18:30:00',
 40.00, 1, NULL, NULL, TRUE);

-- 插入10条订单信息数据
INSERT INTO reservation (user_id, space_id, reservation_time_start, reservation_time_end, reservation_status,
                         total_cost)
VALUES
-- 订单1
(11, 1, '2024-11-15 08:00:00', '2024-11-15 12:00:00', 1, 15.00),
-- 订单2
(12, 2, '2024-11-16 09:00:00', '2024-11-16 13:00:00', 1, 20.00),
-- 订单3
(13, 3, '2024-11-17 10:00:00', '2024-11-17 14:00:00', 1, 12.50),
-- 订单4
(14, 4, '2024-11-18 08:30:00', '2024-11-18 13:30:00', 1, 17.50),
-- 订单5
(15, 5, '2024-11-19 09:30:00', '2024-11-19 13:30:00', 1, 10.00),
-- 订单6
(16, 6, '2024-11-20 10:00:00', '2024-11-20 14:00:00', 1, 22.50),
-- 订单7
(17, 7, '2024-11-21 08:00:00', '2024-11-21 12:00:00', 1, 15.00),
-- 订单8
(18, 8, '2024-11-22 09:00:00', '2024-11-22 13:00:00', 1, 12.50),
-- 订单9
(19, 9, '2024-11-23 10:00:00', '2024-11-23 15:00:00', 1, 17.50),
-- 订单10
(20, 10, '2024-11-24 08:30:00', '2024-11-24 13:30:00', 1, 20.00);

-- 插入评价表（feedback）数据
INSERT INTO feedback (user_id, space_id, rating, comment_text, comment_image)
VALUES (1, 1, 4, '车位位置方便，环境不错', NULL),
       (2, 2, 3, '整体还行，就是价格有点高', NULL),
       (3, 3, 5, '非常满意，下次还会预订', NULL),
       (4, 4, 2, '车位有点小，不太好停', NULL),
       (5, 5, 4, '服务很好，推荐给大家', NULL),
       (6, 6, 3, '性价比一般般吧', NULL),
       (7, 7, 5, '绝佳体验，无可挑剔', NULL),
       (8, 8, 2, '有点难找，希望能改进', NULL),
       (9, 9, 4, '很干净整洁，不错', NULL),
       (10, 10, 3, '中规中矩的一次体验', NULL);