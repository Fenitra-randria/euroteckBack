package com.eurotec.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceWebConfiguration implements WebMvcConfigurer {

	@Value("${images.path}")
	String PATH_IMAGE;

	@Value("${pdf.path}")
	String PATH_PDF;

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) 
	{
		registry.addResourceHandler("/images/**").addResourceLocations("file:"+PATH_IMAGE + "/");
		registry.addResourceHandler("/pdf/**").addResourceLocations("file:"+PATH_PDF + "/");
	}
	
}
