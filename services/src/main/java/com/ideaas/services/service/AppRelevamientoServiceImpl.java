package com.ideaas.services.service;

import com.ideaas.services.dao.AppRelevamientoDao;
import com.ideaas.services.domain.AppEstadoRelevamiento;
import com.ideaas.services.domain.AppRelevamiento;
import com.ideaas.services.service.interfaces.AppEstadoRelService;
import com.ideaas.services.service.interfaces.AppRelevamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AppRelevamientoServiceImpl implements AppRelevamientoService {

    private AppRelevamientoDao dao;

    private AppEstadoRelService appEstadoRelService;

    @Autowired
    public AppRelevamientoServiceImpl(AppRelevamientoDao dao , AppEstadoRelService appEstadoRelService) {
        this.dao = dao;
        this.appEstadoRelService = appEstadoRelService;
    }

    @Override
    public AppRelevamiento get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public Optional<AppRelevamiento> findById(Long id) {

        return dao.findById(id);
    }

    @Override
    public AppRelevamiento save(AppRelevamiento appRelevamiento) {
        return dao.save(appRelevamiento);
    }

    @Override
    public void delete(Long id) {
        dao.deleteById(id);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        dao.deleteByIds(ids);
    }

    @Override
    public List<AppRelevamiento> findAll() {
        Iterable<AppRelevamiento> iterator = dao.findAll();

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppRelevamiento> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<AppRelevamiento> appRelevamientos = dao.findAll(paging);

        return appRelevamientos.getContent();
    }

    @Override
    public List<AppRelevamiento> findAllByUsuario(Long idUsuario) {
        Iterable<AppRelevamiento> iterator = dao.findAllByUsuario_Id(idUsuario);

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppRelevamiento> findAllByUsuarioAndEstadoToShow(Long idUsuario , ArrayList<Long> IdsEstado){
        Iterable<AppRelevamiento> iterator = dao.findAllByUsuario_IdAndEstado_IdIn(idUsuario , IdsEstado);

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());

    }

    @Override
    public List<AppRelevamiento> findAllByConUbicacionIs(Boolean bool) {
        Iterable<AppRelevamiento> iterator = dao.findAllByConUbicacionesPreAsignadasIs(bool);

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppRelevamiento> findAllByEstado(Long idEstado) {
        Iterable<AppRelevamiento> iterator = dao.findAllByEstado_Id(idEstado);

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppRelevamiento> findAllByBajaLogica(Boolean bajaLogica){
        Iterable<AppRelevamiento> iterator = dao.findAllByBajaLogica(bajaLogica);

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());

    }

    public List<AppRelevamiento> findAllByProject(Long idProject){
        Iterable<AppRelevamiento> iterator = dao.findAllByProject_Id(idProject);

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public void setStateFinished(Long idRelevamiento) {
        AppRelevamiento relevamiento = this.get(idRelevamiento);
        AppEstadoRelevamiento estadoFinalizado = appEstadoRelService.get(3L); //idEstado = 3 es para "Finalizado"

        relevamiento.setEstado(estadoFinalizado);
        this.save(relevamiento);
    }

    @Override
    public List<AppRelevamiento> findAllByDates(String startDate, String endDate) {
        return dao.findAllByFechaAsignacionIsBetween(startDate, endDate);
    }
}
