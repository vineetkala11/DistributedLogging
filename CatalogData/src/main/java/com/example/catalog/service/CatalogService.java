package com.example.catalog.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.example.catalog.model.CatalogDetail;

@Service
public class CatalogService {

	private  static final Logger log = LoggerFactory.getLogger(CatalogService.class); 
	
	@Autowired
	private Environment env;
	
	
	public List<CatalogDetail> getAllCatalogDetails(){
		log.info("Inside getAllCatalogDetails ");
		List<CatalogDetail> dbData = dbCall(-1);
		return dbData;
	}
	
	public CatalogDetail getCatalogDetail(long id){
		log.info("Inside getCatalogDetail for id : "+id);
		List<CatalogDetail> dbData = dbCall(id);
		return dbData.get(0);
	}
	
	
	public List<CatalogDetail> dbCall(long id){
		List<CatalogDetail> details = new ArrayList<>();
		if(id <= -1)
			details.addAll(actingAsDbTable());
		else
			details.add(actingAsDbTable().get((int) id-1));
		
		return details;
	}
	
	public List<CatalogDetail> actingAsDbTable() {
		List<CatalogDetail> details = new ArrayList<>();
		CatalogDetail cd = new CatalogDetail();
		cd.setId(1);
		cd.setItemName("Cricket Bat");
		cd.setItemCount(100);
		cd.setPort(env.getProperty("server.port"));
		CatalogDetail cd1 = new CatalogDetail();
		cd1.setId(2);
		cd1.setItemName("Cricket Ball");
		cd1.setItemCount(350);
		cd1.setPort(env.getProperty("server.port"));
		CatalogDetail cd2 = new CatalogDetail();
		cd2.setId(3);
		cd2.setItemName("Football");
		cd2.setItemCount(30);
		cd2.setPort(env.getProperty("server.port"));
		details.add(cd);details.add(cd1);details.add(cd2);
		log.info("Returing data : {}", details);
		return details;
	}
	
	
}
