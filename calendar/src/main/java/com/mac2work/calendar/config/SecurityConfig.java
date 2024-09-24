package com.mac2work.calendar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final UserRepository userRepository;
	
	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		 http
        		.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
        		.authorizeHttpRequests(auth ->auth
        				.requestMatchers("/calendar/auth/register","/content/guest**", "/calendar/auth/login**", "/images/**", "/error**").permitAll()
        				.anyRequest().authenticated())
        		.formLogin( formLogin ->
        			formLogin.loginPage("/calendar/auth/login")
        			.loginProcessingUrl("/calendar/auth/login")
//        			.successHandler(new AuthenticationSuccessHandler() {
//
//						@Override
//						public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//								Authentication authentication) throws IOException, ServletException {
//							String username = authentication.getName();
//							String password = userRepository.findByUsername(username).orElseThrow().getPassword();
//							authenticationService.login(username, password);
//							UrlPathHelper urlPathHelper = new UrlPathHelper();
//							String contextPath = urlPathHelper.getContextPath(request);
//							
//							response.sendRedirect("/calendar/content");
//							
//						}})
        			.defaultSuccessUrl("/calendar/content")
        			.failureUrl("/calendar/auth/login?error=true"))
        		.logout( logout ->
        			logout.logoutUrl("calendar/auth/logout")
        			.logoutSuccessUrl("/content/guest")); 
		return http.build();
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
	    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	    provider.setPasswordEncoder(passwordEncoder());
	    provider.setUserDetailsService(userDetailsService());
	    return provider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return username -> userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("User with username: %s not found", username)));
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	    
	
	
	
}