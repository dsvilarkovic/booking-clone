package xml.booking.dto;

import xml.booking.model.AccommodationUnit;

public class AccommodationUnitDTO {
	
	private Long id;
	private String name;
	private Integer capacity;
	private Double calculatedPrice;
	private Double defaultPrice;
	private Integer cancelationPeriod;
	private String accommodation;
	private Long accommodationId;
	private String accommodationType;
	private String accommodationCategory;
	private String description;
	
	public String getAccommodation() {
		return accommodation;
	}

	public void setAccommodation(String accommodation) {
		this.accommodation = accommodation;
	}

	public String getAccommodationType() {
		return accommodationType;
	}

	public void setAccommodationType(String accommodationType) {
		this.accommodationType = accommodationType;
	}

	public String getAccommodationCategory() {
		return accommodationCategory;
	}

	public void setAccommodationCategory(String accommodationCategory) {
		this.accommodationCategory = accommodationCategory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AccommodationUnitDTO() {
		
	}
		
	public AccommodationUnitDTO(AccommodationUnit accommodationUnit, Double calculatedPrice, String accommodation, String accommodationCategory,
			                    String accommodationType, String description, Long accommodationId) {
		this.id = accommodationUnit.getId();
		this.name = accommodationUnit.getName();
		this.capacity = accommodationUnit.getCapacity();
		this.cancelationPeriod = accommodationUnit.getCancelationPeriod();
		this.calculatedPrice = calculatedPrice;
		this.accommodation = accommodation;
		this.accommodationCategory = accommodationCategory;
		this.accommodationType = accommodationType;
		this.description = description;
		this.defaultPrice = accommodationUnit.getDefaultPrice();
		this.accommodationId = accommodationId;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	public Double getCalculatedPrice() {
		return calculatedPrice;
	}
	public void setCalculatedPrice(Double calculatedPrice) {
		this.calculatedPrice = calculatedPrice;
	}
	public Integer getCancelationPeriod() {
		return cancelationPeriod;
	}
	public void setCancelationPeriod(Integer cancelationPeriod) {
		this.cancelationPeriod = cancelationPeriod;
	}

	public Double getDefaultPrice() {
		return defaultPrice;
	}

	public void setDefaultPrice(Double defaultPrice) {
		this.defaultPrice = defaultPrice;
	}

	public Long getAccommodationId() {
		return accommodationId;
	}

	public void setAccommodationId(Long accommodationId) {
		this.accommodationId = accommodationId;
	}
	
}
