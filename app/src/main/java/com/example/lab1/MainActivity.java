package com.example.lab1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
    String itemSave = "" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItems();

        //ArrayList list = (ArrayList) value.get((String) key[i]);

        l = findViewById(R.id.itemList);
        arrayList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item , arrayList);

        l.setAdapter(arrayAdapter);

        //manual data //

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
                saveItems();
                return true;
            }
        });
    }



    public boolean onCreateOtipnsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.todo,menu);
        return true ;
    }

    public void addToDoItem(View view) {
        EditText editText = findViewById(R.id.newitem);
        if(editText.getText().toString().equals("")){
            Toast.makeText(MainActivity.this,"PLease Enter Item" , Toast.LENGTH_SHORT).show();
        }else {

            itemSave = editText.getText().toString();

            arrayAdapter.add(editText.getText().toString());

            editText.setText("");
            saveItems();
            Toast.makeText(MainActivity.this, "Item added", Toast.LENGTH_SHORT).show();


        }
    }

    ///reading and writing items


    private void readItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir , "todo.txt");
        Log.d("MY_APP_DEBUG" , "items read");
        try {
            arrayList = new ArrayList<String>(FileUtils.readLines(todoFile));
            Toast.makeText(MainActivity.this , " the item is read" , Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            arrayList = new ArrayList<String>();
            e.printStackTrace();

        }
    }

    private  void saveItems(){
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir , "todo.txt");
        Toast.makeText(MainActivity.this , " the item is saved" , Toast.LENGTH_SHORT).show();
        Log.d("MY_APP_DEBUG" , "items save");
        try {
            FileUtils.writeLines(todoFile , arrayList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    //alternative saving
//    public void save(View v){
//        FileOutputStream fos = null;
//
//        try {
//
//            fos = openFileOutput("testing.txt",MODE_PRIVATE);
//
//            fos.write((itemSave.getBytes()));
//            Toast.makeText(MainActivity.this, "Saved in a directory", Toast.LENGTH_SHORT).show();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//
//        } catch (IOException e) {
//
//        }finally {
//            if (fos != null){
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }
////
//    public void load(View v){
//
//        FileInputStream fis = null;
//        try{
//            fis = openFileInput("testing.txt");
//            InputStreamReader isr = new InputStreamReader(fis);
//            BufferedReader br = new BufferedReader(isr);
//            StringBuilder sb = new StringBuilder();
//            String test ;
//            while ((test = br.readLine()) != null){
//                sb.append(test).append("\n");
//
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            if(fis != null){
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//
//    }


}
