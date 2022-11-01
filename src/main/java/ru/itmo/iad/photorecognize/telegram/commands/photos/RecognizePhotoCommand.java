package ru.itmo.iad.photorecognize.telegram.commands.photos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.itmo.iad.photorecognize.domain.dao.UserDao;
import ru.itmo.iad.photorecognize.service.ImageSaver;
import ru.itmo.iad.photorecognize.service.UserService;
import ru.itmo.iad.photorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.photorecognize.telegram.response.Response;
import ru.itmo.iad.photorecognize.telegram.response.StringResponse;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class RecognizePhotoCommand extends AbsCommand {

    @Autowired
    ImageSaver imageSaver;

    @Autowired
    UserService userService;

    private User telegramUser;
    private final List<PhotoSize> photoSizes;

    public RecognizePhotoCommand(User telegramUser, List<PhotoSize> photoSizes) {
        this.telegramUser = telegramUser;
        this.photoSizes = photoSizes;
    }

    @Override
    public Response<?> execute() {

        log.info("Received photo to recognise!");

        UserDao user = userService.getOrCreateUser(telegramUser);

        PhotoSize photo = photoSizes.stream().max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null);
        imageSaver.saveUserImage(user.getTelegramId(), photo);

        log.info("Photo saved, user updated!");

        return new StringResponse("Success!");
    }
}
