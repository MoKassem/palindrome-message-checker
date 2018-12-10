package infrastructure.configs;

import com.mokassem.sql.JdbcConfig;

public class DevConfig implements AppConfig {

    @Override
    public JdbcConfig getReadJdbcConfig() {
        return new JdbcConfig(
                "jdbc:mysql://restaurant-order-fulfillment.rds.dev.skipinternal.com/restaurant_order_flmt_notification_service",
                "j2_restaurant_order_flmt_ns",
                "2}>._(vBz+}QuWnGfpa)",
                false);
    }

    @Override
    public JdbcConfig getWriteJdbcConfig() {
        return new JdbcConfig(
                "jdbc:mysql://restaurant-order-fulfillment.rds.dev.skipinternal.com/restaurant_order_flmt_notification_service",
                "j2_restaurant_order_flmt_ns",
                "2}>._(vBz+}QuWnGfpa)",
                false);
    }
}
