package unicash.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static unicash.testutil.Assert.assertThrows;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import unicash.commons.core.GuiSettings;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setUniCashFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setUniCashFilePath(null));
    }

    @Test
    public void equals() {
        var userPrefs = new UserPrefs();
        assertEquals(userPrefs, userPrefs);

        assertFalse(userPrefs.equals(5));

        var changedGuiSettings = new UserPrefs();
        changedGuiSettings.setGuiSettings(new GuiSettings(0, 0, 0, 0));
        assertNotEquals(changedGuiSettings, userPrefs);

        var changedUniCashFilePath = new UserPrefs();
        changedUniCashFilePath.setUniCashFilePath(Path.of("invalid_path"));
        assertNotEquals(changedUniCashFilePath, userPrefs);
    }

    @Test
    public void hashCode_test() {
        UserPrefs userPrefs1 = new UserPrefs();
        UserPrefs userPrefs2 = new UserPrefs();
        UserPrefs userPrefs3 = new UserPrefs();
        userPrefs3.setGuiSettings(new GuiSettings(0, 0, 0, 0));
        assertEquals(userPrefs1.hashCode(), userPrefs2.hashCode());
        assertNotEquals(userPrefs1.hashCode(), userPrefs3.hashCode());
    }
}

