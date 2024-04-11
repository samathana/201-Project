CREATE TABLE `Photo Diary`.`Friends` (
    friendshipID INT PRIMARY KEY,
    userID INT,
    friendID INT,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (FriendID) REFERENCES Users(UserID)
);