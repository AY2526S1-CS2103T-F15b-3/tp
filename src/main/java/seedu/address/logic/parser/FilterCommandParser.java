package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.MatchesRoleStatusTagGroupPredicate;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FilterCommand object.
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ROLE, PREFIX_STATUS, PREFIX_TAG);

        List<String> roleStrings = argMultimap.getAllValues(PREFIX_ROLE);
        List<String> statusStrings = argMultimap.getAllValues(PREFIX_STATUS);
        List<String> tagStrings = argMultimap.getAllValues(PREFIX_TAG);

        if (roleStrings.isEmpty() && statusStrings.isEmpty() && tagStrings.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        Set<Role> roles = ParserUtil.parseRoles(roleStrings);
        Set<Status> statuses = ParserUtil.parseStatuses(statusStrings);
        Set<Tag> tags = ParserUtil.parseTags(tagStrings);

        return new FilterCommand(new MatchesRoleStatusTagGroupPredicate(roles, statuses, tags));
    }
}
