package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.AudCampania;

import java.util.List;

public interface AudCampaniaService {

    AudCampania get(Long id);

    AudCampania save(AudCampania audCampania);

    List<AudCampania> findAll(Integer pageSize, Integer pageNo, String sortBy);
}
