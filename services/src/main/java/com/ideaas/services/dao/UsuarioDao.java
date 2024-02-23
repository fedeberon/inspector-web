package com.ideaas.services.dao;

import com.ideaas.services.domain.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioDao extends PagingAndSortingRepository<Usuario, Long> {

    Usuario findByUsername(String username);

    Usuario findByEstadoAndUsernameIs(String estado ,String username);

    @Query("SELECT u FROM Usuario u WHERE u.eMail = :email")
    Usuario getByEmail(@Param("email") String email);

    @Query("SELECT u FROM Usuario u WHERE u.username = :username")
    Usuario getByUsername(@Param("username") String username);

    List<Usuario> findByEstadoNot(String estado);

    List<Usuario> findAllByTipoUsuario_Id(Long idTipoUsuario);
}
