# Moringa Eats

## Description
This is an Android application that enables a user to search for recipes and nutrition information according to the ingredients. Moringa Eats uses the [Yummly API](developer.yummly.com) to parse information throughout the app. These recipes are divided according to categories which include: 
* Cake recipes
* Stew recipes
* Dessert recipes
* Soups
* Appetizers
* Drinks
And many more!

Enter your search criteria according to ingredients and click on `Enter` button. This will display a list of recipes and you can click on an individual recipe and see its ingredients. You also have the option of saving a particular recipe.

![Welcome Acivity](https://kakishamemoirs.files.wordpress.com/2019/03/screenshot_20190307-161547.png "Welcome Activity")   ![Recipes List](https://kakishamemoirs.files.wordpress.com/2019/03/screenshot_20190307-161602.png "Recipes List")   ![View Recipe Details](https://kakishamemoirs.files.wordpress.com/2019/03/screenshot_20190307-161611.png  "View Recipe Details")

## Author 

[MIT](LICENSE) © Licio Lentimo

## Prerequisites
* JDK
* Android Studio 3.2 and above(recommended)

## Installation
* Clone the repo.
* Open it in Android Studio
* The below dependencies are required in case you get build errors 
* After installing the above dependencies and you have a successful build, run the application on your Android device or emulator in Android Studio.


## Required libraries and dependencies
* API Level 27
* Gradle Version 4.6
``` implementation 'com.squareup.picasso:picasso:2.71828
implementation 'org.parceler:parceler-api:1.1.12'
 annotationProcessor 'org.parceler:parceler:1.1.12'
 implementation 'com.squareup.okhttp3:okhttp:3.12.1'
 implementation 'com.flaviofaria:kenburnsview:1.0.7'
  implementation 'com.google.firebase:firebase-database:16.0.1'
   implementation 'com.google.firebase:firebase-core:16.0.1'
   implementation 'com.firebaseui:firebase-ui-database:0.4.1'
   implementation 'com.google.firebase:firebase-auth:16.0.1'
 implementation 'com.jakewharton:butterknife:8.8.1'
 annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1```

