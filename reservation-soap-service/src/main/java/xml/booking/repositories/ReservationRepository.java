package xml.booking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xml.booking.model.Reservation;

/**
* Generated by Spring Data Generator on 09/06/2019
*/
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {

	List<Reservation> findByUserId(Long id);

	Reservation findByIdAndDeleted(Long reservationId, boolean b);

//	List<Reservation> findByUserIdAndDeleted(Long id, boolean b);

	@Query(value = "select r.* " + 
			"from  reservation r, accommodation a, accommodation_accommodation_unit aau, accommodation_unit au " + 
			"where a.id = aau.accommodation_id and au.id = aau.accommodation_unit_id" + 
			"	and a.user_id = ?1 and r.accommodation_unit_id = au.id" + 
			"	and r.deleted = ?2", nativeQuery = true)	
	List<Reservation> findReservations(Long id, boolean b);
}
