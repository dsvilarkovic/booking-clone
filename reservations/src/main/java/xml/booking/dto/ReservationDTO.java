package xml.booking.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
	private Long userId;
	@NotNull
	private Long accommodationUnitId;
	@NotNull @Positive
	private Integer numberOfPersons;
	
	public ReservationDTO(Reservation reservation) {
		this.id = reservation.getId();
		this.beginningDate = reservation.getBeginningDate();
		this.endDate = reservation.getEndDate();
		this.finalPrice = reservation.getFinalPrice();
		this.checkedIn = reservation.isCheckedIn();
		this.ratingId = (reservation.getRating() == null)? null: reservation.getRating().getId();
		this.commentId = (reservation.getComment() == null)? null: reservation.getComment().getId();
		this.userId = reservation.getUser().getId();
		this.accommodationUnitId = reservation.getAccommodationUnit().getId();
	}
	
	
	
	
}
