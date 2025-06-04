package com.nobroker.streamSphere.services;

import com.nobroker.streamSphere.models.Account;
import com.nobroker.streamSphere.repositories.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;

    public Account register(Account account) {
        if (accountRepo.existsByEmail(account.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        return accountRepo.save(account);
    }

    public Optional<Account> findByEmail(String email) {
        return accountRepo.findByEmail(email);
    }
}
