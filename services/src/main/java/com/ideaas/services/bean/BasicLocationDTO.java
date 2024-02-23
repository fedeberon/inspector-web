package com.ideaas.services.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The basic location class. This DTO contains all the attributes that
 * all geoplanning views share.
 */
@Data
@NoArgsConstructor
public class BasicLocationDTO {
    /**
     * The {@link com.ideaas.services.domain.MapUbicacion location}
     * {@link com.ideaas.services.domain.MapLocalidad city}.
     */
    private String city;

    /**
     * The {@link com.ideaas.services.domain.MapUbicacion location}
     * {@link com.ideaas.services.domain.MapEmpresa company}.
     */
    private String company;

    /**
     * The {@link com.ideaas.services.domain.MapUbicacion location}
     * {@link com.ideaas.services.domain.MapElemento advertising element}.
     */
    private String element;

    /**
     * The {@link com.ideaas.services.domain.MapUbicacion location}
     * {@link com.ideaas.services.domain.MapProvincia province}.
     */
    private String province;

    public BasicLocationDTO(String city, String company, String element, String province) {
        this.city = city;
        this.company = company;
        this.element = element;
        this.province = province;
    }
}
