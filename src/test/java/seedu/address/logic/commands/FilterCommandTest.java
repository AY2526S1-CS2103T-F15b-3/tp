package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ISABEL;
import static seedu.address.testutil.TypicalPersons.JACK;
import static seedu.address.testutil.TypicalPersons.KAREN;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Role;
import seedu.address.model.person.RoleStatusPredicate;
import seedu.address.model.person.Status;

public class FilterCommandTest {
    private Model model = new ModelManager(getTestAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTestAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        Set<Role> roles1 = Set.of(new Role("buyer"));
        Set<Status> statuses1 = Set.of(Status.COMPLETED);

        Set<Role> roles2 = Set.of(new Role("seller"));
        Set<Status> statuses2 = Set.of(Status.COMPLETED);

        RoleStatusPredicate firstPredicate =
                new RoleStatusPredicate(roles1, statuses1);
        RoleStatusPredicate secondPredicate =
                new RoleStatusPredicate(roles2, statuses2);

        FilterCommand firstFilterCommand = new FilterCommand(firstPredicate);
        FilterCommand secondFilterCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(firstFilterCommand.equals(firstFilterCommand));

        // same values -> returns true
        FilterCommand firstFilterCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(firstFilterCommand.equals(firstFilterCommandCopy));

        // different types -> returns false
        assertFalse(firstFilterCommand.equals(1));

        // null -> returns false
        assertFalse(firstFilterCommand.equals(null));

        // different person -> returns false
        assertFalse(firstFilterCommand.equals(secondFilterCommand));
    }

    @Test
    public void execute_noRoleAndStatus_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        RoleStatusPredicate predicate = new RoleStatusPredicate(Collections.emptySet(), Collections.emptySet());
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_hasRoleAndStatus_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        Set<Role> roles = Set.of(new Role("seller"));
        Set<Status> statuses = Set.of(Status.COMPLETED);
        RoleStatusPredicate predicate = new RoleStatusPredicate(roles, statuses);
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ISABEL, JACK, KAREN), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        Set<Role> roles = Set.of(new Role("seller"));
        Set<Status> statuses = Set.of(Status.COMPLETED);
        RoleStatusPredicate predicate = new RoleStatusPredicate(roles, statuses);
        FilterCommand filterCommand = new FilterCommand(predicate);
        String expected = FilterCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, filterCommand.toString());
    }

    public AddressBook getTestAddressBook() {
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(ISABEL);
        addressBook.addPerson(JACK);
        addressBook.addPerson(KAREN);
        return addressBook;
    }

}
