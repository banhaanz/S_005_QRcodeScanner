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
    private List<String> titles,descriptions,images,nums,statuss;
    private LayoutInflater inflater;

    public ExcelImportAdapter(Context context, List<String> num, List<String> descriptions, List<String> images, List<String> titles, List<String> status){
        Log.d("data", "titles -> "+titles);
        this.titles = titles;
        this.descriptions = descriptions;
        this.images = images;
        this.nums = num;
        this.statuss = status;
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
        String num = nums.get(position);
        String status = statuss.get(position);
        Picasso.get().load(images.get(position)).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background)
                //.resize(75,75)
                .fit()
                .centerCrop()
                .into(holder.thumbnail);
        holder.title.setText(title);
        holder.desc.setText(desc);
        holder.num.setText(num);
        holder.status.setText(status);

    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,desc,num,status;
        ImageView thumbnail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            num = itemView.findViewById(R.id.num);
            status = itemView.findViewById(R.id.status);
            thumbnail = itemView.findViewById(R.id.cardImg);

        }
    }

}
