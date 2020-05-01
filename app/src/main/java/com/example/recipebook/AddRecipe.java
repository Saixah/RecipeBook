package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
    }

    public void onClick(View v) {
        SQLiteDatabase myDB;

        EditText editName = this.findViewById(R.id.editText3);
        EditText editIngredients = this.findViewById(R.id.editText);
        EditText editSteps = this.findViewById(R.id.editText4);

        String name1 = editName.getText().toString();
        String ingredients1 = editIngredients.getText().toString();
        String steps1 = editSteps.getText().toString();

        try {
            myDB = SQLiteDatabase.openDatabase(
                    "/data/data/" + this.getPackageName() + "/databases/Recipies.db",
                    null, SQLiteDatabase.OPEN_READWRITE);
            String insertSQL = "INSERT INTO Recipe (NAME, INGREDIENTS, STEPS) VALUES ('" +
                    name1 + "', '" + ingredients1 + "', '" + steps1 + "');";
            myDB.execSQL(insertSQL);
        } catch (SQLiteException e) {
            System.out.println("Cannot Read Database");
        }

        Intent addRecipe = new Intent(AddRecipe.this,
                MainActivity.class);
        startActivity(addRecipe);

    }
}

