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
import java.util.HashMap;


interface URLConnection {
    String DOMAIN = "http://localhost:3000/";
    StringBuffer getResponse();
    JsonObject getJsonResponse();
}

public class CreateConnection implements URLConnection{
    String baseURL, method;
    HashMap<String, Object> params;
    public int connCode, responseCode;
    CreateConnection(String baseURL, HashMap<String, Object> params, int connCode, String method){
        this.baseURL = baseURL;
        this.params = params;
        this.connCode = connCode;
        this.method = method;
    }

    // Get server response as from server
    @Override
    public StringBuffer getResponse() {
        try {
            for(String key: this.params.keySet()){
                baseURL += "/" + params.get(key).toString().replace(" ", "+");
            }
            System.out.println("baseURL " + baseURL);
            URL url = new URL(baseURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod(this.method);

            this.responseCode = conn.getResponseCode();
            System.out.println("Response Code: \n" + responseCode);

            // Getting server response to the response variable
            StringBuffer response;
            if (responseCode == this.connCode) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                response = new StringBuffer();
                while ((line = in.readLine()) != null)
                    response.append(line);
                in.close();
            }else {
                response = null;
            }

            System.out.println("Server response: " +  response);
            return response;

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public JsonObject getJsonResponse() {
        StringBuffer stringResponse = getResponse();
        if(stringResponse != null)
            return new JsonParser().parse(stringResponse.toString()).getAsJsonObject();

        return null;
    }



}

