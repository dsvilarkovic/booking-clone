package xml.booking.dto;

import javax.validation.constraints.NotNull;

import xml.booking.model.AccommodationCategory;
import xml.booking.model.AccommodationType;
import xml.booking.model.AdditionalService;


public class CodeBookDTO {
	public enum Type {ADDITIONAL_SERVICE, ACCOMMODATION_TYPE, ACCOMMODATION_CATEGORY};
	
	private Long id;
	@NotNull
	private String name;

	public CodeBookDTO() {}
	
	public CodeBookDTO(AdditionalService additionalService) {
		this.id = additionalService.getId();
		this.name = additionalService.getName();
	}

	public CodeBookDTO( AccommodationCategory accommodationCategory) {
		this.id = accommodationCategory.getId();
		this.name = accommodationCategory.getName();
	}

	public CodeBookDTO(AccommodationType accommodationType) {
		this.id = accommodationType.getId();
		this.name = accommodationType.getName();
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
	
}
