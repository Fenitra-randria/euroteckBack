package com.eurotec.backend.event;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.eurotec.backend.entity.Commande;
import com.eurotec.backend.entity.LigneCommande;
import com.eurotec.backend.entity.Produit;
import com.eurotec.backend.entity.Utilisateur;
import com.eurotec.backend.repository.CommandeRepository;
import com.eurotec.backend.repository.ProduitRepository;
import com.eurotec.backend.repository.UtilisateurRepository;


@Component
@RepositoryEventHandler(LigneCommande.class) 
public class LigneCommandeEventHandler {

	@Autowired
	ProduitRepository produitRepository;
	
	@HandleAfterCreate
    public void handleLigneCommandeAfterCreate(LigneCommande l)
	{	
		//Produit p = l.getProduit();
		//Integer qte = l.getQuantite();
		
		//p.setStockReel( p.getStockReel() - qte );
		//produitRepository.saveAndFlush(p);
    }
	
}
