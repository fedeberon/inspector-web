package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.AllowedStatus;

public interface AllowedStatusService {

    Boolean existsByDescripcion(String descripcion);
}
