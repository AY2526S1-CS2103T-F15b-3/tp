package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_GROUP;

import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Role;
import seedu.address.model.person.RoleStatusTagGroupPredicate;
import seedu.address.model.person.Status;
import seedu.address.model.tag.TagGroup;

/**
 * Parses input arguments and creates a new FilterCommand object.
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    @Override
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ROLE, PREFIX_STATUS, PREFIX_TAG_GROUP);

        List<String> roleStrings = argMultimap.getAllValues(PREFIX_ROLE);
        List<String> statusStrings = argMultimap.getAllValues(PREFIX_STATUS);
        List<String> tagGroupStrings = argMultimap.getAllValues(PREFIX_TAG_GROUP);

        if (roleStrings.isEmpty() && statusStrings.isEmpty() && tagGroupStrings.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        Set<Role> roles = ParserUtil.parseRoles(roleStrings);
        Set<Status> statuses = ParserUtil.parseStatuses(statusStrings);
        Set<TagGroup> tagGroups = ParserUtil.parseTagGroups(tagGroupStrings);

        return new FilterCommand(new RoleStatusTagGroupPredicate(roles, statuses, tagGroups));
    }
}
