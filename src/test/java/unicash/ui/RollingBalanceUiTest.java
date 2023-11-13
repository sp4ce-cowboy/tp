package unicash.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unicash.model.util.SampleDataUtil.getSampleUniCash;
import static unicash.testutil.TestDataUtil.getSumOfTestExpenses;
import static unicash.testutil.TestDataUtil.getSumOfTestIncomes;
import static unicash.testutil.TestDataUtil.getTestTransactions;
import static unicash.testutil.TestDataUtil.getTestTransactionsAsUserInputs;

import java.nio.file.Path;
import java.util.Collections;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import unicash.MainApp;
import unicash.commons.enums.CommandType;
import unicash.commons.enums.TransactionType;
import unicash.model.transaction.Transaction;
import unicash.model.transaction.predicates.TransactionNameContainsKeywordsPredicate;
import unicash.testutil.TransactionBuilder;
import unicash.testutil.UserInputBuilder;

@ExtendWith(ApplicationExtension.class)
public class RollingBalanceUiTest {

    @TempDir
    Path tempDir;

    @BeforeEach
    public void setUp() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(() -> new MainApp(tempDir.resolve("ui_data.json")));
        FxToolkit.showStage();
        WaitForAsyncUtils.waitForFxEvents(20);
    }

    @AfterEach
    public void stopApp() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    @Test
    public void rollingBalance_unicashResetState_showsDefaultBalance(FxRobot robot) {
        var rollingBalanceNode = robot.lookup("#balanceIndicator").tryQuery();
        assertTrue(rollingBalanceNode.isPresent());
        var rollingBalanceNodeLabel = (Label) rollingBalanceNode.get();
        robot.clickOn("#commandBoxPlaceholder");
        robot.write(CommandType.RESET.getMainCommandWord());
        robot.type(KeyCode.ENTER);

        Double resetUniCashSumOfIncomes = getSampleUniCash().getTransactionList()
                .stream()
                .filter(x -> x.getTypeString().equalsIgnoreCase(
                        String.valueOf(TransactionType.INCOME)))
                .map(Transaction::getAmountAsDouble)
                .reduce(0.0, Double::sum);


        Double resetUniCashSumOfExpenses = getSampleUniCash().getTransactionList()
                .stream()
                .filter(x -> x.getTypeString().equalsIgnoreCase(
                        String.valueOf(TransactionType.EXPENSE)))
                .map(Transaction::getAmountAsDouble)
                .reduce(0.0, Double::sum);

        Double resetUniCashNetSum = resetUniCashSumOfIncomes - resetUniCashSumOfExpenses;

        String formattedRollingBalanceLabel;
        if (Double.compare(resetUniCashNetSum, 0) < 0) {
            formattedRollingBalanceLabel = String.format("Rolling Balance: -$%.2f",
                    Math.abs(resetUniCashNetSum));
        } else {
            formattedRollingBalanceLabel = String.format("Rolling Balance: $%.2f",
                    resetUniCashNetSum);
        }

        /* Format rolling balance string to account for negative values */
        assertEquals(rollingBalanceNodeLabel.getText(), formattedRollingBalanceLabel);

    }


    @Test
    public void rollingBalance_filteredTransactionList_showsCorrectBalance(FxRobot robot) {

        /* All transactions in UniCash are cleared at first */
        robot.clickOn("#commandBoxPlaceholder");
        robot.write(CommandType.CLEAR_TRANSACTIONS.getMainCommandWord());
        robot.type(KeyCode.ENTER);

        /* Each test transaction is manually input into the command box */
        Transaction[] transactionList = getTestTransactions();
        for (String userInput : getTestTransactionsAsUserInputs()) {
            robot.clickOn("#commandBoxPlaceholder");
            robot.write(userInput);
            robot.type(KeyCode.ENTER);

        }

        /* The sum of expenses is tabulated from the test transactions list internally */
        Double lunchExpensesSum = getSumOfTestExpenses(
                new TransactionNameContainsKeywordsPredicate(
                        Collections.singletonList("lunch")));

        /* The sum of incomes is tabulated from the test transactions list internally.
         * This is added to account for incomes that might contain the word "lunch" in
         * their names, for e.g "Fund-raising luncheon" thus covering both boundaries */
        Double lunchIncomesSum = getSumOfTestIncomes(
                new TransactionNameContainsKeywordsPredicate(
                        Collections.singletonList("lunch")));

        Double rollingBalance = lunchIncomesSum - lunchExpensesSum;

        /* User input for Find command constructed */
        String userInputString = new UserInputBuilder(
                new TransactionBuilder().withName("lunch"))
                .addName()
                .addCommand(CommandType.FIND)
                .toString();


        /* Find command is input by the FxRobot */
        robot.clickOn("#commandBoxPlaceholder");
        robot.write(userInputString);
        robot.type(KeyCode.ENTER);

        var rollingBalanceNode = robot.lookup("#balanceIndicator").tryQuery();
        assert rollingBalanceNode.isPresent();
        var rollingBalanceNodeLabel = (Label) rollingBalanceNode.get();

        /* Format rolling balance string to account for negative values */
        String formattedRollingBalanceLabel;
        if (Double.compare(rollingBalance, 0) < 0) {
            formattedRollingBalanceLabel = String.format("Rolling Balance: -$%.2f",
                    Math.abs(rollingBalance));
        } else {
            formattedRollingBalanceLabel = String.format("Rolling Balance: $%.2f",
                    rollingBalance);
        }

        assertEquals(formattedRollingBalanceLabel, rollingBalanceNodeLabel.getText());

    }

}

