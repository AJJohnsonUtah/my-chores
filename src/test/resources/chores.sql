-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Aug 21, 2016 at 02:07 AM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 5.6.23


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: chores
--

-- --------------------------------------------------------

--
-- Table structure for table chore
--

CREATE TABLE chore (
  chore_id bigint NOT NULL,
  chore_spec_id bigint NOT NULL,
  doer bigint NOT NULL,
  status int NOT NULL,
  duration int NOT NULL,
  created timestamp NOT NULL ,
  updated timestamp NOT NULL,
  location bigint 
);

-- --------------------------------------------------------

--
-- Table structure for table chore_app_role
--

CREATE TABLE chore_app_role (
  role_id int NOT NULL,
  role_name varchar(20) NOT NULL,
  description varchar(255) NOT NULL
) ;

--
-- Dumping data for table chore_app_role
--

INSERT INTO chore_app_role (role_id, role_name, description) VALUES
(1, 'OWNER', 'Owner of a chore group'),
(2, 'MEMBER', 'Member of a chore group'),
(3, 'ADMIN', 'Administrative user');

-- --------------------------------------------------------

--
-- Table structure for table chore_app_status
--

CREATE TABLE chore_app_status (
  status_id int NOT NULL,
  status_name varchar(20) NOT NULL,
  description varchar(255) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Table structure for table chore_group
--

CREATE TABLE chore_group (
  chore_group_id bigint NOT NULL,
  chore_group_name varchar(35) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Table structure for table chore_group_user
--

CREATE TABLE chore_group_user (
  chore_group_user_id bigint NOT NULL,
  chore_group bigint NOT NULL,
  chore_user bigint NOT NULL,
  invited_by bigint ,
  chore_group_user_role int NOT NULL,
  status int NOT NULL,
  updated timestamp NOT NULL
) ;

-- --------------------------------------------------------

--
-- Table structure for table chore_spec
--

CREATE TABLE chore_spec (
  chore_spec_id bigint NOT NULL,
  name varchar(50) NOT NULL,
  chore_group_id bigint NOT NULL,
  preferred_doer bigint ,
  frequency varchar(255) 
) ;

-- --------------------------------------------------------

--
-- Table structure for table chore_user
--

CREATE TABLE chore_user (
  user_id bigint NOT NULL,
  email varchar(128) NOT NULL,
  password_hash varchar(255) NOT NULL,
  screen_name varchar(20) 
) ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table chore
--
ALTER TABLE chore
  ADD PRIMARY KEY (chore_id);

--
-- Indexes for table chore_app_role
--
ALTER TABLE chore_app_role
  ADD PRIMARY KEY (role_id);

--
-- Indexes for table chore_app_status
--
ALTER TABLE chore_app_status
  ADD PRIMARY KEY (status_id);

--
-- Indexes for table chore_group
--
ALTER TABLE chore_group
  ADD PRIMARY KEY (chore_group_id);
ALTER TABLE chore_group
  ADD UNIQUE KEY chore_group_name (chore_group_name);

--
-- Indexes for table chore_group_user
--
ALTER TABLE chore_group_user
  ADD PRIMARY KEY (chore_group_user_id);
ALTER TABLE chore_group_user
  ADD UNIQUE KEY unique_chore_member (chore_group,chore_user);

--
-- Indexes for table chore_spec
--
ALTER TABLE chore_spec
  ADD PRIMARY KEY (chore_spec_id);

--
-- Indexes for table chore_user
--
ALTER TABLE chore_user
  ADD PRIMARY KEY (user_id);
ALTER TABLE chore_user
  ADD UNIQUE KEY email (email);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table chore
--
ALTER TABLE chore
  MODIFY chore_id bigint NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table chore_app_role
--
ALTER TABLE chore_app_role
  MODIFY role_id int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table chore_app_status
--
ALTER TABLE chore_app_status
  MODIFY status_id int NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table chore_group
--
ALTER TABLE chore_group
  MODIFY chore_group_id bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table chore_group_user
--
ALTER TABLE chore_group_user
  MODIFY chore_group_user_id bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table chore_spec
--
ALTER TABLE chore_spec
  MODIFY chore_spec_id bigint NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table chore_user
--
ALTER TABLE chore_user
  MODIFY user_id bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
