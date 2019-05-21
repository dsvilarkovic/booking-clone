package xml.booking.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xml.booking.model.Reservation;
import xml.booking.repositories.ReservationRepository;

/**
* Generated by Spring Data Generator on 18/05/2019
*/
@Component
public class ReservationManager {

	private ReservationRepository reservationRepository;

	@Autowired
	public ReservationManager(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

}
