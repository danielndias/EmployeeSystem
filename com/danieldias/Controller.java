package com.danieldias;

import com.danieldias.pdf.CreatePDF;
import com.danieldias.pdf.CreationPDF;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.Scanner;

/**
 * Controller class for the Employee Management Application.
 *
 * @author Daniel Dias
 */
public class Controller {

    private Employee currentEmployee;


    //The main GridPane
    @FXML
    public GridPane mainGrid;

    //VBox for swap the central area between the the main
    //application and the search screen
    @FXML
    public VBox mainScreen, search;

    //TextFields of the main application screen and the search screen
    @FXML
    public TextField txtFName, txtLName, txtId, txtSal, txtSearch;

    //Combo Box with the ENUM Constants corresponding to the departments
    @FXML
    public ComboBox<Department> cmbDept;

    //Combo Box with the ENUM Constants corresponding to the positions
    @FXML
    public ComboBox<Position> cmbPos;

    //Lateral and search TableViews
    @FXML
    public TableView<Employee> tblView, tblSearch;

    //Columns of the TableViews
    @FXML
    public TableColumn<Employee, String> colEmpId, colEmpLName, colEmpName, colSearchId, colSearchLName, colSearchFName;

    //Application Buttons
    @FXML
    public Button btnSave, btnCancel, btnExit, btnAdd, btnEdit, btnDel, btnSearch;

    //RadioButtons of the search screen
    @FXML
    public RadioButton radFName, radLName;

    //String indicating the current active screen in the central VBox
    private String current = "mainScreen";

    //List that stores the all employees
    ObservableList<Employee> employees = FXCollections.observableArrayList();

    //List that stores the employees that match the searching
    //criteria in the search screen
    ObservableList<Employee> searchResult = FXCollections.observableArrayList();


    /**
     * Method executed when the application loads. This method populates the
     * two ComboBoxes with all the ENUM Constants corresponding to the departments and
     * and the positions.  Also creates a new File Object that will be read by
     * the application and creates a Listener to change the data displayed in the
     * central screen to show the information about the selected employee. Then, calls
     * the showEmployees method.
     *
     */
    @FXML
    public void initialize() {

        //Disable the editing feature from the labels in the central VBox
        txtSal.setEditable(false);
        txtId.setEditable(false);
        txtFName.setEditable(false);
        txtLName.setEditable(false);
        cmbDept.setDisable(true);
        cmbPos.setDisable(true);
        btnCancel.setDisable(true);
        btnSave.setDisable(true);

        //Populates the ComboBoxes with all the ENUM Constants corresponding
        //to the departments and the positions
        cmbDept.setItems(FXCollections.observableArrayList(Department.values()));
        cmbPos.setItems(FXCollections.observableArrayList(Position.values()));

        //Creates a listener to show in the central VBox the information
        //about the employee selected in the left VBox
        tblView.getSelectionModel().selectedItemProperty().addListener((employees, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(String.valueOf(newSelection.getId()));
                txtLName.setText(newSelection.getLName());
                txtFName.setText(newSelection.getFName());
                cmbPos.setValue(newSelection.getPosition());
                cmbDept.setValue(newSelection.getDepartment());
                txtSal.setText(newSelection.strSalary());

                currentEmployee = newSelection;
            }

        });

        //Creates a new File object and reads the employees.csv file.
        //If the file doesn't exists, a new one is created.
        File file = new File("data/employees.csv");
        if (!file.exists()) {
            try {
                PrintWriter outputFile = new PrintWriter(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        showEmployees(file);



    }

    /**
     * Method to read the created file and populates the left TableView.
     *
     * @param file the File object created on the initialize method
     */
    private void showEmployees(File file) {

        String[] fields = {};
        Employee employee;
        try {
            Scanner input = new Scanner(file);
            String line = input.next();
            while (input.hasNext()) {
                employee = new Employee();
                line = input.next();
                fields = getFields(line);
                employee.setId(Integer.parseInt(fields[0]));
                employee.setLName(fields[1]);
                employee.setFName(fields[2]);
                employee.setPosition(Position.getPosition(Integer.parseInt(fields[3])));
                employee.setDept(Department.getDepartment(Integer.parseInt(fields[4])));
                employee.setSalary(Double.parseDouble(fields[5]));
                employees.add(employee);
            }
        input.close();
    } catch (Exception ex) {

    }

        //Populates the left TableView
        colEmpId.setCellValueFactory(new PropertyValueFactory<Employee, String>("id"));
        colEmpLName.setCellValueFactory(new PropertyValueFactory<Employee, String>("lName"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<Employee, String>("fName"));
        tblView.setItems(employees);
        tblView.refresh();

    }

    /**
     * Delete the selected employee. Generates an confirmation windows
     * to make sure the user wants to delete the employee
     *
     * @param event
     */
    public void deleteEmployee  (ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to remove this employee?", ButtonType.YES,
                ButtonType.NO);
        alert.setTitle("Remove Employee");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            employees.remove(currentEmployee);
        }
    }

    /**
     * Method used to create a new Employee. Empty all the fields
     * and enable then, allowing the user to insert the data.
     * @param event
     */
    public void addEmployee(ActionEvent event) {
        int newId= getMax() + 1;
        txtId.setText(String.valueOf(newId));
        txtLName.clear();
        txtLName.setEditable(true);
        txtFName.clear();
        txtFName.setEditable(true);
        cmbDept.setValue(null);
        cmbDept.setDisable(false);
        cmbPos.setValue(null);
        cmbPos.setDisable(false);
        txtSal.clear();
        txtSal.setEditable(true);
        tblView.setDisable(true);

        //Disable all unnecessary buttons and enable the save and cancel buttons
        btnAdd.setDisable(true);
        btnEdit.setDisable(true);
        btnDel.setDisable(true);
        btnSearch.setDisable(true);
        btnExit.setDisable(true);
        btnSave.setDisable(false);
        btnCancel.setDisable(false);
    }

    /**
     * Method used to edit the currently selectedd Employee.
     * Enable all the fields, allowing the user to change the data.
     * @param event
     */
    public void editEmployee(ActionEvent event) {

        txtLName.setEditable(true);
        txtFName.setEditable(true);
        cmbDept.setDisable(false);
        cmbPos.setDisable(false);
        txtSal.setEditable(true);

        //Change the salary text from the currency format to regular double format
        txtSal.setText(String.valueOf(currentEmployee.getSalary()));
        tblView.setDisable(true);

        //Disable all unnecessary buttons and enable the save and cancel buttons
        btnAdd.setDisable(true);
        btnEdit.setDisable(true);
        btnDel.setDisable(true);
        btnSearch.setDisable(true);
        btnExit.setDisable(true);
        btnSave.setDisable(false);
        btnCancel.setDisable(false);
    }

    /**
     * Method used to save the information typed by the user.
     * The method checks if the user is inserting a new employee or
     * editing an existing one. If the user is adding a new employee,
     * a new Employee object is created and added to the list. If the user
     * is editing a current employee, the attributes of the
     * corresponding object is changed.
     * If any invalid information is typed, an error alert is shown with
     * the proper message informing the user what he typed wrong.
     *
     * @param event
     */
    public void saveEmployee(ActionEvent event) {
        try {

            if (Integer.parseInt(txtId.getText())>getMax()) {
                currentEmployee = new Employee();
                currentEmployee.setId(Integer.parseInt(txtId.getText()));
                currentEmployee.setLName(txtLName.getText());
                currentEmployee.setFName(txtFName.getText());
                currentEmployee.setDept(cmbDept.getValue());
                currentEmployee.setPosition(cmbPos.getValue());
                currentEmployee.setSalary(Double.parseDouble(txtSal.getText()));
                employees.add(currentEmployee);
            } else {
                int idx = employees.indexOf(currentEmployee);
                currentEmployee.setLName(txtLName.getText());
                currentEmployee.setFName(txtFName.getText());
                currentEmployee.setDept(cmbDept.getValue());
                currentEmployee.setPosition(cmbPos.getValue());
                currentEmployee.setSalary(Double.parseDouble(txtSal.getText()));
                employees.set(idx, currentEmployee);
            }

            //Removes again the editing capability of the labels in the central VBox
            txtSal.setEditable(false);
            txtId.setEditable(false);
            txtFName.setEditable(false);
            txtLName.setEditable(false);
            cmbDept.setDisable(true);
            cmbPos.setDisable(true);

            //Re-enable the buttons and disable the save and cancel buttons
            btnAdd.setDisable(false);
            btnEdit.setDisable(false);
            btnDel.setDisable(false);
            btnSearch.setDisable(false);
            btnExit.setDisable(false);
            btnSave.setDisable(true);
            btnCancel.setDisable(true);
            tblView.setDisable(false);

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Format");
            alert.setHeaderText(null);
            alert.setContentText("Salary must be a valid positive numeric value");
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Data Entry Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    /**
     * Method that receives a search parameter from the user, that could be a first or last
     * name, and return all the records that match the criteria.
     *
     * @param event
     */
    @FXML
    public void search(ActionEvent event) {
        String searchParam = txtSearch.getText();

        if (radLName.isSelected()) {
            for (int i = 0; i < employees.size(); i++) {
                if (employees.get(i).getLName().toLowerCase().contains(searchParam)) {
                    searchResult.add(employees.get(i));
                }
            }
        } else {
            for (int i = 0; i < employees.size(); i++) {
                if (employees.get(i).getFName().toLowerCase().contains(searchParam)) {
                    searchResult.add(employees.get(i));
                }
            }
        }

        if (searchResult.size() >= 1) {
            tblSearch.getSelectionModel().selectedItemProperty().addListener((searchResult, oldSel, newSel) -> {
                if (newSel != null) {
                    txtLName.setText(newSel.getLName());
                    txtId.setText(String.valueOf(newSel.getId()));
                    txtFName.setText(newSel.getFName());
                    cmbPos.setValue(newSel.getPosition());
                    cmbDept.setValue(newSel.getDepartment());
                    txtSal.setText(newSel.strSalary());

                    currentEmployee = newSel;
                }

            });


            colSearchId.setCellValueFactory(new PropertyValueFactory<Employee, String>("id"));
            colSearchLName.setCellValueFactory(new PropertyValueFactory<Employee, String>("lName"));
            colSearchFName.setCellValueFactory(new PropertyValueFactory<Employee, String>("fName"));
            tblSearch.setItems(searchResult);
            tblSearch.refresh();
            tblSearch.setEditable(false);

        } else {

            //If the search returns no result, and information alert is shown.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("No Results Found!");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtSearch.requestFocus();
            txtSal.selectAll();
        }


    }


    /**
     * The method removes all information the user typed without saving and
     * remove the editing capability of the labels in the central VBox
     *
     * @param event
     */
    @FXML
    public void cancel(ActionEvent event) {
        txtLName.clear();
        txtLName.setEditable(false);
        txtFName.clear();
        txtFName.setEditable(false);
        cmbDept.setValue(null);
        cmbDept.setDisable(true);
        cmbPos.setValue(null);
        cmbPos.setDisable(true);
        txtSal.clear();
        txtSal.setEditable(false);

        //Re-enable the buttons and disable the save and cancel buttons
        btnAdd.setDisable(false);
        btnEdit.setDisable(false);
        btnDel.setDisable(false);
        btnSearch.setDisable(false);
        btnExit.setDisable(false);
        btnSave.setDisable(true);
        btnCancel.setDisable(true);
        tblView.setDisable(false);
    }

    /**
     * Swap the central VBox, showing the search screen
     *
     * @param event
     */
    @FXML
    private void swapToSearch(ActionEvent event) {
        if (current.equals("mainScreen")) {
            mainGrid.getChildren().set(1,search);
            current = "search";
        }

        //Disable all unnecessary buttons and the left TableView
        btnAdd.setDisable(true);
        btnEdit.setDisable(true);
        btnDel.setDisable(true);
        btnSearch.setDisable(true);
        tblView.setDisable(true);
    }

    /**
     * Swap the central VBox, exiting the search screen
     * showing the selected employee information again
     *
     * @param event
     */
    @FXML
    private void exitSearch(ActionEvent event) {
        if (current.equals("search")) {
            mainGrid.getChildren().set(1,mainScreen);
            current = "mainScreen";
        }

        //Re-enable the buttons and the left TableView
        btnAdd.setDisable(false);
        btnEdit.setDisable(false);
        btnDel.setDisable(false);
        btnSearch.setDisable(false);
        btnExit.setDisable(false);
        tblView.setDisable(false);
    }

    /**
     * Method to close the application. Shows a confirmation and, if the user
     * clicks in YES, calls the writeFile method to save the List in the
     * employees file and quit the application.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void exit(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you wish to exit?", ButtonType.YES,
                ButtonType.NO);
        alert.setTitle("Exit Program");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            File file = new File("data/employees.csv");
            writeFile(file);
            System.exit(0);
        }
    }

    /**
     * Method used to write the employee list into the employees file
     *
     * @param file the employees file
     * @throws FileNotFoundException
     */
    public void writeFile(File file) throws FileNotFoundException {
        PrintWriter print = new PrintWriter(file);
        print.println("id,lName,fName,position,dept,salary");
        for (int i = 0; i < employees.size(); i++) {
            Employee e = employees.get(i);
            print.println(e.toFileString());
        }
        print.close();
    }

    /**
     * Retrieves the higher ID number.
     *
     * @return the higher ID number
     */
    private int getMax() {
        int max = 0;
        for (Employee employee : employees) {
            if (employee.getId() > max)
                max = employee.getId();
        }
        return  max;
    }

    /**
     * Retrieves a String array containing the fields of the employees file.
     * Each element of the array is one field of one record.
     *
     * @param line the employees file
     *
     * @return the fields String array.
     */
    private String[] getFields(String line) {
        String[] fields = {};
        fields = line.split(",");
        return fields;
    }

    /**
     * Creates a Report as a PDF File containing the total number of employees and the
     * total cost of their salaries. After the PDF be generated, an information alert
     * is generated telling the user where the report is located.
     *
     * @param event
     */
    @FXML public void createPDF(ActionEvent event) {
        String sourceReportTemplate;
        String finalPDFName;
        sourceReportTemplate = "source";
        CreationPDF report = new CreationPDF(sourceReportTemplate);

        //Strings containing the name of the fields created in the template report
        String numberBoxName = "empNum";
        String costBoxName = "cost";

        //Creating a variable for the number of employees box and setting its value to the employees List size.
        int numberBoxValue = employees.size();

        //Creating a variable for the total cost box and setting its value to the sum of all salaries
        double employeeCost = 0;
        for (Employee e: employees) {
            employeeCost += e.getSalary();
        }

        //Creating a DecimalFormat object to format the cost in currency format
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String costBoxValue = "$" + formatter.format(employeeCost);

        report.writeTextBoxValue(numberBoxName,numberBoxValue, costBoxName, costBoxValue);

        //Creating a new object from the CreatePDF class, which is the class used to create the PDF File.
        CreatePDF pdfCreate = new CreatePDF();
        finalPDFName = pdfCreate.getPDFName();
        pdfCreate.savePDF(report.getDoc(), finalPDFName);

        //Creating an alert informing the user where the generated report is located
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("You can find the Report in the Employee_Cost_Report folder in your Desktop");
        alert.setHeaderText("Report Generated Successfully");
        alert.showAndWait();
    }


}
