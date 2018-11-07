CREATE TABLE accounts
(
  id      BIGSERIAL PRIMARY KEY,
  balance BIGINT NOT NULL CHECK (balance >= 0)
);

INSERT INTO accounts (balance) VALUES (100);
INSERT INTO accounts (balance) VALUES (200);
INSERT INTO accounts (balance) VALUES (450);
INSERT INTO accounts (balance) VALUES (300);
INSERT INTO accounts (balance) VALUES (0);
INSERT INTO accounts (balance) VALUES (80);
INSERT INTO accounts (balance) VALUES (99);