package xml.booking.comments.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {

	private Long id;
	
	private String email;
	
    private String firstName;
    
    private String lastName;
    
    private String userType;
    
    private String address;

    private Integer pib;
}

