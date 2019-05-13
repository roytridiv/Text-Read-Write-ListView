package com.example.lab1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> arrayList ;

    ArrayAdapter<String> arrayAdapter;
    ListView l;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        l = findViewById(R.id.itemList);
        arrayList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item,arrayList);

        l.setAdapter(arrayAdapter);
        arrayList.add("amar item");
        arrayList.add("ditio item");
    }


    public boolean onCreateOtipnsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.todo,menu);
        return true ;
    }
}
