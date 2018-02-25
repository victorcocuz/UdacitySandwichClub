package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ActivityDetailBinding binding;
    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        ImageView imageView = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Log.e("WHATEVER", json);
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(imageView);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        //mainName
        binding.nameTv.setText(sandwich.getMainName());

        //alsoKnownAs
        StringBuilder alsoKnownAs = new StringBuilder();
        for (String s : sandwich.getAlsoKnownAs()) {
            alsoKnownAs.append(s + ", ");
        }
        String outputAlsoKnownAs = alsoKnownAs.toString();
        if (outputAlsoKnownAs.length() > 0) {
            outputAlsoKnownAs = outputAlsoKnownAs.substring(0, outputAlsoKnownAs.length() - 2);
            binding.alsoKnownTv.setText(outputAlsoKnownAs);
        } else {
            binding.alsoKnownTv.setVisibility(View.GONE);
        }

        //placeOfOrigin
        binding.originTv.setText(sandwich.getPlaceOfOrigin());

        //description
        binding.descriptionTv.setText(sandwich.getDescription());

        //ingredients
        StringBuilder ingredients = new StringBuilder();
        for (String s : sandwich.getIngredients()) {
            ingredients.append("\u2022 " + s + "\n");
        }
        String outputIngredients = ingredients.toString();
        if (outputIngredients.length() > 0 ){
            outputIngredients = ingredients.substring(0, ingredients.length() - 1);
        }
        binding.ingredientsTv.setText(outputIngredients);
    }
}
