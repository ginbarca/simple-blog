package com.va.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import javax.sql.DataSource;

import com.va.service.impl.UserServiceImpl;
import com.va.utils.RoleEnum;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserServiceImpl userDetailsService;
	@Autowired
	DataSource dataSource;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		SpringSecurityDialect dialect = new SpringSecurityDialect();
		return dialect;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable();
		// authorization
//		http.authorizeRequests().antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')").antMatchers("/user/**")
//				.access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')");
		// login/register/access denied
//		http.authorizeRequests().and().exce//ptionHandling().accessDeniedPage("/403").and().formLogin()
//				.loginProcessingUrl("/login-process").loginPage("/login").defaultSuccessUrl("/user/home")
//				.failureUrl("/login?q=error").usernameParameter("username").passwordParameter("password").and()
//				.rememberMe().rememberMeCookieName("app-remember-me").tokenValiditySeconds(24 * 60 * 60 * 30)
//				.tokenRepository(persistentTokenRepository()).and().logout().logoutUrl("/logout")
//				.logoutSuccessUrl("/login?q=logout").logoutRequestMatcher(new AntPathRequestMatcher("/dang-xuat"))
//				.clearAuthentication(true).invalidateHttpSession(true).permitAll().and().sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.ALWAYS).sessionFixation().migrateSession()
//				.maximumSessions(-1).sessionRegistry(sessionRegistry());

		http.authorizeRequests().antMatchers("/admin/**").hasAnyRole(RoleEnum.ADMIN.getRoleName())
				.antMatchers("/user/**").authenticated().anyRequest().permitAll()
				.and().formLogin().loginPage("/login").loginProcessingUrl("/login-process").defaultSuccessUrl("/user/home", true)
				.failureUrl("/login?e=error").and().rememberMe().rememberMeCookieName("app-remember-me")
				.tokenValiditySeconds(24 * 60 * 60 * 30).tokenRepository(persistentTokenRepository()).and().logout()
				.logoutUrl("/logout").logoutSuccessUrl("/login?e=logout")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).clearAuthentication(true)
				.invalidateHttpSession(true).deleteCookies("JSESSIONID", "app-remember-me").permitAll().and()
				.exceptionHandling().accessDeniedPage("/access-deny").and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.ALWAYS).sessionFixation().migrateSession()
				.maximumSessions(-1).sessionRegistry(sessionRegistry());
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		return tokenRepository;
	}
}
