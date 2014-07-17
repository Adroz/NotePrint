package com.workshoporange.android.noteprint;

import android.app.IntentService;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Nik on 1/06/2014.
 */
public class ConnectionService extends IntentService {
    public ConnectionService() {
        super("ConnectionService");
    }

    public static final String ACTION_LOGIN = "ACTION_LOGIN";

    @Override
    protected void onHandleIntent(Intent intent) {
//        if (ACTION_LOGIN.equals(intent.getAction())) {
            String id = intent.getStringExtra("deviceId");
            String token = intent.getStringExtra("accessToken");
            String func = intent.getStringExtra("functionOrVariableName");
            String arg = intent.getStringExtra("argString");

            cloudRequest(id, token, func, arg);
//            String username = intent.getStringExtra("username");
//            String password = intent.getStringExtra("password");
//            login(username, password);
//            return;
//        }

    }


    public static String cloudRequest(String deviceId, String accessToken, String functionOrVariableName, String argString) {
        try {
            //Setup connection
            URL url = new URL("https://api.spark.io/v1/devices/" + deviceId + "/" + functionOrVariableName);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            String urlParameters = "access_token=" + accessToken + "&args=" + argString;
            con.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            //Receive response
            con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } catch (Exception e) {
//            System.err.println(SparkUtils.class.getName() + ": Exception:");
            e.printStackTrace();
            return "FAILED";
        }
    }


//    private int login(String username, String password) {
//
//        return 1;
//    }






}
