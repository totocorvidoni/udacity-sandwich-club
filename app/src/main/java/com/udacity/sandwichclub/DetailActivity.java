package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if (!sandwich.getDescription().isEmpty()) {
            populateDescription(sandwich);
        }
        if (!sandwich.getAlsoKnownAs().isEmpty()) {
            populateAlsoKnown(sandwich);
        }
        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            populateOrigin(sandwich);
        }
        if (!sandwich.getIngredients().isEmpty()) {
            populateIngredients(sandwich);
        }
    }

    private void populateDescription(Sandwich sandwich) {
        TextView mDescription = findViewById(R.id.description_tv);
        mDescription.setText(sandwich.getDescription());
        mDescription.setVisibility(View.VISIBLE);
    }

    private void populateAlsoKnown(Sandwich sandwich) {
        LinearLayout mAlsoKnownContainer = findViewById(R.id.also_known_ll);
        mAlsoKnownContainer.setVisibility(View.VISIBLE);

        TextView mAlsoKnown = findViewById(R.id.also_known_tv);
        String aka = sandwich.getAlsoKnownAs().toString();
        mAlsoKnown.setText(aka.substring(1, aka.length() - 1));
    }

    private void populateOrigin(Sandwich sandwich) {
        LinearLayout mPlaceOfOriginContainer = findViewById(R.id.origin_ll);
        mPlaceOfOriginContainer.setVisibility(View.VISIBLE);

        TextView mPlaceOfOrigin = findViewById(R.id.origin_tv);
        mPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
    }

    private void populateIngredients(Sandwich sandwich) {
        LinearLayout mIngredientsContainer = findViewById(R.id.ingredients_ll);
        mIngredientsContainer.setVisibility(View.VISIBLE);

        List<String> ingredientList = sandwich.getIngredients();
        String ingredients = "";
        for (String ingredient : ingredientList) {
            ingredients =  ingredients.concat("- " + ingredient + "\n");
        }

        TextView mIngredients = findViewById(R.id.ingredients_tv);
        mIngredients.setText(ingredients);
    }
}
