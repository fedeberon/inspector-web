package com.ideaas.web.controller;

import com.ideaas.services.bean.MapCampaignDTO;
import com.ideaas.services.bean.MapPlanningDTO;
import com.ideaas.services.bean.MyObject;
import com.ideaas.services.bean.Wrapper;
import com.ideaas.services.domain.*;
import com.ideaas.services.enums.ReservationState;
import com.ideaas.services.exception.ReservationException;
import com.ideaas.services.request.CreateMapReservacionRequest;
import com.ideaas.services.request.MapUbicacionRequest;
import com.ideaas.services.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("planificacion")
@Secured({"ROLE_ADMINISTRADOR", "ROLE_ADMINISTRADOR_OOH"})
public class PlanningController {

    private final MapUbicacionService mapUbicacionService;

    private final MapEmpresaService mapEmpresaService;

    private final MapElementoService mapElementoService;

    private final MapFormatoService mapFormatoService;

    private final MapMedioService mapMedioService;

    private final MapLocalidadService mapLocalidadService;

    private final MapProvinciaService mapProvinciaService;

    private final MapBusService mapBusService;

    private final MapUbicacionAlturaService mapUbicacionAlturaService;

    private final MapUbicacionVisibilidadService mapUbicacionVisibilidadService;

    private final ParametroService parametroService;

    private final MapReservationService mapReservationService;

    private final MapClienteService mapClienteService;

    private final MapCampaignsService mapCampaignsService;

    private final FieldError emptyCampaignName =new FieldError("Wrapper" , "createMapReservacionRequest.campaignName" , "Debe ingresar un nuevo nombre para la campaña");

    private final FieldError wrongCampaignId =new FieldError("Wrapper" , "createMapReservacionRequest.campaignId" , "Debe seleccionar una campaña");

    private final FieldError wrongReservacionFechaDesde =new FieldError("Wrapper" , "createMapReservacionRequest.startDate" , "Debe seleccionar una fecha de reservación que sea mayor a la fecha actual");

    private final FieldError wrongReservacionFechaHasta = new FieldError("Wrapper" , "createMapReservacionRequest.finishDate" , "Debe seleccionar una fecha de finalización para la reservación sea mayor a la fecha inicio de la misma");

    private final FieldError emptyReservacionMapCliente = new FieldError("Wrapper" , "createMapReservacionRequest.mapClientId" , "Debe seleccionar al menos un cliente");

    private final FieldError emptyReservacionEstado = new FieldError("Wrapper" , "createMapReservacionRequest.reservationStateCode" , "Seleccione una opción");

    private final FieldError emptyUbicacionSelected = new FieldError("Wrapper" , "idAppUbicacionesList" , "No hay ubicaciones seleccionadas, por favor seleccione al menos una ubicación.");

    private final FieldError ubicacionAlreadyReserved = new FieldError("Wrapper" , "idAppUbicacionesList" , "Existe una reservación confirmada dentro del rango de fechas seleccionadas");

    private final FieldError locationAlreadyReservedBySameClientAndCampaign = new FieldError("Wrapper" , "idAppUbicacionesList" , "La ubicación seleccionada ya está reservada con las mismas fechas en la misma campaña por el mismo cliente");

    @Autowired
    public PlanningController(MapUbicacionService mapUbicacionService, MapEmpresaService mapEmpresaService, MapElementoService mapElementoService, MapFormatoService mapFormatoService, MapMedioService mapMedioService, MapLocalidadService mapLocalidadService, MapProvinciaService mapProvinciaService, MapBusService mapBusService, MapUbicacionAlturaService mapUbicacionAlturaService, MapUbicacionVisibilidadService mapUbicacionVisibilidadService, ParametroService parametroService, MapReservationService mapReservationService, MapClienteService mapClienteService, MapCampaignsService mapCampaignsService) {
        this.mapUbicacionService = mapUbicacionService;
        this.mapEmpresaService = mapEmpresaService;
        this.mapElementoService = mapElementoService;
        this.mapFormatoService = mapFormatoService;
        this.mapMedioService = mapMedioService;
        this.mapLocalidadService = mapLocalidadService;
        this.mapProvinciaService = mapProvinciaService;
        this.mapBusService = mapBusService;
        this.mapUbicacionAlturaService = mapUbicacionAlturaService;
        this.mapUbicacionVisibilidadService = mapUbicacionVisibilidadService;
        this.parametroService = parametroService;
        this.mapReservationService = mapReservationService;
        this.mapClienteService = mapClienteService;
        this.mapCampaignsService = mapCampaignsService;
    }

    @RequestMapping("list")
    public String list(@ModelAttribute("myWrapper") Wrapper wrapper, BindingResult bindingResult, Model model){
        // Obtaining the Model as a map allows to obtain the variables sent by addFlashAttribute method
        // of the RedirectAttributes Object
        Map modelMap = model.asMap();

        MapUbicacionRequest mapUbicacionRequest = modelMap.containsKey("mapUbicacionRequest") ? (MapUbicacionRequest) modelMap.get("mapUbicacionRequest") : new MapUbicacionRequest();

        if(modelMap.containsKey("createMapReservacionRequest.campaignName"))          bindingResult.addError(emptyCampaignName);
        if(modelMap.containsKey("createMapReservacionRequest.campaignId"))            bindingResult.addError(wrongCampaignId);
        if(modelMap.containsKey("createMapReservacionRequest.startDate"))             bindingResult.addError(wrongReservacionFechaDesde);
        if(modelMap.containsKey("createMapReservacionRequest.finishDate"))            bindingResult.addError(wrongReservacionFechaHasta);
        if(modelMap.containsKey("createMapReservacionRequest.mapClientId"))           bindingResult.addError(emptyReservacionMapCliente);
        if(modelMap.containsKey("createMapReservacionRequest.reservationStateCode"))  bindingResult.addError(emptyReservacionEstado);
        if(modelMap.containsKey("idAppUbicacionesListEmpty"))                         bindingResult.addError(emptyUbicacionSelected);
        if(modelMap.containsKey("idAppUbicacionesListAlreadyReserved"))               bindingResult.addError(ubicacionAlreadyReserved);
        if(modelMap.containsKey("idAppUbicacionesListAlreadyReservedInSameCampaign")) bindingResult.addError(locationAlreadyReservedBySameClientAndCampaign);

        model.addAttribute("displayPlanificacionFilters", true);

        List<MapPlanningDTO> plannings = mapUbicacionService.findAllMapPlanningDTOsByLocationsIDs(mapUbicacionRequest);

        model.addAttribute("ubicaciones", plannings);
        model.addAttribute("ubicacionRequest", mapUbicacionRequest);

        if(mapUbicacionRequest.getReservacionFechaDesde()!=null&&mapUbicacionRequest.getReservacionFechaHasta()!=null) {
            LocalDate start = mapUbicacionRequest.getReservacionFechaDesde();
            LocalDate end  = mapUbicacionRequest.getReservacionFechaHasta();
            model.addAttribute("rangoFechasReservaciones",
                    Stream.iterate(start, date -> date.plusDays(1))
                            .limit(ChronoUnit.DAYS.between(start, end) + 1)
                            .map( date -> date.format(DateTimeFormatter.ofPattern("dd/MM/uuuu"))).collect(Collectors.toList()));
        }

        return "planning/list";
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public String search(@ModelAttribute("myWrapper") Wrapper wrapper, Model model){
        if(wrapper==null) wrapper = new Wrapper();
        MapUbicacionRequest mapUbicacionRequest = wrapper.getRequest();
        model.addAttribute("ubicacionRequest", mapUbicacionRequest);

        model.addAttribute("ubicaciones",this.mapUbicacionService.findAllMapPlanningDTOsByLocationsIDs(wrapper.getRequest()));
        model.addAttribute("displayPlanificacionFilters", true);
        model.addAttribute("displaySelectRandomLocations", true);

        if(mapUbicacionRequest.getReservacionFechaDesde()!=null&&mapUbicacionRequest.getReservacionFechaHasta()!=null) {
            LocalDate start = mapUbicacionRequest.getReservacionFechaDesde();
            LocalDate end  = mapUbicacionRequest.getReservacionFechaHasta();
            model.addAttribute("rangoFechasReservaciones",
                    Stream.iterate(start, date -> date.plusDays(1))
                            .limit(ChronoUnit.DAYS.between(start, end) + 1)
                            .map( date -> date.format(DateTimeFormatter.ofPattern("dd/MM/uuuu"))).collect(Collectors.toList()));
        }
        return "planning/list";
    }

    @RequestMapping(value = "maps", method = RequestMethod.POST)
    public String maps(@ModelAttribute("myWrapper") Wrapper wrapper, Model model){
        List<Long> ids = wrapper.getIdAppUbicacionesListSelected();
        List<MyObject> registros = !ids.isEmpty() ? this.mapUbicacionService.findAllByIdIn(ids) : new ArrayList<>();
        model.addAttribute("registros",registros);
        model.addAttribute("ubicacionRequest", wrapper.getRequest());

        return "planning/maps";
    }

    @RequestMapping(value = "save" , method = RequestMethod.POST)
    public String create(@ModelAttribute("myWrapper") Wrapper wrapper, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        CreateMapReservacionRequest createMapReservacionRequest = wrapper.getCreateMapReservacionRequest();
        MapUbicacionRequest mapUbicacionRequest = wrapper.getRequest();
        boolean hashErrors = false;
        LocalDate start = createMapReservacionRequest.getStartDate();
        LocalDate end   = createMapReservacionRequest.getFinishDate();
        LocalDate currentDate = LocalDate.now();

        if(createMapReservacionRequest.getIsNewCampaign()){
            if(Objects.isNull(createMapReservacionRequest.getCampaignName())||createMapReservacionRequest.getCampaignName().isEmpty()) {
                redirectAttributes.addFlashAttribute("createMapReservacionRequest.campaignName", true);
                hashErrors = true;
            }
        } else {
            if(Objects.isNull(createMapReservacionRequest.getCampaignId())||createMapReservacionRequest.getCampaignId()<0) {
                redirectAttributes.addFlashAttribute("createMapReservacionRequest.campaignId", true);
                hashErrors = true;
            }
        }

        // This business rule is temporarily or permanently canceled, until the users that the companies
        // that had a previous system similar to this, finish loading all their reservations
        // // The start date of the reservation cannot be less than the current date
        // if(Objects.isNull(start)||start.compareTo(currentDate) < 0) {
        //     redirectAttributes.addFlashAttribute("createMapReservacionRequest.startDate", true);
        //     hashErrors = true;
        // }

        // The end date of the reservation cannot be less than the start date
        if(Objects.isNull(end)||end.compareTo(start) < 0) {
            redirectAttributes.addFlashAttribute("createMapReservacionRequest.finishDate", true);
            hashErrors = true;
        }

        if(Objects.isNull(createMapReservacionRequest.getMapClientId())||createMapReservacionRequest.getMapClientId() < 0) {
            redirectAttributes.addFlashAttribute("createMapReservacionRequest.mapClientId", true);
            hashErrors = true;
        }

        if(Objects.isNull(createMapReservacionRequest.getReservationStateCode()) || (createMapReservacionRequest.getReservationStateCode() != 2L && createMapReservacionRequest.getReservationState() != ReservationState.CONFIRMED)) {
            redirectAttributes.addFlashAttribute("createMapReservacionRequest.reservationStateCode", true);
            hashErrors = true;
        }

        if(Objects.isNull(wrapper.getIdAppUbicacionesList())||wrapper.getIdAppUbicacionesListSelected().isEmpty()) {
            redirectAttributes.addFlashAttribute("idAppUbicacionesListEmpty", true);
            redirectAttributes.addFlashAttribute("mapReservacionSelectedLocationHashError", true);
            hashErrors = true;
        }

        if(hashErrors) {
            redirectAttributes.addFlashAttribute("mapUbicacionRequest", mapUbicacionRequest);
            redirectAttributes.addFlashAttribute("mapReservacionCreateModalHashError", true);
            return "redirect:/planificacion/list";
        }

        createMapReservacionRequest.setMapLocationsIds(wrapper.getIdAppUbicacionesList());

        try {
            this.mapReservationService.saveMapReservations(createMapReservacionRequest);
        } catch (ReservationException re) {
            redirectAttributes.addFlashAttribute("mapReservacionCreateModalHashError", true);
            redirectAttributes.addFlashAttribute("mapReservacionSelectedLocationHashError", true);
            if(re.code() == ReservationException.Code.OVERLAPPED_CAMPAIGN_RESERVATION) {
                redirectAttributes.addFlashAttribute("idAppUbicacionesListAlreadyReservedInSameCampaign", true);
            }
            if (re.code() == ReservationException.Code.OVERLAPPED_CONFIRMED_RESERVATION) {
                redirectAttributes.addFlashAttribute("idAppUbicacionesListAlreadyReserved", true);
            }
        }
        redirectAttributes.addFlashAttribute("mapUbicacionRequest", MapUbicacionRequest.from(createMapReservacionRequest));

        return "redirect:/planificacion/list";
    }

    //#region @ModelAttribute

    @ModelAttribute("mapUbicacion")
    public MapUbicacion get(){
        return new MapUbicacion();
    }

    @ModelAttribute("empresas")
    public List<MapEmpresa> empresas(){
        return mapEmpresaService.findByOrderByDescripcionAsc();
    }

    @ModelAttribute("parametros")
    public List<Parametro> parametros(){
        return this.parametroService.findAll();
    }

    @ModelAttribute("elementos")
    public List<MapElemento> elementos(){
        return mapElementoService.findByOrderByDescripcionAsc();
    }

    @ModelAttribute("formatos")
    public List<MapFormato> formatos(){
        return mapFormatoService.findByOrderByDescripcionAsc();
    }

    @ModelAttribute("medios")
    public List<MapMedio> medios(){
        return mapMedioService.findByOrderByDescripcionAsc();
    }

    @ModelAttribute("localidades")
    public List<MapLocalidad> localidades(){
        return mapLocalidadService.findByOrderByDescripcionAsc();
    }

    @ModelAttribute("provincias")
    public List<MapProvincia> provincias(){
        return mapProvinciaService.findByOrderByDescripcionAsc();
    }

    @ModelAttribute("buses")
    public List<MapBus> buses(){
        return mapBusService.findAll();
    }

    @ModelAttribute("alturas")
    public List<MapUbicacionAltura> alturas(){
        return mapUbicacionAlturaService.findAll();
    }

    @ModelAttribute("visibilidades")
    public List<MapUbicacionVisibilidad> visibilidades(){
        return mapUbicacionVisibilidadService.findAll();
    }

    @ModelAttribute("clientes")
    public List<MapCliente> clientes(){
        return mapClienteService.findAll();
    }


    @ModelAttribute("campaigns")
    public List<MapCampaignDTO> campaigns(){
        return mapCampaignsService.findAllMapCampaignDTO();
    }

    @ModelAttribute("myWrapper")
    public Wrapper wrapper(){
        return new Wrapper();
    }

    //#endregion
}
