/*
 Navicat Premium Data Transfer

 Source Server         : 119.29.157.231【服务器】
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : 119.29.157.231:3306
 Source Schema         : work_board

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 24/09/2022 19:56:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_users
-- ----------------------------
DROP TABLE IF EXISTS `admin_users`;
CREATE TABLE `admin_users`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户名【1. 超管暂时使用邮箱验证，2、超管创建的管理员是自定义的账号格式（如：tju2021）】',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '密码',
  `avatar_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '头像url',
  `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '姓名',
  `status` tinyint NULL DEFAULT 1 COMMENT '用户状态【1：可用，0：锁定】',
  `org_id` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '组织ID',
  `salt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '密码的盐值',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `admin_users_email_uindex`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'B端用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for clazz
-- ----------------------------
DROP TABLE IF EXISTS `clazz`;
CREATE TABLE `clazz`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '班级ID',
  `grade` smallint UNSIGNED NOT NULL DEFAULT 0 COMMENT '年级ID',
  `class_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '班级名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '行政班表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` smallint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '课程ID',
  `semester_id` smallint UNSIGNED NOT NULL DEFAULT 0 COMMENT '学期ID',
  `user_id` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户ID',
  `course_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '课程名称',
  `course_type` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '课程类型【0：选修，1：必修】',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '教学班表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for courses_clazz
-- ----------------------------
DROP TABLE IF EXISTS `courses_clazz`;
CREATE TABLE `courses_clazz`  (
  `course_id` smallint UNSIGNED NOT NULL COMMENT '课程ID',
  `class_id` smallint UNSIGNED NOT NULL COMMENT '班级ID',
  `org_id` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '组织ID',
  UNIQUE INDEX `course-class_pk`(`course_id`, `class_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '教学班-行政班表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for organization
-- ----------------------------
DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization`  (
  `id` tinyint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '组织ID',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组织名称',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `background` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '组织背景【旗帜】',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `organization_name_uindex`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '组织表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for resources
-- ----------------------------
DROP TABLE IF EXISTS `resources`;
CREATE TABLE `resources`  (
  `id` smallint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '资源ID',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '资源名称',
  `uri` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '映射路由',
  `permission` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '具体权限',
  `type` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '资源类型【1目录，2：菜单，3：资源】',
  `sn` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '序号',
  `icon` varchar(280) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '图标',
  `parent_id` smallint UNSIGNED NOT NULL DEFAULT 0 COMMENT '父级资源【0：无父资源】',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '资源表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `id` tinyint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `intro` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '角色简介',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `roles_name_uindex`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for roles_resources
-- ----------------------------
DROP TABLE IF EXISTS `roles_resources`;
CREATE TABLE `roles_resources`  (
  `role_id` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '角色ID',
  `resource_id` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '资源ID',
  PRIMARY KEY (`role_id`, `resource_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色-资源表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for semesters
-- ----------------------------
DROP TABLE IF EXISTS `semesters`;
CREATE TABLE `semesters`  (
  `id` smallint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '学期ID',
  `semester_describe` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '学期描述',
  `start_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `weeks` tinyint UNSIGNED NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '学期表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tags
-- ----------------------------
DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags`  (
  `id` smallint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `detail` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '标签细节',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `tags_detail_uindex`(`detail`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '标签表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `openid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户微信openID',
  `avatar_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户头像地址',
  `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户姓名',
  `student_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '学号',
  `class_id` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '班级ID',
  `org_id` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `users_openid_uindex`(`openid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for users_courses
-- ----------------------------
DROP TABLE IF EXISTS `users_courses`;
CREATE TABLE `users_courses`  (
  `user_id` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户ID',
  `course_id` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '班级ID',
  PRIMARY KEY (`user_id`, `course_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户-教学班表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for users_roles
-- ----------------------------
DROP TABLE IF EXISTS `users_roles`;
CREATE TABLE `users_roles`  (
  `user_id` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户ID',
  `role_id` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户-角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for users_works
-- ----------------------------
DROP TABLE IF EXISTS `users_works`;
CREATE TABLE `users_works`  (
  `user_id` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户ID',
  `work_id` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '作业ID',
  `work_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '阅读状态【0：未读，1：已读】',
  `work_pin` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否完成【0：未完成，1：已完成】',
  `times` smallint NOT NULL DEFAULT 0,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`, `work_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户-作业表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for work
-- ----------------------------
DROP TABLE IF EXISTS `work`;
CREATE TABLE `work`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '作业ID',
  `course_id` smallint UNSIGNED NOT NULL DEFAULT 0 COMMENT '课程ID',
  `semester_id` smallint UNSIGNED NOT NULL DEFAULT 0 COMMENT '学期ID',
  `uid` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户ID',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '作业标题',
  `detail` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '作业内容',
  `dateline` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '截止日期',
  `show` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否展示【1：展示，0：不展示】',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '作业表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for works_tags
-- ----------------------------
DROP TABLE IF EXISTS `works_tags`;
CREATE TABLE `works_tags`  (
  `work_id` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '作业ID',
  `tag_id` smallint UNSIGNED NOT NULL DEFAULT 0 COMMENT '标签ID',
  PRIMARY KEY (`work_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '作业-标签表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
