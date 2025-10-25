package seedu.address.model.person;

import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.TagGroup;

/**
 * Tests that a {@code Person}'s role, status, and/or tag groups match any of the given keywords.
 */
public class RoleStatusTagGroupPredicate implements Predicate<Person> {

    private final Set<Role> roles;
    private final Set<Status> statuses;
    private final Set<TagGroup> tagGroups;

    /**
     * Creates a predicate with the given roles, statuses, and tag groups.
     *
     * @param roles      the set of roles to match
     * @param statuses   the set of statuses to match
     * @param tagGroups  the set of tag groups to match
     */
    public RoleStatusTagGroupPredicate(Set<Role> roles, Set<Status> statuses, Set<TagGroup> tagGroups) {
        this.roles = roles;
        this.statuses = statuses;
        this.tagGroups = tagGroups;
    }

    /**
     * Returns true if the given {@code Person} matches the roles, statuses, or tag groups.
     */
    @Override
    public boolean test(Person person) {
        // If no filter criteria are provided, no person should match.
        if (roles.isEmpty() && statuses.isEmpty() && tagGroups.isEmpty()) {
            return false;
        }

        // A person matches if they match any of the specified roles OR statuses OR tag groups.
        boolean roleMatch = !roles.isEmpty() && person.getRoles().stream()
                .anyMatch(roles::contains);
        boolean statusMatch = !statuses.isEmpty() && person.getStatus()
                .map(statuses::contains).orElse(false);
        boolean tagGroupMatch = !tagGroups.isEmpty() && person.getTags().stream()
                .anyMatch(tag -> tag.getGroup() != null && tagGroups.contains(tag.getGroup()));

        return roleMatch || statusMatch || tagGroupMatch;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof RoleStatusTagGroupPredicate)) {
            return false;
        }
        RoleStatusTagGroupPredicate otherPredicate = (RoleStatusTagGroupPredicate) other;
        return Objects.equals(roles, otherPredicate.roles)
                && Objects.equals(statuses, otherPredicate.statuses)
                && Objects.equals(tagGroups, otherPredicate.tagGroups);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("roles", roles)
                .add("statuses", statuses)
                .add("tagGroups", tagGroups)
                .toString();
    }
}
