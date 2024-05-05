package ru.itis.mockeride.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import ru.itis.mockeride.wrappers.GenerateDialogWrapper;
import ru.itis.mockeride.wrappers.ValidateDialogWrapper;

public class ValidateAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        ValidateDialogWrapper validateDialogWrapper = new ValidateDialogWrapper();
        if (validateDialogWrapper.showAndGet()) {
            validateDialogWrapper.close(0);
        }
    }
}
