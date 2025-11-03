package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.TagGroup;

/**
 * Creates a new Tag Group or lists all Tag Groups.
 */
public class TagGroupCommand extends Command {

    public static final String COMMAND_WORD = "tg";

    public static final String MESSAGE_USAGE = "To list "
            + "Tag Groups, please follow the given format: "
            + COMMAND_WORD + "\n"
            + "To create Tag Groups, please follow the given format: "
            + COMMAND_WORD + " TAG_GROUP\n"
            + TagGroup.MESSAGE_CONSTRAINTS + "\n"
            + "Example: " + COMMAND_WORD + " location";

    public static final String MESSAGE_CREATE_SUCCESS = "New Tag Group has been created: %1$s";
    public static final String MESSAGE_LIST_SUCCESS = "Existing Tag Groups: %1$s";
    public static final String MESSAGE_DUPLICATE = "This Tag Group already exists.";
    public static final String MESSAGE_EMPTY_LIST = "No Tag Groups have been created yet.";

    private final TagGroup toCreate;
    private final boolean isListCommand;

    /**
     * Constructs a TagGroupCommand for listing or creating TagGroups.
     * If toCreate is null, this is a list command.
     */
    public TagGroupCommand(TagGroup toCreate) {
        this.toCreate = toCreate;
        this.isListCommand = (toCreate == null);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // If listing TagGroups
        if (isListCommand) {
            Set<TagGroup> tagGroups = model.getTagGroups();
            if (tagGroups.isEmpty()) {
                return new CommandResult(MESSAGE_EMPTY_LIST);
            }

            String groups = tagGroups.stream()
                    .map(TagGroup::getName)
                    .sorted(String::compareToIgnoreCase)
                    .collect(Collectors.joining(", "));

            return new CommandResult(String.format(MESSAGE_LIST_SUCCESS, groups));
        }

        // If creating new TagGroup
        if (model.hasTagGroup(toCreate)) {
            throw new CommandException(MESSAGE_DUPLICATE);
        }

        model.addTagGroup(toCreate);
        return new CommandResult(String.format(MESSAGE_CREATE_SUCCESS, toCreate));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof TagGroupCommand)) {
            return false;
        }
        TagGroupCommand otherCommand = (TagGroupCommand) other;
        return (isListCommand == otherCommand.isListCommand)
                && ((toCreate == null && otherCommand.toCreate == null)
                || (toCreate != null && toCreate.equals(otherCommand.toCreate)));
    }
}
