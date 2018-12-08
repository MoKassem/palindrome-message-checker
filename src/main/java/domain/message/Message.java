package domain.message;

import com.google.auto.value.AutoValue;

import java.time.Instant;

@AutoValue
public abstract class Message {
    public abstract String messageId();
    public abstract String message();
    public abstract boolean ispalindrome();
    public abstract Instant createdTime();

    public static Message create(String messageId, String message, boolean ispalindrome, Instant createdTime) {
        return builder()
                .messageId(messageId)
                .message(message)
                .ispalindrome(ispalindrome)
                .createdTime(createdTime)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_Message.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder messageId(String messageId);

        public abstract Builder message(String message);

        public abstract Builder ispalindrome(boolean ispalindrome);

        public abstract Builder createdTime(Instant createdTime);

        public abstract Message build();
    }
}
