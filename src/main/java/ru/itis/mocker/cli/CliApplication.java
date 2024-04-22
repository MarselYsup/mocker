package ru.itis.mocker.cli;

import ru.itis.mocker.cli.commands.Command;
import ru.itis.mocker.cli.commands.impl.GenerateCommand;
import ru.itis.mocker.cli.commands.impl.HelpCommand;
import ru.itis.mocker.cli.commands.impl.RunCommand;
import ru.itis.mocker.cli.commands.impl.ValidateCommand;

import java.util.*;

import static ru.itis.mocker.cli.commands.Commands.*;

public class CliApplication {

    public static void main(String[] args) {
        List<String> arguments = new ArrayList<>();
        Map<String, String> options = new HashMap<>();

        if (args.length == 0) {
            System.err.println("No command provided.");
            return;
        }

        // Parse command arguments and options
        for (int i = 1; i < args.length; i++) {
            String arg = args[i];
            if (arg.startsWith("--")) {
                if(arg.startsWith("--help")) {
                    options.put(arg, arg);
                    break;
                }
                int equalsIndex = arg.indexOf('=');
                if (equalsIndex != -1) {
                    String key = arg.substring(0, equalsIndex);
                    String value = arg.substring(equalsIndex + 1);
                    options.put(key, value);
                } else if (i + 1 < args.length) {
                    String key = arg;
                    String value = args[++i]; // Move to the next argument for the value
                    options.put(key, value);
                } else {
                    System.err.println("Error: Expected value after flag " + arg);
                    return;
                }
            } else {
                arguments.add(arg);
            }
        }

        // Extracting command and options
        String commandName = args[0];

        Command command;
        switch (commandName) {
            case GENERATE_COMMAND:
                command = new GenerateCommand();
                break;
            case VALIDATE_COMMAND:
                command = new ValidateCommand();
                break;
            case RUN_COMMAND:
                command = new RunCommand();
                break;
            case HELP_COMMAND:
                command = new HelpCommand();
                break;
            default:
                System.out.println("Unknown command: " + commandName);
                return;
        }

        // Execute the command with options
        try {
            command.execute(arguments, options);
        } catch (Exception e) {
            System.err.println("Command failed: " + e.getMessage());
        }
    }



}
