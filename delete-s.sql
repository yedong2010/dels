DROP TABLE IF EXISTS  `accommentsinfo`;
CREATE TABLE `accommentsinfo` (
  `id` int(15) NOT NULL,
  `type` varchar(10) NOT NULL,
  `title` tinytext NOT NULL,
  `checkTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `url` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

DROP TABLE IF EXISTS  `accomments`;
CREATE TABLE `accomments` (
  `cid` int(15) NOT NULL,
  `content` text NOT NULL,
  `userName` tinytext NOT NULL,
  `layer` int(10) NOT NULL,
  `acid` int(15) NOT NULL,
  `isDelete` tinyint(10) NOT NULL,
  `checkTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY(acid, layer)
) ENGINE=InnoDB AUTO_INCREMENT=1000001 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

DROP TABLE IF EXISTS  `accommentsstore`;
CREATE TABLE `accommentsstore` (
  `cid` int(15) NOT NULL,
  `name` varchar(50) NOT NULL,
  `content` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



DROP TABLE IF EXISTS  `proxysinfo`;
CREATE TABLE `proxysinfo` (
  `proxy` varchar(30) NOT NULL,
  `errornum` int(5) NOT NULL DEFAULT 0,
  `checkTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`proxy`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

DROP TABLE IF EXISTS  `messages`;
CREATE TABLE `proxysinfo` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `status` tinyint(10) NOT NULL DEFAULT 0,
  `checktime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;