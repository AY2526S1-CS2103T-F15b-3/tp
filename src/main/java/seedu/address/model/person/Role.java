package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's role in the address book.
 * Guarantees: immutable;
 */
public class Role {
    public static final String MESSAGE_CONSTRAINTS =
            "Roles should only contain alphanumeric characters.\n"
            + "Space, hyphens and underscores are allowed except as the first character.\n"
            + "No blank inputs allowed when adding a role. Use 'r/' alone to clear all roles when editing.";
    /*
     * The first and last character of the role must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * The first character can only be alphanumeric,
     * to guard against pasted inputs from external sources (e.g. bullet points)
     */
    public static final String VALIDATION_REGEX = "^[A-Za-z0-9][A-Za-z0-9 _-]*[A-Za-z0-9_-]$|^[A-Za-z0-9]$";

    public final String roleName;

    /**
     * Constructs a {@code Role}.
     *
     * @param role A valid role, already trimmed at the ends for whitespace.
     */
    public Role(String role) {
        requireNonNull(role);
        checkArgument(isValidRoleName(role), MESSAGE_CONSTRAINTS);
        roleName = role;
    }

    /**
     * Returns true if a given string is a valid role.
     */
    public static boolean isValidRoleName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if another role has the same roleName as this role, ignoring casing.
     */
    public boolean isSameRoleIgnoreCase(Role other) {
        requireNonNull(other);
        return this.roleName.equalsIgnoreCase(other.roleName);
    }
    @Override
    public String toString() {
        return '[' + roleName + ']';
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Role)) {
            return false;
        }

        Role otherRole = (Role) other;
        return roleName.equals(otherRole.roleName);
    }

    @Override
    public int hashCode() {
        return roleName.hashCode();
    }

}
