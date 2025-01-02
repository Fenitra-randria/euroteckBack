package com.eurotec.backend.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.core.mapping.ExposureConfiguration;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.eurotec.backend.entity.Boutique;
import com.eurotec.backend.entity.Client;
import com.eurotec.backend.entity.Commande;
import com.eurotec.backend.entity.PointLivraison;
import com.eurotec.backend.entity.Produit;
import com.eurotec.backend.entity.Utilisateur;


@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {
		
	@Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration restConfig, CorsRegistry cors) {
        
		
        // id exposed entity
        restConfig.exposeIdsFor(Boutique.class);
        restConfig.exposeIdsFor(Client.class);
        restConfig.exposeIdsFor(Commande.class);
        restConfig.exposeIdsFor(PointLivraison.class);
        restConfig.exposeIdsFor(Commande.class);
        restConfig.exposeIdsFor(Produit.class);
        restConfig.exposeIdsFor(Utilisateur.class);
        
        
	}
	
}