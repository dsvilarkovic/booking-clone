package xml.booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import xml.booking.model.AccommodationCategory;

/**
* Generated by Spring Data Generator on 29/05/2019
*/
@Repository
public interface AccommodationCategoryRepository extends JpaRepository<AccommodationCategory, Long>, JpaSpecificationExecutor<AccommodationCategory> {

}
