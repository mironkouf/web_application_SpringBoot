package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class demoApplicationSecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		UserDetails user1 = User.withUsername("admin")
							.password("$2a$12$nOwuFu.lYdYd.GMu8p6Rc.fUdKulyoJzfuH945pcuR.LjevuIXYpa") //test
							.roles("PROFESSOR", "ADMIN")
							.build();
		
		UserDetails user2 = User.withUsername("andreas")
				.password("$2a$12$WIpawNEn8HKUhw9VpYk2Fu5tS6.Vhq05mzNQ2oWWLLGEBV2M38eau") //andreas
				.roles("PROFESSOR")
				.build();
		
		UserDetails user3 = User.withUsername("myron")
				.password("$2a$12$Rop6zJBTFMOksYFJQwIkAezgyJnpcaYYNPqU5GMLF2vg.Yl0zb34O") //myron
				.roles("PROFESSOR")
				.build();
		
		auth.inMemoryAuthentication().withUser(user1).withUser(user2).withUser(user3);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
}
