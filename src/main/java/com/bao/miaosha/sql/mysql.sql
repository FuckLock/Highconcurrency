# 创建表miaosha_user
CREATE TABLE `miaosha_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表id',
  `phone` BIGINT NOT NULL COMMENT '用户手机号码',
  `nickname` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '用户密码，MD5加密',
  `salt` varchar(50) DEFAULT NULL COMMENT 'md5加密需要的盐',
  `head` varchar(20) DEFAULT NULL,
  `register_date` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_date` datetime DEFAULT NULL COMMENT '最后一次登录时间',
  `login_count` int(10) DEFAULT NULL COMMENT '登录次数',
   PRIMARY KEY (`id`),
   UNIQUE KEY `nike_name_unique` (`nickname`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

# 创建记录
INSERT INTO
miaosha_user(id, phone, nickname, password, salt, head, register_date, last_login_date, login_count)
VALUE (1, 18810856476, "东东", "b0a99513651ccf9711984e271c92b05b", "1a2b3c4d", "", DATE("2018-05-01"), DATE("2018-05-02"), 2);

# 创建表goods
CREATE TABLE `goods` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `goods_name` VARCHAR(50) NOT NULL COMMENT '商品名称',
  `goods_title` varchar(255) DEFAULT NULL COMMENT '商品主题',
  `goods_img` varchar(50) DEFAULT NULL COMMENT '商品图片',
  `goods_detail` varchar(50) DEFAULT NULL COMMENT '商品详细信息',
  `goods_price` DOUBLE DEFAULT NULL COMMENT '商品价格',
  `goods_stock` int(11) DEFAULT NULL COMMENT '商品库存',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

# 创建记录
INSERT INTO `goods` (`id`, `goods_name`, `goods_title`, `goods_img`, `goods_detail`, `goods_price`, `goods_stock`)
VALUES
  (1, 'Apple iPhone 7 Plus', 'Apple iPhone 7 Plus (A1661) 128G 玫瑰金色 移动联通电信4G手机', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', 'iPhone 7，现更以红色呈现。', 7777.0, 10);

INSERT INTO `goods` (`id`, `goods_name`, `goods_title`, `goods_img`, `goods_detail`, `goods_price`, `goods_stock`)
VALUES
  (2, '华为 P10', '华为 P10 128G 玫瑰金色 移动联通电信4G手机', '11241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '华为 P10，现更以红色呈现。', 5555.0, 5);

# MiaoshaGoods
CREATE TABLE `miaosha_goods` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀商品id',
  `goods_id` BIGINT(20) NOT NULL COMMENT '商品ID',
  `miaosha_price` DOUBLE NOT NULL COMMENT '秒杀商品价格',
  `stock_count` int DEFAULT NULL COMMENT '秒杀库存数量',
  `start_date` DATETIME DEFAULT NULL COMMENT '秒杀开始时间',
  `end_date` DATETIME  DEFAULT NULL COMMENT '秒杀结束时间',
  PRIMARY KEY (`id`),
  KEY `index_miaosha_goods_on_goods_id` (`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

# 创建记录
INSERT INTO `miaosha_goods` (`id`, `goods_id`, `miaosha_price`, `stock_count`, `start_date`, `end_date`)
VALUES (1, 2, 0.01, 4, DATE("2018-03-02"), DATE("2018-03-05"));

INSERT INTO `miaosha_goods` (`id`, `goods_id`, `miaosha_price`, `stock_count`, `start_date`, `end_date`)
VALUES (2, 1, 0.01, 5, DATE("2018-03-02"), DATE("2018-03-05"));

# orderInfo
CREATE TABLE `order_info` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `goods_id` BIGINT(20) NOT NULL COMMENT '商品id',
  `delivery_addr_id` BIGINT(20) DEFAULT NULL,
  `goods_name` VARCHAR(50) DEFAULT NULL COMMENT '商品名称',
  `goods_count` int(11) DEFAULT NULL COMMENT '商品数量',
  `goods_price` DOUBLE DEFAULT NULL COMMENT '商品价格',
  `order_channel` int(11)  DEFAULT NULL,
  `status` int(5)  NOT NULL  COMMENT '0:未支付, 1:已支付, 2:已发货, 3:已收货, 4:已退款, 5:已完成',
  `create_date` DATETIME  NOT NULL COMMENT '订单创建时间',
  `pay_date` DATETIME  DEFAULT NULL COMMENT '订单付款时间',
  PRIMARY KEY (`id`),
  KEY `index_order_info_on_user_id` (`user_id`),
  KEY `index_order_info_on_user_goods_id` (`goods_id`)
#   CONSTRAINT `index_order_info_on_user_id` FOREIGN KEY (`user_id`) REFERENCES `index_order_info_on_user_goods_id` (`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;


# miaosha_order
CREATE TABLE `miaosha_order` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀订单id',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `order_id` BIGINT(20) NOT NULL COMMENT '订单id',
  `goods_id` BIGINT(20) DEFAULT NULL COMMENT '商品id',
  PRIMARY KEY (`id`),
  KEY `index_miaosha_order_on_user_id` (`user_id`),
  KEY `index_miaosha_order_on_user_order_id` (`order_id`),
  KEY `index_miaosha_order_on_user_goods_id` (`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

# 测试使用
# CREATE TABLE `users` (
#   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表id',
#   `username` varchar(50) NOT NULL COMMENT '用户名',
#   `password` varchar(50) NOT NULL COMMENT '用户密码',
#   `register_date` datetime DEFAULT NULL COMMENT '注册时间',
#   PRIMARY KEY (`id`)
# ) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
