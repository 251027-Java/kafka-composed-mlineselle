package com.revature.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
@RequestMapping("/api/messages")
public class ConsumerApp {

    private final List<ReceivedMessage> receivedMessages = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApp.class, args);
    }

    @KafkaListener(topics = {"orders", "notifications"}, groupId = "message-consumers")
    public void listen(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        System.out.println("Received message with topic ["+ topic +"]: " + message);
        receivedMessages.add(new ReceivedMessage(topic, message));
    }

    @GetMapping
    public Map<String, Object> getMessages() {
        return Map.of(
                "count", receivedMessages.size(),
                "messages", receivedMessages);
    }

    @DeleteMapping
    public Map<String, String> clearMessages() {
        receivedMessages.clear();
        return Map.of("status", "cleared");
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "consumer");
    }
}
