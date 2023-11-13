package unicash.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PrefixTest {
    @Test
    public void hashCode_nullPrefix_returnsZero() {
        var prefix = new Prefix(null);
        assertEquals(0, prefix.hashCode());
    }

    @Test
    public void hashCode_nonNullPrefix_returnsNonZero() {
        var prefix = new Prefix("p/");
        assertTrue(prefix.hashCode() != 0);
    }
}
