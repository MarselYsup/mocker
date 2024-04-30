package ru.itis.mocker.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Mojo(name = "run")
public class RunPlugin extends AbstractMojo {
    @Parameter(property = "run.pathOfProject", required = true)
    private String pathOfProject;

    @Parameter(property = "run.imageName", defaultValue = "mocker")
    private String imageName;

    @Parameter(property = "run.port", defaultValue = "8080")
    private String port;

    private void executeDockerBuild(String projectPath, String imageName) throws Exception {
        ProcessBuilder buildProcessBuilder;
        buildProcessBuilder = new ProcessBuilder("docker", "build", ".", "-t", imageName);
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

    private void executeDockerRun(String imageName) throws Exception {
        ProcessBuilder runProcessBuilder = new ProcessBuilder(
                "docker", "run", "-d", "-p", String.format("%s:8080", port), imageName
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

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            executeDockerBuild(pathOfProject, imageName);
            executeDockerRun(imageName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
