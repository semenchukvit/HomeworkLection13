package com.example.vsemenchuk.homeworklection13;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class StudentsProvider extends ContentProvider {

    private DataBaseHelper dbHelper;
    private SQLiteDatabase db;

    private static final String AUTHORITY = "com.example.vsemenchuk.homeworklection13";
    private static final String STUDENTS_PATH = "students";

    public static final Uri STUDENT_URI = Uri.parse("content://" + AUTHORITY + "/" + STUDENTS_PATH);

    private static final String STUDENT_CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + STUDENTS_PATH;
    private static final String STUDENT_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + STUDENTS_PATH;

    private static final int URI_STUDENTS = 1;
    private static final int URI_STUDENTS_ID = 2;

    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, STUDENTS_PATH, URI_STUDENTS);
        uriMatcher.addURI(AUTHORITY, STUDENTS_PATH + "/#", URI_STUDENTS_ID);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DataBaseHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        db = dbHelper.getWritableDatabase();

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(Student.TABLE_STUDENTS);

        switch (uriMatcher.match(uri)) {
            case URI_STUDENTS_ID:
                queryBuilder.appendWhere(Student.KEY_ID + " = " + uri.getLastPathSegment());
                break;
        }

        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        String type = null;

        switch (uriMatcher.match(uri)) {
            case URI_STUDENTS:
                type = STUDENT_CONTENT_TYPE;
                break;
            case URI_STUDENTS_ID:
                type = STUDENT_CONTENT_ITEM_TYPE;
                break;
        }

        return type;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        db = dbHelper.getWritableDatabase();
        long id = 0;

        switch (uriMatcher.match(uri)) {
            case URI_STUDENTS:
                id = db.insert(Student.TABLE_STUDENTS, null, values);
                break;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(STUDENT_URI, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        db = dbHelper.getWritableDatabase();
        int count = 0;

        switch (uriMatcher.match(uri)) {
            case URI_STUDENTS:
                count = db.delete(Student.TABLE_STUDENTS, selection, selectionArgs);
                break;
            case URI_STUDENTS_ID:
                String id = uri.getLastPathSegment();
                if (selection == null || selection.isEmpty()) {
                    count = db.delete(Student.TABLE_STUDENTS, Student.KEY_ID + " = " + id, null);
                } else {
                    count = db.delete(Student.TABLE_STUDENTS, Student.KEY_ID + " = " + id + " and " + selection, selectionArgs);
                }
                break;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        db = dbHelper.getWritableDatabase();
        int count = 0;

        switch (uriMatcher.match(uri)) {
            case URI_STUDENTS:
                count = db.update(Student.TABLE_STUDENTS, values, selection, selectionArgs);
                break;
            case URI_STUDENTS_ID:
                String id = uri.getLastPathSegment();
                if (selection == null || selection.isEmpty()) {
                    count = db.update(Student.TABLE_STUDENTS, values, Student.KEY_ID + " = " + id, null);
                } else {
                    count = db.update(Student.TABLE_STUDENTS, values, Student.KEY_ID + " = " + id + " and " + selection, selectionArgs);
                }
                break;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
