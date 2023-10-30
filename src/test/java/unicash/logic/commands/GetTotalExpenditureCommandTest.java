package unicash.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unicash.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import unicash.commons.enums.TransactionType;
import unicash.logic.commands.exceptions.CommandException;
import unicash.model.Model;
import unicash.model.ModelManager;
import unicash.model.UniCash;
import unicash.model.UserPrefs;
import unicash.model.category.Category;
import unicash.testutil.TransactionBuilder;

public class GetTotalExpenditureCommandTest {

    private static final Model BASE_MODEL = getModel();

    @Test
    public void constructor_givenYear_usesGivenYearInsteadOfDefault() {
        var command = new GetTotalExpenditureCommand(10, 2022, null);
        var expected =
                GetTotalExpenditureCommand.class.getCanonicalName()
                        + "{month=10, year=2022, categoryFilter=null}";
        assertEquals(expected, command.toString());
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        var command = new GetTotalExpenditureCommand(10, 2002, null);
        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void execute_negativeMonth_throwsCommandException() {
        var command = new GetTotalExpenditureCommand(-12, 2002, null);
        assertThrows(CommandException.class, () -> command.execute(BASE_MODEL));
    }

    @Test
    public void execute_monthGreaterThan12_throwsCommandException() {
        var command = new GetTotalExpenditureCommand(13, 2002, null);
        assertThrows(CommandException.class, () -> command.execute(BASE_MODEL));
    }

    @Test
    public void execute_yearLessThan1920_throwsCommandException() {
        var command = new GetTotalExpenditureCommand(13, 200, null);
        assertThrows(CommandException.class, () -> command.execute(BASE_MODEL));

        var negativeYearCommand = new GetTotalExpenditureCommand(13, -10, null);
        assertThrows(CommandException.class, () -> negativeYearCommand.execute(BASE_MODEL));
    }

    @Test
    public void execute_validMonthWithOnlyOneMonth_filtersOnlyExpenses() throws CommandException {
        var model = getModel();
        model.addTransaction(new TransactionBuilder().withType("income").build());
        model.addTransaction(new TransactionBuilder().withType("expense").build());
        model.addTransaction(new TransactionBuilder().withType("expense").build());
        var command = new GetTotalExpenditureCommand(8, 2001, null);
        command.execute(model);
        var filteredResult = model.getFilteredTransactionList();
        assertEquals(2, filteredResult.size());
        for (var result : filteredResult) {
            assertEquals(TransactionType.EXPENSE, result.getType().type);
        }
    }

    @Test
    public void execute_multipleMonthsOnly_filtersOnlySelectedMonths() throws CommandException {
        var model = getModel();
        model.addTransaction(new TransactionBuilder().withType("expense").build());
        model.addTransaction(new TransactionBuilder().withType("expense").withDateTime("18-07-2001 00:00").build());
        model.addTransaction(new TransactionBuilder().withType("expense").build());
        var command = new GetTotalExpenditureCommand(8, 2001, null);
        command.execute(model);
        var filteredResult = model.getFilteredTransactionList();
        assertEquals(2, filteredResult.size());
        for (var result : filteredResult) {
            assertEquals(TransactionType.EXPENSE, result.getType().type);
            assertEquals(8, result.getDateTime().getDateTime().getMonthValue());
        }
    }

    @Test
    public void execute_expenseWithoutCategoryWithCategoryFilter_notIncludedInFilter() throws CommandException {
        var model = getModel();
        // This transaction does not contain any categories even if it's in August
        model.addTransaction(new TransactionBuilder().withCategories().withType("expense").build());
        model.addTransaction(new TransactionBuilder().withType("expense").withDateTime("18-07-2001 00:00").build());
        model.addTransaction(new TransactionBuilder().withType("expense").withCategories("Food").build());
        var command = new GetTotalExpenditureCommand(8, 2001, new Category("Food"));
        command.execute(model);
        var filteredResult = model.getFilteredTransactionList();
        assertEquals(1, filteredResult.size());
        for (var result : filteredResult) {
            assertEquals(TransactionType.EXPENSE, result.getType().type);
            assertEquals(8, result.getDateTime().getDateTime().getMonthValue());
        }
    }

    @Test
    public void execute_expenseWithoutCategoryWithoutCategoryFilter_includedInFilter() throws CommandException {
        var model = getModel();
        // This transaction does not contain any categories even if it's in August
        // This should be included this round as no category filter is in place
        model.addTransaction(new TransactionBuilder().withCategories().withType("expense").build());
        model.addTransaction(new TransactionBuilder().withType("expense").withDateTime("18-07-2001 00:00").build());
        model.addTransaction(new TransactionBuilder().withType("expense").withCategories("Food").build());
        var command = new GetTotalExpenditureCommand(8, 2001, null);
        command.execute(model);
        var filteredResult = model.getFilteredTransactionList();
        assertEquals(2, filteredResult.size());
        for (var result : filteredResult) {
            assertEquals(TransactionType.EXPENSE, result.getType().type);
            assertEquals(8, result.getDateTime().getDateTime().getMonthValue());
        }
    }

    @Test
    public void execute_multipleCategoriesOnly_filtersOnlySelectedCategory() throws CommandException {
        var model = getModel();
        model.addTransaction(new TransactionBuilder().withType("expense").withCategories("Food").build());
        model.addTransaction(new TransactionBuilder().withType("expense").withCategories("Others").build());
        model.addTransaction(new TransactionBuilder().withType("expense").build());
        var command = new GetTotalExpenditureCommand(8, 2001, new Category("Food"));
        command.execute(model);
        var filteredResult = model.getFilteredTransactionList();
        assertEquals(2, filteredResult.size());
        for (var result : filteredResult) {
            assertEquals(TransactionType.EXPENSE, result.getType().type);
            assertTrue(result.getCategories().asUnmodifiableObservableList()
                    .stream().anyMatch(cat -> cat.equals(new Category("Food"))));
        }
    }

    @Test
    public void execute_multipleMonthsAndCategories_filtersForSelectedMonthAndCategory() throws CommandException {
        var model = getModel();
        model.addTransaction(new TransactionBuilder().withType("expense").withDateTime("23-06-2001 00:00").build());
        model.addTransaction(new TransactionBuilder().withType("expense").withCategories("Others").build());
        model.addTransaction(new TransactionBuilder().withType("expense").build());
        var command = new GetTotalExpenditureCommand(8, 2001, new Category("Food"));
        command.execute(model);
        var filteredResult = model.getFilteredTransactionList();
        assertEquals(1, filteredResult.size());
        for (var result : filteredResult) {
            assertEquals(TransactionType.EXPENSE, result.getType().type);
            assertTrue(result.getCategories().asUnmodifiableObservableList()
                    .stream().anyMatch(cat -> cat.equals(new Category("Food"))));
            assertEquals(8, result.getDateTime().getDateTime().getMonthValue());
        }
    }

    @Test
    public void execute_multipleMonthsAndCategories_returnsValidTotalExpenditure() throws CommandException {
        var model = getModel();
        model.addTransaction(new TransactionBuilder().withType("expense").withDateTime("23-06-2001 00:00").build());
        model.addTransaction(new TransactionBuilder().withType("expense").withCategories("Others").build());
        model.addTransaction(new TransactionBuilder().withType("expense").build());
        model.addTransaction(new TransactionBuilder().withType("expense").withAmount(133.15).build());
        var command = new GetTotalExpenditureCommand(8, 2001, new Category("Food"));
        var result = command.execute(model);
        var filteredResult = model.getFilteredTransactionList();
        assertEquals(2, filteredResult.size());
        assertEquals(
                String.format(GetTotalExpenditureCommand.MESSAGE_SUCCESS, "August", 2001, 123.45 + 133.15),
                result.getFeedbackToUser()
        );
    }

    @Test
    public void execute_multipleCategories_includedIfOneCategoryFitsFilter() throws CommandException {
        var model = getModel();
        model.addTransaction(
                new TransactionBuilder()
                        .withType("expense")
                        .withCategories("Food", "Drinks", "Social")
                        .build()
        );
        model.addTransaction(new TransactionBuilder().withType("expense").withCategories().build());
        model.addTransaction(new TransactionBuilder().withType("expense").withCategories("School", "Food").build());
        var command = new GetTotalExpenditureCommand(8, 2001, new Category("Food"));
        command.execute(model);
        var filteredResult = model.getFilteredTransactionList();
        assertEquals(2, filteredResult.size());
        for (var res : filteredResult) {
            assertEquals(TransactionType.EXPENSE, res.getType().type);
            boolean hasMatchingCategory = false;
            for (var category : res.getCategories()) {
                if (category.equals(new Category("Food"))) {
                    hasMatchingCategory = true;
                    break;
                }
            }
            assertTrue(hasMatchingCategory);
        }
    }

    @Test
    public void execute_expensesWithDifferentYears_filtersForOnlyGivenYear() throws CommandException {
        var model = getModel();
        model.addTransaction(new TransactionBuilder().withType("expense").withDateTime("23-06-2001 00:00").build());
        model.addTransaction(new TransactionBuilder().withType("expense").withCategories("Others").build());
        model.addTransaction(new TransactionBuilder().withType("expense").withDateTime("23-06-2002 00:00").build());
        model.addTransaction(new TransactionBuilder().withType("expense").withAmount(133.15).build());
        var command = new GetTotalExpenditureCommand(8, 2001, new Category("Food"));
        var result = command.execute(model);
        var filteredResult = model.getFilteredTransactionList();
        assertEquals(1, filteredResult.size());
        assertEquals(
                String.format(GetTotalExpenditureCommand.MESSAGE_SUCCESS, "August", 2001, 133.15),
                result.getFeedbackToUser()
        );
    }

    @Test
    public void toString_noInput_returnsCommandStringFormatted() {
        var currentYear = LocalDate.now().getYear();
        var command = new GetTotalExpenditureCommand(8, currentYear, new Category("Food"));
        var toStringResult = command.toString();
        String expected =
                GetTotalExpenditureCommand.class.getCanonicalName()
                        + "{month=8, year=" + currentYear + ", categoryFilter=food}";
        assertEquals(expected, toStringResult);
    }

    @Test
    public void equals_sameInstance_returnsTrue() {
        var command = new GetTotalExpenditureCommand(8, 2002, new Category("Food"));
        assertEquals(command, command);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        var command = new GetTotalExpenditureCommand(8, 2002, new Category("Food"));
        assertFalse(command.equals(5));
    }

    @Test
    public void equals_allSame_returnsTrue() {
        var command = new GetTotalExpenditureCommand(8, 2006, new Category("Food"));
        var other = new GetTotalExpenditureCommand(8, 2006, new Category("Food"));
        assertEquals(command, other);
    }

    @Test
    public void equals_differentYear_returnsFalse() {
        var command = new GetTotalExpenditureCommand(8, 2007, new Category("Food"));
        var other = new GetTotalExpenditureCommand(8, 2008, new Category("Food"));
        assertNotEquals(command, other);
    }

    @Test
    public void equals_differentCategoryFilter_returnsFalse() {
        var command = new GetTotalExpenditureCommand(8, 2002, new Category("Food"));
        var other = new GetTotalExpenditureCommand(8, 2002, new Category("Others"));
        assertNotEquals(command, other);
    }

    @Test
    public void equals_differentMonth_returnsFalse() {
        var command = new GetTotalExpenditureCommand(7, 2002, new Category("Food"));
        var other = new GetTotalExpenditureCommand(8, 2002, new Category("Food"));
        assertNotEquals(command, other);
    }

    @Test
    public void equals_differentMonthAndCategoryFilter_returnsFalse() {
        var command = new GetTotalExpenditureCommand(7, 2002, new Category("Food"));
        var other = new GetTotalExpenditureCommand(8, 2002, new Category("Others"));
        assertNotEquals(command, other);
    }

    @Test
    public void equals_differentMonthAndYear_returnsFalse() {
        var command = new GetTotalExpenditureCommand(7, 2006, new Category("Food"));
        var other = new GetTotalExpenditureCommand(8, 2007, new Category("Food"));
        assertNotEquals(command, other);
    }

    @Test
    public void equals_differentYearAndCategoryFilter_returnsFalse() {
        var command = new GetTotalExpenditureCommand(8, 2006, new Category("Food"));
        var other = new GetTotalExpenditureCommand(8, 2007, new Category("Others"));
        assertNotEquals(command, other);
    }

    @Test
    public void equals_nullCatFilterOtherNonNullCatFilter_returnsFalse() {
        var command = new GetTotalExpenditureCommand(7, 2002, null);
        var other = new GetTotalExpenditureCommand(7, 2002, new Category("Others"));
        assertNotEquals(command, other);
    }

    @Test
    public void equals_nullCatFilterOtherNullCatFilter_returnsTrue() {
        var command = new GetTotalExpenditureCommand(7, 2002, null);
        var other = new GetTotalExpenditureCommand(7, 2002, null);
        assertEquals(command, other);
    }

    private static Model getModel() {
        return new ModelManager(new UniCash(), new UserPrefs());
    }
}
