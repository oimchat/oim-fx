//package com.startup;
//
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.MessageListener;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//
///**
// * @Author: XiaHui
// * @Date: 2015年12月17日
// * @ModifyUser: XiaHui
// * @ModifyDate: 2015年12月17日
// */
//@Configuration
//@PropertySource({"classpath:config/config.properties"})
//public class MessageQueueConfig {
//
//    @Autowired
//    private Environment environment;
//
//    
//
//    // //////////////////////////////////////////
//    @Bean
//    public ConnectionFactory rabbitConnectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
//        // connectionFactory.setUsername("only");
//        // connectionFactory.setPassword("123456");
//        return connectionFactory;
//    }
//
//    @Bean
//    public Queue queue() {
//        Queue queue = new Queue("im.room.all", true, false, false);
//        //queue.setShouldDeclare(true);
//        return queue;
//    }
//
//    // @Bean
//    // public RabbitAdmin rabbitAdmin() {
//    // RabbitAdmin bean=new RabbitAdmin(queue());
//    // return container;
//    // }
//    @Bean
//    public SimpleMessageListenerContainer messageListenerContainer() {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(rabbitConnectionFactory());
//        // container.setQueueNames("chat.room");
//        container.setQueues(queue());
//        container.setMessageListener(exampleListener());
//        return container;
//    }
//
//    @Bean
//    public MessageListener exampleListener() {
//        return new MessageListener() {
//            @Override
//            public void onMessage(Message message) {
//                System.out.println("received: " + message);
//            }
//        };
//    }
//}
