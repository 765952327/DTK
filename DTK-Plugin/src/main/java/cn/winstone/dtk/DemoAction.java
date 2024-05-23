package cn.winstone.dtk;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.apache.tools.ant.taskdefs.email.Message;

public class DemoAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Messages.showMessageDialog("Hello World!", "Demo Action", Messages.getInformationIcon());
    }
}
