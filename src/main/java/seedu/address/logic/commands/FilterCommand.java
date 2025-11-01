package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.MatchesRoleStatusTagGroupPredicate;

/**
 * Filters and lists all persons in the address book whose role, status, and/or specific Tags
 * match the specified keywords.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = "To filter "
            + "contacts by role, status, and/or specific tags, please follow the given format: "
            + COMMAND_WORD + " [r/ROLE]... [s/STATUS]... [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " r/buyer s/completed t/budget.500k t/urgent.high";

    private final MatchesRoleStatusTagGroupPredicate predicate;

    /**
     * Creates a new FilterCommand with the given predicate.
     *
     * @param predicate the predicate containing the role, status, and/or Tag Group filters
     */
    public FilterCommand(MatchesRoleStatusTagGroupPredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Executes the filter command and updates the filtered person list in the model.
     *
     * @param model the model which the command should operate on
     * @return a CommandResult indicating the number of persons listed
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FilterCommand
                && predicate.equals(((FilterCommand) other).predicate));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("predicate", predicate).toString();
    }
}
