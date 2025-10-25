package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteTagGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.TagGroup;

public class DeleteTagGroupCommandParserTest {

    private final DeleteTagGroupCommandParser parser = new DeleteTagGroupCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() throws Exception {
        DeleteTagGroupCommand command = parser.parse("location");
        assertEquals(new DeleteTagGroupCommand(new TagGroup("location")), command);
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
    }

    @Test
    public void parse_whitespaceOnlyArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("   "));
    }

    @Test
    public void parse_invalidTagGroupName_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("!!invalid"));
    }
}
