package ru.itis.mocker.cli.commands;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Command {
     void execute(List<String> arguments, Map<String, String> options) throws Exception;
}
