package com.issoft.btm.repository;

import com.issoft.btm.dto.filter.OrderFilterDTO;
import com.issoft.btm.model.OrderModel;
import com.issoft.btm.repository.specification.OrderSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "orders", path = "orders")
public interface OrderRepository extends JpaRepository<OrderModel, Long>, JpaSpecificationExecutor<OrderModel>,
        PagingAndSortingRepository<OrderModel, Long> {

    default Page<OrderModel> findAllBySpecification(OrderFilterDTO orderFilterDTO, Pageable pageable) {
        return findAll(OrderSpecification.findAllByFilter(orderFilterDTO), pageable);
    }
}
