package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            Log.e(TAG, "intent is null");
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            Log.e(TAG, "EXTRA_POSITION not found in intent");
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            Log.e(TAG, "Sandwich data unavailable");
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
        TextView alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        TextView originTextView = findViewById(R.id.origin_tv);
        TextView descriptionTextView = findViewById(R.id.description_tv);
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);

        if (sandwich.getAlsoKnownAs() != null)
            alsoKnownAsTextView.setText(sandwich.getAlsoKnownAs().toString());

        if (sandwich.getPlaceOfOrigin() != null)
            originTextView.setText(sandwich.getPlaceOfOrigin());

        if (sandwich.getDescription() != null)
            descriptionTextView.setText(sandwich.getDescription());

        if (sandwich.getIngredients() != null)
            ingredientsTextView.setText(formatListAsCommaSeparated(sandwich.getIngredients()));
    }

    private String formatListAsCommaSeparated(List<String> list) {
        if (list == null || list.size() < 1)
            return "";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1)
                sb.append(", ");
        }

        return sb.toString();
    }
}
