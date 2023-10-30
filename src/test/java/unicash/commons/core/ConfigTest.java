package unicash.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;
import java.util.logging.Level;

import org.junit.jupiter.api.Test;

public class ConfigTest {

    @Test
    public void toStringMethod() {
        Config config = new Config();
        String expected = Config.class.getCanonicalName() + "{logLevel=" + config.getLogLevel()
                + ", userPrefsFilePath=" + config.getUserPrefsFilePath() + "}";
        assertEquals(expected, config.toString());
    }

    @Test
    public void equalsMethod() {
        Config defaultConfig = new Config();
        assertNotNull(defaultConfig);
        assertEquals(defaultConfig, defaultConfig);
        assertFalse(defaultConfig.equals(2));

        Config otherConfig = new Config();
        assertEquals(defaultConfig, otherConfig);

        otherConfig.setLogLevel(Level.OFF);
        assertNotEquals(otherConfig, defaultConfig);

        otherConfig = new Config();
        otherConfig.setUserPrefsFilePath(Path.of("invalid_path.txt"));
        assertNotEquals(otherConfig, defaultConfig);
    }

    @Test
    public void hashCode_sameConfig_returnsSameHashCode() {
        var first = new Config();
        var second = new Config();
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    public void hashCode_differentConfig_returnsDifferentHashCode() {
        var first = new Config();
        var second = new Config();
        second.setLogLevel(Level.OFF);
        assertNotEquals(first.hashCode(), second.hashCode());
    }

}
