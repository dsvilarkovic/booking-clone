package xml.booking.dto;

import xml.booking.model.User;

public class UserDTO {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String userType;
	private Integer pib;
	private String address;
	private Boolean activated;
	private String password;

	public UserDTO() {
		// TODO Auto-generated constructor stub
	}
		
	public UserDTO(Long id, String firstName, String lastName, String email, String userType, Integer pib,
			String address, Boolean activated) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userType = userType;
		this.pib = pib;
		this.address = address;
		this.activated = activated;
	}
	
	public UserDTO(User user) {
		super();
		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.userType = user.getUserType();
		this.pib = user.getPib();
		this.address = user.getAddress();
		this.activated = user.isActivated();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Integer getPib() {
		return pib;
	}

	public void setPib(Integer pib) {
		this.pib = pib;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
