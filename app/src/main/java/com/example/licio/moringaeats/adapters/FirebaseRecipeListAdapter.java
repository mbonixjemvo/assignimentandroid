package com.example.licio.moringaeats.adapters;




import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import com.example.licio.moringaeats.R;
import com.example.licio.moringaeats.models.Recipe;
import com.example.licio.moringaeats.ui.RecipeDetailActivity;
import com.example.licio.moringaeats.util.ItemTouchHelperAdapter;
import com.example.licio.moringaeats.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

public class FirebaseRecipeListAdapter extends FirebaseRecyclerAdapter<Recipe, FirebaseViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;

    private ChildEventListener mChildEventListener;
    private ArrayList<Recipe> mComics = new ArrayList<>();

    public FirebaseRecipeListAdapter(FirebaseRecyclerOptions<Recipe> options, Query ref, OnStartDragListener onStartDragListener, Context context){
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mComics.add(dataSnapshot.getValue(Recipe.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onBindViewHolder(final FirebaseViewHolder viewHolder, int position, Recipe model) {
        viewHolder.onBindRecipe(model);
        viewHolder.mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RecipeDetailActivity.class);
                intent.putExtra("position", viewHolder.getAdapterPosition());
                intent.putExtra("comics", Parcels.wrap(mComics));
                mContext.startActivity(intent);
            }
        });
    }


    @NonNull
    @Override
    public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item_drag, parent, false);
        return new FirebaseViewHolder(view);
    }


//    @Override
//    protected void onBindViewHolder(final FirebaseComicViewHolder viewHolder, int position,Comics model) {
//        viewHolder.bindComics(model);
//        viewHolder.mComicImageView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
//                    mOnStartDragListener.onStartDrag(viewHolder);
//                }
//                return false;
//            }
//        });
//    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mComics, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        setIndexInFirebase();
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        mComics.remove(position);
        getRef(position).removeValue();
    }

    private void setIndexInFirebase() {
        for (Recipe recipes : mComics) {
            int index = mComics.indexOf(recipes);
            DatabaseReference ref = getRef(index);
            recipes.setIndex(Integer.toString(index));
            ref.setValue(recipes);
        }
    }


}