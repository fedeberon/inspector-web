package com.ideaas.services.request;

import java.time.LocalDate;


public class HeatMapRequest {

    private Long[] edad;

    private String [] genero;

    private LocalDate fechaRecepcion;

    public Long[] getEdad() {
        return edad;
    }

    public void setEdad(Long[] edad) {
        this.edad = edad;
    }

    public String[] getGenero() {
        return genero;
    }

    public void setGenero(String[] genero) {
        this.genero = genero;
    }

    public LocalDate getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(LocalDate fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }
}
