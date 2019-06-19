package xml.booking.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import xml.booking.dto.AccommodationUnitDTO;
import xml.booking.model.AccommodationUnit;
import xml.booking.model.Day;
import xml.booking.repositories.AccommodationUnitRepository;

/**
* Generated by Spring Data Generator on 19/06/2019
*/
@Component
public class AccommodationUnitManager {

	private AccommodationUnitRepository accommodationUnitRepository;

	@Autowired
	public AccommodationUnitManager(AccommodationUnitRepository accommodationUnitRepository) {
		this.accommodationUnitRepository = accommodationUnitRepository;
	}
	
	public List<AccommodationUnitDTO> getAllAccommodationUnitsList() {
		return this.accommodationUnitRepository.findByDeleted(false).stream().map(accommodationUnit -> new AccommodationUnitDTO(accommodationUnit)).collect(Collectors.toList());
	}
	
	public Page<AccommodationUnitDTO> getAllAccommodationUnitsPage(Pageable page) {
		return accommodationUnitRepository.findByDeleted(page, false).map(new Function<AccommodationUnit, AccommodationUnitDTO>() {
			@Override
			public AccommodationUnitDTO apply(AccommodationUnit accommodationUnit) {
				AccommodationUnitDTO accommodationUnitDTO = new AccommodationUnitDTO(accommodationUnit);
				return accommodationUnitDTO;
			}
		});
	}
	
	public AccommodationUnitDTO findById(Long id) {
		AccommodationUnit accommodationUnit = this.accommodationUnitRepository.findByIdAndDeleted(id, false);
		return (accommodationUnit == null)? null : new AccommodationUnitDTO(accommodationUnit);
	}
	
	public List<Day> findAllDays(Long id) {
		AccommodationUnit accommodationUnit = this.accommodationUnitRepository.findByIdAndDeleted(id, false);
		if(accommodationUnit == null)
			return null;
		return (accommodationUnit.getDay() == null)? new ArrayList<Day>() : accommodationUnit.getDay();
	}
	
	public List<Day> findDaysBetween(Long id, Long from, Long to) {
		AccommodationUnit accommodationUnit = this.accommodationUnitRepository.findByIdAndDeleted(id, false);
		if(accommodationUnit == null)
			return null;
		List<Day> days = this.accommodationUnitRepository.findDaysBetween(id, from, to);
		return (days == null)? new ArrayList<Day>() : days;
	}

}
