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
    private String behaviourName = "testfxBehaviour";
    private String behaviourNameExtension = behaviourName + "." + Extensions.BEHAVIOUR;

    @Override
    public void start(Stage stage) throws Exception {
        PlanDesigner.init();

        planDesignerApplication = new PlanDesignerApplication();
        planDesignerApplication.start(stage);
    }

    @Test
    public void testCreatePlan() {
        createPlan();
        createBehaviour();
        openPlan();
        placeEntryPoint();
        placeState();
        placeSuccessState();
        initTransitionFromEntryPointToState();
        transitionFromStateToSuccessState();
        placeBehaviour();
        deletePlan();
        deleteBehaviour();
        sleep(10000);
    }

    private void placeBehaviour() {
        clickOn("#behavioursTab");
        type(KeyCode.TAB);
        for (int i = 0; i < 5; i++) {
            type(KeyCode.PAGE_DOWN);
        }
        drag(behaviourName).dropTo("#StateContainer");
    }

    private void initTransitionFromEntryPointToState() {
        clickOn("#InitTransitionToolButton");
        clickOn("#EntryPointContainer");
        clickOn("#StateContainer");
        dropElement();
    }

    private void transitionFromStateToSuccessState() {
        clickOn("#TransitionToolButton");
        clickOn("#StateContainer");
        clickOn("#SuccessStateContainer");
        dropElement();
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
        return offset("#PlanTabPlanContent", 200, 0);
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
        rightClickOn("plans");
        clickOn("New");
        clickOn("Plan");
        clickOn("#nameTextField");
        write(planName);
        clickOn("#createButton");
    }

    private void createBehaviour() {
        rightClickOn("plans");
        clickOn("New");
        moveTo("Plan");  // avoid closing menu if mouse is outside of menu dialog
        clickOn("Behaviour");
        clickOn("#nameTextField");
        write(behaviourName);
        clickOn("#createButton");
    }

    private void openPlan() {
        openPlansView();
        doubleClickOn(planNameExtension);
    }

    private void deletePlan() {
        openPlansView();
        clickOn(planNameExtension);
        type(KeyCode.DELETE);
    }

    private void deleteBehaviour() {
        openPlansView();
        clickOn(behaviourNameExtension);
        type(KeyCode.DELETE);
    }

    private void openPlansView() {
        clickOn("#fileTreeView");
        type(KeyCode.PAGE_UP);
        type(KeyCode.RIGHT);
    }
}
