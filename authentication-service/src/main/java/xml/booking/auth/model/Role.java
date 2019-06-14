package xml.booking.auth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.EqualsAndHashCode;

@Entity(name = "role")
@EqualsAndHashCode
@SequenceGenerator(name="seqLocation", initialValue=100, allocationSize=50)
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator= "seqLocation")
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
