package com.evg.teachingassistant.tg.util;

import com.evg.teachingassistant.exception.CommandNotFoundException;
import com.evg.teachingassistant.tg.model.CommandType;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CommandParsUtil {

    private final Logger logger;

    public CommandParsUtil(Logger logger) {
        this.logger = logger;
    }

    public CommandType getCommand(String text){
        String commandFromTg = text.split(" ")[0];
        logger.info("Command default: {}", commandFromTg);
        return Arrays.stream(CommandType.values())
                .filter(command -> command.getCommand().equals(commandFromTg))
                .findFirst()
                .orElseThrow(CommandNotFoundException::new);
    }
}
