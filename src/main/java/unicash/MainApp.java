package unicash;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import unicash.commons.core.Config;
import unicash.commons.core.LogsCenter;
import unicash.commons.core.Version;
import unicash.commons.exceptions.DataLoadingException;
import unicash.commons.util.ConfigUtil;
import unicash.commons.util.StringUtil;
import unicash.logic.Logic;
import unicash.logic.LogicManager;
import unicash.model.Model;
import unicash.model.ModelManager;
import unicash.model.ReadOnlyUniCash;
import unicash.model.ReadOnlyUserPrefs;
import unicash.model.UniCash;
import unicash.model.UserPrefs;
import unicash.model.util.SampleDataUtil;
import unicash.storage.JsonUniCashStorage;
import unicash.storage.JsonUserPrefsStorage;
import unicash.storage.Storage;
import unicash.storage.StorageManager;
import unicash.storage.UniCashStorage;
import unicash.storage.UserPrefsStorage;
import unicash.ui.Ui;
import unicash.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 3, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    private final Path customStoragePath;

    public MainApp(Path customStoragePath) {
        this.customStoragePath = customStoragePath;
    }

    public MainApp() {
        this(null);
    }

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing UniCa$h ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        if (customStoragePath != null) {
            userPrefs.setUniCashFilePath(customStoragePath);
        }
        UniCashStorage uniCashStorage = new JsonUniCashStorage(userPrefs.getUniCashFilePath());
        storage = new StorageManager(uniCashStorage, userPrefsStorage);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s UniCash and {@code userPrefs}. <br>
     * The data from the sample UniCash will be used instead if {@code storage}'s UniCash is not found,
     * or an empty UniCash will be used instead if errors occur when reading {@code storage}'s UniCash.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getUniCashFilePath());

        Optional<ReadOnlyUniCash> uniCashOptional;
        ReadOnlyUniCash initialData;
        try {
            uniCashOptional = storage.readUniCash();
            if (uniCashOptional.isEmpty()) {
                logger.info("Creating a new data file " + storage.getUniCashFilePath()
                        + " populated with a sample UniCash.");
            }
            initialData = uniCashOptional.orElseGet(SampleDataUtil::getSampleUniCash);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getUniCashFilePath() + " could not be loaded."
                    + " Will be starting with an empty UniCash.");
            initialData = new UniCash();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            if (configOptional.isEmpty()) {
                logger.info("Creating new config file " + configFilePathUsed);
            }
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logger.warning("Config file at " + configFilePathUsed + " could not be loaded."
                    + " Using default config properties.");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using preference file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            if (prefsOptional.isEmpty()) {
                logger.info("Creating new preference file " + prefsFilePath);
            }
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            logger.warning("Preference file at " + prefsFilePath + " could not be loaded."
                    + " Using default preferences.");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UniCa$h " + MainApp.VERSION);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Inter-Regular.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Inter-Medium.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Inter-Bold.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Inter-SemiBold.ttf"), 12);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping UniCa$h ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
