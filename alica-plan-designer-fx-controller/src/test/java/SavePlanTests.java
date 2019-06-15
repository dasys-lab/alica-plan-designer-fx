import de.unikassel.vs.alica.planDesigner.PlanDesigner;
import de.unikassel.vs.alica.planDesigner.PlanDesignerApplication;
import de.unikassel.vs.alica.planDesigner.alicamodel.*;
import de.unikassel.vs.alica.planDesigner.configuration.Configuration;
import de.unikassel.vs.alica.planDesigner.configuration.ConfigurationManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Extensions;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.service.query.PointQuery;

import java.util.ArrayList;
import java.util.List;

public class SavePlanTests extends ApplicationTest {
    private String taskRepository = "ServiceRobotsTasks";
    private String taskRepositoryExtension = taskRepository + "." + Extensions.TASKREPOSITORY;
    private String taskName = "testfxTask";
    private String planName = "testfxPlan";
    private String planNameExtension = planName + "." + Extensions.PLAN;
    private String behaviourName = "testfxBehaviour";
    private String roleSetName = "testfxRoleSet";
    private String roleSetNameExtension = roleSetName + "." + Extensions.ROLESET;
    private String roleName1 = "testfxRole1";
    private String roleName2 = "testfxRole2";
    private String behaviourNameExtension = behaviourName + "." + Extensions.BEHAVIOUR;

    @Override
    public void start(Stage stage) throws Exception {
        PlanDesigner.init();

        PlanDesignerApplication planDesignerApplication = new PlanDesignerApplication();
        planDesignerApplication.start(stage);
    }

    @Test
    public void testCreatePlan() {
        // init
        createPlan();
        createBehaviour();
        createRoleSet();

        // modify roleset and roles
        openRoleSet();
        createRoles();
        saveCurrentData();

        // modify plan
        openPlan();
        placeEntryPoint();
        placeState();
        placeSuccessState();
        initTransitionFromEntryPointToState();
        transitionFromStateToSuccessState();
        placeBehaviour();
        saveCurrentData();
        saveTask();

        // check saved data
        checkConfig();

        // clean
        deletePlan();
        deleteBehaviour();
        deleteRoleSet();
    }

    private void placeBehaviour() {
        clickOn("#behavioursTab");
        type(KeyCode.TAB);
        for (int i = 0; i < 5; i++) {
            type(KeyCode.PAGE_DOWN);
        }
        drag(behaviourName).dropTo("#StateContainer");
    }

    private void saveCurrentData() {
        press(KeyCode.CONTROL, KeyCode.S);
        release(KeyCode.CONTROL, KeyCode.S);
    }

    private void saveTask() {
        clickOn("#fileTreeView");
        type(KeyCode.PAGE_DOWN);
        type(KeyCode.RIGHT);
        doubleClickOn(taskRepositoryExtension);
        saveCurrentData();
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

    private void placeState() {
        clickOn("#StateToolButton");
        clickOn(freePlanContentPos());
        dropElement();
    }

    private void placeSuccessState() {
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

    private void createRoleSet() {
        rightClickOn("roles");
        clickOn("New");
        moveTo("Plan");  // avoid closing menu if mouse is outside of menu dialog
        clickOn("RoleSet");
        clickOn("#nameTextField");
        write(roleSetName);
        clickOn("#createButton");
    }

    private void openRoleSet() {
        openRolesView();
        doubleClickOn(roleSetNameExtension);
    }

    private void createRoles() {
        Node firstListElement = lookup("#RoleTableView").lookup("").selectAt(1).queryFirst();

        // create first role
        clickOn(firstListElement);
        write(roleName1);
        type(KeyCode.ENTER);

        // create second role
        type(KeyCode.ENTER);
        write(roleName2);
        type(KeyCode.ENTER);
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

    private void deleteRoleSet() {
        openRolesView();
        clickOn(roleSetNameExtension);
        type(KeyCode.DELETE);
    }

    private void openPlansView() {
        clickOn("#fileTreeView");
        type(KeyCode.PAGE_UP);
        type(KeyCode.RIGHT);
    }

    private void openRolesView() {
        clickOn("#fileTreeView");
        type(KeyCode.PAGE_UP);
        type(KeyCode.DOWN);
        type(KeyCode.RIGHT);
    }

     private void checkConfig() {
         ModelManager modelManager = new ModelManager();
         Configuration activeConfiguration = ConfigurationManager.getInstance().getActiveConfiguration();
         String plansPath = activeConfiguration.getPlansPath();
         String tasksPath = activeConfiguration.getTasksPath();
         String rolesPath = activeConfiguration.getRolesPath();
         modelManager.setPlansPath(plansPath);
         modelManager.setTasksPath(tasksPath);
         modelManager.setRolesPath(rolesPath);
         modelManager.loadModelFromDisk();

         // get root elements
         ArrayList<Plan> plans = modelManager.getPlans();
         Plan plan = plans.stream().filter(p -> p.getName().equals(planName)).findFirst().get();

         List<EntryPoint> entryPoints = plan.getEntryPoints();
         EntryPoint entryPoint = entryPoints.get(0);

         List<State> states = plan.getStates();
         State terminalState = states.stream().filter(s -> s instanceof TerminalState).findFirst().get();
         State state = states.stream().filter(s -> !s.equals(terminalState)).findFirst().get();

         List<Transition> transitions = plan.getTransitions();
         Transition transition = transitions.get(0);

         ArrayList<Behaviour> behaviours = modelManager.getBehaviours();
         Behaviour behaviour = behaviours.stream().filter(b -> b.getName().equals(behaviourName)).findFirst().get();

         List<de.unikassel.vs.alica.planDesigner.alicamodel.Configuration> configurations = behaviour.getConfigurations();
         de.unikassel.vs.alica.planDesigner.alicamodel.Configuration configuration = configurations.get(0);

         List<Task> tasks = modelManager.getTasks();
         Task task = tasks.stream().filter(t -> t.getName().equals(taskName)).findFirst().get();

         // test plan
         Assert.assertNotNull(plan.getId());
         Assert.assertEquals(plan.getName(), planName);
         Assert.assertEquals(plan.getComment(), "");
         Assert.assertEquals(plan.getRelativeDirectory(), "");
         Assert.assertEquals(plan.getVariables().size(), 0);
         Assert.assertFalse(plan.getMasterPlan());
         Assert.assertEquals(plan.getUtilityThreshold(), 0.0, 0.0);
         Assert.assertNull(plan.getPreCondition());
         Assert.assertNull(plan.getRuntimeCondition());

         // test entrypoint
         Assert.assertEquals(entryPoints.size(), 1);
         Assert.assertNotNull(entryPoint.getId());
         Assert.assertEquals(entryPoint.getName(), String.valueOf(entryPoint.getId()));
         Assert.assertEquals(entryPoint.getComment(), "");
         Assert.assertFalse(entryPoint.getSuccessRequired());
         Assert.assertEquals(entryPoint.getMinCardinality(), 0);
         Assert.assertEquals(entryPoint.getMaxCardinality(), 0);
         Assert.assertEquals(entryPoint.getTask().getId(), task.getId());
         Assert.assertEquals(entryPoint.getState().getId(), state.getId());
         Assert.assertEquals(entryPoint.getPlan().getId(), plan.getId());

         // test state
         // missing: type
         Assert.assertNotNull(state.getId());
         Assert.assertEquals(state.getName(), "Default Name");
         Assert.assertEquals(state.getComment(), "");
         Assert.assertEquals(state.getEntryPoint().getId(), entryPoint.getId());
         Assert.assertEquals(state.getParentPlan().getId(), plan.getId());
         Assert.assertEquals(state.getAbstractPlans().size(), 1);
         Assert.assertEquals(state.getAbstractPlans().get(0).getId(), configuration.getId());
         Assert.assertEquals(state.getVariableBindings().size(), 0);
         Assert.assertEquals(state.getOutTransitions().get(0).getId(), transition.getId());
         Assert.assertEquals(state.getInTransitions().size(), 0);

         // test terminalState
         // missing: type
         Assert.assertNotNull(terminalState.getId());
         Assert.assertEquals(terminalState.getName(), "Default Name");
         Assert.assertEquals(terminalState.getComment(), "");
         Assert.assertNull(terminalState.getEntryPoint());
         Assert.assertEquals(terminalState.getParentPlan().getId(), plan.getId());
         Assert.assertEquals(terminalState.getAbstractPlans().size(), 0);
         Assert.assertEquals(terminalState.getVariableBindings().size(), 0);
         Assert.assertEquals(terminalState.getOutTransitions().size(), 0);
         Assert.assertEquals(terminalState.getInTransitions().get(0).getId(), transition.getId());
         // missing: success
         // missing: postCondition

         // test transition
         Assert.assertNotNull(transition.getId());
         Assert.assertEquals(transition.getName(), "FromDefault NameToDefault Name");
         Assert.assertEquals(transition.getComment(), "MISSING_COMMENT");
         Assert.assertEquals(transition.getInState().getId(), state.getId());
         Assert.assertEquals(transition.getOutState().getId(), terminalState.getId());
         Assert.assertNull(transition.getPreCondition());
         Assert.assertNull(transition.getSynchronisation());

         // test synchronisations
         Assert.assertEquals(plan.getSynchronisations().size(), 0);

         // test behaviour
         Assert.assertNotNull(behaviour.getId());
         Assert.assertEquals(behaviour.getName(), behaviourName);
         Assert.assertEquals(behaviour.getComment(), "");
         Assert.assertEquals(behaviour.getRelativeDirectory(), "");
         Assert.assertEquals(behaviour.getVariables().size(), 0);
         Assert.assertEquals(behaviour.getFrequency(), 0);
         Assert.assertEquals(behaviour.getDeferring(), 0);
         Assert.assertNull(behaviour.getPreCondition());
         Assert.assertNull(behaviour.getRuntimeCondition());
         Assert.assertNull(behaviour.getPostCondition());

         Assert.assertNotNull(configuration.getId());
         Assert.assertEquals(configuration.getName(), "default");
         Assert.assertEquals(configuration.getComment(), "");
         Assert.assertEquals(configuration.getRelativeDirectory(), "");
         Assert.assertEquals(configuration.getVariables().size(), 0);
         Assert.assertEquals(configuration.getBehaviour().getId(), behaviour.getId());
         Assert.assertEquals(configuration.getKeyValuePairs().size(), 0);
     }
}
