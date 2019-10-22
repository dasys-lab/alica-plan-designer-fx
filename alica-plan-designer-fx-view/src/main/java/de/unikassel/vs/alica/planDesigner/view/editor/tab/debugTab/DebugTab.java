package de.unikassel.vs.alica.planDesigner.view.editor.tab.debugTab;

import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IAlicaHandler;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.EditorTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.EditorTabPane;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import de.unikassel.vs.alica.planDesigner.view.model.SerializableViewModel;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DebugTab extends EditorTab {

    public static final int SPACING = 10;

    private IAlicaHandler alicaHandler;

    // JavaFX stuff
    private VBox allAgentsVBox;
    private TextArea debugTextArea;
    private Button runButton;

    // Process indicator
    private boolean isRunning;

    // Globals.conf parsed
    private Map<Agent, Boolean> availableAgents;

    public DebugTab(SerializableViewModel serializableViewModel, EditorTabPane editorTabPane, IAlicaHandler handler) {
        super(serializableViewModel, editorTabPane.getGuiModificationHandler());
        alicaHandler = handler;

        availableAgents = new HashMap<>();
        parseGlobals(Path.of("/opt/pd-debug/cnc-turtlebots/etc/Globals.conf")); // TODO no hardcoded paths

        draw();
    }

    private void draw() {
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

        allAgentsVBox = new VBox(SPACING);

        for (Agent agent : availableAgents.keySet()) {
            HBox hBox = new HBox(SPACING);

            Label nameLabel = new Label(agent.name + " (id=" + agent.id + "; defaultRole=" + agent.defaultRole + "; speed=" + agent.speed + ")");
            CheckBox checkBox = new CheckBox();
            checkBox.setSelected(availableAgents.get(agent));

            checkBox.selectedProperty().addListener((observableValue, oldValue, newValue) -> availableAgents.put(agent, newValue));

            hBox.getChildren().addAll(checkBox, nameLabel);
            allAgentsVBox.getChildren().addAll(hBox);
        }

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

    private void onRunClicked(ActionEvent event) {
        if (!isRunning) {
            isRunning = alicaHandler.runAlica();
            if (isRunning) {
                runButton.setText("Stop");
                runButton.setGraphic(new ImageView(new Image(AlicaIcon.class.getClassLoader().getResourceAsStream("images/stop16x16.png"))));
            }
        } else {
            alicaHandler.stopAlica();
            isRunning = false;
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
                alicaHandler.handleAlicaMessageReceived(8765,
                        "ServeMaster",
                        "ServeMaster",
                        "Stop",
                        "Assistant",
                        "DefaultTask",
                        new long[]{8765});
                Thread.sleep(10);
                alicaHandler.handleAlicaMessageReceived(5678,
                        "ServeMaster",
                        "PutDown",
                        "DriveToPoint",
                        "Assistant",
                        "DefaultTask",
                        new long[]{5678});
                Thread.sleep(2500);
                alicaHandler.handleAlicaMessageReceived(8765,
                        "ServeMaster",
                        "ServeMaster",
                        "Serve",
                        "Assistant",
                        "DefaultTask",
                        new long[]{8765});
                Thread.sleep(2500);
                alicaHandler.handleAlicaMessageReceived(8765,
                        "ServeMaster",
                        "ServeMaster",
                        "Charge",
                        "Assistant",
                        "DefaultTask",
                        new long[]{8765});
                Thread.sleep(2500);
                alicaHandler.handleAlicaMessageReceived(8765,
                        "ServeMaster",
                        "Serve",
                        "WaitForTask",
                        "Assistant",
                        "DefaultTask",
                        new long[]{8765});
                Thread.sleep(2500);
                alicaHandler.handleAlicaMessageReceived(8765,
                        "ServeMaster",
                        "Serve",
                        "DriveToPOI",
                        "Assistant",
                        "DefaultTask",
                        new long[]{8765});
                Thread.sleep(2500);
                alicaHandler.handleAlicaMessageReceived(8765,
                        "ServeMaster",
                        "Serve",
                        "WaitForTask",
                        "Assistant",
                        "DefaultTask",
                        new long[]{8765});
                Thread.sleep(2500);
                alicaHandler.handleAlicaMessageReceived(8765,
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

    private void parseGlobals(Path pathToGlobals) {
        try {
            List<String> globals = Files.readAllLines(pathToGlobals);

            globals.replaceAll(s -> s.strip());
            globals.removeIf(s -> s.isEmpty());
            globals.removeIf(s -> s.startsWith("#"));

            List<String> teams = globals.subList(globals.indexOf("[Team]") + 1, globals.indexOf("[!Team]"));

            while (!teams.isEmpty())
            {
                String name = teams.get(0).replace("[", "").replace("]", "");
                Agent agent = new Agent();
                agent.name = name;
                List<String> agentAttributes = teams.subList(0, teams.indexOf("[!" + name + "]") + 1);

                // Get the id, which is given like 'ID = 1', or else set it to 0
                agent.id = Short.parseShort(agentAttributes.stream()
                        .filter(s -> s.startsWith("ID"))
                        .map(s -> s.replaceAll(" ", ""))
                        .findFirst()
                        .orElse("ID=0").split("=")[1]);

                agent.defaultRole = agentAttributes.stream()
                        .filter(s -> s.startsWith("DefaultRole"))
                        .map(s -> s.replaceAll(" ", ""))
                        .findFirst()
                        .orElse("DefaultRole=None").split("=")[1];

                agent.speed = agentAttributes.stream()
                        .filter(s -> s.startsWith("Speed"))
                        .map(s -> s.replaceAll(" ", ""))
                        .findFirst()
                        .orElse("Speed=Fast").split("=")[1];

                availableAgents.put(agent, false);

                // Delete this agent from team
                while (!teams.get(0).equals("[!" + name + "]")) {
                    teams.remove(0);
                }
                // Delete [!name] tag
                teams.remove(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
    }

    @Override
    public GuiModificationEvent handleDelete() {
        return null;
    }

    public void setAlicaHandler(IAlicaHandler alicaHandler) {
        this.alicaHandler = alicaHandler;
    }

    @Override
    public void setOnCloseRequest(EventHandler<Event> value) {
        super.setOnCloseRequest(value);
    }

    private static class Agent {
        short id;
        String name, defaultRole, speed;

        @Override
        public String toString() {
            return "Agent(name=" + name + ";id=" + id + ";defaultRole=" + defaultRole + ";speed=" + speed + ")";
        }
    }
}
