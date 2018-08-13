package com.example.vsemenchuk.homeworklection13;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "MyDB.db";
    private static final int DB_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + Student.TABLE_STUDENTS + "(" +
                Student.KEY_ID + " integer primary key autoincrement," +
                Student.KEY_FIRST_NAME + " text," +
                Student.KEY_LAST_NAME + " text," +
                Student.KEY_AGE + " integer" +
                ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertStudent(Student student) {
        long id = 0;
        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues cv = new ContentValues();
            cv.put(Student.KEY_FIRST_NAME, student.getFirstName());
            cv.put(Student.KEY_LAST_NAME, student.getLastName());
            cv.put(Student.KEY_AGE, student.getAge());
            id = db.insert(Student.TABLE_STUDENTS, null, cv);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public int updateStudent(Student student) {
        int count = 0;
        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues cv = new ContentValues();
            cv.put(Student.KEY_FIRST_NAME, student.getFirstName());
            cv.put(Student.KEY_LAST_NAME, student.getLastName());
            cv.put(Student.KEY_AGE, student.getAge());
            count = db.update(Student.TABLE_STUDENTS, cv, Student.KEY_ID + " = " +
                    student.getId(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    /*public Cursor getStudents() {
        SQLiteDatabase db = getWritableDatabase();

        return db.query(Student.TABLE_STUDENTS, null,null, null,
                null, null, null);
    }*/

    public ArrayList<Student> getStudents() {
        ArrayList<Student> students = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(Student.TABLE_STUDENTS, null,null, null,
                null, null, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Student student = new Student();
                student.setId(cursor.getInt(cursor.getColumnIndex(Student.KEY_ID)));
                student.setFirstName(cursor.getString(cursor.getColumnIndex(Student.KEY_FIRST_NAME)));
                student.setLastName(cursor.getString(cursor.getColumnIndex(Student.KEY_LAST_NAME)));
                student.setAge(cursor.getInt(cursor.getColumnIndex(Student.KEY_AGE)));

                students.add(student);
                cursor.moveToNext();
            }
        }

        cursor.close();

        return students;
    }

    public int deleteStudent(Student student) {
        int count = 0;
        SQLiteDatabase db = getWritableDatabase();

        try {
            count = db.delete(Student.TABLE_STUDENTS, Student.KEY_ID + " = " + student.getId(),
                    null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }
}
