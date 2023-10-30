package unicash.logic;

import java.nio.file.Path;
import java.util.HashMap;

import javafx.collections.ObservableList;
import unicash.commons.core.GuiSettings;
import unicash.logic.commands.CommandResult;
import unicash.logic.commands.exceptions.CommandException;
import unicash.logic.parser.exceptions.ParseException;
import unicash.model.transaction.Transaction;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the user prefs' UniCash file path.
     */
    Path getUniCashFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns an unmodifiable view of the filtered list of transactions
     */
    ObservableList<Transaction> getFilteredTransactionList();

    /**
     * Returns a HashMap of the expense summary from the model
     */
    HashMap<String, Double> getExpenseSummary();
}
