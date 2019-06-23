package xml.booking.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchDTO {
	
	private String location;
	private Long beginningDate;
	private Long endDate;
	private Integer numberOfPersons;
	private CodeBookDTO accommodationType;
	private CodeBookDTO accommodationCategory;
	private List<CodeBookDTO> additionalServices;
	private Double distance;
	private Double userLongitude;
	private Double userLatitude;
	
	public SearchDTO() {
		this.additionalServices = new ArrayList<CodeBookDTO>();
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getBeginningDate() {
		return beginningDate;
	}

	public void setBeginningDate(Long beginningDate) {
		this.beginningDate = beginningDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	public Integer getNumberOfPersons() {
		return numberOfPersons;
	}

	public void setNumberOfPersons(Integer numberOfPersons) {
		this.numberOfPersons = numberOfPersons;
	}

	public CodeBookDTO getAccommodationType() {
		return accommodationType;
	}

	public void setAccommodationType(CodeBookDTO accommodationType) {
		this.accommodationType = accommodationType;
	}

	public CodeBookDTO getAccommodationCategory() {
		return accommodationCategory;
	}

	public void setAccommodationCategory(CodeBookDTO accommodationCategory) {
		this.accommodationCategory = accommodationCategory;
	}

	public List<CodeBookDTO> getAdditionalServices() {
		return additionalServices;
	}

	public void setAdditionalServices(List<CodeBookDTO> additionalServices) {
		this.additionalServices = additionalServices;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Double getUserLongitude() {
		return userLongitude;
	}

	public void setUserLongitude(Double userLongitude) {
		this.userLongitude = userLongitude;
	}

	public Double getUserLatitude() {
		return userLatitude;
	}

	public void setUserLatitude(Double userLatitude) {
		this.userLatitude = userLatitude;
	}	

}
