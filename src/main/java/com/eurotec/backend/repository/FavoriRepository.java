package com.eurotec.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eurotec.backend.entity.Favori;
import com.eurotec.backend.entity.Panier;
import com.eurotec.backend.entity.Produit;
import com.eurotec.backend.entity.Utilisateur;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@Repository
public interface FavoriRepository extends JpaRepository<Favori, Long>
{
	int countByProduitAndClient(Produit p , Utilisateur u);	
	
	int countByProduitIdAndClientId(Long p , Long u);	
	
	Favori findByProduitIdAndClientId(Long p , Long u);	
	
	List<Favori> findByClientId(Long id);

}
