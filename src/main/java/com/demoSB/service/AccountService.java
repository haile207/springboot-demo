package com.demoSB.service;

import com.demoSB.model.Account;


import java.util.Optional;


public interface AccountService extends GeneralService<Account> {
    Optional<Account> findAccountByUsername(String username);
}
