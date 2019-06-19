package xml.booking.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xml.booking.model.Day;
import xml.booking.repositories.DayRepository;

/**
* Generated by Spring Data Generator on 19/06/2019
*/
@Component
public class DayManager {

	private DayRepository dayRepository;

	@Autowired
	public DayManager(DayRepository dayRepository) {
		this.dayRepository = dayRepository;
	}

}
