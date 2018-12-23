package com.synway.StandardComparision;

import ch.qos.logback.classic.Logger;
import com.synway.StandardComparision.pojo.LoggerMessage;
import com.synway.StandardComparision.util.LoggerQueue;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableWebSocketMessageBroker
@EnableScheduling
public class StandardComparisionApplication {
	private Logger logger = (Logger) LoggerFactory.getLogger(StandardComparisionApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StandardComparisionApplication.class, args);
	}
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

//    @Scheduled(fixedRate = 1000)
//	public void outPutLog(){
//		logger.info("测试日志");
//	}

	@PostConstruct
	public void pushLogger(){
        ExecutorService executorService= Executors.newFixedThreadPool(2);
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        LoggerMessage log = LoggerQueue.getInstance().poll();

                        if(log!=null){
                            if(messagingTemplate!=null)
                                messagingTemplate.convertAndSend("/topic/pullLogger",log);
                        }
                    } catch (Exception e) {
                    	Boolean flag =true;
                        e.printStackTrace();
                    }
                }
            }
        };
        executorService.submit(runnable);
        executorService.submit(runnable);
    }

}

