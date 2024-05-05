package ru.itis.mockeride.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class HelpAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Messages.showInfoMessage("Commands:                           Description\n" +
                "-------------------------------------------------------\n\n" +
                "generate  - Processes the specified file and generates a project with mocks.\n\n" +
                "run  - Runs Docker commands for starting the generated project.\n\n" +
                "validate  - Checks the specified file for correctness.\n\n",
                "Help Command"
        );
    }
}
