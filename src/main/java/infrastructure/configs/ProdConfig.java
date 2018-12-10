package infrastructure.configs;

import com.mokassem.sql.JdbcConfig;

public class ProdConfig implements AppConfig {

    @Override
    public JdbcConfig getReadJdbcConfig() {
        return new JdbcConfig(
                "jdbc:mysql://restaurant-order-fulfillment.rds.prod.skipinternal.com/restaurant_order_flmt_notification_service",
                "j2_restaurant_order_flmt_ns",
                "~XPrD9%m<$K\\Bq4A(:G-",
                false);
    }

    @Override
    public JdbcConfig getWriteJdbcConfig() {
        return new JdbcConfig(
                "jdbc:mysql://restaurant-order-fulfillment.rds.prod.skipinternal.com/restaurant_order_flmt_notification_service",
                "j2_restaurant_order_flmt_ns",
                "~XPrD9%m<$K\\Bq4A(:G-",
                false);
    }
}
