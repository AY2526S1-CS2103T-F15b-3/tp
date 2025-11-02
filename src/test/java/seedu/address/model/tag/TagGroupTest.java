package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagGroupTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TagGroup(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName1 = "";
        String invalidName2 = "propertyType!";
        assertThrows(IllegalArgumentException.class, () -> new TagGroup(invalidName1));
        assertThrows(IllegalArgumentException.class, () -> new TagGroup(invalidName2));
    }

    @Test
    public void isValidTagGroupName() {
        // null group name
        assertFalse(TagGroup.isValidTagGroupName(null));

        // invalid group names
        assertFalse(TagGroup.isValidTagGroupName(""));
        assertFalse(TagGroup.isValidTagGroupName(" "));
        assertFalse(TagGroup.isValidTagGroupName("propertyType!"));
        assertFalse(TagGroup.isValidTagGroupName("group 1"));

        // valid group names
        assertTrue(TagGroup.isValidTagGroupName("propertyType"));
        assertTrue(TagGroup.isValidTagGroupName("location123"));
        assertTrue(TagGroup.isValidTagGroupName("GROUP"));
    }

    @Test
    public void equals() {
        TagGroup group1a = new TagGroup("propertyType");
        TagGroup group1b = new TagGroup("propertyType");
        TagGroup group2 = new TagGroup("location");

        // same object
        assertTrue(group1a.equals(group1a));

        // different objects, same content
        assertTrue(group1a.equals(group1b));

        // different content
        assertFalse(group1a.equals(group2));

        // null object
        assertFalse(group1a.equals(null));

        // different type
        assertFalse(group1a.equals("propertyType"));
    }

    @Test
    public void getName() {
        TagGroup group1 = new TagGroup("property");
        assertTrue(group1.getName().equals("PROPERTY"));
    }

    @Test
    public void hashCode_sameContent_sameHashCode() {
        TagGroup group1a = new TagGroup("propertyType");
        TagGroup group1b = new TagGroup("propertyType");
        assertEquals(group1a.hashCode(), group1b.hashCode());
    }

    @Test
    public void toString_validTagGroupName_returnsGroupName() {
        TagGroup group = new TagGroup("property");
        assertEquals("PROPERTY", group.toString());
    }
}
