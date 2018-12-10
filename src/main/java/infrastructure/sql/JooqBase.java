
package infrastructure.sql;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import com.mokassem.jooq.context.JooqContext;
import domain.ReadContext;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.impl.DSL;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class JooqBase {
    private final DSLContext readContext;
    private final DSLContext writeContext;

    protected JooqBase(JooqContext context) {
        this.readContext = context.getReadContext();
        this.writeContext = context.getWriteContext();
    }

    protected DSLContext currentContext(ReadContext context) {
        return context == ReadContext.SYNC_REQUIRED ? writeContext : readContext;
    }

    protected DSLContext writeContext() {
        return currentContext(ReadContext.SYNC_REQUIRED);
    }

    protected void writeTransaction(Consumer<DSLContext> consumer) {
        writeContext().transaction(config -> consumer.accept(DSL.using(config)));
    }

    protected <B extends Comparable, T extends Comparable> Optional<Condition> rangeCondition(Field<T> field, Range<B> range, Function<B, T> mapper) {
        if (range.hasLowerBound() && range.hasUpperBound()) {
            return Optional.of(
                    lowerConditionByBoundType(field, range.lowerBoundType(), mapper.apply(range.lowerEndpoint()))
                    .and(upperConditionByBoundType(field, range.upperBoundType(), mapper.apply(range.upperEndpoint())))
            );
        } else if (range.hasLowerBound()) {
            return Optional.of(lowerConditionByBoundType(field, range.lowerBoundType(), mapper.apply(range.lowerEndpoint())));
        } else if (range.hasUpperBound()) {
            return Optional.of(upperConditionByBoundType(field, range.upperBoundType(), mapper.apply(range.upperEndpoint())));
        }
        return Optional.empty();
    }

    private <T> Condition lowerConditionByBoundType(Field<T> field, BoundType type, T value) {
        return type == BoundType.CLOSED
                ? field.greaterOrEqual(value)
                : field.greaterThan(value);
    }

    private <T> Condition upperConditionByBoundType(Field<T> field, BoundType type, T value) {
        return type == BoundType.CLOSED
                ? field.lessOrEqual(value)
                : field.lessThan(value);
    }
}

