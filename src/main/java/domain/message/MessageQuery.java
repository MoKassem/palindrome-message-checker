package domain.message;

import com.google.auto.value.AutoValue;

import java.util.Optional;
import java.util.Set;

@AutoValue
public abstract class MessageQuery {

    public abstract Optional<Set<String>> Ids();

    public abstract Optional<Integer> number();

    public abstract Optional<Boolean> isPalindeome();

    public static Builder builder() {
        return new AutoValue_MessageQuery.Builder();
    }

    public abstract Builder toBuilder();

    public static MessageQuery create(Optional<Set<String>> Ids, Optional<Integer> number, Optional<Boolean> isPalindeome) {
        return builder()
                .Ids(Ids)
                .number(number)
                .isPalindeome(isPalindeome)
                .build();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder isPalindeome(Optional<Boolean> isPalindeome);

        public abstract Builder number(Optional<Integer> number);

        public abstract Builder Ids(Optional<Set<String>> Ids);

        public abstract MessageQuery build();
    }
}