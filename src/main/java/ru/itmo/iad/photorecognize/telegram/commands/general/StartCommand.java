package ru.itmo.iad.photorecognize.telegram.commands.general;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.iad.photorecognize.service.MonitoringService;
import ru.itmo.iad.photorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.photorecognize.telegram.response.Response;
import ru.itmo.iad.photorecognize.telegram.response.StringResponse;

@Service
@Slf4j
public class StartCommand extends AbsCommand {

    @Autowired
    private MonitoringService monitoringService;

    @Override
    public Response<?> execute() {

        String text = """
                Привет! Это бот, который определяет, что изображено на твоей фотографии.

                Все просто: ты загружаешь фотографию в диалог (в сжатом виде) и бот присылает тебе 3 наиболее вероятных класса того, что там изображено!
                                
                Ограничения по фото: не более 50 Мб, 1080*1920
                Форматы: png, jpg, jpeg
                                
                Описание, что означает каждый класс, доступно по ссылке\s
                https://docs.google.com/document/d/1pG41a8pfhYZNnzaHU08J-A06_P2_QkFhOHgTluehetU
                """;

        try {
            monitoringService.incrementStartCounter();
            return new StringResponse(text);
        } catch (Exception e) {
            log.error("Failed to send Start response, " + e.getMessage());
            monitoringService.incrementStartErrorCounter();
            return new StringResponse("error occurs...");
        }
    }
}
