package com.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Component
public class StringTokenService {
    //    private static final String SECRET_KEY = "Nairobi";
    private static final int TOKEN_LENGTH = 30;
    //    static final long EXPIRATION_TIME = 60*60*1000; // 1 hour

    static final String HEADER_STRING = "Authorization";

    @Autowired
    private static AppAuthentication appAuthentication;

    public static void addAuthentication(HttpServletResponse res, String username) {
        String tokenKey = getAlphaNumericString(TOKEN_LENGTH);
        res.addHeader(HEADER_STRING, tokenKey);
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String tokenKey = request.getHeader(HEADER_STRING);
        if (tokenKey != null) {
            // parse the token.
            Integer user = appAuthentication.validateToken(tokenKey);

            return user != null ? new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()) : null;
        }
        return null;
    }

    public String createToken() {
        return getAlphaNumericString(TOKEN_LENGTH);
    }


    // function to generate a random string of length n
    static String getAlphaNumericString(int n) {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }

}


