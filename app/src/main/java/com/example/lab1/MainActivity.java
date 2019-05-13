package com.example.lab1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

    private void setupListViewListerner(){
        l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                arrayList.remove(position);
                arrayAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }



    public boolean onCreateOtipnsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.todo,menu);
        return true ;
    }

    public void addToDoItem(View view) {
        EditText e = findViewById(R.id.newitem);
        arrayAdapter.add(e.getText().toString());
        e.setText("");
    }
}
