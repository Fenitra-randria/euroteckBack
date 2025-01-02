package com.eurotec.backend.dto;

import com.eurotec.backend.entity.MesBoutiques;

public class MesBoutiquesDto {
	
	Long id;
	Long idBoutique;
	Long numero;
	String nom;
	String telFixe;
	String photo;
	
	
	public MesBoutiquesDto(MesBoutiques mb)
	{
		this.id = mb.getId();
		this.idBoutique = mb.getBoutique().getId();
		this.numero = mb.getBoutique().getNumero();
		this.nom = mb.getBoutique().getNom();
		this.telFixe = mb.getBoutique().getTelFixe();
		this.photo = mb.getBoutique().getPhoto();
	}
	
	
	public Long getIdBoutique() {
		return idBoutique;
	}



	public void setIdBoutique(Long idBoutique) {
		this.idBoutique = idBoutique;
	}



	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getTelFixe() {
		return telFixe;
	}
	public void setTelFixe(String telFixe) {
		this.telFixe = telFixe;
	}
	
	
	

}
