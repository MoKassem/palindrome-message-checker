package infrastructure.configs;

import com.mokassem.sql.JdbcConfig;

public class StubConfig implements AppConfig {

//    public JdbcConfig getJdbcConfig() {
//        return new JdbcConfig(
//                "jdbc:h2:mem:jslice/template;" +
//                        "DB_CLOSE_DELAY=-1;" +
//                        "DATABASE_TO_UPPER=false;" +
//                        "INIT=CREATE SCHEMA IF NOT EXISTS jslice_template\\; SET SCHEMA jslice_template",
//                "jslicedev_temp",
//                "password",
//                true);
//    }

    public JdbcConfig getJdbcConfig() {
        return new JdbcConfig(
                "jdbc:mysql://localhost:3306/palindrome_checker?autoReconnect=true&useSSL=false",
                "palindromechecker",
                "password",
                false);
    }

    @Override
    public JdbcConfig getReadJdbcConfig() {
        return getJdbcConfig();
    }

    @Override
    public JdbcConfig getWriteJdbcConfig() {
        return getJdbcConfig();
    }
}
