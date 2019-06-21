package xml.booking.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import xml.booking.model.Reservation;

@Setter @Getter @ToString @EqualsAndHashCode @NoArgsConstructor
public @Data class ReservationDTO {
	private Long id;
	@NotNull
	private Long beginningDate;
	@NotNull
	private Long endDate;
	private BigDecimal finalPrice;
	private boolean checkedIn;
	private Long ratingId;
	private Long commentId;
	@NotNull
	private Long userId;
	@NotNull
	private Long accommodationUnitId;
	@NotNull
	private Long numberOfPersons;
	
	
	
	
	
}
