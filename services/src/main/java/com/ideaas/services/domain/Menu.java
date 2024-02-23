package com.ideaas.services.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @Column(name = "idMenu")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "pantalla")
    private String pantalla;

    @Column(name = "btnMenu")
    private String btnMenu;

    public Menu() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPantalla() {
        return pantalla;
    }

    public void setPantalla(String pantalla) {
        this.pantalla = pantalla;
    }

    public String getBtnMenu() {
        return btnMenu;
    }

    public void setBtnMenu(String btnMenu) {
        this.btnMenu = btnMenu;
    }
}
