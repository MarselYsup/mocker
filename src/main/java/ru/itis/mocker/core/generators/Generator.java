package ru.itis.mocker.core.generators;

import ru.itis.mocker.core.models.MockerModel;

public interface Generator {
    void generateContent(MockerModel content);

    void setPath(String path);
}
