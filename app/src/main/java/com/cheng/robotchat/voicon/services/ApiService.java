package com.cheng.robotchat.voicon.services;


import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

public class ApiService {

    public enum ApiResquestType {
        TYPE_POST,
        TYPE_PUT,
        TYPE_DELETE,
        TYPE_GET
    }

    public static JSONObject makeHttpRequest(Context context, String targetUrl, ApiResquestType typeRequest, String params)  {
        JSONObject jsonObjectResult = null;
        URL url;
        HttpURLConnection httpURLConnection = null;


        try {
            url = new URL(targetUrl);
            httpURLConnection = (HttpURLConnection)url.openConnection();

            switch (typeRequest){
                case TYPE_POST:
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    break;
                case TYPE_GET:
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setDoOutput(false);
                    break;
                case TYPE_PUT:
                    httpURLConnection.setRequestMethod("PUT");
                    httpURLConnection.setDoOutput(true);
                    break;
                default:
                    httpURLConnection.setRequestMethod("DELETE");
                    httpURLConnection.setDoOutput(false);
                    break;
            }

            httpURLConnection.setRequestProperty("Accept","application/json;charset=utf-8");

            httpURLConnection.setReadTimeout(30000); //60s
            httpURLConnection.setConnectTimeout(30000); //30s

            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);

            httpURLConnection.connect();

            if ((!params.equals("")) && (params != null)){
                DataOutputStream os = new DataOutputStream(httpURLConnection.getOutputStream());
                os.writeBytes(params);
                os.flush();
                os.close();
            }

            InputStream is = httpURLConnection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = rd.readLine()) != null){
                response.append(line);
            }
            rd.close();
            jsonObjectResult = new JSONObject(response.toString());
        } catch (SocketTimeoutException e) {
                e.printStackTrace();
           return null;

        }catch (ProtocolException e) {
            e.printStackTrace();
            jsonObjectResult = new JSONObject();
            try {
                Log.d("API", "Loi van ban: ");
                return jsonObjectResult.put("error", "text");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        catch (UnknownHostException e) {
                 e.printStackTrace();
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
            }
        }

        return jsonObjectResult;
    }
}
