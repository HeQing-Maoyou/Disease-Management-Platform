-- 创建数据库
CREATE DATABASE IF NOT EXISTS community_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE community_management;

-- 用户表
CREATE TABLE IF NOT EXISTS user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    id_card VARCHAR(18) UNIQUE NOT NULL,
    community_id BIGINT NOT NULL,
    building VARCHAR(50) NOT NULL,
    unit VARCHAR(20) NOT NULL,
    room VARCHAR(20) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

-- 健康打卡表
CREATE TABLE IF NOT EXISTS health_checkin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    temperature DECIMAL(3,1) NOT NULL,
    symptoms VARCHAR(200),
    status VARCHAR(20) NOT NULL,
    checkin_time DATETIME NOT NULL,
    created_at DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- 健康码表
CREATE TABLE IF NOT EXISTS health_code (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    proxy_user_id BIGINT,
    code_type VARCHAR(20) NOT NULL,
    last_update DATETIME NOT NULL,
    valid_until DATETIME NOT NULL,
    created_at DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (proxy_user_id) REFERENCES user(id)
);

-- 核酸检测表
CREATE TABLE IF NOT EXISTS nucleic_acid_test (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    test_time DATETIME NOT NULL,
    result VARCHAR(20) NOT NULL,
    test_location VARCHAR(100) NOT NULL,
    created_at DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- 团购商品表
CREATE TABLE IF NOT EXISTS group_purchase_product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    category VARCHAR(50) NOT NULL,
    supplier_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

-- 订单表
CREATE TABLE IF NOT EXISTS `order` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    order_no VARCHAR(50) UNIQUE NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    delivery_address VARCHAR(200) NOT NULL,
    contact_phone VARCHAR(20) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- 订单详情表
CREATE TABLE IF NOT EXISTS order_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    created_at DATETIME NOT NULL,
    FOREIGN KEY (order_id) REFERENCES `order`(id),
    FOREIGN KEY (product_id) REFERENCES group_purchase_product(id)
);

-- 社区通知表
CREATE TABLE IF NOT EXISTS community_notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    community_id BIGINT NOT NULL,
    building VARCHAR(50),
    unit VARCHAR(20),
    is_urgent BOOLEAN NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

-- 隔离服务表
CREATE TABLE IF NOT EXISTS quarantine_service (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    service_type VARCHAR(50) NOT NULL,
    appointment_time DATETIME NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- 社区互助表
CREATE TABLE IF NOT EXISTS community_help (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    type VARCHAR(50) NOT NULL,
    content TEXT NOT NULL,
    contact_info VARCHAR(200) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- 消息表
CREATE TABLE IF NOT EXISTS message (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    sender_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    send_time DATETIME NOT NULL,
    read_status BOOLEAN NOT NULL,
    created_at DATETIME NOT NULL,
    FOREIGN KEY (sender_id) REFERENCES user(id),
    FOREIGN KEY (receiver_id) REFERENCES user(id)
);

-- 插入测试数据
INSERT INTO user (username, password, name, phone, id_card, community_id, building, unit, room, role, created_at, updated_at) VALUES
('admin', '123456', '管理员', '13800138000', '110101199001011234', 1, '1栋', '1单元', '101', '管理员', NOW(), NOW()),
('user1', '123456', '张三', '13800138001', '110101199002022345', 1, '1栋', '1单元', '102', '居民', NOW(), NOW()),
('user2', '123456', '李四', '13800138002', '110101199003033456', 1, '2栋', '2单元', '201', '居民', NOW(), NOW());

-- 插入团购商品测试数据
INSERT INTO group_purchase_product (name, description, price, stock, category, supplier_id, status, created_at, updated_at) VALUES
('新鲜蔬菜包', '包含土豆、白菜、萝卜等新鲜蔬菜', 39.9, 100, '蔬菜', 1, '上架', NOW(), NOW()),
('水果套餐', '包含苹果、梨、橙子等新鲜水果', 59.9, 50, '水果', 1, '上架', NOW(), NOW()),
('肉类套餐', '包含猪肉、鸡肉等肉类', 89.9, 30, '肉类', 1, '上架', NOW(), NOW()),
('生活用品包', '包含纸巾、牙膏、洗发水等生活用品', 69.9, 80, '生活用品', 1, '上架', NOW(), NOW());

-- 插入社区通知测试数据
INSERT INTO community_notice (title, content, community_id, building, unit, is_urgent, created_at, updated_at) VALUES
('关于开展新一轮核酸检测的通知', '根据社区疫情防控要求，将于3月9日开展新一轮核酸检测，请各位居民按时参加。', 1, '', '', true, NOW(), NOW()),
('社区疫情防控措施调整通知', '根据最新疫情形势，社区防控措施进行调整，具体内容请查看附件。', 1, '', '', false, NOW(), NOW()),
('1栋居民生活物资配送安排', '1栋居民的生活物资将于3月8日下午2点开始配送，请各位居民注意查收。', 1, '1栋', '', false, NOW(), NOW());
