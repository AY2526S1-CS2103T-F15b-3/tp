package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Role;
import seedu.address.model.person.RoleStatusPredicate;
import seedu.address.model.person.Status;

/**
 * Parses input arguments and creates a new FilterCommand object.
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    @Override
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ROLE, PREFIX_STATUS);

        List<String> roleStrings = argMultimap.getAllValues(PREFIX_ROLE);
        List<String> statusStrings = argMultimap.getAllValues(PREFIX_STATUS);

        if (roleStrings.isEmpty() && statusStrings.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        Set<Role> roles = ParserUtil.parseRoles(roleStrings);
        Set<Status> statuses = ParserUtil.parseStatuses(statusStrings);

        return new FilterCommand(new RoleStatusPredicate(roles, statuses));
    }
}
