package shadattonmoy.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shadat Tonmoy on 6/18/2017.
 */

public class SQLiteAdapter {

    SQLiteHelper sqLiteHelper;
    public SQLiteAdapter(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
    }

    public long insertData(String name, String regNo) {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteHelper.NAME, name);
        contentValues.put(SQLiteHelper.REG_NO,regNo);
        return db.insert(SQLiteHelper.TABLE_NAME, null, contentValues);
    }

    public String getAllData()
    {
        StringBuffer stringBuffer = new StringBuffer();
        String detail = "";
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        String[] columns = {SQLiteHelper.UID,SQLiteHelper.NAME,SQLiteHelper.REG_NO};
        Cursor cursor = db.query(SQLiteHelper.TABLE_NAME,columns,null,null,null,null,null);

        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(SQLiteHelper.UID);
            int nameIndex = cursor.getColumnIndex(SQLiteHelper.NAME);
            int regNoIndex = cursor.getColumnIndex(SQLiteHelper.REG_NO);
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String regNo = cursor.getString(2);
            detail = detail + " "+ id + " " + name + " " + regNo + "\n";
            //stringBuffer.append(name + " " + regNo + "\n");
        }
        return detail;
    }

    public String getRegNo(String name)
    {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        String[] columns = {sqLiteHelper.UID,sqLiteHelper.REG_NO};
        String selection = sqLiteHelper.NAME + "=?";
        String[] selectionArgs = {name};
        Cursor cursor = db.query(sqLiteHelper.TABLE_NAME,columns,selection,selectionArgs,null,null,null,null);
        String result = "";
        while (cursor.moveToNext())
        {
            int indexOfID = cursor.getColumnIndex(sqLiteHelper.UID);
            int indexOfRegNo = cursor.getColumnIndex(sqLiteHelper.REG_NO);
            int index = cursor.getInt(indexOfID);
            String regNo = cursor.getString(indexOfRegNo);
            result = result + " " + index + " " + regNo + "\n";
        }
        return result;
    }

    public int update(String oldName, String newName)
    {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(sqLiteHelper.NAME,newName);
        String whereClause = sqLiteHelper.NAME+"=?";
        String[] whereArgs = {oldName};
        int result = db.update(sqLiteHelper.TABLE_NAME,contentValues,whereClause,whereArgs);
        return result;
    }

    public int delete(String name)
    {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        String whereClause = sqLiteHelper.NAME+"=?";
        String[] whereArgs = {name};
        int result = db.delete(sqLiteHelper.TABLE_NAME,whereClause,whereArgs);
        return result;

    }

    static class SQLiteHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "projectDB";
        private static final String TABLE_NAME = "user";
        private static final int DATABASE_VERSION = 9;
        private static final String UID = "_id";
        private static final String NAME = "name";
        private static final String REG_NO = "regNo";
        private static final String CREATE_TABLE = "create table "+TABLE_NAME+"("+UID+" INTEGER primary key autoincrement,"+NAME+" varchar(255),"+REG_NO+" varchar(255));";
        private static final String DROP_TABLE = "drop table if exists "+TABLE_NAME+" ";
        private Context context;

        public SQLiteHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            new Message(context,"OnCreate was called");

            try {
                db.execSQL(CREATE_TABLE);
            } catch (SQLException e) {
                new Message(context,e.getMessage());
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            new Message(context,"onUpgrade was called");
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (SQLException e) {
                new Message(context,e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
