package com.example.licio.moringaeats.models;





import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Recipe {
    String recipeName;
    List<String> ingredients = new ArrayList<>();
    String imageUrl;
    String rating;
    String source;
    String id;
    String index;
    String pushId;


    public Recipe() {}

    public Recipe(String recipeName, ArrayList<String> ingredients, String imageUrl, String rating, String source, String id) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.source = source;
        this.id = id;
        this.index = "not_specified";

    }

    public String getRecipeName() {
        return recipeName;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public String getImageUrl() {
        return  imageUrl;
    }

    public String getRating() {
        return rating;
    }

    public String getSource() {
        return source;
    }

    public String getId() { return id; }


    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getPushId() {
        return pushId;
    }
    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
