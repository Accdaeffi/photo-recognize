package assessorphotorecognize.telegram.response;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.itmo.iad.photorecognize.telegram.response.EditMessageResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class EditMessageResponseTest {

    private final EditMessageResponse response
            = new EditMessageResponse("text", 1, new InlineKeyboardMarkup());

    @Test
    void testSend() throws TelegramApiException {
        AbsSender absSender = mock(AbsSender.class);
        Mockito.when(absSender.execute(any(SendMessage.class))).thenReturn(null);

        response.send(absSender, 2L);

        verify(absSender).execute(any(EditMessageText.class));
    }

}