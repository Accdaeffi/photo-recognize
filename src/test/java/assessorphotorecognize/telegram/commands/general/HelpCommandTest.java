package assessorphotorecognize.telegram.commands.general;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.itmo.iad.photorecognize.service.MonitoringService;
import ru.itmo.iad.photorecognize.telegram.commands.general.HelpCommand;

import static com.mongodb.assertions.Assertions.assertFalse;

class HelpCommandTest {
    @Mock
    private MonitoringService monitoringService;

    @InjectMocks
    private HelpCommand helpCommand;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        Mockito.doNothing().when(monitoringService).incrementHelpCounter();
    }

    @Test
    void testResponse() {
        assertFalse(helpCommand.execute().getContent().isEmpty());
    }
}