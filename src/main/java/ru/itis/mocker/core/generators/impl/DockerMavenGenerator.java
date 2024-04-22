package ru.itis.mocker.core.generators.impl;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import lombok.Getter;
import lombok.Setter;
import ru.itis.mocker.core.generators.Generator;
import ru.itis.mocker.core.mappers.DockerMapper;
import ru.itis.mocker.core.models.MockerModel;
import ru.itis.mocker.core.objects.DockerObject;
import ru.itis.mocker.core.utils.FileUtils;

import java.io.IOException;
import java.io.StringWriter;

public final class DockerMavenGenerator implements Generator {

    private static final String TEMPLATE_PATH = "templates/maven/docker.mustache";

    private static final String FILE_NAME = "Dockerfile";

    private static DockerMavenGenerator INSTANCE;


    private final Mustache mustacheGenerator;

    @Setter
    @Getter
    private String path;

    private DockerMavenGenerator() {
        mustacheGenerator = configureMustacheGenerator();
    }

    public static DockerMavenGenerator getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new DockerMavenGenerator();
        }

        return INSTANCE;
    }

    @Override
    public void generateContent(MockerModel content) {
        DockerObject dockerObject = DockerMapper.mapToMavenObject(content);
        try(StringWriter stringWriter = new StringWriter()) {
            mustacheGenerator.execute(stringWriter, dockerObject).flush();
            FileUtils.createFile(
                    getFullFileName(),
                    stringWriter.toString()
            );
            stringWriter.getBuffer().setLength(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFullFileName() {
        return String.format("%s/%s", path, FILE_NAME);
    }

    private Mustache configureMustacheGenerator() {
        MustacheFactory mf = new DefaultMustacheFactory();
        return mf.compile(TEMPLATE_PATH);
    }
}
