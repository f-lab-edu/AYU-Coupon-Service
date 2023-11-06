package com.ayucoupon.common.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.ConcurrentModificationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
@Slf4j
public class UserExclusiveRunner {

    private final ConcurrentMap<Long, RunnerLock> map = new ConcurrentHashMap<>();

    public <T> T call(Long userId, Duration tryLockTimeout, Supplier<T> f) {
        Assert.notNull(tryLockTimeout, "tryLockTimeout must not be null");

        RunnerLock lock = map.computeIfAbsent(userId, k -> new RunnerLock());
        lock.increase();
        try {
            log.debug("Method \"{}\" tried to get lock of \"{}\"", f.getClass().getSimpleName(), this.getClass().getSimpleName());
            if (lock.tryLock(tryLockTimeout.toMillis(), TimeUnit.MILLISECONDS)) {
                log.debug("User {} get lock of \"{}\"", userId, this.getClass().getSimpleName());
                try {
                    return f.get();
                } finally {
                    int count = lock.decrease();
                    if (count <= 0) {
                        map.remove(userId, lock);
                    }
                    log.debug("User {} release lock of \"{}\"", userId, this.getClass().getSimpleName());
                    lock.unLock();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.debug("User {} fail to get Lock of \"{}\"", userId, this.getClass().getSimpleName());
        throw new ConcurrentModificationException("동시에 같은 요청을 보낼 수 없습니다.");
    }

}
