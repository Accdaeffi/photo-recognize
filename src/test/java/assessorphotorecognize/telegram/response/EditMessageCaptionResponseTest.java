package assessorphotorecognize.telegram.response;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.itmo.iad.photorecognize.telegram.response.EditMessageCaptionResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class EditMessageCaptionResponseTest {

    private final EditMessageCaptionResponse response
            = new EditMessageCaptionResponse("text", 1, new InlineKeyboardMarkup());

    @Test
    void testSend() throws TelegramApiException {
        AbsSender absSender = mock(AbsSender.class);
        Mockito.when(absSender.execute(any(SendMessage.class))).thenReturn(null);

        response.send(absSender, 2L);

        verify(absSender).execute(any(EditMessageCaption.class));
    }

}