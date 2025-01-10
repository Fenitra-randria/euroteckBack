package com.eurotec.backend.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.eurotec.backend.entity.Commande;
import com.eurotec.backend.entity.LigneCommande;

public class CommandeListeDto
{
	Long id;
	LocalDate dateSaisie;
	String nom;
	String prenom;
	String email;
	String numero;
	String methodePaiement;
	LocalDate dateEcheance;
	String livraison;
	List<LigneCommandeListeDto> produits;
	Double total;
	String statut;
	Double tva;
	
	public CommandeListeDto(Commande c , List<LigneCommande> lignes) 
	{
		this.setId(c.getId());
		this.setDateSaisie(c.getDateSaisie());
		this.setNom(c.getClient().getNom());
		this.setPrenom(c.getClient().getPrenom());
		this.setEmail(c.getClient().getEmail());
		this.setNumero(c.getClient().getNumero());
		this.setMethodePaiement(c.getMethodePaiement());
		this.setDateEcheance(c.getDateEcheance());
		this.setLivraison(c.getPointLivraison());
		this.setStatut(c.getStatut());
		this.setTva(c.getTva());
		
		produits = new ArrayList<LigneCommandeListeDto>();
		for(LigneCommande l : lignes )
		{
			LigneCommandeListeDto p = new LigneCommandeListeDto();
			
			p.setPhoto( l.getProduit().getPhoto() );
			
			p.setNom( l.getProduit().getNom() );
			p.setCategorie( l.getProduit().getFamille() );
			p.setReference( l.getProduit().getCodeArticle() );
			
			p.setQuantite( l.getQuantite() );
			p.setChoix( l.getChoix() );
			
			p.setPrix( l.getPrix() );
			p.setCodeBarre( l.getProduit().getCodeBarre() );
			p.setTotal( l.getPrix() * l.getQuantite()    );
			produits.add(p);
		}
		Double total =	produits.stream()
							   .map( p -> { return p.getPrix() * p.getQuantite() ; } )
							   .reduce( Double.valueOf(0) , (a,b) -> { return a + b ; } );
		this.setTotal( Double.valueOf(total) + ( Double.valueOf(total)*c.getTva() ) );
	}
	
	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public List<LigneCommandeListeDto> getProduits() {
		return produits;
	}

	public void setProduits(List<LigneCommandeListeDto> produits) {
		this.produits = produits;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDateSaisie() {
		return dateSaisie;
	}

	public void setDateSaisie(LocalDate dateSaisie) {
		this.dateSaisie = dateSaisie;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getMethodePaiement() {
		return methodePaiement;
	}

	public void setMethodePaiement(String methodePaiement) {
		this.methodePaiement = methodePaiement;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public LocalDate getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(LocalDate dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	public String getLivraison() {
		return livraison;
	}

	public void setLivraison(String livraison) {
		this.livraison = livraison;
	}

	public Double getTva() {
		return tva;
	}

	public void setTva(Double tva) {
		this.tva = tva;
	}
	
	
	
}
