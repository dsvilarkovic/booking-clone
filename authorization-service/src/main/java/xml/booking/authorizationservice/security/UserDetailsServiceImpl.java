package xml.booking.authorizationservice.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import xml.booking.authorizationservice.model.Role;
import xml.booking.authorizationservice.model.User;
import xml.booking.authorizationservice.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired 
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByEmail(username);
		

		if(user != null) {
			String password = encoder.encode(user.getPassword());
			List<SimpleGrantedAuthority> authorities = getAuthorities(user.getRoles());
			return new org.springframework.security.core.userdetails.User(username, password, authorities);
		}
		throw new UsernameNotFoundException("User not found by name: " + username);
	}
	
    private List<SimpleGrantedAuthority> getAuthorities(Set<Role> set) {
        List<SimpleGrantedAuthority> authList = new ArrayList<>();
        
        for (Role role : set) {
			authList.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
		} 
        return authList;
    }

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
}
