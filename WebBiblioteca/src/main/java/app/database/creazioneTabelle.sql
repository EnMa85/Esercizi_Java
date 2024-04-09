CREATE DATABASE biblioteca;
USE biblioteca;

CREATE TABLE `admins` (
  `id` int AUTO_INCREMENT PRIMARY KEY,
  `username` varchar(10) NOT NULL UNIQUE,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Inseriamo nella tabella admin l'user con la password "pass" già criptata.
-- Le password degli utenti le creeremo e cripteremo dal programma.
INSERT INTO admins (username, password) 
VALUES ('admin', 'd74ff0ee8da3b9806b18c877dbf29bbde50b5bd8e4dad7a3a725000feb82e8f1');

CREATE TABLE `utenti` (
  `id` int AUTO_INCREMENT PRIMARY KEY,
  `CF` varchar(16) NOT NULL UNIQUE,
  `password` varchar(100) NOT NULL,
  `stato_account` TINYINT(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

 CREATE TABLE `libri` (
  `id` int AUTO_INCREMENT PRIMARY KEY,
  `titolo` varchar(50) NOT NULL,
  `autore` varchar(50) NOT NULL,
  `anno` int NOT NULL,
  `maxGiorniPrestito` int DEFAULT 30,
  `stato` TINYINT(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `prenotazioni` (
  `id` int AUTO_INCREMENT PRIMARY KEY,
  `id_libro` int NOT NULL,
  `id_utente` int NOT NULL,
  `data_inizio` date NOT NULL,
  `data_fine_massima` date NOT NULL,
  `data_fine_effettiva` date,
  FOREIGN KEY (id_libro) REFERENCES libri(`id`) ON UPDATE CASCADE,
  FOREIGN KEY (id_utente) REFERENCES utenti(`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

