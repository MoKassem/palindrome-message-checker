package api.dto.in;

import domain.message.Message;

import java.time.Instant;
import java.util.UUID;

public class MessageDto {
    private static final int MESSAGE_NUMBER_AUTO_INCR = 0;
    public final String content;
    public final boolean ignoreCaseAndPunct;

    public MessageDto(String content, boolean ignoreCaseAndPunct) {
        this.content = content;
        this.ignoreCaseAndPunct = ignoreCaseAndPunct;
    }

    public boolean isIgnoreCaseAndPunct() {
        return ignoreCaseAndPunct;
    }

    public Message toDomain() {
        return Message.create(UUID.randomUUID().toString(), MESSAGE_NUMBER_AUTO_INCR, this.content, false, Instant.now());
    }
}

