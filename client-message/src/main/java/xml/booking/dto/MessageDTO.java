package xml.booking.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import xml.booking.model.Message;

@Setter @Getter @EqualsAndHashCode @ToString
public @Data class MessageDTO {
	private Long id;
	@NotNull
	private String value;
	private Long date;
	@NotNull
	private Long reservationId;
	private Long userId;
	
	public MessageDTO() {}
	
	public MessageDTO(Message message) {
		this.id = message.getId();
		this.value = message.getValue();
		this.userId = message.getUser().getId();
		this.date = message.getDate();
	}
	
}
