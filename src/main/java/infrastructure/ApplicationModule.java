package infrastructure;

import api.PalindromeMessageCheckerApi;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import com.mokassem.guice.ConfiguredModeExtensions;
import com.mokassem.jooq.context.JooqContext;
import com.mokassem.jooq.context.QueryContextProvider;
import com.mokassem.sql.PooledReadWriteFlyway;
import domain.message.MessageRepository;
import domain.message.PalindromeMessageCheckerService;
import infrastructure.configs.AppConfig;
import infrastructure.configs.DevConfig;
import infrastructure.configs.ProdConfig;
import infrastructure.configs.StubConfig;
import infrastructure.sql.JooqMessageRepository;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.sql.DataSource;


public class ApplicationModule extends AbstractModule implements ConfiguredModeExtensions<AppConfig> {

    @Override
    protected void configure() {

        AppConfig config = getConfig();

        PooledReadWriteFlyway flyway = new PooledReadWriteFlyway(config.getReadJdbcConfig(), config.getWriteJdbcConfig());
        flyway.migrate();

        bind(JooqContext.class).toInstance(new JooqContext(
                DSL.using(flyway.getReadSource(), SQLDialect.MYSQL, QueryContextProvider.JOOQ_SETTINGS),
                DSL.using(flyway.getWriteSource(), SQLDialect.MYSQL, QueryContextProvider.JOOQ_SETTINGS)
        ));

        bind(DataSource.class)
                .annotatedWith(Names.named("ReadSource"))
                .toInstance(flyway.getReadSource());

        bind(DataSource.class)
                .annotatedWith(Names.named("WriteSource"))
                .toInstance(flyway.getWriteSource());

        bind(PalindromeMessageCheckerApi.class)
                .toConstructor(constructorOf(PalindromeMessageCheckerApi.class))
                .in(Scopes.SINGLETON);

        bind(MessageRepository.class)
                .toConstructor(constructorOf(JooqMessageRepository.class))
                .in(Scopes.SINGLETON);

        bind(PalindromeMessageCheckerService.class)
                .toConstructor(constructorOf(PalindromeMessageCheckerService.class))
                .in(Scopes.SINGLETON);
    }

    @Override
    public AppConfig stubConfig() {
        return new StubConfig();
    }

    @Override
    public AppConfig devConfig() {
        return new DevConfig();
    }

    @Override
    public AppConfig prodConfig() {
        return new ProdConfig();
    }
}
