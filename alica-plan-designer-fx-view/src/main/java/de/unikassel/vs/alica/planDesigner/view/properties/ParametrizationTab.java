package de.unikassel.vs.alica.planDesigner.view.properties;

import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import de.unikassel.vs.alica.planDesigner.view.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ParametrizationTab extends Tab {

    private Label varLabel;
    private ComboBox<ViewModelElement> varDropDown;

    private Label subPlanLabel;
    private ComboBox<ViewModelElement> subPlanDropDown;

    private Label subVarLabel;
    private ComboBox<ViewModelElement> subVarDropDown;

    private Button addButton;

    private ParamTable paramTable;

    private ViewModelElement element;
    private final IGuiModificationHandler guiModificationHandler;

    public ParametrizationTab(IGuiModificationHandler guiModificationHandler, String title) {
        super(title);
        this.guiModificationHandler = guiModificationHandler;

        ObservableMap<Object, Object> properties = this.getProperties();

        Node root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("paramTabContent.fxml"));

            varLabel = (Label) root.lookup("#varLabel");
            varDropDown = (ComboBox<ViewModelElement>) root.lookup("#varDropDown");
            varDropDown.setConverter(new ViewModelElementStringConverter(varDropDown, false));

            subPlanLabel = (Label) root.lookup("#subPlanLabel");
            subPlanDropDown = (ComboBox<ViewModelElement>) root.lookup("#subPlanDropDown");
            subPlanDropDown.setConverter(new ViewModelElementStringConverter(subPlanDropDown, false));
            subPlanDropDown.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ViewModelElement>() {
                @Override
                public void changed(ObservableValue<? extends ViewModelElement> observable, ViewModelElement oldValue, ViewModelElement newValue) {
                    ObservableList<VariableViewModel> variables = null;
                    switch (newValue.getType()) {
                        case Types.ANNOTATEDPLAN: {
                            AnnotatedPlanView annotatedPlanView = (AnnotatedPlanView) newValue;
                            PlanViewModel planViewModel = (PlanViewModel) guiModificationHandler.getViewModelElement(annotatedPlanView.getPlanId());
                            variables = planViewModel.getVariables();
                        } break;
                        case Types.PLAN: {
                            PlanViewModel planViewModel = (PlanViewModel) newValue;
                            variables = planViewModel.getVariables();
                        } break;
                        default: throw new RuntimeException(newValue.getType());
                    }
                    subVarDropDown.setItems(FXCollections.observableArrayList(variables));
                }
            });

            subVarLabel = (Label) root.lookup("#subVarLabel");
            subVarDropDown = (ComboBox<ViewModelElement>) root.lookup("#subVarDropDown");
            subVarDropDown.setConverter(new ViewModelElementStringConverter(subVarDropDown, false));
        } catch (IOException e) {
            e.printStackTrace();
        }

        addButton = (Button) root.lookup("#addButton");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                VariableViewModel selectedVar = (VariableViewModel) varDropDown.getSelectionModel().getSelectedItem();
                VariableViewModel selectedSubVar = (VariableViewModel) subVarDropDown.getSelectionModel().getSelectedItem();

                PlanViewModel selectedSubPlan = (PlanViewModel) subPlanDropDown.getSelectionModel().getSelectedItem();

                GuiModificationEvent event = new GuiModificationEvent(GuiEventType.ADD_ELEMENT, Types.PARAMETRIZATION, "NEW_VARIABLE");
                event.setParentId(element.getId());

                HashMap<String, Long> relatedObjects = new HashMap<>();
                relatedObjects.put(Types.VARIABLE, selectedVar.getId());
                relatedObjects.put(Types.PARAMETRIZATION, selectedSubVar.getId());
                relatedObjects.put(Types.PLAN, selectedSubPlan.getId());

                event.setRelatedObjects(relatedObjects);
                guiModificationHandler.handle(event);
            }
        });

        paramTable = (ParamTable) root.lookup("#paramTable");

        this.setContent(root);
        this.init();
    }

    private void init() {
        I18NRepo repo = I18NRepo.getInstance();

        // ---- LABEL ----
        varLabel.setText(repo.getString("label.caption.variables"));
        subPlanLabel.setText(repo.getString("label.column.subplan"));
        subVarLabel.setText(repo.getString("label.column.subVariable"));

        // ---- BUTTON ----
        addButton.setGraphic(new ImageView(new AlicaIcon(AlicaIcon.ADD, AlicaIcon.Size.SMALL)));

        // ---- DROPDOWN ----
        if (varDropDown.getItems().isEmpty()) {
            varDropDown.setItems(FXCollections.observableArrayList(new ViewModelElement(0L, "--empty--", null)));
        }

        if (subPlanDropDown.getItems().isEmpty()) {
            subPlanDropDown.setItems(FXCollections.observableArrayList(new ViewModelElement(0L, "--empty--", null)));
        }

        if (subVarDropDown.getItems().isEmpty()) {
            subVarDropDown.setItems(FXCollections.observableArrayList(new ViewModelElement(0L, "--empty--", null)));
        }
    }

    public void setViewModel(ViewModelElement element) {
        if (!(element instanceof HasParametrizationView)) {
            return;
        }
        this.element = element;
        switch (element.getType()) {
            case Types.PLANTYPE: {
                PlanTypeViewModel planTypeViewModel = (PlanTypeViewModel) element;

                ArrayList<ViewModelElement> elements = new ArrayList<>();
                for(PlanViewModel planViewModel: planTypeViewModel.getAllPlans()) {
                    elements.addAll(planViewModel.getVariables());
                }

                varDropDown.setItems(FXCollections.observableArrayList(elements));
                varDropDown.setConverter(new ViewModelElementStringConverter(varDropDown, true));

                subPlanDropDown.setItems(FXCollections.observableArrayList(planTypeViewModel.getPlansInPlanType()));
                subVarDropDown.setItems(FXCollections.observableArrayList(new ViewModelElement(0L, "please select plan first", null)));


            } break;
            case Types.STATE: {
                StateViewModel stateViewModel = (StateViewModel) element;
                varDropDown.setItems(FXCollections.observableArrayList(((PlanViewModel) guiModificationHandler.getViewModelElement(element.getParentId())).getVariables()));
                subPlanDropDown.setItems(FXCollections.observableArrayList(stateViewModel.getAbstractPlans()));
                subVarDropDown.setItems(FXCollections.observableArrayList(new ViewModelElement(0L, "please select plan first", null)));

            } break;
            default:{
                System.err.println("ParametrizationTab: Unrecognized ViewElementType");
            }
        }

        ObservableList parametrizations = ((HasParametrizationView) element).getParametrizations();
        paramTable.setItems(parametrizations);
        parametrizations.addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                for (TableColumn column: paramTable.getColumns()) {
                    paramTable.resizeColumn(column, 1);
                }
                if (c.getList().size() > 0) {
                    paramTable.setVisible(true);
                } else {
                    paramTable.setVisible(false);
                }
            }
        });

        if (parametrizations.isEmpty()) {
            paramTable.setVisible(false);
        } else {
            paramTable.setVisible(true);
        }
    }

    private class ViewModelElementStringConverter extends StringConverter<ViewModelElement> {

        private final ComboBox<ViewModelElement> comboBox;
        private final boolean withPlan;

        ViewModelElementStringConverter(ComboBox<ViewModelElement> comboBox, boolean withPlan) {
            this.comboBox = comboBox;
            this.withPlan = withPlan;
        }

        @Override
        public String toString(ViewModelElement viewModelElement) {
            if (withPlan) {
                PlanViewModel plan = (PlanViewModel) guiModificationHandler.getViewModelElement(viewModelElement.getParentId());
                return plan.getName() + ":" + viewModelElement.getName();
            }
            return viewModelElement.getName();
        }

        @Override
        public ViewModelElement fromString(String string) {
            string = string.substring(string.lastIndexOf(":"), string.length());

            for (ViewModelElement viewModelElement: comboBox.getItems()) {
                if (viewModelElement.getName().equals(string)) {
                    return viewModelElement;
                }
            }
            return null;
        }
    }
}
