/*
 Navicat Premium Data Transfer
 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 27/09/2022 09:58:46
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
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'B端用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_users
-- ----------------------------
INSERT INTO `admin_users` VALUES (5, '1@qq.com', 'c7999a946c5e6495607393e8946095d0', '', '222', 1, 13, '16f74b');
INSERT INTO `admin_users` VALUES (9, 'stu2021', 'bd13a5ae42f6820457b031e2a6eafaf7', '', 'Ciusyan', 1, 13, 'c9f093');
INSERT INTO `admin_users` VALUES (10, '2@qq.com', '5c4d1b57f51f4dce2a3c575b9e105ebb', '', '', 1, 22, 'eddf6b');

-- ----------------------------
-- Table structure for admin_users_roles
-- ----------------------------
DROP TABLE IF EXISTS `admin_users_roles`;
CREATE TABLE `admin_users_roles`  (
  `user_id` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户ID',
  `role_id` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户-角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_users_roles
-- ----------------------------
INSERT INTO `admin_users_roles` VALUES (9, 2);
INSERT INTO `admin_users_roles` VALUES (10, 1);

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
-- Records of clazz
-- ----------------------------

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
-- Records of course
-- ----------------------------

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
-- Records of courses_clazz
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '组织表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of organization
-- ----------------------------
INSERT INTO `organization` VALUES (13, '华信软件学院', '2022-09-24 10:27:35', '');
INSERT INTO `organization` VALUES (22, NULL, '2022-09-26 20:40:17', '');

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
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '资源表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of resources
-- ----------------------------
INSERT INTO `resources` VALUES (1, '系统数据', '/main/data', '', 1, 0, '', 0);
INSERT INTO `resources` VALUES (2, '系统管理', '/main/system', '', 1, 0, '', 0);
INSERT INTO `resources` VALUES (3, '班级中心', '/class/center', '', 1, 0, '', 0);
INSERT INTO `resources` VALUES (4, '作业中心', '/work/center', '', 1, 0, '', 0);
INSERT INTO `resources` VALUES (5, '系统总揽', '/main/system/overall', '', 2, 0, '', 1);
INSERT INTO `resources` VALUES (6, '班级统计', '/main/system/classStatistics', '', 2, 0, '', 1);
INSERT INTO `resources` VALUES (7, '组织管理', '/main/system/organization', '', 2, 0, '', 2);
INSERT INTO `resources` VALUES (8, 'B端用户管理', '/main/system/adminUser', '', 2, 0, '', 2);
INSERT INTO `resources` VALUES (9, 'C端用户管理', '/main/system/user', '', 2, 0, '', 2);
INSERT INTO `resources` VALUES (10, '角色管理', '/main/system/role', '', 2, 0, '', 2);
INSERT INTO `resources` VALUES (11, '行政班级', '/class/center/executive', '', 2, 0, '', 3);
INSERT INTO `resources` VALUES (12, '教学班级', '/class/center/education', '', 2, 0, '', 3);
INSERT INTO `resources` VALUES (13, '作业管理', '/work/center/manage', '', 2, 0, '', 4);
INSERT INTO `resources` VALUES (14, '查询组织', '', 'sysOrganization:read', 3, 0, '', 7);
INSERT INTO `resources` VALUES (15, '修改组织', '', 'sysOrganization:update', 3, 0, '', 7);
INSERT INTO `resources` VALUES (16, '查询B端用户', '', 'sysAdminUser:read', 3, 0, '', 8);
INSERT INTO `resources` VALUES (17, '修改B端用户', '', 'sysAdminUser:update', 3, 0, '', 8);
INSERT INTO `resources` VALUES (18, '删除B端用户', '', 'sysAdminUser:delete', 3, 0, '', 8);
INSERT INTO `resources` VALUES (19, '创建B端用户', '', 'sysAdminUser:create', 3, 0, '', 8);
INSERT INTO `resources` VALUES (20, '查询C端用户', '', 'sysUser:read', 3, 0, '', 9);
INSERT INTO `resources` VALUES (21, '修改C端用户', '', 'sysUser:update', 3, 0, '', 9);
INSERT INTO `resources` VALUES (22, '删除C端用户', '', 'sysUser:delete', 3, 0, '', 9);
INSERT INTO `resources` VALUES (23, '查询角色', '', 'sysRole:read', 3, 0, '', 10);
INSERT INTO `resources` VALUES (24, '修改角色', '', 'sysRole:update', 3, 0, '', 10);
INSERT INTO `resources` VALUES (25, '删除角色', '', 'sysRole:delete', 3, 0, '', 10);
INSERT INTO `resources` VALUES (26, '创建角色', '', 'sysRole:create', 3, 0, '', 10);
INSERT INTO `resources` VALUES (27, '查询行政班', '', 'clsExecutive:read', 3, 0, '', 11);
INSERT INTO `resources` VALUES (28, '修改行政班', '', 'clsExecutive:update', 3, 0, '', 11);
INSERT INTO `resources` VALUES (29, '删除行政班', '', 'clsExecutive:delete', 3, 0, '', 11);
INSERT INTO `resources` VALUES (30, '创建行政班', '', 'clsExecutive:create', 3, 0, '', 11);
INSERT INTO `resources` VALUES (31, '查询教学班', '', 'clsEducation:read', 3, 0, '', 12);
INSERT INTO `resources` VALUES (32, '修改教学班', '', 'clsEducation:update', 3, 0, '', 12);
INSERT INTO `resources` VALUES (33, '删除教学班', '', 'clsEducation:delete', 3, 0, '', 12);
INSERT INTO `resources` VALUES (34, '创建教学班', '', 'clsEducation:create', 3, 0, '', 12);
INSERT INTO `resources` VALUES (35, '查询作业', '', 'workManage:read', 3, 0, '', 13);
INSERT INTO `resources` VALUES (36, '修改作业', '', 'workManage:update', 3, 0, '', 13);
INSERT INTO `resources` VALUES (37, '删除作业', '', 'workManage:delete', 3, 0, '', 13);
INSERT INTO `resources` VALUES (38, '创建作业', '', 'workManage:create', 3, 0, '', 13);

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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES (1, '超级管理员', '什么权限都有，组织发起者必须是这个角色');

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
-- Records of roles_resources
-- ----------------------------
INSERT INTO `roles_resources` VALUES (1, 1);
INSERT INTO `roles_resources` VALUES (1, 2);
INSERT INTO `roles_resources` VALUES (1, 3);
INSERT INTO `roles_resources` VALUES (1, 4);
INSERT INTO `roles_resources` VALUES (1, 5);
INSERT INTO `roles_resources` VALUES (1, 6);
INSERT INTO `roles_resources` VALUES (1, 7);
INSERT INTO `roles_resources` VALUES (1, 8);
INSERT INTO `roles_resources` VALUES (1, 9);
INSERT INTO `roles_resources` VALUES (1, 10);
INSERT INTO `roles_resources` VALUES (1, 11);
INSERT INTO `roles_resources` VALUES (1, 12);
INSERT INTO `roles_resources` VALUES (1, 13);
INSERT INTO `roles_resources` VALUES (1, 14);
INSERT INTO `roles_resources` VALUES (1, 15);
INSERT INTO `roles_resources` VALUES (1, 16);
INSERT INTO `roles_resources` VALUES (1, 17);
INSERT INTO `roles_resources` VALUES (1, 18);
INSERT INTO `roles_resources` VALUES (1, 19);
INSERT INTO `roles_resources` VALUES (1, 20);
INSERT INTO `roles_resources` VALUES (1, 21);
INSERT INTO `roles_resources` VALUES (1, 22);
INSERT INTO `roles_resources` VALUES (1, 23);
INSERT INTO `roles_resources` VALUES (1, 24);
INSERT INTO `roles_resources` VALUES (1, 25);
INSERT INTO `roles_resources` VALUES (1, 26);
INSERT INTO `roles_resources` VALUES (1, 27);
INSERT INTO `roles_resources` VALUES (1, 28);
INSERT INTO `roles_resources` VALUES (1, 29);
INSERT INTO `roles_resources` VALUES (1, 30);
INSERT INTO `roles_resources` VALUES (1, 31);
INSERT INTO `roles_resources` VALUES (1, 32);
INSERT INTO `roles_resources` VALUES (1, 33);
INSERT INTO `roles_resources` VALUES (1, 34);
INSERT INTO `roles_resources` VALUES (1, 35);
INSERT INTO `roles_resources` VALUES (1, 36);
INSERT INTO `roles_resources` VALUES (1, 37);
INSERT INTO `roles_resources` VALUES (1, 38);

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '学期表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of semesters
-- ----------------------------

-- ----------------------------
-- Table structure for tags
-- ----------------------------
DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags`  (
  `id` smallint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `detail` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '标签细节',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `tags_detail_uindex`(`detail`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '标签表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tags
-- ----------------------------

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
-- Records of users
-- ----------------------------

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
-- Records of users_courses
-- ----------------------------

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
-- Records of users_works
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '作业表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of work
-- ----------------------------

-- ----------------------------
-- Table structure for works_tags
-- ----------------------------
DROP TABLE IF EXISTS `works_tags`;
CREATE TABLE `works_tags`  (
  `work_id` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '作业ID',
  `tag_id` smallint UNSIGNED NOT NULL DEFAULT 0 COMMENT '标签ID',
  PRIMARY KEY (`work_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '作业-标签表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of works_tags
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
