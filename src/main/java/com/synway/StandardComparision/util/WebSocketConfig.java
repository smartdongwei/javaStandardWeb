package com.synway.StandardComparision.util;

import com.synway.StandardComparision.pojo.LoggerMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Configuration
//@EnableWebSocketMessageBroker
public  class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    //添加一个服务端点，来接收客户端的连接
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket")
                .setAllowedOrigins("*")
                .withSockJS();
    }
//   //定义客户端订阅地址的前缀信息
//   @Override
//   public void configureMessageBroker(MessageBrokerRegistry registry){
//        registry.enableSimpleBroker("/topic");
//   }


}
