package com.eurotec.backend.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class RestExceptionHandler 
{
	  
	  
	  @ExceptionHandler(BindException.class)
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  @ResponseBody 
	  Map<String ,String> handleException(BindException e) 
	  {
		  Map<String ,String> error = new HashMap<String, String>();
		  for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			  error.put( fieldError.getField() , fieldError.getDefaultMessage() );
		  }
		  return error;
	  }
	  
	  
	  
	  @ExceptionHandler(ConstraintViolationException.class)
	  @ResponseStatus( HttpStatus.BAD_REQUEST)
	  @ResponseBody	
	  Map<String ,String> handleException(ConstraintViolationException e) 
	  {  
		  Map<String ,String> error = new HashMap<String, String>();
		  for (ConstraintViolation violation : e.getConstraintViolations()) {
			error.put( violation.getPropertyPath().toString() , violation.getMessage() );
	    }
	    return error;
	  }	  
		  
	  
	  @ExceptionHandler(DataIntegrityViolationException.class)
	  public ResponseEntity<String> handleException(DataIntegrityViolationException exception) {
		    return new  ResponseEntity<String>( HttpStatusCode.valueOf(409) );
	  }
	  
	  
	  @ExceptionHandler(ResponseStatusException.class)
	  public ResponseEntity<String> handleException(ResponseStatusException exception) {
	    return new  ResponseEntity<String>( exception.getStatusCode() );
	  }

		  
	
	  @ExceptionHandler(Exception.class)
	  public ResponseEntity<String> handleException(Exception exception) {
		return new  ResponseEntity<String>( HttpStatusCode.valueOf(500) );
	  }
	  
	  
	  


}
