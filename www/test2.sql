-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 11, 2016 at 10:41 AM
-- Server version: 5.7.9
-- PHP Version: 5.6.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `test2`
--

-- --------------------------------------------------------

--
-- Table structure for table `mst_counter`
--

DROP TABLE IF EXISTS `mst_counter`;
CREATE TABLE IF NOT EXISTS `mst_counter` (
  `counterID` varchar(20) NOT NULL,
  `counterName` varchar(30) NOT NULL,
  `locationName` varchar(30) NOT NULL,
  `currentNo` varchar(6) NOT NULL,
  `lastSync` datetime NOT NULL,
  `version` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `mst_counter`
--

INSERT INTO `mst_counter` (`counterID`, `counterName`, `locationName`, `currentNo`, `lastSync`, `version`) VALUES
('jamban', 'Jamban Kedai Buku', 'Giant Kelana Jaya', '1020', '2016-04-04 20:13:59', 1);

-- --------------------------------------------------------

--
-- Table structure for table `mst_user`
--

DROP TABLE IF EXISTS `mst_user`;
CREATE TABLE IF NOT EXISTS `mst_user` (
  `email` varchar(50) NOT NULL,
  `token` varchar(300) NOT NULL,
  `password` varchar(20) NOT NULL,
  `lastSync` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `paid` varchar(10) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `mst_user`
--

INSERT INTO `mst_user` (`email`, `token`, `password`, `lastSync`, `paid`) VALUES
('a@gmail.com', 'fNocRigmNSw:APA91bGwsMjt3czcKmhZwslsAYBI6wnNHdKbqke-_t3AZLJQsT6yKREvOzre-CIAXWwd8kRSh7SW9rKPwRQHcZxj7iyeJlRWCLbM2lVGN0FY2DjRNfPNVZIDnJGFXswwAyYoteBBl8MI', '12345', '2016-03-28 15:44:08', 'NO'),
('b@gmail.com', '', '12345', '2016-03-28 15:44:30', 'YES');

-- --------------------------------------------------------

--
-- Table structure for table `org_counter`
--

DROP TABLE IF EXISTS `org_counter`;
CREATE TABLE IF NOT EXISTS `org_counter` (
  `counterID` varchar(20) NOT NULL,
  `userNo` varchar(4) NOT NULL,
  `paddingNo` int(2) NOT NULL DEFAULT '10',
  `userID` varchar(50) NOT NULL,
  `lastSync` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `version` int(2) NOT NULL DEFAULT '1',
  KEY `version` (`version`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `org_counter`
--

INSERT INTO `org_counter` (`counterID`, `userNo`, `paddingNo`, `userID`, `lastSync`, `version`) VALUES
('maybankampang001', '4522', 10, 'a@gmail.com', '2016-04-04 19:41:53', 1),
('jamban', '1023', 10, 'a@gmail.com', '2016-04-11 14:41:39', 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
