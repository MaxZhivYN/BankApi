CREATE TABLE IF NOT EXISTS User (
                                    id INTEGER AUTO_INCREMENT primary key,
                                    name VARCHAR(25) NOT NULL,
                                    patronymic VARCHAR(25) NOT NULL,
                                    surname VARCHAR(25) NOT NULL,
                                    email VARCHAR(256) NOT NULL,
                                    phone VARCHAR(25) NOT NULL
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



INSERT INTO User (name, patronymic, surname, email, phone) VALUES ('Maxim', 'Olegovich', 'Zhivykh', 'maxzhiv@gmail.com', '89252288558');
INSERT INTO User (name, patronymic, surname, email, phone) VALUES ('Vladimir', 'Vladimirovich', 'Babilev', 'VVB@gmail.com', '89252288559');

INSERT INTO Account (balance, user_id) VALUES (10000, 1);
INSERT INTO Account (balance, user_id) VALUES (20000, 1);
INSERT INTO Account (balance, user_id) VALUES (30000, 2);
INSERT INTO Account (balance, user_id) VALUES (40000, 2);
INSERT INTO Account (balance, user_id) VALUES (50000, 2);

INSERT INTO Card (number, account_id) VALUES ('1111111111111111', 1);
INSERT INTO Card (number, account_id) VALUES ('2222222222222222', 2);
INSERT INTO Card (number, account_id) VALUES ('3333333333333333', 3);