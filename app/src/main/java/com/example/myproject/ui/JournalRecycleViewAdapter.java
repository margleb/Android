package com.example.myproject.ui;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.R;
import com.example.myproject.model.Journal;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

public class JournalRecycleViewAdapter extends RecyclerView.Adapter<JournalRecycleViewAdapter.ViewHolder> {
    private Context context;
    private List<Journal> journalList;

    public JournalRecycleViewAdapter(Context context, List<Journal> journalList) {
        this.context = context;
        this.journalList = journalList;
    }

    @NonNull
    @Override
    public JournalRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.journal_row, parent, false);
        return new ViewHolder(view);
        // return null;
    }

    @Override
    public void onBindViewHolder(@NonNull JournalRecycleViewAdapter.ViewHolder holder, int position) {
        Journal journal = journalList.get(position);
        holder.journal_title_list.setText(journal.getTitle());
        holder.journal_thought_list.setText(journal.getDescription());
        // пикассо плагин
        Picasso.get().load(journal.getImageUrl()).placeholder(R.drawable.image_three).into(holder.journal_image_view);
        holder.journal_timestamp_list.setText(DateUtils.getRelativeTimeSpanString(journal.getTimeAdded().getSeconds() * 1000));
    }

    @Override
    public int getItemCount() {
        return journalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView journal_image_view;
        private ImageButton journal_row_share_button;
        private TextView journal_title_list, journal_thought_list, journal_timestamp_list, journal_row_username;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            journal_image_view = itemView.findViewById(R.id.journal_image_view);
            journal_title_list = itemView.findViewById(R.id.journal_title_list);
            journal_thought_list = itemView.findViewById(R.id.journal_thought_list);
            journal_timestamp_list = itemView.findViewById(R.id.journal_timestamp_list);
            journal_row_username = itemView.findViewById(R.id.journal_row_username);
            journal_row_share_button = itemView.findViewById(R.id.journal_row_share_button);
            journal_row_share_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }
}
