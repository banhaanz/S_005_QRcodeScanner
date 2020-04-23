package com.me.s_005_qrcodescanner.helpers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.me.s_005_qrcodescanner.model.DataList;
import com.me.s_005_qrcodescanner.model.DataSource;

import java.util.ArrayList;
import java.util.List;

    public class DBHelper extends SQLiteOpenHelper {
        private final String TAG = getClass().getSimpleName();
        private SQLiteDatabase sqLiteDatabase;
        public DBHelper(Context context){
            super(context, DataSource.DATABASE_NAME,null,DataSource.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_DATASOURCE_TABLE = String.format("CREATE TABLE %s " +
                            "(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT)",
                    DataSource.TABLE,
                    DataSource.Column.ID,
                    DataSource.Column.TITLE,
                    DataSource.Column.DATA,
                    DataSource.Column.URL);

            Log.i(TAG, CREATE_DATASOURCE_TABLE);

            //Create 'friend' database
            db.execSQL(CREATE_DATASOURCE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String DROP_DATASOURCE_TABLE = String.format("DROP TABLE IF EXISTS %s",DataSource.TABLE);

            Log.i(TAG, "Upgrade database from " + oldVersion +" to "+ newVersion);
            //Drop table if it exists
            db.execSQL(DROP_DATASOURCE_TABLE);
            onCreate(db);
        }

        //select all data
        public DataList getDataList(){
            DataList dataList = new DataList();
            List<String> id = new ArrayList<>();
            List<String> title = new ArrayList<>();
            List<String> data = new ArrayList<>();
            List<String> url = new ArrayList<>();

            //ask to permission to be writable database
            sqLiteDatabase = this.getWritableDatabase();

            @SuppressLint("Recycle")
            Cursor cursor = sqLiteDatabase.query(DataSource.TABLE,null,null,null,null,null,null);
            if(cursor != null){
                cursor.moveToFirst();
            }

            assert cursor != null;
            while (!cursor.isAfterLast()){
                //dataList.add(cursor.getLong(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3));
                id.add(cursor.getString(0));
                title.add(cursor.getString(1));
                data.add(cursor.getString(2));
                url.add(cursor.getString(3));

                dataList.setId(id);
                dataList.setTitle(title);
                dataList.setData(data);
                dataList.setUrl(url);

                cursor.moveToNext();
            }
            return dataList;
        }

        //add data
        public void addDataSource(DataSource dataSource){
            //ask to permission to be writable database
            sqLiteDatabase = this.getWritableDatabase();

            //declare ContentValues class
            ContentValues values = new ContentValues();

            values.put(DataSource.Column.TITLE,dataSource.getTitle());
            values.put(DataSource.Column.DATA,dataSource.getData());
            values.put(DataSource.Column.URL,dataSource.getUrl());

            sqLiteDatabase.insert(DataSource.TABLE,null,values);
            sqLiteDatabase.close();
        }

        //just select 1 data
        public DataSource getDataSource(String id){
            sqLiteDatabase = this.getWritableDatabase();

            @SuppressLint("Recycle")
            Cursor cursor = sqLiteDatabase.query(DataSource.TABLE,
                    null,
                    DataSource.Column.ID + "=?",
                    new String[]{id},
                    null,
                    null,
                    null);

            if(cursor!=null){
                cursor.moveToFirst();
            }

            DataSource dataSource = new DataSource();
            assert cursor != null;
            dataSource.setId((int) cursor.getLong(0));
            dataSource.setData(cursor.getString(1));
            dataSource.setData(cursor.getString(2));
            dataSource.setData(cursor.getString(3));

            sqLiteDatabase.close();
            return dataSource;
        }

        //Update 1 data
        public void updateFriend(){

        }

        //Delete 1 data
        public void deleteFriend(String id){

        }

        public void deleteAll(){
            sqLiteDatabase = this.getWritableDatabase();
            sqLiteDatabase.delete(DataSource.TABLE,null,null);
            sqLiteDatabase.close();
        }
    }
