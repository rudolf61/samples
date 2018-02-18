package eu.configuration;


import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static java.lang.Thread.currentThread;

/**
 * Singleton configuration. There is only one instance on the current main
 * thread
 *
 * This class is threadsafe. It is loaded from configuration files,
 * config.properties. The property names indicate the type. This type is used to
 * instantiate and store every property.
 * <p>
 * Furthermore the configuration makes a distinction between the current
 * environment. This is determined by property environment property
 * environment.string which is located at the root of the classpath in property
 * file config.properties.
 * </p>
 */
public class AppConfig {

    private static AppConfig SINGLETON;
    private Map<String, Wrapper> properties;

    static {
       SINGLETON = createConfiguration();
    }

    private AppConfig() {
        properties = new HashMap<>();
    }

    public static final AppConfig createConfiguration() {
        AppConfig config = new AppConfig();
        ClassLoader classLoader = currentThread().getContextClassLoader();
        try (InputStream is = classLoader.getResourceAsStream("config/config.properties")) {
            Properties props = new Properties();
            props.load(is);
            loadConfiguration(config, props);
        } catch (ConfigurationException e) {
            throw e;
        } catch (Exception e) {
            throw new ConfigurationException(e);
        }
        String env = config.getValue("environment");

        try (InputStream is = classLoader.getResourceAsStream("config/" + env + "/config.properties")) {
            Properties props = new Properties();
            props.load(is);
            loadConfiguration(config, props);
        } catch (ConfigurationException e) {
            throw e;
        } catch (Exception e) {
            throw new ConfigurationException(e);
        }

        return config;
    }

    public static final AppConfig getInstance() {
        return SINGLETON;
    }

    private static void loadConfiguration(AppConfig config, Properties props) {

        for (String prop : props.stringPropertyNames()) {
            int typePos = prop.lastIndexOf('.');
            if (typePos == -1) {
                throw new ConfigurationException("Failed to parse property " + prop + ". Missing type specifocation.");
            }
            String propName = prop.substring(0, typePos);
            String propType = prop.substring(typePos + 1);
            String value = props.getProperty(prop);
            switch (propType) {
                case "string":
                    Wrapper<String> s = new Wrapper<>(value);
                    config.properties.put(propName, s);
                    break;
                case "boolean":
                    Wrapper<Boolean> b = new Wrapper<>(Boolean.valueOf("true".equals(value)));
                    config.properties.put(propName, b);
                    break;
                case "int":
                    Wrapper<Integer> i = new Wrapper<>(Integer.valueOf(value));
                    config.properties.put(propName, i);
                    break;
                case "long":
                    Wrapper<Long> l = new Wrapper<>(Long.valueOf(value));
                    config.properties.put(propName, l);
                    break;
                case "double":
                    Wrapper<Double> dbl = new Wrapper<>(Double.valueOf(value));
                    config.properties.put(propName, dbl);
                    break;
                case "float":
                    Wrapper<Float> flt = new Wrapper<>(Float.valueOf(value));
                    config.properties.put(propName, flt);
                    break;
                case "date":
                    Wrapper<LocalDate> dt = new Wrapper<>(LocalDate.parse(value, DateTimeFormatter.ISO_DATE));
                    config.properties.put(propName, dt);
                    break;
                case "datetime":
                    Wrapper<LocalDateTime> dtm = new Wrapper<>(LocalDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME));
                    config.properties.put(propName, dtm);
                    break;
                default:
                    throw new ConfigurationException("Failed to parse property " + prop);

            }

        }
    }

    public <T> T getValue(String name) {
        return properties.containsKey(name) ? ((T) properties.get(name).getValue()) : null;
    }

    public <T> T getValue(String name, T defaultValue) {
        return properties.containsKey(name) ? ((T) properties.get(name).getValue()) : defaultValue;
    }

    
    static class Wrapper<T> {

        private T value;

        public Wrapper(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }
    }
}
