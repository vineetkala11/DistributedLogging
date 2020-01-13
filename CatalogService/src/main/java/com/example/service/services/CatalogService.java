package com.example.service.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.service.restClient.CatalogServiceProxy;
import com.example.service.model.CatalogDetail;

@Service
public class CatalogService {

	private  static final Logger log = LoggerFactory.getLogger(CatalogService.class);  
	
	@Autowired
	private CatalogServiceProxy serviceProxy;
	
	public List<CatalogDetail> getCatalogUsingRestTemplate(){
		List<CatalogDetail> details = null;
		log.info("Inside CatalogService, calling catalog-data service");
		try {
			details = serviceProxy.getCatalogList();
		}catch(Exception e) {
			
			details = fallback();
		}
		return details;
	}
	
	public List<CatalogDetail> fallback() {
		log.info("Inside fallback !!!!");
		List<CatalogDetail> details = new ArrayList<>();
		CatalogDetail cd = new CatalogDetail();
		cd.setItemName("NA");
		cd.setItemCount(0);
		details.add(cd);
		return details;
	}
}
