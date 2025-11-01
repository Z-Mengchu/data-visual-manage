/*
 Navicat Premium Data Transfer

 Source Server         : conn-localhost
 Source Server Type    : MySQL
 Source Server Version : 80034
 Source Host           : localhost:3306
 Source Schema         : data_visual_manage

 Target Server Type    : MySQL
 Target Server Version : 80034
 File Encoding         : 65001

 Date: 31/10/2025 23:59:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for channel_sales_data
-- ----------------------------
DROP TABLE IF EXISTS `channel_sales_data`;
CREATE TABLE `channel_sales_data`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_level_category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '一级品类',
  `category` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '品类',
  `tongtu_sku` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '通途SKU',
  `product_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '产品名称',
  `operation_group_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '运营组名',
  `operator` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '运营',
  `development_group_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '开发组名',
  `product_developer` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '产品开发员',
  `channel` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '渠道',
  `channel_account` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '渠道账号',
  `operation_department` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '运营部门',
  `store_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '店铺代码',
  `region` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地区',
  `country` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '国家',
  `country_region` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '国家/地区',
  `brand` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '品牌',
  `sales_mode` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '销售模式',
  `order_number` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单号',
  `payment_settlement_date` date NULL DEFAULT NULL COMMENT '付款日期/结算时间',
  `shipping_warehouse` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发货仓库',
  `shipping_quantity` int NULL DEFAULT NULL COMMENT '发货数量',
  `revenue_rmb` decimal(15, 2) NULL DEFAULT NULL COMMENT '收入(人民币)',
  `product_cost` decimal(15, 2) NULL DEFAULT NULL COMMENT '商品成本',
  `first_mile_shipping` decimal(15, 2) NULL DEFAULT NULL COMMENT '头程运费',
  `packaging_cost` decimal(15, 2) NULL DEFAULT NULL COMMENT '包装费用',
  `logistics_shipping` decimal(15, 2) NULL DEFAULT NULL COMMENT '物流商运费',
  `channel_transaction_fee_rmb` decimal(15, 2) NULL DEFAULT NULL COMMENT '渠道成交费(人民币)',
  `taxes` decimal(15, 2) NULL DEFAULT NULL COMMENT '税费',
  `refund` decimal(15, 2) NULL DEFAULT NULL COMMENT '退款',
  `reshipment_amount` decimal(15, 2) NULL DEFAULT NULL COMMENT '补发金额',
  `sample_cost` decimal(15, 2) NULL DEFAULT NULL COMMENT '送样成本',
  `unsold_subsidy` decimal(15, 2) NULL DEFAULT NULL COMMENT '滞销补贴',
  `order_gross_profit` decimal(15, 2) NULL DEFAULT NULL COMMENT '订单毛利',
  `order_gross_profit_rate` decimal(10, 6) NULL DEFAULT NULL COMMENT '订单毛利率',
  `advertising_promotion` decimal(15, 2) NULL DEFAULT NULL COMMENT '广告/促销',
  `storage_fee` decimal(15, 2) NULL DEFAULT NULL COMMENT '仓储费',
  `removal_fee` decimal(15, 2) NULL DEFAULT NULL COMMENT '移除费',
  `return_processing_fee` decimal(15, 2) NULL DEFAULT NULL COMMENT '退货处理费',
  `other_fees` decimal(15, 2) NULL DEFAULT NULL COMMENT '其他费用',
  `unsold_budget` decimal(15, 2) NULL DEFAULT NULL COMMENT '滞销品预算',
  `handling_fee` decimal(15, 2) NULL DEFAULT NULL COMMENT '手续费',
  `order_net_profit` decimal(15, 2) NULL DEFAULT NULL COMMENT '订单净利',
  `roi` decimal(10, 2) NULL DEFAULT NULL COMMENT 'ROI',
  `days_to_positive_cash_flow` int NULL DEFAULT NULL COMMENT '产品现金流为正所需的天数',
  `verification_formula` decimal(10, 6) NULL DEFAULT NULL COMMENT '验算公式',
  `is_gross_profit_positive` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '毛利是否大于0（0否 1是）',
  `created_at` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_category`(`category` ASC) USING BTREE,
  INDEX `idx_tongtu_sku`(`tongtu_sku` ASC) USING BTREE,
  INDEX `idx_channel`(`channel` ASC) USING BTREE,
  INDEX `idx_order_number`(`order_number` ASC) USING BTREE,
  INDEX `idx_country`(`country` ASC) USING BTREE,
  INDEX `idx_brand`(`brand` ASC) USING BTREE,
  INDEX `idx_sales_mode`(`sales_mode` ASC) USING BTREE,
  INDEX `idx_payment_date`(`payment_settlement_date` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '全渠道销售数据分析表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for overseas_hosting_data
-- ----------------------------
DROP TABLE IF EXISTS `overseas_hosting_data`;
CREATE TABLE `overseas_hosting_data`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键，唯一标识每条记录',
  `store` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '店铺名称，例如 AE56S',
  `settlement_time` datetime NULL DEFAULT NULL COMMENT '结算时间，格式为 YYYY-MM-DD HH:MM:SS',
  `order_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单号，可能包含长数字字符串',
  `product_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品ID，允许NULL（处理#N/A等无效值）',
  `brand` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '品牌名称',
  `sku` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'SKU编码',
  `category` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品类目',
  `fee_item` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '费用项，例如供货款、处罚费等',
  `amount_cny` decimal(10, 2) NULL DEFAULT NULL COMMENT '金额（人民币），支持负值（如退款或处罚）',
  `platform_transaction_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '平台流水单号',
  `limited_supply_price_task_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '限时供货价任务ID，允许NULL（处理#DIV/0!等无效值）',
  `revenue` decimal(10, 2) NULL DEFAULT NULL COMMENT '收入金额，允许NULL',
  `refund` decimal(10, 2) NULL DEFAULT NULL COMMENT '退款金额，允许NULL',
  `allocation_ratio` decimal(10, 4) NULL DEFAULT NULL COMMENT '分摊比例，小数形式（如0.0495），允许NULL',
  `purchase_cost` decimal(10, 2) NULL DEFAULT NULL COMMENT '采购成本',
  `first_mile_cost` decimal(10, 2) NULL DEFAULT NULL COMMENT '头程成本',
  `logistics_fee` decimal(10, 2) NULL DEFAULT NULL COMMENT '物流费',
  `packaging_cost` decimal(10, 2) NULL DEFAULT NULL COMMENT '包装费用',
  `other_costs` decimal(10, 2) NULL DEFAULT NULL COMMENT '其他费用',
  `reshipment_cost` decimal(10, 2) NULL DEFAULT NULL COMMENT '补发费用',
  `gross_profit` decimal(10, 2) NULL DEFAULT NULL COMMENT '毛利',
  `gross_profit_margin` decimal(10, 4) NULL DEFAULT NULL COMMENT '毛利率，小数形式（如0.1172），支持负值',
  `developer` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '开发员姓名',
  `operator` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '运营人员姓名',
  `shipping_warehouse` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发货仓库',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_number`(`order_number` ASC) USING BTREE COMMENT '索引：优化订单号查询',
  INDEX `idx_settlement_time`(`settlement_time` ASC) USING BTREE COMMENT '索引：优化按时间范围的查询',
  INDEX `idx_store`(`store` ASC) USING BTREE COMMENT '索引：优化按店铺的查询'
) ENGINE = InnoDB AUTO_INCREMENT = 15162 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '海外托管业务数据表，用于存储财务和运营记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for purchase_payment_period
-- ----------------------------
DROP TABLE IF EXISTS `purchase_payment_period`;
CREATE TABLE `purchase_payment_period`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `purchase_order_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '采购单号',
  `purchase_plan_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '采购计划编号',
  `account_1688` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '1688账号',
  `purchase_warehouse` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '采购仓库',
  `purchaser` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '采购员',
  `purchase_date` datetime NULL DEFAULT NULL COMMENT '采购日期',
  `image_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片存放url',
  `location` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '货位',
  `sku` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'SKU',
  `product_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '货品名称',
  `specifications` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '规格',
  `purchase_unit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '采购单价',
  `purchase_quantity` int NULL DEFAULT NULL COMMENT '采购数量',
  `goods_payment` decimal(12, 2) NULL DEFAULT NULL COMMENT '货款',
  `supplier_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '供应商名称',
  `arrival_date` date NULL DEFAULT NULL COMMENT '到货日期',
  `purchase_notes` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '采购备注',
  `payment_period_days` int NULL DEFAULT NULL COMMENT '账期天数',
  `due_payment_date` date NULL DEFAULT NULL COMMENT '到期付款时间',
  `purchase_department` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '采购部门',
  `is_paid` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否已付款(0否 1是)',
  `created_at` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_purchase_order`(`purchase_order_number` ASC) USING BTREE,
  INDEX `idx_sku`(`sku` ASC) USING BTREE,
  INDEX `idx_supplier`(`supplier_name` ASC) USING BTREE,
  INDEX `idx_due_date`(`due_payment_date` ASC) USING BTREE,
  INDEX `idx_purchase_date`(`purchase_date` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 185 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '采购账期表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for temu_order_details
-- ----------------------------
DROP TABLE IF EXISTS `temu_order_details`;
CREATE TABLE `temu_order_details`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `shipment_date` date NULL DEFAULT NULL COMMENT '发货日期',
  `is_replenishment` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否补发货（0否 1是）',
  `tongtu_sku` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '通途SKU',
  `product_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '产品名称',
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '品类',
  `shipment_quantity` int NULL DEFAULT NULL COMMENT '发货数量',
  `channel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '渠道',
  `channel_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '渠道账号',
  `sales_site` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '销售站点',
  `shipment_warehouse` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发货仓库',
  `package_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '包裹号',
  `shipping_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮寄方式',
  `logistics_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '物流商单号',
  `order_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单号',
  `buyer_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '买家姓名',
  `country_region` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '国家 / 地区',
  `payment_date` date NULL DEFAULT NULL COMMENT '付款日期',
  `product_developer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '产品开发员',
  `receipt_currency` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收款币种',
  `total_order_price` decimal(15, 2) NULL DEFAULT NULL COMMENT '订单总售价',
  `total_order_price_cny` decimal(15, 2) NULL DEFAULT NULL COMMENT '订单总售价 (人民币)',
  `selling_price` decimal(15, 2) NULL DEFAULT NULL COMMENT '售价',
  `selling_price_cny` decimal(15, 2) NULL DEFAULT NULL COMMENT '售价 (人民币)',
  `product_cost` decimal(15, 2) NULL DEFAULT NULL COMMENT '商品成本',
  `channel_fee_cny` decimal(15, 2) NULL DEFAULT NULL COMMENT '渠道成交费 (人民币)',
  `income_cny` decimal(15, 2) NULL DEFAULT NULL COMMENT '收入 (人民币)',
  `first_leg_shipping_cost` decimal(15, 2) NULL DEFAULT NULL COMMENT '头程运费',
  `first_leg_customs_fee` decimal(15, 2) NULL DEFAULT NULL COMMENT '头程报关费',
  `first_leg_other_fee` decimal(15, 2) NULL DEFAULT NULL COMMENT '头程其它费',
  `packaging_material` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '包装材料',
  `packaging_cost` decimal(15, 2) NULL DEFAULT NULL COMMENT '包装费用',
  `tongtu_shipping_cost` decimal(15, 2) NULL DEFAULT NULL COMMENT '通途运费',
  `logistics_shipping_cost` decimal(15, 2) NULL DEFAULT NULL COMMENT '物流商运费',
  `order_notes` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '订单备注',
  `sorted_order_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单号-整理',
  `first_leg_cost` decimal(15, 2) NULL DEFAULT NULL COMMENT '头程费用',
  `refund_amount` decimal(15, 2) NULL DEFAULT NULL COMMENT '退款金额',
  `replenishment_cost` decimal(15, 2) NULL DEFAULT NULL COMMENT '补发费用',
  `other_costs` decimal(15, 2) NULL DEFAULT NULL COMMENT '其他费用',
  `backend_refund_subsidy` decimal(15, 2) NULL DEFAULT NULL COMMENT '后台退款运费补贴/费用',
  `advertising_fee` decimal(15, 2) NULL DEFAULT NULL COMMENT '广告费',
  `clearance_subsidy` decimal(15, 2) NULL DEFAULT NULL COMMENT '清货补贴',
  `us_platform_label_fee` decimal(15, 2) NULL DEFAULT NULL COMMENT '美国平台面单费用',
  `ratio` decimal(10, 6) NULL DEFAULT NULL COMMENT '比例',
  `gross_profit` decimal(15, 2) NULL DEFAULT NULL COMMENT '毛利',
  `gross_profit_rate` decimal(10, 6) NULL DEFAULT NULL COMMENT '毛利率',
  `distribution_commission` decimal(15, 2) NULL DEFAULT NULL COMMENT '分销佣金',
  `td_actual_gross_profit` decimal(15, 2) NULL DEFAULT NULL COMMENT 'TD实际毛利',
  `td_actual_gross_profit_rate` decimal(10, 6) NULL DEFAULT NULL COMMENT 'TD实际毛利率',
  `operator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '运营',
  `developer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '开发员',
  `order_quantity` int NULL DEFAULT NULL COMMENT '订单数量',
  `created_at` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_at` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remarks` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34278 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Temu订单明细表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
