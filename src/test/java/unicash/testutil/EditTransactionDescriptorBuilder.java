package unicash.testutil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import unicash.logic.commands.EditTransactionCommand;
import unicash.model.category.Category;
import unicash.model.category.UniqueCategoryList;
import unicash.model.commons.Amount;
import unicash.model.transaction.DateTime;
import unicash.model.transaction.Location;
import unicash.model.transaction.Name;
import unicash.model.transaction.Transaction;
import unicash.model.transaction.Type;

/**
 * A utility class to help with building EditTransactionDescriptor objects.
 */
public class EditTransactionDescriptorBuilder {
    private final EditTransactionCommand.EditTransactionDescriptor descriptor;

    public EditTransactionDescriptorBuilder() {
        descriptor = new EditTransactionCommand.EditTransactionDescriptor();
    }

    public EditTransactionDescriptorBuilder(EditTransactionCommand.EditTransactionDescriptor descriptor) {
        this.descriptor = new EditTransactionCommand.EditTransactionDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTransactionDescriptor} with fields containing {@code transaction}'s details
     */
    public EditTransactionDescriptorBuilder(Transaction transaction) {
        descriptor = new EditTransactionCommand.EditTransactionDescriptor();
        descriptor.setName(transaction.getName());
        descriptor.setAmount(transaction.getAmount());
        descriptor.setDateTime(transaction.getDateTime());
        descriptor.setLocation(transaction.getLocation());
        descriptor.setType(transaction.getType());
        descriptor.setCategories(transaction.getCategories());
    }

    /**
     * Sets the {@code Name} of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withAmount(double amount) {
        descriptor.setAmount(new Amount(amount));
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withDateTime(String dateTime) {
        descriptor.setDateTime(new DateTime(dateTime));
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withLocation(String location) {
        descriptor.setLocation(new Location(location));
        return this;
    }

    /**
     * Sets the {@code Type} of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withType(String type) {
        descriptor.setType(new Type(type));
        return this;
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>} and set it to the {@code EditTransactionDescriptor}
     * that we are building.
     */
    public EditTransactionDescriptorBuilder withCategories(String... categories) {
        List<Category> categoriesList = Stream.of(categories).map(Category::new).collect(Collectors.toList());
        descriptor.setCategories(new UniqueCategoryList(categoriesList));
        return this;
    }

    public EditTransactionCommand.EditTransactionDescriptor build() {
        return descriptor;
    }
}
