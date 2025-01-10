package com.eurotec.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eurotec.backend.entity.Boutique;
import com.eurotec.backend.entity.Utilisateur;
import com.eurotec.backend.repository.BoutiqueRepository;
import com.eurotec.backend.repository.CommandeRepository;
import com.eurotec.backend.repository.UtilisateurRepository;

@Controller
@RequestMapping(path = { "/boutique"  })
public class BoutiqueController {
	
	@Autowired
	BoutiqueRepository boutiqueRepository;
	
	@Autowired
	CommandeRepository commandeRepository;

	@Autowired
	UtilisateurRepository utilisateurRepository;
	
	@GetMapping(path = { "/liste" } )
	public String index(Model model,@RequestParam(name = "user", required = true) Long user) 
	{		
		model.addAttribute("boutiques", boutiqueRepository.findByUtilisateurIdOrderByIdDesc(user));
		model.addAttribute("user", user);
		return "boutique/liste";
	}

	@GetMapping(path = { "/formulaire" } )
	public String formulaire(
		@RequestParam(name = "boutique", required = false) Long boutique,
		@RequestParam(name = "user", required = true) Long user,
		Model model) 
	{		
		Boutique btq = new Boutique();
		if(boutique!=null && boutique>0) {
			btq = boutiqueRepository.findById(boutique).orElse(new Boutique());
		} else {
			Utilisateur userData = utilisateurRepository.findById(user).orElse(new Utilisateur());
			btq.setUtilisateur(userData);
		}

		System.out.println("boutique : "+btq.getUtilisateur().getEmail());
		model.addAttribute("boutique", btq);
		return "boutique/formulaire";
	}

	@PostMapping(path = { "/save" } )
	public String save(Boutique boutique, Model model) 
	{		
		try {
			if(boutique.getId()!=null && boutique.getId()>0)
			{
				Boutique oldBoutique = boutiqueRepository.findById(boutique.getId()).orElse(new Boutique());
				Utilisateur oldUser = utilisateurRepository.findById(oldBoutique.getUtilisateur().getId()).orElse(new Utilisateur());
				boutique.setUtilisateur(oldUser);
			}
            boutiqueRepository.save(boutique);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Une erreur est survenue lors de l'enregistrement de la boutique");
            return "boutique/formulaire";
        }
        return "redirect:/boutique/liste?user=" + boutique.getUtilisateur().getId();
	}
	

}
