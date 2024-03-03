package com.colindalepass.config;

import com.colindalepass.security.CustomerOAuth2UserService;
import com.colindalepass.security.DatabaseLoginSuccessHandler;
import com.colindalepass.security.OAuth2LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;




@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

	private final OAuth2LoginSuccessHandler oauth2LoginHandler;
	@Autowired private CustomerOAuth2UserService oAuth2UserService;

	@Autowired private DatabaseLoginSuccessHandler databaseLoginHandler;

	@Autowired
	public WebSecurityConfig(OAuth2LoginSuccessHandler oauth2LoginHandler) {
		this.oauth2LoginHandler = oauth2LoginHandler;
	}


	@Bean
	UserDetailsService userDetailsService() {
		return new ColindalePassUserDetailsService();
	}



    @Bean
	BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	 DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;

	}


	 @Bean
	  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http.csrf().disable()
	                .authorizeHttpRequests((authorize) ->
	                        authorize.requestMatchers("/register","/forgot_password","/reset_password","/map").permitAll()
	                                .requestMatchers("/login","/barracks","/tejuosho","/airport").permitAll()
									.requestMatchers("/index","/test").permitAll()
									.requestMatchers("/create_user").permitAll()
									.requestMatchers("/check_Code/**","/checkCode/**","/valid_code","/Invalid_code","/Validate_Code/check_doescode_match").permitAll()
									.requestMatchers("/phone-number-input/**").hasAuthority("Admin")
									.requestMatchers("/PhoneNumberForm").hasAuthority("Admin")
									.requestMatchers("/generateCode/**").hasAuthority("Admin")
									.requestMatchers("/Generate_code").hasAuthority("Admin")
									.requestMatchers("/users/**").hasAuthority("Admin")
									.requestMatchers("/notification/subscribe/mobile","/subscribe/mobile").permitAll()
									.anyRequest().authenticated()

	                ).formLogin(
	                        form -> {
                                try {
                                    form
												.loginPage("/login")
												.usernameParameter("email")
												.loginProcessingUrl("/login")
												.successHandler(databaseLoginHandler)
												.permitAll()
                                            .and()
                                                .oauth2Login()
                                                .loginPage("/login")
                                                .userInfoEndpoint()
                                                .userService(oAuth2UserService)
                                            .and()
                                            .successHandler(oauth2LoginHandler);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }


	                ).logout(
	                        logout -> logout
	                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	                                .permitAll()
					);
	        return http.build();
	    }

	 @Bean
	 WebSecurityCustomizer ignoringCustomizer() {
	 	return (web) -> web.ignoring().requestMatchers("/img/**","/richtext/**","/images/**", "/js/**","/css/**" ,"/webjars/**","/style.css/**","/styles.css/**","/fontawesome/**","/webfonts/**","/vendor/**");
	 }

}
