package com.ideaas.services.service;

import com.ideaas.services.dao.UsuarioMenuDao;
import com.ideaas.services.domain.UsuarioMenu;
import com.ideaas.services.service.interfaces.UsuarioMenuService;
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
public class UsuarioMenuServiceImpl implements UsuarioMenuService {

    private UsuarioMenuDao dao;

    @Autowired
    public UsuarioMenuServiceImpl(UsuarioMenuDao dao) {
        this.dao = dao;
    }

    @Override
    public UsuarioMenu save(UsuarioMenu usuarioMenu) {
        return dao.save(usuarioMenu);
    }

    @Override
    public List<UsuarioMenu> saveAll(List<UsuarioMenu> usuarioMenus) {
       return (List<UsuarioMenu>) dao.saveAll(usuarioMenus);
    }

    @Override
    public List<UsuarioMenu> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<UsuarioMenu> usuarioMenus = dao.findAll(paging);

        return usuarioMenus.getContent();
    }

    @Override
    public List<UsuarioMenu> findAll() {
        Iterable<UsuarioMenu> iterator = dao.findAll();

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public  List<UsuarioMenu> findByUsuarioMenuId_IdUsuario(Long idUsuario){
        Iterable<UsuarioMenu> iterator = dao.findByUsuarioMenuId_IdUsuario(idUsuario);

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }
}
