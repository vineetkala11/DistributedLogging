package com.example.service.restClient;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.service.model.CatalogDetail;

@Service
public class CatalogServiceProxy {

	@Value("${catalogdata.host}")
	private String host;
	
	@Value("${catalogdata.getall}")
	private String path;
	
	private RestTemplate restTemplate;
	
	@Autowired
	public CatalogServiceProxy(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
    public List<CatalogDetail> getCatalogList(){
    	String uri = host+path; 
    	ResponseEntity<CatalogDetail[]> responseEntity = restTemplate.getForEntity(uri, CatalogDetail[].class);
    	CatalogDetail[] result = responseEntity.getBody();
        return Arrays.asList(result);
    }
}
