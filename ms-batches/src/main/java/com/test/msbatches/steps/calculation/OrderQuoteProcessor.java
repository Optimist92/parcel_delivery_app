package com.test.msbatches.steps.calculation;

import dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import util.DelayService;

import java.math.BigDecimal;

import static java.lang.Thread.sleep;
@Slf4j

public class OrderQuoteProcessor implements ItemProcessor<OrderDTO, OrderDTO> {

    @Override
    public OrderDTO process(OrderDTO item) throws InterruptedException {
        //sleep(DelayService.getDelay(1000L, 5000L));
        item.setCost(new BigDecimal(DelayService.getDelay(1L, 3L)));
        log.debug("Цена установлена");
        return item;
    }
}
