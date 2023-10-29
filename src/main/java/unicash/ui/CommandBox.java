package unicash.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import unicash.logic.Logic;
import unicash.logic.commands.CommandResult;
import unicash.logic.commands.exceptions.CommandException;
import unicash.logic.parser.exceptions.ParseException;


/**
 * The UI component that is responsible for receiving user command inputs.
 *
 * </p> Each user input, regardless of the correctness of input and as long as
 * the input is not blank, will be stored in a User Input History {@code ArrayList}.
 * The user can traverse through this history with the standard Keyboard Input
 * {@code UP} and {@code DOWN} arrow keys. The User Input History only persists
 * during the application's runtime, and will be reset once the application is closed.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final int DEFAULT_INDEX = -1;

    private final CommandExecutor commandExecutor;

    @FXML
    private TextField commandTextField;

    // To keep a history of previous userInputs
    private List<String> userInputHistory = new ArrayList<>();

    // To keep track of which userInput the user is currently on
    private int currentUserInputIndex = DEFAULT_INDEX;

    // To capture the latest user input text, which is blank by default
    private String latestUserInput = "";

    // To flag the state of the latest user input capture
    private boolean isLatestInputCaptured = false;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;

        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1) -> setStyleToDefault());

        /* Iterates through history of userInputs held in the userInputHistory List
         * the arrow "UP" key and arrow "DOWN" key are constants defined by the KeyCode
         * class, and user keyboard input is observed by the KeyEvent class. The user can
         * also input the "ESCAPE" key to blank the field, instead of having to manually
         * backspace through the command text field.
         *
         * If the user chooses to traverse the command history list while having already
         * typed something in the command text field, that input is captured and restored
         * after the last user input from command history. */
        commandTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
            case UP:
                if (!isLatestInputCaptured) {
                    captureCommandTextField();
                }
                showPreviousUserInput();
                commandTextField.end(); // Moves the text cursor to the end of the TextField
                break;
            case DOWN:
                showNextUserInput();
                commandTextField.end(); // Moves the text cursor to the end of the TextField
                break;
            case ESCAPE:
                clearCommandTextField(); // Clears the command text field while MainWindow is in focus
                break;
            default:
                break;
            }
        });
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

    /**
     * Returns the command text field as a TextField object
     *
     * @return the command text field
     */
    public TextField getCommandTextField() {
        return this.commandTextField;
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        try {
            addUserInputToHistory(commandText);
            commandExecutor.execute(commandText);
            clearCommandTextField();
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Clears the Command Text Field.
     */
    public void clearCommandTextField() {
        commandTextField.setText("");
    }

    /**
     * Captures the state of the Command Text Field.
     */
    public void captureCommandTextField() {
        latestUserInput = commandTextField.getText();
        isLatestInputCaptured = true;
    }

    /**
     * Restores the initial state of the Command Text Field.
     */
    public void restoreCommandTextField() {
        commandTextField.setText(latestUserInput);
        isLatestInputCaptured = false;
    }

    /**
     * Adds the input string to command history list and resets command index.
     */
    private void addUserInputToHistory(String input) {
        userInputHistory.add(input);
        currentUserInputIndex = DEFAULT_INDEX; // reset index as new command is added
    }

    /**
     * Iterates through userInput history backwards and sets the commandTextField text
     * stopping at the earliest entered user input.
     */
    private void showPreviousUserInput() {
        if (userInputHistory.isEmpty()) {
            return;
        }

        if (currentUserInputIndex == DEFAULT_INDEX) {
            currentUserInputIndex = userInputHistory.size() - 1;

        } else if (currentUserInputIndex > 0) {
            currentUserInputIndex--;
        }

        String userInputFromHistory = userInputHistory.get(currentUserInputIndex);
        commandTextField.setText(userInputFromHistory);
    }

    /**
     * Iterates through userInput history backwards and sets the commandTextField text
     * stopping at the latest state of the CommandTextField, which is cleared by default,
     * but if the user had entered an incomplete input while iterating through, that input
     * is restored.
     */
    private void showNextUserInput() {
        if (userInputHistory.isEmpty() || currentUserInputIndex == DEFAULT_INDEX) {
            return;
        }

        if (currentUserInputIndex < userInputHistory.size() - 1) {
            // If not at the end of the list, move one step forward i.e. down
            currentUserInputIndex++;

            String userInputFromHistory = userInputHistory.get(currentUserInputIndex);
            commandTextField.setText(userInputFromHistory);;

        } else {
            // If at the end of the list, reset index to -1 and restore text field
            currentUserInputIndex = DEFAULT_INDEX;
            restoreCommandTextField();
        }
    }
}
