package com.ideaas.services.service;

import com.ideaas.services.dao.ContratoDao;
import com.ideaas.services.domain.Contrato;
import com.ideaas.services.service.interfaces.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ContratoServiceImpl implements ContratoService {

    private ContratoDao dao;

    @Autowired
    public ContratoServiceImpl(ContratoDao dao) {
        this.dao = dao;
    }

    @Override
    public Contrato get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public Contrato save(Contrato contrato) {
        return dao.save(contrato);
    }

    @Override
    public List<Contrato> findAll() {
        Iterable<Contrato> iterator = dao.findAll();

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<Contrato> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Contrato> contratos = dao.findAll(paging);

        return contratos.getContent();
    }

    @Override
    public List<Contrato> findByEstadoNotAndTipoContratoSorted(String estado , String tipoContrato){
        Iterable<Contrato> iterator = dao.findByEstadoNotAndTipoContratoOrderByDescripcionAsc(estado , tipoContrato);

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }
}
