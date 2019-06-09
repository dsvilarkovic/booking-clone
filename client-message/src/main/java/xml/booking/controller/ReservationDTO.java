package xml.booking.controller;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public @Data class ReservationDTO {
	private Long id;
	private Long beginningDate;
	private Long endDate;
	private Double finalPrice;
	private Boolean checkedIn;
	private Long ratingId;
	private Long userId;
	private List<Long> messages;
	
	
	
}
