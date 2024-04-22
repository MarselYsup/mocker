package ru.itis.mocker.core.generators.impl;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import lombok.Getter;
import lombok.Setter;
import ru.itis.mocker.core.generators.Generator;
import ru.itis.mocker.core.mappers.MavenMapper;
import ru.itis.mocker.core.models.MockerModel;
import ru.itis.mocker.core.objects.MavenObject;
import ru.itis.mocker.core.utils.FileUtils;

import java.io.IOException;
import java.io.StringWriter;

public final class MavenGenerator implements Generator {
    private static final String TEMPLATE_PATH = "templates/maven/pom.xml.mustache";

    private static final String FILE_NAME = "pom.xml";

    private final Mustache mustacheGenerator;

    private static MavenGenerator INSTANCE;


    @Setter
    @Getter
    private String path;

    private MavenGenerator() {
        mustacheGenerator = configureMustacheGenerator();
    }

    public static MavenGenerator getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new MavenGenerator();
        }

        return INSTANCE;
    }
    @Override
    public void generateContent(MockerModel content) {
        MavenObject mavenObject = MavenMapper.mapToMavenObject(content);
        try(StringWriter stringWriter = new StringWriter()) {
            mustacheGenerator.execute(stringWriter, mavenObject).flush();
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
