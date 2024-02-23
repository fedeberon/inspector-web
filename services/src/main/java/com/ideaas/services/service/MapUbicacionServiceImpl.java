package com.ideaas.services.service;

import com.ideaas.services.bean.*;
import com.ideaas.services.dao.FilterDao;
import com.ideaas.services.dao.MapUbicacionDao;
import com.ideaas.services.domain.*;
import com.ideaas.services.enums.ReservationState;
import com.ideaas.services.enums.TipoUsuarioEnum;
import com.ideaas.services.exception.StockException;
import com.ideaas.services.request.MapUbicacionRequest;
import com.ideaas.services.service.interfaces.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class MapUbicacionServiceImpl implements MapUbicacionService{

    private MapUbicacionDao dao;

    private FilterDao filterDao;

    private FileStorageService fileStorageService;

    private MapEmpresaService mapEmpresaService;

    private MapElementoService mapElementoService;

    private MapFormatoService mapFormatoService;

    private MapMedioService mapMedioService;

    private MapProvinciaService mapProvinciaService;

    private MapLocalidadService mapLocalidadService;

    private MapUbicacionAlturaService mapUbicacionAlturaService;

    private MapUbicacionVisibilidadService mapUbicacionVisibilidadService;

    private UsuarioService usuarioService;

    private MapUbicaionParametroService mapUbicaionParametroService;

    private ParametroService parametroService;

    private GoogleMapsService googleMapsService;

    private StockService stockService;

    private MapReservationService mapReservacionService;

    private MapClienteService mapClienteService;

    @Autowired
    public MapUbicacionServiceImpl(MapUbicacionDao dao, FilterDao filterDao, FileStorageService fileStorageService, MapEmpresaService mapEmpresaService, MapElementoService mapElementoService, MapFormatoService mapFormatoService, MapMedioService mapMedioService, MapProvinciaService mapProvinciaService, MapLocalidadService mapLocalidadService, MapUbicacionAlturaService mapUbicacionAlturaService, MapUbicacionVisibilidadService mapUbicacionVisibilidadService, UsuarioService usuarioService, GoogleMapsService googleMapsService, MapUbicaionParametroService mapUbicaionParametroService, ParametroService parametroService, StockService stockService, MapReservationService mapReservacionService, MapClienteService mapClienteService) {
        this.dao = dao;
        this.filterDao = filterDao;
        this.fileStorageService = fileStorageService;
        this.mapEmpresaService = mapEmpresaService;
        this.mapElementoService = mapElementoService;
        this.mapFormatoService = mapFormatoService;
        this.mapMedioService = mapMedioService;
        this.mapProvinciaService = mapProvinciaService;
        this.mapLocalidadService = mapLocalidadService;
        this.mapUbicacionAlturaService = mapUbicacionAlturaService;
        this.mapUbicacionVisibilidadService = mapUbicacionVisibilidadService;
        this.usuarioService = usuarioService;
        this.mapUbicaionParametroService = mapUbicaionParametroService;
        this.parametroService = parametroService;
        this.googleMapsService = googleMapsService;
        this.stockService = stockService;
        this.mapReservacionService = mapReservacionService;
        this.mapClienteService = mapClienteService;
    }

    @Override
    public MapUbicacion get(Long id) {
        Usuario usuario = this.usuarioService.getUsuarioLogeado();
        MapUbicacion  ubicacion = ((usuario.isTipo(TipoUsuarioEnum.ROLE_ADMINISTRADOR_OOH)) ? dao.findByIdAndUsuarios(id, usuario) : dao.findById(id)).get();
        ubicacion.setImages(fileStorageService.readFiles(ubicacion.getMapEmpresa().getId() , ubicacion.getId()));

        return ubicacion;
    }

    @Override
    public MapUbicacion save(MapUbicacion mapUbicacion) throws StockException {

        this.stockService.updateStock(
                mapUbicacion.getId(),
                mapUbicacion.getMapEmpresa().getId(),
                mapUbicacion.getMapElemento().getId(),
                Objects.isNull(mapUbicacion.getId()),
                dao.locationHasChangedDeleted(mapUbicacion.getId(), mapUbicacion.getBajaLogica()),
                dao.companyHasChanged(mapUbicacion.getId(), mapUbicacion.getMapEmpresa().getId()),
                dao.elementHasChanged(mapUbicacion.getId(), mapUbicacion.getMapElemento().getId()),
                !mapUbicacion.getBajaLogica());

        if(mapUbicacion.getId() == null) {
            if(mapUbicacion.getParametros() !=  null) {
                List<MapUbicaionParametro> temp = mapUbicacion.getParametros();
                mapUbicacion.setParametros(null);
                mapUbicacion = dao.save(mapUbicacion);
                for (MapUbicaionParametro p : temp ) { p.setMapUbicacion(mapUbicacion); }
                mapUbicacion.setParametros(temp);
            }
        } else if(this.usuarioService.getTipoUsurioOfTheLoggedInUser() == TipoUsuarioEnum.ROLE_ADMINISTRADOR_OOH) {
            MapUbicacion mapLocation = this.get(mapUbicacion.getId());
            mapUbicacion.setCoeficiente(mapLocation.getCoeficiente());
            mapUbicacion.setVisibilidad(mapLocation.getVisibilidad());
            mapUbicacion.setMetrosContacto(mapLocation.getMetrosContacto());
            mapUbicacion.setMapUbicacionVisibilidad(mapLocation.getMapUbicacionVisibilidad());
            mapUbicacion.setMapBuses(mapLocation.getMapBuses());
            mapUbicacion.setMapUbicacionAltura(mapLocation.getMapUbicacionAltura());
        }

        if(mapUbicacion.getParametros() !=  null) {
            mapUbicacion.setParametros(mapUbicacion.getParametros().stream().map(u -> {
                u.setParametro(this.parametroService.get(u.getId().getIdParametro()));
                u.setMapUbicacion(this.dao.findById(u.getId().getIdUbicaion()).get());
                return u;
            }).collect(Collectors.toList()));

            this.mapUbicaionParametroService.saveAll(mapUbicacion.getParametros());
        }

        return dao.save(mapUbicacion);
    }

    @Override
    public Optional<MapUbicacion> findById(Long id) {
        Usuario usuario = this.usuarioService.getUsuarioLogeado();
        return ((usuario.isTipo(TipoUsuarioEnum.ROLE_ADMINISTRADOR_OOH)) ? dao.findByIdAndUsuarios(id, usuario) : dao.findById(id));
    }

    @Override
    public List<MapUbicacion> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<MapUbicacion> mapUbicaciones = dao.findAll(paging);
        mapUbicaciones.forEach(mapUbicacion -> mapUbicacion.setImages(fileStorageService.readFiles(mapUbicacion.getMapEmpresa().getId() , mapUbicacion.getId())));

        return mapUbicaciones.getContent();
    }

    @Override
    public List<MapUbicacion> findAll(MapUbicacionRequest mapUbicacionRequest) {
        if(this.usuarioService.getTipoUsurioOfTheLoggedInUser() == TipoUsuarioEnum.ROLE_ADMINISTRADOR_OOH) {
            mapUbicacionRequest.setIsAdminUserOOH(true);
            mapUbicacionRequest.setUserMapCompanyIds(this.usuarioService.getMapCompanyIdOfUserLoggedInIfIsOHHAdminTypeUser().get());
        } else {
            List<Long> userMapCompanies = new ArrayList<>(); userMapCompanies.add(-1l);
            mapUbicacionRequest.setIsAdminUserOOH(false);
            mapUbicacionRequest.setUserMapCompanyIds(userMapCompanies);
        }
        return filterDao.find(mapUbicacionRequest);
    }

    @Override
    public List<MapUbicacion> saveList(MapUbicacionRequest request) throws StockException {
        List<MapUbicacion> results = filterDao.find(request);

        if(Objects.nonNull(request.getIdEmpresa()) && !request.getIdEmpresa().equals(-1l)){
            MapEmpresa empresa = mapEmpresaService.get(request.getIdEmpresa());
            results.forEach(mapUbicacion -> mapUbicacion.setMapEmpresa(empresa));
        }

        if(Objects.nonNull(request.getIdElemento()) && !request.getIdElemento().equals(-1l)){
            MapElemento elemento = mapElementoService.get(request.getIdElemento());
            results.forEach(mapUbicacion -> mapUbicacion.setMapElemento(elemento));
        }

        if(Objects.nonNull(request.getIdFormato()) && !request.getIdFormato().equals(-1l)){
            MapFormato formato = mapFormatoService.get(request.getIdFormato());
            results.forEach(mapUbicacion -> mapUbicacion.setMapFormato(formato));
        }

        if(Objects.nonNull(request.getIdMedio()) && !request.getIdMedio().equals(-1l)){
            MapMedio medio = mapMedioService.get(request.getIdMedio());
            results.forEach(mapUbicacion -> mapUbicacion.setMapMedio(medio));
        }

        if(Objects.nonNull(request.getMapProvincia()) && !request.getIdProvincia().equals(-1l)){
            MapProvincia provincia = mapProvinciaService.get(request.getIdProvincia());
            results.forEach(mapUbicacion -> mapUbicacion.setMapProvincia(provincia));
        }

        if(Objects.nonNull(request.getIdLocalidad()) && !request.getIdLocalidad().equals(-1l)){
            MapLocalidad localidad = mapLocalidadService.get(request.getIdLocalidad());
            results.forEach(mapUbicacion -> mapUbicacion.setMapLocalidad(localidad));
        }

        if(Objects.nonNull(request.getIdAltura()) && !request.getIdAltura().equals(-1l)){
            MapUbicacionAltura altura = mapUbicacionAlturaService.get(request.getIdAltura());
            results.forEach(mapUbicacion -> mapUbicacion.setMapUbicacionAltura(altura));
        }

        if(Objects.nonNull(request.getIdVisibilidad()) && !request.getIdVisibilidad().equals(-1l)){
            MapUbicacionVisibilidad visibilidad = mapUbicacionVisibilidadService.get(request.getIdVisibilidad());
            results.forEach(mapUbicacion -> mapUbicacion.setMapUbicacionVisibilidad(visibilidad));
        }

        if(Objects.nonNull(request.getBajaLogicaRequest()) && request.getBajaLogicaRequest() instanceof Boolean){
            results.forEach(mapUbicacion -> mapUbicacion.setBajaLogica(request.getBajaLogicaRequest()));
        }

        if(Objects.nonNull(request.getMetrosContactoRequest()) && request.getMetrosContactoRequest() instanceof Long){
            results.forEach(mapUbicacion -> mapUbicacion.setMetrosContacto(request.getMetrosContactoRequest()));
        }

        if(Objects.nonNull(request.getCoeficienteRequest()) && request.getCoeficienteRequest() instanceof BigDecimal){
            results.forEach(mapUbicacion -> mapUbicacion.setCoeficiente(request.getCoeficienteRequest()));
        }


        Map<Long, Map<String, Long>> locations = new HashMap<>();

        for ( MapUbicacion mapUbicacion : results) {
            Map<String, Long> locationsAttributes = new HashMap<>();
            locationsAttributes.put("deleted", mapUbicacion.getBajaLogica() ? 1L : 0L);
            locationsAttributes.put("companyId", mapUbicacion.getMapEmpresa().getId());
            locationsAttributes.put("elementId", mapUbicacion.getMapElemento().getId());
            locationsAttributes.put("changedDeleted",
                    dao.locationHasChangedDeleted(mapUbicacion.getId(),mapUbicacion.getBajaLogica()) ? 1L : 0L
            );
            locationsAttributes.put("changedCompany",
                    dao.companyHasChanged(mapUbicacion.getId(), mapUbicacion.getMapEmpresa().getId()) ? 1L : 0L
            );
            locationsAttributes.put("changedElement",
                    dao.elementHasChanged(mapUbicacion.getId(), mapUbicacion.getMapElemento().getId()) ? 1L : 0L
            );
            locations.put(mapUbicacion.getId(), locationsAttributes);
        }

        for ( MapUbicacion mapUbicacion : results) {
            this.stockService.updateStock(
                    mapUbicacion.getId(),
                    locations.get(mapUbicacion.getId()).get("companyId"),
                    locations.get(mapUbicacion.getId()).get("elementId"),
                    Objects.isNull(mapUbicacion.getId()),
                    locations.get(mapUbicacion.getId()).get("changedDeleted").equals(1L),
                    locations.get(mapUbicacion.getId()).get("changedCompany").equals(1L),
                    locations.get(mapUbicacion.getId()).get("changedElement").equals(1L),
                    !(locations.get(mapUbicacion.getId()).get("deleted").equals(1L)));
        }

        HashMap<Long, String> filteredParametros = request.getFilteredParametros();
        if(Objects.nonNull(filteredParametros) && !filteredParametros.isEmpty()) {

            List<MapUbicaionParametro> parametros = new ArrayList<>();

            for (Long key : filteredParametros.keySet()) {
                MapUbicaionParametroPk id = new MapUbicaionParametroPk();
                MapUbicaionParametro mapUbicaionParametro = new MapUbicaionParametro();
                Parametro p = new Parametro();

                p.setIdParametro(key);
                mapUbicaionParametro.setId(id);
                mapUbicaionParametro.setParametro(p);
                mapUbicaionParametro.setDescripcion(filteredParametros.get(key));
                parametros.add(mapUbicaionParametro);

            }

            for (MapUbicacion u : results) {
                parametros.forEach(p -> p.setMapUbicacion(u));
                if(!u.getParametros().isEmpty()) {
                    for (MapUbicaionParametro p : parametros) {
                        if (u.getParametros().contains(p)) {
                            for (MapUbicaionParametro mup : u.getParametros()) {
                                if (mup.equals(p)) {
                                    mup.setDescripcion(p.getDescripcion());
                                }
                            }
                        } else {
                            u.getParametros().add(p);
                        }
                    }
                } else {
                    u.setParametros(parametros);
                }
                this.mapUbicaionParametroService.saveAll(u.getParametros());
            }

        }

        dao.saveAll(results);

        return results;
    }

    public void saveLatLong(MapUbicacionRequest request) {
        MapUbicacion ubicacion = this.get(request.getId());
        String address =  googleMapsService.locate(request.getLatitud(), request.getLongitud());
        ubicacion.setLatitud(request.getLatitud());
        ubicacion.setLongitud(request.getLongitud());
        save(ubicacion);
    }

    public void savePolygon(MapUbicacionRequest request) {
        MapUbicacion ubicacion = this.get(request.getId());
        ubicacion.setPolygonLatLong(request.getPolygonLatLong());
        save(ubicacion);
    }

    @Override
    public MapUbicacion deletePolygonLatLong(Long idUbicacion) {
        MapUbicacion ubicacion = dao.findById(idUbicacion).orElse(null);
        if(ubicacion != null){
            ubicacion.setPolygonLatLong(null);
            save(ubicacion);
        }
        return ubicacion;
    }

    @Override
    public List<MyObject> findAllByIdIn(List<Long> idMapUbicacionesListSelected) {
        return this.dao.findAllByIdUbicaciones(idMapUbicacionesListSelected);
    }

    //#region MapPlanning

    /**
     * Select all locations that meet the search filters. This method, for efficiency reasons,
     * first selects the locations by instantiating them as DTOs, and assigns them their
     * parameters and reservations.
     *
     * @param mapUbicacionRequest the search filter
     * @return all locations that meet the search filters
     */
    @Override
    public List<MapPlanningDTO> findAllMapPlanningDTOsByLocationsIDs(MapUbicacionRequest mapUbicacionRequest) {
        // 1. Security stuff:
        if(this.usuarioService.getTipoUsurioOfTheLoggedInUser() == TipoUsuarioEnum.ROLE_ADMINISTRADOR_OOH) {
            mapUbicacionRequest.setIsAdminUserOOH(true);
            mapUbicacionRequest.setUserMapCompanyIds(this.usuarioService.getMapCompanyIdOfUserLoggedInIfIsOHHAdminTypeUser().get());
        } else {
            List<Long> userMapCompanies = new ArrayList<>(); userMapCompanies.add(-1l);
            mapUbicacionRequest.setIsAdminUserOOH(false);
            mapUbicacionRequest.setUserMapCompanyIds(userMapCompanies);
        }
        // 2. Get locations IDs and map:
        List<Long> mapLocations = this.filterDao.find(mapUbicacionRequest).stream().map(MapUbicacion::getId).collect(Collectors.toList());
        if(mapLocations.isEmpty()) return new ArrayList<>();
        // 3. and convert them into DTO:
        List<MapPlanningDTO> planningDTOs = this.dao.findAllMapPlanningDTOsByIDs(mapLocations);
        // 4. convert it to a hashmap to be able to perform the next steps more efficiently
        HashMap<Long, MapPlanningDTO> planningDTOsWithHash = planningDTOs.stream().collect(Collectors.toMap(MapPlanningDTO::getId, (dto) -> dto, (prev, next) -> next, HashMap::new));
        // 5. Assign parameters to your location
        this.mapUbicaionParametroService.findAllByLocationsIDs(mapLocations).forEach( parameter ->
            planningDTOsWithHash.get(parameter.getMapUbicacion().getId()).getParameters().add(parameter)
        );
        // 6. Assign reservations to your location
        this.mapReservacionService.findAllByLocationsIDs(mapLocations).stream()
            .filter( reservation -> reservation.getReservationState() != ReservationState.CANCELLED  )
            .forEach(reservation -> {
                MapReservationPerDayDTO reservationPerDayDTO = new MapReservationPerDayDTO(reservation.getMapCampaign().getMapClient().getNombre(), reservation.getMapCampaign().getName(), reservation.getReservationState());
                MapPlanningDTO planningDTO = planningDTOsWithHash.get(reservation.getMapUbicacion().getId());

                Stream
                    .iterate(reservation.getStartDate(), date -> date.plusDays(1))
                    .limit(ChronoUnit.DAYS.between(reservation.getStartDate(), reservation.getFinishDate()) + 1)
                    .map(date -> date.format(DateTimeFormatter.ofPattern("dd/MM/uuuu")))
                    .forEach(date -> {
                        if (!planningDTO.getReservationsPerDay().containsKey(date)) {
                            planningDTO.getReservationsPerDay().put(date, new ArrayList<>());
                        }
                        planningDTO.getReservationsPerDay().get(date).add(reservationPerDayDTO);
                    });
            });
        return planningDTOs;
    }

    @Override
    public int countAllInactiveLocationsOfStock(Long idElemento, Long empresaId) {
        return this.dao.countAllInactiveLocationsOfStock(idElemento, empresaId);
    }

    @Override
    public int countAllActiveLocations(Long idElemento) {
        Optional<List<Long>> idEmpresas = this.usuarioService.getMapCompanyIdOfUserLoggedInIfIsOHHAdminTypeUser();
        if(idEmpresas.isPresent()){
            return this.dao.countAllActiveLocations(idElemento, idEmpresas.get());
        }
        return 0;
    }
    //#endregion

    //#region validations

    @Override
    public List<Long> filterEntitiesIdsThaBelongToAMapCompanies(List<Long> idsToFilter, List<Long> mapCompanyIds) {
        return this.dao.filterEntitiesIdsThaBelongToAMapCompanies(idsToFilter, mapCompanyIds);
    }

    //#endregion
}
