package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.TagGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.TagGroup;

/**
 * Parses input arguments and creates a new TagGroupCommand object.
 */
public class TagGroupCommandParser implements Parser<TagGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagGroupCommand
     * and returns a TagGroupCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to expected format
     */
    public TagGroupCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        // Empty input means list all TagGroups
        if (trimmedArgs.isEmpty()) {
            return new TagGroupCommand(null);
        }

        // Non-empty means attempt to create a TagGroup
        if (!TagGroup.isValidTagGroupName(trimmedArgs)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagGroupCommand.MESSAGE_USAGE));
        }

        TagGroup toCreate = new TagGroup(trimmedArgs.toUpperCase());
        return new TagGroupCommand(toCreate);
    }
}
