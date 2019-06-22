package xml.booking.dto;

import xml.booking.model.User;

public class RegistrationDTO {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String userType;
	private Integer pib;
	private String address;
	private Boolean activated;
	private String password;
	private String oldPassword;
	private Boolean deleted;
	private String username;
	
	public RegistrationDTO() {
		// TODO Auto-generated constructor stub
	}
			
	public RegistrationDTO(UserDTO dto, String userType, String password, Integer pib) {
		super();
		this.id = dto.getId();
		this.firstName = dto.getFirstName();
		this.lastName = dto.getLastName();
		this.email = dto.getEmail();
		this.userType = userType;
		this.pib = pib;
		this.address = dto.getAddress();
		this.activated = true;
		this.deleted = false;
		this.oldPassword = null;
		this.password = password;
		this.username = dto.getEmail();
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

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public Boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
