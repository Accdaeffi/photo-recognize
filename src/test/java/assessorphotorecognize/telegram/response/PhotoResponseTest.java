package assessorphotorecognize.telegram.response;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.itmo.iad.photorecognize.telegram.response.PhotoResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PhotoResponseTest {

    private final PhotoResponse response
            = new PhotoResponse(new InputFile(), "text");

    private final PhotoResponse responseFull
            = new PhotoResponse(null, "text", "caption", new ReplyKeyboardMarkup());

    @Test
    void testSend() throws TelegramApiException {
        AbsSender absSender = mock(AbsSender.class);
        Mockito.when(absSender.execute(any(SendMessage.class))).thenReturn(null);

        response.send(absSender, 2L);

        verify(absSender).execute(any(SendPhoto.class));
    }

    @Test
    void testSend2() throws TelegramApiException {
        AbsSender absSender = mock(AbsSender.class);
        Mockito.when(absSender.execute(any(SendMessage.class))).thenReturn(null);

        responseFull.send(absSender, 2L);

        verify(absSender).execute(any(SendPhoto.class));
    }

    @Test
    void testGet() {
        assertEquals("text", response.getContent());
    }

}