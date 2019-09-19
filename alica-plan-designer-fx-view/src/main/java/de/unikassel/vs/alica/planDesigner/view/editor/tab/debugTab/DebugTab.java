package de.unikassel.vs.alica.planDesigner.view.editor.tab.debugTab;

import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IAlicaMessageHandler;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.EditorTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.EditorTabPane;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import de.unikassel.vs.alica.planDesigner.view.model.SerializableViewModel;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class DebugTab extends EditorTab {

    private IAlicaMessageHandler alicaMessageHandler;

    public DebugTab(SerializableViewModel serializableViewModel, EditorTabPane editorTabPane, IAlicaMessageHandler handler) {
        super(serializableViewModel, editorTabPane.getGuiModificationHandler());
        alicaMessageHandler = handler;

        TextArea textArea = new TextArea();

//        HBox topic = new HBox();
//        Label labelTopic = new Label("Topic:");
//        TextField textfieldTopic = new TextField();
//        topic.getChildren().addAll(labelTopic, textfieldTopic);
//        topic.setSpacing(10);
//        HBox.setHgrow(textfieldTopic, Priority.ALWAYS);
//
//        HBox message = new HBox();
//        Label labelMessage = new Label("Message:");
//        TextField textfieldMessage = new TextField();
//        message.getChildren().addAll(labelMessage, textfieldMessage);
//        message.setSpacing(10);
//        HBox.setHgrow(textfieldMessage, Priority.ALWAYS);
//
//        Button pubButton = new Button("Publish");
//        Button subButton = new Button("Subscribe");
//
//        pubButton.setMaxWidth(Double.MAX_VALUE);
//        subButton.setMaxWidth(Double.MAX_VALUE);
//
//        VBox vbox = new VBox();
//        vbox.getChildren().addAll(topic, message, pubButton, subButton);
//        VBox.setVgrow(vbox, Priority.ALWAYS);
//        vbox.setMaxWidth(Double.MAX_VALUE);
//        vbox.setSpacing(10);

        Button runButton = new Button("Run");
        runButton.setGraphic(new ImageView(new Image(AlicaIcon.class.getClassLoader().getResourceAsStream("images/run16x16.png"))));
        runButton.setMaxWidth(Double.MAX_VALUE);
        runButton.setOnAction(this::onRunClicked);

        HBox hbox = new HBox();
        hbox.getChildren().addAll(textArea, runButton);
        hbox.setSpacing(10);
        HBox.setHgrow(runButton, Priority.ALWAYS);
        //HBox.setHgrow(vbox, Priority.ALWAYS);

        this.elementInformationPane.setContent(hbox);
    }

    private void onRunClicked(ActionEvent event) {
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

}
