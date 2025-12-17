package com.revature.consumer;
public record ReceivedMessage(
        String topic,
        String message
) {}