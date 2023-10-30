package unicash.testutil;

import static unicash.logic.commands.CommandTestUtil.VALID_AMOUNT_INTERN;
import static unicash.logic.commands.CommandTestUtil.VALID_AMOUNT_NUS;
import static unicash.logic.commands.CommandTestUtil.VALID_AMOUNT_SHOPPING;
import static unicash.logic.commands.CommandTestUtil.VALID_CATEGORY_EDUCATION;
import static unicash.logic.commands.CommandTestUtil.VALID_CATEGORY_ENTERTAINMENT;
import static unicash.logic.commands.CommandTestUtil.VALID_CATEGORY_NUS;
import static unicash.logic.commands.CommandTestUtil.VALID_DATETIME_INTERN;
import static unicash.logic.commands.CommandTestUtil.VALID_DATETIME_NUS;
import static unicash.logic.commands.CommandTestUtil.VALID_DATETIME_SHOPPING;
import static unicash.logic.commands.CommandTestUtil.VALID_LOCATION_NUS;
import static unicash.logic.commands.CommandTestUtil.VALID_LOCATION_ORCHARD;
import static unicash.logic.commands.CommandTestUtil.VALID_TRANSACTION_NAME_INTERN;
import static unicash.logic.commands.CommandTestUtil.VALID_TRANSACTION_NAME_NUS;
import static unicash.logic.commands.CommandTestUtil.VALID_TRANSACTION_NAME_SHOPPING;
import static unicash.logic.commands.CommandTestUtil.VALID_TYPE_EXPENSE;
import static unicash.logic.commands.CommandTestUtil.VALID_TYPE_INCOME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import unicash.model.UniCash;
import unicash.model.transaction.Transaction;

/**
 * A utility class containing a list of {@code Transaction} objects to be used in tests.
 */
public class TypicalTransactions {
    public static final Transaction BUYING_GROCERIES = new TransactionBuilder()
            .withName("Buying groceries")
            .withAmount(16.75)
            .withLocation("Sheng Shiong (UTown)")
            .withCategories("Groceries")
            .build();

    public static final Transaction DINING_WITH_FRIENDS = new TransactionBuilder()
            .withName("End of Semester Celebration")
            .withAmount(234.50)
            .withLocation("Poulet")
            .withDateTime("18-08-2001 18:18")
            .withCategories("Food")
            .build();

    public static final Transaction WORK_AT_LIHO = new TransactionBuilder()
            .withName("Working at liho")
            .withType("income")
            .withAmount(888)
            .withDateTime("18-09-2002 18:17")
            .build();

    // Manually added - Transaction's details found in {@code CommandTestUtil}
    public static final Transaction NUS = new TransactionBuilder().withName(VALID_TRANSACTION_NAME_NUS)
            .withType(VALID_TYPE_EXPENSE)
            .withAmount(VALID_AMOUNT_NUS)
            .withDateTime(VALID_DATETIME_NUS)
            .withLocation(VALID_LOCATION_NUS)
            .withCategories(VALID_CATEGORY_NUS)
            .build();
    public static final Transaction INTERN = new TransactionBuilder().withName(VALID_TRANSACTION_NAME_INTERN)
            .withType(VALID_TYPE_EXPENSE)
            .withAmount(VALID_AMOUNT_INTERN)
            .withDateTime(VALID_DATETIME_INTERN)
            .withLocation(VALID_LOCATION_ORCHARD)
            .build();

    public static final Transaction SHOPPING = new TransactionBuilder().withName(VALID_TRANSACTION_NAME_SHOPPING)
            .withType(VALID_TYPE_INCOME)
            .withAmount(VALID_AMOUNT_SHOPPING)
            .withDateTime(VALID_DATETIME_SHOPPING)
            .withLocation(VALID_LOCATION_ORCHARD)
            .withCategories(VALID_CATEGORY_ENTERTAINMENT, VALID_CATEGORY_EDUCATION)
            .build();
    /**
     * Returns a {@code UniCash} with all the typical transactions.
     */
    public static UniCash getTypicalUniCash() {
        UniCash typicalUnicash = new UniCash();
        for (Transaction transaction : getTypicalTransactions()) {
            typicalUnicash.addTransaction(transaction);
        }
        return typicalUnicash;
    }

    public static List<Transaction> getTypicalTransactions() {
        return new ArrayList<>(Arrays.asList(BUYING_GROCERIES, DINING_WITH_FRIENDS, WORK_AT_LIHO, NUS, INTERN));
    }
}
