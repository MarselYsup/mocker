package ru.itis.mocker.maven;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import ru.itis.mocker.core.generators.Generator;
import ru.itis.mocker.core.generators.GeneratorFactory;
import ru.itis.mocker.core.generators.impl.*;
import ru.itis.mocker.core.models.MockerModel;
import ru.itis.mocker.core.utils.FileUtils;

import java.io.IOException;
import java.util.Set;

import static ru.itis.mocker.core.utils.ValidateUtils.validate;


@Mojo(name = "generate", defaultPhase = LifecyclePhase.COMPILE)
public class GeneratePlugin extends AbstractMojo {

    private static final Set<Generator> SET_OF_GENERATORS = Set.of(
            ClassGenerator.getInstance(), ApplicationGenerator.getInstance(),
            ControllerGenerator.getInstance(), DockerMavenGenerator.getInstance(),
            MavenGenerator.getInstance(), ReadmeGenerator.getInstance()
    );

    @Parameter(property = "generate.pathOfFile", required = true)
    private String pathOfFile;

    @Parameter(property = "generate.pathToGenerate", defaultValue = "./mocker/")
    private String pathToGenerate;

    public void execute() {
        String json = null;
        try {
            json = FileUtils.readContentFromFile(pathOfFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        MockerModel mc = null;
        try {
            mc = objectMapper.readValue(json, MockerModel.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (!validate(mc)) {
            throw new RuntimeException();
        }

        GeneratorFactory generatorFactory;

        generatorFactory = new GeneratorFactory(SET_OF_GENERATORS, pathToGenerate);

        generatorFactory.generate(mc);
    }
}