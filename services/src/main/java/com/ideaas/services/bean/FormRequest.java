package com.ideaas.services.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class FormRequest {
    private Long idFormulario;
    private Long idUbicacion;
    private Long idRelevamiento;
    private Long idRespuesta;

    public FormRequest() {
    }

    public FormRequest(Long idFormulario, Long idUbicacion, Long idRelevamiento) {
        this.idFormulario = idFormulario;
        this.idUbicacion = idUbicacion;
        this.idRelevamiento = idRelevamiento;
    }

    public Long getIdFormulario() {
        return idFormulario;
    }

    public void setIdFormulario(Long idFormulario) {
        this.idFormulario = idFormulario;
    }

    public Long getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Long idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public Long getIdRelevamiento() {
        return idRelevamiento;
    }

    public void setIdRelevamiento(Long idRelevamiento) {
        this.idRelevamiento = idRelevamiento;
    }
}
