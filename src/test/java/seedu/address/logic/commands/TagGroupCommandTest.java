package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.TagGroupCommand.MESSAGE_CREATE_SUCCESS;
import static seedu.address.logic.commands.TagGroupCommand.MESSAGE_DUPLICATE;
import static seedu.address.logic.commands.TagGroupCommand.MESSAGE_EMPTY_LIST;
import static seedu.address.logic.commands.TagGroupCommand.MESSAGE_LIST_SUCCESS;
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
 * Unit tests for TagGroupCommand.
 */
public class TagGroupCommandTest {

    private ModelStub modelStub;

    @BeforeEach
    public void setUp() {
        modelStub = new ModelStub();
    }

    @Test
    public void execute_createTagGroup_success() throws CommandException {
        TagGroup group = new TagGroup("location");
        TagGroupCommand command = new TagGroupCommand(group);
        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(MESSAGE_CREATE_SUCCESS, group), result.getFeedbackToUser());
        assertTrue(modelStub.tagGroups.contains(group));
    }

    @Test
    public void execute_createDuplicateTagGroup_throwsCommandException() throws CommandException {
        TagGroup group = new TagGroup("property");
        modelStub.tagGroups.add(group);
        TagGroupCommand command = new TagGroupCommand(group);

        assertThrows(CommandException.class, MESSAGE_DUPLICATE, () -> command.execute(modelStub));
    }

    @Test
    public void execute_listNoTagGroups_returnsEmptyMessage() throws CommandException {
        TagGroupCommand listCommand = new TagGroupCommand(null);
        CommandResult result = listCommand.execute(modelStub);

        assertEquals(MESSAGE_EMPTY_LIST, result.getFeedbackToUser());
    }

    @Test
    public void execute_listExistingTagGroups_success() throws CommandException {
        TagGroup group = new TagGroup("property");
        modelStub.tagGroups.add(group);

        TagGroupCommand command = new TagGroupCommand(null);
        CommandResult result = command.execute(modelStub);

        String expectedOutput = String.format(MESSAGE_LIST_SUCCESS, "PROPERTY");
        assertEquals(expectedOutput, result.getFeedbackToUser());
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        TagGroup group = new TagGroup("finance");
        TagGroupCommand commandA = new TagGroupCommand(group);
        TagGroupCommand commandB = new TagGroupCommand(new TagGroup("finance"));
        assertTrue(commandA.equals(commandB));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        TagGroupCommand commandA = new TagGroupCommand(new TagGroup("finance"));
        TagGroupCommand commandB = new TagGroupCommand(new TagGroup("location"));
        assertFalse(commandA.equals(commandB));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        TagGroupCommand command = new TagGroupCommand(new TagGroup("test"));
        assertTrue(command.equals(command));
    }

    @Test
    public void equals_null_returnsFalse() {
        TagGroupCommand command = new TagGroupCommand(new TagGroup("test"));
        assertFalse(command.equals(null));
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
        public boolean isTagGroupInUse(TagGroup tg) {
            throw new AssertionError("This method should not be called.");
        }

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
    }
}
