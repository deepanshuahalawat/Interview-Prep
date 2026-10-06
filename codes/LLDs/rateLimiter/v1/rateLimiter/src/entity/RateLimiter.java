package entity;

public interface RateLimiter {
    boolean check(Long userId);
}
