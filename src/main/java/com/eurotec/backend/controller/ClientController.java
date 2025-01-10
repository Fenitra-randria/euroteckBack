package com.eurotec.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eurotec.backend.entity.Boutique;
import java.util.List;
import com.eurotec.backend.entity.Utilisateur;
import com.eurotec.backend.entity.Client;
import com.eurotec.backend.repository.UtilisateurRepository;
import com.eurotec.backend.repository.ClientRepository;

@Controller
@RequestMapping(path = { "/client"  })
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @GetMapping(path = { "/liste" } )
	public String index(Model model,@RequestParam(name = "boutique", required = true) Long boutique) 
	{	
        model.addAttribute("clients", clientRepository.findByBoutiqueIdWithUtilisateur(boutique));
		return "client/liste";
	}
}
