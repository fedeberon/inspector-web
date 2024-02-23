package com.ideaas.services.dao;


import com.ideaas.services.bean.DateTimeUtil;
import com.ideaas.services.domain.*;
import com.ideaas.services.enums.ReservationState;
import com.ideaas.services.enums.TipoUsuarioEnum;
import com.ideaas.services.request.MapPoiRequest;
import com.ideaas.services.request.MapUbiActEspecialRequest;
import com.ideaas.services.request.MapUbicacionRequest;
import com.ideaas.services.request.UbicacionActualizacionRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
@Repository
public class FilterDaoImpl implements FilterDao {

    private static final String WHERE = " WHERE ";

    private static final String AND = " AND ";

    private static final String OR = " OR ";

    private static final String IN = " IN ";

    private static final String PREFIX_PARAMETER = ":";

    private static final String LEFT_PARENTHESIS = "(";
    private static final String RIGTH_PARENTHESIS = ")";

    private String where(){
        return WHERE;
    }


    private String and(){
        return AND;
    }

    private String or(){
        return OR;
    }

    private String in(){
        return IN;
    }

    @PersistenceContext
    EntityManager entityManager;

    public List<Long> findParametros(MapUbicacionRequest request, HashMap<Long, String> filteredParametros) {



       List<MapUbicacion> ubicaciones = new ArrayList<>();
        if (Objects.nonNull(filteredParametros) && !filteredParametros.isEmpty()) {
            for (Long key : filteredParametros.keySet()) {

                Query query = entityManager.createQuery("SELECT DISTINCT u FROM MapUbicacion u, MapUbicaionParametro mup, Parametro p WHERE mup.mapUbicacion.id = u.id AND mup.parametro.idParametro = (:idParametro) AND mup.descripcion LIKE :descripcion");
                query.setParameter("idParametro", key);
                query.setParameter("descripcion", "%" + filteredParametros.get(key) + "%");

                if(getAllResults(request)){
                    Integer maxResults = Integer.valueOf(request.getMaxResults());
                    query.setMaxResults(maxResults);
                    query.setFirstResult(request.getPage() * maxResults);
                }
                ubicaciones.addAll(query.getResultList());
            }
        }
        return ubicaciones.stream().map( u -> u.getId() ).collect(Collectors.toList());
    }

    @Override
    public List<MapUbicacion> find(MapUbicacionRequest request) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MapUbicacion> cr = cb.createQuery(MapUbicacion.class);
        boolean isEmptyRequest = true;
        Root<MapUbicacion> root = cr.from(MapUbicacion.class);
        root.alias("MapUbicacion");
        List<Predicate> conditions = new ArrayList<>();
        // CriteriaBuilderUtil<MapUbicacion> cbu = new CriteriaBuilderUtil(entityManager.getCriteriaBuilder());
        // The following joins are defined for efficiency reasons. Criteriaquery will perform all
        // the joins automatically, even if they are not defined in the following lines. However,
        // the criteria query generates a cross-type join automatically. On the contrary, the type
        // of join that is generated is of inner-type when defining it explicitly.
        root.join("mapBuses", JoinType.LEFT /* , JoinType.INNER */);
        root.join("mapEmpresa").join("usuarios", JoinType.LEFT);
        root.join("mapElemento");
        root.join("mapFormato");
        root.join("mapMedio");
        root.join("mapProvincia");
        root.join("mapLocalidad");
        root.join("mapUbicacionAltura");
        root.join("mapUbicacionVisibilidad");
        root.join("parametros", JoinType.LEFT).join("parametro", JoinType.LEFT);
        Join<MapUbicacion, MapReservacion> mumr = root.join("mapReservaciones", JoinType.LEFT);
        mumr.on(cb.notEqual(mumr.get("reservationState"), ReservationState.CANCELLED)).join("mapCampaign", JoinType.LEFT).join("mapClient", JoinType.LEFT);
        root.fetch("parametros", JoinType.LEFT).fetch("parametro", JoinType.LEFT);

        if(Objects.nonNull(request.getIsAdminUserOOH())&&request.getIsAdminUserOOH()){
            conditions.add(cb.in(root.get("mapEmpresa").get("id")).value(request.getUserMapCompanyIds()));

            HashMap<Long, String> filteredParametros = request.getFilteredParametros();
            if(Objects.nonNull(filteredParametros)&&!filteredParametros.isEmpty()) {
                isEmptyRequest = false;
                for (Map.Entry<Long, String> entry : filteredParametros.entrySet()) {
                    Subquery<Integer> subQuery =  cr.subquery(Integer.class);
                    Root<MapUbicaionParametro> mup =  subQuery.from(MapUbicaionParametro.class);
                    conditions.add(
                        cb.exists(subQuery.select(cb.literal(1))
                            .where(
                                cb.and(
                                    cb.equal(mup.get("mapUbicacion").get("id"), root.get("id")),
                                    cb.equal(mup.get("parametro").get("idParametro"),entry.getKey()),
                                    cb.like(mup.get("descripcion"),"%"+entry.getValue()+"%" )
                                )
                            )
                        )
                    );
                }
            }
        }

        if(request.getReservacionFechaDesde()!=null&&request.getReservacionFechaHasta()!=null) {
            isEmptyRequest = false;
        }

        // if(request.getEstadoReservacion()!=null&&!request.getEstadoReservacion().isEmpty()) {
            Long value = 0L;
            if(request.getEstadoReservacion()!=null&&!request.getEstadoReservacion().isEmpty()) {
                value = Arrays.stream(request.getEstadoReservacion().split("\\s*,\\s*")).map(Long::valueOf).reduce((p, n) -> n + p).orElse(0L);
            } else {
                value = 7L;
            }

            if((request.getMapClientes()!=null&&!request.getMapClientes().isEmpty())&&(request.getCampana()!=null&&!request.getCampana().isEmpty())) {
                if(value == 0L||value == 7L) {
                    value = 6L;
                }
            }
            if(request.getReservacionFechaDesde()!=null&&request.getReservacionFechaHasta()!=null) {
                isEmptyRequest = false;
                LocalDate startOfDay = request.getReservacionFechaDesde();
                LocalDate endOfDate  = request.getReservacionFechaHasta();

                Subquery<Integer> subQuery1 = cr.subquery(Integer.class);
                Root<MapReservacion> mr1 =  subQuery1.from(MapReservacion.class);
                Subquery<Integer> subQuery2 = cr.subquery(Integer.class);
                Root<MapReservacion> mr2 =  subQuery2.from(MapReservacion.class);
                Subquery<Integer> subQuery3 = cr.subquery(Integer.class);
                Root<MapReservacion> mr3 =  subQuery3.from(MapReservacion.class);
                Subquery<Integer> subQuery4 = cr.subquery(Integer.class);
                Root<MapReservacion> mr4 =  subQuery4.from(MapReservacion.class);
                Predicate ubicacionNotInReservaciones = cb.not(cb.exists(
                    subQuery1.select(cb.literal(1)).where(
                        cb.and(
                            cb.equal(mr1.get("mapUbicacion").get("id"), root.get("id")),
                            cb.or(
                                cb.between(mr1.get("startDate"), startOfDay, endOfDate),
                                cb.between(mr1.get("finishDate"), startOfDay, endOfDate),
                                cb.between(cb.literal(startOfDay), mr1.get("startDate"), mr1.get("finishDate")),
                                cb.between(cb.literal(endOfDate), mr1.get("startDate"), mr1.get("finishDate"))
                            )
                        )
                    )
                ));
                Predicate ubicacionInReservaciones = cb.exists(
                    subQuery2.select(cb.literal(1)).where(
                        cb.and(
                            cb.equal(mr2.get("mapUbicacion").get("id"), root.get("id")),
                            cb.or(
                                cb.between(mr2.get("startDate"), startOfDay, endOfDate),
                                cb.between(mr2.get("finishDate"), startOfDay, endOfDate),
                                cb.between(cb.literal(startOfDay), mr2.get("startDate"), mr2.get("finishDate")),
                                cb.between(cb.literal(endOfDate), mr2.get("startDate"), mr2.get("finishDate"))
                            ),
                            request.getMapClientes()!=null&&!request.getMapClientes().isEmpty() ?
                                cb.in(mr2.get("mapCampaign").get("mapClient").get("id")).value(Arrays.stream(request.getMapClientes().split(",")).map(Long::valueOf).collect(Collectors.toList())) :
                                cb.equal(cb.literal(1), cb.literal(1)),
                            request.getCampana()!=null&&!request.getCampana().isEmpty() ?
                                cb.in(mr2.get("mapCampaign").get("id")).value(Arrays.stream(request.getCampana().split(",")).map(Long::valueOf).collect(Collectors.toList())) :
                                cb.equal(cb.literal(1), cb.literal(1))
                        )
                    )
                );
                Predicate ubicacionInReservacionesAndNotIsConfirmada = cb.exists(
                    subQuery3.select(cb.literal(1)).where(
                        cb.and(
                            cb.equal(mr3.get("mapUbicacion").get("id"), root.get("id")),
                            cb.or(
                                cb.between(mr3.get("startDate"), startOfDay, endOfDate),
                                cb.between(mr3.get("finishDate"), startOfDay, endOfDate),
                                cb.between(cb.literal(startOfDay), mr3.get("startDate"), mr3.get("finishDate")),
                                cb.between(cb.literal(endOfDate), mr3.get("startDate"), mr3.get("finishDate"))
                            ),
                            cb.equal(mr3.get("reservationState"), ReservationState.NOT_CONFIRMED),
                            request.getMapClientes()!=null&&!request.getMapClientes().isEmpty() ?
                                cb.in(mr3.get("mapCampaign").get("mapClient").get("id")).value(Arrays.stream(request.getMapClientes().split(",")).map(Long::valueOf).collect(Collectors.toList())) :
                                cb.equal(cb.literal(1), cb.literal(1)),
                            request.getCampana()!=null&&!request.getCampana().isEmpty() ?
                                cb.in(mr3.get("mapCampaign").get("id")).value(Arrays.stream(request.getCampana().split(",")).map(Long::valueOf).collect(Collectors.toList())) :
                                cb.equal(cb.literal(1), cb.literal(1))
                        )
                    )
                );
                 Predicate ubicacionInReservacionesAndIsConfirmada = cb.exists(
                     subQuery4.select(cb.literal(1)).where(
                         cb.and(
                             cb.equal(mr4.get("mapUbicacion").get("id"), root.get("id")),
                             cb.or(
                                 cb.between(mr4.get("startDate"), startOfDay, endOfDate),
                                 cb.between(mr4.get("finishDate"), startOfDay, endOfDate),
                                 cb.between(cb.literal(startOfDay), mr4.get("startDate"), mr4.get("finishDate")),
                                 cb.between(cb.literal(endOfDate), mr4.get("startDate"), mr4.get("finishDate"))
                             ),
                             cb.equal(mr4.get("reservationState"), ReservationState.CONFIRMED),
                             request.getMapClientes()!=null&&!request.getMapClientes().isEmpty() ?
                                 cb.in(mr4.get("mapCampaign").get("mapClient").get("id")).value(Arrays.stream(request.getMapClientes().split(",")).map(Long::valueOf).collect(Collectors.toList())) :
                                 cb.equal(cb.literal(1), cb.literal(1)),
                             request.getCampana()!=null&&!request.getCampana().isEmpty() ?
                                 cb.in(mr4.get("mapCampaign").get("id")).value(Arrays.stream(request.getCampana().split(",")).map(Long::valueOf).collect(Collectors.toList())) :
                                 cb.equal(cb.literal(1), cb.literal(1))
                         )
                     )
                 );
                 Predicate ubicacionNotInOrInReservacionesAndIsNotConfirmada = cb.or(ubicacionNotInReservaciones, ubicacionInReservacionesAndNotIsConfirmada);
                 Predicate ubicacionNotInOrInReservacionesAndIsConfirmada = cb.or(ubicacionNotInReservaciones, ubicacionInReservacionesAndIsConfirmada);
                 Predicate ubicacionNotInOrReservaciones = cb.or(ubicacionNotInReservaciones, ubicacionInReservaciones);
                 if(value == 0L||value == 7L) {conditions.add(ubicacionNotInOrReservaciones);}
                 if(value == 1L)              {conditions.add(ubicacionNotInReservaciones);}
                 if(value == 2L)              {conditions.add(ubicacionInReservacionesAndNotIsConfirmada);}
                 if(value == 3L)              {conditions.add(ubicacionNotInOrInReservacionesAndIsNotConfirmada);}
                 if(value == 4L)              {conditions.add(ubicacionInReservacionesAndIsConfirmada);}
                 if(value == 5L)              {conditions.add(ubicacionNotInOrInReservacionesAndIsConfirmada);}
                 if(value == 6L)              {conditions.add(ubicacionInReservaciones);}
            }
        // } else {
        //     conditions.add(cb.or(cb.notEqual(mumr.get("reservationState"), ReservationState.CANCELLED), cb.isNull(mumr.get("reservationState"))));
        // }

        if(request.getMapClientes()!=null&&!request.getMapClientes().isEmpty()) {
            isEmptyRequest = false;
            // conditions.add(cb.in(mumr.get("mapCampaign").get("mapClient").get("id")).value(Arrays.stream(request.getMapClientes().split(",")).map(Long::valueOf).collect(Collectors.toList())));
        }

        if(request.getCampana()!=null&&!request.getCampana().isEmpty()) {
            isEmptyRequest = false;
            // conditions.add(cb.in(mumr.get("mapCampaign").get("id")).value(Arrays.stream(request.getCampana().split(",")).map(Long::valueOf).collect(Collectors.toList())));
        }

        if(request.getMapEmpresa() != null && !request.getMapEmpresa().isEmpty()) {
            isEmptyRequest = false;
            conditions.add(cb.in(root.get("mapEmpresa").get("descripcion")).value(Arrays.asList(request.getMapEmpresa().split(","))));
        }

        if(Objects.nonNull(request.getMapElemento()) && !request.getMapElemento().trim().isEmpty()){
            isEmptyRequest = false;
            conditions.add(cb.in(root.get("mapElemento").get("descripcion")).value(Arrays.asList(request.getMapElemento().split(","))));
        }

        if(Objects.nonNull(request.getMapFormato()) && !request.getMapFormato().trim().isEmpty()){
            isEmptyRequest = false;
            conditions.add(cb.in(root.get("mapFormato").get("descripcion")).value(Arrays.asList(request.getMapFormato().split(","))));
        }

        if(Objects.nonNull(request.getMapMedio()) && !request.getMapMedio().trim().isEmpty()){
            isEmptyRequest = false;
            conditions.add(cb.in(root.get("mapMedio").get("descripcion")).value(Arrays.asList(request.getMapMedio().split(","))));
        }

        if(Objects.nonNull(request.getMapProvincia()) && !request.getMapProvincia().trim().isEmpty()){
            isEmptyRequest = false;
            conditions.add(cb.in(root.get("mapProvincia").get("descripcion")).value(Arrays.asList(request.getMapProvincia().split(","))));
        }

        if(Objects.nonNull(request.getMapLocalidad()) && !request.getMapLocalidad().trim().isEmpty()){
            isEmptyRequest = false;
            conditions.add(cb.in(root.get("mapLocalidad").get("descripcion")).value(Arrays.asList(request.getMapLocalidad().split(","))));
        }

        if(Objects.nonNull(request.getBajaLogica()) && !request.getBajaLogica().trim().isEmpty()){
            isEmptyRequest = false;
            conditions.add(cb.in(root.get("bajaLogica")).value(Arrays.stream(request.getBajaLogica().split("\\s*,\\s*")).map(Boolean::valueOf).collect(Collectors.toList())));
        }

        if(Objects.nonNull(request.getLatLngEmpty()) && !request.getLatLngEmpty().trim().isEmpty()){
            isEmptyRequest = false;
            if(request.getLatLngEmpty().equals("true")){
                conditions.add(cb.or(
                        cb.isNull(root.get("latitud")),
                        cb.isNull(root.get("longitud"))
                ));
            } else{
                conditions.add(cb.or(
                        cb.isNotNull(root.get("latitud")),
                        cb.isNotNull(root.get("longitud"))
                ));
            }
        }

        if(Objects.nonNull(request.getFechaAlta())){
            isEmptyRequest = false;
            LocalDateTime  dateToSearch = DateTimeUtil.convertToLocalDateTimeViaSqlTimestamp(request.getFechaAlta());
            LocalDate localDate = DateTimeUtil.convertToLocalDateViaInstant(request.getFechaAlta());
            LocalDateTime startOfDay = localDate.atStartOfDay();
            LocalDateTime endOfDate = dateToSearch.toLocalDate().atTime(LocalTime.MAX);

            conditions.add(cb.between(root.get("fechaAlta"),  startOfDay, endOfDate));
        }

        if(Objects.nonNull(request.getIdsSelected())){
            isEmptyRequest = false;
            conditions.add(cb.in(root.get("id")).value(request.getIdsSelected().stream()));
        }

        if(Objects.nonNull(request.getIdsSearch()) && !request.getIdsSearch().trim().isEmpty()){
            isEmptyRequest = false;
            conditions.add(cb.in(root.get("id")).value(Arrays.stream(request.getIdsSearch().split("\\s*,\\s*")).map(Long::valueOf).collect(Collectors.toList())));
        }

        if(Objects.nonNull(request.getSearchValue()) && !request.getSearchValue().trim().isEmpty()){
            isEmptyRequest = false;
            conditions.add(
                    cb.or(
                            cb.like(root.get("direccion"), '%'+request.getSearchValue()+'%'),
                            cb.like(root.get("mapUbicacionAltura").get("descripcion"), '%'+request.getSearchValue()+'%'),
                            cb.like(root.get("mapUbicacionVisibilidad").get("descripcion"), '%'+request.getSearchValue()+'%'),
                            cb.like(root.get("idReferencia"), '%'+request.getSearchValue()+'%')
                    )
            );
        }

        TypedQuery<MapUbicacion> query = null;

        if(isEmptyRequest){
            String companyIds = request.getUserMapCompanyIds().stream().map(String::valueOf).collect(Collectors.joining(","));
            Long idUbiacaion = Long.valueOf(entityManager.createNativeQuery(""+
                    "select distinct mu.idUbicacion "+
                    "from map_ubicaciones mu "+
                    "where (-1) in ("+companyIds+") or mu.idEmpresa in ("+companyIds+") " +
                    "order by mu.idUbicacion desc limit 1 offset 9 "
            ).getSingleResult().toString());
            conditions.add(cb.greaterThanOrEqualTo(root.get("id"), idUbiacaion));
            cr.select(root).distinct(true).where( cb.and(conditions.toArray( new Predicate[0] )) ).orderBy(cb.asc(root.get("id")));

            query = this.entityManager.createQuery(cr);
        } else {
            cr.select(root).distinct(true).where( cb.and(conditions.toArray( new Predicate[0] )) ).orderBy(cb.asc(root.get("id")));

            query = this.entityManager.createQuery(cr);
        }

        return query.getResultList();
    }

    @Override
    public List<MapUbicacion> filterSearchUbicacion(Map<String, String> clauses){
        StringBuilder builder = new StringBuilder();
        builder.append("select u from MapUbicacion u ");
        builder.append("where 1 = 1");

        clauses.forEach((k, v) -> {
            if(Objects.isNull(v) || v.isEmpty()) return;
            String nameParameter = LEFT_PARENTHESIS.concat(PREFIX_PARAMETER.concat(k)).concat(RIGTH_PARENTHESIS).replace(".","");
            builder.append(AND);
            builder.append(k).append(in()).append(nameParameter);

        });

        Query query = entityManager.createQuery(builder.toString());
        clauses.forEach((k, v) -> {
            if(Objects.isNull(v) || v.isEmpty()) return;
            String nameParameter = k.replace(".","");
            query.setParameter(nameParameter, Arrays.asList(v.split(",")));
        });
        return query.getResultList();
    }

    @Override
    public List<MapUbicacionActualizacion> find(UbicacionActualizacionRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append("select u from MapUbicacionActualizacion u ");
        boolean isFirstClause = true;
        boolean isLastClause = false;

        if(Objects.nonNull(request.getIdsSearch()) && !request.getIdsSearch().trim().isEmpty()){
            builder.append(isFirstClause ? where() : and());
            builder.append(" u.id.mapUbicacionId in (:idsSearch)");

            isFirstClause = false;
        }

        isLastClause = true;

        if(isLastClause){
            builder.append("order by u.id.mapUbicacionId desc");
        }

        Query query = entityManager.createQuery(builder.toString());

        if(Objects.nonNull(request.getIdsSearch()) && !request.getIdsSearch().isEmpty()){
            List<String> idsString = Arrays.asList(request.getIdsSearch().split("\\s*,\\s*"));
            List<Long> idsSearch = new ArrayList<>();

            for(String s : idsString) idsSearch.add(Long.valueOf(s));

            query.setParameter("idsSearch", idsSearch );
        }

        // if(getAllResultsUbiActualizacion(request)){
        Integer maxResults = Integer.valueOf("1000");
        query.setMaxResults(maxResults);
        query.setFirstResult(request.getPage() * maxResults);
        // }

        return query.getResultList();
    }

    @Override
    public List<MapUbicacionActEspecial> find(MapUbiActEspecialRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append("select u from MapUbicacionActEspecial u ");
        boolean isFirstClause = true;
        boolean isLastClause = false;

        if(Objects.nonNull(request.getIdsSearch()) && !request.getIdsSearch().trim().isEmpty()){
            builder.append(isFirstClause ? where() : and());
            builder.append(" u.id.mapUbicacionId in (:idsSearch)");

            isFirstClause = false;
        }

        isLastClause = true;

        if(isLastClause){
            builder.append("order by u.id.mapUbicacionId desc");
        }

        Query query = entityManager.createQuery(builder.toString());

        if(Objects.nonNull(request.getIdsSearch()) && !request.getIdsSearch().isEmpty()){
            List<String> idsString = Arrays.asList(request.getIdsSearch().split("\\s*,\\s*"));
            List<Long> idsSearch = new ArrayList<>();

            for(String s : idsString) idsSearch.add(Long.valueOf(s));

            query.setParameter("idsSearch", idsSearch );
        }

        if(getAllResultsUbiActEspecial(request)){
            Integer maxResults = Integer.valueOf(request.getMaxResults());

            query.setMaxResults(maxResults);
            query.setFirstResult(request.getPage() * maxResults);
        }

        return query.getResultList();
    }

    @Override
    public List<MapPoi> find(MapPoiRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append("select u from MapPoi u ");
        boolean isFirstClause = true;

        if(Objects.nonNull(request.getMapPoiEntidad()) && !request.getMapPoiEntidad().trim().isEmpty()){
            builder.append(isFirstClause ? where() : and());
            builder.append("u.mapPoiEntidad.descripcion in (:mapPoiEntidad)");

            isFirstClause = false;
        }

        Query query = entityManager.createQuery(builder.toString());

        if(Objects.nonNull(request.getMapPoiEntidad()) && !request.getMapPoiEntidad().trim().isEmpty()){
            query.setParameter("mapPoiEntidad", Arrays.asList(request.getMapPoiEntidad().split(",")));
        }
        if(getAllResultsMapPoi(request)){
            Integer maxResults = Integer.valueOf(request.getMaxResults());

            query.setMaxResults(maxResults);
            query.setFirstResult(request.getPage() * maxResults);
        }

        return query.getResultList();
    }

    public Boolean getAllResults(MapUbicacionRequest request){
        return !request.getMaxResults().equals("-1");
    }

    public Boolean getAllResultsUbiActualizacion(UbicacionActualizacionRequest request){
        return !request.getMaxResults().equals("-1");
    }

    public Boolean getAllResultsUbiActEspecial(MapUbiActEspecialRequest request){
        return !request.getMaxResults().equals("-1");
    }

    public Boolean getAllResultsMapPoi(MapPoiRequest request){
        return !request.getMaxResults().equals("-1");
    }
}
