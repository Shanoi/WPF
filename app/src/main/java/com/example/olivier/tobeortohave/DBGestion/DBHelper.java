package com.example.olivier.tobeortohave.DBGestion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.olivier.tobeortohave.Data.Magasin;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "magasin.sqlite";

    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public void openDataBase() throws SQLException, IOException {
        //Open the database
        String myPath = myContext.getDatabasePath(DB_NAME).getAbsolutePath();
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (!dbExist) {
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                // Copy the database in assets to the application database.
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database", e);
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = myContext.getDatabasePath(DB_NAME).getAbsolutePath();
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            //database doesn't exist yet.
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null;
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = myContext.getDatabasePath(DB_NAME).getAbsolutePath();
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void setSel(int id, int sel){

        ContentValues cv = new ContentValues();
        cv.put("selection",sel);

        myDataBase.update("magasin", cv, "idMagasin="+id, null);

        //myDataBase.


    }

    public Cursor fetchStat(){

        Cursor mCursor = myDataBase.rawQuery("SELECT *\n" +
                "FROM (SELECT * FROM Informations NATURAL JOIN magasin WHERE Informations.idMagasin = 2 OR Informations.idMagasin = 1)\n" +
                "WHERE date LIKE '%05/18'", null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }


    public Cursor fetchData(int id) {

        Cursor mCursor = myDataBase.rawQuery("SELECT * FROM Informations WHERE idMagasin = " + id, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;

    }

    public Cursor fetchDpt() {

        Cursor mCursor = myDataBase.rawQuery("SELECT * FROM departements", null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;

    }

    public Cursor fetchRegion() {

        Cursor mCursor = myDataBase.rawQuery("SELECT * FROM regions", null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;

    }

    public Cursor fetchVille() {

        Cursor mCursor = myDataBase.rawQuery("SELECT DISTINCT Ville FROM magasin ORDER BY Ville", null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;

    }

    public List<Magasin> getMagasins(String query) {

        System.out.println("QUERY FROM DB : " + query);

        ArrayList<Magasin> magasins = new ArrayList<>();

        String subQuery = "(SELECT num_departement FROM departements WHERE num_region = 7)";

        /*Cursor cursor = myDataBase.rawQuery("SELECT * " +
                "FROM magasin WHERE codePostale LIKE '01%' OR codePostale LIKE '02%'", null);*/

        /*Cursor cursor = myDataBase.rawQuery("SELECT I.* \n" +
                "FROM magasin I\n" +
                "INNER JOIN (SELECT num_departement \n" +
                "            FROM departements\n" +
                "\t\t\tWHERE num_region = 7) E\n" +
                "    ON I.codePostale LIKE E.num_departement || '%'", null);*/

        Cursor cursor = myDataBase.rawQuery(query, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            magasins.add(new Magasin(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getLong(5),
                    cursor.getLong(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getInt(10)));

            cursor.moveToNext();

        }

        cursor.close();

        return magasins;

    }
}