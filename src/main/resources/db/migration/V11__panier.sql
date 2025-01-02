CREATE TABLE panier ( `id` bigint(20) NOT NULL , `produit_id` bigint(20) NOT NULL , 
`client_id` bigint(20) NOT NULL , `quantite` INT NOT NULL , `choix` VARCHAR(10) NOT NULL ) 
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE `panier` ADD PRIMARY KEY (`id`);
  
ALTER TABLE panier MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;