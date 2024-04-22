package ru.itis.mocker.core.generators;

import ru.itis.mocker.core.models.MockerModel;
import ru.itis.mocker.core.utils.FileUtils;
import ru.itis.mocker.core.utils.StringUtils;

import java.util.Set;

import static ru.itis.mocker.core.consts.DirectoryConsts.*;

public class GeneratorFactory {
    private final Set<Generator> generatorSet;

    private final String path;

    public GeneratorFactory(Set<Generator> generatorSet, String path) {
        this.generatorSet = generatorSet;
        this.path = path;
    }

    public void generate(MockerModel mockerModel) {
        createDirectories(mockerModel);
        generatorSet.forEach(generator -> {
            generator.setPath(path);
            generator.generateContent(mockerModel);
        });
    }

    private void createDirectories(MockerModel mockerModel) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(path);
        stringBuilder.append("/");
        stringBuilder.append(MAIN_JAVA_PATH);
        stringBuilder.append("/");
        stringBuilder.append(StringUtils.splitByDotAndJoinWithSlash(mockerModel.getGroupId()));
        stringBuilder.append("/");
        stringBuilder.append(mockerModel.getArtefactId());
        FileUtils.createDirectory(stringBuilder.toString());
        createControllerDirectory(stringBuilder.toString());
        createDtoDirectory(stringBuilder.toString());
    }

    private void createControllerDirectory(String path) {
        FileUtils.createDirectory(String.format("%s/%s", path, CONTROLLERS));
    }

    private void createDtoDirectory(String path) {
        FileUtils.createDirectory(String.format("%s/%s", path, DTO));
    }


}
