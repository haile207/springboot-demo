package com.demoSB.service.impl;


import com.demoSB.model.Account;
import com.demoSB.repository.AccountRepository;
import com.demoSB.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Iterable<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> findOneById(int id) {
        return accountRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account update(Account obj) {
        return accountRepository.save(obj);
    }

    @Override
    public Account save(Account obj) {
        return accountRepository.save(obj);
    }
}
