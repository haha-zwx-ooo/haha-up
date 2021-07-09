-- ----------------------------
-- 订单表--order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT,
    `pid`     int(11) DEFAULT NULL,
    `user_id` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1513 DEFAULT CHARSET=utf8mb4;
.



-- ----------------------------
-- 商品表--product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT,
    `product_name` varchar(255) DEFAULT NULL,
    `stock`        int(11) DEFAULT NULL,
    `version`      int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('1', '元旦大礼包', '5', '0');
