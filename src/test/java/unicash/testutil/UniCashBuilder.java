package unicash.testutil;

import unicash.model.UniCash;
import unicash.model.transaction.Transaction;

/**
 * A utility class to help with building UniCash objects.
 * Example usage: <br>
 *     {@code UniCash uc = new UniCashBuilder().withTransaction(NUS).build();}
 */
public class UniCashBuilder {

    private final UniCash uniCash;

    public UniCashBuilder() {
        uniCash = new UniCash();
    }

    public UniCashBuilder(UniCash transactionList) {
        this.uniCash = transactionList;
    }

    /**
     * Adds a new {@code Transaction} to the {@code UniCash} that we are building.
     */
    public UniCashBuilder withTransaction(Transaction transaction) {
        uniCash.addTransaction(transaction);
        return this;
    }

    public UniCash build() {
        return uniCash;
    }
}
