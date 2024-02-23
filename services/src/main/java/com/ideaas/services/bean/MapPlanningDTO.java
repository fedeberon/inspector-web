package com.ideaas.services.bean;

import com.ideaas.services.domain.MapUbicaionParametro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This DTO represents a location, and all of its reservations for each day,
 * within a given range of dates. This class does not save separately what
 * the start and end values of the date range it contains are.
 */
@Data
@NoArgsConstructor
public class MapPlanningDTO extends LocationDTO {
    /**
     * A hashmap, where the keys represent a day, and the value saves the
     * reservation data.
     */
    private HashMap<String, List<MapReservationPerDayDTO>> reservationsPerDay;

    /**
     * The location parameter.
     */
    private List<MapUbicaionParametro> parameters;

    public MapPlanningDTO(Long id, String company, String address, String city, String element, String province, String referenceId, HashMap<String, List<MapReservationPerDayDTO>> reservationsPerDay) {
        super(id, company, address, city, element, province, referenceId);
        this.reservationsPerDay = reservationsPerDay;
    }

    public MapPlanningDTO(Long id, String company, String address, String city, String element, String province, String referenceId) {
        super(id, company, address, city, element, province, referenceId);
        this.reservationsPerDay = new HashMap<>();
        this.parameters = new ArrayList<>();
    }
}
