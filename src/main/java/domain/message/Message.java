package domain.message;

import com.google.auto.value.AutoValue;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

@AutoValue
public abstract class Message {
    public abstract String messageId();
    public abstract int messageNumber();
    public abstract String content();
    public abstract boolean ispalindrome();
    public abstract Instant createdTime();

    public static Builder builder() {
        return new AutoValue_Message.Builder();
    }

    public abstract Builder toBuilder();

    public static Message create(String messageId, int messageNumber, String content, boolean ispalindrome, Instant createdTime) {
        return builder()
                .messageId(messageId)
                .messageNumber(messageNumber)
                .content(content)
                .ispalindrome(ispalindrome)
                .createdTime(createdTime)
                .build();
    }

    public static Builder builderForTest() {
        return builder()
                .messageId(UUID.randomUUID().toString())
                .messageNumber(new Random().nextInt())
                .content("test message")
                .ispalindrome(new Random().nextBoolean())
                .createdTime(Instant.now());
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder messageId(String messageId);

        public abstract Builder ispalindrome(boolean ispalindrome);

        public abstract Builder createdTime(Instant createdTime);

        public abstract Builder messageNumber(int messageNumber);

        public abstract Builder content(String content);

        public abstract Message build();
    }
}
