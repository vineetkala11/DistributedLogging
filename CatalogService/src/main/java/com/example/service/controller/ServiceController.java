package com.example.service.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.model.CatalogDetail;
import com.example.service.services.CatalogService;



@RestController
@RequestMapping("/service")
public class ServiceController {

	private  static final Logger logger = LoggerFactory.getLogger(ServiceController.class);    

	@Autowired
	private CatalogService catalogService;
	/*@GetMapping("/fetchCatalog")
	public ResponseEntity<List<CatalogDetail>> getCatalogUsingRestTemplate() { 
		log.info("Inside getCatalogUsingRestTemplate");
	    ResponseEntity<List<CatalogDetail>> responseEntity = new RestTemplate().exchange(
	            "http://localhost:8000/catalog/getAllCatalog", 
	            HttpMethod.GET,
	            null,
	            new ParameterizedTypeReference<List<CatalogDetail>>(){});  
	    
	    List<CatalogDetail> response = responseEntity.getBody();     
	    log.info("Returing data : {}", response);
	    return new ResponseEntity<List<CatalogDetail>>(response, HttpStatus.OK);
	}*/
	
	
	@GetMapping("/fetchCatalog")
	public ResponseEntity<List<CatalogDetail>> getCatalogUsingRestTemplate() {
		logger.info("Inside getCatalogUsingRestTemplate");
		List<CatalogDetail> details = catalogService.getCatalogUsingRestTemplate();
		logger.info("Returing data : {}", details);
		return new ResponseEntity<List<CatalogDetail>>(details, HttpStatus.OK);
	}
	
}
