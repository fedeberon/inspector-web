package com.ideaas.services.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data

public class AudCircuitoDetallePK implements Serializable {

    @Column(name = "Orden")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "IdCircuito")
    private AudCircuito audCircuito;

    public AudCircuitoDetallePK() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.audCircuito);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AudCircuitoDetallePK other = (AudCircuitoDetallePK) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.audCircuito, other.audCircuito)) {
            return false;
        }
        return true;
    }
}
