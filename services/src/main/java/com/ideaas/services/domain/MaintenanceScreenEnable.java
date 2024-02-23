package com.ideaas.services.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "maintenance_screen_enable")
public class MaintenanceScreenEnable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "module")
    private String module;

    @Column(name = "status")
    private Boolean status;

    public MaintenanceScreenEnable() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
