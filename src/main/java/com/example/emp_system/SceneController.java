package com.example.emp_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private Scene scene;
    private Stage stage;
    private Parent root;

    public void changeToMainScreen(ActionEvent event, EmployeeData adminData) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("mainscreen.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root.load(), 1400, 860);

        MainscreenController mainscreenController = root.getController();
        mainscreenController.initMainScreen(adminData);

        stage.setX(150);
        stage.setY(100);
        stage.setScene(scene);
        stage.show();
    }


}
