package seedu.address.model;

import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.tag.TagGroup;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the TagGroups in this address book.
     * @return unmodifiable set of TagGroups
     */
    Set<TagGroup> getTagGroups();

    /**
     * Returns true if the given TagGroup exists in the address book.
     * @param group TagGroup to check
     * @return true if exists, false otherwise
     */
    boolean hasTagGroup(TagGroup group);
}
