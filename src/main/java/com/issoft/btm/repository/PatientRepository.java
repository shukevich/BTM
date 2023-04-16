package com.issoft.btm.repository;

import com.issoft.btm.model.PatientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "patients", path = "patients")
public interface PatientRepository extends JpaRepository<PatientModel, Long>,
        PagingAndSortingRepository<PatientModel, Long> {
}
