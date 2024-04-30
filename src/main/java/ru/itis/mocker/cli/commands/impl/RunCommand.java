package ru.itis.mocker.cli.commands.impl;

import ru.itis.mocker.cli.commands.Command;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RunCommand implements Command {

    private static final String DEFAULT_IMAGE_NAME = "mocker";

    @Override
    public void execute(List<String> arguments, Map<String, String> options) throws Exception {
        String help = options.get("--help");

        if (help != null) {
            printHelp();
            return;
        }

        if (arguments.isEmpty()) {
            System.err.println("Error: Missing path of project for 'run' command.");
            return;
        }

        String projectPath = arguments.get(0);
        String imageName = options.get("--image");
        String port = options.get("--port");

        executeDockerBuild(projectPath, imageName);
        executeDockerRun(imageName, port);
    }

    private void executeDockerBuild(String projectPath, String imageName) throws Exception {
        ProcessBuilder buildProcessBuilder;
        buildProcessBuilder = new ProcessBuilder("docker", "build", ".", "-t", Objects.requireNonNullElse(imageName, DEFAULT_IMAGE_NAME));
        buildProcessBuilder.redirectErrorStream(true);
        buildProcessBuilder.directory(new java.io.File(projectPath));
        Process buildProcess = buildProcessBuilder.start();
        // Output the process's stream to the console
        outputProcessStream(buildProcess);

        int buildExitCode = buildProcess.waitFor();

        if (buildExitCode == 0) {
            System.out.println("Docker image built successfully.");
        } else {
            System.err.println("Docker build failed.");
            System.exit(buildExitCode);
        }
    }

    private void executeDockerRun(String imageName, String port) throws Exception {
        ProcessBuilder runProcessBuilder = new ProcessBuilder(
                "docker", "run", "-d", "-p", String.format("%s:8080",port), Objects.requireNonNullElse(imageName, DEFAULT_IMAGE_NAME)
        );
        runProcessBuilder.redirectErrorStream(true);
        Process runProcess = runProcessBuilder.start();
        outputProcessStream(runProcess);
        // Output the process's stream to the console        outputProcessStream(runProcess);
        System.out.println("Docker container is starting on port 8080...");
    }
    private void outputProcessStream(Process process) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }

    private void printHelp() {
        System.out.println("Usage: mocker run [pathOfFile] [options]");
        System.out.println("The 'run' command runs docker commands for starting the generated project.");
        System.out.println();
        System.out.println("Arguments:");
        System.out.println("\tprojectPath\tPath to the project to be processed. Required if not using --help.");
        System.out.println();
        System.out.println("Options:");
        System.out.println("\t--help\t\tDisplays help information about the 'run' command.");
        System.out.println("\t--image <image>\tSpecifies the name of the image for docker build command. If not specified, by default image = mocker");
        System.out.println("\t--port <port>\tSpecifies the number of the port for docker build command. If not specified, by default port = 8080");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("\tmocker run /path/to/project --image my-image --port 8081");
        System.out.println("\tmocker run --help");
    }
}



