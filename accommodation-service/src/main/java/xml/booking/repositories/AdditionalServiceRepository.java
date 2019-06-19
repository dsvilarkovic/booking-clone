package xml.booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import xml.booking.model.AdditionalService;

/**
* Generated by Spring Data Generator on 19/06/2019
*/
@Repository
public interface AdditionalServiceRepository extends JpaRepository<AdditionalService, Long>, JpaSpecificationExecutor<AdditionalService> {

}
