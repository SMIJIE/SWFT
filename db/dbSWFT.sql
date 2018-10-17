-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 62.80.166.79    Database: dbswft
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `dayration`
--

DROP TABLE IF EXISTS `dayration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dayration` (
  `idDR` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `idUser` int(11) NOT NULL,
  `userCalories` int(11) NOT NULL,
  `userCaloriesDesired` int(11) NOT NULL,
  PRIMARY KEY (`idDR`),
  KEY `idUser_idx` (`idUser`),
  CONSTRAINT `idUser` FOREIGN KEY (`idUser`) REFERENCES `user` (`idU`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dayration`
--

LOCK TABLES `dayration` WRITE;
/*!40000 ALTER TABLE `dayration` DISABLE KEYS */;
INSERT INTO `dayration` VALUES 
(1,'2018-05-13',1,2865000,309000),
(2,ADDDATE(CURDATE(),-7),2,2678000,2918000),
(3,ADDDATE(CURDATE(),-5),2,2135000,2469000),
(18,CURDATE(),2,2458000,2318000),
(21,ADDDATE(CURDATE(),5),2,2943000,3073000),
(22,ADDDATE(CURDATE(),7),2,3012000,2612000),
(23,ADDDATE(CURDATE(),9),2,2582000,2912000);
/*!40000 ALTER TABLE `dayration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish`
--

DROP TABLE IF EXISTS `dish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dish` (
  `idD` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `weight` int(11) NOT NULL,
  `calories` int(11) NOT NULL,
  `proteins` int(11) NOT NULL,
  `fats` int(11) NOT NULL,
  `carbohydrates` int(11) NOT NULL,
  `idUsers` int(11) DEFAULT NULL,
  `generalFood` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idD`),
  KEY `idUsers_idx` (`idUsers`),
  CONSTRAINT `idUsers` FOREIGN KEY (`idUsers`) REFERENCES `user` (`idU`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish`
--

LOCK TABLES `dish` WRITE;
/*!40000 ALTER TABLE `dish` DISABLE KEYS */;
INSERT INTO `dish` VALUES 
(1,'HOT','Pie with indian',200000,300000,2500,500,4500,1,0),
(2,'DESSERT','Homemade ice cream',200000,300000,2500,500,4500,1,0),
(3,'LUNCHEON','page.luncheon.boiledEggsAndRriedCelery',250000,350000,16000,2800,6000,NULL,1),
(4,'SOUP','page.soup.soupFromSoyBean',320000,300000,14000,4800,29500,NULL,1),
(5,'SOUP','page.soup.soupFromAsparagusBeans',300000,290000,16000,4600,30000,NULL,1),
(6,'SOUP','page.soup.vegetableBroth',350000,280000,15000,5100,31000,NULL,1),
(7,'HOT','page.hot.risottoWithMushrooms',330000,150000,3300,500,28000,NULL,1),
(8,'HOT','page.hot.steamTurkey',250000,170000,3200,400,32000,NULL,1),
(9,'HOT','page.hot.steamChicken',260000,160000,3100,350,29500,NULL,1),
(10,'HOT','page.hot.fishCell',250000,180000,2700,550,27500,NULL,1),
(11,'DESSERT','page.dessert.cheeseWithCranberries',200000,170000,8600,1600,4900,NULL,1),
(12,'DESSERT','page.dessert.blackcurrantCurd',200000,160000,8900,1400,5000,NULL,1),
(13,'DESSERT','page.dessert.coconutBiscuits',200000,190000,9000,1600,5600,NULL,1),
(14,'DESSERT','page.dessert.beetrootBiscuit',250000,190000,9600,1200,5900,NULL,1),
(15,'DESSERT','page.dessert.orangeTart',150000,180000,9100,1700,5100,NULL,1),
(16,'DESSERT','page.dessert.curdMousse',150000,160000,8600,1800,6100,NULL,1),
(17,'DESSERT','page.dessert.cranberrySherbet',150000,180000,8700,1500,5900,NULL,1),
(18,'DESSERT','page.dessert.honeyPuree',150000,170000,7900,900,6200,NULL,1),
(19,'DESSERT','page.dessert.dessertWithCoconut',150000,170000,8300,990,4900,NULL,1),
(20,'LUNCHEON','page.luncheon.rollWithChickenKebab',250000,300000,2700,3200,6700,NULL,1),
(21,'LUNCHEON','page.luncheon.baguetWithTune',260000,250000,16000,3800,6500,NULL,1),
(22,'DESSERT','Cheese with strawberries',180000,170000,8500,1500,6000,2,0),
(23,'HOT','Pie with indian',200000,180000,8300,1800,6200,2,0),
(24,'HOT','Chicken carri',190000,175000,8700,1000,5100,2,0),
(25,'LUNCHEON','Couscous',200000,200000,8500,990,6100,2,0),
(26,'LUNCHEON','Scramble with spinate',250000,180000,9100,1700,4900,2,0),
(27,'LUNCHEON','Beetroot salad',200000,300000,2500,500,4500,2,0),
(28,'HOT','Rye cake',150000,160000,500,500,6500,2,0),
(29,'DESSERT','Carrot-cheese cake',190000,175000,8500,1000,5600,2,0);
/*!40000 ALTER TABLE `dish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rationcomposition`
--

DROP TABLE IF EXISTS `rationcomposition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rationcomposition` (
  `idRC` int(11) NOT NULL AUTO_INCREMENT,
  `idDayRation` int(11) NOT NULL,
  `foodIntake` varchar(45) NOT NULL,
  `idDish` int(11) NOT NULL,
  `numberOfDish` int(11) NOT NULL,
  `caloriesOfDish` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idRC`),
  KEY `idRation_idx` (`idDayRation`),
  KEY `idDish_idx` (`idDish`),
  CONSTRAINT `dayRationId` FOREIGN KEY (`idDayRation`) REFERENCES `dayration` (`idDR`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `dishId` FOREIGN KEY (`idDish`) REFERENCES `dish` (`idD`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rationcomposition`
--

LOCK TABLES `rationcomposition` WRITE;
/*!40000 ALTER TABLE `rationcomposition` DISABLE KEYS */;
INSERT INTO `rationcomposition` VALUES 
(1,1,'BREAKFAST',1,5,175000),
(2,2,'BREAKFAST',23,2,160000),
(3,2,'BREAKFAST',25,3,140000),
(4,2,'DINNER',24,1,175000),
(5,2,'DINNER',26,2,180000),
(6,2,'DINNER',27,3,300000),
(8,2,'SUPPER',20,2,300000),
(9,2,'SUPPER',20,3,300000),
(10,2,'SUPPER',20,3,300000),
(11,3,'BREAKFAST',25,2,140000),
(12,3,'DINNER',4,2,175000),
(13,3,'SUPPER',7,2,175000),
(14,3,'BREAKFAST',23,2,160000),
(15,3,'DINNER',26,2,180000),
(16,3,'SUPPER',16,1,160000),
(18,18,'BREAKFAST',28,2,175000),
(19,18,'BREAKFAST',23,1,160000),
(20,18,'BREAKFAST',24,1,175000),
(21,18,'DINNER',3,1,350000),
(22,18,'DINNER',4,1,270000),
(23,18,'SUPPER',16,1,160000),
(24,18,'SUPPER',9,1,160000),
(26,21,'BREAKFAST',3,1,300000),
(27,22,'BREAKFAST',3,3,350000),
(28,22,'BREAKFAST',20,4,300000),
(29,22,'BREAKFAST',21,4,250000),
(30,22,'DINNER',7,1,150000),
(31,22,'DINNER',9,2,160000),
(32,22,'SUPPER',16,1,160000),
(33,22,'SUPPER',12,1,160000),
(34,22,'DINNER',8,1,170000),(
35,22,'DINNER',10,1,180000),
(36,22,'DINNER',12,1,160000),
(37,23,'DINNER',16,1,160000),
(38,23,'DINNER',18,1,170000),
(39,23,'DINNER',8,1,170000),
(40,23,'DINNER',9,1,160000),
(41,23,'DINNER',10,1,180000),
(42,23,'DINNER',11,1,170000),
(43,23,'DINNER',12,1,160000),
(44,22,'DINNER',3,1,350000),
(45,22,'DINNER',4,1,300000),
(46,22,'DINNER',5,1,290000),
(47,22,'DINNER',6,1,280000),
(48,22,'BREAKFAST',5,1,290000),
(49,22,'BREAKFAST',6,1,280000);
/*!40000 ALTER TABLE `rationcomposition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `idU` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `dob` date NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `role` varchar(10) NOT NULL DEFAULT 'USER',
  `height` int(11) NOT NULL,
  `weight` int(11) NOT NULL,
  `weightDesired` int(11) NOT NULL DEFAULT '0',
  `lifeStyleCoefficient` int(11) NOT NULL,
  PRIMARY KEY (`idU`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES 
(1,'Zakusylo','1993-06-15','zakusylo@gmail.com','d8578edf8458ce06fbc5bb76a58c5ca4','ADMIN',18000,80000,70000,1500),
(2,'Павло','1992-06-30','pavlo@mail.ua','d8578edf8458ce06fbc5bb76a58c5ca4','USER',18500,100000,90000,1200),
(3,'Sasha','1992-05-09','sasha@bigmir.net','d8578edf8458ce06fbc5bb76a58c5ca4','ADMIN',19000,90000,70000,1550),
(4,'Mark','1992-05-09','mark@bigmir.net','d8578edf8458ce06fbc5bb76a58c5ca4','ADMIN',19000,90000,70000,1550),
(5,'Kolya','1992-05-09','kolya@bigmir.net','d8578edf8458ce06fbc5bb76a58c5ca4','ADMIN',19000,90000,70000,1550),
(6,'Vasya','1992-05-09','vasya@bigmir.net','d8578edf8458ce06fbc5bb76a58c5ca4','USER',19000,90000,70000,1550),
(7,'Zlata','1992-05-09','zlata@bigmir.net','d8578edf8458ce06fbc5bb76a58c5ca4','USER',19000,90000,70000,1550),
(8,'Masha','1993-05-17','masha@bigmir.net','76419c58730d9f35de7ac538c2fd6737','USER',15000,90000,80000,1900),
(9,'Kira','1993-05-17','kira@bigmir.net','d8578edf8458ce06fbc5bb76a58c5ca4','USER',15000,90000,80000,1900);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-04 11:50:15
