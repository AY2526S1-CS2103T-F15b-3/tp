package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.DeleteTagGroupCommand.MESSAGE_DELETE_SUCCESS;
import static seedu.address.logic.commands.DeleteTagGroupCommand.MESSAGE_TAGGROUP_NOT_FOUND;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.TagGroup;

/**
 * Unit tests for DeleteTagGroupCommand.
 */
public class DeleteTagGroupCommandTest {

    private ModelStub modelStub;

    @BeforeEach
    public void setUp() {
        modelStub = new ModelStub();
    }

    @Test
    public void execute_validTagGroup_deleteSuccessful() throws CommandException {
        TagGroup group = new TagGroup("location");
        modelStub.tagGroups.add(group);

        DeleteTagGroupCommand command = new DeleteTagGroupCommand(group);
        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(MESSAGE_DELETE_SUCCESS, group), result.getFeedbackToUser());
        assertFalse(modelStub.tagGroups.contains(group));
    }

    @Test
    public void execute_nonExistentTagGroup_throwsCommandException() {
        TagGroup group = new TagGroup("nonExistent");
        DeleteTagGroupCommand command = new DeleteTagGroupCommand(group);

        assertThrows(CommandException.class,
                String.format(MESSAGE_TAGGROUP_NOT_FOUND, group), () -> command.execute(modelStub));
    }

    @Test
    public void equals_sameTagGroup_returnsTrue() {
        DeleteTagGroupCommand commandA = new DeleteTagGroupCommand(new TagGroup("finance"));
        DeleteTagGroupCommand commandB = new DeleteTagGroupCommand(new TagGroup("finance"));
        assertTrue(commandA.equals(commandB));
    }

    @Test
    public void equals_differentTagGroup_returnsFalse() {
        DeleteTagGroupCommand commandA = new DeleteTagGroupCommand(new TagGroup("finance"));
        DeleteTagGroupCommand commandB = new DeleteTagGroupCommand(new TagGroup("location"));
        assertFalse(commandA.equals(commandB));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        DeleteTagGroupCommand command = new DeleteTagGroupCommand(new TagGroup("test"));
        assertTrue(command.equals(command));
    }

    @Test
    public void equals_null_returnsFalse() {
        DeleteTagGroupCommand command = new DeleteTagGroupCommand(new TagGroup("test"));
        assertFalse(command.equals(null));
    }

    @Test
    public void hashCode_sameTagGroup_returnsSameHashCode() {
        DeleteTagGroupCommand commandA = new DeleteTagGroupCommand(new TagGroup("finance"));
        DeleteTagGroupCommand commandB = new DeleteTagGroupCommand(new TagGroup("finance"));

        assertEquals(commandA.hashCode(), commandB.hashCode());
    }

    /**
     * A Model stub that only supports TagGroup operations for testing.
     */
    private static class ModelStub implements Model {
        final Set<TagGroup> tagGroups = new HashSet<>();

        @Override
        public boolean hasTagGroup(TagGroup group) {
            return tagGroups.contains(group);
        }

        @Override
        public void addTagGroup(TagGroup group) {
            tagGroups.add(group);
        }

        @Override
        public Set<TagGroup> getTagGroups() {
            return Collections.unmodifiableSet(tagGroups);
        }

        @Override
        public void removeTagGroup(TagGroup group) {
            tagGroups.remove(group);
        }

        // Unused Model methods
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
    }
}
