package xml.booking.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import xml.booking.model.AccommodationCategory;
import xml.booking.model.AccommodationType;
import xml.booking.model.AdditionalService;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public @Data class CodeBookDTO {
	public enum Type {ADDITIONAL_SERVICE, ACCOMMODATION_TYPE, ACCOMMODATION_CATEGORY};
	
	private Long id;
	@NotNull
	private String name;
	@NotNull
	private String codebookType;

	public CodeBookDTO() {}
	public CodeBookDTO(AdditionalService additionalService) {
		this.id = additionalService.getId();
		this.name = additionalService.getName();
		this.codebookType = Type.ADDITIONAL_SERVICE.toString();

	}

	public CodeBookDTO( AccommodationCategory accommodationCategory) {
		this.id = accommodationCategory.getId();
		this.name = accommodationCategory.getName();
		this.codebookType = Type.ACCOMMODATION_CATEGORY.toString();

	}

	public CodeBookDTO(AccommodationType accommodationType) {
		this.id = accommodationType.getId();
		this.name = accommodationType.getName();
		this.codebookType = Type.ACCOMMODATION_TYPE.toString();

	}
}