package com.test.msbatches.config;

import com.test.msbatches.converters.JacksonMessageConverter;
import com.test.msbatches.steps.calculation.OrderQuoteProcessor;
import dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.amqp.AmqpItemReader;
import org.springframework.batch.item.amqp.AmqpItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableBatchProcessing(dataSourceRef = "batchDataSource", transactionManagerRef = "batchTransactionManager")
@Slf4j
public class MyBatchAppConfig {

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        cachingConnectionFactory.setUsername("admin");
        cachingConnectionFactory.setPassword("admin");
        return cachingConnectionFactory;
    }

    @Bean
    public JobRepository jobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource());
        factory.setTransactionManager(transactionManager());
        factory.setIncrementerFactory(new MyIncrementerFactory(dataSource()));
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean(name = "batchDataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5489/ms-batches-db");
        dataSource.setUsername("admin");
        dataSource.setPassword("admin");
        dataSource.setSchema("public");
        return dataSource;
    }

    @Bean
    @Qualifier("draftOrderTemplate")
    public RabbitTemplate getDraftOrderTemplate() throws IOException {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        String draftQueueName = "draft-orders-queue";
        template.setDefaultReceiveQueue(draftQueueName);
        template.setMessageConverter(converter());
        return template;
    }

    @Bean
    @Qualifier("quotedOrderTemplate")
    public RabbitTemplate getQuotedOrderTemplate() throws IOException {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        String quotedQueueName = "quote-orders-queue";
        template.setMessageConverter(converter());
        template.setRoutingKey(quotedQueueName);
        return template;
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
    public Queue orderBean() {
        return new Queue("quote-orders-queue");
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("exchange");
    }

    @Bean
    public Binding createOrderBinding(){
        return BindingBuilder.bind(orderBean()).to(directExchange()).with("quote-order");
    }

    @Bean("batchTransactionManager")
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    @Bean
    public JobLauncher jobLauncher() throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository());
        return jobLauncher;

    }

    @Bean
    public ItemReader<OrderDTO> orderQuoteReader() throws IOException {
        return new AmqpItemReader<>(getDraftOrderTemplate());
    }

    @Bean
    public ItemProcessor<OrderDTO, OrderDTO> orderQuoteProcessor(){
        return new OrderQuoteProcessor();
    }

    @Bean
    public ItemWriter<OrderDTO> orderQuoteWriter() throws IOException {
        return new AmqpItemWriter<>(getQuotedOrderTemplate());
    }

    @Bean
    protected Step processOrder() throws Exception {
        return new StepBuilder("quote-order-step", jobRepository()).<OrderDTO, OrderDTO>chunk(2, transactionManager())
                .reader(orderQuoteReader())
                .processor(orderQuoteProcessor())
                .writer(orderQuoteWriter())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean(name = "quote-order")
    public Job job() throws Exception {
        return new JobBuilder("quote-order-job", jobRepository())
                .start(processOrder())
                .build();
    }

    @Bean
    public MessageConverter converter() {
        //jsonMessageConverter.setClassMapper(classMapper());

        return new JacksonMessageConverter();
    }


    /*@Bean
    public ItemWriter<Order> itemWriter() {
        return new AmqpItemReader<Order>();
    }
*/
    /*@Bean
    protected Step fetchOrder() throws Exception {
        return new StepBuilder("step-fetch", jobRepository())
                .tasklet(getFetchOrder(), transactionManager())
                .build();
    }

    @Bean
    protected Step reserveFunds() throws Exception {
        return new StepBuilder("step-reserve", jobRepository())
                .tasklet(getReserveFunds(), transactionManager())
                .build();
    }

    @Bean
    protected Step commitOrder() throws Exception {
        return new StepBuilder("step-commit", jobRepository())
                .tasklet(getCommitOrder(), transactionManager())
                .build();
    }

    @Bean
    public Job job() throws Exception {
        return new JobBuilder("create-order", jobRepository())
                .start(fetchOrder())
                .next(reserveFunds())
                .next(commitOrder())
                .build();
    }

    @Bean
    public FetchOrder getFetchOrder() {
        FetchOrder tasklet = new FetchOrder();
        return tasklet;
    }

    @Bean
    public ReserveFunds getReserveFunds() {
        ReserveFunds tasklet = new ReserveFunds();
        return tasklet;
    }

    @Bean
    public CommitOrder getCommitOrder() {
        CommitOrder tasklet = new CommitOrder();
        return tasklet;
    }*/
}
