-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 09, 2024 at 02:43 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `login`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin_login`
--

CREATE TABLE `admin_login` (
  `AID` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `last_login_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin_login`
--

INSERT INTO `admin_login` (`AID`, `username`, `email`, `password`, `last_login_date`) VALUES
(2, 'admin', 'admin@gmail.com', 'admin', '2024-02-01 11:31:17');

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `courseID` int(11) NOT NULL,
  `courseName` varchar(255) NOT NULL,
  `noOfSeats` int(11) NOT NULL,
  `Batch` varchar(255) NOT NULL,
  `noOfYears` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`courseID`, `courseName`, `noOfSeats`, `Batch`, `noOfYears`) VALUES
(244, 'BTech', 300, '2024', 3),
(2331499, 'BIBM', 5000, '2024', 3),
(2334155, 'HIM', 300, '2024', 3);

-- --------------------------------------------------------

--
-- Table structure for table `course_modules`
--

CREATE TABLE `course_modules` (
  `level` int(11) DEFAULT NULL,
  `module_1` varchar(255) DEFAULT NULL,
  `module_2` varchar(255) DEFAULT NULL,
  `module_3` varchar(255) DEFAULT NULL,
  `module_4` varchar(255) DEFAULT NULL,
  `course` varchar(255) DEFAULT NULL,
  `module_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `course_modules`
--

INSERT INTO `course_modules` (`level`, `module_1`, `module_2`, `module_3`, `module_4`, `course`, `module_id`) VALUES
(4, 'computer', 'english', 'nepali', 'science', 'BIT', 1),
(4, 'networking', 'programming', 'fundamental of computer', 'ai / ml', 'BIT', 2),
(5, 'math', 'science', 'bio', 'chem', 'Btech', 3);

-- --------------------------------------------------------

--
-- Table structure for table `student_enroll`
--

CREATE TABLE `student_enroll` (
  `enrollID` int(11) NOT NULL,
  `studentName` varchar(255) DEFAULT NULL,
  `studentEmail` varchar(255) DEFAULT NULL,
  `course` varchar(255) DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  `module_1` varchar(255) DEFAULT NULL,
  `module_2` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student_enroll`
--

INSERT INTO `student_enroll` (`enrollID`, `studentName`, `studentEmail`, `course`, `level`, `module_1`, `module_2`) VALUES
(1, 'birat', 'birat@gmail.com', 'BIT', 'Level 4', '', ''),
(2, 'suvam', 'suvam@gmail.com', 'BIT', 'Level 5', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `student_login`
--

CREATE TABLE `student_login` (
  `SID` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `course` varchar(255) DEFAULT NULL,
  `last_login_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student_login`
--

INSERT INTO `student_login` (`SID`, `username`, `email`, `password`, `course`, `last_login_date`) VALUES
(16, 'hari', 'hari@gmail.com', 'ram', 'BIT', '2024-01-31 08:14:47'),
(17, 'Roshan Magar', 'roshan@gmail.com', 'roshan', 'BIBM (Business Management)', '2024-01-31 08:17:01');

-- --------------------------------------------------------

--
-- Table structure for table `student_report`
--

CREATE TABLE `student_report` (
  `student_ID` int(11) DEFAULT NULL,
  `student_name` varchar(255) NOT NULL,
  `course` varchar(255) NOT NULL,
  `total_percentage` decimal(5,2) NOT NULL,
  `gpa` varchar(255) NOT NULL,
  `module` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student_report`
--

INSERT INTO `student_report` (`student_ID`, `student_name`, `course`, `total_percentage`, `gpa`, `module`) VALUES
(16, 'ram.com', 'BIT', 80.00, 'A', 'COMPUTER'),
(17, 'ro.com', 'BIT', 90.00, 'A+', 'computer'),
(19, 'rohit', 'BIT', 90.00, 'A+', 'computer'),
(17, 'ro.com', 'BIT', 80.00, 'A', 'math');

-- --------------------------------------------------------

--
-- Table structure for table `teacher`
--

CREATE TABLE `teacher` (
  `TutorID` int(11) NOT NULL,
  `TutorName` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `teacher`
--

INSERT INTO `teacher` (`TutorID`, `TutorName`, `Email`, `Date`) VALUES
(233, 'NIraj', 'niraj@gmail.com', '2024-02-02'),
(444, 'Birat', 'birat@gmail.com', '2024-02-03'),
(553, 'vurun', 'vurun@gmail.com', '2024-02-07'),
(555, 'bibak', 'bibek@gmail.com', '2024-02-07');

-- --------------------------------------------------------

--
-- Table structure for table `teachermodule`
--

CREATE TABLE `teachermodule` (
  `Year` int(11) NOT NULL,
  `Module` int(11) NOT NULL,
  `Module_Tutor1` varchar(255) DEFAULT NULL,
  `Module_Tutor2` varchar(255) DEFAULT NULL,
  `Module_Tutor3` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `teacher_login`
--

CREATE TABLE `teacher_login` (
  `TID` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `last_login_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `teacher_login`
--

INSERT INTO `teacher_login` (`TID`, `username`, `email`, `password`, `last_login_date`) VALUES
(2, 'bibek.com', 'bibek@gmail.com', 'bibek', '2024-01-31 08:58:22');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin_login`
--
ALTER TABLE `admin_login`
  ADD PRIMARY KEY (`AID`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`courseID`);

--
-- Indexes for table `course_modules`
--
ALTER TABLE `course_modules`
  ADD PRIMARY KEY (`module_id`);

--
-- Indexes for table `student_enroll`
--
ALTER TABLE `student_enroll`
  ADD PRIMARY KEY (`enrollID`);

--
-- Indexes for table `student_login`
--
ALTER TABLE `student_login`
  ADD PRIMARY KEY (`SID`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `teacher`
--
ALTER TABLE `teacher`
  ADD PRIMARY KEY (`TutorID`);

--
-- Indexes for table `teachermodule`
--
ALTER TABLE `teachermodule`
  ADD PRIMARY KEY (`Year`);

--
-- Indexes for table `teacher_login`
--
ALTER TABLE `teacher_login`
  ADD PRIMARY KEY (`TID`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin_login`
--
ALTER TABLE `admin_login`
  MODIFY `AID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `course_modules`
--
ALTER TABLE `course_modules`
  MODIFY `module_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `student_enroll`
--
ALTER TABLE `student_enroll`
  MODIFY `enrollID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `student_login`
--
ALTER TABLE `student_login`
  MODIFY `SID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `teacher_login`
--
ALTER TABLE `teacher_login`
  MODIFY `TID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
