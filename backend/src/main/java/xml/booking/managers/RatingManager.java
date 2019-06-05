package xml.booking.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xml.booking.model.Rating;
import xml.booking.repositories.RatingRepository;

/**
* Generated by Spring Data Generator on 18/05/2019
*/
@Component
public class RatingManager {

	private RatingRepository ratingRepository;

	@Autowired
	public RatingManager(RatingRepository ratingRepository) {
		this.ratingRepository = ratingRepository;
	}

}