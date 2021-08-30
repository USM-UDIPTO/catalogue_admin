CREATE DATABASE IF NOT EXISTS `catalogue_admin_service`;

USE `catalogue_admin_service`;

DROP TABLE IF EXISTS `catalogue`;

CREATE TABLE `catalogue` (
`id` int NOT NULL AUTO_INCREMENT,
`catalogue_code` varchar(25) NOT NULL,
`catalogue_name` varchar(100) NOT NULL,
`active_yn` tinyint(4) DEFAULT '1',
`dept_id` int NOT NULL,
`dept_name` varchar(200) NOT NULL,
`created_by` varchar(200) DEFAULT NULL,
`created_ts` datetime DEFAULT NULL,
`last_modified_by` varchar(200) DEFAULT NULL,
`last_modified_ts` datetime DEFAULT NULL,
`version` integer DEFAULT '0',
PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
`id` int NOT NULL AUTO_INCREMENT,
`category_code` varchar(25) NOT NULL,
`category_name` varchar(100) NOT NULL,
`active_yn` tinyint(4) DEFAULT '1',
`catalogue_id` int NOT NULL,
`dept_id` int NOT NULL,
`dept_name` varchar(200) NOT NULL,
`created_by` varchar(200) DEFAULT NULL,
`created_ts` datetime DEFAULT NULL,
`last_modified_by` varchar(200) DEFAULT NULL,
`last_modified_ts` datetime DEFAULT NULL,
`version` integer DEFAULT '0',
PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `specification`;

CREATE TABLE `specification` (
`id` int NOT NULL AUTO_INCREMENT,
`spec_name` varchar(200) NOT NULL,
`active_yn` tinyint(4) DEFAULT '1',
`dept_id` int NOT NULL,
`dept_name` varchar(200) NOT NULL,
`created_by` varchar(200) DEFAULT NULL,
`created_ts` datetime DEFAULT NULL,
`last_modified_by` varchar(200) DEFAULT NULL,
`last_modified_ts` datetime DEFAULT NULL,
`version` integer DEFAULT '0',
PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `catalogue_item`;

CREATE TABLE `catalogue_item` (
`id` int NOT NULL AUTO_INCREMENT,
`item_code` VARCHAR(25) NOT NULL,
`item_name` TEXT NOT NULL,
`uom_id` int NOT NULL,
`uom_name` VARCHAR(100) NOT NULL,
`part_number` VARCHAR(25) NULL,
`active_yn` TINYINT(4) DEFAULT '1',
`catalogue_code` VARCHAR(25) NOT NULL,
`catalogue_name` VARCHAR(200) NOT NULL,
`categories` JSON NULL,
`unspsc_details` JSON NULL,
`unspsc_item_code` VARCHAR(100) NULL,
`specifications` JSON NULL,
`dept_id` int NOT NULL,
`dept_name` VARCHAR(200) NOT NULL,
`created_by` varchar(200) DEFAULT NULL,
`created_ts` datetime DEFAULT NULL,
`last_modified_by` varchar(200) DEFAULT NULL,
`last_modified_ts` datetime DEFAULT NULL,
`version` integer DEFAULT '0',
PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `catalogue_upload_file`;

CREATE TABLE `catalogue_upload_file` (

`id` int NOT NULL AUTO_INCREMENT,
`file_name` VARCHAR(200) NOT NULL,
`status` VARCHAR(200) NOT NULL,
`created_by` varchar(200) DEFAULT NULL,
`created_ts` datetime DEFAULT NULL,
`last_modified_by` varchar(200) DEFAULT NULL,
`last_modified_ts` datetime DEFAULT NULL,
`version` integer DEFAULT '0',
PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `catalogue_upload_file_details`;

CREATE TABLE `catalogue_upload_file_details` (

`id` int NOT NULL AUTO_INCREMENT,
`catalogue_file_id` VARCHAR(200) NOT NULL,
`catalogue_code` VARCHAR(200) NOT NULL,
`item_code` VARCHAR(200) NOT NULL,
`item_name` VARCHAR(200) NOT NULL,
`uom` VARCHAR(200) NOT NULL,
`category_code` VARCHAR(200) NOT NULL,
`status` VARCHAR(200) NOT NULL,
`error`  VARCHAR(200) NOT NULL,
`created_by` varchar(200) DEFAULT NULL,
`created_ts` datetime DEFAULT NULL,
`last_modified_by` varchar(200) DEFAULT NULL,
`last_modified_ts` datetime DEFAULT NULL,
`version` integer DEFAULT '0',
PRIMARY KEY (`id`)
);
