package com.eurotec.backend.dto;

public class LigneCommandeListeDto 
{
	String photo;
	
	String reference;
	String nom;
	String codeBarre;
	String choix;
	Integer quantite;
	Double prix;
	Double total;
	
	String categorie;
	
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getCodeBarre() {
		return codeBarre;
	}

	public void setCodeBarre(String codeBarre) {
		this.codeBarre = codeBarre;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
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

	public Double getPrix() {
		return prix;
	}

	public void setPrix(Double prix) {
		this.prix = prix;
	}
	
}
