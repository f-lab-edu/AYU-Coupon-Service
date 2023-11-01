package com.ayucoupon.warmup;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WarmupRunner {

    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    private Integer WARM_UP_COUNT;
    private String baseUrl;
    private HttpMethod method;

    public static WarmupRunner create(HttpMethod method, String baseUrl, Integer count) {
        return new WarmupRunner(method, baseUrl, count);
    }

    private WarmupRunner(HttpMethod method, String baseUrl, Integer count) {
        this.requestBodyUriSpec = WebClient.create(baseUrl).method(method);
        this.method = method;
        this.baseUrl = baseUrl;
        this.WARM_UP_COUNT = count;
    }

    public WarmupRunner pathSegment(String pathSegment) {
        requestBodyUriSpec.uri(uriBuilder -> uriBuilder.pathSegment(pathSegment).build());
        return this;
    }

    public WarmupRunner headers(HttpHeaders headers) {
        requestBodyUriSpec.headers(httpHeaders -> httpHeaders.putAll(headers));
        return this;
    }

    public WarmupRunner bodyValue(String bodyValue) {
        requestBodyUriSpec.bodyValue(bodyValue);
        return this;
    }

    @Async
    public void start(CountDownLatch latch) {
        if (WARM_UP_COUNT == 0) return;
        log.info("start {} {} warm up", method, baseUrl);
        IntStream.range(0, WARM_UP_COUNT).forEach(i ->
                requestBodyUriSpec.retrieve()
                        .bodyToMono(String.class)
                        .onErrorComplete()
                        .block()
        );
        log.info("finish {} {} warm up", method, baseUrl);
        latch.countDown();
    }

}
