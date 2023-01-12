package ru.itmo.iad.photorecognize.telegram.commands.general;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.itmo.iad.photorecognize.service.MonitoringService;
import ru.itmo.iad.photorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.photorecognize.telegram.response.StringResponse;

@Service
@Scope("prototype")
@Slf4j
public class HelpCommand extends AbsCommand {

    @Autowired
    private MonitoringService monitoringService;

    public HelpCommand() {
    }

    @Override
    public StringResponse execute() {

        StringBuilder builder = new StringBuilder();
        builder.append("Описание классов с примерами:");
        builder.append(System.lineSeparator());
        builder.append(System.lineSeparator());
        builder.append("https://docs.google.com/document/d/1pG41a8pfhYZNnzaHU08J-A06_P2_QkFhOHgTluehetU");

        try {
            monitoringService.incrementHelpCounter();
            return new StringResponse(builder.toString());
        } catch (Exception e) {
            log.error("Failed to send Start response, " + e.getMessage());
            monitoringService.incrementHelpErrorCounter();
            return new StringResponse("error occurs...");
        }
    }
}
