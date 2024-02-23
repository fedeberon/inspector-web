package com.ideaas.services.service.interfaces;

import com.ideaas.services.bean.GeocodingRequest;


public interface AppGeocodingService {

    String createOptimizedRoute(GeocodingRequest geocodingRequest);

    String getOptimizedRoute(String id);

}

