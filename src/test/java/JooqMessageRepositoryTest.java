import com.mokassem.jooq.context.JooqContext;
import com.mokassem.jooq.context.QueryContextProvider;
import com.mokassem.sql.PooledReadWriteFlyway;
import domain.ReadContext;
import domain.message.Message;
import domain.message.MessageQuery;
import infrastructure.sql.JooqMessageRepository;
import infrastructure.sql.generated.Jooq;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import testing.PooledDataSource;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class JooqMessageRepositoryTest {
    private JooqMessageRepository messageRepository;
    private static PooledReadWriteFlyway flyway;

    @BeforeClass
    public static void setupClass() {
        flyway = PooledDataSource.getDataSource();
    }

    @Before
    public void setup() throws Exception {
        // JooqTesting.setupForTest(flyway, Jooq.JOOQ.getTables());
        List<Table<?>> tables = Jooq.JOOQ.getTables();

        // public static JooqTesting.Setup setupForTest(ReadWriteSource source, List<Table<?>> tables) {
        DSLContext dslContext = DSL.using(flyway.getWriteSource(), SQLDialect.H2, QueryContextProvider.JOOQ_SETTINGS);
        dslContext.execute("SET REFERENTIAL_INTEGRITY FALSE");
        tables.forEach((table) -> {
            if (!table.getName().toUpperCase().equals("SCHEMA_VERSION")) {
                dslContext.deleteFrom(table).execute();
            }

        });
        dslContext.execute("SET REFERENTIAL_INTEGRITY TRUE");


        final JooqContext jooqContext = new JooqContext(
                DSL.using(flyway.getWriteSource(), SQLDialect.H2, QueryContextProvider.JOOQ_SETTINGS),
                DSL.using(flyway.getWriteSource(), SQLDialect.H2, QueryContextProvider.JOOQ_SETTINGS)
        );
        messageRepository = new JooqMessageRepository(jooqContext);
    }

    public JooqMessageRepository repository() {
        return messageRepository;
    }

//    @Test
//    public void remove_removesSingleToken() {
//        Token token1 = Token.builderForTests().build();
//        Token token2 = Token.builderForTests().build();
//        Token token3 = Token.builderForTests().build();
//
//        repository().save(token1);
//        repository().save(token2);
//        repository().save(token3);
//
//        repository().remove(token3);
//
//        Set<Token> tokensByIds = repository().find(ReadContext.SYNC_NOT_REQUIRED,
//                                                   TokenQuery.builder()
//                                                             .tokens(Optional.of(
//                                                                     ImmutableSet.of(token1.token(), token2.token(), token3.token())))
//                                                             .build());
//
//        assertThat(tokensByIds.size(), is(2));
//    }
//
//    @Test
//    public void remove_removesMultipleTokens() {
//        Token token1 = Token.builderForTests().build();
//        Token token2 = Token.builderForTests().build();
//        Token token3 = Token.builderForTests().build();
//
//        repository().save(token1);
//        repository().save(token2);
//        repository().save(token3);
//
//        repository().remove(token3, token2);
//
//        Set<Token> tokensByIds = repository().find(ReadContext.SYNC_NOT_REQUIRED,
//                                                   TokenQuery.builder()
//                                                             .tokens(Optional.of(
//                                                                     Sets.newHashSet(token1.token(), token2.token(), token3.token())))
//                                                             .build());
//
//        assertThat(tokensByIds.size(), is(1));
//    }

    @Test
    public void save_doesNotThrowException_whenSavingTokenTwice() {

        String str = "Too hot to hoot.";
        System.out.println(str.length() / 2);


        str = str.toLowerCase().replaceAll("[^A-Za-z0-9]", "");


        Message token1 = Message.builder().messageId("fd").content("fdsfds").ispalindrome(true).createdTime(Instant.now()).build();
        System.out.println(token1);

        repository().save(token1);
        repository().save(token1);
    }


   @Test
    public void save_savesTokenProperly() {

        Message token1 = Message.builderForTest().ispalindrome(true).build();

        Message token2 = Message.builderForTest().ispalindrome(false).build();

        System.out.println(token1);

        repository().save(token1);
        repository().save(token2);

        Optional<Message> result = repository().find(ReadContext.SYNC_NOT_REQUIRED, MessageQuery.builder().isPalindeome(Optional.of(false)).build())
                                               .stream().findFirst();

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(token2));
    }

//    @Test
//    public void find_returnsTokensByUserIdAndUserType() {
//        String userId = "1";
//        Token token1 = Token.builderForTests().userType(UserType.COURIER).userId(userId).build();
//        Token token2 = Token.builderForTests().userType(UserType.RESTAURANT).userId(userId).build();
//        Token token3 = Token.builderForTests().userType(UserType.RESTAURANT).build();
//
//        repository().save(token1);
//        repository().save(token2);
//        repository().save(token3);
//
//        Set<Token> tokens = repository().find(ReadContext.SYNC_NOT_REQUIRED,
//                                              TokenQuery.builder().userIdAndType(userId, UserType.COURIER).build());
//
//        assertThat(tokens.size(), is(1));
//        assertThat(tokens.iterator().next(), is(token1));
//    }
//
//    @Test
//    public void find_returnsTokensByUserId() {
//        String userId = "1";
//        Token token1 = Token.builderForTests().userId(userId).build();
//        Token token2 = Token.builderForTests().userId(userId).build();
//        Token token3 = Token.builderForTests().build();
//
//        repository().save(token1);
//        repository().save(token2);
//        repository().save(token3);
//
//        Set<Token> tokens = repository().find(ReadContext.SYNC_NOT_REQUIRED, TokenQuery.builder().userId(Optional.of(userId)).build());
//
//        assertThat(tokens.size(), is(2));
//        assertThat(tokens.contains(token1), is(true));
//        assertThat(tokens.contains(token2), is(true));
//    }
//
//    @Test
//    public void find_returnsTokensByTokens() {
//        Token token1 = Token.builderForTests().build();
//        Token token2 = Token.builderForTests().build();
//        Token token3 = Token.builderForTests().build();
//
//        repository().save(token1);
//        repository().save(token2);
//        repository().save(token3);
//
//        Set<Token> tokens = repository().find(ReadContext.SYNC_NOT_REQUIRED,
//                                              TokenQuery.builder()
//                                                        .tokens(Optional.of(Sets.newHashSet(token1.token(), token2.token())))
//                                                        .build());
//
//        assertThat(tokens.size(), is(2));
//        assertThat(tokens.contains(token1), is(true));
//        assertThat(tokens.contains(token2), is(true));
//    }
//
//    @Test
//    public void find_returnsTokensByUserType() {
//        Token token1 = Token.builderForTests().userType(UserType.COURIER).build();
//        Token token2 = Token.builderForTests().userType(UserType.RESTAURANT).build();
//        Token token3 = Token.builderForTests().userType(UserType.RESTAURANT).build();
//
//        repository().save(token1);
//        repository().save(token2);
//        repository().save(token3);
//
//        Set<Token> tokens = repository().find(ReadContext.SYNC_NOT_REQUIRED,
//                                              TokenQuery.builder().userType(Optional.of(UserType.RESTAURANT)).build());
//
//        assertThat(tokens.size(), is(2));
//        assertThat(tokens.contains(token3), is(true));
//        assertThat(tokens.contains(token2), is(true));
//    }
//
//    @Test
//    public void removeByQuery_removesTokensByUserIdAndUserType() {
//        String userId = "1";
//        Token token1 = Token.builderForTests().userType(UserType.COURIER).userId(userId).build();
//        Token token2 = Token.builderForTests().userType(UserType.RESTAURANT).userId(userId).build();
//        Token token3 = Token.builderForTests().userType(UserType.RESTAURANT).build();
//
//        repository().save(token1);
//        repository().save(token2);
//        repository().save(token3);
//
//        repository().removeByQuery(TokenQuery.builder().userIdAndType(userId, UserType.COURIER).build());
//
//        Set<Token> tokens = repository().find(ReadContext.SYNC_NOT_REQUIRED, TokenQuery.builder().build());
//
//        assertThat(tokens.size(), is(2));
//        assertThat(tokens.contains(token2), is(true));
//        assertThat(tokens.contains(token3), is(true));
//    }
//
//    @Test
//    public void removeByQuery_removesTokensByUserId() {
//        String userId = "1";
//        Token token1 = Token.builderForTests().userId(userId).build();
//        Token token2 = Token.builderForTests().userId(userId).build();
//        Token token3 = Token.builderForTests().build();
//
//        repository().save(token1);
//        repository().save(token2);
//        repository().save(token3);
//
//        repository().removeByQuery(TokenQuery.builder().userId(Optional.of(userId)).build());
//
//        Set<Token> tokens = repository().find(ReadContext.SYNC_NOT_REQUIRED, TokenQuery.builder().build());
//
//        assertThat(tokens.size(), is(1));
//        assertThat(tokens.contains(token3), is(true));
//    }
//
//    @Test
//    public void removeByQuery_removesTokensByTokens() {
//        Token token1 = Token.builderForTests().build();
//        Token token2 = Token.builderForTests().build();
//        Token token3 = Token.builderForTests().build();
//
//        repository().save(token1);
//        repository().save(token2);
//        repository().save(token3);
//
//        repository().removeByQuery(
//                                              TokenQuery.builder()
//                                                        .tokens(Optional.of(Sets.newHashSet(token1.token(), token2.token())))
//                                                        .build());
//
//        Set<Token> tokens = repository().find(ReadContext.SYNC_NOT_REQUIRED, TokenQuery.builder().build());
//
//        assertThat(tokens.size(), is(1));
//        assertThat(tokens.contains(token3), is(true));
//    }
//
//    @Test
//    public void removeByQuery_removesTokensByUserType() {
//        Token token1 = Token.builderForTests().userType(UserType.COURIER).build();
//        Token token2 = Token.builderForTests().userType(UserType.RESTAURANT).build();
//        Token token3 = Token.builderForTests().userType(UserType.RESTAURANT).build();
//
//        repository().save(token1);
//        repository().save(token2);
//        repository().save(token3);
//
//        repository().removeByQuery(TokenQuery.builder().userType(Optional.of(UserType.RESTAURANT)).build());
//
//        Set<Token> tokens = repository().find(ReadContext.SYNC_NOT_REQUIRED, TokenQuery.builder().build());
//
//        assertThat(tokens.size(), is(1));
//        assertThat(tokens.contains(token1), is(true));
//    }

}
