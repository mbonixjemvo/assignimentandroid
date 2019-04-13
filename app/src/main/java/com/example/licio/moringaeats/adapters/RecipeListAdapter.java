package com.example.licio.moringaeats.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.licio.moringaeats.R;
import com.example.licio.moringaeats.models.Recipe;
import com.example.licio.moringaeats.ui.RecipeDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    private ArrayList<Recipe> mRecipes = new ArrayList<>();
    private Context mContext;

    public RecipeListAdapter(Context context, ArrayList<Recipe> recipes){
        mContext = context;
        mRecipes = recipes;
    }

    @Override
    public RecipeListAdapter.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false);
        RecipeViewHolder viewHolder = new RecipeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeListAdapter.RecipeViewHolder holder, int position) {
        holder.bindRecipe(mRecipes.get(position));
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.recipeImage)
        ImageView mRecipeImageView;
        @BindView(R.id.recipeName)
        TextView mRecipeNameTextView;
        @BindView(R.id.txtCuisine) TextView mCuisineTextView;
        @BindView(R.id.ratingTextView) TextView mRatingTextView;

        private Context mContext;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, RecipeDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("recipes", Parcels.wrap(mRecipes));
            mContext.startActivity(intent);
        }

        public void bindRecipe(Recipe recipe) {
            mRecipeNameTextView.setText(recipe.getRecipeName());
            mCuisineTextView.setText(recipe.getSource());
            mRatingTextView.setText("Rating: " + recipe.getRating() + "/5");
            Picasso.get().load(recipe.getImageUrl()).into(mRecipeImageView);
        }
    }
}
