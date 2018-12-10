package infrastructure.configs;

import com.mokassem.guice.Config;
import com.mokassem.sql.JdbcConfig;

public interface AppConfig extends Config {

    JdbcConfig getReadJdbcConfig();

    JdbcConfig getWriteJdbcConfig();


}
