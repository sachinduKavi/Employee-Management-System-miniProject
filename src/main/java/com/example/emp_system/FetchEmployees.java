package com.example.emp_system;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class FetchEmployees {
    public EmployeeData[] getAllEmployees() {
        try {
            URL url = new URL("http://localhost:3000/employee_details/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            System.out.println("Employee: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                StringBuffer response = new StringBuffer();

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                System.out.println(response);
                JsonObject jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();


                if (jsonObject.get("status").getAsString().equals("Record found")) {
                    JsonArray jsonArray = jsonObject.getAsJsonArray("data");
                    EmployeeData[] employeeDataList = new EmployeeData[jsonArray.size()];

                    System.out.println("Array Size: " + jsonArray.size());
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JsonObject singleEmployee = jsonArray.get(i).getAsJsonObject();

                        employeeDataList[i] = new EmployeeData(
                                singleEmployee.get("employee_id").getAsString(),
                                singleEmployee.get("name").getAsString(),
                                singleEmployee.get("email").getAsString(),
                                singleEmployee.get("position").getAsString(),
                                singleEmployee.get("department").getAsString(),
                                singleEmployee.get("payRate").getAsInt(),
                                singleEmployee.get("NIC").getAsString(),
                                singleEmployee.get("mobile_number").getAsString(),
                                true
                        );

                    }
                    return employeeDataList;
                }
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

}
