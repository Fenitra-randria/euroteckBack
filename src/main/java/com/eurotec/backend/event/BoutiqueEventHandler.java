package com.eurotec.backend.event;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterLinkSave;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.eurotec.backend.entity.Boutique;
import com.eurotec.backend.repository.BoutiqueRepository;

@Component
@RepositoryEventHandler(Boutique.class) 
public class BoutiqueEventHandler {

	@Autowired
	BoutiqueRepository boutiqueRepository;
	
	@HandleAfterCreate
    public void handleBoutiqueAfterCreate(Boutique b)
	{	
		Long numero =  b.getId() + Long.valueOf(100000L);
		b.setNumero(numero);
		boutiqueRepository.saveAndFlush(b);
    }
	
	
}
