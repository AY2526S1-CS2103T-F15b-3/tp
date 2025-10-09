package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's role in the address book.
 * Guarantees: immutable;
 */
public class Role {
    public static final String MESSAGE_CONSTRAINTS =
            "Roles should only contain alphanumeric characters, with spaces and hyphens in between words and no blanks";
    /*
     * The first character of the address must not be a whitespace or hyphen,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[\\p{Alnum}][\\p{Alnum} \\\\-]*$";

    public final String roleName;

    /**
     * Constructs a {@code Role}.
     *
     * @param role A valid role.
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

    @Override
    public String toString() {
        return roleName;
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
