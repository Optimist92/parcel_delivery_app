package com.test.msorders.queue.config;

import com.test.msorders.converters.JacksonMessageConverter;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

import java.io.IOException;

@EnableRabbit
@Configuration
public class RabbitConfiguration {

    private final static String QUEUE_NAME = "draft-orders-queue";

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        cachingConnectionFactory.setUsername("admin");
        cachingConnectionFactory.setPassword("admin");
        return cachingConnectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() throws IOException {
        AmqpAdmin amqpAdmin = new RabbitAdmin(connectionFactory());
        amqpAdmin.declareQueue(orderBean());
        amqpAdmin.declareExchange(directExchange());
        amqpAdmin.declareBinding(createOrderBinding());
        return amqpAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() throws IOException {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setExchange("exchange");
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    @Bean
    public Queue orderBean() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("exchange");
    }

    @Bean
    public Binding createOrderBinding(){
        return BindingBuilder.bind(orderBean()).to(directExchange()).with("draft-order");
    }

    @Bean
    public MessageConverter converter() {
        //jsonMessageConverter.setClassMapper(classMapper());

        return new JacksonMessageConverter();
    }
}
