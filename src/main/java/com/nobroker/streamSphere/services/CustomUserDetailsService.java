package com.nobroker.streamSphere.services;

import com.nobroker.streamSphere.models.Account;
import com.nobroker.streamSphere.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        System.out.println("Loaded user: " + account.getEmail() + " with password hash: " + account.getPasswordHash());

        return new org.springframework.security.core.userdetails.User(
                account.getEmail(),
                account.getPasswordHash(),
                account.isActive(), true, true, true,
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
