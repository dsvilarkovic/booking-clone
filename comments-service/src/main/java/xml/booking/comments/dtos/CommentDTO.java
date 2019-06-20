package xml.booking.comments.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CommentDTO {

	private Long id;
	private String value;
	private Long date;
	private UserDTO userDTO;
	private String commentState;
}
