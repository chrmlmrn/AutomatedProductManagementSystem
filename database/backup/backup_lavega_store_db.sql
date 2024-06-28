-- MySQL dump 10.13  Distrib 8.0.37, for Win64 (x86_64)
--
-- Host: localhost    Database: lavega_store_db
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_id` char(2) NOT NULL,
  `category_name` varchar(50) NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES ('CG','Canned Goods'),('CO','Condiments'),('DA','Dairy'),('DR','Drinks'),('FF','Frozen Foods'),('FR','Fruits'),('HH','Household'),('OT','Other'),('PI','Personal Items'),('RP','Rice and Pasta'),('SN','Snacks'),('VE','Vegetables');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discount`
--

DROP TABLE IF EXISTS `discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discount` (
  `discount_type_id` char(3) NOT NULL,
  `discount_type` varchar(50) NOT NULL,
  `discount_value` decimal(10,2) NOT NULL,
  PRIMARY KEY (`discount_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discount`
--

LOCK TABLES `discount` WRITE;
/*!40000 ALTER TABLE `discount` DISABLE KEYS */;
INSERT INTO `discount` VALUES ('PWD','Person with Disability',20.00),('SCN','Senior Citizen',20.00);
/*!40000 ALTER TABLE `discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `inventory_id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `product_total_quantity` int NOT NULL,
  `critical_stock_level` int NOT NULL,
  `last_updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `product_inventory_status_id` char(3) NOT NULL DEFAULT 'INS',
  PRIMARY KEY (`inventory_id`),
  KEY `product_id` (`product_id`),
  KEY `product_inventory_status_id` (`product_inventory_status_id`),
  CONSTRAINT `inventory_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `inventory_ibfk_2` FOREIGN KEY (`product_inventory_status_id`) REFERENCES `product_inventory_status` (`product_inventory_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES (1,1,80,15,'2024-06-28 01:48:14','INS'),(2,2,78,15,'2024-06-28 00:17:41','INS'),(3,3,50,15,'2024-06-28 00:18:07','INS'),(4,4,45,15,'2024-06-26 06:54:24','INS'),(5,5,30,10,'2024-06-26 06:55:35','INS'),(6,6,35,10,'2024-06-26 06:56:54','INS'),(7,7,40,15,'2024-06-26 06:58:13','INS'),(8,8,30,10,'2024-06-26 07:00:16','INS'),(9,9,40,15,'2024-06-26 07:01:28','INS'),(10,10,30,10,'2024-06-26 07:02:35','INS'),(11,11,20,10,'2024-06-26 07:06:01','INS'),(12,12,40,15,'2024-06-26 07:08:01','INS'),(13,13,40,15,'2024-06-26 07:12:22','INS'),(14,14,30,15,'2024-06-26 07:14:52','INS'),(15,15,30,15,'2024-06-26 07:18:07','INS'),(16,16,40,15,'2024-06-26 07:20:10','INS'),(17,17,38,15,'2024-06-26 07:48:10','INS'),(18,18,39,15,'2024-06-26 07:48:10','INS'),(19,19,19,15,'2024-06-26 07:48:10','INS'),(20,20,29,15,'2024-06-26 07:48:10','INS');
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_expiration`
--

DROP TABLE IF EXISTS `product_expiration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_expiration` (
  `product_expiration_id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `product_expiration_date` date DEFAULT NULL,
  `product_quantity` int NOT NULL,
  PRIMARY KEY (`product_expiration_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `product_expiration_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_expiration`
--

LOCK TABLES `product_expiration` WRITE;
/*!40000 ALTER TABLE `product_expiration` DISABLE KEYS */;
INSERT INTO `product_expiration` VALUES (1,1,'2026-02-08',40),(2,2,'2026-06-10',40),(3,3,'2027-10-11',40),(4,4,'2028-12-29',45),(5,5,'2026-09-08',30),(6,6,'2026-09-03',35),(7,7,'2027-02-10',40),(8,8,'2027-06-04',30),(9,9,'2027-10-11',40),(10,10,'2026-06-03',30),(11,11,'2026-06-22',20),(12,12,'2026-06-12',40),(13,13,'2027-11-29',40),(14,14,'2026-09-08',30),(15,15,'2026-11-02',30),(16,16,'2027-12-08',40),(17,17,'2028-11-23',40),(18,18,'2028-02-11',40),(19,19,'2027-06-04',20),(20,20,'2026-03-04',30),(21,1,'2028-02-29',42),(22,2,'2031-05-25',39),(23,3,'2029-10-09',10);
/*!40000 ALTER TABLE `product_expiration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_inventory_status`
--

DROP TABLE IF EXISTS `product_inventory_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_inventory_status` (
  `product_inventory_status_id` char(3) NOT NULL,
  `product_inventory_status_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`product_inventory_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_inventory_status`
--

LOCK TABLES `product_inventory_status` WRITE;
/*!40000 ALTER TABLE `product_inventory_status` DISABLE KEYS */;
INSERT INTO `product_inventory_status` VALUES ('INS','In Stock'),('OOS','Out of Stock'),('ROG','Reordering');
/*!40000 ALTER TABLE `product_inventory_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_sales`
--

DROP TABLE IF EXISTS `product_sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_sales` (
  `product_sales_id` int NOT NULL AUTO_INCREMENT,
  `sales_id` int NOT NULL,
  `product_id` int NOT NULL,
  `product_quantity_sold` int NOT NULL,
  `product_unit_price` decimal(10,2) NOT NULL,
  `total_sales` decimal(10,2) NOT NULL,
  `discount_type_id` char(3) DEFAULT NULL,
  PRIMARY KEY (`product_sales_id`),
  KEY `sales_id` (`sales_id`),
  KEY `product_id` (`product_id`),
  KEY `discount_type_id` (`discount_type_id`),
  CONSTRAINT `product_sales_ibfk_1` FOREIGN KEY (`sales_id`) REFERENCES `sales` (`sales_id`),
  CONSTRAINT `product_sales_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `product_sales_ibfk_3` FOREIGN KEY (`discount_type_id`) REFERENCES `discount` (`discount_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_sales`
--

LOCK TABLES `product_sales` WRITE;
/*!40000 ALTER TABLE `product_sales` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_sales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_status`
--

DROP TABLE IF EXISTS `product_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_status` (
  `product_status_id` char(3) NOT NULL,
  `product_status_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`product_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_status`
--

LOCK TABLES `product_status` WRITE;
/*!40000 ALTER TABLE `product_status` DISABLE KEYS */;
INSERT INTO `product_status` VALUES ('ACT','Active'),('DIS','Discontinued'),('INA','Inactive');
/*!40000 ALTER TABLE `product_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_type`
--

DROP TABLE IF EXISTS `product_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_type` (
  `product_type_id` char(1) NOT NULL,
  `product_type_name` varchar(50) NOT NULL,
  PRIMARY KEY (`product_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_type`
--

LOCK TABLES `product_type` WRITE;
/*!40000 ALTER TABLE `product_type` DISABLE KEYS */;
INSERT INTO `product_type` VALUES ('F','Fast'),('S','Slow');
/*!40000 ALTER TABLE `product_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `product_code` varchar(20) NOT NULL,
  `barcode` varchar(50) NOT NULL,
  `barcode_image` blob,
  `product_name` varchar(255) NOT NULL,
  `product_price` decimal(10,2) NOT NULL,
  `product_size` varchar(50) DEFAULT NULL,
  `category_id` char(2) NOT NULL,
  `supplier_id` int NOT NULL,
  `product_type_id` char(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `product_status_id` char(3) NOT NULL DEFAULT 'ACT',
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `product_code` (`product_code`),
  KEY `category_id` (`category_id`),
  KEY `supplier_id` (`supplier_id`),
  KEY `product_type_id` (`product_type_id`),
  KEY `product_status_id` (`product_status_id`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
  CONSTRAINT `products_ibfk_2` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`supplier_id`),
  CONSTRAINT `products_ibfk_3` FOREIGN KEY (`product_type_id`) REFERENCES `product_type` (`product_type_id`),
  CONSTRAINT `products_ibfk_4` FOREIGN KEY (`product_status_id`) REFERENCES `product_status` (`product_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'PROD0001','7484858019194',_binary '�PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0�\0\0\0p\0\0\0�.r\0\0	IDATx^\�\�\�n-\�\rCQ��O\'�kD�ܚ�4�C\"w\�\�\��\�\��\�\�;�\�=}\�/\�\�y�\�\�\��BO�\�=}\�/\�\�y�\�\�\��BO�\�=}\�/\�\�y�\�\�\��BO�\�=}\�/\�\�y�\�\�\��BO�\�=}\�/\�\�Y}����<\�\�\�N��E�����J@%\�tO\�f)ֹ�U�m\�dc�rt\�\�\�U*I�{B6K�\����lc\'k]��\�n��PI2\��Y�u\�gd;\�X\�]w3}��J�鞐\�R�s?� \�\�\�\�Z\�躛\�T�L\��l�b��Y\�\�N6ֺ G\�\�L_%��d�\'d�\�\�\�*\�6v��\�9�\�f�*�$\�=!��X\�~VA�����.\�\�u7\�W	�$�\�	\�,\�:\��\n���l�uA�����J@%\�tO\�f)ֹ�U�m\�dc�rt\�\�\�U*I�{B6K�\����lc\'k]��\�n��PI2\��Y�u\�gd;\�X\�]w3}��J�鞐\�R�s?� \�\�\�\�Z\�躛\�T�L\��l�b��Y\�\�N6ֺ G\�\�L_%��d�\'d�\�\�\�*\�6v��\�9�\�f�*�$\�=!��X\�~VA�����.\�\�u7\�W	�$�\�	\�,\�:\��\n���l�uA�����J@%\�tO\�f)ֹ�U�m\�dc�rt\�\�\�U*I�{B6K�\����lc\'k]��\�n��PI2\��Y�u\�gd;\�X\�]w3}��J�鞐\�R�s?� \�\�\�\�Z\�躛\�T�L\��l�b��Y\�\�N6ֺ G\�\�L_%��d�\'d�\�\�\�*\�6v��\�9�\�f�*�$\�=!��X\�~VA�����.\�\�u7\�W	�$�\�	\�,\�:\��\n���l�uA�����J@%\�tO\�f)ֹ�U�m\�dc�rt\�\�\�U*I�{B6K�\����lc\'k]��\�n��PI2\��Y�u\�gd;\�X\�]w3}��J�鞐\�R�s?� \�\�\�\�Z\�躛\�T�L\��l�b��Y\�\�N6ֺ G\�\�L_%��d�\'d�\�\�\�*\�6v��\�9�\�f�*�$\�=!��X\�~VA�����.\�\�u7\�W	�$�\�	\�,\�:\��\n���l�uA�����J@%\�tO\�f)ֹ�U�m\�dc�rt\�\�\�U*I�{B6K�\����lc\'k]��\�n��PI2\��Y�u\�gd;\�X\�]w3}��J�鞐\�R�s?� \�\�\�\�Z\�躛\�T�L\��l�b��Y\�\�N6ֺ G\�\�L_%��d�\'d�\�\�\�*\�6v��\�9�\�f�*�$\�=!��X\�~VA�����.\�\�u7\�W	�$�\�	\�,\�:\��\n���l�uA�����J@%\�tO\�f)ֹ�U�m\�dc�rt\�\�\�U*I�{B6K�\����lc\'k]��\�n��PI2\��Y�u\�gd;\�X\�]w3}��J�鞐\�R�s?� \�\�\�\�Z\�躛\�T�L\��l�b��Y\�\�N6ֺ G\�\�L_%��d�\'d�\�\�\�*\�6v��\�9�\�f�*�$\�=!��X\�~VA�����.\�\�u7\�W	�$�\�	\�,\�:\��\n���l�uA�����J@%\�tO\�f)ֹ�U�m\�dc�rt\�\�\�U*I�{B6K�\����lc\'k]��\�n��PI2\��Y�u\�gd;\�X\�]w3}��J�鞐\�R�s?� \�\�\�\�Z\�躛\�T�L\��l�b��Y\�\�N6ֺ G\�\�L_%��d�\'d�\�\�\�*\�6v��\�9�\�f�*�$\�=!��X\�~VA�����.\�\�u7\�W	�$�\�	\�,\�:\��\n���l�uA�����J@%\�tO\�f)ֹ�U�m\�dc�rt\�\�\�U*I�{B6K�\����lc\'k]��\�n��PI2\��Y�u\�gd;\�X\�]w3}��J�鞐\�R�s?� \�\�\�\�Z\�躛\�T�L\��l�b��Y\�\�N6ֺ G\�\�L_%��d�\'d�\�\�\�*\�6v��\�9�\�f�*�$\�=!��X\�~VA�����.\�\�u7\�W	�$�\�	\�,\�:\��\n���l�uA�����J@%\�tO\�f)ֹ�U�m\�dc�rt\�\�\�U*I�{B6K�\����lc\'k]��\�n��PI2\��Y�u\�gd;\�X\�]w3}��J�鞐\�R�s?� \�\�\�\�Z\�躛\�T�L\��l�b��Y\�\�N6ֺ G\�\�L_%��d�\'d�\�\�\�*\�6v��\�9�\�f�*�$\�=!��X\�~VA�����.\�\�u7\�W	�$�\�	\�,\�:\��\n���l�uA�����J@%\�tO\�f)ֹ�U�m\�dc�rt\�\�\�U*I�{B6K�\����lc\'k]��\�n��PI2\��Y�u\�gd;\�X\�]w3}��J�鞐\�R�s?��_os=�\�&��_os=�\�&��_os=�\�&s\�\�_�\���\�m|�S\�g\�[�\��=����{\�0M�3\�\��C\�\�_�\�FK \�\��\�\��}�⸏?q�4\�\'q3��K?�i�[�W�$\�Ũ\��}�b\��d��}6q3����3c�����\�e�����	\�cRig6��OA���\�\�4���S\�&�v~��\�\��-�Je9?��\�\�$�\�Ỏ\�\�Q\�\�sɤ26\�̏A�\���5\��k}/�9Q>\�O�I\�	SV�~u��\�\�\�\��BO�\�=}\�/\�\�y�\�\�\��BO�\�T\�wnoR\0\0\0\0IEND�B`�','555 Carne norte1',44.00,'260 g','CG',1,'F','2024-06-26 06:49:28','ACT'),(2,'PROD0002','7484852000648',_binary '�PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0�\0\0\0p\0\0\0�.r\0\0IDATx^\�ъ%W��z�\�v\�\�\��z�0�qZ�ɹ���\�\�\��\�\�\�\�\�~��\��BO\�����\�=]\�z�\�/\�t�_\�\�z�\�\�\�~��\��BO\�����\�=]\�z�\�/\�t�_\�\�z�\�\�\�~��\��BOW\�����\�\�\�\�\����\�\0\�\�o!�\��$��C\�$\�pu �6�p\�[���\�\�$I�\�\�4\�3\\ȩ\r ��\�h\��0IR�:4M\�Wrj\'��8Zx?L���M�<\�Ձ�\�\0\�\�o!�\��$��C\�$\�pu �6�p\�[���\�\�$I�\�\�4\�3\\ȩ\r ��\�h\��0IR�:4M\�Wrj\'��8Zx?L���M�<\�Ձ�\�\0\�\�o!�\��$��C\�$\�pu �6�p\�[���\�\�$I�\�\�4\�3\\ȩ\r ��\�h\��0IR�:4M\�Wrj\'��8Zx?L���M�<\�Ձ�\�\0\�\�o!�\��$��C\�$\�pu �6�p\�[���\�\�$I�\�\�4\�3\\ȩ\r ��\�h\��0IR�:4M\�Wrj\'��8Zx?L���M�<\�Ձ�\�\0\�\�o!�\��$��C\�$\�pu �6�p\�[���\�\�$I�\�\�4\�3\\ȩ\r ��\�h\��0IR�:4M\�Wrj\'��8Zx?L���M�<\�Ձ�\�\0\�\�o!�\��$��C\�$\�pu �6�p\�[���\�\�$I�\�\�4\�3\\ȩ\r ��\�h\��0IR�:4M\�Wrj\'��8Zx?L���M�<\�Ձ�\�\0\�\�o!�\��$��C\�$\�pu �6�p\�[���\�\�$I�\�\�4\�3\\ȩ\r ��\�h\��0IR�:4M\�Wrj\'��8Zx?L���M�<\�Ձ�\�\0\�\�o!�\��$��C\�$\�pu �6�p\�[���\�\�$I�\�\�4\�3\\ȩ\r ��\�h\��0IR�:4M\�Wrj\'��8Zx?L���M�<\�Ձ�\�\0\�\�o!�\��$��C\�$\�pu �6�p\�[���\�\�$I�\�\�4\�3\\ȩ\r ��\�h\��0IR�:4M\�Wrj\'��8Zx?L���M�<\�Ձ�\�\0\�\�o!�\��$��C\�$\�pu �6�p\�[���\�\�$I�\�\�4\�3\\ȩ\r ��\�h\��0IR�:4M\�Wrj\'��8Zx?L���M�<\�Ձ�\�\0\�\�o!�\��$��C\�$\�pu �6�p\�[���\�\�$I�\�\�4\�3\\ȩ\r ��\�h\��0IR�:4M\�Wrj\'��8Zx?L���M�<\�Ձ�\�\0\�\�o!�\��$��C\�$\�pu �6�p\�[���\�\�$I�\�\�4\�3\\ȩ\r ��\�h\��0IR�:4M\�Wrj\'��8Zx?L���M�<\�Ձ�\�\0\�\�o!�\��$��C\�$\�pu �6�p\�[���\�\�$I�\�\�4\�3\\ȩ\r ��\�h\��0IR�:4M\�Wrj\'��8Zx?L���M�<\�Ձ�\�\0\�\�o!�\��$��C\�$\�pu �6�p\�[���\�\�$I�\�\�4\�3\\ȩ\r ��\�h\��0IR�:4M\�Wrj\'��8Zx?L���M�<\�Ձ�\�\0\�\�o!�\��$��C\�$\�pu �6�p\�[���\�\�$I�\�\�4\�3\\ȩ\r ��\�h\��0IR�:4M\�Wrj\'��8Zx?L���M�<\�Ձ�\�\0\�\�o!�\��$��C\�$\�pu �6�p\�[���\�\�$I�\�\�4\�3\\ȩ\r ��\�h\��0IR�:4M\�Wrj\'��8Zx?L���M�<\�Ձ�\�\0\�\�o!�\��$��C\�$\�pu �6�p\�[���\�\�$I�\�\�4\�3\\ȩ\r ��\�h\��0IR�:4M\�Wrj\'��8Zx?L���M�<\�Ձ�\�\0\�\�o!�\��$��C\�$\�pu �6�p\�[���\�\�$I�\�\�4\�3\\ȩ\r ��\�h\��0IR�:4M\�Wrj\'��8Zx?L���M�<\�Ձ�\�\0\�\�o!�\��$��C\�$\�pu �6�p\�[���\�\�$I�\�\�4\�3\\ȩ\r ��\�h\��0IR�:4M\�Wrj\'��8Zx?L������9\�_�K�C?�k\����\�Џ����ſ\��C_W\�i�5��ө�!!�?\�ɷy\�Ĺ\�\�2��\��\��i�5�|�&.\��\�oC�o\�g��ǟ6�š��~\r\�\'\�\�|IT(\�\�#\�\�\'�$(*3�����\�v\��ͷ�\�\�_�\�\�\�<0\��I�`�@��\��CT\���h;\�\��\�\�_\�O�\��&c\�-aT��\�|*��3\�F\�\��?~�k>�Oxb>��\�~��X�d>�R\��?8\�\���\��Y\�&�t�\��%�xL\�\�\����*\�~��\��BO\�����\�=]\�z����� XBv�\0\0\0\0IEND�B`�','555 Sardines Green',42.00,'425 g','CG',2,'F','2024-06-26 06:51:18','ACT'),(3,'PROD0003','7484858000970',_binary '�PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0�\0\0\0p\0\0\0�.r\0\0rIDATx^\�\�jdI���_z�%;\�\�>��z�\�AG�V�\�?\�ξ�\���B�\�}�\�\�\�\�w_\�\��/t�\�:�\��~\��N��B�\�}�\�\�\�\�w_\�\��/t�\�:�\��~\��N��B�\�}�\�/z�\�\�8�/�\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�m����\�Ubv2\�\r)�`:��`~QѶ��)\�Q%f\'S\�`�\"	��x	\�}|�}?xIT\�\�5\��\�%Q\�\�\�\�\���\�E�\�O\�_\���٭\�\�SL�\�Y\�Ō��\r�>G�R\�\�ߏܛ�\�_zXC���<\�<z�9�\�\�?\�z�L\�M\�\�/mk\�?�����|\�\�9�\�\�?\�z�L\�\�\�όk\�\��_\�YO\�#|o\�\�\��\�\�{*z�3\�\�tT�\�\�}1�n�\�nؘ/P\�\�\\�B&�}\�7ޮ\�n\�<\�a\�s$�=FH�%!�[�>\���\�w���x�\�<_\�\�L\�b\�\�\����\�\��~\��N��B�\�}�\�\�\�\�\�/\�M��5+\�\0\0\0\0IEND�B`�','Argentina beefloaf',21.00,'170 g','CG',3,'F','2024-06-26 06:52:52','ACT'),(4,'PROD0004','7484858000352',_binary '�PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0�\0\0\0p\0\0\0�.r\0\0\�IDATx^\�\�\�j\�X\��K\�0�\�|\�Rv\�Hi0?\�E�U�]�_�\�\���\�=\�\�/\�\�{�\�\�\��BO�\�=�\�/\�\�{�\�\�\��BO�\�=�\�/\�\�{�\�\�\��BO�\�=�\�/\�\�{�\�\�\��BO�\�=�\�/\�\�{�\�\�o\�����\�\�\�ߞ��\�٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�5nr�\��٪b;M�ᜬgK\ZI�����\\\�&+\r��*�\�T\Z\�\�z���\�\�ߪ\�\�׌\�^�*�\�5\������~}\���\�%}\�\�?\�.�\�>�1�]\�g\�\�\\\�\�y]1-\�/���=�+&�\��P\���>�QҐ��\�\�\�z|��x\�\�\�۳�n\�\�~(��K߭��c\��\�狓O|=[R|\�x�纛�\�|�\�\�}*�\�g\�5>/|�\�s]17>N�_\�\�<���\�\�\��\�ǟi\�\��v;\�C�W\�O\Zn\�4|\�x��T/��f\�m\�\�o��&\ri�\�s\�ܞ-)�-\�FzY޷E�\��?u�\���.p\�0�/��\�\\O\�\��\�\�\�\�\�~��\����~\�z��_\�\�\�~��\�\0�H�\�z\�;\0\0\0\0IEND�B`�','Argentina cornedbeef',52.00,'260 g','CG',4,'F','2024-06-26 06:54:24','ACT'),(5,'PROD0005','7484858015066',_binary '�PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0�\0\0\0p\0\0\0�.r\0\0	IDATx^\�\�\�n-�\rDQ��O\'�0kH\�XCz�\�E׃���T�\�_�z\�l}e\�\�az�\�\�\�~��\��BO\�����\�=]\�z�\�/\�t�_\�\�z�\�\�\�~��\��BO\�����\�=]\�z�\�/\�t�_\�\�z�\�\�\�~��k\�����s\�3x�\�𷒭\�l��j@PbR+��{�Ѫ贽/\�\Z$[\�\�NoՀ$�ĤV:C\�p�U\�i{_�5H����ުI@�I�t�\�\�F��\�\��Pk�l]g;�U���Z\�\�ÍVE�\�}�\� ٺ\�vz�$%&�\�����N\��B�A�u�\�\�V\rHJLj�3t7Z��\��Z�d\�:\�\�\Z���\�Jg\�n�*:m\��\�\�u�\�[5 	(1��\�\�=\�hUt\�\�j\r��\�l��j@PbR+��{�Ѫ贽/\�\Z$[\�\�NoՀ$�ĤV:C\�p�U\�i{_�5H����ުI@�I�t�\�\�F��\�\��Pk�l]g;�U���Z\�\�ÍVE�\�}�\� ٺ\�vz�$%&�\�����N\��B�A�u�\�\�V\rHJLj�3t7Z��\��Z�d\�:\�\�\Z���\�Jg\�n�*:m\��\�\�u�\�[5 	(1��\�\�=\�hUt\�\�j\r��\�l��j@PbR+��{�Ѫ贽/\�\Z$[\�\�NoՀ$�ĤV:C\�p�U\�i{_�5H����ުI@�I�t�\�\�F��\�\��Pk�l]g;�U���Z\�\�ÍVE�\�}�\� ٺ\�vz�$%&�\�����N\��B�A�u�\�\�V\rHJLj�3t7Z��\��Z�d\�:\�\�\Z���\�Jg\�n�*:m\��\�\�u�\�[5 	(1��\�\�=\�hUt\�\�j\r��\�l��j@PbR+��{�Ѫ贽/\�\Z$[\�\�NoՀ$�ĤV:C\�p�U\�i{_�5H����ުI@�I�t�\�\�F��\�\��Pk�l]g;�U���Z\�\�ÍVE�\�}�\� ٺ\�vz�$%&�\�����N\��B�A�u�\�\�V\rHJLj�3t7Z��\��Z�d\�:\�\�\Z���\�Jg\�n�*:m\��\�\�u�\�[5 	(1��\�\�=\�hUt\�\�j\r��\�l��j@PbR+��{�Ѫ贽/\�\Z$[\�\�NoՀ$�ĤV:C\�p�U\�i{_�5H����ުI@�I�t�\�\�F��\�\��Pk�l]g;�U���Z\�\�ÍVE�\�}�\� ٺ\�vz�$%&�\�����N\��B�A�u�\�\�V\rHJLj�3t7Z��\��Z�d\�:\�\�\Z���\�Jg\�n�*:m\��\�\�u�\�[5 	(1��\�\�=\�hUt\�\�j\r��\�l��j@PbR+��{�Ѫ贽/\�\Z$[\�\�NoՀ$�ĤV:C\�p�U\�i{_�5H����ުI@�I�t�\�\�F��\�\��Pk�l]g;�U���Z\�\�ÍVE�\�}�\� ٺ\�vz�$%&�\�����N\��B�A�u�\�\�V\rHJLj�3t7Z��\��Z�d\�:\�\�\Z���\�Jg\�n�*:m\��\�\�u�\�[5 	(1��\�\�=\�hUt\�\�j\r��\�l��j@PbR+��{�Ѫ贽/\�\Z$[\�\�NoՀ$�ĤV:C\�p�U\�i{_�5H����ުI@�I�t�\�\�F��\�\��Pk�l]g;�U���Z\�\�ÍVE�\�}�\� ٺ\�vz�$%&�\�����N\��B�A�u�\�\�V\rHJLj�3t7Z��\��Z�d\�:\�\�\Z���\�Jg\�n�*:m\��\�\�u�\�[5 	(1��\�\�=\�hUt\�\�j\r��\�l��j@PbR+��{�Ѫ贽/\�\Z$[\�\�NoՀ$�ĤV:C\�p�U\�i{_�5H����ުI@�I�t�\�\�F��\�\��Pk�l]g;�U���Z\�\�ÍVE�\�}�\� ٺ\�vz�$%&�\�����N\��B�A�u�\�\�V\rHJLj�3t7Z��\��Z�d\�:\�\�\Z���\�Jg\�n�*:m\��\�\�u�\�[5 	(1��\�\�=\�hUt\�\�j\r��\�l��j@PbR+��{�Ѫ贽/\�\Z$[\�\�NoՀ$�ĤV:C\�p�U\�i{_�5H����ުI@�I�t�\�\�F��\�\��Pk�l]g;�U���Z\�\�ÍVE�\�}�\� ٺ\�vz�$%&�\�����N\��B�A�u�\�\�V\rHJLj�3t7Z��\��Z�d\�:\�\�\Z���\�Jg\�n�*:m\��\�\�u�\�[5 	(1��\�\�=\�hUt\�\�j\r��\�l��j@PbR+��{�Ѫ贽/\�\Z$[\�\�NoՀ$�ĤV:C\�p�U\�i{_�5H����ުI@�I�t�\�\�F��\�\��Pk�l]g;�U���Z\�\�ÍVE��\�X\�&��_\�f�?x�Uѯo3\��I_\�\�We����wOU�3A���o\�\���S,��$�\���P\��/}\�F+=\�>��ی?[\�\�\'+���\'�~&�\�E��\�i� 5���/$�s�la�S\��\��W\�L\"�oR�X��~�3\�6��9�\�2�o�xې~\�̌��uñ>��g\�m�:\�^f\�7����\�\�`�uC\�϶�c\�\�\��q�\Z�\�S憤�-��?kfF\�\�7:�\�\�\�\����bH�\�Y��\�\���\���WK�_\�\�z�\�\�\�~��\��BO\�������\\p��\�(\0\0\0\0IEND�B`�','Argentina Spicy Sisig',41.00,'150 g','CG',5,'S','2024-06-26 06:55:35','ACT'),(6,'PROD0006','7484858004930',_binary '�PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0�\0\0\0p\0\0\0�.r\0\0	�IDATx^\�\�ۊ49\�~��\�e\���\�\�<���.�⠨(\�\�?\�<{�>�w6\�z��_\�\�\�~��\����>\�z��_\�\�\�~��\����>\�z��_\�\�\�~��\����>\�z��_\�\�\�~��\����>\�z��_\�\�3�B__�;\�-���#gT\�\�\��\"���kꔬ\�ʌ�v�\�t\�����^uC$�TPr-B��\�^�QЮ����Qu7ӫn�d�\nJ�E�S�\�+3\n\�u��ҁ3�\�fz\�\r��RAɵuJ\�{eFA�N\�V:pF\�\�L��!�Q*(��N\�z�\�(h\�\�\�JΨ��\�U7D2J%\�\"\�)Y\�\�:\�[\��Uw3�\�HF��\�Z�:%\�2��]\'{+8�\�n�W\�\�(�\\�P�d�Wf�\�do�gT\�\�\��\"���kꔬ\�ʌ�v�\�t\�����^uC$�TPr-B��\�^�QЮ����Qu7ӫn�d�\nJ�E�S�\�+3\n\�u��ҁ3�\�fz\�\r��RAɵuJ\�{eFA�N\�V:pF\�\�L��!�Q*(��N\�z�\�(h\�\�\�JΨ��\�U7D2J%\�\"\�)Y\�\�:\�[\��Uw3�\�HF��\�Z�:%\�2��]\'{+8�\�n�W\�\�(�\\�P�d�Wf�\�do�gT\�\�\��\"���kꔬ\�ʌ�v�\�t\�����^uC$�TPr-B��\�^�QЮ����Qu7ӫn�d�\nJ�E�S�\�+3\n\�u��ҁ3�\�fz\�\r��RAɵuJ\�{eFA�N\�V:pF\�\�L��!�Q*(��N\�z�\�(h\�\�\�JΨ��\�U7D2J%\�\"\�)Y\�\�:\�[\��Uw3�\�HF��\�Z�:%\�2��]\'{+8�\�n�W\�\�(�\\�P�d�Wf�\�do�gT\�\�\��\"���kꔬ\�ʌ�v�\�t\�����^uC$�TPr-B��\�^�QЮ����Qu7ӫn�d�\nJ�E�S�\�+3\n\�u��ҁ3�\�fz\�\r��RAɵuJ\�{eFA�N\�V:pF\�\�L��!�Q*(��N\�z�\�(h\�\�\�JΨ��\�U7D2J%\�\"\�)Y\�\�:\�[\��Uw3�\�HF��\�Z�:%\�2��]\'{+8�\�n�W\�\�(�\\�P�d�Wf�\�do�gT\�\�\��\"���kꔬ\�ʌ�v�\�t\�����^uC$�TPr-B��\�^�QЮ����Qu7ӫn�d�\nJ�E�S�\�+3\n\�u��ҁ3�\�fz\�\r��RAɵuJ\�{eFA�N\�V:pF\�\�L��!�Q*(��N\�z�\�(h\�\�\�JΨ��\�U7D2J%\�\"\�)Y\�\�:\�[\��Uw3�\�HF��\�Z�:%\�2��]\'{+8�\�n�W\�\�(�\\�P�d�Wf�\�do�gT\�\�\��\"���kꔬ\�ʌ�v�\�t\�����^uC$�TPr-B��\�^�QЮ����Qu7ӫn�d�\nJ�E�S�\�+3\n\�u��ҁ3�\�fz\�\r��RAɵuJ\�{eFA�N\�V:pF\�\�L��!�Q*(��N\�z�\�(h\�\�\�JΨ��\�U7D2J%\�\"\�)Y\�\�:\�[\��Uw3�\�HF��\�Z�:%\�2��]\'{+8�\�n�W\�\�(�\\�P�d�Wf�\�do�gT\�\�\��\"���kꔬ\�ʌ�v�\�t\�����^uC$�TPr-B��\�^�QЮ����Qu7ӫn�d�\nJ�E�S�\�+3\n\�u��ҁ3�\�fz\�\r��RAɵuJ\�{eFA�N\�V:pF\�\�L��!�Q*(��N\�z�\�(h\�\�\�JΨ��\�U7D2J%\�\"\�)Y\�\�:\�[\��Uw3�\�HF��\�Z�:%\�2��]\'{+8�\�n�W\�\�(�\\�P�d�Wf�\�do�gT\�\�\��\"���kꔬ\�ʌ�v�\�t\�����^uC$�TPr-B��\�^�QЮ����Qu7ӫn�d�\nJ�E�S�\�+3\n\�u��ҁ3�\�fz\�\r��RAɵuJ\�{eFA�N\�V:pF\�\�L��!�Q*(��N\�z�\�(h\�\�\�JΨ��\�U7D2J%\�\"\�)Y\�\�:\�[\��Uw3�\�HF��\�Z�:%\�2��]\'{+8�\�n�W\�\�(�\\�P�d�Wf�\�do�gT\�\�\��\"���kꔬ\�ʌ�v�\�t\�����^uC$�TPr-B��\�^�QЮ����Qu7ӫn�d�\nJ�E�S�\�+3\n\�u��ҁ3�\�fz\�\r��RAɵuJ\�{eFA�N\�V:pF\�\�L��!�Q*(��N\�z�\�(h\�\�\�JΨ��\�U7D2J%\�\"\�)Y\�\�:\�[\��Uw3�\�HF��\�Z�:%\�2��]\'{+8�\�n�W\�\�(�\\�P�d�Wf�\�do�gT\�\�\��\"���kꔬ\�ʌ�v�\�t\�����^uC$�TPr-B��\�^�QЮ����Qu7ӫn�d�\nJ�E�S�\�+3\n\�u��ҁ3�\�fz\�\r��RAɵuJ\�{eFA\�\�\�\�dt�\�\���MFA\�\�\�\�$}�}>\�\��6\�\�S\��YL\�?���w\�\���z�\�A\��\�7m�>�\�\�=\�\�#��{g��\�g>\�9�����]�b�\�C�\�\�\�\�\��`$\�;ӗ\��`�\�t\�p��\�gb�\��믹\�Y\�\�Θ\�\�<?\�\�|\�\�\�\�6\�|�\�/=\�b�\�t\�\�b>\�,�\�\�\��\�\�l�~\�?�\�n\�y~\�|�H�w\�\�y~\�|�~<۠+�Q!_\�\�\��K�|x~\�|?\�\��Z~�\�̿4���3�\�=}\�/\�\�y�\�\�\��BO�\�=}��n� \�zRw\0\0\0\0IEND�B`�','Argentina Vienna Sausage',42.00,'260 g','CG',6,'S','2024-06-26 06:56:54','ACT'),(7,'PROD0007','4800249908237',_binary '�PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0�\0\0\0p\0\0\0�.r\0\0\n	IDATx^\�Qn%\�\r��M\'\�Q)�c\�r�Ơ\�qA�%]\�ǿ\�zv}\�x\�a\�~��\����^\�zz�_\�\�\�~��\����^\�zz�_\�\�\�~��\����^\�zz�_\�\�\�~��\����^\�zz�_\�\�\�~��\����^�/\�\�\�\��R0�IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-\�5z��IG��蕜Q\�e��I! ��Zt�\�\"^��\�P�t\�(�^\�5_�x��\�j�E\�:-�\�k�]\�%�E?~ͷ\�/�\�\�ǯ�v�ŗ|�\�㟲��\�5��\�ϡO�2��/�G��4\�\�G\�O�>��`;��;3��\�դ�)��q��\�\���\�����8��ןNM@\��\��\�i�\��]��?ߝj\�|����Vg����\�դ�\�/\�\�W;�4\�\�{�/\�\�)�\�/_)\�	4g\�*�!���O�@s�f}\�̵\��|\��l�f�\�+�L\��خ&\�W�j��k�\���o�\�\�\�����fjb��ך��N\�\�\�T\����O\�ַ\��BO�\�=�\�/\�\�z�\�\�\��BO��wY\�:a\0\0\0\0IEND�B`�','Cdo Karne Norte',32.00,'175 g','CG',7,'F','2024-06-26 06:58:13','ACT'),(8,'PROD0008','7484851004326',_binary '�PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0�\0\0\0p\0\0\0�.r\0\0	XIDATx^\�\�\�n&GDa��K\'���o��N\�F���V�U\�4�z�>r\�\�az�\�\�\�~��\��BO\�����\�=]\�z�\�/\�t�_\�\�z�\�\�\�~��\��BO\�����\�=]\�z�\�/\�t�_\�\�z�\�\�\�~��k\��>>��s\�\�\�O\��\"kk>��®\n\�h��\�\�F�An\�N�auA\�\�0|z3�]�\�X��ÍV�\�Ɲ<\�ꂬ�a�\�&f�*$��?����;y�\�Y[\�\�\�M\�vUHFc~7Z\rrw\�����\�ӛ�!쪐�\�\n�n�\Z\�6\�\�Vdm\rç71C\�U!��9\�h5\�m\�\�3�.\�\�\Z�Oob���B2\Z+\�s�\�j�۸�gX]��5�\�\�aW�d4V\�\�p�\� �q\'ϰ� kk>��®\n\�h��\�\�F�An\�N�auA\�\�0|z3�]�\�X��ÍV�\�Ɲ<\�ꂬ�a�\�&f�*$��?����;y�\�Y[\�\�\�M\�vUHFc~7Z\rrw\�����\�ӛ�!쪐�\�\n�n�\Z\�6\�\�Vdm\rç71C\�U!��9\�h5\�m\�\�3�.\�\�\Z�Oob���B2\Z+\�s�\�j�۸�gX]��5�\�\�aW�d4V\�\�p�\� �q\'ϰ� kk>��®\n\�h��\�\�F�An\�N�auA\�\�0|z3�]�\�X��ÍV�\�Ɲ<\�ꂬ�a�\�&f�*$��?����;y�\�Y[\�\�\�M\�vUHFc~7Z\rrw\�����\�ӛ�!쪐�\�\n�n�\Z\�6\�\�Vdm\rç71C\�U!��9\�h5\�m\�\�3�.\�\�\Z�Oob���B2\Z+\�s�\�j�۸�gX]��5�\�\�aW�d4V\�\�p�\� �q\'ϰ� kk>��®\n\�h��\�\�F�An\�N�auA\�\�0|z3�]�\�X��ÍV�\�Ɲ<\�ꂬ�a�\�&f�*$��?����;y�\�Y[\�\�\�M\�vUHFc~7Z\rrw\�����\�ӛ�!쪐�\�\n�n�\Z\�6\�\�Vdm\rç71C\�U!��9\�h5\�m\�\�3�.\�\�\Z�Oob���B2\Z+\�s�\�j�۸�gX]��5�\�\�aW�d4V\�\�p�\� �q\'ϰ� kk>��®\n\�h��\�\�F�An\�N�auA\�\�0|z3�]�\�X��ÍV�\�Ɲ<\�ꂬ�a�\�&f�*$��?����;y�\�Y[\�\�\�M\�vUHFc~7Z\rrw\�����\�ӛ�!쪐�\�\n�n�\Z\�6\�\�Vdm\rç71C\�U!��9\�h5\�m\�\�3�.\�\�\Z�Oob���B2\Z+\�s�\�j�۸�gX]��5�\�\�aW�d4V\�\�p�\� �q\'ϰ� kk>��®\n\�h��\�\�F�An\�N�auA\�\�0|z3�]�\�X��ÍV�\�Ɲ<\�ꂬ�a�\�&f�*$��?����;y�\�Y[\�\�\�M\�vUHFc~7Z\rrw\�����\�ӛ�!쪐�\�\n�n�\Z\�6\�\�Vdm\rç71C\�U!��9\�h5\�m\�\�3�.\�\�\Z�Oob���B2\Z+\�s�\�j�۸�gX]��5�\�\�aW�d4V\�\�p�\� �q\'ϰ� kk>��®\n\�h��\�\�F�An\�N�auA\�\�0|z3�]�\�X��ÍV�\�Ɲ<\�ꂬ�a�\�&f�*$��?����;y�\�Y[\�\�\�M\�vUHFc~7Z\rrw\�����\�ӛ�!쪐�\�\n�n�\Z\�6\�\�Vdm\rç71C\�U!��9\�h5\�m\�\�3�.\�\�\Z�Oob���B2\Z+\�s�\�j�۸�gX]��5�\�\�aW�d4V\�\�p�\� �q\'ϰ� kk>��®\n\�h��\�\�F�An\�N�auA\�\�0|z3�]�\�X��ÍV�\�Ɲ<\�ꂬ�a�\�&f�*$��?����;y�\�Y[\�\�\�M\�vUHFc~7Z\rrw\�����\�ӛ�!쪐�\�\n�n�\Z\�6\�\�Vdm\rç71C\�U!��9\�h5\�m\�\�3�.\�\�\Z�Oob���B2\Z+\�s�\�j�۸�gX]��5�\�\�aW�d4V\�\�p�\� �q\'ϰ� kk>��®\n\�h��\�\�F�An\�N�auA\�\�0|z3�]�\�X��ÍV�\�Ɲ<\�ꂬ�a�\�&f�*$��?����;y�\�Y[\�\�\�M\�vUHFc~7Z\rrw\�����\�ӛ�!쪐�\�\n�n�\Z\�6\�\�Vdm\rç71C\�U!��9\�h5\�m\�\�3�.\�\�\Z�Oob���B2\Z+\�s�\�j�۸�gX]��5�\�\�aW�d4V\�\�p�\� �q\'ϰ� kk>��®\n\�h��\�\�F�An\�N�auA\�\�0|z3�]�\�X��ÍV�\�Ɲ<\�ꂬ�a�\�&f�*$��?����;y�\�Y[\�\�\�M\�vUHFc~7Z\r�\�m�\�o�\Z\�\�\�\\\�\�d5\�Ƿ�\��\�y\�ǯ\�\���\�o�Q3�\�g\�\�H�y�\�\�e��D\�\�A\�\Z��J�}�\�&׼I?+P}\�|\'I?\�\��b\����m\"�@�\�\�*�!��0|5�.�\�\�S�\�kM���7\�m�>\�s\�F2`\�\�\�\�$?\�\�^k\Z\�\�9n\�y[�\�7���\nD�@0\�\�\�3����\�?�ܦt?#�\n|gH�/\'��u��\�A?\�?�\�S�\��VaS�\�SҩN��U��y?�>�\�\�j�\�=]\�z�\�/\�t�_\�\�z�\�\�\�7�\\���\�D\0\0\0\0IEND�B`�','Century Tuna Afritada',24.00,'155 g','CG',8,'S','2024-06-26 07:00:16','ACT'),(9,'PROD0009','4800249902068',_binary '�PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0�\0\0\0p\0\0\0�.r\0\0	�IDATx^\�\�j%[��?=\�s��\�\�\�rC\�T>,r��e5\�\�7\�·���\��BO\�����\�==\�zz\�/\�\��_\�\�y�\�\�\�~��\��BO\�����\�==\�zz\�/\�\��_\�\�y�\�\�\�~��\��BO\�����\����\�?\�\� ��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ�\�Yt(!��\�[��&.(�e1\�&r\�e^rZ\�\�\��8\�%�E�~͏\�_rZ\�\�\��8\�%_,��\'��f�\�\�_������N^q���\���l���c���ƏI�V��-d�sD?/\�o,���^�\�\�?�忪�~Va��\��@\�k\�O�K�E\�\�t͗��J\�\�\�T��-d����N%�\�3�\���\�\�\��\�kʟU�H\����<�8}\n\�\�\�!�)x\�׋�?\�^S��T�ş-d�DP?�U�~\n^\�\�����\�}���I\��>��\"�\�\�\�_�\�\���7Ǽ_\�\�y�\�\�\�~��\��BO\������w\�.<>��\0\0\0\0IEND�B`�','CDO Meat loaf',26.00,'210 g','CG',9,'F','2024-06-26 07:01:28','ACT'),(10,'PROD0010','7484851004494',_binary '�PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0�\0\0\0p\0\0\0�.r\0\0�IDATx^\�\�j%iC\��/�\�0;⌜+w[Y(�ҏ[:\�Q����z��\�x\�0�_\�\�z�\�\�\�~��\��BO\�����\�=]\�z�\�/\�t�_\�\�z�\�\�\�~��\��BO\�����\�=]\�z�\�/\�t�_\�\�z�\�\�U}����~\�W��~��)M\�\�\Z\���u%\�\���\r;�1�9S��\�5&�!2S\�J�\�UbvbcFr�45\�kL0Cd�֕$ӫ:\�6\�\�ƌ\�Lij�ט`�\�L�+I�Wu�m؉�ș\�\�l�1���ZW�L�\�۰3:�3��\�^c�\"3��$�^\�!�a\'6ft gJS��\�3Dfj]I2��Cl\�Nl\�\�@Δ�f{�	f�\�Ժ�dzU�؆�ؘс�)M\�\�\Z\���u%\�\���\r;�1�9S��\�5&�!2S\�J�\�UbvbcFr�45\�kL0Cd�֕$ӫ:\�6\�\�ƌ\�Lij�ט`�\�L�+I�Wu�m؉�ș\�\�l�1���ZW�L�\�۰3:�3��\�^c�\"3��$�^\�!�a\'6ft gJS��\�3Dfj]I2��Cl\�Nl\�\�@Δ�f{�	f�\�Ժ�dzU�؆�ؘс�)M\�\�\Z\���u%\�\���\r;�1�9S��\�5&�!2S\�J�\�UbvbcFr�45\�kL0Cd�֕$ӫ:\�6\�\�ƌ\�Lij�ט`�\�L�+I�Wu�m؉�ș\�\�l�1���ZW�L�\�۰3:�3��\�^c�\"3��$�^\�!�a\'6ft gJS��\�3Dfj]I2��Cl\�Nl\�\�@Δ�f{�	f�\�Ժ�dzU�؆�ؘс�)M\�\�\Z\���u%\�\���\r;�1�9S��\�5&�!2S\�J�\�UbvbcFr�45\�kL0Cd�֕$ӫ:\�6\�\�ƌ\�Lij�ט`�\�L�+I�Wu�m؉�ș\�\�l�1���ZW�L�\�۰3:�3��\�^c�\"3��$�^\�!�a\'6ft gJS��\�3Dfj]I2��Cl\�Nl\�\�@Δ�f{�	f�\�Ժ�dzU�؆�ؘс�)M\�\�\Z\���u%\�\���\r;�1�9S��\�5&�!2S\�J�\�UbvbcFr�45\�kL0Cd�֕$ӫ:\�6\�\�ƌ\�Lij�ט`�\�L�+I�Wu�m؉�ș\�\�l�1���ZW�L�\�۰3:�3��\�^c�\"3��$�^\�!�a\'6ft gJS��\�3Dfj]I2��Cl\�Nl\�\�@Δ�f{�	f�\�Ժ�dzU�؆�ؘс�)M\�\�\Z\���u%\�\���\r;�1�9S��\�5&�!2S\�J�\�UbvbcFr�45\�kL0Cd�֕$ӫ:\�6\�\�ƌ\�Lij�ט`�\�L�+I�Wu�m؉�ș\�\�l�1���ZW�L�\�۰3:�3��\�^c�\"3��$�^\�!�a\'6ft gJS��\�3Dfj]I2��Cl\�Nl\�\�@Δ�f{�	f�\�Ժ�dzU�؆�ؘс�)M\�\�\Z\���u%\�\���\r;�1�9S��\�5&�!2S\�J�\�UbvbcFr�45\�kL0Cd�֕$ӫ:\�6\�\�ƌ\�Lij�ט`�\�L�+I�Wu�m؉�ș\�\�l�1���ZW�L�\�۰3:�3��\�^c�\"3��$�^\�!�a\'6ft gJS��\�3Dfj]I2��Cl\�Nl\�\�@Δ�f{�	f�\�Ժ�dzU�؆�ؘс�)M\�\�\Z\���u%\�\���\r;�1�9S��\�5&�!2S\�J�\�UbvbcFr�45\�kL0Cd�֕$ӫ:\�6\�\�ƌ\�Lij�ט`�\�L�+I�Wu�m؉�ș\�\�l�1���ZW�L�\�۰3:�3��\�^c�\"3��$�^\�!�a\'6ft gJS��\�3Dfj]I2��Cl\�Nl\�\�@Δ�f{�	f�\�Ժ�dzU�؆�ؘс�)M\�\�\Z\���u%\�\���\r;�1�9S��\�5&�!2S\�J�\�UbvbcFr�45\�kL0Cd�֕$ӫ:\�6\�\�ƌ\�Lij�ט`�\�L�+I�Wu�m؉�ș\�\�l�1���ZW�L�\�۰3:�3��\�^c�\"3��$�^\�!�a\'6ft gJS��\�3Dfj]I2��Cl\�Nl\�\�@Δ�f{�	f�\�Ժ�dzU�؆�ؘс�)M\�\�\Z\���u%\�\���\r;�1�9S��\�5&�!2S\�J�\�UbvbcFr�45\�kL0Cd�֕$ӫ:\�6\�\�ƌ\�Lij�ט`�\�L�+I�Wu\�\�ۜ\�7��x���\�&աos\�\�\��C_��\��(�\�o?�ʌ��\\\�|\�\�ϧC��\�[�\��#Z\�\�0\"0\��|�<�y\�\�	\���3�\�d�\�r(��Om\�L���|A3���C\�\�\���\�:���\�\�6����U2߮r6��ul�\r\�J�\�?\�m��\�\�\�ʜO@�& g�a\��j��S\�\�_�k�	L�3�O���M\�ӑ\��֯\�OJ}<\�#CG��ѿ\��E\Z���|s>i^��O\�o�\�g\�����\�����\�=]\�z�\�/\�t�_\�\��/Yq�t\��\0\0\0\0IEND�B`�','Century Tuna Mechado',27.00,'155 g','CG',10,'S','2024-06-26 07:02:35','ACT'),(11,'PROD0011','7484854013622',_binary '�PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0�\0\0\0p\0\0\0�.r\0\0	/IDATx^\�\�j%G\��?mcd�u;ru/\�\�C\"eT�R\�\�_�����z�\�/\�t�_\�\�z�\�\�\�~��\��BO\�����\�=]\�z�\�/\�t�_\�\�z�\�\�\�~��\��BO\�����\�=]\�z�\�/\�t�_\�\�:�B__�<���_�\���\�(�th��f4\�8į\�\�k�ɋ���\r����Ì\���y�7y�\�ⲣ�ҡ!\�sr�\�\�\����\"�\�&/6Z\\vT:4�~B3�{\�WtT\�5\�\�\�F�ˎ�J��\�\�A\�aFs�C�����ƛ�\�hq\�QP\�\��99\�h\�q�_\�Q�\�x�-.;\n*\ZB?!�\�=\�+:*\�\Zo\�b�\�eGA�CC\�\� \�0��\�!~EGE^\�M^l��\�(�th��f4\�8į\�\�k�ɋ���\r����Ì\���y�7y�\�ⲣ�ҡ!\�sr�\�\�\����\"�\�&/6Z\\vT:4�~B3�{\�WtT\�5\�\�\�F�ˎ�J��\�\�A\�aFs�C�����ƛ�\�hq\�QP\�\��99\�h\�q�_\�Q�\�x�-.;\n*\ZB?!�\�=\�+:*\�\Zo\�b�\�eGA�CC\�\� \�0��\�!~EGE^\�M^l��\�(�th��f4\�8į\�\�k�ɋ���\r����Ì\���y�7y�\�ⲣ�ҡ!\�sr�\�\�\����\"�\�&/6Z\\vT:4�~B3�{\�WtT\�5\�\�\�F�ˎ�J��\�\�A\�aFs�C�����ƛ�\�hq\�QP\�\��99\�h\�q�_\�Q�\�x�-.;\n*\ZB?!�\�=\�+:*\�\Zo\�b�\�eGA�CC\�\� \�0��\�!~EGE^\�M^l��\�(�th��f4\�8į\�\�k�ɋ���\r����Ì\���y�7y�\�ⲣ�ҡ!\�sr�\�\�\����\"�\�&/6Z\\vT:4�~B3�{\�WtT\�5\�\�\�F�ˎ�J��\�\�A\�aFs�C�����ƛ�\�hq\�QP\�\��99\�h\�q�_\�Q�\�x�-.;\n*\ZB?!�\�=\�+:*\�\Zo\�b�\�eGA�CC\�\� \�0��\�!~EGE^\�M^l��\�(�th��f4\�8į\�\�k�ɋ���\r����Ì\���y�7y�\�ⲣ�ҡ!\�sr�\�\�\����\"�\�&/6Z\\vT:4�~B3�{\�WtT\�5\�\�\�F�ˎ�J��\�\�A\�aFs�C�����ƛ�\�hq\�QP\�\��99\�h\�q�_\�Q�\�x�-.;\n*\ZB?!�\�=\�+:*\�\Zo\�b�\�eGA�CC\�\� \�0��\�!~EGE^\�M^l��\�(�th��f4\�8į\�\�k�ɋ���\r����Ì\���y�7y�\�ⲣ�ҡ!\�sr�\�\�\����\"�\�&/6Z\\vT:4�~B3�{\�WtT\�5\�\�\�F�ˎ�J��\�\�A\�aFs�C�����ƛ�\�hq\�QP\�\��99\�h\�q�_\�Q�\�x�-.;\n*\ZB?!�\�=\�+:*\�\Zo\�b�\�eGA�CC\�\� \�0��\�!~EGE^\�M^l��\�(�th��f4\�8į\�\�k�ɋ���\r����Ì\���y�7y�\�ⲣ�ҡ!\�sr�\�\�\����\"�\�&/6Z\\vT:4�~B3�{\�WtT\�5\�\�\�F�ˎ�J��\�\�A\�aFs�C�����ƛ�\�hq\�QP\�\��99\�h\�q�_\�Q�\�x�-.;\n*\ZB?!�\�=\�+:*\�\Zo\�b�\�eGA�CC\�\� \�0��\�!~EGE^\�M^l��\�(�th��f4\�8į\�\�k�ɋ���\r����Ì\���y�7y�\�ⲣ�ҡ!\�sr�\�\�\����\"�\�&/6Z\\vT:4�~B3�{\�WtT\�5\�\�\�F�ˎ�J��\�\�A\�aFs�C�����ƛ�\�hq\�QP\�\��99\�h\�q�_\�Q�\�x�-.;\n*\ZB?!�\�=\�+:*\�\Zo\�b�\�eGA�CC\�\� \�0��\�!~EGE^\�M^l��\�(�th��f4\�8į\�\�k�ɋ���\r����Ì\���y�7y�\�ⲣ�ҡ!\�sr�\�\�\����\"�\�&/6Z\\vT:4�~B3�{\�WtT\�5\�\�\�F�ˎ�J��\�\�A\�aFs�C�����ƛ�\�hq\�QP\�\��99\�h\�q�_\�Q�\�x�-.;\n*\ZB?!�\�=\�+:*\�\Zo\�b�\�eGA�CC\�\� \�0��\�!~EGE^\�M^l��\�(�th��f4\�8į\�\�k�ɋ���\r����Ì\���y�7y�\�ⲣ�ҡ!\�sr�\�\�\����\"�\�&/6Z\\vT:4�~B3�{\�WtTt}\�Z𒣢\�k\����]_�\���.��U��y�\�vU\�\r�C\�\�!\�\�߹7����E\�_\Z\��\�\�7��?�\"��<	��\�D�\�ԭ	���|���\���\�\�\�\�@(^TC2\�\�4}�3\�\Z�\�\�\�\�\�\�$�Z��\�\�\�\�\�v�I\�ך�>��r\�\�d�\�\�;�\�\'\�\���{��䄰D;�E?�\�\�|\�\�\�\�\nI~~\�P�\�I�\�[�\�Pmѕ�\��\�[�\�?���ŨR\�0���\�+aN~�~o��s�_\�\�z�\�\�\�~��\��BO\������\�a��~\�Ux\0\0\0\0IEND�B`�','Angel KremQuezo',56.00,'370 ml','DA',11,'S','2024-06-26 07:06:01','ACT'),(12,'PROD0012','7484854014926',_binary '�PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0�\0\0\0p\0\0\0�.r\0\0	QIDATx^\�Q�$W���\�\�zl\"̒ؽ\�H���j�&��\�γ\�˅w6\�z��_\�\�\�~��\����>\�z��_\�\�\�~��\����>\�z��_\�\�\�~��\����>\�z��_\�\�\�~��\����>\�z��_\�\�S}����Ź�\�O�\���+k\�rMd<\�X�\"� 6Sbv\�3h\�͕5n�&2x,B�L���\r;\��\�\�\�\Z�\\��H&�\�T�؆��Zrse�[���\�\0�P$\�f�Cl\�N|����\�-\�D\�o�E(�	b3\�!�a\'>�ց\�\\Y\�k\"\�7�\"\���\�۰�A\�@n��q\�5�\��`�d�\�Lu�m؉Ϡu 7Wָ\�\�x\�\r�E2Al�:\�6\�\�g\�:��+k\�rMd<\�X�\"� 6Sbv\�3h\�͕5n�&2x,B�L���\r;\��\�\�\�\Z�\\��H&�\�T�؆��Zrse�[���\�\0�P$\�f�Cl\�N|����\�-\�D\�o�E(�	b3\�!�a\'>�ց\�\\Y\�k\"\�7�\"\���\�۰�A\�@n��q\�5�\��`�d�\�Lu�m؉Ϡu 7Wָ\�\�x\�\r�E2Al�:\�6\�\�g\�:��+k\�rMd<\�X�\"� 6Sbv\�3h\�͕5n�&2x,B�L���\r;\��\�\�\�\Z�\\��H&�\�T�؆��Zrse�[���\�\0�P$\�f�Cl\�N|����\�-\�D\�o�E(�	b3\�!�a\'>�ց\�\\Y\�k\"\�7�\"\���\�۰�A\�@n��q\�5�\��`�d�\�Lu�m؉Ϡu 7Wָ\�\�x\�\r�E2Al�:\�6\�\�g\�:��+k\�rMd<\�X�\"� 6Sbv\�3h\�͕5n�&2x,B�L���\r;\��\�\�\�\Z�\\��H&�\�T�؆��Zrse�[���\�\0�P$\�f�Cl\�N|����\�-\�D\�o�E(�	b3\�!�a\'>�ց\�\\Y\�k\"\�7�\"\���\�۰�A\�@n��q\�5�\��`�d�\�Lu�m؉Ϡu 7Wָ\�\�x\�\r�E2Al�:\�6\�\�g\�:��+k\�rMd<\�X�\"� 6Sbv\�3h\�͕5n�&2x,B�L���\r;\��\�\�\�\Z�\\��H&�\�T�؆��Zrse�[���\�\0�P$\�f�Cl\�N|����\�-\�D\�o�E(�	b3\�!�a\'>�ց\�\\Y\�k\"\�7�\"\���\�۰�A\�@n��q\�5�\��`�d�\�Lu�m؉Ϡu 7Wָ\�\�x\�\r�E2Al�:\�6\�\�g\�:��+k\�rMd<\�X�\"� 6Sbv\�3h\�͕5n�&2x,B�L���\r;\��\�\�\�\Z�\\��H&�\�T�؆��Zrse�[���\�\0�P$\�f�Cl\�N|����\�-\�D\�o�E(�	b3\�!�a\'>�ց\�\\Y\�k\"\�7�\"\���\�۰�A\�@n��q\�5�\��`�d�\�Lu�m؉Ϡu 7Wָ\�\�x\�\r�E2Al�:\�6\�\�g\�:��+k\�rMd<\�X�\"� 6Sbv\�3h\�͕5n�&2x,B�L���\r;\��\�\�\�\Z�\\��H&�\�T�؆��Zrse�[���\�\0�P$\�f�Cl\�N|����\�-\�D\�o�E(�	b3\�!�a\'>�ց\�\\Y\�k\"\�7�\"\���\�۰�A\�@n��q\�5�\��`�d�\�Lu�m؉Ϡu 7Wָ\�\�x\�\r�E2Al�:\�6\�\�g\�:��+k\�rMd<\�X�\"� 6Sbv\�3h\�͕5n�&2x,B�L���\r;\��\�\�\�\Z�\\��H&�\�T�؆��Zrse�[���\�\0�P$\�f�Cl\�N|����\�-\�D\�o�E(�	b3\�!�a\'>�ց\�\\Y\�k\"\�7�\"\���\�۰�A\�@n��q\�5�\��`�d�\�Lu�m؉Ϡu 7Wָ\�\�x\�\r�E2Al�:\�6\�\�g\�:��+k\�rMd<\�X�\"� 6Sbv\�3h\�͕5n�&2x,B�L���\r;\��\�\�\�\Z�\\��H&�\�T�؆��Zrse�[���\�\0�P$\�f�Cl\�N|����\�-\�D\�o�E(�	b3\�!�a\'>�ց\�\\Y\�k\"\�7�\"\���\�۰�A\�@n��q\�5�\��`�d�\�Lu�m؉Ϡu 7Wָ\�\�x\�\r�E2Al�:\�6\�\�g\�:��+k\�rMd<\�X�\"� 6Sbv\�3h\�͕5n�&2x,B�L���\r;\��\�\�\�\Z�\\��H&�\�T�؆��Zrse�[���\�\0�P$\�f�C��\�y�\�M�C��\�y�\�M�C��\�y�\�M\�C_���܆\�\�Tf�P�n��\�\�/�S�����\"]��\�7�\�\�в����\�\�O\�\�\��<\�s\�~�\�\�7�򿴵�\�	��/�Y��?G\���$b�\�G�?gL?O:\�\�?3�\�O�m\�1���\�Ͽ�\��T>~˘\�L:\�\�?3����\�\�/\�*\�s��v\�1\�3�\�O�Y�\�\���\�\'\�	o̧\��\��K�\�yP\�\n\�\�\Z\�\�f=\�[�\r�\�\��\��\�\�\�|*�\\�f�\�P��~�⯟�\��\���;\�_\�\�\�~��\����>\�z��_\�\�\�__�۸����\0\0\0\0IEND�B`�','Birchtree Fortified Milk',8.00,'33 g','DA',12,'F','2024-06-26 07:08:01','ACT'),(13,'PROD0013','7622300501549',_binary '�PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0�\0\0\0p\0\0\0�.r\0\0*IDATx^\�\�n%\���?�\��H\�Z�\�6V0X�\ZU\�=�\�\��h㭇\����^\�zz�_\�\�\�~��\����^\�zz�_\�\�\�~��\����^\�zz�_\�\�\�~��\����^\�zz�_\�\�\�~��\����^\�zz��\�\�\��\�\�`<\�bM\�m�v\�\�\r@���9��!�L��U��\�\�\�8�5�S@��\�=G7\0m\�\�>�`2\�VA��\�w\�X\0\�4N\�fj\�\�\0�I;�\��R�ɴ�Z�\Z^ߍc\\\�8D��\�st\�&\�`�\�cH&\�\�k\�kx}7�pM\�m�v\�\�\r@���9��!�L��U��\�\�\�8�5�S@��\�=G7\0m\�\�>�`2\�VA��\�w\�X\0\�4N\�fj\�\�\0�I;�\��R�ɴ�Z�\Z^ߍc\\\�8D��\�st\�&\�`�\�cH&\�\�k\�kx}7�pM\�m�v\�\�\r@���9��!�L��U��\�\�\�8�5�S@��\�=G7\0m\�\�>�`2\�VA��\�w\�X\0\�4N\�fj\�\�\0�I;�\��R�ɴ�Z�\Z^ߍc\\\�8D��\�st\�&\�`�\�cH&\�\�k\�kx}7�pM\�m�v\�\�\r@���9��!�L��U��\�\�\�8�5�S@��\�=G7\0m\�\�>�`2\�VA��\�w\�X\0\�4N\�fj\�\�\0�I;�\��R�ɴ�Z�\Z^ߍc\\\�8D��\�st\�&\�`�\�cH&\�\�k\�kx}7�pM\�m�v\�\�\r@���9��!�L��U��\�\�\�8�5�S@��\�=G7\0m\�\�>�`2\�VA��\�w\�X\0\�4N\�fj\�\�\0�I;�\��R�ɴ�Z�\Z^ߍc\\\�8D��\�st\�&\�`�\�cH&\�\�k\�kx}7�pM\�m�v\�\�\r@���9��!�L��U��\�\�\�8�5�S@��\�=G7\0m\�\�>�`2\�VA��\�w\�X\0\�4N\�fj\�\�\0�I;�\��R�ɴ�Z�\Z^ߍc\\\�8D��\�st\�&\�`�\�cH&\�\�k\�kx}7�pM\�m�v\�\�\r@���9��!�L��U��\�\�\�8�5�S@��\�=G7\0m\�\�>�`2\�VA��\�w\�X\0\�4N\�fj\�\�\0�I;�\��R�ɴ�Z�\Z^ߍc\\\�8D��\�st\�&\�`�\�cH&\�\�k\�kx}7�pM\�m�v\�\�\r@���9��!�L��U��\�\�\�8�5�S@��\�=G7\0m\�\�>�`2\�VA��\�w\�X\0\�4N\�fj\�\�\0�I;�\��R�ɴ�Z�\Z^ߍc\\\�8D��\�st\�&\�`�\�cH&\�\�k\�kx}7�pM\�m�v\�\�\r@���9��!�L��U��\�\�\�8�5�S@��\�=G7\0m\�\�>�`2\�VA��\�w\�X\0\�4N\�fj\�\�\0�I;�\��R�ɴ�Z�\Z^ߍc\\\�8D��\�st\�&\�`�\�cH&\�\�k\�kx}7�pM\�m�v\�\�\r@���9��!�L��U��\�\�\�8�5�S@��\�=G7\0m\�\�>�`2\�VA��\�w\�X\0\�4N\�fj\�\�\0�I;�\��R�ɴ�Z�\Z^ߍc\\\�8D��\�st\�&\�`�\�cH&\�\�k\�kx}7�pM\�m�v\�\�\r@���9��!�L��U��\�\�\�8�5�S@��\�=G7\0m\�\�>�`2\�VA��\�w\�X\0\�4N\�fj\�\�\0�I;�\��R�ɴ�Z�\Z^ߍc\\\�8D��\�st\�&\�`�\�cH&\�\�k\�kx}7�pM\�m�v\�\�\r@���9��!�L��U��\�\�\�8�5�S@��\�=G7\0m\�\�>�`2\�VA��\�w\�X\0\�4N\�fj\�\�\0�I;�\��R�ɴ�Z�\Z^ߍc\\\�8D��\�st\�&\�`�\�cH&\�\�k\�kx}7�pM\�m�v\�\�\r@���9��!�L��U��\�\�\�8�5�S@��\�=G7\0m\�\�>�`2\�VA��\�w\�X\0\�4N\�fj\�\�\0�I;�\��R�ɴ�Z�\Z^ߍc\\\�8D��\�st\�&\�`�\�cH&\�\�k\�kx}7�pM\�m�v\�\�\r@���9��!�L��U��\�\�\�8�5�S@��\�=G7\0m\�\�>�`2\�VA��\�w\�X\0\�4N\�fj\�\�\0�I;�\��R�ɴ�Z�\Z^ߍc\\\�8D��\�st\�&\�`�\�cH&\�\�k\�kx}7�pM\�m�v\�\�\r@���9��!�L��U��\�\�\�8�5�S@��\�=G7\0m\�\�>�`2\�VA��\�w\�X\0\�4N\�fj\�\�\0�I;�\��R�ɴ�Z�\Z^ߍc\\\�8D��\�st\�&\�`�\�cH&\�\�k\�kx}7�pM\�m�v\�\�\r@���9��!�L��U��\�\�\�8�5�S@��\�=G7\0m\�\�>�`2\�VA��\�w\�X\0\�4N\�fj\�\�\0�I;�\��R�ɴ�Z�\Z^ߍc\\\�8D��\�st\�&\�`�\�cH&\�\�k\�kx}7�pM\�m�v\�\�\r@���9��!�L��U��\�\�\�8�5�S@��\�=G7\0m\�\�>�`2\�VA��\�w\�X\0\�4N\�fj\�\�\0�I;�\��R�ɴ�Z�\Z^ߍc\\\�8D��\�st\�&\�`�\�cH&\�\�k\�kx}7�pM\�m�v\�\�\r@���9��!�L��U��\�\�\�8�5�S@��\�=G7\0m\�\�>�`2\�VA��\�w\�X\0\�4N\�fj\�\�\0�I;�\��R�ɴ�Z�\Z^ߍc\\\�8D��\�st\�&\�`�\�cH&\�\�k\�kx}7�pM\�m�v\�\�\r@���9��!�L��U��\�\�\�8�5�S@��\�=G7\0m\�\�>�`2\�VA��\�w\�X\0\�4N\�fj\�\�\0�I;�\��R�ɴ�Z�\Z^ߍc\\\�8D��\�st\�&\�`�\�cH&\�\�k\�\�\��v�����~��߮?��UЏ_\�\�\�_2}�Z=��nל����\�[~�TK\���ί���\��#\�E\�\�4^\�\�~֩\��v\�F}�y�\��c\�\�\�A��\�xM�ڔ}�\�t\�ߜjO�2V�vϭO\�Ҵen고/f�\�\�\�?q����\�>�jG\��t\�\�d�\�T[\�>�\�g\�knןi��:coN����&`L�b��\�q\�榮A\���\�\Z�\�3\�˭\�8Վ�\�\�\�\'\�\'\�	,\�\Z�����Z\�\�9��\�|^\':�\�#�\�\��l�\�?\�ֲ\�/\�\�z�\�\�\��BO�\�=�\�/\�\��2\0���oZ\�\0\0\0\0IEND�B`�','Cheez Whiz Squeeze',40.00,'115 g','DA',13,'F','2024-06-26 07:12:22','ACT'),(14,'PROD0014','4805358373037',_binary '�PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0�\0\0\0p\0\0\0�.r\0\0\n\�IDATx^\�\�n G��?}��{D��\�&���T�(5\�\�o<;>\�xX�\�\�\����\����\�zz�\�\�\����\����\�zz�\�\�\����\����\�zz�\�\�\����\����\�zz�\�\�\����\�\�}|�5\�7�lM\�xj4T.�dVCʻ��O�Hm-ce�\�\�\�PA�\�S�)�r\�%��\ZRޕ\�*Ejk+���W�D�\n\�5�\ZM�K/Y�Ր\�l�S)R[\�X\�5�\�$:T�\�\�h\n�\\z\�*���we\�J�\�Z\�\�(�\�U\'ѡ�p��FS@\�\�KVa5��+��T�\�\�2VFy\r�:��k<5�*�^�\n�!\�]\���R����2\�kx\�It� \\\�\�P�\��UX\r)\�\�\�?�\"����Q^ëN�C\�\ZO���ʥ��\�jHyW6����e��\�\Z^u*\�xj4T.�dVCʻ��O�Hm-ce�\�\�\�PA�\�S�)�r\�%��\ZRޕ\�*Ejk+���W�D�\n\�5�\ZM�K/Y�Ր\�l�S)R[\�X\�5�\�$:T�\�\�h\n�\\z\�*���we\�J�\�Z\�\�(�\�U\'ѡ�p��FS@\�\�KVa5��+��T�\�\�2VFy\r�:��k<5�*�^�\n�!\�]\���R����2\�kx\�It� \\\�\�P�\��UX\r)\�\�\�?�\"����Q^ëN�C\�\ZO���ʥ��\�jHyW6����e��\�\Z^u*\�xj4T.�dVCʻ��O�Hm-ce�\�\�\�PA�\�S�)�r\�%��\ZRޕ\�*Ejk+���W�D�\n\�5�\ZM�K/Y�Ր\�l�S)R[\�X\�5�\�$:T�\�\�h\n�\\z\�*���we\�J�\�Z\�\�(�\�U\'ѡ�p��FS@\�\�KVa5��+��T�\�\�2VFy\r�:��k<5�*�^�\n�!\�]\���R����2\�kx\�It� \\\�\�P�\��UX\r)\�\�\�?�\"����Q^ëN�C\�\ZO���ʥ��\�jHyW6����e��\�\Z^u*\�xj4T.�dVCʻ��O�Hm-ce�\�\�\�PA�\�S�)�r\�%��\ZRޕ\�*Ejk+���W�D�\n\�5�\ZM�K/Y�Ր\�l�S)R[\�X\�5�\�$:T�\�\�h\n�\\z\�*���we\�J�\�Z\�\�(�\�U\'ѡ�p��FS@\�\�KVa5��+��T�\�\�2VFy\r�:��k<5�*�^�\n�!\�]\���R����2\�kx\�It� \\\�\�P�\��UX\r)\�\�\�?�\"����Q^ëN�C\�\ZO���ʥ��\�jHyW6����e��\�\Z^u*\�xj4T.�dVCʻ��O�Hm-ce�\�\�\�PA�\�S�)�r\�%��\ZRޕ\�*Ejk+���W�D�\n\�5�\ZM�K/Y�Ր\�l�S)R[\�X\�5�\�$:T�\�\�h\n�\\z\�*���we\�J�\�Z\�\�(�\�U\'ѡ�p��FS@\�\�KVa5��+��T�\�\�2VFy\r�:��k<5�*�^�\n�!\�]\���R����2\�kx\�It� \\\�\�P�\��UX\r)\�\�\�?�\"����Q^ëN�C\�\ZO���ʥ��\�jHyW6����e��\�\Z^u*\�xj4T.�dVCʻ��O�Hm-ce�\�\�\�PA�\�S�)�r\�%��\ZRޕ\�*Ejk+���W�D�\n\�5�\ZM�K/Y�Ր\�l�S)R[\�X\�5�\�$:T�\�\�h\n�\\z\�*���we\�J�\�Z\�\�(�\�U\'ѡ�p��FS@\�\�KVa5��+��T�\�\�2VFy\r�:��k<5�*�^�\n�!\�]\���R����2\�kx\�It� \\\�\�P�\��UX\r)\�\�\�?�\"����Q^ëN�C\�\ZO���ʥ��\�jHyW6����e��\�\Z^u*\�xj4T.�dVCʻ��O�Hm-ce�\�\�\�PA�\�S�)�r\�%��\ZRޕ\�*Ejk+���W�D�\n\�5�\ZM�K/Y�Ր\�l�S)R[\�X\�5�\�$:T�\�\�h\n�\\z\�*���we\�J�\�Z\�\�(�\�U\'ѡ�p��FS@\�\�KVa5��+��T�\�\�2VFy\r�:��k<5�*�^�\n�!\�]\���R����2\�kx\�It� \\\�\�P�\��UX\r)\�\�\�?�\"����Q^ëN�C\�\ZO���ʥ��\�jHyW6����e��\�\Z^u*\�xj4T.�dVCʻ��O�Hm-ce�\�\�\�PA�\�S�)�r\�%��\ZRޕ\�*Ejk+���W�D�\n\�5�\ZM�K/Y�Ր\�l�S)R[\�X\�5�\�$:T�\�\�h\n�\\z\�*���we\�J�\�Z\�\�(�\�U\'ѡ�p��FS@\�\�KVa5��+��T�\�\�2VFy\r�:��k<5�*�^�\n�!\�]\���R����2\�kx\�It� \\\�\�P�\��UX\r)\�\�\�?�\"����Q^ëN�C\�\ZO���ʥ��\�jHyW6����e��\�\Z^u*\�xj4T.�dVCʻ��O�Hm-ce�\�\�\�PA�\�S�)�r\�%��\ZRޕ\�*Ejk+���W�D�\n\�5�\ZM�K/Y�Ր\�l�S)R[\�X\�5�\�$:T�\�\�h\n�\\z\�*���we\�J�\�Z\�\�(�\�U\'ѡ�p��FS@\�\�KVa5��+��T�\�\�2VFy\r�:��k<5�*�^�\n�!\�]\���R����2\�kx\�It� \\\�\�P�\��UX\r)\�\�\�?�\"����Q^ëN�C\�\ZO���ʥ��\�jHyW6����e��\�\Z^u*\�xj4T.�dVCʻ��O�Hm-ce�\�\�\�PA�\�S�)�r\�%��\ZRޕ\�*Ejk+���W�D�\n\�5�\ZM�K/Y�Ր\�l�S)R[\�X\�5�\�$:T�\�\�h\n�\\z\�*���we\�J�\�Z\�\�\�_\�\��_�2�\�\��8�×����5?��\�%\�}���\�k\�T\�/P\�oj\�|\�\�x��MMB��\�?_}��\n��\�o�U\�V�3\�rj~�|VB&�q|jTߠ�\�\�\�TB\�\">ӤX��$̨z\�4��o}�\�2�\�\�W�]\�\�+(�U	�S�竘�)KMv�\����E7:\�dX\�w�n�S0�~�|\�3�/f�i2�_w��\���\�\�e\�������P���\�!�sd\��`\�ǯ�\�\�1\�s�j~����\�/�y\�\�\����\����\�zz�\�\�\����\�����y\�ж\0\0\0\0IEND�B`�','Daily Queso',45.00,'165 g','DA',14,'F','2024-06-26 07:14:52','ACT'),(15,'PROD0015','4808647020094',_binary '�PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0�\0\0\0p\0\0\0�.r\0\0\nIDATx^\�\�j%gC\��/\�Қ\�ѦQ\n\�2�\�!˾�\�\�_o=�>�\��z�\�\�\��BO�\�=�\�/\�\�z�\�\�\��BO�\�=�\�/\�\�z�\�\�\��BO�\�=�\�/\�\�z�\�\�\��BO�\�=�\�/\�\�z�\�\�\��BO�\�}|������\r)�I�==\�\�j\� EvF�\�\�Ub\Z�db\�6Æ$Ӟ\ni{�b�\";#\�i��1�R21s�aC�iO���Z1H���\�}U��F)���Ͱ!ɴ��B\�^��\�\�\0Èpھ�CL��L\�\�fؐd\�\�C!m�VRdg�aD8m_\�!�QJ&fn3lH2\�顐�W+)�3�0\"���\�\�(%3�6$�\�\�PH۫�\�`N\�Wu�i����\��L{z(�\�ՊA�\�0��\�:\�4J\�\�\�m�\rI�==\�\�j\� EvF�\�\�Ub\Z�db\�6Æ$Ӟ\ni{�b�\";#\�i��1�R21s�aC�iO���Z1H���\�}U��F)���Ͱ!ɴ��B\�^��\�\�\0Èpھ�CL��L\�\�fؐd\�\�C!m�VRdg�aD8m_\�!�QJ&fn3lH2\�顐�W+)�3�0\"���\�\�(%3�6$�\�\�PH۫�\�`N\�Wu�i����\��L{z(�\�ՊA�\�0��\�:\�4J\�\�\�m�\rI�==\�\�j\� EvF�\�\�Ub\Z�db\�6Æ$Ӟ\ni{�b�\";#\�i��1�R21s�aC�iO���Z1H���\�}U��F)���Ͱ!ɴ��B\�^��\�\�\0Èpھ�CL��L\�\�fؐd\�\�C!m�VRdg�aD8m_\�!�QJ&fn3lH2\�顐�W+)�3�0\"���\�\�(%3�6$�\�\�PH۫�\�`N\�Wu�i����\��L{z(�\�ՊA�\�0��\�:\�4J\�\�\�m�\rI�==\�\�j\� EvF�\�\�Ub\Z�db\�6Æ$Ӟ\ni{�b�\";#\�i��1�R21s�aC�iO���Z1H���\�}U��F)���Ͱ!ɴ��B\�^��\�\�\0Èpھ�CL��L\�\�fؐd\�\�C!m�VRdg�aD8m_\�!�QJ&fn3lH2\�顐�W+)�3�0\"���\�\�(%3�6$�\�\�PH۫�\�`N\�Wu�i����\��L{z(�\�ՊA�\�0��\�:\�4J\�\�\�m�\rI�==\�\�j\� EvF�\�\�Ub\Z�db\�6Æ$Ӟ\ni{�b�\";#\�i��1�R21s�aC�iO���Z1H���\�}U��F)���Ͱ!ɴ��B\�^��\�\�\0Èpھ�CL��L\�\�fؐd\�\�C!m�VRdg�aD8m_\�!�QJ&fn3lH2\�顐�W+)�3�0\"���\�\�(%3�6$�\�\�PH۫�\�`N\�Wu�i����\��L{z(�\�ՊA�\�0��\�:\�4J\�\�\�m�\rI�==\�\�j\� EvF�\�\�Ub\Z�db\�6Æ$Ӟ\ni{�b�\";#\�i��1�R21s�aC�iO���Z1H���\�}U��F)���Ͱ!ɴ��B\�^��\�\�\0Èpھ�CL��L\�\�fؐd\�\�C!m�VRdg�aD8m_\�!�QJ&fn3lH2\�顐�W+)�3�0\"���\�\�(%3�6$�\�\�PH۫�\�`N\�Wu�i����\��L{z(�\�ՊA�\�0��\�:\�4J\�\�\�m�\rI�==\�\�j\� EvF�\�\�Ub\Z�db\�6Æ$Ӟ\ni{�b�\";#\�i��1�R21s�aC�iO���Z1H���\�}U��F)���Ͱ!ɴ��B\�^��\�\�\0Èpھ�CL��L\�\�fؐd\�\�C!m�VRdg�aD8m_\�!�QJ&fn3lH2\�顐�W+)�3�0\"���\�\�(%3�6$�\�\�PH۫�\�`N\�Wu�i����\��L{z(�\�ՊA�\�0��\�:\�4J\�\�\�m�\rI�==\�\�j\� EvF�\�\�Ub\Z�db\�6Æ$Ӟ\ni{�b�\";#\�i��1�R21s�aC�iO���Z1H���\�}U��F)���Ͱ!ɴ��B\�^��\�\�\0Èpھ�CL��L\�\�fؐd\�\�C!m�VRdg�aD8m_\�!�QJ&fn3lH2\�顐�W+)�3�0\"���\�\�(%3�6$�\�\�PH۫�\�`N\�Wu�i����\��L{z(�\�ՊA�\�0��\�:\�4J\�\�\�m�\rI�==\�\�j\� EvF�\�\�Ub\Z�db\�6Æ$Ӟ\ni{�b�\";#\�i��1�R21s�aC�iO���Z1H���\�}U��F)���Ͱ!ɴ��B\�^��\�\�\0Èpھ�CL��L\�\�fؐd\�\�C!m�VRdg�aD8m_\�!�QJ&fn3lH2\�顐�W+)�3�0\"���\�\�(%3�6$�\�\�PH۫�\�`N\�Wu�i����\��L{z(�\�ՊA�\�0��\�:\�4J\�\�\�m�\rI�==\�\�j\� EvF�\�\�Ub\Z�db\�6Æ$Ӟ\ni{�b�\";#\�i��1�R21s�aC�iO���Z1H���\�}U��F)���Ͱ!ɴ��B\�^��\�\�\0Èpھ�C?�\�\�\�\'��x�o\���:\�\�i�]p�\�������\�\�\��\�9\r\"\�\�5n\�\"\�F\��H�C���\��v�N>�\�V$ض�!�M?�M}zH�~\�L��C *Q��H�}e\�\�\�l\�\�}�\�Cg�]����Uj9��g��\r\�(l��\��\n�\����}�rǿ5ܦl�\�?+N�ڈ\�\�\�&#T\�\nr���\�\n?v�\�q�NX\"{4��X�م\��\na��\�\�\��C\����_U�L|�\�\�~�u��h��\�\�\�\��BO�\�=�\�/\�\�z�\�\�\��BO��\�|\�+�U\�\�\0\0\0\0IEND�B`�','Eden Cheese',49.00,'165 g','DA',15,'F','2024-06-26 07:18:07','ACT'),(16,'PROD0016','4801668600542',_binary '�PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0�\0\0\0p\0\0\0�.r\0\0\n[IDATx^\�\�j%K��?�\�^s�@\�؃s��\��TE�tt\���z�>�z�\�/\�t�_\�\�z�\�\�\�~��\��BO\�����\�=]\�z�\�/\�t�_\�\�z�\�\�\�~��\��BO\�����\�=]\�z�\�/\�t�_\�\�:}����=g�\�U@�g��\�1�UO�x&\�\'LW:\�S�\�D\�(jh\�\�=&�\�\"τ��	\�\�C�\�u\nr��E\rm�!޶\�$V=Q\�♰�0!\\}萻NAnu���\�3\�\�\��Ī\'�X<\�&��r\�)\�m�n5�y�x\��X\�D�g\�~p\��C\�:�Mԍ��6\�o\�c��(b\�L\�O��>t\�]� ���Q\�\�\�\�m{Lb\�E,�	�	\�Շ�\�\�6Q7�\Z\�<C�m�I�z��\�3a?aB��\�!w��\�&\�FQC�g��\�1�UO�x&\�\'LW:\�S�\�D\�(jh\�\�=&�\�\"τ��	\�\�C�\�u\nr��E\rm�!޶\�$V=Q\�♰�0!\\}萻NAnu���\�3\�\�\��Ī\'�X<\�&��r\�)\�m�n5�y�x\��X\�D�g\�~p\��C\�:�Mԍ��6\�o\�c��(b\�L\�O��>t\�]� ���Q\�\�\�\�m{Lb\�E,�	�	\�Շ�\�\�6Q7�\Z\�<C�m�I�z��\�3a?aB��\�!w��\�&\�FQC�g��\�1�UO�x&\�\'LW:\�S�\�D\�(jh\�\�=&�\�\"τ��	\�\�C�\�u\nr��E\rm�!޶\�$V=Q\�♰�0!\\}萻NAnu���\�3\�\�\��Ī\'�X<\�&��r\�)\�m�n5�y�x\��X\�D�g\�~p\��C\�:�Mԍ��6\�o\�c��(b\�L\�O��>t\�]� ���Q\�\�\�\�m{Lb\�E,�	�	\�Շ�\�\�6Q7�\Z\�<C�m�I�z��\�3a?aB��\�!w��\�&\�FQC�g��\�1�UO�x&\�\'LW:\�S�\�D\�(jh\�\�=&�\�\"τ��	\�\�C�\�u\nr��E\rm�!޶\�$V=Q\�♰�0!\\}萻NAnu���\�3\�\�\��Ī\'�X<\�&��r\�)\�m�n5�y�x\��X\�D�g\�~p\��C\�:�Mԍ��6\�o\�c��(b\�L\�O��>t\�]� ���Q\�\�\�\�m{Lb\�E,�	�	\�Շ�\�\�6Q7�\Z\�<C�m�I�z��\�3a?aB��\�!w��\�&\�FQC�g��\�1�UO�x&\�\'LW:\�S�\�D\�(jh\�\�=&�\�\"τ��	\�\�C�\�u\nr��E\rm�!޶\�$V=Q\�♰�0!\\}萻NAnu���\�3\�\�\��Ī\'�X<\�&��r\�)\�m�n5�y�x\��X\�D�g\�~p\��C\�:�Mԍ��6\�o\�c��(b\�L\�O��>t\�]� ���Q\�\�\�\�m{Lb\�E,�	�	\�Շ�\�\�6Q7�\Z\�<C�m�I�z��\�3a?aB��\�!w��\�&\�FQC�g��\�1�UO�x&\�\'LW:\�S�\�D\�(jh\�\�=&�\�\"τ��	\�\�C�\�u\nr��E\rm�!޶\�$V=Q\�♰�0!\\}萻NAnu���\�3\�\�\��Ī\'�X<\�&��r\�)\�m�n5�y�x\��X\�D�g\�~p\��C\�:�Mԍ��6\�o\�c��(b\�L\�O��>t\�]� ���Q\�\�\�\�m{Lb\�E,�	�	\�Շ�\�\�6Q7�\Z\�<C�m�I�z��\�3a?aB��\�!w��\�&\�FQC�g��\�1�UO�x&\�\'LW:\�S�\�D\�(jh\�\�=&�\�\"τ��	\�\�C�\�u\nr��E\rm�!޶\�$V=Q\�♰�0!\\}萻NAnu���\�3\�\�\��Ī\'�X<\�&��r\�)\�m�n5�y�x\��X\�D�g\�~p\��C\�:�Mԍ��6\�o\�c��(b\�L\�O��>t\�]� ���Q\�\�\�\�m{Lb\�E,�	�	\�Շ�\�\�6Q7�\Z\�<C�m�I�z��\�3a?aB��\�!w��\�&\�FQC�g��\�1�UO�x&\�\'LW:\�S�\�D\�(jh\�\�=&�\�\"τ��	\�\�C�\�u\nr��E\rm�!޶\�$V=Q\�♰�0!\\}萻NAnu���\�3\�\�\��Ī\'�X<\�&��r\�)\�m�n5�y�x\��X\�D�g\�~p\��C\�:�Mԍ��6\�o\�c��(b\�L\�O��>t\�]� ���Q\�\�\�\�m{Lb\�E,�	�	\�Շ�\�\�6Q7�\Z\�<C�m�I�z��\�3a?aB��\�!w��\�&\�FQC�g��\�1�UO�x&\�\'LW:\�S�\�D\�(jh\�\�=&�\�\"τ��	\�\�C�\�u\nr��E\rm�!޶\�$V=Q\�♰�0!\\}萻NAnu���\�3\�\�\��Ī\'�X<\�&��r\�)\�m�n5�y�x\��X\�D�g\�~p\��C\�:�Mԍ��6\�o\�c��(b\�L\�O��>t\�]� ���Q\�\�\�\�m{Lb\�E,�	�	\�Շ�\�\�6Q7�\Z\�<C�m�I�z��\�3a?aB��\�!w��\�&\�FQC�g��\�1�UO�x&\�\'LW:\�S�\�D\�(jh\�\�=&�\�\"τ��	\�\�C�\�u\nr��E\rm�!޶\�$V=Q\�♰�0!\\}萻NAnu���\�3\�\�\��Ī\'�X<\�&��r\�)菷���\�&��?\�\�\�������x�\�/n\�M\�\�?J��~\�\�\�y�\�߾jg�6\�b�\r���G\�Uз?6\r��!\�\\\�\�;$���;$ ��\�u&�L���\�f�nߤW\�\�\�g�\r�M�$^\�f8I\���f�֯\�\�?�\"\�O�σ�Q?�k.\�WWH\�k}�\�\�>yl}mï\�\��\�	}�b\�	y��s\�3}\�ŏ\�6��\�W\�\���\�>��X�^}�C��>\�?\�\� p��}\�\�k��gī\�i�\�\����\�\�=]\�z�\�/\�t�_\�\�z�\�\�\�_�\�:0��B\0\0\0\0IEND�B`�','Datu Puti Patis Pack',11.00,'150 ml','CO',16,'F','2024-06-26 07:20:10','ACT'),(17,'PROD0017','4801668602034',_binary '�PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0�\0\0\0p\0\0\0�.r\0\0	\�IDATx^\�\�j%\�C\��?}/\�f\�B\�qL���)=ldy\�\�\�\��W\�֗��\�=]\�z�\�/\�t�_\�\�z�\�\�\�~��\��BO\�����\�=]\�z�\�/\�t�_\�\�z�\�\�\�~��\��BO\�����\�=]\�z�N_\�\�럟\�\�\�B\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:\�2v(C\�m	�\�0�U��Պ13�5f�\�/:��5?\�_|ɩ\�ׯ���\�KNE�~͏\�_\�M\�\�y��>]S�b��r�o��r��\�𳄹%zESѷ�8&`^c2�\�\�6���\�\�\�Sx\�\�\"��Ᏽ\�\���\�(\rI\�\Z3\�7(\�0�\�?�����J\�\�5�����F�\�9&�I6K��b�w?1\Z-��/�k(\'��r\�rL\��\�&��\r\�3\�{&_��\�}\�\�\�\�k\�s?�\�lې>m~\�)ɟ\�}\��\�׿\Z\�\�\��*�Y\�[��\r?��j\�_\�\��\�\�=]\�z�\�/\�t�_\�\�z�\�\�\�5�	}�+\0\0\0\0IEND�B`�','Datu Puti Soy Sauce',10.00,'200 ml','CO',17,'F','2024-06-26 07:21:23','ACT'),(18,'PROD0018','4801668602027',_binary '�PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0�\0\0\0p\0\0\0�.r\0\0\n#IDATx^\�\�nI��/}�Mc�ڢl��\�a����Z���ϫg\�ËW\�����\�=]\�z�\�/\�t�_\�\�z�\�\�\�~��\��BO\�����\�=]\�z�\�/\�t�_\�\�z�\�\�\�~��\��BO\�����\�=]�/\�\�\�\�?Ϥa�%᱄i3\�(\��6�\�-y}�DK��x�ޠC�$<�0m�E=#Ն��%���h�U�\�t(��\��\�p��g�\�02�\�1\��-�*\�5z�\�\�X´n\��TF\�<�>X��VE�FoС\\K�6Í���j\�\�ܒ\�\�K�Ԫ�\�\�\r:�K\�c	\�f�Q\�3Rm�[\��`��Z\�\Z�A�rIx,a\�7�zF�\r#sKC,\�R�\"^�7\�P.	�%L�\�FQ\�H�adn\�c\�%ZjU\�k\�\�%᱄i3\�(\��6�\�-y}�DK��x�ޠC�$<�0m�E=#Ն��%���h�U�\�t(��\��\�p��g�\�02�\�1\��-�*\�5z�\�\�X´n\��TF\�<�>X��VE�FoС\\K�6Í���j\�\�ܒ\�\�K�Ԫ�\�\�\r:�K\�c	\�f�Q\�3Rm�[\��`��Z\�\Z�A�rIx,a\�7�zF�\r#sKC,\�R�\"^�7\�P.	�%L�\�FQ\�H�adn\�c\�%ZjU\�k\�\�%᱄i3\�(\��6�\�-y}�DK��x�ޠC�$<�0m�E=#Ն��%���h�U�\�t(��\��\�p��g�\�02�\�1\��-�*\�5z�\�\�X´n\��TF\�<�>X��VE�FoС\\K�6Í���j\�\�ܒ\�\�K�Ԫ�\�\�\r:�K\�c	\�f�Q\�3Rm�[\��`��Z\�\Z�A�rIx,a\�7�zF�\r#sKC,\�R�\"^�7\�P.	�%L�\�FQ\�H�adn\�c\�%ZjU\�k\�\�%᱄i3\�(\��6�\�-y}�DK��x�ޠC�$<�0m�E=#Ն��%���h�U�\�t(��\��\�p��g�\�02�\�1\��-�*\�5z�\�\�X´n\��TF\�<�>X��VE�FoС\\K�6Í���j\�\�ܒ\�\�K�Ԫ�\�\�\r:�K\�c	\�f�Q\�3Rm�[\��`��Z\�\Z�A�rIx,a\�7�zF�\r#sKC,\�R�\"^�7\�P.	�%L�\�FQ\�H�adn\�c\�%ZjU\�k\�\�%᱄i3\�(\��6�\�-y}�DK��x�ޠC�$<�0m�E=#Ն��%���h�U�\�t(��\��\�p��g�\�02�\�1\��-�*\�5z�\�\�X´n\��TF\�<�>X��VE�FoС\\K�6Í���j\�\�ܒ\�\�K�Ԫ�\�\�\r:�K\�c	\�f�Q\�3Rm�[\��`��Z\�\Z�A�rIx,a\�7�zF�\r#sKC,\�R�\"^�7\�P.	�%L�\�FQ\�H�adn\�c\�%ZjU\�k\�\�%᱄i3\�(\��6�\�-y}�DK��x�ޠC�$<�0m�E=#Ն��%���h�U�\�t(��\��\�p��g�\�02�\�1\��-�*\�5z�\�\�X´n\��TF\�<�>X��VE�FoС\\K�6Í���j\�\�ܒ\�\�K�Ԫ�\�\�\r:�K\�c	\�f�Q\�3Rm�[\��`��Z\�\Z�A�rIx,a\�7�zF�\r#sKC,\�R�\"^�7\�P.	�%L�\�FQ\�H�adn\�c\�%ZjU\�k\�\�%᱄i3\�(\��6�\�-y}�DK��x�ޠC�$<�0m�E=#Ն��%���h�U�\�t(��\��\�p��g�\�02�\�1\��-�*\�5z�\�\�X´n\��TF\�<�>X��VE�FoС\\K�6Í���j\�\�ܒ\�\�K�Ԫ�\�\�\r:�K\�c	\�f�Q\�3Rm�[\��`��Z\�\Z�A�rIx,a\�7�zF�\r#sKC,\�R�\"^�7\�P.	�%L�\�FQ\�H�adn\�c\�%ZjU\�k\�\�%᱄i3\�(\��6�\�-y}�DK��x�ޠC�$<�0m�E=#Ն��%���h�U�\�t(��\��\�p��g�\�02�\�1\��-�*\�5z�\�\�X´n\��TF\�<�>X��VE�FoС\\K�6Í���j\�\�ܒ\�\�K�Ԫ�\�\�\r:�K\�c	\�f�Q\�3Rm�[\��`��Z\�\Z�A�rIx,a\�7�zF�\r#sKC,\�R�\"^�7\�P.	�%L�\�FQ\�H�adn\�c\�%ZjU\�k\�\�%᱄i3\�(\��6�\�-y}�DK��x�ޠC�$<�0m�E=#Ն��%���h�U�\�t(��\��\�p��g�\�02�\�1\��-�*\�5z�\�\�X´n\��TF\�<�>X��VE�FoС\\K�6Í���j\�\�ܒ\�\�K�Ԫ�\�\�\r:�K\�c	\�f�Q\�3Rm�[\��`��Z\�\Z�A�rIx,a\�7�zF�\r#sKC,\�R�\"^�7\�P.	�%L�\�FQ\�H�adn\�c\�%ZjU\�k\�\�%᱄i3\�(\��6�\�-y}�DK��x�ޠC�$<�0m�E=#Ն��%���h�U�\�t(��\��\�p��g�\�02�\�1\��-�*\�5z�\�\�X´n\��TF\�<�>X��VE��\�_|ɪ\�_\�c��/Y�\�k~�\�%\�}���\�kο\�7\�\���z�}�\�\�6�`��\\&\��#��\�\�?6�ـ|~6\�;��\�j\�\�\�lz�c}Z��\�\��\�\�\���\�\�1\��\�\�\�o\�\�w�\�~f\��k.��\�\�k~�����dϟ\�\�\�6G�O\���\�\�\�aƟ\\�\�\�4��O��L�\�\�+\�\�>�����~�K�\��\��\�r�~��c\�5��\�\�\�f~g��㒾7Z\�\�g̸9\�?�\�\�E?�c\�M\�\�\���\�|�\�\�\�q�5s\��Z��?\�\�\�z�\�\�\�~��\��BO\�����\�=]�h�n�\�^\�\0\0\0\0IEND�B`�','Datu Puti Vinegar',9.00,'200 ml','CO',18,'F','2024-06-26 07:22:25','ACT'),(19,'PROD0019','4800024013477',_binary '�PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0�\0\0\0p\0\0\0�.r\0\0\nwIDATx^\�\�jmk\n\��/=ýr��4&\�̰8��!\�\�\�\���ϫg\�\�\�\�\�\�~��\��BO\�����\�=]\�z�\�/\�t�_\�\�z�\�\�\�~��\��BO\�����\�=]\�z�\�/\�t�_\�\�z�\�\�\�~��\��BO\�\�}|���ʆ#AzFH\"H�j��\\\�(\",Wdۡ�/:\�\'�ѡ�$\�D�N\�L��QDX�ȶC%_t\n\�OJ�C	I\�!� ���\nr=���<\\�m�J�\�ԟ�F��\�3BA:U3\�zFay�\"\��|\�)�?)�%$�g�$�t�f*\�\��\"\�\�pE�*��SPR\ZJHB\�I\�T\�T�\�E�\�\�l;T\�E����4:�����ҩ�� \�3�\�\�\�v�\�NA�Iit(!	=#$�S5SA�g��+�\�P\�����\�\�PBzFH\"H�j��\\\�(\",Wdۡ�/:\�\'�ѡ�$\�D�N\�L��QDX�ȶC%_t\n\�OJ�C	I\�!� ���\nr=���<\\�m�J�\�ԟ�F��\�3BA:U3\�zFay�\"\��|\�)�?)�%$�g�$�t�f*\�\��\"\�\�pE�*��SPR\ZJHB\�I\�T\�T�\�E�\�\�l;T\�E����4:�����ҩ�� \�3�\�\�\�v�\�NA�Iit(!	=#$�S5SA�g��+�\�P\�����\�\�PBzFH\"H�j��\\\�(\",Wdۡ�/:\�\'�ѡ�$\�D�N\�L��QDX�ȶC%_t\n\�OJ�C	I\�!� ���\nr=���<\\�m�J�\�ԟ�F��\�3BA:U3\�zFay�\"\��|\�)�?)�%$�g�$�t�f*\�\��\"\�\�pE�*��SPR\ZJHB\�I\�T\�T�\�E�\�\�l;T\�E����4:�����ҩ�� \�3�\�\�\�v�\�NA�Iit(!	=#$�S5SA�g��+�\�P\�����\�\�PBzFH\"H�j��\\\�(\",Wdۡ�/:\�\'�ѡ�$\�D�N\�L��QDX�ȶC%_t\n\�OJ�C	I\�!� ���\nr=���<\\�m�J�\�ԟ�F��\�3BA:U3\�zFay�\"\��|\�)�?)�%$�g�$�t�f*\�\��\"\�\�pE�*��SPR\ZJHB\�I\�T\�T�\�E�\�\�l;T\�E����4:�����ҩ�� \�3�\�\�\�v�\�NA�Iit(!	=#$�S5SA�g��+�\�P\�����\�\�PBzFH\"H�j��\\\�(\",Wdۡ�/:\�\'�ѡ�$\�D�N\�L��QDX�ȶC%_t\n\�OJ�C	I\�!� ���\nr=���<\\�m�J�\�ԟ�F��\�3BA:U3\�zFay�\"\��|\�)�?)�%$�g�$�t�f*\�\��\"\�\�pE�*��SPR\ZJHB\�I\�T\�T�\�E�\�\�l;T\�E����4:�����ҩ�� \�3�\�\�\�v�\�NA�Iit(!	=#$�S5SA�g��+�\�P\�����\�\�PBzFH\"H�j��\\\�(\",Wdۡ�/:\�\'�ѡ�$\�D�N\�L��QDX�ȶC%_t\n\�OJ�C	I\�!� ���\nr=���<\\�m�J�\�ԟ�F��\�3BA:U3\�zFay�\"\��|\�)�?)�%$�g�$�t�f*\�\��\"\�\�pE�*��SPR\ZJHB\�I\�T\�T�\�E�\�\�l;T\�E����4:�����ҩ�� \�3�\�\�\�v�\�NA�Iit(!	=#$�S5SA�g��+�\�P\�����\�\�PBzFH\"H�j��\\\�(\",Wdۡ�/:\�\'�ѡ�$\�D�N\�L��QDX�ȶC%_t\n\�OJ�C	I\�!� ���\nr=���<\\�m�J�\�ԟ�F��\�3BA:U3\�zFay�\"\��|\�)�?)�%$�g�$�t�f*\�\��\"\�\�pE�*��SPR\ZJHB\�I\�T\�T�\�E�\�\�l;T\�E����4:�����ҩ�� \�3�\�\�\�v�\�NA�Iit(!	=#$�S5SA�g��+�\�P\�����\�\�PBzFH\"H�j��\\\�(\",Wdۡ�/:\�\'�ѡ�$\�D�N\�L��QDX�ȶC%_t\n\�OJ�C	I\�!� ���\nr=���<\\�m�J�\�ԟ�F��\�3BA:U3\�zFay�\"\��|\�)�?)�%$�g�$�t�f*\�\��\"\�\�pE�*��SPR\ZJHB\�I\�T\�T�\�E�\�\�l;T\�E����4:�����ҩ�� \�3�\�\�\�v�\�NA�Iit(!	=#$�S5SA�g��+�\�P\�����\�\�PBzFH\"H�j��\\\�(\",Wdۡ�/:\�\'�ѡ�$\�D�N\�L��QDX�ȶC%_t\n\�OJ�C	I\�!� ���\nr=���<\\�m�J�\�ԟ�F��\�3BA:U3\�zFay�\"\��|\�)�?)�%$�g�$�t�f*\�\��\"\�\�pE�*��SPR\ZJHB\�I\�T\�T�\�E�\�\�l;T\�E����4:�����ҩ�� \�3�\�\�\�v�\�NA�Iit(!	=#$�S5SA�g��+�\�P\�����\�\�PBzFH\"H�j��\\\�(\",Wdۡ�/:\�\'�ѡ�$\�D�N\�L��QDX�ȶC%_t\n\�OJ�C	I\�!� ���\nr=���<\\�m�J�\�ԟ�F��\�3BA:U3\�zFay�\"\��|\�)\�ׯ���\�KNA�~͏\�_r\n�\�k~����/�>��\�}vMm��!I\�\�\�\�?\��a!\�J�mA_�\�h\�\�n��Ϧ;��\\<\"�\�B:��>\r\Z\� �\�\�\��>��1㟎P�\�,\�\�%p\'��Xs\����Ϯ����\�蛤��\�3\�ޏ?����$p�LsP]L\�\�kʟ\�\�}2\��l$����_\�\�$p�L_-6^ß\�\�}\"(�:\�\�\�\�\�!YF\�x\�E_�\�\�>�\�B\�0\�d\�w\�?\�\�HM\�\��H���y\�c�_\�\�z�\�\�\�~��\��BO\�������<J�i	�\0\0\0\0IEND�B`�','DM Original Ketchup Pack',35.00,'320 g','CO',19,'F','2024-06-26 07:25:15','ACT'),(20,'PROD0020','4801668600641',_binary '�PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0�\0\0\0p\0\0\0�.r\0\0	\�IDATx^\�\�j5\�B\��/}3ad�V\����\�2n\�\�\��\�Ɨ/�\����\����\����\����\����\����\����\����\����\����\����\����\����\����\����\����\����\����\�}}�\�\�b\n�\�d��\�:vJ�V�j��)��|�U�m���4�%�f�����U��baʧ&�`Ud�(l(M�k	���c�d�j���X�\�\�\'X\�&\nJ�\�Z\�kf\�\�)Y�Ze�)�|j\�	VE��\�d��\�:vJ�V�j��)��|�U�m���4�%�f�����U��baʧ&�`Ud�(l(M�k	���c�d�j���X�\�\�\'X\�&\nJ�\�Z\�kf\�\�)Y�Ze�)�|j\�	VE��\�d��\�:vJ�V�j��)��|�U�m���4�%�f�����U��baʧ&�`Ud�(l(M�k	���c�d�j���X�\�\�\'X\�&\nJ�\�Z\�kf\�\�)Y�Ze�)�|j\�	VE��\�d��\�:vJ�V�j��)��|�U�m���4�%�f�����U��baʧ&�`Ud�(l(M�k	���c�d�j���X�\�\�\'X\�&\nJ�\�Z\�kf\�\�)Y�Ze�)�|j\�	VE��\�d��\�:vJ�V�j��)��|�U�m���4�%�f�����U��baʧ&�`Ud�(l(M�k	���c�d�j���X�\�\�\'X\�&\nJ�\�Z\�kf\�\�)Y�Ze�)�|j\�	VE��\�d��\�:vJ�V�j��)��|�U�m���4�%�f�����U��baʧ&�`Ud�(l(M�k	���c�d�j���X�\�\�\'X\�&\nJ�\�Z\�kf\�\�)Y�Ze�)�|j\�	VE��\�d��\�:vJ�V�j��)��|�U�m���4�%�f�����U��baʧ&�`Ud�(l(M�k	���c�d�j���X�\�\�\'X\�&\nJ�\�Z\�kf\�\�)Y�Ze�)�|j\�	VE��\�d��\�:vJ�V�j��)��|�U�m���4�%�f�����U��baʧ&�`Ud�(l(M�k	���c�d�j���X�\�\�\'X\�&\nJ�\�Z\�kf\�\�)Y�Ze�)�|j\�	VE��\�d��\�:vJ�V�j��)��|�U�m���4�%�f�����U��baʧ&�`Ud�(l(M�k	���c�d�j���X�\�\�\'X\�&\nJ�\�Z\�kf\�\�)Y�Ze�)�|j\�	VE��\�d��\�:vJ�V�j��)��|�U�m���4�%�f�����U��baʧ&�`Ud�(l(M�k	���c�d�j���X�\�\�\'X\�&\nJ�\�Z\�kf\�\�)Y�Ze�)�|j\�	VE��\�d��\�:vJ�V�j��)��|�U�m���4�%�f�����U��baʧ&�`Ud�(l(M�k	���c�d�j���X�\�\�\'X\�&\nJ�\�Z\�kf\�\�)Y�Ze�)�|j\�	VE��\�d��\�:vJ�V�j��)��|�U�m���4�%�f�����U��baʧ&�`Ud�(l(M�k	���c�d�j���X�\�\�\'X\�&\nJ�\�Z\�kf\�\�)Y�Ze�)�|j\�	VE��\�d��\�:vJ�V�j��)��|�U�m���4�%�f�����U��baʧ&�`Ud�(l(M�k	���c�d�j���X�\�\�\'X\�&\nJ�\�Z\�kf\�\�)Y�Ze�)�|j\�	VE��\�d��\�:vJ�V�j��)��|�U�m���4�%�f�����U��baʧ&�`Ud�(l(M�k	���c�d�j���X�\�\�\'X\�&\nJ�\�Z\�kf\�\�)Y�Ze�)�|j\�	VE��\�d��\�:vJ�V�j��)��|�U�m���4�%�f�����U��baʧ&�`Ud�(l(M�k	���c�d�j���X�\�\�\'X\�&\nJ�\�Z\�kf\�\�)Y�Ze�)�|j\�	VE��\�d��\�:vJ�V�j��)��|�U�m���4�%�f�����U��baʧ&�`Ud�(l(M�k	���c�d�j���X�\�\�\'X\�&\nJ�\�Z\�kf\�\�)Y�Ze�)�|j\�	VE��\�d��\�:vJ�V�j��)��|�U�m���4�%�f�����U��baʧ&�`Ud�(l(M�k	���c�d�j���X�\�\�\'X\�&\nJ�\�Z\�kf\�\�)Y�Ze�)�|j\�	VE��\�d��\�:vJ�V�j��)��|�U�m���4�%�f�����U��baʧ&�`Ud�(l(M�k	���c�d�j���X�\�\�\'X\�&\nJ�\�Z\�kf\�\�)Y�Ze�)�|j\�	VE��\�d��\�:vJ�V�j��)��|�U�m���4�%�f�����U��baʧ&�`Ud�(l(M�k	���c�d�j���X�\�\�\'X\�&\nJ�\�Z\�kf\�\�)Y�Ze�)�|j\�	VE\�k>\�xɪ\�|\�\��/Y����\�%�}�\rw���\���\�#��׿JG�\��_�ġo?\��1~*�\�\�j��0�\�\���c�դ�\��\�j��m�O0\�7~���\�\������\�o�\�|\��L\�׆\�\�_1f�����ы\�Ĵ\�\��+\��\�L:߂\�B��}f`\�L�r\���\�+\���\�\�ە���\�>�I��c�\0\�\��1~/�\�\�\��\�\�\�\���c�դ�\�!~�	:\��ߋ>���\��#��׿J\�gd�3��>�\r�\�?\�b�\����\����\����\����\�����^Vz\�!p\0\0\0\0IEND�B`�','Mang Tomas Sachet',10.00,'100 g','CO',20,'F','2024-06-26 07:27:09','ACT');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report_type`
--

DROP TABLE IF EXISTS `report_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report_type` (
  `report_type_id` int NOT NULL AUTO_INCREMENT,
  `report_type` varchar(100) NOT NULL,
  PRIMARY KEY (`report_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report_type`
--

LOCK TABLES `report_type` WRITE;
/*!40000 ALTER TABLE `report_type` DISABLE KEYS */;
INSERT INTO `report_type` VALUES (1,'Sales Report'),(2,'Inventory Report'),(3,'Return Report');
/*!40000 ALTER TABLE `report_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reports`
--

DROP TABLE IF EXISTS `reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reports` (
  `report_id` int NOT NULL AUTO_INCREMENT,
  `report_type_id` int DEFAULT NULL,
  `report_date` date DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`report_id`),
  KEY `report_type_id` (`report_type_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `reports_ibfk_1` FOREIGN KEY (`report_type_id`) REFERENCES `report_type` (`report_type_id`),
  CONSTRAINT `reports_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reports`
--

LOCK TABLES `reports` WRITE;
/*!40000 ALTER TABLE `reports` DISABLE KEYS */;
/*!40000 ALTER TABLE `reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `return_products`
--

DROP TABLE IF EXISTS `return_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `return_products` (
  `return_id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `return_quantity` int NOT NULL,
  `return_reason_id` char(3) NOT NULL,
  `return_date` date NOT NULL,
  `return_status_id` char(3) NOT NULL,
  PRIMARY KEY (`return_id`),
  KEY `product_id` (`product_id`),
  KEY `return_reason_id` (`return_reason_id`),
  KEY `return_status_id` (`return_status_id`),
  CONSTRAINT `return_products_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `return_products_ibfk_2` FOREIGN KEY (`return_reason_id`) REFERENCES `return_reason` (`return_reason_id`),
  CONSTRAINT `return_products_ibfk_3` FOREIGN KEY (`return_status_id`) REFERENCES `return_status` (`return_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `return_products`
--

LOCK TABLES `return_products` WRITE;
/*!40000 ALTER TABLE `return_products` DISABLE KEYS */;
/*!40000 ALTER TABLE `return_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `return_reason`
--

DROP TABLE IF EXISTS `return_reason`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `return_reason` (
  `return_reason_id` char(3) NOT NULL,
  `return_reason_name` varchar(50) NOT NULL,
  PRIMARY KEY (`return_reason_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `return_reason`
--

LOCK TABLES `return_reason` WRITE;
/*!40000 ALTER TABLE `return_reason` DISABLE KEYS */;
INSERT INTO `return_reason` VALUES ('DEF','Defective Product'),('EXP','Expired Product'),('WRO','Wrong Product');
/*!40000 ALTER TABLE `return_reason` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `return_status`
--

DROP TABLE IF EXISTS `return_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `return_status` (
  `return_status_id` char(3) NOT NULL,
  `return_status_name` varchar(50) NOT NULL,
  PRIMARY KEY (`return_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `return_status`
--

LOCK TABLES `return_status` WRITE;
/*!40000 ALTER TABLE `return_status` DISABLE KEYS */;
INSERT INTO `return_status` VALUES ('CAN','Cancelled'),('COM','Completed'),('PRO','Processing'),('REJ','Rejected');
/*!40000 ALTER TABLE `return_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales` (
  `sales_id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `sales_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int NOT NULL,
  PRIMARY KEY (`sales_id`),
  KEY `product_id` (`product_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `sales_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `sales_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales`
--

LOCK TABLES `sales` WRITE;
/*!40000 ALTER TABLE `sales` DISABLE KEYS */;
/*!40000 ALTER TABLE `sales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales_summary`
--

DROP TABLE IF EXISTS `sales_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales_summary` (
  `sale_date` date NOT NULL,
  `hours_open` int NOT NULL,
  `hours_closed` int NOT NULL,
  `products_sold` int NOT NULL,
  `tax` decimal(10,2) NOT NULL,
  `return_refund` decimal(10,2) NOT NULL,
  `total_sales` decimal(10,2) NOT NULL,
  PRIMARY KEY (`sale_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales_summary`
--

LOCK TABLES `sales_summary` WRITE;
/*!40000 ALTER TABLE `sales_summary` DISABLE KEYS */;
/*!40000 ALTER TABLE `sales_summary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `security_answer`
--

DROP TABLE IF EXISTS `security_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_answer` (
  `security_answer_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `security_question_id` int DEFAULT NULL,
  `security_answer_hash` varchar(255) NOT NULL,
  PRIMARY KEY (`security_answer_id`),
  KEY `user_id` (`user_id`),
  KEY `security_question_id` (`security_question_id`),
  CONSTRAINT `security_answer_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `security_answer_ibfk_2` FOREIGN KEY (`security_question_id`) REFERENCES `security_question` (`security_question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_answer`
--

LOCK TABLES `security_answer` WRITE;
/*!40000 ALTER TABLE `security_answer` DISABLE KEYS */;
INSERT INTO `security_answer` VALUES (1,1,3,'02cb49196bfa84bf66fee252429232977586ec602c16adbfa56e142c5888a134'),(2,2,2,'fb4f0b0aa4676052e4636c78b30793d539a4b99bd85823807b87c9c339522d7f');
/*!40000 ALTER TABLE `security_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `security_question`
--

DROP TABLE IF EXISTS `security_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_question` (
  `security_question_id` int NOT NULL AUTO_INCREMENT,
  `security_question` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`security_question_id`),
  UNIQUE KEY `security_question` (`security_question`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_question`
--

LOCK TABLES `security_question` WRITE;
/*!40000 ALTER TABLE `security_question` DISABLE KEYS */;
INSERT INTO `security_question` VALUES (3,'In what city were you born?'),(5,'In what year did you graduate from high school?'),(2,'What is the name of your first pet?'),(4,'What is your favorite movie?'),(1,'What is your mother\'s maiden name?'),(6,'What was the name of your first school?');
/*!40000 ALTER TABLE `security_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `supplier_id` int NOT NULL AUTO_INCREMENT,
  `supplier_name` varchar(255) NOT NULL,
  PRIMARY KEY (`supplier_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (1,'555 - Century Pacific Food'),(2,'555 - Century Pacific Food'),(3,'Argentina'),(4,'Argentina'),(5,'Argentina'),(6,'Argentina'),(7,'CDO'),(8,'Century'),(9,'CDO'),(10,'Century'),(11,'Angel'),(12,'Birchtree'),(13,'Kraft'),(14,'Magnolia'),(15,'Mondelez'),(16,'Datu Puti'),(17,'Datu Puti'),(18,'Datu Puti'),(19,'Del Monte'),(20,'NutriAsia');
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions` (
  `transaction_id` int NOT NULL AUTO_INCREMENT,
  `receipt_number` varchar(50) DEFAULT NULL,
  `reference_number` varchar(50) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `time` time DEFAULT NULL,
  `subtotal` double DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `vat` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `fk_product_id` (`product_id`),
  CONSTRAINT `fk_product_id` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (1,'BF2A0006','33047021','2024-06-26','15:48:10',118,0,14.16,132.16,NULL),(2,'801DFC5F','54603066','2024-06-26','16:31:33',86,0,10.32,96.32,NULL);
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account_status`
--

DROP TABLE IF EXISTS `user_account_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_account_status` (
  `user_account_status_id` char(3) NOT NULL,
  `account_status` varchar(50) NOT NULL,
  PRIMARY KEY (`user_account_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account_status`
--

LOCK TABLES `user_account_status` WRITE;
/*!40000 ALTER TABLE `user_account_status` DISABLE KEYS */;
INSERT INTO `user_account_status` VALUES ('ACT','Active'),('INA','Inactive');
/*!40000 ALTER TABLE `user_account_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_level_of_access`
--

DROP TABLE IF EXISTS `user_level_of_access`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_level_of_access` (
  `user_role_id` char(1) NOT NULL,
  `user_role_name` varchar(50) NOT NULL,
  PRIMARY KEY (`user_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_level_of_access`
--

LOCK TABLES `user_level_of_access` WRITE;
/*!40000 ALTER TABLE `user_level_of_access` DISABLE KEYS */;
INSERT INTO `user_level_of_access` VALUES ('A','Admin'),('C','Cashier');
/*!40000 ALTER TABLE `user_level_of_access` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_logs`
--

DROP TABLE IF EXISTS `user_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_logs` (
  `user_log_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `user_action` varchar(100) DEFAULT NULL,
  `action_timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `action_timeout_timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_log_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_logs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_logs`
--

LOCK TABLES `user_logs` WRITE;
/*!40000 ALTER TABLE `user_logs` DISABLE KEYS */;
INSERT INTO `user_logs` VALUES (1,1,'User registered','2024-06-26 06:45:16','2024-06-26 06:45:16'),(2,2,'User registered','2024-06-26 06:46:06','2024-06-26 06:46:06'),(3,2,'User logged in','2024-06-26 06:46:43','2024-06-26 06:46:43'),(4,1,'User logged in','2024-06-26 06:46:52','2024-06-26 06:46:52'),(5,2,'User logged in','2024-06-26 07:42:59','2024-06-26 07:42:59'),(6,1,'User logged in','2024-06-26 07:51:05','2024-06-26 07:51:05'),(7,1,'User logged in','2024-06-26 08:03:21','2024-06-26 08:03:21'),(8,1,'User logged in','2024-06-26 08:07:57','2024-06-26 08:07:57'),(9,1,'User logged in','2024-06-26 08:12:33','2024-06-26 08:12:33'),(10,2,'User login failed','2024-06-26 08:16:24','2024-06-26 08:16:24'),(11,2,'User logged in','2024-06-26 08:16:28','2024-06-26 08:16:28'),(12,1,'User logged in','2024-06-26 08:30:30','2024-06-26 08:30:30'),(13,2,'User logged in','2024-06-26 08:30:47','2024-06-26 08:30:47'),(14,1,'User logged in','2024-06-26 08:32:21','2024-06-26 08:32:21'),(15,1,'User logged in','2024-06-26 08:35:53','2024-06-26 08:35:53'),(16,1,'User logged in','2024-06-26 08:37:57','2024-06-26 08:37:57'),(17,1,'User logged in','2024-06-26 08:46:30','2024-06-26 08:46:30'),(18,1,'User logged in','2024-06-26 08:47:09','2024-06-26 08:47:09'),(19,1,'User logged in','2024-06-26 08:48:02','2024-06-26 08:48:02'),(20,1,'User logged in','2024-06-26 08:55:00','2024-06-26 08:55:00'),(21,1,'User logged in','2024-06-26 09:00:30','2024-06-26 09:00:30'),(22,1,'User logged in','2024-06-26 09:01:04','2024-06-26 09:01:04'),(23,1,'User logged in','2024-06-26 09:04:49','2024-06-26 09:04:49'),(24,1,'User logged in','2024-06-26 09:08:35','2024-06-26 09:08:35'),(25,1,'User logged in','2024-06-26 09:09:03','2024-06-26 09:09:03'),(26,1,'User logged in','2024-06-26 09:09:29','2024-06-26 09:09:29'),(27,1,'User login failed','2024-06-26 09:10:56','2024-06-26 09:10:56'),(28,1,'User login failed','2024-06-26 09:11:01','2024-06-26 09:11:01'),(29,1,'User logged in','2024-06-26 09:11:05','2024-06-26 09:11:05'),(30,1,'User logged in','2024-06-26 09:34:26','2024-06-26 09:34:26'),(31,1,'User logged in','2024-06-26 09:35:06','2024-06-26 09:35:06'),(32,1,'User logged in','2024-06-26 10:02:13','2024-06-26 10:02:13'),(33,2,'User logged in','2024-06-26 11:18:31','2024-06-26 11:18:31'),(34,1,'User logged in','2024-06-28 00:16:25','2024-06-28 00:16:25'),(35,1,'User logged in','2024-06-28 01:02:57','2024-06-28 01:02:57'),(36,1,'User logged in','2024-06-28 01:26:07','2024-06-28 01:26:07'),(37,1,'User logged in','2024-06-28 01:37:29','2024-06-28 01:37:29'),(38,1,'User logged in','2024-06-28 01:43:03','2024-06-28 01:43:03'),(39,1,'User logged in','2024-06-28 01:47:43','2024-06-28 01:47:43'),(40,1,'User login failed','2024-06-28 02:03:33','2024-06-28 02:03:33'),(41,1,'User logged in','2024-06-28 02:03:39','2024-06-28 02:03:39');
/*!40000 ALTER TABLE `user_logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `unique_user_id` varchar(20) NOT NULL,
  `user_role_id` char(1) DEFAULT NULL,
  `user_first_name` varchar(50) NOT NULL,
  `user_last_name` varchar(50) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password_hash` varchar(255) NOT NULL,
  `user_account_status_id` char(3) DEFAULT 'ACT',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `unique_user_id` (`unique_user_id`),
  UNIQUE KEY `username` (`username`),
  KEY `user_role_id` (`user_role_id`),
  KEY `user_account_status_id` (`user_account_status_id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`user_role_id`) REFERENCES `user_level_of_access` (`user_role_id`),
  CONSTRAINT `users_ibfk_2` FOREIGN KEY (`user_account_status_id`) REFERENCES `user_account_status` (`user_account_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'ADM-0001','A','Charimel','Mariano','chrmlmrn','855ef7c180aa5f7c085b3a3b099977dc95b80e6708cf587a7dcdce1ef5634e7d','ACT','2024-06-26 06:45:15'),(2,'CAS-0002','C','Mark','Regis','rinkashime','02be079b5342c8f4f4a22933ff3266b9993abe330519036ca5783c83f0b2d619','ACT','2024-06-26 06:46:06');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'lavega_store_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-28 10:04:52
