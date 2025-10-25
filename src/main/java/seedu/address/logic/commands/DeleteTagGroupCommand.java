package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.TagGroup;

/**
 * Deletes an existing TagGroup from the address book.
 */
public class DeleteTagGroupCommand extends Command {

    public static final String COMMAND_WORD = "dtg";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the specified TagGroup from the address book.\n"
            + "Parameters: TAG_GROUP_NAME\n"
            + "Example: " + COMMAND_WORD + " location";

    public static final String MESSAGE_DELETE_SUCCESS = "Deleted TagGroup: %1$s";
    public static final String MESSAGE_TAGGROUP_NOT_FOUND = "TagGroup does not exist: %1$s";

    private final TagGroup toDelete;

    /**
     * Creates a DeleteTagGroupCommand to delete the specified {@code TagGroup}.
     */
    public DeleteTagGroupCommand(TagGroup toDelete) {
        requireNonNull(toDelete);
        this.toDelete = toDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTagGroup(toDelete)) {
            throw new CommandException(String.format(MESSAGE_TAGGROUP_NOT_FOUND, toDelete));
        }

        model.removeTagGroup(toDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_SUCCESS, toDelete));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteTagGroupCommand)) {
            return false;
        }

        DeleteTagGroupCommand otherCommand = (DeleteTagGroupCommand) other;
        return toDelete.equals(otherCommand.toDelete);
    }

    @Override
    public int hashCode() {
        return toDelete.hashCode();
    }
}
