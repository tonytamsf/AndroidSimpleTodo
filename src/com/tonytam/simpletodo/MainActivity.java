package com.tonytam.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {

	ArrayList<String> items;
	ArrayAdapter<String> itemAdapter;
	ListView lvItems;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        items = new ArrayList<String>();
        this.readItems();
 
        lvItems = (ListView) findViewById(R.id.listView1);
        
        // insert items into lvItems
        // Adapter pattern
        // ArrayAdaptor
        // translates into ListView
        // ArrayAdapter<String> => "Item 1" => <textView>
        itemAdapter = new ArrayAdapter<String>(this, 
        android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemAdapter);
        // Quick Key Cmd shift O to auto import
        // Quick Key Ctrl-space to auto create
        
        lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
	
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String item = items.get(arg2);
				itemAdapter.remove(item);
				return true;
			};
        });

    }

        
    public void readItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		this.items = new ArrayList<String>(FileUtils.readLines(todoFile));
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    public void writeItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		FileUtils.writeLines(todoFile, items);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public void onNewItem(View v) {
    	EditText etItem = (EditText) findViewById(R.id.editText1);
    	itemAdapter.add(etItem.getText().toString());
    	etItem.setText("");
    	this.writeItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
