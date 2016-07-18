package com.cheng.robotchat.voiconchat.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cheng.robotchat.voiconchat.interfaces.OnReturnObject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chientruong on 5/27/16.
 */
public class GetJSONObject extends AsyncTask<String,String,JSONObject> {
    public OnReturnObject onReturnObject;
    private Context context;
    private String param,url;

    private ApiService.ApiResquestType typeRequest;

    public GetJSONObject(Context context, String url, String param, ApiService.ApiResquestType typeRequest, OnReturnObject onReturnObject) {
        this.context=context;
        this.url=url;
        this.param=param;
        this.onReturnObject=onReturnObject;
        this.typeRequest=typeRequest;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        Log.d("Link", "doInBackground: "+url);
        return ApiService.makeHttpRequest(context,url,typeRequest,param);
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        try {
            onReturnObject.onReturnObjectFinish(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
