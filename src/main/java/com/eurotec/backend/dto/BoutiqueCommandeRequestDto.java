package com.eurotec.backend.dto;

import java.time.LocalDate;
import java.util.List;

// DTO pour la requête de commande client par la boutique
public record BoutiqueCommandeRequestDto(
    Long clientId,
    List<LigneInsertDto> lignes,
    String methodePaiement,
    String pointLivraison,
    LocalDate dateEcheance
) {}