package domain.message;

import domain.ReadContext;

import java.util.Optional;
import java.util.Set;

public interface MessageRepository {
    void save (Message message);

    Set<Message> find(ReadContext context, MessageQuery query);

    Optional<Message> findById(ReadContext context, String restaurantId);

    void update (String messageId, Message message);

    void removeByQuery(MessageQuery query);

    default void removeByIds(Set<String> messageIds) {
        removeByQuery(MessageQuery.builder().Ids(Optional.of(messageIds)).build());
    }
}
