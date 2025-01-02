package com.eurotec.backend.dto;

import com.eurotec.backend.entity.Produit;
import com.eurotec.backend.entity.Utilisateur;

public class PanierDtoListe {
	
	Long id;
	Utilisateur client;
	Produit produit;
	Integer quantite;
	String choix;
	
}
