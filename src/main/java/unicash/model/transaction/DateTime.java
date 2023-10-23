package unicash.model.transaction;

import static unicash.commons.util.AppUtil.checkArgument;
import static unicash.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Objects;
/**
 * Represents a Transaction's dateTime.
 * Guarantees: immutable;
 */
public class DateTime {
    public static final String DATETIME_PATTERN = "dd-MM-uuuu HH:mm";
    public static final String MESSAGE_CONSTRAINTS =
            "DateTime should be in the following format " + DATETIME_PATTERN;
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter
            .ofPattern(DATETIME_PATTERN);
    private LocalDateTime dateTime;

    /**
     * Constructs a {@code DateTime}.
     * Defaults to current date if not provided.
     *
     * @param dateTime A valid date time.
     */
    public DateTime(String dateTime) {
        requireAllNonNull(dateTime);
        init(dateTime, Clock.systemDefaultZone());
    }

    /**
     * Constructs a {@code DateTime} with a Clock.
     * Defaults to current date if not provided.
     * Used for mocking the current Clock used to generate LocalDateTime.now().
     *
     * @param dateTime A valid date time.
     * @param clock A clock object to configure current time settings.
     */
    public DateTime(String dateTime, Clock clock) {
        requireAllNonNull(dateTime, clock);
        init(dateTime, clock);
    }

    /**
     * Initialises the DateTime object when called by constructors.
     * Sets the dateTime based on given {@code dateTime} and {@code clock}.
     *
     * @param dateTime the dateTime string to be set.
     * @param clock the clock object of the system.
     */
    private void init(String dateTime, Clock clock) {
        if (dateTime.isBlank()) {
            this.dateTime = LocalDateTime.now(clock);
            return;
        }
        checkArgument(isValidDateTime(dateTime), MESSAGE_CONSTRAINTS);
        this.dateTime = LocalDateTime.parse(dateTime, DATETIME_FORMATTER);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Helper method to stringify LocalDateTime objects into original text string
     * passed by the user.
     *
     * @return text string of the LocalDateTime object
     */
    public String originalString() {
        return dateTime.format(DateTimeFormatter.ofPattern(DATETIME_PATTERN));
    }

    /**
     * Returns true if a given string is a valid dateTime.
     */
    public static boolean isValidDateTime(String dateString) {
        try {
            LocalDateTime.parse(dateString, DATETIME_FORMATTER.withResolverStyle(ResolverStyle.STRICT));
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return dateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, HH:mm"));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DateTime)) {
            return false;
        }

        DateTime otherDateTime = (DateTime) other;
        return Objects.equals(this.dateTime, otherDateTime.dateTime);
    }

    @Override
    public int hashCode() {
        return this.dateTime.hashCode();
    }
}
