package util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Authentication {
    private static Authentication instance;
    private Map<String, Integer> token;

    private Authentication() {
        this.token = new HashMap<>();
    }

    public static Authentication getInstance() {
        if (instance == null) {
            instance = new Authentication();
        }
        return instance;
    }

    public void addToken(String key, Integer accountId) {
        token.put(key, accountId);
    }

    public void deleteToken(String key) {
        token.remove(key);
    }
}
