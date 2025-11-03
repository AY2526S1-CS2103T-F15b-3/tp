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
            + "where GROUP is alphanumeric and VALUE can contain alphanumeric characters, "
            + "dots, hyphens, and underscores (no spaces). VALUE has to start with alphanumeric.\n"
            + "OR it could be a simple Tag with any alphanumeric text.\n"
            + "Tags are single word.";

    /**
     *     Validation for standalone tags (alphanumeric only, no spaces)
     */
    public static final String STANDALONE_VALIDATION_REGEX = "^[a-zA-Z0-9]+$";

    /**
     * Validation for GROUP.VALUE format:
     *     - GROUP: alphanumeric only (no spaces, no symbols)
     *     - VALUE: alphanumeric + dots, hyphens, underscores (but no spaces)
     *     - VALUE must start with alphanumeric, and can contain any combination after
     */
    public static final String GROUPED_VALIDATION_REGEX = "^([a-zA-Z0-9]+)\\.([a-zA-Z0-9][a-zA-Z0-9.\\-_]*)$";

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

        if (tagFormat.contains(TAG_GROUP_IDENTIFIER)) {
            // Parse group and value from "GROUP.VALUE"
            String[] parts = tagFormat.split("\\.", 2);
            this.group = new TagGroup(parts[0].toUpperCase());
            this.value = parts[1];
            this.tagFormat = this.group.getName() + "." + this.value;;
        } else {
            // Simple Tag: no group, entire value is tagName
            this.group = null;
            this.value = tagFormat;
            this.tagFormat = tagFormat;
        }
    }

    /**
     * Returns true if a given string is a valid tag name.
     * The valid format is "GROUP.VALUE" where GROUP and VALUE are alphanumeric.
     * @param test String to test.
     * @return true if valid format, false otherwise.
     */
    public static boolean isValidTagFormat(String test) {
        // Check if it's a valid standalone tag or a valid grouped tag
        return test.matches(STANDALONE_VALIDATION_REGEX) || test.matches(GROUPED_VALIDATION_REGEX);
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
