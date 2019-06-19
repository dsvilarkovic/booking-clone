package xml.booking.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xml.booking.model.Location;
import xml.booking.repositories.LocationRepository;

/**
* Generated by Spring Data Generator on 19/06/2019
*/
@Component
public class LocationManager {

	private LocationRepository locationRepository;

	@Autowired
	public LocationManager(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

}
