package com.eurotec.backend.dto;

import java.time.LocalDate;
import java.util.List;

public record CommandeInsertDto(LocalDate dateEcheance,String methodePaiement,String pointLivraison,Long client,Long boutique,Double tva) 
{
	

}
