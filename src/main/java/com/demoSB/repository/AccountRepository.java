package com.demoSB.repository;

import com.demoSB.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account,Integer> {
    Optional<Account> findAccountByUsername(String username);
}
