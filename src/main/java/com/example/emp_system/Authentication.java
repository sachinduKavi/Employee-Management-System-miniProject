package com.example.emp_system;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


public class Authentication {
    String userEmail, password;
    Authentication(String userEmail, String password){
        this.userEmail = userEmail;
        this.password = password;
    }

    // Update access time of admin
    public void updateAccessDate(String emp_id) throws IOException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("empID", emp_id);
        CreateConnection conn = new CreateConnection(CreateConnection.DOMAIN + "admin/updateAccess", params, HttpURLConnection.HTTP_CREATED, "PUT");
        conn.getResponse();
    }
    public EmployeeData userAuthentication(){
        System.out.println(this.userEmail + this.password);
        // Fetching data from the API
            HashMap<String, Object> params = new HashMap<>();
            params.put("user_email", this.userEmail);
            CreateConnection connection = new CreateConnection(CreateConnection.DOMAIN + "admin/details", params, HttpURLConnection.HTTP_OK, "GET");
            JsonObject jsonObject = connection.getJsonResponse();

            if (connection.responseCode != HttpURLConnection.HTTP_NOT_FOUND) {
                if (jsonObject.get("status").getAsString().equals("Record Found")) {
                    JsonArray jsonArray = jsonObject.getAsJsonArray("data");
                    JsonObject data = jsonArray.get(0).getAsJsonObject();

                    try {
                        updateAccessDate(data.get("emp_id").getAsString());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    // Creating employee object
                    EmployeeData thisEmployee = new EmployeeData(
                            data.get("emp_id").getAsString(),
                            data.get("f_name").getAsString() + " " +
                                    data.get("l_name").getAsString(),
                            data.get("user_email").getAsString(),
                            data.get("position").getAsString(),
                            data.get("deptID").getAsString(),
                            data.get("payRate").getAsInt(),
                            data.get("NIC").getAsString(),
                            data.get("mobile_number").getAsString(),
                            this.password.equals(data.get("password").getAsString())
                    );

                    return thisEmployee;
                } else
                    return new EmployeeData(false);
            }else
                return new EmployeeData(false);
        }



}
