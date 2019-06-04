package xml.booking.serviceregistry.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import xml.booking.serviceregistry.model.Role;

/**
* Generated by Spring Data Generator on 31/05/2019
*/
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

}
