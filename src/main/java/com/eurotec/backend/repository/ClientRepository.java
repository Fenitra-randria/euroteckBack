package com.eurotec.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eurotec.backend.entity.Client;
import com.eurotec.backend.entity.Produit;
import com.eurotec.backend.entity.Utilisateur;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@Repository
public interface ClientRepository extends JpaRepository<Client, Long>
{

	@Query(value = "SELECT c FROM Client c where c.boutique.id = :id and ( c.nom like %:search% or c.prenom like %:search% or c.email like %:search% ) order by c.id desc")
	List<Client> rechercher(@Param("search") String search , @Param("id") Long id);
	
	Optional<Client> findByUtilisateurId(Long id);
	
}
