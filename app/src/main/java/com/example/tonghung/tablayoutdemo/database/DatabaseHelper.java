package com.example.tonghung.tablayoutdemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import com.example.tonghung.tablayoutdemo.object.Product;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by tonghung on 30/12/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context/*, String name, SQLiteDatabase.CursorFactory factory, int version*/) {
        super(context, "sqlitedemodb1.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Product_tb(id_pro integer primary key autoincrement, name_pro " +
                "text not null, id_cate integer not null, price integer not null, image blob not " +
                "null, day text not null, foreign key (id_cate) references Category_tb(id_cate))");

        db.execSQL("create table Category_tb(id_cate integer primary key autoincrement, name_cate" +
                " text not null)");
        db.execSQL("insert into Category_tb(name_cate) values('Notebook')");
        db.execSQL("insert into Category_tb(name_cate) values('Smart phone')");
        db.execSQL("insert into Category_tb(name_cate) values('Tablet')");
        db.execSQL("insert into Category_tb(name_cate) values('Smart watch')");
        db.execSQL("insert into Category_tb(name_cate) values('Accessories')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Product_tb");
        db.execSQL("drop table if exists Category_tb");
        onCreate(db);
    }

    public long insertProduct(Product product){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name_pro", product.getName());
        contentValues.put("id_cate", product.getCate());
        contentValues.put("price", product.getPrice());
        contentValues.put("image", bitmap2Byte(product.getImage()));
        contentValues.put("day", dateToString(product.getDate()));
        return db.insert("Product_tb",null, contentValues);
    }

    public Cursor selectCate(){
        SQLiteDatabase db = getReadableDatabase();
//        return db.query("Category_tb", null, null, null, null, null, null);
        return db.rawQuery("select * from Category_tb", null);
    }

    private String dateToString(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }

    private byte[] bitmap2Byte(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 5, baos);
        return baos.toByteArray();
    }
}
