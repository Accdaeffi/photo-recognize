package assessorphotorecognize.telegram.commands.general;

import org.junit.jupiter.api.Test;
import ru.itmo.iad.photorecognize.telegram.commands.general.HelpCommand;

import static com.mongodb.assertions.Assertions.assertFalse;

class HelpCommandTest {

    private final HelpCommand helpCommand = new HelpCommand();

    @Test
    void testResponse() {
        assertFalse(helpCommand.execute().getContent().isEmpty());
    }

}