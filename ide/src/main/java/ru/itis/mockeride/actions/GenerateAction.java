package ru.itis.mockeride.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import ru.itis.mockeride.wrappers.GenerateDialogWrapper;

public class GenerateAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        GenerateDialogWrapper generateDialogWrapper = new GenerateDialogWrapper(e.getProject().getBasePath());
        if (generateDialogWrapper.showAndGet()) {
            generateDialogWrapper.close(0);
        }
    }
}
