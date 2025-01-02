package com.eurotec.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.eurotec.backend.dto.CommandeListeDto;
import com.eurotec.backend.entity.Commande;
import com.eurotec.backend.entity.Produit;
import com.eurotec.backend.entity.Utilisateur;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long>
{

	List<Commande> findByBoutiqueId(Long id);
	
	/*
	@Query(value = """ 
			SELECT new com.eurotec.backend.dto.CommandeDto( c.id , cl.nom , cl.prenom , c.dateSaisie ) 
			 FROM Commande c join c.client cl 
			 where 
			 ( cl.nom like %:search% or cl.prenom like %:search% ) 
			  and 
			 c.boutique.id = :id order by c.id desc 
			 """)
	List<CommandeListeDto> rechercher( @Param( "search")  String search,  @Param("id") Long id);
	*/
	
	@Query(value = """ 
					SELECT  c 
					 FROM Commande c join c.client cl 
					 where 
					 ( cl.nom like %:search% or cl.prenom like %:search% ) 
					  and 
					   c.statut like %:statut%
					  and 
					 c.boutique.id = :id order by c.id desc 
			 """)
	List<Commande> rechercher( @Param( "search")  String search,  @Param( "statut") String statut , @Param("id") Long id);
	
	
	@Query(value = """ 
			SELECT  c 
			 FROM Commande c 
			  join c.client cl 
			  join cl.utilisateur u 
			 where 
			  u.id = :idClient  
			  and 
			  c.statut like %:statut%
			  and 
			 c.boutique.id = 17
			 order by c.id desc 
	 """)
	List<Commande> rechercherParclient( @Param( "statut") String statut , @Param("idClient") Long idClient);
	
	
}



