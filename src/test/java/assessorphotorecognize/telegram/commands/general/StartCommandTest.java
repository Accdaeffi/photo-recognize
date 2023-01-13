package assessorphotorecognize.telegram.commands.general;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.itmo.iad.photorecognize.service.MonitoringService;
import ru.itmo.iad.photorecognize.telegram.commands.general.StartCommand;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

class StartCommandTest {
    @Mock
    private MonitoringService monitoringService;

    @InjectMocks
    private StartCommand startCommand;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        Mockito.doNothing().when(monitoringService).incrementStartCounter();
    }

    @Test
    void testResponse() {
        assertNotNull(startCommand.execute().getContent());
    }

    @Test
    void testException() {
        doThrow(RuntimeException.class).when(monitoringService).incrementStartCounter();
        startCommand.execute();
        verify(monitoringService).incrementStartErrorCounter();
    }
}