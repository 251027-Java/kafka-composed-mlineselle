package com.revature.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@SpringBootApplication
@RestController
@RequestMapping("/api/messages")
public class ProducerApp {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public ProducerApp(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProducerApp.class, args);
    }

    @PostMapping("/{topic}")
    public Map<String, String> sendMessage(@PathVariable String topic, @RequestBody Map<String, String> payload) {
        String message = payload.get("message");

        kafkaTemplate.send(topic, message);

        System.out.println("Sending message: " + message);

        return Map.of(
                "status", "sent",
                "topic", topic,
                "message", message);
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "producer");
    }
}
