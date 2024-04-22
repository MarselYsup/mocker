package ru.itis.mocker.core.parsers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import ru.itis.mocker.core.models.MockerModel;
import ru.itis.mocker.core.parsers.MockerModelParser;
import ru.itis.mocker.core.utils.FileUtils;

import java.io.IOException;

public class YamlMockerModelParser implements MockerModelParser {

    private final ObjectMapper objectMapper;

    public YamlMockerModelParser() {
        objectMapper = configureObjectMapper();
    }
    @Override
    public MockerModel parseFile(String filePath) {
        try {
            String json = FileUtils.readContentFromFile(filePath);
            return objectMapper.readValue(json, MockerModel.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private ObjectMapper configureObjectMapper() {
        return new ObjectMapper(new YAMLFactory());
    }
}