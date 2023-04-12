package com.mac2work.myfirstproject.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.mac2work.myfirstproject.webapp.service.JpaUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

	private final JpaUserDetailsService jpaUserDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public SecurityConfiguration(JpaUserDetailsService jpaUserDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.jpaUserDetailsService = jpaUserDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception{
		 http
        		.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
        		.authorizeHttpRequests(auth ->auth
        				.requestMatchers("/register**","/content/guest**", "/login**", "/images/**").permitAll()
        				.anyRequest().authenticated())
        		.userDetailsService(jpaUserDetailsService)
        		.headers(headers -> headers.frameOptions().sameOrigin())
        		.formLogin().loginPage("/login")
    			.loginProcessingUrl("/login")
    			.defaultSuccessUrl("/content", true)
    		    .failureUrl("/login?error=true")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/content/guest");;
		return http.build();


	}
	

	    @Bean
	    public DaoAuthenticationProvider daoAuthenticationProvider() {
	        DaoAuthenticationProvider provider =
	                new DaoAuthenticationProvider();
	        provider.setPasswordEncoder(bCryptPasswordEncoder);
	        provider.setUserDetailsService(jpaUserDetailsService);
	        return provider;
	    }
	
	
	
	
}
