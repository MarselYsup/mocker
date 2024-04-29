package ru.itis.mocker.core.generators.impl;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import lombok.Getter;
import lombok.Setter;
import ru.itis.mocker.core.generators.Generator;
import ru.itis.mocker.core.mappers.ClassMapper;
import ru.itis.mocker.core.models.MockerModel;
import ru.itis.mocker.core.utils.ClassUtils;
import ru.itis.mocker.core.utils.FileUtils;
import ru.itis.mocker.core.utils.StringUtils;

import java.io.IOException;
import java.io.StringWriter;

import static ru.itis.mocker.core.consts.DirectoryConsts.*;

public final class ClassGenerator implements Generator {

    private static final String TEMPLATE_PATH = "templates/class.mustache";

    private static final String DIRECTORY_NAME = "dto";

    private static ClassGenerator INSTANCE;

    private final Mustache mustacheGenerator;

    @Setter
    @Getter
    private String path;
    private ClassGenerator() {
        mustacheGenerator = configureMustacheGenerator();
    }

    public static ClassGenerator getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ClassGenerator();
        }

        return INSTANCE;
    }

    @Override
    public void generateContent(MockerModel content) {
        if (content.getClasses() == null) {
            return;
        }
        content.getClasses()
                .stream()
                .map(obj -> ClassMapper.mapToClassObject(obj, content))
                .forEach(obj -> {
                    try(StringWriter stringWriter = new StringWriter()) {
                        mustacheGenerator.execute(stringWriter, obj).flush();
                        FileUtils.createFile(
                                getFullFileName(obj.getName(), content),
                                stringWriter.toString()
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
                MAIN_JAVA_PATH,
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
