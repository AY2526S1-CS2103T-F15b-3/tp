package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Status;
import seedu.address.model.tag.TagGroup;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_sameNameDifferentPhoneAndEmail_success() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person existingPerson = new PersonBuilder()
                .withName("John Doe")
                .withPhone("12345678")
                .withEmail("john@example.com")
                .build();
        modelStub.addPerson(existingPerson);

        // Person with same name but different phone and email should be allowed
        Person personWithSameName = new PersonBuilder()
                .withName("John Doe")
                .withPhone("87654321")
                .withEmail("johndoe@example.com")
                .build();
        AddCommand addCommand = new AddCommand(personWithSameName);

        CommandResult commandResult = addCommand.execute(modelStub);
        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(personWithSameName)),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_differentNameDuplicatePhone_throwsCommandException() {
        Person validPerson = new PersonBuilder().withPhone("12345678").build();
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        Person personWithSamePhone = new PersonBuilder()
                .withName("Different Name")
                .withPhone("12345678")
                .build();
        AddCommand addCommand = new AddCommand(personWithSamePhone);

        assertThrows(CommandException.class,
                PersonValidator.MESSAGE_DUPLICATE_PHONE, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_differentNameDuplicateEmail_throwsCommandException() {
        Person validPerson = new PersonBuilder()
                .withEmail("test@example.com")
                .withPhone("12345678")
                .build();
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        Person personWithSameEmail = new PersonBuilder()
                .withName("Different Name")
                .withPhone("87654321")
                .withEmail("test@example.com")
                .build();
        AddCommand addCommand = new AddCommand(personWithSameEmail);

        assertThrows(CommandException.class,
                PersonValidator.MESSAGE_DUPLICATE_EMAIL, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_sameNameAndDuplicatePhone_throwsCommandException() {
        Person validPerson = new PersonBuilder()
                .withName("John Doe")
                .withPhone("12345678")
                .withEmail("john@example.com")
                .build();
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        // Person with same name AND same phone should fail with duplicate phone error
        Person personWithSameNameAndPhone = new PersonBuilder()
                .withName("John Doe")
                .withPhone("12345678")
                .withEmail("different@example.com")
                .build();
        AddCommand addCommand = new AddCommand(personWithSameNameAndPhone);

        assertThrows(CommandException.class,
                PersonValidator.MESSAGE_DUPLICATE_PHONE, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_sameNameAndDuplicateEmail_throwsCommandException() {
        Person validPerson = new PersonBuilder()
                .withName("John Doe")
                .withPhone("12345678")
                .withEmail("john@example.com")
                .build();
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        // Person with same name AND same email should fail with duplicate email error
        Person personWithSameNameAndEmail = new PersonBuilder()
                .withName("John Doe")
                .withPhone("87654321")
                .withEmail("john@example.com")
                .build();
        AddCommand addCommand = new AddCommand(personWithSameNameAndEmail);

        assertThrows(CommandException.class,
                PersonValidator.MESSAGE_DUPLICATE_EMAIL, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_personWithUnknownTagGroup_throwsCommandException() {
        Set<TagGroup> allowedGroups = Collections.singleton(new TagGroup("group1"));
        Model model = new ModelStubWithAllowedGroupsAndPersons(allowedGroups);
        Person invalidPerson = new PersonBuilder().withTags("unknowngroup.value").build();

        assertThrows(CommandException.class, () -> new AddCommand(invalidPerson).execute(model));
    }

    @Test
    public void execute_personWithKnownTagGroup_addSuccessful() throws Exception {
        Set<TagGroup> allowedGroups = Collections.singleton(new TagGroup("friends"));
        Model model = new ModelStubWithAllowedGroupsAndPersons(allowedGroups);
        Person validPerson = new PersonBuilder().withTags("friends.value").build();

        CommandResult result = new AddCommand(validPerson).execute(model);
        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_personWithMixedTagGroups_throwsCommandException() {
        Set<TagGroup> allowedGroups = Collections.singleton(new TagGroup("friends"));
        Model model = new ModelStubWithAllowedGroupsAndPersons(allowedGroups);
        // "friends.value" is allowed, "work.value" group is not
        Person invalidPerson = new PersonBuilder().withTags("friends.value", "work.value").build();

        assertThrows(CommandException.class, () -> new AddCommand(invalidPerson).execute(model));
    }

    @Test
    public void execute_personWithoutRolesAndTags_successMessageDoesNotShowRolesAndTags() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person personWithoutRolesAndTags = new PersonBuilder()
                .withRoles()
                .withTags()
                .build();

        CommandResult commandResult = new AddCommand(personWithoutRolesAndTags).execute(modelStub);
        String message = commandResult.getFeedbackToUser();

        assertFalse(message.contains("Roles:"), "Message should not contain 'Roles:' when no roles are present");
        assertFalse(message.contains("Tags:"), "Message should not contain 'Tags:' when no tags are present");
    }

    @Test
    public void execute_personWithRolesOnly_successMessageShowsRolesButNotTags() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person personWithRolesOnly = new PersonBuilder()
                .withRoles("Buyer")
                .withTags()
                .build();

        CommandResult commandResult = new AddCommand(personWithRolesOnly).execute(modelStub);
        String message = commandResult.getFeedbackToUser();

        assertTrue(message.contains("Roles:"), "Message should contain 'Roles:' when roles are present");
        assertFalse(message.contains("Tags:"), "Message should not contain 'Tags:' when no tags are present");
    }

    @Test
    public void execute_personWithTagsOnly_successMessageShowsTagsButNotRoles() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person personWithTagsOnly = new PersonBuilder()
                .withRoles()
                .withTags("urgent")
                .build();

        CommandResult commandResult = new AddCommand(personWithTagsOnly).execute(modelStub);
        String message = commandResult.getFeedbackToUser();

        assertFalse(message.contains("Roles:"), "Message should not contain 'Roles:' when no roles are present");
        assertTrue(message.contains("Tags:"), "Message should contain 'Tags:' when tags are present");
    }

    @Test
    public void execute_personWithRolesAndTags_successMessageShowsBoth() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person personWithBoth = new PersonBuilder()
                .withRoles("Seller")
                .withTags("premium")
                .build();

        CommandResult commandResult = new AddCommand(personWithBoth).execute(modelStub);
        String message = commandResult.getFeedbackToUser();

        assertTrue(message.contains("Roles:"), "Message should contain 'Roles:' when roles are present");
        assertTrue(message.contains("Tags:"), "Message should contain 'Tags:' when tags are present");
    }

    @Test
    public void execute_personWithoutStatus_successMessageDoesNotShowStatus() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person personWithoutStatus = new PersonBuilder()
                .withoutStatus()
                .build();

        CommandResult commandResult = new AddCommand(personWithoutStatus).execute(modelStub);
        String message = commandResult.getFeedbackToUser();

        assertFalse(message.contains("Status:"), "Message should not contain 'Status:' when no status is present");
    }

    @Test
    public void execute_personWithStatus_successMessageShowsStatus() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person personWithStatus = new PersonBuilder()
                .withStatus(Status.PENDING)
                .build();

        CommandResult commandResult = new AddCommand(personWithStatus).execute(modelStub);
        String message = commandResult.getFeedbackToUser();

        assertTrue(message.contains("Status:"), "Message should contain 'Status:' when status is present");
        assertTrue(message.contains("PENDING"), "Message should contain the status value");
    }


    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSamePhoneNumber(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSameEmail(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Set<TagGroup> getTagGroups() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTagGroup(TagGroup group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTagGroup(TagGroup group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeTagGroup(TagGroup group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isTagGroupInUse(TagGroup tg) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that allows Tag Groups.
     */
    private class ModelStubWithAllowedGroupsAndPersons extends ModelStub {
        private final Set<TagGroup> tagGroups;
        private final List<Person> persons = new ArrayList<>();

        ModelStubWithAllowedGroupsAndPersons(Set<TagGroup> allowedGroups) {
            this.tagGroups = allowedGroups;
        }

        @Override
        public Set<TagGroup> getTagGroups() {
            return Collections.unmodifiableSet(tagGroups);
        }

        @Override
        public boolean hasPerson(Person person) {
            return hasSamePhoneNumber(person) || hasSameEmail(person);
        }

        @Override
        public boolean hasSamePhoneNumber(Person person) {
            return persons.stream().anyMatch(person::isSamePhone);
        }

        @Override
        public boolean hasSameEmail(Person person) {
            return persons.stream().anyMatch(person::isSameEmail);
        }

        @Override
        public void addPerson(Person person) {
            persons.add(person);
        }
    }


    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return hasSamePhoneNumber(person) || hasSameEmail(person);
        }

        @Override
        public boolean hasSamePhoneNumber(Person person) {
            requireNonNull(person);
            return this.person.isSamePhone(person);
        }

        @Override
        public boolean hasSameEmail(Person person) {
            requireNonNull(person);
            return this.person.isSameEmail(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return hasSamePhoneNumber(person) || hasSameEmail(person);
        }

        @Override
        public boolean hasSamePhoneNumber(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePhone);
        }

        @Override
        public boolean hasSameEmail(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSameEmail);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
