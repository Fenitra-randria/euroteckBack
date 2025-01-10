ALTER TABLE `client` ADD `utilisateur_id` bigint(20) NULL ;

ALTER TABLE `client` ADD CONSTRAINT `FK21564524862146546534168` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateur` (`id`);
