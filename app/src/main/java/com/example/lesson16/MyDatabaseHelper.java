package com.example.lesson16;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.ContentValues;
import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "comentarios.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "comentarios";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "titulo";
    public static final String COLUMN_TEXT = "texto";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_TEXT + " TEXT);";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Método para insertar un comentario
    public void insertComentario(Comentario comentario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, comentario.getTitulo());
        values.put(COLUMN_TEXT, comentario.getTexto());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // Método para obtener todos los comentarios
    public List<Comentario> getAllComentarios() {
        List<Comentario> comentariosList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_TEXT},
                null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Comentario comentario = new Comentario();
                comentario.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                comentario.setTitulo(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                comentario.setTexto(cursor.getString(cursor.getColumnIndex(COLUMN_TEXT)));
                comentariosList.add(comentario);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return comentariosList;
    }

    // Método para obtener un comentario por ID
    public Comentario getComentarioById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_TEXT},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Comentario comentario = new Comentario();
            comentario.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            comentario.setTitulo(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
            comentario.setTexto(cursor.getString(cursor.getColumnIndex(COLUMN_TEXT)));
            cursor.close();
            return comentario;
        }
        return null;
    }

    // Método para eliminar un comentario por ID
    public void deleteComentario(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
