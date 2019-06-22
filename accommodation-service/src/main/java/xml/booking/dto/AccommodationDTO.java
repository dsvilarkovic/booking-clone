package xml.booking.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xml.booking.model.Accommodation;
import xml.booking.model.Location;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
public @Data class AccommodationDTO {

	private Long id;
	private String name;
	private String description;
	private Long accommodationCategoryId;
	private Long accommodationTypeId;
	private Location location;
	private Long userId;
	
	
	
	public AccommodationDTO(Accommodation accommodation) {
		this.id = accommodation.getId();
		this.name = accommodation.getName();
		this.description = accommodation.getDescription();
		this.accommodationCategoryId = accommodation.getAccommodationCategory().getId();
		this.accommodationTypeId = accommodation.getAccommodationType().getId();
		this.location = accommodation.getLocation();
		this.userId = accommodation.getUser().getId();	}
}
