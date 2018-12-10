package infrastructure.configs;

import com.mokassem.sql.JdbcConfig;

public class DevConfig implements AppConfig {

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
