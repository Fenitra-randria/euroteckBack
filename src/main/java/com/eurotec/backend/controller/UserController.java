package com.eurotec.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.eurotec.backend.service.UserService;
import com.eurotec.backend.repository.ClientRepository;

@Controller
@RequestMapping(path = { "/user"  })
public class UserController {

    @Autowired
	UtilisateurRepository utilisateurRepository;

	@Autowired
	ClientRepository clientRepository;
    
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserService userService;

    @GetMapping(path = { "/liste" } )
	public String index(Model model) 
	{	
        model.addAttribute("users", utilisateurRepository.findAllAndOrderByNomWhereRolesIsNotAdmin());
		return "user/liste";
	}

	@GetMapping(path = { "/formulaire" } )
	public String formulaire(
		@RequestParam(name = "userId", required = false) Long userId,
		Model model) 
	{		
		Utilisateur user = new Utilisateur();
		if(userId!=null) {
			user = utilisateurRepository.findById(userId).orElse(new Utilisateur());
		}
		model.addAttribute("user", user);
		return "user/formulaire";
	}

	@PostMapping(path = { "/save" } )
	public String save(Utilisateur user, Model model) 
	{		
		if(user.getId()!=null && user.getId()>0 && user.getPassword().isEmpty()) 
		{
			Utilisateur oldUser = utilisateurRepository.findById(user.getId()).orElse(new Utilisateur());
			user.setPassword(oldUser.getPassword());
		} else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		
		utilisateurRepository.save(user);

		// userService.insertClientIfNull(user,null);

		return "redirect:/user/liste";
	}
}
