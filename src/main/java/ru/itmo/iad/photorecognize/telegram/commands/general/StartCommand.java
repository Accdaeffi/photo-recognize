package ru.itmo.iad.photorecognize.telegram.commands.general;

import org.springframework.stereotype.Service;
import ru.itmo.iad.photorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.photorecognize.telegram.response.Response;
import ru.itmo.iad.photorecognize.telegram.response.StringResponse;

@Service
public class StartCommand extends AbsCommand {

    @Override
    public Response<?> execute() {

        String text = """
                Привет! Это бот, который определяет, что изображенно на твоей фотографии.

                Все просто: ты загружаешь фотографию в диалог (в сжатом виде) и бот присылает тебе 3 наиболее вероятных класса того, что там изображено!
                Описание, что означает каждый класс, доступно по ссылке\s
                https://docs.google.com/document/d/1pG41a8pfhYZNnzaHU08J-A06_P2_QkFhOHgTluehetU
                """;

        return new StringResponse(text);
    }

}
