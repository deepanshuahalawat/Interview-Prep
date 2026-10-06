package entity;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class FixedWindowRateLimiter implements RateLimiter {
    private final Long WINDOW_SIZE = 5000L;
    private final Long TOKEN_LIMIT = 5L;

    ConcurrentHashMap<Long, Long> windowTimeMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, Long> tokensUsedMap = new ConcurrentHashMap<>();

    @Override
    public boolean check(Long userId) {
        //first request;
        if(!windowTimeMap.containsKey(userId)){
            windowTimeMap.put(userId, System.currentTimeMillis());
            tokensUsedMap.put(userId, 0L);
            return true;
        }
        Long windowSize = System.currentTimeMillis() - windowTimeMap.get(userId);

        // check if window expired
        if(windowSize > WINDOW_SIZE){
            windowTimeMap.put(userId, System.currentTimeMillis());
            tokensUsedMap.put(userId, 0L);
            return true;
        }

        if(tokensUsedMap.get(userId) < TOKEN_LIMIT){
            tokensUsedMap.put(userId, tokensUsedMap.get(userId)+1);
            return true;
        }


        return false;
    }
}
