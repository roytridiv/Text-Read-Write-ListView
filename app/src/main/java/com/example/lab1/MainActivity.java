package com.example.lab1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> arrayList ;
    ArrayList<String> list_items = new ArrayList<String>();
    int count = 0 ;
    int s = 0 ;
    ArrayAdapter<String> arrayAdapter;
    ListView l;
    Button b;
    private Object View;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        l = findViewById(R.id.itemList);
        arrayList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item,arrayList);

        l.setAdapter(arrayAdapter);
        arrayList.add("আমার প্রথম আইটেম");
        arrayList.add("দ্বিতীয় item");
      //  b = findViewById(R.id.r);

        l.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        l.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                count++;

                mode.setTitle(count + " item selected ");
                list_items.add(arrayList.get(position));

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {

                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.my_context_menu,menu);

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                switch (item.getItemId()){


                    case R.id.delete_id :
                        for (String msg : list_items){
                            arrayAdapter.remove(msg);
                        }
                        Toast.makeText(MainActivity.this, "items removed : " + count , Toast.LENGTH_SHORT).show();
                        count =0;
                        mode.finish();
                        return true;



                    default:
                        return  false;
                }

                // return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
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
        if(e.getText().toString().equals("")){
            Toast.makeText(MainActivity.this,"PLease Enter Item" , Toast.LENGTH_SHORT).show();
        }else {
            arrayAdapter.add(e.getText().toString());
            e.setText("");
            Toast.makeText(MainActivity.this, "Item added", Toast.LENGTH_SHORT).show();


        }
    }
}
