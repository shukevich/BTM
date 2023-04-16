package com.issoft.btm.repository.dictionary;

import com.issoft.btm.model.dictionaries.TransportationRequestStatusModel;
import com.issoft.btm.repository.DictionaryRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "transportationRequestStatusModels", path = "transportationRequestStatusModels")
public interface TransportationRequestStatusRepository
        extends DictionaryRepository<TransportationRequestStatusModel>,
        CrudRepository<TransportationRequestStatusModel, Integer>,
        PagingAndSortingRepository<TransportationRequestStatusModel, Integer> {
}
