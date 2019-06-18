package xml.booking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "role")
@SequenceGenerator(name="seqRole", initialValue=100, allocationSize=50)
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqRole")
    private Long id;
	
	@Column(name = "role")
    private String role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
}
