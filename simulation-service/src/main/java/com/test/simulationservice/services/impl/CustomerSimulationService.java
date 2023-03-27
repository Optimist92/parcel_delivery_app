package com.test.simulationservice.services.impl;

import com.test.simulationservice.services.ICustomerSimulationService;
import dto.OrderDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.DoubleRange;
import org.apache.commons.lang.math.LongRange;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import payload.CustomerSimulationParams;
import payload.CustomerSimulationParams2;
import payload.OrderPayload;
import util.DelayService;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static java.lang.Thread.sleep;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerSimulationService implements ICustomerSimulationService {

    private static Boolean CUSTOMER_SIMULATION_STATE = false;
    private static LongRange customerGenerationDelay;
    private static LongRange customerStartActionDelay;
    private static DoubleRange customerDeclineOrderProbability;

    private final static ThreadPoolExecutor es = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    private final RestTemplate restTemplate;

    @Override
    public CustomerSimulationParams updateCustomerSimulationParams(CustomerSimulationParams2 params) {
        customerGenerationDelay = params.getCustomerGenerationDelay();
        customerStartActionDelay =params.getCustomerStartActionDelay();
        customerDeclineOrderProbability = params.getCustomerDeclineOrderProbability();
        return getParams();
    }

    @Override
    public CustomerSimulationParams readCustomerSimulationParams() {

        return getParams();
    }

    @Override
    public Boolean readCustomerSimulationState() {

        return CUSTOMER_SIMULATION_STATE;
    }

    @Override
    public CustomerSimulationParams startCustomerSimulation() {

        CUSTOMER_SIMULATION_STATE = true;

        System.out.println("customer simulation started at " + new Date());

        while (CUSTOMER_SIMULATION_STATE) {

            es.submit(getCustomerActivity());

            System.out.println("current executor service pool is " + es.getPoolSize());
            System.out.println("current executor service active count is " + es.getActiveCount());

            try {
                sleep(DelayService.getDelay(customerGenerationDelay));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("customer simulation finished at " + new Date());
/*

        var runnable = new Runnable() {
            @Override
            @Transactional
            public void run() {

                System.out.println(customerGenerationDelay);
                var counter = 0;
                QUEUE_QUOTE_PROCESSING = true;
                ObjectMapper mapper = new ObjectMapper();

                try(Connection connection = connectionFactory.createConnection();
                    Channel channel = connection.createChannel(false);) {

                    channel.queueDeclare(QUEUE_QUOTE_NAME, true, false, false, null);

                    while (!Thread.interrupted() | OrderService.QUEUE_QUOTE_PROCESSING) {

                        GetResponse resp = channel.basicGet(QUEUE_QUOTE_NAME, false);
                        if( resp != null ){
                            String message = new String(resp.getBody(), StandardCharsets.UTF_8);
                            //System.out.println(" [x] Received '" + message + "'");
                            //channel.basicNack(resp.getEnvelope().getDeliveryTag(), false, true);


                            OrderQuoteDTO dto = mapper.readValue(message, OrderQuoteDTO.class);
                            orderRepository.updateQuotedOrder(dto.getId(), dto.getCost());

                            channel.basicAck(resp.getEnvelope().getDeliveryTag(), false);
                            System.out.println("processing message " + message);
                        }

                        try {
                            sleep(DelayService.getDelay(minDelay, maxDelay));
                        } catch (InterruptedException e) {
                            System.out.println("interrupted exception");
                            Thread.currentThread().interrupt();
                            QUEUE_QUOTE_PROCESSING = false;
                            //do nothing, stop cycle
                        } catch (Exception e) {
                            System.out.println("another exception");
                            throw new RuntimeException(e);
                        }
                    }

                } catch (IOException | TimeoutException e) {
                    throw new RuntimeException(e);
                } finally {
                    QUEUE_QUOTE_PROCESSING = false;
                }
            }
        };

        var thread = new Thread(runnable);
        thread.start();
        //return thread.getName();
*/
        return getParams();
    }


    private Runnable getCustomerActivity() {

        return () -> {

            System.out.println(Thread.currentThread().getName() + " : new customer created at " + new Date());

            //pause to make customers start at different moments, not right after the creation
            try {
                sleep(DelayService.getDelay(customerStartActionDelay));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            UUID orderPublicId;
            try {
                var orderPayload = new OrderPayload();
                orderPayload.setContent("");
                orderPayload.setCardNumber("");
                orderPayload.setLocationFrom("");
                orderPayload.setLocationTo("");

                System.out.println(Thread.currentThread().getName() + " : customer submits request for new order");
                String username = "Username: " + new Date();

                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", "Bearer " + createToken(username));
                HttpEntity<OrderPayload> entity = new HttpEntity<>(orderPayload, headers);
                //ResponseEntity<String> response = restTemplate.exchange(theUrl, HttpMethod.GET, entity, String.class);
                ResponseEntity<OrderDTO> response = restTemplate.postForEntity("http://localhost:8080/ms-orders/v1/orders", entity, OrderDTO.class);

                System.out.println(Thread.currentThread().getName() + " : a draft order created with publicId" + response.getBody().getPublicId() + " at " + new Date());
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        };
    }

    @Override
    public CustomerSimulationParams stopCustomerSimulation() {

        CUSTOMER_SIMULATION_STATE = false;

        return getParams();
    }

    private @NonNull CustomerSimulationParams getParams() {
        return new CustomerSimulationParams(
                customerGenerationDelay,
                customerStartActionDelay,
                customerDeclineOrderProbability
        );
    }

    private String createToken(String username) {
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSignInKey("6A576E5A7234753778214125432A462D4A614E645267556B5870327335763879"), SignatureAlgorithm.HS256)
                .compact();
    }

    private SecretKey getSignInKey(String jwtSecret) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    private HttpHeaders createHttpHeaders(String user, String password)
    {
        String notEncoded = user + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(notEncoded.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic " + encodedAuth);
        return headers;
    }
}
