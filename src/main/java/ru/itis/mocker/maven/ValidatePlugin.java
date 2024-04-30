package ru.itis.mocker.maven;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import ru.itis.mocker.core.models.MockerModel;
import ru.itis.mocker.core.utils.FileUtils;

import java.io.IOException;

import static ru.itis.mocker.core.utils.ValidateUtils.validate;


@Mojo(name = "validate", defaultPhase = LifecyclePhase.VALIDATE)
public class ValidatePlugin extends AbstractMojo {

    @Parameter(property = "validate.pathOfFile", required = true)
    private String pathOfFile;


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
    }
}