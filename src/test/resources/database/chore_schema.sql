-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 12, 2016 at 12:12 AM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 5.6.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `chore_test`
--

-- --------------------------------------------------------

--
-- Table structure for table `chore`
--

CREATE TABLE `chore` (
  `chore_id` bigint(20) NOT NULL,
  `chore_spec_id` bigint(20) NOT NULL,
  `doer` bigint(20) NOT NULL,
  `status` int(11) NOT NULL,
  `duration` int(10) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `location` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `chore_group`
--

CREATE TABLE `chore_group` (
  `chore_group_id` bigint(20) NOT NULL,
  `chore_group_name` varchar(35) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `chore_group_user`
--

CREATE TABLE `chore_group_user` (
  `chore_group_user_id` bigint(20) NOT NULL,
  `chore_group` bigint(20) NOT NULL,
  `chore_user` bigint(20) NOT NULL,
  `invited_by` bigint(20) DEFAULT NULL,
  `chore_group_user_role` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `chore_spec`
--

CREATE TABLE `chore_spec` (
  `chore_spec_id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `chore_group_id` bigint(20) NOT NULL,
  `preferred_doer` bigint(20) DEFAULT NULL,
  `frequency` bigint(20) DEFAULT NULL,
  `next_instance_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `chore_user`
--

CREATE TABLE `chore_user` (
  `user_id` bigint(20) NOT NULL,
  `email` varchar(128) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `screen_name` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `chore`
--
ALTER TABLE `chore`
  ADD PRIMARY KEY (`chore_id`);

--
-- Indexes for table `chore_group`
--
ALTER TABLE `chore_group`
  ADD PRIMARY KEY (`chore_group_id`),
  ADD UNIQUE KEY `chore_group_name` (`chore_group_name`);

--
-- Indexes for table `chore_group_user`
--
ALTER TABLE `chore_group_user`
  ADD PRIMARY KEY (`chore_group_user_id`),
  ADD UNIQUE KEY `unique_chore_member` (`chore_group`,`chore_user`);

--
-- Indexes for table `chore_spec`
--
ALTER TABLE `chore_spec`
  ADD PRIMARY KEY (`chore_spec_id`);

--
-- Indexes for table `chore_user`
--
ALTER TABLE `chore_user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `chore`
--
ALTER TABLE `chore`
  MODIFY `chore_id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `chore_group`
--
ALTER TABLE `chore_group`
  MODIFY `chore_group_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1004;
--
-- AUTO_INCREMENT for table `chore_group_user`
--
ALTER TABLE `chore_group_user`
  MODIFY `chore_group_user_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1410;
--
-- AUTO_INCREMENT for table `chore_spec`
--
ALTER TABLE `chore_spec`
  MODIFY `chore_spec_id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `chore_user`
--
ALTER TABLE `chore_user`
  MODIFY `user_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1820;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
