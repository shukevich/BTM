package com.issoft.btm.repository;

import com.issoft.btm.model.DonorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "donors", path = "donors")
public interface DonorRepository extends JpaRepository<DonorModel, Long>,
        PagingAndSortingRepository<DonorModel, Long> {
}
