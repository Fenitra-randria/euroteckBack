package com.eurotec.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.eurotec.backend.constante.CstRole;
import com.eurotec.backend.entity.Client;
import com.eurotec.backend.entity.Boutique;
import com.eurotec.backend.entity.Utilisateur;
import com.eurotec.backend.repository.ClientRepository;

@Service
public class UserService {

    @Autowired
    ClientRepository clientRepository;

    public Boolean IsAdmin(Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(CstRole.ADMIN));
        return isAdmin;
    }

    public Client insertClientIfNull(Utilisateur u, Boutique b) {
        if (!u.getRoles().equals(CstRole.CLIENT))
            return null;

        Client c = null;
        if (b == null) {
            c = clientRepository.findByEmailOrUtilisateurId(u.getEmail(), u.getId()).orElse(null);
        } else {
            c = clientRepository.findByEmailOrUtilisateurIdAndBoutiqueId(u.getEmail(), u.getId(), b.getId())
                    .orElse(null);
        }

        if (c != null) {
            c.setUtilisateur(u);
            clientRepository.save(c);
            return c;
        }

        c = new Client();
        c.setUtilisateur(u);
        c.setNom(u.getNom());
        c.setPrenom(u.getPrenom());
        c.setEmail(u.getEmail());
        c.setNumero(u.getNumero());
        c.setSiret(u.getSiret());
        c.setTva(0.2);
        c.setType("standard");
        if (b != null) {
            c.setBoutique(b);
        }
        clientRepository.save(c);

        return c;
    }
}
