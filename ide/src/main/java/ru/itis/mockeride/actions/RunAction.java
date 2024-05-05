package ru.itis.mockeride.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import ru.itis.mockeride.wrappers.RunDialogWrapper;

public class RunAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        RunDialogWrapper runDialogWrapper = new RunDialogWrapper(e.getProject());
        if (runDialogWrapper.showAndGet()) {
            runDialogWrapper.close(0);
        }
    }
}
