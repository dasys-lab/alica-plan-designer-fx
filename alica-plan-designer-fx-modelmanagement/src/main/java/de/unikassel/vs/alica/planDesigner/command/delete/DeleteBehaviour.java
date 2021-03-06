package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DeleteBehaviour extends Command {

    protected Behaviour behaviour;

    private List<File> paths = new ArrayList<File>();
    private List<String> text = new ArrayList<String>();

    public DeleteBehaviour(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        behaviour = (Behaviour) modelManager.getPlanElement(mmq.getElementId());
    }

    @Override
    public void doCommand() {
        if (behaviour == null) {
            return;
        }

        //Get the generated Files
        paths = modelManager.getGeneratedFilesForAbstractPlan(behaviour);

        if(paths.size() != 0) {
            try {
                for (File path: paths) {
                    text.add(new String (Files.readAllBytes(Paths.get(path.toString()))));
                    path.delete();
                }
            } catch (Exception e) {
                e.getMessage();
            }
        } else {
            System.out.println("ERROR:" + behaviour.getName() + ".h and " + behaviour.getName() + ".cpp are not autogenerated!!!");
        }

        modelManager.dropPlanElement(Types.BEHAVIOUR, behaviour, true);
        this.fireEvent(ModelEventType.ELEMENT_DELETED, this.behaviour);
    }

    @Override
    public void undoCommand() {
        if (behaviour == null) {
            return;
        }
        //Write back the autogenerated files, if available
        try {
            if(text.size() != 0) {
                for (int i = 0; i < text.size(); i++) {
                    PrintWriter writerH = new PrintWriter(new FileWriter(paths.get(i), true));
                    writerH.print(text.get(i));
                    writerH.close();
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR: ");
        }

        modelManager.storePlanElement(Types.BEHAVIOUR, behaviour,true);
        this.fireEvent(ModelEventType.ELEMENT_CREATED, this.behaviour);
    }
}
