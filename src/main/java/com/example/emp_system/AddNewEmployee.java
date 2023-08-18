package com.example.emp_system;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class AddNewEmployee {
    @FXML
    TextField employeeID, fName, lName, payRate, position, nic, mobileNumber, emailAddress;
    @FXML
    ChoiceBox departmentMenu;
    @FXML
    Button submitBtn;
    @FXML
    Label addHeading;
    Stage stage;

    public String[] fetchData() {
        CreateConnection connection = new CreateConnection(CreateConnection.DOMAIN + "employee_details/get_emp", new HashMap<>(), HttpURLConnection.HTTP_OK, "GET");
        JsonObject jsonObject = connection.getJsonResponse();
        JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
        // Declaring string array
        String[] allData = new String[jsonArray.size() + 1];
        allData[0] = jsonObject.get("last_emp").getAsJsonObject().get("employee_id").getAsString();
        for(int i = 1; i < allData.length; i++) {
            allData[i] = (jsonArray.get(i-1).getAsJsonObject()).get("name").getAsString();
        }
        return allData;
    }

    public void initAddEmployees(EmployeeData thisAdmin, Stage stage) {
        this.stage = stage;
        System.out.println(thisAdmin.getEmployeeID());
        String[] allData = fetchData();
        if(allData != null) {
            String newEmployeeID = allData[0];
            // This is a temporary method to assign new primary key
            newEmployeeID = (((String.valueOf(Integer.parseInt(newEmployeeID.substring(1)) + 1)).length() < 3) ? "E0" : "E") + (Integer.parseInt(newEmployeeID.substring(1)) + 1);
            System.out.println(newEmployeeID);
            employeeID.setText(newEmployeeID);
            employeeID.setDisable(true);

            // Initializing department menus
            for(int i = 1; i < allData.length; i++) {
                departmentMenu.getItems().add(allData[i]);
            }


        }else {
            System.out.println("Got null value");
        }
    }

    public void updateEmployeeInit(EmployeeData employeeData, MouseEvent event, Stage stage) {
        initAddEmployees(employeeData, stage);
        departmentMenu.setValue(employeeData.getDepartment());

        addHeading.setText("Update Employee");
        submitBtn.setText("Update");

        employeeID.setText(employeeData.getEmployeeID());
        employeeID.setDisable(true);
        emailAddress.setText(employeeData.getEmail());
        position.setText(employeeData.getPosition());
        nic.setText(employeeData.getNIC());
        mobileNumber.setText(employeeData.getMobileNumber());
        payRate.setText(Integer.toString(employeeData.getPayRate()));

        String[] name = employeeData.getName().split(" ");
        fName.setText(name[0]);
        lName.setText(name[1]);
    }


    public void submitData() {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        employeeID.setDisable(false);
        params.put("emID", employeeID.getText());
        employeeID.setDisable(true);
        params.put("fName", fName.getText());
        params.put("lName", lName.getText());
        params.put("email", emailAddress.getText());
        params.put("position", position.getText());
        params.put("department", departmentMenu.getValue());
        params.put("payRate", Integer.parseInt(payRate.getText()));
        params.put("nic", nic.getText());
        params.put("mobileNumber", mobileNumber.getText());
        System.out.println("Params: " + params);

        if (submitBtn.getText().equals("Update")) {
            System.out.println("updating Employee Details");
            new CreateConnection(CreateConnection.DOMAIN + "employee_details/updateEmployee", params, HttpURLConnection.HTTP_CREATED, "PUT").getResponse();
        } else {
            System.out.println("Submitting employee data");
            new CreateConnection(CreateConnection.DOMAIN + "employee_details/addEmployee", params, HttpURLConnection.HTTP_CREATED, "POST").getResponse();
        }
        stage.close();
    }
}
