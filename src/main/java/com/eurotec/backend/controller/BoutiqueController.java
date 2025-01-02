package com.eurotec.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eurotec.backend.repository.BoutiqueRepository;
import com.eurotec.backend.repository.CommandeRepository;

@Controller
@RequestMapping(path = { "/boutique"  })
public class BoutiqueController {
	
	@Autowired
	BoutiqueRepository boutiqueRepository;
	
	@Autowired
	CommandeRepository commandeRepository;
	
	@GetMapping(path = { "/liste" } )
	public String index(Model model) 
	{		
		model.addAttribute("boutiques", boutiqueRepository.findAll(Sort.by("id").descending() ));
		return "boutique/liste";
	}
	

}
