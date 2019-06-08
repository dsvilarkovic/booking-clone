package xml.booking.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import xml.booking.model.AccommodationCategory;
import xml.booking.model.AccommodationType;
import xml.booking.model.AdditionalService;

@Getter
@Setter
public @Data class CodebookDTO {
	public enum Type {ADDITIONAL_SERVICE, ACCOMMODATION_TYPE, ACCOMMODATION_CATEGORY};
	private long id;
	private String name;
	private String codebookType;

	public CodebookDTO(AdditionalService additionalService) {
		this.id = additionalService.getId();
		this.name = additionalService.getName();

	}

	public CodebookDTO( AccommodationCategory accommodationCategory) {
		this.id = accommodationCategory.getId();
		this.name = accommodationCategory.getName();

	}

	public CodebookDTO(AccommodationType accommodationType) {
		this.id = accommodationType.getId();
		this.name = accommodationType.getName();

	}
}
