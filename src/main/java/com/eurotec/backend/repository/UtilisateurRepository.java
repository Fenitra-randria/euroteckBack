package com.eurotec.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eurotec.backend.entity.Utilisateur;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

// @SecurityRequirement(name = "bearerAuth")
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>{

	Optional<Utilisateur> findByEmail(String email);
	
}
