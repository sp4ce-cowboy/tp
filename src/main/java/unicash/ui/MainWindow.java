package unicash.ui;

import static unicash.logic.UniCashMessages.MESSAGE_UNICASH_WELCOME;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import unicash.commons.core.GuiSettings;
import unicash.commons.core.LogsCenter;
import unicash.commons.util.StringUtil;
import unicash.logic.Logic;
import unicash.logic.commands.CommandResult;
import unicash.logic.commands.exceptions.CommandException;
import unicash.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {
    public static final String USER_GUIDE_NAME_PREFIX = "userguide_local";
    public static final String USER_GUIDE_NAME_SUFFIX = ".pdf";
    public static final String USER_GUIDE_NAME = USER_GUIDE_NAME_PREFIX + USER_GUIDE_NAME_SUFFIX;
    public static final String PATH_TO_USER_GUIDE = "/documents/" + USER_GUIDE_NAME;
    public static final String FILE_ERROR_MESSAGE = "A FILE ERROR OCCURRED.";
    public static final String UNKNOWN_ERROR_MESSAGE = "AN UNKNOWN ERROR OCCURRED.";

    private static final String FXML = "MainWindow.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private TransactionListPanel transactionListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private SummaryWindow summaryWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem userGuideMenuItem;

    @FXML
    private StackPane transactionListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;


    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();

        // link expense summary from model to UI
        summaryWindow = new SummaryWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
        setAccelerator(userGuideMenuItem, KeyCombination.valueOf("F2"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        transactionListPanel = new TransactionListPanel(logic.getFilteredTransactionList());
        transactionListPanelPlaceholder.getChildren().add(transactionListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplay.setFeedbackToUser(MESSAGE_UNICASH_WELCOME);
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(
                logic.getUniCashFilePath(), logic.getFilteredTransactionList());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Opens the summary window or focuses on it if it's already opened.
     */
    @FXML
    public void handleSummary() {
        if (!summaryWindow.isShowing()) {
            summaryWindow.show(logic.getExpenseSummary());
        } else {
            summaryWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        summaryWindow.hide();
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            summaryWindow.setPieChart(logic.getExpenseSummary());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isShowSummary()) {
                handleSummary();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Opens the local user guide.
     */
    @FXML
    private void handleOpenUserGuide() {
        try {
            // Path to the resource inside the jar file
            InputStream inputStream = getClass().getResourceAsStream(PATH_TO_USER_GUIDE);

            File tempFile = File.createTempFile(USER_GUIDE_NAME_PREFIX, USER_GUIDE_NAME_SUFFIX);
            tempFile.deleteOnExit(); // The user guide will be deleted upon JVM exit

            Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Desktop.getDesktop().open(tempFile);

        } catch (IOException e) {
            System.err.println(FILE_ERROR_MESSAGE);
            logger.severe(FILE_ERROR_MESSAGE + StringUtil.getDetails(e));

        } catch (Exception ex) {
            System.err.println(UNKNOWN_ERROR_MESSAGE);
            logger.severe(UNKNOWN_ERROR_MESSAGE + StringUtil.getDetails(ex));
        }
    }

}
