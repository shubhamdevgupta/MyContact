package app.zapurse.mycontacts.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import app.zapurse.mycontacts.models.UserModel;

public class DBhelper extends SQLiteOpenHelper {
    SQLiteDatabase database;
    private static final String data = "DATABASE";
    private static final int version = 1;

    public DBhelper(Context context) {
        super(context, data, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry = "CREATE TABLE CONTACTS (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, MOBILE TEXT, EMAIL TEXT)";
        db.execSQL(qry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS CONTACTS");
        onCreate(db);
    }

    public String insertContact(String name, String mobile, String email) {

        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", name);
        values.put("MOBILE", mobile);
        values.put("EMAIL", email);
        float res = database.insert("CONTACTS", null, values);
        if (res == -1) return "Failed";
        else return "Contact Created";
    }

    public ArrayList<UserModel> showContact() {
        ArrayList<UserModel> arrayList = new ArrayList<>();
        String qry = "SELECT *FROM CONTACTS";
        database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(qry, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String mobile = cursor.getString(2);
                String email = cursor.getString(3);
                arrayList.add(new UserModel(name, email, mobile, id));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }

    public void deleteContact(int id) {
        database = this.getWritableDatabase();
        database.delete("CONTACTS", "ID=?", new String[]{Integer.toString(id)});
        database.close();
    }

    public String updateContact(UserModel model) {

        ContentValues values = new ContentValues();
        values.put("NAME", model.getName());
        values.put("MOBILE", model.getMobile());
        values.put("EMAIL", model.getEmail());
        float res = database.update("CONTACTS", values, "ID=?", new String[]{String.valueOf(model.getId())});
        database = this.getWritableDatabase();
        if (res == -1) return "Failed";
        else return "success";
    }
}
