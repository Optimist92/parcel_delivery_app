package com.test.msorders.controllers.v1;

import com.test.msorders.util.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/notifications")
public class PerformanceController {

    private final PerformanceService performanceService;


    private final AtomicInteger id = new AtomicInteger();

    private final ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);

    //private final SseEmitters emitters = new SseEmitters();

    /*
    @PostConstruct
    void init() {
        scheduledThreadPool.scheduleAtFixedRate(() -> {
            try {
                //emitters.send(performanceService.getPerformance());
                emitter.send(performanceService.getPerformance());
            }
            catch (Exception ex) {
                Logger logger = LoggerFactory.getLogger(PerformanceController.class);
                emitter.completeWithError(ex);
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
*/


    @GetMapping(path = "/performance", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    SseEmitter getPerformance() {

        SseEmitter emitter = new SseEmitter();


        scheduledThreadPool.scheduleAtFixedRate(() -> {
            try {
                //emitters.send(performanceService.getPerformance());
                emitter.send(performanceService.getPerformance());
            }
            catch (Exception ex) {
                Logger logger = LoggerFactory.getLogger(PerformanceController.class);
                emitter.completeWithError(ex);
            }
        }, 0, 3, TimeUnit.SECONDS);


        //return emitters.add();
        return emitter;
    }
}