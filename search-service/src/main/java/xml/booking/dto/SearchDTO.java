package xml.booking.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchDTO {
	
	private String location;
	private Date beginningDate;
	private Date endDate;
	private Integer numberOfPersons;
	private Long accommodationType;
	private Long accommodationCategory;
	private List<Long> additionalServices;
	private Double distance;
	
	public SearchDTO() {
		this.additionalServices = new ArrayList<Long>();
	}
	
	public SearchDTO(String location, Date beginningDate, Date endDate, Integer numberOfPersons, Long accommodationType,
			Long accommodationCategory, List<Long> additionalServices, Double distance) {
		super();
		this.location = location;
		this.beginningDate = beginningDate;
		this.endDate = endDate;
		this.numberOfPersons = numberOfPersons;
		this.accommodationType = accommodationType;
		this.accommodationCategory = accommodationCategory;
		this.additionalServices = additionalServices;
		this.distance = distance;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getBeginningDate() {
		return beginningDate;
	}

	public void setBeginningDate(Date beginningDate) {
		this.beginningDate = beginningDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getNumberOfPersons() {
		return numberOfPersons;
	}

	public void setNumberOfPersons(Integer numberOfPersons) {
		this.numberOfPersons = numberOfPersons;
	}

	public Long getAccommodationType() {
		return accommodationType;
	}

	public void setAccommodationType(Long accommodationType) {
		this.accommodationType = accommodationType;
	}

	public Long getAccommodationCategory() {
		return accommodationCategory;
	}

	public void setAccommodationCategory(Long accommodationCategory) {
		this.accommodationCategory = accommodationCategory;
	}

	public List<Long> getAdditionalServices() {
		return additionalServices;
	}

	public void setAdditionalServices(List<Long> additionalServices) {
		this.additionalServices = additionalServices;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}	

}
