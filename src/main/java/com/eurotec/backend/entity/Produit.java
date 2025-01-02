package com.eurotec.backend.entity;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Produit 
{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	
	@NotBlank
	@Column(nullable = false)
	String nom;
	
	String codeArticle;

	Integer nombreArticleColis;
	
	String codeBarre;
	
	@Column(nullable = true)
	String photo;
	
	@NotNull
	@ManyToOne
	Boutique boutique;
	
	String dispoWeb;
	
	String ruptureDeStock;
	
	Double prix2;

	Double prix1;
	
	Double prix3;
	
	Integer minCommande;
	
	String type;
	
	String etat;
	
	String famille;
	
	String familleCode;
	
	String description;
	
	Integer quantitePartiel;
	
	Integer quantiteComplet;
	
	Boolean nouveaute;
	
	Boolean promotion;
	
	Boolean retourEnStock;
	
	Double prixUnitaireSousColis;
	
	Double prixUnitaireColis;
	
	public Double getPrixUnitaireSousColis() {
		return prixUnitaireSousColis;
	}

	public void setPrixUnitaireSousColis(Double prixUnitaireSousColis) {
		this.prixUnitaireSousColis = prixUnitaireSousColis;
	}

	public Double getPrixUnitaireColis() {
		return prixUnitaireColis;
	}

	public void setPrixUnitaireColis(Double prixUnitaireColis) {
		this.prixUnitaireColis = prixUnitaireColis;
	}

	public Boolean getRetourEnStock() {
		return retourEnStock;
	}

	public void setRetourEnStock(Boolean retourEnStock) {
		this.retourEnStock = retourEnStock;
	}

	public Boolean getNouveaute() {
		return nouveaute;
	}

	public void setNouveaute(Boolean nouveaute) {
		this.nouveaute = nouveaute;
	}

	public Boolean getPromotion() {
		return promotion;
	}

	public void setPromotion(Boolean promotion) {
		this.promotion = promotion;
	}

	public Integer getQuantitePartiel() {
		return quantitePartiel;
	}

	public void setQuantitePartiel(Integer quantitePartiel) {
		this.quantitePartiel = quantitePartiel;
	}

	public Integer getQuantiteComplet() {
		return quantiteComplet;
	}

	public void setQuantiteComplet(Integer quantiteComplet) {
		this.quantiteComplet = quantiteComplet;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrix3() {
		return prix3;
	}

	public void setPrix3(Double prix3) {
		this.prix3 = prix3;
	}

	public String getFamille() {
		return famille;
	}

	public void setFamille(String famille) {
		this.famille = famille;
	}

	public String getFamilleCode() {
		return familleCode;
	}

	public void setFamilleCode(String familleCode) {
		this.familleCode = familleCode;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public String getCodeArticle() {
		return codeArticle;
	}

	public void setCodeArticle(String codeArticle) {
		this.codeArticle = codeArticle;
	}

	public Boutique getBoutique() {
		return boutique;
	}

	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}

	public String getDispoWeb() {
		return dispoWeb;
	}

	public void setDispoWeb(String dispoWeb) {
		this.dispoWeb = dispoWeb;
	}

	public String getRuptureDeStock() {
		return ruptureDeStock;
	}

	public void setRuptureDeStock(String ruptureDeStock) {
		this.ruptureDeStock = ruptureDeStock;
	}

	public Double getPrix2() {
		return prix2;
	}

	public void setPrix2(Double prix2) {
		this.prix2 = prix2;
	}

	public Double getPrix1() {
		return prix1;
	}

	public void setPrix1(Double prix1) {
		this.prix1 = prix1;
	}


	public Integer getMinCommande() {
		return minCommande;
	}

	public void setMinCommande(Integer minCommande) {
		this.minCommande = minCommande;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}


	public Integer getNombreArticleColis() {
		return nombreArticleColis;
	}

	public String getCodeBarre() {
		return codeBarre;
	}

	public void setCodeBarre(String codeBarre) {
		this.codeBarre = codeBarre;
	}

	public void setNombreArticleColis(Integer nombreArticleColis) {
		this.nombreArticleColis = nombreArticleColis;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	
}
