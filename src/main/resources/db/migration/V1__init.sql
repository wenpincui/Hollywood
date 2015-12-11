CREATE TABLE `movies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `movieId` int(32) DEFAULT 0,
  `title` varchar(256) DEFAULT NULL,
  `genres` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;

CREATE TABLE `ratings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(32) DEFAULT 0,
  `movieId` int(32) DEFAULT 0,
  `rating` float(10,1) DEFAULT '0.5',
  `timestamp` int(32) DEFAULT 0,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;

CREATE TABLE `links` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `movieId` int(32) DEFAULT 0,
  `imdbId` varchar(256) DEFAULT 0,
  `tmdbId` varchar(256) DEFAULT 0,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;

CREATE TABLE `tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(32) DEFAULT 0,
  `movieId` int(32) DEFAULT 0,
  `tag` varchar(256) DEFAULT NULL,
  `timestamp` int(32) DEFAULT 0,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;

ALTER TABLE `movies`
ADD UNIQUE INDEX `movie_id_idx` (`movieId` ASC),
ADD INDEX `title_idx` (`title` ASC);

ALTER TABLE `ratings`
ADD INDEX `movie_id_idx` (`movieId` ASC),
ADD INDEX `user_id_idx` (`userId` ASC),
ADD INDEX `rating_idx` (`rating` ASC);

ALTER TABLE `links`
ADD INDEX `movie_id_idx` (`movieId` ASC);

ALTER TABLE `tags`
ADD INDEX `movie_id_idx` (`movieId` ASC),
ADD INDEX `tag_idx` (`userId` ASC);
