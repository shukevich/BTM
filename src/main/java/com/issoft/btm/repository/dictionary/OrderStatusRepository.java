package com.issoft.btm.repository.dictionary;

import com.issoft.btm.model.dictionaries.OrderStatusModel;
import com.issoft.btm.repository.DictionaryRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "orderStatuses", path = "orderStatuses")
public interface OrderStatusRepository extends DictionaryRepository<OrderStatusModel>,
        CrudRepository<OrderStatusModel, Integer>,
        PagingAndSortingRepository<OrderStatusModel, Integer> {
}
