-- Per eliminare il database, in caso si voglia ricrearlo con nuove impostazioni
DROP DATABASE prodotti;

CREATE DATABASE prodotti;
USE prodotti;	
                             
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
  `username` varchar(10) NOT NULL UNIQUE,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

 CREATE TABLE `prodotti` (
  `id` int AUTO_INCREMENT PRIMARY KEY,
  `nome` varchar(50) NOT NULL,
  `prezzo` double NOT NULL,
  `quantita` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ordini` (
  `id` int AUTO_INCREMENT PRIMARY KEY,
  `data` date NOT NULL,
  `id_utente` int NOT NULL,
  `totale` double NOT NULL,
   FOREIGN KEY (id_utente) REFERENCES utenti(`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `dettaglio_ordine` (
  `id` int AUTO_INCREMENT PRIMARY KEY,
  `id_ordine` int NOT NULL,
  `id_prodotto` int NOT NULL,
  `nome_prodotto` VARCHAR(50) NOT NULL,
  `prezzo_prodotto` double NOT NULL,
  `quantita_ordinata` int NOT NULL,
   FOREIGN KEY (id_ordine) REFERENCES ordini(`id`),
   FOREIGN KEY (id_prodotto) REFERENCES prodotti(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- Prova Select
SELECT 
    ordini.id, 
    DATE_FORMAT(ordini.data, '%d-%m-%Y') AS data, 
    ordini.totale, 
    GROUP_CONCAT(
        CONCAT(
            dettaglio_ordine.nome_prodotto, ' - ',
            dettaglio_ordine.prezzo_prodotto, ' - ',
            dettaglio_ordine.quantita_ordinata
        )
        SEPARATOR ', '
    ) AS dettaglio
FROM 
    ordini
JOIN 
    dettaglio_ordine ON dettaglio_ordine.id_ordine = ordini.id
WHERE 
    ordini.id_utente = 1
GROUP BY 
    ordini.id, ordini.data, ordini.totale
ORDER BY 
    ordini.id DESC;

SELECT o.id AS ordine_id, o.data, o.totale,
       d.nome_prodotto, d.prezzo_prodotto, d.quantita_ordinata
FROM ordini o
JOIN (SELECT id_ordine, nome_prodotto, prezzo_prodotto, quantita_ordinata
      FROM dettaglio_ordine) d ON o.id = d.id_ordine;