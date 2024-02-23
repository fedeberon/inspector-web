package com.ideaas.services.dao;

import com.ideaas.services.domain.AllowedStatus;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllowedStatusDao extends PagingAndSortingRepository<AllowedStatus , Long> {

   Boolean existsByDescripcion(String descripcion);
}
