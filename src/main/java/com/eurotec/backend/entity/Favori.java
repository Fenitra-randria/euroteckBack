package com.eurotec.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Favori {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;

	@NotNull
	@ManyToOne
	Utilisateur client;
	
	@NotNull
	@ManyToOne
	Produit produit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Utilisateur getClient() {
		return client;
	}

	public void setClient(Utilisateur client) {
		this.client = client;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}
	
	
	
	
}
