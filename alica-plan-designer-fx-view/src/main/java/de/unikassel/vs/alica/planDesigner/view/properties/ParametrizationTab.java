package de.unikassel.vs.alica.planDesigner.view.properties;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaCursor;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import de.unikassel.vs.alica.planDesigner.view.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;

import javax.swing.text.View;
import java.io.IOException;

public class ParametrizationTab extends Tab {

    private Label varLabel;
    private ComboBox<ViewModelElement> varDropDown;

    private Label subPlanLabel;
    private ComboBox<ViewModelElement> subPlanDropDown;

    private Label subVarLabel;
    private ComboBox<ViewModelElement> subVarDropDown;

    private Button addButton;

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
            varDropDown.setConverter(new ViewModelElementStringConverter(varDropDown));

            subPlanLabel = (Label) root.lookup("#subPlanLabel");
            subPlanDropDown = (ComboBox<ViewModelElement>) root.lookup("#subPlanDropDown");
            subPlanDropDown.setConverter(new ViewModelElementStringConverter(subPlanDropDown));
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
            subVarDropDown.setConverter(new ViewModelElementStringConverter(subVarDropDown));
        } catch (IOException e) {
            e.printStackTrace();
        }

        addButton = (Button) root.lookup("#addButton");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

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
    }

    public void setViewModel(ViewModelElement element) {
        if (element == null) {
            return;
        }
        this.element = element;
        switch (element.getType()) {
            case Types.PLANTYPE: {
                PlanTypeViewModel planTypeViewModel = (PlanTypeViewModel) element;
                subPlanDropDown.setItems(FXCollections.observableArrayList(planTypeViewModel.getPlansInPlanType()));
                subVarDropDown.setItems(FXCollections.observableArrayList(new ViewModelElement(0L, "please select plan first", null)));
            } break;
            default:{
                System.err.println("ParametrizationTab: Unrecognized ViewElementType");
            }
        }

    }

    private class ViewModelElementStringConverter extends StringConverter<ViewModelElement> {

        private final ComboBox<ViewModelElement> comboBox;

        ViewModelElementStringConverter(ComboBox<ViewModelElement> comboBox) {
            this.comboBox = comboBox;
        }

        @Override
        public String toString(ViewModelElement viewModelElement) {
            return viewModelElement.getName();
        }

        @Override
        public ViewModelElement fromString(String string) {
            for (ViewModelElement viewModelElement: comboBox.getItems()) {
                if (viewModelElement.getName().equals(string)) {
                    return viewModelElement;
                }
            }
            return null;
        }
    }
}
