package com.me.s_005_qrcodescanner.activities.customviews;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.me.s_005_qrcodescanner.R;
import com.me.s_005_qrcodescanner.model.LoremItem;
import com.me.s_005_qrcodescanner.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ListViewProductGroupAc extends AppCompatActivity {
    private Context context;
    ListView dataList;
    List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_list_view_product_group);

        Product p1 = new Product("01","BE001",1);
        Product p2 = new Product("02","BE002",1);
        Product p3 = new Product("03","BE003",1);

        context = this;
        dataList = findViewById(R.id.dataList);
        productList = new ArrayList<>();
        productList.add(p1);
        productList.add(p2);
        productList.add(p3);

        dataList.setAdapter(new ListViewAdapter((ArrayList<Product>) productList));
    }

    private class ListViewAdapter extends BaseAdapter {
        private ArrayList<Product> arrayItem;

        public ListViewAdapter(ArrayList<Product> arrayItem) {
            this.arrayItem = arrayItem;
        }

        @Override
        public int getCount() {
            return arrayItem.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @SuppressLint({"ViewHolder", "InflateParams"})
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.view_item_steel, null);
            }

            TextView txt_id = view.findViewById(R.id.txt_product_id);
            TextView txt_qrscan = view.findViewById(R.id.txt_product_qrscan);
            TextView txt_status = view.findViewById(R.id.txt_product_status);


            txt_id.setText("Coil ID : "+arrayItem.get(i).getId());
            txt_qrscan.setText("QRCode : "+arrayItem.get(i).getQrscan());
            txt_status.setText("Status : "+arrayItem.get(i).getStatus());
            txt_status.setTextColor(Color.GREEN);

            return view;
        }
    }
}
