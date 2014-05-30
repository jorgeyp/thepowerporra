-- MySQL dump 10.13  Distrib 5.6.14, for Win64 (x86_64)
--
-- Host: localhost    Database: porra
-- ------------------------------------------------------
-- Server version	5.6.14

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
-- Table structure for table `clasificado`
--

DROP TABLE IF EXISTS `clasificado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clasificado` (
  `idRonda` int(11) NOT NULL,
  `idEquipo` int(11) NOT NULL,
  PRIMARY KEY (`idRonda`,`idEquipo`),
  KEY `IDX_EV_CLASIF_EQUIPO` (`idEquipo`),
  KEY `IDX_EV_CLASIF_RONDA` (`idRonda`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clasificado`
--

LOCK TABLES `clasificado` WRITE;
/*!40000 ALTER TABLE `clasificado` DISABLE KEYS */;
/*!40000 ALTER TABLE `clasificado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clasificado_apuesta`
--

DROP TABLE IF EXISTS `clasificado_apuesta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clasificado_apuesta` (
  `idUsuario` int(11) NOT NULL,
  `idRonda` int(11) NOT NULL,
  `idEquipo` int(11) NOT NULL,
  PRIMARY KEY (`idUsuario`,`idRonda`,`idEquipo`),
  KEY `FK_CLASIF_APUESTA_RONDA` (`idRonda`),
  KEY `FK_CLASIF_APUESTA_EQUIPO` (`idEquipo`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clasificado_apuesta`
--

LOCK TABLES `clasificado_apuesta` WRITE;
/*!40000 ALTER TABLE `clasificado_apuesta` DISABLE KEYS */;
INSERT INTO `clasificado_apuesta` VALUES (1,1,15),(1,1,16);
/*!40000 ALTER TABLE `clasificado_apuesta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipo`
--

DROP TABLE IF EXISTS `equipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `equipo` (
  `idEquipo` int(11) NOT NULL,
  `descEquipo` varchar(30) NOT NULL,
  PRIMARY KEY (`idEquipo`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipo`
--

LOCK TABLES `equipo` WRITE;
/*!40000 ALTER TABLE `equipo` DISABLE KEYS */;
INSERT INTO `equipo` VALUES (1,'Alemania'),(2,'Argelia'),(3,'Argentina'),(4,'Australia'),(5,'Bélgica'),(6,'Bosnia y Herzegovina'),(7,'Brasil'),(8,'Camerún'),(9,'Chile'),(10,'Colombia'),(11,'Corea del Sur'),(12,'Costa de Marfil'),(13,'Costa Rica'),(14,'Croacia'),(15,'Ecuador'),(16,'España'),(17,'Estados Unidos'),(18,'Francia'),(19,'Ghana'),(20,'Grecia'),(21,'Honduras'),(22,'Inglaterra'),(23,'Irán'),(24,'Italia'),(25,'Japón'),(26,'México'),(27,'Nigeria'),(28,'Holanda'),(29,'Portugal'),(30,'Rusia'),(31,'Suiza'),(32,'Uruguay');
/*!40000 ALTER TABLE `equipo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jugador`
--

DROP TABLE IF EXISTS `jugador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jugador` (
  `idJugador` int(11) NOT NULL AUTO_INCREMENT,
  `descJugador` varchar(100) NOT NULL,
  `idEquipo` int(11) NOT NULL,
  `golesJugador` int(11) DEFAULT '0',
  PRIMARY KEY (`idJugador`),
  KEY `INDEX_JUGADOR_EQUIPO` (`idEquipo`) COMMENT 'Index para foreign a tabla Equipo'
) ENGINE=MyISAM AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jugador`
--

LOCK TABLES `jugador` WRITE;
/*!40000 ALTER TABLE `jugador` DISABLE KEYS */;
INSERT INTO `jugador` VALUES (1,'Klose',1,0),(2,'Müller',1,0),(3,'Özil',1,0),(4,'Slimani',2,0),(5,'Agüero',3,0),(6,'Higuaín',3,0),(7,'Messi',3,0),(8,'Joshua Kennedy',4,0),(9,'De Bruyne',5,0),(10,'Hazard',5,0),(11,'Dzeko',6,0),(12,'Fred',7,0),(13,'Hulk',7,0),(14,'Neymar',7,0),(15,'Eto\'o',8,0),(16,'Alexis Sánchez',9,0),(17,'Falcao',10,0),(18,'Gutiérrez',10,0),(19,'Park Chu Young',11,0),(20,'Drogba',12,0),(21,'Álvaro Saborío',13,0),(22,'Mandzukic',14,0),(23,'Caicedo',15,0),(24,'David Villa',16,0),(25,'Diego Costa',16,0),(26,'Torres',16,0),(27,'Altidore',17,0),(28,'Dempsey',17,0),(29,'Benzema',18,0),(30,'Ribery',18,0),(31,'Mitroglou',20,0),(32,'Robben',28,0),(33,'Van Persie',28,0),(34,'Rooney',22,0),(35,'Nekounam',23,0),(36,'Balotelli',24,0),(37,'Okazaki',25,0),(38,'Peralta',26,0),(39,'Emenike',27,0),(40,'Cristiano Ronaldo',29,0),(41,'Cavani',32,0),(42,'Luis Suárez',32,0),(43,'asdfasdf',1,0),(44,'asdfasdf',1,0),(45,'asdfasdf',1,0),(46,'ddasf',1,0),(47,'asdfasdf',1,0),(48,'asdfdfassdfa',1,0),(49,'yagüe',6,0),(50,'qrwerqwer',3,0);
/*!40000 ALTER TABLE `jugador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jugador_apuesta`
--

DROP TABLE IF EXISTS `jugador_apuesta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jugador_apuesta` (
  `idUsuario` int(11) NOT NULL,
  `idJugador` int(11) NOT NULL,
  PRIMARY KEY (`idUsuario`,`idJugador`),
  KEY `FK_JUG_APUESTA_JUGADOR` (`idJugador`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jugador_apuesta`
--

LOCK TABLES `jugador_apuesta` WRITE;
/*!40000 ALTER TABLE `jugador_apuesta` DISABLE KEYS */;
INSERT INTO `jugador_apuesta` VALUES (1,50);
/*!40000 ALTER TABLE `jugador_apuesta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partido`
--

DROP TABLE IF EXISTS `partido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partido` (
  `idEquipo1` int(11) NOT NULL,
  `idEquipo2` int(11) NOT NULL,
  `golesEquipo1` int(11) DEFAULT NULL,
  `golesEquipo2` int(11) DEFAULT NULL,
  `fechaJornada` date NOT NULL,
  PRIMARY KEY (`idEquipo1`,`idEquipo2`),
  KEY `FK_EV_PARTIDO_EQUIPO2` (`idEquipo2`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partido`
--

LOCK TABLES `partido` WRITE;
/*!40000 ALTER TABLE `partido` DISABLE KEYS */;
INSERT INTO `partido` VALUES (7,14,NULL,NULL,'2014-06-12'),(26,8,NULL,NULL,'2014-06-13'),(16,28,NULL,NULL,'2014-06-13'),(9,4,NULL,NULL,'2014-06-13'),(10,20,NULL,NULL,'2014-06-14'),(12,25,NULL,NULL,'2014-06-14'),(32,13,NULL,NULL,'2014-06-14'),(22,24,NULL,NULL,'2014-06-14'),(31,15,NULL,NULL,'2014-06-15'),(18,21,NULL,NULL,'2014-06-15'),(3,6,NULL,NULL,'2014-06-15'),(23,27,NULL,NULL,'2014-06-16'),(1,29,NULL,NULL,'2014-06-16'),(19,17,NULL,NULL,'2014-06-16'),(5,2,NULL,NULL,'2014-06-17'),(30,11,NULL,NULL,'2014-06-17'),(7,26,NULL,NULL,'2014-06-17'),(8,14,NULL,NULL,'2014-06-18'),(16,9,NULL,NULL,'2014-06-18'),(4,28,NULL,NULL,'2014-06-18'),(10,12,NULL,NULL,'2014-06-19'),(25,20,NULL,NULL,'2014-06-19'),(32,22,NULL,NULL,'2014-06-19'),(24,13,NULL,NULL,'2014-06-20'),(31,18,NULL,NULL,'2014-06-20'),(21,15,NULL,NULL,'2014-06-20'),(3,23,NULL,NULL,'2014-06-21'),(27,6,NULL,NULL,'2014-06-21'),(1,19,NULL,NULL,'2014-06-21'),(17,29,NULL,NULL,'2014-06-22'),(5,30,NULL,NULL,'2014-06-22'),(11,2,NULL,NULL,'2014-06-22'),(8,7,NULL,NULL,'2014-06-23'),(14,26,NULL,NULL,'2014-06-23'),(4,16,NULL,NULL,'2014-06-23'),(28,9,NULL,NULL,'2014-06-23'),(25,10,NULL,NULL,'2014-06-24'),(20,12,NULL,NULL,'2014-06-24'),(24,32,NULL,NULL,'2014-06-24'),(13,22,NULL,NULL,'2014-06-24'),(21,31,NULL,NULL,'2014-06-25'),(15,18,NULL,NULL,'2014-06-25'),(27,3,NULL,NULL,'2014-06-25'),(6,23,NULL,NULL,'2014-06-25'),(17,1,NULL,NULL,'2014-06-26'),(29,19,NULL,NULL,'2014-06-26'),(11,5,NULL,NULL,'2014-06-26'),(2,30,NULL,NULL,'2014-06-26');
/*!40000 ALTER TABLE `partido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partido_apuesta`
--

DROP TABLE IF EXISTS `partido_apuesta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partido_apuesta` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `idEquipo1` int(11) NOT NULL,
  `idEquipo2` int(11) NOT NULL,
  `golesEquipo1` int(11) NOT NULL,
  `golesEquipo2` int(11) NOT NULL,
  PRIMARY KEY (`idUsuario`,`idEquipo1`,`idEquipo2`),
  KEY `FK_PARTIDO_APUESTA_PART_EQ1` (`idEquipo1`),
  KEY `FK_PARTIDO_APUESTA_PART_EQ2` (`idEquipo2`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partido_apuesta`
--

LOCK TABLES `partido_apuesta` WRITE;
/*!40000 ALTER TABLE `partido_apuesta` DISABLE KEYS */;
INSERT INTO `partido_apuesta` VALUES (1,7,14,3,0),(1,26,8,3,0),(1,9,4,2,2),(1,10,20,2,1),(1,16,28,0,1),(1,32,13,4,2),(1,19,17,0,0),(1,3,6,0,0),(1,31,15,0,0),(1,12,25,0,0),(1,22,24,0,0);
/*!40000 ALTER TABLE `partido_apuesta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permiso`
--

DROP TABLE IF EXISTS `permiso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permiso` (
  `idPermiso` int(11) NOT NULL,
  `descPermiso` varchar(100) NOT NULL,
  PRIMARY KEY (`idPermiso`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permiso`
--

LOCK TABLES `permiso` WRITE;
/*!40000 ALTER TABLE `permiso` DISABLE KEYS */;
INSERT INTO `permiso` VALUES (1,'Lectura'),(2,'Actualizacion');
/*!40000 ALTER TABLE `permiso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permiso_usuario`
--

DROP TABLE IF EXISTS `permiso_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permiso_usuario` (
  `idUsuario` int(11) NOT NULL,
  `idPermiso` int(11) NOT NULL,
  PRIMARY KEY (`idUsuario`,`idPermiso`),
  KEY `FK_PERM_USER_PERMISO` (`idPermiso`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permiso_usuario`
--

LOCK TABLES `permiso_usuario` WRITE;
/*!40000 ALTER TABLE `permiso_usuario` DISABLE KEYS */;
INSERT INTO `permiso_usuario` VALUES (1,2);
/*!40000 ALTER TABLE `permiso_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `puntuaciones`
--

DROP TABLE IF EXISTS `puntuaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `puntuaciones` (
  `idUsuario` int(11) NOT NULL,
  `fechaJornada` date NOT NULL,
  `puntos_partido` double NOT NULL,
  `puntos_clasificacion` double NOT NULL,
  `puntos_goleador` double NOT NULL,
  PRIMARY KEY (`idUsuario`,`fechaJornada`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `puntuaciones`
--

LOCK TABLES `puntuaciones` WRITE;
/*!40000 ALTER TABLE `puntuaciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `puntuaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ronda`
--

DROP TABLE IF EXISTS `ronda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ronda` (
  `idRonda` int(11) NOT NULL,
  `descRonda` varchar(100) NOT NULL,
  PRIMARY KEY (`idRonda`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ronda`
--

LOCK TABLES `ronda` WRITE;
/*!40000 ALTER TABLE `ronda` DISABLE KEYS */;
INSERT INTO `ronda` VALUES (1,'Octavos de final'),(2,'Cuartos de final'),(3,'Semifinales'),(4,'Final');
/*!40000 ALTER TABLE `ronda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `descUsuario` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `sha1Hash` char(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `validado` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `email_unico` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'jorgeyp','jorgeyp@gmail.com','$2a$12$lweYNkCojnWRbNqkLcUv/u9HW8zKiFYeYhINhpeoDaBUqDxhTt182',0),(5,'prueba4','prueba4@prueba4.com','$2a$12$98pdyPF.kVPGFSbVm7nwkOyKVf8RJafhlhdqfEXyHQAkbjIG7LDHS',0);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-05-30 14:01:24
