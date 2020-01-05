package com.example.catalog.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.example.catalog.model.CatalogDetail;
import com.example.catalog.redis.CatalogCacheManager;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CatalogService {

	private CatalogCacheManager catalogCacheManager;

	@Autowired
	private Environment env;
	
	@Autowired
	CatalogService(CatalogCacheManager catalogCacheManager){
		this.catalogCacheManager = catalogCacheManager;
	}
	
	public List<CatalogDetail> getAllCatalogDetails(){
		Map<Object, CatalogDetail> data = this.catalogCacheManager.getAllCatalogDetail();
		if(null != data) {
			log.info("No Data found in Redis");
			return data.values().stream().collect(Collectors.toList());
		}
		List<CatalogDetail> dbData = dbCall(-1);
		dbData.forEach(d -> this.catalogCacheManager.cacheCatalogDetails(d));
		return dbData;
	}
	
	public CatalogDetail getCatalogDetail(long id){
		CatalogDetail data = this.catalogCacheManager.getCatalogDetail(id);
		if(null != data) {
			return data;
		}
		List<CatalogDetail> dbData = actingAsDbTable();
		dbData.forEach(d -> this.catalogCacheManager.cacheCatalogDetails(d));
		return dbData.get(0);
	}
	
	public void addData(CatalogDetail catalogDetails) {
		this.catalogCacheManager.cacheCatalogDetails(catalogDetails);
	}
	
	public void removeData(long id) {
		this.catalogCacheManager.removeCatalogDetail(id);
	}
	
	public void updateData(CatalogDetail catalogDetails) {
		this.catalogCacheManager.cacheCatalogDetails(catalogDetails);
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
