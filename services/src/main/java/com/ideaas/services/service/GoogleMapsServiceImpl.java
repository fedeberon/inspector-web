package com.ideaas.services.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.ideaas.services.bean.GoogleMapsProperties;
import com.ideaas.services.service.interfaces.GoogleMapsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Created by federicoberon on 04/12/2019.
 */
@Service
public class GoogleMapsServiceImpl implements GoogleMapsService {

    private GeoApiContext context;
    private String apiKey;

    @Autowired
    public GoogleMapsServiceImpl(GoogleMapsProperties googleMapsProperties) {
        apiKey = googleMapsProperties.getMapKey();
        this.context = new GeoApiContext.Builder().apiKey("AIzaSyANGywbfxItEbdle738SiU-AVJGIjadVYM").build();
    }

    @Override
    public GeocodingResult[] getByAddress(String address){
        GeocodingResult[] results = new GeocodingResult[0];
        try {
            results = GeocodingApi.geocode(context, address).await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }


    @Override
    public String locate(final BigDecimal latitude, final BigDecimal longitude) {
        if (latitude == null || longitude == null) {
            return null;
        }
        final LatLng latlng = new LatLng(latitude.doubleValue(), longitude.doubleValue());

        StringBuilder sb = new StringBuilder();

        try {
            final GeocodingResult[] results = GeocodingApi.reverseGeocode(this.context, latlng).await();
            if (results != null && results.length > 0) {
                return results[0].formattedAddress;

            }
        } catch (final Exception e) {
            return null;
        }

        return "";
    }

}
