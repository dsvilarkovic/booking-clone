package xml.booking.apigateway.security;



import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import xml.booking.apigateway.security.JwtConfig;

@Configuration
@EnableWebSecurity 	// Enable security config. This annotation denotes config for spring security.
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private JwtConfig jwtConfig;
 
	@Override
  	protected void configure(HttpSecurity http) throws Exception {
    	   http.cors().and()
    	   	   .csrf().disable()
		    // make sure we use stateless session; session won't be used to store user's state.
	 	    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) 	
		.and()
		    // handle an authorized attempts 
		    .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED)) 	
		.and()
		   // Add a filter to validate the tokens with every request
		   .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
		// authorization requests config
		.authorizeRequests()		   
		   .antMatchers("/api/maintenanceOfCodeBook/**").permitAll()
		   .antMatchers("/api/clientMessage/**").permitAll()
		   .antMatchers("/api/search/**").permitAll()
		   .antMatchers("/api/user/**").permitAll()
		   .antMatchers("/api/mock-service/**").permitAll()
		   .antMatchers("/api/reservations/**").permitAll()
		   .antMatchers("/api/accommodationService/**").permitAll()
		   
		   
		   .antMatchers("/api/auth/**").permitAll()
		   .antMatchers(HttpMethod.POST, "/api/users/").permitAll()
		   .antMatchers(HttpMethod.PUT, "/api/users/").authenticated()
		   .antMatchers(HttpMethod.GET, "/api/users/whoami").authenticated()
		   .antMatchers("/api/comments/**").permitAll()
		   .antMatchers("/api/accommodation/**").hasRole("AGENT")
		   .antMatchers("/api/messagingsoap/**").hasRole("AGENT")
		   .antMatchers("/api/reservationsoap/**").hasRole("AGENT") //permitAll()
		   // allow all who are accessing "auth" service
		   .antMatchers(HttpMethod.POST, "/api" +  jwtConfig.getUri()).permitAll();
    	   
    	   
	}
	
	@Bean
  	public JwtConfig jwtConfig() {
    	   return new JwtConfig();
  	}
	
	@Bean
	public CorsFilter corsFilter() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    final CorsConfiguration config = new CorsConfiguration();
	    config.setAllowCredentials(true);
	    config.addAllowedOrigin("*");
	    config.addAllowedHeader("*");
	    config.addAllowedMethod("OPTIONS");
	    config.addAllowedMethod("HEAD");
	    config.addAllowedMethod("GET");
	    config.addAllowedMethod("PUT");
	    config.addAllowedMethod("POST");
	    config.addAllowedMethod("DELETE");
	    config.addAllowedMethod("PATCH");
	    config.addExposedHeader("Authorization");
	    source.registerCorsConfiguration("/**", config);
	    return new CorsFilter(source);
	}

}