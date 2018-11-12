package com.sdx.yundian.core;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    //声明队列
    @Bean
    public Queue queue1() {
        return new Queue("sdx.queue1", true); // true表示持久化该队列
    }


    @Bean
    public Queue queue2() {
        return new Queue("sdx.queue2", true);
    }

    //声明队列
    @Bean
    public Queue queue3() {
        return new Queue("sdx.queue3", true); // true表示持久化该队列
    }

    //声明交互器
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    //声明交互器
    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    //绑定
    @Bean
    public Binding bindingDirect(){
        return BindingBuilder.bind(queue3()).to(directExchange()).with("direct.sdx");
    }
    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(queue1()).to(topicExchange()).with("key.1");
    }
    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(queue2()).to(topicExchange()).with("key.#");
    }
}
