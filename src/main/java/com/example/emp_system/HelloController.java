package com.example.emp_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class HelloController {
    @FXML
    TextField user_name, password;
    @FXML
    Label errorLabel;

    @FXML
    void checkUser(ActionEvent event){
        Authentication user = new Authentication(user_name.getText().toString().trim(), password.getText());
        EmployeeData thisEmployee = user.userAuthentication();
        if(thisEmployee.getStatus()) {
            System.out.printf("Login success");
            try {
                new SceneController().changeToMainScreen(event, thisEmployee);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else
            errorLabel.setVisible(true);

    }
}