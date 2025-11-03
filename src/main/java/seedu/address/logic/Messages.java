package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "This is an unknown command.\n"
            + "Please Type 'help' to see the list of available commands.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "This command format is invalid. \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The index provided is invalid.\n"
            + "Refer to current displayed list for valid indexes.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "You’ve entered multiple values for the following field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress());

        if (!person.getRoles().isEmpty()) {
            builder.append("; Roles: ");
            person.getRoles().forEach(builder::append);
        }

        person.getStatus().ifPresent(status -> builder.append("; Status: ").append(status));

        if (!person.getTags().isEmpty()) {
            builder.append("; Tags: ");
            person.getTags().forEach(builder::append);
        }

        return builder.toString();
    }

}
