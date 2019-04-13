package com.example.licio.moringaeats.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.licio.moringaeats.Constants;
import com.example.licio.moringaeats.R;
import com.example.licio.moringaeats.adapters.RecipeListAdapter;
import com.example.licio.moringaeats.models.Recipe;
import com.example.licio.moringaeats.services.YummlyService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Home extends AppCompatActivity {


    public static final String TAG = Home.class.getSimpleName();

    public ArrayList<Recipe> recipes = new ArrayList<>();

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentIngredient;

//    private String[] recipes = new String[] {"Sweet Potatoes with Apple Butter","Old-Fashioned Apple Pie","Beef Stew in Red Wine Sauce",
//    "Butternut Squash Soup with Crisp Pancetta","Hot Mulled Cider","Pear-Cranberry Hand Pies","Caramel Lady Apples","Three-Chile Beef Chili",
//    "Poached Egg over Spinach and Mushroom","10-Minute Energizing Oatmeal","Breakfast Bagel","Granola with Fresh Fruit"};

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private RecipeListAdapter mAdapter;
//    @BindView(R.id.txtRecipe)
//    TextView mTxtRecipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);




//        final RecipesAdapter adapter = new RecipesAdapter(this, R.layout.support_simple_spinner_dropdown_item,recipes);
//        mListView.setAdapter(adapter);

//                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String recipe = ((TextView)view).getText().toString();
//                Toast.makeText(Home.this, recipe, Toast.LENGTH_SHORT).show();
//           }
//        });

        Intent intent = getIntent();
        String ingredients = intent.getStringExtra("ingredients");
        //mTxtRecipe.setText("The following recipes are loved by " + name);

        getRecipes(ingredients);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentIngredient = mSharedPreferences.getString(Constants.PREFERENCES_INGREDIENTS_KEY, null);

        if (mRecentIngredient != null) {
            getRecipes(mRecentIngredient);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                addToSharedPreferences(query);
                getRecipes(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    private void getRecipes(String ingredients){
        final YummlyService yummlyService = new YummlyService();
        yummlyService.findRecipes(ingredients, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                   //String jsonData = response.body().string();
//                    if (response.isSuccessful()) {
                      // Log.v(TAG, jsonData);
                        recipes = yummlyService.processResults(response);
                        Home.this.runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                mAdapter = new RecipeListAdapter(getApplicationContext(), recipes);
                                mRecyclerView.setAdapter(mAdapter);
                                RecyclerView.LayoutManager layoutManager =
                                        new LinearLayoutManager(Home.this);
                                mRecyclerView.setLayoutManager(layoutManager);
                                mRecyclerView.setHasFixedSize(true);
                            }
                        });

            }
        });
    }

    private void addToSharedPreferences(String ingredients) {
        mEditor.putString(Constants.PREFERENCES_INGREDIENTS_KEY,ingredients).apply();
    }

}
