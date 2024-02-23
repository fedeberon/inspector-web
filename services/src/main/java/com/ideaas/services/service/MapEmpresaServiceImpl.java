package com.ideaas.services.service;

import com.ideaas.services.dao.MapEmpresaDao;
import com.ideaas.services.domain.MapEmpresa;
import com.ideaas.services.domain.Usuario;
import com.ideaas.services.enums.TipoUsuarioEnum;
import com.ideaas.services.service.interfaces.MapEmpresaService;
import com.ideaas.services.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MapEmpresaServiceImpl implements MapEmpresaService {

    private MapEmpresaDao dao;

    @Autowired
    public MapEmpresaServiceImpl(MapEmpresaDao dao) {
        this.dao = dao;
    }

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public MapEmpresa get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public MapEmpresa save(MapEmpresa mapEmpresa) {
        return dao.save(mapEmpresa);
    }

    @Override
    public void saveAll(List<MapEmpresa> mapEmpresas) {
        this.dao.saveAll(mapEmpresas);
    }

    @Override
    public List<MapEmpresa> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<MapEmpresa> mapEmpresas = dao.findAll(paging);

        return mapEmpresas.getContent();
    }

    @Override
    public List<MapEmpresa> findAll() {
        Iterable<MapEmpresa> iterator = dao.findAll();

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<MapEmpresa> findAllByUsuariosContaining(Usuario usuario) {
        return this.dao.findAllByUsuariosContaining(usuario);
    }

    @Override
    public List<MapEmpresa> findByOrderByDescripcionAsc(){

        Usuario usuario = this.usuarioService.getUsuarioLogeado();
        Iterable<MapEmpresa> iterator = null;
        switch (TipoUsuarioEnum.tipoUsuarioOf(usuario.getTipoUsuario().getId())) {
            case ROLE_ADMINISTRADOR:
                iterator = dao.findByOrderByDescripcionAsc();
                break;
            case ROLE_ADMINISTRADOR_OOH:
                iterator = usuario.getEmpresas().stream().sorted(Comparator.comparing(MapEmpresa::getDescripcion)).collect(Collectors.toList());
                break;
            default:
                break;
        }
        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());

    }
}
