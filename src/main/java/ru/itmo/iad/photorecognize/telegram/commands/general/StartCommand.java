package ru.itmo.iad.photorecognize.telegram.commands.general;

import org.springframework.stereotype.Service;
import ru.itmo.iad.photorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.photorecognize.telegram.response.Response;
import ru.itmo.iad.photorecognize.telegram.response.StringResponse;

@Service
public class StartCommand extends AbsCommand {

    @Override
    public Response<?> execute() {


        StringBuilder builder = new StringBuilder();
        // TODO: refactor
        builder.append("Привет! Это бот, с помощью которого ты можешь помочь нам разметить фотографии.\n\n" +
            "Все просто: ты получаешь фотографию и выбираешь, какой класс больше всего подходит " +
            "главному объекту на изображении или идее изображения в целом. " +
            "Людей и животных на фотографиях можно игнорировать.\n\n"
            + "Мы написали подробное описание классов - обращайся к нему, если что-то не понятно.");
        builder.append(System.lineSeparator());
        builder.append(System.lineSeparator());
        builder.append("https://docs.google.com/document/d/1pG41a8pfhYZNnzaHU08J-A06_P2_QkFhOHgTluehetU"
            + "\n\nЧтобы начать, отправь команду /next.");

        return new StringResponse(builder.toString());
    }

}
