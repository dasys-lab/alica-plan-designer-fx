package de.unikassel.vs.alica.planDesigner.controller;

import de.uniks.vs.capnzero.monitoring.MonitorClient;
import de.uniks.vs.capnzero.monitoring.event.DebugEvent;
import de.uniks.vs.capnzero.monitoring.handler.DebugEventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


public class DebugWindowController implements DebugEventHandler
{
  private TextArea textArea;
  private Stage stage;

  public DebugWindowController()
  {
  }

  private Scene buildScene()
  {
    this.textArea = new TextArea();
    this.textArea.setPrefWidth(600);
    this.textArea.setPrefHeight(370);

    return new Scene(textArea, 600, 370);
  }

  public void showDebugWindow()
  {
    MonitorClient debugMonitorClient = new MonitorClient(this);
    this.stage = new Stage();
    this.stage.setOnCloseRequest((e) -> debugMonitorClient.stop());

    this.stage.setScene(buildScene());
    this.stage.setTitle("CapnZero Debug Messages");

    this.stage.show();

    debugMonitorClient.start();
  }

  @Override
  public void handleDebugEvent( DebugEvent debugEvent )
  {
    if(this.stage.isShowing())
    {
      this.textArea.appendText(debugEvent.toString() + "\n");
    }
  }
}

