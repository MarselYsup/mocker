package ru.itis.mockeride.wrappers;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.uiDesigner.core.AbstractLayout;
import com.intellij.util.ui.GridBag;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;


public class RunDialogWrapper extends DialogWrapper {
    private static final String DEFAULT_IMAGE_NAME = "mocker";
    private static final String DEFAULT_PORT_NUMBER = "8080";



    private final JPanel panel = new JPanel(new GridBagLayout());
    private final JTextField pathOfTheProjectField = new JTextField();
    private final JTextField imageNameField = new JTextField();
    private final JTextField portField = new JTextField();


    private Project project;

    public RunDialogWrapper(Project project) {
        super(true);
        init();
        setTitle("Run Command");
        this.project = project;
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        GridBag gb = new GridBag()
                .setDefaultInsets(JBUI.insets(0, 0, AbstractLayout.DEFAULT_VGAP, AbstractLayout.DEFAULT_HGAP))
                .setDefaultWeightX(1.0)
                .setDefaultFill(GridBagConstraints.HORIZONTAL);

        panel.setPreferredSize(new Dimension(400, 300));
        panel.add(label("Path of the project"), gb.nextLine().next().weightx(0.2));
        panel.add(pathOfTheProjectField, gb.nextLine().next().weightx(0.8));
        panel.add(label("Name of the Image"), gb.nextLine().next().weightx(0.2));
        panel.add(imageNameField, gb.nextLine().next().weightx(0.8));
        panel.add(label("Port"), gb.nextLine().next().weightx(0.2));
        panel.add(portField, gb.nextLine().next().weightx(0.8));
        return panel;
    }

    @Override
    protected void doOKAction() {
        String pathOfTheProject = pathOfTheProjectField.getText();
        System.out.println(pathOfTheProject);
        if(pathOfTheProject.isEmpty() || pathOfTheProject.isBlank()){
            JOptionPane.showMessageDialog(panel, "Error: Please enter the path of the project!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String imageName = imageNameField.getText();
        if(imageName.isEmpty() || imageName.isBlank()){ imageName = DEFAULT_IMAGE_NAME; }

        String portNumber = portField.getText();
        if(portNumber.isEmpty() || portNumber.isBlank()){ portNumber = DEFAULT_PORT_NUMBER; }

        try {
            executeDockerBuild(pathOfTheProject, imageName);
            executeDockerRun(imageName, portNumber);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel, "Error: Please enter the path of the project!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Messages.showMessageDialog(project, "Process finished", "Information", Messages.getInformationIcon());
        super.doOKAction();
    }

    private JComponent label(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UIUtil.getLabelFont().deriveFont(Font.BOLD));
        label.setBorder(JBUI.Borders.empty(0, 5, 2, 0));
        return label;
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
                "docker", "run", "-d", "-p", String.format("%s:8080",Objects.requireNonNullElse(port, DEFAULT_PORT_NUMBER)), Objects.requireNonNullElse(imageName, DEFAULT_IMAGE_NAME)
        );
        runProcessBuilder.redirectErrorStream(true);
        Process runProcess = runProcessBuilder.start();
        outputProcessStream(runProcess);
        // Output the process's stream to the console        outputProcessStream(runProcess);
        System.out.println("Docker container is starting on port " + Objects.requireNonNullElse(port, DEFAULT_PORT_NUMBER) + "...");
    }
    private void outputProcessStream(Process process) throws Exception {
            ProgressManager.getInstance().run(new Task.Backgroundable(project, "Loading", true) {
                public void run(ProgressIndicator progressIndicator) {
                    // Set the progress to be indeterminate
                    progressIndicator.setFraction(0.0);

                    // Begin long-running operation here.
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while (true) {
                        try {
                            if (!((line = reader.readLine()) != null)) break;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(line);
                        progressIndicator.setText(line);
                    }
                    try {
                        reader.close();
                        progressIndicator.setFraction(1.0);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

        });
    }


}
