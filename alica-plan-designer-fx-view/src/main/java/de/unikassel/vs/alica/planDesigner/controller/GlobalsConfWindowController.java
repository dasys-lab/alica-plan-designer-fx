package de.unikassel.vs.alica.planDesigner.controller;

import com.google.common.io.Files;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGlobalsConfEventHandler;
import de.unikassel.vs.alica.planDesigner.view.model.GlobalsConfViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class GlobalsConfWindowController  implements Initializable {

    @FXML
    private TableColumn<GlobalsConfWindowController, String> teamNameTableColumn;

    @FXML
    private TableColumn<GlobalsConfWindowController, Integer> teamIDTableColumn;

    @FXML
    private TableColumn<GlobalsConfWindowController, String> teamDefaultRoleTableColumn;

    @FXML
    private TableColumn<GlobalsConfWindowController, String> teamSpeedTableColumn;

    @FXML
    private TableView<GlobalsConfWindowController> teamTableView;

    @FXML
    private TextField activeGlobalsTextField;

    @FXML
    private Button deleteButton;

    @FXML
    private Button activeGlobalsButton;

    @FXML
    private TextField addNameTextField;

    @FXML
    private Button setDefaultConfButton;

    @FXML
    private TextField diameterRobotTextField;

    @FXML
    private Label activeGlobalsLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Label assistentLabel;

    @FXML
    private TextField defaultRoleTextField;

    @FXML
    private TextField addDefaultRoleTextField;

    @FXML
    private Button addButton;

    @FXML
    private TextField speedTextField;

    @FXML
    private Button exitButton;

    @FXML
    private Label speedLabel;

    @FXML
    private TextField addSpeedTextField;

    @FXML
    private Label diameterRobotLabel;

    @FXML
    private TextField assistentTextField;

    @FXML
    private Label defaultRoleLabel;

    @FXML
    private TextField addIDTextField;


    private int teamID;
    private String teamName;
    private String teamDefaultRole;
    private String speed;

    // ---- GETTER ----
    public GlobalsConfViewModel getGlobalsConfViewModel() {
        return globalsConfViewModel;
    }

    public String getActiveGlobalsTextField() {
        return activeGlobalsTextField.getText();
    }

    public String getAddNameTextField() {
        return addNameTextField.getText();
    }

    public Double getDiameterRobotTextField() {
        return Double.valueOf(diameterRobotTextField.getText());
    }

    public String getDefaultRoleTextField() {
        return defaultRoleTextField.getText();
    }

    public String getAddDefaultRoleTextField() {
        return addDefaultRoleTextField.getText();
    }

    public String getSpeedTextField() {
        return speedTextField.getText();
    }

    public String getAddSpeedTextField() {
        return addSpeedTextField.getText();
    }

    public Integer getAssistentTextField() {
        return Integer.valueOf(assistentTextField.getText());
    }

    public Integer getAddIDTextField() {
        return Integer.valueOf(addIDTextField.getText());
    }

    public int getId() {
        return teamID;
    }

    public String getName() {
        return teamName;
    }

    public String getDefaultRole() {
        return teamDefaultRole;
    }

    public String getSpeed() {
        return speed;
    }

    // ---- SETTER ---
    public void setActiveGlobalsTextField(String activeGlobals) { this.activeGlobalsTextField.setText(activeGlobals); }

    public void setAddNameTextField(String addName) {
        this.addNameTextField.setText(addName);
    }

    public void setDiameterRobotTextField(Double diameterRobot) {
        this.diameterRobotTextField.setText(diameterRobot.toString());
    }

    public void setDefaultRoleTextField(String defaultRole) {
        this.defaultRoleTextField.setText(defaultRole);
    }

    public void setAddDefaultRoleTextField(String addDefault) {
        this.addDefaultRoleTextField.setText(addDefault);
    }

    public void setSpeedTextField(String speed) {
        this.speedTextField.setText(speed);
    }

    public void setAddSpeedTextField(String addSpeed) {
        this.addSpeedTextField.setText(addSpeed);
    }

    public void setAssistentTextField(Integer assistent) {
        this.assistentTextField.setText(assistent.toString());
    }

    public void setAddIDTextField(Integer addID) {
        if (addID == null) {
            this.addIDTextField.setText(null);
        } else {
            this.addIDTextField.setText(addID.toString());
        }
    }

    public void setId(int id) {
        this.teamID = id;
    }

    public void setName(String name) {
        this.teamName = name;
    }

    public void setDefaultRole(String defaultRole) {
        this.teamDefaultRole = defaultRole;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public void setHandler(IGlobalsConfEventHandler globalsConfEventHandler) {
        this.globalsConfEventHandler = globalsConfEventHandler;
    }

    private ObservableList<GlobalsConfWindowController> tableInputData = FXCollections.observableArrayList();
    private GlobalsConfViewModel globalsConfViewModel = new GlobalsConfViewModel();
    private IGlobalsConfEventHandler globalsConfEventHandler;
    private MainWindowController mainWindowController;

    public GlobalsConfWindowController(int id, String name, String defaultRole, String speed) {
        this.teamID = id;
        this.teamName = name;
        this.teamDefaultRole = defaultRole;
        this.speed = speed;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.mainWindowController = MainWindowController.getInstance();
        iniTable();
        iniListener();
        iniButtons();
    }

    private void makeFileChooserField(TextField textField) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            textField.setText(file.getAbsolutePath());
        }
    }
    private void iniButtons() {
        saveButton.setOnAction(e -> onSave());
        exitButton.setOnAction(e -> onExit());
        activeGlobalsButton.setOnAction(e -> makeFileChooserField(activeGlobalsTextField));
        addButton.setOnAction(e -> onAdd());
        deleteButton.setOnAction(e -> onDelete());
        setDefaultConfButton.setOnAction(e -> onDefault());
    }

    private void onDefault() {
        globalsConfViewModel = globalsConfEventHandler.setDefaultConfiguration(globalsConfViewModel);
        updateGui(globalsConfViewModel);
    }

    public void loadDefaultGlobalsConfNoGui() {
        globalsConfViewModel = globalsConfEventHandler.setDefaultConfiguration(globalsConfViewModel);
    }

    private void updateGui(GlobalsConfViewModel globalsConfViewModel) {
        setDiameterRobotTextField(globalsConfViewModel.getDiameterRobotViewModel());
        setDefaultRoleTextField(globalsConfViewModel.getDefaultRoleViewModel());
        setSpeedTextField(globalsConfViewModel.getSpeedViewModel());
        setAssistentTextField(globalsConfViewModel.getAssistentViewModel());
        tableInputData.clear();
        tableInputData.addAll(globalsConfViewModel.getListOfTeamsViewModel());
    }

    private void onDelete() {
        if (teamTableView.getSelectionModel().getSelectedItem() != null) {
            GlobalsConfWindowController globalsConfWindowController = teamTableView.getSelectionModel().getSelectedItem();
            if(globalsConfWindowController.getId() == getId()) {
                tableInputData.remove(globalsConfWindowController);
            }
        }

        setAddDefaultRoleTextField(null);
        setAddIDTextField(null);
        setAddSpeedTextField(null);
        setAddNameTextField(null);

        addButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    private void onAdd() {
        GlobalsConfWindowController overwrite  = null;
        for (GlobalsConfWindowController globalsConfWindowController:tableInputData) {
            if(globalsConfWindowController.getId() == getId()) {
                overwrite = globalsConfWindowController;
            }
        }
        if(overwrite != null) {
            tableInputData.remove(overwrite);
        }
        tableInputData.add(new GlobalsConfWindowController(getId(),getName(), getDefaultRole(), getSpeed()));

        setAddDefaultRoleTextField(null);
        setAddIDTextField(null);
        setAddSpeedTextField(null);
        setAddNameTextField(null);

        addButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    private void onExit() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    private void onSave() {
        updateViewModel();
        File activeGlobalsFolder = new File(mainWindowController.getPlansPath());
        if(globalsConfViewModel.getActiveGlobalsViewModel() == null ||
                globalsConfViewModel.getActiveGlobalsViewModel() .equals("") ||
                globalsConfViewModel.getActiveGlobalsViewModel() .equals("Wrong File. Please load .conf File.")){
            globalsConfViewModel.setActiveGlobalsViewModel(activeGlobalsFolder.getParentFile().getAbsolutePath() + "/Globals.conf");
        }
        Boolean onlyIfSave = globalsConfEventHandler.save(globalsConfViewModel);
        if(onlyIfSave){
            setActiveGlobalsTextField(globalsConfViewModel.getActiveGlobalsViewModel());
        }
    }

    private void iniListener() {
        activeGlobalsTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            String extension = Files.getFileExtension(newValue);
            if (!(extension.equals("conf"))) {
                setActiveGlobalsTextField("Wrong File. Please load .conf File.");
                return;
            }
            setActiveGlobalsTextField(newValue);
            loadGlobalsConfFromFile(newValue);

            updateViewModel();
            globalsConfEventHandler.updateModel(globalsConfViewModel);
        });

        teamTableView.setOnMouseClicked(mouseEvent -> {
            if (teamTableView.getSelectionModel().getSelectedItem() != null) {
                GlobalsConfWindowController globalsConfWindowController = teamTableView.getSelectionModel().getSelectedItem();
                addIDTextField.setText(String.valueOf(globalsConfWindowController.getId()));
                addNameTextField.setText(globalsConfWindowController.getName());
                addDefaultRoleTextField.setText(globalsConfWindowController.getDefaultRole());
                addSpeedTextField.setText(globalsConfWindowController.getSpeed());
            }
        });

        addIDTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                if(newValue.length() != 0) {
                    addButton.setDisable(false);
                    deleteButton.setDisable(false);
                    setId(Integer.valueOf(newValue));
                }
            }
        });

        addNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addButton.setDisable(false);
            deleteButton.setDisable(false);
            setName(newValue);
        });

        addDefaultRoleTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addButton.setDisable(false);
            deleteButton.setDisable(false);
            setDefaultRole(newValue);
        });

        addSpeedTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addButton.setDisable(false);
            deleteButton.setDisable(false);
            setSpeed(newValue);
        });
    }

    private void updateViewModel() {
        globalsConfViewModel.setDiameterRobotViewModel(getDiameterRobotTextField());
        globalsConfViewModel.setDefaultRoleViewModel(getDefaultRoleTextField());
        globalsConfViewModel.setSpeedViewModel(getSpeedTextField());
        globalsConfViewModel.setAssistentViewModel(getAssistentTextField());
        globalsConfViewModel.setListOfTeamsViewModel(tableInputData);
    }

    private void loadGlobalsConfFromFile(String confPath) {
        String yamlAlicaConf = globalsConfEventHandler.loadAlicaConf(confPath);
        Yaml yaml = new Yaml();

        Map<String , Object> yamlMaps = yaml.load(yamlAlicaConf);
        Map<String , Object> globalsMap = (Map<String, Object>) yamlMaps.get("Globals");
        Map<String , Object> dimensionsMap = (Map<String, Object>) globalsMap.get("Dimensions");
        Map<String , Object> teamMap = (Map<String, Object>) globalsMap.get("Team");
        Map<String , Object> teamDefaultMap = (Map<String, Object>) globalsMap.get("TeamDefault");
        Map<String , Object> rolePriorityMap = (Map<String, Object>) globalsMap.get("RolePriority");

        setDiameterRobotTextField(Double.valueOf(dimensionsMap.get("DiameterRobot").toString()));

        setDefaultRoleTextField(teamDefaultMap.get("DefaultRole").toString());
        setSpeedTextField(teamDefaultMap.get("Speed").toString());

        setAssistentTextField(Integer.valueOf(rolePriorityMap.get("Assistent").toString()));

        tableInputData.clear();
        List<String> nameOfTeams = new ArrayList<>();
        teamMap.forEach((k, v) -> nameOfTeams.add(k));

        for (String name: nameOfTeams) {
            Map<String , Object> nameTeamMap = (Map<String, Object>) teamMap.get(name);
            setName(name);
            setId(Integer.parseInt(nameTeamMap.get("ID").toString()));
            setDefaultRole(nameTeamMap.get("DefaultRole").toString());
            setSpeed(nameTeamMap.get("Speed").toString());;
            tableInputData.add(new GlobalsConfWindowController(getId(),getName(), getDefaultRole(), getSpeed()));
        }
    }

    private void iniTable() {
        //set editable
//        teamNameTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        teamIDTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
//        teamSpeedTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        teamDefaultRoleTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        teamIDTableColumn.setCellValueFactory(new PropertyValueFactory<GlobalsConfWindowController, Integer>("id"));
        teamNameTableColumn.setCellValueFactory(new PropertyValueFactory<GlobalsConfWindowController, String>("name"));
        teamDefaultRoleTableColumn.setCellValueFactory(new PropertyValueFactory<GlobalsConfWindowController, String>("defaultRole"));
        teamSpeedTableColumn.setCellValueFactory(new PropertyValueFactory<GlobalsConfWindowController, String>("speed"));
        teamTableView.setItems(tableInputData);
    }
}
