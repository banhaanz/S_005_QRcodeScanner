package com.me.s_005_qrcodescanner.activities.customviews;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.me.s_005_qrcodescanner.R;
import com.me.s_005_qrcodescanner.helpers.SimulateItem;
import com.me.s_005_qrcodescanner.model.LoremItem;

import java.util.ArrayList;

public class ListViewGroupAc extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_list_view_group);

        context = this;

        ListView listView = findViewById(R.id.dataList);
        ArrayList<LoremItem> items = SimulateItem.getSimulateItems(this);
        listView.setAdapter(new ListViewAdapter(items));
    }

    private class ListViewAdapter extends BaseAdapter{
        private ArrayList<LoremItem> arrayItem;
        public ListViewAdapter(ArrayList<LoremItem> arrayItem){
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
            if(view == null){
                view = LayoutInflater.from(context).inflate(R.layout.view_item,null);
            }

            TextView item_text = view.findViewById(R.id.txt_product_id);
            CheckBox item_check = view.findViewById(R.id.item_check);

            item_text.setText(arrayItem.get(i).getLoremText());
            item_check.setChecked(arrayItem.get(i).getLoremCheck());

            return view;
        }
    }
}
