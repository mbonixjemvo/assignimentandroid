package com.example.licio.moringaeats.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

public class RecipesAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mRecipes;

    public RecipesAdapter(Context mContext, int resource, String[] mRecipes){
        super(mContext, resource);
        this.mContext = mContext;
        this.mRecipes = mRecipes;
    }

    @Override
    public Object getItem(int position){
        String recipe = mRecipes[position];
        return String.format(recipe);
    }
    @Override
    public int getCount() {
        return mRecipes.length;
    }
}


