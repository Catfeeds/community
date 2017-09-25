/*
Navicat MySQL Data Transfer

Source Server         : Community
Source Server Version : 50709
Source Host           : localhost:3306
Source Database       : cloud_community

Target Server Type    : MYSQL
Target Server Version : 50709
File Encoding         : 65001

Date: 2017-09-12 14:56:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area` (
  `id` varchar(36) UNIQUE NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `build_date` date DEFAULT NULL,
  `charge_id` varchar(32) DEFAULT NULL,
  `residence_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_AREA_2_USER` (`charge_id`),
  KEY `FK_AREA_2_RESIDENCE` (`residence_id`),
  CONSTRAINT `FK_AREA_2_RESIDENCE` FOREIGN KEY (`residence_id`) REFERENCES `residence` (`id`),
  CONSTRAINT `FK_AREA_2_USER` FOREIGN KEY (`charge_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='小区分区表';

-- ----------------------------
-- Records of area
-- ----------------------------
INSERT INTO `area` VALUES ('1', '吴中区', '2017-07-01', '2', '1');
INSERT INTO `area` VALUES ('2', '吴江区', '2017-07-12', '2', '1');

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `id` varchar(36) UNIQUE NOT NULL,
  `file_name` varchar(100) DEFAULT NULL,
  `file_path` varchar(100) DEFAULT NULL,
  `file_type` varchar(10) DEFAULT NULL,
  `des` varchar(100) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `upload_user_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_FILE_2_USER` (`upload_user_id`),
  CONSTRAINT `FK_FILE_2_USER` FOREIGN KEY (`upload_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件表';

-- ----------------------------
-- Records of file
-- ----------------------------
INSERT INTO `file` VALUES ('1', 'www', 'D:/', '报表', '财务报表', '2', '2');
INSERT INTO `file` VALUES ('2', '停电通知', 'D:/', '紧急', '停电通知', '1', '1');
INSERT INTO `file` VALUES ('402880aa5d882091015d8843dccb0000', 'welcome.jpg', '/uploadAvatar/2017/7/293925cd-3d9c-4217-a283-d6ce2df98317.jpg', 'image', null, null, '2');

-- ----------------------------
-- Table structure for floor
-- ----------------------------
DROP TABLE IF EXISTS `floor`;
CREATE TABLE `floor` (
  `id` varchar(36) UNIQUE NOT NULL,
  `code` varchar(20) DEFAULT NULL COMMENT '楼宇号',
  `name` varchar(10) DEFAULT NULL,
  `parent_code` varchar(20) DEFAULT NULL COMMENT '单元号',
  `principal_id` varchar(32) DEFAULT NULL COMMENT '负责人，业主',
  `area_id` varchar(32) DEFAULT NULL COMMENT '地区ID',
  PRIMARY KEY (`id`),
  KEY `FK_FLOOR_2_AREA` (`area_id`),
  KEY `FK_FLOOR_2_USER` (`principal_id`),
  CONSTRAINT `FK_FLOOR_2_AREA` FOREIGN KEY (`area_id`) REFERENCES `area` (`id`),
  CONSTRAINT `FK_FLOOR_2_USER` FOREIGN KEY (`principal_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='小区单元楼表';

-- ----------------------------
-- Records of floor
-- ----------------------------
INSERT INTO `floor` VALUES ('1', '1001', '花园小区1001栋', '1', '2', '1');
INSERT INTO `floor` VALUES ('2', '1002', '花园小区1002栋', '1', '1', '1');

-- ----------------------------
-- Table structure for group_role
-- ----------------------------
DROP TABLE IF EXISTS `group_role`;
CREATE TABLE `group_role` (
  `id` varchar(36) UNIQUE NOT NULL,
  `group_id` varchar(32) DEFAULT NULL,
  `role_id` varchar(32) DEFAULT NULL,
  `des` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_GROUP_RO_2_ROLE` (`role_id`),
  KEY `FK_GROUP_RO_2_SUBGROUP` (`group_id`),
  CONSTRAINT `FK_GROUP_RO_2_ROLE` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK_GROUP_RO_2_SUBGROUP` FOREIGN KEY (`group_id`) REFERENCES `subgroup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='群组角色关系表';

-- ----------------------------
-- Records of group_role
-- ----------------------------

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` varchar(36) UNIQUE NOT NULL,
  `content` longtext,
  `record_date` datetime DEFAULT NULL,
  `operator_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_LOG_2_USER` (`operator_id`),
  CONSTRAINT `FK_LOG_2_USER` FOREIGN KEY (`operator_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志表';

-- ----------------------------
-- Records of log
-- ----------------------------

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` varchar(36) UNIQUE NOT NULL,
  `title` varchar(50) DEFAULT NULL,
  `content` longtext,
  `file_id` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` varchar(32) DEFAULT NULL,
  `message_type_id` varchar(32) DEFAULT NULL,
  `process_state` tinyint(4) DEFAULT NULL COMMENT '主要展示投诉类型消息的处理进度',
  `repair_start_time` datetime DEFAULT NULL,
  `repair_end_time` datetime DEFAULT NULL,
  `residence_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_MESSAGE_2_USER` (`create_user_id`),
  KEY `FK_MESSAGE_2_TYPE` (`message_type_id`),
  KEY `FK_MESSAGE_2_FILE` (`file_id`),
  KEY `FK_MESSAGE_2_RESIDENCE` (`residence_id`),
  CONSTRAINT `FK_MESSAGE_2_FILE` FOREIGN KEY (`file_id`) REFERENCES `file` (`id`),
  CONSTRAINT `FK_MESSAGE_2_RESIDENCE` FOREIGN KEY (`residence_id`) REFERENCES `residence` (`id`),
  CONSTRAINT `FK_MESSAGE_2_TYPE` FOREIGN KEY (`message_type_id`) REFERENCES `message_type` (`id`),
  CONSTRAINT `FK_MESSAGE_2_USER` FOREIGN KEY (`create_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息表';

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('402880aa5d4f8afc015d4f90dbbc0000', 'ceshi', 'ceshi', null, '2017-07-17 16:01:20', '1', '1', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d5e21f2015d5e26f87a0000', '停电通知', '停电通知', '1', '2017-07-20 11:59:53', '1', '6', '0', null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d5e8ac9015d5e8d1b6c0000', '停水通知', '经水管理局最新发布的消息，从今天至后天将对1,2,3期的水源进行不定时的停用操作，因此请大家及时备水。由此带给大家生活的不便还望体谅。', null, '2017-07-20 13:51:29', '1', '6', '0', null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d5e8ac9015d5e9049280001', '关于临时停车收费标准温馨提示', '关于临时停车收费标准，是建立在苏州吴中区收费标准之上建立的，望大家能够自觉遵守该标准，如有异议，请致电物业！', null, '2017-07-20 13:55:01', '1', '6', '0', null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d5f44f2015d5f4663290000', '关于临时停车收费标准温馨提示', '关于临时停车收费标准，是建立在苏州吴中区收费标准之上建立的，望大家能够自觉遵守该标准，如有异议，请致电物业！', null, '2017-07-20 17:13:54', '1', '3', '0', null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d5f5fdf015d62cf4efa0000', '关于临时停车收费标准温馨提示', '关于临时停车收费标准，是建立在苏州吴中区收费标准之上建立的，望大家能够自觉遵守该标准，如有异议，请致电物业！', null, '2017-07-21 09:42:19', '1', '3', '0', null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d62e674015d62f30daf0000', '关于临时停车收费标准温馨提示', '关于临时停车收费标准，是建立在苏州吴中区收费标准之上建立的，望大家能够自觉遵守该标准，如有异议，请致电物业！关于临时停车收费标准，是建立在苏州吴中区收费标准之上建立的，望大家能够自觉遵守该标准，如有异议，请致电物业!', null, '2017-07-21 10:21:22', '1', '3', '0', null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d62e674015d63b098160001', '高考温馨提示', '近期迎接全国高考，望各位家长注意孩子的饮食与作息时间!', null, '2017-07-21 13:48:24', '1', '7', '0', null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d62e674015d63d5414b0002', '越湖名邸1期增加绿植', '越湖名邸1期增加绿植，投票开始!', null, '2017-07-21 14:28:27', '1', '4', '0', null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d62e674015d63d5a6110003', '我区甜心小朋友参加我市组织的童年艺术活动，望大家投票支持', '我区甜心小朋友参加我市组织的童年艺术活动，望大家投票支持!', null, '2017-07-21 14:28:52', '1', '4', '0', null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d62e674015d63d5ee550004', '物业公司对后勤服务员考核投票开始', '物业公司对后勤服务员考核投票开始!', null, '2017-07-21 14:29:11', '1', '4', '0', null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d62e674015d63d640c70005', '我区需要重新选举妇联主任，请大家投票', '我区需要重新选举妇联主任，请大家投票!', null, '2017-07-21 14:29:32', '1', '4', '0', null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d62e674015d63d68b4b0006', '新颖发艺申请入驻小区内，请大家投票表决', '新颖发艺申请入驻小区内，请大家投票表决!', null, '2017-07-21 14:29:51', '1', '4', '0', null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d62e674015d63d7f5360007', '针对小区饭店卫生安全问题，请大家填调查问卷', '针对小区饭店卫生安全问题，请大家填调查问卷!', null, '2017-07-21 14:31:24', '1', '4', '0', null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d62e674015d63fbb6f40008', '夏天吃素有“讲究”', '夏天吃素有“讲究”!', null, '2017-07-21 15:10:27', '1', '7', '0', null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d62e674015d63fc6b080009', '叫师傅不容易|教师节，给您的老师表个白', '叫师傅不容易|教师节，给您的老师表个白!', null, '2017-07-21 15:11:13', '1', '7', '0', null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d62e674015d63fcd6cf000a', '夏日炎炎，戏水也得注意安全', '夏日炎炎，戏水也得注意安全!', null, '2017-07-21 15:11:41', '1', '7', '0', null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cc776015d7cd828280000', '通知消息', '这是一条通知消息1', null, '2017-07-26 11:02:06', '1', '3', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7cdc5d6c0000', '通知消息', '这是一条通知消息2', null, '2017-07-26 11:06:42', '1', '3', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7cdc71fb0001', '通知消息', '这是一条通知消息3', null, '2017-07-26 11:06:48', '1', '3', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7cdc80330002', '通知消息', '这是一条通知消息4', null, '2017-07-26 11:06:52', '1', '3', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7cdc910e0003', '通知消息', '这是一条通知消息5', null, '2017-07-26 11:06:56', '1', '3', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7cdd4af70005', '通知消息', '这是一条通知消息6', null, '2017-07-26 11:07:44', '1', '3', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7cdd5af90006', '通知消息', '这是一条通知消息7', null, '2017-07-26 11:07:48', '1', '3', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7cdd67e90007', '通知消息', '这是一条通知消息8', null, '2017-07-26 11:07:51', '1', '3', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7cdd75a50008', '通知消息', '这是一条通知消息9', null, '2017-07-26 11:07:55', '1', '3', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7cdd86dc0009', '通知消息', '这是一条通知消息10', null, '2017-07-26 11:07:59', '1', '3', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7cde8fcd000b', '提示消息', '这是一条提示消息1', null, '2017-07-26 11:09:07', '1', '7', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7cdea8dd000c', '提示消息', '这是一条提示消息2', null, '2017-07-26 11:09:13', '1', '7', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7cdebdd6000d', '提示消息', '这是一条提示消息3', null, '2017-07-26 11:09:19', '1', '7', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7cdecc04000e', '提示消息', '这是一条提示消息4', null, '2017-07-26 11:09:22', '1', '7', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7cded8da000f', '提示消息', '这是一条提示消息5', null, '2017-07-26 11:09:26', '1', '7', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7cdee7dd0010', '提示消息', '这是一条提示消息6', null, '2017-07-26 11:09:29', '1', '7', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7cdef5220011', '提示消息', '这是一条提示消息7', null, '2017-07-26 11:09:33', '1', '7', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7cdf03330012', '提示消息', '这是一条提示消息8', null, '2017-07-26 11:09:36', '1', '7', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7cdf10600013', '提示消息', '这是一条提示消息9', null, '2017-07-26 11:09:40', '1', '7', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7cdf26930014', '提示消息', '这是一条提示消息10', null, '2017-07-26 11:09:45', '1', '7', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7cdfdf9b0015', '紧急消息', '这是一条紧急消息1', null, '2017-07-26 11:10:33', '1', '6', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7cdfee380016', '紧急消息', '这是一条紧急消息2', null, '2017-07-26 11:10:37', '1', '6', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7cdffad40017', '紧急消息', '这是一条紧急消息3', null, '2017-07-26 11:10:40', '1', '6', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7ce007aa0018', '紧急消息', '这是一条紧急消息4', null, '2017-07-26 11:10:43', '1', '6', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7ce0131f0019', '紧急消息', '这是一条紧急消息5', null, '2017-07-26 11:10:46', '1', '6', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7ce01f3e001a', '紧急消息', '这是一条紧急消息6', null, '2017-07-26 11:10:49', '1', '6', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7ce02b7d001b', '紧急消息', '这是一条紧急消息7', null, '2017-07-26 11:10:52', '1', '6', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7ce038e7001c', '紧急消息', '这是一条紧急消息8', null, '2017-07-26 11:10:56', '1', '6', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7ce04477001d', '紧急消息', '这是一条紧急消息9', null, '2017-07-26 11:10:59', '1', '6', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7ce0532f001e', '紧急消息', '这是一条紧急消息10', null, '2017-07-26 11:11:02', '1', '6', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7ce0dbae001f', '投票消息', '这是一条投票消息1', null, '2017-07-26 11:11:37', '1', '4', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7ce0e77e0020', '投票消息', '这是一条投票消息2', null, '2017-07-26 11:11:40', '1', '4', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7ce0f1bc0021', '投票消息', '这是一条投票消息3', null, '2017-07-26 11:11:43', '1', '4', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7ce0fd140022', '投票消息', '这是一条投票消息4', null, '2017-07-26 11:11:46', '1', '4', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7ce107d00023', '投票消息', '这是一条投票消息5', null, '2017-07-26 11:11:49', '1', '4', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7ce114140024', '投票消息', '这是一条投票消息6', null, '2017-07-26 11:11:52', '1', '4', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7ce11d770025', '投票消息', '这是一条投票消息7', null, '2017-07-26 11:11:54', '1', '4', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7ce129060026', '投票消息', '这是一条投票消息8', null, '2017-07-26 11:11:57', '1', '4', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7ce134470027', '投票消息', '这是一条投票消息9', null, '2017-07-26 11:12:00', '1', '4', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7ce143620028', '投票消息', '这是一条投票消息10', null, '2017-07-26 11:12:04', '1', '4', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7e5e82e40029', '通知消息', '这是一条通知消息11', null, '2017-07-26 18:08:29', '1', '3', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7e5e9e9b002a', '通知消息', '这是一条通知消息12', null, '2017-07-26 18:08:37', '1', '3', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7e5eb0c5002b', '通知消息', '这是一条通知消息13', null, '2017-07-26 18:08:41', '1', '3', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7e5ebf41002c', '通知消息', '这是一条通知消息14', null, '2017-07-26 18:08:45', '1', '3', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7e5ece1b002d', '通知消息', '这是一条通知消息15', null, '2017-07-26 18:08:49', '1', '3', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7e5eddec002e', '通知消息', '这是一条通知消息16', null, '2017-07-26 18:08:53', '1', '3', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7e5eebb4002f', '通知消息', '这是一条通知消息17', null, '2017-07-26 18:08:56', '1', '3', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d7cdbd2015d7e5efbe60030', '通知消息', '这是一条通知消息18', null, '2017-07-26 18:09:00', '1', '3', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d871b66015d871bec5a0000', '通知消息', '这是一条通知消息19', null, '2017-07-28 10:52:18', '1', '3', null, null, null, '1');
INSERT INTO `message` VALUES ('402880aa5d871b66015d873ca98e0001', '通知消息', '这是通知消息', null, '2017-07-28 11:28:06', '1', '3', null, null, null, '1');

-- ----------------------------
-- Table structure for message_comment
-- ----------------------------
DROP TABLE IF EXISTS `message_comment`;
CREATE TABLE `message_comment` (
  `id` varchar(36) UNIQUE UNIQUE NOT NULL,
  `message_id` varchar(32) DEFAULT NULL,
  `is_like` bit(1) DEFAULT NULL,
  `comment` longtext,
  `comment_date` datetime DEFAULT NULL,
  `commentator_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_MESSAGE_2_COMMENT` (`message_id`),
  KEY `FK_COMMENT_2_USER` (`commentator_id`),
  CONSTRAINT `FK_COMMENT_2_USER` FOREIGN KEY (`commentator_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_MESSAGE_2_COMMENT` FOREIGN KEY (`message_id`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息评论表';

-- ----------------------------
-- Records of message_comment
-- ----------------------------

-- ----------------------------
-- Table structure for message_type
-- ----------------------------
DROP TABLE IF EXISTS `message_type`;
CREATE TABLE `message_type` (
  `id` varchar(36) UNIQUE NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `code` varchar(32) DEFAULT NULL,
  `des` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息类型';

-- ----------------------------
-- Records of message_type
-- ----------------------------
INSERT INTO `message_type` VALUES ('1', '推送消息', 'PUSH_MESSAGE', '该消息类型专门为接受快递消息所设');
INSERT INTO `message_type` VALUES ('2', '新闻消息', 'NEW_MESSAGE', null);
INSERT INTO `message_type` VALUES ('3', '通知消息', 'NOTIFY_MESSAGE', null);
INSERT INTO `message_type` VALUES ('4', '投票消息', 'VOTE_MESSAGE', null);
INSERT INTO `message_type` VALUES ('5', '投诉消息', 'COMPLAINT_MESSAGE', null);
INSERT INTO `message_type` VALUES ('6', '紧急消息', 'HURRY_MESSAGE', null);
INSERT INTO `message_type` VALUES ('7', '提示消息', 'NODE_MESSAGE', null);
INSERT INTO `message_type` VALUES ('8', '动态消息', 'DYNAMIC_MESSAGE', null);
INSERT INTO `message_type` VALUES ('9', '在线服务消息', 'ONLINE_MESSAGE', null);

-- ----------------------------
-- Table structure for region
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region` (
  `id` varchar(36) UNIQUE NOT NULL,
  `region_code` varchar(10) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `parent_code` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='区域表';

-- ----------------------------
-- Records of region
-- ----------------------------
INSERT INTO `region` VALUES ('1', '110101', '', '110000');
INSERT INTO `region` VALUES ('2', '110102', null, '110000');

-- ----------------------------
-- Table structure for residence
-- ----------------------------
DROP TABLE IF EXISTS `residence`;
CREATE TABLE `residence` (
  `id` varchar(36) UNIQUE NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `floor_count` int(3) DEFAULT NULL,
  `region_id` varchar(32) DEFAULT NULL,
  `server_url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_RESIDENC_2_REGION` (`region_id`),
  KEY `FK_RESIDENC_2_USER` (`user_id`),
  CONSTRAINT `FK_RESIDENC_2_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='社区表';

-- ----------------------------
-- Records of residence
-- ----------------------------
INSERT INTO `residence` VALUES ('1', '鲁能公馆', '11111111111111111', '1', '200', '1', 'http://192.168.0.42');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` varchar(36) UNIQUE NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `des` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of role
-- ----------------------------

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `id` varchar(36) UNIQUE NOT NULL,
  `room_code` varchar(10) DEFAULT NULL,
  `area` float(4,1) DEFAULT NULL,
  `hu_xing` varchar(1) DEFAULT NULL,
  `client_code` varchar(32) DEFAULT NULL,
  `owner_id` varchar(32) DEFAULT NULL,
  `unit_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ROOM_2_FLOOR` (`unit_id`),
  KEY `FK_ROOM_2_USER` (`owner_id`),
  CONSTRAINT `FK_ROOM_2_FLOOR` FOREIGN KEY (`unit_id`) REFERENCES `floor` (`id`),
  CONSTRAINT `FK_ROOM_2_USER` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='房间表';

-- ----------------------------
-- Records of room
-- ----------------------------
INSERT INTO `room` VALUES ('1', '1001001', '1.0', '大', null, '1', '2');
INSERT INTO `room` VALUES ('2', '1001002', '1.0', '大', null, '1', '1');

-- ----------------------------
-- Table structure for subgroup
-- ----------------------------
DROP TABLE IF EXISTS `subgroup`;
CREATE TABLE `subgroup` (
  `id` varchar(36) UNIQUE NOT NULL,
  `name` varchar(10) DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  `des` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_SUBGROUP_2_SUBGROUP` (`parent_id`),
  CONSTRAINT `FK_SUBGROUP_2_SUBGROUP` FOREIGN KEY (`parent_id`) REFERENCES `subgroup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='群组表';

-- ----------------------------
-- Records of subgroup
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(36) UNIQUE NOT NULL,
  `account` varchar(20) DEFAULT NULL,
  `nick_name` varchar(20) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `signature` varchar(120) DEFAULT NULL,
  `sex` tinyint(4) DEFAULT NULL,
  `avatar_file_id` varchar(32) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `password` varchar(60) DEFAULT NULL,
  `id_card` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `client_id` varchar(32) DEFAULT NULL,
  `add_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `state` tinyint(4) DEFAULT 1,
  PRIMARY KEY (`id`),
  KEY `FK_USER_2_FILE` (`avatar_file_id`),
  CONSTRAINT `FK_USER_2_FILE` FOREIGN KEY (`avatar_file_id`) REFERENCES `file` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'Root', '管理员', '2016-06-17', '哈哈哈，我是管理员', '1', null, '张星星', '111111', '1001001', '18432299002', 'bc29012763ecc2928b87996216a476d3', null, '1');
INSERT INTO `user` VALUES ('2', 'wangym', 'yamo', '2017-07-13', '我是可爱的默默', '0', '402880aa5d882091015d8843dccb0000', '王雅默', '96e79218965eb72c92a549dd5a330112', '1001001', '18409292394', '28af2201c9b78bfcb88def0ad61378da', null, '1');
INSERT INTO `user` VALUES ('3', 'tangmiaomiao', '苗苗', '2017-07-04', '苗苗是个大笨蛋', '0', null, '唐苗苗', '96e79218965eb72c92a549dd5a330112', '1001001', '13775142344', '79403c212e0204283955aba356b74331', null, '1');
INSERT INTO `user` VALUES ('402880aa5e5f8814015e60c5bf450000', 'tangmm', null, null, null, null, null, null, '96e79218965eb72c92a549dd5a330112', null, null, null, null, '0');

-- ----------------------------
-- Table structure for user_contact
-- ----------------------------
DROP TABLE IF EXISTS `user_contact`;
CREATE TABLE `user_contact` (
  `id` varchar(36) UNIQUE NOT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `friend_id` varchar(32) DEFAULT NULL,
  `des` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USER_FRIEND_2_USER` (`friend_id`),
  KEY `FK_USER_2_USER` (`user_id`),
  CONSTRAINT `FK_USER_2_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_USER_FRIEND_2_USER` FOREIGN KEY (`friend_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户通讯录表';

-- ----------------------------
-- Records of user_contact
-- ----------------------------
INSERT INTO `user_contact` VALUES ('402880aa5d59670d015d5968a21d0000', '1', '2', null);
INSERT INTO `user_contact` VALUES ('402880aa5d5a0060015d5a0230060000', '1', '3', 'aaa');

-- ----------------------------
-- Table structure for user_group
-- ----------------------------
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group` (
  `id` varchar(36) UNIQUE NOT NULL,
  `group_id` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `des` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USER_GRO_2_USER` (`user_id`),
  KEY `FK_USER_GRO_2_SUBGROUP` (`group_id`),
  CONSTRAINT `FK_USER_GRO_2_SUBGROUP` FOREIGN KEY (`group_id`) REFERENCES `subgroup` (`id`),
  CONSTRAINT `FK_USER_GRO_2_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户群组关系表';

-- ----------------------------
-- Records of user_group
-- ----------------------------

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` varchar(36) UNIQUE NOT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  `role_id` varchar(36) DEFAULT NULL,
  `des` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USER_ROLE_2_USER` (`user_id`),
  KEY `FK_USER_ROLE_2_ROLE` (`role_id`),
  CONSTRAINT `FK_USER_ROLE_2_ROLE` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK_USER_ROLE_2_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records of user_role
-- ----------------------------

-- ----------------------------
-- Table structure for user_room
-- ----------------------------
DROP TABLE IF EXISTS `user_room`;
CREATE TABLE `user_room` (
  `id` varchar(36) UNIQUE NOT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `room_id` varchar(32) DEFAULT NULL,
  `type` tinyint(4) DEFAULT '1' COMMENT '用户和房间的关系',
  `des` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USER_ROOM_2_USER` (`user_id`),
  KEY `FK_USER_ROOM_2_ROOM` (`room_id`),
  CONSTRAINT `FK_USER_ROOM_2_ROOM` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`),
  CONSTRAINT `FK_USER_ROOM_2_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户房间关系表';

-- ----------------------------
-- Records of user_room
-- ----------------------------
INSERT INTO `user_room` VALUES ('1', '1', '2', null, null);
INSERT INTO `user_room` VALUES ('2', '2', '1', null, null);
INSERT INTO `user_room` VALUES ('3', '3', '2', null, null);
