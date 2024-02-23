package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.UsuarioMenu;

import java.util.List;

public interface UsuarioMenuService {

    UsuarioMenu save(UsuarioMenu usuarioMenu);

    List<UsuarioMenu> saveAll(List<UsuarioMenu> usuarioMenus);

    List<UsuarioMenu> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<UsuarioMenu> findAll();

    List<UsuarioMenu> findByUsuarioMenuId_IdUsuario(Long idUsuario);
}