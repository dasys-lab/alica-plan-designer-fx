package de.uni_kassel.vs.cn.planDesigner.ui.properties;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change.ChangeAttributeValue;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by marci on 27.11.16.
 */
public class PropertyHBox<T extends PlanElement> extends HBox {

    public static final int wrappingWidth = 100;

    private CommandStack commandStack;

    // TODO resolve problem of not getting value by property reference
    public PropertyHBox(T object, String propertyName, Class<?> propertyClass, CommandStack commandStack) {
        this.commandStack = commandStack;
        try {
            Text text = new Text(I18NRepo.getString("alicatype.property." + propertyName));
            text.setWrappingWidth(wrappingWidth);
            Node inputField = getInputField(object, propertyName, propertyClass);
            if (propertyName.equals("id")) {
                inputField.setDisable(true);
            }
            getChildren().addAll(text, inputField);
            setHgrow(inputField, Priority.ALWAYS);
            setHgrow(text, Priority.ALWAYS);
            setMargin(inputField,new Insets(0,100,0,50));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public CommandStack getCommandStack() {
        return commandStack;
    }

    private Node getInputField(T object, String propertyName, Class<?> propertyClass) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (propertyClass.equals(String.class)) {
            return createTextField(object, propertyName);
        }

        if (propertyClass.equals(long.class) || propertyClass.equals(int.class)) {
            TextInputControl textField = createTextField(object, propertyName);
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.matches("\\d*") == false) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });
            return textField;
        }

        if (propertyClass.equals(double.class) || propertyClass.equals(float.class)) {
            TextInputControl textField = createTextField(object, propertyName);
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.matches("\\d*\\.?\\d*") == false) {
                    textField.setText(oldValue);
                }
            });
            return textField;
        }

        if (propertyClass.equals(boolean.class)) {
            ComboBox<Boolean> booleanComboBox = new ComboBox<>();
            booleanComboBox.setItems(FXCollections.observableArrayList(true,false));
            booleanComboBox.getSelectionModel().select(Boolean.parseBoolean(BeanUtils.getProperty(object, propertyName)));
            booleanComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> getCommandStack()
                    .storeAndExecute(
                            new ChangeAttributeValue<>(object, propertyName, object.getClass(), (T) ((Object) newValue))));
            return booleanComboBox;
        }

        return createTextField(object, propertyName);
    }

    protected TextInputControl createTextField(T object, String propertyName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return new PropertyTextField<T>(object, propertyName, commandStack) {
            @Override
            public CommandStack getCommandStack() {
                return PropertyHBox.this.getCommandStack();
            }
        };
    }

    static class PropertyTextArea extends TextArea {
        public PropertyTextArea(PlanElement object, String propertyName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
            super(BeanUtils.getProperty(object, propertyName));
            autosize();
            textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    BeanUtils.setProperty(object, propertyName, newValue);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
