package xml.booking.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xml.booking.model.AccommodationType;
import xml.booking.repositories.AccommodationTypeRepository;

/**
* Generated by Spring Data Generator on 07/06/2019
*/
@Component
public class AccommodationTypeManager {

	private AccommodationTypeRepository accommodationTypeRepository;

	@Autowired
	public AccommodationTypeManager(AccommodationTypeRepository accommodationTypeRepository) {
		this.accommodationTypeRepository = accommodationTypeRepository;
	}

}
