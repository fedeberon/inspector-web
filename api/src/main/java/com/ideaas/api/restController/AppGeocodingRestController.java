package com.ideaas.api.restController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideaas.services.bean.GeocodingRequest;
import com.ideaas.services.service.interfaces.AppGeocodingService;

@RestController
@RequestMapping("api/geocoding")
public class AppGeocodingRestController {

    private final AppGeocodingService appGeocodingService;

    public AppGeocodingRestController(AppGeocodingService appGeocodingService) {
        this.appGeocodingService = appGeocodingService;
    }


    @PostMapping
    public Map<String, Object> createGeocodingRoute(@RequestBody GeocodingRequest geocodingRequest) {

        String responseBody = appGeocodingService.createOptimizedRoute(geocodingRequest);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap = new HashMap<>();

        try {
            responseMap = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al convertir JSON: " + e.getMessage());
        }
    
        return responseMap;

    }


    @GetMapping("/{id}")
    public Map<String, Object> getOptimizedRouteById(@PathVariable String id) {

        String responseBody = appGeocodingService.getOptimizedRoute(id);
        
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap = new HashMap<>();

        try {
            responseMap = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al convertir JSON: " + e.getMessage());
        }
    
        return responseMap;
    }
}