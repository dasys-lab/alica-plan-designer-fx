package de.unikassel.vs.alica.planDesigner.controller;

import de.uniks.vs.capnzero.monitoring.MonitorClient;
import de.uniks.vs.capnzero.monitoring.event.DebugEvent;
import de.uniks.vs.capnzero.monitoring.handler.DebugEventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


public class DebugWindowController implements DebugEventHandler
{
  private MonitorClient debugMonitorClient;
  private TextArea textArea;
  private Stage stage;


  public DebugWindowController()
  {
    this.debugMonitorClient = new MonitorClient(this);
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
    this.stage = new Stage();
    this.stage.setOnCloseRequest((e) -> this.debugMonitorClient.stop());

    this.stage.setScene(buildScene());
    this.stage.setTitle("CapnZero Debug Messages");

    this.stage.show();

    this.debugMonitorClient.start();
  }

  public void closeDebugWindow()
  {
    this.debugMonitorClient.stop();

    this.stage.hide();
    this.stage.close();
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

