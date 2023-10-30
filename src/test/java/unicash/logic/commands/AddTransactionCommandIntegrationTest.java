package unicash.logic.commands;

import static unicash.logic.commands.CommandTestUtil.assertCommandSuccess;
import static unicash.testutil.TypicalTransactions.getTypicalUniCash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unicash.logic.UniCashMessages;
import unicash.model.Model;
import unicash.model.ModelManager;
import unicash.model.UserPrefs;
import unicash.model.transaction.Transaction;
import unicash.testutil.TransactionBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddTransactionCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalUniCash(), new UserPrefs());
    }

    @Test
    public void execute_newTransaction_success() {
        Transaction validTransaction = new TransactionBuilder().build();

        Model expectedModel = new ModelManager(getTypicalUniCash(), new UserPrefs());
        expectedModel.addTransaction(validTransaction);

        assertCommandSuccess(new AddTransactionCommand(validTransaction), model,
                String.format(AddTransactionCommand.MESSAGE_SUCCESS,
                        UniCashMessages.formatTransaction(validTransaction)), expectedModel);
    }

    @Test
    public void execute_duplicateTransaction_success() {
        Transaction transactionInList = model.getUniCash().getTransactionList().get(0);
        Model expectedModel = new ModelManager(getTypicalUniCash(), new UserPrefs());
        expectedModel.addTransaction(transactionInList);
        assertCommandSuccess(new AddTransactionCommand(transactionInList), model,
                String.format(AddTransactionCommand.MESSAGE_SUCCESS,
                        UniCashMessages.formatTransaction(transactionInList)), expectedModel);
    }

}
