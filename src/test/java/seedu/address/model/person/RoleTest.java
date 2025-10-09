package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null));
    }

    @Test
    public void constructor_invalidRole_throwsIllegalArgumentException() {
        String invalidRole = "";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRole));
    }

    @Test
    public void isValidRole() {
        // null roll
        assertThrows(NullPointerException.class, () -> Role.isValidRoleName(null));

        // invalid role
        assertFalse(Role.isValidRoleName("")); // empty string
        assertFalse(Role.isValidRoleName(" ")); // spaces only
        assertFalse(Role.isValidRoleName(" ")); // numbers only
        assertFalse(Role.isValidRoleName("-buyer")); // start with hyphen
        assertFalse(Role.isValidRoleName(" buyer")); // space before string
        assertFalse(Role.isValidRoleName("buyer&")); // contain invalid character

        // valid role
        assertTrue(Role.isValidRoleName("buyer"));
        assertTrue(Role.isValidRoleName("Buyer2"));
        assertTrue(Role.isValidRoleName("co-brokers"));
        assertTrue(Role.isValidRoleName("interior designers"));
    }

    @Test
    public void equals() {
        Role role = new Role("Valid Role");

        // same values -> returns true
        assertTrue(role.equals(new Role("Valid Role")));

        // same object -> returns true
        assertTrue(role.equals(role));

        // null -> returns false
        assertFalse(role.equals(null));

        // different types -> returns false
        assertFalse(role.equals(5.0f));

        // different values -> returns false
        assertFalse(role.equals(new Role("Other Valid Role")));
    }

}
