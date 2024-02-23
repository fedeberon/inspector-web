package com.ideaas.services.bean;

import com.ideaas.services.request.*;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by federicoberon on 29/10/2019.
 */
 @Component
 @ToString
public class Wrapper {

    private Long id;

    private String idAppUbicacionesList;

    private List<MyObject> list = new ArrayList<>();

    private MapUbicacionRequest request = new MapUbicacionRequest();

    private UbicacionActualizacionRequest ubicacionActualizacionRequest = new UbicacionActualizacionRequest();

    private MapUbiActEspecialRequest mapUbiActEspecialRequest = new MapUbiActEspecialRequest();

    private MapPoiRequest mapPoiRequest = new MapPoiRequest();

    private CreateMapReservacionRequest createMapReservacionRequest = new CreateMapReservacionRequest();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MyObject> getSelectedElements() {
        return list.stream().filter(line -> line.getChecked()).collect(Collectors.toList());
    }

    public MapUbicacionRequest getRequest() {
        return request;
    }

    public void setRequest(MapUbicacionRequest request) {
        this.request = request;
    }

    private Integer page = 0;

    public List<MyObject> getList() {
        return list;
    }

    public void setList(List<MyObject> list) {
        this.list = list;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public UbicacionActualizacionRequest getUbicacionActualizacionRequest() {
        return ubicacionActualizacionRequest;
    }

    public void setUbicacionActualizacionRequest(UbicacionActualizacionRequest ubicacionActualizacionRequest) {
        this.ubicacionActualizacionRequest = ubicacionActualizacionRequest;
    }

    public MapUbiActEspecialRequest getMapUbiActEspecialRequest() {
        return mapUbiActEspecialRequest;
    }

    public void setMapUbiActEspecialRequest(MapUbiActEspecialRequest mapUbiActEspecialRequest) {
        this.mapUbiActEspecialRequest = mapUbiActEspecialRequest;
    }

    public MapPoiRequest getMapPoiRequest() {
        return mapPoiRequest;
    }

    public void setMapPoiRequest(MapPoiRequest mapPoiRequest) {
        this.mapPoiRequest = mapPoiRequest;
    }

    public String getIdAppUbicacionesList() {
        return idAppUbicacionesList;

    }

    public List<Long> getIdAppUbicacionesListSelected() {
        if(Objects.isNull(idAppUbicacionesList)||idAppUbicacionesList.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(idAppUbicacionesList.split(",")).map(Long::valueOf).collect(Collectors.toList());
    }

    public void setIdAppUbicacionesList (String idAppUbicacionesList) {
        this.idAppUbicacionesList = idAppUbicacionesList;
    }

    public CreateMapReservacionRequest getCreateMapReservacionRequest() {
        return createMapReservacionRequest;
    }

    public void setCreateMapReservacionRequest(CreateMapReservacionRequest createMapReservacionRequest) {
        this.createMapReservacionRequest = createMapReservacionRequest;
    }
}
