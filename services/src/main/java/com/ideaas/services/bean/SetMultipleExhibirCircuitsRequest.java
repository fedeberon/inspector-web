package com.ideaas.services.bean;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@Data
public class SetMultipleExhibirCircuitsRequest {
    private Boolean exhibir;
    private String circuitsDTOs;
    private ObjectMapper mapper;

    public SetMultipleExhibirCircuitsRequest() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public List<MapCircuitDTO> getCircuitDTOSObjects(){;
        try {
            return mapper.readValue(circuitsDTOs, new TypeReference<List<MapCircuitDTO>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
