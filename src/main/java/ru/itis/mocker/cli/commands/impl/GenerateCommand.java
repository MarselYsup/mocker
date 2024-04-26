package ru.itis.mocker.cli.commands.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itis.mocker.cli.commands.Command;
import ru.itis.mocker.core.generators.Generator;
import ru.itis.mocker.core.generators.GeneratorFactory;
import ru.itis.mocker.core.generators.impl.*;
import ru.itis.mocker.core.models.MockerModel;
import ru.itis.mocker.core.utils.FileUtils;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class GenerateCommand implements Command {

    private static final String DEFAULT_PATH = "./mocker/";

    private static final Set<Generator> SET_OF_GENERATORS = Set.of(
            ClassGenerator.getInstance(), ApplicationGenerator.getInstance(),
            ControllerGenerator.getInstance(), DockerMavenGenerator.getInstance(),
            MavenGenerator.getInstance(), ReadmeGenerator.getInstance()
    );

    @Override
    public void execute(List<String> arguments, Map<String, String> options) throws Exception {
        String help = options.get("--help");

        if (help != null) {
            printHelp();
            return;
        }

        if (arguments.isEmpty()) {
            System.err.println("Error: Missing pathOfFile for 'generate' command.");
            return;
        }

        String pathOfFile = arguments.get(0);
        String path = options.get("--path");


        String json = FileUtils.readContentFromFile(pathOfFile);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        MockerModel mc = objectMapper.readValue(json, MockerModel.class);

        GeneratorFactory generatorFactory;

        generatorFactory = new GeneratorFactory(SET_OF_GENERATORS, Objects.requireNonNullElse(path, DEFAULT_PATH));

        generatorFactory.generate(mc);
    }

    private void printHelp() {
        System.out.println("Usage: mocker generate [pathOfFile] [options]");
        System.out.println("The 'generate' command processes the specified file at pathOfFile and generate project with mocks.");
        System.out.println();
        System.out.println("Arguments:");
        System.out.println("\tpathOfFile\tPath to the file to be processed. Required if not using --help.");
        System.out.println();
        System.out.println("Options:");
        System.out.println("\t--help\t\tDisplays help information about the 'generate' command.");
        System.out.println("\t--path <path>\tSpecifies the path of the project used by the 'generate' command. If not specified, by default path = ./mocker/");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("\tmocker generate /path/to/file.mock --path /project/path");
        System.out.println("\tmocker generate --help");
    }
}