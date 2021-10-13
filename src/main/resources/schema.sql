CREATE TABLE IF NOT EXISTS Bank (
                                    id INTEGER AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Partner (
                                    id INTEGER AUTO_INCREMENT primary key,
                                    firstname VARCHAR(100) NOT NULL,
                                    lastname VARCHAR(100) NOT NULL,
                                    email VARCHAR(100) NOT NULL,
                                    bank_id INTEGER,
                                    foreign key (bank_id) references Bank(id)
);

CREATE TABLE IF NOT EXISTS OperationType (
                                    id INTEGER AUTO_INCREMENT primary key,
                                    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS OperationStatus (
                                    id INTEGER AUTO_INCREMENT primary key,
                                    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Operation (
                                    id INTEGER AUTO_INCREMENT primary key,
                                    operation_type_id INTEGER,
                                    operation_status_id INTEGER,
                                    foreign key (operation_type_id) references OperationType(id),
                                    foreign key (operation_status_id) references OperationStatus(id)
);

CREATE TABLE IF NOT EXISTS User (
                                    id INTEGER AUTO_INCREMENT primary key,
                                    username VARCHAR(255) NOT NULL UNIQUE,
                                    firstname VARCHAR(100) NOT NULL,
                                    lastname VARCHAR(100) NOT NULL,
                                    email VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Account (
                                    id INTEGER AUTO_INCREMENT primary key,
                                    balance DOUBLE NOT NULL,
                                    user_id INTEGER,
                                    foreign key (user_id) references User(id)
);

CREATE TABLE IF NOT EXISTS Card (
                                    id INTEGER AUTO_INCREMENT primary key,
                                    number VARCHAR(16) NOT NULL UNIQUE,
                                    account_id INTEGER,
                                    foreign key (account_id) references Account(id)
);

CREATE TABLE IF NOT EXISTS Role (
                                    id INTEGER AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS user_role (
                                    user_id INTEGER,
                                    role_id INTEGER,
                                    foreign key (user_id) references User(id),
                                    foreign key (role_id) references Role(id)
);

INSERT INTO OperationType (name) VALUES ('TRANSFER');
INSERT INTO OperationType (name) VALUES ('CREATE');

INSERT INTO OperationStatus (name) VALUES ('SUCCESS');
INSERT INTO OperationStatus (name) VALUES ('AWAITING');

INSERT INTO Bank (name) VALUES ('Tinkoff');
INSERT INTO Bank (name) VALUES ('AlphaBank');
INSERT INTO Bank (name) VALUES ('VTB');

INSERT INTO Role (name) VALUES ('USER');
INSERT INTO Role (name) VALUES ('ADMIN');

INSERT INTO User (username, firstname, lastname, email) VALUES ('username1', 'Maxim', 'Zhivykh', 'maxzhiv@gmail.com');
INSERT INTO User (username, firstname, lastname, email) VALUES ('username2', 'Artem', 'Lunin', 'artlun@gmail.com');

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 2);

INSERT INTO Account (balance, user_id) VALUES (10000, 1);
INSERT INTO Account (balance, user_id) VALUES (10000, 1);
INSERT INTO Account (balance, user_id) VALUES (30000, 2);
INSERT INTO Account (balance, user_id) VALUES (40000, 2);
INSERT INTO Account (balance, user_id) VALUES (50000, 2);

INSERT INTO Card (number, account_id) VALUES ('1111111111111111', 1);
INSERT INTO Card (number, account_id) VALUES ('2222222222222222', 2);
INSERT INTO Card (number, account_id) VALUES ('3333333333333333', 3);