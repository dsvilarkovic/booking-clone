package xml.booking.serviceregistry.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails{

    private String username;
    private String password;
    private Integer active;
    private boolean isLocked;
    private boolean isExpired;
    private boolean isEnabled;
    private List<GrantedAuthority> grantedAuthorities;
    
    
    
    public UserDetails(String username, String password, String[] authorities) {
		super();
		this.username = username;
		this.password = password;
		this.active = active;
		this.isLocked = false;
		this.isExpired = false;
		this.isEnabled = true;
		this.grantedAuthorities = AuthorityUtils.createAuthorityList(authorities);;
	}

    public UserDetails(String username, String [] authorities) {
        this.username = username;
        this.grantedAuthorities = AuthorityUtils.createAuthorityList(authorities);
    }

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active==1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
