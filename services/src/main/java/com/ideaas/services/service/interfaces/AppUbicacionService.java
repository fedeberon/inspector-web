package com.ideaas.services.service.interfaces;

import com.ideaas.services.bean.MyObject;
import com.ideaas.services.domain.AppFormulario;
import com.ideaas.services.domain.AppUbicacion;
import com.ideaas.services.domain.AppUbicacionRelevamiento;

import java.util.List;
import java.util.Optional;

public interface AppUbicacionService {

    AppUbicacion get(Long id);

    AppUbicacion findByOneClaveUbi(Long oneClaveUbi);

    AppUbicacion save(AppUbicacion appUbicacion);

    void saveAll(List<AppUbicacion> appUbicaciones);

    List<AppUbicacion> findAll();

//    List<AppUbicacionRelevamiento> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<AppUbicacion> findAllByBajaLogica(Boolean bajaLogica);

    void saveLatLong(MyObject object);

    List<MyObject> findAllByIdIn(List<Long> ids);

    List<MyObject> findAllByIdUbicaciones(List<Long> ids);
}
