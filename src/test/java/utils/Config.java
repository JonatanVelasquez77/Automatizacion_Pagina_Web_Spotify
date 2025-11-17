package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final Properties props = new Properties();

    static {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) {
                props.load(is);
            } else {
                System.err.println("Advertencia: config.properties no encontrado en el classpath (src/test/resources). Se usarán variables de entorno o propiedades del sistema si existen.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo config.properties: " + e.getMessage(), e);
        }
    }

    public static String get(String key) {
        // buscar en propiedades del sistema (-Dkey=value)
        String value = System.getProperty(key);
        if (value != null && !value.isEmpty()) {
            return value;
        }

        // buscar en variables de entorno (convertir a formato ENV: spotify.email -> SPOTIFY_EMAIL)
        String envKey = key.toUpperCase().replace('.', '_');
        value = System.getenv(envKey);
        if (value != null && !value.isEmpty()) {
            return value;
        }

        // buscar en config.properties
        value = props.getProperty(key);
        if (value != null && !value.isEmpty()) {
            return value;
        }

        throw new RuntimeException("Clave '" + key + "' no encontrada en config.properties, variables de entorno ni propiedades del sistema");
    }

    public static String getEmail() {
        return get("spotify.email");
    }

    public static String getPassword() {
        return get("spotify.password");
    }

    public static String getmyEmail() {
        return get("spotify.myEmail");
    }

    public static String getmyPassword() {
        return get("spotify.myPassword");
    }
}
