package api.dto;

import domain.message.Message;

import java.time.Instant;
import java.util.UUID;

public class MyMessage {
    String message;

    public MyMessage(String message) {
        this.message = message;
    }


    public Message toDomain(){
        return Message.create(UUID.randomUUID().toString(), this.message, false, Instant.now());
    }
}

