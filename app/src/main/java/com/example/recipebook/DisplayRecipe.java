package com.example.recipebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe);
        final TextView view_name = this.findViewById(R.id.recipe_title);
        final String name = view_name.getText().toString();
        String newString;
        Bundle extras = getIntent().getExtras();
        newString= extras.getString("RecipeName");
        database(newString);
        view_name.setText(newString);
        delete(newString);
    }

    public void delete(final String name){
        Button delete = (Button) this.findViewById(R.id.btn_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DisplayRecipe.this);
                builder.setTitle("Are you sure you want to delete?");
                builder.setMessage("It can't be retrieved after");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            SQLiteDatabase myDB;
                            myDB = SQLiteDatabase.openDatabase(
                                    "/data/data/" + getPackageName() + "/databases/Recipies.db",
                                    null, SQLiteDatabase.OPEN_READWRITE);
                            String insertSQL = "DELETE FROM Recipe WHERE NAME ='"+ name +"'";
                            myDB.execSQL(insertSQL);
                            Intent deleteRecipe = new Intent(DisplayRecipe.this,
                                    MainActivity.class);
                            startActivity(deleteRecipe);
                        } catch (SQLiteException e) {
                            System.out.println("Cannot Read Database");
                        }
                    }
                });

                // Set the alert dialog no button click listener
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void database(String recipe){
        SQLiteDatabase myDB;
        TextView view_ingredients = this.findViewById(R.id.textView4);
        view_ingredients.setMovementMethod(new ScrollingMovementMethod());
        TextView view_step = this.findViewById(R.id.textView5);
        view_step.setMovementMethod(new ScrollingMovementMethod());
        String ingre = null;
        String steps = null;

        try{
            myDB = SQLiteDatabase.openDatabase(
                    "/data/data/"+this.getPackageName()+"/databases/Recipies.db",
                    null,SQLiteDatabase.OPEN_READONLY);

            String sql = "SELECT * FROM Recipe WHERE name = '"+recipe+"'";
            Cursor crs = myDB.rawQuery(sql,null);

            if (crs.moveToFirst()){
                do{
                    ingre = crs.getString(1);
                    steps = crs.getString(2);
                }while(crs.moveToNext());
                myDB.close();
            }
        }catch(SQLiteException e){
            view_ingredients.setText("CANNOT READ DATABASE");
        }
        view_ingredients.setText(ingre);
        view_step.setText(steps);
    }

}
