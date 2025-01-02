package com.eurotec.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eurotec.backend.entity.Boutique;
import com.eurotec.backend.entity.MesBoutiques;
import com.eurotec.backend.entity.Utilisateur;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@Repository
public interface MesBoutiquesRepository extends JpaRepository<MesBoutiques, Long>{

	@Query(value = """
			SELECT mb FROM MesBoutiques mb where mb.utilisateur.id = :id and mb.boutique.actif = true
		""")
	List<MesBoutiques> mesboutiques(@Param("id") Long id);

}
