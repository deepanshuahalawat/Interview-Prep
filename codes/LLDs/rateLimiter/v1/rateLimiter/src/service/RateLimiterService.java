package service;

import dao.UserData;
import entity.MovingWindowRateLimiter;
import entity.RateLimiter;
import entity.TokenBucketWindowRateLimiter;
import entity.User;
import enums.LimitorType;
import enums.UserType;
import factory.RateLimiterFactory;

public class RateLimiterService {
    private UserData userData;
    private RateLimiter fixedWindow ;
    private RateLimiter movingWindow;
    private RateLimiter tokenBucket;
    public RateLimiterService(UserData userData){
        this.userData = userData;
        fixedWindow = RateLimiterFactory.getRateLimiter(LimitorType.FIXED_WINDOW);
        movingWindow = RateLimiterFactory.getRateLimiter(LimitorType.MOVING_WINDOW);
        tokenBucket = RateLimiterFactory.getRateLimiter(LimitorType.TOKEN_BUCKET);

    }

    public boolean allow(Long userId){
        if(userData.getUserType(userId) == UserType.FreeUser){
            return fixedWindow.check(userId);
        }else{
            return tokenBucket.check(userId);
        }
    }
}
