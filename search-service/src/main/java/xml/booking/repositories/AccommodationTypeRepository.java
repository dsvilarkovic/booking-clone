package xml.booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xml.booking.model.AccommodationType;

/**
* Generated by Spring Data Generator on 18/05/2019
*/
@Repository
public interface AccommodationTypeRepository extends JpaRepository<AccommodationType, Long>, JpaSpecificationExecutor<AccommodationType> {

	@Query("select act from Accommodation as acc inner join acc.accommodationUnit as au "
			 + "inner join acc.accommodationType as act where au.id = ?1")
		AccommodationType findTypeByAccommodationUnitId(Long accommodationUnitId);
}