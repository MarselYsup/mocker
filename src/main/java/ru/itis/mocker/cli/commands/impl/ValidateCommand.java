package ru.itis.mocker.cli.commands.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ru.itis.mocker.cli.commands.Command;
import ru.itis.mocker.core.consts.TypeConsts;
import ru.itis.mocker.core.models.ClassModel;
import ru.itis.mocker.core.models.FieldModel;
import ru.itis.mocker.core.models.MockerModel;
import ru.itis.mocker.core.utils.FileUtils;
import ru.itis.mocker.core.utils.TypeUtils;

import java.util.List;
import java.util.Map;

import static ru.itis.mocker.core.utils.ValidateUtils.validate;

public class ValidateCommand implements Command {
    @Override
    public void execute(List<String> arguments, Map<String, String> options) throws Exception {

        String help = options.get("--help");

        if (help != null) {
            printHelp();
            return;
        }

        String pathOfFile = arguments.get(0);

        String json = FileUtils.readContentFromFile(pathOfFile);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        MockerModel mc;

        try {
           mc = objectMapper.readValue(json, MockerModel.class);
        } catch (JsonProcessingException e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            return;
        }

        validate(mc);
    }



    private void printHelp() {
        System.out.println("Usage: mocker validate [pathOfFile]");
        System.out.println("The 'validate' command validates the specified file at pathOfFile.");
        System.out.println();
        System.out.println("Arguments:");
        System.out.println("\tpathOfFile\tPath to the file to be validated. Required if not using --help.");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("\tmocker validate /path/to/file.mock");
        System.out.println("\tmocker validate --help");
    }
}