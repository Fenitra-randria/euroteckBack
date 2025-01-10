package com.eurotec.backend.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.eurotec.backend.entity.Produit;
import com.eurotec.backend.repository.ProduitRepository;
import com.eurotec.backend.service.DataDto;
import com.eurotec.backend.service.MessageFcmDto;
import com.eurotec.backend.service.NotificationService;
import com.eurotec.backend.service.PromotionFcmDto;

import jakarta.persistence.EntityManager;

@Component
@RepositoryEventHandler(Produit.class) 
public class ProduitRepositoryEventHandler 
{
	@Autowired
	NotificationService notificationService;
	
	Boolean promotionAvant;
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	ProduitRepository produitRepository;
	
    @HandleBeforeSave
    private void beforeUpdate(Produit produit) 
    {
    	try
		{
    		/*
        	entityManager.detach(produit);
            Produit avant = produitRepository.getReferenceById( produit.getId() );
            promotionAvant = avant.getPromotion();
	        */
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
    	
    }
    
    @HandleAfterSave
    private void afterUpdate(Produit produit) 
    {
    	try
		{
    		
    		/*	
    		if( (promotionAvant.booleanValue() == false) && (produit.getPromotion().booleanValue() == true) ) 
        	{
            	System.out.println(" ===================== produit en promotion ");
            	notificationService.envoyerNotififcationPromotion(produit);
        	}
    		*/
	        
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
    		
    }

	
}
