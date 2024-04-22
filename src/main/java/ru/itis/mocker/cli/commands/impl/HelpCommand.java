package ru.itis.mocker.cli.commands.impl;

import ru.itis.mocker.cli.commands.Command;


import java.util.List;
import java.util.Map;
public class HelpCommand implements Command {

    @Override
    public void execute(List<String> arguments, Map<String, String> options) throws Exception {
        printHelp();
    }

    private void printHelp() {
        System.out.println("Commands:                Description");
        System.out.println("------------             --------------");
        System.out.println("generate                 Processes the specified file and generates a project with mocks.");
        System.out.println("run                      Runs Docker commands for starting the generated project.");
        System.out.println("validate                 Checks the specified file for correctness.");
    }
}
