package com.eurotec.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.eurotec.backend.entity.PointLivraison;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@Repository
public interface PointLivraisonRepository extends JpaRepository<PointLivraison, Long>
{
	
	List<PointLivraison> findByBoutiqueId(Long id);	
	
}
