DROP TABLE IF EXISTS PLAYER, TEAM;

CREATE TABLE TEAM (
                      id INT NOT NULL PRIMARY KEY IDENTITY,
                      name VARCHAR(30) NOT NULL CONSTRAINT UN_name UNIQUE
);

CREATE TABLE PLAYER (
                        id INT NOT NULL PRIMARY KEY IDENTITY,
                        firstname VARCHAR(30) NOT NULL CONSTRAINT ck_firstname CHECK(LEN(firstname) BETWEEN 2 AND 30),
                        lastname VARCHAR(30) NOT NULL CONSTRAINT ck_lastname CHECK(LEN(lastname) BETWEEN 2 AND 30),
                        email VARCHAR(255) NULL,
                        id_team INT NOT NULL CONSTRAINT FK_team FOREIGN KEY REFERENCES TEAM(id)
);

INSERT INTO TEAM(name) VALUES ('U15F1'), ('U15M1'), ('U18F1'), ('U18M1');

SELECT * FROM TEAM;
SELECT * FROM PLAYER;
