package de.uni_kassel.vs.cn.planDesigner.view.editor.container;

import de.uni_kassel.vs.cn.planDesigner.alica.State;
import de.uni_kassel.vs.cn.planDesigner.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;

public class TerminalStateContainer extends StateContainer {

    public TerminalStateContainer(PmlUiExtension pmlUiExtension, State state, CommandStack commandStack) {
        super(pmlUiExtension, state, commandStack);
    }


}
