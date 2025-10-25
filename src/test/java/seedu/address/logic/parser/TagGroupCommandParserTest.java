package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TagGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.TagGroup;

public class TagGroupCommandParserTest {

    private final TagGroupCommandParser parser = new TagGroupCommandParser();

    @Test
    public void parse_emptyArgs_returnsListCommand() throws Exception {
        TagGroupCommand command = parser.parse("");
        assertEquals(new TagGroupCommand(null), command);
    }

    @Test
    public void parse_whitespaceOnlyArgs_returnsListCommand() throws Exception {
        TagGroupCommand command = parser.parse("   ");
        assertEquals(new TagGroupCommand(null), command);
    }

    @Test
    public void parse_validTagGroupName_returnsCreateCommand() throws Exception {
        TagGroupCommand command = parser.parse("housing");
        assertEquals(new TagGroupCommand(new TagGroup("housing")), command);
    }

    @Test
    public void parse_invalidTagGroupName_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("!!invalidTag"));
    }
}
