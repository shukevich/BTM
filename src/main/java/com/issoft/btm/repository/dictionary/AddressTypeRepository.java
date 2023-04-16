package com.issoft.btm.repository.dictionary;

import com.issoft.btm.model.dictionaries.AddressTypeModel;
import com.issoft.btm.repository.DictionaryRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "addressTypes", path = "addressTypes")
public interface AddressTypeRepository extends DictionaryRepository<AddressTypeModel>, CrudRepository<AddressTypeModel, Integer>,
        PagingAndSortingRepository<AddressTypeModel, Integer> {
}
