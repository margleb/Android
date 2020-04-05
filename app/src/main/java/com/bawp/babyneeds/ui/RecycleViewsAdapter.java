package com.bawp.babyneeds.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.babyneeds.R;
import com.bawp.babyneeds.data.DatabaseHandler;
import com.bawp.babyneeds.model.Item;

import java.util.List;

public class RecycleViewsAdapter extends RecyclerView.Adapter<RecycleViewsAdapter.ViewHandler> {
    private List<Item> itemList;
    private Context context;

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

        private void deleteItem(int id) {
            DatabaseHandler db = new DatabaseHandler(context);
            db.deleteItem(id);
            // удалает карточку из интерфейса
            itemList.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
        }
    }

}
