package com.example.catalog.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.catalog.model.CatalogDetail;
import com.example.catalog.service.CatalogService;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

	private  static final Logger log = LoggerFactory.getLogger(CatalogController.class);    
	
	@Autowired
	private Environment env;
	
	@Autowired
	private CatalogService catalogService; 
	
	@GetMapping("/getAllCatalog")
	//@Cacheable("catalog_data")
	//List<CatalogDetail>
	public ResponseEntity<List<CatalogDetail>> getAllCatalog() throws InterruptedException {
		log.info("Inside getCatalog Details .....");
		return new ResponseEntity<List<CatalogDetail>>(this.catalogService.getAllCatalogDetails(), HttpStatus.OK);
	}
	
	@GetMapping("/getCatalog/{id}")
	//@Cacheable("catalog_data")
	public CatalogDetail getCatalog(@PathVariable(value="id") long id ) throws InterruptedException {
		log.info("Inside getCatalog Details .....");
		return this.catalogService.getCatalogDetail(id);
	}
	
	/*@GetMapping("/remove/{id}")
	public void removeCatalog(@PathVariable(value="id") long id ) throws InterruptedException {
		log.info("Inside removeCatalog .....");
		this.catalogService.removeData(id);
	}
	
	@PostMapping("/add")
	public void addData(@RequestBody CatalogDetail catalogDetails) {
		this.catalogService.addData(catalogDetails);
	}
	
	@PutMapping("/update")
	public void updateData(@RequestBody CatalogDetail catalogDetails) {
		this.catalogService.updateData(catalogDetails);
	}*/
}
