package de.uni_kassel.vs.cn.planDesigner.ui.filebrowser;

import de.uni_kassel.vs.cn.planDesigner.PlanDesigner;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.controller.CreatNewDialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.eclipse.emf.ecore.EClass;

import java.io.File;
import java.io.IOException;

import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 08.03.17.
 */
public class PLDFileTreeViewContextMenu extends ContextMenu {

    private File hintFile;

    public PLDFileTreeViewContextMenu() {
        getItems().add(createNewMenu());
    }

    private Menu createNewMenu() {
        Menu root = new Menu(I18NRepo.getString("label.menu.new"));
        MenuItem newPlanMenuItem = new MenuItem(I18NRepo.getString("label.menu.new.plan"));
        newPlanMenuItem.setOnAction(e -> onElementClick(getAlicaFactory().createPlan().eClass()));
        root.getItems().add(newPlanMenuItem);
        MenuItem newPlanTypeMenuItem = new MenuItem(I18NRepo.getString("label.menu.new.plantype"));
        newPlanTypeMenuItem.setOnAction(e -> onElementClick(getAlicaFactory().createPlanType().eClass()));
        root.getItems().add(newPlanTypeMenuItem);
        MenuItem newBehaviourMenuItem = new MenuItem(I18NRepo.getString("label.menu.new.behaviour"));
        newBehaviourMenuItem.setOnAction(e -> onElementClick(getAlicaFactory().createBehaviour().eClass()));
        root.getItems().add(newBehaviourMenuItem);
        MenuItem newFolderMenuItem = new MenuItem(I18NRepo.getString("label.menu.new.folder"));
        newFolderMenuItem.setOnAction(e -> onElementClick(null));
        root.getItems().add(newFolderMenuItem);
        return root;
    }

    private void onElementClick(EClass resourceInstanceClass) {
        createFileDialog(hintFile, resourceInstanceClass);
    }

    private void createFileDialog(File unwrappedFile, EClass planClass) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("createNewDialog.fxml"));
        try {
            Parent rootOfDialog = fxmlLoader.load();
            CreatNewDialogController controller = fxmlLoader.getController();
            controller.setAlicaType(planClass);
            controller.setInitialDirectoryHint(unwrappedFile);
            Stage stage = new Stage();
            stage.setResizable(false);
            if (planClass != null) {
                stage.setTitle(I18NRepo.getString("label.menu.new") + " " +
                        I18NRepo.getString("label.menu.new." + planClass.getName().toLowerCase()));
            } else {
                stage.setTitle(I18NRepo.getString("label.menu.new") + " " +
                        I18NRepo.getString("label.menu.new.folder"));
            }
            stage.setScene(new Scene(rootOfDialog));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(PlanDesigner.getPrimaryStage());
            stage.showAndWait();

        } catch (IOException e) {
            // if the helper window is not loadable something is really wrong here
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void setHintFile(File hintFile) {
        this.hintFile = hintFile;
    }
}
