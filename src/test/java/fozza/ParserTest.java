package fozza;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import fozza.FozzaException;
import fozza.CommandType;
import fozza.ParsedCommand;

public class ParserTest {

    @Test
    public void parse_todo_success() throws Exception {
        ParsedCommand c = Parser.parse("todo read book");

        assertEquals(CommandType.TODO, c.getCommandType());
        assertEquals("read book", c.getArg1());
    }

    @Test
    public void parse_deadline_success() throws Exception {
        ParsedCommand c = Parser.parse("deadline return book /by 2019-12-02");

        assertEquals(CommandType.DEADLINE, c.getCommandType());
        assertEquals("return book", c.getArg1());
        assertEquals("2019-12-02", c.getArg2());
    }

    @Test
    public void parse_deadline_missingBy_throwsException() {
        assertThrows(
                FozzaException.class,
                () -> Parser.parse("deadline return book")
        );
    }
}
