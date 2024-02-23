package com.ideaas.services.service;

import com.ideaas.services.bean.*;
import com.ideaas.services.dao.MapCampaignDao;
import com.ideaas.services.dao.MapReservacionDao;
import com.ideaas.services.domain.*;
import com.ideaas.services.enums.ReservationState;
import com.ideaas.services.exception.ReservationException;
import com.ideaas.services.request.CreateMapReservacionRequest;
import com.ideaas.services.request.ExportCircuitRequest;
import com.ideaas.services.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MapReservacionServiceImpl implements MapReservationService {

    //#region properties and constructor
    @Autowired
    private MapReservacionDao dao;

    @Autowired
    private MapCampaignDao mapCampaignDao;

    @Autowired
    private MapCampaignsService mapCampaignsService;

    @Autowired
    private MapClienteService mapClienteService;

    @Autowired
    private MapUbicaionParametroService mapUbicaionParametroService;

    @Autowired
    private UsuarioService usuarioService;

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${spring.api.files.url}")
    private String urlFileServer;
    //#endregion

    //#region commons methods
    @Override
    public MapReservacion get(Long id) {
        Optional<Optional<Long>> idValidated = this.usuarioService.filterIdBelongToUserLoggedIn(id, this);
        if(idValidated.isPresent()) if(!idValidated.get().isPresent()) return null;
        return this.dao.findById(id).get();
    }

    @Override
    public MapReservacion save(MapReservacion mapReservacion) {
        Optional<Optional<Long>> idValidated = this.usuarioService.filterIdBelongToUserLoggedIn(mapReservacion.getId(), this);
        if(idValidated.isPresent()) if(!idValidated.get().isPresent()) return null;
        return this.dao.save(mapReservacion);
    }

    @Override
    public List<MapReservacion> findAll() {
        return this.dao.findAll();
    }

    @Override
    public void delete(MapReservacion mapReservacion) {
        Optional<Optional<Long>> idValidated = this.usuarioService.filterIdBelongToUserLoggedIn(mapReservacion.getId(), this);
        if(idValidated.isPresent()) if(!idValidated.get().isPresent()) return;
        this.dao.delete(mapReservacion);
    }

    @Override
    public void deleteAllByIds(List<Long> ids){
        ids.stream().forEach((id) -> this.deleteReservationById(id, null, null));
    }
    //#endregion

    //#region planning
    @Override
    public boolean saveMapReservations(CreateMapReservacionRequest createMapReservacionRequest) {
        if(!createMapReservacionRequest.getIsNewCampaign()) {
            Optional<Optional<Long>> idValidated = this.usuarioService.filterIdBelongToUserLoggedIn(createMapReservacionRequest.getCampaignId(), this.mapCampaignsService);
            if(idValidated.isPresent()) if(!idValidated.get().isPresent()) return false;
        }
        Optional<Optional<Long>> idValidated = this.usuarioService.filterIdBelongToUserLoggedIn(createMapReservacionRequest.getMapClientId(), this.mapClienteService);
        if(idValidated.isPresent()) if(!idValidated.get().isPresent()) return false;

        List<Long> locationIds = createMapReservacionRequest.getMapLocationsIdsList();
        List<ReservationState> reservationStates = new ArrayList<>();
        reservationStates.add(ReservationState.CONFIRMED); reservationStates.add(ReservationState.NOT_CONFIRMED);
        ReservationState reservationState = createMapReservacionRequest.getReservationState();
        Long campaignId = (createMapReservacionRequest.getIsNewCampaign()) ? -1L : createMapReservacionRequest.getCampaignId();
        LocalDate startDate  = createMapReservacionRequest.getStartDate();
        LocalDate finishDate = createMapReservacionRequest.getFinishDate();

        // Verify that they do not understand each other by adding a reservation with the same dates to the same campaign
        if(!createMapReservacionRequest.getIsNewCampaign()) {
            List<Long> reservationsIds = new ArrayList<>(); reservationsIds.add(-1l);
            if(this.dao.selectCampaignByIdWithReservationsBetweenDates(reservationsIds, locationIds, campaignId, startDate, finishDate).isPresent()) {
                throw new ReservationException(ReservationException.Code.OVERLAPPED_CAMPAIGN_RESERVATION);
            }
        }

        // Verify that there are no confirmed reservations with the same dates in the same location
        if(this.dao.existsReservationsInGivenLocationsBetweenDatesWithState(locationIds, startDate, finishDate, ReservationState.CONFIRMED)) {
            throw new ReservationException(ReservationException.Code.OVERLAPPED_CONFIRMED_RESERVATION);
        }

        // Getting the campaign
        MapCampaign campaign = createMapReservacionRequest.getIsNewCampaign() ?
                this.mapCampaignsService.save(
                        new MapCampaign(
                                createMapReservacionRequest.getCampaignName(),
                                entityManager.getReference(MapCliente.class, createMapReservacionRequest.getMapClientId()))) :
                entityManager.getReference(MapCampaign.class, createMapReservacionRequest.getCampaignId());

        // Creating the reservations
        List<MapReservacion> saveReservations = this.dao.saveAll(locationIds.stream().map(id -> entityManager.getReference(MapUbicacion.class, id)).collect(Collectors.toList()).stream()
                .map(location ->
                        new MapReservacion(
                                null,
                                campaign,
                                location,
                                startDate,
                                finishDate,
                                reservationState,
                                createMapReservacionRequest.getOrdenNumber())).collect(Collectors.toList()));

        // Update status of overlapping reservations to canceled
        if(reservationState == ReservationState.CONFIRMED) {
            this.dao.updateAllReservationOverlappedWithGivenIfIsPosible(saveReservations.stream().map(MapReservacion::getId).collect(Collectors.toList()), locationIds,ReservationState.CANCELLED.getCode());
        }
        
        return true;
    }
    //#endregion

    //#region campaign
    public void deleteReservationsByCampaignId(Long campaignID) {
        List<MapReservacion> reservations = this.dao.selectAllReservationByCampaignId(campaignID);
        List<Long> reservationsIds = reservations.stream().map(MapReservacion::getId).collect(Collectors.toList());
        List<Long> locationsIds = reservations.stream().map(MapReservacion::getMapUbicacion).map(MapUbicacion::getId).collect(Collectors.toList());
        this.dao.updateAllReservationsNotOverlappedWithGivenIfIsPosible(reservationsIds, locationsIds, ReservationState.NOT_CONFIRMED.getCode());
        this.dao.deleteAll(reservations);
    }
    //#endregion

    //#region circuits
    @Override
    public List<MapCircuitDTO> findAllCircuitsByCampaignId(Long id) {
        List<Long> mapCompanyIds = this.usuarioService.getMapCompanyIdOfUserLoggedInIfIsOHHAdminTypeUser().orElse(new ArrayList<>(Arrays.asList(-1l)));
        List<MapCircuitDTO> circuitDTOList = this.dao.findAllCircuitsByCampaignId(id, mapCompanyIds.stream().map(Long::intValue).collect(Collectors.toList()));
        return circuitDTOList;
    }

    private void doesCircuitDTOListHaveCanceledReservations(List<MapCircuitDTO> circuitDTOList){
        for (MapCircuitDTO circuitDTO : circuitDTOList){
            circuitDTO.setHasCanceledReservations(this.dao.doesCircuitHaveCanceledReservations(circuitDTO));
        }
    }

    @Override
    public MapCampaign clone(MapCampaignDTO newMapCampaign) {
        Optional<Optional<Long>> idValidated = this.usuarioService.filterIdBelongToUserLoggedIn(newMapCampaign.getCampaignId(), this.mapCampaignsService);
        if(idValidated.isPresent()) if(!idValidated.get().isPresent()) return null;

        MapCampaignDTO oldMapCampaign = this.mapCampaignsService.getCampaignDTOByCampaignId(newMapCampaign.getCampaignId());

        boolean datesChanged = !oldMapCampaign.getStartDate().equals(newMapCampaign.getStartDate());

        Long displaceDate  = !datesChanged ? 0L : ChronoUnit.DAYS.between(oldMapCampaign.getStartDate(),  newMapCampaign.getStartDate());

        List<MapReservacion> mapReservations = this.dao.selectAllReservationByCampaignId(oldMapCampaign.getCampaignId());

        MapCampaign clonedCampaign = new MapCampaign();
        clonedCampaign.setName(newMapCampaign.getCampaign());
        clonedCampaign.setMapClient(this.mapClienteService.get(newMapCampaign.getClientId()));
        this.mapCampaignsService.save(clonedCampaign);

        if(!mapReservations.isEmpty()) {
            if(!this.dao.selectOverlappedCampaignsReservationWithGivenReservationsAndState(
                    mapReservations.stream().map(MapReservacion::getId).collect(Collectors.toList()),
                    ReservationState.CONFIRMED,
                    displaceDate,
                    displaceDate,
                    true).isEmpty()) {
                return null;
            }
            this.dao.saveAll(
                mapReservations.stream().map( r -> {
                    this.entityManager.detach(r);
                    r.setId(null);
                    r.setReservationState(ReservationState.NOT_CONFIRMED);
                    r.setStartDate (r.getStartDate() .plusDays(displaceDate));
                    r.setFinishDate(r.getFinishDate().plusDays(displaceDate));
                    r.setMapCampaign(clonedCampaign);
                    r.setExhibir(false);
                    return r;
                }).collect(Collectors.toList())
            );
        }
        return clonedCampaign;
    }

    @Override
    public MapCampaign saveCampaign(MapCampaignDTO newMapCampaign) {
        Optional<Optional<Long>> idValidated = this.usuarioService.filterIdBelongToUserLoggedIn(newMapCampaign.getCampaignId(), this.mapCampaignsService);
        if(idValidated.isPresent()) if(!idValidated.get().isPresent()) return null;
        idValidated = this.usuarioService.filterIdBelongToUserLoggedIn(newMapCampaign.getCampaignId(), this.mapCampaignsService);
        if(idValidated.isPresent()) if(!idValidated.get().isPresent()) return null;

        MapCampaignDTO oldMapCampaign = this.mapCampaignsService.getCampaignDTOByCampaignId(newMapCampaign.getCampaignId());
        List<MapReservacion> reservations = this.dao.selectAllReservationByCampaignId(newMapCampaign.getCampaignId());

        this.safeUpdateLocationsReservationsStateFilteringByDatesAnStates(
                reservations,
                null,
                oldMapCampaign.getStartDate(),
                oldMapCampaign.getFinishDate(),
                newMapCampaign.getStartDate(),
                newMapCampaign.getFinishDate(),
                newMapCampaign.getReservationState(),
                0L);

        MapCampaign mapCampaign = this.mapCampaignsService.get(newMapCampaign.getCampaignId());
        mapCampaign.setName(newMapCampaign.getCampaign());
        mapCampaign.setMapClient(this.mapClienteService.get(newMapCampaign.getClientId()));

        return this.mapCampaignsService.save(mapCampaign);
    }

    @Override
    public MapCircuitDTO saveCircuit(MapCircuitDTO circuitDTO) {
        Optional<Optional<Long>> idValidated = this.usuarioService.filterIdBelongToUserLoggedIn(circuitDTO.getCampaignId(), this.mapCampaignsService);
        if(idValidated.isPresent()) if(!idValidated.get().isPresent()) return null;

        List<MapReservacion> reservations = this.dao.selectAllReservationByCircuit(circuitDTO);
        MapReservacion reservation = reservations.get(0);

        this.safeUpdateLocationsReservationsStateFilteringByDatesAnStates(
                reservations,
                circuitDTO.getCampaignId(),
                circuitDTO.getStartDate(),
                circuitDTO.getFinishDate(),
                circuitDTO.getUpdatedStartDate(),
                circuitDTO.getUpdatedFinishDate(),
                circuitDTO.getReservationState(),
                -1L);

        circuitDTO.setStartDate(circuitDTO.getUpdatedStartDate());
        circuitDTO.setFinishDate(circuitDTO.getUpdatedFinishDate());

        return circuitDTO;
    }

    @Override
    public void deleteCircuit(MapCircuitDTO circuitDTO) {
        Optional<Optional<Long>> idValidated = this.usuarioService.filterIdBelongToUserLoggedIn(circuitDTO.getCampaignId(), this.mapCampaignsService);
        if(idValidated.isPresent()) if(!idValidated.get().isPresent()) return;
        List<MapReservacion> reservations = this.dao.selectAllReservationByCircuit(circuitDTO);
        List<Long> reservationsIds = reservations.stream().map(MapReservacion::getId).collect(Collectors.toList());
        List<Long> locationsIds = reservations.stream().map(MapReservacion::getMapUbicacion).map(MapUbicacion::getId).collect(Collectors.toList());

        this.dao.updateAllReservationsNotOverlappedWithGivenIfIsPosible(reservationsIds, locationsIds, ReservationState.NOT_CONFIRMED.getCode());
        this.dao.deleteAll(reservations);
    }
    //#endregion

    //#region reservation
    @Override
    public List<MapReservacion> findAllByLocationsIDs(List<Long> mapLocations) {
        return this.dao.findAllByLocationsIDs(mapLocations);
    }
    //#endregion

    //#region dto reservations
    public List<MapReservationDTO> findAllReservationsDTOByCircuit(MapCircuitDTO circuitDTO) {
        List<Long> mapCompanyIds = this.usuarioService.getMapCompanyIdOfUserLoggedInIfIsOHHAdminTypeUser().orElse(new ArrayList<>(Arrays.asList(-1l)));
        return this.dao.selectAllReservationDTOByCircuit(circuitDTO, mapCompanyIds.stream().map(Long::intValue).collect(Collectors.toList()));
    }

    @Override
    public MapReservationDTO getReservation(Long id) {
        Optional<Optional<Long>> idValidated = this.usuarioService.filterIdBelongToUserLoggedIn(id, this);
        if(idValidated.isPresent()) if(!idValidated.get().isPresent()) return null;
        return this.dao.findReservationDTOById(id);
    }

    @Override
    public MapReservationDTO saveReservation(MapReservationDTO mapReservationDTO) {
        Optional<Optional<Long>> idValidated = this.usuarioService.filterIdBelongToUserLoggedIn(mapReservationDTO.getReservationId(), this);
        if(idValidated.isPresent()) if(!idValidated.get().isPresent()) return null;
        MapReservacion reservation = this.dao.findById(mapReservationDTO.getReservationId()).get();
        List<MapReservacion> reservations = new ArrayList<>(); reservations.add(reservation);

        this.safeUpdateLocationsReservationsStateFilteringByDatesAnStates(
                reservations,
                reservation.getMapCampaign().getId(),
                reservation.getStartDate(),
                reservation.getFinishDate(),
                mapReservationDTO.getStartDate(),
                mapReservationDTO.getFinishDate(),
                mapReservationDTO.getReservationState(),
                mapReservationDTO.getOrdenNumber());

        return this.dao.findReservationDTOById(mapReservationDTO.getReservationId());
    }

    @Override
    public void deleteReservationById(Long id, LocalDate startDate, LocalDate finishDate) {
        Optional<Optional<Long>> idValidated = this.usuarioService.filterIdBelongToUserLoggedIn(id, this);
        if(idValidated.isPresent()) if(!idValidated.get().isPresent()) return;

        System.out.println(id);
        Optional<MapReservacion> optional = this.dao.findById(id);
        if(!optional.isPresent()){
            return;
        }
        MapReservacion reservation = optional.get();
        List<MapReservacion> reservations = new ArrayList<>(); reservations.add(reservation);
        List<Long> reservationsIds = reservations.stream().map(MapReservacion::getId).collect(Collectors.toList());
        List<Long> locationsIds = reservations.stream().map(MapReservacion::getMapUbicacion).map(MapUbicacion::getId).collect(Collectors.toList());

        this.dao.updateAllReservationsNotOverlappedWithGivenIfIsPosible(reservationsIds, locationsIds, ReservationState.NOT_CONFIRMED.getCode());
        this.dao.deleteAll(reservations);
    }

    @Override
    public byte[] exportCircuitsToExcel(ExportCircuitRequest exportCircuitRequest) throws IOException {
        List<MapReservationDTO> mapReservationDTOS =  exportCircuitRequest.getCircuitDTOS().stream()
                .map( circuit ->
                        this.findAllReservationsDTOByCircuit(circuit))
                .flatMap(reservationsList ->
                        reservationsList.stream())
                .map( dto -> {    dto.setAmount(Objects.isNull(dto.getAmount())?1:dto.getAmount());  return dto; } ).collect(Collectors.toList());;

        List<MapUbicaionParametro> parametters = new ArrayList<>();
        if(!exportCircuitRequest.getParameters().isEmpty()&&!mapReservationDTOS.isEmpty()){
            parametters = mapUbicaionParametroService.findAllByLocationsIDs(mapReservationDTOS.stream().map(MapReservationDTO::getId).collect(Collectors.toList()));
        }
        return new ParseCircuitsExportRequest(exportCircuitRequest, mapReservationDTOS, parametters, urlFileServer).buildExcel();
    }

    @Override
    public byte[] exportCircuitsToPDF(ExportCircuitRequest exportCircuitRequest) throws IOException, ParserConfigurationException, InterruptedException, TransformerException {
        List<MapReservationDTO> mapReservationDTOS =  exportCircuitRequest.getCircuitDTOS().stream()
                .map( circuit ->
                        this.findAllReservationsDTOByCircuit(circuit))
                .flatMap(reservationsList ->
                        reservationsList.stream())
                .map( dto -> {    dto.setAmount(Objects.isNull(dto.getAmount())?1:dto.getAmount());  return dto; } ).collect(Collectors.toList());

        List<MapUbicaionParametro> parametters = new ArrayList<>();
        if(!exportCircuitRequest.getParameters().isEmpty()&&!mapReservationDTOS.isEmpty()){
            parametters = mapUbicaionParametroService.findAllByLocationsIDs(mapReservationDTOS.stream().map(MapReservationDTO::getId).collect(Collectors.toList()));
        }

        return new ParseCircuitsExportRequest(exportCircuitRequest, mapReservationDTOS, parametters, urlFileServer).buildPDF();
    }
    //#endregion

    //#region validations
    public boolean safeUpdateLocationsReservationsStateFilteringByDatesAnStates(List<MapReservacion> reservations, Long campaignId, LocalDate oldStartDate, LocalDate oldFinishDate, LocalDate newStartDate, LocalDate newFinishDate, ReservationState reservationState, Long ordenNumber) {
        if(reservations.isEmpty()||reservationState == ReservationState.NOT_CHANGE) return true;
        List<Long> reservationsIds = reservations.stream().map(MapReservacion::getId).collect(Collectors.toList());
        List<Long> locationsIds = reservations.stream().map(MapReservacion::getMapUbicacion).map(MapUbicacion::getId).collect(Collectors.toList());
        boolean datesChanged = Objects.nonNull(oldStartDate)&&Objects.nonNull(oldFinishDate)&&
                Objects.nonNull(newStartDate)&&Objects.nonNull(newFinishDate)&&(!oldStartDate.equals(newStartDate)||!oldFinishDate.equals(newFinishDate));
        Long  startDayDiffDays = 0L, finishDayDiffDays = 0L;
        if(datesChanged) {
            startDayDiffDays = ChronoUnit.DAYS.between(oldStartDate,  newStartDate);
            finishDayDiffDays = ChronoUnit.DAYS.between(oldFinishDate, newFinishDate);
        }

        if(this.dao.existsOverlappedReservationWithGivenReservationAndStateDisplacedByDays(reservationsIds, ReservationState.CONFIRMED, startDayDiffDays, finishDayDiffDays)) {
            return false;
        }

        if(Objects.nonNull(campaignId)) {
            if(this.dao.selectCampaignByIdWithReservationsBetweenDates(reservationsIds, locationsIds, campaignId, newStartDate, newFinishDate).isPresent()) {
                return false;
            }
        }

        if(datesChanged) {
            this.dao.updateAllReservationsNotOverlappedWithGivenIfIsPosible(reservationsIds, locationsIds, ReservationState.NOT_CONFIRMED.getCode());
        }

        reservations.forEach( reservation -> {
            if(datesChanged) {
                reservation.setStartDate(newStartDate);
                reservation.setFinishDate(newFinishDate);
            }
            reservation.setReservationState(reservationState);
            //reservation.setOrdenNumber(ordenNumber);
        });
        this.dao.saveAll(reservations);

        if(reservationState== ReservationState.CONFIRMED) {
            this.dao.updateAllReservationOverlappedWithGivenIfIsPosible(reservationsIds, locationsIds, ReservationState.CANCELLED.getCode());
        } else if(reservationState == ReservationState.NOT_CONFIRMED) {
            this.dao.updateAllReservationOverlappedWithGivenIfIsPosible(reservationsIds, locationsIds, ReservationState.NOT_CONFIRMED.getCode());
        }

        return true;
    }

    @Override
    public VerifyReservationsCanUpdateResponse selectCampaignsWithReservationsThatOverlapWithGivenReservationsAndStatus(MapCampaignDTO newCampaign) {
        MapCampaignDTO oldCampaign = this.mapCampaignsService.getCampaignDTOByCampaignId(newCampaign.getCampaignId());

        if(newCampaign.getIsCloning()) {
            newCampaign.setFinishDate(oldCampaign.getFinishDate().plusDays(
                ChronoUnit.DAYS.between(oldCampaign.getStartDate(),  newCampaign.getStartDate())
            ));
        } else {
            newCampaign.setStartDate(oldCampaign.getStartDate());
            newCampaign.setFinishDate(oldCampaign.getFinishDate());
        }

        List<MapReservacion> reservations = this.dao.selectAllReservationByCampaignId(newCampaign.getCampaignId());
        List<Long> reservationsIds = reservations.stream().map(MapReservacion::getId).collect(Collectors.toList());
        List<Long> locationsIds = reservations.stream().map(MapReservacion::getMapUbicacion).map(MapUbicacion::getId).collect(Collectors.toList());

        return selectCampaignsWithReservationsThatOverlapWithGivenReservationsAndStatus(
                reservationsIds,
                locationsIds,
                null,
                oldCampaign.getStartDate(),
                oldCampaign.getFinishDate(),
                newCampaign.getStartDate(),
                newCampaign.getFinishDate(),
                newCampaign.getIsCloning()
        );
    }

    @Override
    public VerifyReservationsCanUpdateResponse selectCampaignsWithReservationsThatOverlapWithGivenReservationsAndStatus(MapCircuitDTO circuitDTO) {
        List<MapReservacion> reservations = this.dao.selectAllReservationByCircuit(circuitDTO);
        List<Long> reservationsIds = reservations.stream().map(MapReservacion::getId).collect(Collectors.toList());
        List<Long> locationsIds = reservations.stream().map(MapReservacion::getMapUbicacion).map(MapUbicacion::getId).collect(Collectors.toList());

        return selectCampaignsWithReservationsThatOverlapWithGivenReservationsAndStatus(
                reservationsIds,
                locationsIds,
                circuitDTO.getCampaignId(),
                circuitDTO.getStartDate(),
                circuitDTO.getFinishDate(),
                circuitDTO.getUpdatedStartDate(),
                circuitDTO.getUpdatedFinishDate(),
                false
        );
    }

    @Override
    public VerifyReservationsCanUpdateResponse selectCampaignsWithReservationsThatOverlapWithGivenReservationsAndStatus(MapReservationDTO reservationDTO) {
        MapReservacion reservation = this.dao.findById(reservationDTO.getReservationId()).get();
        List<Long> reservationsIds = new ArrayList<>(); reservationsIds.add(reservationDTO.getReservationId());
        List<Long> locationsIds = new ArrayList<>(); locationsIds.add(reservation.getMapUbicacion().getId());

        return selectCampaignsWithReservationsThatOverlapWithGivenReservationsAndStatus(
                reservationsIds,
                locationsIds,
                reservation.getMapCampaign().getId(),
                reservation.getStartDate(),
                reservation.getFinishDate(),
                reservationDTO.getStartDate(),
                reservationDTO.getFinishDate(),
                false
        );
    }

    @Override
    public void setExhibido(List<Long> reservationIds) {
        this.dao.setReservationExhibirByReservationsIds(reservationIds, true);
    }


    @Override
    public void setMultipleReservationExhibido(List<Long> reservationIds, Boolean exhibir) {
        this.dao.setReservationExhibirByReservationsIds(reservationIds, exhibir);
    }

    @Override
    public byte[] exportCampaignCircuitsToExcel(ExportCircuitRequest exportCircuitRequest) throws IOException {
        List<MapReservationDTO> mapReservationDTOS = this.dao.selectAllReservationDTOByCampaignId(exportCircuitRequest.getCampaignId()).stream()
                .map( dto -> {    dto.setAmount(Objects.isNull(dto.getAmount())?1:dto.getAmount());  return dto; } ).collect(Collectors.toList());;

        exportCircuitRequest.setCompanyId(this.mapCampaignDao.findCompanyIdByCampaignId(exportCircuitRequest.getCampaignId()));
        
        List<MapUbicaionParametro> parametters = new ArrayList<>();
        if(!exportCircuitRequest.getParameters().isEmpty()&&!mapReservationDTOS.isEmpty()){
            parametters = mapUbicaionParametroService.findAllByLocationsIDs(mapReservationDTOS.stream().map(MapReservationDTO::getId).collect(Collectors.toList()));
        }
        return new ParseCircuitsExportRequest(exportCircuitRequest, mapReservationDTOS, parametters, urlFileServer).buildExcel();
    }

    @Override
    public void setExhibido(MapCircuitDTO circuitDTO, Boolean exhibido) {
        this.dao.setReservationExhibirByCircuit(
                exhibido,
                circuitDTO.getCampaignId(),
                circuitDTO.getClientId(),
                circuitDTO.getStartDate(),
                circuitDTO.getFinishDate(),
                circuitDTO.getCompanyId(),
                circuitDTO.getElementId(),
                circuitDTO.getProvinceId(),
                circuitDTO.getCityId());
    }

    @Override
    public void setCampaignReservationExhibido(Long id, Boolean exhibido) {
        this.dao.setReservationExhibirByCampaignId(id, exhibido);
    }

    public VerifyReservationsCanUpdateResponse selectCampaignsWithReservationsThatOverlapWithGivenReservationsAndStatus(List<Long> reservationsIds, List<Long> locationsIds, Long campaignId, LocalDate oldStartDate, LocalDate oldFinishDate, LocalDate newStartDate, LocalDate newFinishDate, Boolean includeSameReservations) {
        if(reservationsIds.isEmpty()||Objects.isNull(oldStartDate)||Objects.isNull(oldFinishDate)||Objects.isNull(newStartDate)||Objects.isNull(newFinishDate)) return null;
        boolean datesChanged = !oldStartDate.equals(newStartDate)||!oldFinishDate.equals(newFinishDate);
        Long  startDayDiffDays = 0L, finishDayDiffDays = 0L;
        if(datesChanged) {
            startDayDiffDays = ChronoUnit.DAYS.between(oldStartDate,  newStartDate);
            finishDayDiffDays = ChronoUnit.DAYS.between(oldFinishDate, newFinishDate);
        }
        Optional<MapCampaign> sameCampaign = Optional.empty();
        if(Objects.nonNull(campaignId)&&Objects.nonNull(newStartDate)&&Objects.nonNull(newFinishDate)&&!locationsIds.isEmpty()) {
            sameCampaign = this.dao.selectCampaignByIdWithReservationsBetweenDates(reservationsIds, locationsIds, campaignId, newStartDate, newFinishDate);
        }
        return new VerifyReservationsCanUpdateResponse(
                sameCampaign.isPresent() ? sameCampaign.get() : null,
                this.dao.selectOverlappedCampaignsReservationWithGivenReservationsAndState(reservationsIds, ReservationState.CONFIRMED    , startDayDiffDays, finishDayDiffDays, includeSameReservations),
                this.dao.selectOverlappedCampaignsReservationWithGivenReservationsAndState(reservationsIds, ReservationState.NOT_CONFIRMED, startDayDiffDays, finishDayDiffDays, includeSameReservations)
        );
    }

    @Override
    public List<Long> filterEntitiesIdsThaBelongToAMapCompanies(List<Long> idsToFilter, List<Long> mapCompanyIds) {
        return this.dao.filterEntitiesIdsThaBelongToAMapCompanies(idsToFilter, mapCompanyIds);
    }
    //#endregion

    @Override @Transactional
    public void setExhibido(SetMultipleExhibirCircuitsRequest circuitsDto) {
        circuitsDto.getCircuitDTOSObjects().forEach((circuits)->this.setExhibido(circuits,circuitsDto.getExhibir()));
    }

    @Override
    public byte[] exportCampaignCircuitsToPDF(ExportCircuitRequest exportCircuitRequest) throws IOException, InterruptedException, ParserConfigurationException, TransformerException {
        List<MapReservationDTO> mapReservationDTOS = this.dao.selectAllReservationDTOByCampaignId(exportCircuitRequest.getCampaignId()).stream()
                .map( dto -> {    dto.setAmount(Objects.isNull(dto.getAmount())?1:dto.getAmount());  return dto; } ).collect(Collectors.toList());;

        exportCircuitRequest.setCompanyId(this.mapCampaignDao.findCompanyIdByCampaignId(exportCircuitRequest.getCampaignId()));

        List<MapUbicaionParametro> parametters = new ArrayList<>();
        if(!exportCircuitRequest.getParameters().isEmpty()&&!mapReservationDTOS.isEmpty()){
            parametters = mapUbicaionParametroService.findAllByLocationsIDs(mapReservationDTOS.stream().map(MapReservationDTO::getId).collect(Collectors.toList()));
        }
        return new ParseCircuitsExportRequest(exportCircuitRequest, mapReservationDTOS, parametters, urlFileServer).buildPDF();
    }

    @Override
    public void updateOrderNumber(Long id, Long ordenNumber) {
        dao.updateOrderNumber(id, ordenNumber);
    }
}