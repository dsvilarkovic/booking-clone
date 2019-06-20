package xml.booking.auth.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xml.booking.auth.model.Role;
import xml.booking.auth.model.User;
import xml.booking.auth.repositories.UserRepository;

@Service   // It has to be annotated with @Service.
public class UserDetailsServiceImpl implements UserDetailsService  {
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private UserRepository userRepository;
	
//	@Autowired
//	private RoleRepository roleRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("ee1");
		User user = userRepository.findByEmail(username);
		System.out.println(username);
		System.out.println();
		
		if(user != null) {
			System.out.println("ee2");
			// Remember that Spring needs roles to be in this format: "ROLE_" + userRole (i.e. "ROLE_ADMIN")
			// So, we need to set it to that format, so we can verify and compare roles (i.e. hasRole("ADMIN")).
			List<GrantedAuthority> grantedAuthorities =  getAuthorities(user.getRoles());//AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_" + user.getRoles());
			System.out.println("ee2-granted");
				return new org.springframework.security.core.userdetails.User(username, user.getPassword(), grantedAuthorities);
		}
		System.out.println("ee3");
		// If user not found. Throw this exception.
		throw new UsernameNotFoundException("Username: " + username + " not found");
	}
	
	
	private List<GrantedAuthority> getAuthorities(Set<Role> roles){
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
		}
		
		return authorities;
	}
}