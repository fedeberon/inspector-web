package com.ideaas.services.service;

import com.ideaas.services.bean.MyObject;
import com.ideaas.services.dao.AppUbicacionRelDao;
import com.ideaas.services.domain.AppRelevamiento;
import com.ideaas.services.domain.AppRespuesta;
import com.ideaas.services.domain.AppUbicacionRelevamiento;
import com.ideaas.services.service.interfaces.AppUbicacionRelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AppUbicacionRelServiceImpl implements AppUbicacionRelService {

    private AppUbicacionRelDao dao;

    public AppUbicacionRelServiceImpl(AppUbicacionRelDao dao) {
        this.dao = dao;
    }

    @Override
    public AppUbicacionRelevamiento get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public AppUbicacionRelevamiento save(AppUbicacionRelevamiento appUbicacionRelevamiento) {
        return dao.save(appUbicacionRelevamiento);
    }

    @Override
    public void saveAll(List<AppUbicacionRelevamiento> appUbicacionRelevamientos) {
        dao.saveAll(appUbicacionRelevamientos);
    }

    @Override
    public void delete(Long id) {
        dao.deleteById(id);
    }

    @Override
    public void deleteAllByIds(List<Long> ids) {
        dao.deleteByIds(ids);
    }

    @Override
    public void deleteByRelevamientoIds(List<Long> ids) {
        dao.deleteByRelevamientoIds(ids);
    }

    @Override
    public List<AppUbicacionRelevamiento> findAll() {
        Iterable<AppUbicacionRelevamiento> iterator = dao.findAll();

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppUbicacionRelevamiento> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<AppUbicacionRelevamiento> appUbicacionesRelevamientos = dao.findAll(paging);

        return appUbicacionesRelevamientos.getContent();
    }

    @Override
    public List<AppUbicacionRelevamiento> findAllByBajaLogica(Boolean bajaLogica) {
        Iterable<AppUbicacionRelevamiento> iterator = dao.findAllByBajaLogica(bajaLogica);

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }


    public void saveLatLong(MyObject object) {
        AppUbicacionRelevamiento ubicacion = this.get(object.getId());
//        ubicacion.setLatitud(object.getLatitud());
//        ubicacion.setLongitud(object.getLongitud());
        save(ubicacion);
    }

    @Override
    public List<AppUbicacionRelevamiento> findAllByIdRelevamiento(Long idRelevamiento) {
        {
            Iterable<AppUbicacionRelevamiento> iterator = dao.findAllByAppRelevamiento_Id(idRelevamiento);

            return  StreamSupport
                    .stream(iterator.spliterator(), false)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<AppUbicacionRelevamiento> findAllByRelevamientoAndBajaLogica(Long idRelevamiento) {
        {
            Iterable<AppUbicacionRelevamiento> iterator = dao.findAllByRelevamientoAndBajaLogica(idRelevamiento);

            return  StreamSupport
                    .stream(iterator.spliterator(), false)
                    .collect(Collectors.toList());
        }
    }
    
    @Override
    public Map<Long, Integer> cantidadUbicacionesByRelevamiento() {

        Map<Long, Integer> cantidadUbicaciones = new HashMap<Long, Integer>();

        for (Object[] i : dao.cantidadUbicacionesByRelevamiento()) {
            Long idRelevamiento = new Long(String.valueOf(i[0])) ;
            Integer cantidad = ((Number) i[1]).intValue();
            cantidadUbicaciones.put(idRelevamiento, cantidad);
        }

        return cantidadUbicaciones;
    }

    @Override
    public List<MyObject> findAllByIdIn(List<Long> ids){
        return this.dao.findAllByIdIn(ids);
    }

    @Override
    public Long findNextById(Long idUbicacionRelevamiento, Long idRelevamiento) {
        Long nextId = dao.findNextById(idUbicacionRelevamiento, idRelevamiento);
        if (nextId == null){
            nextId = -1L;
        }
        return nextId;
    }

    @Override
    public Long findPreviousById(Long idUbicacionRelevamiento, Long idRelevamiento) {
        Long previousId = dao.findPreviousById(idUbicacionRelevamiento, idRelevamiento);
        if (previousId == null){
            previousId = -1L;
        }
        return previousId;
    }

    @Override
    public List<AppUbicacionRelevamiento> searchByDates(String startDate, String endDate) {
        return dao.findAllByAppRelevamientoFechaAsignacionIsBetween(startDate, endDate);
    }

    @Override
    public List<MyObject> findAllByIdUbicacionRel(List<Long> ids) {
        return dao.findAllByIdUbicacionRel(ids);
    }
}
