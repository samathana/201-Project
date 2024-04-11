CREATE DATABASE `Photo Diary`;

CREATE TABLE `Photo Diary`.`Users` (
    UserID INT PRIMARY KEY,
    UserName VARCHAR(50) NOT NULL
);

CREATE TABLE `Photo Diary`.`Friends` (
    friendshipID INT PRIMARY KEY,
    userID INT,
    friendID INT,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (FriendID) REFERENCES Users(UserID)
);

CREATE UNIQUE INDEX idx_UserName ON `Photo Diary`. `Users` (UserName);

CREATE TABLE `Photo Diary`.`Entries` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user` VARCHAR(45),
  `image` VARCHAR(45) NULL,
  `caption` VARCHAR(255) NULL,
  `time` DATETIME NULL,
  `longitude` DECIMAL(10,6) NULL,
  `latitude` DECIMAL(10,6) NULL,
  `likeCount` INT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user`) REFERENCES Users(`UserName`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE
);