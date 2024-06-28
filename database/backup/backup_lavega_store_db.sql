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
INSERT INTO `inventory` VALUES (1,1,75,15,'2024-06-28 07:26:28','INS'),(2,2,75,15,'2024-06-28 07:22:49','INS'),(3,3,50,15,'2024-06-28 00:18:07','INS'),(4,4,42,15,'2024-06-28 03:46:36','INS'),(5,5,30,10,'2024-06-26 06:55:35','INS'),(6,6,34,10,'2024-06-28 03:46:36','INS'),(7,7,40,15,'2024-06-26 06:58:13','INS'),(8,8,30,10,'2024-06-26 07:00:16','INS'),(9,9,39,15,'2024-06-28 07:10:03','INS'),(10,10,30,10,'2024-06-26 07:02:35','INS'),(11,11,19,10,'2024-06-28 06:49:29','INS'),(12,12,39,15,'2024-06-28 07:10:03','INS'),(13,13,40,15,'2024-06-26 07:12:22','INS'),(14,14,30,15,'2024-06-26 07:14:52','INS'),(15,15,30,15,'2024-06-26 07:18:07','INS'),(16,16,40,15,'2024-06-26 07:20:10','INS'),(17,17,38,15,'2024-06-26 07:48:10','INS'),(18,18,39,15,'2024-06-26 07:48:10','INS'),(19,19,19,15,'2024-06-26 07:48:10','INS'),(20,20,29,15,'2024-06-26 07:48:10','INS');
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
INSERT INTO `products` VALUES (1,'PROD0001','7484858019194',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0‹\0\0\0p\0\0\0¡.r\0\0	IDATx^\í\Ñ\Ñn-\É\rCQÿÿO\'”kDùÜš’4­C\"w\ñ\Ğ\è¯½\ó\ìù\ê\Â;›\÷=}\Ş/\ô\ôy¿\Ğ\Ó\çıBOŸ\÷=}\Ş/\ô\ôy¿\Ğ\Ó\çıBOŸ\÷=}\Ş/\ô\ôy¿\Ğ\Ó\çıBOŸ\÷=}\Ş/\ô\ôy¿\Ğ\Ó\çıBOŸ\÷=}\Ş/\ô\ôY}¡¯¯ÿ<\÷\ï·\ŞN—¿E®»™¾J@%\ÉtO\Èf)Ö¹ŸUm\ìdc­rt\İ\Í\ôU*I¦{B6K±\Îı¬‚lc\'k]£\ën¦¯PI2\İ²YŠu\îgd;\ÙX\ë‚]w3}•€J’é\ÍR¬s?« \Û\Ø\É\ÆZ\äèº›\é«T’L\÷„l–bûY\Ù\ÆN6Öº G\×\İL_% ’dº\'d³\ë\Ü\Ï*\È6v²±\Ö9º\îfú*•$\Ó=!›¥X\ç~VA¶±“µ.\È\Ñu7\ÓW	¨$™\î	\Ù,\Å:\÷³\n²l¬uA®»™¾J@%\ÉtO\Èf)Ö¹ŸUm\ìdc­rt\İ\Í\ôU*I¦{B6K±\Îı¬‚lc\'k]£\ën¦¯PI2\İ²YŠu\îgd;\ÙX\ë‚]w3}•€J’é\ÍR¬s?« \Û\Ø\É\ÆZ\äèº›\é«T’L\÷„l–bûY\Ù\ÆN6Öº G\×\İL_% ’dº\'d³\ë\Ü\Ï*\È6v²±\Ö9º\îfú*•$\Ó=!›¥X\ç~VA¶±“µ.\È\Ñu7\ÓW	¨$™\î	\Ù,\Å:\÷³\n²l¬uA®»™¾J@%\ÉtO\Èf)Ö¹ŸUm\ìdc­rt\İ\Í\ôU*I¦{B6K±\Îı¬‚lc\'k]£\ën¦¯PI2\İ²YŠu\îgd;\ÙX\ë‚]w3}•€J’é\ÍR¬s?« \Û\Ø\É\ÆZ\äèº›\é«T’L\÷„l–bûY\Ù\ÆN6Öº G\×\İL_% ’dº\'d³\ë\Ü\Ï*\È6v²±\Ö9º\îfú*•$\Ó=!›¥X\ç~VA¶±“µ.\È\Ñu7\ÓW	¨$™\î	\Ù,\Å:\÷³\n²l¬uA®»™¾J@%\ÉtO\Èf)Ö¹ŸUm\ìdc­rt\İ\Í\ôU*I¦{B6K±\Îı¬‚lc\'k]£\ën¦¯PI2\İ²YŠu\îgd;\ÙX\ë‚]w3}•€J’é\ÍR¬s?« \Û\Ø\É\ÆZ\äèº›\é«T’L\÷„l–bûY\Ù\ÆN6Öº G\×\İL_% ’dº\'d³\ë\Ü\Ï*\È6v²±\Ö9º\îfú*•$\Ó=!›¥X\ç~VA¶±“µ.\È\Ñu7\ÓW	¨$™\î	\Ù,\Å:\÷³\n²l¬uA®»™¾J@%\ÉtO\Èf)Ö¹ŸUm\ìdc­rt\İ\Í\ôU*I¦{B6K±\Îı¬‚lc\'k]£\ën¦¯PI2\İ²YŠu\îgd;\ÙX\ë‚]w3}•€J’é\ÍR¬s?« \Û\Ø\É\ÆZ\äèº›\é«T’L\÷„l–bûY\Ù\ÆN6Öº G\×\İL_% ’dº\'d³\ë\Ü\Ï*\È6v²±\Ö9º\îfú*•$\Ó=!›¥X\ç~VA¶±“µ.\È\Ñu7\ÓW	¨$™\î	\Ù,\Å:\÷³\n²l¬uA®»™¾J@%\ÉtO\Èf)Ö¹ŸUm\ìdc­rt\İ\Í\ôU*I¦{B6K±\Îı¬‚lc\'k]£\ën¦¯PI2\İ²YŠu\îgd;\ÙX\ë‚]w3}•€J’é\ÍR¬s?« \Û\Ø\É\ÆZ\äèº›\é«T’L\÷„l–bûY\Ù\ÆN6Öº G\×\İL_% ’dº\'d³\ë\Ü\Ï*\È6v²±\Ö9º\îfú*•$\Ó=!›¥X\ç~VA¶±“µ.\È\Ñu7\ÓW	¨$™\î	\Ù,\Å:\÷³\n²l¬uA®»™¾J@%\ÉtO\Èf)Ö¹ŸUm\ìdc­rt\İ\Í\ôU*I¦{B6K±\Îı¬‚lc\'k]£\ën¦¯PI2\İ²YŠu\îgd;\ÙX\ë‚]w3}•€J’é\ÍR¬s?« \Û\Ø\É\ÆZ\äèº›\é«T’L\÷„l–bûY\Ù\ÆN6Öº G\×\İL_% ’dº\'d³\ë\Ü\Ï*\È6v²±\Ö9º\îfú*•$\Ó=!›¥X\ç~VA¶±“µ.\È\Ñu7\ÓW	¨$™\î	\Ù,\Å:\÷³\n²l¬uA®»™¾J@%\ÉtO\Èf)Ö¹ŸUm\ìdc­rt\İ\Í\ôU*I¦{B6K±\Îı¬‚lc\'k]£\ën¦¯PI2\İ²YŠu\îgd;\ÙX\ë‚]w3}•€J’é\ÍR¬s?« \Û\Ø\É\ÆZ\äèº›\é«T’L\÷„l–bûY\Ù\ÆN6Öº G\×\İL_% ’dº\'d³\ë\Ü\Ï*\È6v²±\Ö9º\îfú*•$\Ó=!›¥X\ç~VA¶±“µ.\È\Ñu7\ÓW	¨$™\î	\Ù,\Å:\÷³\n²l¬uA®»™¾J@%\ÉtO\Èf)Ö¹ŸUm\ìdc­rt\İ\Í\ôU*I¦{B6K±\Îı¬‚lc\'k]£\ën¦¯PI2\İ²YŠu\îgd;\ÙX\ë‚]w3}•€J’é\ÍR¬s?« \Û\Ø\É\ÆZ\äèº›\é«T’L\÷„l–bûY\Ù\ÆN6Öº G\×\İL_% ’dº\'d³\ë\Ü\Ï*\È6v²±\Ö9º\îfú*•$\Ó=!›¥X\ç~VA¶±“µ.\È\Ñu7\ÓW	¨$™\î	\Ù,\Å:\÷³\n²l¬uA®»™¾J@%\ÉtO\Èf)Ö¹ŸUm\ìdc­rt\İ\Í\ôU*I¦{B6K±\Îı¬‚lc\'k]£\ën¦¯PI2\İ²YŠu\îgd;\ÙX\ë‚]w3}•€J’é\ÍR¬s?« _os=ÿ\à&« _os=ÿ\à&« _os=ÿ\à&s\Ğ\×_§\Ûÿ›\Ïm|ûS\Ôg\æ[ù\Ûù=¥Œúş{\ß0M¬3\É\ëùC\Ğ\ç_ú\ĞFK \÷\Ìø\Ï\çş}â¸?qÁ4\å\'q3úüK?µiÿ[şW¹$\óÅ¨\çş}b\Û¹dšû}6q3Ÿ‚şø3c›ŸşŸš\ße¾§”¦	\í¼cRig6¼OAü™±\Í\×4º‡ŒS\Ê&v~¶¾\ä\ãù-úJe9?ü\Æ\Û$\ğOÌ‰\Ò\ÎQ\Ì\İsÉ¤26\ÙÌA¿\òş“5\õÿk}/9Q>\ÏOüI\æ	SV—~uş¿\é\ï\ì\çıBOŸ\÷=}\Ş/\ô\ôy¿\Ğ\Ó\çıBOŸ\ĞT\ç¬wnoR\0\0\0\0IEND®B`‚','555 Carne norte ',44.00,'260 g','CG',1,'F','2024-06-26 06:49:28','ACT'),(2,'PROD0002','7484852000648',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0‹\0\0\0p\0\0\0¡.r\0\0IDATx^\í‘ÑŠ%Wûÿz—\Åv\Ö\í\Ì§z¡0¥qZŠÉ¹¢¾ş\ó\ê\Ùú\Ê\à\Õ\Ã\ô~¡§\ëıBO\×û…®\÷=]\ïzº\Ş/\ôt½_\è\éz¿\Ğ\Ó\õ~¡§\ëıBO\×û…®\÷=]\ïzº\Ş/\ôt½_\è\éz¿\Ğ\Ó\õ~¡§\ëıBOW\õ…¾¾ş\÷\Ï\í\ä\ñ§ÿˆœ\Ú\0\Â\Éo!\Ş“$¥ªC\Ó$\Ïpu §6€p\ò[ˆ£…\÷\Ã$I©\ê\Ğ4\É3\\È©\r œü\âh\áı0IRª:4M\òWrj\'¿…8Zx?L’”ªM“<\ÃÕœ\Ú\0\Â\Éo!\Ş“$¥ªC\Ó$\Ïpu §6€p\ò[ˆ£…\÷\Ã$I©\ê\Ğ4\É3\\È©\r œü\âh\áı0IRª:4M\òWrj\'¿…8Zx?L’”ªM“<\ÃÕœ\Ú\0\Â\Éo!\Ş“$¥ªC\Ó$\Ïpu §6€p\ò[ˆ£…\÷\Ã$I©\ê\Ğ4\É3\\È©\r œü\âh\áı0IRª:4M\òWrj\'¿…8Zx?L’”ªM“<\ÃÕœ\Ú\0\Â\Éo!\Ş“$¥ªC\Ó$\Ïpu §6€p\ò[ˆ£…\÷\Ã$I©\ê\Ğ4\É3\\È©\r œü\âh\áı0IRª:4M\òWrj\'¿…8Zx?L’”ªM“<\ÃÕœ\Ú\0\Â\Éo!\Ş“$¥ªC\Ó$\Ïpu §6€p\ò[ˆ£…\÷\Ã$I©\ê\Ğ4\É3\\È©\r œü\âh\áı0IRª:4M\òWrj\'¿…8Zx?L’”ªM“<\ÃÕœ\Ú\0\Â\Éo!\Ş“$¥ªC\Ó$\Ïpu §6€p\ò[ˆ£…\÷\Ã$I©\ê\Ğ4\É3\\È©\r œü\âh\áı0IRª:4M\òWrj\'¿…8Zx?L’”ªM“<\ÃÕœ\Ú\0\Â\Éo!\Ş“$¥ªC\Ó$\Ïpu §6€p\ò[ˆ£…\÷\Ã$I©\ê\Ğ4\É3\\È©\r œü\âh\áı0IRª:4M\òWrj\'¿…8Zx?L’”ªM“<\ÃÕœ\Ú\0\Â\Éo!\Ş“$¥ªC\Ó$\Ïpu §6€p\ò[ˆ£…\÷\Ã$I©\ê\Ğ4\É3\\È©\r œü\âh\áı0IRª:4M\òWrj\'¿…8Zx?L’”ªM“<\ÃÕœ\Ú\0\Â\Éo!\Ş“$¥ªC\Ó$\Ïpu §6€p\ò[ˆ£…\÷\Ã$I©\ê\Ğ4\É3\\È©\r œü\âh\áı0IRª:4M\òWrj\'¿…8Zx?L’”ªM“<\ÃÕœ\Ú\0\Â\Éo!\Ş“$¥ªC\Ó$\Ïpu §6€p\ò[ˆ£…\÷\Ã$I©\ê\Ğ4\É3\\È©\r œü\âh\áı0IRª:4M\òWrj\'¿…8Zx?L’”ªM“<\ÃÕœ\Ú\0\Â\Éo!\Ş“$¥ªC\Ó$\Ïpu §6€p\ò[ˆ£…\÷\Ã$I©\ê\Ğ4\É3\\È©\r œü\âh\áı0IRª:4M\òWrj\'¿…8Zx?L’”ªM“<\ÃÕœ\Ú\0\Â\Éo!\Ş“$¥ªC\Ó$\Ïpu §6€p\ò[ˆ£…\÷\Ã$I©\ê\Ğ4\É3\\È©\r œü\âh\áı0IRª:4M\òWrj\'¿…8Zx?L’”ªM“<\ÃÕœ\Ú\0\Â\Éo!\Ş“$¥ªC\Ó$\Ïpu §6€p\ò[ˆ£…\÷\Ã$I©\ê\Ğ4\É3\\È©\r œü\âh\áı0IRª:4M\òWrj\'¿…8Zx?L’”ªM“<\ÃÕœ\Ú\0\Â\Éo!\Ş“$¥ªC\Ó$\Ïpu §6€p\ò[ˆ£…\÷\Ã$I©\ê\Ğ4\É3\\È©\r œü\âh\áı0IRª:4M\òWrj\'¿…8Zx?L’”ªM“<\ÃÕœ\Ú\0\Â\Éo!\Ş“$¥ªC\Ó$\Ïpu §6€p\ò[ˆ£…\÷\Ã$I©\ê\Ğ4\É3\\È©\r œü\âh\áı0IRª:4M\òWrj\'¿…8Zx?L’”ªM“<\ÃÕœ\Ú\0\Â\Éo!\Ş“$¥ªC\Ó$\Ïpu §6€p\ò[ˆ£…\÷\Ã$I©\ê\Ğ4\É3\\È©\r œü\âh\áı0IRª:4M\òWrj\'¿…8Zx?L’”ªM“<\ÃÕœ\Ú\0\Â\Éo!\Ş“$¥ªC\Ó$\Ïpu §6€p\ò[ˆ£…\÷\Ã$I©\ê\Ğ4\É3\\È©\r œü\âh\áı0IRª:4M\òWrj\'¿…8Zx?L’”ªM“<\ÃÕœ\Ú\0\Â\Éo!\Ş“$¥ªC\Ó$\Ïpu §6€p\ò[ˆ£…\÷\Ã$I©\ê\Ğ4\É3\\È©\r œü\âh\áı0IRª:4M\òWrj\'¿…8Zx?L’”ªM“<\ÃÕœ\Ú\0\Â\Éo!\Ş“$¥ªC\Ó$\Ïpu §6€p\ò[ˆ£…\÷\Ã$I©\ê\Ğ4\É3\\È©\r œü\âh\áı0IRª:4M\òWrj\'¿…8Zx?L’”ªıø¯9\ë_üKªC?şk\Îúÿ’\êĞÿš³şÅ¿\äûC_W\Öiÿ5ş·Ó©!!Œ?\ÑÉ·y\ğ·Ä¹\ß\ß2ø\è‡\öÿiù5®|¾&.\Ëø\ÏoC¿o\Égû™ÇŸ6úÅ¡ıš~\r\É\'\Õ\Â|IT(\Â\Ï#\ñ\ç\'°$(*3Ÿ•ÿŒ°\Ñv\è—ÿÍ·¿\æ\Û_ÿ\ç\â¿\ï<0\ßşI²`Ÿ@“ü\ñøCT\äøh;\ô\Ëÿ\æ\Û_\ÃO·\Üş&c\í-aTŸÿ\êœ|*˜ø3\ÂF\ã¡\ßù?~ùk>Oxb>“­\Ï~¿ŸX¿d>R\ã¡ù?8\ò\õ—ş\ŞÿY\ñ&şt¸\ë¾%®xL\Ì\Ï\êÿş¼*\õ~¡§\ëıBO\×û…®\÷=]\ïzºş¾À XBv±\0\0\0\0IEND®B`‚','555 Sardines Green',42.00,'425 g','CG',2,'F','2024-06-26 06:51:18','ACT'),(3,'PROD0003','7484858000970',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0‹\0\0\0p\0\0\0¡.r\0\0rIDATx^\í‘\ëjdIƒûı_z—%;\æ\Ù>‘z \åAG·V¨\×?\÷Î¾—\÷»ûB§\ß}¡\Ó\ï¾\Ğ\éw_\è\ô»/tú\İ:ı\î~\÷…N¿ûB§\ß}¡\Ó\ï¾\Ğ\éw_\è\ô»/tú\İ:ı\î~\÷…N¿ûB§\ß}¡\Ó/z¡\×\ë¿8ÿ/Ÿ\äUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æm›¸›’\áUbv2\Õ\r)’`:‹—`~QÑ¶‰»)\ÎQ%f\'S\İ`\"	¦³x	\æ}|}?xIT\ô\ñ5\öı\à%Q\Ñ\Ç\×\Ø\÷ƒ—\ÌE¯\ßO\å_\÷¼†Ù­\ê\ÙSL‘\òY\÷ÅŒ¼ø\r¦>G²R\Å\×ßÜ›¢\ç_zXC‰†<\Ò<zø9’\Ä\Ó?\ßz„L\îM\Ñ\ó/mk\ä?ÿŸ·|\Ç\õ9’\Ä\Ó?\ßz„L\î©\è\íÏŒk\Æ\õ¯_\÷YO\İ#|o\ğ˜\ï\ãú\ì\í{*zû3\ãš\×tT¿\é\á}1n\ÏnØ˜/P\×\Õ\\ŸB&·}\ç7Ş®\é†n\Ş<\ßa\äs$‰=FHù%!“[‹>\òü¿\îwı©€x¾\Ã<_\÷\ÛL\áb\Ä\ó—\î¯ÿÀ½\ğ\î~\÷…N¿ûB§\ß}¡\Ó\ï¾\Ğ\é\÷/\×M™…5+\ï\0\0\0\0IEND®B`‚','Argentina beefloaf',21.00,'170 g','CG',3,'F','2024-06-26 06:52:52','ACT'),(4,'PROD0004','7484858000352',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0‹\0\0\0p\0\0\0¡.r\0\0\ÚIDATx^\í\Ñ\Ûj\õX\à¼ÿK\÷0¨\Ç|\íRv\ÖHi0?\ÖE¨U§]Á_½\÷\ìûº\ï=\ì\Ş/\ô\ô{¿\Ğ\Ó\ïıBO¿\÷=ı\Ş/\ô\ô{¿\Ğ\Ó\ïıBO¿\÷=ı\Ş/\ô\ô{¿\Ğ\Ó\ïıBO¿\÷=ı\Ş/\ô\ô{¿\Ğ\Ó\ïıBO¿\÷=ı\Ş/\ô\ô{¿\Ğ\Óo\õ…¾¾ş\÷\ï\Åß‚ÿ\ËÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\È5nr±\ÒÀÙªb;M¥áœ¬gK\ZIşı­Š\\\ã&+\rœ­*¶\ÓT\Z\Î\Éz¶¤‘\é\ßßª\è\××Œ\ï^²*ú\õ5\ãûƒ—¬Š~}\Íøş\à%}\Ñ\×?\ï.ÿ\ï>¯1û]\Õg\Ï\Å\\\ä\íy]1-\ó/™‹¼=¯+&ù\ñıP\ôù—>¬QÒøƒ\ç\Ö\Üz|¶¤x\Æ\Ü\ÈÛ³•n\ä\æ~(úüKß­¹ıc\íÿ\ó£ç‹“O|=[R|\Âx—çº›¡\Ô|¶\Î\Ù}*ú\ñg\Ú5>/|û\Ës]17>N˜_\Ï\ï<ù¼˜\ä\Ç\÷©\èÇŸi\×\Ôÿv;\ÕCW\ÌO\Zn\Ï4|\ÇxŸ™T/²•f\÷m\Ñ\Éoü¸&\riş\Îs\ÂÜ-)ş-\æFzYŞ·E¿\òş?uÿ\Ôÿ–.p\óœ0Ÿ/ı¿\Â\\O\É\éş\İ\ö\÷\ö\÷~¡§\ßû…~\ïzú½_\è\é\÷~¡§\ß\0„H™\Åz\Å;\0\0\0\0IEND®B`‚','Argentina cornedbeef',52.00,'260 g','CG',4,'F','2024-06-26 06:54:24','ACT'),(5,'PROD0005','7484858015066',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0‹\0\0\0p\0\0\0¡.r\0\0	IDATx^\í\Ñ\Ñn-¹\rDQÿÿO\'œ0kH\êXCz€\ÆE×ƒÀ³«T¦\Ü_ÿz\õl}e\ğ\êaz¿\Ğ\Ó\õ~¡§\ëıBO\×û…®\÷=]\ïzº\Ş/\ôt½_\è\éz¿\Ğ\Ó\õ~¡§\ëıBO\×û…®\÷=]\ïzº\Ş/\ôt½_\è\éz¿\Ğ\Ó\õ~¡§k\õ…¾¾şs\İ3xú\éğ·’­\ël§·j@PbR+¡{¸Ñªè´½/\Ô\Z$[\×\ÙNoÕ€$ Ä¤V:C\÷p£U\Ñi{_¨5H¶®³ŞªI@‰I­t†\î\áF«¢\Ó\ö¾Pkl]g;½U’€“Z\é\İÃVE§\í}¡\Ö Ùº\Îvz«$%&µ\Òº‡­ŠN\ÛûB­A²u\í\ôV\rHJLj¥3t7Z¶\÷…Zƒd\ë:\Û\é­\Z”˜\ÔJg\èn´*:m\ïµ\É\Öu¶\Ó[5 	(1©•\Î\Ğ=\ÜhUt\Ú\Şj\r’­\ël§·j@PbR+¡{¸Ñªè´½/\Ô\Z$[\×\ÙNoÕ€$ Ä¤V:C\÷p£U\Ñi{_¨5H¶®³ŞªI@‰I­t†\î\áF«¢\Ó\ö¾Pkl]g;½U’€“Z\é\İÃVE§\í}¡\Ö Ùº\Îvz«$%&µ\Òº‡­ŠN\ÛûB­A²u\í\ôV\rHJLj¥3t7Z¶\÷…Zƒd\ë:\Û\é­\Z”˜\ÔJg\èn´*:m\ïµ\É\Öu¶\Ó[5 	(1©•\Î\Ğ=\ÜhUt\Ú\Şj\r’­\ël§·j@PbR+¡{¸Ñªè´½/\Ô\Z$[\×\ÙNoÕ€$ Ä¤V:C\÷p£U\Ñi{_¨5H¶®³ŞªI@‰I­t†\î\áF«¢\Ó\ö¾Pkl]g;½U’€“Z\é\İÃVE§\í}¡\Ö Ùº\Îvz«$%&µ\Òº‡­ŠN\ÛûB­A²u\í\ôV\rHJLj¥3t7Z¶\÷…Zƒd\ë:\Û\é­\Z”˜\ÔJg\èn´*:m\ïµ\É\Öu¶\Ó[5 	(1©•\Î\Ğ=\ÜhUt\Ú\Şj\r’­\ël§·j@PbR+¡{¸Ñªè´½/\Ô\Z$[\×\ÙNoÕ€$ Ä¤V:C\÷p£U\Ñi{_¨5H¶®³ŞªI@‰I­t†\î\áF«¢\Ó\ö¾Pkl]g;½U’€“Z\é\İÃVE§\í}¡\Ö Ùº\Îvz«$%&µ\Òº‡­ŠN\ÛûB­A²u\í\ôV\rHJLj¥3t7Z¶\÷…Zƒd\ë:\Û\é­\Z”˜\ÔJg\èn´*:m\ïµ\É\Öu¶\Ó[5 	(1©•\Î\Ğ=\ÜhUt\Ú\Şj\r’­\ël§·j@PbR+¡{¸Ñªè´½/\Ô\Z$[\×\ÙNoÕ€$ Ä¤V:C\÷p£U\Ñi{_¨5H¶®³ŞªI@‰I­t†\î\áF«¢\Ó\ö¾Pkl]g;½U’€“Z\é\İÃVE§\í}¡\Ö Ùº\Îvz«$%&µ\Òº‡­ŠN\ÛûB­A²u\í\ôV\rHJLj¥3t7Z¶\÷…Zƒd\ë:\Û\é­\Z”˜\ÔJg\èn´*:m\ïµ\É\Öu¶\Ó[5 	(1©•\Î\Ğ=\ÜhUt\Ú\Şj\r’­\ël§·j@PbR+¡{¸Ñªè´½/\Ô\Z$[\×\ÙNoÕ€$ Ä¤V:C\÷p£U\Ñi{_¨5H¶®³ŞªI@‰I­t†\î\áF«¢\Ó\ö¾Pkl]g;½U’€“Z\é\İÃVE§\í}¡\Ö Ùº\Îvz«$%&µ\Òº‡­ŠN\ÛûB­A²u\í\ôV\rHJLj¥3t7Z¶\÷…Zƒd\ë:\Û\é­\Z”˜\ÔJg\èn´*:m\ïµ\É\Öu¶\Ó[5 	(1©•\Î\Ğ=\ÜhUt\Ú\Şj\r’­\ël§·j@PbR+¡{¸Ñªè´½/\Ô\Z$[\×\ÙNoÕ€$ Ä¤V:C\÷p£U\Ñi{_¨5H¶®³ŞªI@‰I­t†\î\áF«¢\Ó\ö¾Pkl]g;½U’€“Z\é\İÃVE§\í}¡\Ö Ùº\Îvz«$%&µ\Òº‡­ŠN\ÛûB­A²u\í\ôV\rHJLj¥3t7Z¶\÷…Zƒd\ë:\Û\é­\Z”˜\ÔJg\èn´*:m\ïµ\É\Öu¶\Ó[5 	(1©•\Î\Ğ=\ÜhUt\Ú\Şj\r’­\ël§·j@PbR+¡{¸Ñªè´½/\Ô\Z$[\×\ÙNoÕ€$ Ä¤V:C\÷p£U\Ñi{_¨5H¶®³ŞªI@‰I­t†\î\áF«¢\Ó\ö¾Pkl]g;½U’€“Z\é\İÃVE§\í}¡\Ö Ùº\Îvz«$%&µ\Òº‡­ŠN\ÛûB­A²u\í\ôV\rHJLj¥3t7Z¶\÷…Zƒd\ë:\Û\é­\Z”˜\ÔJg\èn´*:m\ïµ\É\Öu¶\Ó[5 	(1©•\Î\Ğ=\ÜhUt\Ú\Şj\r’­\ël§·j@PbR+¡{¸Ñªè´½/\Ô\Z$[\×\ÙNoÕ€$ Ä¤V:C\÷p£U\Ñi{_¨5H¶®³ŞªI@‰I­t†\î\áF«¢\Ó\ö¾Pkl]g;½U’€“Z\é\İÃVE§\í}¡\Ö Ùº\Îvz«$%&µ\Òº‡­ŠN\ÛûB­A²u\í\ôV\rHJLj¥3t7Z¶\÷…Zƒd\ë:\Û\é­\Z”˜\ÔJg\èn´*:m\ïµ\É\Öu¶\Ó[5 	(1©•\Î\Ğ=\ÜhUt\Ú\Şj\r’­\ël§·j@PbR+¡{¸Ñªè´½/\Ô\Z$[\×\ÙNoÕ€$ Ä¤V:C\÷p£U\Ñi{_¨5H¶®³ŞªI@‰I­t†\î\áF«¢\Ó\ö¾Pkl]g;½U’€“Z\é\İÃVE¿¾\ÍX\ğ&«¢_\ßf¬?x“UÑ¯o3\Ö¼I_\ô\õWeûú¼wOUŸ3A¦Ÿ¡o\Ò\òù˜S,ı¼$Á\ãüıP\ôù/}\ØF+=\ì>“šÛŒ?[\Ø\Î\'+ı¬µ\'’~&¸\ÑEŸÿ\Òi› 5¬™/$¯sülašS\òÿ\ãüW\ÚL\"¡oRùXŸŠ~ü3\í6şŒ9½\ç·2¡o’xÛ~\ÖÌŒ¤ŸuÃ±>ıøg\Úm¾:\é^f\Ô7ù©\ô\óƒ•`½uC\ÒÏ¶¦c\Ñ\Í\ßøq›\Z¨\áSæ†¤Ÿ-¬³?kfF\Ò\Ï7:ı\Ê\ß\ğ\ñ­¿úÿµbH™\òY§ü\ç\Îù‡\ôÿWK½_\è\éz¿\Ğ\Ó\õ~¡§\ëıBO\×û…®¬œ\\p¸¾\Å(\0\0\0\0IEND®B`‚','Argentina Spicy Sisig',41.00,'150 g','CG',5,'S','2024-06-26 06:55:35','ACT'),(6,'PROD0006','7484858004930',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0‹\0\0\0p\0\0\0¡.r\0\0	„IDATx^\í\ÑÛŠ49\à~ÿ—\Şe\ñø¦\î\î±<ü¤.Œâ ¨(\ò\ë?\ï<{¾>‰w6\ïzú¼_\è\é\ó~¡§\Ïû…>\ïzú¼_\è\é\ó~¡§\Ïû…>\ïzú¼_\è\é\ó~¡§\Ïû…>\ïzú¼_\è\é\ó~¡§\Ïû…>\ïzú¼_\è\é3úB__ÿ;\÷-şºü#gT\İ\Í\ôª\"¥‚’kê”¬\÷ÊŒ‚v\ì­t\àŒª»™^uC$£TPr-B’\õ^™QĞ®“½•œQu7Ó«nˆd”\nJ®E¨S²\Ş+3\n\Úu²·Ò3ª\îfz\Õ\r‘ŒRAÉµuJ\Ö{eFA»N\öV:pF\Õ\İL¯º!’Q*(¹¡N\Éz¯\Ì(h\×\É\ŞJÎ¨º›\éU7D2J%\×\"\Ô)Y\ï•\í:\Ù[\éÀUw3½\ê†HF© \äZ„:%\ë½2£ ]\'{+8£\ên¦W\İ\É(”\\‹P§d½Wf´\ëdo¥gT\İ\Í\ôª\"¥‚’kê”¬\÷ÊŒ‚v\ì­t\àŒª»™^uC$£TPr-B’\õ^™QĞ®“½•œQu7Ó«nˆd”\nJ®E¨S²\Ş+3\n\Úu²·Ò3ª\îfz\Õ\r‘ŒRAÉµuJ\Ö{eFA»N\öV:pF\Õ\İL¯º!’Q*(¹¡N\Éz¯\Ì(h\×\É\ŞJÎ¨º›\éU7D2J%\×\"\Ô)Y\ï•\í:\Ù[\éÀUw3½\ê†HF© \äZ„:%\ë½2£ ]\'{+8£\ên¦W\İ\É(”\\‹P§d½Wf´\ëdo¥gT\İ\Í\ôª\"¥‚’kê”¬\÷ÊŒ‚v\ì­t\àŒª»™^uC$£TPr-B’\õ^™QĞ®“½•œQu7Ó«nˆd”\nJ®E¨S²\Ş+3\n\Úu²·Ò3ª\îfz\Õ\r‘ŒRAÉµuJ\Ö{eFA»N\öV:pF\Õ\İL¯º!’Q*(¹¡N\Éz¯\Ì(h\×\É\ŞJÎ¨º›\éU7D2J%\×\"\Ô)Y\ï•\í:\Ù[\éÀUw3½\ê†HF© \äZ„:%\ë½2£ ]\'{+8£\ên¦W\İ\É(”\\‹P§d½Wf´\ëdo¥gT\İ\Í\ôª\"¥‚’kê”¬\÷ÊŒ‚v\ì­t\àŒª»™^uC$£TPr-B’\õ^™QĞ®“½•œQu7Ó«nˆd”\nJ®E¨S²\Ş+3\n\Úu²·Ò3ª\îfz\Õ\r‘ŒRAÉµuJ\Ö{eFA»N\öV:pF\Õ\İL¯º!’Q*(¹¡N\Éz¯\Ì(h\×\É\ŞJÎ¨º›\éU7D2J%\×\"\Ô)Y\ï•\í:\Ù[\éÀUw3½\ê†HF© \äZ„:%\ë½2£ ]\'{+8£\ên¦W\İ\É(”\\‹P§d½Wf´\ëdo¥gT\İ\Í\ôª\"¥‚’kê”¬\÷ÊŒ‚v\ì­t\àŒª»™^uC$£TPr-B’\õ^™QĞ®“½•œQu7Ó«nˆd”\nJ®E¨S²\Ş+3\n\Úu²·Ò3ª\îfz\Õ\r‘ŒRAÉµuJ\Ö{eFA»N\öV:pF\Õ\İL¯º!’Q*(¹¡N\Éz¯\Ì(h\×\É\ŞJÎ¨º›\éU7D2J%\×\"\Ô)Y\ï•\í:\Ù[\éÀUw3½\ê†HF© \äZ„:%\ë½2£ ]\'{+8£\ên¦W\İ\É(”\\‹P§d½Wf´\ëdo¥gT\İ\Í\ôª\"¥‚’kê”¬\÷ÊŒ‚v\ì­t\àŒª»™^uC$£TPr-B’\õ^™QĞ®“½•œQu7Ó«nˆd”\nJ®E¨S²\Ş+3\n\Úu²·Ò3ª\îfz\Õ\r‘ŒRAÉµuJ\Ö{eFA»N\öV:pF\Õ\İL¯º!’Q*(¹¡N\Éz¯\Ì(h\×\É\ŞJÎ¨º›\éU7D2J%\×\"\Ô)Y\ï•\í:\Ù[\éÀUw3½\ê†HF© \äZ„:%\ë½2£ ]\'{+8£\ên¦W\İ\É(”\\‹P§d½Wf´\ëdo¥gT\İ\Í\ôª\"¥‚’kê”¬\÷ÊŒ‚v\ì­t\àŒª»™^uC$£TPr-B’\õ^™QĞ®“½•œQu7Ó«nˆd”\nJ®E¨S²\Ş+3\n\Úu²·Ò3ª\îfz\Õ\r‘ŒRAÉµuJ\Ö{eFA»N\öV:pF\Õ\İL¯º!’Q*(¹¡N\Éz¯\Ì(h\×\É\ŞJÎ¨º›\éU7D2J%\×\"\Ô)Y\ï•\í:\Ù[\éÀUw3½\ê†HF© \äZ„:%\ë½2£ ]\'{+8£\ên¦W\İ\É(”\\‹P§d½Wf´\ëdo¥gT\İ\Í\ôª\"¥‚’kê”¬\÷ÊŒ‚v\ì­t\àŒª»™^uC$£TPr-B’\õ^™QĞ®“½•œQu7Ó«nˆd”\nJ®E¨S²\Ş+3\n\Úu²·Ò3ª\îfz\Õ\r‘ŒRAÉµuJ\Ö{eFA»N\öV:pF\Õ\İL¯º!’Q*(¹¡N\Éz¯\Ì(h\×\É\ŞJÎ¨º›\éU7D2J%\×\"\Ô)Y\ï•\í:\Ù[\éÀUw3½\ê†HF© \äZ„:%\ë½2£ ]\'{+8£\ên¦W\İ\É(”\\‹P§d½Wf´\ëdo¥gT\İ\Í\ôª\"¥‚’kê”¬\÷ÊŒ‚v\ì­t\àŒª»™^uC$£TPr-B’\õ^™QĞ®“½•œQu7Ó«nˆd”\nJ®E¨S²\Ş+3\n\Úu²·Ò3ª\îfz\Õ\r‘ŒRAÉµuJ\Ö{eFA»N\öV:pF\Õ\İL¯º!’Q*(¹¡N\Éz¯\Ì(h\×\É\ŞJÎ¨º›\éU7D2J%\×\"\Ô)Y\ï•\í:\Ù[\éÀUw3½\ê†HF© \äZ„:%\ë½2£ ]\'{+8£\ên¦W\İ\É(”\\‹P§d½Wf´\ëdo¥gT\İ\Í\ôª\"¥‚’kê”¬\÷ÊŒ‚v\ì­t\àŒª»™^uC$£TPr-B’\õ^™QĞ®“½•œQu7Ó«nˆd”\nJ®E¨S²\Ş+3\n\Úu²·Ò3ª\îfz\Õ\r‘ŒRAÉµuJ\Ö{eFA\×\Û\Ï\Üdt½\Í\ñüÁMFA\×\Û\Ï\Ü$}ı}>\å¿\æû6\Şî¢¾\÷S\ä¬YL\ä?ü‘w\×\ó¦øz¯\ÌA\ßÿ\Ò7m”>ş\Ø\ï=\É\Ñ#Œ¤{gºº\ã¿g>\à9™‚¾ÿ¥]›bº\áCú\Æ\ó\Å\È\÷½`$\İ;Ó—\èù`¾\ñt\Ãp¾ú\ñgb›\Øşë¯¹\ë©Y\Ì\ßÎ˜\Ú\î<?\È\É|\ô\ã\Ï\Ä6\ëŸ|Œ\ê/=\Îb¾\ñt\Ã\ì†b>\æƒ,ÿ\Ç\Õ\óû\á\ñlƒ~\ó?¶\é†n\Şy~\Ã|ÀHºw\Æ\Ùy~\Ã|À~<Û +¿Q!_\Í\ß\õÿKµ|x~\Ã|?\İ\ß¥Z~¼\êÌ¿4ÿú¼3œ\÷=}\Ş/\ô\ôy¿\Ğ\Ó\çıBOŸ\÷=}şŠn \ÙzRw\0\0\0\0IEND®B`‚','Argentina Vienna Sausage',42.00,'260 g','CG',6,'S','2024-06-26 06:56:54','ACT'),(7,'PROD0007','4800249908237',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0‹\0\0\0p\0\0\0¡.r\0\0\n	IDATx^\í‘Qn%\Û\r½ÿM\'\ÈQ)‰c\Ãr‚Æ \õqA‘%]\ôÇ¿\Şzv}\Øx\ëa\õ~¡§\×û…^\ïzz½_\è\é\õ~¡§\×û…^\ïzz½_\è\é\õ~¡§\×û…^\ïzz½_\è\é\õ~¡§\×û…^\ïzz½_\è\é\õ~¡§\×û…^§/\ô\ñ\ñŸ\ñüR0’IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-\â5z˜¥IGŒ¢è•œQ\óeŠ§I! ­¦Zt¬\Ó\"^£‡\éPšt\Ä(Š^\É5_¦xŠ\ÒjªE\Ç:-ú\ñk¾]\ñ%§E?~Í·\ë/¾\ä´\èÇ¯ùvıÅ—|²\èãŸ²û»\Ú5¿¦\èÏ¡O½2ù•/şGœ´4\å\÷G\êO‹>ı³`;¯;3¾\ÚÕ¤ş)§µq¦ÿ\íª‹\òø³\õšù¯8¬ù×ŸNM@\íš\Ãú\ÔiÀ\ô¿]û¢?ßj\×|ü®´ŒVg¶“™\ÚÕ¤\Î/\ñ«\ÅW;¶4\×\è{µ/\Ê\Ñ)ÿ\Ô/_)\Û	4g\Õ*™!ùù•O@s¦f}\ÊÌµ\Çú|\Ñşl½f¾\á+L\Õ¦Ø®&\õW™j“šk\õù¢oü\Ù\Ç\ïúº“–fjbÿ×šü§N\Ú\Õ\äT\ôÿ´şO\óÖ·\ëıBO¯\÷=½\Ş/\ô\ôz¿\Ğ\Ó\ëıBO¯—wY\Ò:a\0\0\0\0IEND®B`‚','Cdo Karne Norte',32.00,'175 g','CG',7,'F','2024-06-26 06:58:13','ACT'),(8,'PROD0008','7484851004326',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0‹\0\0\0p\0\0\0¡.r\0\0	XIDATx^\í\Ñ\Ñn&GDa¿ÿK\'Šœ o¡ÁN\ã•F«©‹VıU\Ç4½z¶>r\ğ\êaz¿\Ğ\Ó\õ~¡§\ëıBO\×û…®\÷=]\ïzº\Ş/\ôt½_\è\éz¿\Ğ\Ó\õ~¡§\ëıBO\×û…®\÷=]\ïzº\Ş/\ôt½_\è\éz¿\Ğ\Ó\õ~¡§k\õ…>>şùs\ß\È\ÓO\Íÿ\"kk>½‰Â®\n\Éh¬À\Ï\áF«An\ãNauA\Ö\Ö0|z3„]’\ÑXŸÃVƒ\ÜÆ<\Ãê‚¬­aø\ô&f»*$£±?‡­¹;y†\ÕY[\Ã\ğ\éM\ÌvUHFc~7Z\rrw\ò«²¶†\áÓ›˜!ìªŒ\Æ\nün´\Z\ä6\î\äVdm\rÃ§71C\ØU!ø9\Üh5\Èm\Ü\É3¬.\È\Ú\Z†Oob†°«B2\Z+\ğs¸\ÑjÛ¸“gX]µ5Ÿ\Ş\ÄaW…d4V\à\çp£\Õ ·q\'Ï°º kk>½‰Â®\n\Éh¬À\Ï\áF«An\ãNauA\Ö\Ö0|z3„]’\ÑXŸÃVƒ\ÜÆ<\Ãê‚¬­aø\ô&f»*$£±?‡­¹;y†\ÕY[\Ã\ğ\éM\ÌvUHFc~7Z\rrw\ò«²¶†\áÓ›˜!ìªŒ\Æ\nün´\Z\ä6\î\äVdm\rÃ§71C\ØU!ø9\Üh5\Èm\Ü\É3¬.\È\Ú\Z†Oob†°«B2\Z+\ğs¸\ÑjÛ¸“gX]µ5Ÿ\Ş\ÄaW…d4V\à\çp£\Õ ·q\'Ï°º kk>½‰Â®\n\Éh¬À\Ï\áF«An\ãNauA\Ö\Ö0|z3„]’\ÑXŸÃVƒ\ÜÆ<\Ãê‚¬­aø\ô&f»*$£±?‡­¹;y†\ÕY[\Ã\ğ\éM\ÌvUHFc~7Z\rrw\ò«²¶†\áÓ›˜!ìªŒ\Æ\nün´\Z\ä6\î\äVdm\rÃ§71C\ØU!ø9\Üh5\Èm\Ü\É3¬.\È\Ú\Z†Oob†°«B2\Z+\ğs¸\ÑjÛ¸“gX]µ5Ÿ\Ş\ÄaW…d4V\à\çp£\Õ ·q\'Ï°º kk>½‰Â®\n\Éh¬À\Ï\áF«An\ãNauA\Ö\Ö0|z3„]’\ÑXŸÃVƒ\ÜÆ<\Ãê‚¬­aø\ô&f»*$£±?‡­¹;y†\ÕY[\Ã\ğ\éM\ÌvUHFc~7Z\rrw\ò«²¶†\áÓ›˜!ìªŒ\Æ\nün´\Z\ä6\î\äVdm\rÃ§71C\ØU!ø9\Üh5\Èm\Ü\É3¬.\È\Ú\Z†Oob†°«B2\Z+\ğs¸\ÑjÛ¸“gX]µ5Ÿ\Ş\ÄaW…d4V\à\çp£\Õ ·q\'Ï°º kk>½‰Â®\n\Éh¬À\Ï\áF«An\ãNauA\Ö\Ö0|z3„]’\ÑXŸÃVƒ\ÜÆ<\Ãê‚¬­aø\ô&f»*$£±?‡­¹;y†\ÕY[\Ã\ğ\éM\ÌvUHFc~7Z\rrw\ò«²¶†\áÓ›˜!ìªŒ\Æ\nün´\Z\ä6\î\äVdm\rÃ§71C\ØU!ø9\Üh5\Èm\Ü\É3¬.\È\Ú\Z†Oob†°«B2\Z+\ğs¸\ÑjÛ¸“gX]µ5Ÿ\Ş\ÄaW…d4V\à\çp£\Õ ·q\'Ï°º kk>½‰Â®\n\Éh¬À\Ï\áF«An\ãNauA\Ö\Ö0|z3„]’\ÑXŸÃVƒ\ÜÆ<\Ãê‚¬­aø\ô&f»*$£±?‡­¹;y†\ÕY[\Ã\ğ\éM\ÌvUHFc~7Z\rrw\ò«²¶†\áÓ›˜!ìªŒ\Æ\nün´\Z\ä6\î\äVdm\rÃ§71C\ØU!ø9\Üh5\Èm\Ü\É3¬.\È\Ú\Z†Oob†°«B2\Z+\ğs¸\ÑjÛ¸“gX]µ5Ÿ\Ş\ÄaW…d4V\à\çp£\Õ ·q\'Ï°º kk>½‰Â®\n\Éh¬À\Ï\áF«An\ãNauA\Ö\Ö0|z3„]’\ÑXŸÃVƒ\ÜÆ<\Ãê‚¬­aø\ô&f»*$£±?‡­¹;y†\ÕY[\Ã\ğ\éM\ÌvUHFc~7Z\rrw\ò«²¶†\áÓ›˜!ìªŒ\Æ\nün´\Z\ä6\î\äVdm\rÃ§71C\ØU!ø9\Üh5\Èm\Ü\É3¬.\È\Ú\Z†Oob†°«B2\Z+\ğs¸\ÑjÛ¸“gX]µ5Ÿ\Ş\ÄaW…d4V\à\çp£\Õ ·q\'Ï°º kk>½‰Â®\n\Éh¬À\Ï\áF«An\ãNauA\Ö\Ö0|z3„]’\ÑXŸÃVƒ\ÜÆ<\Ãê‚¬­aø\ô&f»*$£±?‡­¹;y†\ÕY[\Ã\ğ\éM\ÌvUHFc~7Z\rrw\ò«²¶†\áÓ›˜!ìªŒ\Æ\nün´\Z\ä6\î\äVdm\rÃ§71C\ØU!ø9\Üh5\Èm\Ü\É3¬.\È\Ú\Z†Oob†°«B2\Z+\ğs¸\ÑjÛ¸“gX]µ5Ÿ\Ş\ÄaW…d4V\à\çp£\Õ ·q\'Ï°º kk>½‰Â®\n\Éh¬À\Ï\áF«An\ãNauA\Ö\Ö0|z3„]’\ÑXŸÃVƒ\ÜÆ<\Ãê‚¬­aø\ô&f»*$£±?‡­¹;y†\ÕY[\Ã\ğ\éM\ÌvUHFc~7Z\rrw\ò«²¶†\áÓ›˜!ìªŒ\Æ\nün´\Z\ä6\î\äVdm\rÃ§71C\ØU!ø9\Üh5\Èm\Ü\É3¬.\È\Ú\Z†Oob†°«B2\Z+\ğs¸\ÑjÛ¸“gX]µ5Ÿ\Ş\ÄaW…d4V\à\çp£\Õ ·q\'Ï°º kk>½‰Â®\n\Éh¬À\Ï\áF«An\ãNauA\Ö\Ö0|z3„]’\ÑXŸÃVƒ\ÜÆ<\Ãê‚¬­aø\ô&f»*$£±?‡­¹;y†\ÕY[\Ã\ğ\éM\ÌvUHFc~7Z\rú\ñm®\õo²\Z\ô\ã\Û\\\ë\Şd5\èÇ·¹\Ö¼\Éy\ĞÇ¯\Ê\õš·\ño»Q3‰\ág\î\ÏHyú\óš˜\ëe¾“D\ï\è‹A\ó\Z¶±J‡}Ÿ\é&×¼I?+P}\Ç|\'I?\ë†\×úb\ĞüŸºm\"©@ª\æ\Õ*’!Ÿÿ0|5¡.ù\Ü\êS\òkMƒ¾ü7\Çmü>\İs\ÍF2`\è’\ğ\İ\Ñ$?\ë\ß^k\Z\ô\å¿9n\óy[’\í7•’°\nD’@0\ß\÷\ñ3…µƒ¾\ó?¾Ü¦î˜št?#°\n|gHø/\'§Ÿuøµ\ÚA?\ò?¼\ğS¿\öÿVaS“\ÈSÒ©N¨‰U™øy?ş>ı\ö\ğj©\÷=]\ïzº\Ş/\ôt½_\è\éz¿\Ğ\Ó\õ7­\\®ù\ÂD\0\0\0\0IEND®B`‚','Century Tuna Afritada',24.00,'155 g','CG',8,'S','2024-06-26 07:00:16','ACT'),(9,'PROD0009','4800249902068',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0‹\0\0\0p\0\0\0¡.r\0\0	IDATx^\í‘\Ûj%[ıÿ?=\Ãs’˜\Ğ\Å\ÆrC\ÑT>,r‡¢e5\õ\ñŸ7\ÏÎ‡Á›‡\åıBO\Ïû…\÷==\ïzz\Ş/\ô\ô¼_\è\éy¿\Ğ\Ó\ó~¡§\çıBO\Ïû…\÷==\ïzz\Ş/\ô\ô¼_\è\éy¿\Ğ\Ó\ó~¡§\çıBO\Ïû…\Óúøø\ß?\Ï\ËÂ‘ ‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ¤\ëYt(!‰\Ò[³&.(“e1\ó&r\Øe^rZ\ô\ë\×ü8\ñ%§E¿~Í\ó_rZ\ô\ë\×ü8\ñ%_,úø\'¦ÿfº\æ\ó_‘ÿŒ³·N^qù„\çı•l‹¾üc­ ÿÆIV®Ÿ-dÿsD?/\åo,¬½¦^ÿ\Â\Ô?ıå¿ª‚~Vaú»\Õù@\Èk\ÏOÁKúE\Ë\ÌtÍ—ÿŸJ\ê\Ï\êT¢Ÿ-d¯¤ş¬N%Ÿ\å3…\çı•\ô‹\ò\çÿ\ä“kÊŸU˜H\ÛÁ˜Ÿ<¯8}\n\ß\ï\ß!ú)x\É×‹–?\Ö^S¯ÿT–ÅŸ-d¯DP?§U‰~\n^\ò\õ¢ü±\ó}’Ÿ„I\Õş>¦ú\"ù\Ù\Â\é_µ\ä\åÿ7Ç¼_\è\éy¿\Ğ\Ó\ó~¡§\çıBO\Ïû…ÿw\É.<>ü¹\0\0\0\0IEND®B`‚','CDO Meat loaf',26.00,'210 g','CG',9,'F','2024-06-26 07:01:28','ACT'),(10,'PROD0010','7484851004494',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0‹\0\0\0p\0\0\0¡.r\0\0­IDATx^\í‘\áj%iC\óş/½\Ë0;âŒœ+w[Y(šÒ[:\×Q¨¯ÿ¼z¶¾\Üx\õ0½_\è\éz¿\Ğ\Ó\õ~¡§\ëıBO\×û…®\÷=]\ïzº\Ş/\ôt½_\è\éz¿\Ğ\Ó\õ~¡§\ëıBO\×û…®\÷=]\ïzº\Ş/\ôt½_\è\éz¿\Ğ\ÓU}¡¯¯¿~\ÎW¾­~‹œ)M\Í\ö\Z\Ì™©u%\É\ôª±\r;±1£9Sšš\í5&˜!2S\ëJ’\éUbvbcFr¦45\ÛkL0Cd¦Ö•$Ó«:\Ä6\ì\ÄÆŒ\äLij¶×˜`†\ÈL­+I¦WuˆmØ‰È™\Ò\Ôl¯1Á‘™ZW’L¯\êÛ°3:3¥©\Ù^c‚\"3µ®$™^\Õ!¶a\'6ft gJS³½\Æ3Dfj]I2½ªCl\ÃNl\Ì\è@Î”¦f{	fˆ\ÌÔº’dzU‡Ø†Ø˜Ñœ)M\Í\ö\Z\Ì™©u%\É\ôª±\r;±1£9Sšš\í5&˜!2S\ëJ’\éUbvbcFr¦45\ÛkL0Cd¦Ö•$Ó«:\Ä6\ì\ÄÆŒ\äLij¶×˜`†\ÈL­+I¦WuˆmØ‰È™\Ò\Ôl¯1Á‘™ZW’L¯\êÛ°3:3¥©\Ù^c‚\"3µ®$™^\Õ!¶a\'6ft gJS³½\Æ3Dfj]I2½ªCl\ÃNl\Ì\è@Î”¦f{	fˆ\ÌÔº’dzU‡Ø†Ø˜Ñœ)M\Í\ö\Z\Ì™©u%\É\ôª±\r;±1£9Sšš\í5&˜!2S\ëJ’\éUbvbcFr¦45\ÛkL0Cd¦Ö•$Ó«:\Ä6\ì\ÄÆŒ\äLij¶×˜`†\ÈL­+I¦WuˆmØ‰È™\Ò\Ôl¯1Á‘™ZW’L¯\êÛ°3:3¥©\Ù^c‚\"3µ®$™^\Õ!¶a\'6ft gJS³½\Æ3Dfj]I2½ªCl\ÃNl\Ì\è@Î”¦f{	fˆ\ÌÔº’dzU‡Ø†Ø˜Ñœ)M\Í\ö\Z\Ì™©u%\É\ôª±\r;±1£9Sšš\í5&˜!2S\ëJ’\éUbvbcFr¦45\ÛkL0Cd¦Ö•$Ó«:\Ä6\ì\ÄÆŒ\äLij¶×˜`†\ÈL­+I¦WuˆmØ‰È™\Ò\Ôl¯1Á‘™ZW’L¯\êÛ°3:3¥©\Ù^c‚\"3µ®$™^\Õ!¶a\'6ft gJS³½\Æ3Dfj]I2½ªCl\ÃNl\Ì\è@Î”¦f{	fˆ\ÌÔº’dzU‡Ø†Ø˜Ñœ)M\Í\ö\Z\Ì™©u%\É\ôª±\r;±1£9Sšš\í5&˜!2S\ëJ’\éUbvbcFr¦45\ÛkL0Cd¦Ö•$Ó«:\Ä6\ì\ÄÆŒ\äLij¶×˜`†\ÈL­+I¦WuˆmØ‰È™\Ò\Ôl¯1Á‘™ZW’L¯\êÛ°3:3¥©\Ù^c‚\"3µ®$™^\Õ!¶a\'6ft gJS³½\Æ3Dfj]I2½ªCl\ÃNl\Ì\è@Î”¦f{	fˆ\ÌÔº’dzU‡Ø†Ø˜Ñœ)M\Í\ö\Z\Ì™©u%\É\ôª±\r;±1£9Sšš\í5&˜!2S\ëJ’\éUbvbcFr¦45\ÛkL0Cd¦Ö•$Ó«:\Ä6\ì\ÄÆŒ\äLij¶×˜`†\ÈL­+I¦WuˆmØ‰È™\Ò\Ôl¯1Á‘™ZW’L¯\êÛ°3:3¥©\Ù^c‚\"3µ®$™^\Õ!¶a\'6ft gJS³½\Æ3Dfj]I2½ªCl\ÃNl\Ì\è@Î”¦f{	fˆ\ÌÔº’dzU‡Ø†Ø˜Ñœ)M\Í\ö\Z\Ì™©u%\É\ôª±\r;±1£9Sšš\í5&˜!2S\ëJ’\éUbvbcFr¦45\ÛkL0Cd¦Ö•$Ó«:\Ä6\ì\ÄÆŒ\äLij¶×˜`†\ÈL­+I¦WuˆmØ‰È™\Ò\Ôl¯1Á‘™ZW’L¯\êÛ°3:3¥©\Ù^c‚\"3µ®$™^\Õ!¶a\'6ft gJS³½\Æ3Dfj]I2½ªCl\ÃNl\Ì\è@Î”¦f{	fˆ\ÌÔº’dzU‡Ø†Ø˜Ñœ)M\Í\ö\Z\Ì™©u%\É\ôª±\r;±1£9Sšš\í5&˜!2S\ëJ’\éUbvbcFr¦45\ÛkL0Cd¦Ö•$Ó«:\Ä6\ì\ÄÆŒ\äLij¶×˜`†\ÈL­+I¦WuˆmØ‰È™\Ò\Ôl¯1Á‘™ZW’L¯\êÛ°3:3¥©\Ù^c‚\"3µ®$™^\Õ!¶a\'6ft gJS³½\Æ3Dfj]I2½ªCl\ÃNl\Ì\è@Î”¦f{	fˆ\ÌÔº’dzU‡Ø†Ø˜Ñœ)M\Í\ö\Z\Ì™©u%\É\ôª±\r;±1£9Sšš\í5&˜!2S\ëJ’\éUbvbcFr¦45\ÛkL0Cd¦Ö•$Ó«:\Ä6\ì\ÄÆŒ\äLij¶×˜`†\ÈL­+I¦WuˆmØ‰È™\Ò\Ôl¯1Á‘™ZW’L¯\êÛ°3:3¥©\Ù^c‚\"3µ®$™^\Õ!¶a\'6ft gJS³½\Æ3Dfj]I2½ªCl\ÃNl\Ì\è@Î”¦f{	fˆ\ÌÔº’dzU‡Ø†Ø˜Ñœ)M\Í\ö\Z\Ì™©u%\É\ôª±\r;±1£9Sšš\í5&˜!2S\ëJ’\éUbvbcFr¦45\ÛkL0Cd¦Ö•$Ó«:\Ä6\ì\ÄÆŒ\äLij¶×˜`†\ÈL­+I¦Wu\è\ÇÛœ\õ7©ıx›³ş\à&Õ¡os\Ö\Ü\äûC_ÿ–\Çÿ(·\áo?ÊŒšû\\\å|\ë\ÛÏ§Cÿ“\ó[¿\Òû#Z\å¿\Ú0\"0\çÀ|º<ıy\Ä\Ö	\Ìù“3ı\Éd³\Ñr(ÿ¥Om\äLÀ¢À|A3’üüC\Í\ë\ğ‹¿\â:•­\æ\Û6ŸşŸ¿U2ß®r6Ÿul\r\ÏJ‡\Ö?\óm›¯\ï\Ä\ôÊœO@À& g•a\ßşj•S\ê\ã¡_ùk›	Lø3O«œ€M\àÓ‘\éüÖ¯\æOJ}<\ô#CG¾şÑ¿\óÿE\ZŒ™|s>i^˜£O\Îoı\êg\õÿ½şª\×û…®\÷=]\ïzº\Ş/\ôt½_\è\éú/Yq®t\Åù\0\0\0\0IEND®B`‚','Century Tuna Mechado',27.00,'155 g','CG',10,'S','2024-06-26 07:02:35','ACT'),(11,'PROD0011','7484854013622',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0‹\0\0\0p\0\0\0¡.r\0\0	/IDATx^\í‘\Ûj%G\õÿ?mcd±u;ru/\Ë\äC\"eT§R\Ì\×_¯­¯¼z˜\Ş/\ôt½_\è\éz¿\Ğ\Ó\õ~¡§\ëıBO\×û…®\÷=]\ïzº\Ş/\ôt½_\è\éz¿\Ğ\Ó\õ~¡§\ëıBO\×û…®\÷=]\ïzº\Ş/\ôt½_\è\é:úB__ÿ<·“‡_ı\Ãÿº\ì(¨thı„f4\÷8Ä¯\è¨\Èk¼É‹—•\r¡ŸƒÃŒ\æ‡øy7y±\Ñâ²£ Ò¡!\ôsr˜\Ñ\Ü\ã¿¢£\"¯\ñ&/6Z\\vT:4„~B3š{\âWtT\ä5\Ş\ä\ÅF‹Ë‚J‡†\Ğ\ÏA\ÈaFsCüŠŠ¼Æ›¼\Øhq\ÙQP\é\Ğú99\Ìh\îqˆ_\ÑQ‘\×x“-.;\n*\ZB?!‡\Í=\ñ+:*\ò\Zo\òb£\ÅeGA¥CC\è\ç \ä0£¹\Ç!~EGE^\ãM^l´¸\ì(¨thı„f4\÷8Ä¯\è¨\Èk¼É‹—•\r¡ŸƒÃŒ\æ‡øy7y±\Ñâ²£ Ò¡!\ôsr˜\Ñ\Ü\ã¿¢£\"¯\ñ&/6Z\\vT:4„~B3š{\âWtT\ä5\Ş\ä\ÅF‹Ë‚J‡†\Ğ\ÏA\ÈaFsCüŠŠ¼Æ›¼\Øhq\ÙQP\é\Ğú99\Ìh\îqˆ_\ÑQ‘\×x“-.;\n*\ZB?!‡\Í=\ñ+:*\ò\Zo\òb£\ÅeGA¥CC\è\ç \ä0£¹\Ç!~EGE^\ãM^l´¸\ì(¨thı„f4\÷8Ä¯\è¨\Èk¼É‹—•\r¡ŸƒÃŒ\æ‡øy7y±\Ñâ²£ Ò¡!\ôsr˜\Ñ\Ü\ã¿¢£\"¯\ñ&/6Z\\vT:4„~B3š{\âWtT\ä5\Ş\ä\ÅF‹Ë‚J‡†\Ğ\ÏA\ÈaFsCüŠŠ¼Æ›¼\Øhq\ÙQP\é\Ğú99\Ìh\îqˆ_\ÑQ‘\×x“-.;\n*\ZB?!‡\Í=\ñ+:*\ò\Zo\òb£\ÅeGA¥CC\è\ç \ä0£¹\Ç!~EGE^\ãM^l´¸\ì(¨thı„f4\÷8Ä¯\è¨\Èk¼É‹—•\r¡ŸƒÃŒ\æ‡øy7y±\Ñâ²£ Ò¡!\ôsr˜\Ñ\Ü\ã¿¢£\"¯\ñ&/6Z\\vT:4„~B3š{\âWtT\ä5\Ş\ä\ÅF‹Ë‚J‡†\Ğ\ÏA\ÈaFsCüŠŠ¼Æ›¼\Øhq\ÙQP\é\Ğú99\Ìh\îqˆ_\ÑQ‘\×x“-.;\n*\ZB?!‡\Í=\ñ+:*\ò\Zo\òb£\ÅeGA¥CC\è\ç \ä0£¹\Ç!~EGE^\ãM^l´¸\ì(¨thı„f4\÷8Ä¯\è¨\Èk¼É‹—•\r¡ŸƒÃŒ\æ‡øy7y±\Ñâ²£ Ò¡!\ôsr˜\Ñ\Ü\ã¿¢£\"¯\ñ&/6Z\\vT:4„~B3š{\âWtT\ä5\Ş\ä\ÅF‹Ë‚J‡†\Ğ\ÏA\ÈaFsCüŠŠ¼Æ›¼\Øhq\ÙQP\é\Ğú99\Ìh\îqˆ_\ÑQ‘\×x“-.;\n*\ZB?!‡\Í=\ñ+:*\ò\Zo\òb£\ÅeGA¥CC\è\ç \ä0£¹\Ç!~EGE^\ãM^l´¸\ì(¨thı„f4\÷8Ä¯\è¨\Èk¼É‹—•\r¡ŸƒÃŒ\æ‡øy7y±\Ñâ²£ Ò¡!\ôsr˜\Ñ\Ü\ã¿¢£\"¯\ñ&/6Z\\vT:4„~B3š{\âWtT\ä5\Ş\ä\ÅF‹Ë‚J‡†\Ğ\ÏA\ÈaFsCüŠŠ¼Æ›¼\Øhq\ÙQP\é\Ğú99\Ìh\îqˆ_\ÑQ‘\×x“-.;\n*\ZB?!‡\Í=\ñ+:*\ò\Zo\òb£\ÅeGA¥CC\è\ç \ä0£¹\Ç!~EGE^\ãM^l´¸\ì(¨thı„f4\÷8Ä¯\è¨\Èk¼É‹—•\r¡ŸƒÃŒ\æ‡øy7y±\Ñâ²£ Ò¡!\ôsr˜\Ñ\Ü\ã¿¢£\"¯\ñ&/6Z\\vT:4„~B3š{\âWtT\ä5\Ş\ä\ÅF‹Ë‚J‡†\Ğ\ÏA\ÈaFsCüŠŠ¼Æ›¼\Øhq\ÙQP\é\Ğú99\Ìh\îqˆ_\ÑQ‘\×x“-.;\n*\ZB?!‡\Í=\ñ+:*\ò\Zo\òb£\ÅeGA¥CC\è\ç \ä0£¹\Ç!~EGE^\ãM^l´¸\ì(¨thı„f4\÷8Ä¯\è¨\Èk¼É‹—•\r¡ŸƒÃŒ\æ‡øy7y±\Ñâ²£ Ò¡!\ôsr˜\Ñ\Ü\ã¿¢£\"¯\ñ&/6Z\\vT:4„~B3š{\âWtT\ä5\Ş\ä\ÅF‹Ë‚J‡†\Ğ\ÏA\ÈaFsCüŠŠ¼Æ›¼\Øhq\ÙQP\é\Ğú99\Ìh\îqˆ_\ÑQ‘\×x“-.;\n*\ZB?!‡\Í=\ñ+:*\ò\Zo\òb£\ÅeGA¥CC\è\ç \ä0£¹\Ç!~EGE^\ãM^l´¸\ì(¨thı„f4\÷8Ä¯\è¨\Èk¼É‹—•\r¡ŸƒÃŒ\æ‡øy7y±\Ñâ²£ Ò¡!\ôsr˜\Ñ\Ü\ã¿¢£\"¯\ñ&/6Z\\vT:4„~B3š{\âWtT\ä5\Ş\ä\ÅF‹Ë‚J‡†\Ğ\ÏA\ÈaFsCüŠŠ¼Æ›¼\Øhq\ÙQP\é\Ğú99\Ìh\îqˆ_\ÑQ‘\×x“-.;\n*\ZB?!‡\Í=\ñ+:*\ò\Zo\òb£\ÅeGA¥CC\è\ç \ä0£¹\Ç!~EGE^\ãM^l´¸\ì(¨thı„f4\÷8Ä¯\è¨\Èk¼É‹—•\r¡ŸƒÃŒ\æ‡øy7y±\Ñâ²£ Ò¡!\ôsr˜\Ñ\Ü\ã¿¢£\"¯\ñ&/6Z\\vT:4„~B3š{\âWtTt}\ÍZğ’£¢\ëk\Öúƒ—]_³\Ö¼¤.úúUÿ§y\ßvU\ó\r‰C\ò\à!\Ï\Çß¹7»„¿¢E\ó_\Z\Ö…\ì\ç7›–?—\"—ü<	¿†\ğDŠ\æ¿Ô­	ÿùÿ|¼ù’\òşµ\Ã\Ï\å\å·@(^TC2\ô\ï4}ü3\å\Zÿ\Ê\Ï\á\Ş\İ\ä$üZ†ù\ç\ò\×\á\ïv¿I\Î×šŠ>ş™r\Í\÷dú\Ã\ë;‰\Ò\'\á\×…°{•Ÿä„°D;µE?ù\×\äƒ|\Ü\İ\ä\Ä\nI~~\íP¾\ÉI»\ä[¹\äPmÑ•¿\áÿ\ğ[¿\ò?„›œÅ¨R\×0ÿ•\ğ+aN~Ÿ~oû«s½_\è\éz¿\Ğ\Ó\õ~¡§\ëıBO\×û…®¿\Êa³ü~\ôUx\0\0\0\0IEND®B`‚','Angel KremQuezo',56.00,'370 ml','DA',11,'S','2024-06-26 07:06:01','ACT'),(12,'PROD0012','7484854014926',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0‹\0\0\0p\0\0\0¡.r\0\0	QIDATx^\í‘Q$Wûş—\Ş\Åzl\"Ì’Ø½\âH©¡‹jø&¿ş\óÎ³\çË…w6\ïzú¼_\è\é\ó~¡§\Ïû…>\ïzú¼_\è\é\ó~¡§\Ïû…>\ïzú¼_\è\é\ó~¡§\Ïû…>\ïzú¼_\è\é\ó~¡§\Ïû…>\ïzú¼_\è\éS}¡¯¯ÿÅ¹¥\ÛOş\ñ‘›+k\ÜrMd<\ğX„\"™ 6Sbv\â3h\ÈÍ•5n¹&2x,B‘L›©±\r;\ñ´\ä\æ\Ê\Z·\\¼¡H&ˆ\ÍT‡Ø†øZrse[®‰Œ\Ş\0‹P$\ÄfªCl\ÃN|­¹¹²\Æ-\×D\Æo€E(’	b3\Õ!¶a\'>ƒÖ\Ü\\Y\ã–k\"\ã7À\"\É±™\êÛ°ŸA\ë@n®¬q\Ë5‘\ñÀ`Šd‚\ØLuˆmØ‰Ï u 7WÖ¸\åš\Èx\à\r°E2Al¦:\Ä6\ì\Äg\Ğ:›+k\ÜrMd<\ğX„\"™ 6Sbv\â3h\ÈÍ•5n¹&2x,B‘L›©±\r;\ñ´\ä\æ\Ê\Z·\\¼¡H&ˆ\ÍT‡Ø†øZrse[®‰Œ\Ş\0‹P$\ÄfªCl\ÃN|­¹¹²\Æ-\×D\Æo€E(’	b3\Õ!¶a\'>ƒÖ\Ü\\Y\ã–k\"\ã7À\"\É±™\êÛ°ŸA\ë@n®¬q\Ë5‘\ñÀ`Šd‚\ØLuˆmØ‰Ï u 7WÖ¸\åš\Èx\à\r°E2Al¦:\Ä6\ì\Äg\Ğ:›+k\ÜrMd<\ğX„\"™ 6Sbv\â3h\ÈÍ•5n¹&2x,B‘L›©±\r;\ñ´\ä\æ\Ê\Z·\\¼¡H&ˆ\ÍT‡Ø†øZrse[®‰Œ\Ş\0‹P$\ÄfªCl\ÃN|­¹¹²\Æ-\×D\Æo€E(’	b3\Õ!¶a\'>ƒÖ\Ü\\Y\ã–k\"\ã7À\"\É±™\êÛ°ŸA\ë@n®¬q\Ë5‘\ñÀ`Šd‚\ØLuˆmØ‰Ï u 7WÖ¸\åš\Èx\à\r°E2Al¦:\Ä6\ì\Äg\Ğ:›+k\ÜrMd<\ğX„\"™ 6Sbv\â3h\ÈÍ•5n¹&2x,B‘L›©±\r;\ñ´\ä\æ\Ê\Z·\\¼¡H&ˆ\ÍT‡Ø†øZrse[®‰Œ\Ş\0‹P$\ÄfªCl\ÃN|­¹¹²\Æ-\×D\Æo€E(’	b3\Õ!¶a\'>ƒÖ\Ü\\Y\ã–k\"\ã7À\"\É±™\êÛ°ŸA\ë@n®¬q\Ë5‘\ñÀ`Šd‚\ØLuˆmØ‰Ï u 7WÖ¸\åš\Èx\à\r°E2Al¦:\Ä6\ì\Äg\Ğ:›+k\ÜrMd<\ğX„\"™ 6Sbv\â3h\ÈÍ•5n¹&2x,B‘L›©±\r;\ñ´\ä\æ\Ê\Z·\\¼¡H&ˆ\ÍT‡Ø†øZrse[®‰Œ\Ş\0‹P$\ÄfªCl\ÃN|­¹¹²\Æ-\×D\Æo€E(’	b3\Õ!¶a\'>ƒÖ\Ü\\Y\ã–k\"\ã7À\"\É±™\êÛ°ŸA\ë@n®¬q\Ë5‘\ñÀ`Šd‚\ØLuˆmØ‰Ï u 7WÖ¸\åš\Èx\à\r°E2Al¦:\Ä6\ì\Äg\Ğ:›+k\ÜrMd<\ğX„\"™ 6Sbv\â3h\ÈÍ•5n¹&2x,B‘L›©±\r;\ñ´\ä\æ\Ê\Z·\\¼¡H&ˆ\ÍT‡Ø†øZrse[®‰Œ\Ş\0‹P$\ÄfªCl\ÃN|­¹¹²\Æ-\×D\Æo€E(’	b3\Õ!¶a\'>ƒÖ\Ü\\Y\ã–k\"\ã7À\"\É±™\êÛ°ŸA\ë@n®¬q\Ë5‘\ñÀ`Šd‚\ØLuˆmØ‰Ï u 7WÖ¸\åš\Èx\à\r°E2Al¦:\Ä6\ì\Äg\Ğ:›+k\ÜrMd<\ğX„\"™ 6Sbv\â3h\ÈÍ•5n¹&2x,B‘L›©±\r;\ñ´\ä\æ\Ê\Z·\\¼¡H&ˆ\ÍT‡Ø†øZrse[®‰Œ\Ş\0‹P$\ÄfªCl\ÃN|­¹¹²\Æ-\×D\Æo€E(’	b3\Õ!¶a\'>ƒÖ\Ü\\Y\ã–k\"\ã7À\"\É±™\êÛ°ŸA\ë@n®¬q\Ë5‘\ñÀ`Šd‚\ØLuˆmØ‰Ï u 7WÖ¸\åš\Èx\à\r°E2Al¦:\Ä6\ì\Äg\Ğ:›+k\ÜrMd<\ğX„\"™ 6Sbv\â3h\ÈÍ•5n¹&2x,B‘L›©±\r;\ñ´\ä\æ\Ê\Z·\\¼¡H&ˆ\ÍT‡Ø†øZrse[®‰Œ\Ş\0‹P$\ÄfªCl\ÃN|­¹¹²\Æ-\×D\Æo€E(’	b3\Õ!¶a\'>ƒÖ\Ü\\Y\ã–k\"\ã7À\"\É±™\êÛ°ŸA\ë@n®¬q\Ë5‘\ñÀ`Šd‚\ØLuˆmØ‰Ï u 7WÖ¸\åš\Èx\à\r°E2Al¦:\Ä6\ì\Äg\Ğ:›+k\ÜrMd<\ğX„\"™ 6Sbv\â3h\ÈÍ•5n¹&2x,B‘L›©±\r;\ñ´\ä\æ\Ê\Z·\\¼¡H&ˆ\ÍT‡Ø†øZrse[®‰Œ\Ş\0‹P$\ÄfªCl\ÃN|­¹¹²\Æ-\×D\Æo€E(’	b3\Õ!¶a\'>ƒÖ\Ü\\Y\ã–k\"\ã7À\"\É±™\êÛ°ŸA\ë@n®¬q\Ë5‘\ñÀ`Šd‚\ØLuˆmØ‰Ï u 7WÖ¸\åš\Èx\à\r°E2Al¦:\Ä6\ì\Äg\Ğ:›+k\ÜrMd<\ğX„\"™ 6Sbv\â3h\ÈÍ•5n¹&2x,B‘L›©±\r;\ñ´\ä\æ\Ê\Z·\\¼¡H&ˆ\ÍT‡Ø†øZrse[®‰Œ\Ş\0‹P$\ÄfªC¿½\Íyş\ÅMªC¿½\Íyş\ÅMªC¿½\Íyş\ÅM\æC_·ÿšÜ†\Ù\íTf¤P”nŠ¶\éŸ\ğ/ıS‘ø™ú‰\"]û·\Ì7‡\ò¿\ÚĞ²‡ıœù\ö\òO\Ä\ñ\ï‘ü<\òs\Å~š\Ø\Ì7‡ò¿´µ‘\ò	˜˜/ŒYüƒ?G\Ñşş$b™\òG©?gL?O:\ô\í?3¶\áOım\ï¹1ŸŠı\ÅÏ¿ù\ó“ùT>~Ë˜\ØL:\ô\í?3¶ùš†\î\Î/\Å*\ös³şv\î1\ñ3ş\ÖO›Yı\ä\ßø¶\Í\'\ğ	oÌ§\Âù\ÉÿK¸\ğyP\â˜\n\ñ\Ñ\Z\ï\ßf=\ô[ş\r¾\ğ\×ü\İÿ\Ó\Ò\Æ|*´\\šf»\ğ©P´”~â¯Ÿÿ\Üü\ãÿÀ;\å¼_\è\é\ó~¡§\Ïû…>\ïzú¼_\è\é\ó__ŒÛ¸½°™\0\0\0\0IEND®B`‚','Birchtree Fortified Milk',8.00,'33 g','DA',12,'F','2024-06-26 07:08:01','ACT'),(13,'PROD0013','7622300501549',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0‹\0\0\0p\0\0\0¡.r\0\0*IDATx^\í‘\Ñn%\Çıÿ?\à†H\İZ¶\Ú6V0XŒ\ZU\â‘=½\õ\ìúhã­‡\Õû…^\ïzz½_\è\é\õ~¡§\×û…^\ïzz½_\è\é\õ~¡§\×û…^\ïzz½_\è\é\õ~¡§\×û…^\ïzz½_\è\é\õ~¡§\×û…^\ïzz­¾\Ğ\Ç\Çÿ\Ö\ë¥`<\òbM\ãm¦v\Ï\Ñ\r@›´ƒ9®!˜L»¯U¯\á\õ\İ8À5S@´™\Ú=G7\0m\Ò\æ¸>†`2\í¾VA¾†\×w\ãX\0\×4N\Ñfj\÷\İ\0´I;˜\ãúR€É´ûZù\Z^ßc\\\Ó8D›©\İst\Ğ&\í`\ëcH&\Ó\îk\äkx}7pM\ãm¦v\Ï\Ñ\r@›´ƒ9®!˜L»¯U¯\á\õ\İ8À5S@´™\Ú=G7\0m\Ò\æ¸>†`2\í¾VA¾†\×w\ãX\0\×4N\Ñfj\÷\İ\0´I;˜\ãúR€É´ûZù\Z^ßc\\\Ó8D›©\İst\Ğ&\í`\ëcH&\Ó\îk\äkx}7pM\ãm¦v\Ï\Ñ\r@›´ƒ9®!˜L»¯U¯\á\õ\İ8À5S@´™\Ú=G7\0m\Ò\æ¸>†`2\í¾VA¾†\×w\ãX\0\×4N\Ñfj\÷\İ\0´I;˜\ãúR€É´ûZù\Z^ßc\\\Ó8D›©\İst\Ğ&\í`\ëcH&\Ó\îk\äkx}7pM\ãm¦v\Ï\Ñ\r@›´ƒ9®!˜L»¯U¯\á\õ\İ8À5S@´™\Ú=G7\0m\Ò\æ¸>†`2\í¾VA¾†\×w\ãX\0\×4N\Ñfj\÷\İ\0´I;˜\ãúR€É´ûZù\Z^ßc\\\Ó8D›©\İst\Ğ&\í`\ëcH&\Ó\îk\äkx}7pM\ãm¦v\Ï\Ñ\r@›´ƒ9®!˜L»¯U¯\á\õ\İ8À5S@´™\Ú=G7\0m\Ò\æ¸>†`2\í¾VA¾†\×w\ãX\0\×4N\Ñfj\÷\İ\0´I;˜\ãúR€É´ûZù\Z^ßc\\\Ó8D›©\İst\Ğ&\í`\ëcH&\Ó\îk\äkx}7pM\ãm¦v\Ï\Ñ\r@›´ƒ9®!˜L»¯U¯\á\õ\İ8À5S@´™\Ú=G7\0m\Ò\æ¸>†`2\í¾VA¾†\×w\ãX\0\×4N\Ñfj\÷\İ\0´I;˜\ãúR€É´ûZù\Z^ßc\\\Ó8D›©\İst\Ğ&\í`\ëcH&\Ó\îk\äkx}7pM\ãm¦v\Ï\Ñ\r@›´ƒ9®!˜L»¯U¯\á\õ\İ8À5S@´™\Ú=G7\0m\Ò\æ¸>†`2\í¾VA¾†\×w\ãX\0\×4N\Ñfj\÷\İ\0´I;˜\ãúR€É´ûZù\Z^ßc\\\Ó8D›©\İst\Ğ&\í`\ëcH&\Ó\îk\äkx}7pM\ãm¦v\Ï\Ñ\r@›´ƒ9®!˜L»¯U¯\á\õ\İ8À5S@´™\Ú=G7\0m\Ò\æ¸>†`2\í¾VA¾†\×w\ãX\0\×4N\Ñfj\÷\İ\0´I;˜\ãúR€É´ûZù\Z^ßc\\\Ó8D›©\İst\Ğ&\í`\ëcH&\Ó\îk\äkx}7pM\ãm¦v\Ï\Ñ\r@›´ƒ9®!˜L»¯U¯\á\õ\İ8À5S@´™\Ú=G7\0m\Ò\æ¸>†`2\í¾VA¾†\×w\ãX\0\×4N\Ñfj\÷\İ\0´I;˜\ãúR€É´ûZù\Z^ßc\\\Ó8D›©\İst\Ğ&\í`\ëcH&\Ó\îk\äkx}7pM\ãm¦v\Ï\Ñ\r@›´ƒ9®!˜L»¯U¯\á\õ\İ8À5S@´™\Ú=G7\0m\Ò\æ¸>†`2\í¾VA¾†\×w\ãX\0\×4N\Ñfj\÷\İ\0´I;˜\ãúR€É´ûZù\Z^ßc\\\Ó8D›©\İst\Ğ&\í`\ëcH&\Ó\îk\äkx}7pM\ãm¦v\Ï\Ñ\r@›´ƒ9®!˜L»¯U¯\á\õ\İ8À5S@´™\Ú=G7\0m\Ò\æ¸>†`2\í¾VA¾†\×w\ãX\0\×4N\Ñfj\÷\İ\0´I;˜\ãúR€É´ûZù\Z^ßc\\\Ó8D›©\İst\Ğ&\í`\ëcH&\Ó\îk\äkx}7pM\ãm¦v\Ï\Ñ\r@›´ƒ9®!˜L»¯U¯\á\õ\İ8À5S@´™\Ú=G7\0m\Ò\æ¸>†`2\í¾VA¾†\×w\ãX\0\×4N\Ñfj\÷\İ\0´I;˜\ãúR€É´ûZù\Z^ßc\\\Ó8D›©\İst\Ğ&\í`\ëcH&\Ó\îk\äkx}7pM\ãm¦v\Ï\Ñ\r@›´ƒ9®!˜L»¯U¯\á\õ\İ8À5S@´™\Ú=G7\0m\Ò\æ¸>†`2\í¾VA¾†\×w\ãX\0\×4N\Ñfj\÷\İ\0´I;˜\ãúR€É´ûZù\Z^ßc\\\Ó8D›©\İst\Ğ&\í`\ëcH&\Ó\îk\äkx}7pM\ãm¦v\Ï\Ñ\r@›´ƒ9®!˜L»¯U¯\á\õ\İ8À5S@´™\Ú=G7\0m\Ò\æ¸>†`2\í¾VA¾†\×w\ãX\0\×4N\Ñfj\÷\İ\0´I;˜\ãúR€É´ûZù\Z^ßc\\\Ó8D›©\İst\Ğ&\í`\ëcH&\Ó\îk\äkx}7pM\ãm¦v\Ï\Ñ\r@›´ƒ9®!˜L»¯U¯\á\õ\İ8À5S@´™\Ú=G7\0m\Ò\æ¸>†`2\í¾VA¾†\×w\ãX\0\×4N\Ñfj\÷\İ\0´I;˜\ãúR€É´ûZù\Z^ßc\\\Ó8D›©\İst\Ğ&\í`\ëcH&\Ó\îk\äkx}7pM\ãm¦v\Ï\Ñ\r@›´ƒ9®!˜L»¯U¯\á\õ\İ8À5S@´™\Ú=G7\0m\Ò\æ¸>†`2\í¾VA¾†\×w\ãX\0\×4N\Ñfj\÷\İ\0´I;˜\ãúR€É´ûZù\Z^ßc\\\Ó8D›©\İst\Ğ&\í`\ëcH&\Ó\îk\äkx}7pM\ãm¦v\Ï\Ñ\r@›´ƒ9®!˜L»¯U¯\á\õ\İ8À5S@´™\Ú=G7\0m\Ò\æ¸>†`2\í¾VA¾†\×w\ãX\0\×4N\Ñfj\÷\İ\0´I;˜\ãúR€É´ûZù\Z^ßc\\\Ó8D›©\İst\Ğ&\í`\ëcH&\Ó\îk\äkx}7pM\ãm¦v\Ï\Ñ\r@›´ƒ9®!˜L»¯U¯\á\õ\İ8À5S@´™\Ú=G7\0m\Ò\æ¸>†`2\í¾VA¾†\×w\ãX\0\×4N\Ñfj\÷\İ\0´I;˜\ãúR€É´ûZù\Z^ßc\\\Ó8D›©\İst\Ğ&\í`\ëcH&\Ó\îk\äkx}7pM\ãm¦v\Ï\Ñ\r@›´ƒ9®!˜L»¯U¯\á\õ\İ8À5S@´™\Ú=G7\0m\Ò\æ¸>†`2\í¾VA¾†\×w\ãX\0\×4N\Ñfj\÷\İ\0´I;˜\ãúR€É´ûZù\Z^ßc\\\Ó8D›©\İst\Ğ&\í`\ëcH&\Ó\îk\ô\ã\×üvıÁ—¬‚~üšß®?ø’UĞ_\ó\Û\õ_2}üZ=ş·n×œ‹ÿƒ\Ï[~­TK\ÙùÿÎ¯˜ˆ\Ïû#\õE\Ğ\ç¿4^\ã\ö~Ö©\ö¼v\ÄF}•yş\Ğøc\Â\ï\ÕAŸÿ\ÒxMşÚ”}¦\ßt\ìßœjO“2VºvÏ­O\àÒ´enê³ /f¼\æ\Ë\ë?qşù·ü\ò¿°>jG\Óút\Î\öd¾\ãT[\æ¦>ú\òg\Ækn×Ÿi§“:coNµ·À¿&`L¨b«ª\Ìq\Åæ¦®A\ßù\ñ\Z·\è3\íË­\ï8Õ¦\õ\éœ\í\'\Î\'\Ì	,\ë\Z´ù«Z\Ì\Ó9·¾\é|^\':ø\Ö#“\â\Æülı\ç?\ğÖ²\Ş/\ô\ôz¿\Ğ\Ó\ëıBO¯\÷=½\Ş/\ô\ôú2\0—¶¢oZ\ô\0\0\0\0IEND®B`‚','Cheez Whiz Squeeze',40.00,'115 g','DA',13,'F','2024-06-26 07:12:22','ACT'),(14,'PROD0014','4805358373037',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0‹\0\0\0p\0\0\0¡.r\0\0\n\İIDATx^\í‘\Ñn Gıÿ?}‡¤{D‰¶\Ö&£‡T¢(5\æ\ão<;>\ŞxX¼\è\é\ñş¡§\Çû‡\ïzz¼\è\é\ñş¡§\Çû‡\ïzz¼\è\é\ñş¡§\Çû‡\ïzz¼\è\é\ñş¡§\Çû‡\ïzz¼\è\é\ñş¡§\Ç\ê}|ü5\Î7“lM\×xj4T.½dVCÊ»²ùO¥Hm-ce”\×\ğª“\èPA¸\ÆS£) r\é%«°\ZRŞ•\Í*Ejk+£¼†WD‡\n\Â5\ZM•K/Y…Õ\ò®lşS)R[\ËX\å5¼\ê$:T®\ñ\Ôh\n¨\\z\É*¬†”we\óŸJ‘\ÚZ\Æ\Ê(¯\áU\'Ñ¡‚p§FS@\å\ÒKVa5¤¼+›ÿTŠ\Ô\Ö2VFy\r¯:‰„k<5š*—^²\n«!\å]\Ùü§R¤¶–±2\Êkx\ÕIt¨ \\\ã©\ÑP¹\ô’UX\r)\ï\Ê\æ?•\"µµŒ•Q^Ã«N¢C\á\ZO¦€Ê¥—¬\ÂjHyW6ÿ©©­e¬Œ\ò\Z^u*\×xj4T.½dVCÊ»²ùO¥Hm-ce”\×\ğª“\èPA¸\ÆS£) r\é%«°\ZRŞ•\Í*Ejk+£¼†WD‡\n\Â5\ZM•K/Y…Õ\ò®lşS)R[\ËX\å5¼\ê$:T®\ñ\Ôh\n¨\\z\É*¬†”we\óŸJ‘\ÚZ\Æ\Ê(¯\áU\'Ñ¡‚p§FS@\å\ÒKVa5¤¼+›ÿTŠ\Ô\Ö2VFy\r¯:‰„k<5š*—^²\n«!\å]\Ùü§R¤¶–±2\Êkx\ÕIt¨ \\\ã©\ÑP¹\ô’UX\r)\ï\Ê\æ?•\"µµŒ•Q^Ã«N¢C\á\ZO¦€Ê¥—¬\ÂjHyW6ÿ©©­e¬Œ\ò\Z^u*\×xj4T.½dVCÊ»²ùO¥Hm-ce”\×\ğª“\èPA¸\ÆS£) r\é%«°\ZRŞ•\Í*Ejk+£¼†WD‡\n\Â5\ZM•K/Y…Õ\ò®lşS)R[\ËX\å5¼\ê$:T®\ñ\Ôh\n¨\\z\É*¬†”we\óŸJ‘\ÚZ\Æ\Ê(¯\áU\'Ñ¡‚p§FS@\å\ÒKVa5¤¼+›ÿTŠ\Ô\Ö2VFy\r¯:‰„k<5š*—^²\n«!\å]\Ùü§R¤¶–±2\Êkx\ÕIt¨ \\\ã©\ÑP¹\ô’UX\r)\ï\Ê\æ?•\"µµŒ•Q^Ã«N¢C\á\ZO¦€Ê¥—¬\ÂjHyW6ÿ©©­e¬Œ\ò\Z^u*\×xj4T.½dVCÊ»²ùO¥Hm-ce”\×\ğª“\èPA¸\ÆS£) r\é%«°\ZRŞ•\Í*Ejk+£¼†WD‡\n\Â5\ZM•K/Y…Õ\ò®lşS)R[\ËX\å5¼\ê$:T®\ñ\Ôh\n¨\\z\É*¬†”we\óŸJ‘\ÚZ\Æ\Ê(¯\áU\'Ñ¡‚p§FS@\å\ÒKVa5¤¼+›ÿTŠ\Ô\Ö2VFy\r¯:‰„k<5š*—^²\n«!\å]\Ùü§R¤¶–±2\Êkx\ÕIt¨ \\\ã©\ÑP¹\ô’UX\r)\ï\Ê\æ?•\"µµŒ•Q^Ã«N¢C\á\ZO¦€Ê¥—¬\ÂjHyW6ÿ©©­e¬Œ\ò\Z^u*\×xj4T.½dVCÊ»²ùO¥Hm-ce”\×\ğª“\èPA¸\ÆS£) r\é%«°\ZRŞ•\Í*Ejk+£¼†WD‡\n\Â5\ZM•K/Y…Õ\ò®lşS)R[\ËX\å5¼\ê$:T®\ñ\Ôh\n¨\\z\É*¬†”we\óŸJ‘\ÚZ\Æ\Ê(¯\áU\'Ñ¡‚p§FS@\å\ÒKVa5¤¼+›ÿTŠ\Ô\Ö2VFy\r¯:‰„k<5š*—^²\n«!\å]\Ùü§R¤¶–±2\Êkx\ÕIt¨ \\\ã©\ÑP¹\ô’UX\r)\ï\Ê\æ?•\"µµŒ•Q^Ã«N¢C\á\ZO¦€Ê¥—¬\ÂjHyW6ÿ©©­e¬Œ\ò\Z^u*\×xj4T.½dVCÊ»²ùO¥Hm-ce”\×\ğª“\èPA¸\ÆS£) r\é%«°\ZRŞ•\Í*Ejk+£¼†WD‡\n\Â5\ZM•K/Y…Õ\ò®lşS)R[\ËX\å5¼\ê$:T®\ñ\Ôh\n¨\\z\É*¬†”we\óŸJ‘\ÚZ\Æ\Ê(¯\áU\'Ñ¡‚p§FS@\å\ÒKVa5¤¼+›ÿTŠ\Ô\Ö2VFy\r¯:‰„k<5š*—^²\n«!\å]\Ùü§R¤¶–±2\Êkx\ÕIt¨ \\\ã©\ÑP¹\ô’UX\r)\ï\Ê\æ?•\"µµŒ•Q^Ã«N¢C\á\ZO¦€Ê¥—¬\ÂjHyW6ÿ©©­e¬Œ\ò\Z^u*\×xj4T.½dVCÊ»²ùO¥Hm-ce”\×\ğª“\èPA¸\ÆS£) r\é%«°\ZRŞ•\Í*Ejk+£¼†WD‡\n\Â5\ZM•K/Y…Õ\ò®lşS)R[\ËX\å5¼\ê$:T®\ñ\Ôh\n¨\\z\É*¬†”we\óŸJ‘\ÚZ\Æ\Ê(¯\áU\'Ñ¡‚p§FS@\å\ÒKVa5¤¼+›ÿTŠ\Ô\Ö2VFy\r¯:‰„k<5š*—^²\n«!\å]\Ùü§R¤¶–±2\Êkx\ÕIt¨ \\\ã©\ÑP¹\ô’UX\r)\ï\Ê\æ?•\"µµŒ•Q^Ã«N¢C\á\ZO¦€Ê¥—¬\ÂjHyW6ÿ©©­e¬Œ\ò\Z^u*\×xj4T.½dVCÊ»²ùO¥Hm-ce”\×\ğª“\èPA¸\ÆS£) r\é%«°\ZRŞ•\Í*Ejk+£¼†WD‡\n\Â5\ZM•K/Y…Õ\ò®lşS)R[\ËX\å5¼\ê$:T®\ñ\Ôh\n¨\\z\É*¬†”we\óŸJ‘\ÚZ\Æ\Ê(¯\áU\'Ñ¡‚p§FS@\å\ÒKVa5¤¼+›ÿTŠ\Ô\Ö2VFy\r¯:‰„k<5š*—^²\n«!\å]\Ùü§R¤¶–±2\Êkx\ÕIt¨ \\\ã©\ÑP¹\ô’UX\r)\ï\Ê\æ?•\"µµŒ•Q^Ã«N¢C\á\ZO¦€Ê¥—¬\ÂjHyW6ÿ©©­e¬Œ\ò\Z^u*\×xj4T.½dVCÊ»²ùO¥Hm-ce”\×\ğª“\èPA¸\ÆS£) r\é%«°\ZRŞ•\Í*Ejk+£¼†WD‡\n\Â5\ZM•K/Y…Õ\ò®lşS)R[\ËX\å5¼\ê$:T®\ñ\Ôh\n¨\\z\É*¬†”we\óŸJ‘\ÚZ\Æ\Ê(¯\áU\'Ñ¡‚p§FS@\å\ÒKVa5¤¼+›ÿTŠ\Ô\Ö2VFy\r¯:‰„k<5š*—^²\n«!\å]\Ùü§R¤¶–±2\Êkx\ÕIt¨ \\\ã©\ÑP¹\ô’UX\r)\ï\Ê\æ?•\"µµŒ•Q^Ã«N¢C\á\ZO¦€Ê¥—¬\ÂjHyW6ÿ©©­e¬Œ\ò\Z^u*\×xj4T.½dVCÊ»²ùO¥Hm-ce”\×\ğª“\èPA¸\ÆS£) r\é%«°\ZRŞ•\Í*Ejk+£¼†WD‡\n\Â5\ZM•K/Y…Õ\ò®lşS)R[\ËX\å5¼\ê$:T®\ñ\Ôh\n¨\\z\É*¬†”we\óŸJ‘\ÚZ\Æ\Ê\è_\ó\ãø_²2ú\ã\×ü8şÃ—¬Œşø5?ÿ\ğ%\ß}ü¦¿\â³k\ÎT\ò/P\åoj\à|\Å\ëx…ŸMMB™\ß?_}»¬\n²œ\òo§U\ÖV…3\Ïrj~‡|VB&ÿq|jTß ¨\×\Ì\÷TB\Ğ\">Ó¤XƒŒ$Ì¨z\Å4™²o}¦\É2º\Ñ\×WŸ]\ó\ñ+(³U	ùS²ç«˜ú)KMv•\ë¬­ŸE7:\×dX\ñw®n–S0­~Ÿ|\á3Š/f‰i2•_wµ\õ³ø\Ş\è‹e\õšù†¨¬Pù·š\ß!‚sd\ßı`\ÙÇ¯ø\È\×1\õs¶j~‡ÿ£\ñ/­y\ã\Ç\ñş¡§\Çû‡\ïzz¼\è\é\ñş¡§\Çÿ¶ùy\ìĞ¶\0\0\0\0IEND®B`‚','Daily Queso',45.00,'165 g','DA',14,'F','2024-06-26 07:14:52','ACT'),(15,'PROD0015','4808647020094',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0‹\0\0\0p\0\0\0¡.r\0\0\nIDATx^\í‘\áj%gC\óş/\İÒš\ÙÑ¦Q\n\Ã2ş\ñ!Ë¾º\Ì\Ç_o=»>¼\õ°z¿\Ğ\Ó\ëıBO¯\÷=½\Ş/\ô\ôz¿\Ğ\Ó\ëıBO¯\÷=½\Ş/\ô\ôz¿\Ğ\Ó\ëıBO¯\÷=½\Ş/\ô\ôz¿\Ğ\Ó\ëıBO¯\÷=½\Ş/\ô\ôz¿\Ğ\Ó\ëıBO¯\ê}|ü³®—‚£\r)¤I¦==\Ò\öj\Å EvF„\Ó\öUb\Z¥db\æ6Ã†$Ó\ni{µb\";#\Âiûª1R21s›aC’iO…´½Z1H‘†\á´}U‡˜F)™˜¹Í°!É´§‡B\Ú^­¤\È\Î\0ÃˆpÚ¾ªCL£”L\Ì\ÜfØd\Ú\ÓC!m¯VRdg€aD8m_\Õ!¦QJ&fn3lH2\íé¡¶W+)²3À0\"œ¶¯\ê\Ó(%3·6$™\ö\ôPHÛ«ƒ\Ù`N\ÛWuˆi”’‰™\Û’L{z(¤\íÕŠAŠ\ì0Œ§\í«:\Ä4J\É\Ä\Ìm†\rI¦==\Ò\öj\Å EvF„\Ó\öUb\Z¥db\æ6Ã†$Ó\ni{µb\";#\Âiûª1R21s›aC’iO…´½Z1H‘†\á´}U‡˜F)™˜¹Í°!É´§‡B\Ú^­¤\È\Î\0ÃˆpÚ¾ªCL£”L\Ì\ÜfØd\Ú\ÓC!m¯VRdg€aD8m_\Õ!¦QJ&fn3lH2\íé¡¶W+)²3À0\"œ¶¯\ê\Ó(%3·6$™\ö\ôPHÛ«ƒ\Ù`N\ÛWuˆi”’‰™\Û’L{z(¤\íÕŠAŠ\ì0Œ§\í«:\Ä4J\É\Ä\Ìm†\rI¦==\Ò\öj\Å EvF„\Ó\öUb\Z¥db\æ6Ã†$Ó\ni{µb\";#\Âiûª1R21s›aC’iO…´½Z1H‘†\á´}U‡˜F)™˜¹Í°!É´§‡B\Ú^­¤\È\Î\0ÃˆpÚ¾ªCL£”L\Ì\ÜfØd\Ú\ÓC!m¯VRdg€aD8m_\Õ!¦QJ&fn3lH2\íé¡¶W+)²3À0\"œ¶¯\ê\Ó(%3·6$™\ö\ôPHÛ«ƒ\Ù`N\ÛWuˆi”’‰™\Û’L{z(¤\íÕŠAŠ\ì0Œ§\í«:\Ä4J\É\Ä\Ìm†\rI¦==\Ò\öj\Å EvF„\Ó\öUb\Z¥db\æ6Ã†$Ó\ni{µb\";#\Âiûª1R21s›aC’iO…´½Z1H‘†\á´}U‡˜F)™˜¹Í°!É´§‡B\Ú^­¤\È\Î\0ÃˆpÚ¾ªCL£”L\Ì\ÜfØd\Ú\ÓC!m¯VRdg€aD8m_\Õ!¦QJ&fn3lH2\íé¡¶W+)²3À0\"œ¶¯\ê\Ó(%3·6$™\ö\ôPHÛ«ƒ\Ù`N\ÛWuˆi”’‰™\Û’L{z(¤\íÕŠAŠ\ì0Œ§\í«:\Ä4J\É\Ä\Ìm†\rI¦==\Ò\öj\Å EvF„\Ó\öUb\Z¥db\æ6Ã†$Ó\ni{µb\";#\Âiûª1R21s›aC’iO…´½Z1H‘†\á´}U‡˜F)™˜¹Í°!É´§‡B\Ú^­¤\È\Î\0ÃˆpÚ¾ªCL£”L\Ì\ÜfØd\Ú\ÓC!m¯VRdg€aD8m_\Õ!¦QJ&fn3lH2\íé¡¶W+)²3À0\"œ¶¯\ê\Ó(%3·6$™\ö\ôPHÛ«ƒ\Ù`N\ÛWuˆi”’‰™\Û’L{z(¤\íÕŠAŠ\ì0Œ§\í«:\Ä4J\É\Ä\Ìm†\rI¦==\Ò\öj\Å EvF„\Ó\öUb\Z¥db\æ6Ã†$Ó\ni{µb\";#\Âiûª1R21s›aC’iO…´½Z1H‘†\á´}U‡˜F)™˜¹Í°!É´§‡B\Ú^­¤\È\Î\0ÃˆpÚ¾ªCL£”L\Ì\ÜfØd\Ú\ÓC!m¯VRdg€aD8m_\Õ!¦QJ&fn3lH2\íé¡¶W+)²3À0\"œ¶¯\ê\Ó(%3·6$™\ö\ôPHÛ«ƒ\Ù`N\ÛWuˆi”’‰™\Û’L{z(¤\íÕŠAŠ\ì0Œ§\í«:\Ä4J\É\Ä\Ìm†\rI¦==\Ò\öj\Å EvF„\Ó\öUb\Z¥db\æ6Ã†$Ó\ni{µb\";#\Âiûª1R21s›aC’iO…´½Z1H‘†\á´}U‡˜F)™˜¹Í°!É´§‡B\Ú^­¤\È\Î\0ÃˆpÚ¾ªCL£”L\Ì\ÜfØd\Ú\ÓC!m¯VRdg€aD8m_\Õ!¦QJ&fn3lH2\íé¡¶W+)²3À0\"œ¶¯\ê\Ó(%3·6$™\ö\ôPHÛ«ƒ\Ù`N\ÛWuˆi”’‰™\Û’L{z(¤\íÕŠAŠ\ì0Œ§\í«:\Ä4J\É\Ä\Ìm†\rI¦==\Ò\öj\Å EvF„\Ó\öUb\Z¥db\æ6Ã†$Ó\ni{µb\";#\Âiûª1R21s›aC’iO…´½Z1H‘†\á´}U‡˜F)™˜¹Í°!É´§‡B\Ú^­¤\È\Î\0ÃˆpÚ¾ªCL£”L\Ì\ÜfØd\Ú\ÓC!m¯VRdg€aD8m_\Õ!¦QJ&fn3lH2\íé¡¶W+)²3À0\"œ¶¯\ê\Ó(%3·6$™\ö\ôPHÛ«ƒ\Ù`N\ÛWuˆi”’‰™\Û’L{z(¤\íÕŠAŠ\ì0Œ§\í«:\Ä4J\É\Ä\Ìm†\rI¦==\Ò\öj\Å EvF„\Ó\öUb\Z¥db\æ6Ã†$Ó\ni{µb\";#\Âiûª1R21s›aC’iO…´½Z1H‘†\á´}U‡˜F)™˜¹Í°!É´§‡B\Ú^­¤\È\Î\0ÃˆpÚ¾ªCL£”L\Ì\ÜfØd\Ú\ÓC!m¯VRdg€aD8m_\Õ!¦QJ&fn3lH2\íé¡¶W+)²3À0\"œ¶¯\ê\Ó(%3·6$™\ö\ôPHÛ«ƒ\Ù`N\ÛWuˆi”’‰™\Û’L{z(¤\íÕŠAŠ\ì0Œ§\í«:\Ä4J\É\Ä\Ìm†\rI¦==\Ò\öj\Å EvF„\Ó\öUb\Z¥db\æ6Ã†$Ó\ni{µb\";#\Âiûª1R21s›aC’iO…´½Z1H‘†\á´}U‡˜F)™˜¹Í°!É´§‡B\Ú^­¤\È\Î\0ÃˆpÚ¾ªC?\æ\Û\õ\'©ıxšo\×œ¤:\ô\ãi¾]p’\ßúø·œşª\Ï\Ò\Ìù\÷9\r\"\ô\è5n\æŸ\"\äF\ôşH¥C¿ı±\ÓÀv¾N>›\îV$Ø¶¡!ŸM?ƒM}zH¿~\ìL“ÿC *Qœ­H°}e\å\ë\äl\÷\Ï}»\îCg¦]Ÿ¥ùøUj9ú¢g“­\r\Ú(lı§\ßı\n±\öù½º}¬rÇ¿5Ü¦l·\á?+N­Úˆ\í\×\õ&#T\Ö\nr…¤¬\ß\n?v¦\Éq¿NX\"{4µXûÙ…\íù\na·\í\Û\õûC\ßø±_UL|±\ì¦\Ú~¶uıhı¿\×\ß\ê\ëıBO¯\÷=½\Ş/\ô\ôz¿\Ğ\Ó\ëıBO¯¿\÷|\ê+¢U\ß\Ë\0\0\0\0IEND®B`‚','Eden Cheese',49.00,'165 g','DA',15,'F','2024-06-26 07:18:07','ACT'),(16,'PROD0016','4801668600542',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0‹\0\0\0p\0\0\0¡.r\0\0\n[IDATx^\í‘\Ñj%Kıÿ?½\Ë^sƒ@\ÒØƒsš¡\õTEªtt\èÿ¼z¶>¼z˜\Ş/\ôt½_\è\éz¿\Ğ\Ó\õ~¡§\ëıBO\×û…®\÷=]\ïzº\Ş/\ôt½_\è\éz¿\Ğ\Ó\õ~¡§\ëıBO\×û…®\÷=]\ïzº\Ş/\ôt½_\è\é:}¡ÿ=gú\àU@›gˆ·\í1‰UO±x&\ì\'LW:\ä®S\ÛD\İ(jh\ó\ñ¶=&±\ê‰\"Ï„ı„	\á\êC‡\Üu\nr›¨E\rm!Ş¶\Ç$V=Q\Äâ™°Ÿ0!\\}è»NAnu£¨¡\Í3\Ä\Û\ö˜Äª\'ŠX<\ö&„«r\×)\Èm¢n5´y†x\Û“X\õD‹g\Â~Â„p\õ¡C\î:¹MÔ¢†6\Ïo\Ûc«(b\ñL\ØO˜®>t\È]§ ·‰ºQ\Ô\Ğ\æ\âm{Lb\ÕE,	û	\ÂÕ‡¹\ë\ä6Q7Š\Z\Ú<C¼mI¬z¢ˆ\Å3a?aB¸ú\Ğ!w‚\Ü&\êFQC›gˆ·\í1‰UO±x&\ì\'LW:\ä®S\ÛD\İ(jh\ó\ñ¶=&±\ê‰\"Ï„ı„	\á\êC‡\Üu\nr›¨E\rm!Ş¶\Ç$V=Q\Äâ™°Ÿ0!\\}è»NAnu£¨¡\Í3\Ä\Û\ö˜Äª\'ŠX<\ö&„«r\×)\Èm¢n5´y†x\Û“X\õD‹g\Â~Â„p\õ¡C\î:¹MÔ¢†6\Ïo\Ûc«(b\ñL\ØO˜®>t\È]§ ·‰ºQ\Ô\Ğ\æ\âm{Lb\ÕE,	û	\ÂÕ‡¹\ë\ä6Q7Š\Z\Ú<C¼mI¬z¢ˆ\Å3a?aB¸ú\Ğ!w‚\Ü&\êFQC›gˆ·\í1‰UO±x&\ì\'LW:\ä®S\ÛD\İ(jh\ó\ñ¶=&±\ê‰\"Ï„ı„	\á\êC‡\Üu\nr›¨E\rm!Ş¶\Ç$V=Q\Äâ™°Ÿ0!\\}è»NAnu£¨¡\Í3\Ä\Û\ö˜Äª\'ŠX<\ö&„«r\×)\Èm¢n5´y†x\Û“X\õD‹g\Â~Â„p\õ¡C\î:¹MÔ¢†6\Ïo\Ûc«(b\ñL\ØO˜®>t\È]§ ·‰ºQ\Ô\Ğ\æ\âm{Lb\ÕE,	û	\ÂÕ‡¹\ë\ä6Q7Š\Z\Ú<C¼mI¬z¢ˆ\Å3a?aB¸ú\Ğ!w‚\Ü&\êFQC›gˆ·\í1‰UO±x&\ì\'LW:\ä®S\ÛD\İ(jh\ó\ñ¶=&±\ê‰\"Ï„ı„	\á\êC‡\Üu\nr›¨E\rm!Ş¶\Ç$V=Q\Äâ™°Ÿ0!\\}è»NAnu£¨¡\Í3\Ä\Û\ö˜Äª\'ŠX<\ö&„«r\×)\Èm¢n5´y†x\Û“X\õD‹g\Â~Â„p\õ¡C\î:¹MÔ¢†6\Ïo\Ûc«(b\ñL\ØO˜®>t\È]§ ·‰ºQ\Ô\Ğ\æ\âm{Lb\ÕE,	û	\ÂÕ‡¹\ë\ä6Q7Š\Z\Ú<C¼mI¬z¢ˆ\Å3a?aB¸ú\Ğ!w‚\Ü&\êFQC›gˆ·\í1‰UO±x&\ì\'LW:\ä®S\ÛD\İ(jh\ó\ñ¶=&±\ê‰\"Ï„ı„	\á\êC‡\Üu\nr›¨E\rm!Ş¶\Ç$V=Q\Äâ™°Ÿ0!\\}è»NAnu£¨¡\Í3\Ä\Û\ö˜Äª\'ŠX<\ö&„«r\×)\Èm¢n5´y†x\Û“X\õD‹g\Â~Â„p\õ¡C\î:¹MÔ¢†6\Ïo\Ûc«(b\ñL\ØO˜®>t\È]§ ·‰ºQ\Ô\Ğ\æ\âm{Lb\ÕE,	û	\ÂÕ‡¹\ë\ä6Q7Š\Z\Ú<C¼mI¬z¢ˆ\Å3a?aB¸ú\Ğ!w‚\Ü&\êFQC›gˆ·\í1‰UO±x&\ì\'LW:\ä®S\ÛD\İ(jh\ó\ñ¶=&±\ê‰\"Ï„ı„	\á\êC‡\Üu\nr›¨E\rm!Ş¶\Ç$V=Q\Äâ™°Ÿ0!\\}è»NAnu£¨¡\Í3\Ä\Û\ö˜Äª\'ŠX<\ö&„«r\×)\Èm¢n5´y†x\Û“X\õD‹g\Â~Â„p\õ¡C\î:¹MÔ¢†6\Ïo\Ûc«(b\ñL\ØO˜®>t\È]§ ·‰ºQ\Ô\Ğ\æ\âm{Lb\ÕE,	û	\ÂÕ‡¹\ë\ä6Q7Š\Z\Ú<C¼mI¬z¢ˆ\Å3a?aB¸ú\Ğ!w‚\Ü&\êFQC›gˆ·\í1‰UO±x&\ì\'LW:\ä®S\ÛD\İ(jh\ó\ñ¶=&±\ê‰\"Ï„ı„	\á\êC‡\Üu\nr›¨E\rm!Ş¶\Ç$V=Q\Äâ™°Ÿ0!\\}è»NAnu£¨¡\Í3\Ä\Û\ö˜Äª\'ŠX<\ö&„«r\×)\Èm¢n5´y†x\Û“X\õD‹g\Â~Â„p\õ¡C\î:¹MÔ¢†6\Ïo\Ûc«(b\ñL\ØO˜®>t\È]§ ·‰ºQ\Ô\Ğ\æ\âm{Lb\ÕE,	û	\ÂÕ‡¹\ë\ä6Q7Š\Z\Ú<C¼mI¬z¢ˆ\Å3a?aB¸ú\Ğ!w‚\Ü&\êFQC›gˆ·\í1‰UO±x&\ì\'LW:\ä®S\ÛD\İ(jh\ó\ñ¶=&±\ê‰\"Ï„ı„	\á\êC‡\Üu\nr›¨E\rm!Ş¶\Ç$V=Q\Äâ™°Ÿ0!\\}è»NAnu£¨¡\Í3\Ä\Û\ö˜Äª\'ŠX<\ö&„«r\×)\Èm¢n5´y†x\Û“X\õD‹g\Â~Â„p\õ¡C\î:¹MÔ¢†6\Ïo\Ûc«(b\ñL\ØO˜®>t\È]§ ·‰ºQ\Ô\Ğ\æ\âm{Lb\ÕE,	û	\ÂÕ‡¹\ë\ä6Q7Š\Z\Ú<C¼mI¬z¢ˆ\Å3a?aB¸ú\Ğ!w‚\Ü&\êFQC›gˆ·\í1‰UO±x&\ì\'LW:\ä®S\ÛD\İ(jh\ó\ñ¶=&±\ê‰\"Ï„ı„	\á\êC‡\Üu\nr›¨E\rm!Ş¶\Ç$V=Q\Äâ™°Ÿ0!\\}è»NAnu£¨¡\Í3\Ä\Û\ö˜Äª\'ŠX<\ö&„«r\×)\Èm¢n5´y†x\Û“X\õD‹g\Â~Â„p\õ¡C\î:¹MÔ¢†6\Ïo\Ûc«(b\ñL\ØO˜®>t\È]§ ·‰ºQ\Ô\Ğ\æ\âm{Lb\ÕE,	û	\ÂÕ‡¹\ë\ä6Q7Š\Z\Ú<C¼mI¬z¢ˆ\Å3a?aB¸ú\Ğ!w‚\Ü&\êFQC›gˆ·\í1‰UO±x&\ì\'LW:\ä®S\ÛD\İ(jh\ó\ñ¶=&±\ê‰\"Ï„ı„	\á\êC‡\Üu\nr›¨E\rm!Ş¶\Ç$V=Q\Äâ™°Ÿ0!\\}è»NAnu£¨¡\Í3\Ä\Û\ö˜Äª\'ŠX<\ö&„«r\×)è·ù±ş\â&§ ?\Ş\æ\Çú‹›œ‚şx›\ë/n\òM\Ğ\Ç?Jú¯~\Õ\æ\ó•y¸\Ïß¾jgÿ6\áb\rŸœùG\ôUĞ·?6\r¾¶!\Î\\\Û\ó;$®úü;$ ²¾\òu&üL¿úº\î§f›nß¤W\Ò\ä\óg®\r¿M˜$^\Ùf8I\Çµƒf¹Ö¯\Ú\Ä?ü\"\íO“Ïƒ“Q?‰k.\äWWH\ók}”\Ò\ñ>yl}mÃ¯\Ì\íù\×	}şb\á	yú„s\õ3}\ôÅ\Í6¾¶\áW\æ\öü‰\ë„>ÿŒX^}ªCú>\è?\ö\ñ¯¾ pŸ¿}\Õ\äkµÿgÄ«\Ïi¥\é\êÿ›ş\ê®\÷=]\ïzº\Ş/\ôt½_\è\éz¿\Ğ\Ó\õ_®\Å:0Š’B\0\0\0\0IEND®B`‚','Datu Puti Patis Pack',11.00,'150 ml','CO',16,'F','2024-06-26 07:20:10','ACT'),(17,'PROD0017','4801668602034',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0‹\0\0\0p\0\0\0¡.r\0\0	\ÛIDATx^\í‘\Ñj%\ÙC\óÿ?}/\Óf\ÄB\òqLœ¢)=ldy\Å\İ\õ\õ¿W\ÏÖ—¯¦\÷=]\ïzº\Ş/\ôt½_\è\éz¿\Ğ\Ó\õ~¡§\ëıBO\×û…®\÷=]\ïzº\Ş/\ôt½_\è\éz¿\Ğ\Ó\õ~¡§\ëıBO\×û…®\÷=]\ïzºN_\è\ëëŸŸ\ë¥\á\ÊB\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:\å¡2v(C\Âm	·\É0±U¾’ÕŠ135f¸\á/:ıú5?\Ö_|É©\è×¯ù±ş\âKNE¿~Í\õ_\òM\Ñ\×yú¯>]S¿b‰rúo•‰r½–\Ûğ³„¹%zESÑ·¬8&`^c2›\Ä\Æ6¤ÿ­\ä\Ó\öSx\Ñ\Ç\"ıáµ\×\Ìÿ«\å(\rI\ó\Z3\Ü7(\Ù0­\á˜?ü±ú¢ùJ\é\Ó5ÿü—ü‘F®\ä9&“I6Kùøbøw?1\Z-¼¨/ªk(\'ş¨r\ÛrL\àœ\Ì&±±\r\é3\Ù{&_¡”\õ}\Ñ\ğ\Ç\Úk\æs?Á\ÉlÛ>m~\õ)ÉŸ\õ}\Ñş\Ø×¿\Z\å\ô\ßş*“Y\É[¢±\r?ıŠj\Ã_\Ô\Ûş\ê®\÷=]\ïzº\Ş/\ôt½_\è\éz¿\Ğ\Ó\õ5¯	}+\0\0\0\0IEND®B`‚','Datu Puti Soy Sauce',10.00,'200 ml','CO',17,'F','2024-06-26 07:21:23','ACT'),(18,'PROD0018','4801668602027',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0‹\0\0\0p\0\0\0¡.r\0\0\n#IDATx^\í‘\ánIış/}‡Mc‰Ú¢l§­\Üa°şø‰ZùøÏ«g\ëÃ‹W\Óû…®\÷=]\ïzº\Ş/\ôt½_\è\éz¿\Ğ\Ó\õ~¡§\ëıBO\×û…®\÷=]\ïzº\Ş/\ôt½_\è\éz¿\Ğ\Ó\õ~¡§\ëıBO\×û…®\÷=]«/\ô\ñ\ñ\×?Ï¤a¤%á±„i3\Ü(\ê©6Œ\Ì-y}°DK­ŠxŞ C¹$<–0m†E=#Õ†‘¹%¡–h©U¯\Ñt(—„\Ç¦\Íp£¨g¤\Ú02·\ä1\ôÁ-µ*\â5zƒ\å’\ğXÂ´n\õŒTF\æ–<†>X¢¥VE¼FoĞ¡\\K˜6Ã¢‘j\Ã\ÈÜ’\Ç\ĞK´Ôªˆ\×\è\r:”K\Âc	\Óf¸Q\Ô3Rm™[\òú`‰–Z\ñ\Z½A‡rIx,a\Ú7ŠzFª\r#sKC,\ÑR«\"^£7\èP.	%L›\áFQ\ÏHµadn\Éc\èƒ%ZjU\Äk\ô\Ê%á±„i3\Ü(\ê©6Œ\Ì-y}°DK­ŠxŞ C¹$<–0m†E=#Õ†‘¹%¡–h©U¯\Ñt(—„\Ç¦\Íp£¨g¤\Ú02·\ä1\ôÁ-µ*\â5zƒ\å’\ğXÂ´n\õŒTF\æ–<†>X¢¥VE¼FoĞ¡\\K˜6Ã¢‘j\Ã\ÈÜ’\Ç\ĞK´Ôªˆ\×\è\r:”K\Âc	\Óf¸Q\Ô3Rm™[\òú`‰–Z\ñ\Z½A‡rIx,a\Ú7ŠzFª\r#sKC,\ÑR«\"^£7\èP.	%L›\áFQ\ÏHµadn\Éc\èƒ%ZjU\Äk\ô\Ê%á±„i3\Ü(\ê©6Œ\Ì-y}°DK­ŠxŞ C¹$<–0m†E=#Õ†‘¹%¡–h©U¯\Ñt(—„\Ç¦\Íp£¨g¤\Ú02·\ä1\ôÁ-µ*\â5zƒ\å’\ğXÂ´n\õŒTF\æ–<†>X¢¥VE¼FoĞ¡\\K˜6Ã¢‘j\Ã\ÈÜ’\Ç\ĞK´Ôªˆ\×\è\r:”K\Âc	\Óf¸Q\Ô3Rm™[\òú`‰–Z\ñ\Z½A‡rIx,a\Ú7ŠzFª\r#sKC,\ÑR«\"^£7\èP.	%L›\áFQ\ÏHµadn\Éc\èƒ%ZjU\Äk\ô\Ê%á±„i3\Ü(\ê©6Œ\Ì-y}°DK­ŠxŞ C¹$<–0m†E=#Õ†‘¹%¡–h©U¯\Ñt(—„\Ç¦\Íp£¨g¤\Ú02·\ä1\ôÁ-µ*\â5zƒ\å’\ğXÂ´n\õŒTF\æ–<†>X¢¥VE¼FoĞ¡\\K˜6Ã¢‘j\Ã\ÈÜ’\Ç\ĞK´Ôªˆ\×\è\r:”K\Âc	\Óf¸Q\Ô3Rm™[\òú`‰–Z\ñ\Z½A‡rIx,a\Ú7ŠzFª\r#sKC,\ÑR«\"^£7\èP.	%L›\áFQ\ÏHµadn\Éc\èƒ%ZjU\Äk\ô\Ê%á±„i3\Ü(\ê©6Œ\Ì-y}°DK­ŠxŞ C¹$<–0m†E=#Õ†‘¹%¡–h©U¯\Ñt(—„\Ç¦\Íp£¨g¤\Ú02·\ä1\ôÁ-µ*\â5zƒ\å’\ğXÂ´n\õŒTF\æ–<†>X¢¥VE¼FoĞ¡\\K˜6Ã¢‘j\Ã\ÈÜ’\Ç\ĞK´Ôªˆ\×\è\r:”K\Âc	\Óf¸Q\Ô3Rm™[\òú`‰–Z\ñ\Z½A‡rIx,a\Ú7ŠzFª\r#sKC,\ÑR«\"^£7\èP.	%L›\áFQ\ÏHµadn\Éc\èƒ%ZjU\Äk\ô\Ê%á±„i3\Ü(\ê©6Œ\Ì-y}°DK­ŠxŞ C¹$<–0m†E=#Õ†‘¹%¡–h©U¯\Ñt(—„\Ç¦\Íp£¨g¤\Ú02·\ä1\ôÁ-µ*\â5zƒ\å’\ğXÂ´n\õŒTF\æ–<†>X¢¥VE¼FoĞ¡\\K˜6Ã¢‘j\Ã\ÈÜ’\Ç\ĞK´Ôªˆ\×\è\r:”K\Âc	\Óf¸Q\Ô3Rm™[\òú`‰–Z\ñ\Z½A‡rIx,a\Ú7ŠzFª\r#sKC,\ÑR«\"^£7\èP.	%L›\áFQ\ÏHµadn\Éc\èƒ%ZjU\Äk\ô\Ê%á±„i3\Ü(\ê©6Œ\Ì-y}°DK­ŠxŞ C¹$<–0m†E=#Õ†‘¹%¡–h©U¯\Ñt(—„\Ç¦\Íp£¨g¤\Ú02·\ä1\ôÁ-µ*\â5zƒ\å’\ğXÂ´n\õŒTF\æ–<†>X¢¥VE¼FoĞ¡\\K˜6Ã¢‘j\Ã\ÈÜ’\Ç\ĞK´Ôªˆ\×\è\r:”K\Âc	\Óf¸Q\Ô3Rm™[\òú`‰–Z\ñ\Z½A‡rIx,a\Ú7ŠzFª\r#sKC,\ÑR«\"^£7\èP.	%L›\áFQ\ÏHµadn\Éc\èƒ%ZjU\Äk\ô\Ê%á±„i3\Ü(\ê©6Œ\Ì-y}°DK­ŠxŞ C¹$<–0m†E=#Õ†‘¹%¡–h©U¯\Ñt(—„\Ç¦\Íp£¨g¤\Ú02·\ä1\ôÁ-µ*\â5zƒ\å’\ğXÂ´n\õŒTF\æ–<†>X¢¥VE¼FoĞ¡\\K˜6Ã¢‘j\Ã\ÈÜ’\Ç\ĞK´Ôªˆ\×\è\r:”K\Âc	\Óf¸Q\Ô3Rm™[\òú`‰–Z\ñ\Z½A‡rIx,a\Ú7ŠzFª\r#sKC,\ÑR«\"^£7\èP.	%L›\áFQ\ÏHµadn\Éc\èƒ%ZjU\Äk\ô\Ê%á±„i3\Ü(\ê©6Œ\Ì-y}°DK­ŠxŞ C¹$<–0m†E=#Õ†‘¹%¡–h©U¯\Ñt(—„\Ç¦\Íp£¨g¤\Ú02·\ä1\ôÁ-µ*\â5zƒ\å’\ğXÂ´n\õŒTF\æ–<†>X¢¥VE¼FoĞ¡\\K˜6Ã¢‘j\Ã\ÈÜ’\Ç\ĞK´Ôªˆ\×\è\r:”K\Âc	\Óf¸Q\Ô3Rm™[\òú`‰–Z\ñ\Z½A‡rIx,a\Ú7ŠzFª\r#sKC,\ÑR«\"^£7\èP.	%L›\áFQ\ÏHµadn\Éc\èƒ%ZjU\Äk\ô\Ê%á±„i3\Ü(\ê©6Œ\Ì-y}°DK­ŠxŞ C¹$<–0m†E=#Õ†‘¹%¡–h©U¯\Ñt(—„\Ç¦\Íp£¨g¤\Ú02·\ä1\ôÁ-µ*\â5zƒ\å’\ğXÂ´n\õŒTF\æ–<†>X¢¥VEüš\ë_|Éª\è_\ócı‹/Yı\ñk~¬\ñ%\ß}ü’·\ë³kÎ¿\â¾7\Ù\Óû¯z“}¦\ö\ß6ü`“Ÿ\\&\Êü#úª\è\Û?6üÙ€|~6\ó;ı—\ôj\ó\Ù\Ïlzÿc}Z”¿\ñ\Å¯\é\÷\ô¦£\è‹\Í1\òù\Ù\Ë\ßo\È\æw˜\Ï~f\Óûk.úú\Ê\è³k~ıŸü¥üdÏŸ\Í\ô\æ6GıO\ô³\Ï\à\ßaÆŸ\\\Ñ\Ï4ıúOø‡Lü\Ò\Ù+\å\Ï>ƒ›ù~Kú\Şü\Ì¶\ÉrŒ~¦ï‹¾øc\ã5ıª\Ş\ô\Ïf~g£Ÿã’¾7Z\ê\çgÌ¸9\ê?´\Ô\÷E?øc\ë‹M\ö\ô\ßş«\Ş|­\æµ\É\Ïqù5s\ÌÿZÿ§?\ó\ê\Çz¿\Ğ\Ó\õ~¡§\ëıBO\×û…®\÷=]ÿhœn¥\î^\í\0\0\0\0IEND®B`‚','Datu Puti Vinegar',9.00,'200 ml','CO',18,'F','2024-06-26 07:22:25','ACT'),(19,'PROD0019','4800024013477',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0‹\0\0\0p\0\0\0¡.r\0\0\nwIDATx^\í‘\ájmk\n\óş/=Ã½rš¢4&\ÄÌ°8¬ş!\î²\í\í\ÇúøÏ«g\ë\Ã\à\Õ\Ã\ô~¡§\ëıBO\×û…®\÷=]\ïzº\Ş/\ôt½_\è\éz¿\Ğ\Ó\õ~¡§\ëıBO\×û…®\÷=]\ïzº\Ş/\ôt½_\è\éz¿\Ğ\Ó\õ~¡§\ëıBO\×\é}|ü³Ê†#AzFH\"H§j¦‚\\\Ï(\",WdÛ¡’/:\õ\'¥Ñ¡„$\ôŒDN\ÕL¹QDX®È¶C%_t\n\êOJ£C	I\è!‰ ª™\nr=£ˆ°<\\‘m‡J¾\èÔŸ”F‡’\Ğ3BA:U3\äzFay¸\"\Û•|\Ñ)¨?)%$¡g„$‚tªf*\È\õŒ\"\Â\òpE¶*ù¢SPR\ZJHB\ÏI\éT\ÍT\ëE„\å\áŠl;T\òE§ ş¤4:”„’Ò©š© \×3Š\Ë\Ã\Ùv¨\ä‹NAıIit(!	=#$¤S5SA®g–‡+²\íP\É‚ú“\Ò\èPBzFH\"H§j¦‚\\\Ï(\",WdÛ¡’/:\õ\'¥Ñ¡„$\ôŒDN\ÕL¹QDX®È¶C%_t\n\êOJ£C	I\è!‰ ª™\nr=£ˆ°<\\‘m‡J¾\èÔŸ”F‡’\Ğ3BA:U3\äzFay¸\"\Û•|\Ñ)¨?)%$¡g„$‚tªf*\È\õŒ\"\Â\òpE¶*ù¢SPR\ZJHB\ÏI\éT\ÍT\ëE„\å\áŠl;T\òE§ ş¤4:”„’Ò©š© \×3Š\Ë\Ã\Ùv¨\ä‹NAıIit(!	=#$¤S5SA®g–‡+²\íP\É‚ú“\Ò\èPBzFH\"H§j¦‚\\\Ï(\",WdÛ¡’/:\õ\'¥Ñ¡„$\ôŒDN\ÕL¹QDX®È¶C%_t\n\êOJ£C	I\è!‰ ª™\nr=£ˆ°<\\‘m‡J¾\èÔŸ”F‡’\Ğ3BA:U3\äzFay¸\"\Û•|\Ñ)¨?)%$¡g„$‚tªf*\È\õŒ\"\Â\òpE¶*ù¢SPR\ZJHB\ÏI\éT\ÍT\ëE„\å\áŠl;T\òE§ ş¤4:”„’Ò©š© \×3Š\Ë\Ã\Ùv¨\ä‹NAıIit(!	=#$¤S5SA®g–‡+²\íP\É‚ú“\Ò\èPBzFH\"H§j¦‚\\\Ï(\",WdÛ¡’/:\õ\'¥Ñ¡„$\ôŒDN\ÕL¹QDX®È¶C%_t\n\êOJ£C	I\è!‰ ª™\nr=£ˆ°<\\‘m‡J¾\èÔŸ”F‡’\Ğ3BA:U3\äzFay¸\"\Û•|\Ñ)¨?)%$¡g„$‚tªf*\È\õŒ\"\Â\òpE¶*ù¢SPR\ZJHB\ÏI\éT\ÍT\ëE„\å\áŠl;T\òE§ ş¤4:”„’Ò©š© \×3Š\Ë\Ã\Ùv¨\ä‹NAıIit(!	=#$¤S5SA®g–‡+²\íP\É‚ú“\Ò\èPBzFH\"H§j¦‚\\\Ï(\",WdÛ¡’/:\õ\'¥Ñ¡„$\ôŒDN\ÕL¹QDX®È¶C%_t\n\êOJ£C	I\è!‰ ª™\nr=£ˆ°<\\‘m‡J¾\èÔŸ”F‡’\Ğ3BA:U3\äzFay¸\"\Û•|\Ñ)¨?)%$¡g„$‚tªf*\È\õŒ\"\Â\òpE¶*ù¢SPR\ZJHB\ÏI\éT\ÍT\ëE„\å\áŠl;T\òE§ ş¤4:”„’Ò©š© \×3Š\Ë\Ã\Ùv¨\ä‹NAıIit(!	=#$¤S5SA®g–‡+²\íP\É‚ú“\Ò\èPBzFH\"H§j¦‚\\\Ï(\",WdÛ¡’/:\õ\'¥Ñ¡„$\ôŒDN\ÕL¹QDX®È¶C%_t\n\êOJ£C	I\è!‰ ª™\nr=£ˆ°<\\‘m‡J¾\èÔŸ”F‡’\Ğ3BA:U3\äzFay¸\"\Û•|\Ñ)¨?)%$¡g„$‚tªf*\È\õŒ\"\Â\òpE¶*ù¢SPR\ZJHB\ÏI\éT\ÍT\ëE„\å\áŠl;T\òE§ ş¤4:”„’Ò©š© \×3Š\Ë\Ã\Ùv¨\ä‹NAıIit(!	=#$¤S5SA®g–‡+²\íP\É‚ú“\Ò\èPBzFH\"H§j¦‚\\\Ï(\",WdÛ¡’/:\õ\'¥Ñ¡„$\ôŒDN\ÕL¹QDX®È¶C%_t\n\êOJ£C	I\è!‰ ª™\nr=£ˆ°<\\‘m‡J¾\èÔŸ”F‡’\Ğ3BA:U3\äzFay¸\"\Û•|\Ñ)¨?)%$¡g„$‚tªf*\È\õŒ\"\Â\òpE¶*ù¢SPR\ZJHB\ÏI\éT\ÍT\ëE„\å\áŠl;T\òE§ ş¤4:”„’Ò©š© \×3Š\Ë\Ã\Ùv¨\ä‹NAıIit(!	=#$¤S5SA®g–‡+²\íP\É‚ú“\Ò\èPBzFH\"H§j¦‚\\\Ï(\",WdÛ¡’/:\õ\'¥Ñ¡„$\ôŒDN\ÕL¹QDX®È¶C%_t\n\êOJ£C	I\è!‰ ª™\nr=£ˆ°<\\‘m‡J¾\èÔŸ”F‡’\Ğ3BA:U3\äzFay¸\"\Û•|\Ñ)¨?)%$¡g„$‚tªf*\È\õŒ\"\Â\òpE¶*ù¢SPR\ZJHB\ÏI\éT\ÍT\ëE„\å\áŠl;T\òE§ ş¤4:”„’Ò©š© \×3Š\Ë\Ã\Ùv¨\ä‹NAıIit(!	=#$¤S5SA®g–‡+²\íP\É‚ú“\Ò\èPBzFH\"H§j¦‚\\\Ï(\",WdÛ¡’/:\õ\'¥Ñ¡„$\ôŒDN\ÕL¹QDX®È¶C%_t\n\êOJ£C	I\è!‰ ª™\nr=£ˆ°<\\‘m‡J¾\èÔŸ”F‡’\Ğ3BA:U3\äzFay¸\"\Û•|\Ñ)¨?)%$¡g„$‚tªf*\È\õŒ\"\Â\òpE¶*ù¢SPR\ZJHB\ÏI\éT\ÍT\ëE„\å\áŠl;T\òE§ ş¤4:”„’Ò©š© \×3Š\Ë\Ã\Ùv¨\ä‹NAıIit(!	=#$¤S5SA®g–‡+²\íP\É‚ú“\Ò\èPBzFH\"H§j¦‚\\\Ï(\",WdÛ¡’/:\õ\'¥Ñ¡„$\ôŒDN\ÕL¹QDX®È¶C%_t\n\êOJ£C	I\è!‰ ª™\nr=£ˆ°<\\‘m‡J¾\èÔŸ”F‡’\Ğ3BA:U3\äzFay¸\"\Û•|\Ñ)\è×¯ù±ş\âKNA¿~Í\õ_r\nú\õk~¬¿ø’/‚>ş•\é}vMm‘ÿ!I\ï\æ\â\ì?\óŒa!\ÜJımA_ş\Ùh\à\Ïn¸Ï¦;ûü\\<\"ı\çB:ÿ±>\r\Z\ß \×\ì\ïü>¡ú1ãŸPı\è,\õ\ğ%p\'ÿXs\Ğø¶®Ï®ùø£ü\äè›¤ÿü\Ò3\ÂŞ?¿ü­Œ$pıLsP]L\Ù\ñ¯ŠkÊŸ\İ\ğ}2\ö’l$ú¹Œ™_\â”\ê$pıL_-6^ÃŸ\İ\ğ}\"(:\Â\Ş\ó\ç\â!YF\Òx\ÉE_ı\à\Ï>ş\èB\ò“0\êd\Ôw\Ö?\ó\ÔHM\Ô\ÉÿHÿ§¿y\õc½_\è\éz¿\Ğ\Ó\õ~¡§\ëıBO\×û…®ÿ”<J«i	©\0\0\0\0IEND®B`‚','DM Original Ketchup Pack',35.00,'320 g','CO',19,'F','2024-06-26 07:25:15','ACT'),(20,'PROD0020','4801668600641',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0‹\0\0\0p\0\0\0¡.r\0\0	\ËIDATx^\í‘\Ñj5\éB\óş/}3ad¡V\ò³«š¡½\Û2n\Ã\÷\õ¿\ÏÆ—/†\÷…\÷…\÷…\÷…\÷…\÷…\÷…\÷…\÷…\÷…\÷…\÷…\÷…\÷…\÷…\÷…\÷…\÷…\Õ}}ı\õ\çb\n\Ìd¸–\ğš:vJ¬V™jŠ…)Ÿš|‚U‘m¢°¡4®%¼f†’«U¦šbaÊ§&Ÿ`Ud›(l(M†k	¯™¡c§dÁj•©¦X˜\ò©\É\'X\Ù&\nJ“\áZ\Âkf\è\Ø)Y°Zeª)¦|j\ò	VE¶‰Â†\Òd¸–\ğš:vJ¬V™jŠ…)Ÿš|‚U‘m¢°¡4®%¼f†’«U¦šbaÊ§&Ÿ`Ud›(l(M†k	¯™¡c§dÁj•©¦X˜\ò©\É\'X\Ù&\nJ“\áZ\Âkf\è\Ø)Y°Zeª)¦|j\ò	VE¶‰Â†\Òd¸–\ğš:vJ¬V™jŠ…)Ÿš|‚U‘m¢°¡4®%¼f†’«U¦šbaÊ§&Ÿ`Ud›(l(M†k	¯™¡c§dÁj•©¦X˜\ò©\É\'X\Ù&\nJ“\áZ\Âkf\è\Ø)Y°Zeª)¦|j\ò	VE¶‰Â†\Òd¸–\ğš:vJ¬V™jŠ…)Ÿš|‚U‘m¢°¡4®%¼f†’«U¦šbaÊ§&Ÿ`Ud›(l(M†k	¯™¡c§dÁj•©¦X˜\ò©\É\'X\Ù&\nJ“\áZ\Âkf\è\Ø)Y°Zeª)¦|j\ò	VE¶‰Â†\Òd¸–\ğš:vJ¬V™jŠ…)Ÿš|‚U‘m¢°¡4®%¼f†’«U¦šbaÊ§&Ÿ`Ud›(l(M†k	¯™¡c§dÁj•©¦X˜\ò©\É\'X\Ù&\nJ“\áZ\Âkf\è\Ø)Y°Zeª)¦|j\ò	VE¶‰Â†\Òd¸–\ğš:vJ¬V™jŠ…)Ÿš|‚U‘m¢°¡4®%¼f†’«U¦šbaÊ§&Ÿ`Ud›(l(M†k	¯™¡c§dÁj•©¦X˜\ò©\É\'X\Ù&\nJ“\áZ\Âkf\è\Ø)Y°Zeª)¦|j\ò	VE¶‰Â†\Òd¸–\ğš:vJ¬V™jŠ…)Ÿš|‚U‘m¢°¡4®%¼f†’«U¦šbaÊ§&Ÿ`Ud›(l(M†k	¯™¡c§dÁj•©¦X˜\ò©\É\'X\Ù&\nJ“\áZ\Âkf\è\Ø)Y°Zeª)¦|j\ò	VE¶‰Â†\Òd¸–\ğš:vJ¬V™jŠ…)Ÿš|‚U‘m¢°¡4®%¼f†’«U¦šbaÊ§&Ÿ`Ud›(l(M†k	¯™¡c§dÁj•©¦X˜\ò©\É\'X\Ù&\nJ“\áZ\Âkf\è\Ø)Y°Zeª)¦|j\ò	VE¶‰Â†\Òd¸–\ğš:vJ¬V™jŠ…)Ÿš|‚U‘m¢°¡4®%¼f†’«U¦šbaÊ§&Ÿ`Ud›(l(M†k	¯™¡c§dÁj•©¦X˜\ò©\É\'X\Ù&\nJ“\áZ\Âkf\è\Ø)Y°Zeª)¦|j\ò	VE¶‰Â†\Òd¸–\ğš:vJ¬V™jŠ…)Ÿš|‚U‘m¢°¡4®%¼f†’«U¦šbaÊ§&Ÿ`Ud›(l(M†k	¯™¡c§dÁj•©¦X˜\ò©\É\'X\Ù&\nJ“\áZ\Âkf\è\Ø)Y°Zeª)¦|j\ò	VE¶‰Â†\Òd¸–\ğš:vJ¬V™jŠ…)Ÿš|‚U‘m¢°¡4®%¼f†’«U¦šbaÊ§&Ÿ`Ud›(l(M†k	¯™¡c§dÁj•©¦X˜\ò©\É\'X\Ù&\nJ“\áZ\Âkf\è\Ø)Y°Zeª)¦|j\ò	VE¶‰Â†\Òd¸–\ğš:vJ¬V™jŠ…)Ÿš|‚U‘m¢°¡4®%¼f†’«U¦šbaÊ§&Ÿ`Ud›(l(M†k	¯™¡c§dÁj•©¦X˜\ò©\É\'X\Ù&\nJ“\áZ\Âkf\è\Ø)Y°Zeª)¦|j\ò	VE¶‰Â†\Òd¸–\ğš:vJ¬V™jŠ…)Ÿš|‚U‘m¢°¡4®%¼f†’«U¦šbaÊ§&Ÿ`Ud›(l(M†k	¯™¡c§dÁj•©¦X˜\ò©\É\'X\Ù&\nJ“\áZ\Âkf\è\Ø)Y°Zeª)¦|j\ò	VE¶‰Â†\Òd¸–\ğš:vJ¬V™jŠ…)Ÿš|‚U‘m¢°¡4®%¼f†’«U¦šbaÊ§&Ÿ`Ud›(l(M†k	¯™¡c§dÁj•©¦X˜\ò©\É\'X\Ù&\nJ“\áZ\Âkf\è\Ø)Y°Zeª)¦|j\ò	VE¶‰Â†\Òd¸–\ğš:vJ¬V™jŠ…)Ÿš|‚U‘m¢°¡4®%¼f†’«U¦šbaÊ§&Ÿ`Ud›(l(M†k	¯™¡c§dÁj•©¦X˜\ò©\É\'X\Ù&\nJ“\áZ\Âkf\è\Ø)Y°Zeª)¦|j\ò	VE¶‰Â†\Òd¸–\ğš:vJ¬V™jŠ…)Ÿš|‚U‘m¢°¡4®%¼f†’«U¦šbaÊ§&Ÿ`Ud›(l(M†k	¯™¡c§dÁj•©¦X˜\ò©\É\'X\Ù&\nJ“\áZ\Âkf\è\Ø)Y°Zeª)¦|j\ò	VE¶‰Â†\Òd¸–\ğš:vJ¬V™jŠ…)Ÿš|‚U‘m¢°¡4®%¼f†’«U¦šbaÊ§&Ÿ`Ud›(l(M†k	¯™¡c§dÁj•©¦X˜\ò©\É\'X\Ù&\nJ“\áZ\Âkf\è\Ø)Y°Zeª)¦|j\ò	VE¶‰Â†\Òd¸–\ğš:vJ¬V™jŠ…)Ÿš|‚U‘m¢°¡4®%¼f†’«U¦šbaÊ§&Ÿ`Ud›(l(M†k	¯™¡c§dÁj•©¦X˜\ò©\É\'X\Ù&\nJ“\áZ\Âkf\è\Ø)Y°Zeª)¦|j\ò	VE¶‰Â†\Òd¸–\ğš:vJ¬V™jŠ…)Ÿš|‚U‘m¢°¡4®%¼f†’«U¦šbaÊ§&Ÿ`Ud›(l(M†k	¯™¡c§dÁj•©¦X˜\ò©\É\'X\Ù&\nJ“\áZ\Âkf\è\Ø)Y°Zeª)¦|j\ò	VE\çk>\ÆxÉª\è|\Í\Çø/Y¯ùÿ\á%¿}ı\rwÿÁ´\æû¯\è§#Ÿú×¿JG¾\Øü_şÄ¡o?\å¤ÿ1~*ú\õ\Çj€Ÿ0­\Ï\Ìü‰cŸÕ¤ş\Ìù\áj¿ømŠO0\é7~ø±º\æ\×ÿª¾ÿ\Ûoü\à|\ÓúL\ó×†\ê\Ø_1f‚™ŸıÑ‹\ê¸Ä´\æŸ\Ğÿ+\Óü\ÌL:ß‚\ÍBş‰}f`\ãLŸr\Òÿ½\è+\à‰¿\ñ\íÛ•Ÿ˜Â™ù\Ç>«Iı±c°\0\ñ\í¤ÿ1~/ú\á\Ç\êšü\Ò\É\Ï\Ìü‰cŸÕ¤ş\Ì!~ø	:\éŒß‹>ø±¯\ğƒ#Ÿú×¿J\çgdş3‡§>ÿ\rü\ë?\ğb‰\÷…\÷…\÷…\÷…\÷…ÿ^Vz\Ü!p\0\0\0\0IEND®B`‚','Mang Tomas Sachet',10.00,'100 g','CO',20,'F','2024-06-26 07:27:09','ACT');
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `return_products`
--

LOCK TABLES `return_products` WRITE;
/*!40000 ALTER TABLE `return_products` DISABLE KEYS */;
INSERT INTO `return_products` VALUES (1,1,2,'WRO','2024-06-28','PRO'),(2,1,2,'DEF','2024-06-28','PRO'),(3,1,5,'WRO','2024-06-28','PRO'),(4,2,1,'EXP','2024-06-28','PRO'),(5,2,2,'EXP','2024-06-28','PRO');
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
INSERT INTO `sales_summary` VALUES ('2024-06-26',8,16,2,24.48,0.00,204.00),('2024-06-28',8,16,6,108.29,39.60,942.00);
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (1,'BF2A0006','33047021','2024-06-26','15:48:10',118,0,14.16,132.16,NULL),(2,'801DFC5F','54603066','2024-06-26','16:31:33',86,0,10.32,96.32,NULL),(3,'3AE98863','04420162','2024-06-28','11:46:35',198,39.6,19.008,177.40800000000002,NULL),(5,'7A82009F','79604903','2024-06-28','14:49:29',186,0,22.32,208.32,NULL),(7,'5EDD6DB5','91092108','2024-06-28','15:10:03',34,0,4.08,38.08,NULL),(9,'E89382F0','38067583','2024-06-28','15:17:15',86,0,10.32,96.32,NULL),(11,'B9FD0DB4','40005776','2024-06-28','15:22:49',262,0,31.439999999999998,293.44,NULL),(13,'736D4AB2','21520688','2024-06-28','15:26:28',176,0,21.119999999999997,197.12,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_logs`
--

LOCK TABLES `user_logs` WRITE;
/*!40000 ALTER TABLE `user_logs` DISABLE KEYS */;
INSERT INTO `user_logs` VALUES (1,1,'User registered','2024-06-26 06:45:16','2024-06-26 06:45:16'),(2,2,'User registered','2024-06-26 06:46:06','2024-06-26 06:46:06'),(3,2,'User logged in','2024-06-26 06:46:43','2024-06-26 06:46:43'),(4,1,'User logged in','2024-06-26 06:46:52','2024-06-26 06:46:52'),(5,2,'User logged in','2024-06-26 07:42:59','2024-06-26 07:42:59'),(6,1,'User logged in','2024-06-26 07:51:05','2024-06-26 07:51:05'),(7,1,'User logged in','2024-06-26 08:03:21','2024-06-26 08:03:21'),(8,1,'User logged in','2024-06-26 08:07:57','2024-06-26 08:07:57'),(9,1,'User logged in','2024-06-26 08:12:33','2024-06-26 08:12:33'),(10,2,'User login failed','2024-06-26 08:16:24','2024-06-26 08:16:24'),(11,2,'User logged in','2024-06-26 08:16:28','2024-06-26 08:16:28'),(12,1,'User logged in','2024-06-26 08:30:30','2024-06-26 08:30:30'),(13,2,'User logged in','2024-06-26 08:30:47','2024-06-26 08:30:47'),(14,1,'User logged in','2024-06-26 08:32:21','2024-06-26 08:32:21'),(15,1,'User logged in','2024-06-26 08:35:53','2024-06-26 08:35:53'),(16,1,'User logged in','2024-06-26 08:37:57','2024-06-26 08:37:57'),(17,1,'User logged in','2024-06-26 08:46:30','2024-06-26 08:46:30'),(18,1,'User logged in','2024-06-26 08:47:09','2024-06-26 08:47:09'),(19,1,'User logged in','2024-06-26 08:48:02','2024-06-26 08:48:02'),(20,1,'User logged in','2024-06-26 08:55:00','2024-06-26 08:55:00'),(21,1,'User logged in','2024-06-26 09:00:30','2024-06-26 09:00:30'),(22,1,'User logged in','2024-06-26 09:01:04','2024-06-26 09:01:04'),(23,1,'User logged in','2024-06-26 09:04:49','2024-06-26 09:04:49'),(24,1,'User logged in','2024-06-26 09:08:35','2024-06-26 09:08:35'),(25,1,'User logged in','2024-06-26 09:09:03','2024-06-26 09:09:03'),(26,1,'User logged in','2024-06-26 09:09:29','2024-06-26 09:09:29'),(27,1,'User login failed','2024-06-26 09:10:56','2024-06-26 09:10:56'),(28,1,'User login failed','2024-06-26 09:11:01','2024-06-26 09:11:01'),(29,1,'User logged in','2024-06-26 09:11:05','2024-06-26 09:11:05'),(30,1,'User logged in','2024-06-26 09:34:26','2024-06-26 09:34:26'),(31,1,'User logged in','2024-06-26 09:35:06','2024-06-26 09:35:06'),(32,1,'User logged in','2024-06-26 10:02:13','2024-06-26 10:02:13'),(33,2,'User logged in','2024-06-26 11:18:31','2024-06-26 11:18:31'),(34,1,'User logged in','2024-06-28 00:16:25','2024-06-28 00:16:25'),(35,1,'User logged in','2024-06-28 01:02:57','2024-06-28 01:02:57'),(36,1,'User logged in','2024-06-28 01:26:07','2024-06-28 01:26:07'),(37,1,'User logged in','2024-06-28 01:37:29','2024-06-28 01:37:29'),(38,1,'User logged in','2024-06-28 01:43:03','2024-06-28 01:43:03'),(39,1,'User logged in','2024-06-28 01:47:43','2024-06-28 01:47:43'),(40,1,'User login failed','2024-06-28 02:03:33','2024-06-28 02:03:33'),(41,1,'User logged in','2024-06-28 02:03:39','2024-06-28 02:03:39'),(42,1,'User logged in','2024-06-28 02:42:58','2024-06-28 02:42:58'),(43,1,'User logged in','2024-06-28 02:48:51','2024-06-28 02:48:51'),(44,1,'User logged in','2024-06-28 02:50:13','2024-06-28 02:50:13'),(45,1,'User logged in','2024-06-28 02:52:37','2024-06-28 02:52:37'),(46,1,'User logged in','2024-06-28 02:55:01','2024-06-28 02:55:01'),(47,1,'User logged in','2024-06-28 02:57:00','2024-06-28 02:57:00'),(48,1,'User logged in','2024-06-28 03:01:15','2024-06-28 03:01:15'),(49,1,'User logged in','2024-06-28 03:06:14','2024-06-28 03:06:14'),(50,1,'User login failed','2024-06-28 03:29:17','2024-06-28 03:29:17'),(51,1,'User logged in','2024-06-28 03:29:22','2024-06-28 03:29:22'),(52,1,'User login failed','2024-06-28 03:31:44','2024-06-28 03:31:44'),(53,1,'User logged in','2024-06-28 03:31:48','2024-06-28 03:31:48'),(54,1,'User logged in','2024-06-28 03:36:31','2024-06-28 03:36:31'),(55,1,'User logged in','2024-06-28 03:37:52','2024-06-28 03:37:52'),(56,1,'User logged in','2024-06-28 03:39:57','2024-06-28 03:39:57'),(57,1,'User login failed','2024-06-28 03:40:31','2024-06-28 03:40:31'),(58,1,'User logged in','2024-06-28 03:40:32','2024-06-28 03:40:32'),(59,1,'User logged in','2024-06-28 03:40:57','2024-06-28 03:40:57'),(60,1,'User logged in','2024-06-28 03:41:13','2024-06-28 03:41:13'),(61,2,'User logged in','2024-06-28 03:44:04','2024-06-28 03:44:04'),(62,1,'User logged in','2024-06-28 03:47:25','2024-06-28 03:47:25'),(63,1,'User logged in','2024-06-28 04:46:48','2024-06-28 04:46:48'),(64,1,'User logged in','2024-06-28 04:50:36','2024-06-28 04:50:36'),(65,1,'User logged in','2024-06-28 04:53:25','2024-06-28 04:53:25'),(66,1,'User logged in','2024-06-28 04:57:03','2024-06-28 04:57:03'),(67,1,'User logged in','2024-06-28 05:08:14','2024-06-28 05:08:14'),(68,1,'User logged in','2024-06-28 05:15:36','2024-06-28 05:15:36'),(69,1,'User logged in','2024-06-28 05:19:25','2024-06-28 05:19:25'),(70,1,'User logged in','2024-06-28 05:20:43','2024-06-28 05:20:43'),(71,1,'User logged in','2024-06-28 05:22:24','2024-06-28 05:22:24'),(72,1,'User logged in','2024-06-28 05:23:51','2024-06-28 05:23:51'),(73,1,'User logged in','2024-06-28 05:40:38','2024-06-28 05:40:38'),(74,1,'User logged in','2024-06-28 05:42:57','2024-06-28 05:42:57'),(75,1,'User logged in','2024-06-28 05:45:22','2024-06-28 05:45:22'),(76,1,'User logged in','2024-06-28 05:46:49','2024-06-28 05:46:49'),(77,2,'User logged in','2024-06-28 05:46:57','2024-06-28 05:46:57'),(78,1,'User logged in','2024-06-28 05:49:52','2024-06-28 05:49:52'),(79,1,'User logged in','2024-06-28 06:00:27','2024-06-28 06:00:27'),(80,1,'User logged in','2024-06-28 06:20:41','2024-06-28 06:20:41'),(81,1,'User logged in','2024-06-28 06:28:37','2024-06-28 06:28:37'),(82,1,'User logged in','2024-06-28 06:33:06','2024-06-28 06:33:06'),(83,1,'User logged in','2024-06-28 06:34:18','2024-06-28 06:34:18'),(84,1,'User logged in','2024-06-28 06:36:16','2024-06-28 06:36:16'),(85,1,'User logged in','2024-06-28 06:42:25','2024-06-28 06:42:25'),(86,1,'User logged in','2024-06-28 06:44:40','2024-06-28 06:44:40'),(87,1,'User logged in','2024-06-28 06:45:28','2024-06-28 06:45:28'),(88,1,'User logged in','2024-06-28 06:46:39','2024-06-28 06:46:39'),(89,2,'User logged in','2024-06-28 06:47:41','2024-06-28 06:47:41'),(90,2,'User logged in','2024-06-28 06:49:10','2024-06-28 06:49:10'),(91,1,'User logged in','2024-06-28 06:51:47','2024-06-28 06:51:47'),(92,1,'User logged in','2024-06-28 06:53:52','2024-06-28 06:53:52'),(93,1,'User logged in','2024-06-28 06:56:32','2024-06-28 06:56:32'),(94,2,'User logged in','2024-06-28 07:01:11','2024-06-28 07:01:11'),(95,1,'User logged in','2024-06-28 07:15:31','2024-06-28 07:15:31'),(96,2,'User logged in','2024-06-28 07:16:39','2024-06-28 07:16:39'),(97,1,'User logged in','2024-06-28 07:17:40','2024-06-28 07:17:40'),(98,1,'User logged in','2024-06-28 07:21:49','2024-06-28 07:21:49'),(99,2,'User login failed','2024-06-28 07:22:00','2024-06-28 07:22:00'),(100,2,'User logged in','2024-06-28 07:22:04','2024-06-28 07:22:04'),(101,1,'User logged in','2024-06-28 07:23:06','2024-06-28 07:23:06'),(102,2,'User logged in','2024-06-28 07:25:56','2024-06-28 07:25:56'),(103,1,'User logged in','2024-06-28 07:26:52','2024-06-28 07:26:52');
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

-- Dump completed on 2024-06-28 15:30:22
