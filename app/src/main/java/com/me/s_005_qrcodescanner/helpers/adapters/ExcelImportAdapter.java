package com.me.s_005_qrcodescanner.helpers.adapters;

import android.annotation.SuppressLint;
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
    private List<String> titles,descriptions,images,nums,statuss,scanneds,timestamps;
    private LayoutInflater inflater;

    public ExcelImportAdapter(Context context, List<String> num, List<String> descriptions,
                              List<String> images, List<String> titles, List<String> status,
                              List<String> scanned, List<String> timestamp){
        Log.d("data", "titles -> "+titles);
        this.titles = titles;
        this.descriptions = descriptions;
        this.images = images;
        this.nums = num;
        this.statuss = status;
        this.scanneds = scanned;
        this.timestamps = timestamp;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_layout,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = titles.get(position);
        String desc = descriptions.get(position);
        String num = nums.get(position);
        String status = statuss.get(position);
        String scanned = scanneds.get(position);
        String timestamp = timestamps.get(position);


        Picasso.get().load(images.get(position)).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background)
                //.resize(75,75)
                .fit()
                .centerCrop()
                .into(holder.thumbnail);
        holder.title.setText(title);
        holder.desc.setText("Ref NO: "+desc);
        holder.num.setText(num+".");
        holder.scanned.setText("times: "+scanned);
        holder.timestamp.setText("Last scan: "+timestamp);
        holder.status.setText("In stock: " + status);

    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,desc,num,status,scanned,timestamp;
        ImageView thumbnail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            num = itemView.findViewById(R.id.num);
            status = itemView.findViewById(R.id.status);
            scanned = itemView.findViewById(R.id.scantime);
            timestamp = itemView.findViewById(R.id.lastscan);
            thumbnail = itemView.findViewById(R.id.cardImg);

        }
    }

}
