package ru.itis.mocker.core.generators.impl;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import lombok.Getter;
import lombok.Setter;
import ru.itis.mocker.core.generators.Generator;
import ru.itis.mocker.core.mappers.ApplicationMapper;
import ru.itis.mocker.core.objects.ApplicationObject;
import ru.itis.mocker.core.models.MockerModel;
import ru.itis.mocker.core.utils.FileUtils;
import ru.itis.mocker.core.utils.StringUtils;

import java.io.IOException;
import java.io.StringWriter;

import static ru.itis.mocker.core.consts.DirectoryConsts.*;

public final class ApplicationGenerator implements Generator {

    private static final String TEMPLATE_PATH = "templates/application.mustache";

    private static final String FILE_NAME = "Application.java";

    private static ApplicationGenerator INSTANCE;

    private final Mustache mustacheGenerator;

    @Setter
    @Getter
    private String path;
    private ApplicationGenerator() {
        mustacheGenerator = configureMustacheGenerator();
    }

    public static ApplicationGenerator getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ApplicationGenerator();
        }

        return INSTANCE;
    }
    @Override
    public void generateContent(MockerModel content) {
        ApplicationObject applicationObject = ApplicationMapper.mapToApplicationObject(content);
        try(StringWriter stringWriter = new StringWriter()) {
            mustacheGenerator.execute(stringWriter, applicationObject).flush();
            FileUtils.createFile(
                    getFullFileName(content),
                    stringWriter.toString()
            );
            stringWriter.getBuffer().setLength(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFullFileName(MockerModel mockerModel) {
        String packagePath = String.format("%s/%s",
                StringUtils.splitByDotAndJoinWithSlash(mockerModel.getGroupId()),
                mockerModel.getArtefactId()
        );
        return String.format(
                "%s/%s/%s/%s",
                path,
                MAIN_JAVA_PATH,
                packagePath,
                FILE_NAME
        );
    }

    private Mustache configureMustacheGenerator() {
        MustacheFactory mf = new DefaultMustacheFactory();
        return mf.compile(TEMPLATE_PATH);
    }
}
