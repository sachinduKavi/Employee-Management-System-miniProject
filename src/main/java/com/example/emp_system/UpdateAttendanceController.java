package com.example.emp_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.HttpURLConnection;
import java.util.LinkedHashMap;

public class UpdateAttendanceController {
    Stage thisStage;

    @FXML
    TextField empNO, year, month, attendance;
    public void initUpdateAttendance(Stage thisStage, String empID, String year, String month) {
        this.thisStage = thisStage;
        empNO.setText(empID);
        this.month.setText(month);
        this.year.setText(year);
        this.attendance.requestFocus();
    }
    @FXML
    public void updateBtn(ActionEvent e) {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("empID", empNO.getText());
        params.put("year", year.getText());
        params.put("months", month.getText());
        params.put("attendance", attendance.getText());
        CreateConnection connection = new CreateConnection(CreateConnection.DOMAIN + "attendance/updateAttendance", params, HttpURLConnection.HTTP_OK, "POST");
        connection.getResponse();
        thisStage.close();
    }

    @FXML
    public void cancelBtn(ActionEvent e) {
        this.thisStage.close();
    }



}
