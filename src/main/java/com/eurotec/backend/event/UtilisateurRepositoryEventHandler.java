package com.eurotec.backend.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.eurotec.backend.entity.Utilisateur;
import com.eurotec.backend.repository.UtilisateurRepository;


@Component
@RepositoryEventHandler(Utilisateur.class) 
public class UtilisateurRepositoryEventHandler {

	@Autowired
	UtilisateurRepository utilisateurRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@HandleBeforeCreate
    public void handleUtilisateurAfterCreate(Utilisateur u)
	{	
		u.setPassword( passwordEncoder.encode( u.getPassword() ) );  
		utilisateurRepository.save(u);
    }
	
}
