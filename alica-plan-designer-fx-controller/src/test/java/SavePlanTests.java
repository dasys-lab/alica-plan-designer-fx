import de.unikassel.vs.alica.planDesigner.PlanDesigner;
import de.unikassel.vs.alica.planDesigner.PlanDesignerApplication;
import de.unikassel.vs.alica.planDesigner.configuration.ConfigurationManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Extensions;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.service.query.PointQuery;

public class SavePlanTests extends ApplicationTest {
    private PlanDesignerApplication planDesignerApplication;
    private String planName = "testfxPlan";
    private String planNameExtension = planName + "." + Extensions.PLAN;

    @Override
    public void start(Stage stage) throws Exception {
        PlanDesigner.init();

        planDesignerApplication = new PlanDesignerApplication();
        planDesignerApplication.start(stage);
    }

    @Test
    public void testCreatePlan() {
        createPlan();
        openPlan();
        placeEntryPoint();
        placeState();
        placeSuccessState();
        deletePlan();
    }

    private void placeEntryPoint() {
        // place entryPoint
        clickOn("#EntryPointToolButton");
        clickOn(freePlanContentPos());
        // create task
        clickOn("#newTaskNameTextField");
        String taskName = "testfxTask";
        write(taskName);
        clickOn("#createTaskButton");
        // choose created task
        clickOn("#taskComboBox");
        Node taskComboBoxTaskName = lookup(taskName).selectAt(1).queryFirst();  // ignore the taskName in the textField
        clickOn(taskComboBoxTaskName);
        clickOn("#confirmTaskChoiceButton");
        dropElement();
    }

    private PointQuery freePlanContentPos() {
        return offset("#PlanTabPlanContent", 100, 0);
    }

    public void placeState() {
        clickOn("#StateToolButton");
        clickOn(freePlanContentPos());
        dropElement();
    }

    public void placeSuccessState() {
        clickOn("#SuccessStateToolButton");
        clickOn(freePlanContentPos());
        dropElement();
    }

    private void dropElement() {
        type(KeyCode.ESCAPE);
    }

    private void createPlan() {
        // open create plan dialog
        clickOn("#fileMenu");
        clickOn("#newMenuItem");
        clickOn("#newPlanMenuItem");

        // enter plan path and name
        String plansPath = ConfigurationManager.getInstance().getActiveConfiguration().getPlansPath();
        clickOn("#pathTextField");
        write(plansPath);
        clickOn("#nameTextField");
        write(planName);
        clickOn("#createButton");
    }

    private void openPlan() {
        openPlansView();
        doubleClickOn(planNameExtension);
    }

    private void deletePlan() {
        openPlansView();
        rightClickOn(planNameExtension);
        for (int i = 0; i < 3; i++) {
            type(KeyCode.DOWN);
        }
        type(KeyCode.ENTER);
    }

    private void openPlansView() {
        clickOn("#fileTreeView");
        type(KeyCode.PAGE_UP);
        type(KeyCode.RIGHT);
    }
}
