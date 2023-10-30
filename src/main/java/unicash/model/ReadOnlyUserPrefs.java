package unicash.model;

import java.nio.file.Path;

import unicash.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getUniCashFilePath();
}
