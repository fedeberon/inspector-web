package com.ideaas.services.request;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ideaas.services.bean.MapCircuitDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
//import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;


/**
 * The type Export circuit request.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExportCircuitRequest {
    private Long companyId;
    private Long campaignId;
    private String campaign;
    private String client;
    private String endDate;
    private String startDate;
    private String observations;
    private Long listType;
    private Boolean isSubjectToAvailability;
    private byte[] image;

    private Boolean includeReferenceId;
    private Boolean includeAddress;
    private Boolean includeCity;
    private Boolean includeElement;
    private Boolean includeCoordinates;

    private List<MapCircuitDTO> circuitDTOS;
    private Map<Long, String> parameters = new HashMap<>();


    /**
     * Include observations boolean.
     *
     * @return the boolean
     */
    public boolean includeObservations() {
        return Objects.nonNull(observations)&&!observations.trim().isEmpty();
    }

    /**
     * Is subject to availability boolean.
     *
     * @return the boolean
     */
    public boolean isSubjectToAvailability() {
        return Objects.nonNull(isSubjectToAvailability)&&isSubjectToAvailability;
    }

    /**
     * Sets image.
     *
     * @param image the image
     * @throws IOException the io exception
     * @deprecated
     */
    public void setImage(String image) throws IOException {
        //BASE64Decoder decoder = new BASE64Decoder();
        //this.image = decoder.decodeBuffer(image.substring(image.indexOf(",") + 1));
    }

    /**
     * Sets image.
     *
     * @param image the image
     */
    public void setImage(byte[] image) {
        this.image = image;
    }


    /**
     * Get image byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getImage() {
        return Optional.ofNullable(image).orElse(new byte[0]);
    }

    /**
     * Gets list type.
     *
     * @return the list type
     */
    public String getListType() {
        return ListType.of(Optional.ofNullable(listType).orElse(1L)).getDescription();
    }

    /**
     * Gets subject to availability.
     *
     * @return the subject to availability
     */
    public Boolean getSubjectToAvailability() {
        return isSubjectToAvailability;
    }

    /**
     * Include reference id boolean.
     *
     * @return the boolean
     */
    public Boolean includeReferenceId() {
        return Objects.nonNull(includeReferenceId)&&includeReferenceId;
    }

    /**
     * Include address boolean.
     *
     * @return the boolean
     */
    public Boolean includeAddress() {
        return Objects.nonNull(includeAddress)&&includeAddress;
    }

    /**
     * Include city boolean.
     *
     * @return the boolean
     */
    public Boolean includeCity() {
        return Objects.nonNull(includeCity)&&includeCity;
    }

    /**
     * Include element boolean.
     *
     * @return the boolean
     */
    public Boolean includeElement() {
        return Objects.nonNull(includeElement)&&includeElement;
    }

    /**
     * Include coordinates boolean.
     *
     * @return the boolean
     */
    public Boolean includeCoordinates() {
        return Objects.nonNull(includeCoordinates)&&includeCoordinates;
    }


    /**
     * A simple interface to be able to map the value sent by the request
     */
    private enum ListType {
        /**
         * The tentative list type.
         */
        TENTATIVE(1L, "LISTADO TENTATIVO"),
        /**
         * The definitive list type.
         */
        DEFINITIVE(2L, "LISTADO DEFINITIVO");

        private Long code;
        private String description;

        private ListType(Long code, String description) {
            this.code = code;
            this.description = description;
        }

        /**
         * Gets code.
         *
         * @return the code
         */
        public Long getCode() {
            return code;
        }

        /**
         * Gets description.
         *
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        /**
         * Of list type.
         *
         * @param code the code
         * @return the list type
         */
        public static ListType of(Long code) {
            return Stream.of(ListType.values())
                    .filter(p -> p.getCode() == code)
                    .findFirst()
                    .orElse(null);
        }
    }

    /**
     * The parameters map.
     *
     * @return the map
     */
    @JsonAnyGetter
    public Map<Long, String> any(){
        return this.parameters;
    }


    /**
     * Setter the values for the parameter map.
     *
     * @param key   the key
     * @param value the value
     */
    @JsonAnySetter
    public void setMap(String key, String value) {
        try {
        parameters.put(Long.valueOf(key), value);
        } catch (NumberFormatException  ex) { }
    }
}