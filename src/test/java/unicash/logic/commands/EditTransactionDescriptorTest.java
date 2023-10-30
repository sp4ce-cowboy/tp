package unicash.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unicash.logic.commands.CommandTestUtil.DESC_NUS;
import static unicash.logic.commands.CommandTestUtil.VALID_AMOUNT_SHOPPING;
import static unicash.logic.commands.CommandTestUtil.VALID_DATETIME_SHOPPING;
import static unicash.logic.commands.CommandTestUtil.VALID_LOCATION_ORCHARD;
import static unicash.logic.commands.CommandTestUtil.VALID_TRANSACTION_NAME_SHOPPING;
import static unicash.logic.commands.CommandTestUtil.VALID_TYPE_INCOME;
import static unicash.logic.commands.EditTransactionCommand.EditTransactionDescriptor;

import org.junit.jupiter.api.Test;

import unicash.model.transaction.Name;
import unicash.testutil.EditTransactionDescriptorBuilder;

public class EditTransactionDescriptorTest {

    @Test
    public void equals_sameInstance_returnsTrue() {
        assertEquals(DESC_NUS, DESC_NUS);
    }

    @Test
    public void equals_constructorCopy_returnsTrue() {
        var descriptor = new EditTransactionDescriptor(DESC_NUS);
        assertEquals(DESC_NUS, descriptor);
    }

    @Test
    public void equals_otherNull_returnsFalse() {
        assertNotEquals(DESC_NUS, null);
    }

    @Test
    public void equals_otherDifferentType_returnsFalse() {
        assertFalse(DESC_NUS.equals(5));
    }

    @Test
    public void equals_differentName_returnsFalse() {
        var other = new EditTransactionDescriptorBuilder(DESC_NUS).withName(VALID_TRANSACTION_NAME_SHOPPING).build();
        assertNotEquals(DESC_NUS, other);
    }

    @Test
    public void equals_differentAmount_returnsFalse() {
        var other = new EditTransactionDescriptorBuilder(DESC_NUS).withAmount(VALID_AMOUNT_SHOPPING).build();
        assertNotEquals(DESC_NUS, other);
    }

    @Test
    public void equals_differentDateTime_returnsFalse() {
        var other = new EditTransactionDescriptorBuilder(DESC_NUS).withDateTime(VALID_DATETIME_SHOPPING).build();
        assertNotEquals(DESC_NUS, other);
    }

    @Test
    public void equals_differentLocation_returnsFalse() {
        var other = new EditTransactionDescriptorBuilder(DESC_NUS).withLocation(VALID_LOCATION_ORCHARD).build();
        assertNotEquals(DESC_NUS, other);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        var other = new EditTransactionDescriptorBuilder(DESC_NUS).withLocation(VALID_TYPE_INCOME).build();
        assertNotEquals(DESC_NUS, other);
    }

    @Test
    public void equals_differentCategories_returnsFalse() {
        var other = new EditTransactionDescriptorBuilder(DESC_NUS).withCategories("Completely", "Different").build();
        assertNotEquals(DESC_NUS, other);
    }

    @Test
    public void isAnyFieldEdited_withNoEdits_returnsFalse() {
        var descriptor = new EditTransactionDescriptor();
        assertFalse(descriptor.isAnyFieldEdited());
    }

    @Test
    public void isAnyFieldEdited_withEdits_returnsTrue() {
        var descriptor = new EditTransactionDescriptor();
        descriptor.setName(new Name("Changed name"));
        assertTrue(descriptor.isAnyFieldEdited());
    }

    @Test
    public void toStringMethod() {
        var descriptor = new EditTransactionDescriptor();
        String expected = EditTransactionDescriptor.class.getCanonicalName() + "{name="
                + descriptor.getName().orElse(null) + ", amount="
                + descriptor.getAmount().orElse(null) + ", datetime="
                + descriptor.getDateTime().orElse(null) + ", location="
                + descriptor.getLocation().orElse(null) + ", type="
                + descriptor.getType().orElse(null) + ", categories="
                + descriptor.getCategories().orElse(null) + "}";
        assertEquals(expected, descriptor.toString());
    }
}
