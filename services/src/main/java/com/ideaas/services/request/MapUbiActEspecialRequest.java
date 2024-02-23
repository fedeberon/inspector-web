package com.ideaas.services.request;

import com.ideaas.services.bean.RequestUtil;

import java.util.Objects;

public class MapUbiActEspecialRequest {

    private String idsSearch;

    private String maxResults = "10";

    private Integer page = 0;

    private RequestUtil requestUtil = new RequestUtil();

    public String getIdsSearch() {
        return idsSearch;
    }

    public void setIdsSearch(String idsSearch) {
        this.idsSearch = idsSearch;
    }

    public String getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(String maxResults) {
        this.maxResults = maxResults;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getIdsSearching(){
        if(Objects.isNull(idsSearch)) return requestUtil.EMPTY;

        return requestUtil.buildFormatValuesSelected(idsSearch);
    }

    public String getMaxResultsSelected(){
        if(Objects.isNull(maxResults)) return requestUtil.EMPTY;

        return requestUtil.buildFormatValuesSelected(maxResults);
    }

}
