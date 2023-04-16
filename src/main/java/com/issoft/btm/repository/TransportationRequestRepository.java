package com.issoft.btm.repository;

import com.issoft.btm.dto.filter.TransportationRequestFilterDTO;
import com.issoft.btm.model.TransportationRequestModel;
import com.issoft.btm.repository.specification.TransportationRequestSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "transportationRequests", path = "transportationRequests")
public interface TransportationRequestRepository extends JpaRepository<TransportationRequestModel, Long>,
        JpaSpecificationExecutor<TransportationRequestModel>, PagingAndSortingRepository<TransportationRequestModel, Long> {

    Page<TransportationRequestModel> findTransportationRequestModelsByOrderId(Long orderId, Pageable pageable);

    default Page<TransportationRequestModel> findAllBySpecification(TransportationRequestFilterDTO filterDTO, Pageable pageable) {
        return findAll(TransportationRequestSpecification.findAllByFilter(filterDTO), pageable);
    }

}
