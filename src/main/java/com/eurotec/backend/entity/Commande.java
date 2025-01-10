package com.eurotec.backend.entity;

import java.time.LocalDate;
import java.util.List;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Commande 
{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;

	@NotNull
	@ManyToOne
	Boutique boutique;

	@NotNull
	@ManyToOne
	Client client;

	String numero;
	
	LocalDate dateSaisie;
	
	String methodePaiement;
	
	LocalDate dateEcheance;	
	
	String pointLivraison;
	
	String statut;
	
	Double tva;
	
	public Double getTva() {
		return tva;
	}

	public void setTva(Double tva) {
		this.tva = tva;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public String getPointLivraison() {
		return pointLivraison;
	}

	public void setPointLivraison(String pointLivraison) {
		this.pointLivraison = pointLivraison;
	}

	public String getMethodePaiement() {
		return methodePaiement;
	}

	public void setMethodePaiement(String methodePaiement) {
		this.methodePaiement = methodePaiement;
	}

	public LocalDate getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(LocalDate dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boutique getBoutique() {
		return boutique;
	}


	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public LocalDate getDateSaisie() {
		return dateSaisie;
	}

	public void setDateSaisie(LocalDate dateSaisie) {
		this.dateSaisie = dateSaisie;
	}

}
