package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_CUSTOMER;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_COMPLETED;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_PENDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_CUSTOMER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.Role;
import seedu.address.model.person.RoleStatusPredicate;
import seedu.address.model.person.Status;

public class FilterCommandParserTest {

    private final FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_validRole_success() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(VALID_ROLE_CUSTOMER));
        Set<Status> statuses = Collections.emptySet();

        RoleStatusPredicate predicate = new RoleStatusPredicate(roles, statuses);
        FilterCommand expectedCommand = new FilterCommand(predicate);

        assertParseSuccess(parser, ROLE_DESC_CUSTOMER, expectedCommand);
    }

    @Test
    public void parse_validStatus_success() {
        Set<Role> roles = Collections.emptySet();
        Set<Status> statuses = new HashSet<>();
        statuses.add(Status.PENDING);

        RoleStatusPredicate predicate = new RoleStatusPredicate(roles, statuses);
        FilterCommand expectedCommand = new FilterCommand(predicate);

        assertParseSuccess(parser, STATUS_DESC_PENDING, expectedCommand);
    }

    @Test
    public void parse_validRoleAndStatus_success() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(VALID_ROLE_CUSTOMER));
        Set<Status> statuses = new HashSet<>();
        statuses.add(Status.COMPLETED);

        RoleStatusPredicate predicate = new RoleStatusPredicate(roles, statuses);
        FilterCommand expectedCommand = new FilterCommand(predicate);

        assertParseSuccess(parser, ROLE_DESC_CUSTOMER + STATUS_DESC_COMPLETED, expectedCommand);
    }

    @Test
    public void parse_invalidArgs_failure() {
        // completely empty input
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));

        // invalid prefix
        assertParseFailure(parser, " x/random",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }
}
