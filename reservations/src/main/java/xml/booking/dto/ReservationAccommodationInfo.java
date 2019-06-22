package xml.booking.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xml.booking.model.Reservation;

@Getter @Setter @NoArgsConstructor 
public class ReservationAccommodationInfo {
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
	@Positive
	private Integer numberOfPersons;
	private String accommodationUnitName;
	private String agentFirstName;
	private String agentLastName;
	private String accommodationName;
	private int messageCount;
	
	
	public ReservationAccommodationInfo(Reservation reservation) {
		this.id = reservation.getId();
		this.beginningDate = reservation.getBeginningDate();
		this.endDate = reservation.getEndDate();
		this.finalPrice = reservation.getFinalPrice();
		this.checkedIn = reservation.isCheckedIn();
		this.ratingId = (reservation.getRating() == null)? null: reservation.getRating().getId();
		this.commentId = (reservation.getComment() == null)? null: reservation.getComment().getId();
		this.userId = reservation.getUser().getId();
		this.accommodationUnitId = reservation.getAccommodationUnit().getId();
		this.messageCount = (reservation.getMessage() == null)? 0 : reservation.getMessage().size();
	}
}
