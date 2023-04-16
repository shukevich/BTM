package com.issoft.btm.repository.dictionary;

import com.issoft.btm.model.dictionaries.ContactTypeModel;
import com.issoft.btm.repository.DictionaryRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "contactTypes", path = "contactTypes")
public interface ContactTypeRepository extends DictionaryRepository<ContactTypeModel>,
        CrudRepository<ContactTypeModel, Integer>,
        PagingAndSortingRepository<ContactTypeModel, Integer> {
}
