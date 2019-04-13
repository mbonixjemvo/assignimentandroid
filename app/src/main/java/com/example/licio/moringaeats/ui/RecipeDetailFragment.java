package com.example.licio.moringaeats.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licio.moringaeats.Constants;
import com.example.licio.moringaeats.R;
import com.example.licio.moringaeats.models.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.recipeImageView)
    ImageView mImageLabel;
    @BindView(R.id.recipeNameTextView)
    TextView mNameLabel;
    @BindView(R.id.ratingTextView)
    TextView mRatingLabel;
    @BindView(R.id.ingredientList)
    TextView mIngredientList;
    @BindView(R.id.saveRecipeButton)
    Button mSaveRecipeButton;
    @BindView(R.id.viewRecipeButton)
    Button mViewRecipeButton;
    @BindView(R.id.sourceTextView)
    TextView mSourceTextView;

    private Recipe mRecipe;

    public static RecipeDetailFragment newInstance(Recipe recipe) {
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("recipe", Parcels.wrap(recipe));
        recipeDetailFragment.setArguments(args);
        return recipeDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecipe = Parcels.unwrap(getArguments().getParcelable("recipe"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.get().load(mRecipe.getImageUrl()).into(mImageLabel);

        mNameLabel.setText(mRecipe.getRecipeName());
        mRatingLabel.setText("Rating: " + mRecipe.getRating() + "/5");
        //mSourceTextView.setText("sourceDisplayName" + mSourceTextView);
        mIngredientList.setText(android.text.TextUtils.join(", ", mRecipe.getIngredients()));


        mViewRecipeButton.setOnClickListener(this);
        mSaveRecipeButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == mViewRecipeButton) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.yummly.co/#recipe/" + mRecipe.getId()));
            startActivity(webIntent);
        }
        if(v == mSaveRecipeButton){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            DatabaseReference recipeRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_RECIPES)
                    .child(uid);

            DatabaseReference pushRef = recipeRef.push();
            String pushId = pushRef.getKey();
            mRecipe.setPushId(pushId);
            pushRef.setValue(mRecipe);
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }

    }
}
