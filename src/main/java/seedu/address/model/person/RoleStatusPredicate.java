package seedu.address.model.person;

import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s role and/or status matches any of the given keywords.
 */
public class RoleStatusPredicate implements Predicate<Person> {

    private final Set<Role> roles;
    private final Set<Status> statuses;

    /**
     * Creates a predicate with the given roles and statuses.
     *
     * @param roles   the list of role keywords to match
     * @param statuses the list of status keywords to match
     */
    public RoleStatusPredicate(Set<Role> roles, Set<Status> statuses) {
        this.roles = roles;
        this.statuses = statuses;
    }

    /**
     * Returns true if the given {@code Person} matches the roles or statuses.
     */
    @Override
    public boolean test(Person person) {
        Set<Role> personRoles = person.getRoles();
        Status personStatus = person.getStatus();

        boolean hasMatchingRole = this.roles.stream().anyMatch(personRoles::contains);
        boolean hasMatchingStatus = this.statuses.stream().anyMatch(status -> status.equals(personStatus));
        return hasMatchingRole || hasMatchingStatus;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RoleStatusPredicate)) {
            return false;
        }
        RoleStatusPredicate otherPredicate = (RoleStatusPredicate) other;
        return Objects.equals(roles, otherPredicate.roles)
                && Objects.equals(statuses, otherPredicate.statuses);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("roles", roles)
                .add("statuses", statuses).toString();
    }

}
