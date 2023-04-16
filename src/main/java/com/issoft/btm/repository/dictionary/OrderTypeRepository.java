package com.issoft.btm.repository.dictionary;

import com.issoft.btm.model.dictionaries.OrderTypeModel;
import com.issoft.btm.repository.DictionaryRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "orderTypes", path = "orderTypes")
public interface OrderTypeRepository
        extends DictionaryRepository<OrderTypeModel>, CrudRepository<OrderTypeModel, Integer>,
        PagingAndSortingRepository<OrderTypeModel, Integer> {
}
