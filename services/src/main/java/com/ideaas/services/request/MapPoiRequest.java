package com.ideaas.services.request;

import com.ideaas.services.bean.RequestUtil;

import java.util.Objects;

public class MapPoiRequest {

    private String mapPoiEntidad;

    private Integer page = 0;

    private String maxResults = "10";

    private RequestUtil requestUtil = new RequestUtil();

    public String getMapPoiEntidad() {
        return mapPoiEntidad;
    }

    public void setMapPoiEntidad(String mapPoiEntidad) {
        this.mapPoiEntidad = mapPoiEntidad;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(String maxResults) {
        this.maxResults = maxResults;
    }


    public String getPoisEntidadSelected(){
        if(Objects.isNull(mapPoiEntidad)) return requestUtil.EMPTY;

        return requestUtil.buildFormatValuesSelected(mapPoiEntidad);
    }

    public String getMaxResultsSelected(){
        if(Objects.isNull(maxResults)) return requestUtil.EMPTY;

        return requestUtil.buildFormatValuesSelected(maxResults);
    }
}
