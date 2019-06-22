package xml.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {

	private Long id;
	
	private String username;
	private String password;
	
	/**
	 * Ako se updateuje password, da se proveri da li je prethodni dobar
	 */
	private String oldPassword;
	
    private String firstName;
    
    private String lastName;
    
    private String userType;
    
    private String address;

    private Integer pib;
}

