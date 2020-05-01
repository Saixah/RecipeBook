package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase myDB;
        String name = null;
        String ingre = null;
        String steps = null;
        ArrayList<String> menuItems = new ArrayList<String>();

        try{
            myDB = SQLiteDatabase.openDatabase(
                    "/data/data/"+this.getPackageName()+"/databases/Recipies.db",
                    null,SQLiteDatabase.OPEN_READONLY);
            String sql = "SELECT * FROM Recipe";
            Cursor crs = myDB.rawQuery(sql,null);
            if (crs.moveToFirst()){
                do{
                    name = crs.getString(0);
                    ingre = crs.getString(1);
                    steps = crs.getString(2);
                    menuItems.add(name);
                }while(crs.moveToNext());
                myDB.close();
            }
        }catch(SQLiteException e){
        }

        final ListView listView = (ListView) this.findViewById(R.id.recipe_list);
        final ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                menuItems){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);
                listView.setDivider(new ColorDrawable(Color.WHITE));
                listView.setDividerHeight(1);
                // Generate ListView Item using TextView
                return view;
            }
        };

        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent recipe = new Intent(MainActivity.this,
                        DisplayRecipe.class);
                String recipeItem = (String) parent.getItemAtPosition(position);
                recipe.putExtra("RecipeName",recipeItem);
                startActivity(recipe);
            }

        });

        Button btn = (Button) this.findViewById(R.id.btn_add);
        btn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Intent addRecipe = new Intent(MainActivity.this,
                        AddRecipe.class);
                startActivity(addRecipe);
            }
        });
    }
}
