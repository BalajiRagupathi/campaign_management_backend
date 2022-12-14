package com.campaign_management.campaign_management.config;

import com.campaign_management.campaign_management.config.jwt_configure.JwtTokenConfigurer;
import com.campaign_management.campaign_management.config.jwt_configure.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtTokenProvider tokenProvider;


	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}



	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors();
		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests()
				.antMatchers("/api/v1/user/authenticate", "/api/v1/user/validate/{token}", "/api/v1/user/signup",
						"/api/v1/user/verify", "/api/v1/user/forgotpassword/generate/otp",
						"/api/v1/user/forgotpassword/reset")
				.permitAll().anyRequest().authenticated();
		http.apply(new JwtTokenConfigurer(tokenProvider));
	}

	// @Autowired
	// private AuthenticationEntryPoint entryPoint;

	// @Autowired
	// private UserDetailsServiceImpl userDetailsService;

	// @Override
	// protected void configure(AuthenticationManagerBuilder auth) throws Exception
	// {
	// auth.userDetailsService(userDetailsService);
	// }

	// @Override
	// protected void configure(HttpSecurity http) throws Exception {
	// http.csrf().disable().authorizeRequests().anyRequest().authenticated().
	// and().httpBasic().authenticationEntryPoint(entryPoint).
	// and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	// }

}
