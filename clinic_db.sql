-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: clinic_db
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `image_url` varchar(999) DEFAULT NULL,
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'mig','11526882','doctor',NULL),(2,'mitch','11545569','doctor',NULL),(3,'shad','11545321','doctor',NULL),(4,'ivan','11534346','secretary',NULL),(5,'don','11536462','client',NULL),(6,'plinky','11534583','client',NULL);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appointment` (
  `appointment_id` int(11) NOT NULL AUTO_INCREMENT,
  `time_start` varchar(45) NOT NULL,
  `time_end` varchar(45) NOT NULL,
  `doctor_id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `recurring` tinyint(4) NOT NULL DEFAULT '0',
  `except_dates` varchar(999) DEFAULT NULL,
  `approved` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`appointment_id`),
  KEY `fk_appointment_doctor1_idx` (`doctor_id`),
  KEY `fk_appointment_client1_idx` (`client_id`),
  CONSTRAINT `fk_appointment_client1` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_appointment_doctor1` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`doctor_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES (1,'2018/04/17 07:30 am','2018/04/17 08:00 am',1,1,0,NULL,1),(2,'2018/04/18 07:30 am','2018/04/18 08:00 am',1,2,0,NULL,1),(3,'2018/04/19 07:30 am','2018/04/19 08:00 am',1,1,0,NULL,1),(4,'2018/04/20 07:30 am','2018/04/20 08:00 am',1,2,0,NULL,1),(5,'2018/04/21 07:30 am','2018/04/21 08:00 am',1,1,0,NULL,1),(6,'2018/04/22 07:30 am','2018/04/22 08:00 am',1,2,0,NULL,1),(7,'2018/04/23 07:30 am','2018/04/23 08:00 am',1,1,0,NULL,1),(8,'2018/04/17 05:30 pm','2018/04/17 06:00 pm',2,1,0,NULL,1),(9,'2018/04/18 06:00 pm','2018/04/18 06:30 pm',2,2,0,NULL,1),(10,'2018/04/18 06:30 pm','2018/04/18 07:00 pm',2,1,0,NULL,1),(11,'2018/04/18 02:30 pm','2018/04/18 04:30 pm',1,1,0,NULL,1);
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `availability`
--

DROP TABLE IF EXISTS `availability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `availability` (
  `doctor_id` int(11) NOT NULL,
  `time_start` varchar(45) NOT NULL,
  `time_end` varchar(45) NOT NULL,
  `recurring` varchar(45) NOT NULL,
  `except_dates` varchar(999) DEFAULT NULL,
  KEY `fk_availability_doctor_idx` (`doctor_id`),
  CONSTRAINT `fk_availability_doctor` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`doctor_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `availability`
--

LOCK TABLES `availability` WRITE;
/*!40000 ALTER TABLE `availability` DISABLE KEYS */;
INSERT INTO `availability` VALUES (1,'2018/04/18 08:30 am','2018/04/18 11:30 am','0',NULL),(2,'2018/04/18 10:30 am','2018/04/18 02:30 pm','0',NULL);
/*!40000 ALTER TABLE `availability` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client` (
  `client_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `contact` varchar(45) DEFAULT NULL,
  `account_id` int(11) DEFAULT NULL,
  `type` varchar(45) DEFAULT 'normal',
  PRIMARY KEY (`client_id`),
  KEY `fk_client_account1_idx` (`account_id`),
  CONSTRAINT `fk_client_account1` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,'Khaldon','Minaga',NULL,5,'normal'),(2,'Plinky','Gamara',NULL,6,'normal');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctor` (
  `doctor_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `account_id` int(11) NOT NULL,
  PRIMARY KEY (`doctor_id`),
  KEY `fk_doctor_account1_idx` (`account_id`),
  CONSTRAINT `fk_doctor_account1` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES (1,'Miguel','Quiambao',1),(2,'Mitchell','Ong',2),(3,'Shadrach','Penano',3);
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `secretary`
--

DROP TABLE IF EXISTS `secretary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `secretary` (
  `secretary_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `account_id` int(11) NOT NULL,
  PRIMARY KEY (`secretary_id`),
  KEY `fk_secretary_account1_idx` (`account_id`),
  CONSTRAINT `fk_secretary_account1` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `secretary`
--

LOCK TABLES `secretary` WRITE;
/*!40000 ALTER TABLE `secretary` DISABLE KEYS */;
INSERT INTO `secretary` VALUES (1,'Ivan','Dichaves',4);
/*!40000 ALTER TABLE `secretary` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-21 18:03:35
