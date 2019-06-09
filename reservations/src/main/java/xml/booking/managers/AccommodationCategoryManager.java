package xml.booking.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xml.booking.model.AccommodationCategory;
import xml.booking.repositories.AccommodationCategoryRepository;

/**
* Generated by Spring Data Generator on 08/06/2019
*/
@Component
public class AccommodationCategoryManager {

	private AccommodationCategoryRepository accommodationCategoryRepository;

	@Autowired
	public AccommodationCategoryManager(AccommodationCategoryRepository accommodationCategoryRepository) {
		this.accommodationCategoryRepository = accommodationCategoryRepository;
	}

}
