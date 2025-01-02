package com.eurotec.backend.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eurotec.backend.entity.Utilisateur;
import com.eurotec.backend.repository.UtilisateurRepository;


@Service
public class UtilisateurDetailService implements UserDetailsService{

	@Autowired
	UtilisateurRepository utilisateurRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Utilisateur u = utilisateurRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("not found"));
		String[] roles = u.getRoles().split(",");
		List<SimpleGrantedAuthority> authorities = List.of(roles)
															.stream()
															.map( role -> new SimpleGrantedAuthority(role) )
															.toList();		
		return new User(u.getEmail(), u.getPassword(), authorities );
	}
	
}
