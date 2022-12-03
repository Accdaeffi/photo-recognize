package assessorphotorecognize.telegram.response;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.itmo.iad.photorecognize.telegram.response.EditMessageReplyMarkupResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class EditMessageReplyMarkupResponseTest {

    private final EditMessageReplyMarkupResponse response
            = new EditMessageReplyMarkupResponse(new InlineKeyboardMarkup(), 1);

    @Test
    void testSend() throws TelegramApiException {
        AbsSender absSender = mock(AbsSender.class);
        Mockito.when(absSender.execute(any(SendMessage.class))).thenReturn(null);

        response.send(absSender, 2L);

        verify(absSender).execute(any(EditMessageReplyMarkup.class));
    }

}