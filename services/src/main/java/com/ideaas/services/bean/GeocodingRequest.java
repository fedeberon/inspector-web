package com.ideaas.services.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GeocodingRequest {
    private List<List<String>> points;

    @JsonProperty("profile")
    private String profile;

    public GeocodingRequest() {

    }

    public GeocodingRequest(List<List<String>> points, String profile) {
        this.points = points;
        this.profile = profile;
    }

    public List<List<String>> getPoints() {
        return points;
    }

    public void setPoints(List<List<String>> points) {
        this.points = points;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
