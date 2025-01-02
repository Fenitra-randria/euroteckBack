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
import com.eurotec.backend.entity.Utilisateur;
import com.eurotec.backend.repository.CommandeRepository;
import com.eurotec.backend.repository.UtilisateurRepository;


@Component
@RepositoryEventHandler(Commande.class) 
public class CommandeEventHandler {

	@Autowired
	CommandeRepository commandeRepository;
	
	@HandleBeforeCreate
    public void handleCommandeAfterCreate(Commande c)
	{	
		c.setDateSaisie( LocalDate.now() );
		c.setStatut("en_attente");
		commandeRepository.save(c);
    }
	
}
