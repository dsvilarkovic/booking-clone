package xml.booking.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xml.booking.model.Role;
import xml.booking.repositories.RoleRepository;

/**
* Generated by Spring Data Generator on 19/06/2019
*/
@Component
public class RoleManager {

	private RoleRepository roleRepository;

	@Autowired
	public RoleManager(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

}
