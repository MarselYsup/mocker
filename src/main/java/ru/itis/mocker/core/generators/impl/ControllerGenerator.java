package ru.itis.mocker.core.generators.impl;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import lombok.Getter;
import lombok.Setter;
import ru.itis.mocker.core.consts.DirectoryConsts;
import ru.itis.mocker.core.generators.Generator;
import ru.itis.mocker.core.mappers.ControllerMapper;
import ru.itis.mocker.core.models.MockerModel;
import ru.itis.mocker.core.utils.ClassUtils;
import ru.itis.mocker.core.utils.FileUtils;
import ru.itis.mocker.core.utils.StringUtils;

import java.io.IOException;
import java.io.StringWriter;

public final class ControllerGenerator implements Generator {

    private static final String TEMPLATE_PATH = "templates/controller.mustache";

    private static final String DIRECTORY_NAME = "controllers";

    private static ControllerGenerator INSTANCE;

    private final Mustache mustacheGenerator;

    @Setter
    @Getter
    private String path;
    private ControllerGenerator() {
        mustacheGenerator = configureMustacheGenerator();
    }

    public static ControllerGenerator getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ControllerGenerator();
        }

        return INSTANCE;
    }

    @Override
    public void generateContent(MockerModel content) {
        content.getMocks()
                .stream()
                .map(mock -> ControllerMapper.mapToControllerObject(mock, content))
                .forEach(mock -> {
                    try(StringWriter stringWriter = new StringWriter()) {
                        mustacheGenerator.execute(stringWriter, mock).flush();
                        FileUtils.createFile(
                                getFullFileName(mock.getName(), content),
                                StringUtils.formatString(stringWriter.toString())
                        );
                        stringWriter.getBuffer().setLength(0);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private String getFullFileName(String fileName, MockerModel mockerModel) {
        String packagePath = String.format("%s/%s",
                StringUtils.splitByDotAndJoinWithSlash(mockerModel.getGroupId()),
                mockerModel.getArtefactId()
        );
        return String.format(
                "%s/%s/%s/%s/%s",
                path,
                DirectoryConsts.MAIN_JAVA_PATH,
                packagePath,
                DIRECTORY_NAME,
                ClassUtils.getClassNameFile(fileName)
        );
    }

    private Mustache configureMustacheGenerator() {
        MustacheFactory mf = new DefaultMustacheFactory();
        return mf.compile(TEMPLATE_PATH);
    }



}
