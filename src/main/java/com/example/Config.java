package com.example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class Config extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Bean
    public AuthenticationProvider authProvider() {
    	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    	provider.setUserDetailsService(userDetailsService);
    	provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
    	return provider;
    }
    
    
    @Override
     		protected void configure(HttpSecurity http) throws Exception {
    		http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/todos/signup").hasAuthority("ADMIN")
                .antMatchers("/todos/add").hasAuthority("ADMIN")
                .antMatchers("/todos/edit/**").hasAuthority("ADMIN")
                .antMatchers("/todos/delete/**").hasAuthority("ADMIN")
                .antMatchers("/todos/update/**").hasAuthority("ADMIN")
                .antMatchers("/todos/list").hasAnyAuthority("ADMIN","USER")
                .antMatchers("/users/signup").hasAuthority("ADMIN")
                .antMatchers("/users/add").hasAuthority("ADMIN")
                .antMatchers("/users/edit/**").hasAuthority("ADMIN")
                .antMatchers("/users/delete/**").hasAuthority("ADMIN")
                .antMatchers("/users/update/**").hasAuthority("ADMIN")
                .antMatchers("/users/list").hasAnyAuthority("ADMIN","USER")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
