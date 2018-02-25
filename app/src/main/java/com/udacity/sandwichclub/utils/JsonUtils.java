package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String LOG_TAG = JsonUtils.class.getSimpleName();
    public static final String jsonSandwichName = "name";
    public static final String jsonSandwichMainName = "mainName";
    public static final String jsonSandwichAlsoKnownAs = "alsoKnownAs";
    public static final String jsonSandwichPlaceOfOrigin = "placeOfOrigin";
    public static final String jsonSandwichDescription = "description";
    public static final String jsonSandwichImage = "image";
    public static final String jsonSandwichIngredients = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();

        try {
            JSONObject jsonSandwich = new JSONObject(json);
            JSONObject jsonName = jsonSandwich.getJSONObject(jsonSandwichName);

            //Sandwich mainName
            String sandwichMainName = jsonName.getString(jsonSandwichMainName);
            sandwich.setMainName(sandwichMainName);

            //Sandwich alsoKnownAs
            JSONArray jsonAlsoKnownAs = jsonName.getJSONArray(jsonSandwichAlsoKnownAs);
            List<String> sandwichAlsoKnownAs = new ArrayList<>();
            for (int i = 0; i < jsonAlsoKnownAs.length(); i++) {
                sandwichAlsoKnownAs.add(jsonAlsoKnownAs.getString(i));
            }
            sandwich.setAlsoKnownAs(sandwichAlsoKnownAs);

            //Sandwich placeOfOrigin
            String sandwichPlaceOfOrigin = jsonSandwich.getString(jsonSandwichPlaceOfOrigin);
            sandwich.setPlaceOfOrigin(sandwichPlaceOfOrigin);

            //Sandwich description
            String sandwichDescription = jsonSandwich.getString(jsonSandwichDescription);
            sandwich.setDescription(sandwichDescription);

            //Sandwich image
            String sandwichImage = jsonSandwich.getString(jsonSandwichImage);
            sandwich.setImage(sandwichImage);

            //Sandwich ingredients
            JSONArray jsonIngredients = jsonSandwich.getJSONArray(jsonSandwichIngredients);
            List<String> sandwichIngredients = new ArrayList<>();
            for (int i = 0; i < jsonIngredients.length(); i++) {
                sandwichIngredients.add(jsonIngredients.getString(i));
            }
            sandwich.setIngredients(sandwichIngredients);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "JSONObject could not be parsed");
        }

        return sandwich;
    }
}
