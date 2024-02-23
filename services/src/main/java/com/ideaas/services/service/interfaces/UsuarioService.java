package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.MapEmpresa;
import com.ideaas.services.domain.Usuario;
import com.ideaas.services.enums.TipoUsuarioEnum;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;
import java.util.Optional;

public interface UsuarioService extends UserDetailsService {

    Usuario get(Long id);

    Usuario save(Usuario usuario);

    Usuario getByEmail(String email);

    Usuario getByUsername (String username);

    List<Usuario> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<Usuario> findAll();

    List<Usuario> findByEstadoNot(String estado);

    List<Usuario> findAllByTipoUsuario(Long idTipoUsuario);

    Usuario getUsuarioLogeado();

    /**
     * This function takes as arguments the ids of the objects that belong to the {@link MapEmpresa MapCompany} group
     * of the logged in OOH Administrator user. If the logged in user is an administrator type, an empty optional is
     * returned, since the administrator user does not belong to any company and has CRUD permissions on all objects.
     * This method works similarly to {@link #filterIdBelongToUserLoggedIn}, but on a single id.
     *
     * @param idToFilter the ids to filter
     * @return the objects ids that belong to the user logged in
     */

    Optional<Optional<Long>> filterIdBelongToUserLoggedIn(Long idToFilter, MapCompanyFilterIdsService mapCompanyFilterId);

    /**
     * This function takes as arguments the ids of the objects that belong to the {@link MapEmpresa MapCompany} group
     * of the logged in OOH Administrator user. If the logged in user is an administrator type, an empty optional is
     * returned, since the administrator user does not belong to any company and has CRUD permissions on all objects.
     * This method works similarly to {@see filterIdsBelongToUserLoggedIn}, but on multiple ids.
     *
     * @param idsToFilter the ids to filter
     * @return the objects ids that belong to the user logged in
     */
    Optional<List<Long>> filterIdsBelongToUserLoggedIn(List<Long> idsToFilter, MapCompanyFilterIdsService mapCompanyFilterIds);

    /**
     * Return the ids of the {@link MapEmpresa MapCompanies} group of the logged in OOH Administrator user.
     *
     * @return an optional that have the {@link MapEmpresa MapCompanies} ids if user logged in is Admin OOH user type
     */
    Optional<List<Long>> getMapCompanyIdOfUserLoggedInIfIsOHHAdminTypeUser();

    /**
     * Return the logged in {@link com.ideaas.services.enums.TipoUsuarioEnum user type}.
     *
     * @return the logged in {@link com.ideaas.services.enums.TipoUsuarioEnum user type}
     */
    TipoUsuarioEnum getTipoUsurioOfTheLoggedInUser();

    String generateToken(UserDetails userDetails);
}
