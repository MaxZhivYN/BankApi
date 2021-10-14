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
                                    card_id INTEGER,
                                    foreign key (operation_type_id) references OperationType(id),
                                    foreign key (operation_status_id) references OperationStatus(id),
                                    foreign key (card_id) references Card(id)
);

INSERT INTO OperationType (name) VALUES ('CREATE');
INSERT INTO OperationType (name) VALUES ('TRANSFER');

INSERT INTO OperationStatus (name) VALUES ('SUCCESS');
INSERT INTO OperationStatus (name) VALUES ('AWAITING');

INSERT INTO Bank (name) VALUES ('Tinkoff');
INSERT INTO Bank (name) VALUES ('AlphaBank');
INSERT INTO Bank (name) VALUES ('VTB');


INSERT INTO User (username, firstname, lastname, email) VALUES ('username1', 'Maxim', 'Zhivykh', 'maxzhiv@gmail.com');
INSERT INTO User (username, firstname, lastname, email) VALUES ('username2', 'Artem', 'Lunin', 'artlun@gmail.com');

INSERT INTO Account (balance, user_id) VALUES (10000, 1);
INSERT INTO Account (balance, user_id) VALUES (10000, 1);
INSERT INTO Account (balance, user_id) VALUES (30000, 2);
INSERT INTO Account (balance, user_id) VALUES (40000, 2);
INSERT INTO Account (balance, user_id) VALUES (50000, 2);

INSERT INTO Card (number, account_id) VALUES ('1111111111111111', 1);
INSERT INTO Card (number, account_id) VALUES ('2222222222222222', 2);
INSERT INTO Card (number, account_id) VALUES ('3333333333333333', 3);
INSERT INTO Card (number, account_id) VALUES ('4444444444444444', 4);

INSERT INTO Operation (operation_type_id, operation_status_id, card_id) VALUES (1, 1, 1);
INSERT INTO Operation (operation_type_id, operation_status_id, card_id) VALUES (1, 1, 2);
INSERT INTO Operation (operation_type_id, operation_status_id, card_id) VALUES (1, 1, 3);
INSERT INTO Operation (operation_type_id, operation_status_id, card_id) VALUES (1, 2, 4);

INSERT INTO Partner (firstname, lastname, email, bank_id) VALUES ('Valera', 'Kolokolov', 'vk@mail.ru', 1);
INSERT INTO Partner (firstname, lastname, email, bank_id) VALUES ('Maxim', 'Maximov', 'mm@mail.ru', 2);
INSERT INTO Partner (firstname, lastname, email, bank_id) VALUES ('Vitya', 'Baranov', 'vb@mail.ru', 3);