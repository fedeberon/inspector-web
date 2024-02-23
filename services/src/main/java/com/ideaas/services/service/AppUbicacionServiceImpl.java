package com.ideaas.services.service;

import com.ideaas.services.bean.MyObject;
import com.ideaas.services.dao.AppUbicacionDao;
import com.ideaas.services.domain.AppUbicacion;
import com.ideaas.services.service.interfaces.AppUbicacionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AppUbicacionServiceImpl implements AppUbicacionService {

    private AppUbicacionDao dao;

    public AppUbicacionServiceImpl(AppUbicacionDao dao) {
        this.dao = dao;
    }

    @Override
    public AppUbicacion get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public AppUbicacion findByOneClaveUbi(Long oneClaveUbi) {
        return dao.findByOneClaveUbi(oneClaveUbi);
    }

    @Override
    public AppUbicacion save(AppUbicacion appUbicacion) {
        return dao.save(appUbicacion);
    }

    @Override
    public void saveAll(List<AppUbicacion> appUbicaciones) {
        dao.saveAll(appUbicaciones);
    }

    @Override
    public List<AppUbicacion> findAll() {
        Iterable<AppUbicacion> iterator = dao.findAll();

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }


    @Override
    public List<AppUbicacion> findAllByBajaLogica(Boolean bajaLogica) {
        Iterable<AppUbicacion> iterator = dao.findAllByBajaLogica(bajaLogica);

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }


    //    @Override
//    public List<AppUbicacionRelevamiento> findAll(Integer pageSize, Integer pageNo, String sortBy) {
//        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
//        Page<AppUbicacionRelevamiento> appUbicacionesRelevamientos = dao.findAll(paging);
//
//        return appUbicacionesRelevamientos.getContent();
//    }
//
    public void saveLatLong(MyObject object) {
        AppUbicacion ubicacion = this.get(object.getId());
        ubicacion.setLatitud(object.getLatitud());
        ubicacion.setLongitud(object.getLongitud());
        save(ubicacion);
    }

    @Override
    public List<MyObject> findAllByIdIn(List<Long> ids){
        return this.dao.findAllByIdIn(ids);
    }

    @Override
    public List<MyObject> findAllByIdUbicaciones(List<Long> ids){
        return this.dao.findAllByIdUbicaciones(ids);
    }
}
