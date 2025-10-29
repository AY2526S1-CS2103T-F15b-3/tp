package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagFormat(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "To utilise Tag Groups, Tags should "
            + "be in the format GROUP.VALUE\n"
            + "where both GROUP and VALUE are alphanumeric OR it could be a simple Tag with any alphanumeric text.\n"
            + "Tags are single word.";

    /**
     * Regex to validate tags of the form GROUP.VALUE,
     * where both GROUP and VALUE contain one or more alphanumeric characters.
     */
    public static final String VALIDATION_REGEX = "^[A-Za-z0-9]+$|^[A-Za-z0-9]+\\.[A-Za-z0-9]+$";
    private static final String TAG_GROUP_IDENTIFIER = ".";

    public final String tagFormat; // original string
    private final TagGroup group; // null if simple tag
    private final String value; // either entire tag or value after "."

    /**
     * Constructs a {@code Tag}.
     * Parses the string to set group and value if grouped.
     *
     * @param tagFormat A valid tag format, which must conform to either GROUP.VALUE or ALPHANUMERIC format.
     */
    public Tag(String tagFormat) {
        requireNonNull(tagFormat);
        checkArgument(isValidTagFormat(tagFormat), MESSAGE_CONSTRAINTS);
        this.tagFormat = tagFormat;

        if (tagFormat.contains(TAG_GROUP_IDENTIFIER)) {
            // Parse group and value from "GROUP.VALUE"
            // No check yet for the existence of TagGroup instances; will be added when registry logic is implemented
            String[] parts = tagFormat.split("\\.", 2);
            this.group = new TagGroup(parts[0]);
            this.value = parts[1];
        } else {
            // Simple Tag: no group, entire value is tagName
            this.group = null;
            this.value = tagFormat;
        }
    }

    /**
     * Returns true if a given string is a valid tag name.
     * The valid format is "GROUP.VALUE" where GROUP and VALUE are alphanumeric.
     * @param test String to test.
     * @return true if valid format, false otherwise.
     */
    public static boolean isValidTagFormat(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tag)) {
            return false;
        }

        Tag otherTag = (Tag) other;
        return tagFormat.equals(otherTag.tagFormat);
    }

    /**
     * Returns the tag's original string, i.e., what was passed into the constructor.
     */
    public String getTagFormat() {
        return tagFormat;
    }

    /**
     * Returns the TagGroup if any, or null if this is a legacy tag.
     */
    public TagGroup getGroup() {
        return group;
    }

    /**
     * Returns the value part of the tag.
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns true if the Tag has a group.
     */
    public boolean hasGroup() {
        return group != null;
    }

    @Override
    public int hashCode() {
        return tagFormat.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagFormat + ']';
    }


}
