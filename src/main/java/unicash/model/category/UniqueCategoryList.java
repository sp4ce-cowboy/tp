package unicash.model.category;

import static java.util.Objects.requireNonNull;
import static unicash.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import unicash.model.category.exceptions.CategoryNotFoundException;
import unicash.model.category.exceptions.DuplicateCategoryException;
import unicash.model.category.exceptions.MaxCategoryException;

/**
 * A list of categories that enforces uniqueness between its elements and does not allow nulls.
 * A category is considered unique by comparing using {@code Category#equals(Category)}. As such, adding and updating of
 * categories uses Category#equals(Category) for equality so as to ensure that the category being added or updated is
 * unique in terms of identity in the UniqueCategoryList.
 * <p>
 * Supports a minimal set of list operations.
 */
public class UniqueCategoryList implements Iterable<Category> {

    public static final String MESSAGE_SIZE_CONSTRAINTS =
            "There should only be a maximum of 5 unique categories.";
    public static final String MESSAGE_DUPLICATION_CONSTRAINTS =
            "All categories must be unique, duplicate categories are not allowed.";
    public static final int MAX_CATEGORIES = 5;

    private final ObservableList<Category> internalList = FXCollections.observableArrayList();
    private final ObservableList<Category> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Default constructor to create an empty category list.
     */
    public UniqueCategoryList() {}

    /**
     * Constructs a category list from a List of {@code Category}.
     *
     * @param categoryList the category list to be constructed into.
     */
    public UniqueCategoryList(List<Category> categoryList) {
        requireNonNull(categoryList);
        if (!categoriesAreUnique(categoryList)) {
            throw new DuplicateCategoryException();
        }

        if (isMoreThanMax(categoryList)) {
            throw new MaxCategoryException();
        }
        internalList.setAll(categoryList);
    }

    /**
     * Returns {@code true} if the list contains an equivalent category as the given argument.
     */
    public boolean contains(Category toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Returns true is the storage is full, false otherwise.
     */
    public boolean isMax() {
        return internalList.size() == MAX_CATEGORIES;
    }

    /**
     * Adds a category to the list.
     * The category must not already exist in the list.
     */
    public void add(Category toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCategoryException();
        }
        if (isMax()) {
            throw new MaxCategoryException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the category {@code target} in the list with {@code editedCategory}.
     * {@code target} must exist in the list.
     * The category identity of {@code editedCategory} must not be the same as another existing category in the list.
     */
    public void setCategory(Category target, Category editedCategory) {
        requireAllNonNull(target, editedCategory);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CategoryNotFoundException();
        }

        if (!target.equals(editedCategory) && contains(editedCategory)) {
            throw new DuplicateCategoryException();
        }

        internalList.set(index, editedCategory);
    }

    /**
     * Removes the equivalent category from the list.
     * The category exist in the list.
     */
    public void remove(Category toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CategoryNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code categories}.
     */
    public void setCategories(UniqueCategoryList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code categories}.
     * {@code categories} must not contain duplicate categories.
     */
    public void setCategories(List<Category> categories) {
        requireAllNonNull(categories);
        if (!categoriesAreUnique(categories)) {
            throw new DuplicateCategoryException();
        }

        if (isMoreThanMax(categories)) {
            throw new MaxCategoryException();
        }

        internalList.setAll(categories);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Category> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Category> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UniqueCategoryList
                && new HashSet<>(internalList).equals(new HashSet<>(((UniqueCategoryList) other).internalList)));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns the size of the encapsulated ObservableList
     *
     * @return int size of the list
     */
    public int getSize() {
        return internalList.size();
    }

    /**
     * Returns true if a given list of categories is less than the maximum allowed categories.
     */
    public static boolean isMoreThanMax(List<Category> categories) {
        return categories.size() > MAX_CATEGORIES;
    }

    /**
     * Returns {@code true} if {@code categories} contains only unique categories.
     */
    public static boolean categoriesAreUnique(List<Category> categories) {
        HashSet<Category> set = new HashSet<>();
        for (Category category : categories) {
            if (set.contains(category)) {
                return false;
            }
            set.add(category);
        }
        return true;
    }
}
