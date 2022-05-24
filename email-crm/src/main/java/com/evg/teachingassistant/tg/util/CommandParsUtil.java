package com.evg.teachingassistant.tg.util;

import com.evg.teachingassistant.exception.CommandNotFoundException;
import com.evg.teachingassistant.tg.model.CommandType;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CommandParsUtil {

    public CommandType getCommand(String text) {
        String commandFromTg = text.split(" ")[0];
        return Arrays.stream(CommandType.values())
                .filter(command -> command.getCommand().equals(commandFromTg))
                .findFirst()
                .orElseThrow(CommandNotFoundException::new);
    }
}
