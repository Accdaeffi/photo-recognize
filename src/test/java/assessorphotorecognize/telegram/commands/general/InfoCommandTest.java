package assessorphotorecognize.telegram.commands.general;

import org.junit.jupiter.api.Test;
import ru.itmo.iad.photorecognize.telegram.commands.general.InfoCommand;
import ru.itmo.iad.photorecognize.telegram.exception.NotEnoughParametersException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InfoCommandTest {

    private final InfoCommand infoCommand = new InfoCommand("shopping");

    InfoCommandTest() throws Exception {
    }

    @Test
    void testResponse() {
        assertEquals("Фото внутри помещения. Магазины, ТЦ, рынки, салоны красоты, аптеки, а также общепит - кафе, бары, рестораны, и т.д.",
                infoCommand.execute().getContent());
    }

    @Test
    void testException() {
        assertThrows(NotEnoughParametersException.class,
                () -> new InfoCommand(null));
    }

}