package com.example.emp_system;

import com.google.gson.JsonObject;

import java.net.HttpURLConnection;
import java.util.HashMap;

public class testing {
    public static void main(String[] args) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("user_email", "root@gmail.com");
        CreateConnection connection = new CreateConnection(CreateConnection.DOMAIN + "admin/details", params, HttpURLConnection.HTTP_OK, "GET");
        JsonObject jsonObject = connection.getJsonResponse();
        System.out.println(jsonObject);
    }
}
