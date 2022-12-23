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
import java.util.Optional;

@Service
@Slf4j
public class RecognizePhotoCommand extends AbsCommand {

    @Autowired
    ImageSaver imageSaver;

    @Autowired
    UserService userService;

    private final User telegramUser;
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

        //TODO: добавила валидацию картинок, потом нужно будет раскомментировать
        /*String result = checkUserImage(photo);
        if (!result.equals("ok")) {
            return new StringResponse(result);
        }*/

        imageSaver.saveUserImage(user.getTelegramId(), photo);

        log.info("Photo saved, user updated!");

        return new StringResponse("Success!");
    }

    private String checkUserImage(PhotoSize photo) {
        StringBuilder result = new StringBuilder();
        Optional.ofNullable(photo).ifPresentOrElse(
                (photoToCheck) -> {
                    if (!checkSizes(photoToCheck) || !checkExtension(photoToCheck)
                            || !checkSize(photoToCheck)) {
                        result.append("User image has invalid size or extension " +
                                "(max size is 1920*1080, image: png, jpg, jpeg)");
                    } else {
                        result.append("ok");
                    }
                },
                () -> result.append("Empty image")
        );
        return result.toString();
    }

    private boolean checkSize(PhotoSize photo) {
        int sizeInKb = photo.getFileSize() / 1024;
        return sizeInKb >= 1 && sizeInKb / 1024 <= 50;
    }

    private boolean checkSizes(PhotoSize photo) {
        return photo.getHeight() < 1080 && photo.getWidth() < 1920;
    }

    private boolean checkExtension(PhotoSize photo) {
        return photo.getFilePath().endsWith(".jpg")
                || photo.getFilePath().endsWith(".jpeg")
                || photo.getFilePath().endsWith(".png");
    }
}
