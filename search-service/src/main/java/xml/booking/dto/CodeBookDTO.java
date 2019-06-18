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

	public String getCodebookType() {
		return codebookType;
	}

	public void setCodebookType(String codebookType) {
		this.codebookType = codebookType;
	}
	
}
