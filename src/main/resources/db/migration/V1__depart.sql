

CREATE TABLE `boutique` (
  `actif` bit(1) DEFAULT NULL,
  `solde_actuel` double DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `utilisateur_id` bigint(20) DEFAULT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  `categorie` varchar(255) DEFAULT NULL,
  `code_pays` varchar(255) DEFAULT NULL,
  `code_postal` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `nom` varchar(255) NOT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `tel_fixe` varchar(255) DEFAULT NULL,
  `type_commerce` varchar(255) DEFAULT NULL,
  `ville` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `boutique`
--

INSERT INTO `boutique` (`actif`, `solde_actuel`, `id`, `utilisateur_id`, `adresse`, `categorie`, `code_pays`, `code_postal`, `fax`, `nom`, `photo`, `tel_fixe`, `type_commerce`, `ville`) VALUES
(b'1', -4571.4, 1, 1, '46/48 RUE DE LA MADELEINE', NULL, 'FR', '28230', '02 37 83 53 49', 'Boutique Test ', NULL, '02 37 83 44 56', NULL, 'EPERNON');

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `boutique_id` bigint(20) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `numero` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`boutique_id`, `id`, `email`, `nom`, `numero`, `prenom`) VALUES
(1, 1, 'louis@gmail.com', 'martin', '25367894', 'louis'),
(1, 2, 'marie@gmail.com', 'silph', '25698147', 'marie');

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

CREATE TABLE `commande` (
  `date_echeance` date DEFAULT NULL,
  `date_saisie` date DEFAULT NULL,
  `boutique_id` bigint(20) DEFAULT NULL,
  `client_id` bigint(20) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `methode_paiement` varchar(255) DEFAULT NULL,
  `numero` varchar(255) DEFAULT NULL,
  `point_livraison` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `ligne_commande`
--

CREATE TABLE `ligne_commande` (
  `prix` double DEFAULT NULL,
  `quantite` int(11) DEFAULT NULL,
  `commande_id` bigint(20) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `produit_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `point_livraison`
--

CREATE TABLE `point_livraison` (
  `boutique_id` bigint(20) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `detail_ouverture` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `point_livraison`
--

INSERT INTO `point_livraison` (`boutique_id`, `id`, `detail_ouverture`, `nom`) VALUES
(1, 1, 'lundi - vendredi ( 8h-16h)', 'station gare'),
(1, 2, 'mardi - jeudi ( 9h-18h)', 'coin de rue');

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `min_commande` int(11) DEFAULT NULL,
  `nombre_article_colis` int(11) DEFAULT NULL,
  `prix1` double DEFAULT NULL,
  `prix2` double DEFAULT NULL,
  `prix3` double DEFAULT NULL,
  `stock_reel` int(11) DEFAULT NULL,
  `stock_virtuel` int(11) DEFAULT NULL,
  `boutique_id` bigint(20) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `code_article` varchar(255) DEFAULT NULL,
  `code_barre` varchar(255) DEFAULT NULL,
  `dispo_web` varchar(255) DEFAULT NULL,
  `etat` varchar(255) DEFAULT NULL,
  `famille` varchar(255) DEFAULT NULL,
  `famille_code` varchar(255) DEFAULT NULL,
  `nom` varchar(255) NOT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `rupture_de_stock` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`min_commande`, `nombre_article_colis`, `prix1`, `prix2`, `prix3`, `stock_reel`, `stock_virtuel`, `boutique_id`, `id`, `code_article`, `code_barre`, `dispo_web`, `etat`, `famille`, `famille_code`, `nom`, `photo`, `rupture_de_stock`, `type`) VALUES
(5, NULL, 15.05, 12, NULL, 20, NULL, 1, 1, '', '21145588888866', '', NULL, '', '', 'chaise en bois', NULL, '', 'partiel'),
(NULL, NULL, 10, NULL, NULL, 30, NULL, 1, 3, '', '', '', NULL, '', '', 'sac light', NULL, '', 'normal');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `numero` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `roles` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `email`, `nom`, `numero`, `password`, `prenom`, `roles`) VALUES
(1, 'boutique@gmail.com', NULL, NULL, '$2a$10$tKNtKfdoIXKU1z6BfuOvtODt5F6vkB.C5rYXhbL/6d99.3A4OGDdK', NULL, 'BOUTIQUE');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `boutique`
--
ALTER TABLE `boutique`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKq0gw7f5s9a4iykk12kflbtojm` (`utilisateur_id`);

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKq47ula12tpatbxktj5c7t6nuy` (`boutique_id`);

--
-- Index pour la table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK89vuhv3tojxv1bybnfxj6rexb` (`boutique_id`),
  ADD KEY `FK79q1nginx2k3m83vi3bt3rlon` (`client_id`);

--
-- Index pour la table `ligne_commande`
--
ALTER TABLE `ligne_commande`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKaff2bjyreiuyi723relg10spm` (`commande_id`),
  ADD KEY `FK5ykb96p8me6913jyiwbe8nyj5` (`produit_id`);

--
-- Index pour la table `point_livraison`
--
ALTER TABLE `point_livraison`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKki14r2kv2j4p5a5siukqixb4s` (`boutique_id`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKib0723v64htrn73dji6qgu9ov` (`boutique_id`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_rma38wvnqfaf66vvmi57c71lo` (`email`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `boutique`
--
ALTER TABLE `boutique`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `commande`
--
ALTER TABLE `commande`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `ligne_commande`
--
ALTER TABLE `ligne_commande`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `point_livraison`
--
ALTER TABLE `point_livraison`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `boutique`
--
ALTER TABLE `boutique`
  ADD CONSTRAINT `FKq0gw7f5s9a4iykk12kflbtojm` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateur` (`id`);

--
-- Contraintes pour la table `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `FKq47ula12tpatbxktj5c7t6nuy` FOREIGN KEY (`boutique_id`) REFERENCES `boutique` (`id`);

--
-- Contraintes pour la table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `FK79q1nginx2k3m83vi3bt3rlon` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`),
  ADD CONSTRAINT `FK89vuhv3tojxv1bybnfxj6rexb` FOREIGN KEY (`boutique_id`) REFERENCES `boutique` (`id`);

--
-- Contraintes pour la table `ligne_commande`
--
ALTER TABLE `ligne_commande`
  ADD CONSTRAINT `FK5ykb96p8me6913jyiwbe8nyj5` FOREIGN KEY (`produit_id`) REFERENCES `produit` (`id`),
  ADD CONSTRAINT `FKaff2bjyreiuyi723relg10spm` FOREIGN KEY (`commande_id`) REFERENCES `commande` (`id`);

--
-- Contraintes pour la table `point_livraison`
--
ALTER TABLE `point_livraison`
  ADD CONSTRAINT `FKki14r2kv2j4p5a5siukqixb4s` FOREIGN KEY (`boutique_id`) REFERENCES `boutique` (`id`);

--
-- Contraintes pour la table `produit`
--
ALTER TABLE `produit`
  ADD CONSTRAINT `FKib0723v64htrn73dji6qgu9ov` FOREIGN KEY (`boutique_id`) REFERENCES `boutique` (`id`);
COMMIT;

