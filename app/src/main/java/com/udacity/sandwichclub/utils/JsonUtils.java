package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject details = new JSONObject(json);
            JSONObject name = details.getJSONObject("name");

            String mainName = name.getString("mainName");
            List<String> alsoKnownAs = parseJSONStringArray(name.getJSONArray("alsoKnownAs"));
            String placeOfOrigin = details.getString("placeOfOrigin");
            String description = details.getString("description");
            String image = details.getString("image");
            List<String> ingredients = parseJSONStringArray(details.getJSONArray("ingredients"));

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<String> parseJSONStringArray(JSONArray strings) {
        List<String> parsedList = new ArrayList<>();

        if (strings.length() != 0) {
            for (int i = 0; i < strings.length(); i++) {
                try {
                    parsedList.add(strings.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return parsedList;
    }
}
