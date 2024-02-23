package com.ideaas.services.service;

import com.ideaas.services.dao.AudCampaniaDao;
import com.ideaas.services.domain.AudCampania;
import com.ideaas.services.service.interfaces.AudCampaniaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AudCampaniaServiceImpl implements AudCampaniaService {

    private AudCampaniaDao dao;

    @Autowired
    public AudCampaniaServiceImpl(AudCampaniaDao dao) {
        this.dao = dao;
    }

    @Override
    public AudCampania get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public AudCampania save(AudCampania audCampania) {
        return dao.save(audCampania);
    }

    @Override
    public List<AudCampania> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<AudCampania> audCampanias = dao.findAll(paging);

        return audCampanias.getContent();
    }

}
