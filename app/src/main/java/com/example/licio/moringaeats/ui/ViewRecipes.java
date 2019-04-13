package com.example.licio.moringaeats.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.licio.moringaeats.Constants;
import com.example.licio.moringaeats.R;
import com.example.licio.moringaeats.adapters.FirebaseRecipeListAdapter;
import com.example.licio.moringaeats.adapters.FirebaseViewHolder;
import com.example.licio.moringaeats.models.Recipe;
import com.example.licio.moringaeats.util.SimpleItemTouchHelperCallback;
import com.example.licio.moringaeats.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewRecipes extends AppCompatActivity implements OnStartDragListener {
    private DatabaseReference mRestaurantReference;
    private FirebaseRecipeListAdapter mFirebaseAdapter;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);



//        mRestaurantReference = FirebaseDatabase
//                .getInstance()
//                .getReference(Constants.FIREBASE_CHILD_RECIPES)
//                .child(uid);
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

//        mFirebaseAdapter = new FirebaseRecyclerAdapter<Recipe, FirebaseViewHolder>
//                (Recipe.class, R.layout.recipe_list_item, FirebaseViewHolder.class,
//                        mRestaurantReference) {
//
//            @Override
//            protected void populateViewHolder(FirebaseViewHolder viewHolder,
//                                              Recipe model, int position) {
//                viewHolder.onBindRecipe(model);
//
//            }
//        };

        Query query = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_CHILD_RECIPES )
                .child(uid)
                .orderByChild(Constants.FIREBASE_QUERY_INDEX);

        FirebaseRecyclerOptions<Recipe> options =
                new FirebaseRecyclerOptions.Builder<Recipe>()
                        .setQuery(query, Recipe.class)
                        .build();

        mFirebaseAdapter = new FirebaseRecipeListAdapter(options, query, this, this);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
        mRecyclerView.setHasFixedSize(true);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mFirebaseAdapter!= null) {
            mFirebaseAdapter.stopListening();
        }
    }
    public void onStartDrag(RecyclerView.ViewHolder viewHolder){
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.stopListening();
    }
}
