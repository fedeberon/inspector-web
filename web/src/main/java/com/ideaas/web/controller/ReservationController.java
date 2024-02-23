package com.ideaas.web.controller;

import com.ideaas.services.bean.*;
import com.ideaas.services.domain.*;
import com.ideaas.services.enums.ReservationState;
import com.ideaas.services.enums.TipoUsuarioEnum;
import com.ideaas.services.request.ExportCircuitRequest;
import com.ideaas.services.request.MapUbicacionRequest;
import com.ideaas.services.service.interfaces.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The Reservation controller. All CRUD operations that can be performed
 * in this service are restricted depending on the logged in user:
 * <ul>
 *     <li>{@link com.ideaas.services.enums.TipoUsuarioEnum#ROLE_ADMINISTRADOR admin type}:
 *     return all reservations</li>
 *     <li>{@link com.ideaas.services.enums.TipoUsuarioEnum#ROLE_ADMINISTRADOR_OOH OOH admin user}:
 *     returns all reservations if the circuit campaign belong to their
 *     {@link com.ideaas.services.domain.MapEmpresa companies}</li>
 * </ul>
 *
 * @todo remove all trows on methods and replace for something more semantic object
 */
@Slf4j
@Controller
@RequestMapping("campanas")
@Secured({"ROLE_ADMINISTRADOR", "ROLE_ADMINISTRADOR_OOH"})
public class ReservationController {
    //#region properties
    @Autowired
    private MapCampaignsService mapCampaignsService;
    @Autowired
    private MapReservationService mapReservationService;
    @Autowired
    private MapClienteService mapClienteService;
    @Autowired
    private ParametroService parametroService;
    //#endregion

    //#region campaigns
    /**
     * Get all campaigns depending on the logged user. The campaigns that it returns
     * depend on the type of logged in user:
     * <ul>
     *     <li>{@link TipoUsuarioEnum#ROLE_ADMINISTRADOR admin type}:
     *     return all campaigns</li>
     *     <li>{@link TipoUsuarioEnum#ROLE_ADMINISTRADOR_OOH OOH admin user}:
     *     returns all the campaigns that belong to their {@link MapEmpresa companies}</li>
     * </ul>
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("list")
    public String listCampaigns(Model model) {
        List<MapCampaignDTO> campaigns = this.mapCampaignsService.findAllMapCampaignDTO();
        model.addAttribute("campaigns", campaigns);
        return "campaign/list";
    }

    /**
     * Get a campaign by id.
     *
     * @param campaignId the campaign id
     * @param model      the model
     * @return the string
     */
    @RequestMapping(value = "/show/{campaignId}", method = RequestMethod.GET)
    public String showCampaign(@PathVariable Long campaignId, Model model) {
        model.addAttribute("campaign", this.mapCampaignsService.getCampaignDTOByCampaignId(campaignId));
        return "campaign/show";
    }

    /**
     * Get a campaign by id to edit.
     *
     * @param campaignId the campaign id
     * @param model      the model
     * @return the string
     */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String editCampaign(@RequestParam  Long campaignId, Model model) {
        model.addAttribute("updateCampaign", this.mapCampaignsService.getCampaignDTOByCampaignId(campaignId));
        model.addAttribute("clientes", this.mapClienteService.findAll());
        return "campaign/update";
    }

    /**
     * Update a campaign, and if its start and end dates or status have changed,
     * update all of its reservations if possible.
     *
     * @param campaing      the campaing
     * @param model         the model
     * @param bindingResult the binding result
     * @return the string
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String editCampaign(@ModelAttribute MapCampaignDTO campaing, Model model, BindingResult bindingResult) {
        boolean hashErrors = false;
        // LocalDate start = null;
        // LocalDate end   = null;

        // if(campaing.getStartDate()!=null&&campaing.getFinishDate()!=null) {
        //     start = campaing.getStartDate();
        //     end   = campaing.getFinishDate();
        // }

        // if(Objects.isNull(start)) {
        //     bindingResult.addError(
        //             new FieldError("MapCampaign" , "startDate" , "Debe seleccionar una fecha de inicio para la campaña")
        //     ); hashErrors = true;
        // }

        // // The end date of the reservation cannot be less than the start date
        // if(Objects.isNull(end)||end.compareTo(start) < 0) {
        //     bindingResult.addError(
        //             new FieldError("MapCampaign" , "finishDate" , "Debe seleccionar una fecha de finalización para la campaña")
        //     ); hashErrors = true;
        // }

        if(Objects.isNull(campaing.getCampaign())||campaing.getCampaign().isEmpty()) {
            bindingResult.addError(
                    new FieldError("MapCampaign" , "name" , "Debe ingresar un nombre para la campaña")
            ); hashErrors = true;
        }

        if(Objects.isNull(campaing.getCampaignId())||campaing.getCampaignId() < 0) {
            bindingResult.addError(
                    new FieldError("MapCampaign" , "mapClient" , "Debe seleccionar al menos un cliente")
            ); hashErrors = true;
        }

        if(Objects.isNull(campaing.getReservationState()) || (campaing.getReservationState() != ReservationState.CONFIRMED && campaing.getReservationState() != ReservationState.NOT_CONFIRMED&&campaing.getReservationState() != ReservationState.NOT_CHANGE)) {
            bindingResult.addError(
                    new FieldError("MapCampaign" , "reservationStateCode" , "Seleccione una opción")
            ); hashErrors = true;
        }

        if(hashErrors){
            model.addAttribute("updateCampaign", campaing);
            return "campaign/update";
        }

        model.addAttribute("campaign", this.mapReservationService.saveCampaign(campaing));
        return "redirect:/campanas/show/"+campaing.getCampaignId();
    }

    /**
     * Get a campaign by id to clone.
     *
     * @param campaignId the campaign id
     * @param model      the model
     * @return the string
     */
    @RequestMapping(value = "clone", method = RequestMethod.GET)
    public String cloneCampaign(@RequestParam Long campaignId, Model model) {
        model.addAttribute("newCampaign", this.mapCampaignsService.getCampaignDTOByCampaignId(campaignId));
        // model.addAttribute("campaigns", this.mapCampaignsService.findAllMapCampaignDTO());
        model.addAttribute("clientes", this.mapClienteService.findAll());
        return "campaign/clone";
    }

    /**
     * Clone a campaign.
     * @param bindingResult the binding result
     * @param model         the model
     * @return the string
     */
    @RequestMapping(value = "clone", method = RequestMethod.POST)
    public String cloneCampaign(@ModelAttribute MapCampaignDTO newCampaign, BindingResult bindingResult, Model model) {
        boolean hashErrors = false;

        if(Objects.isNull(newCampaign.getStartDate())) {
            bindingResult.addError(
                    new FieldError("MapCampaign" , "startDate" , "Debe seleccionar una fecha de inicio para la campaña")
            ); hashErrors = true;
        }

        if(Objects.isNull(newCampaign.getCampaign())||newCampaign.getCampaign().isEmpty()) {
            bindingResult.addError(
                    new FieldError("MapCampaign" , "name" , "Debe ingresar un nombre para la campaña")
            ); hashErrors = true;
        }

        if(Objects.isNull(newCampaign.getClientId())||newCampaign.getClientId() < 0) {
            bindingResult.addError(
                    new FieldError("MapCampaign" , "mapClient" , "Debe seleccionar al menos un cliente")
            ); hashErrors = true;
        }


        if(hashErrors){
            model.addAttribute("newCampaign", newCampaign);
            return "campaign/clone";
        }
        newCampaign.setIsCloning(true);
        return "redirect:/campanas/show/"+this.mapReservationService.clone(newCampaign).getId();
    }

    /**
     * Delete a campaign by id and all its reservations.
     *
     * @param id the id
     * @return the string
     */
    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public String deleteCampaign(@PathVariable Long id) {
        this.mapCampaignsService.delete(this.mapCampaignsService.get(id));
        return "redirect:/campanas/list";
    }

    @RequestMapping(value = "set-exhibido/{id}", method = RequestMethod.POST)
    public String setExhibido(@PathVariable Long id, @RequestParam Boolean exhibido) {
        this.mapReservationService.setCampaignReservationExhibido(id, exhibido);
        return "redirect:/campanas/list";
    }

    /**
     * Generates an Excel file with the requested circuits and returns it with an
     * array of bytes.
     *
     * @param exportCircuitRequest the export circuit request
     * @return byte [ ]
     * @throws ParseException the parse exception
     * @throws IOException    the io exception
     */
    @RequestMapping(
            value = "excel-campaign-export",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE,
            method = RequestMethod.POST)
    public @ResponseBody byte[] exportCampaignExcel(@RequestBody ExportCircuitRequest exportCircuitRequest) throws ParseException, IOException {
        try {
            return this.mapReservationService.exportCampaignCircuitsToExcel(exportCircuitRequest);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return null;
    }

    /**
     * Generates an PDF file with the requested circuits and returns it with an
     * array of bytes.
     *
     * @param exportCircuitRequest the export circuit request
     * @return byte [ ]
     * @throws ParseException
     * @throws IOException
     */
    @RequestMapping(
            value = "pdf-campaign-export",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE,
            method = RequestMethod.POST)
    public @ResponseBody byte[] exportCampaignPdf(@RequestBody ExportCircuitRequest exportCircuitRequest)  {
        try {
            return this.mapReservationService.exportCampaignCircuitsToPDF(exportCircuitRequest);
        } catch (IOException ex) {
            System.out.println(ex);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }
    //#endregion


    //#region circuits
    /**
     * Generates an Excel file with the requested circuits and returns it with an
     * array of bytes.
     *
     * @param exportCircuitRequest the export circuit request
     * @return byte [ ]
     * @throws ParseException the parse exception
     * @throws IOException    the io exception
     */
    @RequestMapping(
            value = "excel-circuits-export",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE,
            method = RequestMethod.POST)
    public @ResponseBody byte[] exportExcel(@RequestBody ExportCircuitRequest exportCircuitRequest) throws ParseException, IOException {
        try {
            return this.mapReservationService.exportCircuitsToExcel(exportCircuitRequest);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return null;
    }

    /**
     * Generates an PDF file with the requested circuits and returns it with an
     * array of bytes.
     *
     * @param exportCircuitRequest the export circuit request
     * @return byte [ ]
     * @throws ParseException
     * @throws IOException
     */
    @RequestMapping(
            value = "pdf-circuits-export",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE,
            method = RequestMethod.POST)
    public @ResponseBody byte[] exportPdf(@RequestBody ExportCircuitRequest exportCircuitRequest)  {
        try {
            return this.mapReservationService.exportCircuitsToPDF(exportCircuitRequest);
        } catch (IOException ex) {
            System.out.println(ex);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get all circuits by campaign id, depending on the logged user. The circuits that it returns
     * depend on the type of logged in user:
     * <ul>
     *     <li>{@link TipoUsuarioEnum#ROLE_ADMINISTRADOR admin type}:
     *     return all circuit</li>
     *     <li>{@link TipoUsuarioEnum#ROLE_ADMINISTRADOR_OOH OOH admin user}:
     *     returns all circuits if the circuit campaign belong to their {@link MapEmpresa companies}</li>
     * </ul>
     *
     * @param campaignId the campaign id
     * @param model      the model
     * @return the string
     */
    @GetMapping(value = "{campaignId}/circuitos/list")
    public String listCircuits(@PathVariable Long campaignId, Model model) {
        model.addAttribute("circuits", this.mapReservationService.findAllCircuitsByCampaignId(campaignId));
        List<Long> mapCompaniesIDs = this.mapCampaignsService.get(campaignId).getMapClient().getMapEmpresas().stream().map(MapEmpresa::getId).collect(Collectors.toList());
        if(!mapCompaniesIDs.isEmpty()) {
            model.addAttribute("parametros", this.parametroService.findAllByMapCompanyIds(mapCompaniesIDs));
        }
        return "circuit/list";
    }

    /**
     * Redirect to a campaign's circuit list when a request is made to get a
     * circuit with the wrong HTTP resource.
     *
     * @param campaignId the campaign id
     * @return the string
     */
    @RequestMapping(value = "{campaignId}/circuitos/show", method = RequestMethod.GET)
    public String showCircuitFallback(@PathVariable Long campaignId) {
        return "redirect:/campanas/"+campaignId+"/circuitos/list";
    }

    /**
     * Get a circuit.
     *
     * @param campaignId the campaign id
     * @param circuitDTO the circuit dto
     * @param model      the model
     * @return the string
     */
    @RequestMapping(value = "{campaignId}/circuitos/show", method = RequestMethod.POST)
    public String showCircuit(@PathVariable Long campaignId, @ModelAttribute MapCircuitDTO circuitDTO, Model model) {
        model.addAttribute("circuitDTO", circuitDTO);
        model.addAttribute("idCampaign", campaignId);
        return "circuit/show";
    }

    /**
     * Get a circuit to edit.
     *
     * @param campaignId the campaign id
     * @param circuitDTO the circuit dto
     * @param model      the model
     * @return the circuit for edit
     */
    @RequestMapping(value = "{campaignId}/circuitos/edit", method = RequestMethod.POST)
    public String getCircuitForEdit(@PathVariable Long campaignId, @ModelAttribute MapCircuitDTO circuitDTO, Model model) {
        circuitDTO.setUpdatedStartDate (circuitDTO.getStartDate());
        circuitDTO.setUpdatedFinishDate(circuitDTO.getFinishDate());
        model.addAttribute("circuitDTO", circuitDTO);
        model.addAttribute("campaignId", campaignId);
        return "circuit/update";
    }

    /**
     * Update a circuit, and if its start and end dates or status have changed,
     * update all of its reservations if possible.
     *
     * @param campaignId         the campaign id
     * @param circuitDTO         the circuit dto
     * @param model              the model
     * @param bindingResult      the binding result
     * @param redirectAttributes the redirect attributes
     * @return the string
     */
    @RequestMapping(value = "{campaignId}/circuitos/edited", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String editCircuit(Long campaignId, MapCircuitDTO circuitDTO, Model model, BindingResult bindingResult,RedirectAttributes redirectAttributes) {
        boolean hashErrors = false;
        LocalDate start = null;
        LocalDate end   = null;

        if(circuitDTO.getStartDate()!=null&&circuitDTO.getFinishDate()!=null) {
            start = circuitDTO.getStartDate();
            end   = circuitDTO.getFinishDate();
        }

        if(Objects.isNull(start)) {
            bindingResult.addError(
                    new FieldError("MapCircuitDTO" , "startDate" , "Debe seleccionar una fecha de inicio para el circuito")
            ); hashErrors = true;
        }

        // The end date of the reservation cannot be less than the start date
        if(Objects.isNull(end)||end.compareTo(start) < 0) {
            bindingResult.addError(
                    new FieldError("MapCircuitDTO" , "finishDate" , "Debe seleccionar una fecha de finalización para el circuito sea mayor a la fecha inicio")
            ); hashErrors = true;
        }

        if(Objects.isNull(circuitDTO.getReservationState()) || (circuitDTO.getReservationState() != ReservationState.CONFIRMED && circuitDTO.getReservationState() != ReservationState.NOT_CONFIRMED)) {
            bindingResult.addError(
                    new FieldError("MapCircuitDTO" , "reservationStateCode" , "Seleccione una opción")
            ); hashErrors = true;
        }

        model.addAttribute("circuitDTO", circuitDTO);
        model.addAttribute("campaignId", campaignId);

        if(hashErrors){
            return "circuit/update";
        }

        this.mapReservationService.saveCircuit(circuitDTO);
        return "circuit/show";
    }

    /**
     * Delete a circuit and all its reservations.
     *
     * @param campaignId the campaign id
     * @param circuitDTO the circuit dto
     * @return the string
     */
    @RequestMapping(value = "{campaignId}/circuitos/delete", method = RequestMethod.POST)
    public String deleteCircuit(@PathVariable Long campaignId, @ModelAttribute MapCircuitDTO circuitDTO) {
        this.mapReservationService.deleteCircuit(circuitDTO);
        return "redirect:/campanas/"+campaignId+"/circuitos/list";
    }

    @RequestMapping(value = "{campaignId}/circuitos/set-exhibido", method = RequestMethod.POST)
    public String setExhibido(Long campaignId, @RequestParam Boolean exhibido, MapCircuitDTO circuitDTO) {

        this.mapReservationService.setExhibido(circuitDTO, exhibido);
        return "redirect:/campanas/"+campaignId+"/circuitos/list";
    }

    @RequestMapping(value = "{campaignId}/circuitos/set-multiple-exhibido", method = RequestMethod.POST)
    public String setExhibido(@PathVariable Long campaignId, SetMultipleExhibirCircuitsRequest circuitsDto) {
        
        this.mapReservationService.setExhibido(circuitsDto);
        return "redirect:/campanas/"+campaignId.toString()+"/circuitos/list";
    }

    //#endregion

    //#region reservations
    /**
     * Get all the reservations of a circuit, depending on the logged user. The circuits that it returns
     * depend on the type of logged in user:
     * <ul>
     *     <li>{@link TipoUsuarioEnum#ROLE_ADMINISTRADOR admin type}:
     *     return all reservations</li>
     *     <li>{@link TipoUsuarioEnum#ROLE_ADMINISTRADOR_OOH OOH admin user}:
     *     returns all reservations if the circuit campaign belong to their {@link MapEmpresa companies}</li>
     * </ul>
     *
     * @param campaignId the campaign id
     * @param circuitDTO the circuit dto
     * @param model      the model
     * @return the string
     */
    @RequestMapping(value = "{campaignId}/circuitos/reservaciones/list")
    public String listReservations(@PathVariable Long campaignId, @ModelAttribute MapCircuitDTO circuitDTO, Model model) {
        model.addAttribute("reservations", this.mapReservationService.findAllReservationsDTOByCircuit(circuitDTO));
        model.addAttribute("campaignId", campaignId);
        model.addAttribute("circuitDTO", circuitDTO);
        return "reservation/list";
    }

    /**
     * Get a reservation by id.
     *
     * @param campaignId    the campaign id
     * @param reservationId the reservation id
     * @param model         the model
     * @return the string
     */
    @RequestMapping(value = "{campaignId}/circuitos/reservaciones/show/{reservationId}", method = RequestMethod.GET)
    public String showReservation(@PathVariable Long campaignId, @PathVariable Long reservationId, Model model) {
        model.addAttribute("reservationDTO", this.mapReservationService.getReservation(reservationId));
        return "reservation/show";
    }

    /**
     * Get a reservation to edit.
     *
     * @param campaignId    the campaign id
     * @param reservationId the reservation id
     * @param model         the model
     * @return the string
     */
    @RequestMapping(value = "{campaignId}/circuitos/reservaciones/edit", method = RequestMethod.GET)
    public String editReservation(@PathVariable Long campaignId, @RequestParam Long reservationId, Model model) {
        model.addAttribute("updateReservation", this.mapReservationService.getReservation(reservationId));
        return "reservation/update";
    }

    /**
     * Update a reservation if possible.
     *
     * @param campaignId     the campaign id
     * @param reservationDTO the reservation dto
     * @param model          the model
     * @param bindingResult  the binding result
     * @return the string
     */
    @RequestMapping(value = "{campaignId}/circuitos/reservaciones/edit", method = RequestMethod.POST)
    public String saveReservation(@PathVariable Long campaignId, @ModelAttribute MapReservationDTO reservationDTO, Model model, BindingResult bindingResult) {
        boolean hashErrors = false;
        LocalDate start = null;
        LocalDate end   = null;

        if(reservationDTO.getStartDate()!=null&&reservationDTO.getFinishDate()!=null) {
            start = reservationDTO.getStartDate();
            end   = reservationDTO.getFinishDate();
        }

        if(Objects.isNull(start)) {
            bindingResult.addError(
                    new FieldError("MapReservationDTO" , "startDate" , "Debe seleccionar una fecha de reservación")
            ); hashErrors = true;
        }

        // The end date of the reservation cannot be less than the start date
        if(Objects.isNull(end)||end.compareTo(start) < 0) {
            bindingResult.addError(
                    new FieldError("MapReservationDTO" , "finishDate" , "Debe seleccionar una fecha de finalización para la reservación sea mayor a la fecha inicio")
            ); hashErrors = true;
        }

        if(Objects.isNull(reservationDTO.getReservationState()) || (reservationDTO.getReservationState() != ReservationState.CONFIRMED && reservationDTO.getReservationState() != ReservationState.NOT_CONFIRMED)) {
            bindingResult.addError(
                    new FieldError("MapReservationDTO" , "reservationStateCode" , "Seleccione una opción")
            ); hashErrors = true;
        }

        if(hashErrors){
            model.addAttribute("updateReservation", reservationDTO);
            return "reservation/update";
        }

        this.mapReservationService.saveReservation(reservationDTO);

        model.addAttribute("reservationDTO", reservationDTO);
        return "redirect:/campanas/"+campaignId+"/circuitos/reservaciones/show/"+reservationDTO.getReservationId();
    }

    /**
     * Delete reservation by id.
     *
     * @param campaignId the campaign id
     * @param id         the id
     * @return the string
     */
    @RequestMapping(value = "{campaignId}/circuitos/reservaciones/delete/{id}", method = RequestMethod.POST)
    public String deleteReservation(@PathVariable Long campaignId, @PathVariable Long id) {
        this.mapReservationService.deleteReservationById(id, null, null);
        return "redirect:/campanas/"+campaignId+"/circuitos/reservaciones/list";
    }

    @RequestMapping(value = "{campaignId}/circuitos/reservaciones/deleteAll", params = "delete" , method = RequestMethod.POST)
    public ModelAndView deleteAllReservations(@ModelAttribute MapCircuitDTO circuitDTO,
                                              @ModelAttribute("myWrapper") Wrapper reservaciones,
                                              @PathVariable Long campaignId,
                                              Model model,
                                              HttpServletRequest request){
        List<Long> reservationIds = reservaciones.getIdAppUbicacionesListSelected();

        if(!reservationIds.isEmpty()){
            this.mapReservationService.deleteAllByIds(reservationIds);
        }
        model.addAttribute("circuitDTO", circuitDTO);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
        return new ModelAndView("redirect:/campanas/"+campaignId+"/circuitos/reservaciones/list");
    }

    @RequestMapping(value = "{campaignId}/circuitos/reservaciones/set-exhibido", method = RequestMethod.POST)
    public ModelAndView setExhibido(@ModelAttribute MapCircuitDTO circuitDTO,
                                              @ModelAttribute("myWrapper") Wrapper reservaciones,
                                              @PathVariable Long campaignId,
                                              Model model,
                                              HttpServletRequest request){
        List<Long> reservationIds = reservaciones.getIdAppUbicacionesListSelected();
        if(!reservationIds.isEmpty()){
            this.mapReservationService.setExhibido(reservationIds);
        }
        model.addAttribute("circuitDTO", circuitDTO);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
        return new ModelAndView("redirect:/campanas/"+campaignId+"/circuitos/reservaciones/list");
    }

    @RequestMapping(value = "{campaignId}/circuitos/reservaciones/set-multiple-exhibido", method = RequestMethod.POST)
    public ModelAndView setMultipleReservationExhibido(
                                    @PathVariable Long campaignId,
                                    @RequestParam Boolean exhibir,
                                    @ModelAttribute MapCircuitDTO circuitDTO,
                                    String reservationIds,
                                    Model model,
                                    HttpServletRequest request){
        List<Long> reservationIdss = Objects.nonNull(reservationIds) ? Arrays.stream(reservationIds.trim().split(",")).map(Long::valueOf).collect(Collectors.toList()) : new ArrayList<>();
        if(!reservationIds.isEmpty()){
        this.mapReservationService.setMultipleReservationExhibido(reservationIdss, exhibir);
        }
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
        return new ModelAndView("redirect:/campanas/"+campaignId+"/circuitos/reservaciones/list");
    }
    //#endregion

    //#region validations
    /**
     * This method returns all campaigns that have reservations overlap with
     * the dates of the given campaign, to decide if it can be edited or not.
     *
     * @param campaign the campaign
     * @return the response entity
     */
    @RequestMapping(value ="/can-update", method = RequestMethod.POST)
    public ResponseEntity<VerifyReservationsCanUpdateResponse> canUpdateCampaign(@RequestBody MapCampaignDTO campaign) {
        return new ResponseEntity<>(this.mapReservationService.selectCampaignsWithReservationsThatOverlapWithGivenReservationsAndStatus(campaign), HttpStatus.OK);
    }

    /**
     * This method returns all campaigns that have reservations overlap with
     * the dates of the given circuit, to decide if it can be edited or not.
     *
     * @param circuitDTO the circuit dto
     * @return the response entity
     */
    @RequestMapping(value = "{campaignId}/circuitos/can-update", method = RequestMethod.POST)
    public ResponseEntity<VerifyReservationsCanUpdateResponse> canUpdateCircuit(@RequestBody MapCircuitDTO circuitDTO) {
        return new ResponseEntity<>(this.mapReservationService.selectCampaignsWithReservationsThatOverlapWithGivenReservationsAndStatus(circuitDTO), HttpStatus.OK);
    }

    /**
     * This method returns all campaigns that have reservations overlap with
     * the dates of the given reservation, to decide if it can be edited or not.
     *
     * @param reservationDTO the reservation dto
     * @return the response entity
     */
    @RequestMapping(value = "{campaignId}/circuitos/reservaciones/can-update", method = RequestMethod.POST)
    public ResponseEntity<VerifyReservationsCanUpdateResponse> canUpdateReservation(@RequestBody MapReservationDTO reservationDTO) {
        return new ResponseEntity<>(this.mapReservationService.selectCampaignsWithReservationsThatOverlapWithGivenReservationsAndStatus(reservationDTO), HttpStatus.OK);
    }
    //#endregion
}