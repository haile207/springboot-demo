package com.demoSB.api.v1;

import com.demoSB.model.Account;
import com.demoSB.service.AccountService;
import com.dto.AccountForm;
import com.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        ApiResponse<Account> response = new ApiResponse<>();

        //Check if the request is a legal request
        boolean isValidUsername = isValidUsername(accountForm.getUsername());
        boolean isValidPassword = isValidPassword(accountForm.getPassword());
        boolean isValidStatus = isValidStatus(accountForm.getStatus());
        boolean isAvailableUsername = isAvailableUsername(accountForm.getUsername());
        if (!(isValidPassword && isValidUsername && isValidStatus && isAvailableUsername)) {
            response.setStatus(31);
            response.setData(null);
            response.setMessage("Invalid input.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        //Change password into MD5 code
        String pass = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(accountForm.getPassword().getBytes());
            byte[] digest = md.digest();
            pass = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //Call service to save new Account to database
        Account acc = accountService.save(new Account(
                accountForm.getUsername(),
                pass,
                accountForm.getStatus()
        ));
        response.setStatus(20);
        response.setData(acc);
        response.setMessage("Account was created successfully.");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    private boolean isValidUsername(String username) {
        return Pattern
                .matches("[a-zA-Z][a-zA-Z0-9_]" +
                        "{" + (MIN_USERNAME_LENGTH - 1) + "," + MAX_USERNAME_LENGTH + "}", username);
    }


    private boolean isAvailableUsername(String username) {
        Iterable<Account> accounts = accountService.findAll();
        for (Account acc : accounts) {
            if (username.equals(acc.getUsername())) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidPassword(String password) {
        return Pattern.matches("[a-zA-Z0-9]{" + MIN_PASSWORD_LENGTH + "," + MAX_PASSWORD_LENGTH + "}", password);
    }

    private boolean isValidStatus(int status) {
        return status == 0 || status == 1;
    }


}
