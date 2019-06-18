package xml.booking.dto;

import xml.booking.model.AccommodationUnit;

public class AccommodationUnitDTO {
	
	private Long id;
	private String name;
	private Integer capacity;
	private Double defaultPrice;
	private Integer cancelationPeriod;
	private String accommodation;
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
		
	public AccommodationUnitDTO(Long id, String name, Integer capacity, Double defaultPrice,
			Integer cancelationPeriod) {
		super();
		this.id = id;
		this.name = name;
		this.capacity = capacity;
		this.defaultPrice = defaultPrice;
		this.cancelationPeriod = cancelationPeriod;
	}

	public AccommodationUnitDTO(AccommodationUnit accommodationUnit) {
		this.id = accommodationUnit.getId();
		this.name = accommodationUnit.getName();
		this.capacity = accommodationUnit.getCapacity();
		this.defaultPrice = accommodationUnit.getDefaultPrice();
		this.cancelationPeriod = accommodationUnit.getCancelationPeriod();
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
	public Double getDefaultPrice() {
		return defaultPrice;
	}
	public void setDefaultPrice(Double defaultPrice) {
		this.defaultPrice = defaultPrice;
	}
	public Integer getCancelationPeriod() {
		return cancelationPeriod;
	}
	public void setCancelationPeriod(Integer cancelationPeriod) {
		this.cancelationPeriod = cancelationPeriod;
	}
	
	

}
