package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeleteTagGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.TagGroup;

/**
 * Parses input arguments and creates a new DeleteTagGroupCommand object.
 */
public class DeleteTagGroupCommandParser implements Parser<DeleteTagGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTagGroupCommand
     * and returns a DeleteTagGroupCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteTagGroupCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagGroupCommand.MESSAGE_USAGE));
        }

        if (!TagGroup.isValidTagGroupName(trimmedArgs)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagGroup.MESSAGE_CONSTRAINTS));
        }

        TagGroup toDelete = new TagGroup(trimmedArgs);
        return new DeleteTagGroupCommand(toDelete);
    }
}
