package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a group of tags in TrackerGuru.
 * Examples: propertyType, location, propertySize.
 * A TagGroup exists independently and can be managed by the user.
 * Tag Groups are referenced by tags of form "GROUP.VALUE", and allows for grouped tag management.
 */
public class TagGroup {

    public static final String MESSAGE_CONSTRAINTS =
            "TagGroups should be alphanumeric and contain at least one character.";

    public static final String VALIDATION_REGEX = "^[A-Za-z0-9]+$";

    private final String groupName;

    /**
     * Constructs a TagGroup with a valid name.
     *
     * @param groupName Alphanumeric group name, e.g., 'propertyType'.
     */
    public TagGroup(String groupName) {
        requireNonNull(groupName);
        checkArgument(isValidTagGroupName(groupName), MESSAGE_CONSTRAINTS);
        this.groupName = groupName.toUpperCase();
    }

    /**
     * Returns true if the given string is a valid TagGroup name.
     *
     * @param test Group name to validate.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidTagGroupName(String test) {
        return test != null && test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the group name.
     *
     * @return group name.
     */
    public String getName() {
        return groupName;
    }

    @Override
    public String toString() {
        return groupName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagGroup // instanceof handles nulls
                && groupName.equals(((TagGroup) other).groupName));
    }

    @Override
    public int hashCode() {
        return groupName.hashCode();
    }
}
