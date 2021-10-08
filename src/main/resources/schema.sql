CREATE TABLE IF NOT EXISTS ACCOUNT (
                         id INTEGER AUTO_INCREMENT primary key,
                         name VARCHAR(22) NOT NULL UNIQUE,
                         account_balance DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS CARD (
                      id INTEGER AUTO_INCREMENT primary key,
                      name VARCHAR(22) NOT NULL,
                      account_id INTEGER,
                      card_balance DOUBLE NOT NULL,
                      foreign key (account_id) references ACCOUNT(id)
);

INSERT INTO ACCOUNT (name, account_balance) VALUES ('Vova account', 10000.00);
INSERT INTO ACCOUNT (name, account_balance) VALUES ('Goga account', 10000.00);
INSERT INTO CARD (name, account_id, card_balance) VALUES ('Vova card 1', 1, 5000.50);
INSERT INTO CARD (name, account_id, card_balance) VALUES ('Vova card 2', 1, 5000.50);
INSERT INTO CARD (name, account_id, card_balance) VALUES ('Vova card 3', 1, 5000.50);
INSERT INTO CARD (name, account_id, card_balance) VALUES ('Vova card 4', 1, 5000.50);
INSERT INTO CARD (name, account_id, card_balance) VALUES ('Vova card 5', 1, 5000.50);