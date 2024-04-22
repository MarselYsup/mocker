package ru.itis.mocker.core.parsers;

import ru.itis.mocker.core.models.MockerModel;

public interface MockerModelParser {
    MockerModel parseFile(String filePath);
}
