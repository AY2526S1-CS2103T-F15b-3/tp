package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
/**
 * Represents a Person's role in the address book.
 * Guarantees: immutable;
 */

public class Role {

    public final String value;

    /**
     * Constructs a {@code Role}.
     *
     * @param role A valid role.
     */
    public Role(String role) {
        requireNonNull(role);
        value = role;
    }

    @Override
    public String toString() {
        return value;
    }

}
