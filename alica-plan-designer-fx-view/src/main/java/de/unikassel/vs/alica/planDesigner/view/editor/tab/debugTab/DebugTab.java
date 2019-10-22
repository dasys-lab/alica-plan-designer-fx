package de.unikassel.vs.alica.planDesigner.view.editor.tab.debugTab;

import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IAlicaMessageHandler;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.EditorTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.EditorTabPane;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import de.unikassel.vs.alica.planDesigner.view.model.SerializableViewModel;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class DebugTab extends EditorTab {

    public static final int SPACING = 10;

    private IAlicaMessageHandler alicaMessageHandler;

    // JavaFX stuff
    private final VBox allAgentsVBox;
    private final TextArea debugTextArea;
    private final Button runButton;

    // Processes
    private Process roscoreProcess;
    private Process pdAlicaRunnerProcess;

    public DebugTab(SerializableViewModel serializableViewModel, EditorTabPane editorTabPane, IAlicaMessageHandler handler) {
        super(serializableViewModel, editorTabPane.getGuiModificationHandler());
        alicaMessageHandler = handler;


        debugTextArea = new TextArea();
        debugTextArea.setEditable(false);

        // redirect System.out to the debugTextArea
        OutputStream stream = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                debugTextArea.appendText(String.valueOf((char) b));
            }
        };
        System.setOut(new PrintStream(stream, true));

        Button addAgentButton = new Button("Add Agent");
        addAgentButton.setOnAction(this::onAddAgentClicked);

        allAgentsVBox = new VBox(SPACING);
        allAgentsVBox.getChildren().add(addAgentButton);

        runButton = new Button("Run");
        runButton.setGraphic(new ImageView(new Image(AlicaIcon.class.getClassLoader().getResourceAsStream("images/run16x16.png"))));
        runButton.setMaxWidth(Double.MAX_VALUE);
        runButton.setOnAction(this::onRunClicked);

        Button simulateButton = new Button("Simulate");
        simulateButton.setOnAction(this::simulate);

        VBox vBox = new VBox(SPACING);
        vBox.getChildren().addAll(allAgentsVBox, runButton, simulateButton);

        HBox hbox = new HBox(SPACING);
        hbox.getChildren().addAll(debugTextArea, vBox);
        HBox.setHgrow(vBox, Priority.ALWAYS);
        //HBox.setHgrow(vbox, Priority.ALWAYS);

        this.elementInformationPane.setContent(hbox);
    }

    private void onAddAgentClicked(ActionEvent event) {

        TextField textFieldName = new TextField();
        textFieldName.setPromptText("Agent Name");

        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList("Assistant", "Transporter"));
        comboBox.getSelectionModel().clearAndSelect(0);
        // TODO get roles from repository

        Button deleteButton = new Button("X");
        HBox hBox = new HBox(SPACING);

        deleteButton.setOnAction(e -> this.onAgentHBoxDeleted(e, hBox));
        hBox.getChildren().addAll(deleteButton, textFieldName, comboBox);

        allAgentsVBox.getChildren().add(allAgentsVBox.getChildrenUnmodifiable().size() - 1, hBox);

    }

    private void onAgentHBoxDeleted(ActionEvent event, HBox hBox) {
        allAgentsVBox.getChildren().remove(hBox);
    }

    private void onRunClicked(ActionEvent event) {
        if (pdAlicaRunnerProcess == null || roscoreProcess == null) {
            try {
                // start roscore, because we still need it
                ProcessBuilder pb = new ProcessBuilder("bash", "-c", "echo 'Sourcing setup.bash'; source /opt/ros/melodic/setup.bash; echo 'Starting roscore...'; roscore");
                pb.environment().put("PATH", pb.environment().get("PATH") + ":/opt/ros/melodic/bin");
                pb.inheritIO();
                roscoreProcess = pb.start();

                // Load pd_alica_runner
                pb = new ProcessBuilder("bash", "-c", "echo Starting pd_alica_runner...; /opt/pd-debug/ttb-ws/devel/lib/pd_alica_runner/pd_alica_runner -m ServeMaster -rd  /opt/pd-debug/cnc-turtlebots/etc/roles/ -r ServiceRobotsRoleSet -sim");
                pb.directory(new File("/opt/pd-debug/cnc-turtlebots"));
                pb.environment().put("PATH", pb.environment().getOrDefault("PATH", "") + ":/opt/ros/melodic/bin");
                pb.environment().put("ROBOT", "donatello");
                pb.environment().put("LD_LIBRARY_PATH", "/opt/pd-debug/ttb-ws/devel/lib:/opt/ros/melodic/lib" + pb.environment().getOrDefault("LD_LIBRARY_PATH", ""));
                pb.environment().put("ROS_MASTER_URI", "http://localhost:11311");
                pb.inheritIO();
                pdAlicaRunnerProcess = pb.start();

                // if everything worked, change the run button to a stop button
                runButton.setText("Stop");
                runButton.setGraphic(new ImageView(new Image(AlicaIcon.class.getClassLoader().getResourceAsStream("images/stop16x16.png"))));

            } catch (Exception e) {
                System.err.println("Could not load roscore :(");
                e.printStackTrace();
            }
        } else {
            try {
                pdAlicaRunnerProcess.destroyForcibly();
                pdAlicaRunnerProcess.waitFor();
                roscoreProcess.destroyForcibly();
                roscoreProcess.waitFor();

                System.out.println("Destroyed both processes.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // change the stop button to a start button
            runButton.setText("Run");
            runButton.setGraphic(new ImageView(new Image(AlicaIcon.class.getClassLoader().getResourceAsStream("images/run16x16.png"))));
        }

    }

    private void simulate(ActionEvent event) {
        // Simulates a AlicaEngineInfo message:
        //(senderId = (value = "\x02\x00\x00\x00", type = wildcard), masterPlan = "ServeMaster", currentPlan = "ServeMaster", currentState = "Stop", currentRole = "Assistant", currentTask = "DefaultTask", agentIdsWithMe = [(value = "\x02\x00\x00\x00", type = wildcard)])

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                alicaMessageHandler.handleAlicaMessageReceived(8765,
                        "ServeMaster",
                        "ServeMaster",
                        "Stop",
                        "Assistant",
                        "DefaultTask",
                        new long[]{8765});
                Thread.sleep(10);
                alicaMessageHandler.handleAlicaMessageReceived(5678,
                        "ServeMaster",
                        "PutDown",
                        "DriveToPoint",
                        "Assistant",
                        "DefaultTask",
                        new long[]{5678});
                Thread.sleep(2500);
                alicaMessageHandler.handleAlicaMessageReceived(8765,
                        "ServeMaster",
                        "ServeMaster",
                        "Serve",
                        "Assistant",
                        "DefaultTask",
                        new long[]{8765});
                Thread.sleep(2500);
                alicaMessageHandler.handleAlicaMessageReceived(8765,
                        "ServeMaster",
                        "ServeMaster",
                        "Charge",
                        "Assistant",
                        "DefaultTask",
                        new long[]{8765});
                Thread.sleep(2500);
                alicaMessageHandler.handleAlicaMessageReceived(8765,
                        "ServeMaster",
                        "Serve",
                        "WaitForTask",
                        "Assistant",
                        "DefaultTask",
                        new long[]{8765});
                Thread.sleep(2500);
                alicaMessageHandler.handleAlicaMessageReceived(8765,
                        "ServeMaster",
                        "Serve",
                        "DriveToPOI",
                        "Assistant",
                        "DefaultTask",
                        new long[]{8765});
                Thread.sleep(2500);
                alicaMessageHandler.handleAlicaMessageReceived(8765,
                        "ServeMaster",
                        "Serve",
                        "WaitForTask",
                        "Assistant",
                        "DefaultTask",
                        new long[]{8765});
                Thread.sleep(2500);
                alicaMessageHandler.handleAlicaMessageReceived(8765,
                        "ServeMaster",
                        "Serve",
                        "SearchFor",
                        "Assistant",
                        "DefaultTask",
                        new long[]{8765});
                return null;
            }
        };
        new Thread(task).start();
    }

    @Override
    public void save() {
    }

    @Override
    public GuiModificationEvent handleDelete() {
        return null;
    }



    public void setAlicaMessageHandler(IAlicaMessageHandler alicaMessageHandler) {
        this.alicaMessageHandler = alicaMessageHandler;
    }

    @Override
    public void setOnCloseRequest(EventHandler<Event> value) {
        super.setOnCloseRequest(value);
    }
}
