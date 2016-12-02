package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.Command;
import de.uni_kassel.vs.cn.planDesigner.alica.Condition;

/**
 * Created by marci on 02.12.16.
 */
public class AddConditionToPlan extends Command<Condition> {


    public AddConditionToPlan(Condition element) {
        super(element);
    }

    @Override
    public void doCommand() {

    }

    @Override
    public void undoCommand() {

    }

    @Override
    public String getCommandString() {
        return null;
    }
}
