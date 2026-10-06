package entity;

public class MovingWindowRateLimiter implements RateLimiter {

    @Override
    public boolean check(Long userId) {
        return false;
    }
}
