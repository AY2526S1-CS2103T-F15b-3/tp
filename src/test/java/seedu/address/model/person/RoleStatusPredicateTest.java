package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

class RoleStatusPredicateTest {

    @Test
    void test_matchesRoleOnly_returnsTrue() {
        Person person = new PersonBuilder().withRoles("buyer").build();
        Set<Role> roles = Set.of(new Role("buyer"));
        Set<Status> statuses = Collections.emptySet();
        RoleStatusPredicate predicate = new RoleStatusPredicate(roles, statuses);

        assertTrue(predicate.test(person));
    }

    @Test
    void test_matchesStatusOnly_returnsTrue() {
        Person person = new PersonBuilder().withStatus(Status.COMPLETED).build();
        Set<Role> roles = Collections.emptySet();
        Set<Status> statuses = Set.of(Status.COMPLETED);
        RoleStatusPredicate predicate = new RoleStatusPredicate(roles, statuses);

        assertTrue(predicate.test(person));
    }

    @Test
    void test_matchesRoleAndStatus_returnsTrue() {
        Person person = new PersonBuilder().withRoles("buyer").withStatus(Status.COMPLETED).build();
        Set<Role> roles = Set.of(new Role("buyer"));
        Set<Status> statuses = Set.of(Status.COMPLETED);
        RoleStatusPredicate predicate = new RoleStatusPredicate(roles, statuses);

        assertTrue(predicate.test(person));
    }

    @Test
    void test_noMatches_returnsFalse() {
        Person person = new PersonBuilder().withRoles("buyer").withStatus(Status.PENDING).build();
        Set<Role> roles = Set.of(new Role("seller"));
        Set<Status> statuses = Set.of(Status.COMPLETED);
        RoleStatusPredicate predicate = new RoleStatusPredicate(roles, statuses);

        assertFalse(predicate.test(person));
    }

    @Test
    void test_equals() {
        Set<Role> roles1 = Set.of(new Role("buyer"));
        Set<Status> statuses1 = Set.of(Status.COMPLETED);

        Set<Role> roles2 = Set.of(new Role("seller"));
        Set<Status> statuses2 = Set.of(Status.PENDING);

        RoleStatusPredicate predicate1 = new RoleStatusPredicate(roles1, statuses1);
        RoleStatusPredicate predicate2 = new RoleStatusPredicate(roles1, statuses1);
        RoleStatusPredicate predicate3 = new RoleStatusPredicate(roles2, statuses2);

        // same object
        assertTrue(predicate1.equals(predicate1));

        // same values
        assertTrue(predicate1.equals(predicate2));

        // different values
        assertFalse(predicate1.equals(predicate3));

        // null
        assertFalse(predicate1.equals(null));

        // different type
        assertFalse(predicate1.equals("not a predicate"));
    }
}
