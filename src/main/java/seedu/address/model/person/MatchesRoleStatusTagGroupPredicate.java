package seedu.address.model.person;

import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s role, status, and/or specific Tags match any of the parameters.
 */
public class MatchesRoleStatusTagGroupPredicate implements Predicate<Person> {

    private final Set<Role> roles;
    private final Set<Status> statuses;
    private final Set<Tag> tags;

    /**
     * Creates a predicate with the given roles, statuses, and specific Tags.
     *
     * @param roles      the set of roles to match
     * @param statuses   the set of statuses to match
     * @param tags       the set of specific Tags to match
     */
    public MatchesRoleStatusTagGroupPredicate(Set<Role> roles, Set<Status> statuses, Set<Tag> tags) {
        this.roles = roles;
        this.statuses = statuses;
        this.tags = tags;
    }

    /**
     * Returns true if the given {@code Person} matches the roles, statuses, or specific Tags.
     */
    @Override
    public boolean test(Person person) {
        // If no filter criteria are provided, no person should match.
        if (roles.isEmpty() && statuses.isEmpty() && tags.isEmpty()) {
            return false;
        }

        // A person matches if they match any of the specified roles OR statuses OR ALL of the specific Tags.
        boolean roleMatch = !roles.isEmpty() && person.getRoles().stream()
                .anyMatch(personRole ->
                        roles.stream().anyMatch(personRole::isSameRoleIgnoreCase));
        boolean statusMatch = !statuses.isEmpty() && person.getStatus()
                .map(statuses::contains).orElse(false);
        // Tag match: person must have ALL specified tags (AND logic)
        boolean tagMatch = !tags.isEmpty() && tags.stream()
                .allMatch(filterTag ->
                        person.getTags().stream()
                                .anyMatch(personTag ->
                                        personTag.getTagFormat().equalsIgnoreCase(filterTag.getTagFormat())));

        return roleMatch || statusMatch || tagMatch;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof MatchesRoleStatusTagGroupPredicate)) {
            return false;
        }
        MatchesRoleStatusTagGroupPredicate otherPredicate = (MatchesRoleStatusTagGroupPredicate) other;
        return Objects.equals(roles, otherPredicate.roles)
                && Objects.equals(statuses, otherPredicate.statuses)
                && Objects.equals(tags, otherPredicate.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("roles", roles)
                .add("statuses", statuses)
                .add("tags", tags)
                .toString();
    }
}
