package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagFormat_throwsIllegalArgumentException() {
        String invalidTagFormat = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagFormat));

        String invalidTag = "propertyType!HDB"; // invalid character '!'
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTag));
    }

    @Test
    public void isValidTagFormat_null_throwsNullPointerException() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagFormat(null));
    }

    @Test
    public void isValidTagFormat_validTags_returnsTrue() {
        assertTrue(Tag.isValidTagFormat("propertyType.HDB"));
        assertTrue(Tag.isValidTagFormat("location.Sengkang"));
        assertTrue(Tag.isValidTagFormat("group1.value1"));
        assertTrue(Tag.isValidTagFormat("G2.V2"));
    }

    @Test
    public void isValidTagFormat_missingDot_returnsTrue() {
        assertTrue(Tag.isValidTagFormat("propertyTypeHDB"));
        assertTrue(Tag.isValidTagFormat("locationSengkang"));
        assertTrue(Tag.isValidTagFormat("group1value1"));
    }

    @Test
    public void isValidTagFormat_startWithDot_returnsFalse() {
        assertFalse(Tag.isValidTagFormat(".propertyTypeHDB"));
        assertFalse(Tag.isValidTagFormat(".locationSengkang"));
        assertFalse(Tag.isValidTagFormat("location..Sengkang"));
    }

    @Test
    public void isValidTagFormat_endWithDot_returnsFalse() {
        assertFalse(Tag.isValidTagFormat("propertyTypeHDB."));
        assertFalse(Tag.isValidTagFormat("locationSengkang."));
    }

    @Test
    public void isValidTagFormat_multipleDots_returnsTrue() {
        assertTrue(Tag.isValidTagFormat("priceRange.800k-1M"));
        assertTrue(Tag.isValidTagFormat("group1.value1.another"));
    }

    @Test
    public void isValidTagFormat_invalidCharacters_returnsFalse() {
        assertFalse(Tag.isValidTagFormat("propertyType.H*DB"));
        assertFalse(Tag.isValidTagFormat("loca@tion.Sengk@ng"));
        assertFalse(Tag.isValidTagFormat("group1.v@lue"));
    }

    @Test
    public void getterMethods_simpleTag_noGroup() {
        Tag tag = new Tag("priority");
        assertEquals("priority", tag.getTagFormat());
        assertNull(tag.getGroup()); // no group
        assertEquals("priority", tag.getValue());
    }

    @Test
    public void getterMethods_groupedTag() {
        Tag tag = new Tag("property.HDB");
        assertEquals("PROPERTY.HDB", tag.getTagFormat());
        assertEquals(new TagGroup("property"), tag.getGroup());
        assertEquals("HDB", tag.getValue());
    }

    @Test
    public void hasGroup_tagWithGroup_returnsTrue() {
        Tag tagWithGroup = new Tag("group.value");
        assertTrue(tagWithGroup.hasGroup());
    }

    @Test
    public void hasGroup_tagWithoutGroup_returnsFalse() {
        Tag tagWithoutGroup = new Tag("sad");
        assertFalse(tagWithoutGroup.hasGroup());
    }

    @Test
    public void equals_sameTagName_isEqual() {
        Tag tag1 = new Tag("propertyType.HDB");
        Tag tag2 = new Tag("propertyType.HDB");
        assertTrue(tag1.equals(tag2));
        assertTrue(tag1.equals(tag1));
    }

    @Test
    public void equals_notTag_notEqual() {
        Tag tag1 = new Tag("propertyType.HDB");
        String tag2 = "propertyType.HDB";
        assertFalse(tag1.equals(tag2));
    }

    @Test
    public void equals_differentTagNames_notEqual() {
        Tag tag1 = new Tag("propertyType.HDB");
        Tag tag2 = new Tag("priority");
        assertFalse(tag1.equals(tag2));
    }

    @Test
    public void hashCode_sameContent_sameHashCode() {
        Tag tag1a = new Tag("priority");
        Tag tag1b = new Tag("priority");
        assertEquals(tag1a.hashCode(), tag1b.hashCode());
    }

    @Test
    public void toString_returnsTagName() {
        Tag tag = new Tag("location.Sengkang");
        assertEquals("[LOCATION.Sengkang]", tag.toString());
    }

}
