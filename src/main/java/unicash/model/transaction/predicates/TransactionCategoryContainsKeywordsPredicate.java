package unicash.model.transaction.predicates;

import java.util.List;
import java.util.function.Predicate;

import unicash.commons.util.StringUtil;
import unicash.commons.util.ToStringBuilder;
import unicash.model.category.UniqueCategoryList;
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

    @Override
    public boolean test(Transaction transaction) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsSubstringIgnoreCase(
                        categoryJoiner(transaction.getCategories()), keyword));
    }

    /**
     * Returns all categories as a single string.
     *
     * @param categoryList
     * @return
     */
    public String categoryJoiner(UniqueCategoryList categoryList) {
        String originalToString = categoryList.toString();
        String trimmedToString = originalToString
                .substring(1, originalToString.length() - 1);

        String[] trimmedToStringArray = trimmedToString.split(", ");
        String joinedString = String.join("", trimmedToString);

        return joinedString;
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
