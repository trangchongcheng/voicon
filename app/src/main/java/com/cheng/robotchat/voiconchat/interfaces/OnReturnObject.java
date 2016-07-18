package com.cheng.robotchat.voiconchat.interfaces;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chientruong on 5/27/16.
 */
public interface OnReturnObject {
    void onReturnObjectFinish(JSONObject object) throws JSONException;
}
