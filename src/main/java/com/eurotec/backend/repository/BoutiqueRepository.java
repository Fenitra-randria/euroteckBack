package com.eurotec.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eurotec.backend.entity.Boutique;
import com.eurotec.backend.entity.Utilisateur;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@Repository
public interface BoutiqueRepository extends JpaRepository<Boutique, Long>{

	int countByUtilisateur(Utilisateur u);
	
	List<Boutique> findByUtilisateur(Utilisateur u);

	List<Boutique> findByUtilisateurId(Long id);


}
