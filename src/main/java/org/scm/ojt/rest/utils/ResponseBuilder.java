package org.scm.ojt.rest.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

/**
 * Created by Yoyok_T on 29/01/2019.
 */
public class ResponseBuilder {
    public static Response createResponse( Response.Status status  ) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put( "message", status.toString() );
        }
        catch( JSONException e ) {
            return Response.status( Response.Status.INTERNAL_SERVER_ERROR ).entity( Response.Status.INTERNAL_SERVER_ERROR ).build();
        }

        return Response.status( status ).entity( jsonObject.toString() ).build();
    }

    public static Response createResponse( Response.Status status, String message ) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put( "message", message );
        }
        catch( JSONException e ) {
            return Response.status( Response.Status.INTERNAL_SERVER_ERROR ).entity( Response.Status.INTERNAL_SERVER_ERROR ).build();
        }

        return Response.status( status ).entity( jsonObject.toString() ).build();
    }

    public static Response createResponse( Response.Status status, JsonSerializable json ) throws JSONException {
        return Response.status( status ).entity( json.toJson().toString() ).build();
    }

    public static Response createResponse( Response.Status status, List<JsonSerializable> json ) throws JSONException {
        JSONArray jsonArray = new JSONArray();

        for( int i = 0; i < json.size(); i++ ) {
            jsonArray.put( json.get(i).toJson() );
        }

        return Response.status( status ).entity( jsonArray.toString() ).build();
    }

    public static Response createResponse( Response.Status status, Map<String,Object> map ) {
        JSONObject jsonObject = new JSONObject();

        try {
            for( Map.Entry<String,Object> entry : map.entrySet() ) {
                jsonObject.put( entry.getKey(), entry.getValue() );
            }
        }
        catch( JSONException e ) {
            return Response.status( Response.Status.INTERNAL_SERVER_ERROR ).entity( Response.Status.INTERNAL_SERVER_ERROR ).build();
        }

        return Response.status( status ).entity( jsonObject.toString() ).build();
    }
}