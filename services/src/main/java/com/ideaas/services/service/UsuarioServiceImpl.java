package com.ideaas.services.service;

import com.ideaas.services.dao.UsuarioDao;
import com.ideaas.services.domain.MapEmpresa;
import com.ideaas.services.domain.UserDetailImpL;
import com.ideaas.services.domain.Usuario;
import com.ideaas.services.enums.TipoUsuarioEnum;
import com.ideaas.services.service.interfaces.AllowedStatusService;
import com.ideaas.services.service.interfaces.AllowedTipoUsuarioService;
import com.ideaas.services.service.interfaces.MapCompanyFilterIdsService;
import com.ideaas.services.service.interfaces.UsuarioService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioDao dao;

    private AllowedStatusService allowedStatusService;

    private AllowedTipoUsuarioService allowedTipoUsuarioService;


    @Value("${jwt.secret}")
    private String secret;

    public static final long JWT_TOKEN_VALIDITY = 10 * 365 * 24 * 60 * 60;

    @Autowired
    public UsuarioServiceImpl(UsuarioDao dao, AllowedStatusService allowedStatusService , AllowedTipoUsuarioService allowedTipoUsuarioService) {
        this.dao = dao;
        this.allowedStatusService = allowedStatusService;
        this.allowedTipoUsuarioService = allowedTipoUsuarioService;
    }

    @Override
    public Usuario get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public Usuario save(Usuario usuario) {
        return dao.save(usuario);
    }

    @Override
    public Usuario getByEmail(String email) {
        return dao.getByEmail(email);
    }

    @Override
    public Usuario getByUsername(String username) {
        return dao.getByUsername(username);
    }

    @Override
    public List<Usuario> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Usuario> usuarios = dao.findAll(paging);

        return usuarios.getContent();
    }

    @Override
    public List<Usuario> findAll() {
        Iterable<Usuario> iterator = dao.findAll();

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<Usuario> findByEstadoNot(String estado) {
        Iterable<Usuario> iterator = dao.findByEstadoNot(estado);

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<Usuario> findAllByTipoUsuario(Long idTipoUsuario) {
        Iterable<Usuario> iterator = dao.findAllByTipoUsuario_Id(idTipoUsuario);

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = dao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        boolean enabled = isEnabled(user);

        List<Long> idMapEmpresas = user.getEmpresas().stream().map(MapEmpresa::getId).collect(Collectors.toList());
        List<SimpleGrantedAuthority> authority = new ArrayList<SimpleGrantedAuthority>();
        authority.add(new SimpleGrantedAuthority(TipoUsuarioEnum.tipoUsuarioOf(user.getTipoUsuario().getId()).toString()));
        return new com.ideaas.services.domain.UserDetailImpL(user.getUsername(), user.getPassword(), user.getId(), enabled, true, true, true, idMapEmpresas, authority);
    }
    private boolean isEnabled(Usuario user){
        return allowedStatusService.existsByDescripcion(user.getEstado()) && allowedTipoUsuarioService.existsByTipoUsuario_Id(user.getTipoUsuario().getId());
    }

    @Override
    public Usuario getUsuarioLogeado() {
        return this.dao.findById(((UserDetailImpL) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdUser()).get();
    }

    @Override
    public Optional<List<Long>> getMapCompanyIdOfUserLoggedInIfIsOHHAdminTypeUser() {
        String stringRoleUser = ((UserDetailImpL) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAuthorities().toArray()[0].toString();
        if (TipoUsuarioEnum.tipoUsuarioOfString(stringRoleUser) == TipoUsuarioEnum.ROLE_ADMINISTRADOR_OOH){
            return Optional.of(((UserDetailImpL) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdMapEmpresas());
        }
        return Optional.empty();
    }

    @Override
    public TipoUsuarioEnum getTipoUsurioOfTheLoggedInUser() {
        return TipoUsuarioEnum.tipoUsuarioOfString(((UserDetailImpL) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAuthorities().toArray()[0].toString());
    }

    //#region validations
    @Override
    public Optional<Optional<Long>> filterIdBelongToUserLoggedIn(Long idToFilter, MapCompanyFilterIdsService mapCompanyFilterId) {
        List<Long> idsToFilter = new ArrayList<>();
        
        idsToFilter.add(idToFilter);
        
        Optional<List<Long>> filteredIds = filterIdsBelongToUserLoggedIn(idsToFilter, mapCompanyFilterId);

        if(!filteredIds.isPresent()) return Optional.empty();
        if(filteredIds.get().isEmpty()) return Optional.of(Optional.empty());
        
        return Optional.of(Optional.of(filteredIds.get().get(0)));
    }

    @Override
    public Optional<List<Long>> filterIdsBelongToUserLoggedIn(List<Long> idsToFilter, MapCompanyFilterIdsService mapCompanyFilterIds) {
        String stringRoleUser = ((UserDetailImpL) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAuthorities().toArray()[0].toString();
        List<Long> filteredIds = new ArrayList<>();
        switch (TipoUsuarioEnum.tipoUsuarioOfString(stringRoleUser)) {
            case ROLE_ADMINISTRADOR:
                filteredIds = null;
                break;
            case ROLE_ADMINISTRADOR_OOH:
                filteredIds = mapCompanyFilterIds.filterEntitiesIdsThaBelongToAMapCompanies(idsToFilter, this.getMapCompanyIdOfUserLoggedInIfIsOHHAdminTypeUser().get());
                break;
        }

        
        return Objects.nonNull(filteredIds) ? Optional.of(filteredIds) : Optional.empty();
    }
    //#endregion



    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }
}
