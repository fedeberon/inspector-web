package com.ideaas.services.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This DTP is used by views that are heavily based on location, and
 * not on any of its attributes.
 */
@Data
@NoArgsConstructor
public class LocationDTO extends BasicLocationDTO{
    /**
     * The {@link com.ideaas.services.domain.MapUbicacion location}
     * {@link com.ideaas.services.domain.MapUbicacion#getId id}.
     */
    private Long id;

    /**
     * The {@link com.ideaas.services.domain.MapUbicacion location}
     * {@link com.ideaas.services.domain.MapUbicacion#getDireccion address}.
     */
    private String address;

    /**
     * The {@link com.ideaas.services.domain.MapUbicacion location}
     * {@link com.ideaas.services.domain.MapUbicacion#getIdReferencia reference id}.
     */
    private String referenceId;

    public LocationDTO(Long id, String company, String address, String city, String element, String province, String referenceId) {
        super(city, company, element, province);
        this.id = id;
        this.address = address;
        this.referenceId = referenceId;
    }

}
