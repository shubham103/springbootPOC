package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class SeviceLayer implements UserDetailsService {

        @Autowired
        private UserRepository repo;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

                Users user = repo.findByUsername(username);
                if(user==null)
                        throw new UsernameNotFoundException("User 404");

                return new UserPrincipal(user);
        }

}