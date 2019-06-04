package xml.booking.serviceregistry.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xml.booking.serviceregistry.exceptions.CustomException;
import xml.booking.serviceregistry.model.Role;
import xml.booking.serviceregistry.model.User;
import xml.booking.serviceregistry.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{

        User user = userRepository.findByEmail(email);
        if (user == null || user.getRoles() == null || user.getRoles().isEmpty()) {
            throw new CustomException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
        }
        String [] authorities = new String[user.getRoles().size()];
        int count=0;
        for (Role role : user.getRoles()) {
            //NOTE: normally we dont need to add "ROLE_" prefix. Spring does automatically for us.
            //Since we are using custom token using JWT we should add ROLE_ prefix
            authorities[count] = "ROLE_"+role.getRole();
            count++;
        }
        UserDetails userDetails = new UserDetails(user.getEmail(),user.getPassword(),authorities);
        return userDetails;
	}

}
