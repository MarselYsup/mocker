package ru.itis.mocker.cli.commands.impl;

import ru.itis.mocker.cli.commands.Command;

import java.util.List;
import java.util.Map;

public class ValidateCommand implements Command {
    @Override
    public void execute(List<String> arguments, Map<String, String> options) throws Exception {


        System.out.println("Validating...");
        // Your validation logic here
    }
}