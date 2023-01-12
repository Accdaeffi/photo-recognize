package ru.itmo.iad.photorecognize.telegram;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.itmo.iad.photorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.photorecognize.telegram.commands.photos.RecognizePhotoCommand;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PhotoParser implements ApplicationContextAware {

    @Autowired
    ApplicationContext appContext;

    /**
     * Decide, which message was sent and execute necessary operations. Main method
     * of the class.
     */
    public Optional<AbsCommand> parseMessageWithPhoto(@NonNull List<PhotoSize> photoSizes, @Nullable String messageText,
                                                      @NonNull User messageAuthor) {

        try {
            String command;
            String argument;

            if (messageText != null) {
                String arr[] = messageText.split(" ", 2);
                command = arr[0];
                if (command.contains("@")) {
                    command = arr[0].substring(0, arr[0].indexOf("@"));
                }
                argument = (arr.length > 1) ? arr[1] : null;
            } else {
                command = null;
                argument = null;
            }

            AbsCommand commandHandler;

            if (command == null) {
                commandHandler = appContext.getBean(RecognizePhotoCommand.class, messageAuthor.getId().toString(), photoSizes);
            } else {
                switch (command) {
                    default: {
                        commandHandler = null;
                    }
                }
            }

            return Optional.ofNullable(commandHandler);
        } catch (Exception ex) {
            log.error("Error during parsing command {}!", messageText, ex);
            return Optional.empty();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }
}