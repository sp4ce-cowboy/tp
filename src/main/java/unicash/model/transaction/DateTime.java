package unicash.model.transaction;

import static unicash.commons.util.AppUtil.checkArgument;
import static unicash.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
/**
 * Represents a Transaction's dateTime.
 * Guarantees: immutable;
 */
public class DateTime {
    public static final String DATETIME_PATTERN_ONE = "dd-MM-uuuu HH:mm";
    public static final String DATETIME_PATTERN_TWO = "uuuu-MM-dd HH:mm";
    public static final String DATETIME_STORAGE_PATTERN = "dd MMM uuuu HH:mm";
    public static final String MESSAGE_CONSTRAINTS =
            "DateTime should be in either of the following formats: " + "\n"
                    + "1. " + DATETIME_PATTERN_ONE + "\n"
                    + "2. " + DATETIME_PATTERN_TWO + "\n"
                    + "3. " + DATETIME_STORAGE_PATTERN + "\n";
    //accept date in multiple formats
    private static final DateTimeFormatterBuilder DATETIME_FORMATTER_BUILDER =
            new DateTimeFormatterBuilder()
                    .appendOptional(DateTimeFormatter.ofPattern(DATETIME_PATTERN_ONE))
                    .appendOptional(DateTimeFormatter.ofPattern(DATETIME_PATTERN_TWO))
                    .appendOptional(DateTimeFormatter.ofPattern(DATETIME_STORAGE_PATTERN));

    private static final DateTimeFormatter DATETIME_FORMATTER = DATETIME_FORMATTER_BUILDER.toFormatter();

    private String originalDateTime;
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
            LocalDateTime now = LocalDateTime.now(clock).truncatedTo(ChronoUnit.MINUTES);
            this.dateTime = now;
            originalDateTime = now.format(DateTimeFormatter.ofPattern(DATETIME_STORAGE_PATTERN));
            return;
        }
        checkArgument(isValidDateTime(dateTime), MESSAGE_CONSTRAINTS);
        this.dateTime = LocalDateTime.parse(dateTime, DATETIME_FORMATTER);
        originalDateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Helper method to return original text string
     *
     * @return text string of the LocalDateTime object
     */
    public String inputString() {
        return originalDateTime;
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
        return dateTime.format(DateTimeFormatter.ofPattern(DATETIME_STORAGE_PATTERN));
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
