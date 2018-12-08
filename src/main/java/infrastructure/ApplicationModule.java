package infrastructure;

import api.PalindromeMessageCheckerApi;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.mokassem.guice.GuiceExtensions;
import domain.message.MessageRepository;
import domain.message.PalindromeMessageCheckerService;
import infrastructure.sql.JooqMessageRepository;




public class ApplicationModule extends AbstractModule implements GuiceExtensions {

    @Override
    protected void configure() {


//       String connectionString = "jdbc:h2:mem:jslice/partner-management;" +
//                "DB_CLOSE_DELAY=-1;" +
//                "DATABASE_TO_UPPER=false;" +
//                "INIT=CREATE SCHEMA IF NOT EXISTS partners\\; SET SCHEMA partners";
//        String user = "jslicedev_temp";
//        String password = "password";
//
//
//        Flyway flyway = Flyway.configure().dataSource(connectionString, user, password).load();
//        flyway.migrate();

        bind(MessageRepository.class)
                .toConstructor(constructorOf(JooqMessageRepository.class))
                .in(Scopes.SINGLETON);


        bind(PalindromeMessageCheckerApi.class)
                .toConstructor(constructorOf(PalindromeMessageCheckerApi.class))
                .in(Scopes.SINGLETON);

        bind(PalindromeMessageCheckerService.class)
                .toConstructor(constructorOf(PalindromeMessageCheckerService.class))
                .in(Scopes.SINGLETON);



    }








}
