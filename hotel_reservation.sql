-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 12, 2022 at 04:25 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hotel_reservation`
--

-- --------------------------------------------------------

--
-- Table structure for table `registration`
--

CREATE TABLE `registration` (
  `user_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `mobilenumber` varchar(11) NOT NULL,
  `gender` varchar(20) NOT NULL,
  `password` varchar(150) NOT NULL,
  `address` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `registration`
--

INSERT INTO `registration` (`user_id`, `name`, `email`, `mobilenumber`, `gender`, `password`, `address`) VALUES
(8, 'marklester09', 'marklestermoreno09@gmail.com', '09089260456', 'Male', 'b08f917001552d2f4c67361be1628e9c.848796742cdf9bc0a7004d491f30818c', 'Marikina City'),
(9, 'Eduard Paranis', 'eduardparanis@gmail.com', '09123456465', 'Male', 'd86e37f6a7abc49db8d78c6985896532.8d11d76b4a2178b2d26348480c29bb9e', 'Quezon City');

-- --------------------------------------------------------

--
-- Table structure for table `reservation`
--

CREATE TABLE `reservation` (
  `cus_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `contactnumber` varchar(11) NOT NULL,
  `email` varchar(50) NOT NULL,
  `month` varchar(20) NOT NULL,
  `day` varchar(20) NOT NULL,
  `year` varchar(20) NOT NULL,
  `roomtype` varchar(50) NOT NULL,
  `roomnum` varchar(50) NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `reservation`
--

INSERT INTO `reservation` (`cus_id`, `name`, `contactnumber`, `email`, `month`, `day`, `year`, `roomtype`, `roomnum`, `status`) VALUES
(9, 'Mark Lester Moreno', '09089260456', 'marklestermoreno09@gmail.com', 'Oct', '12', '2022', 'VIP Class', 'Room No. 9', 'Checked In'),
(16, 'Charles Mateo', '09123456789', 'charles@gmail.com', 'Oct', '13', '2022', 'VIP Class', 'Room No. 2', 'Checked Out');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `registration`
--
ALTER TABLE `registration`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`cus_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `registration`
--
ALTER TABLE `registration`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `cus_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
