package unicash.model.transaction.predicates;

import java.util.List;
import java.util.function.Predicate;

import unicash.commons.util.StringUtil;
import unicash.commons.util.ToStringBuilder;
import unicash.model.transaction.Transaction;

/**
 * Tests that a {@code Transactions}'s {@code UniqueCategoryList} matches any of
 * the keywords given.
 */
public class TransactionCategoryContainsKeywordsPredicate
        implements Predicate<Transaction> {
    private final List<String> keywords;

    public TransactionCategoryContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /* A nested anyMatch compares each keyword for each category in the categoryList.
     * This removes any String dependency entirely so the keyword "od,so" now will not
     * match something like "food,social" compared to if the string representation
     * of the categoryList was returned.
     */
    @Override
    public boolean test(Transaction transaction) {
        return keywords
                .stream()
                .anyMatch(keyword -> transaction
                        .getCategories()
                        .joinCategoriesAsList()
                        .stream()
                        .anyMatch(category ->
                                    StringUtil.containsSubstringIgnoreCase(
                                            category.toString(), keyword)
                                )
                );
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TransactionCategoryContainsKeywordsPredicate)) {
            return false;
        }

        TransactionCategoryContainsKeywordsPredicate otherNameContainsKeywordsPredicate =
                (TransactionCategoryContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
