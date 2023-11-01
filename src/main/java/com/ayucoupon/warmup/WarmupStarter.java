package com.ayucoupon.warmup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class WarmupStarter {

    private final List<WarmupRunner> warmupRunners;

    public WarmupStarter(WarmupRegistry registry) {
        this.warmupRunners = registry.getWarmupRunners();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void start() throws InterruptedException {
        log.info("warm up start");
        CountDownLatch latch = new CountDownLatch(warmupRunners.size());
        warmupRunners.forEach(warmupRunner -> warmupRunner.start(latch));
        latch.await();
        log.info("all warm up finished");
    }
}
