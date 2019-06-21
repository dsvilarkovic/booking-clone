package xml.booking.comments.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xml.booking.comments.model.AccommodationUnit;
import xml.booking.comments.repositories.AccommodationUnitRepository;

/**
* Generated by Spring Data Generator on 16/06/2019
*/
@Component
public class AccommodationUnitManager {

	private AccommodationUnitRepository accommodationUnitRepository;

	@Autowired
	public AccommodationUnitManager(AccommodationUnitRepository accommodationUnitRepository) {
		this.accommodationUnitRepository = accommodationUnitRepository;
	}

}