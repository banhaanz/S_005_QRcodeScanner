package com.me.s_005_qrcodescanner.helpers.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.s_005_qrcodescanner.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExcelImportAdapter extends RecyclerView.Adapter<ExcelImportAdapter.ViewHolder> {
    private List<String> titles,descriptions,images;
    private LayoutInflater inflater;

    public ExcelImportAdapter(Context context, List<String> titles, List<String> descriptions, List<String> images){
        Log.d("data", "titles -> "+titles);
        Log.d("data", "Des -> "+descriptions);
        this.titles = titles;
        this.descriptions = descriptions;
        this.images = images;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = titles.get(position);
        String desc = descriptions.get(position);
        Picasso.get().load(images.get(position)).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background)
                //.resize(75,75)
                .fit()
                .centerCrop()
                .into(holder.thumbnail);
        holder.title.setText(title);
        holder.desc.setText(desc);

    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,desc;
        ImageView thumbnail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            thumbnail = itemView.findViewById(R.id.cardImg);

        }
    }

}
