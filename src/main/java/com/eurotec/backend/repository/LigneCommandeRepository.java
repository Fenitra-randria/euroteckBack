package com.eurotec.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eurotec.backend.entity.Commande;
import com.eurotec.backend.entity.LigneCommande;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@SecurityRequirement(name = "bearerAuth")
@Repository
public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long>{

	List<LigneCommande> findByCommande(Commande c);

}