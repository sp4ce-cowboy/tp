package unicash.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.awt.Point;

import org.junit.jupiter.api.Test;

public class GuiSettingsTest {
    @Test
    public void toStringMethod() {
        GuiSettings guiSettings = new GuiSettings();
        String expected = GuiSettings.class.getCanonicalName() + "{windowWidth=" + guiSettings.getWindowWidth()
                + ", windowHeight=" + guiSettings.getWindowHeight() + ", windowCoordinates="
                + guiSettings.getWindowCoordinates() + "}";
        assertEquals(expected, guiSettings.toString());
    }

    @Test
    public void getWindowCoordinates() {
        var settings = new GuiSettings();
        assertNull(settings.getWindowCoordinates());

        var settingsWithCoords = new GuiSettings(400, 500, 15, 30);
        assertNotNull(settingsWithCoords.getWindowCoordinates());

        assertEquals(new Point(15, 30), settingsWithCoords.getWindowCoordinates());
    }

    @Test
    public void hashCode_tests() {
        var settingsOne = new GuiSettings();
        var settingsTwo = new GuiSettings();
        assertEquals(settingsOne.hashCode(), settingsTwo.hashCode());

        var settingsThreeDifferent = new GuiSettings(400, 500, 10, 10);
        assertNotEquals(settingsOne.hashCode(), settingsThreeDifferent.hashCode());
    }

    @Test
    public void equals() {
        var settings = new GuiSettings(400, 500, 0, 0);
        assertEquals(settings, settings);

        assertFalse(settings.equals(5));

        var changedWidth = new GuiSettings(15, 500, 0, 0);
        assertNotEquals(settings, changedWidth);

        var changedHeight = new GuiSettings(400, 400, 0, 0);
        assertNotEquals(settings, changedHeight);

        var changedCoordinates = new GuiSettings(400, 500, 15, 15);
        assertNotEquals(settings, changedCoordinates);
    }
}
