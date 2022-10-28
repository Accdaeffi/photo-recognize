package ru.itmo.iad.photorecognize.telegram;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.User;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import ru.itmo.iad.photorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.photorecognize.telegram.commands.exception.NotEnoughParametersExceptionCommand;
import ru.itmo.iad.photorecognize.telegram.commands.photos.SaveNewPhotoToTrainSetCommand;

@Service
@Slf4j
public class PhotoParser implements ApplicationContextAware {

	@Autowired
	ApplicationContext appContext;

	/**
	 * Decide, which message was sent and execute necessary operations. Main method
	 * of the class.
	 */
	public Optional<AbsCommand> parseMessage(@NonNull List<PhotoSize> photoSizes, @Nullable String messageText,
			@NonNull User messageAuthor) {

		try {
			String arr[] = messageText.split(" ", 2);
			String command = arr[0];
			if (command.contains("@")) {
				command = arr[0].substring(0, arr[0].indexOf("@"));
			}
			String argument = (arr.length > 1) ? arr[1] : null;

			AbsCommand commandHandler;

			switch (command) {
			case "asd2a2213h": {
				commandHandler = appContext.getBean(SaveNewPhotoToTrainSetCommand.class, photoSizes);
			}
				break;

			default: {
				commandHandler = null;
			}
			}

			return Optional.ofNullable(commandHandler);
		} catch (BeansException ex) {
			log.error("Error!", ex);
			return Optional.ofNullable(appContext.getBean(NotEnoughParametersExceptionCommand.class));
		} catch (Exception ex) {
			log.error("Error during parsing command {}!", messageText, ex);
			return Optional.ofNullable(null);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.appContext = applicationContext;
	}
}