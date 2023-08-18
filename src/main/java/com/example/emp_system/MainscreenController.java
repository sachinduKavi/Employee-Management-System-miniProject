package com.example.emp_system;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DecimalStyle;
import java.util.*;


public class MainscreenController {
    @FXML
    Label user_name, heading;
    Button button;
    @FXML
    Button dashBoard, employeeBtn, attendanceBtn, projectBtn;

    @FXML
    AnchorPane dashBoardPane, employeePane, attendancePane, projectPane, mainAnchor, paySheetContainer;
    @FXML
    TableView<EmployeeData> employeeTable;
    @FXML
    TableColumn<EmployeeData, String> empIDC, nameC, emailC, positionC, departmentC, nicC, mobileNumberC;
    @FXML
    TableColumn<EmployeeData, Integer> payRateC;
    EmployeeData adminData;

    @FXML
    Label place01, place02, place03, place04, place05, place06, count, emptyError;
    @FXML
    VBox departmentContainer;
    @FXML
    ChoiceBox monthList;
    @FXML
    TextField year, employeeNum;
    @FXML
    Label empID, empName, deptName, positionName, userNic, userMobile, noDays, payRates, subTotal, bonus, grossPay, deduction, netPay, monthName;
    String currentEMP = "";

    public void initMainScreen(EmployeeData adminData) {
        // Setting up user interface
        user_name.setText(adminData.getName());
        this.adminData = adminData;
        String strNum, curDate = DateTimeFormatter.ofPattern("yyy-MM").format(LocalDate.now());
        String[] dayNow = curDate.split("-");
        year.setText(dayNow[0]);
        monthList.setValue(dayNow[1]);
        for (int i = 1; i <= 12; i++) {
            strNum = Integer.toString(i);
            strNum = (strNum.length() < 2) ? "0" + strNum : strNum;
            monthList.getItems().add(strNum);
        }
        initDashboard();
    }

    protected void displayEmployees(EmployeeData[] employeeDataList) {
        employeeTable.getItems().clear();
        ObservableList<EmployeeData> employees = employeeTable.getItems();
        Collections.addAll(employees, employeeDataList);
        employeeTable.setItems(employees);

        // Employee Table columns declaration
        empIDC.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("employeeID"));
        nameC.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("name"));
        emailC.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("email"));
        positionC.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("position"));
        departmentC.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("department"));
        nicC.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("NIC"));
        mobileNumberC.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("mobileNumber"));
        payRateC.setCellValueFactory(new PropertyValueFactory<EmployeeData, Integer>("payRate"));
    }

    protected void initEmployees() {
        FetchEmployees fetchEmployees = new FetchEmployees();

        if (fetchEmployees.getAllEmployees() != null)
            displayEmployees(fetchEmployees.getAllEmployees());
    }

    public void initDashboard() {
        // Ranking employees
        Label[] places = {place01, place02, place03, place04, place05, place06};
        CreateConnection connection = new CreateConnection(CreateConnection.DOMAIN + "employee_details/rankOrder", new HashMap<>(), HttpURLConnection.HTTP_OK, "GET");
        JsonObject jsonObject = connection.getJsonResponse();

        if (jsonObject != null) {
            JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
            String user_name, empNO;
            int rank;
            System.out.println("Json Array: " + jsonArray);

            for (int i = 0; i < jsonArray.size(); i++) {
                empNO = jsonArray.get(i).getAsJsonObject().get("employee_id").getAsString();
                user_name = jsonArray.get(i).getAsJsonObject().get("name").getAsString();
                rank = jsonArray.get(i).getAsJsonObject().get("rank").getAsInt();

                places[i].setText(empNO + " | " + rank + " | " + user_name);
            }
        }

        // Total employee count
        connection = new CreateConnection(CreateConnection.DOMAIN + "employee_details/getEmployeeCount", new HashMap<>(), HttpURLConnection.HTTP_OK, "GET");
        int tCount = connection.getJsonResponse().get("count").getAsInt();
        count.setText(Integer.toString(tCount));


        // Department section
        CreateConnection connDept = new CreateConnection(CreateConnection.DOMAIN + "department/details", new HashMap<>(), HttpURLConnection.HTTP_OK, "GET");
        JsonArray deptJsonArray = connDept.getJsonResponse().get("data").getAsJsonArray();

        departmentContainer.getChildren().clear();
        for (int i = 0; i < deptJsonArray.size(); i++) {
            JsonObject singleDept = deptJsonArray.get(i).getAsJsonObject();
            Label label = new Label("Department: " + singleDept.get("name").getAsString() + "\n" + "Employee Count: " + singleDept.get("numEmployees").getAsString() + "\n" +
                    "HOD: " + singleDept.get("headName").getAsString());
            label.setMinHeight(70);
            label.getStyleClass().add("labels");
            departmentContainer.getChildren().add(label);
        }


    }

    // Generating pay sheets
    public void intiPaySheets() {
        employeeNum.setText("E");
        for (int i = 0; i < paySheetContainer.getChildren().size(); i++)
            paySheetContainer.getChildren().get(i).setVisible(false);
    }

    public void addEmployee(MouseEvent event) {
        System.out.println("New Window");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("employeeadd.fxml"));
            Stage stage = new Stage();

            stage.isAlwaysOnTop();
            stage.initOwner(((Node) event.getTarget()).getScene().getWindow());
            stage.setTitle("New Employee");

            // Sending admin data to the controller
            mainAnchor.setDisable(true);

            stage.setScene(new Scene(fxmlLoader.load()));

            AddNewEmployee addNewEmployee = fxmlLoader.getController();
            addNewEmployee.initAddEmployees(this.adminData, stage);

            stage.showAndWait();
            mainAnchor.setDisable(false);
            initEmployees();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void mainButton(ActionEvent event) {
        Button[] buttons = {dashBoard, employeeBtn, attendanceBtn, projectBtn};
        AnchorPane[] panes = {dashBoardPane, employeePane, attendancePane, projectPane};
        // Resetting all styles in main buttons
        for (Button btn : buttons) {
            btn.getStyleClass().clear();
            btn.getStyleClass().add("buttons");
        }
        for (AnchorPane p : panes) {
            p.setVisible(false);
        }
        String buttonStr = event.getTarget().toString().substring(event.getTarget().toString().indexOf(']') + 1);
        String headingStr = "Dashboard";
        switch (buttonStr) {
            case "'DASHBOARD'":
                headingStr = "Dashboard";
                dashBoardPane.setVisible(true);
                initDashboard();
                break;
            case "'EMPLOYEE'":
                headingStr = "Employee";
                employeePane.setVisible(true);
                initEmployees();
                break;
            case "'PAYSHEETS'":
                headingStr = "Pay Sheets Generator";
                attendancePane.setVisible(true);
                intiPaySheets();
                break;
            case "'PROJECT'":
                headingStr = "Project";
                projectPane.setVisible(true);
                break;
        }
        heading.setText(headingStr);

        System.out.println(buttonStr);
        // Applying click styles
        button = (Button) event.getSource();
        button.getStyleClass().add("clickBtn");

    }

    @FXML
    public void deleteRecord(MouseEvent event) {
        try {
            String empNo = employeeTable.getSelectionModel().getSelectedItem().getEmployeeID();
            System.out.println("Selected Item: " + empNo);

            // Confirming delete and deleting the record
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Delete Employee " + employeeTable.getSelectionModel().getSelectedItem().getName() + "\nEmployee number: " + empNo + "?");
            alert.setTitle("Delete Record");
            alert.showAndWait().ifPresent(res -> {
                if (res.getButtonData().isDefaultButton()) {
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("empNo", empNo);
                    CreateConnection connection = new CreateConnection(CreateConnection.DOMAIN + "employee_details/deleteEmp", params, HttpURLConnection.HTTP_ACCEPTED, "DELETE");
                    connection.getResponse();
                    initEmployees();
                }

            });
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selection Error");
            alert.setHeaderText("Please select record to Delete");
            alert.show();
        }
    }

    @FXML
    public void updateRecord(MouseEvent event) {
        System.out.println("Updating record");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("employeeadd.fxml"));
        Stage updateStage = new Stage();
        updateStage.isAlwaysOnTop();
        updateStage.initOwner(((Node) event.getTarget()).getScene().getWindow());
        updateStage.setTitle("Update Employee");

        try {
            updateStage.setScene(new Scene(fxmlLoader.load()));
            AddNewEmployee addNewEmployee = fxmlLoader.getController();
            try {
                addNewEmployee.updateEmployeeInit(employeeTable.getSelectionModel().getSelectedItem(), event, updateStage);

                updateStage.showAndWait();
                mainAnchor.setDisable(false);
                initEmployees();
            } catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Selection Error");
                alert.setHeaderText("Please select record to update");
                alert.show();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void generateBtn(ActionEvent e) {
        currentEMP = employeeNum.getText();
        intiPaySheets();
        if (currentEMP.length() == 4) {
            LinkedHashMap<String, Object> params = new LinkedHashMap<>();
            params.put("empNo", currentEMP);
            params.put("year", year.getText());
            params.put("month", monthList.getValue().toString());
            CreateConnection connection = new CreateConnection(CreateConnection.DOMAIN + "attendance/checkAvailable", params, 200, "GET");
            JsonObject jsonObject = connection.getJsonResponse();
            System.out.println(connection.responseCode);
            if (connection.responseCode != 404) {
                JsonObject data = jsonObject.get("data").getAsJsonArray().get(0).getAsJsonObject();
                // Setting up the pay sheet
                empID.setText(data.get("empID").getAsString());
                empName.setText(data.get("fullName").getAsString());
                deptName.setText(data.get("deptName").getAsString());
                positionName.setText(data.get("position").getAsString());
                userNic.setText(data.get("nic").getAsString());
                userMobile.setText(data.get("mobile_number").getAsString());
                noDays.setText(data.get("noDays").getAsString());
                payRates.setText(data.get("payRate").getAsString());
                String[] monthNameList = {"January","February","March","April","May","June","July","August","September","October","November","December","InvalidNumber"};
                int monthNum = Integer.parseInt(monthList.getValue().toString());
                if(monthNum <= 12)
                    monthName.setText(monthNameList[monthNum]);
                else
                    monthName.setText(monthNameList[12]);

                DecimalFormat formatter = new DecimalFormat("#,###.00");

                float subtotal = Integer.parseInt(data.get("noDays").getAsString()) * Integer.parseInt(data.get("payRate").getAsString());
                subTotal.setText(formatter.format(subtotal));
                bonus.setText(formatter.format(subtotal * 0.1));
                float gross = (float) (subtotal * 1.1);
                grossPay.setText(formatter.format(gross));

                deduction.setText(formatter.format(gross * 0.03));
                netPay.setText(formatter.format(gross * 0.97));
                for (int i = 0; i < paySheetContainer.getChildren().size(); i++) {
                    paySheetContainer.getChildren().get(i).setVisible(true);
                }
            } else {
                // Check for empID is registered or not
                HashMap<String, Object> checkParams = new HashMap<>();
                checkParams.put("empID", currentEMP);
                CreateConnection empAvailable = new CreateConnection(CreateConnection.DOMAIN + "employee_details/isRegistered", checkParams, HttpURLConnection.HTTP_OK, "GET");
                empAvailable.getResponse();
                if (empAvailable.responseCode == HttpURLConnection.HTTP_OK) {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Record not found");
                    alert.setHeaderText("Record not found\nClick OK to add record");

                    alert.showAndWait().ifPresent(res -> {
                        if (res.getButtonData().isDefaultButton()) {
                            System.out.println("Add attendance");
                            Stage attendanceStage = new Stage();
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("updateAttendance.fxml"));
                            try {
                                attendanceStage.setScene(new Scene(fxmlLoader.load()));
                                attendanceStage.isAlwaysOnTop();

                                UpdateAttendanceController updateAttendanceController = fxmlLoader.getController();
                                updateAttendanceController.initUpdateAttendance(attendanceStage, currentEMP, year.getText(), monthList.getValue().toString());

                                attendanceStage.showAndWait();

                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }

                        }
                    });
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Employee Not Found");
                    alert.setHeaderText("Employee " + currentEMP + " is not registered");
                    alert.showAndWait();
                }
            }
            ;

        } else {
            ShowError error = new ShowError(emptyError);
            error.start();
        }

    }

    @FXML
    public void changeAttendance() {
        Stage attendanceStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("updateAttendance.fxml"));
        try {
            attendanceStage.setScene(new Scene(fxmlLoader.load()));
            attendanceStage.isAlwaysOnTop();

            UpdateAttendanceController updateAttendanceController = fxmlLoader.getController();
            updateAttendanceController.initUpdateAttendance(attendanceStage, currentEMP, "", "");

            attendanceStage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

class ShowError extends Thread {
    Label errorLabel;
    ShowError(Label errorLabel) {
        this.errorLabel = errorLabel;
    }
    @Override
    public void run() {
        errorLabel.setVisible(true);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        errorLabel.setVisible(false);
    }
}