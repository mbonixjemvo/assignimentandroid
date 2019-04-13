package com.example.licio.moringaeats.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.licio.moringaeats.R;
import com.example.licio.moringaeats.adapters.RecipePagerAdapter;
import com.example.licio.moringaeats.models.Recipe;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity {

    @BindView(R.id.viewPager) ViewPager mViewPager;
    private RecipePagerAdapter adapterViewPager;
    ArrayList<Recipe> mRecipes = new ArrayList<>();


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_recipe_detail);
            ButterKnife.bind(this);

            mRecipes = Parcels.unwrap(getIntent().getParcelableExtra("recipes"));
            int position = getIntent().getIntExtra("position", 0);

            adapterViewPager = new RecipePagerAdapter(getSupportFragmentManager(), mRecipes);
            mViewPager.setAdapter(adapterViewPager);
            mViewPager.setCurrentItem(position);
        }
}
