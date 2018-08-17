/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50619
Source Host           : localhost:3306
Source Database       : sso_demo

Target Server Type    : MYSQL
Target Server Version : 50619
File Encoding         : 65001

Date: 2016-10-26 09:40:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for code_list
-- ----------------------------
DROP TABLE IF EXISTS `code_list`;
CREATE TABLE `code_list` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `KIND_NAME` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '类型名称',
  `KIND_VALUE` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '类型代码',
  `CODE_NAME` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '代码名称',
  `CODE_VALUE` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '代码值',
  `ORDER_NUM` smallint(3) NOT NULL COMMENT '顺序号',
  `REMARK` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `CREATE_BY` bigint(20) DEFAULT NULL COMMENT '创建人员',
  `CREATE_ON` datetime DEFAULT NULL COMMENT '创建时间',
  `MODIFIED_BY` bigint(20) DEFAULT NULL COMMENT '修改人员',
  `MODIFIED_ON` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='数据字典表';

-- ----------------------------
-- Records of code_list
-- ----------------------------
INSERT INTO `code_list` VALUES ('1', '性别', 'SEX', '男', '1', '1', null, null, SYSDATE(), null, SYSDATE());
INSERT INTO `code_list` VALUES ('2', '性别', 'SEX', '女', '2', '2', null, null, SYSDATE(), null, SYSDATE());
INSERT INTO `code_list` VALUES ('15', '学历', 'DEGREE_TYPE', '小学', '1', '1', null, null, SYSDATE(), null, SYSDATE());
INSERT INTO `code_list` VALUES ('16', '学历', 'DEGREE_TYPE', '初中', '2', '2', null, null, SYSDATE(), null, SYSDATE());
INSERT INTO `code_list` VALUES ('17', '学历', 'DEGREE_TYPE', '高中', '3', '3', null, null, SYSDATE(), null, SYSDATE());
INSERT INTO `code_list` VALUES ('18', '学历', 'DEGREE_TYPE', '中专', '4', '4', null, null, SYSDATE(), null, SYSDATE());
INSERT INTO `code_list` VALUES ('19', '学历', 'DEGREE_TYPE', '高职', '5', '5', null, null, SYSDATE(), null, SYSDATE());
INSERT INTO `code_list` VALUES ('20', '学历', 'DEGREE_TYPE', '专科', '6', '6', null, null, SYSDATE(), null, SYSDATE());
INSERT INTO `code_list` VALUES ('21', '学历', 'DEGREE_TYPE', '本科', '7', '7', null, null, SYSDATE(), null, SYSDATE());
INSERT INTO `code_list` VALUES ('22', '学历', 'DEGREE_TYPE', '硕士', '8', '8', null, null, SYSDATE(), null, SYSDATE());
INSERT INTO `code_list` VALUES ('23', '学历', 'DEGREE_TYPE', '博士', '9', '9', null, null, SYSDATE(), null, SYSDATE());
INSERT INTO `code_list` VALUES ('27', '是否', 'isTrue', '是', '1', '1', null, null, SYSDATE(), null, SYSDATE());
INSERT INTO `code_list` VALUES ('28', '是否', 'isTrue', '否', '0', '2', null, null, SYSDATE(), null, SYSDATE());
INSERT INTO `code_list` VALUES ('35', '政治面貌', 'POLITICAL_STATUS', '中共党员', '1', '1', null, null, SYSDATE(), null, SYSDATE());
INSERT INTO `code_list` VALUES ('36', '政治面貌', 'POLITICAL_STATUS', '中共预备党员', '2', '2', null, null, SYSDATE(), null, SYSDATE());
INSERT INTO `code_list` VALUES ('37', '政治面貌', 'POLITICAL_STATUS', '共青团员', '3', '3', null, null, SYSDATE(), null, SYSDATE());
INSERT INTO `code_list` VALUES ('38', '政治面貌', 'POLITICAL_STATUS', '民革党员', '4', '4', null, null, SYSDATE(), null, SYSDATE());
INSERT INTO `code_list` VALUES ('39', '政治面貌', 'POLITICAL_STATUS', '民盟盟员', '5', '5', null, null, SYSDATE(), null, SYSDATE());
INSERT INTO `code_list` VALUES ('40', '政治面貌', 'POLITICAL_STATUS', '民建会员', '6', '6', null, null, SYSDATE(), null, SYSDATE());
INSERT INTO `code_list` VALUES ('41', '政治面貌', 'POLITICAL_STATUS', '民进会员', '7', '7', null, null, SYSDATE(), null, SYSDATE());
INSERT INTO `code_list` VALUES ('42', '政治面貌', 'POLITICAL_STATUS', '农工党党员', '8', '8', null, null, SYSDATE(), null, SYSDATE());
INSERT INTO `code_list` VALUES ('43', '政治面貌', 'POLITICAL_STATUS', '致公党党员', '9', '9', null, null, SYSDATE(), null, SYSDATE());
INSERT INTO `code_list` VALUES ('44', '政治面貌', 'POLITICAL_STATUS', '九三学社社员', 'A', '10', null, null, SYSDATE(), null, SYSDATE());
INSERT INTO `code_list` VALUES ('45', '政治面貌', 'POLITICAL_STATUS', '台盟盟员', 'B', '11', null, null, SYSDATE(), null, SYSDATE());
INSERT INTO `code_list` VALUES ('46', '政治面貌', 'POLITICAL_STATUS', '无党派民主人士', 'C', '12', null, null, SYSDATE(), null, SYSDATE());
INSERT INTO `code_list` VALUES ('47', '政治面貌', 'POLITICAL_STATUS', '群众（普通公民）', 'D', '13', null, null, SYSDATE(), null, SYSDATE());


-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `MENU_NAME` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '菜单名称',
  `ORDER_NUM` smallint(6) NOT NULL DEFAULT '0' COMMENT '顺序号',
  `PARENT_ID` bigint(20) NOT NULL COMMENT '父节点ID',
  `MENU_URL` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT 'MENU_URL',
  `IMAGE` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT '图片地址',
  `MENU_TYPE` char(1) COLLATE utf8_bin NOT NULL COMMENT '菜单类型',
  `ISLEAF` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否叶子节点',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单表';

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '初九数据科技后台管理系统', '1', '-1', null, '', '1', '0');
INSERT INTO `menu` VALUES ('101', '系统管理', '1', '1', null, 'fa-desktop', '1', '0');
INSERT INTO `menu` VALUES ('201', '数据字典管理', '1', '101', '/codeList/method_index.html', null, '1', '1');
INSERT INTO `menu` VALUES ('202', '菜单管理', '2', '101', '/menuManager/method_index.html', null, '1', '1');
INSERT INTO `menu` VALUES ('203', '权限设定', '3', '101', '/privilege/method_index.html', null, '1', '1');
INSERT INTO `menu` VALUES ('204', '角色管理', '4', '101', '/role/method_index.html', null, '1', '1');
INSERT INTO `menu` VALUES ('205', '用户管理', '5', '101', '/sysuser/method_index.html', null, '1', '1');
INSERT INTO `menu` VALUES ('301', '管理员在线管理', '6', '101', '/UserOnlineController/method_index.html', NULL, '1', '1');
INSERT INTO `menu` VALUES ('401', '定时任务管理', '7', '101', '/QuartzController/method_index.html', NULL, '1', '1');
INSERT INTO `menu` VALUES ('501', '公告管理', '8', '101', '/noticeManage/method_index.html', '', '1', '1');


-- ----------------------------
-- Table structure for privilege
-- ----------------------------
DROP TABLE IF EXISTS `privilege`;
CREATE TABLE `privilege` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `PRIVILEGE_NAME` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '权限名称',
  `PRIVILEGE_DESC` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '权限描述',
  `STATUS` char(1) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '状态',
  `PRIVILEGE_TYPE` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '权限类型',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='权限表';

-- ----------------------------
-- Records of privilege
-- ----------------------------
INSERT INTO `privilege` VALUES ('1', 'PRIV_SJZD', '数据字典管理', '1', null);
INSERT INTO `privilege` VALUES ('2', 'PRIV_CDGL', '菜单管理', '1', null);
INSERT INTO `privilege` VALUES ('3', 'PRIV_QXSD', '权限设定', '1', null);
INSERT INTO `privilege` VALUES ('4', 'PRIV_JSGL', '角色管理', '1', null);
INSERT INTO `privilege` VALUES ('5', 'PRIV_ZHGL', '账号管理', '1', null);
INSERT INTO `privilege` VALUES ('6', 'PRIV_ZXGL', '管理员在线管理', '1', NULL);
INSERT INTO `privilege` VALUES ('7', 'PRIV_DSRW', '定时任务管理', '1', NULL);
INSERT INTO `privilege` VALUES ('8', 'PRIV_GGGL', '公告管理', '1', NULL);


-- ----------------------------
-- Table structure for privilege_menu_rela
-- ----------------------------
DROP TABLE IF EXISTS `privilege_menu_rela`;
CREATE TABLE `privilege_menu_rela` (
  `PRIVILEGE_ID` bigint(20) NOT NULL COMMENT '权限ID',
  `MENU_ID` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`PRIVILEGE_ID`,`MENU_ID`),
  KEY `FK_MENU_PRI_RELA` (`MENU_ID`),
  CONSTRAINT `FK_MENU_PRI_RELA` FOREIGN KEY (`MENU_ID`) REFERENCES `menu` (`ID`),
  CONSTRAINT `FK_PRI_MENU_RELA` FOREIGN KEY (`PRIVILEGE_ID`) REFERENCES `privilege` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='权限菜单关系表';

-- ----------------------------
-- Records of privilege_menu_rela
-- ----------------------------
INSERT INTO `privilege_menu_rela` VALUES ('1', '201');
INSERT INTO `privilege_menu_rela` VALUES ('2', '202');
INSERT INTO `privilege_menu_rela` VALUES ('3', '203');
INSERT INTO `privilege_menu_rela` VALUES ('4', '204');
INSERT INTO `privilege_menu_rela` VALUES ('5', '205');
INSERT INTO `privilege_menu_rela` VALUES ('6', '301');
INSERT INTO `privilege_menu_rela` VALUES ('7', '401');
INSERT INTO `privilege_menu_rela` VALUES ('8', '501');


-- ----------------------------
-- Table structure for province
-- ----------------------------
DROP TABLE IF EXISTS `province`;
CREATE TABLE `province` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(18) CHARACTER SET utf8 NOT NULL,
  `NAME` varchar(600) CHARACTER SET utf8 NOT NULL,
  `PHONE_CODE` varchar(18) CHARACTER SET utf8 DEFAULT NULL,
  `POST_CODE` varchar(18) CHARACTER SET utf8 DEFAULT NULL,
  `PARENT_CODE` varchar(18) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `code_UNIQUE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='省市区国家标准码';

-- ----------------------------
-- Records of province
-- ----------------------------
INSERT INTO `province` VALUES ('1', '210000', '辽宁省', null, null, '000000');
INSERT INTO `province` VALUES ('2', '210100', '沈阳市', '024', '110000', '210000');
INSERT INTO `province` VALUES ('3', '210101', '市辖区', null, null, '210100');
INSERT INTO `province` VALUES ('4', '210102', '和平区', '024', '110001', '210100');
INSERT INTO `province` VALUES ('5', '210103', '沈河区', '024', '110011', '210100');
INSERT INTO `province` VALUES ('6', '210104', '大东区', '024', '110041', '210100');
INSERT INTO `province` VALUES ('7', '210105', '皇姑区', '024', '110031', '210100');
INSERT INTO `province` VALUES ('8', '210106', '铁西区', '024', '110021', '210100');
INSERT INTO `province` VALUES ('9', '210111', '苏家屯区', '024', '110101', '210100');
INSERT INTO `province` VALUES ('10', '210112', '东陵区', '024', '110015', '210100');
INSERT INTO `province` VALUES ('11', '210113', '沈北新区', '024', '110121', '210100');
INSERT INTO `province` VALUES ('12', '210114', '于洪区', '024', '110024', '210100');
INSERT INTO `province` VALUES ('13', '210122', '辽中县', '024', '110200', '210100');
INSERT INTO `province` VALUES ('14', '210123', '康平县', '024', '110500', '210100');
INSERT INTO `province` VALUES ('15', '210124', '法库县', '024', '110400', '210100');
INSERT INTO `province` VALUES ('16', '210181', '新民市', '024', '110300', '210100');
INSERT INTO `province` VALUES ('17', '210200', '大连市', '0411', '116000', '210000');
INSERT INTO `province` VALUES ('18', '210201', '市辖区', null, null, '210200');
INSERT INTO `province` VALUES ('19', '210202', '中山区', '0411', '116001', '210200');
INSERT INTO `province` VALUES ('20', '210203', '西岗区', '0411', '116011', '210200');
INSERT INTO `province` VALUES ('21', '210204', '沙河口区', '0411', '116021', '210200');
INSERT INTO `province` VALUES ('22', '210211', '甘井子区', '0411', '116033', '210200');
INSERT INTO `province` VALUES ('23', '210212', '旅顺口区', '0411', '116041', '210200');
INSERT INTO `province` VALUES ('24', '210213', '金州区', '0411', '116100', '210200');
INSERT INTO `province` VALUES ('25', '210224', '长海县', '0411', '116500', '210200');
INSERT INTO `province` VALUES ('26', '210281', '瓦房店市', '0411', '116300', '210200');
INSERT INTO `province` VALUES ('27', '210282', '普兰店市', '0411', '116200', '210200');
INSERT INTO `province` VALUES ('28', '210283', '庄河市', '0411', '116400', '210200');
INSERT INTO `province` VALUES ('29', '210300', '鞍山市', '0412', '114000', '210000');
INSERT INTO `province` VALUES ('30', '210301', '市辖区', null, null, '210300');
INSERT INTO `province` VALUES ('31', '210302', '铁东区', '0412', '114001', '210300');
INSERT INTO `province` VALUES ('32', '210303', '铁西区', '0412', '114014', '210300');
INSERT INTO `province` VALUES ('33', '210304', '立山区', '0412', '114031', '210300');
INSERT INTO `province` VALUES ('34', '210311', '千山区', '0412', '114041', '210300');
INSERT INTO `province` VALUES ('35', '210321', '台安县', '0412', '114100', '210300');
INSERT INTO `province` VALUES ('36', '210323', '岫岩满族自治县', '0412', '114300', '210300');
INSERT INTO `province` VALUES ('37', '210381', '海城市', '0412', '114200', '210300');
INSERT INTO `province` VALUES ('38', '210400', '抚顺市', '0413', '113000', '210000');
INSERT INTO `province` VALUES ('39', '210401', '市辖区', null, null, '210400');
INSERT INTO `province` VALUES ('40', '210402', '新抚区', '0413', '113008', '210400');
INSERT INTO `province` VALUES ('41', '210403', '东洲区', '0413', '113003', '210400');
INSERT INTO `province` VALUES ('42', '210404', '望花区', '0413', '113001', '210400');
INSERT INTO `province` VALUES ('43', '210411', '顺城区', '0413', '113006', '210400');
INSERT INTO `province` VALUES ('44', '210421', '抚顺县', '0413', '113006', '210400');
INSERT INTO `province` VALUES ('45', '210422', '新宾满族自治县', '0413', '113200', '210400');
INSERT INTO `province` VALUES ('46', '210423', '清原满族自治县', '0413', '113300', '210400');
INSERT INTO `province` VALUES ('47', '210500', '本溪市', '0414', '117000', '210000');
INSERT INTO `province` VALUES ('48', '210501', '市辖区', null, null, '210500');
INSERT INTO `province` VALUES ('49', '210502', '平山区', '0414', '117000', '210500');
INSERT INTO `province` VALUES ('50', '210503', '溪湖区', '0414', '117002', '210500');
INSERT INTO `province` VALUES ('51', '210504', '明山区', '0414', '117021', '210500');
INSERT INTO `province` VALUES ('52', '210505', '南芬区', '0414', '117014', '210500');
INSERT INTO `province` VALUES ('53', '210521', '本溪满族自治县', '0414', '117100', '210500');
INSERT INTO `province` VALUES ('54', '210522', '桓仁满族自治县', '0414', '117200', '210500');
INSERT INTO `province` VALUES ('55', '210600', '丹东市', '0415', '118000', '210000');
INSERT INTO `province` VALUES ('56', '210601', '市辖区', null, null, '210600');
INSERT INTO `province` VALUES ('57', '210602', '元宝区', '0415', '118000', '210600');
INSERT INTO `province` VALUES ('58', '210603', '振兴区', '0415', '118002', '210600');
INSERT INTO `province` VALUES ('59', '210604', '振安区', '0415', '118001', '210600');
INSERT INTO `province` VALUES ('60', '210624', '宽甸满族自治县', '0415', '118200', '210600');
INSERT INTO `province` VALUES ('61', '210681', '东港市', '0415', '118300', '210600');
INSERT INTO `province` VALUES ('62', '210682', '凤城市', '0415', '118100', '210600');
INSERT INTO `province` VALUES ('63', '210700', '锦州市', '0416', '121000', '210000');
INSERT INTO `province` VALUES ('64', '210701', '市辖区', null, null, '210700');
INSERT INTO `province` VALUES ('65', '210702', '古塔区', '0416', '121001', '210700');
INSERT INTO `province` VALUES ('66', '210703', '凌河区', '0416', '121000', '210700');
INSERT INTO `province` VALUES ('67', '210711', '太和区', '0416', '121011', '210700');
INSERT INTO `province` VALUES ('68', '210726', '黑山县', '0416', '121400', '210700');
INSERT INTO `province` VALUES ('69', '210727', '义县', '0416', '121100', '210700');
INSERT INTO `province` VALUES ('70', '210781', '凌海市', '0416', '121200', '210700');
INSERT INTO `province` VALUES ('71', '210782', '北镇市', '0416', '121300', '210700');
INSERT INTO `province` VALUES ('72', '210800', '营口市', '0417', '115000', '210000');
INSERT INTO `province` VALUES ('73', '210801', '市辖区', null, null, '210800');
INSERT INTO `province` VALUES ('74', '210802', '站前区', '0417', '115002', '210800');
INSERT INTO `province` VALUES ('75', '210803', '西市区', '0417', '115004', '210800');
INSERT INTO `province` VALUES ('76', '210804', '鲅鱼圈区', '0417', '115007', '210800');
INSERT INTO `province` VALUES ('77', '210811', '老边区', '0417', '115005', '210800');
INSERT INTO `province` VALUES ('78', '210881', '盖州市', '0417', '115200', '210800');
INSERT INTO `province` VALUES ('79', '210882', '大石桥市', '0417', '115100', '210800');
INSERT INTO `province` VALUES ('80', '210900', '阜新市', '0418', '123000', '210000');
INSERT INTO `province` VALUES ('81', '210901', '市辖区', null, null, '210900');
INSERT INTO `province` VALUES ('82', '210902', '海州区', '0418', '123000', '210900');
INSERT INTO `province` VALUES ('83', '210903', '新邱区', null, null, '210900');
INSERT INTO `province` VALUES ('84', '210904', '太平区', null, null, '210900');
INSERT INTO `province` VALUES ('85', '210905', '清河门区', '0418', '123006', '210900');
INSERT INTO `province` VALUES ('86', '210911', '细河区', '0418', '123000', '210900');
INSERT INTO `province` VALUES ('87', '210921', '阜新蒙古族自治县', '0418', '123100', '210900');
INSERT INTO `province` VALUES ('88', '210922', '彰武县', '0418', '123200', '210900');
INSERT INTO `province` VALUES ('89', '211000', '辽阳市', '0419', '111000', '210000');
INSERT INTO `province` VALUES ('90', '211001', '市辖区', null, null, '211000');
INSERT INTO `province` VALUES ('91', '211002', '白塔区', '0419', '111000', '211000');
INSERT INTO `province` VALUES ('92', '211003', '文圣区', '0419', '111000', '211000');
INSERT INTO `province` VALUES ('93', '211004', '宏伟区', '0419', '111003', '211000');
INSERT INTO `province` VALUES ('94', '211005', '弓长岭区', '0419', '111008', '211000');
INSERT INTO `province` VALUES ('95', '211011', '太子河区', '0419', '111000', '211000');
INSERT INTO `province` VALUES ('96', '211021', '辽阳县', '0419', '111200', '211000');
INSERT INTO `province` VALUES ('97', '211081', '灯塔市', '0419', '111300', '211000');
INSERT INTO `province` VALUES ('98', '211100', '盘锦市', '0427', '124000', '210000');
INSERT INTO `province` VALUES ('99', '211101', '市辖区', null, null, '211100');
INSERT INTO `province` VALUES ('100', '211102', '双台子区', '0427', '124000', '211100');
INSERT INTO `province` VALUES ('101', '211103', '兴隆台区', '0427', '124010', '211100');
INSERT INTO `province` VALUES ('102', '211121', '大洼县', '0427', '124200', '211100');
INSERT INTO `province` VALUES ('103', '211122', '盘山县', '0427', '124000', '211100');
INSERT INTO `province` VALUES ('104', '211200', '铁岭市', '0410', '112000', '210000');
INSERT INTO `province` VALUES ('105', '211201', '市辖区', null, null, '211200');
INSERT INTO `province` VALUES ('106', '211202', '银州区', null, null, '211200');
INSERT INTO `province` VALUES ('107', '211204', '清河区', '0410', '112003', '211200');
INSERT INTO `province` VALUES ('108', '211221', '铁岭县', '0410', '112000', '211200');
INSERT INTO `province` VALUES ('109', '211223', '西丰县', '0410', '112400', '211200');
INSERT INTO `province` VALUES ('110', '211224', '昌图县', '0410', '112500', '211200');
INSERT INTO `province` VALUES ('111', '211281', '调兵山市', '0410', '112700', '211200');
INSERT INTO `province` VALUES ('112', '211282', '开原市', '0410', '112300', '211200');
INSERT INTO `province` VALUES ('113', '211300', '朝阳市', '0421', '122000', '210000');
INSERT INTO `province` VALUES ('114', '211301', '市辖区', null, null, '211300');
INSERT INTO `province` VALUES ('115', '211302', '双塔区', '0421', '122000', '211300');
INSERT INTO `province` VALUES ('116', '211303', '龙城区', '0421', '122000', '211300');
INSERT INTO `province` VALUES ('117', '211321', '朝阳县', '0421', '122000', '211300');
INSERT INTO `province` VALUES ('118', '211322', '建平县', '0421', '122400', '211300');
INSERT INTO `province` VALUES ('119', '211324', '喀喇沁左翼蒙古族自治县', '0421', '122300', '211300');
INSERT INTO `province` VALUES ('120', '211381', '北票市', '0421', '122100', '211300');
INSERT INTO `province` VALUES ('121', '211382', '凌源市', '0421', '122500', '211300');
INSERT INTO `province` VALUES ('122', '211400', '葫芦岛市', '0429', '125000', '210000');
INSERT INTO `province` VALUES ('123', '211401', '市辖区', null, null, '211400');
INSERT INTO `province` VALUES ('124', '211402', '连山区', '0429', '125001', '211400');
INSERT INTO `province` VALUES ('125', '211403', '龙港区', '0429', '125004', '211400');
INSERT INTO `province` VALUES ('126', '211404', '南票区', '0429', '125027', '211400');
INSERT INTO `province` VALUES ('127', '211421', '绥中县', '0429', '125200', '211400');
INSERT INTO `province` VALUES ('128', '211422', '建昌县', '0429', '125300', '211400');
INSERT INTO `province` VALUES ('129', '211481', '兴城市', '0429', '125100', '211400');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ROLE_NAME` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `ROLE_DESC` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '角色描述',
  `STATUS` char(1) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '状态',
  `CREATE_BY` bigint(20) DEFAULT NULL COMMENT '创建人员',
  `CREATE_ON` datetime DEFAULT NULL COMMENT '创建时间',
  `MODIFIED_BY` bigint(20) DEFAULT NULL COMMENT '修改人员',
  `MODIFIED_ON` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  KEY `ROLE_NAME` (`ROLE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'ROLE_ADMIN', '超级管理员', '1', null, '2016-10-25 01:41:06', null, '2016-10-25 01:41:06');
INSERT INTO `role` VALUES ('2', 'ROLE_TEST1', 'test1', '1', null, '2016-10-25 01:52:13', null, '2016-10-25 01:52:13');
INSERT INTO `role` VALUES ('3', 'ROLE_TEST2', 'test2', '1', null, '2016-10-25 01:52:13', null, '2016-10-25 01:52:13');

-- ----------------------------
-- Table structure for role_privilege_rela
-- ----------------------------
DROP TABLE IF EXISTS `role_privilege_rela`;
CREATE TABLE `role_privilege_rela` (
  `ROLE_ID` bigint(20) NOT NULL COMMENT '角色ID',
  `PRIVILEGE_ID` bigint(20) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`ROLE_ID`,`PRIVILEGE_ID`),
  KEY `FK_PRI_ROLE_RELA` (`PRIVILEGE_ID`),
  CONSTRAINT `FK_PRI_ROLE_RELA` FOREIGN KEY (`PRIVILEGE_ID`) REFERENCES `privilege` (`ID`),
  CONSTRAINT `FK_ROLE_PRI_RELA` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色权限关系表';

-- ----------------------------
-- Records of role_privilege_rela
-- ----------------------------
INSERT INTO `role_privilege_rela` VALUES ('1', '1');
INSERT INTO `role_privilege_rela` VALUES ('2', '1');
INSERT INTO `role_privilege_rela` VALUES ('1', '2');
INSERT INTO `role_privilege_rela` VALUES ('3', '2');
INSERT INTO `role_privilege_rela` VALUES ('1', '3');
INSERT INTO `role_privilege_rela` VALUES ('1', '4');
INSERT INTO `role_privilege_rela` VALUES ('1', '5');
INSERT INTO `role_privilege_rela` VALUES ('1', '6');
INSERT INTO `role_privilege_rela` VALUES ('1', '7');
INSERT INTO `role_privilege_rela` VALUES ('1', '8');


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USERNAME` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '用户名（账号）',
  `REAL_NAME` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '真实姓名',
  `PASSWORD` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `DISABLE_DESC` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '禁用原因',
  `ENABLED` bit(1) DEFAULT b'1' COMMENT '是否有效',
  `ACCOUNT_NON_LOCKED` bit(1) DEFAULT b'1' COMMENT '帐号是否未锁定',
  `ACCOUNT_NON_EXPIRED` bit(1) DEFAULT b'1' COMMENT '帐号是否未过期',
  `CREDENTIALS_NON_EXPIRED` bit(1) DEFAULT b'1' COMMENT '登录凭据是否未过期',
  `SEX` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `MOBILE` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '手机',
  `EMAIL` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '电子邮箱',
  `QQ` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT 'QQ号码',
  `CREATE_BY` bigint(20) DEFAULT NULL COMMENT '创建人员',
  `CREATE_ON` datetime DEFAULT NULL COMMENT '创建时间',
  `MODIFIED_BY` bigint(20) DEFAULT NULL COMMENT '修改人员',
  `MODIFIED_ON` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USERNAME` (`USERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'linzhiling', '林志玲', 'xMpCOKC5I4INzFCab3WEmw==', null, '', '', '', '', '1', '13455555555', 'aaa@126.com', '2222', '1', '2016-09-28 17:08:32', '1', '2016-10-21 17:06:04');
INSERT INTO `user` VALUES ('2', 'sul', '塑料', 'xMpCOKC5I4INzFCab3WEmw==', null, '', '', '', '', '0', '13533333333', 'bbb@126.com', '3333', '1', '2016-09-28 20:23:53', '1', '2016-09-28 17:08:32');
INSERT INTO `user` VALUES ('3', 'test', '测试1', 'xMpCOKC5I4INzFCab3WEmw==', null, '', '', '', '', '0', '13222222222', 'ccc@126.com', '4444', '1', '2016-09-28 17:08:32', '1', '2016-09-28 17:08:32');

-- ----------------------------
-- Table structure for user_role_rela
-- ----------------------------
DROP TABLE IF EXISTS `user_role_rela`;
CREATE TABLE `user_role_rela` (
  `USER_ID` bigint(20) NOT NULL COMMENT '用户ID',
  `ROLE_ID` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`USER_ID`,`ROLE_ID`),
  KEY `FK_ROLE_USER_RELA` (`ROLE_ID`),
  CONSTRAINT `FK_ROLE_USER_RELA` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ID`),
  CONSTRAINT `FK_USER_ROLE_RELA` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户角色关系表';

-- ----------------------------
-- Records of user_role_rela
-- ----------------------------
INSERT INTO `user_role_rela` VALUES ('1', '1');
INSERT INTO `user_role_rela` VALUES ('2', '2');
INSERT INTO `user_role_rela` VALUES ('3', '3');

-- ----------------------------
-- Table structure for qrtz_task
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_task`;
CREATE TABLE `qrtz_task` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '定时任务的任务id',
  `JOB_NAME` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `JOB_GROUP` varchar(255) DEFAULT NULL COMMENT '任务分组',
  `JOB_STATUS` char(1) DEFAULT NULL COMMENT '任务状态 0禁用，1启用，2删除',
  `CRON_EXPRESSION` varchar(255) DEFAULT NULL COMMENT 'cron 任务运行时间表达式',
  `ORDER_NO` bigint(10) DEFAULT NULL COMMENT '任务执行顺序',
  `IS_ORDER_BY` char(1) DEFAULT '0' COMMENT '是否按顺序执行 0否 1是',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '任务创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '任务更新时间',
  `LAST_PROCESS_TIME` datetime DEFAULT NULL COMMENT '上一次任务执行时间',
  `NEXT_PROCESS_TIME` datetime DEFAULT NULL COMMENT '下一次计划任务执行时间',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '任务描述',
  `BEAN_CLASS` varchar(255) DEFAULT NULL COMMENT '任务执行时调用的任务类 包名+类名',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_task
-- ----------------------------
INSERT INTO `qrtz_task` VALUES ('1', 'JobName_1', 'dataWork1', '1', '0/60 * * * * ?', '1', '1', '2016-10-24 15:15:39', '2016-10-26 21:56:35', '2016-10-24 17:52:37', '2016-10-24 17:52:40', '延迟任务', 'com.chujiu.manager.quartz.job.QuartzJobFactoryImpl');
INSERT INTO `qrtz_task` VALUES ('2', 'JobName_2', 'dataWork1', '1', '0/60 * * * * ?', '2', '1', '2016-10-24 15:15:39', '2016-10-24 15:15:42', '2016-10-24 17:52:37', '2016-10-24 17:52:40', '调用数据库shell任务', 'com.chujiu.manager.quartz.job.QuartzJobShell');
INSERT INTO `qrtz_task` VALUES ('3', 'JobName_3', 'dataWork1', '1', '0/60 * * * * ?', '3', '1', '2016-10-24 15:15:39', '2016-10-24 15:15:42', '2016-10-24 17:52:37', '2016-10-24 17:52:40', '调用数据库存储过程任务', 'com.chujiu.manager.quartz.job.QuartzJobCall');
INSERT INTO `qrtz_task` VALUES ('4', 'JobName_1d', 'dataWork2', '1', '0/30 * * * * ?', null, '0', '2016-10-24 15:15:39', '2016-10-27 11:38:17', '2016-10-24 17:52:37', '2016-10-24 17:52:40', '独立任务1', 'com.chujiu.manager.quartz.job.QuartzJobGeneral1');
INSERT INTO `qrtz_task` VALUES ('5', 'JobName_2d', 'dataWork3', '1', '0/30 * * * * ?', null, '0', '2016-10-24 15:15:39', '2016-10-27 11:56:44', '2016-10-24 17:52:37', '2016-10-24 17:52:40', '独立任务2', 'com.chujiu.manager.quartz.job.QuartzJobGeneral2');
INSERT INTO `qrtz_task` VALUES ('6', 'JobName_3d', 'dataWork4', '1', '0/30 * * * * ?', null, '0', '2016-10-24 15:15:39', '2016-10-27 11:56:49', '2016-10-24 17:52:37', '2016-10-24 17:52:40', '独立任务3', 'com.chujiu.manager.quartz.job.QuartzJobGeneral3');
INSERT INTO `qrtz_task` VALUES ('7', 'JobName_1_2', 'dataWork5', '1', '0/60 * * * * ?', '1', '1', '2016-10-24 15:15:39', '2016-10-24 15:15:42', '2016-10-24 17:52:37', '2016-10-24 17:52:40', '延迟任务', 'com.chujiu.manager.quartz.job.QuartzJobFactoryImpl');
INSERT INTO `qrtz_task` VALUES ('8', 'JobName_2_2', 'dataWork5', '1', '0/60 * * * * ?', '2', '1', '2016-10-24 15:15:39', '2016-10-24 15:15:42', '2016-10-24 17:52:37', '2016-10-24 17:52:40', '调用数据库shell任务', 'com.chujiu.manager.quartz.job.QuartzJobShell');
INSERT INTO `qrtz_task` VALUES ('9', 'JobName_3_2', 'dataWork5', '1', '0/60 * * * * ?', '3', '1', '2016-10-24 15:15:39', '2016-10-24 15:15:42', '2016-10-24 17:52:37', '2016-10-24 17:52:40', '调用数据库存储过程任务', 'com.chujiu.manager.quartz.job.QuartzJobCall');

-- ----------------------------
-- Table structure for qrtz_task_record
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_task_record`;
CREATE TABLE `qrtz_task_record` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '定时任务的任务id',
  `JOB_NAME` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `JOB_GROUP` varchar(255) DEFAULT NULL COMMENT '任务分组',
  `START_TIME` datetime DEFAULT NULL COMMENT '任务创建时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '任务更新时间',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '任务描述',
  `BEAN_CLASS` varchar(255) DEFAULT NULL COMMENT '任务执行时调用的任务类 包名+类名',
  `NOTE` varchar(255) DEFAULT NULL COMMENT '记录详情',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=586 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `NOTICE_TEXT` text COLLATE utf8_bin NOT NULL COMMENT '公告内容',
  `ORDER_NUM` smallint(6) NOT NULL COMMENT '顺序号',
  `NEED_SHOW` char(1) COLLATE utf8_bin NOT NULL COMMENT '是否展示',
  `BUILD_DATE` datetime NOT NULL COMMENT '创建日期',
  `MODIFY_DATE` datetime NOT NULL COMMENT '修改日期',
  `BUILDER` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '创建人',
  `MODIFIER` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '修改人',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统公告';