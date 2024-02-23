package com.ideaas.services.domain;

import javax.persistence.*;

/**
 * <p>This class represents a project associated
 * with many different surveys, {@link AppRelevamiento}.
 *
 *
 * @see AppRelevamiento
 */
@Entity
@Table(name="app_proyecto")
public class AppProject {

    /**
     * The App Project's id
     */
    @Id
    @SequenceGenerator(name = "AppProyectoSeqGen", sequenceName = "SEQ_APP_PROYECTO", allocationSize = 1)
    @GeneratedValue(generator = "AppProyectoSeqGen")
    @Column(name = "id_app_proyecto")
    private Long id;

    /**
    *  The name of the project
     */
    @Column(name = "nombre")
    private String name;

    /**
     * Instantiates a new App Project.
     *
     * @param id                 the id
     * @param name               the name
     */
    public AppProject(Long id, String name){
        this.id = id;
        this.name = name;
    }
    /**
     * Instantiates a new App Project.
     *
     * @param name               the name
     */
    public AppProject(String name) {
        this.name = name;
    }

    /**
     * Instantiates a new App Project.
     */
    public AppProject(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
