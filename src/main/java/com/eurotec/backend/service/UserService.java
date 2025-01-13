package com.eurotec.backend.service;

import java.util.List;
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

        List<Client> clients;
        if (b == null) {
            clients = clientRepository.findByEmailOrUtilisateurId(u.getEmail(), u.getId());
        } else {
            clients = clientRepository.findByEmailOrUtilisateurIdAndBoutiqueId(u.getEmail(), u.getId(), b.getId());
        }

        Client c = clients.stream().findFirst().orElse(null);
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
