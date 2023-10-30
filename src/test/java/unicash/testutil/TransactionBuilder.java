package unicash.testutil;


import unicash.model.category.Category;
import unicash.model.category.UniqueCategoryList;
import unicash.model.commons.Amount;
import unicash.model.transaction.DateTime;
import unicash.model.transaction.Location;
import unicash.model.transaction.Name;
import unicash.model.transaction.Transaction;
import unicash.model.transaction.Type;
import unicash.model.util.SampleDataUtil;

/**
 * A utility class to help with building Transaction objects.
 */
public class TransactionBuilder {

    public static final String DEFAULT_NAME = "Dog food";
    public static final double DEFAULT_AMOUNT = 123.45;
    public static final String DEFAULT_CATEGORY = "Food";
    public static final String DEFAULT_DATE_TIME = "18-08-2001 10:10";
    public static final String DEFAULT_LOCATION = "Fairprice";
    public static final String DEFAULT_TYPE = "income";

    private Name name;
    private Amount amount;
    private UniqueCategoryList categories;
    private DateTime dateTime;
    private Location location;
    private Type type;

    /**
     * Creates a {@code TransactionBuilder} with the default details.
     */
    public TransactionBuilder() {
        name = new Name(DEFAULT_NAME);
        amount = new Amount(DEFAULT_AMOUNT);
        dateTime = new DateTime(DEFAULT_DATE_TIME);
        location = new Location(DEFAULT_LOCATION);
        type = new Type(DEFAULT_TYPE);
        categories = new UniqueCategoryList();
        categories.add(new Category(DEFAULT_CATEGORY));
    }

    /**
     * Initializes the TransactionBuilder with the data of {@code transactionToCopy}.
     */
    public TransactionBuilder(Transaction transactionToCopy) {
        name = transactionToCopy.getName();
        amount = transactionToCopy.getAmount();
        dateTime = transactionToCopy.getDateTime();
        location = transactionToCopy.getLocation();
        type = transactionToCopy.getType();
        categories = transactionToCopy.getCategories();
    }

    /**
     * Sets the {@code Name} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withAmount(double amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code LocalDateTime} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withDateTime(String dateString) {
        this.dateTime = new DateTime(dateString);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    /**
     * Sets the {@code Type} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withType(String type) {
        this.type = new Type(type);
        return this;
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>} and set it to
     * the {@code Transaction} that we are building.
     */
    public TransactionBuilder withCategories(String ... categories) {
        this.categories = SampleDataUtil.getCategoryList(categories);
        return this;
    }

    public Transaction build() {
        return new Transaction(name, type, amount, dateTime, location, categories);
    }
}
