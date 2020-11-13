package com.util;

import com.demoSB.model.Account;
import com.demoSB.service.AccountService;
import javafx.util.Pair;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AppAuthentication {
    @Autowired
    private AccountService accountService;

    @Autowired
    private StringTokenService stringTokenService;

    @Autowired
    EntityManagerFactory emf;

    private static AppAuthentication instance;

    private Map<String, Integer> token;

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

    public Integer validateToken(String tokenKey) {
        return token.get(tokenKey);
    }

    public String authenticate(String username, String password) throws InvocationTargetException {
        String pass = null;
        String token = null;
        SessionFactory sessionFactory = emf.unwrap(SessionFactory.class);
        Session session = sessionFactory.openSession();
        NativeQuery query = session.createNativeQuery("select * from accounts a where username=:name");

        Account rs = (Account) query.setParameter("name", username).uniqueResult();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            pass = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();

        } catch (Exception e) {
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

    public void addToken(String tokenKey, Integer accountId) {
        token.put(tokenKey, accountId);
    }

    public Integer deleteToken(String tokenKey) {
        return token.remove(tokenKey);
    }
}
