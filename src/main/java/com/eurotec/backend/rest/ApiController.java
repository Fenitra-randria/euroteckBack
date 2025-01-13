package com.eurotec.backend.rest;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mapping.MappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.eurotec.backend.constante.CstRole;
import com.eurotec.backend.dto.BoutiqueCommandeRequestDto;
import com.eurotec.backend.dto.CommandeInsertDto;
import com.eurotec.backend.dto.CommandeListeDto;
import com.eurotec.backend.dto.FavoriDtoInsert;
import com.eurotec.backend.dto.LigneCommandeListeDto;
import com.eurotec.backend.dto.LigneInsertDto;
import com.eurotec.backend.dto.Login;
import com.eurotec.backend.dto.MesBoutiquesDto;
import com.eurotec.backend.dto.PanierDto;
import com.eurotec.backend.entity.Boutique;
import com.eurotec.backend.entity.Client;
import com.eurotec.backend.entity.Commande;
import com.eurotec.backend.entity.Favori;
import com.eurotec.backend.entity.LigneCommande;
import com.eurotec.backend.entity.MesBoutiques;
import com.eurotec.backend.entity.Panier;
import com.eurotec.backend.entity.Produit;
import com.eurotec.backend.entity.Utilisateur;
import com.eurotec.backend.repository.BoutiqueRepository;
import com.eurotec.backend.repository.ClientRepository;
import com.eurotec.backend.repository.CommandeRepository;
import com.eurotec.backend.repository.FavoriRepository;
import com.eurotec.backend.repository.LigneCommandeRepository;
import com.eurotec.backend.repository.MesBoutiquesRepository;
import com.eurotec.backend.repository.PanierRepository;
import com.eurotec.backend.repository.ProduitRepository;
import com.eurotec.backend.repository.UtilisateurRepository;
import com.eurotec.backend.service.NotificationService;
import com.eurotec.backend.service.TokenService;
import com.eurotec.backend.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.ConstraintViolationException;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = "/api")
@SecurityRequirement(name = "bearerAuth")
public class ApiController {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	NotificationService notificationService;

	@Autowired
	TokenService tokenService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UtilisateurRepository utilisateurRepository;

	@Autowired
	BoutiqueRepository boutiqueRepository;

	@Autowired
	ProduitRepository produitRepository;

	@Autowired
	CommandeRepository commandeRepository;

	@Autowired
	LigneCommandeRepository ligneCommandeRepository;

	@Autowired
	MesBoutiquesRepository mesBoutiquesRepository;

	@Autowired
	PanierRepository panierRepository;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	FavoriRepository favoriRepository;

	@Autowired
	UserService userService;

	@Value("${images.path}")
	String PATH_IMAGE;

	@Value("${pdf.path}")
	String PATH_PDF;

	@Value("${jasper.path}")
	String PATH_JASPER;

	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

	@PostMapping(path = "/login")
	@Operation(summary = "connexion", responses = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "401", description = "Non authorisé")
	})

	public ResponseEntity<Map<String, Object>> login(@RequestBody Login jsonLogin) {
		Map<String, Object> result = new HashMap<String, Object>();
		Authentication authentication;

		UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(jsonLogin.getLogin(),
				jsonLogin.getPassword());
		try {
			authentication = authenticationManager.authenticate(authReq);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatusCode.valueOf(401));
		}

		Utilisateur u = utilisateurRepository.findByEmail(authentication.getName()).get();
		if (!u.getRoles().equals(jsonLogin.getRole())) {
			throw new ResponseStatusException(HttpStatusCode.valueOf(401));
		}

		String token = tokenService.genererToken(authentication);
		result.put("token", token);

		// boutique nombre
		String nombre = Integer.valueOf(boutiqueRepository.countByUtilisateur(u)).toString();
		result.put("nombre", nombre);

		// iduser
		result.put("iduser", u.getId().toString());
		result.put("nomUser", u.getNom() + " " + u.getPrenom());
		result.put("emailUser", u.getEmail());
		result.put("numeroUser", u.getNumero());

		// boutiques
		List<Boutique> boutiques = boutiqueRepository.findByUtilisateur(u);
		result.put("boutiques", boutiques);

		// userService.insertClientIfNull(u,null);

		return new ResponseEntity(result, HttpStatusCode.valueOf(200));
	}

	@GetMapping(path = "/user-actif")
	public ResponseEntity<List<Utilisateur>> userActif() {
		List<Utilisateur> u = utilisateurRepository.findAllByRolesIsClientAndActifIsTrue();
		return new ResponseEntity<>(u, HttpStatusCode.valueOf(200));
	}

	// #region ADMIN VALUE
	// @GetMapping("/admin-insert")
	// public ResponseEntity AjoutAdmin() {
	// Utilisateur user = new Utilisateur();
	// user.setEmail("admin@eurotec.com");
	// user.setNom("ADMIN");
	// user.setPrenom("EUROTEC ADMIN");
	// user.setNumero("ADMIN");
	// user.setSiret("ADMIN");
	// user.setPassword(passwordEncoder.encode("ADMIN_EUROTEC"));
	// user.setRoles(CstRole.ADMIN);

	// utilisateurRepository.save(user);

	// return new ResponseEntity<>(HttpStatusCode.valueOf(200));
	// }
	// #endregion

	@PostMapping(path = "/image/{idBoutique}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> insertImage(@PathVariable Long idBoutique, @RequestParam MultipartFile photo) {
		String nomUniqueUUID = UUID.randomUUID().toString();
		String[] temp = photo.getOriginalFilename().split("\\.");
		String newName = nomUniqueUUID + ".png";

		try {

			Files.createDirectories(Paths.get(PATH_IMAGE));

			BufferedImage srcImage = ImageIO.read(photo.getInputStream()); // Load image
			BufferedImage scaledImage = Scalr.resize(srcImage, 500); // Scale image

			File outputfile = new File(Paths.get(PATH_IMAGE + "/" + newName).toString());
			ImageIO.write(scaledImage, "png", outputfile);

			Boutique b = boutiqueRepository.findById(idBoutique).get();
			b.setPhoto(newName);
			boutiqueRepository.saveAndFlush(b);

			return new ResponseEntity("{\"result\":\"ok\"}", HttpStatusCode.valueOf(201));

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("{\"result\":\"erreur serveur\"}", HttpStatusCode.valueOf(500));
		}
	}

	@PostMapping(path = "/image/produit/{idProduit}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> insertImageProduit(@PathVariable Long idProduit, @RequestParam MultipartFile photo) {
		String nomUniqueUUID = UUID.randomUUID().toString();
		String[] temp = photo.getOriginalFilename().split("\\.");
		String newName = nomUniqueUUID + ".png";

		try {

			Files.createDirectories(Paths.get(PATH_IMAGE));

			BufferedImage srcImage = ImageIO.read(photo.getInputStream()); // Load image
			BufferedImage scaledImage = Scalr.resize(srcImage, 500); // Scale image

			File outputfile = new File(Paths.get(PATH_IMAGE + "/" + newName).toString());
			ImageIO.write(scaledImage, "png", outputfile);

			Produit p = produitRepository.findById(idProduit).get();
			p.setPhoto(newName);
			produitRepository.saveAndFlush(p);
			return new ResponseEntity("{\"result\":\"ok\"}", HttpStatusCode.valueOf(201));

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity("{\"result\":\"erreur serveur\"}", HttpStatusCode.valueOf(500));
		}

	}

	@GetMapping(path = "/liste-commandes/{idBoutique}")
	public ResponseEntity<List<CommandeListeDto>> prendreCommande(
			@RequestParam(name = "search") Optional<String> search,
			@RequestParam(name = "statut") Optional<String> statut,
			@PathVariable(name = "idBoutique") Long idBoutique) {
		List<Commande> liste = commandeRepository.rechercher(search.orElse(""), statut.orElse(""), idBoutique);
		List<CommandeListeDto> result = new ArrayList<CommandeListeDto>();
		for (Commande c : liste) {
			CommandeListeDto dto = new CommandeListeDto(c, ligneCommandeRepository.findByCommande(c));
			result.add(dto);
		}
		return new ResponseEntity(result, HttpStatusCode.valueOf(200));
	}

	@GetMapping(path = "/liste-commandes-client/{idClient}")
	public ResponseEntity<List<CommandeListeDto>> prendreCommandeClient(
			@RequestParam(name = "statut") Optional<String> statut, @PathVariable(name = "idClient") Long idClient) {

		Utilisateur clientUtilisateur = utilisateurRepository.findById(idClient)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client non trouvé"));
		if (!clientUtilisateur.getActif()) {
			return new ResponseEntity("{\"result\":\"error\"}", HttpStatusCode.valueOf(401));
		}
		List<Commande> liste = commandeRepository.rechercherParclient(statut.orElse(""), idClient);
		List<CommandeListeDto> result = new ArrayList<CommandeListeDto>();
		for (Commande c : liste) {
			CommandeListeDto dto = new CommandeListeDto(c, ligneCommandeRepository.findByCommande(c));
			result.add(dto);
		}
		return new ResponseEntity(result, HttpStatusCode.valueOf(200));
	}

	@Transactional
	@PostMapping(path = "/upload-excel-rest/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> insertProduit(@PathVariable Long id, @RequestParam MultipartFile fichier) {
		Boutique b = boutiqueRepository.findById(id).get();

		try {

			HSSFWorkbook workbook = new HSSFWorkbook(fichier.getInputStream());
			HSSFSheet sheet = workbook.getSheetAt(0);
			int i = 0;
			for (Row row : sheet) {
				String code;
				String nom;
				Double prix;
				Integer nombre_article_colis;
				String code_barre;
				String famille;
				String code_famille;
				String etat;
				Integer stock_reel;
				Integer stock_virtuel;
				String dispo;
				String rupture;

				Produit p;

				if (i != 0) {
					code = row.getCell(0).getStringCellValue();
					nom = row.getCell(1).getStringCellValue();
					prix = Double.valueOf(row.getCell(2).getNumericCellValue());
					nombre_article_colis = Integer
							.valueOf(Double.valueOf(row.getCell(3).getNumericCellValue()).intValue());
					code_barre = row.getCell(4).getStringCellValue();
					famille = row.getCell(5).getStringCellValue();
					code_famille = row.getCell(6).getStringCellValue();

					etat = row.getCell(8).getStringCellValue();
					stock_virtuel = Integer.valueOf(Double.valueOf(row.getCell(9).getNumericCellValue()).intValue());
					stock_reel = Integer.valueOf(Double.valueOf(row.getCell(10).getNumericCellValue()).intValue());
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
			return new ResponseEntity("{\"result\":\"ok\"}", HttpStatusCode.valueOf(201));

		} catch (Exception e) {
			return new ResponseEntity("{\"error\":\"erreur\"}", HttpStatusCode.valueOf(500));
		}

	}

	@PostMapping(path = "/insert-panier")
	public ResponseEntity<String> insertCommande(@RequestBody PanierDto p) {
		// test si deja insere
		Utilisateur client = utilisateurRepository.getReferenceById(p.getClient());

		if (client.getActif() == false) {
			return new ResponseEntity("{\"result\":\"error\"}", HttpStatusCode.valueOf(401));
		}

		Produit produit = produitRepository.getReferenceById(p.getProduit());
		int nb = panierRepository.countByProduitAndClient(produit, client);

		if (nb == 0) {
			Panier panier = new Panier();
			panier.setChoix(p.getChoix());
			panier.setClient(client);
			panier.setProduit(produit);
			panier.setQuantite(p.getQuantite());
			panierRepository.saveAndFlush(panier);
		}

		return new ResponseEntity("{\"result\":\"ok\"}", HttpStatusCode.valueOf(201));
	}

	@DeleteMapping(path = "/vider-panier/{idClient}")
	@Transactional
	public ResponseEntity<String> viderPanier(@PathVariable(name = "idClient") Long idClient) {
		panierRepository.deleteAll(panierRepository.findByClientId(idClient));
		return new ResponseEntity<>("{\"result\":\"ok\"}", HttpStatusCode.valueOf(200));
	}

	@PostMapping(path = "/insert-favori")
	public ResponseEntity<String> insertCommande(@RequestBody FavoriDtoInsert p) {

		// test si deja insere
		Utilisateur client = utilisateurRepository.getReferenceById(p.client());

		if (client.getActif() == false) {
			return new ResponseEntity("{\"result\":\"error\"}", HttpStatusCode.valueOf(401));
		}

		Produit produit = produitRepository.getReferenceById(p.produit());

		int nb = favoriRepository.countByProduitAndClient(produit, client);

		if (nb == 0) {
			Favori f = new Favori();
			f.setClient(client);
			f.setProduit(produit);
			favoriRepository.saveAndFlush(f);
		}

		return new ResponseEntity("{\"result\":\"ok\"}", HttpStatusCode.valueOf(201));
	}

	@GetMapping(path = "/panier")
	public ResponseEntity<List<Panier>> panier(Long idclient) {
		List<Panier> liste = panierRepository.findByClientId(idclient);
		return new ResponseEntity(liste, HttpStatusCode.valueOf(200));
	}

	@GetMapping(path = "/favori")
	public ResponseEntity<List<Favori>> favori(Long idclient) {
		List<Favori> liste = favoriRepository.findByClientId(idclient);

		return new ResponseEntity(liste, HttpStatusCode.valueOf(200));
	}

	@GetMapping(path = "/mesboutiques/{idUtilisateur}")
	public ResponseEntity<List<MesBoutiquesDto>> mesboutiques(
			@PathVariable(name = "idUtilisateur") Long idUtilisateur) {
		List<MesBoutiques> liste = mesBoutiquesRepository.mesboutiques(idUtilisateur);

		List<MesBoutiquesDto> result = new ArrayList<MesBoutiquesDto>();
		for (MesBoutiques c : liste) {
			MesBoutiquesDto dto = new MesBoutiquesDto(c);
			result.add(dto);
		}
		return new ResponseEntity(result, HttpStatusCode.valueOf(200));
	}

	@GetMapping(path = "/categories")
	public ResponseEntity<List<String>> rechercherCategorie() {
		List<String> liste = produitRepository.rechercherCategorie();

		return new ResponseEntity(liste, HttpStatusCode.valueOf(200));
	}

	@PostMapping(path = "/insert-commande")
	public ResponseEntity<String> inserCommande(@RequestBody CommandeInsertDto commande) {
		try {

			Utilisateur u = utilisateurRepository.getReferenceById(commande.client());
			logger.info("Fetching a with ID: " + u);
			Boutique b = boutiqueRepository.findById(commande.boutique())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Boutique non trouvée"));
			logger.info("Fetching b with ID: " + b);

			Client c = userService.insertClientIfNull(u, b);
			logger.info("Fetching c with ID: " + c);

			if (!c.getUtilisateur().getActif()) {
				return new ResponseEntity("{\"result\":\"error activation compte\"}",
						HttpStatusCode.valueOf(401));
			}

			if (c.getBoutique() == null) {
				c.setBoutique(b);
				clientRepository.saveAndFlush(c);
			}

			Commande cmd = new Commande();
			cmd.setBoutique(b);
			cmd.setClient(c);
			cmd.setDateEcheance(commande.dateEcheance());
			cmd.setDateSaisie(LocalDate.now());
			cmd.setMethodePaiement(commande.methodePaiement());
			cmd.setPointLivraison(commande.pointLivraison());
			cmd.setStatut("en_attente");
			cmd.setTva(c.getTva());

			commandeRepository.saveAndFlush(cmd);

			// get all ligne commande insert all then delete
			List<Panier> liste = panierRepository.findByClientId(commande.client());
			Produit produitTEmp;
			for (Panier panier : liste) {
				produitTEmp = panier.getProduit();
				LigneCommande ligne = new LigneCommande();
				ligne.setCommande(cmd);
				ligne.setProduit(produitTEmp);
				ligne.setQuantite(panier.getQuantite());

				// prix
				if (panier.getQuantite() < produitTEmp.getQuantiteComplet()) {
					ligne.setPrix(produitTEmp.getPrixUnitaireSousColis());
				} else {
					ligne.setPrix(produitTEmp.getPrixUnitaireColis());
				}

				// nombre colis
				int nombreColis = 1;
				nombreColis = panier.getQuantite() / produitTEmp.getQuantiteComplet();
				if ((panier.getQuantite() % produitTEmp.getQuantiteComplet()) != 0)
					nombreColis = nombreColis + 1;

				/*
				 * if( panier.getQuantite() < produitTEmp.getQuantiteComplet() )
				 * {
				 * nombreColis = panier.getQuantite() / produitTEmp.getQuantitePartiel();
				 * }
				 * else
				 * {
				 * nombreColis = panier.getQuantite() / produitTEmp.getQuantiteComplet();
				 * int reste = ( panier.getQuantite() -
				 * nombreColis*produitTEmp.getQuantiteComplet() ) /
				 * produitTEmp.getQuantitePartiel() ;
				 * nombreColis = nombreColis + reste ;
				 * }
				 */

				String choix = nombreColis + " colis ";
				ligne.setChoix(choix);

				ligneCommandeRepository.saveAndFlush(ligne);
				panierRepository.delete(panier);
			}

			// creation PDF facture
			CommandeListeDto commandeDTO = new CommandeListeDto(cmd, ligneCommandeRepository.findByCommande(cmd));
			List<LigneCommandeListeDto> ligneCommandeDTO = commandeDTO.getProduits();
			Double tvaTemp = cmd.getClient().getTva() * 100;
			String tva = tvaTemp.intValue() + "%";
			Double total = commandeDTO.getTotal();

			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ligneCommandeDTO);

			InputStream in = new ClassPathResource(PATH_JASPER + "/report.jrxml").getInputStream();
			if (!Files.exists(Paths.get("report.jrxml"))) {
				File targetFile = new File("report.jrxml");
				Files.copy(in, Paths.get("report.jrxml"));
			}

			JasperReport jasperReport = JasperCompileManager.compileReport(Paths.get("report.jrxml").toString());

			Map<String, Object> parameters = new HashMap<>();

			parameters.put("adresseLivraison", commandeDTO.getLivraison());
			parameters.put("nomClient", commandeDTO.getNom().toUpperCase());
			parameters.put("prenomClient", commandeDTO.getPrenom().toUpperCase());
			parameters.put("emailClient", commandeDTO.getEmail());
			parameters.put("numeroClient", commandeDTO.getNumero());
			parameters.put("numeroCommande", commandeDTO.getId().toString());

			String jour = (commandeDTO.getDateSaisie().getDayOfMonth() < 10)
					? "0" + commandeDTO.getDateSaisie().getDayOfMonth()
					: "" + commandeDTO.getDateSaisie().getDayOfMonth();
			String mois = (commandeDTO.getDateSaisie().getMonthValue() < 10)
					? "0" + commandeDTO.getDateSaisie().getMonthValue()
					: "" + commandeDTO.getDateSaisie().getMonthValue();
			parameters.put("dateCommande", jour + "/" + mois + "/" + commandeDTO.getDateSaisie().getYear());

			parameters.put("tva", tva);
			parameters.put("total", total);

			parameters.put("listeProduit", dataSource);

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

			Files.createDirectories(Paths.get(PATH_PDF));
			JasperExportManager.exportReportToPdfFile(jasperPrint,
					Paths.get(PATH_PDF + "/commande_" + cmd.getId() + ".pdf").toString());

			return new ResponseEntity("{\"result\":\"ok\"}", HttpStatusCode.valueOf(201));

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity(e, HttpStatusCode.valueOf(500));
		}

	}

	@PostMapping(path = "/boutique/{boutiqueId}/commande-client")
	@Operation(summary = "Création d'une commande client par une boutique", responses = {
			@ApiResponse(responseCode = "201", description = "Commande créée avec succès"),
			@ApiResponse(responseCode = "401", description = "Non autorisé"),
			@ApiResponse(responseCode = "404", description = "Ressource non trouvée"),
			@ApiResponse(responseCode = "500", description = "Erreur serveur")
	})
	public ResponseEntity<String> passerCommandeClientParBoutique(
			@PathVariable Long boutiqueId,
			@RequestBody BoutiqueCommandeRequestDto requestDto) {
		try {
			// Vérification de la boutique
			Boutique boutique = boutiqueRepository.findById(boutiqueId)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Boutique non trouvée"));

			// Vérification du client
			Utilisateur clientUtilisateur = utilisateurRepository.findById(requestDto.clientId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client non trouvé"));

			if (!clientUtilisateur.getActif()) {
				return new ResponseEntity<>("{\"result\":\"error\", \"message\":\"Client inactif\"}",
						HttpStatus.BAD_REQUEST);
			}

			// Vérification des produits
			for (LigneInsertDto ligne : requestDto.lignes()) {
				Produit produit = produitRepository.findById(ligne.produit())
						.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
								"Produit non trouvé: " + ligne.produit()));

				// Vérifier que le produit appartient à la boutique
				if (!produit.getBoutique().getId().equals(boutiqueId)) {
					return new ResponseEntity<>(
							"{\"result\":\"error\", \"message\":\"Produit " + produit.getId()
									+ " n'appartient pas à la boutique\"}",
							HttpStatus.BAD_REQUEST);
				}
			}

			// Gestion du panier - Supprimer l'ancien panier
			panierRepository.deleteAll(panierRepository.findByClientId(requestDto.clientId()));

			// Création du nouveau panier temporaire
			for (LigneInsertDto ligne : requestDto.lignes()) {
				Panier nouveauPanier = new Panier();
				nouveauPanier.setProduit(produitRepository.getReferenceById(ligne.produit()));
				nouveauPanier.setClient(clientUtilisateur);
				nouveauPanier.setQuantite(ligne.quantite());
				nouveauPanier.setChoix(ligne.quantite() + " articles"); // Ajout du choix manquant
				panierRepository.save(nouveauPanier);
			}

			// S'assurer que le client est lié à la boutique
			Client client = clientRepository.findByUtilisateurId(requestDto.clientId())
					.orElseGet(() -> {
						Client newClient = userService.insertClientIfNull(clientUtilisateur, boutique);
						return newClient;
					});

			// Création de la commande
			CommandeInsertDto commandeDto = new CommandeInsertDto(
					requestDto.dateEcheance(),
					requestDto.methodePaiement(),
					requestDto.pointLivraison(),
					requestDto.clientId(),
					boutiqueId,
					client.getTva() // Utilisation de la TVA du client
			);

			return inserCommande(commandeDto);

		} catch (ResponseStatusException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("{\"result\":\"error\", \"message\":\"Erreur serveur\"}",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/deleteFavori/{p}/{u}")
	public ResponseEntity<String> deleteFavori(@PathVariable(name = "p") Long p, @PathVariable(name = "u") Long u) {
		Favori favori = favoriRepository.findByProduitIdAndClientId(p, u);
		favoriRepository.delete(favori);

		return new ResponseEntity("{\"result\":\"ok\"}", HttpStatusCode.valueOf(200));
	}

	@GetMapping(path = "/create-commande-pdf/{id}")
	public ResponseEntity<String> pdf(@PathVariable(name = "id") Long id) {

		try {

			Commande cmd = commandeRepository.getReferenceById(id);
			// creation PDF facture
			CommandeListeDto commandeDTO = new CommandeListeDto(cmd, ligneCommandeRepository.findByCommande(cmd));
			List<LigneCommandeListeDto> ligneCommandeDTO = commandeDTO.getProduits();
			Double tvaTemp = commandeDTO.getTva();
			String tva = tvaTemp * 100 + "%";
			Double total = commandeDTO.getTotal() + (commandeDTO.getTotal() * commandeDTO.getTva());

			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ligneCommandeDTO);

			InputStream in = new ClassPathResource(PATH_JASPER + "/report.jrxml").getInputStream();
			if (!Files.exists(Paths.get("report.jrxml"))) {
				File targetFile = new File("report.jrxml");
				Files.copy(in, Paths.get("report.jrxml"));
			}

			JasperReport jasperReport = JasperCompileManager.compileReport(Paths.get("report.jrxml").toString());
			/*
			 * File targetFile = new File("report.jrxml");
			 * Files.copy( in, targetFile.toPath() , StandardCopyOption.REPLACE_EXISTING );
			 * JasperReport jasperReport =
			 * JasperCompileManager.compileReport(targetFile.getAbsolutePath());
			 */
			Map<String, Object> parameters = new HashMap<>();

			parameters.put("adresseLivraison", commandeDTO.getLivraison());
			parameters.put("nomClient", commandeDTO.getNom().toUpperCase());
			parameters.put("prenomClient", commandeDTO.getPrenom().toUpperCase());
			parameters.put("emailClient", commandeDTO.getEmail());
			parameters.put("numeroClient", commandeDTO.getNumero());
			parameters.put("numeroCommande", commandeDTO.getId().toString());

			String jour = (commandeDTO.getDateSaisie().getDayOfMonth() < 10)
					? "0" + commandeDTO.getDateSaisie().getDayOfMonth()
					: "" + commandeDTO.getDateSaisie().getDayOfMonth();
			String mois = (commandeDTO.getDateSaisie().getMonthValue() < 10)
					? "0" + commandeDTO.getDateSaisie().getMonthValue()
					: "" + commandeDTO.getDateSaisie().getMonthValue();
			parameters.put("dateCommande", jour + "/" + mois + "/" + commandeDTO.getDateSaisie().getYear());

			parameters.put("tva", tva);
			parameters.put("total", total);

			parameters.put("listeProduit", dataSource);

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

			Files.createDirectories(Paths.get(PATH_PDF));
			JasperExportManager.exportReportToPdfFile(jasperPrint,
					Paths.get(PATH_PDF + "/commande_" + cmd.getId() + ".pdf").toString());

			return new ResponseEntity("{\"result\":\"ok\"}", HttpStatusCode.valueOf(201));

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("{\"result\":\"error\"}", HttpStatusCode.valueOf(500));
		}

	}

	@GetMapping(path = "/notification/{id}")
	public ResponseEntity<String> notification(@PathVariable(name = "id") Long id) {

		try {

			notificationService.envoyerNotififcationPromotion(produitRepository.getReferenceById(id));
			return new ResponseEntity("{\"result\":\"ok\"}", HttpStatusCode.valueOf(200));

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity("{\"result\":\"error\"}", HttpStatusCode.valueOf(500));
		}

	}

}
