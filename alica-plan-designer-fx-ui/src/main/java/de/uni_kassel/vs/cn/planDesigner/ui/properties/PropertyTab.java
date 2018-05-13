package de.uni_kassel.vs.cn.planDesigner.ui.properties;

import de.uni_kassel.vs.cn.generator.plugin.IPlugin;
import de.uni_kassel.vs.cn.generator.plugin.PluginManager;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change.SetTaskOfEntryPoint;
import de.uni_kassel.vs.cn.planDesigner.alica.EntryPoint;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alica.Task;
import de.uni_kassel.vs.cn.planDesigner.alica.Transition;
import de.uni_kassel.vs.cn.planDesigner.alica.util.RepoViewBackend;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.controller.ErrorWindowController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.AbstractEditorTab;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.eclipse.emf.ecore.impl.EAttributeImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by marci on 27.11.16.
 */
public class PropertyTab extends AbstractPropertyTab {

    private ObservableList<Node> propertyHBoxList;

    public PropertyTab(AbstractEditorTab<PlanElement> activeEditorTab, CommandStack commandStack) {
        super(activeEditorTab, commandStack);
        setText(I18NRepo.getString("label.properties"));
    }

    @Override
    protected void createTabContent() {
        if (propertyHBoxList == null) {
            propertyHBoxList = FXCollections.observableList(new ArrayList<>());
        }
        propertyHBoxList.clear();
        if (selectedPlanElement == null) {
            setContent(new ListView<>(propertyHBoxList));
            return;
        }
        getSelectedEditorTabPlanElement()
                .eClass()
                .getEAllStructuralFeatures()
                .forEach(eStructuralFeature -> {
                    if (!eStructuralFeature.getName().equalsIgnoreCase("parametrisation") && !eStructuralFeature.getName().equalsIgnoreCase("inplan")) {
                        if(eStructuralFeature.getClass().equals(EAttributeImpl.class)) {
                            if (eStructuralFeature.getName().equalsIgnoreCase("comment")) {
                                propertyHBoxList.add(new PropertyHBox<PlanElement>(getSelectedEditorTabPlanElement(), eStructuralFeature.getName(),
                                        ((EAttributeImpl) eStructuralFeature).getEAttributeType().getInstanceClass(), commandStack){
                                    @Override
                                    public CommandStack getCommandStack() {
                                        return PropertyTab.this.getCommandStack();
                                    }

                                    @Override
                                    protected TextInputControl createTextField(PlanElement object, String propertyName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
                                        return new PropertyTextArea(object, propertyName);
                                    }
                                });
                            } else {
                                propertyHBoxList.add(new PropertyHBox<PlanElement>(getSelectedEditorTabPlanElement(), eStructuralFeature.getName(),
                                        ((EAttributeImpl) eStructuralFeature).getEAttributeType().getInstanceClass(), commandStack) {
                                    @Override
                                    public CommandStack getCommandStack() {
                                        return PropertyTab.this.getCommandStack();
                                    }
                                });
                            }
                        }
                    }
                });

        if (getSelectedEditorTabPlanElement() instanceof EntryPoint) {
            Text text = new Text(I18NRepo.getString("alicatype.property.task"));
            ComboBox<Task> taskComboBox = new ComboBox<>();
            taskComboBox.getSelectionModel().select(((EntryPoint) getSelectedEditorTabPlanElement()).getTask());
            taskComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
                @Override
                public void changed(ObservableValue<? extends Task> observable, Task oldValue, Task newValue) {
                    commandStack.storeAndExecute(new SetTaskOfEntryPoint(newValue, (EntryPoint) getSelectedEditorTabPlanElement()));
                    ((EntryPoint) getSelectedEditorTabPlanElement()).setTask(newValue);
                    selectedElementContainerProperty().getValue().getValue().setupContainer();
                }
            });
            taskComboBox.setItems(FXCollections.observableArrayList(RepoViewBackend.getInstance().getTasks().getKey()));
            taskComboBox.setButtonCell(new ListCell<Task>() {
                @Override
                protected void updateItem(Task item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item.getName());
                    } else {
                        setText(null);
                    }
                }
            });
            taskComboBox.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {
                @Override
                public ListCell<Task> call(ListView<Task> param) {
                    return new ListCell<Task>() {
                        @Override
                        protected void updateItem(Task item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                setText(item.getName());
                            } else {
                                setText(null);
                            }
                        }
                    };
                }
            });
            text.setWrappingWidth(100);
            HBox hBox = new HBox(text, taskComboBox);
            HBox.setHgrow(taskComboBox, Priority.ALWAYS);
            HBox.setHgrow(text, Priority.ALWAYS);
            HBox.setMargin(taskComboBox, new Insets(0,100,0,50));

            propertyHBoxList.add(hBox);
        }

        if (getSelectedEditorTabPlanElement() instanceof Transition) {
            String pluginName = ((Transition) getSelectedEditorTabPlanElement()).getPreCondition().getPluginName();
            IPlugin matchingPlugin = PluginManager.getInstance().getPluginByName(pluginName);
            if (matchingPlugin != null) {
                propertyHBoxList.add(matchingPlugin.getPluginUI());
            } else {
                ErrorWindowController.createErrorWindow(I18NRepo.getString("label.error.pluginNotFound"), null);
            }
        }


        setContent(new ListView<>(propertyHBoxList));
    }

    public void setPropertyHBoxList(ObservableList<Node> propertyHBoxList) {
        this.propertyHBoxList = propertyHBoxList;
    }

    public ObservableList<Node> getPropertyHBoxList() {
        return propertyHBoxList;
    }
}
