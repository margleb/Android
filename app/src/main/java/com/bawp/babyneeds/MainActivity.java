package com.bawp.babyneeds;

import android.os.Bundle;

import com.bawp.babyneeds.data.DatabaseHandler;
import com.bawp.babyneeds.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button saveButton;
    private EditText babyItem;
    private EditText itemQuantity;
    private EditText itemColor;
    private EditText itemSize;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        // позволяет работать тулбару на более ранних версиях андройда
        setSupportActionBar(toolbar);

        databaseHandler = new DatabaseHandler(this);

        // check if item was saved
        List<Item> items = databaseHandler.getAllItems();
        for(Item item : items) {
            Log.d("Main", "onCreate: " + item.getDateItemAdded());
        }
        
       FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupDialog();
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    public void saveItem(View view) {
      // Todo: save each baby item to db
      Item item = new Item();
      String newItem = babyItem.getText().toString().trim();
      String newColor = itemColor.getText().toString().trim();
      int quantity = Integer.parseInt(itemQuantity.getText().toString().trim());
      int size = Integer.parseInt(itemSize.getText().toString().trim());
      item.setItemName(newItem);
      item.setItemColor(newColor);
      item.setItemQuantity(quantity);
      item.setItemSize(size);

      databaseHandler.addItem(item);
      Snackbar.make(view, "Item Saved", Snackbar.LENGTH_SHORT).show();

      // Todo: move to next screen - details screen
    }

    private void createPopupDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);
        babyItem = view.findViewById(R.id.babyItem);
        itemQuantity = view.findViewById(R.id.itemQuantity);
        itemColor = view.findViewById(R.id.itemColor);
        itemSize = view.findViewById(R.id.itemSize);
        saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // view передается для Snackbar
                if(!babyItem.getText().toString().isEmpty()
                && !itemColor.getText().toString().isEmpty()
                && !itemQuantity.getText().toString().isEmpty()
                && !itemSize.getText().toString().isEmpty()) {
                    saveItem(v);
                } else {
                    Snackbar.make(v, "Empty Fields not Allowed!", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        builder.setView(view);
        dialog = builder.create(); // создаем обьект dialog
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
