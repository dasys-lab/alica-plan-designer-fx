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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SavePlanTests extends ApplicationTest {
    private int randomNum = (int) (Math.random() * 1000);
    private String taskRepository = "ServiceRobotsTasks";
    private String taskName = "testfxTask";
    private String planName = "testfxPlan" + randomNum;
    private String behaviourName = "testfxBehaviour" + randomNum;
    private String roleSetName = "testfxRoleSet" + randomNum;
    private String roleName1 = "testfxRole1";
    private String roleName2 = "testfxRole2";
    private String taskRepositoryExtension = taskRepository + "." + Extensions.TASKREPOSITORY;
    private String planNameExtension = planName + "." + Extensions.PLAN;
    private String behaviourNameExtension = behaviourName + "." + Extensions.BEHAVIOUR;
    private String roleSetNameExtension = roleSetName + "." + Extensions.ROLESET;
    private String configDir = getConfigDir();

    @Override
    public void start(Stage stage) throws Exception {
        PlanDesigner.init();

        PlanDesignerApplication planDesignerApplication = new PlanDesignerApplication();
        planDesignerApplication.start(stage);
    }

    @Test
    public void testCreatePlan() {
        // init
        openPlansView();
        createPlan();
        createBehaviour();

        closeFileThreeElements();
        openRolesView();
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

    private String getConfigDir() {
        String root = ConfigurationManager.getInstance().getPlanDesignerConfigFolder().getPath();
        String configDir = root + "/testfx";
        return configDir;
    }

    private void createConfigDir() {
        deleteConfigDir();
        new File(configDir).mkdirs();
    }

    private void deleteConfigDir() {
        // deletes an old config dir to start clean
        Path path = Paths.get(configDir);
        try {
            Files.walk(path)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            System.out.println("Config dir can not be deleted");
        }
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

        assertExists(planNameExtension);
    }

    private void createBehaviour() {
        rightClickOn("plans");
        clickOn("New");
        moveTo("Plan");  // avoid closing menu if mouse is outside of menu dialog
        clickOn("Behaviour");
        clickOn("#nameTextField");
        write(behaviourName);
        clickOn("#createButton");

        assertExists(behaviourNameExtension);
    }

    private void createRoleSet() {
        rightClickOn("roles");
        clickOn("New");
        moveTo("Plan");  // avoid closing menu if mouse is outside of menu dialog
        clickOn("RoleSet");
        clickOn("#nameTextField");
        write(roleSetName);
        clickOn("#createButton");

        assertExists(roleSetNameExtension);
    }

    private void openRoleSet() {
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

        assertExists(roleName1);
        assertExists(roleName2);
    }

    private void openPlan() {
        openPlansView();
        doubleClickOn(planNameExtension);
    }

    private void deletePlan() {
        openPlansView();
        clickOn(planNameExtension);
        type(KeyCode.DELETE);

        assertNotExists(planNameExtension);
    }

    private void deleteBehaviour() {
        openPlansView();
        clickOn(behaviourNameExtension);
        type(KeyCode.DELETE);

        assertNotExists(behaviourNameExtension);
    }

    private void deleteRoleSet() {
        openRolesView();
        clickOn(roleSetNameExtension);
        type(KeyCode.DELETE);

        assertNotExists(roleSetNameExtension);
    }

    private void openPlansView() {
        clickOn("#fileTreeView");
        type(KeyCode.PAGE_UP);
        type(KeyCode.RIGHT);
    }

    private void closeFileThreeElements() {
        clickOn("#fileTreeView");
        type(KeyCode.PAGE_UP);
        for (int i = 0; i < 5; i++) {
            type(KeyCode.LEFT);
            type(KeyCode.DOWN);
        }
    }

    private void openRolesView() {
        clickOn("#fileTreeView");
        type(KeyCode.PAGE_UP);
        type(KeyCode.DOWN);
        type(KeyCode.RIGHT);
    }

    private void assertExists(String query) {
        Assert.assertEquals(1, lookup(query).queryAll().size());
    }

    private void assertNotExists(String query) {
        Assert.assertEquals(0, lookup(query).queryAll().size());
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
        Assert.assertEquals(planName, plan.getName());
        Assert.assertEquals("", plan.getComment());
        Assert.assertEquals("", plan.getRelativeDirectory());
        Assert.assertEquals(0, plan.getVariables().size());
        Assert.assertFalse(plan.getMasterPlan());
        Assert.assertEquals(0.0, plan.getUtilityThreshold(), 0.0);
        Assert.assertNull(plan.getPreCondition());
        Assert.assertNull(plan.getRuntimeCondition());

        // test entrypoint
        Assert.assertEquals(1, entryPoints.size());
        Assert.assertNotNull(entryPoint.getId());
        Assert.assertEquals(String.valueOf(entryPoint.getId()), entryPoint.getName());
        Assert.assertEquals("", entryPoint.getComment());
        Assert.assertFalse(entryPoint.getSuccessRequired());
        Assert.assertEquals(0, entryPoint.getMinCardinality());
        Assert.assertEquals(0, entryPoint.getMaxCardinality());
        Assert.assertEquals(task.getId(), entryPoint.getTask().getId());
        Assert.assertEquals(state.getId(), entryPoint.getState().getId());
        Assert.assertEquals(plan.getId(), entryPoint.getPlan().getId());

        // test state
        // missing: type
        Assert.assertNotNull(state.getId());
        Assert.assertEquals("Default Name", state.getName());
        Assert.assertEquals("", state.getComment());
        Assert.assertEquals(entryPoint.getId(), state.getEntryPoint().getId());
        Assert.assertEquals(plan.getId(), state.getParentPlan().getId());
        Assert.assertEquals(1, state.getAbstractPlans().size());
        Assert.assertEquals(configuration.getId(), state.getAbstractPlans().get(0).getId());
        Assert.assertEquals(0, state.getVariableBindings().size());
        Assert.assertEquals(transition.getId(), state.getOutTransitions().get(0).getId());
        Assert.assertEquals(0, state.getInTransitions().size());

        // test terminalState
        // missing: type
        Assert.assertNotNull(terminalState.getId());
        Assert.assertEquals("Default Name", terminalState.getName());
        Assert.assertEquals("", terminalState.getComment());
        Assert.assertNull(terminalState.getEntryPoint());
        Assert.assertEquals(plan.getId(), terminalState.getParentPlan().getId());
        Assert.assertEquals(0, terminalState.getAbstractPlans().size());
        Assert.assertEquals(0, terminalState.getVariableBindings().size());
        Assert.assertEquals(0, terminalState.getOutTransitions().size());
        Assert.assertEquals(transition.getId(), terminalState.getInTransitions().get(0).getId());
        // missing: success
        // missing: postCondition

        // test transition
        Assert.assertNotNull(transition.getId());
        Assert.assertEquals("FromDefault NameToDefault Name", transition.getName());
        Assert.assertEquals("MISSING_COMMENT", transition.getComment());
        Assert.assertEquals(state.getId(), transition.getInState().getId());
        Assert.assertEquals(terminalState.getId(), transition.getOutState().getId());
        Assert.assertNull(transition.getPreCondition());
        Assert.assertNull(transition.getSynchronisation());

        // test synchronisations
        Assert.assertEquals(0, plan.getSynchronisations().size());

        // test behaviour
        Assert.assertNotNull(behaviour.getId());
        Assert.assertEquals(behaviourName, behaviour.getName());
        Assert.assertEquals("", behaviour.getComment());
        Assert.assertEquals("", behaviour.getRelativeDirectory());
        Assert.assertEquals(0, behaviour.getVariables().size());
        Assert.assertEquals(0, behaviour.getFrequency());
        Assert.assertEquals(0, behaviour.getDeferring());
        Assert.assertNull(behaviour.getPreCondition());
        Assert.assertNull(behaviour.getRuntimeCondition());
        Assert.assertNull(behaviour.getPostCondition());

        Assert.assertNotNull(configuration.getId());
        Assert.assertEquals("default", configuration.getName());
        Assert.assertEquals("", configuration.getComment());
        Assert.assertEquals("", configuration.getRelativeDirectory());
        Assert.assertEquals(0, configuration.getVariables().size());
        Assert.assertEquals(behaviour.getId(), configuration.getBehaviour().getId());
        Assert.assertEquals(0, configuration.getKeyValuePairs().size());
    }
}
