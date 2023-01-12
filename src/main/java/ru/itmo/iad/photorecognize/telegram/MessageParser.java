package ru.itmo.iad.photorecognize.telegram;

import java.util.Optional;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.itmo.iad.photorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.photorecognize.telegram.commands.general.StartCommand;

@Service
@Slf4j
public class MessageParser implements ApplicationContextAware {

    @Autowired
    ApplicationContext appContext;

    /**
     * Decide, which message was sent and execute necessary operations. Main method
     * of the class.
     */
    public Optional<AbsCommand> parseMessage(@NonNull String messageText, @NonNull User messageAuthor) {

        try {
            String arr[] = messageText.split(" ", 2);
            String command = arr[0];
            if (command.contains("@")) {
                command = arr[0].substring(0, arr[0].indexOf("@"));
            }
            String argument = (arr.length > 1) ? arr[1] : null;

            AbsCommand commandHandler;

            switch (command) {
                case "/start": {
                    commandHandler = appContext.getBean(StartCommand.class);
                }
                break;

                default: {
                    commandHandler = null;
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
