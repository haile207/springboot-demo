package com.demoSB.api.v1;

import com.demoSB.model.Account;
import com.demoSB.service.AccountService;
import com.dto.AccountForm;
import com.dto.ApiResponse;
import com.dto.LoginForm;
import com.util.AppAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
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

    @PostMapping(value = "login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginForm loginForm) {
        ApiResponse<String> response = new ApiResponse<>();
        AppAuthentication authentication = AppAuthentication.getInstance();
        String tokenKey = null;
        try {
            tokenKey = authentication.authenticate(loginForm.getUsername(), loginForm.getPassword());
        } catch (InvocationTargetException e) {
            e.getCause().printStackTrace();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        response.setData(tokenKey);
        if (response.getData() == null) {
            response.setMessage("Invalidated login information.");
            response.setStatus(32);
        } else {
            response.setMessage("Signed in successfully.");
            response.setStatus(21);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "changePassword")
    public ResponseEntity<ApiResponse<Boolean>> changePassword(HttpServletRequest request, @RequestBody String newPassword) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        if (!isValidPassword(newPassword)) {
            response.setStatus(31);
            response.setData(false);
            response.setMessage("Invalid password.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        AppAuthentication authentication = AppAuthentication.getInstance();
        String tokenKey = request.getHeader("Authorization");
        Integer id = authentication.validateToken(tokenKey);
        if (id != null) {
            Account account = null;
            Optional<Account> acc = accountService.findOneById(id);
            String pass = passwordIntoMd5(newPassword);
            if (acc.isPresent() && pass != null) {
                account = acc.get();
                account.setPassword(pass);
                accountService.save(account);
                response.setMessage("Changed password Successfully.");
                response.setData(true);
                response.setStatus(20);
            } else {
                response.setMessage("Changed password Failed.");
                response.setData(false);
                response.setStatus(51);
            }
        }else {
            response.setMessage("Invalidated user.");
            response.setData(false);
            response.setStatus(31);
        }
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


        String pass = passwordIntoMd5(accountForm.getPassword());
        Account acc = null;
        if (pass != null) {
            //Call service to save new Account to database
            acc = accountService.save(new Account(
                    accountForm.getUsername(),
                    passwordIntoMd5(accountForm.getPassword()),
                    accountForm.getStatus()
            ));
            response.setStatus(20);
            response.setMessage("Account was created successfully.");
        } else {
            response.setMessage("Error! Encrypted password failed.");
            response.setStatus(51);
        }

        response.setData(acc);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private String passwordIntoMd5(String password) {
        String pass = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            pass = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return pass;
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
