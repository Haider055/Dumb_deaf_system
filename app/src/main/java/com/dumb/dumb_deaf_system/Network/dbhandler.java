package com.dumb.dumb_deaf_system.Network;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.dumb.dumb_deaf_system.models.model_mytalks;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class dbhandler extends SQLiteOpenHelper {


    SQLiteDatabase db;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Ecommerce";

    //table for product
    public static final String TABLE_NAME = "myfavorites_table";
    public static final String ID_COLUMN = "ID";
    public static final String TITLE_COLUMN = "TITLE";
    public static final String DESC_COLUMN = "DESCC";
    public static final String VIDEO_COLUMN = "VIDEO";
    public static final String GIF_COLUMN = "GIF";
    public static final String URDU_COLUMN = "URDU";
    public static final String CATEGORY_COLUMN = "CATEGORY";
    public static final String PROID_COLUMN = "PROID";
    public static final String FILETYPE_COLUMN = "FILETYPE";
    public static final String AUDIO_COLUMN = "AUDIO";



    public dbhandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db=this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String tableCART = "CREATE TABLE " + TABLE_NAME + " (" +
                ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                TITLE_COLUMN + " TEXT, " +
                DESC_COLUMN + " TEXT, " +
                GIF_COLUMN + " TEXT, " +
                VIDEO_COLUMN + " TEXT, " +
                PROID_COLUMN + " TEXT, " +
                FILETYPE_COLUMN + " TEXT, " +
                CATEGORY_COLUMN + " TEXT, " +
                AUDIO_COLUMN + " TEXT, " +
                URDU_COLUMN + " TEXT)";
        db.execSQL(tableCART);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public String addtofav(String proid,String title,String gif,String desc,String video,String urdu,String audio,String filetype,String category){

        String response="Already in favourites";

        String colomn[]=new String[]{ID_COLUMN,TITLE_COLUMN,DESC_COLUMN,GIF_COLUMN,VIDEO_COLUMN,AUDIO_COLUMN,URDU_COLUMN,PROID_COLUMN,FILETYPE_COLUMN,CATEGORY_COLUMN};

        String check=checkforduplicateincart(proid);
        if (check.equals("yes")){}
        else {
            ContentValues contentValues=new ContentValues();
            contentValues.put(TITLE_COLUMN,title);
            contentValues.put(GIF_COLUMN,gif);
            contentValues.put(DESC_COLUMN,desc);
            contentValues.put(VIDEO_COLUMN,video);
            contentValues.put(AUDIO_COLUMN,audio);
            contentValues.put(URDU_COLUMN,urdu);
            contentValues.put(PROID_COLUMN,proid);
            contentValues.put(FILETYPE_COLUMN,filetype);
            contentValues.put(CATEGORY_COLUMN,category);

            db.insert(TABLE_NAME,null,contentValues);
            response="Added to favourites";
        }

        return response;
    }

    private String checkforduplicateincart(String proid) {
        String a="no";
        String response="";
        String[] colomn =new String[]{ID_COLUMN,TITLE_COLUMN,DESC_COLUMN,GIF_COLUMN,VIDEO_COLUMN,AUDIO_COLUMN,URDU_COLUMN,PROID_COLUMN,FILETYPE_COLUMN,CATEGORY_COLUMN};
        Cursor query= db.query(TABLE_NAME,colomn,null,null,null,null,null,null);
        while (query.moveToNext()) {

            a = query.getString(query.getColumnIndex(PROID_COLUMN));
            if (a.equals(proid) || proid.equals(a)) {
                response = "yes";
                return "yes";
            }
            {
                response = "no";
            }

        }
        return response;
    }

    public List<model_mytalks> retrievecart(){
        List<model_mytalks> s=new ArrayList<>();
        String[] colomn =new String[]{ID_COLUMN,TITLE_COLUMN,DESC_COLUMN,GIF_COLUMN,VIDEO_COLUMN,AUDIO_COLUMN,URDU_COLUMN,PROID_COLUMN,FILETYPE_COLUMN,CATEGORY_COLUMN};
        Cursor query= db.query(TABLE_NAME,colomn,null,null,null,null,null,null);
        while (query.moveToNext()){
            String a,b,c,d,e,f,g,i,k,h;
            a=query.getString(query.getColumnIndex(TITLE_COLUMN));
            b=query.getString(query.getColumnIndex(DESC_COLUMN));
            c=query.getString(query.getColumnIndex(GIF_COLUMN));
            d=query.getString(query.getColumnIndex(VIDEO_COLUMN));
            e=query.getString(query.getColumnIndex(AUDIO_COLUMN));
            f=query.getString(query.getColumnIndex(PROID_COLUMN));
            g=query.getString(query.getColumnIndex(FILETYPE_COLUMN));
            h=query.getString(query.getColumnIndex(CATEGORY_COLUMN));
            i=query.getString(query.getColumnIndex(URDU_COLUMN));
            s.add(new model_mytalks(f,g,b,"",h,"","",c,d,e,i,a));
        }
        return s;
    }

    public void deletetalks(String id){
        db.delete(TABLE_NAME,PROID_COLUMN+ "=?",new String[]{String.valueOf(id)});
    }

}
