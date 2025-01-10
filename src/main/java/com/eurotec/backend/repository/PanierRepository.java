package com.eurotec.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.eurotec.backend.entity.Boutique;
import com.eurotec.backend.entity.Panier;
import com.eurotec.backend.entity.Produit;
import com.eurotec.backend.entity.Utilisateur;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@Repository
public interface PanierRepository extends JpaRepository<Panier, Long>
{
    int countByProduitAndClient(Produit p , Utilisateur u);    
    
    List<Panier> findByClientId(Long id);
    
    @Modifying
    @Query("DELETE FROM Panier p WHERE p.client.id = :clientId")
    void deleteAllByClientId(@Param("clientId") Long clientId);
}