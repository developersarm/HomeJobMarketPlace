CREATE DATABASE `homejobmarketplace` /*!40100 DEFAULT CHARACTER SET latin1 */;

CREATE TABLE `member` (
  `id` int(32) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(32) NOT NULL,
  `last_name` varchar(32) DEFAULT NULL,
  `phone_no` int(10) unsigned NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(45) NOT NULL,
  `type` enum('seeker','sitter') NOT NULL,
  `address` varchar(250) NOT NULL,
  `pincode` int(6) unsigned NOT NULL,
  `status` enum('active','inactive') NOT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Saves information about members';

CREATE TABLE `seeker` (
  `seeker_id` int(32) unsigned NOT NULL AUTO_INCREMENT,
  `total_children` int(11) DEFAULT NULL,
  `spouse_name` varchar(45) DEFAULT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`seeker_id`),
  UNIQUE KEY `member_id_UNIQUE` (`seeker_id`),
  CONSTRAINT `seeker_id` FOREIGN KEY (`seeker_id`) REFERENCES `member` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Information specific to seeker';

CREATE TABLE `sitter` (
  `sitter_id` int(32) unsigned NOT NULL AUTO_INCREMENT,
  `experience` int(3) unsigned NOT NULL DEFAULT '0',
  `last_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`sitter_id`),
  UNIQUE KEY `member_id_UNIQUE` (`sitter_id`),
  CONSTRAINT `sitter_id` FOREIGN KEY (`sitter_id`) REFERENCES `member` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Information about sitter';

CREATE TABLE `job` (
  `id` int(32) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(256) NOT NULL,
  `posted_by` int(32) unsigned NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `pay_per_hour` decimal(10,0) unsigned NOT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `posted_by_idx` (`posted_by`),
  CONSTRAINT `posted_by` FOREIGN KEY (`posted_by`) REFERENCES `member` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Information about job';

CREATE TABLE `job_application` (
  `id` int(32) unsigned NOT NULL AUTO_INCREMENT,
  `job_id` int(32) unsigned NOT NULL,
  `member_id` int(32) unsigned NOT NULL,
  `expected_pay` int(32) unsigned NOT NULL DEFAULT '0',
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `job_id_idx` (`job_id`),
  KEY `member_id_idx` (`member_id`),
  CONSTRAINT `job_id` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `member_id` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Information about applications';