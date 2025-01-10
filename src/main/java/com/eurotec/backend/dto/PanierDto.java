package com.eurotec.backend.dto;

import com.eurotec.backend.entity.Produit;
import com.eurotec.backend.entity.Utilisateur;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

public class PanierDto 
{
	
	Long client;
	
	Long produit;
	
	Integer quantite;

	String choix;

	public Long getClient() {
		return client;
	}

	public void setClient(Long client) {
		this.client = client;
	}

	public Long getProduit() {
		return produit;
	}

	public void setProduit(Long produit) {
		this.produit = produit;
	}

	public Integer getQuantite() {
		return quantite;
	}

	public void setQuantite(Integer quantite) {
		this.quantite = quantite;
	}

	public String getChoix() {
		return choix;
	}

	public void setChoix(String choix) {
		this.choix = choix;
	}

	
	
}
