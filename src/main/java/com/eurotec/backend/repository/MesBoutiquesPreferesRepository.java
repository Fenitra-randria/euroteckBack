package com.eurotec.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eurotec.backend.entity.Boutique;
import com.eurotec.backend.entity.MesBoutiques;
import com.eurotec.backend.entity.MesBoutiquesPreferes;
import com.eurotec.backend.entity.Utilisateur;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@Repository
public interface MesBoutiquesPreferesRepository extends JpaRepository<MesBoutiquesPreferes, Long>{

	//List<Boutique> mesboutiquespreferes(Long id);

}
