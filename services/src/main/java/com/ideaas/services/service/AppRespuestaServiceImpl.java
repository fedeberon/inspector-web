package com.ideaas.services.service;

import com.ideaas.services.dao.AppRespuestaDao;
import com.ideaas.services.domain.AppRespuesta;
import com.ideaas.services.service.interfaces.AppRespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AppRespuestaServiceImpl implements AppRespuestaService {

    private AppRespuestaDao dao;

    @Autowired
    public AppRespuestaServiceImpl(AppRespuestaDao dao) {
        this.dao = dao;
    }

    @Override
    public AppRespuesta get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public AppRespuesta save(AppRespuesta appRespuesta) {
        return dao.save(appRespuesta);
    }

    @Override
    public void saveRespuestaUpdate(AppRespuesta newRespuesta) {
        AppRespuesta respuestaToUpdated = this.get(newRespuesta.getId());
        respuestaToUpdated.setRespuesta(newRespuesta.getRespuesta());
        save(respuestaToUpdated);
    }

    @Override
    public void saveUpdate(AppRespuesta newRespuesta) {
        AppRespuesta respuestaToUpdated = this.get(newRespuesta.getId());
        respuestaToUpdated.setOneOrdenS(newRespuesta.getOneOrdenS());
        respuestaToUpdated.setOneCircuito(newRespuesta.getOneCircuito());
        save(respuestaToUpdated);
    }

    @Override
    public void saveAll(List<AppRespuesta> appRespuestas) {
        dao.saveAll(appRespuestas);
    }

    @Override
    public void delete(Long id){
        dao.deleteById(id);
    }

    @Override
    public void deleteAllByIds(List<Long> ids){
        dao.deleteByIds(ids);
    }

    @Override
    public void deleteByRelevamientoIds(List<Long> ids) {
        dao.deleteByRelevamientoIds(ids);
    }

    @Override
    public List<AppRespuesta> findAll() {
        Iterable<AppRespuesta> iterator = dao.findAll();

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppRespuesta> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<AppRespuesta> appRespuestas = dao.findAll(paging);

        return appRespuestas.getContent();
    }

    @Override
    public AppRespuesta findByUbicacionAndRelevamiento(Long idUbicacion , Long idRelevamiento) {
        return dao.findByAppUbicacionRelevamientoIdAndAppRelevamientoId(idUbicacion , idRelevamiento);
    }

    @Override
    public List<AppRespuesta> findAllByIdUbicacion(Long idUbicacion) {
        Iterable<AppRespuesta> iterator = dao.findAllByAppUbicacionRelevamiento_Id(idUbicacion);

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppRespuesta> findAllByIdRelevamiento(Long idRelevamiento) {
        Iterable<AppRespuesta> iterator = dao.findAllByAppRelevamiento_Id(idRelevamiento);

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }

}
