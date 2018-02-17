package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = "JsonUtils";

    /**
     * parse the json string into Java class called Sandwich
     * <p>
     * e.g: {
     * "name":{
     * "mainName":"Ham and cheese sandwich",
     * "alsoKnownAs":[
     * <p>
     * ]
     * },
     * "placeOfOrigin":"",
     * "description":"A ham and cheese sandwich is a common type of sandwich. It is made by putting cheese and sliced ham between two slices of bread. The bread is sometimes buttered and/or toasted. Vegetables like lettuce, tomato, onion or pickle slices can also be included. Various kinds of mustard and mayonnaise are also common.",
     * "image":"https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Grilled_ham_and_cheese_014.JPG/800px-Grilled_ham_and_cheese_014.JPG",
     * "ingredients":[
     * "Sliced bread",
     * "Cheese",
     * "Ham"
     * ]
     * }
     *
     * @param json the sandwich to be parsed
     * @return the parsed sandwich
     */
    public static Sandwich parseSandwichJson(String json) {
        // validate on the json
        if (json == null)
            return null;

        try {
            JSONObject jsonObject = new JSONObject(json);

            // Parse the name
            JSONObject name = jsonObject.optJSONObject("name");
            if (name == null)
                return null;

            String mainName = name.optString("mainName");
            List<String> knownAs = jsonArrayToList(name.optJSONArray("alsoKnownAs"));

            // Parse the placeOfOrigin
            String placeOfOrigin = jsonObject.optString("placeOfOrigin");

            // Parse the description
            String description = jsonObject.optString("description");

            // Parse the image
            String image = jsonObject.optString("image");

            // Parse the ingredients
            List<String> ingredients = jsonArrayToList(jsonObject.optJSONArray("ingredients"));

            return new Sandwich(mainName, knownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            Log.e(JsonUtils.class.getSimpleName(), e.getMessage());
            return null;
        }
    }

    private static List<String> jsonArrayToList(JSONArray array) {
        // validate on the array
        if (array == null)
            return null;

        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                stringList.add(array.getString(i));
            } catch (JSONException e) {
                Log.e(JsonUtils.class.getSimpleName(), e.getMessage());
                return null;
            }
        }

        return stringList;
    }
}
