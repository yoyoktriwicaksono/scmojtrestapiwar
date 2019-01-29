package org.scm.ojt.rest.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yoyok_T on 29/01/2019.
 */
public interface JsonSerializable {
    public JSONObject toJson() throws JSONException;
}
