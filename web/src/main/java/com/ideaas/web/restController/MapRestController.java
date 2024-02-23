package com.ideaas.web.restController;

import com.google.maps.model.GeocodingResult;
import com.ideaas.services.domain.MapUbicacion;
import com.ideaas.services.service.interfaces.GoogleMapsService;
import com.ideaas.services.service.interfaces.MapUbicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by federicoberon on 04/12/2019.
 */
@RestController
public class MapRestController {

    private GoogleMapsService googleMapsService;

    private MapUbicacionService mapUbicacionService;

    @Autowired
    public MapRestController(GoogleMapsService googleMapsService , MapUbicacionService mapUbicacionService) {
        this.googleMapsService = googleMapsService;
        this.mapUbicacionService = mapUbicacionService;
    }

    @GetMapping("map/searchCoordinatesByAdress")
    public ResponseEntity locate(@RequestParam String address) {
        GeocodingResult[] results = googleMapsService.getByAddress(address);
        return new ResponseEntity<>(results[0].geometry, HttpStatus.OK);
    }

    @PostMapping("map/deletePolygon")
    public ResponseEntity<MapUbicacion> deletePolygon(@RequestParam Long idUbicacion){
        MapUbicacion mapUbicacion = mapUbicacionService.deletePolygonLatLong(idUbicacion);

        return new ResponseEntity<>( mapUbicacion , HttpStatus.OK);
    }
}
