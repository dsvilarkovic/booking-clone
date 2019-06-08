package xml.booking.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public @Data class MessageDTO {
	private Long id;
	private String value;
	private Long reservationId;
	private Long userId;
	
}
