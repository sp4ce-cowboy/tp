package unicash.model.transaction;

import static unicash.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import unicash.commons.util.ToStringBuilder;
import unicash.model.category.UniqueCategoryList;
import unicash.model.commons.Amount;

/**
 * Represents a Transaction in UniCash.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Transaction {
    private final Name name;
    private final Amount amount;
    private final DateTime dateTime;
    private final Location location;
    private final Type type;
    private final UniqueCategoryList categories = new UniqueCategoryList();


    /**
     * Constructs a Transaction with all fields populated.
     * Guarantees: details are present and not null, field values are validated, immutable.
     */
    public Transaction(
        Name name,
        Type type,
        Amount amount,
        DateTime dateTime,
        Location location,
        UniqueCategoryList categories
    ) {
        requireAllNonNull(name, type, amount, categories, dateTime, location);
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.dateTime = dateTime;
        this.location = location;
        this.categories.setCategories(categories);
    }

    public Name getName() {
        return name;
    }

    public Amount getAmount() {
        return amount;
    }

    public Double getAmountAsDouble() {
        return amount.amount;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public Location getLocation() {
        return location;
    }

    public Type getType() {
        return type;
    }

    /**
     * Returns the string value associated with this Type
     *
     * @return string value of type
     */
    public String getTypeString() {
        return type.type.getOriginalString();
    }

    public UniqueCategoryList getCategories() {
        return categories;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, amount, dateTime, location, type, categories);
    }

    /**
     * Returns true if both transactions have the same data fields.
     * This defines a stronger notion of equality between two transactions.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Transaction)) {
            return false;
        }

        Transaction otherTransaction = (Transaction) other;
        return name.equals(otherTransaction.name)
                && type.equals(otherTransaction.type)
                && amount.equals(otherTransaction.amount)
                && categories.equals(otherTransaction.categories)
                && dateTime.equals(otherTransaction.dateTime)
                && location.equals(otherTransaction.location);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("type", type)
                .add("amount", amount)
                .add("dateTime", dateTime)
                .add("location", location)
                .add("categories", categories)
                .toString();
    }
}
