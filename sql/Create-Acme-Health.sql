start transaction;

create database `Acme-Health`;

use `Acme-Health`;

create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';

create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete 
		on `Acme-Health`.* to 'acme-user'@'%';

grant select, insert, update, delete, create, drop, references, index, alter, 
	        create temporary tables, lock tables, create view, create routine,
	        alter routine, execute, trigger, show view
	    on `Acme-Health`.* to 'acme-manager'@'%';



-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: Acme-Health
-- ------------------------------------------------------
-- Server version	5.5.29

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
-- Table structure for table `actor`
--

DROP TABLE IF EXISTS `actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor` (
  `emailAddress` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_cgls5lrufx91ufsyh467spwa3` (`userAccount_id`),
  CONSTRAINT `FK_b88pmjby6mc0hms0vb18apeyk` FOREIGN KEY (`id`) REFERENCES `domainentity` (`id`),
  CONSTRAINT `FK_cgls5lrufx91ufsyh467spwa3` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor`
--

LOCK TABLES `actor` WRITE;
/*!40000 ALTER TABLE `actor` DISABLE KEYS */;
INSERT INTO `actor` VALUES ('luidia@gmail.com','Luis','Diaz',3,1),('rosa@gmail.com','Rosa','Diaz',4,2);
/*!40000 ALTER TABLE `actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_jd0370oy76ntbbd6qi0ofdqjs` FOREIGN KEY (`id`) REFERENCES `actor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (3),(4);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appointment` (
  `isFinish` bit(1) NOT NULL,
  `purpose` varchar(255) DEFAULT NULL,
  `result` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `medicalHistory_id` int(11) NOT NULL,
  `offer_id` int(11) DEFAULT NULL,
  `patient_id` int(11) NOT NULL,
  `specialist_id` int(11) DEFAULT NULL,
  `timetable_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_4ajkslvij8b42ayk3lll51xn9` (`isFinish`),
  KEY `FK_8xbg6q5c282d9eiv29cbb5oai` (`medicalHistory_id`),
  KEY `FK_lvbgpd4u1lsx205gbbwllc7oc` (`offer_id`),
  KEY `FK_oy39osrhv8yum5bv1i82y98b2` (`patient_id`),
  KEY `FK_q2f0885gb665q7dm2w6rtvpy9` (`specialist_id`),
  KEY `FK_bunhtffcpc77xpevdr6b2g1pp` (`timetable_id`),
  CONSTRAINT `FK_e15oc0y91ikxfi14m0cxleyki` FOREIGN KEY (`id`) REFERENCES `slot` (`id`),
  CONSTRAINT `FK_8xbg6q5c282d9eiv29cbb5oai` FOREIGN KEY (`medicalHistory_id`) REFERENCES `medicalhistory` (`id`),
  CONSTRAINT `FK_bunhtffcpc77xpevdr6b2g1pp` FOREIGN KEY (`timetable_id`) REFERENCES `timetable` (`id`),
  CONSTRAINT `FK_lvbgpd4u1lsx205gbbwllc7oc` FOREIGN KEY (`offer_id`) REFERENCES `offer` (`id`),
  CONSTRAINT `FK_oy39osrhv8yum5bv1i82y98b2` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`),
  CONSTRAINT `FK_q2f0885gb665q7dm2w6rtvpy9` FOREIGN KEY (`specialist_id`) REFERENCES `specialist` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `creationMoment` datetime DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `patient_id` int(11) NOT NULL,
  `profile_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_o471hit4efrlia9b407qotvd3` (`patient_id`),
  KEY `FK_dly47jb0hatqfnphqhaypbbqn` (`profile_id`),
  CONSTRAINT `FK_efqbpcxw2bbqrq93yvvkswr7e` FOREIGN KEY (`id`) REFERENCES `domainentity` (`id`),
  CONSTRAINT `FK_dly47jb0hatqfnphqhaypbbqn` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`),
  CONSTRAINT `FK_o471hit4efrlia9b407qotvd3` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_ncsqu0mq8fmfoi1wuh7wxs0jx` FOREIGN KEY (`id`) REFERENCES `actor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `domainentity`
--

DROP TABLE IF EXISTS `domainentity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `domainentity` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `domainentity`
--

LOCK TABLES `domainentity` WRITE;
/*!40000 ALTER TABLE `domainentity` DISABLE KEYS */;
INSERT INTO `domainentity` VALUES (1,0),(2,0),(3,0),(4,0);
/*!40000 ALTER TABLE `domainentity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `freeday`
--

DROP TABLE IF EXISTS `freeday`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `freeday` (
  `description` varchar(255) DEFAULT NULL,
  `finishMoment` datetime DEFAULT NULL,
  `id` int(11) NOT NULL,
  `specialist_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_d66cgqjq9yf1o3sehbdlq6ohp` (`specialist_id`),
  CONSTRAINT `FK_8jlbedgrph6pql8vg9doqxmfj` FOREIGN KEY (`id`) REFERENCES `slot` (`id`),
  CONSTRAINT `FK_d66cgqjq9yf1o3sehbdlq6ohp` FOREIGN KEY (`specialist_id`) REFERENCES `specialist` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `freeday`
--

LOCK TABLES `freeday` WRITE;
/*!40000 ALTER TABLE `freeday` DISABLE KEYS */;
/*!40000 ALTER TABLE `freeday` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('DomainEntity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicalhistory`
--

DROP TABLE IF EXISTS `medicalhistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicalhistory` (
  `allergy` varchar(255) DEFAULT NULL,
  `bloodGroup` varchar(255) DEFAULT NULL,
  `incompatibilities` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `patient_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_57vewxt2xp70tbfyhyh64py73` (`patient_id`),
  CONSTRAINT `FK_miufstel0ci4qwfbpe41oyjv7` FOREIGN KEY (`id`) REFERENCES `domainentity` (`id`),
  CONSTRAINT `FK_57vewxt2xp70tbfyhyh64py73` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicalhistory`
--

LOCK TABLES `medicalhistory` WRITE;
/*!40000 ALTER TABLE `medicalhistory` DISABLE KEYS */;
/*!40000 ALTER TABLE `medicalhistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `creationMoment` datetime DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `textBody` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `recipient_id` int(11) NOT NULL,
  `sender_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_q55vuhpo0cr2pdrb0rejw3bmi` (`recipient_id`),
  KEY `FK_tbto6hemu447oixxk730el2vx` (`sender_id`),
  CONSTRAINT `FK_1lk61g74f6w90a3jabg0gkr68` FOREIGN KEY (`id`) REFERENCES `domainentity` (`id`),
  CONSTRAINT `FK_q55vuhpo0cr2pdrb0rejw3bmi` FOREIGN KEY (`recipient_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `FK_tbto6hemu447oixxk730el2vx` FOREIGN KEY (`sender_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `offer`
--

DROP TABLE IF EXISTS `offer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `offer` (
  `amountPerson` int(11) DEFAULT NULL,
  `creationMoment` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `enrollees` int(11) DEFAULT NULL,
  `finishMoment` date DEFAULT NULL,
  `price` double DEFAULT NULL,
  `startMoment` date DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `administrator_id` int(11) NOT NULL,
  `specialist_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_g6666cddiuyq4qeoq9m4di821` (`price`),
  KEY `UK_u10d93qvkm6gj0oe73aj6qjs` (`startMoment`),
  KEY `UK_ca4kesr69hanke7t9yy7lr1y5` (`finishMoment`),
  KEY `FK_q65vc52k0x75bvsoeq3h8ys69` (`administrator_id`),
  KEY `FK_i83g7m1d1k9d56y7f96gf0w9v` (`specialist_id`),
  CONSTRAINT `FK_6m9wntni9fv96g2gi0cixwvg6` FOREIGN KEY (`id`) REFERENCES `domainentity` (`id`),
  CONSTRAINT `FK_i83g7m1d1k9d56y7f96gf0w9v` FOREIGN KEY (`specialist_id`) REFERENCES `specialist` (`id`),
  CONSTRAINT `FK_q65vc52k0x75bvsoeq3h8ys69` FOREIGN KEY (`administrator_id`) REFERENCES `administrator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offer`
--

LOCK TABLES `offer` WRITE;
/*!40000 ALTER TABLE `offer` DISABLE KEYS */;
/*!40000 ALTER TABLE `offer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `offer_patient`
--

DROP TABLE IF EXISTS `offer_patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `offer_patient` (
  `offers_id` int(11) NOT NULL,
  `patients_id` int(11) NOT NULL,
  KEY `FK_4cyp0p3wod52ic4apnhdx6cy2` (`patients_id`),
  KEY `FK_qsry51626yuf14y8oowiqrp77` (`offers_id`),
  CONSTRAINT `FK_qsry51626yuf14y8oowiqrp77` FOREIGN KEY (`offers_id`) REFERENCES `offer` (`id`),
  CONSTRAINT `FK_4cyp0p3wod52ic4apnhdx6cy2` FOREIGN KEY (`patients_id`) REFERENCES `patient` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offer_patient`
--

LOCK TABLES `offer_patient` WRITE;
/*!40000 ALTER TABLE `offer_patient` DISABLE KEYS */;
/*!40000 ALTER TABLE `offer_patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient` (
  `address` varchar(255) DEFAULT NULL,
  `creationMoment` datetime DEFAULT NULL,
  `brandName` varchar(255) DEFAULT NULL,
  `cVVcode` int(11) NOT NULL,
  `expirationMonth` int(11) NOT NULL,
  `expirationYear` int(11) NOT NULL,
  `holderName` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `enableMessage` bit(1) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `medicalHistory_id` int(11) DEFAULT NULL,
  `specialist_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9jh4njnuvbpvg9vtievgrj4lc` (`number`),
  KEY `UK_leyvajm4195kyfvug0aacyt1a` (`address`),
  KEY `FK_ehvk0kvbca5kdeo7c3unsyv13` (`medicalHistory_id`),
  KEY `FK_3yl1r5yf12w0v99xmg4t73lq2` (`specialist_id`),
  CONSTRAINT `FK_o6lg4it98g5yfbwceirtj0a6v` FOREIGN KEY (`id`) REFERENCES `customer` (`id`),
  CONSTRAINT `FK_3yl1r5yf12w0v99xmg4t73lq2` FOREIGN KEY (`specialist_id`) REFERENCES `specialist` (`id`),
  CONSTRAINT `FK_ehvk0kvbca5kdeo7c3unsyv13` FOREIGN KEY (`medicalHistory_id`) REFERENCES `medicalhistory` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prescription`
--

DROP TABLE IF EXISTS `prescription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prescription` (
  `creationMoment` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `appointment_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_ohs1e3blsw9831s34tc9mmpj2` (`price`),
  KEY `FK_cuqk6hogqbe21fpr4u8kpb8qq` (`appointment_id`),
  CONSTRAINT `FK_ie1l8weyx4gt57o165o12kxn5` FOREIGN KEY (`id`) REFERENCES `domainentity` (`id`),
  CONSTRAINT `FK_cuqk6hogqbe21fpr4u8kpb8qq` FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prescription`
--

LOCK TABLES `prescription` WRITE;
/*!40000 ALTER TABLE `prescription` DISABLE KEYS */;
/*!40000 ALTER TABLE `prescription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile` (
  `rating` double DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `specialist_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5awyp1gdittif5825aoxohnmq` (`specialist_id`),
  KEY `UK_1ptxlkmme4nxt49hhko5k1sm4` (`rating`),
  CONSTRAINT `FK_ccdlignjdk3a2lspkdldqi8sr` FOREIGN KEY (`id`) REFERENCES `domainentity` (`id`),
  CONSTRAINT `FK_5awyp1gdittif5825aoxohnmq` FOREIGN KEY (`specialist_id`) REFERENCES `specialist` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile`
--

LOCK TABLES `profile` WRITE;
/*!40000 ALTER TABLE `profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slot`
--

DROP TABLE IF EXISTS `slot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `slot` (
  `startMoment` datetime DEFAULT NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_l3stotct7uk8t4kpdks2b14vw` (`startMoment`),
  CONSTRAINT `FK_blex4m23quemay2241jhtgpa2` FOREIGN KEY (`id`) REFERENCES `domainentity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slot`
--

LOCK TABLES `slot` WRITE;
/*!40000 ALTER TABLE `slot` DISABLE KEYS */;
/*!40000 ALTER TABLE `slot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `specialist`
--

DROP TABLE IF EXISTS `specialist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `specialist` (
  `id` int(11) NOT NULL,
  `profile_id` int(11) DEFAULT NULL,
  `specialty_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_talgf2lo54j3uisk4muikxsgq` (`profile_id`),
  KEY `FK_rcvporhhfonranxoeelv1sixk` (`specialty_id`),
  CONSTRAINT `FK_i885q9ar4ckvs07l0j57pepdr` FOREIGN KEY (`id`) REFERENCES `customer` (`id`),
  CONSTRAINT `FK_rcvporhhfonranxoeelv1sixk` FOREIGN KEY (`specialty_id`) REFERENCES `specialty` (`id`),
  CONSTRAINT `FK_talgf2lo54j3uisk4muikxsgq` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `specialist`
--

LOCK TABLES `specialist` WRITE;
/*!40000 ALTER TABLE `specialist` DISABLE KEYS */;
/*!40000 ALTER TABLE `specialist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `specialty`
--

DROP TABLE IF EXISTS `specialty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `specialty` (
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `administrator_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_8ea2grmb0fn642sslfm7yllhx` (`name`),
  KEY `FK_osag6or2ci3e9xxcpurjc11u1` (`administrator_id`),
  CONSTRAINT `FK_omnhjopywhkynuohpnp77kre5` FOREIGN KEY (`id`) REFERENCES `domainentity` (`id`),
  CONSTRAINT `FK_osag6or2ci3e9xxcpurjc11u1` FOREIGN KEY (`administrator_id`) REFERENCES `administrator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `specialty`
--

LOCK TABLES `specialty` WRITE;
/*!40000 ALTER TABLE `specialty` DISABLE KEYS */;
/*!40000 ALTER TABLE `specialty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `timetable`
--

DROP TABLE IF EXISTS `timetable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timetable` (
  `day` int(11) DEFAULT NULL,
  `endShift` time DEFAULT NULL,
  `startShift` time DEFAULT NULL,
  `id` int(11) NOT NULL,
  `specialist_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_fxuntablgtwg7py21nk1016ki` (`day`),
  KEY `FK_i13bn5bcorsrv4f0m234kqcfj` (`specialist_id`),
  CONSTRAINT `FK_6m1yrtw1e7j9h0os0xstoscdc` FOREIGN KEY (`id`) REFERENCES `domainentity` (`id`),
  CONSTRAINT `FK_i13bn5bcorsrv4f0m234kqcfj` FOREIGN KEY (`specialist_id`) REFERENCES `specialist` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timetable`
--

LOCK TABLES `timetable` WRITE;
/*!40000 ALTER TABLE `timetable` DISABLE KEYS */;
/*!40000 ALTER TABLE `timetable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount`
--

DROP TABLE IF EXISTS `useraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount` (
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_csivo9yqa08nrbkog71ycilh5` (`username`),
  CONSTRAINT `FK_ld5o32pooojx5q0qcunqqfs2u` FOREIGN KEY (`id`) REFERENCES `domainentity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount`
--

LOCK TABLES `useraccount` WRITE;
/*!40000 ALTER TABLE `useraccount` DISABLE KEYS */;
INSERT INTO `useraccount` VALUES ('d5cee333abe432891a0de57d0ee38713','administrator1',1),('82954495ff7e2a735ed2192c35b2cd00','administrator2',2);
/*!40000 ALTER TABLE `useraccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount_authorities`
--

DROP TABLE IF EXISTS `useraccount_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount_authorities` (
  `UserAccount_id` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_b63ua47r0u1m7ccc9lte2ui4r` (`UserAccount_id`),
  CONSTRAINT `FK_b63ua47r0u1m7ccc9lte2ui4r` FOREIGN KEY (`UserAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount_authorities`
--

LOCK TABLES `useraccount_authorities` WRITE;
/*!40000 ALTER TABLE `useraccount_authorities` DISABLE KEYS */;
INSERT INTO `useraccount_authorities` VALUES (1,'ADMINISTRATOR'),(2,'ADMINISTRATOR');
/*!40000 ALTER TABLE `useraccount_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-06-05 22:36:58

commit;