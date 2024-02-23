package com.ideaas.services.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.http.MediaType;
import com.ideaas.services.bean.GeocodingRequest;
import com.ideaas.services.service.interfaces.AppGeocodingService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Service
public class GeocodingServiceImpl implements AppGeocodingService {

    @Autowired
    private RestTemplate restTemplate;

    public GeocodingServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String createOptimizedRoute(GeocodingRequest geocodingRequest) {
        String url = "https://geocoding.geoplanningmas.com/route";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<GeocodingRequest> request = new HttpEntity<>(geocodingRequest, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al obtener la ruta optimizada.";
        }

    }


    @Override
    public String getOptimizedRoute(String id) {

        String url = "https://geocoding.geoplanningmas.com/route?routeId=" + id;

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al obtener la ruta optimizada.";
        }
          
    }
}


    