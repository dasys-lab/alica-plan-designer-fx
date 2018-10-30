
import de.uni_kassel.vs.cn.planDesigner.PlanDesignerApplication;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.configuration.ConfigurationManager;
import de.uni_kassel.vs.cn.planDesigner.controller.MainWindowController;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.view.filebrowser.FileTreeView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;
import java.io.IOException;

public class SaveTest extends ApplicationTest {

    private ModelManager modelManager;

    //@Before
    public void init() {
        ConfigurationManager.getInstance();
        modelManager = new ModelManager();
        PlanDesignerApplication.setRunning(true);
    }

    //@Test
    public void testSaveBug() {
        // lege Plan mit einem State an
        Plan plan = new Plan();
        plan.setName("Test1");

        String pathname = ConfigurationManager.getInstance().getActiveConfiguration().getPlansPath() + "/Test1.pml";
        File planFile = new File(pathname);

        // Dateien erzeugen
//        try {
            //TODO: load file
//            EMFModelUtils.createAlicaFile(plan, true, planFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//            Assert.fail();
//        }

        // Speichern
        modelManager.getPlans().add(plan);
        FileTreeView fileTreeView = MainWindowController.getInstance().getFileTreeView();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TreeItem<File> fileWrapperTreeItem = traverseFor(fileTreeView.getRoot().getChildren().get(0), planFile);

        Assert.assertTrue(fileWrapperTreeItem != null);
//        Platform.runLater(() -> {
//            MainWindowController.getInstance().getEditorTabPane().openTab(fileWrapperTreeItem.getValue().unwrap().toPath());
//            PlanTab selectedItem = (PlanTab) MainWindowController.getInstance().getEditorTabPane().getTabs().get(0);
//            State state = new State();
//            state.setKey("State1");
//            AddStateInPlan addStateInPlan = new AddStateInPlan(selectedItem.getPlanModelVisualisationObject(), state);
//            MainWindowController.getInstance().getCommandStack().storeAndExecute(addStateInPlan);
//            selectedItem.save();
//            MainWindowController.getInstance().getCommandStack().storeAndExecute(new DeleteAbstractPlan(selectedItem.getEditable()));
//        });

        PlanDesignerApplication.setRunning(false);
    }


    //@Test
    public void testSaveBug2() {
        // lege Plan mit einem State an
        Plan plan = new Plan();
        plan.setName("Test2");

        String pathname = ConfigurationManager.getInstance().getActiveConfiguration().getPlansPath() + "/Test2.pml";
        File planFile = new File(pathname);

        // Dateien erzeugen
//        try {
            //TODO: load file
//            EMFModelUtils.createAlicaFile(plan, true, planFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//            Assert.fail();
//        }

        // Speichern
        modelManager.getPlans().add(plan);
        FileTreeView fileTreeView = MainWindowController.getInstance().getFileTreeView();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TreeItem<File> fileWrapperTreeItem = traverseFor(fileTreeView.getRoot().getChildren().get(0), planFile);

        Assert.assertTrue(fileWrapperTreeItem != null);
//        Platform.runLater(() -> {
//            MainWindowController.getInstance().getEditorTabPane().openTab(fileWrapperTreeItem.getValue().unwrap().toPath());
//            PlanTab selectedItem = (PlanTab) MainWindowController.getInstance().getEditorTabPane().getTabs().get(0);
//            State state = new State();
//            state.setKey("State2");
//            AddStateInPlan addStateInPlan = new AddStateInPlan(selectedItem.getPlanModelVisualisationObject(), state);
//            MainWindowController.getInstance().getCommandStack().storeAndExecute(addStateInPlan);
//            selectedItem.save();
//            MainWindowController.getInstance().getCommandStack().storeAndExecute(new DeleteAbstractPlan(selectedItem.getEditable()));
//        });

        PlanDesignerApplication.setRunning(false);
    }

    private TreeItem<File> traverseFor(TreeItem<File> root, File planFile) {
        if (root.getValue().equals(planFile)) {
            return root;
        } else {
            for (TreeItem<File> e : root.getChildren()) {
                TreeItem<File> fileWrapperTreeItem = traverseFor(e, planFile);
                if (fileWrapperTreeItem != null) {
                    return fileWrapperTreeItem;
                }
            }
        }

        return null;
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("mainWindow.fxml"));
            Parent root = fxmlLoader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
