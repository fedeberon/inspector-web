package com.ideaas.services.service;

import com.ideaas.services.dao.TipoUsuarioDao;
import com.ideaas.services.domain.TipoUsuario;
import com.ideaas.services.service.interfaces.TipoUsuarioService;
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
public class TipoUsuarioServiceImpl implements TipoUsuarioService {

    private TipoUsuarioDao dao;

    @Autowired
    public TipoUsuarioServiceImpl(TipoUsuarioDao dao) {
        this.dao = dao;
    }

    @Override
    public TipoUsuario get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public TipoUsuario save(TipoUsuario tipoUsuario) {
        return dao.save(tipoUsuario);
    }

    @Override
    public List<TipoUsuario> findAll() {
        Iterable<TipoUsuario> iterator = dao.findAll();

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<TipoUsuario> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<TipoUsuario> tipoUsuarios = dao.findAll(paging);

        return tipoUsuarios.getContent();
    }
}
