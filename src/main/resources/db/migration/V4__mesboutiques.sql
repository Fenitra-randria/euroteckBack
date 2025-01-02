

CREATE TABLE `mes_boutiques` (
  `id` bigint(20) NOT NULL,
  `utilisateur_id` bigint(20) DEFAULT NULL,
  `boutique_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `mes_boutiques_preferes` (
  `id` bigint(20) NOT NULL,
  `utilisateur_id` bigint(20) DEFAULT NULL,
  `boutique_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE `mes_boutiques` ADD CONSTRAINT `FK21564454665boutique` FOREIGN KEY (`boutique_id`) REFERENCES `boutique` (`id`);
  
ALTER TABLE `mes_boutiques` ADD CONSTRAINT `FK2564546546546821utilisateur` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateur` (`id`);
  
ALTER TABLE `mes_boutiques_preferes` ADD CONSTRAINT `FK21njvv4r56drb4vdrboutique` FOREIGN KEY (`boutique_id`) REFERENCES `boutique` (`id`);
  
ALTER TABLE `mes_boutiques_preferes` ADD CONSTRAINT `FK255vds64vsd6v4s6dutilisateur` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateur` (`id`);

