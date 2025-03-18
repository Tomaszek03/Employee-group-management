package aoim.zad4;

import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.converter.DoubleStringConverter;

import java.util.*;

public class MainController {

    @FXML
    private Button add_group;
    @FXML
    private Button delete_group;
    @FXML
    private ComboBox<ClassEmployee> combo_box;
    @FXML
    private TextField group_name;
    @FXML
    private TextField max_person;
    @FXML
    private TableColumn<Employee, String> nameColumn;
    @FXML
    private TableColumn<Employee, Double> salaryColumn;
    @FXML
    private TableColumn<Employee, EmployeeCondition> stateColumn;
    @FXML
    private TableColumn<Employee, String> surnameColumn;
    @FXML
    private TableView<Employee> tableEmployees;
    @FXML
    private TableColumn<Employee, Integer> yearColumn;
    @FXML
    private Button addEmployeeButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private ComboBox<EmployeeCondition> stateComboBox;
    @FXML
    private TextField yearTextField;
    @FXML
    private TextField salaryTextField;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableColumn<Employee, Employee> deleteColumn;
    @FXML
    private Label amount;
    @FXML
    private Label amountLabel1;
    @FXML
    private Label amountLabel2;
    @FXML
    private Label amountLabel3;
    @FXML
    private Label maxAmount;
    @FXML
    private Label percentage;
    @FXML
    private Button addRateButton;
    @FXML
    private Label rateAverage;
    @FXML
    private Label rateAverageLabel;
    @FXML
    private TextField rateComment;
    @FXML
    private Label rateCount;
    @FXML
    private Label rateCountLabel;
    @FXML
    private Label rateLabel;
    @FXML
    private Slider rateSlider;
    @FXML
    private Button exportCSVButton;


    EmployeeDao employeeDao = new EmployeeDao();
    GroupsDao groupsDao = new GroupsDao();
    RateDao rateDao = new RateDao();
    Data data = new Data();

    private ObservableList<ClassEmployee> groupsObservableList;
    private ListProperty<ClassEmployee> groupsListProperty;
    private ClassEmployee group;

    public void initialize() {
        // Inicjalizacja grup pracowniczych
        Collection<ClassEmployee> groupsCollection = data.loadGroups();
        groupsListProperty = new SimpleListProperty<>();
        groupsObservableList = FXCollections.observableArrayList(groupsCollection);
        groupsListProperty.set(groupsObservableList);
        combo_box.itemsProperty().bindBidirectional(groupsListProperty);

        //Inicjalizacja tabel pracowników
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("imie"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("stan"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("rok_urodzenia"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("wynagrodzenie"));
        combo_box.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadGroupToTable(newSelection);
                setAmountText();
                setRateText();
            }
        });
        stateComboBox.getItems().addAll(EmployeeCondition.values());
        initBindings();

        this.salaryColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        this.stateColumn.setCellFactory(TextFieldTableCell.forTableColumn(new EnumStringConverter<>(EmployeeCondition.class)));

        searchTextField.getOnKeyReleased();
    }

    private void initBindings() {
        add_group.disableProperty().bind(group_name.textProperty().isEmpty().or(max_person.textProperty().isEmpty()));
        delete_group.disableProperty().bind(combo_box.valueProperty().isNull());
        searchTextField.disableProperty().bind(combo_box.valueProperty().isNull());

        addEmployeeButton.disableProperty().bind(combo_box.valueProperty().isNull()
                .or(nameTextField.textProperty().isEmpty())
                .or(surnameTextField.textProperty().isEmpty())
                .or(stateComboBox.valueProperty().isNull())
                .or(yearTextField.textProperty().isEmpty())
                .or(salaryTextField.textProperty().isEmpty())
        );
        addRateButton.disableProperty().bind(combo_box.valueProperty().isNull());

        amountLabel1.visibleProperty().bind(combo_box.valueProperty().isNotNull());
        amountLabel2.visibleProperty().bind(combo_box.valueProperty().isNotNull());
        amountLabel3.visibleProperty().bind(combo_box.valueProperty().isNotNull());
        amount.visibleProperty().bind(combo_box.valueProperty().isNotNull());
        maxAmount.visibleProperty().bind(combo_box.valueProperty().isNotNull());
        percentage.visibleProperty().bind(combo_box.valueProperty().isNotNull());

        addRateButton.visibleProperty().bind(combo_box.valueProperty().isNotNull());
        rateLabel.visibleProperty().bind(combo_box.valueProperty().isNotNull());
        rateComment.visibleProperty().bind(combo_box.valueProperty().isNotNull());
        rateAverage.visibleProperty().bind(combo_box.valueProperty().isNotNull());
        rateAverageLabel.visibleProperty().bind(combo_box.valueProperty().isNotNull());
        rateCount.visibleProperty().bind(combo_box.valueProperty().isNotNull());
        rateCountLabel.visibleProperty().bind(combo_box.valueProperty().isNotNull());
        rateSlider.visibleProperty().bind(combo_box.valueProperty().isNotNull());
    }

    @FXML
    private void addGroupAction() {
        String name = group_name.getText();
        int max;

        try {
            max = Integer.parseInt(max_person.getText());
        } catch (NumberFormatException e) {
            DialogUtils.dialogIncorrectAmount();
            return;
        }

        if (groupsDao.searchGroupId(name) != 0) {
            DialogUtils.dialogGroupExists(name);
        } else if (max < 1) {
            DialogUtils.dialogIncorrectAmount();
        } else {
            DialogUtils.dialogGroupAdded(name, max);
            groupsDao.addGroup(name, max);
            groupsObservableList.add(groupsDao.getGroup(name));
            group_name.clear();
            max_person.clear();
        }
    }

    @FXML
    private void deleteGroupAction() {
        group = combo_box.getSelectionModel().getSelectedItem();
        Optional<ButtonType> result = DialogUtils.dialogGroupRemovedConfirmation(group.toString());

        if (result.get() == ButtonType.OK) {
            groupsDao.deleteGroup(group.toString());
            data.loadGroups();
            groupsObservableList.remove(combo_box.getSelectionModel().getSelectedItem());
            combo_box.getSelectionModel().clearSelection();
            tableEmployees.setItems(FXCollections.observableArrayList());
            DialogUtils.dialogGroupRemoved(group.toString());
        }
    }

    public void loadGroupToTable(ClassEmployee selectedGroup) {
        List<Employee> employes = data.loadEmployees(selectedGroup.toString());
        if (employes != null) {
            ObservableList<Employee> employeesObservableList = FXCollections.observableArrayList(employes);
            addDeleteButton();
            tableEmployees.setItems(employeesObservableList);
            data.loadGroups();
        } else {
            tableEmployees.setItems(FXCollections.observableArrayList());
        }
    }

    private void addDeleteButton() {
        this.deleteColumn.setCellFactory(param -> new TableCell<Employee, Employee>() {
            Button button = createButton("delete.png");

            @Override
            protected void updateItem(Employee item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }
                if (!empty) {
                    setGraphic(button);
                    button.setOnAction(event -> {
                        Employee deletedEmployee = getTableView().getItems().get(getIndex());
                        group = combo_box.getSelectionModel().getSelectedItem();
                        int id = employeeDao.getEmployeeId(deletedEmployee.getImie(), deletedEmployee.getNazwisko(), deletedEmployee.getRok_urodzenia());
                        employeeDao.removeEmployee(id);
                        setAmountText();
                        loadGroupToTable(group);
                    });
                }
            }
        });
    }

    @FXML
    void exitAcction() {
        Optional<ButtonType> result = DialogUtils.dialogExitConfirmation();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
            System.exit(0);
        }
    }

    public void searchAction() {
        group = combo_box.getSelectionModel().getSelectedItem();
        ObservableList<Employee> filteredList = FXCollections.observableArrayList();
        String searchText = searchTextField.getText();
        filteredList.addAll(employeeDao.searchEmployees(searchText, groupsDao.searchGroupId(combo_box.getSelectionModel().getSelectedItem().toString())));
        tableEmployees.setItems(filteredList);
        addDeleteButton();
    }

    public void addEmployeeAction() {
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        EmployeeCondition state = stateComboBox.getValue();
        int year;
        double salary;
        ClassEmployee group = combo_box.getSelectionModel().getSelectedItem();
        int max = group.max.get();
        int actual = group.actual_size.get();
        int groupId = groupsDao.searchGroupId(combo_box.getSelectionModel().getSelectedItem().toString());

        try {
            year = Integer.parseInt(yearTextField.getText());
        } catch (NumberFormatException e) {
            DialogUtils.dialogIncorrectYear();
            return;
        }

        try {
            salary = Double.parseDouble(salaryTextField.getText());
        } catch (NumberFormatException e) {
            DialogUtils.dialogIncorrectSalary();
            return;
        }

        if (year <= 0) DialogUtils.dialogIncorrectYear();
        else if (salary <= 0) DialogUtils.dialogIncorrectSalary();
        else if (actual == max) DialogUtils.dialogGroupMax();
        else {
            employeeDao.addEmployee(name, surname, state, year, salary, groupId);
            loadGroupToTable(group);
            setAmountText();
            clearInputFields();
        }
    }

    public void clearInputFields() {
        nameTextField.clear();
        surnameTextField.clear();
        stateComboBox.setValue(null);
        yearTextField.clear();
        salaryTextField.clear();
    }

    private Button createButton(String path) {
        Button button = new Button();
        Image image = new Image(this.getClass().getResource(path).toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        button.setGraphic(imageView);
        button.getStyleClass().add("deleteButton");
        return button;
    }

    public void onEditCommitSalary(TableColumn.CellEditEvent<Employee, Double> employeeDoubleCellEditEvent) {
        Employee editedEmployee = employeeDoubleCellEditEvent.getRowValue();
        double newSalary = employeeDoubleCellEditEvent.getNewValue();

        if (newSalary <= 0) {
            DialogUtils.dialogIncorrectSalary();
            employeeDoubleCellEditEvent.getTableView().refresh();
        } else
            employeeDao.changeSalary(editedEmployee, newSalary, groupsDao.searchGroupId(combo_box.getSelectionModel().getSelectedItem().toString()));
    }

    public void onEditCommitState(TableColumn.CellEditEvent<Employee, EmployeeCondition> employeeEmployeeConditionCellEditEvent) {
        Employee editedEmployee = employeeEmployeeConditionCellEditEvent.getRowValue();
        EmployeeCondition newState = employeeEmployeeConditionCellEditEvent.getNewValue();
        employeeDao.changeCondition(editedEmployee, newState, groupsDao.searchGroupId(combo_box.getSelectionModel().getSelectedItem().toString()));
    }

    public void setAmountText() {
        if (combo_box.getSelectionModel().getSelectedItem() != null) {
            ClassEmployee group = combo_box.getSelectionModel().getSelectedItem();

            int actual = groupsDao.calculateGroup(groupsDao.searchGroupId(combo_box.getSelectionModel().getSelectedItem().toString()));
            int max = group.max.get();

            amount.setText(String.valueOf(actual));
            maxAmount.setText(String.valueOf(max));

            double percentageAmount = ((double) actual / max) * 100;
            percentage.setText(String.format("%.2f", percentageAmount) + "%");
        }
    }

    @FXML
    public void addRateAction() {
        int rate = (int) rateSlider.getValue();
        String commment = rateComment.getText();
        int groupId = groupsDao.searchGroupId(combo_box.getSelectionModel().getSelectedItem().toString());
        rateDao.addRate(groupId, rate, commment);
        setRateText();
    }

    public void setRateText() {
        int groupId = groupsDao.searchGroupId(combo_box.getSelectionModel().getSelectedItem().toString());
        int rate = rateDao.rateCount(groupId);
        double rateAvg = rateDao.rateAvg(groupId);
        rateCount.setText(String.valueOf(rate));
        rateAverage.setText(String.format("%.2f", rateAvg));
    }

    @FXML
    public void exportCSVAction() {
        data.exportToCSV();
        DialogUtils.dialogDataExport();
    }
}
