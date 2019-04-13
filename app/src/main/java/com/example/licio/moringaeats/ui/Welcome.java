package com.example.licio.moringaeats.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.licio.moringaeats.Constants;
import com.example.licio.moringaeats.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Welcome extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.welcome) TextView mWelcome;
    @BindView(R.id.desc) TextView mDescription;
    @BindView(R.id.btnLearn) Button mBtnLearn;
    //@BindView(R.id.btnView) Button mBtnView;
   // @BindView(R.id.edtName) EditText mEdtName;
    @BindView(R.id.btnEnter) Button mBtnEnter;
    public static final String TAG = MainActivity.class.getSimpleName();

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mSearchedIngredientReference;
    private ValueEventListener mSearchedIngredientReferenceListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ButterKnife.bind(this);

        Typeface caviarFont = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");
        mWelcome.setTypeface(caviarFont);
        mDescription.setTypeface(caviarFont);

        mBtnLearn.setOnClickListener(this);
        //mBtnView.setOnClickListener(this);
        mBtnEnter.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    getSupportActionBar().setTitle("Welcome, " + user.getDisplayName() + "!");
                } else {

                }
            }
        };

        mSearchedIngredientReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_RECIPES);

        mSearchedIngredientReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ingredientsSnapshot : dataSnapshot.getChildren()) {
                    String ingredients = ingredientsSnapshot.getValue().toString();
                    Log.d("Ingredients updated", "ingredients: " + ingredients);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { //update UI here if error occurred.

            }

        });

        }

    @Override
    public void onClick(View v) {
//        Intent intent = new Intent(Welcome.this,Home.class);
//        startActivity(intent);
        if(v == mBtnLearn) {
            Intent intent2 = new Intent(Welcome.this, ViewRecipes.class);
            startActivity(intent2);
        }
        if(v == mBtnEnter) {
            Intent intent3 = new Intent(Welcome.this, Home.class);
//                String ingredients = mEdtName.getText().toString();
//                intent3.putExtra("ingredients", ingredients);
            startActivity(intent3);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mSearchedIngredientReference.removeEventListener(mSearchedIngredientReferenceListener);
//    }


    }



