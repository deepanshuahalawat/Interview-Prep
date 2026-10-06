package factory;

import entity.FixedWindowRateLimiter;
import entity.MovingWindowRateLimiter;
import entity.RateLimiter;
import entity.TokenBucketWindowRateLimiter;
import enums.LimitorType;

public class RateLimiterFactory {
    public static RateLimiter getRateLimiter(LimitorType type){
        if(type == LimitorType.FIXED_WINDOW){
            return new FixedWindowRateLimiter();
        }
        else if(type == LimitorType.MOVING_WINDOW){
            return new MovingWindowRateLimiter();
        }
        else{
            return new TokenBucketWindowRateLimiter();
        }
    }
}
