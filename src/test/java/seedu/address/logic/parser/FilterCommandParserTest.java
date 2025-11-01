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
import seedu.address.model.person.MatchesRoleStatusTagGroupPredicate;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.tag.Tag;

public class FilterCommandParserTest {

    private final FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_validRole_success() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(VALID_ROLE_CUSTOMER));
        Set<Status> statuses = Collections.emptySet();
        Set<Tag> tags = Collections.emptySet();

        MatchesRoleStatusTagGroupPredicate predicate =
                new MatchesRoleStatusTagGroupPredicate(roles, statuses, tags);
        FilterCommand expectedCommand = new FilterCommand(predicate);

        assertParseSuccess(parser, ROLE_DESC_CUSTOMER, expectedCommand);
    }

    @Test
    public void parse_validStatus_success() {
        Set<Role> roles = Collections.emptySet();
        Set<Status> statuses = new HashSet<>();
        statuses.add(Status.PENDING);
        Set<Tag> tags = Collections.emptySet();

        MatchesRoleStatusTagGroupPredicate predicate =
                new MatchesRoleStatusTagGroupPredicate(roles, statuses, tags);
        FilterCommand expectedCommand = new FilterCommand(predicate);

        assertParseSuccess(parser, STATUS_DESC_PENDING, expectedCommand);
    }

    @Test
    public void parse_validRoleAndStatus_success() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(VALID_ROLE_CUSTOMER));
        Set<Status> statuses = new HashSet<>();
        statuses.add(Status.COMPLETED);
        Set<Tag> tags = Collections.emptySet();

        MatchesRoleStatusTagGroupPredicate predicate =
                new MatchesRoleStatusTagGroupPredicate(roles, statuses, tags);
        FilterCommand expectedCommand = new FilterCommand(predicate);

        assertParseSuccess(parser, ROLE_DESC_CUSTOMER + STATUS_DESC_COMPLETED, expectedCommand);
    }

    @Test
    public void parse_validTag_success() {
        Set<Role> roles = Collections.emptySet();
        Set<Status> statuses = Collections.emptySet();
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("location.downtown"));

        MatchesRoleStatusTagGroupPredicate predicate =
                new MatchesRoleStatusTagGroupPredicate(roles, statuses, tags);
        FilterCommand expectedCommand = new FilterCommand(predicate);

        assertParseSuccess(parser, " t/location.downtown", expectedCommand);
    }

    @Test
    public void parse_multipleTags_success() {
        Set<Role> roles = Collections.emptySet();
        Set<Status> statuses = Collections.emptySet();
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("location.downtown"));
        tags.add(new Tag("budget.500k"));

        MatchesRoleStatusTagGroupPredicate predicate =
                new MatchesRoleStatusTagGroupPredicate(roles, statuses, tags);
        FilterCommand expectedCommand = new FilterCommand(predicate);

        assertParseSuccess(parser, " t/location.downtown t/budget.500k", expectedCommand);
    }

    @Test
    public void parse_allFiltersPresent_success() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(VALID_ROLE_CUSTOMER));
        Set<Status> statuses = new HashSet<>();
        statuses.add(Status.COMPLETED);
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("urgent.high"));

        MatchesRoleStatusTagGroupPredicate predicate =
                new MatchesRoleStatusTagGroupPredicate(roles, statuses, tags);
        FilterCommand expectedCommand = new FilterCommand(predicate);

        assertParseSuccess(
                parser,
                ROLE_DESC_CUSTOMER + STATUS_DESC_COMPLETED + " t/urgent.high",
                expectedCommand);
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
