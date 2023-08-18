-- MySQL dump 10.13  Distrib 5.7.24, for Win32 (AMD64)
--
-- Host: localhost    Database: employee_managementDB
-- ------------------------------------------------------
-- Server version	5.7.24

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
-- Table structure for table `admin_details`
--

DROP TABLE IF EXISTS `admin_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_details` (
  `emp_id` char(4) NOT NULL,
  `user_email` varchar(20) DEFAULT NULL,
  `last_accessed_date` date DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`emp_id`),
  CONSTRAINT `admin_details_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `employee_details` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_details`
--

LOCK TABLES `admin_details` WRITE;
/*!40000 ALTER TABLE `admin_details` DISABLE KEYS */;
INSERT INTO `admin_details` VALUES ('E001','root@gmail.com','2023-08-16','root'),('E006','kamal787@gmail.com','2023-08-09','kama123'),('E008','dulina88@gmail.com','2023-08-01','duli233'),('E014','brand4588@gmail.com','2023-08-09','brend3'),('E040','sachindu38@gmail.com','2023-08-17','sachinduk');
/*!40000 ALTER TABLE `admin_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attendance` (
  `empID` char(4) NOT NULL,
  `year` int(11) NOT NULL,
  `month` int(11) NOT NULL,
  `noDays` int(11) DEFAULT NULL,
  PRIMARY KEY (`empID`,`year`,`month`),
  CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`empID`) REFERENCES `employee_details` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
INSERT INTO `attendance` VALUES ('E002',2023,8,19),('E003',2023,8,25),('E004',2023,8,23),('E005',2023,8,27),('E006',2023,8,19),('E007',2023,8,20),('E008',2023,4,22),('E008',2023,6,18),('E008',2023,7,25),('E008',2023,8,28),('E008',2023,9,25),('E008',2023,10,23),('E008',2023,12,17),('E009',2023,8,24),('E010',2023,4,19),('E010',2023,6,26),('E010',2023,8,29),('E013',2023,8,30),('E015',2023,8,29),('E016',2023,8,18),('E017',2023,8,24),('E025',2023,8,17),('E027',2023,8,16),('E040',2023,8,26),('E043',2023,8,26),('E044',2023,8,28),('E045',2023,8,23),('E048',2023,8,27),('E048',2023,9,30);
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department` (
  `deptID` char(4) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `headEMPID` char(4) DEFAULT NULL,
  PRIMARY KEY (`deptID`),
  KEY `headEMPID` (`headEMPID`),
  CONSTRAINT `department_ibfk_1` FOREIGN KEY (`headEMPID`) REFERENCES `employee_details` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES ('D001','HR','E025'),('D002','IT','E014'),('D003','Staff','E015'),('D004','Operational','E016'),('D005','Marketing','E017'),('D006','Engineering','E042'),('D007','Sales','E002'),('D008','Management','E006'),('D009','Accounting','E004'),('D010','Logistic','E047');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `departmentview`
--

DROP TABLE IF EXISTS `departmentview`;
/*!50001 DROP VIEW IF EXISTS `departmentview`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `departmentview` AS SELECT 
 1 AS `deptID`,
 1 AS `name`,
 1 AS `numEmployees`,
 1 AS `headEMPID`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `employee_details`
--

DROP TABLE IF EXISTS `employee_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee_details` (
  `employee_id` char(4) NOT NULL,
  `f_name` varchar(20) DEFAULT NULL,
  `l_name` varchar(20) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `position` varchar(20) DEFAULT NULL,
  `deptID` char(4) DEFAULT NULL,
  `payRate` float(6,2) DEFAULT NULL,
  `NIC` char(13) DEFAULT NULL,
  `mobile_number` char(10) DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  PRIMARY KEY (`employee_id`),
  UNIQUE KEY `NIC` (`NIC`),
  KEY `deptID` (`deptID`),
  CONSTRAINT `employee_details_ibfk_1` FOREIGN KEY (`deptID`) REFERENCES `department` (`deptID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_details`
--

LOCK TABLES `employee_details` WRITE;
/*!40000 ALTER TABLE `employee_details` DISABLE KEYS */;
INSERT INTO `employee_details` VALUES ('E001','root','root','root@gmail.com','CEO','D001',1500.00,'200108000604','0764314505',62),('E002','Rayan','Oshan','raya@gmail.com','Salesman','D005',3000.00,'945673456v','0776538495',67),('E003','Malith','Sithija','MS45@gmail.com','Salesman','D005',3500.00,'922356785v','0712734543',49),('E004','Pooja','Fernando','poji7@gmail.com','Accountant','D001',5000.00,'965678645v','0777543092',44),('E005','Sampath','Silva','samMax@gmail.com','Helper','D005',2000.00,'996785467v','0751234533',72),('E006','Kamal','Perera','kamal67@gmail.com','Manager','D001',1000.00,'946785467v','0721345123',27),('E007','Jayni','Nimasha','jaye44@gmail.com','Supervisor','D004',6000.00,'982359856v','0782541878',20),('E008','Dulina','Fernando','dulina@gmail.com','Software Engineer','D002',3000.00,'983485764v','0777454098',21),('E009','kalani','Ahinsa','ahinsa70@gmail.com','Receptionist','D003',3500.00,'968564857v','0735624346',42),('E010','Pubudu','Dias','p3Dias@gmail.com','Officer','D005',5000.00,'948763759v','0775438678',48),('E013','Damith','Kanishka','Kanishka@gmail.com','Software Engineer','D002',2500.00,'968763456v','0712323245',35),('E014','Brandon','Shane','brandonshane58@gmail.com','Software Engineer','D002',1220.00,'951793542v','0743698512',26),('E015','Julia','Marie','juliamarie18@gmail.com','Officer','D003',5000.00,'976755913v','0756898721',26),('E016','Ally','Aagaard','allyaagaard34@gmail.com','Manager','D004',3000.00,'961234581v','0715489632',51),('E017','Jacobi','Adams','jacobadams41@gmail.com','Supervisor','D005',6000.00,'945673452v','0785412967',76),('E025','Jessica','Alcon','jessicaalcon78@gmail.com','Manager','D001',1000.00,'922356787v','0776421983',27),('E027','Olivia','Carrillo','oliviacarrillo64@gmail.com','Salesman','D005',3500.00,'982359456v','0709964128',9),('E040','Sachindu','Kavishka','sachindu38@gmail.com','Admin','D002',6500.00,'200108000607','0764314505',61),('E041','Pabasara','Rathnayaka','paba@gmail.com','Assistant','D004',3500.00,'45865254252','0856542156',81),('E042','Maheesha','Nethmina','mahee@gmail.com','Engineer','D002',3500.00,'200015255653','0785632545',22),('E043','Kasun','Janith','janith@gmail.com','Representative','D004',2500.00,'20056326569','0789656325',22),('E044','Nisal','Rathnayaka','nisal@gmail.com','Assistant','D003',750.00,'200150688965','0785652365',23),('E045','Pasindu','Ranathunga','pasindu@gmail.com','Cheff','D003',2500.00,'211055236526','078965325',23),('E046','Nimal','Senanayaka','nimall123@gmail.com','Driver','D010',2000.00,'952595456v','0772545723',24),('E047','Tyron','Perera','tyron23@gmail.com','Driver','D010',2000.00,'852545456v','0761234568',25),('E048','Kumara','Silva','kuma234@gmail.com','Driver','D010',2000.00,'902595459v','0789080734',26);
/*!40000 ALTER TABLE `employee_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `projectID` char(4) NOT NULL,
  `name` char(20) DEFAULT NULL,
  `leaderEMPID` char(4) DEFAULT NULL,
  `sDate` date DEFAULT NULL,
  `eDate` date DEFAULT NULL,
  PRIMARY KEY (`projectID`),
  KEY `leaderEMPID` (`leaderEMPID`),
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`leaderEMPID`) REFERENCES `employee_details` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES ('P001','X.Y.Z','E027','2019-08-05','2020-05-02'),('P002','Apex','E017','2019-07-03','2019-07-09'),('P003','Alpha','E041','2019-07-19','2020-08-29'),('P004','Jupiter','E004','2018-10-27','2019-04-27'),('P005','Gunn','E027','2019-02-08','2019-08-08'),('P006','tech','E004','2018-12-12','2019-06-12'),('P007','Drone del','E004','2019-10-12','2020-05-09'),('P008','black tap','E008','2019-04-19','2019-10-19'),('P009','blue sea','E008','2018-10-18','2019-07-19'),('P010','winds','E015','2019-09-19','2020-03-18'),('P011','Atlantis','E016','2019-08-01','2020-02-01'),('P012','Victors','E016','2018-09-19','2019-03-16'),('P013','cyclone','E016','2019-06-30','2019-12-30'),('P014','voyage','E015','2019-04-29','2019-10-29'),('P015','Elixir','E015','2018-02-02','2018-08-02'),('P016','Alex','E027','2020-02-03','2020-10-23'),('P017','Jene','E016','2018-04-05','2019-02-04'),('P018','Nema','E004','2017-03-25','2018-07-12'),('P019','Windy','E004','2019-03-04','2020-02-03'),('P020','Jeen','E017','2020-04-06','2020-07-31');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `departmentview`
--

/*!50001 DROP VIEW IF EXISTS `departmentview`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = cp850 */;
/*!50001 SET character_set_results     = cp850 */;
/*!50001 SET collation_connection      = cp850_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `departmentview` AS select `employee_details`.`deptID` AS `deptID`,`department`.`name` AS `name`,count(`employee_details`.`employee_id`) AS `numEmployees`,`department`.`headEMPID` AS `headEMPID` from (`employee_details` join `department` on((`department`.`deptID` = `employee_details`.`deptID`))) group by `employee_details`.`deptID` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-18  9:47:30
