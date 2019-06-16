package xml.booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import xml.booking.model.AccommodationUnit;

/**
* Generated by Spring Data Generator on 16/06/2019
*/
@Repository
public interface AccommodationUnitRepository extends JpaRepository<AccommodationUnit, Long>, JpaSpecificationExecutor<AccommodationUnit> {

}
