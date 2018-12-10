package infrastructure.sql;

import com.mokassem.jooq.context.JooqContext;
import domain.ReadContext;
import domain.message.Message;
import domain.message.MessageQuery;
import domain.message.MessageRepository;
import infrastructure.sql.generated.tables.records.MessagesRecord;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mokassem.jooq.context.JooqContext.upsert;
import static infrastructure.sql.generated.tables.Messages.MESSAGES;


public class JooqMessageRepository extends JooqBase implements MessageRepository {

    public JooqMessageRepository(JooqContext jooqContext) {
        super(jooqContext);
    }

    @Override
    public void save(Message message) {
        writeTransaction(context ->
                                 upsert(context,
                                        MESSAGES,
                                        serializeMessageRecord(message)
                                 ).execute()
        );
    }

    private MessagesRecord serializeMessageRecord(Message message) {
        return new MessagesRecord(
                message.messageId(),
                message.messageNumber(),
                message.content(),
                message.ispalindrome(),
                message.createdTime().toEpochMilli()
        );
    }

    @Override
    public Optional<Message> findById(ReadContext context, String messageId) {
        return this.find(context, MessageQuery.builder().Ids(Optional.of(Collections.singleton(messageId))).build()).stream().findFirst();
    }

    @Override
    public Set<Message> find(ReadContext context, MessageQuery query) {
        return currentContext(context)
                .selectFrom(MESSAGES)
                .where(messageQueryConditions(query))
                .fetch()
                .stream()
                .map(this::deserializeMessage)
                .collect(Collectors.toSet());
    }

    @Override
    public void update(String messageId, Message modifiedMessage) {
        currentContext(ReadContext.SYNC_REQUIRED).update(MESSAGES)
                                                 .set(MESSAGES.CONTENT, modifiedMessage.content())
                                                 .set(MESSAGES.IS_PALINDROME, modifiedMessage.ispalindrome())
                                                 .set(MESSAGES.CREATED_TIME, modifiedMessage.createdTime().toEpochMilli())
                                                 .where(messageIdAndTypeCondition(messageId, MESSAGES))
                                                 .execute();
    }

    private Condition messageIdAndTypeCondition(String messageId, Table<? extends Record> table) {
        Field<String> messageIdField = table.field("ID", String.class);
        return messageIdField.eq(messageId);
    }


    @Override
    public void removeByQuery(MessageQuery query) {
        writeTransaction(context ->
                                 context.deleteFrom(MESSAGES)
                                        .where(messageQueryConditions(query))
                                        .execute()
        );

    }

    private Message deserializeMessage(MessagesRecord record) {
        return Message.create(
                record.getId(),
                record.getNumber(),
                record.getContent(),
                record.getIsPalindrome(),
                Instant.ofEpochMilli(record.getCreatedTime())
        );
    }

    public List<Condition> messageQueryConditions(MessageQuery query) {
        return flattenOptionals(
                query.Ids().map(MESSAGES.ID::in),
                query.number().map(MESSAGES.NUMBER::eq),
                query.isPalindeome().map(MESSAGES.IS_PALINDROME::eq)
        );
    }

    public List<Condition> flattenOptionals(Optional... conditions) {
        List<Condition> jooQConditions = new ArrayList<>();
        Stream.of(conditions).forEach(condition -> {
            if (condition.isPresent()) {
                jooQConditions.add((Condition) condition.get());
            }
        });
        return jooQConditions;
    }
}
