package com.ayucoupon.warmup;

import java.util.ArrayList;
import java.util.List;

public class WarmupRegistry {

    private final List<WarmupRunner> registrations = new ArrayList<>();

    public WarmupRegistry addWarmupRunner(WarmupRunner warmupRunner) {
        this.registrations.add(warmupRunner);
        return this;
    }

    public List<WarmupRunner> getWarmupRunners() {
        return this.registrations.stream()
                .toList();
    }

}
