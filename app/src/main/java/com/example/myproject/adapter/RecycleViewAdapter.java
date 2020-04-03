package com.example.myproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.DetailsActivity;
import com.example.myproject.R;
import com.example.myproject.model.Contact;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    private Context context;
    private List<Contact> contactList;

    public RecycleViewAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    // 1. Создает новое представление (вызывается layout менеджером)
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // создает новый вид
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    // 2. заменяет контент в представлении (вызывается layout менеджером)
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        // получает индекс элемента из базы данны
        Contact contact = contactList.get(position);
        // заменяет содержимое представления этим элементом
        viewHolder.contactName.setText(contact.getName());
        viewHolder.phoneNumber.setText(contact.getPhoneNumber());
    }

    @Override
    // возращает разамер данных в базе (вызывается layout менеджером)
    public int getItemCount() {
        return contactList.size();
    }

    // 3. предоставляет ссылки на views для каждого элемента data
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView contactName;
        public TextView phoneNumber;
        public ImageView icon_button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // itemView.setOnClickListener(this);
            contactName = itemView.findViewById(R.id.name);
            phoneNumber = itemView.findViewById(R.id.phone_number);
            icon_button = itemView.findViewById(R.id.icon_button);
            icon_button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // получаем позицию
            int position = getAdapterPosition();
            Contact contact = contactList.get(position);
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("name", contact.getName());
            intent.putExtra("phone", contact.getPhoneNumber());
            context.startActivity(intent);
            // switch(v.getId()) {
                // case R.id.icon_button:
                    // Log.d("IconClicked", "onClick: " + contact.getPhoneNumber());
                    // break;
            // }

            // Log.d("Clicked", "onClick: " + contact.getName());
        }
    }
}
