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

-- Insertion des équipes
INSERT INTO TEAM(name) VALUES
    ('U15F1'),
    ('U15M1'),
    ('U18F1'),
    ('U18M1');

-- Insertion des joueurs pour U15F1
INSERT INTO PLAYER(firstname, lastname, email, id_team) VALUES
    ('Emma', 'Lemoine', 'emma.lemoine@example.com', 1),
    ('Chloé', 'Durand', 'chloe.durand@example.com', 1),
    ('Zoé', 'Pires', 'zoe.pires@example.com', 1),
    ('Lina', 'Guerrier', 'lina.guerrier@example.com', 1),
    ('Marie', 'Dufresne', 'marie.dufresne@example.com', 1),
    ('Inès', 'Thomas', 'ines.thomas@example.com', 1),
    ('Clara', 'Fournier', 'clara.fournier@example.com', 1),
    ('Lucie', 'Caron', 'lucie.caron@example.com', 1);

-- Insertion des joueurs pour U15M1
INSERT INTO PLAYER(firstname, lastname, email, id_team) VALUES
    ('Léo', 'Roche', 'leo.roche@example.com', 2),
    ('Maxime', 'Lemoine', 'maxime.lemoine@example.com', 2),
    ('Julien', 'David', 'julien.david@example.com', 2),
    ('Arthur', 'Lemoine', 'arthur.lemoine@example.com', 2),
    ('Nicolas', 'Bastien', 'nicolas.bastien@example.com', 2),
    ('Sacha', 'Lefevre', 'sacha.lefevre@example.com', 2),
    ('Victor', 'Dumas', 'victor.dumas@example.com', 2),
    ('Gabriel', 'Bernard', 'gabriel.bernard@example.com', 2),
    ('Antoine', 'Lambert', 'antoine.lambert@example.com', 2),
    ('Paul', 'Joly', 'paul.joly@example.com', 2);

-- Insertion des joueurs pour U18F1
INSERT INTO PLAYER(firstname, lastname, email, id_team) VALUES
    ('Marie', 'Tanguy', 'marie.tanguy@example.com', 3),
    ('Sophie', 'Chevalier', 'sophie.chevalier@example.com', 3),
    ('Léa', 'Martin', 'lea.martin@example.com', 3),
    ('Camille', 'Besson', 'camille.besson@example.com', 3),
    ('Sarah', 'Leclerc', 'sarah.leclerc@example.com', 3),
    ('Claire', 'Marchand', 'claire.marchand@example.com', 3),
    ('Lucie', 'Hugo', 'lucie.hugo@example.com', 3),
    ('Justine', 'Moreau', 'justine.moreau@example.com', 3),
    ('Manon', 'Pellissier', 'manon.pellissier@example.com', 3),
    ('Charlotte', 'Giraud', 'charlotte.giraud@example.com', 3),
    ('Alice', 'Lemoine', 'alice.lemoine@example.com', 3);

-- Insertion des joueurs pour U18M1
INSERT INTO PLAYER(firstname, lastname, email, id_team) VALUES
    ('Marc', 'Fournier', 'marc.fournier@example.com', 4),
    ('Bastien', 'David', 'bastien.david@example.com', 4),
    ('Vincent', 'Trudeau', 'vincent.trudeau@example.com', 4),
    ('Tom', 'Pires', 'tom.pires@example.com', 4),
    ('Alexis', 'Gerard', 'alexis.gerard@example.com', 4),
    ('Romain', 'Bouvier', 'romain.bouvier@example.com', 4),
    ('Mickael', 'Renard', 'mickael.renard@example.com', 4),
    ('Cédric', 'Sullivan', 'cedric.sullivan@example.com', 4),
    ('Julien', 'Vallier', 'julien.vallier@example.com', 4),
    ('Sébastien', 'Gauthier', 'sebastien.gauthier@example.com', 4),
    ('Paul', 'Pires', 'paul.pires@example.com', 4),
    ('Maxime', 'Blanc', 'maxime.blanc@example.com', 4),
    ('Kévin', 'Perrin', 'kevin.perrin@example.com', 4);
