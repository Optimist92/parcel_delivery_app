package com.test.msorders.controllers.v1;

import dto.OrderDTO;
import enums.EOrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;
import util.DelayService;

import java.util.*;

import static java.lang.Thread.sleep;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/v1/simulation")
public class SimulationController {

    Map<String, Thread> threadMap = new HashMap<>();
    private final RabbitTemplate rabbitTemplate;

    @GetMapping(path="/start-draft-orders/{username}/{minDelay}/{maxDelay}")
    public String createSimulation(@PathVariable @NotNull String username,@PathVariable @NotNull Long minDelay, @PathVariable @NotNull Long maxDelay) {

        var threadName = "draft-orders:" + username;
        var thread = new Thread(getDraftOrdersRunnable(username, minDelay, maxDelay), threadName);
        threadMap.put(threadName, thread);
        thread.start();
        return thread.getName();
    }

    @GetMapping(path="/stop-draft-orders/{username}")
    public String stopSimulation(@PathVariable @NotNull String username) {

        var threadName = "draft-orders:" + username;
        var thread = threadMap.get(threadName);
        threadMap.remove(threadName);
        thread.interrupt();
        return thread.getName();
    }

    @GetMapping(path="/print")
    public String print() {

        return threadMap.toString();
    }

    private Runnable getDraftOrdersRunnable(String username, Long minDelay, Long maxDelay) {

        //rabbitTemplate.setMessageConverter(new JacksonMessageConverter());

        return () -> {
            while (!Thread.interrupted()) {
                var draftOrderDTO = new OrderDTO();
                draftOrderDTO.setId(UUID.randomUUID());
                draftOrderDTO.setTitle(new Date().toString());
                draftOrderDTO.setContent(null);
                draftOrderDTO.setCreationDate(new Date());
                draftOrderDTO.setStatus(EOrderStatus.STATUS_DRAFT);
                //draftOrderDTO.setUsername(username);
                //draftOrderDTO.setCardNumber(null);
                draftOrderDTO.setCost(null);

                System.out.println("send order to rabbit");

                rabbitTemplate.convertAndSend("create-order", draftOrderDTO);

                try {
                    sleep(DelayService.getDelay(minDelay, maxDelay));
                } catch (InterruptedException e) {
                    System.out.println("interrupted exception");
                    Thread.currentThread().interrupt();
                    //do nothing, stop cycle
                } catch (Exception e) {
                    System.out.println("another exception");
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
