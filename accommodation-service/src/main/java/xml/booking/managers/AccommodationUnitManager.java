package xml.booking.managers;

import java.math.BigDecimal;
import java.sql.Date;
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
	
	public AccommodationUnit findById(Long id) {
		AccommodationUnit accommodationUnit = this.accommodationUnitRepository.findByIdAndDeleted(id, false);
		return accommodationUnit;
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
	
	public boolean isAccommodationUnitFreeForReservation(AccommodationUnit unit, Long from, Long to) {
		AccommodationUnit found = this.accommodationUnitRepository.checkIsFreeForReservation(unit.getId(), from, to);
		return (found == null) ? false : true;
	}
	
	public BigDecimal findPrice(AccommodationUnit unit, Long from , Long to) {
		List<Day> days = this.findDaysBetween(unit.getId(), from, to);
		System.out.println("Days");
		double price =0;
		for(Day day : days) {
			price += day.getPrice().doubleValue();
		}
		long numberOfDays = numberOfDays(new Date(from), new Date(to));
		System.out.println(numberOfDays);
		price += (numberOfDays - days.size()) * unit.getDefaultPrice();
		System.out.println(price);
		return new BigDecimal(price);
	}
	
	/**
	 * Metoda za izracuvanje dana proteklih izmedju dva datuma
	 * 
	 * @param date1 datum 1
	 * @param date2 datum 2
	 * @return broj dana
	 */
	private long numberOfDays(Date date1, Date date2) {

		java.util.Date localDate1 = new java.util.Date(date1.getTime());
		java.util.Date localDate2 = new java.util.Date(date2.getTime());
		Long number;
		if (localDate1.after(localDate2)) {
			number = localDate1.getTime() - localDate2.getTime();
		} else {
			number = localDate2.getTime() - localDate1.getTime();
		}


		return number / 86400000;
	}
}
