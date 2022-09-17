/*
 Navicat Premium Data Transfer

 Source Server         : study
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost:3306
 Source Schema         : test_mybatis

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 05/09/2022 20:19:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


-- ----------------------------
-- Table structure for skill
-- ----------------------------
DROP TABLE IF EXISTS `skill`;
CREATE TABLE `skill`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `level` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 227 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of skill
-- ----------------------------
INSERT INTO `skill` VALUES (162, '2022-02-09 12:00:14', 'tom', 1);
INSERT INTO `skill` VALUES (166, '2022-02-09 12:00:14', 'jack', 2);
INSERT INTO `skill` VALUES (169, '2022-02-09 12:00:14', 'zhiyan', 1);
INSERT INTO `skill` VALUES (170, '2022-02-09 12:00:14', 'John', 4);
INSERT INTO `skill` VALUES (177, '2022-02-09 12:00:14', 'Jean', 4);
INSERT INTO `skill` VALUES (178, '2022-02-09 12:00:14', 'bean', 5);
INSERT INTO `skill` VALUES (195, '2022-02-13 09:19:30', 'jack', 5);
INSERT INTO `skill` VALUES (196, '2022-02-13 09:19:30', 'tomcat', 4);
INSERT INTO `skill` VALUES (197, '2022-02-13 09:19:30', 'Ajax', 7);
INSERT INTO `skill` VALUES (198, '2022-02-13 09:24:05', 'zhiyan', 29);
INSERT INTO `skill` VALUES (202, '2022-02-15 09:20:16', 'jax', 199);
INSERT INTO `skill` VALUES (206, '2022-02-15 10:53:11', 'ajxsss', 2222);
INSERT INTO `skill` VALUES (207, '2022-02-15 10:58:43', 'ajx', 2222);
INSERT INTO `skill` VALUES (208, '2022-02-15 11:00:28', 'ajx', 2222);
INSERT INTO `skill` VALUES (209, '2022-02-15 11:06:24', 'ajx', 2222);
INSERT INTO `skill` VALUES (212, '2022-03-09 13:09:41', '12313', 123123123);
INSERT INTO `skill` VALUES (218, '2022-03-30 20:30:06', '志颜', 100);
INSERT INTO `skill` VALUES (219, '2022-03-31 13:47:47', '志颜', 100);
INSERT INTO `skill` VALUES (220, '2022-04-01 10:20:50', '志颜', 100);
INSERT INTO `skill` VALUES (223, '2022-04-17 13:07:59', '测试测是', 22);
INSERT INTO `skill` VALUES (224, '2022-04-17 13:09:20', '试试', 2);
INSERT INTO `skill` VALUES (225, '2022-04-17 13:29:35', '哈哈哈试试后端校验', 2);
INSERT INTO `skill` VALUES (226, '2022-04-17 22:31:02', '哈哈哈试试后端校验', 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `created_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `login_time` datetime NULL DEFAULT NULL COMMENT '最后一次登录时间',
  `email` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '邮箱',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `state` tinyint(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态【0,：禁用 1：可用】',
  `nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '昵称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_email_uindex`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '2022-04-17 17:48:15', '2022-04-19 01:50:35', 'zhiyan@qq.com', '222', 1, '志颜');


SET FOREIGN_KEY_CHECKS = 1;
