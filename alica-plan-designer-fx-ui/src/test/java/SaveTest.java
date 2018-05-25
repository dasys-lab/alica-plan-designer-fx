import de.uni_kassel.vs.cn.planDesigner.PlanDesigner;
import de.uni_kassel.vs.cn.planDesigner.command.add.AddStateInPlan;
import de.uni_kassel.vs.cn.planDesigner.command.delete.DeleteAbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.State;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.ConfigurationManager;
import de.uni_kassel.vs.cn.planDesigner.alica.util.RepoViewBackend;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.common.FileWrapper;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.PlanTab;
import de.uni_kassel.vs.cn.planDesigner.ui.filebrowser.PLDFileTreeView;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;
import java.io.IOException;

import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getAlicaFactory;

public class SaveTest extends ApplicationTest {

    private RepoViewBackend testInstance;

    @Before
    public void init() {
        ConfigurationManager.getInstance().init();
        EMFModelUtils.initializeEMF();
        testInstance = RepoViewBackend.getTestInstance();
        PlanDesigner.setRunning(true);
    }

    @Test
    public void testSaveBug() {
        // lege Plan mit einem State an
        Plan plan = getAlicaFactory().createPlan();
        plan.setName("Test1");

        String pathname = ConfigurationManager.getInstance().getActiveWorkspace().getConfiguration().getPlansPath() + "/Test1.pml";
        File planFile = new File(pathname);

        // Dateien erzeugen
        try {
            EMFModelUtils.createAlicaFile(plan, true, planFile);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }

        // Speichern
        testInstance.getPlans().add(new Pair<>(plan, planFile.toPath()));
        PLDFileTreeView pldFileTreeView = MainController.getInstance().getFileTreeView();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TreeItem<FileWrapper> fileWrapperTreeItem = traverseFor(pldFileTreeView.getRoot().getChildren().get(0), planFile);

        Assert.assertTrue(fileWrapperTreeItem != null);
        Platform.runLater(() -> {
            MainController.getInstance().getEditorTabPane().openTab(fileWrapperTreeItem.getValue().unwrap().toPath());
            PlanTab selectedItem = (PlanTab) MainController.getInstance().getEditorTabPane().getTabs().get(0);
            State state = getAlicaFactory().createState();
            state.setName("State1");
            AddStateInPlan addStateInPlan = new AddStateInPlan(selectedItem.getPlanModelVisualisationObject(), state);
            MainController.getInstance().getCommandStack().storeAndExecute(addStateInPlan);
            selectedItem.save();
            MainController.getInstance().getCommandStack().storeAndExecute(new DeleteAbstractPlan(selectedItem.getEditable()));
        });

        PlanDesigner.setRunning(false);
    }


    @Test
    public void testSaveBug2() {
        // lege Plan mit einem State an
        Plan plan = getAlicaFactory().createPlan();
        plan.setName("Test2");

        String pathname = ConfigurationManager.getInstance().getActiveWorkspace().getConfiguration().getPlansPath() + "/Test2.pml";
        File planFile = new File(pathname);

        // Dateien erzeugen
        try {
            EMFModelUtils.createAlicaFile(plan, true, planFile);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }

        // Speichern
        testInstance.getPlans().add(new Pair<>(plan, planFile.toPath()));
        PLDFileTreeView pldFileTreeView = MainController.getInstance().getFileTreeView();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TreeItem<FileWrapper> fileWrapperTreeItem = traverseFor(pldFileTreeView.getRoot().getChildren().get(0), planFile);

        Assert.assertTrue(fileWrapperTreeItem != null);
        Platform.runLater(() -> {
            MainController.getInstance().getEditorTabPane().openTab(fileWrapperTreeItem.getValue().unwrap().toPath());
            PlanTab selectedItem = (PlanTab) MainController.getInstance().getEditorTabPane().getTabs().get(0);
            State state = getAlicaFactory().createState();
            state.setName("State2");
            AddStateInPlan addStateInPlan = new AddStateInPlan(selectedItem.getPlanModelVisualisationObject(), state);
            MainController.getInstance().getCommandStack().storeAndExecute(addStateInPlan);
            selectedItem.save();
            MainController.getInstance().getCommandStack().storeAndExecute(new DeleteAbstractPlan(selectedItem.getEditable()));

        });

        PlanDesigner.setRunning(false);
    }

    private TreeItem<FileWrapper> traverseFor(TreeItem<FileWrapper> root, File planFile) {
        if (root.getValue().unwrap().equals(planFile)) {
            return root;
        } else {
            for (TreeItem<FileWrapper> e : root.getChildren()) {
                TreeItem<FileWrapper> fileWrapperTreeItem = traverseFor(e, planFile);
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("planDesigner.fxml"));
            Parent root = fxmlLoader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
