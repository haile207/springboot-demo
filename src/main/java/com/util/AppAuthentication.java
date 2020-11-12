package com.util;

import com.demoSB.model.Account;
import com.demoSB.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class AppAuthentication {
    @Autowired
    private static AccountService accountService;

    @Autowired
    private static StringTokenService stringTokenService;

    private static AppAuthentication instance;

    private static Map<String, Integer> token;

    private AppAuthentication() {
        this.token = new HashMap<>();
    }

    public static AppAuthentication getInstance() {
        if (instance == null) {
            instance = new AppAuthentication();
        }
        return instance;
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

//    public static boolean validateToken(String tokenKey, Integer accountId) {
//        if (tokenKey == null || accountId == null) {
//            return false;
//        }
//        return token.get(tokenKey).equals(accountId);
//    }

    public static Integer validateToken(String tokenKey) {
        return token.get(tokenKey);
    }

    public static String authenticate(String username, String password) {
        String pass = null;
        String token = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            pass = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Optional<Account> account = accountService.findAccountByUsername(username);
        if (account.isPresent()) {
            if (account.get().getPassword().equals(pass)) {
                token = stringTokenService.createToken();
                addToken(stringTokenService.createToken(), account.get().getId());
            }
            ;
        }
        return token;
    }

    public static void addToken(String tokenKey, Integer accountId) {
        token.put(tokenKey, accountId);
    }

    public static Integer deleteToken(String tokenKey) {
        return token.remove(tokenKey);
    }
}
