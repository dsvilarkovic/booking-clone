package xml.booking.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xml.booking.model.AccommodationUnit;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
public @Data class AccommodationUnitDTO {
	private Long id;
	private String name;
	private Integer capacity;
	private Double defaultPrice;
	private Integer cancelationPeriod;
	
	
	public AccommodationUnitDTO(AccommodationUnit accommodationUnit) {
		this.id = accommodationUnit.getId();
		this.name = accommodationUnit.getName();
		this.capacity = accommodationUnit.getCapacity();
		this.defaultPrice = accommodationUnit.getDefaultPrice();
		this.cancelationPeriod = accommodationUnit.getCancelationPeriod();
	}
}
