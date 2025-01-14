package com.eurotec.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class LigneCommande {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne
	Produit produit;

	Integer quantite;

	@ManyToOne
	Commande commande;

	Double prix;

	String choix;

	public String getChoix() {
		return choix;
	}

	public void setChoix(String choix) {
		this.choix = choix;
	}

	public Double getPrix() {
		return prix != null ? prix : 0.0;
	}

	public void setPrix(Double prix) {
		this.prix = prix;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public Integer getQuantite() {
		return quantite;
	}

	public void setQuantite(Integer quantite) {
		this.quantite = quantite;
	}

}
