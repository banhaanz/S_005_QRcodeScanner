package com.me.s_005_qrcodescanner.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;

import com.me.s_005_qrcodescanner.R;
import com.me.s_005_qrcodescanner.model.LoremItem;

import java.util.ArrayList;
import java.util.Random;

public class SimulateItem {
    public static ArrayList<LoremItem> getSimulateItems(Context context) {
        int[] loremResId = {
                R.string.lorem_long_1, R.string.lorem_long_2,
                R.string.lorem_long_3, R.string.lorem_long_4,
                R.string.lorem_long_5,6,7
        };

        ArrayList<LoremItem> arrayList = new ArrayList<>();

        for (int i = 0; i < loremResId.length; i++) {
            Random rd = new Random();
            LoremItem loremItem = new LoremItem();
            loremItem.setLoremText("test"+(i+1));
            loremItem.setLoremCheck(rd.nextBoolean());
            arrayList.add(loremItem);
        }

        return arrayList;
    }

    @SuppressLint("StringFormatInvalid")
    public static ArrayList<LoremItem> getSimulateItemsForGridView(Context context) {
        ArrayList<LoremItem> arrayList = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            LoremItem loremItem = new LoremItem();
            loremItem.setLoremText(String.format(context.getString(R.string.gridview_item_x), i));
            loremItem.setLoremCheck(i % 5 == 0);
            arrayList.add(loremItem);
        }

        return arrayList;
    }

    public static CharSequence[] getSimulateColorNames(Context context) {
        return new CharSequence[] {
                context.getString(R.string.red), context.getString(R.string.green),
                context.getString(R.string.blue), context.getString(R.string.orange)
        };
    }

    public static int getSimulateColor(Context context, int index) {
        int[] colorResId = {
                android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_blue_light, android.R.color.holo_orange_light
        };

        return index == -1 ? Color.TRANSPARENT : context.getResources().getColor(colorResId[index]);
    }
}
