package assessorphotorecognize.telegram.commands.general;

import org.junit.jupiter.api.Test;
import ru.itmo.iad.photorecognize.telegram.commands.general.StartCommand;

import static com.mongodb.assertions.Assertions.assertNotNull;

class StartCommandTest {

    private final StartCommand startCommand = new StartCommand();

    @Test
    void testResponse() {
        assertNotNull(startCommand.execute().getContent());
    }

}