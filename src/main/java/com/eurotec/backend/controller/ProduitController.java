package com.eurotec.backend.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.eurotec.backend.entity.Boutique;
import com.eurotec.backend.entity.Produit;
import com.eurotec.backend.entity.Utilisateur;
import com.eurotec.backend.repository.BoutiqueRepository;
import com.eurotec.backend.repository.ProduitRepository;
import com.eurotec.backend.repository.UtilisateurRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = { "/" , "/produit"  })
public class ProduitController 
{

	@Autowired
	ProduitRepository produitRepository;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UtilisateurRepository utilisateurRepository;
	
	@Autowired
	BoutiqueRepository boutiqueRepository;
	
	
	@GetMapping(path = { "/" , "/produit/liste-produit" } )
	public String index(Model model , Authentication auth) 
	{	
		Utilisateur u = utilisateurRepository.findByEmail(auth.getName()).get();
		Boutique b  = boutiqueRepository.findByUtilisateur(u).get(0); 
		model.addAttribute("produits", produitRepository.findByBoutiqueId(b.getId()) );
		return "produit/liste";
	}
	
	
	@GetMapping( "/upload-excel")
	public String upload()
	{
		return "produit/upload-excel";
	}
	
	@Transactional
	@PostMapping("/upload-excel" )
	public String insertProduit( Authentication auth , Model model,  @RequestParam("fichier") MultipartFile file) 
	{
		Utilisateur u = utilisateurRepository.findByEmail(auth.getName()).get();
		Boutique b  = boutiqueRepository.findByUtilisateur(u).get(0); 
		
					try {
								
							HSSFWorkbook workbook = new HSSFWorkbook( file.getInputStream() );
							HSSFSheet  sheet = workbook.getSheetAt(0);
							int i = 0 ;
							for(Row row : sheet) 
							{
								String code;
								String nom;
								Double prix;
								Integer nombre_article_colis;
								String code_barre;
								String famille;
								String code_famille;
								String etat ;
								Integer stock_reel;
								Integer stock_virtuel;
								String dispo;
								String rupture;
								
								Produit p;
								
								if( i != 0)
								{
									code = row.getCell(0).getStringCellValue(); 
									nom = row.getCell(1).getStringCellValue(); 
									prix = Double.valueOf( row.getCell(2).getNumericCellValue() );
									nombre_article_colis = Integer.valueOf(  Double.valueOf( row.getCell(3).getNumericCellValue() ).intValue() ) ;
									code_barre = row.getCell(4).getStringCellValue();
									famille = row.getCell(5).getStringCellValue();
									code_famille = row.getCell(6).getStringCellValue();
									
									etat = row.getCell(8).getStringCellValue();
									stock_virtuel = Integer.valueOf(  Double.valueOf( row.getCell(9).getNumericCellValue() ).intValue() ) ;
									stock_reel = Integer.valueOf(  Double.valueOf( row.getCell(10).getNumericCellValue() ).intValue() ) ;
									dispo = row.getCell(11).getStringCellValue();
									rupture = row.getCell(12).getStringCellValue();
									
									p = new Produit();
									p.setCodeArticle(code);
									p.setNom(nom);
									p.setPrix3(prix);
									p.setNombreArticleColis(nombre_article_colis);
									p.setCodeBarre(code_barre);
									p.setFamille(famille);
									p.setFamilleCode(code_famille);
									p.setEtat(etat);
									p.setDispoWeb(dispo);
									p.setRuptureDeStock(rupture);
									p.setBoutique(b);
									
									produitRepository.save(p);
										
								}
								i++;
							}
							
						return "redirect:/produit/liste-produit";	
						
					} catch (Exception e) {
						e.printStackTrace();
						model.addAttribute("erreur", e.getMessage() );
						return "produit/upload-excel";	
					}
				
	}
	
	
}
