package com.test.msorders.controllers.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/v1/notifications")
public class NotificationController {

    Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @GetMapping("/orders")
    public String getOrderNotifications() {
        return String.valueOf(1);
    }





    @GetMapping(path = "/stream-sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamSseMvc() {
        logger.info("stream-sse started");

        SseEmitter emitter = new SseEmitter();
        ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
        sseMvcExecutor.execute(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    SseEmitter.SseEventBuilder event = SseEmitter.event()
                            .data("SSE MVC - " + LocalTime.now().toString())
                            .id(String.valueOf(i))
                            .name("sse event - mvc");
                    emitter.send(event);
                    logger.info("stream-sse event sent " + event);
                    Thread.sleep(5000);
                }

                SseEmitter.SseEventBuilder event = SseEmitter.event()
                        .data("SSE MVC - bye")
                        .id("bye")
                        .name("sse event - mvc - bye");
                emitter.send(event);
                logger.info("stream-sse event sent " + event);

            } catch (Exception ex) {
                logger.info("stream-sse exception thrown " + ex);
                emitter.completeWithError(ex);
            }
        });
        logger.info("stream-sse return emitter");
        return emitter;
    }
}
