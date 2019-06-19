package xml.booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xml.booking.model.Accommodation;
import xml.booking.model.AccommodationType;

/**
* Generated by Spring Data Generator on 18/05/2019
*/
@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long>, JpaSpecificationExecutor<Accommodation> {

	@Query("select acc from Accommodation as acc inner join acc.accommodationUnit as au where au.id = ?1")
	Accommodation findAccommodationByAccommodationUnitId(Long accommodationUnitId);
}