package ru.itmo.iad.photorecognize.telegram;

import java.util.Optional;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.iad.photorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.photorecognize.telegram.response.Response;

@Slf4j
public class Bot extends TelegramLongPollingBot {

	private final MessageParser commandParser;
	private final CallbackParser callbackParser;

	private final String BOT_USERNAME;
	private final String BOT_TOKEN;

	public Bot(String botUserName, String botToken, MessageParser commandParser, CallbackParser callbackParser) {
		this.BOT_USERNAME = botUserName;
		this.BOT_TOKEN = botToken;
		this.commandParser = commandParser;
		this.callbackParser = callbackParser;
	}

	@Override
	public void onUpdateReceived(Update update) {

		if (update.hasMessage()) {
			if (update.getMessage().hasText()) {

				Message message = update.getMessage();

				String messageText = message.getText();
				Long chatId = message.getChatId();
				User author = message.getFrom();

				if (messageText.startsWith("/")) {
					String authorId = (author.getUserName() == null) ? author.getFirstName() : author.getUserName();
					log.info("Command {} from {}", messageText, authorId);

					/* Parsing command */
					Optional<AbsCommand> optionalCommandHandler = commandParser.parseMessage(messageText, author);

					optionalCommandHandler.ifPresent(handler -> {
						try {

							/* Executing command */
							Response<?> result = handler.execute();

							/* Sending result of command */
							result.send(this, chatId);
						} catch (TelegramApiException ex) {
							log.error("Error sending result of command {} from {}!", messageText, author.getId(), ex);
						} catch (Exception ex) {
							log.error("Error during processing command {}!", messageText, ex);
						}
					});
				}
			} else {
				if (update.getMessage().getPhoto().size() > 0) {
					log.info("Received photo!");
				}
			}
		}

		if (update.hasCallbackQuery()) {
			CallbackQuery callback = update.getCallbackQuery();

			String messageText = callback.getData();
			long chatId = callback.getMessage().getChatId();
			int messageId = callback.getMessage().getMessageId();
			User author = callback.getFrom();

			String authorId = (author.getUserName() == null) ? author.getFirstName() : author.getUserName();
			log.info("Callback \"{}\" from {}", messageText, authorId);

			/* Parsing callback */
			Optional<AbsCommand> optionalCallbackHandler = callbackParser.parseCallback(messageText, messageId, author);

			optionalCallbackHandler.ifPresent(handler -> {
				try {

					/* Executing command */
					Response<?> result = handler.execute();

					/* Sending result of command */
					result.send(this, chatId);
				} catch (TelegramApiException ex) {
					log.error("Error sending result of callback {} from {}!", messageText, author.getId(), ex);
				} catch (Exception ex) {
					log.error("Error during processing callback {}!", messageText, ex);
				}
			});
		}
	}

	@Override
	public String getBotUsername() {
		return BOT_USERNAME;
	}

	@Override
	public String getBotToken() {
		return BOT_TOKEN;
	}

}
