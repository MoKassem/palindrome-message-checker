package infrastructure.configs.spark;

import java.lang.reflect.Constructor;

public interface GuiceExtensions {
    default <T> Constructor<T> constructorOf(Class<T> clazz) {
        if (clazz.getConstructors().length != 1) {
            throw new IllegalArgumentException("Cannot auto-fetch constructor unless there is only one constructor present. " + clazz.getConstructors().length);
        } else {
            return (Constructor<T>) clazz.getConstructors()[0];
        }
    }
}
