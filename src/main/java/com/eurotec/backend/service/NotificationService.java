package com.eurotec.backend.service;

import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.eurotec.backend.entity.Produit;


@Service
public class NotificationService 
{
	
	public void envoyerNotififcationPromotion(Produit produit)
	{	
		
		RestTemplate restTemplate = new RestTemplate();
		
		// simple get by id ============
		/*
		String url = "https://jsonplaceholder.typicode.com/posts/1";
        HttpEntity request = new HttpEntity("");
        ResponseEntity<PostDto> response = restTemplate.exchange( url,  HttpMethod.GET, request, PostDto.class);
            
        System.out.println(" status code == "+response.getStatusCode().value() );
        System.out.println(" contenu == "+response.getBody() );
        */
        
		// get list ==============
        /*
		String url = "https://jsonplaceholder.typicode.com/posts";
        HttpEntity request = new HttpEntity("");
        ResponseEntity<PostDto[]> response = restTemplate.exchange( url,  HttpMethod.GET, request, PostDto[].class);
            
        System.out.println(" status code == "+response.getStatusCode().value() );
        System.out.println(" contenu == "+response.getBody().length );
        */
        
		// post 
		/*
		String url = "https://jsonplaceholder.typicode.com/posts";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type","application/json; charset=UTF-8");
        HttpEntity<PostDto> request = new HttpEntity<PostDto>( new PostDto(Long.valueOf(17L), null, "titre test", "body test ") , headers );
        ResponseEntity<PostDto> response = restTemplate.exchange( url,  HttpMethod.POST, request, PostDto.class);
            
        System.out.println(" status code == "+response.getStatusCode().value() );
        System.out.println(" contenu == "+response.getBody() );
        */
		
		try
		{
			String url ="https://fcm.googleapis.com/fcm/send";
			
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-type"  ,"application/json; charset=UTF-8");
			headers.set("Authorization" ,"key=AAAACx9DKUo:APA91bGy1eT_XZaegCEItcGHkdGxyAUVzW92p7Ohrcxh8-JgMiPnVOF9HIb-pjGbSR_Eu9CR_ILnLaWcnwvjQfNJjwYOUR5nNNe98PmZ3eWkNCmvLRS0z8U0aBJx7MLu7LbWyxCeRcl1");
			
			DataDto data = new DataDto(produit.getId());
			MessageFcmDto mesage = new MessageFcmDto("Promotion !!! ", "Le produit '"+produit.getNom()+"' est en promotion à partir d'aujourd'hui, profitez-en dès maintenant. ");
			PromotionFcmDto promDto = new PromotionFcmDto( "/topics/promotion" , mesage , data );
			
	        HttpEntity<PromotionFcmDto> request = new HttpEntity<>( promDto , headers );
	        ResponseEntity<String> response = restTemplate.exchange( url,  HttpMethod.POST, request, String.class);
	            
	        System.out.println(" Notification : status code == "+response.getStatusCode().value() );
	        
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
    
		
	}
	
	

}
