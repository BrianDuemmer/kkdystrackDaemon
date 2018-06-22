package com.dystify.kkdystrackDaemon.core;

import com.dystify.kkdystrackDaemon.module.websocket.WebSocketClientModule;
import com.dystify.kkdystrackDaemon.module.websocket.handler.TestHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@SpringBootApplication
@Import(WebSocketClientModule.class)
public class Application {
  public static void main(String[] args) {
    WebSocketClient client = new StandardWebSocketClient();
    WebSocketStompClient stompClient = new WebSocketStompClient(client);
    stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    stompClient.setTaskScheduler(new ConcurrentTaskScheduler());

    // probably can get this from a conf file
    String url = "ws://127.0.0.1:8000/data";
    StompSessionHandler handler = new TestHandler();
    stompClient.connect(url, handler);

    SpringApplication.run(Application.class, args);
  }
}
