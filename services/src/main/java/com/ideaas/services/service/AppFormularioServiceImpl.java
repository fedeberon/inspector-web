package com.ideaas.services.service;

import com.ideaas.services.dao.AppFormularioDao;
import com.ideaas.services.domain.AppFormulario;
import com.ideaas.services.service.interfaces.AppFormularioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AppFormularioServiceImpl implements AppFormularioService {

    private AppFormularioDao dao;

    private AppFormularioServiceImpl(AppFormularioDao dao){
        this.dao = dao;
    }

    @Override
    public AppFormulario get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public AppFormulario save(AppFormulario appFormulario) {
        return dao.save(appFormulario);
    }

    @Override
    public List<AppFormulario> findAll() {
        Iterable<AppFormulario> iterator = dao.findAll();

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppFormulario> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<AppFormulario> appFormularios = dao.findAll(paging);

        return appFormularios.getContent();
    }

    @Override
    public List<AppFormulario> findAllByBajaLogica(Boolean bajaLogica) {
        Iterable<AppFormulario> iterator = dao.findAllByBajaLogicaFormulario(bajaLogica);

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }
}
