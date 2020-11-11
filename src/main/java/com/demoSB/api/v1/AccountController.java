package com.demoSB.api.v1;

import com.demoSB.model.Account;
import com.demoSB.service.AccountService;
import dto.AccountForm;
import dto.ApiResponse;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.border.AbstractBorder;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "accounts")
public class AccountController {
    private final int MIN_USERNAME_LENGTH = 6;
    private final int MAX_USERNAME_LENGTH = 50;
    private final int MIN_PASSWORD_LENGTH = 8;
    private final int MAX_PASSWORD_LENGTH = 50;

    @Autowired
    private AccountService accountService;

    @Autowired
    private MD5Encoder md5Encoder;

    @GetMapping(value = "all")
    public ResponseEntity<ApiResponse<Iterable<Account>>> getAllAccount() {
        ApiResponse<Iterable<Account>> response = new ApiResponse<>();
        Iterable<Account> accounts = accountService.findAll();
        response.setStatus(20);
        response.setData(accounts);
        response.setMessage("Loaded all accounts.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "create")
    public ResponseEntity<ApiResponse<Account>> createAccount(@RequestBody AccountForm accountForm) {
        ApiResponse<Iterable<Account>> response = new ApiResponse<>();
        boolean isValidUsername = isValidUsername(accountForm.getUsername());
        boolean isValidPassword = isValidPassword(accountForm.getPassword());
        boolean isValidStatus = false;


        Iterable<Account> accounts = accountService.findAll();
        for (Account acc : accounts) {
            if (acc.g)
        }
    }

    private boolean isValidUsername(String username) {
        return Pattern.matches("([a-zA-Z][a-zA-Z0-9_]){6,50}", username);
    }

    private boolean isValidPassword(String password) {
        return Pattern.matches("[*]{8,50}", password);
    }

    private boolean isValidStatus(int status){

    }
}
