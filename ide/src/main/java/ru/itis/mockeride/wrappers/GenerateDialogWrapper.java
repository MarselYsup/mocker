package ru.itis.mockeride.wrappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.uiDesigner.core.AbstractLayout;
import com.intellij.util.ui.GridBag;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.Nullable;
import ru.itis.mocker.core.generators.Generator;
import ru.itis.mocker.core.generators.GeneratorFactory;
import ru.itis.mocker.core.generators.impl.*;
import ru.itis.mocker.core.models.MockerModel;
import ru.itis.mocker.core.utils.FileUtils;
import ru.itis.mocker.core.utils.ValidateUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Set;


public class GenerateDialogWrapper extends DialogWrapper {
    private static final String DEFAULT_PATH_SUFFICE = "./mocker/";
    private static final Set<Generator> SET_OF_GENERATORS = Set.of(
            ClassGenerator.getInstance(), ApplicationGenerator.getInstance(),
            ControllerGenerator.getInstance(), DockerMavenGenerator.getInstance(),
            MavenGenerator.getInstance(), ReadmeGenerator.getInstance()
    );

    private final JPanel panel = new JPanel(new GridBagLayout());
    private final JTextField pathOfFileField = new JTextField();
    private final JTextField generatedFileField = new JTextField();

    private String defaultGeneratedPath;

    public GenerateDialogWrapper(String defaultPath) {
        super(true);
        init();
        setTitle("Generate Command");
        defaultGeneratedPath = defaultPath + DEFAULT_PATH_SUFFICE;
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        GridBag gb = new GridBag()
                .setDefaultInsets(JBUI.insets(0, 0, AbstractLayout.DEFAULT_VGAP, AbstractLayout.DEFAULT_HGAP))
                .setDefaultWeightX(1.0)
                .setDefaultFill(GridBagConstraints.HORIZONTAL);

        panel.setPreferredSize(new Dimension(400, 200));
        panel.add(label("path of file"), gb.nextLine().next().weightx(0.2));
        panel.add(pathOfFileField, gb.nextLine().next().weightx(0.8));
        panel.add(label("generated file"), gb.nextLine().next().weightx(0.2));
        panel.add(generatedFileField, gb.nextLine().next().weightx(0.8));
        return panel;
    }

    @Override
    protected void doOKAction() {
        String pathOfFile = pathOfFileField.getText();
        if(pathOfFile.isEmpty() || pathOfFile.isBlank()){
            JOptionPane.showMessageDialog(panel, "Error: Please enter the path of the file!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String generatedPath = generatedFileField.getText();
        if (generatedPath.isEmpty()) {
            generatedPath = defaultGeneratedPath;
        }
        String json = null;
        try {
            json = FileUtils.readContentFromFile(pathOfFile);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(panel,"Error with reading file!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        MockerModel mc;

        try {
            mc = objectMapper.readValue(json, MockerModel.class);
        } catch (JsonProcessingException e) {
            JOptionPane.showMessageDialog(panel,"Error parsing JSON!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!ValidateUtils.validate(mc)) {
            return;
        }

        GeneratorFactory generatorFactory;

        generatorFactory = new GeneratorFactory(SET_OF_GENERATORS, generatedPath);

        generatorFactory.generate(mc);

        JOptionPane.showMessageDialog(panel, "Success: Mocker generated project in " + generatedPath, "Success",
                JOptionPane.INFORMATION_MESSAGE);
        super.doOKAction();
    }

    private JComponent label(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UIUtil.getLabelFont().deriveFont(Font.BOLD));
        label.setBorder(JBUI.Borders.empty(0, 5, 2, 0));
        return label;
    }
}
