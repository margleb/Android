package com.bawp.babyneeds.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.babyneeds.R;
import com.bawp.babyneeds.data.DatabaseHandler;
import com.bawp.babyneeds.model.Item;

import java.util.List;

public class RecycleViewsAdapter extends RecyclerView.Adapter<RecycleViewsAdapter.ViewHandler> {
    private List<Item> itemList;
    private Context context;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    public RecycleViewsAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false);
        return new ViewHandler(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHandler holder, int position) {
        Item item = itemList.get(position);
        holder.item_name.setText(String.format("Item: %s", item.getItemName()));
        holder.item_quantity.setText(String.format("Qty: %s", String.valueOf(item.getItemQuantity())));
        holder.item_color.setText(String.format("Color: %s", item.getItemColor()));
        holder.item_size.setText(String.format("Size: %s", String.valueOf(item.getItemSize())));
        holder.dateAdedd.setText(String.format("Added on: %s", item.getDateItemAdded()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHandler extends RecyclerView.ViewHolder implements View.OnClickListener {
        public int id;
        public TextView item_name;
        public TextView dateAdedd;
        public TextView item_quantity;
        public TextView item_color;
        public TextView item_size;
        public Button editButton;
        public Button deleteButton;

        public ViewHandler(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            item_name = itemView.findViewById(R.id.item_name);
            item_quantity = itemView.findViewById(R.id.item_quantity);
            item_color = itemView.findViewById(R.id.item_color);
            item_size = itemView.findViewById(R.id.item_size);
            dateAdedd = itemView.findViewById(R.id.item_date);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // получаем id текущего обьекта
            int position = getAdapterPosition();
            switch (v.getId()) {
                case R.id.editButton:
                    // edit item
                    editItem();
                    // Toast.makeText(context, "work!", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.deleteButton:
                    // delete button
                    // обьявляется дополнительно для избегания возможных ппроблем изменения позиции
                    position = getAdapterPosition();
                    Item item = itemList.get(position);
                    deleteItem(item.getId());
                    break;
            }
        }

        private void editItem() {

            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.popup, null);

            TextView title = view.findViewById(R.id.title);
            EditText babyItem = view.findViewById(R.id.babyItem);
            EditText itemQuantity = view.findViewById(R.id.itemQuantity);
            EditText itemColor = view.findViewById(R.id.itemColor);
            EditText itemSize = view.findViewById(R.id.itemSize);
            Button saveButton = view.findViewById(R.id.saveButton);
            saveButton.setText(R.string.update_text);

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // update our click
                    DatabaseHandler databaseHandler = new DatabaseHandler(context);
                    // update items

                }
            });

            Item item = itemList.get(getAdapterPosition());

            title.setText(R.string.edit_item);
            babyItem.setText(item.getItemName());
            itemQuantity.setText(String.valueOf(item.getItemQuantity()));
            itemColor.setText(item.getItemColor());
            itemSize.setText(String.valueOf(item.getItemSize()));

            builder.setView(view);
            dialog = builder.create();
            dialog.show();
        }

        private void deleteItem(final int id) {
            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirmation_pop, null);

            Button noButton = view.findViewById(R.id.conf_no_button);
            Button yesButton = view.findViewById(R.id.conf_yes_button);

            builder.setView(view);
            dialog = builder.create();
            dialog.show();

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db = new DatabaseHandler(context);
                    db.deleteItem(id);
                    // удалает карточку из интерфейса
                    itemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    dialog.dismiss();
                }
            });

        }
    }

}
