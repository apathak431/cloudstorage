CREATE TABLE IF NOT EXISTS USER (
  id INT PRIMARY KEY auto_increment,
  username VARCHAR(20),
  salt VARCHAR (255),
  password VARCHAR(255),
  firstName VARCHAR(20),
  lastName VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS NOTE (
    id INT PRIMARY KEY auto_increment,
    noteTitle VARCHAR(20),
    noteDescription VARCHAR (1000),
    userId INT,
    foreign key (userId) references USER(id)
);

CREATE TABLE IF NOT EXISTS FILE (
    id INT PRIMARY KEY auto_increment,
    filename VARCHAR(255),
    contentType VARCHAR(255),
    fileSize VARCHAR(255),
    fileData LONGBLOB,
    userId INT,
    foreign key (userId) references USER(id)
);

CREATE TABLE IF NOT EXISTS CREDENTIAL (
    id INT PRIMARY KEY auto_increment,
    url VARCHAR(100),
    username VARCHAR (30),
    `key` VARCHAR(255),
    password VARCHAR(255),
    userId INT,
    foreign key (userId) references USER(id)
);