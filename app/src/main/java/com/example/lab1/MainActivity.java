package com.example.lab1;

import android.os.Environment;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> list_items = new ArrayList<String>();

    ArrayList<String> item_position = new ArrayList<String>();

    int count = 0 ;

    ArrayAdapter<String> arrayAdapter;

    ListView listView;

    String itemSave = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(TextManager.createFile()){
            Log.d("MY_APP_DEB" , "true");
        }

        listView = findViewById(R.id.itemList);

        arrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item , list_items);

        listView.setAdapter(arrayAdapter);

        list_items.add("আমার প্রথম আইটেম");
        list_items.add("দ্বিতীয় item");

        getData();

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                count++;

                mode.setTitle(count + " item selected ");
                item_position.add(list_items.get(position));

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
                        for (String msg : item_position){
                            list_items.remove(msg);

                            String[] lines = {};
                            String dataSet = TextManager.ReadFile(MainActivity.this);

                            lines = dataSet.split("\\r?\\n");

                            TextManager.clearFile();

                            TextManager.saveToFile("");

                            Log.d("MY_APP_DEB", String.valueOf(lines.length));

                            for(int l = 1 ; l < lines.length ; l++){
                                if(!lines[l].equals(msg)){
                                    TextManager.saveToFile(lines[l]);
                                    Log.d("MY_APP_DEB", "Here");
                                }
                            }
                        }
                        arrayAdapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "item removed : " + count , Toast.LENGTH_SHORT).show();
                        count = 0;
                        mode.finish();
                        return true;
                    default:
                        return  false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
    }


    private void getData() {
        String[] lines = {};
        String dataSet = TextManager.ReadFile(MainActivity.this);

        lines = dataSet.split("\\r?\\n");

        Log.d("MY_APP_DEB", String.valueOf(lines.length));

        if(lines.length != 1){
            list_items.addAll(Arrays.asList(lines).subList(1, lines.length));
            arrayAdapter.notifyDataSetChanged();
        }
    }




//    public boolean onCreateOtipnsMenu(Menu menu){
//
//        getMenuInflater().inflate(R.menu.todo,menu);
//        return true ;
//    }

    public void addToDoItem(View view) {
        EditText editText = findViewById(R.id.newitem);

        itemSave = editText.getText().toString();

        if(itemSave.equals("")){
            Toast.makeText(MainActivity.this,"PLease Enter Item" , Toast.LENGTH_SHORT).show();
        }else {
            list_items.add(itemSave);

            arrayAdapter.notifyDataSetChanged();

            if(TextManager.saveToFile(itemSave)){
                Log.d("MY_APP_DEB", "Saved");
            }

            editText.setText("");

            Toast.makeText(MainActivity.this, "Item added", Toast.LENGTH_SHORT).show();

        }
    }



//
//    private String read(){
//        String line = null;
//
//        FileInputStream fileInputStream = null;
//        try {
//            fileInputStream = new FileInputStream(new File(path + fileName));
//            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
//            BufferedReader bufferedReader = new BufferedReader((inputStreamReader));
//            StringBuilder stringBuilder = new StringBuilder();
//
//            while((line =  bufferedReader.readLine()) != null){
//                stringBuilder.append(line+System.getProperty("line.separator"));
//            }
//            fileInputStream.close();
//            line = stringBuilder.toString();
//            Log.d("MY_APP_DEBUG", line);
//            bufferedReader.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return line ;
//    }
//
//
//    private static boolean save(String data){
//        new File(path).mkdir();
//        File file = new File(path+fileName);
//        if(!file.exists()){
//            try {
//                file.createNewFile();
//                return true;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        FileOutputStream fileOutputStream = null;
//        try {
//            fileOutputStream = new FileOutputStream(file,true);
//            return true;
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            if (fileOutputStream != null) {
//                fileOutputStream.write((data + System.getProperty("line.separator")).getBytes());
//            } else {
//                Log.d("MY_APP_DEBUG", "NULL");
//            }
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }















//
//    private void readItems(){
//        File filesDir = getFilesDir();
//        File todoFile = new File(filesDir , "todo.txt");
//        try {
//            arrayList = new ArrayList<String>(FileUtils.readLines(todoFile));
//            Toast.makeText(MainActivity.this , " the item is read" , Toast.LENGTH_SHORT).show();
//        }catch (Exception e){
//            arrayList = new ArrayList<String>();
//            e.printStackTrace();
//
//        }
//    }
//
//    private  void saveItems(){
//        File fileDir = getFilesDir();
//        File todoFile = new File(fileDir , "todo.txt");
//        Toast.makeText(MainActivity.this , " the item is saved" , Toast.LENGTH_SHORT).show();
//        try {
//            FileUtils.writeLines(todoFile , arrayList);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


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
