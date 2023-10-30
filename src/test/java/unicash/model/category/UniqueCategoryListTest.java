package unicash.model.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unicash.testutil.Assert.assertThrows;
import static unicash.testutil.TypicalCategories.EDUCATION;
import static unicash.testutil.TypicalCategories.ENTERTAINMENT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import unicash.model.category.exceptions.CategoryNotFoundException;
import unicash.model.category.exceptions.DuplicateCategoryException;
import unicash.model.category.exceptions.MaxCategoryException;


public class UniqueCategoryListTest {

    private final UniqueCategoryList uniqueCategoryList = new UniqueCategoryList();

    @Test
    public void constructor_duplicate_throwsDuplicateCategoryException() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(EDUCATION);
        categoryList.add(EDUCATION);
        assertThrows(DuplicateCategoryException.class, () -> new UniqueCategoryList(categoryList));
    }

    @Test
    public void constructor_moreThanMaximumAllowed_throwsMaxCategoryException() {
        List<Category> categoryList = new ArrayList<>();
        for (int i = 0; i <= UniqueCategoryList.MAX_CATEGORIES; i++) {
            categoryList.add(new Category("Test" + i));
        }
        assertThrows(MaxCategoryException.class, () -> new UniqueCategoryList(categoryList));
    }

    @Test
    public void contains_nullCategory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCategoryList.contains(null));
    }

    @Test
    public void contains_categoryNotInList_returnsFalse() {
        assertFalse(uniqueCategoryList.contains(ENTERTAINMENT));
    }

    @Test
    public void contains_categoryInList_returnsTrue() {
        uniqueCategoryList.add(ENTERTAINMENT);
        assertTrue(uniqueCategoryList.contains(ENTERTAINMENT));
    }

    @Test
    public void add_nullCategory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCategoryList.add(null));
    }

    @Test
    public void add_duplicateCategory_throwsDuplicateCategoryException() {
        uniqueCategoryList.add(ENTERTAINMENT);
        assertThrows(DuplicateCategoryException.class, () -> uniqueCategoryList.add(ENTERTAINMENT));
    }

    @Test
    public void add_maxCategory_throwsMaxCategoryException() {
        for (int i = 0; i < UniqueCategoryList.MAX_CATEGORIES; i++) {
            uniqueCategoryList.add(new Category("Test" + i));
        }
        assertThrows(MaxCategoryException.class, () -> uniqueCategoryList.add(new Category("test5")));
    }

    @Test
    public void setCategory_nullTargetCategory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCategoryList.setCategory(null, ENTERTAINMENT));
    }

    @Test
    public void setCategory_nullEditedCategory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueCategoryList.setCategory(ENTERTAINMENT, null));
    }

    @Test
    public void setCategory_targetCategoryNotInList_throwsCategoryNotFoundException() {
        assertThrows(CategoryNotFoundException.class, () ->
                uniqueCategoryList.setCategory(ENTERTAINMENT, ENTERTAINMENT));
    }

    @Test
    public void setCategory_editedCategoryIsSameCategory_success() {
        uniqueCategoryList.add(ENTERTAINMENT);
        uniqueCategoryList.setCategory(ENTERTAINMENT, ENTERTAINMENT);
        UniqueCategoryList expectedUniqueCategoryList = new UniqueCategoryList();
        expectedUniqueCategoryList.add(ENTERTAINMENT);
        assertEquals(expectedUniqueCategoryList, uniqueCategoryList);
    }

    @Test
    public void setCategory_editedCategoryHasDifferentIdentity_success() {
        uniqueCategoryList.add(ENTERTAINMENT);
        uniqueCategoryList.setCategory(ENTERTAINMENT, EDUCATION);
        UniqueCategoryList expectedUniqueCategoryList = new UniqueCategoryList();
        expectedUniqueCategoryList.add(EDUCATION);
        assertEquals(expectedUniqueCategoryList, uniqueCategoryList);
    }

    @Test
    public void setCategory_editedCategoryHasNonUniqueIdentity_throwsDuplicateCategoryException() {
        uniqueCategoryList.add(ENTERTAINMENT);
        uniqueCategoryList.add(EDUCATION);
        assertThrows(DuplicateCategoryException.class, () -> uniqueCategoryList.setCategory(ENTERTAINMENT, EDUCATION));
    }

    @Test
    public void remove_nullCategory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCategoryList.remove(null));
    }

    @Test
    public void remove_categoryDoesNotExist_throwsCategoryNotFoundException() {
        assertThrows(CategoryNotFoundException.class, () -> uniqueCategoryList.remove(ENTERTAINMENT));
    }

    @Test
    public void remove_existingCategory_removesCategory() {
        uniqueCategoryList.add(ENTERTAINMENT);
        uniqueCategoryList.remove(ENTERTAINMENT);
        UniqueCategoryList expectedUniqueCategoryList = new UniqueCategoryList();
        assertEquals(expectedUniqueCategoryList, uniqueCategoryList);
    }

    @Test
    public void setCategories_nullUniqueCategoryList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCategoryList.setCategories((UniqueCategoryList) null));
    }

    @Test
    public void setCategories_uniqueCategoryList_replacesOwnListWithProvidedUniqueCategoryList() {
        uniqueCategoryList.add(ENTERTAINMENT);
        UniqueCategoryList expectedUniqueCategoryList = new UniqueCategoryList();
        expectedUniqueCategoryList.add(EDUCATION);
        uniqueCategoryList.setCategories(expectedUniqueCategoryList);
        assertEquals(expectedUniqueCategoryList, uniqueCategoryList);
    }

    @Test
    public void setCategories_moreThanMaxAllowed_throwsMaxCategoryException() {
        List<Category> categoryList = new ArrayList<>();
        for (int i = 0; i <= UniqueCategoryList.MAX_CATEGORIES; i++) {
            categoryList.add(new Category("Test" + i));
        }
        assertThrows(MaxCategoryException.class, () -> uniqueCategoryList.setCategories(categoryList));
    }


    @Test
    public void setCategories_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCategoryList.setCategories((List<Category>) null));
    }

    @Test
    public void setCategories_list_replacesOwnListWithProvidedList() {
        uniqueCategoryList.add(ENTERTAINMENT);
        List<Category> categoryList = Collections.singletonList(EDUCATION);
        uniqueCategoryList.setCategories(categoryList);
        UniqueCategoryList expectedUniqueCategoryList = new UniqueCategoryList();
        expectedUniqueCategoryList.add(EDUCATION);
        assertEquals(expectedUniqueCategoryList, uniqueCategoryList);
    }

    @Test
    public void setCategories_listWithDuplicateCategories_throwsDuplicateCategoryException() {
        List<Category> listWithDuplicateCategories = Arrays.asList(ENTERTAINMENT, ENTERTAINMENT);
        assertThrows(DuplicateCategoryException.class, () ->
                uniqueCategoryList.setCategories(listWithDuplicateCategories));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueCategoryList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueCategoryList.asUnmodifiableObservableList().toString(), uniqueCategoryList.toString());
    }

    @Test
    public void getSize_test() {
        UniqueCategoryList categoryList = new UniqueCategoryList();
        assertEquals(categoryList.getSize(), 0);
    }

    @Test
    public void emptyList_test() {
        UniqueCategoryList categoryList = new UniqueCategoryList();
        assertTrue(categoryList.isEmpty());
    }

    @Test
    public void joinCategoriesAsStringTest() {
        UniqueCategoryList categoryList = new UniqueCategoryList();
        categoryList.add(ENTERTAINMENT);
        categoryList.add(EDUCATION);
        assertEquals(ENTERTAINMENT + "," + EDUCATION,
                categoryList.joinCategoriesAsString());
    }

    @Test
    public void joinCategoriesAsListTest() {
        UniqueCategoryList categoryUniqueList = new UniqueCategoryList();
        categoryUniqueList.add(ENTERTAINMENT);
        categoryUniqueList.add(EDUCATION);

        List<Category> categoriesList = new ArrayList<>();
        categoriesList.add(ENTERTAINMENT);
        categoriesList.add(EDUCATION);

        // List return test
        assertEquals(categoriesList,
                categoryUniqueList.joinCategoriesAsList());

        // List toString return test
        assertEquals("[" + ENTERTAINMENT + ", " + EDUCATION + "]",
                categoryUniqueList.joinCategoriesAsList().toString());
    }

    @Test
    public void equals() {
        // same object -> returns true
        UniqueCategoryList categoryList = new UniqueCategoryList();
        assertEquals(categoryList, categoryList);

        // same lists
        categoryList.add(ENTERTAINMENT);
        UniqueCategoryList anotherList = new UniqueCategoryList();
        anotherList.add(ENTERTAINMENT);
        assertEquals(categoryList, anotherList);

        // different lists
        anotherList = new UniqueCategoryList();
        anotherList.add(EDUCATION);
        assertNotEquals(categoryList, anotherList);

        // null -> returns false
        assertNotEquals(null, categoryList);

        assertFalse(categoryList.equals(null));
    }

    @Test
    public void hashCode_test() {
        UniqueCategoryList categoryList1 = new UniqueCategoryList();
        UniqueCategoryList categoryList2 = new UniqueCategoryList();
        UniqueCategoryList categoryList3 = new UniqueCategoryList();
        categoryList3.add(ENTERTAINMENT);
        assertEquals(categoryList1.hashCode(), categoryList2.hashCode());
        assertNotEquals(categoryList1.hashCode(), categoryList3.hashCode());
    }
}
