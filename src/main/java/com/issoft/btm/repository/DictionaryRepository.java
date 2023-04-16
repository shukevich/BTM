package com.issoft.btm.repository;

import com.issoft.btm.model.dictionaries.base.BaseDictionary;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DictionaryRepository<T extends BaseDictionary> {
}
