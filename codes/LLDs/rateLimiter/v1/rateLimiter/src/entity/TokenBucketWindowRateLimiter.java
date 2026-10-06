package entity;

import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketWindowRateLimiter implements RateLimiter {

    private final long TOKEN_FILL_RATE_PER_SECOND  = 2L;
    private final long BUCKET_LIMIT = 5;

    ConcurrentHashMap<Long, Long> tokens = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, Long> lastRequestTime = new ConcurrentHashMap<>();

    @Override
    public boolean check(Long userId) {
        //first Time user
        if(!tokens.containsKey(userId)){
            //fill the bucket
            tokens.put(userId, BUCKET_LIMIT);
            lastRequestTime.put(userId, System.currentTimeMillis());
            return true;
        }
        //fill the bucket as per time passsed
        long secondsPassed = (System.currentTimeMillis() - lastRequestTime.get(userId))/1000;
        long totalTokens = tokens.get(userId) + secondsPassed*TOKEN_FILL_RATE_PER_SECOND;

        if(totalTokens > BUCKET_LIMIT){
            totalTokens = BUCKET_LIMIT;
        }
        //consuming current token
        totalTokens--;

        //bucket is empty
        if(totalTokens < 0){
            totalTokens = 0;
            tokens.put(userId, totalTokens);
            return false;
        }else{
            tokens.put(userId, totalTokens);
            lastRequestTime.put(userId, System.currentTimeMillis());
            return true;
        }

    }
}
