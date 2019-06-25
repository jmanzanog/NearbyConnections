package jmanzano.com.restapp;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import static android.content.ContentValues.TAG;

public class LEDResource extends ServerResource {

    @Get("json")
    public Representation getState() {
        JSONObject result = new JSONObject();
        try {
            result.put("estado", LEDModel.getInstance().getState());
        } catch (Exception e) {
            Log.e(TAG, "Error en JSONObject: ", e);
        }
        return new StringRepresentation(result.toString(), MediaType.APPLICATION_ALL_JSON);
    }

    @Post("json")
    public Representation postState(Representation entity) {
        JSONObject query = new JSONObject();
        JSONObject fullresult = new JSONObject();
        String result = "ok";
        try {
            JsonRepresentation json = new JsonRepresentation(entity);
            query = json.getJsonObject();
            boolean state = (boolean) query.get("estado");
            Log.d(this.getClass().getSimpleName(), "Nuevo estado del LED: " + state);
            LEDModel.getInstance().setState(state);


        } catch (Exception e) {
            Log.e(TAG, "Error: ", e);
            result = "error";
        }
        try {
            fullresult.put("resultado", result);
        } catch (JSONException e) {
            Log.e(TAG, "Error en JSONObject: ", e);
        }
        return new StringRepresentation(fullresult.toString(), MediaType.APPLICATION_ALL_JSON);
    }
}
