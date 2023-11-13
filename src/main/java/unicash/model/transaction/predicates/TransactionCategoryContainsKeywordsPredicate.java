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

    /**
     * Creates a new {@code TransactionCategoryContainsKeywordsPredicate} object
     * with the given list of string keywords.
     *
     * @param keywords the input list of keywords to be matched
     */
    public TransactionCategoryContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Returns true if any {@code Category} within the {@code UniqueCategoryList}
     * of the Transaction contains any of the keywords in the keywords list
     * as a substring.
     *
     * <p> A nested anyMatch compares each keyword for each category directly in the
     * {@code UniqueCategoryList}. This removes any dependency on {@code UniqueCategoryList}'s
     * representation of the Categories.
     *
     * @param transaction the input {@code Transaction} object to be tested
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
        return new ToStringBuilder(this)
                .add("keywords", keywords)
                .toString();
    }
}
