-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Aug 01, 2016 at 07:15 AM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 5.6.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `chores`
--

-- --------------------------------------------------------

--
-- Table structure for table `chore_group`
--

CREATE TABLE `chore_group` (
  `chore_group_id` bigint(20) NOT NULL,
  `chore_group_name` varchar(35) NOT NULL,
  `chore_group_owner` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `chore_group`
--

INSERT INTO `chore_group` (`chore_group_id`, `chore_group_name`, `chore_group_owner`) VALUES
(1, 'test', 1),
(2, 'testers', 2);

-- --------------------------------------------------------

--
-- Table structure for table `chore_group_invitation`
--

CREATE TABLE `chore_group_invitation` (
  `chore_group_invitation_id` bigint(20) NOT NULL,
  `chore_group_id` bigint(20) NOT NULL,
  `recipient_user_id` bigint(20) NOT NULL,
  `status` enum('SENT','REJECTED','ACCEPTED') NOT NULL DEFAULT 'SENT',
  `last_updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `chore_group_invitation`
--

INSERT INTO `chore_group_invitation` (`chore_group_invitation_id`, `chore_group_id`, `recipient_user_id`, `status`, `last_updated`) VALUES
(1, 1, 2, 'ACCEPTED', '2016-07-31 23:03:40'),
(2, 2, 1, 'ACCEPTED', '2016-07-31 22:58:42');

-- --------------------------------------------------------

--
-- Table structure for table `chore_group_member`
--

CREATE TABLE `chore_group_member` (
  `chore_group_member_id` bigint(20) NOT NULL,
  `chore_group_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `chore_group_member`
--

INSERT INTO `chore_group_member` (`chore_group_member_id`, `chore_group_id`, `user_id`) VALUES
(1, 1, 1),
(3, 1, 2),
(2, 2, 2);

-- --------------------------------------------------------

--
-- Table structure for table `user_auth`
--

CREATE TABLE `user_auth` (
  `user_id` bigint(20) NOT NULL,
  `email` varchar(128) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `screen_name` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_auth`
--

INSERT INTO `user_auth` (`user_id`, `email`, `password_hash`, `screen_name`) VALUES
(1, 'grannys.cookies@hotmail.com', '$2y$10$jL3jit4MECubQLiOV1D3T.zqqOf5IY98XFxy4KQxiwtMb1MnuWHtS', NULL),
(2, 'petersonskylers@gmail.com', '$2y$10$pwUvsVP9hnewhfoltvfKeOGoCV/7QJETWactoeNxEPDf.8xjGYr..', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `chore_group`
--
ALTER TABLE `chore_group`
  ADD PRIMARY KEY (`chore_group_id`),
  ADD UNIQUE KEY `chore_group_name` (`chore_group_name`,`chore_group_owner`);

--
-- Indexes for table `chore_group_invitation`
--
ALTER TABLE `chore_group_invitation`
  ADD PRIMARY KEY (`chore_group_invitation_id`),
  ADD UNIQUE KEY `chore_group_id` (`chore_group_id`);

--
-- Indexes for table `chore_group_member`
--
ALTER TABLE `chore_group_member`
  ADD PRIMARY KEY (`chore_group_member_id`),
  ADD UNIQUE KEY `unique_chore_member` (`chore_group_id`,`user_id`);

--
-- Indexes for table `user_auth`
--
ALTER TABLE `user_auth`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `chore_group`
--
ALTER TABLE `chore_group`
  MODIFY `chore_group_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `chore_group_invitation`
--
ALTER TABLE `chore_group_invitation`
  MODIFY `chore_group_invitation_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `chore_group_member`
--
ALTER TABLE `chore_group_member`
  MODIFY `chore_group_member_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `user_auth`
--
ALTER TABLE `user_auth`
  MODIFY `user_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
